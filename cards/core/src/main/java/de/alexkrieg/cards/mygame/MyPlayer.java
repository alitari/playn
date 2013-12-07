package de.alexkrieg.cards.mygame;

import java.util.Collection;
import java.util.Random;

import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;

public class MyPlayer extends AbstractPlayer<MyCardGame> {

  public MyPlayer(String name) {
    super(name);
  }

  @Override
  public void update(MyCardGame cardGame) {
    Random random = new Random();
    int nextInt = random.nextInt(20);
    if ( nextInt == 10) {
       Collection<Card> childs = cardGame.csWest.childs();
      if ( !childs.isEmpty()) {
         Card card = childs.iterator().next();
         act(cardGame,  new CardMoveAction2(card , 20, cardGame.csSouth));
       }
    }
  }

 

}
