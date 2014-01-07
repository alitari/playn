package de.alexkrieg.cards.core.action;

import de.alexkrieg.cards.core.LayerEntity;

public abstract class AbstractAction<T extends LayerEntity> implements GameAction<T> {

  private final T layerEntity;
  protected final int duration;
  private Animation<T>[] animations;

  public AbstractAction(T layerEntity, int duration) {
    super();
    this.layerEntity = layerEntity;
    this.duration = duration;
    
  }
  
  

  @Override
  public boolean reschedule() {
    return false;
  }



  @Override
  public GameAction<T> with(Animation<T>... animations) {
    this.animations = animations;
    return this;

  }


  @Override
  public void paint(int tick, float alpha) {
    float actionAlpha = tick == 0 ? 0 : (float) ((float) tick / (duration + 1))
        + (float) ((float) alpha / (duration + 1));
    paintWithActionAlpha(actionAlpha);
    if (this.animations != null) {
      for (Animation<T> anim : this.animations) {
        anim.paint( duration, tick, alpha, this.layerEntity());
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
    return getClass().getSimpleName() + "[layerEntity=" + layerEntity() + ", duration=" + duration + "]";
  }



  public T layerEntity() {
    return layerEntity;
  }

}
