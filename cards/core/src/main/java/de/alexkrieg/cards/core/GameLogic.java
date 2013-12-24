package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.Layout;

public interface GameLogic<L extends Layout<CardSlot<?>>, P extends Player<L,P,G>, G extends GameLogic<L,P,G>> {
  
  void configure();
  
  void executeAction(GameAction action) throws Exception;
  
  

}
