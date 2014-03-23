class Point2 {
  double x_, y_;
  Point2(double x, double y) {
    x_=x;
    y_=y;
  }

  boolean equals(Point2 q, double tol) {
    if (Math.abs(x_-q.x_)>tol) return false;
    if (Math.abs(y_-q.y_)>tol) return false;
    return true;
  }

  // print, converts SI units to cm and degrees
  void print() {
    System.out.println(String.format("x:%f m, y:%f m", x_, y_));
  }
  
  Point2 sub (Point2 q) {
    return new Point2(x_-q.x_,  y_-q.y_);
  }

  // print
  String prettyString() {
    return String.format("x:%-3.1f, y:%-3.1f", x_, y_);
  }

  // print
  void prettyPrint() {
    System.out.println(prettyString());
  }
}

