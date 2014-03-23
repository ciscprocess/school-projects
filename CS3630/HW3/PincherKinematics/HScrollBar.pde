class HScrollbar {
  int width_, height_;    // width and height of bar
  float x_, y_;       // x and y position of bar
  float position_, newposition_;    // x position of slider
  float min_, max_; // max and min values of slider
  int loose_;              // how loose_/heavy
  boolean over_;           // is the mouse over_ the slider?
  boolean locked_;
  float ratio_;

  HScrollbar (float xp, float yp, int sw, int sh, int l) {
    width_ = sw;
    height_ = sh;
    int widthtoheight = sw - sh;
    ratio_ = (float)sw / (float)widthtoheight;
    x_ = xp;
    y_ = yp-height_/2;
    position_ = x_ + width_/2 - height_/2;
    newposition_ = position_;
    min_ = x_;
    max_ = x_ + width_ - height_;
    println(x_ + ", " + position_);
    println(min_ + ", " + max_);
    loose_ = l;
  }

  float getPos() {
    // Convert position_ to be values between
    // 0 and the total width of the scrollbar
    return position_ * ratio_;
  }

  void display() {
    noStroke();
    fill(204);
    rectMode(CORNER);
    rect(x_, y_, width_, height_);
    if (over_ || locked_) {
      fill(0, 0, 0);
    } 
    else {
      fill(102, 102, 102);
    }
    rect(position_, y_, height_, height_);
  }

  float constrain(float val, float minv, float maxv) {
    return min(max(val, minv), maxv);
  }

  boolean overEvent() {
    if (mouseX > x_ && mouseX < x_+width_ &&
      mouseY > y_ && mouseY < y_+height_) {
      return true;
    } 
    else {
      return false;
    }
  }

  void update() {
    over_  = overEvent();
    if (mousePressed && over_) {
      locked_ = true;
    }
    if (!mousePressed) {
      locked_ = false;
    }
    if (locked_) {
      newposition_ = constrain(mouseX-height_/2, min_, max_);
    }
    if (abs(newposition_ - position_) > 1) {
      position_ = position_ + (newposition_-position_)/loose_;
    }
  }
}

