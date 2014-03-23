class Rot3 {

  Point3[] axes_; // 3 rotation axes X,Y,Z

  // Deafault constructor
  Rot3 () {
    axes_ = new Point3[3];
    axes_[0]=new Point3(1, 0, 0);
    axes_[1]=new Point3(0, 1, 0);
    axes_[2]=new Point3(0, 0, 1);
  }

  //  constructor from axes in columns
  Rot3 (Point3 X, Point3 Y, Point3 Z) {
    axes_ = new Point3[3];
    axes_[0]=X;
    axes_[1]=Y;
    axes_[2]=Z;
  }

  // Row order constructor
  Rot3(double R11, double R12, double R13, 
  double R21, double R22, double R23, 
  double R31, double R32, double R33) {
    axes_ = new Point3[3];
    axes_[0] = new Point3(R11, R21, R31);
    axes_[1] = new Point3(R12, R22, R32);
    axes_[2] = new Point3(R13, R23, R33);
  }

  boolean equals(Rot3 R, double tol) {
    return axes_[0].equals(R.axes_[0], tol) && axes_[1].equals(R.axes_[1], tol) && axes_[2].equals(R.axes_[2], tol);
  }

  // print
  void print() {
    System.out.println(String.format("%-3.1f, %-3.1f, %-3.1f", axes_[0].x_, axes_[1].x_, axes_[2].x_));
    System.out.println(String.format("%-3.1f, %-3.1f, %-3.1f", axes_[0].y_, axes_[1].y_, axes_[2].y_));
    System.out.println(String.format("%-3.1f, %-3.1f, %-3.1f", axes_[0].z_, axes_[1].z_, axes_[2].z_));
  }

  Point3 unrotate(Point3 P) {
    double x = axes_[0].x_*P.x_ + axes_[0].y_*P.y_ + axes_[0].z_*P.z_;
    double y = axes_[1].x_*P.x_ + axes_[1].y_*P.y_ + axes_[1].z_*P.z_;
    double z = axes_[2].x_*P.x_ + axes_[2].y_*P.y_ + axes_[2].z_*P.z_;

    return new Point3(x, y, z);
  }

  Point3 rotate(Point3 P) {
    double x = axes_[0].x_*P.x_ + axes_[1].x_*P.y_ + axes_[2].x_*P.z_;
    double y = axes_[0].y_*P.x_ + axes_[1].y_*P.y_ + axes_[2].y_*P.z_;
    double z = axes_[0].z_*P.x_ + axes_[1].z_*P.y_ + axes_[2].z_*P.z_;

    return new Point3(x, y, z);
  }

  Rot3 compose(Rot3 R) {
    return new Rot3(rotate(R.axes_[0]), rotate(R.axes_[1]), rotate(R.axes_[2]));
  }

  static Rot3 rodriguez(Point3 w, double theta) {
    // get components of axis \omega
    double wx = w.x_, wy=w.y_, wz=w.z_;
    double wwTxx = wx*wx, wwTyy = wy*wy, wwTzz = wz*wz;
    double l_n = wwTxx + wwTyy + wwTzz;
    if (Math.abs(l_n-1.0)>1e-9) System.out.println("rodriguez: length of n should be 1");

    double c = Math.cos(theta), s = Math.sin(theta), c_1 = 1 - c;

    double swx = wx * s, swy = wy * s, swz = wz * s;
    double C00 = c_1*wwTxx, C01 = c_1*wx*wy, C02 = c_1*wx*wz;
    double                  C11 = c_1*wwTyy, C12 = c_1*wy*wz;
    double                                   C22 = c_1*wwTzz;

    return new Rot3(
    c + C00, -swz + C01, swy + C02, 
    swz + C01, c + C11, -swx + C12, 
    -swy + C02, swx + C12, c + C22);
  }

  static Rot3 rodriguez(Point3 w) {
    double t = w.norm();
    if (t < 1e-10) return new Rot3();
    return rodriguez(w.div(t), t);
  }
}

