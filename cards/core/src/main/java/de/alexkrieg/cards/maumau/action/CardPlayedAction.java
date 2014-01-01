package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.HeapLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class CardPlayedAction extends CardMoveAction2<HeapLayout> {
  
  
  
  
  public CardPlayedAction() {
    // default constructor needed for framework
    super(null,null,null);
  }

  public CardPlayedAction(MaumauRobotPlayer player, Card card, CardSlot<HeapLayout> playSlot) {
    super(card,  playSlot,player);
    card.setSide(Card.Side.Image);
  }

  @Override
  public MaumauRobotPlayer player() {
    return (MaumauRobotPlayer) player;
  }

  @Override
  protected void recalcLayout(HeapLayout layout) {
    layout.recalc(layerEntity, null);
    
  }

}
