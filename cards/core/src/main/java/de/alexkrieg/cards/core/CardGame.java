package de.alexkrieg.cards.core;

import static playn.core.PlayN.graphics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Layer;
import de.alexkrieg.cards.core.action.Action;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class CardGame<L extends Layout<CardSlot<?>>, P extends Player> extends
    Game.Default {

  public boolean debug = true;

  public static String logString(LayerEntity le) {
    Layer l = le.layer();
    String str = "Layer: origin(x" + l.originX() + ",y" + l.originY() + ",), translation(" + l.tx()
        + "," + l.ty() + "), rotation:" + l.rotation() + " scale:(" + l.scaleX() + "," + l.scaleY()
        + ")";
    return str;
  }

  protected ActionManager actionManager = new ActionManager(50);

  protected CardTable<L> cardTable;
  protected GameHUD gameHUD;
  protected PlayerRegistry<P> playerRegistry;

  public static final int UPDATE_RATE = 50;

  public class PlayerRegistry<P extends Player> {
    private final Map<String, P> players = new HashMap<String, P>();

    public void register(P player) {
      players.put(player.id(), player);
    }

    public P getPlayer(String id) {
      return players.get(id);
    }

    public void updatePlayers() {
      for (Player p : players.values()) {
        p.update(CardGame.this);
      }
    }

  }

  public CardGame() {
    super(UPDATE_RATE);
  }

  @Override
  public void init() {
    cardTable = createCardTable();
    gameHUD = createGameHUD();
    playerRegistry = createPlayerRegistry();

    GroupLayer rootLayer = graphics().rootLayer();
    rootLayer.add(cardTable.layer());
    rootLayer.add(gameHUD.layer());
  }

  protected abstract PlayerRegistry<P> createPlayerRegistry();


  protected abstract GameHUD createGameHUD();

  protected abstract CardTable<L> createCardTable();

  public void schedule(Action action) {
    actionManager.schedule(action);
  }

  @Override
  public void paint(float alpha) {
    actionManager.paintActions(alpha);
  }

  @Override
  public void update(int delta) {
    actionManager.executeActions();
    playerRegistry.updatePlayers();
  }

}
