package de.alexkrieg.cards.mygame;

import static playn.core.PlayN.graphics;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.GameHUD;
import de.alexkrieg.cards.core.HUDSegment;
import de.alexkrieg.cards.core.layout.AbsolutLayout;
import de.alexkrieg.cards.core.layout.AbsolutLayout.Attr;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class MyGameHUD extends GameHUD {

	public MyGameHUD(CardTable cardTable) {
		super(cardTable);
	}

	protected class Score extends HUDSegment {

		public Score(float width, float height) {
			super(width, height);
		}

		@Override
		protected ImageLayer createImageLayer() {
			
			CanvasImage[] canvasImage = new CanvasImage[1];
			ImageLayer imageLayer2 = createImageLayer(canvasImage, width(),
					height(), 1, 0xFF660000, 0xFF660000, true);
			Font font = graphics().createFont("Courier", Font.Style.PLAIN, 10);
			String text = "Score\nPlayer1";
			TextLayout layout = graphics().layoutText(text,
					new TextFormat().withFont(font).withWrapWidth(90));
			canvasImage[0].canvas().fillText(layout, 0, 0);
			return imageLayer2;
		}

	}

	public void init() {
		super.init();
//		
		put(new Score(100, 50), new AbsolutLayout.Attr(20, 20, 0, 1));

//		 Font font = graphics().createFont("Courier", Font.Style.PLAIN, 16);
//		 String text = "Text can also be wrapped at a specified width.\n\n" +
//		 "And wrapped manually at newlines.\nLike this.";
//		 TextLayout layout = graphics().layoutText(
//		 text, new TextFormat().withFont(font).withWrapWidth(200));
//		 Layer layer = createTextLayer(layout, 0xFF660000);
//		 layer.setTranslation(20, 20);
//		 graphics().rootLayer().add(layer);

	}
	
	protected Layer createTextLayer(TextLayout layout, int color) {
		CanvasImage canvasImage = graphics().createImage(
				(int) Math.ceil(layout.width()),
				(int) Math.ceil(layout.height()));
		Canvas canvas = canvasImage.canvas();
		canvas.setFillColor(color);
		canvas.fillText(layout, 0, 0);
		return graphics().createImageLayer(canvasImage);
	}

}
