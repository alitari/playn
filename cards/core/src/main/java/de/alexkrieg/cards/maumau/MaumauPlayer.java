package de.alexkrieg.cards.maumau;


import java.util.List;

import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.AbstractAction;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauGameLogic.Mode;

public abstract class MaumauPlayer extends AbstractPlayer<MaumauGameLogic> {

  final protected CardSlot<TiledCardsRotatedLayout> ownedCards;

  protected final GameAction fillTalonAction = new AbstractAction(null, 0) {
    @Override
    public void execute() {
//      List<Card> cardSet = Card.createSet();
//      Collections.shuffle(cardSet);
//      for (Card c : cardSet) {
//        gameLogic.talon.put(c, null);
//      }
    }
  };

  public MaumauPlayer(String name, MaumauGameLogic gameLogic, ActionManager actionManager, CardSlot<TiledCardsRotatedLayout> ownedCards) {
    super(name, gameLogic,actionManager);
    this.ownedCards = ownedCards;
  }
  
  

  public CardSlot<TiledCardsRotatedLayout> ownedCards() {
    return ownedCards;
  }



  @Override
  public void update() {
    Card card = cardDecision();
    if (card != null) {
      //shedule(new CardPlayedAction(this,card);
    } else {
      if (isItMyTurn()) {
        if (gameLogic.getMode() == Mode.Refilling
            || (gameLogic.getMode() == Mode.Playing && gameLogic.talon.size() < 2)) {
          List<Card> childs = gameLogic.playSlot;
          Card cardPlayslot = childs. remove(0);
//          shedule(new FillTalonAction(this, cardPlayslot, game));
        } else {
//          shedule(new TakeCardAction(this, game.takeCardFromTalon(), game));
//          shedule(new TakeCardAction(this, game.takeCardFromTalon(), game));
        }
      }
    }
  }

  abstract protected Card cardDecision();

  protected boolean isItMyTurn() {
    return (gameLogic.getMode() == Mode.Playing || gameLogic.getMode() == Mode.Refilling)
        && gameLogic.waitingForPlayer == this;
  }

}
