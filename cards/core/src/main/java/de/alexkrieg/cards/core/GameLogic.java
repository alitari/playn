package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.action.GameAction;

public interface GameLogic {
  
  
  void executeAction(GameAction action) throws Exception;
  

}
