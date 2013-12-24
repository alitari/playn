package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.layout.Layout;


public interface Player<L extends Layout<CardSlot<?>>, P extends Player<L,P,G>, G extends GameLogic<L, P,G>> extends Updateable<L,P,G>   {
  
  String id();

  boolean isDealer();
  
}
