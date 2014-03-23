/**
 <h3>3DOF Serial Manipulator</h3>
 <p>Author: <a href="http://frank.dellaert.com">Frank Dellaert</a></p>
 <!--Creative Commons License--><a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">
 <img alt="Creative Commons License" style="border-width: 0" src="http://i.creativecommons.org/l/by-nc-sa/2.5/88x31.png"/></a><br/>
 This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">Creative Commons Attribution-Noncommercial-Share Alike 2.5  License</a>.<!--/Creative Commons License--><!-- <rdf:RDF xmlns="http://web.resource.org/cc/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"><Work rdf:about=""><license rdf:resource="http://creativecommons.org/licenses/by-nc-sa/2.5/" /><dc:type rdf:resource="http://purl.org/dc/dcmitype/InteractiveResource" /></Work><License rdf:about="http://creativecommons.org/licenses/by-nc-sa/2.5/"><permits rdf:resource="http://web.resource.org/cc/Reproduction"/><permits rdf:resource="http://web.resource.org/cc/Distribution"/><requires rdf:resource="http://web.resource.org/cc/Notice"/><requires rdf:resource="http://web.resource.org/cc/Attribution"/><prohibits rdf:resource="http://web.resource.org/cc/CommercialUse"/><permits rdf:resource="http://web.resource.org/cc/DerivativeWorks"/><requires rdf:resource="http://web.resource.org/cc/ShareAlike"/></License></rdf:RDF>
 -->
 */

Link2 [] links; // manipulator links
Pose2 tool0 = new Pose2(0, 9.5, PI/2); // tool pose at rest
SerialLink2 manipulator;

Pose2 [] sTi0; // link coordinate frames at rest, for drawing

HScrollbar[] scrollBar = new HScrollbar[3];  // scrollbars
double[] q = new double[3]; // generalized joint coordinates   
PFont font; // font
double Kp = 0.02;

int w = 600, h = 400;

double[][] JT(double[] qq, Pose2 pp)
{
  double dxdt0 = links[0].a_ * -Math.cos(-qq[0]) + links[1].a_ * -Math.cos(-qq[0] - qq[1] - qq[2]) + links[2].a_ * -Math.cos(-qq[0] - qq[1] - qq[2]);
  double dydt0 = links[0].a_ * Math.sin(-qq[0]) + links[1].a_ * Math.sin(-qq[0] - qq[1] - qq[2]) + links[2].a_ * Math.sin(-qq[0] - qq[1] - qq[2]);
  
  double dxdt1 = links[1].a_ * -Math.cos(-qq[0] - qq[1] - qq[2]) + links[2].a_ * -Math.cos(-qq[0] - qq[1] - qq[2]);
  double dydt1 = links[1].a_ * Math.sin(-qq[0] - qq[1] - qq[2]) + links[2].a_ * Math.sin(-qq[0] - qq[1] - qq[2]);
  
  double dxdt2 = links[2].a_ * -Math.cos(-qq[0] - qq[1] - qq[2]);
  double dydt2 = links[2].a_ * Math.sin(-qq[0] - qq[1] - qq[2]);
  double[][] jacobianT = new double[][] {{dxdt0, dydt0}, 
                                           {dxdt1, dydt1}, 
                                           {dxdt2, dydt2}};
  return jacobianT;
}

double[] matVec(double[][] mat, double[] vec)
{
  return new double[] {mat[0][0] * vec[0] + mat[0][1] * vec[1], mat[1][0] * vec[0] + mat[1][1] * vec[1], mat[2][0] * vec[0] + mat[2][1] * vec[1]};
}

double[] dq(Pose2 p, Pose2 pd)
{
  double[] diff = {Kp * (pd.x() - p.x()), Kp * (pd.y() - p.y())};
  return matVec(JT(q, p), diff);
}

void setup () {
  Tests.run(); // Run unit tests

  size(600, 460); // create display space
  hint(ENABLE_NATIVE_FONTS);
  font = createFont("Helvetica", 12);
  textFont(font);

  // Create scrollbars
  scrollBar[0] = new HScrollbar(0, h+48, w, 16, 1);
  scrollBar[1] = new HScrollbar(0, h+30, w, 16, 1);
  scrollBar[2] = new HScrollbar(0, h+12, w, 16, 1);
  //scrollBar[3] = new HScrollbar(0, h - 6, w, 16, 1);

  // Create robot from class notes
  links = new Link2[3];
  links[0] = new Link2(3.5, new Twist2(0, 0, 1));
  links[1] = new Link2(3.5, new Twist2(3.5, 0, 1));
  links[2] = new Link2(2, new Twist2(7, 0, 1));
  //links[3] = new Link2(1, new Twist2(9, 0, 1));
  manipulator = new SerialLink2(3, links, tool0);

  // Create zero configuration link frames for drawing
  sTi0 = new Pose2[3];
  sTi0[0] = new Pose2(0, 0, PI/2);
  sTi0[1] = new Pose2(0, 3.5, PI/2);
  sTi0[2] = new Pose2(0, 7, PI/2);
  //sTi0[3] = new Pose2(0, 9, PI/2);
}

/// draw is called by Processing multiple times
void draw() {

  background(255);

  // update and display the scrollbars
  for (int i=0;i<3;i++) {
    scrollBar[i].update();
    scrollBar[i].display();
  }

  // Get joint angles
  //for (int i=0;i<3;i++) {
    //float x = scrollBar[i].getPos();
    //if(i>0)
      //q[i] = Math.PI*(2*x/w-1)/3;
    //else
      //q[i] = Math.PI*(2*x/w-1)/4;
      
  //}

  // set origin and scale
  translate(w/2, 380);
  scale(35);
  textSize(0.4);
  
  Pose2 pd = new Pose2(1.5, 3.1, 1); 
  // Draw the robot
  Pose2 p = drawRobot(q);
  double[] dqq =  dq(p, pd);
  q[0] = q[0] + dqq[0];
  q[1] = q[1] + dqq[1];
  q[2] = q[2] + dqq[2];
  //drawPoints();
  if (mousePressed) {
    println(String.format("q = (%.1f %.1f %.1f)", q[0]*180/Math.PI, q[1]*180/Math.PI, q[2]*180/Math.PI));
    println(String.format("p = (%.1f %.1f %.1f)", p.x(), p.y(), p.theta()));
  }
}

