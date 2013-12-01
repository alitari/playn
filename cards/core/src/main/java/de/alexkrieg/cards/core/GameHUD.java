package de.alexkrieg.cards.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.List;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import de.alexkrieg.cards.core.layout.AbsolutLayout;


public abstract class GameHUD extends AbstractLayerEntityContainer<HUDSegment,AbsolutLayout> {
	
	final protected CardTable cardTable;

	public GameHUD(CardTable cardTable) {
		super();
		this.cardTable = cardTable;
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	protected void fillWithLayers(List<Layer> layers) {
		layers.add(createImageLayer());

	}

	protected ImageLayer createImageLayer() {
		CanvasImage canvasImage = graphics().createImage( cardTable.width(), cardTable.height());
		return graphics().createImageLayer(canvasImage);
	}

	@Override
	protected AbsolutLayout createLayout() {
		return new AbsolutLayout();
	} 



}
