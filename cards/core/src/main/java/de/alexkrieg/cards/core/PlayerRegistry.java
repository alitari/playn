package de.alexkrieg.cards.core;

import java.util.ArrayList;
import java.util.List;

public class PlayerRegistry< P extends Player> {
  private final List<P> players = new ArrayList<P>();
  
  

  public void register(P player) {
    players.add(player);
  }

  public int getIndexOfPlayer(P player) {
    return players.indexOf(player);
  }
  
  public P getNextPlayerOf(P player) {
    return getNeighbourPlayerOf(player, true);
  }
  
  public P getPreviousPlayerOf(P player) {
    return getNeighbourPlayerOf(player, false);
  }
  
  private P getNeighbourPlayerOf(P player, boolean next) {
    int current = players.indexOf(player);
    int neighbourIndex =  next ? ( current == players.size()-1 ?  0: current+1) : ( current== 0 ? players.size()-1: current-1);
    return players.get(neighbourIndex);
  }
  
  public void updatePlayers() {
    for (P p : players) {
      p.update();
    }
  }

}
