package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.HeapLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class PlaynAction extends CardMoveAction2<HeapLayout> {


  public PlaynAction() {
    this(null, null, null,0);
  }

  public PlaynAction(MaumauRobotPlayer player, Card card, CardSlot<HeapLayout> playSlot,int duration) {
    super(card, playSlot,player,duration);
    if (card != null) {
      card.setSide(Card.Side.Image);
    }
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
