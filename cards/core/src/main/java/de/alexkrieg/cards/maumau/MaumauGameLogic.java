package de.alexkrieg.cards.maumau;

import static playn.core.PlayN.log;

import java.util.List;

import com.googlecode.stateless4j.StateMachine;
import com.googlecode.stateless4j.delegates.Action1;
import com.googlecode.stateless4j.delegates.Action2;
import com.googlecode.stateless4j.delegates.Func;
import com.googlecode.stateless4j.triggers.TriggerWithParameters1;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.GameLogic;
import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.maumau.action.CardPlayedAction;
import de.alexkrieg.cards.maumau.action.LeaveResultsAction;
import de.alexkrieg.cards.maumau.action.PlayerFinishedAction;
import de.alexkrieg.cards.maumau.action.PlaynAction;
import de.alexkrieg.cards.maumau.action.RefillTalonAction;
import de.alexkrieg.cards.maumau.action.StartGameAction;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;
import de.alexkrieg.cards.maumau.action.TalonFilledAction;

public class MaumauGameLogic implements GameLogic {

  public final static int dialedCardsCount = 6;

  @SuppressWarnings("serial")
  public static class Error extends RuntimeException {

    final Mode mode;
    final Class<? extends GameAction> action;

    public Error(String message, Mode mode, Class<? extends GameAction> action) {
      super(message);
      this.mode = mode;
      this.action = action;
    }

  }

  static abstract class ModeEntry<A extends GameAction> implements Action1<A> {

  }

  static enum Direction {
    Clockwise, Counterclockwise
  }

  public static enum Mode {
    Init, Attracting, Dealing, Playing, Refilling, Finishing;
  }

  MaumauPlayer waitingForPlayer;
  Direction direction;

  List<Card> slotPlayer1;
  List<Card> slotPlayer2;
  List<Card> slotPlayer3;
  List<Card> slotPlayer4;

  List<Card> talon;

  List<Card> playSlot;

  private StateMachine<Mode, Class<? extends GameAction>> stateMachine;

  private Func<Boolean> readyForPlayn = new Func<Boolean>() {

    @Override
    public Boolean call() {
      boolean cardsDialed = slotPlayer1.size() == 6 && slotPlayer2.size() == 6
          && slotPlayer3.size() == 6 && slotPlayer4.size() == 6;
      boolean talonSize = talon.size() == 52 - (4 * 6);
      boolean result = cardsDialed && talonSize;
      if (result)
        return result;
      String message = !cardsDialed ? "PlayerSlots are not complete!" : "talon size is wrong: ="
          + talon.size();
      log().error(message);
      return false;
    }

  };

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
      boolean oneSlotEmpty = slotPlayer1.isEmpty() || slotPlayer2.isEmpty() || slotPlayer3.isEmpty() || slotPlayer4.isEmpty();
      if (oneSlotEmpty)
        return true;
      log().error("can't finish, all players still have cards!");
      return false;
    }
  };

  
  private final ModeEntry<CardPlayedAction> cardPlayedModeEntry = new ModeEntry<CardPlayedAction>() {

    @Override
    public void doIt(CardPlayedAction cardPlaydAction) {
      Card card = cardPlaydAction.card();
      Card currenPlayCard = currentPlayCard();
      if (card.value.suit() != currenPlayCard.value.suit()
          && card.value.rank() != currenPlayCard.value.rank()) {
        log().error("can't play card "+ cardPlaydAction.card() +" on " + currenPlayCard);
      } else if ( !cardPlaydAction.player().ownedCards().childs().contains(card)){ 
        log().error("played card "+card+" does not belong to player "+ cardPlaydAction.player());
      }
      else {
        cardPlaydAction.execute();
      }
        
    }

  };

  private final ModeEntry<PlayerFinishedAction> playerFinishedModeEntry = new ModeEntry<PlayerFinishedAction>() {

    @Override
    public void doIt(PlayerFinishedAction finishedAction) {
      MaumauPlayer finishedPlayer = finishedAction.player();
      boolean playerHasNoCards = finishedPlayer.ownedCards().childs().isEmpty();
      if ( playerHasNoCards) {
        finishedAction.execute();
      } else {
        log().error("Player "+finishedPlayer+" still has cards, finish is wrong!");
      }
      
    }
    
  };
  
  
  
  
  public Card currentPlayCard() {
    return playSlot.get(playSlot.size() - 1);
  }
  
  private TriggerWithParameters1<CardPlayedAction, Mode, Class<? extends GameAction>> cardPlayedTrigger;
  private TriggerWithParameters1<PlayerFinishedAction, Mode, Class<? extends GameAction>> playerFinishedTrigger;

  public void configure() throws Exception {

    stateMachine = new StateMachine<Mode, Class<? extends GameAction>>(Mode.Init);
    Action2<Mode, Class<? extends GameAction>> unhandledTriggerAction = new Action2<Mode, Class<? extends GameAction>>() {
      @Override
      public void doIt(Mode mode, Class<? extends GameAction> actionClass) throws Exception {
        throw new Error("Can't go to mode:\"" + mode + "\", trigger \""
            + actionClass.getSimpleName() + "\" is not allowed", mode, actionClass);
      }

    };
    stateMachine.OnUnhandledTrigger(unhandledTriggerAction);

    stateMachine.Configure(Mode.Init).Permit(SystemReadyAction.class, Mode.Attracting);

    stateMachine.Configure(Mode.Attracting).Permit(StartGameAction.class, Mode.Dealing);
    stateMachine.Configure(Mode.Dealing).PermitIf(PlaynAction.class, Mode.Playing, readyForPlayn);
    stateMachine.Configure(Mode.Playing).Permit(RefillTalonAction.class, Mode.Refilling).PermitIf(
        PlayerFinishedAction.class, Mode.Finishing,readyForFinishing);

    cardPlayedTrigger = new TriggerWithParameters1<CardPlayedAction, Mode, Class<? extends GameAction>>(
        CardPlayedAction.class, CardPlayedAction.class);
    stateMachine.SetTriggerParameters(CardPlayedAction.class, CardPlayedAction.class);
    stateMachine.Configure(Mode.Playing).PermitReentryIf(CardPlayedAction.class, readyForPlaynCard);
    stateMachine.Configure(Mode.Playing).OnEntryFrom(cardPlayedTrigger, cardPlayedModeEntry,
        CardPlayedAction.class);
    
    playerFinishedTrigger = new TriggerWithParameters1<PlayerFinishedAction, Mode, Class<? extends GameAction>>(
        PlayerFinishedAction.class, PlayerFinishedAction.class);
    stateMachine.Configure(Mode.Playing).OnEntryFrom(playerFinishedTrigger, playerFinishedModeEntry,
        PlayerFinishedAction.class);
    

    stateMachine.Configure(Mode.Finishing).Permit(LeaveResultsAction.class, Mode.Attracting);
    stateMachine.Configure(Mode.Refilling).PermitReentry(CardPlayedAction.class).PermitIf(
        TalonFilledAction.class, Mode.Playing,readyForContinuePlaying);

  }


  public String gameinfo() {
    return "State [ currentCard=" + currentPlayCard() + ", waitingForPlayer=" + waitingForPlayer
        + ", direction=" + direction + "]";
  }

  Mode getMode() {
    return stateMachine.getState();
  }

  @Override
  public void executeAction(GameAction action) throws Exception {
    if (action instanceof CardPlayedAction) {
      stateMachine.Fire(cardPlayedTrigger, (CardPlayedAction) action);
    } else {
      stateMachine.Fire((Class<? extends GameAction>) action.getClass());
      action.execute();
    }
  }

}
