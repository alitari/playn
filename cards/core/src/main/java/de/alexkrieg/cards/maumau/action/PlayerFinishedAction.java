package de.alexkrieg.cards.maumau.action;

import static playn.core.PlayN.log;
import de.alexkrieg.cards.maumau.MaumauCardtable;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class PlayerFinishedAction extends ActionAdapter {
  
  final MaumauCardtable cardTable;

  public PlayerFinishedAction() {
    this(null,null);
  }
  
  public PlayerFinishedAction(MaumauRobotPlayer player, MaumauCardtable cardTable) {
    super(player);
    this.cardTable = cardTable;
  }

  public MaumauRobotPlayer player() {
    return (MaumauRobotPlayer) player;
  }

  @Override
  public void execute() {
    log().info("executing..."+this);
    super.execute();
  }
  
  
  
  

}
