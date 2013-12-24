package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class PlayerFinishedAction extends ActionAdapter {
  
  final MaumauRobotPlayer player;

  public PlayerFinishedAction() {
    this(null);
  }
  
  public PlayerFinishedAction(MaumauRobotPlayer player) {
    this.player = player;
  }

  public MaumauRobotPlayer player() {
    return player;
  }
  
  

}
