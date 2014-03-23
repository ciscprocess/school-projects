/**
 * A 2D pose
 */
class Pose2 {

  double x_, y_; // position
  double c_, s_; // orientation is stored as cos/sin pair

  // Constructor
  Pose2(double x, double y, double theta) {
    x_=x;
    y_=y;
    c_=Math.cos(theta);
    s_=Math.sin(theta);
  }

  // Constructor from cos/sin
  Pose2(double x, double y, double c, double s) {
    x_=x;
    y_=y;
    c_=c;
    s_=s;
  }

  boolean equals(Pose2 q, double tol) {
    if (Math.abs(x_-q.x_)>tol) return false;
    if (Math.abs(y_-q.y_)>tol) return false;
    if (Math.abs(c_-q.c_)>tol) return false;
    if (Math.abs(s_-q.s_)>tol) return false;
    return true;
  }

  /// get x
  double x() { 
    return x_;
  }

  /// get y
  double y() { 
    return y_;
  }

  /// get translation
  Point2 translation() { 
    return new Point2(x_, y_);
  }

  /// get theta, in radians
  double theta() { 
    return Math.atan2(s_, c_);
  }

  /// get cos(theta)
  double c() { 
    return c_;
  }

  /// get sin(theta)
  double s() { 
    return s_;
  }

  // print, converts SI units to cm and degrees
  void print() {
    System.out.println(String.format("x:%f m, y:%f m, theta:%f rads", x_, y_, theta()));
  }

  // print, converts SI units to cm and degrees
  String prettyString() {
    return String.format("x:%-3.1f cm, y:%-3.1f cm, theta:%-4.1f degrees", x_*100, y_*100, theta()*180/Math.PI);
  }

  // print, converts SI units to cm and degrees
  void prettyPrint() {
    System.out.println(prettyString());
  }

  // Transform point in local coordinates to world coordinates
  Point2 transform_from(Point2 p) {
    double x = c_*p.x() - s_*p.y() + x_;
    double y = s_*p.x() + c_*p.y() + y_;
    return new Point2(x, y);
  }

  // T = this * Q= T1*T2 = (R1*R2, R1*t2+t1)
  Pose2 compose(Pose2 q) {
    // compose translation
    Point2 t = transform_from(q.translation());
    // compose rotation
    double c = c_ * q.c() - s_ * q.s(), s = s_ * q.c() + c_ * q.s();
    return new Pose2(t.x(), t.y(), c, s);
  }
}

