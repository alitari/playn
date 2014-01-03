package de.alexkrieg.cards.maumau;

import java.util.LinkedList;
import java.util.List;

import playn.core.util.Clock.Source;
import de.alexkrieg.cards.core.AbstractPlayer;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.Word;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.action.MoveSimpleAction;
import de.alexkrieg.cards.core.action.VisibiltyAction;
import de.alexkrieg.cards.core.layout.AbsolutLayout;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.action.CardDealedAction;
import de.alexkrieg.cards.maumau.action.CardPlayedAction;
import de.alexkrieg.cards.maumau.action.PickupAction;
import de.alexkrieg.cards.maumau.action.PlayerFinishedAction;
import de.alexkrieg.cards.maumau.action.PlaynAction;
import de.alexkrieg.cards.maumau.action.RefillTalonAction;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;
import de.alexkrieg.cards.maumau.action.TalonFilledAction;

public class MaumauRobotPlayer extends
    AbstractPlayer<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> {

  private int actionDuration = 30;

  private boolean dealer = false;
  private LinkedList<Card> cardsDealt = new LinkedList<Card>();

  public MaumauRobotPlayer(String name) {
    super(name);
  }

  @Override
  public void paint(Source _clock) {
    // nothing tzo paint yet
  }

  @Override
  public void update(int delta, CardGame<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> game) {

    switch (game.gameLogic.getMode()) {
      case Init:
        handleInit(game.actionManager, (MaumauCardGame) game);
        break;
      case Attracting:
        handleAttracting();
        break;
      case Dealing:
        handleDealing(game.actionManager, (MaumauCardtable) game.cardTable);
        break;
      case Playing:
        handlePlaying(game.actionManager, (MaumauCardtable) game.cardTable,
            ownedSlot((MaumauCardtable) game.cardTable), game.gameLogic.currentPlayCard(),
            game.gameLogic.waitingForPlayer);
        break;
      case Refilling:
        handleRefilling(game.actionManager, (MaumauCardGame) game,
            (MaumauCardtable) game.cardTable, game.gameLogic.waitingForPlayer);
        break;
      case Finishing:
        handleFinishing();
        break;
      default:
        throw new IllegalStateException("Unknown mode: " + game.gameLogic.getMode());

    }

  }

  private void handleAttracting() {
    // do nothing

  }

  private void handleFinishing() {
    // do nothing

  }

  private void handleRefilling(ActionManager actionManager, MaumauCardGame game,
      MaumauCardtable cardTable, MaumauRobotPlayer waitingForPlayer) {
    if (waitingForPlayer == this) {
      if (cardTable.playSlot.getFirstUnusedChilds(4).size() < 4
          && noActionScheduled(actionManager, TalonFilledAction.class)) {
        Word refilled = game.textLayer.findChild(MaumauCardGame.Words.Refilled.name());
        shedule(actionManager,
            new TalonFilledAction(refilled,
                game.textLayer.layout(), new AbsolutLayout.Attr(300, 150, 0, 0.5F), actionDuration,
                this));
        sheduleOnce(2000,actionManager, new VisibiltyAction<Word>(refilled, 30, false));
      } else {
        if (noActionScheduled(actionManager, TalonFilledAction.class)) {
          shedule(actionManager, new RefillTalonAction(this,
              cardTable.playSlot.getFirstUnusedChilds(1).get(0), cardTable.talon, actionDuration));
        }
      }
    }

  }

  private void handlePlaying(ActionManager actionManager, MaumauCardtable cardTable,
      CardSlot<TiledCardsRotatedLayout> ownedSlot, Card currentPlayCard,
      MaumauRobotPlayer waitingForPlayer) {
    if (waitingForPlayer == this) {
      if (noActionScheduled(actionManager, PlayerFinishedAction.class)
          && noActionScheduled(actionManager, CardPlayedAction.class)) {
        List<Card> matches = Card.matches(currentPlayCard, ownedSlot.childs());
        if (matches.isEmpty()) {
          pickupCards(actionManager, cardTable, ownedSlot);
          if (cardTable.talon.childs().size() < 8) {
            List<Card> unusedChilds = cardTable.playSlot.getFirstUnusedChilds(1);
            if (!unusedChilds.isEmpty()) {
              shedule(actionManager, new RefillTalonAction(this, unusedChilds.get(0),
                  cardTable.talon, 2));
              cardTable.playSlot.layout().reset();
            }
          }
        } else {
          Card card = matches.get(0);
          shedule(actionManager, new CardPlayedAction(this, card, cardTable.playSlot,
              actionDuration));
          if (ownedSlot.childs().size() == 1) {
            sheduleOnce(actionManager, new PlayerFinishedAction(this, cardTable, actionDuration));
          }
        }
      }

    } else {
      // other player
    }

  }

  private void pickupCards(ActionManager actionManager, MaumauCardtable cardTable,
      CardSlot<TiledCardsRotatedLayout> ownedSlot) {
    if (findMyScheduledFromType(actionManager, PickupAction.class).isEmpty()) {
      List<Card> cards = cardTable.talon.getLastUnusedChilds(2);
      shedule(actionManager, new PickupAction(this, cards.get(0), cards.get(1), ownedSlot,
          actionDuration));
    }
  }

  private void handleDealing(ActionManager actionManager, MaumauCardtable cardTable) {
    if (isDealer()) {
      List<Card> talon = cardTable.talon.childs();
      if (cardsDealt.isEmpty()
          && actionManager.findScheduled(new GameLogicAction.PlayerFilter(this)).isEmpty()) {
        cardsDealt.addAll(talon.subList(talon.size() - 4 * MaumauGameLogic.dialedCardsCount - 1,
            talon.size()));
      }
      if (cardsDealt.size() > 1) {
        int playerNr = (cardsDealt.size() - 1) % 4;
        CardSlot<TiledCardsRotatedLayout> playerSlot = (playerNr == 0 ? cardTable.slotPlayer1
            : (playerNr == 1 ? cardTable.slotPlayer2 : (playerNr == 2 ? cardTable.slotPlayer3
                : cardTable.slotPlayer4)));
        Card card = cardsDealt.removeLast();
        shedule(actionManager, new CardDealedAction(this, card, playerSlot, actionDuration));
      } else {
        if (cardsDealt.size() == 1) {
          Card card = cardsDealt.removeLast();
          shedule(actionManager, new PlaynAction(this, card, cardTable.playSlot, actionDuration));
        }
      }
    }
  }

  private void handleInit(ActionManager actionManager, MaumauCardGame game) {
    if (isDealer()) {
      sheduleOnce(actionManager,
          new SystemReadyAction(game.textLayer.findChild(MaumauCardGame.Words.MauMauTitle.name()),
              game.textLayer.layout(), new AbsolutLayout.Attr(220, 150, 0, 1), 30, this));

      Word arcadeTitle = game.textLayer.findChild(MaumauCardGame.Words.ArcadeTitle.name());
      sheduleOnce(
          actionManager,
          new MoveSimpleAction<Word, AbsolutLayout<Word>>(
              arcadeTitle,
              game.textLayer.layout(), new AbsolutLayout.Attr(300, 300, 0, 0.5F), 30).with(new GameAction.Animation.Rotate(
              1)));
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
      throw new RuntimeException("Unknown id:" + id);
    }
  }

  @Override
  public boolean isDealer() {
    return dealer;
  }

  public void setDealer(boolean dealer) {
    this.dealer = dealer;
  }

}
