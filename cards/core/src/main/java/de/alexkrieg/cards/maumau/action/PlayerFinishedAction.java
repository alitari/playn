package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.maumau.MaumauPlayer;

public class PlayerFinishedAction extends ActionAdapter {
  
  final MaumauPlayer player;

  public PlayerFinishedAction() {
    this(null);
  }
  
  public PlayerFinishedAction(MaumauPlayer player) {
    this.player = player;
  }

  public MaumauPlayer player() {
    return player;
  }
  
  

}
