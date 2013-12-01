package de.alexkrieg.cards.core.layout;

import de.alexkrieg.cards.core.Card;

public class TiledCardsLayout extends Layout<Card> {

	final float angle;
	final int gap;

	public TiledCardsLayout(float angle, int gap) {
		super();
		this.angle = angle;
		this.gap = gap;
	}

	@Override
	public void recalc(Card child,Object p) {
		int cardCount = container.childs().size() - 1;
		x = cardCount * (child.width() + gap);
		y = 0;
		rot = 0;
		scale = 1;

	}

}