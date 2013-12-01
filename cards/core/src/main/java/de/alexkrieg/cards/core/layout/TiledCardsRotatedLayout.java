package de.alexkrieg.cards.core.layout;

import de.alexkrieg.cards.core.Card;

public class TiledCardsRotatedLayout extends TiledCardsLayout {


	public TiledCardsRotatedLayout(float angle, int gap) {
		super(angle,gap);
	}

	@Override
	public void recalc(Card child,Object p) {
		super.recalc(child, p);
		int cardCount = container.childs().size() - 1;
		rot = calcRotation(cardCount);
	}
	
	protected float calcRotation(int cardCount) {
		return (float) (((cardCount % 2) == 0 ? 0.01: -0.01) * Math.PI);
		
	}

}
