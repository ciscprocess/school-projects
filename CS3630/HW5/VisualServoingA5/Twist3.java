/**
 * A 2D twist (2D linear and 1D angular velocity)
 */
class Twist3 {

  static final double PI = Math.PI;
  Point3 w_, v_;

  // Constructor
  Twist3(double wx, double wy, double wz, double vx, double vy, double vz) {
    w_=new Point3(wx, wy, wz);
    v_=new Point3(vx, vy, vz);
  }

  boolean equals(Twist3 xi, double tol) {
    return w_.equals(xi.w_, tol) && v_.equals(xi.v_, tol);
  }

  // Exponential map using conjugation
  Pose3 expmap(double t) {
    double n = w_.norm();
    if (n<1e-10) {
      Point3 T = new Point3(t*v_.x_, t*v_.y_, t*v_.z_);
      return new Pose3(new Rot3(), T);
    }
    else {
      Point3 d = w_.div(n);
      Rot3 R = Rot3.rodriguez(d, n*t);
      double h = d.dot(v_);
      Point3 p = d.cross(v_).div(n);
      Rot3 I = new Rot3();
      Pose3 push = new Pose3(I, p), screw = new Pose3(R, d.mul(h*t)), pull = new Pose3(I, p.neg());
      return push.compose(screw.compose(pull)); // push*rotate*pull conjugation
    }
  }

  // print
  void print() {
    System.out.println(String.format("%g\t%g\t%g\t%g\t%g\t%g", 
    w_.x_*180/PI, w_.y_*180/PI, w_.z_*180/PI, v_.x_, v_.y_, v_.z_));
  }

  // prettPrint
  void prettyPrint() {
    System.out.println(String.format("wx:%g degrees/sec, wy:%g degrees/sec, wz:%g degrees/sec, vx:%g m/s, vy:%g m/s, vz:%g m/s", 
    w_.x_*180/PI, w_.y_*180/PI, w_.z_*180/PI, v_.x_, v_.y_, v_.z_));
  }
}

