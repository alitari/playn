package de.alexkrieg.cards.core.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class CardMoveAction2 extends MoveAction<Card, TiledCardsRotatedLayout> {

  public CardMoveAction2(Card card, int duration, CardSlot destination) {
    super(card, duration, destination);
  }

}
