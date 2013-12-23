package de.alexkrieg.cards.maumau;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauGameLogic.Mode;
import de.alexkrieg.cards.maumau.action.CardDealedAction;
import de.alexkrieg.cards.maumau.action.CardPlayedAction;
import de.alexkrieg.cards.maumau.action.PickupAction;
import de.alexkrieg.cards.maumau.action.PlaynAction;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;

public class MaumauRobotPlayer extends MaumauPlayer {

  private boolean dealer = false;
  private boolean systemReadySent = false;
  private boolean playnSent = false;
  private LinkedList<Card> cardsDealt = new LinkedList<Card>();
  private boolean cardPlayed = false;

  public MaumauRobotPlayer(String name, MaumauGameLogic gameLogic, ActionManager actionManager,
      MaumauCardtable cardTable, CardSlot<TiledCardsRotatedLayout> myCards) {
    super(name, gameLogic, actionManager, cardTable, myCards);
  }

  @Override
  public void update() {
    if (gameLogic.getMode() == Mode.Init && !systemReadySent) {
      if (isDealer()) {
        systemReadySent = true;
        shedule(new SystemReadyAction());
      } else {
        // do nothing
      }

    } else if (gameLogic.getMode() == Mode.Dealing && !playnSent) {
      if (isDealer()) {
        List<Card> talon = table().talon.childs();
        if (cardsDealt.isEmpty()) {
          cardsDealt.addAll(talon.subList(talon.size() - 4 * MaumauGameLogic.dialedCardsCount - 1,
              talon.size()));
        }
        if (cardsDealt.size() > 1) {
          int playerNr = (cardsDealt.size() - 1) % 4;
          CardSlot<TiledCardsRotatedLayout> playerSlot = (playerNr == 0 ? table().slotPlayer1
              : (playerNr == 1 ? table().slotPlayer2 : (playerNr == 2 ? table().slotPlayer3
                  : table().slotPlayer4)));
          Card card = cardsDealt.removeLast();
          shedule(new CardDealedAction(this, card, playerSlot));
        } else {
          Card card = cardsDealt.removeLast();
          shedule(new PlaynAction(this, card, table().playSlot));
          playnSent = true;
        }
      } else {
        // do nothing
      }
    } else if (gameLogic.getMode() == Mode.Playing) {
      if (gameLogic.waitingForPlayer == this) {
        if (!cardPlayed) {
          List<Card> ownCardList = ownedCards().childs();
          List<Card> matches = Card.matches(gameLogic.currentPlayCard(), ownCardList);
          if (matches.isEmpty()) {
            Card card = table().talon.getLastUnusedChild();
            shedule(new PickupAction(this, card, ownedCards()));
            card = table().talon.getLastUnusedChild();
            shedule(new PickupAction(this, card, ownedCards()));
          } else {
            Card card = matches.get(0);
            shedule(new CardPlayedAction(this, card, table().playSlot));
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
