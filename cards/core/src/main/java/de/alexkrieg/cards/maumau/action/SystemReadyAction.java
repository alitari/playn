package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;

public class SystemReadyAction extends ActionAdapter {
  
  public SystemReadyAction() {
    this(null,0);
  }

  public SystemReadyAction(Player<?, ?, ?> player, int duration) {
    super(player,duration);
  }

  @Override
  public int getDuration() {
    return 5;
  }
  

}
