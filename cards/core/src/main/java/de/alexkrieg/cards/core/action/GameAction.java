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

    final Class<? super GameAction> type;

    public TypeFilter(Class<? super GameAction> type) {
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

    private final GameAction action1;
    private final GameAction action2;

    public Merge(GameAction action1, GameAction action2) {
      super();
      this.action1 = action1;
      this.action2 = action2;
    }

    @Override
    public void execute() {
      action1.execute();
      action2.execute();
    }

    @Override
    public int getDuration() {
      return Math.max(action1.getDuration(), action2.getDuration());
    }

    @Override
    public void paint(int tick, float alpha) {
      action1.paint(tick, alpha);
      action2.paint(tick, alpha);
    }

    @Override
    public Player<?, ?, ?> player() {
      return action1.player();
    }

  }

}
