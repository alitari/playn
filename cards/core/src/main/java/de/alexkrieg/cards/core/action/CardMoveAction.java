package de.alexkrieg.cards.core.action;


import static playn.core.PlayN.*;
import playn.core.Layer;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class CardMoveAction implements Action {

	private final Card card;
	private final CardSlot destination;

	final float rotationtimes = 5;

	
	final float originX;
	final float originy;
	final float originRotation;
	final float originScale;

	final float destX;
	final float desty;
	final float destRotation;
	final float destScale;
	final  float dx;
	final float dy;
	
	int paintTimes = 0;
	
	
	
	public CardMoveAction(Card card, CardSlot destination) {

		super();
		this.card = card;
		this.destination = destination;
		Layer cl = card.layer();
		Transform t = cl.transform();
		originRotation = t.rotation();
		originScale = t.uniformScale();
		
		originX = card.getContainer().layer().tx()+ t.tx();
		originy = card.getContainer().layer().ty()+t.ty();
		
		
		TiledCardsRotatedLayout layout = destination.layout();
		layout.recalc(card, null);
		
		destX = destination.layer().tx()+ layout.x(card);
		desty = destination.layer().ty()+layout.y(card);
		destRotation = layout.rot(card);
		destScale = layout.scale(card);
		
		dx = destX- originX;
		dy = desty- originy;
		log().info("ox,oy---destx,desty:"+originX+","+originy+"---"+destX+","+desty);
		log().info("dx,dy:"+dx+","+dy);
	}

	@Override
	public void execute() {
		destination.put(card, null);

	}

	@Override
	public void paint(float alpha) {
		Transform t = card.layer().transform();
		float x = originX +dx*alpha;
		float y = originy+ dy*alpha;
		t.setTx( x);
		t.setTy( y);
		paintTimes++;
		log().info(paintTimes+": alpha,x,y:"+alpha+","+x+","+y);
	}

}
