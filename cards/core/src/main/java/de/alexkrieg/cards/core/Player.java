package de.alexkrieg.cards.core;

public interface Player<G extends CardGame<?,?>> {
  
  String id();

  void update(G cardGame);

}
