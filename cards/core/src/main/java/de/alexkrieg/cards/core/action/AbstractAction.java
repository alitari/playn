package de.alexkrieg.cards.core.action;

import playn.core.Layer;

public abstract class AbstractAction implements GameAction {

  protected final Layer layer;
  protected final int duration;
  private Animation[] animations;

  public AbstractAction(Layer layer, int duration) {
    super();
    this.layer = layer;
    this.duration = duration;
    
  }

  @Override
  public GameAction with(Animation... animations) {
    this.animations = animations;
    return this;

  }


  @Override
  public void paint(int tick, float alpha) {
    float actionAlpha = tick == 0 ? 0 : (float) ((float) tick / (duration + 1))
        + (float) ((float) alpha / (duration + 1));
    paintWithActionAlpha(actionAlpha);
    if (this.animations != null) {
      for (Animation anim : this.animations) {
        anim.paint( duration, tick, alpha, this.layer);
      }
    }

  }

  protected void paintWithActionAlpha(float actionAlpha) {
  }

  @Override
  public int getDuration() {
    return duration;
  }

  

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[layer=" + layer + ", duration=" + duration + "]";
  }

}
