package de.alexkrieg.cards.core.layout;

import java.util.List;

import de.alexkrieg.cards.core.Card;

public class TiledCardsRotatedLayout extends TiledCardsLayout {

	public TiledCardsRotatedLayout(float angle, int gap) {
		super(angle, gap);
	}

	@Override
	public void recalc(Card child, Object p) {
		super.recalc(child, p);
		List<?> childs = container.childs();
		int index = childs.contains(child) ? childs.indexOf(child): childs.size() - 1;
//		child.layer().setOrigin(container.width()/2, container.height());
		x = container.width()/2;
		rot = calcRotation(index);
		y = container.height();
		rot = calcRotation(index);
		scale = 1;
//		log().info("recalc: x="+x+",y="+y);
		

	}

	protected float calcRotation(int cardCount) {
		return (float) ((cardCount * 0.1 ) * Math.PI);

	}

}
