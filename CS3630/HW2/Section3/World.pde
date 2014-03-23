//-------------------------------------- WORLD --------------------------------------------

// ACTIONS: in this world, the agent flies towards the left all the time
// It can only decide whether to fly higher, lower, or stay at the same altitude
static final int up = 0, stay=1, down=2;

// Deterministic state transition
State transition(State s, int a) {
  int dx = -1, dy = a-1;
  State t = new State(s.x+dx, s.y+dy);
  if (t.x<0) t.x=w-1;
  if (t.y<0) t.y=0;
  if (t.y==h) t.y-=1;
  return t;
}

// REWARDS: the reward function in each cell, for each action
static float [][][] R = new float[w][h][3];

// Initialize rewards
void init_rewards() {
  int x, y, a;
  for (x=0;x<w;x++) for (y=0;y<h;y++) for (a=0;a<3;a++) R[x][y][a]=1;
  for (x=0;x<w;x++) for (a=0;a<3;a++) R[x]  [0][a]=0; // top wall
  for (x=0;x<w;x++) for (a=0;a<3;a++) R[x][h-1][a]=0; // bottom wall
}

// Reward function
static float reward(State s, int a) { 
  return R[s.x][s.y][a];
}

// Return Reward as a color
color colorR(State t) {
  final float factor = 255/10;
  float [] Rxy = R[t.x][t.y];
  color c =color(Rxy[0]*factor, Rxy[1]*factor, Rxy[2]*factor);
  return c;
}

