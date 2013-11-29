package de.alexkrieg.cards.core;


public class NESWLayout extends Layout<CardSlot > {

	public static enum NESW {
		N,E,S,W,C;
	}
	
	final float gap;

	public NESWLayout(float gap) {
		super();
		this.gap = gap;
	}

	@Override
	public void recalc(CardSlot child, Object p) {
		scale = 1;
		if ( p == null) {
			//TODO: find next free
		}
		
		
		NESW nesw = (NESW) p;
		switch (nesw) {
		case N:
			x = container.width / 2 ;
			y = child.height / 2+gap;
			rot =0;
			break;
		case E:
			x = container.width  - child.height / 2 -gap;
			y = container.height / 2;
			rot =  0.5f * (float)Math.PI;
			break;
			
		case S:
			x = container.width / 2;
			y = container.height -child.height/2-gap;
			rot =0;
			break;
		case W:
			x = child.height /2+gap;
			y = container.height/2;
			rot = 1.5f * (float)Math.PI;
			break;
		case C:
			x = container.width/2;
			y = container.height/2;
			rot =0;
			break;
		}
		
		

	}

}