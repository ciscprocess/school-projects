/**
 * A 2D twist (2D linear and 1D angular velocity)
 */
class Twist2 {

  static final double PI = Math.PI;
  double vx_, vy_, omega_;

  // Constructor
  Twist2(double vx, double vy, double omega) {
    vx_=vx;
    vy_=vy;
    omega_=omega;
  }

  /// get x
  double vx() { 
    return vx_;
  }

  /// get y
  double vy() { 
    return vy_;
  }

  /// get omega
  double omega() { 
    return omega_;
  }

  // Exponential map using conjugation
  Pose2 expmap(double T) {
    double theta = omega_*T;
    if (Math.abs(theta)<1e-10) {
      return new Pose2(T*vx_, T*vy_, 0);
    }
    else {
      double x = -vy_/omega_, y = vx_/omega_; // calculate axis of rotation
      Pose2 push = new Pose2(x, y, 1, 0), R = new Pose2(0, 0, theta), pull = new Pose2(-x, -y, 1, 0);
      return push.compose(R.compose(pull)); // push*rotate*pull conjugation
    }
  }

  // print
  void print() {
    System.out.println(String.format("vx:%f m/s, vy:%f m/s, omega:%f rad/sec", vx_, vy_, omega()));
  }

  // prettPrint
  void prettPrint() {
    System.out.println(String.format("vx:%-3.1f cm/s, vy:%-3.1f cm/s, omega:%-4.1f degrees/sec", vx_*100, vy_*100, omega()*180/PI));
  }
}

