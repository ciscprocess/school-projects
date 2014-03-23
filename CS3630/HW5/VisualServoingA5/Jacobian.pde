// Vector in image plane
void vector(float u, float v, float dx, float dy) {
  line(u, v, u+scale*FOCAL_LENGTH*dx, v+scale*FOCAL_LENGTH*dy);
}

// Jacobian at an image location u,v
void fillJacobian(double u, double v, float Z, double[][] J, int row) {
  double x=(u-width/2)/FOCAL_LENGTH, y=(v-height/2)/FOCAL_LENGTH;
  // omega_x 
  J[row+0][0] = x*y;
  J[row+1][0] = 1+y*y;
  // omega_y
  J[row+0][1] = -(1+x*y);
  J[row+1][1] = -x*y;
  // omega_z
  J[row+0][2] = y;
  J[row+1][2] = -x;
  // vx 
  J[row+0][3] = -1/Z;
  J[row+1][3] = 0;
  // vy
  J[row+0][4] = 0;
  J[row+1][4] = -1/Z;
  // vz
  J[row+0][5] = x/Z;
  J[row+1][5] = y/Z;
}

// Jacobian at an image location u,v
double[][] jacobian(float u, float v, float Z) {
  double[][] J = new double[2][6];
  fillJacobian( u, v, Z, J, 0);
  return J;
}

// Jacobian w respect to angular velocity
void arrows(float u, float v, boolean angular) {
  double [][] J = jacobian(u, v, 1);
  if (angular) {
    stroke(255, 0, 0); 
    vector(u, v, (float)J[0][0], (float)J[1][0]); // omega_x
    stroke(0, 255, 0); 
    vector(u, v, (float)J[0][1], (float)J[1][1]); // omega_y
    stroke(0, 0, 255); 
    vector(u, v, (float)J[0][2], (float)J[1][2]); // omega_z
  } 
  else 
  {
    stroke(255, 0, 0); 
    vector(u, v, (float)J[0][3], (float)J[1][3]); // vx
    stroke(0, 255, 0); 
    vector(u, v, (float)J[0][4], (float)J[1][4]); // vy
    stroke(0, 0, 255); 
    vector(u, v, (float)J[0][5], (float)J[1][5]); // vz
  }
}

// Give A,b, Solve for Least squares solution of A*X = b
// A and b are changed in the process
void solveQR(double[][] A, int m, int n, double[] b) {

  // DO in-place QR factorization
  double[] Rdiag = new double[n];
  for (int k = 0; k < n; k++) {
    // Compute 2-norm of k-th column without under/overflow.
    double nrm = 0;
    for (int i = k; i < m; i++) {
      nrm = Math.hypot(nrm, A[i][k]);
    }

    if (nrm != 0.0) {
      // Form k-th Householder vector.
      if (A[k][k] < 0) {
        nrm = -nrm;
      }
      for (int i = k; i < m; i++) {
        A[i][k] /= nrm;
      }
      A[k][k] += 1.0;

      // Apply transformation to remaining columns.
      for (int j = k+1; j < n; j++) {
        double s = 0.0; 
        for (int i = k; i < m; i++) {
          s += A[i][k]*A[i][j];
        }
        s = -s/A[k][k];
        for (int i = k; i < m; i++) {
          A[i][j] += s*A[i][k];
        }
      }
    }
    Rdiag[k] = -nrm;
  }

  // Compute Y = transpose(Q)*B
  for (int k = 0; k < n; k++) {
    double s = 0.0; 
    for (int i = k; i < m; i++) {
      s += A[i][k]*b[i];
    }
    s = -s/A[k][k];
    for (int i = k; i < m; i++) {
      b[i] += s*A[i][k];
    }
  }

  // Solve R*X = Y;
  for (int k = n-1; k >= 0; k--) {
    b[k] /= Rdiag[k];
    for (int i = 0; i < k; i++) {
      b[i] -= b[k]*A[i][k];
    }
  }
}

// Given Point2 errors, calculate desired twist
// Kp = proportional gain
Twist3 servo(Point2[] projections, Point2[] errors, int nrPoints, float f, float Z, double Kp) {
  
  // Create Ax=b system
  int m = 2*nrPoints;
  double[][] A = new double[m][6]; 
  double b[] = new double[m];
  for (int k=0;k<nrPoints;k++) {
    Point2 uv = projections[k];
    fillJacobian(uv.x_, uv.y_, Z, A, 2*k);
    Point2 e = errors[k];
    b[2*k] = Kp*e.x_/f;
    b[2*k+1] = Kp*e.y_/f;
  }
  
  // Solve in least-squares sense with QR factorization
  solveQR(A, m, 6, b);

  // Create the new twist
  Twist3 twist = new Twist3(b[0], b[1], b[2], b[3], b[4], b[5]);
  return twist;
}

