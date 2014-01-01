package de.alexkrieg.cards.maumau.action;

import java.util.Collections;
import java.util.List;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.layout.Layout;

public class StartGameAction extends ActionAdapter {

  final CardSlot<? extends Layout<Card>> talon;
  final boolean shuffle;

  public StartGameAction() {
    this(null,null,0);

  }

  public StartGameAction(CardSlot<? extends Layout<Card>> talon,Player<?,?,?> player,int duration) {
    this(talon, true,player,duration);
  }

  public StartGameAction(CardSlot<? extends Layout<Card>> talon, boolean shuffle,Player<?,?,?> player,int duration) {
    super(player,duration);
    this.talon = talon;
    this.shuffle = shuffle;
  }

  @Override
  public int getDuration() {
    return 5;
  }

  @Override
  public void execute() {
    super.execute();
    List<Card> cardSet = Card.createSet();
    if (shuffle) {
      Collections.shuffle(cardSet);
    }
    for (Card c : cardSet) {
      talon.put(c, null);
    }
  }

}
