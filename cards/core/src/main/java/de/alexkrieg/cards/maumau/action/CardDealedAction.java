package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class CardDealedAction extends CardMoveAction2<TiledCardsRotatedLayout> {
  
  
  public CardDealedAction() {
    // default constructor needed for framework
    super(null,null,null);
  }

  public CardDealedAction(MaumauRobotPlayer player, Card card, CardSlot<TiledCardsRotatedLayout> playerSlot) {
    super(card, playerSlot,player);
    card.setSide(Card.Side.Image);
  }

  @Override
  public MaumauRobotPlayer player() {
    return (MaumauRobotPlayer) player;
  }

  @Override
  protected void recalcLayout(TiledCardsRotatedLayout layout) {
    layout.recalc(layerEntity, null);
  }

}
