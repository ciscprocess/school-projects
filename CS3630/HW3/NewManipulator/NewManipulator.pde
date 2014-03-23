/**
 <h3>3DOF Serial Manipulator</h3>
 <p>Author: <a href="http://frank.dellaert.com">Frank Dellaert</a></p>
 <!--Creative Commons License--><a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">
 <img alt="Creative Commons License" style="border-width: 0" src="http://i.creativecommons.org/l/by-nc-sa/2.5/88x31.png"/></a><br/>
 This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">Creative Commons Attribution-Noncommercial-Share Alike 2.5  License</a>.<!--/Creative Commons License--><!-- <rdf:RDF xmlns="http://web.resource.org/cc/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"><Work rdf:about=""><license rdf:resource="http://creativecommons.org/licenses/by-nc-sa/2.5/" /><dc:type rdf:resource="http://purl.org/dc/dcmitype/InteractiveResource" /></Work><License rdf:about="http://creativecommons.org/licenses/by-nc-sa/2.5/"><permits rdf:resource="http://web.resource.org/cc/Reproduction"/><permits rdf:resource="http://web.resource.org/cc/Distribution"/><requires rdf:resource="http://web.resource.org/cc/Notice"/><requires rdf:resource="http://web.resource.org/cc/Attribution"/><prohibits rdf:resource="http://web.resource.org/cc/CommercialUse"/><permits rdf:resource="http://web.resource.org/cc/DerivativeWorks"/><requires rdf:resource="http://web.resource.org/cc/ShareAlike"/></License></rdf:RDF>
 -->
 */

int joints = 5;
Link2 [] links; // manipulator links
Pose2 tool0 = new Pose2(0, 9.5, PI/2); // tool pose at rest
SerialLink2 manipulator;

Pose2 [] sTi0; // link coordinate frames at rest, for drawing

HScrollbar[] scrollBar = new HScrollbar[joints];  // scrollbars
double[] q = new double[joints]; // generalized joint coordinates   
PFont font; // font

int w = 600, h = 650;

void setup () {
  Tests.run(); // Run unit tests

  size(600, 700); // create display space
  hint(ENABLE_NATIVE_FONTS);
  font = createFont("Helvetica", 12);
  textFont(font);

  // Create scrollbars
  scrollBar[0] = new HScrollbar(0, h+48, w, 16, 1);
  scrollBar[1] = new HScrollbar(0, h+30, w, 16, 1);
  scrollBar[2] = new HScrollbar(0, h+12, w, 16, 1);
  scrollBar[3] = new HScrollbar(0, h-6, w, 16, 1);
  scrollBar[4] = new HScrollbar(0, h-24, w, 16, 1);

  // Create robot from class notes
  links = new Link2[joints];
  links[0] = new Link2(3.5, new Twist2(0, 0, 1));
  links[1] = new Link2(3.5, new Twist2(3.5, 0, 1));
  links[2] = new Link2(2, new Twist2(7, 0, 1));
  links[3] = new Link2(2, new Twist2(9, 0, 1));
  links[4] = new Link2(2, new Twist2(11, 0, 1));
  
  manipulator = new SerialLink2(3, links, tool0);

  // Create zero configuration link frames for drawing
  sTi0 = new Pose2[joints];
  sTi0[0] = new Pose2(0, 0, PI/2);
  sTi0[1] = new Pose2(0, 3.5, PI/2);
  sTi0[2] = new Pose2(0, 7, PI/2);
  sTi0[3] = new Pose2(0, 9, PI/2);
  sTi0[4] = new Pose2(0, 11, PI/4);
}

/// draw is called by Processing multiple times
void draw() {

  background(255);

  // update and display the scrollbars
  for (int i=0;i<joints;i++) {
    scrollBar[i].update();
    scrollBar[i].display();
  }

  // Get joint angles
  for (int i=0;i<joints;i++) {
    float x = scrollBar[i].getPos();
    if(i>0)
      q[i] = Math.PI*(4*x/w-1)/3;
    else
      q[i] = Math.PI*(4*x/w-1)/4;
      
  }

  // set origin and scale
  translate(w/2, 380);
  scale(35);
  textSize(0.4);

  // Draw the robot
  Pose2 sTt = drawRobot(q);
  if (mousePressed) {
    println(String.format("q = (%.1f %.1f %.1f)", q[0]*180/Math.PI, q[1]*180/Math.PI, q[2]*180/Math.PI));
    println("That boy: " + links[0].twist_.omega());
  }
}

