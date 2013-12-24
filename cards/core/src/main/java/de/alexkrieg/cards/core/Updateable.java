package de.alexkrieg.cards.core;

import playn.core.util.Clock;
import de.alexkrieg.cards.core.layout.Layout;

public interface Updateable<L extends Layout<CardSlot<?>>, P extends Player<L,P,G>, G extends GameLogic<L, P,G>> {
  
  void update(int delta, CardGame<L, P, G> gameLogic);
  
  void paint(Clock.Source _clock);
  

}
