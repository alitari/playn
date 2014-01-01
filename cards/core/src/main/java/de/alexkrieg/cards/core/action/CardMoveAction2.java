package de.alexkrieg.cards.core.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class CardMoveAction2<L extends Layout<Card>> extends MoveAction<Card, L > {

  
  public CardMoveAction2(Card card,  CardSlot<L> destination,Player<?,?,?> player,int duration) {
    super(card, duration, destination,player);
  }

  @Override
  public void execute() {
    super.execute();
//    layerEntity.setSide(Card.Side.Image);
  }
  
  public Card card() {
    return layerEntity;
  }
  
  
}
