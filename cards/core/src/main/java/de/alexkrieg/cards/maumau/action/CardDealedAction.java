package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class CardDealedAction extends CardMoveAction2<TiledCardsRotatedLayout> implements GameLogicAction<Card> {
  
  
  private MaumauRobotPlayer player;

  public CardDealedAction() {
    // default constructor needed for framework
    this(null,null,null,0);
  }

  public CardDealedAction(MaumauRobotPlayer player, Card card, CardSlot<TiledCardsRotatedLayout> playerSlot,int duration) {
    super(card, playerSlot,duration);
    card.setSide(Card.Side.Image);
  }

  @Override
  public MaumauRobotPlayer player() {
    return  player;
  }

  @Override
  protected void recalcLayout(TiledCardsRotatedLayout layout) {
    layout.recalc(layerEntity, null);
  }

}
