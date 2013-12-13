package de.alexkrieg.cards.core.layout;

import de.alexkrieg.cards.core.Card;

public class StackLayout extends Layout<Card> {

  private int xOffset;
  private int yOffset;

  public StackLayout(int xOffset, int yOffset) {
    super();
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  @Override
  public void recalc(Card child, Object p) {
    int cardCount = container.childs().size() - 1;
    x = cardCount * xOffset;
    y = cardCount * yOffset;
    rot = 0;
    scale = 1;

  }

}
