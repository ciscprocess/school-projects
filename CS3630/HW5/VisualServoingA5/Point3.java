class Point3 {
  double x_, y_, z_;

  Point3() {
    x_=0;
    y_=0;
    z_=0;
  }

  Point3(double x, double y, double z) {
    x_=x;
    y_=y;
    z_=z;
  }

  boolean equals(Point3 q, double tol) {
    if (Math.abs(x_-q.x_)>tol) return false;
    if (Math.abs(y_-q.y_)>tol) return false;
    if (Math.abs(z_-q.z_)>tol) return false;
    return true;
  }

  Point3 add (Point3 Q) {
    return new Point3(x_+Q.x_, y_+Q.y_, z_+Q.z_);
  }

  Point3 neg() {
    return new Point3(-x_, -y_, -z_);
  }

  double norm() {
    return Math.sqrt(x_*x_ + y_*y_ + z_*z_);
  }

  Point3 sub(Point3 Q) {
    return new Point3(x_-Q.x_, y_-Q.y_, z_-Q.z_);
  }

  Point3 mul(double t) {
    return new Point3(x_*t, y_*t, z_*t);
  }

  Point3 div(double t) {
    return mul(1.0/t);
  }

  double dot(Point3 Q) {
    return  x_*Q.x_ + y_*Q.y_ + z_*Q.z_;
  }

  Point3 cross(Point3 q) {
    return new Point3( y_*q.z_ - z_*q.y_, 
    z_*q.x_ - x_*q.z_, 
    x_*q.y_ - y_*q.x_ );
  }

  // print
  void print() {
    System.out.println(String.format("%-3.1f, %-3.1f, %-3.1f", x_, y_, z_));
  }

  // print
  String prettyString() {
    return String.format("x:%-3.1f, y:%-3.1f, z:%-3.1f", x_, y_, z_);
  }

  // print
  void prettyPrint() {
    System.out.println(prettyString());
  }
}

