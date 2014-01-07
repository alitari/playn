package de.alexkrieg.cards.core.action;

import java.util.List;

import playn.core.PlayN;
import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.Thing;
import de.alexkrieg.cards.core.util.Filter;

public interface GameAction<T extends LayerEntity> {

  void execute();

  int getDuration();
  
  boolean reschedule();

  void paint(int tick, float alpha);

  GameAction<T> with(Animation<T>... animations);

  public static interface Animation<T extends LayerEntity> {
    public void paint(int duration, int tick, float alpha, T layerEntity);

    public static class Rotate<T extends LayerEntity> implements Animation<T> {
      final float speed;

      public Rotate(float speed) {
        super();
        this.speed = speed;
      }

      @Override
      public void paint(int duration, int tick, float alpha, T layerEntity) {
        layerEntity.layer().transform().setRotation(
            (float) ((float) Math.PI * 2 * speed * ((float) tick / duration)));
      }
    }

    public static class Blend<T extends LayerEntity> implements Animation<T> {
      final float speed;
      final boolean on;

      public Blend(boolean on, float speed) {
        super();
        this.speed = speed;
        this.on = on;
      }

      @Override
      public void paint(int duration, int tick, float alpha, T layerEntity) {
        float value = Math.min(1, (float) speed * ((float) tick / (float) duration));
        layerEntity.layer().setAlpha(on ? value : 1 - value);
      }
    }
    
    public static class Animate<T extends Thing> implements Animation<T> {
      final int speed;
      private int counter =0; 

      public Animate( int speed) {
        super();
        this.speed = speed;
      }

      @Override
      public void paint(int duration, int tick, float alpha, T layerEntity) {
        if ( counter++ > (duration -speed )) {
          layerEntity.nextAnimationStep();
          counter = 0;
        };
      }
    }
    
  }

  public static class TypeFilter extends Filter<GameAction<?>> {

    final Class<?> type;

    public TypeFilter(Class<?> type) {
      super();
      this.type = type;
    }

    @Override
    public boolean apply(GameAction<?> candidate, List<GameAction<?>> cardSet) {
      return type.isAssignableFrom(candidate.getClass());
    }

  }

  public static class Merge<T extends LayerEntity> implements GameAction<T> {

    protected final GameAction<T>[] actions;

    public Merge(GameAction<T>... actions) {
      super();
      this.actions = actions;
    }

    @Override
    public void execute() {
      for (GameAction<T> a : actions) {
        a.execute();
      }
    }

    @Override
    public int getDuration() {
      return actions[0].getDuration();
    }

    @Override
    public void paint(int tick, float alpha) {
      for (GameAction<T> a : actions) {
        a.paint(tick, alpha);
      }
    }

    @Override
    public GameAction<T> with(Animation<T>... animations) {
      for (GameAction<T> a : actions) {
        a.with(animations);
      }
      return this;
    }

    @Override
    public boolean reschedule() {
      return false;
    }

  }

}
