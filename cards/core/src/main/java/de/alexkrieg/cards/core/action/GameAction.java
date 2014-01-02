package de.alexkrieg.cards.core.action;

import java.util.List;

import playn.core.Layer;
import de.alexkrieg.cards.core.util.Filter;

public interface GameAction {
  

  void execute();

  int getDuration();

  void paint(int tick, float alpha);
  
  GameAction with(Animation...animations );
  
  
  public static interface Animation {
    public void paint(int duration,int tick,float alpha, Layer layer);
    
    
    public static class Rotate implements Animation {
      
      final float speed;

      public Rotate(float speed) {
        super();
        this.speed = speed;
      }

      @Override
      public void paint(int duration, int tick, float alpha, Layer layer) {
        layer.transform().setRotation((float) ((float)Math.PI*2*speed*((float)tick/duration)));
      }
      
    }
    
  }
  
  
  
  
  
  
  

  public static class TypeFilter extends Filter<GameAction> {

    final Class<?> type;

    public TypeFilter(Class<?> type) {
      super();
      this.type = type;
    }

    @Override
    public boolean apply(GameAction candidate, List<GameAction> cardSet) {
      return type.isAssignableFrom(candidate.getClass());
    }

  }
  
  
  

  public static class Merge implements GameAction {

    protected final GameAction[] actions;

    public Merge(GameAction... actions) {
      super();
      this.actions = actions;
    }

    @Override
    public void execute() {
      for ( GameAction a: actions) {
        a.execute();
      }
      
    }

    @Override
    public int getDuration() {
      return actions[0].getDuration();
    }

    @Override
    public void paint(int tick, float alpha) {
      for ( GameAction a: actions) {
        a.paint(tick, alpha);
      }
    }

    @Override
    public GameAction with(Animation... animations) {
      for ( GameAction a: actions) {
        a.with(animations);
      }
      return this;
    }

  

  }

}
