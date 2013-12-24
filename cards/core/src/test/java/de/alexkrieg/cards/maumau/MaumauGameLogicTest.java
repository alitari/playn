package de.alexkrieg.cards.maumau;

import static de.alexkrieg.cards.core.Card.matches;
import static de.alexkrieg.cards.core.Card.matchesNot;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import de.alexkrieg.cards.maumau.MaumauGameLogic.Direction;
import de.alexkrieg.cards.maumau.MaumauGameLogic.Mode;
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

public class MaumauGameLogicTest {

  static {
    JavaPlatform.register();
  }

  private MaumauGameLogic gameLogic;
  private MaumauPlayerRegistry playerRegistry;

  private GameAction[] allActions;
  private CardSlot<? extends Layout<Card>> talon;
  private CardSlot<StackLayout> playSlot;
  private CardSlot<TiledCardsRotatedLayout> playerslot1;
  private CardSlot<TiledCardsRotatedLayout> playerslot2;
  private CardSlot<TiledCardsRotatedLayout> playerslot3;
  private CardSlot<TiledCardsRotatedLayout> playerslot4;
  private MaumauRobotPlayer player1;
  private MaumauRobotPlayer player2;
  private MaumauRobotPlayer player3;
  private MaumauRobotPlayer player4;

  @Before
  public void setup() throws Exception {

    player1 = mock(MaumauRobotPlayer.class);
    player2 = mock(MaumauRobotPlayer.class);
    player3 = mock(MaumauRobotPlayer.class);
    player4 = mock(MaumauRobotPlayer.class);
    playerRegistry = mock(MaumauPlayerRegistry.class);
    when(playerRegistry.getNeighbourPlayerOf(eq(player1),eq(true))).thenReturn(player2);
    when(playerRegistry.getNeighbourPlayerOf(eq(player2),eq(true))).thenReturn(player3);
    when(playerRegistry.getNeighbourPlayerOf(eq(player3),eq(true))).thenReturn(player4);
    when(playerRegistry.getNeighbourPlayerOf(eq(player4),eq(true))).thenReturn(player1);
    when(playerRegistry.getNeighbourPlayerOf(eq(player1),eq(false))).thenReturn(player4);
    when(playerRegistry.getNeighbourPlayerOf(eq(player2),eq(false))).thenReturn(player1);
    when(playerRegistry.getNeighbourPlayerOf(eq(player3),eq(false))).thenReturn(player2);
    when(playerRegistry.getNeighbourPlayerOf(eq(player4),eq(false))).thenReturn(player3);
    
    
    

    gameLogic = new MaumauGameLogic(playerRegistry);
    gameLogic.configure();

    talon = new CardSlot<StackLayout>("Testtalon ", new StackLayout(0, 10));
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

    allActions = new GameAction[] {
        mock(CardDealedAction.class), mock(CardPlayedAction.class), mock(LeaveResultsAction.class),
        mock(PickupAction.class), mock(PlayerFinishedAction.class), mock(PlaynAction.class),
        mock(RefillTalonAction.class), mock(StartGameAction.class), mock(SystemReadyAction.class),
        mock(TalonFilledAction.class)};

    

  }

//  @Test
//  public void actionsComplete() {
//    assertThat("Actions are not in sync: " + allActionClasses(), allActions.length,
//        is(allActionClasses().size()));
//  }

  @Test
  public void systemReady() throws Exception {
    gotoAttracting();
  }

  private <T> T mockaction(Class<T> clazz) {
    for (GameAction action : allActions) {
      if (clazz.isInstance(action))
        return (T) action;
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private void gotoAttracting() throws Exception {
    assertThat(gameLogic.talon.isEmpty(), is(true));
    expectLogicErrorOnAllActionsExceptOf(Mode.Init, SystemReadyAction.class);
    gameLogic.executeAction(new SystemReadyAction());
    assertThat(gameLogic.getMode(), is(Mode.Attracting));
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
    return ActionManager.Util.allActionsClasses("de.alexkrieg.cards.maumau.action");
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

  @Test
  public void startGame() throws Exception {
    gotoDealing();
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

  @Test
  public void dealing() throws Exception {
    gotoDealing();
    expectLogicErrorOnAllActionsExceptOf(Mode.Dealing, CardDealedAction.class);
    gameLogic.executeAction(new CardDealedAction(player1, talon.childs().get(0), playerslot1));
    assertThat(gameLogic.getMode(), is(Mode.Dealing));

  }

  @Test
  public void dealingButPlayerHaveEnoughCards() throws Exception {
    gotoDealing();
    fillPlayerSlots();
    expectLogicErrorOnAction(new CardDealedAction(player1, talon.childs().get(0), playerslot1), Mode.Dealing,
        "Dealing with full playerSlots  ");
  }

  @Test
  public void playnNotReadyCardsNotDealed() throws Exception {
    gotoDealing();
    assertThat(gameLogic.slotPlayer1.isEmpty(), is(true));
    Card firstPlayCard = talon.firstCard();
    expectLogicErrorOnAction(new PlaynAction(player1, firstPlayCard , playSlot), Mode.Dealing,
        "Expect error because player has no cards");
  }

  @Test
  public void playn() throws Exception {
    gotoPlayn();
  }

  @Test
  public void pickupFromTalon() throws Exception {
    gotoPlayn();
    Card card1 = talon.childs().get(0);
    Card card2 = talon.childs().get(1);
    assertThat(gameLogic.waitingForPlayer,is(player2));
    assertThat(gameLogic.slotPlayer2,not(hasItem(card1)));
    assertThat(gameLogic.slotPlayer2,not(hasItem(card2)));
    assertThat(gameLogic.direction,is(Direction.Clockwise));
    
    gameLogic.executeAction(new PickupAction(player2, card1,card2, playerslot2));
    verify(playerRegistry).getNeighbourPlayerOf(eq(player2), eq(true));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.waitingForPlayer, is(player3));
    assertThat(gameLogic.slotPlayer2,hasItem(card1));
    assertThat(gameLogic.slotPlayer2,hasItem(card2));
    assertThat(gameLogic.direction,is(Direction.Clockwise));
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
    expectLogicErrorOnAllActionsExceptOf(Mode.Playing, CardPlayedAction.class, PickupAction.class,
        PlayerFinishedAction.class, RefillTalonAction.class);
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
    expectLogicErrorOnAllActionsExceptOf(Mode.Refilling, CardPlayedAction.class,
        TalonFilledAction.class);
    gameLogic.executeAction(new TalonFilledAction());
    assertThat(gameLogic.getMode(), is(Mode.Playing));
  }

  @Test
  public void talonFilledButisNot() throws Exception {
    gotoRefillTalon();
    expectLogicErrorOnAllActionsExceptOf(Mode.Refilling, CardPlayedAction.class,
        TalonFilledAction.class);
    gameLogic.talon.clear();
    expectLogicErrorOnAction(new TalonFilledAction(), Mode.Refilling,
        "Expect Exception because talon is (still) empty");
  }

  @Test
  public void playerFinishedButStillHasCards() throws Exception {
    gotoPlayn();
    assertThat(gameLogic.slotPlayer1.isEmpty(), is(not(true)));
    expectLogicErrorOnAction(new PlayerFinishedAction(player1), Mode.Playing,
        "Expect Exception because Player still has cards");
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
    assertThat(gameLogic.getMode(), is(Mode.Attracting));
  }

  private void gotoFinishing() throws Exception {
    gotoPlayn();
    gameLogic.slotPlayer1.clear();
    assertThat(gameLogic.slotPlayer1.isEmpty(), is(true));
    gameLogic.executeAction(new PlayerFinishedAction(player1));
    assertThat(gameLogic.getMode(), is(Mode.Finishing));
  }

  private void gotoRefillTalon() throws Exception {
    Card playersCard = findMatchingCardForPlayer1();
    gameLogic.executeAction(new CardPlayedAction(player1, playersCard, playSlot));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    gameLogic.executeAction(new RefillTalonAction());
    assertThat(gameLogic.getMode(), is(Mode.Refilling));
  }

  @SuppressWarnings("unchecked")
  private void gotoPlayn() throws Exception {
    gotoDealing();
    fillPlayerSlots();
    assertThat(playSlot.childs().isEmpty(),is(true));
    expectLogicErrorOnAllActionsExceptOf(Mode.Dealing, PlaynAction.class, CardDealedAction.class);
    Card firstPlayCard = talon.firstCard();
    assertThat(gameLogic.waitingForPlayer,is((MaumauRobotPlayer)null));
    
    
    gameLogic.executeAction(new PlaynAction(player1, firstPlayCard , playSlot));
    verify(playerRegistry).getNeighbourPlayerOf(eq(player1), eq(true));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.playSlot.get(0),is(firstPlayCard));
    assertThat(gameLogic.waitingForPlayer,is(player2));
    assertThat(gameLogic.direction,is(Direction.Clockwise));
    
    
  }

  private void fillPlayerSlots() {
    moveCards(talon, playerslot1, MaumauGameLogic.dialedCardsCount);
    moveCards(talon, playerslot2, MaumauGameLogic.dialedCardsCount);
    moveCards(talon, playerslot3, MaumauGameLogic.dialedCardsCount);
    moveCards(talon, playerslot4, MaumauGameLogic.dialedCardsCount);
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

 

}
