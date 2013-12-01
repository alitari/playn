package de.alexkrieg.cards.core;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;
import playn.core.TextLayout;

public abstract class HUDSegment extends AbstractLayerEntity implements HasSizeEntity {

	
	private float width;
	private float height;

	public HUDSegment(float width, float height) {
		super();
		this.width = width;
		this.height = height;
	}

	
	@Override
	public HasSize hasSize() {
		return (HasSize) layer();
	}

	@Override
	protected Layer createLayer() {
		return createImageLayer();
	}


	protected abstract ImageLayer createImageLayer();
	
	protected ImageLayer createTextLayer(TextLayout layout, int color) {
		CanvasImage canvasImage = graphics().createImage(
				(int) Math.ceil(layout.width()),
				(int) Math.ceil(layout.height()));
		Canvas canvas = canvasImage.canvas();
		canvas.setFillColor(color);
		canvas.fillText(layout, 0, 0);
		return graphics().createImageLayer(canvasImage);
	}

	protected Layer createCanvasLayer(float width, float height,
			int strokeWith, int color, boolean border) {
		CanvasImage canvasImage = graphics().createImage(width, height);

		Canvas canvas = canvasImage.canvas();
		canvas.setStrokeWidth(strokeWith);
		canvas.setStrokeColor(color);
		canvas.strokeRect(1, 1, width - 1, height - 1);
		return graphics().createImageLayer(canvasImage);

	}

	protected ImageLayer createImageLayer(CanvasImage[] returnCanvas,float width, float height, int strokeWith,
			int strokeColor, int fillColor, boolean border) {
		CanvasImage canvasImage = graphics().createImage(width, height);

		Canvas canvas = canvasImage.canvas();
		canvas.setStrokeWidth(strokeWith);
		canvas.setStrokeColor(strokeColor);
		canvas.setFillColor(fillColor);
		if (border) {
			canvas.strokeRect(1, 1, width - 2, height - 2);
		}
		returnCanvas[0] = canvasImage;
		return graphics().createImageLayer(canvasImage);

	}

}
