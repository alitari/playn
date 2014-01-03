package de.alexkrieg.cards.core.action;

import java.util.List;

import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.util.Filter;

public interface GameLogicAction<T extends LayerEntity> extends GameAction<T> {

  Player<?, ?, ?> player();

  public static class PlayerFilter extends Filter<GameAction<?>> {

    private final Player<?, ?, ?> player;

    public PlayerFilter(Player<?, ?, ?> player) {
      super();
      this.player = player;
    }

    @Override
    public boolean apply(GameAction<?> candidate, List<GameAction<?>> cardSet) {
      return candidate instanceof GameLogicAction<?>
          && this.player == ((GameLogicAction<?>) candidate).player();
    }

  }

  public static class Merge<T extends LayerEntity> extends GameAction.Merge<T> implements
      GameLogicAction<T> {

    public Merge(GameLogicAction<T>... actions) {
      super(actions);
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
    public Player<?, ?, ?> player() {
      return ((GameLogicAction<T>) actions[0]).player();
    }

  }

}
