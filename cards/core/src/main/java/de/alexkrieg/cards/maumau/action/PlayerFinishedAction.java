package de.alexkrieg.cards.maumau.action;

import static playn.core.PlayN.log;
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

  @Override
  public void execute() {
    log().info("executing..."+this);
    super.execute();
  }
  
  
  
  

}
