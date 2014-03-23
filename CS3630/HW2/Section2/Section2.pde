/**
 <h3>Reinforcement Learning Applet</h3>
 <p>Author: <a href="http://frank.dellaert.com">Frank Dellaert</a><br/>
 A smalll applet to illustrate <a href="http://en.wikipedia.org/wiki/Reinforcement_learning">reinforcement learning</a> concepts</p>
 
 <p>The agent starts out with a random policy and starts executing it immediately. In each cell, the agent can take three actions: up, stay, or down.
 The agent's preferences for each of these actions (Q values) are indicated by the color of the cell: red=up, green=stay, blue=down. The world is deterministic.</p>
 
 <p>To start playing, click in the applet and try the following: press 'r' to edit rewards, 
 press 'q' to start executing again, press 'l' to start learning a policy that behaves optimally given the rewards.
 Keep pressing 'l' until the Q-values converge.</p>
 
 <h4>Documentation</h4>
 
 <h5>Agent Interface</h5>
 You can display the value function and Q-values and update it with the keys below.
 <ul>
 <li>v, q, p: display value function, Q-values, or policy. Default=q.
 <li>l: start learning a policy by value iteration
 <li>f: forget the policy, i.e. randomize Q values
 </ul>
 
 <h5>Reward Editing Mode</h5>
 The reward is initalized as being 1.0 everywhere, except near the wall where the reward is 0.0. You can edit the reward function with the keys below.
 <ul>
 <li>r: enter reward editing mode
 <li>q or v: leave reqard editing mode
 <li>left mouse click: make the reward 10.0 at a given cell (strawberry)  
 <li>right mouse click: make the reward 0.0 at a given cell (obstacle)
 <li>c: clear all rewards except default ones
 </ul>
 
 <br/>
 <!--Creative Commons License--><a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">
 <img alt="Creative Commons License" style="border-width: 0" src="http://i.creativecommons.org/l/by-nc-sa/2.5/88x31.png"/></a><br/>
 This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/2.5/">Creative Commons Attribution-Noncommercial-Share Alike 2.5  License</a>.<!--/Creative Commons License--><!-- <rdf:RDF xmlns="http://web.resource.org/cc/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"><Work rdf:about=""><license rdf:resource="http://creativecommons.org/licenses/by-nc-sa/2.5/" /><dc:type rdf:resource="http://purl.org/dc/dcmitype/InteractiveResource" /></Work><License rdf:about="http://creativecommons.org/licenses/by-nc-sa/2.5/"><permits rdf:resource="http://web.resource.org/cc/Reproduction"/><permits rdf:resource="http://web.resource.org/cc/Distribution"/><requires rdf:resource="http://web.resource.org/cc/Notice"/><requires rdf:resource="http://web.resource.org/cc/Attribution"/><prohibits rdf:resource="http://web.resource.org/cc/CommercialUse"/><permits rdf:resource="http://web.resource.org/cc/DerivativeWorks"/><requires rdf:resource="http://web.resource.org/cc/ShareAlike"/></License></rdf:RDF>
 -->
 */

// The world is rectangular with size w*h, and consists of square cells with size s
static final int w = 40, h = 20, s = 20; // size of world and cell size

// The current state of the agent is a global variable
Agent agent = new Agent(w/2, h/2);

//------------------------------------- DRAWING -------------------------------------------

// To have a stable display, we will draw the world as scrolling right.
// To implement that, we have a variable du at what u value the world starts.
// It is incremented by s at every time-step
int du=0;

// The drawing mode: v or q
char mode = 'q';

// convert from mouseX,mouseY to x,y
State mouse_state() {
  int u = mouseX - du;
  int v = mouseY;
  if (u<0) u+=width;
  return new State(floor(u/s), floor(v/s));
}

PFont comic, synchro;

//-------------------------------------- SETUP --------------------------------------------

// Processing calls setup() once
void setup() {
  size(800, 400);
  size(w*s, h*s);
  frameRate(60);
  comic = loadFont("ComicSansMS-Bold-32.vlw");
  synchro = loadFont("SynchroLET-18.vlw");
  init_rewards();
  agent.randomizeQ();
}

//------------------------------------ MAIN LOOP -------------------------------------------

// Processing calls draw() coninuously
void draw() {

  // do value iteration if l is pressed
  boolean learning = (keyPressed && key == 'l');
  if (learning) {
    agent.update_Qvalues();
    agent.calculate_values();
  }

  // execute the policy
  //State oldstate = new State(agent.x, agent.y); // FOR Q LEARNING
  int optimal_action = agent.policy();

  // FOR Q LEARNING, TAKE RAMDOM ACTIONS
  if (keyPressed && key == 'e') 
    optimal_action = floor(random(0,2.99999));  

  float r=0;
  if (!learning  && mode!='r') { // FOR VALUE ITERATION
    //if(mode!='r') { // For Q LEARNING  
    r = reward(agent, optimal_action);
    agent.setState(transition(agent, optimal_action));
  }

  // FOR Q LEARNING, ALWAYS DO Q LEARNING
  //agent.learnQ(oldstate, optimal_action, agent);

  // Check for mode switch
  if (keyPressed && (key == 'v' || key == 'q' || key == 'p' || key == 'r')) mode = key; // mode

  // in reward mode, allow user to edit rewards
  if (mode=='r') {
    if (mousePressed) {
      State s = mouse_state();
      int a;
      if (mouseButton ==   LEFT) for (a=0;a<3;a++) R[s.x][s.y][a]=10;
      if (mouseButton ==  RIGHT) for (a=0;a<3;a++) R[s.x][s.y][a]=0;
    }
    if (keyPressed && key == 'c') { 
      init_rewards();
    }  // C = clear
  }

  // if the user presses f, forget policy
  if (keyPressed && key == 'f') { 
    agent.randomizeQ(); 
    agent.calculate_values();
  } // f = forget
  if (mousePressed && mouseButton == CENTER) agent.setState(mouse_state());

  // draw value function, Q-values, or policy depending on mode
  int x, y, u, v;
  State t = new State(0, 0);
  noStroke();
  for (t.x=0,u=du;t.x<w;t.x++,u+=s) {
    if (u==width) u=0; // wrap the world
    for (t.y=0,v=0;t.y<h;t.y++,v+=s) {
      switch (mode) {
      case 'q' : 
        fill(agent.colorQ(t)); 
        break;
      case 'v' : 
        fill(agent.colorV(t)); 
        break;
      case 'p' : 
        fill(agent.colorP(t)); 
        break;
      case 'r' : 
        fill(colorR(t)); 
        break;
      }
      rect(u, v, s, s);
    }
  }

  // unless we're editing or learning, draw agent and advance world
  if (!learning && mode!='r') { // FOR VALUE ITERATION
     //if (mode!='r') { // FOR Q LEARNING
    // Draw the agent
    noFill();
    strokeWeight(4);
    stroke(255);
    u = du+s*agent.x;
    if (u>=width) u-=width;
    v = s*agent.y;
    rect(u, v, s, s);

    // if reward is 10, scream with joy !
    if (r==10) {
      fill(255, 0, 0);
      textFont(comic, 32); 
      text ("Yahoo!", u+2*s, v-s);
    }

    // scroll the world to the right
    du += s;
    if (du==width) du=0;
  }

  // Draw legend
  fill(0, 255, 0);
  textFont(synchro, 18);
  u=s/2;
  v=16;
  if (learning) text("Learning Mode", u, v); 
  else
    switch (mode) {
    case 'q' : 
      text("Q-values", u, v); 
      break;
    case 'v' : 
      text("Value Function", u, v); 
      break;
    case 'p' : 
      text("Policy", u, v); 
      break;
    case 'r' : 
      text("Reward Editing Mode", u, v); 
      break;
    }
}

