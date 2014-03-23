// Unit tests
class Tests {
  
  static final double DEFAULT_TOL = 1e-5;

  static void check(String msg, boolean test) {
    if (!test) {
      System.out.println("Test \"" + msg + "\" failed!");
      System.exit(0);
    }
  }

  static void DOUBLES_EQUAL(String msg, double expected, double actual, double tol) {
    check(msg, Math.abs(expected-actual)<=tol);
  }

  static void DOUBLES_EQUAL(String msg, double expected, double actual) {
    DOUBLES_EQUAL(msg, expected, actual, DEFAULT_TOL);
  }

  static SerialLink2 twolinkExample() {
    Link2 [] links = new Link2[2];
    links[0] = new Link2(1,new Twist2(0,0,1));
    links[1] = new Link2(1,new Twist2(0,-1,1));
    return new SerialLink2(2,links,new Pose2(2,0,0));
  }

  static void test_fkine1() {
    Pose2 expected_sTt = new Pose2(2,0,0);
    double[] q = {0,0};
    SerialLink2 twolink =  twolinkExample();
    Pose2 actual_sTt = twolink.fkine(q);
    check("test_fkine1", actual_sTt.equals(expected_sTt,DEFAULT_TOL));
  }

  static void test_fkine2() {
    Pose2 expected_sTt = new Pose2(1.7071,0.7071,0);
    double[] q = {Math.PI/4, -Math.PI/4};
    SerialLink2 twolink =  twolinkExample();
    Pose2 actual_sTt = twolink.fkine(q);
    check("test_fkine2", actual_sTt.equals(expected_sTt,DEFAULT_TOL));
  }

  static void run() {
    test_fkine1();
    test_fkine2();
  }
}

