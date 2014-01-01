package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;

public class SystemReadyAction extends ActionAdapter {
  
  public SystemReadyAction() {
    this(null);
  }

  public SystemReadyAction(Player<?, ?, ?> player) {
    super(player);
  }

  @Override
  public int getDuration() {
    return 5;
  }
  

}
