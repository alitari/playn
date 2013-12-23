package de.alexkrieg.cards.maumau;


import java.util.List;

import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauGameLogic.Mode;

public abstract class MaumauPlayer extends AbstractPlayer<MaumauGameLogic,NESWLayout> {


  final protected CardSlot<TiledCardsRotatedLayout> ownedCards;


  public MaumauPlayer(String name, MaumauGameLogic gameLogic, ActionManager actionManager, MaumauCardtable cardTable, CardSlot<TiledCardsRotatedLayout> ownedCards) {
    super(name, gameLogic,actionManager,cardTable);
    this.ownedCards = ownedCards;
  }
  
  protected MaumauCardtable table() {
    return (MaumauCardtable) cardTable;
  }

  public CardSlot<TiledCardsRotatedLayout> ownedCards() {
    return ownedCards;
  }



//  @Override
//  public void update() {
//    Card card = cardDecision();
//    if (card != null) {
//      //shedule(new CardPlayedAction(this,card);
//    } else {
//      if (isItMyTurn()) {
//        if (gameLogic.getMode() == Mode.Refilling
//            || (gameLogic.getMode() == Mode.Playing && gameLogic.talon.size() < 2)) {
//          List<Card> childs = gameLogic.playSlot;
//          Card cardPlayslot = childs. remove(0);
////          shedule(new FillTalonAction(this, cardPlayslot, game));
//        } else {
////          shedule(new TakeCardAction(this, game.takeCardFromTalon(), game));
////          shedule(new TakeCardAction(this, game.takeCardFromTalon(), game));
//        }
//      }
//    }
//  }
//
//  abstract protected Card cardDecision();
//
//  protected boolean isItMyTurn() {
//    return (gameLogic.getMode() == Mode.Playing || gameLogic.getMode() == Mode.Refilling)
//        && gameLogic.waitingForPlayer == this;
//  }
  
  @Override
  public String toString() {
    return "MaumauPlayer [id=" + id + "]";
  }


}
