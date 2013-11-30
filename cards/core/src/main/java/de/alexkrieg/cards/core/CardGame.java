package de.alexkrieg.cards.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Pointer;


import org.omg.PortableServer.ImplicitActivationPolicyValue;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;
import playn.core.Layer;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;

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

	protected CardTable cardTable;
	protected GameHUD gameHUD;

	public static final int UPDATE_RATE = 25;

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
		
//		ImageLayer imageLayer = createImageLayer();
		//imageLayer.setTranslation(20, 20);
//		rootLayer.add(imageLayer);
//		ImageLayer il = createImageLayer();
//		rootLayer.add(il);
//
//		Font font = graphics().createFont("Courier", Font.Style.PLAIN, 16);
//		String text = "Text can also be wrapped at a specified width.\n\n"
//				+ "And wrapped manually at newlines.\nLike this.";
//		TextLayout layout = graphics().layoutText(text,
//				new TextFormat().withFont(font).withWrapWidth(200));
//		Layer layer = createTextLayer(layout, 0xFF660000);
//		layer.setTranslation(20, 20);
//		graphics().rootLayer().add(layer);

	}

//	protected Layer createTextLayer(TextLayout layout, int color) {
//		CanvasImage canvasImage = graphics().createImage(
//				(int) Math.ceil(layout.width()),
//				(int) Math.ceil(layout.height()));
//		Canvas canvas = canvasImage.canvas();
//		canvas.setFillColor(color);
//		canvas.fillText(layout, 0, 0);
//		return graphics().createImageLayer(canvasImage);
//	}
//
//	protected ImageLayer createImageLayer() {
//		CanvasImage[] canvasImage = new CanvasImage[1];
//		ImageLayer imageLayer2 = createImageLayer(canvasImage, 60, 60, 10,
//				0xFF660000, 0xFF660000, true);
//		Font font = graphics().createFont("Courier", Font.Style.PLAIN, 16);
//		String text = "Score\nPlayer1"
//				+ "And wrapped manually at newlines.\nLike this.";
//		TextLayout layout = graphics().layoutText(text,
//				new TextFormat().withFont(font).withWrapWidth(200));
//		canvasImage[0].canvas().fillText(layout, 0, 0);
//		return imageLayer2;
//	}
//
//	protected ImageLayer createImageLayer(CanvasImage[] returnCanvas,
//			float width, float height, int strokeWith, int strokeColor,
//			int fillColor, boolean border) {
//		CanvasImage canvasImage = graphics().createImage(width, height);
//
//		Canvas canvas = canvasImage.canvas();
//		canvas.setStrokeWidth(strokeWith);
//		canvas.setStrokeColor(strokeColor);
//		canvas.setFillColor(fillColor);
//		if (border) {
//			canvas.strokeRect(1, 1, width - 1, height - 1);
//		}
//		returnCanvas[0] = canvasImage;
//		return graphics().createImageLayer(canvasImage);
//
//	}

	protected abstract GameHUD createGameHUD();

	protected abstract CardTable createCardTable();

	@Override
	public void paint(float alpha) {
		cardTable.paint(alpha);

		// the background automatically paints itself, so no need to do anything
		// here!
	}

	@Override
	public void update(int delta) {
		cardTable.update(delta);
	}

}
