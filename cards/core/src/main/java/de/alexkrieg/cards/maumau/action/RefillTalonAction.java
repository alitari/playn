package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class RefillTalonAction extends CardMoveAction2<StackLayout> {
  
  
  private final MaumauRobotPlayer player;
  
  
  public RefillTalonAction() {
    // default constructor needed for framework
    super(null,null);
    this.player = null;
  }

  public RefillTalonAction(MaumauRobotPlayer player, Card card, CardSlot<StackLayout> playerSlot) {
    super(card, playerSlot);
    this.player = player;
    card.setSide(Card.Side.Image);
  }

  public MaumauRobotPlayer player() {
    return player;
  }

  @Override
  protected void recalcLayout(StackLayout layout) {
    layout.recalc(layerEntity, null);
  }

}
