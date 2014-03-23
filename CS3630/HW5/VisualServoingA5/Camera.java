/**
 * Very simple camera
 */
class Camera {
  Pose3 pose_;
  double f_, u0_, v0_;
  Camera(Pose3 T, double f, double u0, double v0) {
    pose_ = T;
    f_=f;
    u0_=u0;
    v0_=v0;
  }
  
  Point2 project(Point3 P) {
    // Convert to camera coordinates
    //TODO: Transform to camera
    
    P = pose_.transform_to(P);
    
    // project and calibrate
    //TODO: Implement the camera projection
    
    Point2 scr_pnt = new Point2(0, 0);
    double x = P.x_ / P.z_;
    double y = P.y_ / P.z_;
    double u = f_ * x + u0_;
    double v = f_ * y + v0_; 
    scr_pnt.x_ = u;
    scr_pnt.y_= v;
    
    return scr_pnt;
  }
}
