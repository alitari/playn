package de.alexkrieg.cards.maumau;

import java.util.LinkedList;
import java.util.List;

import playn.core.util.Clock.Source;
import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauGameLogic.Mode;
import de.alexkrieg.cards.maumau.action.CardDealedAction;
import de.alexkrieg.cards.maumau.action.CardPlayedAction;
import de.alexkrieg.cards.maumau.action.PickupAction;
import de.alexkrieg.cards.maumau.action.PlaynAction;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;

public class MaumauRobotPlayer extends
    AbstractPlayer<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> {

  private boolean dealer = false;
  private boolean systemReadySent = false;
  private boolean playnSent = false;
  private LinkedList<Card> cardsDealt = new LinkedList<Card>();
  private boolean cardPlayed = false;

  public MaumauRobotPlayer(String name) {
    super(name);
  }
  
  

  @Override
  public void paint(Source _clock) {
// nothing tzo paint yet    
  }



  @Override
  public void update(int delta, CardGame<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> game) {
    MaumauCardtable table = (MaumauCardtable) game.cardTable;
    CardSlot<TiledCardsRotatedLayout> ownedSlot = ownedSlot(table);
    List<Card> ownCardList = ownedSlot.childs();
    if (game.gameLogic.getMode() == Mode.Init && !systemReadySent) {
      if (isDealer()) {
        systemReadySent = true;
        shedule(game.actionManager, new SystemReadyAction());
      } else {
        // do nothing
      }

    } else if (game.gameLogic.getMode() == Mode.Dealing && !playnSent) {
      if (isDealer()) {
        List<Card> talon = game.gameLogic.talon;
        if (cardsDealt.isEmpty()) {
          cardsDealt.addAll(talon.subList(talon.size() - 4 * MaumauGameLogic.dialedCardsCount - 1,
              talon.size()));
        }
        if (cardsDealt.size() > 1) {
          int playerNr = (cardsDealt.size() - 1) % 4;
          CardSlot<TiledCardsRotatedLayout> playerSlot = (playerNr == 0 ? table.slotPlayer1
              : (playerNr == 1 ? table.slotPlayer2 : (playerNr == 2 ? table.slotPlayer3
                  : table.slotPlayer4)));
          Card card = cardsDealt.removeLast();
          shedule(game.actionManager, new CardDealedAction(this, card, playerSlot));
        } else {
          Card card = cardsDealt.removeLast();
          shedule(game.actionManager, new PlaynAction(this, card, table.playSlot));
          playnSent = true;
        }
      } else {
        // do nothing
      }
    } else if (game.gameLogic.getMode() == Mode.Playing) {
      if (game.gameLogic.waitingForPlayer == this) {
        if (!cardPlayed) {
          List<Card> matches = Card.matches(game.gameLogic.currentPlayCard(), ownCardList);
          if (matches.isEmpty()) {
            Card card = table.talon.getLastUnusedChild();
            shedule( game.actionManager, new PickupAction(this, card, ownedSlot));
            card = table.talon.getLastUnusedChild();
            shedule(game.actionManager,new PickupAction(this, card, ownedSlot));
          } else {
            Card card = matches.get(0);
            shedule(game.actionManager,new CardPlayedAction(this, card, table.playSlot));
          }
          cardPlayed = true;
        } else {
          // do nothing already acted
        }
      } else {
        // other player
        cardPlayed = false;
      }

    }

  }

  private CardSlot<TiledCardsRotatedLayout> ownedSlot(MaumauCardtable table) {
    if (id == MaumauPlayerRegistry.ID_PLAYER1) {
      return table.slotPlayer1;
    } else if (id == MaumauPlayerRegistry.ID_PLAYER2) {
      return table.slotPlayer2;
    } else if (id == MaumauPlayerRegistry.ID_PLAYER3) {
      return table.slotPlayer3;
    } else if (id == MaumauPlayerRegistry.ID_PLAYER4) {
      return table.slotPlayer4;
    } else {
      throw new RuntimeException("Unknown id:"+id);
    }
  }

  // private List<Card> findCandidates(Card card) {
  // List<Card> candidates = Card.applyFilter(new Card.SuitFilter(card.value.suit()),
  // myCards.childs());
  // candidates.addAll(Card.applyFilter(new Card.RankFilter(card.value.rank()), myCards.childs()));
  // return candidates;
  // }

  // @Override
  // protected Card cardDecision() {
  // // TODO Auto-generated method stub
  // return null;
  // }

  // private GameAction nextDealingAction() {
  // if (game.talon.childs().isEmpty()) {
  // return fillTalonAction;
  // }
  // game.state.waitingForPlayer = game.playerRegistry().getNextPlayerOf(
  // game.state.waitingForPlayer == null ? this : game.state.waitingForPlayer);
  //
  // return new CardMoveAction2<TiledCardsRotatedLayout>(game.takeCardFromTalon(), 10,
  // game.state.waitingForPlayer.myCards);
  // }
  //
  // private boolean dealingFinished() {
  // boolean slot1Complete = game.slotPlayer1.childs().size() ==
  // MaumauCardGame.PROP_DealedCardsCount;
  // boolean slot2Complete = game.slotPlayer2.childs().size() ==
  // MaumauCardGame.PROP_DealedCardsCount;
  // boolean slot3Complete = game.slotPlayer3.childs().size() ==
  // MaumauCardGame.PROP_DealedCardsCount;
  // boolean slot4Complete = game.slotPlayer4.childs().size() ==
  // MaumauCardGame.PROP_DealedCardsCount;
  // return slot1Complete && slot2Complete && slot3Complete && slot4Complete;
  // }
  //
  // private boolean mustDeal() {
  // return game.state.mode == MaumauCardGame.State.Mode.Dealing && isDealer();
  // }
  //
  // @Override
  // protected Card cardDecision() {
  // boolean isitmyTurn = isItMyTurn();
  // Card card = (!isitmyTurn) ? lookForTakeOverCard() : lookForCard();
  // return card;
  // }
  //
  // private Card lookForCard() {
  // Card card = game.state.currentCard;
  // List<Card> candidates = findCandidates(card);
  // return !candidates.isEmpty() ? selectFromCandidates(candidates) : (Card) null;
  // }
  //
  // private Card selectFromCandidates(List<Card> candidates) {
  // // TODO: add tactics here
  // return candidates.iterator().next();
  // }
  //
  //
  // private Card lookForTakeOverCard() {
  // return null;
  // }
  //
  public boolean isDealer() {
    return dealer;
  }

  public void setDealer(boolean dealer) {
    this.dealer = dealer;
  }

}
