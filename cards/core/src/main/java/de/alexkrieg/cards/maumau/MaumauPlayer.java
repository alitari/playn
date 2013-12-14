package de.alexkrieg.cards.maumau;


import java.util.Collections;
import java.util.List;

import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.AbstractAction;
import de.alexkrieg.cards.core.action.Action;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauCardGame.State;

public abstract class MaumauPlayer extends AbstractPlayer<MaumauCardGame> {

  final protected CardSlot<TiledCardsRotatedLayout> myCards;

  protected final Action fillTalonAction = new AbstractAction(null, 0) {
    @Override
    public void execute() {
      List<Card> cardSet = Card.createSet();
      Collections.shuffle(cardSet);
      for (Card c : cardSet) {
        game.talon.put(c, null);
      }
    }
  };

  public MaumauPlayer(String name, MaumauCardGame game, CardSlot<TiledCardsRotatedLayout> myCards) {
    super(name, game);
    this.myCards = myCards;
  }

  @Override
  public void update() {
    Card card = cardDecision();
    if (card != null) {
      shedule(new PlayCardAction(this, card, game));
    } else {
      if (isItMyTurn()) {
        if (game.state.mode == State.Mode.Refill
            || (game.state.mode == State.Mode.Playn && game.talon.childs().size() < 2)) {
          List<Card> childs = game.playSlot.childs();
          Card cardPlayslot = childs. remove(0);
          shedule(new FillTalonAction(this, cardPlayslot, game));
        } else {
          shedule(new TakeCardAction(this, game.takeCardFromTalon(), game));
          shedule(new TakeCardAction(this, game.takeCardFromTalon(), game));
        }
      }
    }
  }

  abstract protected Card cardDecision();

  protected boolean isItMyTurn() {
    return (game.state.mode == State.Mode.Playn || game.state.mode == State.Mode.Refill)
        && game.state.waitingForPlayer == this;
  }

}
