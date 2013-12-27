package de.alexkrieg.cards.core.layout;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.alexkrieg.cards.core.Card;

public class HeapLayout extends Layout<Card> {

  final private int areaWidth;
  final private int areaHeight;
  
  Random rand = new Random();
  
  Set<Card> remember = new HashSet<Card>();

  public HeapLayout(int width, int height) {
    super();
    this.areaWidth = width;
    this.areaHeight = height;
  }

  @Override
  public void recalc(Card child, Object p) {
    if ( remember.contains(child)) return;
    remember.add(child);
    x = rand.nextInt(areaWidth);
    y = rand.nextInt(areaHeight);
    scale = 1+ ( container.childs().size()*(float)0.01);
    
    rot = child.getContainer().layer().transform().rotation()+child.layer().transform().rotation();
  }
  
  public void reset() {
    remember.clear();
  }

  
  

}
