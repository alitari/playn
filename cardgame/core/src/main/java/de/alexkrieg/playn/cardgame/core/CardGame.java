package de.alexkrieg.playn.cardgame.core;

import static playn.core.PlayN.*;
import de.alexkrieg.playn.cardgame.core.CardTableAction.Move;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public abstract class CardGame extends Game.Default {


	protected CardTable cardTable;
	
	public static final int UPDATE_RATE = 25;
	
	 public CardGame() {
		    super(UPDATE_RATE);
		  }
	
	@Override
	public void init() {
		 cardTable = createCardTable();
		 cardTable.init();
		 graphics().rootLayer().add(cardTable.groupLayer);
	}
	

	protected abstract CardTable createCardTable();

	

	@Override
	public void paint(float alpha) {
		cardTable.paint(alpha);
		
		// the background automatically paints itself, so no need to do anything
		// here!
	}


		
		
}
