/**
 * A 2D pose
 */
class Pose3 {

  Rot3 R_;
  Point3 t_;

  // Default Constructor
  Pose3() {
    R_= new Rot3();
    t_= new Point3();
  }

  // Constructor
  Pose3(Rot3 R, Point3 t) {
    R_=R;
    t_=t;
  }

  boolean equals(Pose3 T, double tol) {
    return t_.equals(T.t_,tol) && R_.equals(T.R_,tol);
  }

  // print, converts SI units to cm and degrees
//  void print() {
//    System.out.println(String.format("x:%f m, y:%f m, theta:%f rads", x_, y_, theta()));
//  }
//

  // print
  void prettyPrint() {
    R_.print();
    t_.prettyPrint();
  }

  // Transform point in local coordinates to world coordinates
  Point3 transform_from(Point3 P) {
    return R_.rotate(P).add(t_);
  }

  // Transform point to local coordinates
  Point3 transform_to(Point3 P) {
    return R_.unrotate(P.sub(t_));
  }
//
//  // Adjoint transformation
//  Twist3 adjoint(Twist3 p) {
//    double vx = c_*p.vx() - s_*p.vy() + y_*p.omega();
//    double vy = s_*p.vx() + c_*p.vy() - x_*p.omega();
//    return new Twist3(vx,vy,p.omega());
//  }
//
  // T = this * Q = T1*T2 = (R1*R2, R1*t2+t1)
  Pose3 compose(Pose3 Q) {
    // compose translation
    Point3 t = transform_from(Q.t_);
    // compose rotation
    Rot3 R = R_.compose(Q.R_);
    return new Pose3(R,t);
  }
}

