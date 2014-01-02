package de.alexkrieg.cards.maumau;

import java.util.List;

import javax.inject.Inject;

import playn.core.Layer;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.layout.HeapLayout;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.action.LeaveResultsAction;
import de.alexkrieg.cards.maumau.action.StartGameAction;

public class MaumauCardtable extends CardTable<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> {

  public CardSlot<TiledCardsRotatedLayout> slotPlayer1;
  public CardSlot<TiledCardsRotatedLayout> slotPlayer2;
  public CardSlot<TiledCardsRotatedLayout> slotPlayer3;
  public CardSlot<TiledCardsRotatedLayout> slotPlayer4;
  public CardSlot<StackLayout> talon;
  public CardSlot<HeapLayout> playSlot;

  public Root attractingRoot;
  public Root playnRoot;
  public Root finishRoot;
  private Label winnerLabel = new Label("");

  @Inject
  public MaumauCardtable(ActionManager actionManager, NESWLayout layout) {
    super("MaumauCardtable",actionManager, layout);
  }

  @Override
  public void update(int delta, CardGame<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> game) {
    if (game.gameLogic.talon == null) {
      connect(game.gameLogic);
    }
    if (iface != null) {
      boolean startScreen = game.gameLogic.getMode() == MaumauGameLogic.Mode.Attracting;
      attractingRoot.setVisible(startScreen);
      attractingRoot.layer.setDepth(startScreen ? Float.MAX_VALUE : Float.MIN_VALUE);
      boolean finishScreen = game.gameLogic.getMode() == MaumauGameLogic.Mode.Finishing;
      if (finishScreen) {
        winnerLabel.text.update(game.gameLogic.winner.id + " has won!");
      }
      finishRoot.layer.setDepth(finishScreen ? Float.MAX_VALUE : Float.MIN_VALUE);
      finishRoot.setVisible(finishScreen);

      super.update(delta, game);
    }
  }

  @Override
  public void init() {
    super.init();
    slotPlayer1 = new CardSlot<TiledCardsRotatedLayout>("Player1 ", new TiledCardsRotatedLayout(0,
        10));
    slotPlayer2 = new CardSlot<TiledCardsRotatedLayout>("Player2", new TiledCardsRotatedLayout(0,
        10));
    slotPlayer3 = new CardSlot<TiledCardsRotatedLayout>("Player3", new TiledCardsRotatedLayout(0,
        10));
    slotPlayer4 = new CardSlot<TiledCardsRotatedLayout>("Player4", new TiledCardsRotatedLayout(0,
        10));
    talon = new CardSlot<StackLayout>("talon", new StackLayout(2, 2));

    playSlot = new CardSlot<HeapLayout>("playSlot", new HeapLayout(20, 20));

    put(slotPlayer1, NESWLayout.NESW.N);
    put(slotPlayer2, NESWLayout.NESW.E);
    put(slotPlayer3, NESWLayout.NESW.S);
    put(slotPlayer4, NESWLayout.NESW.W);
    put(talon, NESWLayout.NESW.NE);
    put(playSlot, NESWLayout.NESW.C);

  }

  // protected void fi
  // // create our demo interface

  private void connect(MaumauGameLogic gameLogic) {
    gameLogic.playSlot = playSlot.childs();
    gameLogic.talon = talon.childs();
    gameLogic.slotPlayer1 = slotPlayer1.childs();
    gameLogic.slotPlayer2 = slotPlayer2.childs();
    gameLogic.slotPlayer3 = slotPlayer3.childs();
    gameLogic.slotPlayer4 = slotPlayer4.childs();
  }

  @Override
  protected void fillWithLayers(List<Layer> layers) {
    super.fillWithLayers(layers);
    createAttractingRoot();
    layers.add(attractingRoot.layer);
    createFinishRoot();
    layers.add(finishRoot.layer);
  }

  private void createAttractingRoot() {
    attractingRoot = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet());
    attractingRoot.setSize(width(), height());
    attractingRoot.addStyles(Style.BACKGROUND.is(Background.blank().inset(5)));
    Group buttons = new Group(AxisLayout.vertical().offStretch());
    attractingRoot.add(new Label("Card Table"), buttons).addStyles(Style.TEXT_WRAP.is(true));

    Button button = new Button("Start");
    buttons.add(button);
    button.clicked().connect(new UnitSlot() {
      @Override
      public void onEmit() {
        actionManager.schedule(new StartGameAction(talon,null,1));
      }
    });

  }

  private void createFinishRoot() {
    finishRoot = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet());
    finishRoot.setSize(width(), height());
    finishRoot.addStyles(Style.BACKGROUND.is(Background.blank().inset(5)));
    Group buttons = new Group(AxisLayout.vertical().offStretch());
    finishRoot.add(winnerLabel, buttons).addStyles(Style.TEXT_WRAP.is(true));

    Button button = new Button("OK");
    buttons.add(button);
    button.clicked().connect(new UnitSlot() {
      @Override
      public void onEmit() {
        actionManager.schedule(new LeaveResultsAction(MaumauCardtable.this,null,5));
      }
    });
    // finishRoot.layer.setDepth(Float.MAX_VALUE);
  }
  
  
  public void clear() {
    slotPlayer1.removeAll();
    slotPlayer2.removeAll();
    slotPlayer3.removeAll();
    slotPlayer4.removeAll();
    talon.removeAll();
    playSlot.removeAll();
  }

  

}
