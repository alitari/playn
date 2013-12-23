package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.action.GameAction;

public interface GameLogic<P extends Player> {
  
  
  void executeAction(GameAction action) throws Exception;
  
  void setPlayerRegistry(PlayerRegistry<P> playerRegistry);
  

}
