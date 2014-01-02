package de.alexkrieg.cards.maumau;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.sound.midi.SysexMessage;

import org.junit.Before;
import org.junit.Test;

import playn.java.JavaPlatform;
import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.util.Filter;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;

public class ActionManagerTest {

  static {
    JavaPlatform.register();
  }

  static class IdentityFilter extends Filter<GameAction> {

    final GameAction action;

    public IdentityFilter(GameAction action) {
      super();
      this.action = action;
    }

    @Override
    public boolean apply(GameAction candidate, List<GameAction> cardSet) {
      return candidate == this.action;
    }

  }

  private MaumauGameLogic gameLogic;
  private MaumauActionManager actionManager;

  @Before
  public void setup() {
    gameLogic = mock(MaumauGameLogic.class);
    actionManager = new MaumauActionManager(gameLogic);
  }

  @Test
  public void schedule1() throws Exception {
    checkSheduleDuration(1);
  }
  
  @Test
  public void schedule0() throws Exception {
    checkSheduleDuration(0);
  }
  
  @Test
  public void schedule30() throws Exception {
    checkSheduleDuration(30);
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void findSystemReady() throws Exception {
    MaumauRobotPlayer player = mock(MaumauRobotPlayer.class);
    GameAction action = new SystemReadyAction(null,null,null,1,player);
    actionManager.schedule(action);
    
    List<GameAction> findScheduled = actionManager.findScheduled(new Filter.And<GameAction>(new GameAction.TypeFilter((Class<? super GameLogicAction>)action.getClass()), new GameLogicAction.PlayerFilter(player), true));
    assertThat(findScheduled.get(0),is(action)); 
  }
  

  private void checkSheduleDuration(int duration) throws Exception {
    GameLogicAction sheduleAction = sheduleAction(duration);
    for (int i = 0; i < duration; i++) {
      actionManager.executeActions();
      verify(gameLogic, times(0)).executeAction(eq(sheduleAction));
    }

    actionManager.executeActions();
    verify(gameLogic, times(1)).executeAction(eq(sheduleAction));
  }

  private GameLogicAction sheduleAction(int duration) {
    GameLogicAction action = mock(GameLogicAction.class);
    when(action.getDuration()).thenReturn(duration);

    actionManager.schedule(action);
    List<GameAction> findScheduled = actionManager.findScheduled(new IdentityFilter(action));
    assertThat(findScheduled.size(), is(1));
    assertThat(findScheduled, hasItem(action));
    return action;
  }

}
