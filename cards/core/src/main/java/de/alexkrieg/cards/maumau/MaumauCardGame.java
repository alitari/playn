package de.alexkrieg.cards.maumau;

import javax.inject.Inject;

import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.layout.NESWLayout;

public class MaumauCardGame extends CardGame<NESWLayout,MaumauRobotPlayer,MaumauGameLogic>{

  public MaumauCardGame() {
    super();
  }

  @Inject
  public MaumauCardGame(ActionManager actionManager,
      MaumauCardtable cardTable,
      MaumauPlayerRegistry playerRegistry,
      MaumauGameLogic gamelogic) {
    super(actionManager, cardTable, playerRegistry, gamelogic);
    // TODO Auto-generated constructor stub
  }
  
  

}
