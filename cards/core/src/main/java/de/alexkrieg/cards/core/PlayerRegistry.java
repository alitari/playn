package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.layout.Layout;

public interface PlayerRegistry<L extends Layout<CardSlot<?>>, P extends Player<L, P, G>, G extends GameLogic<L, P, G>>
    extends Updateable<L, P, G> {

  void register(P player);

  int getIndexOfPlayer(P player);

  P getNextPlayerOf(P player);

  P getPreviousPlayerOf(P player);

  P getNeighbourPlayerOf(P player, boolean next);

  P getDealer();

}
