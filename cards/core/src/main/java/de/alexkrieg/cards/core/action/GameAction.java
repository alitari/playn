package de.alexkrieg.cards.core.action;

import java.util.List;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.util.Filter;

public interface GameAction {
  
  Player<?,?,?> player();

  void execute();

  int getDuration();

  void paint(int tick, float alpha);

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
  
  public static class PlayerFilter extends Filter<GameAction> {


    private final  Player<?, ?, ?> player;

    public PlayerFilter(Player<?,?,?> player) {
      super();
      this.player = player;
    }

    @Override
    public boolean apply(GameAction candidate, List<GameAction> cardSet) {
      return this.player == candidate.player();
    }

  }

  

  public static class Merge implements GameAction {

    private final GameAction[] actions;

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
    public Player<?, ?, ?> player() {
      return actions[0].player();
    }

  }

}
