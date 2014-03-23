/**
 <h3>Image-based Pincher Robot Simulator</h3>
 <p>Author: <a href="http://frank.dellaert.com">Frank Dellaert</a>, with help from his son Thomas<br/>
 <p>Above is a 3DOF/no end-effector version of the <a href=http://www.trossenrobotics.com/p/PhantomX-Pincher-Robot-Arm.aspx>PhantomX Pincher robot arm</a>
 sold by <a href=http://www.trossenrobotics.com>Trossen Robotics</a>. Move the 3 joint sliders to control the arm.</p>
 <p><b>This applet needs to download 7MB of data, allow it some time to load.</b></p>
 <p>Details: of course, you are not controlling the robot in real-time :-) 
 The robot is controlled using an Arduino-compatible <a href=http://www.vanadiumlabs.com/arbotix.html>Arbotix</a> 
 robot controller board, so Thomas and I created an <a href=http://www.arduino.cc>Arduino</a> sketch to cycle the robot
 through 1000 poses, equally spaced in joint angle space. We then took a video of the whole process, and 
 -which turned out to be the most amount of work- extracted the 1000 individual frames corresponding to each robot pose.
 This very simple ProcessingJs applet simply selects one of those 1000 images.</p>
 <p>One more detail: loading 1000 images in an applet is tedious, so I created 10 2k*2k images instead, e.g. <a href=robot6xx.jpg>robot6xx.jpg</a>.
 I then simply copy the appropriate pixels to the window above, and presto. <b><a href=http://processingjs.org>Processing.js</a> rocks!</b></p>
 <!--Creative Commons License--><a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">
 <img alt="Creative Commons License" style="border-width: 0" src="http://i.creativecommons.org/l/by-nc-sa/2.5/88x31.png"/></a><br/>
 This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">Creative Commons Attribution-Noncommercial-Share Alike 2.5  License</a>.<!--/Creative Commons License--><!-- <rdf:RDF xmlns="http://web.resource.org/cc/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"><Work rdf:about=""><license rdf:resource="http://creativecommons.org/licenses/by-nc-sa/2.5/" /><dc:type rdf:resource="http://purl.org/dc/dcmitype/InteractiveResource" /></Work><License rdf:about="http://creativecommons.org/licenses/by-nc-sa/2.5/"><permits rdf:resource="http://web.resource.org/cc/Reproduction"/><permits rdf:resource="http://web.resource.org/cc/Distribution"/><requires rdf:resource="http://web.resource.org/cc/Notice"/><requires rdf:resource="http://web.resource.org/cc/Attribution"/><prohibits rdf:resource="http://web.resource.org/cc/CommercialUse"/><permits rdf:resource="http://web.resource.org/cc/DerivativeWorks"/><requires rdf:resource="http://web.resource.org/cc/ShareAlike"/></License></rdf:RDF>
 -->
 */

// The next line is needed if running in JavaScript Mode with Processing.js
/* @pjs preload="robot0xx.jpg,robot1xx.jpg,robot2xx.jpg,robot3xx.jpg,robot4xx.jpg,robot5xx.jpg,robot6xx.jpg,robot7xx.jpg,robot8xx.jpg,robot9xx.jpg"; */

HScrollbar joint1ScrollBar, joint2ScrollBar, joint3ScrollBar;  // scrollbars
PImage[] bigImage;  // Big images
int w = 200, h = 200;

void setup() {
  size(200, 260); // create display space
  noStroke();

  // Create scrollbars
  joint1ScrollBar = new HScrollbar(0, h+12, w, 16, 1);
  joint2ScrollBar = new HScrollbar(0, h+30, w, 16, 1);
  joint3ScrollBar = new HScrollbar(0, h+48, w, 16, 1);

  // Load big images, one for each joint 1 position
  bigImage = new PImage[10];
  for (int j=0;j<10;j++)
    bigImage[j] = loadImage("robot"+j+"xx.jpg");
}

// variables to cache the joint angles
int cachedj1=0, cachedj2=0, cachedj3=0;

void draw() {
  // Get the position of the joints from the scrollbars
  int j1 = floor(10*joint1ScrollBar.getPos()/(w+1));
  int j2 = floor(10*joint2ScrollBar.getPos()/(w+1));
  int j3 = floor(10*joint3ScrollBar.getPos()/(w+1));
  // If they changed, display a dfferent robot image
  if (j1!=cachedj1 || j2!=cachedj2 || j3!=cachedj3) {
    background(255);
    // copy appropriate crop from one of the 10 joint1 images
    copy(bigImage[j1], j3*w, j2*h, w, h, 0, 0, w, h);
    // update cache
    cachedj1 = j1;
    cachedj2 = j2;
    cachedj3 = j3;
    stroke(0);
    line(0, h, w, h);
  }
  // update and display the scrollbars
  joint1ScrollBar.update();
  joint2ScrollBar.update();
  joint3ScrollBar.update();
  joint1ScrollBar.display();
  joint2ScrollBar.display();
  joint3ScrollBar.display();
}


