package de.alexkrieg.cards.core.layout;

import de.alexkrieg.cards.core.Card;

public class TiledCardsRotatedLayout extends TiledCardsLayout {

	public TiledCardsRotatedLayout(float angle, int gap) {
		super(angle, gap);
	}

	@Override
	public void recalc(Card child, Object p) {
		super.recalc(child, p);
		int cardCount = container.childs().size() - 1;
		child.layer().setOrigin(container.width()/2, container.height());
		x = container.width()/2;
		rot = calcRotation(cardCount);
		y = container.height();
		rot = calcRotation(cardCount);
		scale = 1;

	}

	protected float calcRotation(int cardCount) {
		return (float) ((cardCount * 0.05 ) * Math.PI);

	}

}
