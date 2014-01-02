package de.alexkrieg.cards.maumau;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static playn.core.PlayN.log;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import playn.java.JavaPlatform;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.Card.Value;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.HeapLayout;
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

  private Value[][] playersCardValues = new Value[][] {
      {Value._2d, Value._3c, Value._4h, Value._5s, Value._6d, Value._7c}, // player1
      {Value._2s, Value._3d, Value._4c, Value._5h, Value._6s, Value._7d}, // player2
      {Value._2h, Value._3s, Value._4d, Value._5c, Value._6h, Value._7s}, // player3
      {Value._2c, Value._3h, Value._4s, Value._5d, Value._6c, Value._7h}};

  private MaumauGameLogic gameLogic;
  private MaumauPlayerRegistry playerRegistry;

  private GameLogicAction[] allActions;
  private CardSlot<StackLayout> talon;
  private CardSlot<HeapLayout> playSlot;
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
    when(player1.id()).thenReturn(MaumauPlayerRegistry.ID_PLAYER1);
    player2 = mock(MaumauRobotPlayer.class);
    when(player2.id()).thenReturn(MaumauPlayerRegistry.ID_PLAYER2);
    player3 = mock(MaumauRobotPlayer.class);
    when(player3.id()).thenReturn(MaumauPlayerRegistry.ID_PLAYER3);
    player4 = mock(MaumauRobotPlayer.class);
    when(player4.id()).thenReturn(MaumauPlayerRegistry.ID_PLAYER4);
    playerRegistry = mock(MaumauPlayerRegistry.class);
    when(playerRegistry.getNeighbourPlayerOf(eq(player1), eq(true))).thenReturn(player2);
    when(playerRegistry.getNeighbourPlayerOf(eq(player2), eq(true))).thenReturn(player3);
    when(playerRegistry.getNeighbourPlayerOf(eq(player3), eq(true))).thenReturn(player4);
    when(playerRegistry.getNeighbourPlayerOf(eq(player4), eq(true))).thenReturn(player1);
    when(playerRegistry.getNeighbourPlayerOf(eq(player1), eq(false))).thenReturn(player4);
    when(playerRegistry.getNeighbourPlayerOf(eq(player2), eq(false))).thenReturn(player1);
    when(playerRegistry.getNeighbourPlayerOf(eq(player3), eq(false))).thenReturn(player2);
    when(playerRegistry.getNeighbourPlayerOf(eq(player4), eq(false))).thenReturn(player3);

    gameLogic = new MaumauGameLogic(playerRegistry);
    gameLogic.configure();

    talon = new CardSlot<StackLayout>("Testtalon ", new StackLayout(0, 10));
    talon.init();
    gameLogic.talon = talon.childs();

    playSlot = new CardSlot<HeapLayout>("testplayslot", new HeapLayout(10, 10));
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

  }

  @Test
  public void systemReady() throws Exception {
    gotoAttracting();
  }

  @SuppressWarnings("unchecked")
  private void gotoAttracting() throws Exception {
    assertThat(gameLogic.talon.isEmpty(), is(true));
    expectLogicErrorOnAllActionsExceptOf(Mode.Init, SystemReadyAction.class);
    gameLogic.executeAction(new SystemReadyAction(null,null,null,0,null));
    assertThat(gameLogic.getMode(), is(Mode.Attracting));
  }

  private GameLogicAction instatiate(Class<?> actionClass) {
    GameLogicAction action;
    try {
      action = (GameLogicAction) actionClass.newInstance();
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
      Class<? extends GameLogicAction>... validActions) {
    List<Class<?>> allActionClasses = allActionClasses();
    for (Class<?> actionClass : allActionClasses) {
      if (!Arrays.asList(validActions).contains(actionClass)) {
        GameLogicAction action = instatiate(actionClass);
        expectLogicErrorOnAction(action, expectedMode, action
            + "should not be allowed!, Only allow:  " + validActions);
      }
    }
  }

  private void expectLogicErrorOnAction(GameLogicAction trigger, Mode expectedMode, String failMessage) {
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
    gameLogic.executeAction(new StartGameAction(talon,null,0));
    assertThat(gameLogic.getMode(), is(Mode.Dealing));
    assertThat(gameLogic.talon, is(not((List<Card>) null)));
    assertThat(gameLogic.talon.size(), is(52));
  }

  @Test
  public void dealing() throws Exception {
    gotoDealing();
    expectLogicErrorOnAllActionsExceptOf(Mode.Dealing, CardDealedAction.class);
    Value c1 = Value._3d;
    Value c2 = Value._4d;
    Value c3 = Value._th;
    dealingCardsToPlayer(player1, playerslot2, new Value[] {c1, c2, c3});
    assertThat(gameLogic.getMode(), is(Mode.Dealing));

    assertThat(Card.find(c1, gameLogic.slotPlayer2), is(not((Card) null)));
    assertThat(Card.find(c2, gameLogic.slotPlayer2), is(not((Card) null)));
    assertThat(Card.find(c3, gameLogic.slotPlayer2), is(not((Card) null)));

  }

  private void dealingCardsToPlayer(MaumauRobotPlayer dealer,
      CardSlot<TiledCardsRotatedLayout> playslot, Value[] cardValues) throws Exception {
    for (Value cv : cardValues) {
      List<Card> talonCards = talon.childs();
      gameLogic.executeAction(new CardDealedAction(dealer, Card.find(cv, talonCards), playslot,0));
    }
  }

  private void dealingCardsToPlayers(MaumauRobotPlayer dealer, Value[][] playerCards)
      throws Exception {
    dealingCardsToPlayer(dealer, playerslot1, playerCards[0]);
    dealingCardsToPlayer(dealer, playerslot2, playerCards[1]);
    dealingCardsToPlayer(dealer, playerslot3, playerCards[2]);
    dealingCardsToPlayer(dealer, playerslot4, playerCards[3]);

    for (int i = 0; i < playerCards.length; i++) {
      for (int j = 0; j < playerCards[0].length; j++) {
        assertThat(Card.find(playerCards[i][j], i == 0 ? gameLogic.slotPlayer1 : (i == 1
            ? gameLogic.slotPlayer2 : (i == 2 ? gameLogic.slotPlayer3 : gameLogic.slotPlayer4))),
            is(not((Card) null)));
      }
    }
  }

  @Test
  public void dealingButPlayerHaveEnoughCards() throws Exception {
    gotoDealing();
    dealingComplete();
    expectLogicErrorOnAction(new CardDealedAction(player1, talon.childs().get(0), playerslot1,0),
        Mode.Dealing, "Expect error because dealing with full playerSlots  ");
  }

  private void dealingComplete() throws Exception {
    // @formatter:off
    dealingCardsToPlayers(player1, playersCardValues); // player4
    // @formatter:on
  }

  @Test
  public void playnNotReadyCardsNotDealedComplete() throws Exception {
    gotoDealing();
    dealingCardsToPlayer(player1, playerslot2, new Value[] {
        Value._2c, Value._3c, Value._4c, Value._5c});
    assertThat(gameLogic.getMode(), is(Mode.Dealing));
    Card firstPlayCard = talon.firstCard();
    expectLogicErrorOnAction(new PlaynAction(player1, firstPlayCard, playSlot,0), Mode.Dealing,
        "Expect error because not all cards are dealt");
  }

  @Test
  public void playn() throws Exception {
    gotoPlayn(Value._ac);
  }

  @SuppressWarnings("unchecked")
  private void gotoPlayn(Value playSlotValue) throws Exception {
    gotoDealing();
    dealingComplete();

    expectLogicErrorOnAllActionsExceptOf(Mode.Dealing, PlaynAction.class, CardDealedAction.class);
    Card playSlotCard = Card.find(playSlotValue, talon.childs());
    assertThat(gameLogic.waitingForPlayer, is((MaumauRobotPlayer) null));

    gameLogic.executeAction(new PlaynAction(player1, playSlotCard, playSlot,0));
    verify(playerRegistry).getNeighbourPlayerOf(eq(player1), eq(true));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.playSlot.get(0), is(playSlotCard));
    assertThat(gameLogic.waitingForPlayer, is(player2));
    assertThat(gameLogic.direction, is(Direction.Clockwise));

  }

  @Test
  public void pickupFromTalon() throws Exception {
    gotoPlayn(Value._ac);
    Card card1 = talon.childs().get(0);
    Card card2 = talon.childs().get(1);
    assertThat(gameLogic.waitingForPlayer, is(player2));
    assertThat(gameLogic.slotPlayer2, not(hasItem(card1)));
    assertThat(gameLogic.slotPlayer2, not(hasItem(card2)));
    assertThat(gameLogic.direction, is(Direction.Clockwise));

    gameLogic.executeAction(new PickupAction(player2, card1, card2, playerslot2,0));
    verify(playerRegistry).getNeighbourPlayerOf(eq(player2), eq(true));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.waitingForPlayer, is(player3));
    assertThat(gameLogic.slotPlayer2, hasItem(card1));
    assertThat(gameLogic.slotPlayer2, hasItem(card2));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
  }

  @Test
  public void cardPlayedNoMatch() throws Exception {
    gotoPlayn(Value._ac);
    Card playersCard = Card.find(Value._2s, gameLogic.slotPlayer2);
    gameLogic.executeAction(new CardPlayedAction(player2, playersCard, playSlot,0));
    assertThat(gameLogic.currentPlayCard(), is(not(playersCard)));
  }

  @Test
  public void cardPlaynNotPlayersTurn() throws Exception {
    gotoPlayn(Value._ah);
    Card playersCard = Card.find(Value._2h, gameLogic.slotPlayer3);
    assertThat(gameLogic.waitingForPlayer, is(player2));
    gameLogic.executeAction(new CardPlayedAction(player3, playersCard, playSlot,0));
    assertThat(gameLogic.currentPlayCard(), is(not(playersCard)));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.waitingForPlayer, is(player2));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void cardPlaynSuccess() throws Exception {
    gotoPlayn(Value._as);
    expectLogicErrorOnAllActionsExceptOf(Mode.Playing, CardPlayedAction.class, PickupAction.class,
        PlayerFinishedAction.class, RefillTalonAction.class);
    Card playersCard = Card.find(Value._2s, gameLogic.slotPlayer2);
    gameLogic.executeAction(new CardPlayedAction(player2, playersCard, playSlot,0));
    assertThat(gameLogic.currentPlayCard(), is(playersCard));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.waitingForPlayer, is(player3));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
  }

  @Test
  public void cardPlayedNotFromPlayer() throws Exception {
    gotoPlayn(Value._ah);
    Card playersCard = Card.find(Value._2h, gameLogic.slotPlayer3);
    gameLogic.executeAction(new CardPlayedAction(player2, playersCard, playSlot,0));
    assertThat(gameLogic.currentPlayCard(), is(not(playersCard)));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.waitingForPlayer, is(player2));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
  }

  @Test
  public void player2Fishishing() throws Exception {
    playnCardsUntilFinish();
    assertThat(gameLogic.winner, is((MaumauRobotPlayer)null));
    gameLogic.executeAction(new PlayerFinishedAction(player2,null,0));
    assertThat(gameLogic.getMode(), is(Mode.Finishing));
    assertThat(gameLogic.winner, is(player2));
    
    gameLogic.executeAction(new PlayerFinishedAction(player3,null,0));
    assertThat(gameLogic.getMode(), is(Mode.Finishing));
    assertThat(gameLogic.winner, is(player2));

  }

  @Test
  public void playerFinishedButStillHasCards() throws Exception {
    playnCardsUntilFinish();
    assertThat(gameLogic.slotPlayer1.isEmpty(), is(not(true)));
    gameLogic.executeAction(new PlayerFinishedAction(player1,null,0));
    assertThat(gameLogic.getMode(), is(Mode.Finishing));
    
    // TODO: this is an error case handle it!
    // expectLogicErrorOnAction(new PlayerFinishedAction(player1), Mode.Playing,
    // "Expect Exception because Player still has cards");
  }

  @Test
  public void backtoAttracting() throws Exception {
    playnCardsUntilFinish();
    gameLogic.executeAction(new PlayerFinishedAction(player2,null,0));
    assertThat(gameLogic.getMode(), is(Mode.Finishing));
    expectLogicErrorOnAllActionsExceptOf(Mode.Finishing, LeaveResultsAction.class,PlayerFinishedAction.class);
    gameLogic.executeAction(new LeaveResultsAction());
    assertThat(gameLogic.getMode(), is(Mode.Attracting));
  }

  private void playnCardsUntilFinish() throws Exception {
    gotoPlayn(Value._as);
    playnCards(0, playersCardValues[0].length);
    assertThat(gameLogic.slotPlayer1.size(), is(1));
    assertThat(gameLogic.slotPlayer2.isEmpty(), is(true));
    assertThat(gameLogic.slotPlayer3.isEmpty(), is(true));
    assertThat(gameLogic.slotPlayer4.isEmpty(), is(true));
  }

  private void playnCards(int start, int end) throws Exception {
    for (int j = start; j < end; j++) {
      Card playersCard = Card.find(playersCardValues[1][j], gameLogic.slotPlayer2);
      gameLogic.executeAction(new CardPlayedAction(player2, playersCard, playSlot,0));
      assertThat(gameLogic.currentPlayCard(), is(playersCard));
      playersCard = Card.find(playersCardValues[2][j], gameLogic.slotPlayer3);
      gameLogic.executeAction(new CardPlayedAction(player3, playersCard, playSlot,0));
      assertThat(gameLogic.currentPlayCard(), is(playersCard));
      playersCard = Card.find(playersCardValues[3][j], gameLogic.slotPlayer4);
      gameLogic.executeAction(new CardPlayedAction(player4, playersCard, playSlot,0));
      assertThat(gameLogic.currentPlayCard(), is(playersCard));
      if (j + 1 < playersCardValues[0].length) {
        playersCard = Card.find(playersCardValues[0][j + 1], gameLogic.slotPlayer1);
        gameLogic.executeAction(new CardPlayedAction(player1, playersCard, playSlot,0));
        assertThat(gameLogic.currentPlayCard(), is(playersCard));
      }
    }
  }

  @Test
  public void refillTalon() throws Exception {
    gotoPlayn(Value._as);
    playnCards(0, 1);
    refillTalonWith(playSlot.firstCard());
    expectLogicErrorOnAllActionsExceptOf(Mode.Refilling, CardPlayedAction.class,
        PickupAction.class, RefillTalonAction.class, TalonFilledAction.class);
    playnCards(2, 3);
    refillTalonWith(playSlot.firstCard());
    playnCards(3, 4);
    Card card1 = talon.childs().get(0);
    Card card2 = talon.childs().get(1);

    gameLogic.executeAction(new PickupAction(player2, card1, card2, playerslot2,0));
    Card currentPlayCard = gameLogic.currentPlayCard();
    gameLogic.executeAction(new TalonFilledAction(null,null,null,0,player1));
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
    assertThat(gameLogic.currentPlayCard(), is(currentPlayCard));

  }

  @Test
  public void talonFilled() throws Exception {
    gotoPlayn(Value._as);
    playnCards(0, 1);
    Card currentPlayCard = gameLogic.currentPlayCard();
    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
    assertThat(gameLogic.currentPlayCard(), is(currentPlayCard));
    assertThat(gameLogic.waitingForPlayer, is(player2));

    refillTalonWith(playSlot.firstCard());
    refillTalonWith(playSlot.firstCard());

    assertThat(gameLogic.getMode(), is(Mode.Refilling));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
    assertThat(gameLogic.currentPlayCard(), is(currentPlayCard));
    assertThat(gameLogic.waitingForPlayer, is(player2));

    gameLogic.executeAction(new TalonFilledAction(null,null,null,0,player1));

    assertThat(gameLogic.getMode(), is(Mode.Playing));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
    assertThat(gameLogic.currentPlayCard(), is(currentPlayCard));
    assertThat(gameLogic.waitingForPlayer, is(player2));

  }

  private void refillTalonWith(Card card) throws Exception {
    Card currentPlayCard = gameLogic.currentPlayCard();
    gameLogic.executeAction(new RefillTalonAction(player1, card, talon,0));
    assertThat(gameLogic.getMode(), is(Mode.Refilling));
    assertThat(gameLogic.direction, is(Direction.Clockwise));
    assertThat(gameLogic.currentPlayCard(), is(currentPlayCard));
    assertThat(gameLogic.talon.get(gameLogic.talon.size() - 1), is(card));
  }

}
