/// Apply a 2D rigid transformation to Processing Applet
void apply(Pose2 p) {
  // Note the negative signs here is to put Y-axis up again
  translate((float)p.x(), -(float)p.y());
  rotate(-(float)p.theta());
}

// Draw a rotational joint
void drawJoint(float x) {
  stroke(0);
  strokeWeight(0.03);

  // draw origin
  ellipseMode(CENTER);
  fill(255, 0, 0);
  ellipse(x, 0, 0.2, 0.2);
}

// Draw a link
void drawLink(float x0, float x1, float w, boolean dimension) {
  stroke(0);
  strokeWeight(0.03);

  // link
  noFill();
  float d=w/2;
  rectMode(CORNERS);
  rect(x0-d, -d, x1+d, d, 5);
  
  // line
  strokeWeight(0.01);
  line(x0, 0, x1, 0);
  
  // dimension
  if (dimension) {
    stroke(0);
    strokeWeight(0.01);
    line(x0, -3*d, x1, -3*d);
    line(x0, 0, x0, -3.5*d);
    line(x1, 0, x1, -3.5*d);
    text(String.format("%.1f",x1-x0), (x0+x1)/2-0.5, -5*d);
  }  

  // frame
  strokeWeight(0.02);
  stroke(255, 0, 0);
  line(0, 0, 1, 0);
  stroke(0, 255, 0);
  line(0, 0, 0, -1);
}

/// Draw a frame in processing, with textual option
void drawFrame(Pose2 p, boolean textual) {
  pushMatrix();
  apply(p);
  strokeWeight(0.05);
  stroke(255, 0, 0);
  line(0, 0, 1, 0);
  stroke(0, 255, 0);
  line(0, 0, 0, -1);
  // dimension
  if (textual) {
    stroke(0);
    rotate((float)p.theta());
    float x = (float)p.x();
    float y = (float)p.y();
    text(String.format("%-3.1f, %-3.1f, %-4.1f", p.x(), p.y(), p.theta()*180/Math.PI),0,-0+1);
  }  
  popMatrix();
}

/// Draw a frame in processing
void drawFrame(Pose2 p) {
  drawFrame(p,false);
}

// show twist circles around joint
void drawTwist(Point2 p) {
  strokeWeight(0.01);
  stroke(128);
  noFill();
  ellipseMode(RADIUS);
  for (float r=1;r<=5;r++)
    ellipse((float)p.x(), -(float)p.y(), r, r);
}


// Draw robot and return Tool pose, with optional dimensions flag
Pose2 drawRobot(double q[], boolean dimensions) {
  drawFrame(new Pose2(0, 0, 0));

  // Draw links 
  for (int i=0;i<joints;i++) {
    pushMatrix(); // save current drawing transformation
    apply(manipulator.fkine(q, i+1, sTi0[i]));
    drawLink(0, (float)links[i].a_, 1, dimensions);
    popMatrix();
  }

  // Check forward kinematics by drawing tool frame
  Pose2 sTt = manipulator.fkine(q);
  boolean textual = true;
  drawFrame(sTt,textual);

  return sTt;
}

// Draw robot and return Tool pose
Pose2 drawRobot(double q[]) {
  return drawRobot(q,true);
}
