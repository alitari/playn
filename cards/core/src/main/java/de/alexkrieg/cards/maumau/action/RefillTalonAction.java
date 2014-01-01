package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class RefillTalonAction extends CardMoveAction2<StackLayout> {
  
  
  
  
  public RefillTalonAction() {
    // default constructor needed for framework
    super(null,null,null);
  }

  public RefillTalonAction(MaumauRobotPlayer player, Card card, CardSlot<StackLayout> playerSlot) {
    super(card, playerSlot,player);
    card.setSide(Card.Side.Image);
  }

  @Override
  public MaumauRobotPlayer player() {
    return (MaumauRobotPlayer) player;
  }

  @Override
  protected void recalcLayout(StackLayout layout) {
    layout.recalc(layerEntity, null);
  }

}
