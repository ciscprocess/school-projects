final double DEFAULT_TOL = 1e-5;

void check(String msg, boolean test) {
  if (!test) {
    System.out.println("Test \"" + msg + "\" failed!");
    System.exit(0);
  }
}

void DOUBLES_EQUAL(String msg, double expected, double actual, double tol) {
  check(msg, Math.abs(expected-actual)<=tol);
}

void DOUBLES_EQUAL(String msg, double expected, double actual) {
  DOUBLES_EQUAL(msg, expected, actual, DEFAULT_TOL);
}

void test_project() {
  Camera camera = new Camera(new Pose3(), 100, 320, 240);
  Point3 P = new Point3(5, 8, 10);
  Point2 expected = new Point2(320+50, 240+80);
  Point2 p = camera.project(P);
  check("project", p.equals(expected, DEFAULT_TOL));
}

void test_project2() {
  Point3 P = new Point3(0, 1, 1);

  Point3 X = new Point3( 0, 1, 0);
  Point3 Y = new Point3( 0, 0, -1);
  Point3 Z = new Point3(-1, 0, 0);
  Rot3 R = new Rot3(X, Y, Z);

  Point3 Pl = R.unrotate(P);
  check("project2 A", Pl.equals(new Point3(1, -1, 0), DEFAULT_TOL));

  Point3 t = new Point3(10, 0, 0);
  Pose3 pose = new Pose3(R, t);
  Point3 Q = pose.transform_to(P);
  check("project2 B", Q.equals(new Point3(1, -1, 10), DEFAULT_TOL));

  Camera camera = new Camera(pose, 100, 320, 240);
  Point2 expected = new Point2(320+10, 240-10);
  Point2 p = camera.project(P);
  check("project2 C", p.equals(expected, DEFAULT_TOL));
}

 void test_servo() {
  Point2 [] projections = new Point2[nrPoints];
  Point2 [] errors = new Point2[nrPoints];
  Point2 [] desired = new Point2[nrPoints];
  Point3 [] landmarks = new Point3[nrPoints];
  
  landmarks[0] = new Point3(0, 1, 1);
  landmarks[1] = new Point3(0, -1, 1);
  landmarks[2] = new Point3(0, -1, -1);
  landmarks[3] = new Point3(0, 1, -1);
  landmarks[4] = new Point3(0, 0.5, -0.5);
  
  double u0 = width/2, v0 = height/2;
  
  desired[0] = new Point2(u0+100, v0-100);
  desired[1] = new Point2(u0-100, v0-100);
  desired[2] = new Point2(u0-100, v0+100);
  desired[3] = new Point2(u0+100, v0+100);
  desired[4] = new Point2(u0+50, v0+50);
  
  // calculate the actual projections
  camera = new Camera(pose, FOCAL_LENGTH, width/2, height/2);
  for (int i=0;i<nrPoints;i++) {
    projections[i] = camera.project(landmarks[i]);
  }    
  // calculate difference with desired projections
  for (int i=0;i<nrPoints;i++)
    errors[i] = desired[i].sub(projections[i]); 
  Twist3 expected = new Twist3(0, 0, 0, 0, 0, 0.05);
  Twist3 twist = servo(projections, errors, nrPoints, FOCAL_LENGTH, 5, 0.01);
  check("servo", twist.equals(expected, DEFAULT_TOL));
}

void test_exp() {
  Rot3 R = new Rot3();
  Point3 t = new Point3(0, 0, 1);
  Pose3 expected = new Pose3(R, t);
  Twist3 twist = new Twist3(0, 0, 0, 0, 0, 1);
  Pose3 actual = twist.expmap(1);
  check("exp", actual.equals(expected, DEFAULT_TOL));
}

void test_rodriguez() 
{
  Point3 axis = new Point3(0., 1., 0.); // rotation around Y
  double angle = 3.14 / 4.0;
  Rot3 actual = Rot3.rodriguez(axis, angle);
  Rot3 expected = new Rot3(new Point3(0.707388, 0, -0.706825), new Point3(0, 1, 0), new Point3(0.706825, 0, 0.707388));
  check("rodriguez", actual.equals(expected, DEFAULT_TOL));
}

void test_exp2() {
  double a=0.3, c=Math.cos(a), s=Math.sin(a), w=0.3;
  Twist3 xi = new Twist3(0, 0, w, w, 0, 1);
  Rot3 expectedR = new Rot3(new Point3(c, s, 0), new Point3(-s, c, 0), new Point3(0, 0, 1)); 
  Point3 expectedT = new Point3(0.29552, 0.0446635, 1);
  Pose3 expected = new Pose3(expectedR, expectedT);
  Pose3 actual = xi.expmap(1);
  check("exp2", actual.equals(expected, DEFAULT_TOL));
}

void test_compose() {
  Rot3 R = new Rot3(X, Y, Z);
  Point3 t = new Point3(10, 0, 0);
  Pose3 pose = new Pose3(R, t);
  Twist3 twist = new Twist3(0, 0, 0, 0, 0, 1);
  Point3 t2 = new Point3(10, 0, 0);
  Pose3 expected = new Pose3(R, t2);
  expected.t_.x_ -= twist.v_.z_;
  Pose3 actual = pose.compose(twist.expmap(1));
  check("compose", actual.equals(expected, DEFAULT_TOL));
}

void runTests() {
  test_project();
  test_project2();
  test_servo();
  test_exp();
  test_rodriguez();
  test_exp2();
  test_compose();
}

