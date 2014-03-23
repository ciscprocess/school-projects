//-------------------------------------- AGENT --------------------------------------------

public class Agent extends State {
  final static float gamma = 0.9;
  final static float alpha = 0.8;
  // The value function
  float [][] V = new float[w][h];
  float maxV=1;

  // For each cell we have three Q values:
  float [][][] Q = new float[w][h][3]; // Q values for 3 actions (down,straight,up)

  public Agent(int x, int y) {
    super(x, y);
    this.randomizeQ();
    this.calculate_values();
  }

  public void setState(State s) {
    this.x=s.x; 
    this.y=s.y;
  }

  // Randomize Q values
  void randomizeQ() {
    for (int x=0;x<w;x++) for (int y=0;y<h;y++) for (int a=0;a<3;a++) Q[x][y][a]=random(0, 1);
  }

  // Calculate the optimal values based on the Q values: choose the one with the highest Q value
  int optimal_action(int x, int y) {
    float [] Qxy = Q[x][y];
    int a, astar=1;//floor(3*random(0,0.99999));
    float Qstar=Qxy[astar];
    for (a=0;a<3;a++)
      if (Qxy[a]>Qstar) {
        astar=a;
        Qstar=Qxy[a];
      }
    return astar;
  }

  // A policy is a mapping from states to actions
  // In this case we always choose the one with the highest Q value
  int policy(State s) {
    return optimal_action(s.x, s.y);
  }

  // Evaluate policy for agent's state
  int policy() {
    return optimal_action(this.x, this.y);
  }

  float calc_discount(State s)
  {
    return gamma * V[s.x][s.y];
  }
  // One step of value iteration calls update_Qvalues and calculate_values
  
  // TODO 1: CHANGE THIS FUNCTION TO UPDATE Q-VALUES GIVEN VALUE FUNCTION
  public void update_Qvalues() {
    
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        State s = new State(x, y);
        Q[x][y][0] = reward(s, 0) + calc_discount(transition(s, 0));
        
        Q[x][y][1] = reward(s, 1) + calc_discount(transition(s, 1));
       
        Q[x][y][2] = reward(s, 2) + calc_discount(transition(s, 2));
      }
    }
  }

  // Calculate value function by taking max Q
  // TODO 2: CHANGE THIS FUNCTION TO RE_CALCULATE VALUE FUNCTION
  void calculate_values() {
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        float maxx = -999999;
        for (int act = 0; act < 3; act++) {
          if (Q[x][y][act] > maxx)
            maxx = Q[x][y][act];
        }
        V[x][y] = maxx;
        if (maxx > maxV)
          maxV = maxx;
      }
    }
  }

  // Do one step of Q-learning based one experience tuple: 
  //   s = state we started in
  //   a = action
  //   t = state we ended up in
  // TODO 3: FILL IN THIS FUNCTION TO DO ONE STEP OF Q-LEARNING
  public void learnQ(State s, int a, State t) {
    float maxx = -999999;
    for (int act = 0; act < 3; act++)
    {
      if (Q[t.x][t.y][act] > maxx)
          maxx = Q[t.x][t.y][act];
    }
    
    Q[s.x][s.y][a] = (1 - alpha) * Q[s.x][s.y][a] + alpha * (reward(s, a) + gamma * maxx);
      if (Q[s.x][s.y][a] > maxV) 
            maxV = Q[s.x][s.y][a];
  }

  // return values as color
  public color colorV(State s) {
    float factor = 255/maxV;
    float Vxy = V[s.x][s.y];
    color c = color(Vxy*factor, Vxy*factor, Vxy*factor);
    return c;
  }

  // return Q values as color
  public color colorQ(State s) {
    float factor = 255/maxV;
    float [] Qxy = Q[s.x][s.y];
    color c = color(Qxy[0]*factor, Qxy[1]*factor, Qxy[2]*factor);
    return c;
  }

  // return policy as color
  public color colorP(State s) {
    int a = optimal_action(s.x, s.y);
    color c=#000000;
    switch (a) {
    case 0: 
      c = #FF0000; 
      break;
    case 1: 
      c = #00FF00; 
      break;
    case 2: 
      c = #0000FF; 
      break;
    }
    return c;
  }
}

