package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;

public class TalonFilledAction extends ActionAdapter {
  
  public TalonFilledAction() {
    this(null,0);
  }

  public TalonFilledAction(Player<?, ?, ?> player,int duration) {
    super(player,duration);
  }
  
  

}
