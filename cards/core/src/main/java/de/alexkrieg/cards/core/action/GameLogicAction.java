package de.alexkrieg.cards.core.action;

import java.util.List;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.util.Filter;

public interface GameLogicAction extends GameAction {
  
  Player<?,?,?> player();

    
  public static class PlayerFilter extends Filter<GameAction> {


    private final  Player<?, ?, ?> player;

    public PlayerFilter(Player<?,?,?> player) {
      super();
      this.player = player;
    }

    @Override
    public boolean apply(GameAction candidate, List<GameAction> cardSet) {
      return candidate instanceof GameLogicAction &&  this.player == ((GameLogicAction)candidate).player();
    }

  }

  

  public static class Merge extends GameAction.Merge implements GameLogicAction{


    public Merge(GameLogicAction... actions) {
      super(actions);
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
      return ((GameLogicAction) actions[0]).player();
    }

  }

}
