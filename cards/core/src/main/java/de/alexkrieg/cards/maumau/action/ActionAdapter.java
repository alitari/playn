package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.action.GameAction;

class ActionAdapter implements GameAction {
  
  final Player<?, ?, ?> player;
  

  public ActionAdapter(Player<?, ?, ?> player) {
    super();
    this.player = player;
  }

  @Override
  public Player<?, ?, ?> player() {
    return this.player;
  }

  @Override
  public void execute() {
    //log().info("execute "+this);
  }

  @Override
  public int getDuration() {
    return 0;
  }

  @Override
  public void paint(int tick, float alpha) {
  }
}
