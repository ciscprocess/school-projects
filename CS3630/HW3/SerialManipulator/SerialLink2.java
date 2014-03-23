/**
 * 2D Serial-link robot class, modeled after Corke's toolbox
 */
class SerialLink2 {

  int n_; ///< number of links
  Link2[] links_; /// The links
  Pose2 sTt0_; ///< tool pose in standard configuration

  /// Constructor
  SerialLink2(int n, Link2[] links, Pose2 tool0) {
    n_ = n;
    links_ = links;
    sTt0_ = tool0;
  }

  /**
   * Forward kinematics up to link j, using product of exponential twists
   * param q generalized coordinates
   * param j base 0 index of link, make n to evaluate tool frame
   * param sTj0 pose of link coordinate frame j with respect to base (or tool if j==n)
   */
  Pose2 fkine(double[] q, int j, Pose2 sTj0) {
    Pose2 t = new Pose2(0, 0, 0);
    for (int i=0;i<j;i++) {
      Pose2 exp_i = links_[i].exp(q[i]);
      t = t.compose(exp_i);
    }
    t = t.compose(sTj0);
    return t;
  }

  /// Forward kinematics, using product of exponential twists
  Pose2 fkine(double[] q) {
    return fkine(q,n_,sTt0_);
  }
}

