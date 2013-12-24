package de.alexkrieg.cards.maumau;

import javax.inject.Inject;

import react.UnitSlot;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.action.StartGameAction;


public class MaumauCardtable extends CardTable<NESWLayout,MaumauRobotPlayer,MaumauGameLogic> {
  
  public CardSlot<TiledCardsRotatedLayout> slotPlayer1;
  public CardSlot<TiledCardsRotatedLayout> slotPlayer2;
  public CardSlot<TiledCardsRotatedLayout> slotPlayer3;
  public CardSlot<TiledCardsRotatedLayout> slotPlayer4;
  public CardSlot<StackLayout> talon;
  public CardSlot<StackLayout> playSlot;



  @Inject
  public MaumauCardtable( ActionManager actionManager, NESWLayout layout) {
    super( actionManager,layout);
  }
  
  @Override
  public void update(int delta, CardGame<NESWLayout,MaumauRobotPlayer,MaumauGameLogic> game) {
    if ( game.gameLogic.talon== null) {
      connect(game.gameLogic);
    }
    if ( iface != null ) {
      boolean startScreen =  game.gameLogic.getMode()== MaumauGameLogic.Mode.Attracting;
      iface.roots().iterator().next().setVisible(startScreen);
      super.update(delta,game);
    }
  }


  @Override
  public void init() {
    super.init();
     slotPlayer1 = new CardSlot<TiledCardsRotatedLayout>(
        "Player1 ", new TiledCardsRotatedLayout(0, 10));
    slotPlayer2 = new CardSlot<TiledCardsRotatedLayout>(
        "Player2", new TiledCardsRotatedLayout(0, 10));
    slotPlayer3 = new CardSlot<TiledCardsRotatedLayout>(
        "Player3", new TiledCardsRotatedLayout(0, 10));
    slotPlayer4 = new CardSlot<TiledCardsRotatedLayout>(
        "Player4", new TiledCardsRotatedLayout(0, 10));
    talon = new CardSlot<StackLayout>("talon", new StackLayout(2, 2));
    
    playSlot = new CardSlot<StackLayout>("playSlot", new StackLayout(2, 2));
    
    put(slotPlayer1, NESWLayout.NESW.N);
    put(slotPlayer2, NESWLayout.NESW.E);
    put(slotPlayer3, NESWLayout.NESW.S);
    put(slotPlayer4, NESWLayout.NESW.W);
    put(talon, NESWLayout.NESW.NE);
    put(playSlot, NESWLayout.NESW.C);

  }



  @Override
  protected void fillHUDRoot(final Root root) {
    Group buttons  = new Group(AxisLayout.vertical().offStretch());
    root.add(new Label("Card Table"), buttons ).addStyles(
        Style.TEXT_WRAP.is(true));

    Button button = new Button("Start");
    buttons.add(button);
    button.clicked().connect(new UnitSlot() {
      @Override
      public void onEmit() {
        actionManager.schedule(new StartGameAction(talon));
      }
    });
  }



  private void connect(MaumauGameLogic gameLogic) {
    gameLogic.playSlot = playSlot.childs();
    gameLogic.talon = talon.childs();
    gameLogic.slotPlayer1 = slotPlayer1.childs();
    gameLogic.slotPlayer2 = slotPlayer2.childs();
    gameLogic.slotPlayer3 = slotPlayer3.childs();
    gameLogic.slotPlayer4 = slotPlayer4.childs();
  }


}
