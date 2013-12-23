package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauPlayer;

public class PickupAction extends CardMoveAction2<TiledCardsRotatedLayout> {
  
  
  private final MaumauPlayer player;
  
  
  public PickupAction() {
    // default constructor needed for framework
    super(null,null);
    this.player = null;
  }

  public PickupAction(MaumauPlayer player, Card card, CardSlot<TiledCardsRotatedLayout> playerSlot) {
    super(card,  playerSlot);
    this.player = player;
    card.setSide(Card.Side.Image);
  }

  public MaumauPlayer player() {
    return player;
  }

}