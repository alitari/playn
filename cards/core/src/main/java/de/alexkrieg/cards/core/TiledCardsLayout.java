package de.alexkrieg.cards.core;

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
		int cardCount = container.groupLayer.size() - 1;
		x = cardCount * (child.layer.image().width() + gap);
		y = 0;
		rot = 0;
		scale = 1;

	}

}
