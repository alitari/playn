package de.alexkrieg.cards.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.util.Clock;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class CardGame<L extends Layout<CardSlot<?>>, P extends Player> extends
    Game.Default {

  private static final Font DEBUG_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 11);
  private final TextFormat debugTextFormat = new TextFormat().withFont(DEBUG_FONT).withWrapWidth(
      graphics().width());

  public boolean debug = true;
  private boolean debugSnapShot = true;
  private final static Key DEBUGKEY = Key.D;

  protected ActionManager actionManager = new ActionManager(50);

  protected CardTable<?, L> cardTable;
  protected PlayerRegistry<P> playerRegistry;

  private Layer debugLayer;

  public static final int UPDATE_RATE = 50;

  public CardGame() {
    super(UPDATE_RATE);
  }
  
  
  public PlayerRegistry<P> playerRegistry() {
    return playerRegistry;
  }

  @Override
  public void init() {
    cardTable = createCardTable();
    playerRegistry = createPlayerRegistry();
    GroupLayer rootLayer = graphics().rootLayer();
    rootLayer.add(cardTable.layer());
    if (debug) {
      keyboard().setListener(new Keyboard.Adapter() {

        @Override
        public void onKeyDown(Event event) {
          super.onKeyDown(event);
          debugSnapShot = event.key().equals(DEBUGKEY);
        }

        @Override
        public void onKeyUp(Event event) {
          super.onKeyUp(event);
          if (event.key().equals(DEBUGKEY)) {
            debugSnapShot = false;
          }
        }

      });

      debugLayer = createDebugLayer();
      rootLayer.add(debugLayer);
    }

  }

  private Layer createDebugLayer() {
    GroupLayer debugGroupLayer = graphics().createGroupLayer();
    CanvasImage debugCanvasImage = graphics().createImage((int) Math.ceil(graphics().width()),
        (int) Math.ceil(graphics().height()));
    debugCanvas = debugCanvasImage.canvas();
    debugCanvas.setFillColor(0xFF660000);
    debugCanvas.setStrokeColor(0xFF660000); //
    // debugCanvasImage.canvas().fillText(debugTextLayout, 0, 0);
    ImageLayer imageLayer = graphics().createImageLayer(debugCanvasImage);
    debugGroupLayer.add(imageLayer);

    return debugGroupLayer;

  }

  protected String debugUpdate(int delta) {
    StringBuffer str = new StringBuffer();
    str.append(LogUtil.logString(this));
    return str.toString();
  }

  private TextLayout createDebugTextLayout(String text, TextFormat textFormat) {
    return graphics().layoutText(text, textFormat);
  }

  protected abstract PlayerRegistry<P> createPlayerRegistry();

  protected abstract CardTable<?, L> createCardTable();

  public void schedule(GameAction action) {
    actionManager.schedule(action);
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
    playerRegistry.updatePlayers();
    cardTable.update(delta);
    if (debug && isDebugSnapShot()) {
      updateDebug(delta);
    }

  }

  private void updateDebug(int delta) {
    String debugText = debugUpdate(delta);
    debugCanvas.clear();
    debugCanvas.fillText(createDebugTextLayout(debugText, createDebugTextFormat()), 0, 0);
    setDebugSnapShot(false);
  }

  protected TextFormat createDebugTextFormat() {
    return debugTextFormat;
  }

  public boolean isDebugSnapShot() {
    return debugSnapShot;
  }

  public void setDebugSnapShot(boolean debugSnapShot) {
    this.debugSnapShot = debugSnapShot;
  }

  protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);
  private Canvas debugCanvas;

}
