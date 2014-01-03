package de.alexkrieg.cards.core;

import static playn.core.PlayN.graphics;

import javax.inject.Inject;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.util.Clock;
import de.alexkrieg.cards.core.layout.AbsolutLayout;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.layout.WordLayout;

public class CardGame<L extends Layout<CardSlot<?>>, P extends Player<L,P,G>, G extends GameLogic<L,P,G>>
    extends Game.Default {

  
  protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);
  public static final int UPDATE_RATE = 50;
  
  public final ActionManager actionManager;// = new ActionManager(50);
  final public CardTable<L,P,G> cardTable;
  final public  PlayerRegistry<L,P,G> playerRegistry;
  final public G gameLogic;
  final public WordContainer<AbsolutLayout<Word>>  textLayer;
  final public ThingContainer<AbsolutLayout<Thing>>  thingLayer;


  @Inject
  public CardGame(ActionManager actionManager, CardTable<L,P,G> cardTable,
      PlayerRegistry<L,P,G> playerRegistry, G gamelogic) {
    super(UPDATE_RATE);
    this.actionManager = actionManager;
    this.cardTable = cardTable;
    this.playerRegistry = playerRegistry;
    this.gameLogic = gamelogic;
    this.textLayer = new WordContainer<AbsolutLayout<Word>>("WordContainer",new AbsolutLayout<Word>());
    this.thingLayer = new ThingContainer<AbsolutLayout<Thing>>("ThingContainer",new AbsolutLayout<Thing>());
    
  }

  // for other platforms which are already not supported
  public CardGame() {
    this(null, null, null, null);
  }

  public PlayerRegistry<L,P,G> playerRegistry() {
    return playerRegistry;
  }

  @Override
  public void init() {
    cardTable.init();
    textLayer.init();
    thingLayer.init();
    gameLogic.configure();
    GroupLayer rootLayer = graphics().rootLayer();
    rootLayer.add(cardTable.layer());
    rootLayer.add(textLayer.layer());
    rootLayer.add(thingLayer.layer());
  }

  @Override
  public void paint(float alpha) {
    _clock.paint(alpha);
    actionManager.paintActions(alpha);
    cardTable.paint(_clock);
  }

  @Override
  public void update(int delta) {
    _clock.update(delta);
    actionManager.executeActions();
    playerRegistry.update(delta,this);
    cardTable.update(delta,this);
  }

  

}
