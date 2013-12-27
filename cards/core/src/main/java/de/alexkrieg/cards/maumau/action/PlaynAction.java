package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.HeapLayout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class PlaynAction extends CardMoveAction2<HeapLayout> {

  private final MaumauRobotPlayer player;

  public PlaynAction() {
    this(null, null, null);
  }

  public PlaynAction(MaumauRobotPlayer player, Card card, CardSlot<HeapLayout> playSlot) {
    super(card, playSlot);
    this.player = player;
    if (card != null) {
      card.setSide(Card.Side.Image);
    }
  }

  public MaumauRobotPlayer player() {
    return player;
  }

  @Override
  protected void recalcLayout(HeapLayout layout) {
    layout.recalc(layerEntity, null);
  }

}
