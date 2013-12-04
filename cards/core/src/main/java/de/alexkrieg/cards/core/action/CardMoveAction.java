package de.alexkrieg.cards.core.action;


import playn.core.Layer;
import pythagoras.f.Point;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class CardMoveAction implements Action {

	private final Card card;
	private final CardSlot destination;

	final float rotationtimes = 5;

	
	final Point originPoint;
	final float originRotation;
	final float originScale;

	final Point destPoint = new Point();
	
	final float destRotation;
	final float destScale;
	final  float dx;
	final float dy;
	final private float dRotation;
	final private float dScale;
	
	int paintTimes = 0;
	
	
	
	public CardMoveAction(Card card, CardSlot destination) {

		super();
		this.card = card;
		this.destination = destination;
		Layer cl = card.layer();
		Transform t = cl.transform();
		originRotation = t.rotation();
		originScale = t.uniformScale();
		
		originPoint = new Point(t.tx(),t.ty());
		
		TiledCardsRotatedLayout layout = destination.layout();
		layout.recalc(card, null);
		Layer destLayer = destination.layer();
		Point destScreen = Layer.Util.layerToScreen(destLayer, layout.x(card), layout.y(card));
		Layer.Util.screenToLayer(card.getContainer().layer(), destScreen,destPoint);
		
		destRotation = destLayer.rotation()+ layout.rot(card);
		destScale = destLayer.transform().uniformScale();
		
		dx = destPoint.x - originPoint.x;
		dy = destPoint.y- originPoint.y;
		
		dRotation = destRotation -  originRotation;
		dScale = destScale - originScale;
		
//		log().info("ox,oy---destPoint:"+originX+","+originy+"---"+destPoint);
//		log().info("dx,dy:"+dx+","+dy);
	}

	@Override
	public void execute() {
		destination.put(card, null);

	}

	@Override
	public void paint(float alpha) {
		Layer layer = card.layer();
		Transform t = layer.transform();
		t.setTranslation(originPoint.x +dx*alpha, originPoint.y+ dy*alpha);
//		layer.setRotation((float)(2*Math.PI*alpha));
//		t.uniformScale(originScale +dScale*alpha);
//		paintTimes++;
//		log().info(paintTimes+": alpha,x,y:"+alpha+","+x+","+y);
	}

}
