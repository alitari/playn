package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauPlayer;

public class CardDealedAction extends CardMoveAction2<TiledCardsRotatedLayout> {
  
  
  private final MaumauPlayer player;
  
  
  public CardDealedAction() {
    // default constructor needed for framework
    super(null,null);
    this.player = null;
  }

  public CardDealedAction(MaumauPlayer player, Card card, CardSlot<TiledCardsRotatedLayout> playerSlot) {
    super(card, playerSlot);
    this.player = player;
    card.setSide(Card.Side.Image);
  }

  public MaumauPlayer player() {
    return player;
  }

}
