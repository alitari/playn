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

public abstract class CardGame extends Game.Default {

	public boolean debug = true;

	public static String logString(LayerEntity le) {
		Layer l = le.layer();
		String str = "Layer: origin(x" + l.originX() + ",y" + l.originY()
				+ ",), translation(" + l.tx() + "," + l.ty() + "), rotation:"
				+ l.rotation() + " scale:(" + l.scaleX() + "," + l.scaleY()
				+ ")";
		return str;
	}

	protected ActionManager actionManager = new ActionManager(50);

	protected Action[] currentActions = new Action[5];

	protected CardTable<?> cardTable;
	protected GameHUD gameHUD;

	public static final int UPDATE_RATE = 50;

	public CardGame() {
		super(UPDATE_RATE);
	}

	@Override
	public void init() {
		cardTable = createCardTable();
		cardTable.init();
		gameHUD = createGameHUD();
		gameHUD.init();
		GroupLayer rootLayer = graphics().rootLayer();
		rootLayer.add(cardTable.layer());
		rootLayer.add(gameHUD.layer());
	}

	protected abstract GameHUD createGameHUD();

	protected abstract CardTable<?> createCardTable();

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
	}

	
}
