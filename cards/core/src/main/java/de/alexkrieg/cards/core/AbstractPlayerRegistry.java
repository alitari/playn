package de.alexkrieg.cards.core;

import java.util.ArrayList;
import java.util.List;

import de.alexkrieg.cards.core.layout.Layout;

public abstract class AbstractPlayerRegistry<L extends Layout<CardSlot<?>>, P extends Player<L,P,G>, G extends GameLogic<L,P,G>> implements PlayerRegistry<L,P,G>{
  protected final List<P> players = new ArrayList<P>();
  

  public AbstractPlayerRegistry() {
    super();
  }

  @Override
  public void register(P player) {
    players.add(player);
  }

  @Override
  public int getIndexOfPlayer(P player) {
    return players.indexOf(player);
  }
  
  @Override
  public P getNextPlayerOf(P player) {
    return getNeighbourPlayerOf(player, true);
  }
  
  @Override
  public P getPreviousPlayerOf(P player) {
    return getNeighbourPlayerOf(player, false);
  }
  
  @Override
  public P getNeighbourPlayerOf(P player, boolean next) {
    int current = players.indexOf(player);
    int neighbourIndex =  next ? ( current == players.size()-1 ?  0: current+1) : ( current== 0 ? players.size()-1: current-1);
    return players.get(neighbourIndex);
  }
  
  
  @Override
  public P getDealer() {
    for ( P p:players) {
      if ( p.isDealer()) return p;
    }
    return null;
  }
  
  

}
