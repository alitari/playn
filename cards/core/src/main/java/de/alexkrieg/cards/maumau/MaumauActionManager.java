package de.alexkrieg.cards.maumau;

import javax.inject.Inject;

import de.alexkrieg.cards.core.DefaultActionManager;
import de.alexkrieg.cards.core.layout.NESWLayout;

public class MaumauActionManager extends DefaultActionManager<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> {

  @Inject
  public MaumauActionManager(MaumauGameLogic gameLogic) {
    super(gameLogic);
  }
  

}
