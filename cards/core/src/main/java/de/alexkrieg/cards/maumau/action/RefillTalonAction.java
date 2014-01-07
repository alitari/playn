package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.action.MoveAction;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class RefillTalonAction extends MoveAction<Card,StackLayout> implements GameLogicAction<Card>{
  
  private final MaumauRobotPlayer player;

  public RefillTalonAction() {
    // default constructor needed for framework
    this(null,null,null,0);
  }

  public RefillTalonAction(MaumauRobotPlayer player, Card card, CardSlot<StackLayout> playerSlot,int duration) {
    super(card, duration,playerSlot);
    this.player = player;
    card.setSide(Card.Side.Image);
  }

  @Override
  public MaumauRobotPlayer player() {
    return  player;
  }

  @Override
  protected void recalcLayout(StackLayout layout) {
    layout.recalc(layerEntity(), null);
  }

}
