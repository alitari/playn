package de.alexkrieg.cards.core.layout;

import java.util.List;

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
		List<?> childs = container.childs();
    int index = childs.contains(child) ? childs.indexOf(child): childs.size();
		x = index * (child.hasSize().width() + gap);
		y = 0;
		rot = 0;
		scale = 1;
	}

  @Override
  public boolean needsRecalcWhenRemove() {
    return true;
  }
	
	

}
