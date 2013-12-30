package de.alexkrieg.cards.maumau.action;

import static playn.core.PlayN.log;
import de.alexkrieg.cards.maumau.MaumauCardtable;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class PlayerFinishedAction extends ActionAdapter {
  
  final MaumauRobotPlayer player;
  final MaumauCardtable cardTable;

  public PlayerFinishedAction() {
    this(null,null);
  }
  
  public PlayerFinishedAction(MaumauRobotPlayer player, MaumauCardtable cardTable) {
    this.player = player;
    this.cardTable = cardTable;
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
