package de.alexkrieg.cards.maumau;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import playn.java.JavaPlatform;

import com.googlecode.stateless4j.StateMachine;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.maumau.MaumauPlayer;

public class StatemachineTest {

  private StateMachine<State, Class<? extends GameAction>> stateMachine;

  static {
    JavaPlatform.register();
  }

  static class ActionAdapter implements GameAction {

    @Override
    public void execute() {
    }

    @Override
    public int getDuration() {
      return 0;
    }

    @Override
    public void paint(int tick, float alpha) {
    }

  }

  static class SystemReadyAction extends ActionAdapter {

  }

  static class StartGameAction extends ActionAdapter {

  }
  
  static class PlaynAction extends ActionAdapter {

  }

  static class CardPlayedAction extends ActionAdapter {

  }

  static class RefillTalonAction extends ActionAdapter {

  }
  
  static class TalonFilledAction extends ActionAdapter {

  }
  
  
  static class PlayerFinishedAction extends ActionAdapter {

  }
  
  static class LeaveResultsAction extends ActionAdapter {

  }

  

  

  public static enum State {

    Init, Attracting, Dealing, Playing, Refilling,Finishing;

    static enum Direction {
      Clock, AgainstClock;
    }

    Card currentCard;
    MaumauPlayer waitingForPlayer;
    Direction direction;

    @Override
    public String toString() {
      return "State [ currentCard=" + currentCard + ", waitingForPlayer=" + waitingForPlayer
          + ", direction=" + direction + "]";
    }

  }

  @Before
  public void setup() throws Exception {
    stateMachine = createStateMachine();
  }

  @Test
  public void transistions() throws Exception {
    assertThat(stateMachine.getState(),is(State.Init));
    checkNotPermitted(StartGameAction.class);
    
    stateMachine.Fire(SystemReadyAction.class);
    assertThat(stateMachine.getState(),is(State.Attracting));

    
    stateMachine.Fire(StartGameAction.class);
    assertThat(stateMachine.getState(),is(State.Dealing));
    
    
    stateMachine.Fire(PlaynAction.class);
    assertThat(stateMachine.getState(),is(State.Playing));
    
    
    stateMachine.Fire(CardPlayedAction.class);
    assertThat(stateMachine.getState(),is(State.Playing));
    
    
    stateMachine.Fire(CardPlayedAction.class);
    assertThat(stateMachine.getState(),is(State.Playing));
    
    
    stateMachine.Fire(RefillTalonAction.class);
    assertThat(stateMachine.getState(),is(State.Refilling));
    
    stateMachine.Fire(CardPlayedAction.class);
    assertThat(stateMachine.getState(),is(State.Refilling));
    
    
    stateMachine.Fire(TalonFilledAction.class);
    assertThat(stateMachine.getState(),is(State.Playing));
    
    stateMachine.Fire(PlayerFinishedAction.class);
    assertThat(stateMachine.getState(),is(State.Finishing));
    
    stateMachine.Fire(LeaveResultsAction.class);
    assertThat(stateMachine.getState(),is(State.Attracting));
    
  }
  
  
  

  private void checkNotPermitted(Class<? extends GameAction>... actions) {
    for (Class<? extends GameAction> action : actions) {
      try {
        stateMachine.Fire(action);
        fail("java.lang.Exception: NoTransitionsPermitted expected");
      } catch (Exception e) {
        assertThat(e.getMessage(), is("NoTransitionsPermitted"));
      }
    }
  }


  private StateMachine<State, Class<? extends GameAction>> createStateMachine() throws Exception {
    StateMachine<State, Class<? extends GameAction>> maumauFSM = new StateMachine<State, Class<? extends GameAction>>(
        State.Init);
    maumauFSM.Configure(State.Init).Permit(SystemReadyAction.class, State.Attracting);
    maumauFSM.Configure(State.Attracting).Permit(StartGameAction.class, State.Dealing);
    maumauFSM.Configure(State.Dealing).Permit(PlaynAction.class, State.Playing);
    maumauFSM.Configure(State.Playing).PermitReentry(CardPlayedAction.class).Permit(RefillTalonAction.class, State.Refilling).Permit(PlayerFinishedAction.class, State.Finishing);
    
    maumauFSM.Configure(State.Finishing).Permit(LeaveResultsAction.class, State.Attracting);
    maumauFSM.Configure(State.Refilling).PermitReentry(CardPlayedAction.class).Permit(TalonFilledAction.class, State.Playing);
    return maumauFSM;
  }

}
