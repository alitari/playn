package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;

public abstract class MaumauPlayer extends AbstractPlayer<MaumauCardGame> {

  final protected CardSlot<?> myCards;
  
  public MaumauPlayer(String name, MaumauCardGame game,  CardSlot<?> myCards) {
    super(name,game);
    this.myCards = myCards;
  }
  
  @Override
  public void update() {
    Card card = cardDecision();
    if ( card != null) {
    
    act(game,  new CardMoveAction2(card, 10, game.talon));
    } else {
      
    }
  }

  abstract protected Card cardDecision();
 

}
