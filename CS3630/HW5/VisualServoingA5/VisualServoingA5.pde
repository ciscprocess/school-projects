// Illustrate Jacobian
// TODO: Create four plots, individual pattern
// 1: at correct depth, changes as you approach
// 2: at desired depth
// 3: high gain, at correct depth
// 4: low gain, at correct depth
// 5: # points

float FOCAL_LENGTH=500;
float scale=0.05;

int nrPoints = 5; 
Point3 [] landmarks = new Point3[nrPoints];
Point2 [] desired = new Point2[nrPoints];
Camera camera;

// Set up initial pose
Point3 X = new Point3( 0, 1, 0);
Point3 Y = new Point3( 0, 0, -1);
Point3 Z = new Point3(-1, 0, 0);
Rot3 R = new Rot3(X, Y, Z);
Point3 t = new Point3(10, 0, 0);
Pose3 pose = new Pose3(R, t);

void setup() {
  size(640, 480);
  double u0 = width/2, v0 = height/2;

  //TODO: Students choose desired and error
  // we make a square, center is world origin, X is normal pointing towards us
  landmarks[0] = new Point3(0, 1, 0.5);
  landmarks[1] = new Point3(0, -1, 0.1);
  landmarks[2] = new Point3(0, -0.8, -1);
  landmarks[3] = new Point3(0, 1, -1);
  landmarks[4] = new Point3(0, 0.5, -0.5);
  //we want to see those landmarks in the image right in front of us
  // at a range of 5m, 1 meter will be 100 pixels with FOCAL_LENGTH=500
  desired[0] = new Point2(u0+110, v0-40);
  desired[1] = new Point2(u0-90, v0);
  desired[2] = new Point2(u0-70, v0+110);
  desired[3] = new Point2(u0+110, v0+110);
  desired[4] = new Point2(u0+55, v0+55);

  runTests();
  
  // Rotate camera a bit before starting
  Twist3 twist = new Twist3(-0.1,0.2,0.3,0,0,0);
  pose = pose.compose(twist.expmap(1));
}

void mousePressed() {
  arrows(mouseX, mouseY, true);
}

// Stuff only used in draw
Point2 [] projections = new Point2[nrPoints];
Point2 [] errors = new Point2[nrPoints];

void draw() {
  
  background(255);
  for (int i=0;i<nrPoints;i++) {
    Point2 p = desired[i];
    fill(0,255,0);
    ellipse((float)p.x_,(float)p.y_,5,5);
  }
  
  // Create camera
  camera = new Camera(pose, FOCAL_LENGTH, width/2, height/2);

  // calculate the actual projections
  for (int i=0;i<nrPoints;i++) {
    Point2 p = camera.project(landmarks[i]);
    projections[i] = p;
    fill(255,0,0);
    ellipse((float)p.x_,(float)p.y_,5,5);
  } 

  // calculate difference with desired projections
  for (int i=0;i<nrPoints;i++) {
    errors[i] = desired[i].sub(projections[i]); // Do this
    //errors[i].prettyPrint();
  }

  // convert to a camera change
  float gain = 0.01;
  double dist = camera.pose_.t_.norm(); //TODO: Not pure visual servoing
  double noise = (random(30) - 15) / 15.0;
  Twist3 twist = servo(projections, errors, nrPoints, FOCAL_LENGTH, (float)(dist), gain);
  twist.print();

  // apply twist (already did proportional control above)
  pose = pose.compose(twist.expmap(1));
}

