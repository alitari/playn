package de.alexkrieg.cards.core;

import static playn.core.PlayN.graphics;
import playn.core.Game;
import playn.core.Layer;

public abstract class CardGame extends Game.Default {
	
	public static String logString(Layer l) {
			String str = "Layer: origin(x"+l.originX()+",y"+l.originY()+",), translation("+l.tx()+","+l.ty()+"), rotation:"+l.rotation()+" scale:("+l.scaleX()+","+l.scaleY()+")";
			return str;
	}


	protected CardTable cardTable;
	
	public static final int UPDATE_RATE = 25;
	
	 public CardGame() {
		    super(UPDATE_RATE);
		  }
	
	@Override
	public void init() {
		 cardTable = createCardTable();
		 cardTable.init();
		 graphics().rootLayer().add(cardTable.layer());
	}
	

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
