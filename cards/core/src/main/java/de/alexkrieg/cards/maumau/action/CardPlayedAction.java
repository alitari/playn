package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.maumau.MaumauPlayer;

public class CardPlayedAction extends CardMoveAction2<StackLayout> {
  
  
  private final MaumauPlayer player;
  
  
  public CardPlayedAction() {
    // default constructor needed for framework
    super(null,null);
    this.player = null;
  }

  public CardPlayedAction(MaumauPlayer player, Card card, CardSlot<StackLayout> playSlot) {
    super(card,  playSlot);
    this.player = player;
    card.setSide(Card.Side.Image);
  }

  public MaumauPlayer player() {
    return player;
  }

}
