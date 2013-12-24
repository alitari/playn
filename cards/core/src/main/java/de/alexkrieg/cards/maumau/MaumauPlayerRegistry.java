package de.alexkrieg.cards.maumau;

import javax.inject.Inject;

import playn.core.util.Clock.Source;
import de.alexkrieg.cards.core.AbstractPlayerRegistry;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.layout.NESWLayout;

public class MaumauPlayerRegistry extends AbstractPlayerRegistry<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> {
  
  public static final String ID_PLAYER1 = "Player 1";
  public static final String ID_PLAYER2 = "Player 2";
  public static final String ID_PLAYER3 = "Player 3";
  public static final String ID_PLAYER4 = "Player 4";
  
  @Inject
  public MaumauPlayerRegistry() {
    super();

    MaumauRobotPlayer myPlayer = new MaumauRobotPlayer(ID_PLAYER1);
    myPlayer.setDealer(true);
    register(myPlayer);

    myPlayer = new MaumauRobotPlayer(ID_PLAYER2);
    register(myPlayer);

    myPlayer = new MaumauRobotPlayer(ID_PLAYER3);
    register(myPlayer);

    myPlayer = new MaumauRobotPlayer(ID_PLAYER4);
    register(myPlayer);

  }

  @Override
  public void update(int delta, CardGame<NESWLayout, MaumauRobotPlayer, MaumauGameLogic> game) {
    for (MaumauRobotPlayer p : players) {
      p.update(delta,game);
    }
  }

  @Override
  public void paint(Source _clock) {
    for (MaumauRobotPlayer p : players) {
      p.paint(_clock);
    }
    
  }
  

}
