package de.alexkrieg.cards.maumau;

import static de.alexkrieg.cards.core.Card.matches;
import static de.alexkrieg.cards.core.Card.matchesNot;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static playn.core.PlayN.log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import playn.java.JavaPlatform;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauGameLogic.Mode;
import de.alexkrieg.cards.maumau.action.CardPlayedAction;
import de.alexkrieg.cards.maumau.action.LeaveResultsAction;
import de.alexkrieg.cards.maumau.action.PlayerFinishedAction;
import de.alexkrieg.cards.maumau.action.PlaynAction;
import de.alexkrieg.cards.maumau.action.RefillTalonAction;
import de.alexkrieg.cards.maumau.action.StartGameAction;
import de.alexkrieg.cards.maumau.action.SystemReadyAction;
import de.alexkrieg.cards.maumau.action.TalonFilledAction;

public class MaumauGameLogicTest {

  static {
    JavaPlatform.register();
  }

  private MaumauGameLogic gameLogic;
  private CardSlot<StackLayout> talon;
  private CardSlot<StackLayout> playSlot;
  private CardSlot<TiledCardsRotatedLayout> playerslot1;
  private CardSlot<TiledCardsRotatedLayout> playerslot2;
  private CardSlot<TiledCardsRotatedLayout> playerslot3;
  private CardSlot<TiledCardsRotatedLayout> playerslot4;
  private MaumauPlayer player1;
  private MaumauPlayer player2;

  @Before
  public void setup() throws Exception {
    gameLogic = new MaumauGameLogic();
    gameLogic.configure();
    talon = new CardSlot<StackLayout>("testtalon", new StackLayout(10, 10));
    talon.init();
    gameLogic.talon = talon.childs();

    playSlot = new CardSlot<StackLayout>("testplayslot", new StackLayout(10, 10));
    playSlot.init();
    gameLogic.playSlot = playSlot.childs();

    playerslot1 = new CardSlot<TiledCardsRotatedLayout>("playslot1", new TiledCardsRotatedLayout(0,
        10));
    playerslot1.init();
    gameLogic.slotPlayer1 = playerslot1.childs();

    playerslot2 = new CardSlot<TiledCardsRotatedLayout>("playslot2", new TiledCardsRotatedLayout(0,
        10));
    playerslot2.init();
    gameLogic.slotPlayer2 = playerslot2.childs();

    playerslot3 = new CardSlot<TiledCardsRotatedLayout>("playslot3", new TiledCardsRotatedLayout(0,
        10));
    playerslot3.init();
    gameLogic.slotPlayer3 = playerslot3.childs();

    playerslot4 = new CardSlot<TiledCardsRotatedLayout>("playslot4", new TiledCardsRotatedLayout(0,
        10));
    playerslot4.init();
    gameLogic.slotPlayer4 = playerslot4.childs();

    player1 = new MaumauPlayer("testPlayer1", gameLogic, null, playerslot1) {

      @Override
      protected Card cardDecision() {
        // TODO Auto-generated method stub
        return null;
      }
    };

    player2 = new MaumauPlayer("testPlayer2", gameLogic, null, playerslot2) {

      @Override
      protected Card cardDecision() {
        // TODO Auto-generated method stub
        return null;
      }
    };

  }


  @Test
  public void systemReady() throws Exception {
    gotoAttracting();
  }

  @Test
  public void startGame() throws Exception {
    gotoDealing();
  }
  

  @Test
  public void playnNotReady() throws Exception {
    gameLogic.executeAction(new SystemReadyAction());
    gameLogic.executeAction(new StartGameAction(talon));
    assertThat(gameLogic.slotPlayer1.isEmpty(), is(true));
    expectLogicErrorOnAction(new PlaynAction(), Mode.Dealing,
        "Expect error because player has no cards");
  }

  @Test
  public void playn() throws Exception {
    gotoPlayn();
  }

  @Test
  public void cardPlayedSlotEmpty() throws Exception {
    gotoPlayn();
    expectLogicErrorOnAction(new CardPlayedAction(player1, playerslot1.firstCard(), playSlot),
        Mode.Playing, "Expect error because playslot is empty");
  }

  @Test
  public void cardPlayedNoMatch() throws Exception {
    gotoPlayn();
    playSlot.put(talon.childs().get(0), null);
    final Card playSlotCard = gameLogic.playSlot.get(0);
    List<Card> cardsNotMatch = matchesNot(playSlotCard, gameLogic.slotPlayer1);
    Card playersCard = cardsNotMatch.get(0);

    gameLogic.executeAction(new CardPlayedAction(player1, playersCard, playSlot));
    assertThat(gameLogic.currentPlayCard(), is(not(playersCard)));
  }


  @SuppressWarnings("unchecked")
  @Test
  public void cardPlaynSuccess() throws Exception {
    Card playersCard = findMatchingCardForPlayer1();
    expectLogicErrorOnAllActionsExceptOf(Mode.Playing, CardPlayedAction.class,PlayerFinishedAction.class,RefillTalonAction.class);
    gameLogic.executeAction(new CardPlayedAction(player1, playersCard, playSlot));
    assertThat(gameLogic.currentPlayCard(), is(playersCard));
  }

  @Test
  public void cardPlayedNotFromPlayer() throws Exception {
    Card playersCard = findMatchingCardForPlayer1();
    gameLogic.executeAction(new CardPlayedAction(player2, playersCard, playSlot));
    assertThat(gameLogic.currentPlayCard(), is(not(playersCard)));
  }

  @Test
  public void refillTalon() throws Exception {
    gotoRefillTalon();
  }
  
  @Test
  public void talonFilled() throws Exception {
    gotoRefillTalon();
    expectLogicErrorOnAllActionsExceptOf(Mode.Refilling, CardPlayedAction.class,TalonFilledAction.class);
    gameLogic.executeAction(new TalonFilledAction());
    assertThat(gameLogic.getMode(),is(Mode.Playing));
  }
  
  @Test
  public void talonFilledButisNot() throws Exception {
    gotoRefillTalon();
    expectLogicErrorOnAllActionsExceptOf(Mode.Refilling, CardPlayedAction.class,TalonFilledAction.class);
    gameLogic.talon.clear();
    expectLogicErrorOnAction(new TalonFilledAction(), Mode.Refilling, "Expect Exception because talon is (still) empty");
  }

  @Test
  public void playerFinishedButStillHasCards() throws Exception {
    gotoPlayn();
    assertThat(gameLogic.slotPlayer1.isEmpty(),is(not(true)));
    expectLogicErrorOnAction(new PlayerFinishedAction(player1),Mode.Playing,"Expect Exception because Player still has cards");
  }
  
  @Test
  public void playerFinishedSuccess() throws Exception {
    gotoFinishing();
  }
  
  @Test
  public void backtoAttracting() throws Exception {
    gotoFinishing();
    expectLogicErrorOnAllActionsExceptOf(Mode.Finishing, LeaveResultsAction.class);
    gameLogic.executeAction(new LeaveResultsAction());
    assertThat(gameLogic.getMode(),is(Mode.Attracting));
  }
  
  


  private void gotoFinishing() throws Exception {
    gotoPlayn();
    gameLogic.slotPlayer1.clear();
    assertThat(gameLogic.slotPlayer1.isEmpty(),is(true));
    gameLogic.executeAction(new PlayerFinishedAction(player1));
    assertThat(gameLogic.getMode(),is(Mode.Finishing));
  }
  
  
  
  
  
  

  private void gotoRefillTalon() throws Exception {
    Card playersCard = findMatchingCardForPlayer1();
    gameLogic.executeAction(new CardPlayedAction(player1, playersCard, playSlot));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    gameLogic.executeAction(new RefillTalonAction());
    assertThat(gameLogic.getMode(), is(Mode.Refilling));
  }
  
  
  
  @SuppressWarnings("unchecked")
  private void gotoAttracting() throws Exception {
    assertThat(gameLogic.talon.isEmpty(), is(true));
    expectLogicErrorOnAllActionsExceptOf(Mode.Init, SystemReadyAction.class);
    gameLogic.executeAction(new SystemReadyAction());
    assertThat(gameLogic.getMode(), is(Mode.Attracting));
  }


  @SuppressWarnings("unchecked")
  private void gotoDealing() throws Exception {
    gotoAttracting();
    expectLogicErrorOnAllActionsExceptOf(Mode.Attracting, StartGameAction.class);
    gameLogic.executeAction(new StartGameAction(talon));
    assertThat(gameLogic.getMode(), is(Mode.Dealing));
    assertThat(gameLogic.talon, is(not((List<Card>) null)));
    assertThat(gameLogic.talon.size(), is(52));
  }

  @SuppressWarnings("unchecked")
  private void gotoPlayn() throws Exception {
    gotoDealing();
    moveCards(talon, playerslot1, 6);
    moveCards(talon, playerslot2, 6);
    moveCards(talon, playerslot3, 6);
    moveCards(talon, playerslot4, 6);
    
    expectLogicErrorOnAllActionsExceptOf(Mode.Dealing, PlaynAction.class);
    gameLogic.executeAction(new PlaynAction());
    assertThat(gameLogic.getMode(), is(Mode.Playing));
  }

  private Card findMatchingCardForPlayer1() throws Exception {
    List<Card> cardsMatch = Collections.EMPTY_LIST;
    do {
      setup();
      gotoPlayn();
      playSlot.put(talon.childs().get(0), null);
      final Card playSlotCard = gameLogic.playSlot.get(0);
      cardsMatch = matches(playSlotCard, gameLogic.slotPlayer1);
    } while (cardsMatch.isEmpty());
    Card playersCard = cardsMatch.get(0);
    return playersCard;
  }


  private void moveCards(CardSlot<? extends Layout<Card>> source,
      CardSlot<? extends Layout<Card>> dest, int count) {
    for (int i = 0; i < count; i++) {
      dest.put(source.childs().get(i), dest);
    }
  }

  private void fillSlot(CardSlot<? extends Layout<Card>> cs, Card... cards) {
    for (Card card : cards) {
      cs.put(card, null);
    }
  }

  private void expectLogicErrorOnAllActionsExceptOf(Mode expectedMode,
      Class<? extends GameAction>... validActions) {
    List<Class<?>> allActionClasses = allActionClasses();
    for (Class<?> actionClass : allActionClasses) {
      if (!Arrays.asList(validActions).contains(actionClass)) {
        GameAction action = instatiate(actionClass);
        expectLogicErrorOnAction(action, expectedMode, action
            + "should not be allowed!, Only allow:  " + validActions);
      }
    }
  }

  private GameAction instatiate(Class<?> actionClass) {
    GameAction action;
    try {
      action = (GameAction) actionClass.newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return action;
  }

  public List<Class<?>> allActionClasses() {
    return ActionManager.allActionsClasses("de.alexkrieg.cards.maumau.action");

  }

  // Class.forName(className)

  // Class<?>[] classes = GameAction.class.getPackage().
  // return classes;
  // GameAction[] allActions = new GameAction[] {new SystemReadyAction(),new
  // StartGameAction(talon),new PlaynAction(),new RefillTalonAction(),new PlayerFinishedAction(),new
  // LeaveResultsAction(),new CardPlayedAction(player1,new Card(Value._2c),playSlot)};
  // return allActions;
  // }

  private void expectLogicErrorOnAction(GameAction trigger, Mode expectedMode, String failMessage) {
    try {
      gameLogic.executeAction(trigger);
      fail(failMessage);
    } catch (MaumauGameLogic.Error e) {
      assertThat(e.mode, is(expectedMode));
      log().info(e.getMessage());
    } catch (Exception e) {
      String msg = "unexpected Exception thrown: " + e;
      log().error(msg, e);
      fail(msg);
    }
  }

}
