/// 2D Version, Modified DH convention, revolute joint
class Link2 {

  double a_; // link length, only needed for drawing links
  Twist2 twist_; // Twist for this link's joint, needed for kinematics

  Link2(double a, Twist2 twist) {
    a_=a;
    twist_=twist;
  }

  // Exponential twist
  public Pose2 exp(double q) {
    return twist_.expmap(q);
  }
}

