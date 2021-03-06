package de.alexkrieg.cards.maumau;

import static playn.core.PlayN.log;

import java.util.List;

import javax.inject.Inject;

import com.googlecode.stateless4j.StateMachine;
import com.googlecode.stateless4j.delegates.Action;
import com.googlecode.stateless4j.delegates.Action1;
import com.googlecode.stateless4j.delegates.Action2;
import com.googlecode.stateless4j.delegates.Func;
import com.googlecode.stateless4j.triggers.TriggerWithParameters1;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.GameLogic;
import de.alexkrieg.cards.core.LogUtil;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.maumau.action.CardDealedAction;
import de.alexkrieg.cards.maumau.action.CardPlayedAction;
import de.alexkrieg.cards.maumau.action.LeaveResultsAction;
import de.alexkrieg.cards.maumau.action.PickupAction;
import de.alexkrieg.cards.maumau.action.PlayerFinishedAction;
import de.alexkrieg.cards.maumau.action.PlaynAction;
import de.alexkrieg.cards.maumau.action.RefillTalonAction;
import de.alexkrieg.cards.maumau.action.StartGameAction;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;
import de.alexkrieg.cards.maumau.action.TalonFilledAction;

public class MaumauGameLogic implements GameLogic<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> {

  public final static int dialedCardsCount = 6;

  @SuppressWarnings("serial")
  public static class Error extends RuntimeException {

    final Mode mode;
    final Class<? extends GameLogicAction> action;

    public Error(String message, Mode mode, Class<? extends GameLogicAction> action) {
      super(message);
      this.mode = mode;
      this.action = action;
    }

    public Error(String message, Throwable cause) {
      super(message, cause);
      this.mode = null;
      this.action = null;
    }

  }

  static abstract class ModeEntry<A extends GameLogicAction> implements Action1<A> {

  }

  static enum Direction {
    Clockwise, Counterclockwise
  }

  public static enum Mode {
    Init, Attracting, Dealing, Playing, Refilling, Finishing;
  }

  MaumauRobotPlayer waitingForPlayer;
  Direction direction;
  MaumauRobotPlayer winner;

  List<Card> slotPlayer1;
  List<Card> slotPlayer2;
  List<Card> slotPlayer3;
  List<Card> slotPlayer4;

  List<Card> talon;

  List<Card> playSlot;

  private StateMachine<Mode, Class<? extends GameLogicAction>> stateMachine;
  final private MaumauPlayerRegistry playerRegistry;

  @Inject
  public MaumauGameLogic(MaumauPlayerRegistry playerRegistry) {
    super();
    this.playerRegistry = playerRegistry;
    this.waitingForPlayer = null;
    this.direction = null;
    this.winner = null;
  }

  private class ReadyForPlayn implements Func<Boolean> {

    private final boolean ready;

    public ReadyForPlayn(boolean ready) {
      super();
      this.ready = ready;
    }

    @Override
    public Boolean call() {
      boolean playerSlotsFilled = playerSlotsFilled();
      boolean result = (ready && playerSlotsFilled) || (!ready && !playerSlotsFilled);
      if (result)
        return result;
      log().error("PlayerSlots are " + (ready ? " not" : "") + " complete! " + dump());
      return false;
    }
  };

  String dump() {
    StringBuffer strb = new StringBuffer("GameLogic:\n");
    strb.append("talon:").append(LogUtil.logString(talon)).append("\n");
    strb.append("playSlot:").append(LogUtil.logString(playSlot)).append("\n");
    strb.append("player1:").append(LogUtil.logString(slotPlayer1)).append("\n");
    strb.append("player2:").append(LogUtil.logString(slotPlayer2)).append("\n");
    strb.append("player3:").append(LogUtil.logString(slotPlayer3)).append("\n");
    strb.append("player4:").append(LogUtil.logString(slotPlayer4)).append("\n");
    return strb.toString();
  }

  private boolean playerSlotsFilled() {
    boolean cardsDialed = slotPlayer1.size() == dialedCardsCount
        && slotPlayer2.size() == dialedCardsCount && slotPlayer3.size() == dialedCardsCount
        && slotPlayer4.size() == dialedCardsCount;
    return cardsDialed;
  }

  private Func<Boolean> readyForPlaynCard = new Func<Boolean>() {

    @Override
    public Boolean call() {
      if (!playSlot.isEmpty())
        return true;
      log().error("No card in playslot");
      return false;
    }

  };

  private Func<Boolean> readyForContinuePlaying = new Func<Boolean>() {

    @Override
    public Boolean call() {
      if (!talon.isEmpty())
        return true;
      log().error("Talon still empty");
      return false;
    }
  };

  private Func<Boolean> readyForFinishing = new Func<Boolean>() {

    @Override
    public Boolean call() {
      boolean oneSlotEmpty = slotPlayer1.isEmpty() || slotPlayer2.isEmpty()
          || slotPlayer3.isEmpty() || slotPlayer4.isEmpty();
      if (oneSlotEmpty)
        return true;
      log().error("can't finish, all players still have cards!");
      return false;
    }
  };

  private final ModeEntry<PlaynAction> playnModeEntry = new ModeEntry<PlaynAction>() {

    @Override
    public void doIt(PlaynAction playnAction) {
      playnAction.execute();
      direction = Direction.Clockwise;
      winner = null;
      nextPlayer(playnAction.player());
    }

  };

  private final ModeEntry<PickupAction> pickupModeEntry = new ModeEntry<PickupAction>() {

    @Override
    public void doIt(PickupAction pickupAction) {
      pickupAction.execute();
      nextPlayer(pickupAction.player());
    }

  };

  private final ModeEntry<CardPlayedAction> cardPlayedModeEntry = new ModeEntry<CardPlayedAction>() {

    @Override
    public void doIt(CardPlayedAction cardPlaydAction) {
      MaumauRobotPlayer player = cardPlaydAction.player();
      if (player != waitingForPlayer) {
        log().error(
            "its not  " + player + " who were waiting for, we wait for  " + waitingForPlayer);
      } else {
        Card card = cardPlaydAction.layerEntity();
        Card currenPlayCard = currentPlayCard();
        if (!Card.matches(card, currenPlayCard)) {
          log().error("can't play card " + cardPlaydAction.layerEntity() + " on " + currenPlayCard);
        } else {
          if (!playersCards(player).contains(card)) {
            log().error("played card " + card + " does not belong to player " + player);
          } else {
            cardPlaydAction.execute();
            nextPlayer(player);
          }
        }

      }
    }

  };

  private final ModeEntry<PlayerFinishedAction> playerFinishedModeEntry = new ModeEntry<PlayerFinishedAction>() {

    @Override
    public void doIt(PlayerFinishedAction finishedAction) {
      MaumauRobotPlayer finishedPlayer = finishedAction.player();
      boolean playerHasNoCards = playersCards(finishedPlayer).isEmpty();
      if (playerHasNoCards) {
        if (winner == null) {
          winner = finishedAction.player();
          finishedAction.execute();
        } else {
          log().info("Player finished too late, " + winner + " already has it !");
        }
      } else {
        log().error("Player " + finishedPlayer + " still has cards, finish is wrong!");
      }

    }

  };

  private final Action playnEntry = new Action() {

    @Override
    public void doIt() {
      // waitingForPlayer = playerRegistry.getNextPlayerOf(playerRegistry.getDealer());
    }

  };

  public Card currentPlayCard() {
    return playSlot.get(playSlot.size() - 1);
  }

  private TriggerWithParameters1<CardPlayedAction, Mode, Class<? extends GameLogicAction>> cardPlayedTrigger;
  private TriggerWithParameters1<PlayerFinishedAction, Mode, Class<? extends GameLogicAction>> playerFinishedTrigger;
  private TriggerWithParameters1<PlaynAction, Mode, Class<? extends GameLogicAction>> playnTrigger;
  private TriggerWithParameters1<PickupAction, Mode, Class<? extends GameLogicAction>> pickupTrigger;

  private List<Card> playersCards(MaumauRobotPlayer finishedPlayer) {
    if (finishedPlayer.id() == MaumauPlayerRegistry.ID_PLAYER1) {
      return slotPlayer1;
    } else if (finishedPlayer.id() == MaumauPlayerRegistry.ID_PLAYER2) {
      return slotPlayer2;
    } else if (finishedPlayer.id() == MaumauPlayerRegistry.ID_PLAYER3) {
      return slotPlayer3;
    } else if (finishedPlayer.id() == MaumauPlayerRegistry.ID_PLAYER4) {
      return slotPlayer4;
    } else {
      throw new RuntimeException("Unknown id:" + finishedPlayer.id());
    }
  }

  private void nextPlayer(MaumauRobotPlayer player) {
    waitingForPlayer = playerRegistry.getNeighbourPlayerOf(player, direction == Direction.Clockwise);
  }

  @Override
  public void configure() {
    stateMachine = new StateMachine<Mode, Class<? extends GameLogicAction>>(Mode.Init);
    Action2<Mode, Class<? extends GameLogicAction>> unhandledTriggerAction = new Action2<Mode, Class<? extends GameLogicAction>>() {
      @Override
      public void doIt(Mode mode, Class<? extends GameLogicAction> actionClass) throws Exception {
        throw new Error("mode:\"" + mode + "\", trigger \"" + actionClass.getSimpleName()
            + "\" is not allowed", mode, actionClass);
      }

    };
    try {
      stateMachine.OnUnhandledTrigger(unhandledTriggerAction);

      stateMachine.Configure(Mode.Init).Permit(SystemReadyAction.class, Mode.Attracting);

      stateMachine.Configure(Mode.Attracting).Permit(StartGameAction.class, Mode.Dealing);

      stateMachine.Configure(Mode.Dealing).PermitReentryIf(CardDealedAction.class,
          new ReadyForPlayn(false));

      // Dealing->Playing
      playnTrigger = new TriggerWithParameters1<PlaynAction, Mode, Class<? extends GameLogicAction>>(
          PlaynAction.class, PlaynAction.class);
      stateMachine.SetTriggerParameters(PlaynAction.class, PlaynAction.class);
      stateMachine.Configure(Mode.Dealing).PermitIf(PlaynAction.class, Mode.Playing,
          new ReadyForPlayn(true));
      stateMachine.Configure(Mode.Playing).OnEntryFrom(playnTrigger, playnModeEntry,
          PlaynAction.class);

      stateMachine.Configure(Mode.Playing).Permit(RefillTalonAction.class, Mode.Refilling);

      cardPlayedTrigger = new TriggerWithParameters1<CardPlayedAction, Mode, Class<? extends GameLogicAction>>(
          CardPlayedAction.class, CardPlayedAction.class);
      stateMachine.SetTriggerParameters(CardPlayedAction.class, CardPlayedAction.class);
      stateMachine.Configure(Mode.Playing).PermitReentryIf(CardPlayedAction.class,
          readyForPlaynCard);

      stateMachine.Configure(Mode.Playing).PermitReentryIf(PickupAction.class, readyForPlaynCard);

      stateMachine.Configure(Mode.Playing).OnEntryFrom(cardPlayedTrigger, cardPlayedModeEntry,
          CardPlayedAction.class);

      stateMachine.Configure(Mode.Playing).OnEntryFrom(PlaynAction.class, playnEntry);

      // PickupTrigger
      pickupTrigger = new TriggerWithParameters1<PickupAction, Mode, Class<? extends GameLogicAction>>(
          PickupAction.class, PickupAction.class);
      stateMachine.SetTriggerParameters(PickupAction.class, PickupAction.class);
      stateMachine.Configure(Mode.Playing).OnEntryFrom(pickupTrigger, pickupModeEntry,
          PickupAction.class);

      stateMachine.Configure(Mode.Refilling).PermitReentry(CardPlayedAction.class).PermitReentry(
          RefillTalonAction.class).PermitReentry(PickupAction.class).PermitIf(
          TalonFilledAction.class, Mode.Playing, readyForContinuePlaying);
      stateMachine.Configure(Mode.Refilling).OnEntryFrom(cardPlayedTrigger, cardPlayedModeEntry,
          CardPlayedAction.class);

      playerFinishedTrigger = new TriggerWithParameters1<PlayerFinishedAction, Mode, Class<? extends GameLogicAction>>(
          PlayerFinishedAction.class, PlayerFinishedAction.class);
      stateMachine.SetTriggerParameters(PlayerFinishedAction.class, PlayerFinishedAction.class);
      stateMachine.Configure(Mode.Playing).PermitIf(PlayerFinishedAction.class, Mode.Finishing,
          readyForFinishing);
      stateMachine.Configure(Mode.Finishing).OnEntryFrom(playerFinishedTrigger,
          playerFinishedModeEntry, PlayerFinishedAction.class);

      stateMachine.Configure(Mode.Finishing).Permit(LeaveResultsAction.class, Mode.Attracting);

      stateMachine.Configure(Mode.Finishing).PermitReentry(PlayerFinishedAction.class);

    } catch (Exception e) {
      throw new Error("Problem during configuration of gamelogic", e);
    }

  }

  public String gameinfo() {
    return "State [ currentCard=" + currentPlayCard() + ", waitingForPlayer=" + waitingForPlayer
        + ", direction=" + direction + "]";
  }

  Mode getMode() {
    return stateMachine.getState();
  }

  @Override
  public void executeAction(GameLogicAction action) throws Exception {
    log().info("want to execute " + action + "...");
    if (action instanceof CardPlayedAction) {
      stateMachine.Fire(cardPlayedTrigger, (CardPlayedAction) action);
    } else if (action instanceof PlaynAction) {
      stateMachine.Fire(playnTrigger, (PlaynAction) action);
    } else if (action instanceof PickupAction) {
      stateMachine.Fire(pickupTrigger, (PickupAction) action);
    } else if (action instanceof PlayerFinishedAction) {
      stateMachine.Fire(playerFinishedTrigger, (PlayerFinishedAction) action);
    } else {
      stateMachine.Fire((Class<? extends GameLogicAction>) action.getClass());
      log().info("execute " + action);
      action.execute();
    }
    log().info("game mode= " + getMode() + ",waiting4Player=" + waitingForPlayer);
  }

}
