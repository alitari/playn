package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.action.MoveAction;
import de.alexkrieg.cards.core.layout.HeapLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class CardPlayedAction extends MoveAction<Card,HeapLayout> implements GameLogicAction<Card> {
  
  private final MaumauRobotPlayer player;

  public CardPlayedAction() {
    // default constructor needed for framework
    this(null,null,null,0);
  }

  public CardPlayedAction(MaumauRobotPlayer player, Card card, CardSlot<HeapLayout> playSlot,int duration) {
    super(card, duration, playSlot);
    this.player = player;
    card.setSide(Card.Side.Image);
  }

  @Override
  public MaumauRobotPlayer player() {
    return (MaumauRobotPlayer) player;
  }

  @Override
  protected void recalcLayout(HeapLayout layout) {
    layout.recalc(layerEntity(), null);
    
  }

}
