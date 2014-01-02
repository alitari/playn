package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.GameLogicAction;

@Deprecated
class ActionAdapter implements GameLogicAction {
  
  final Player<?, ?, ?> player;
  final int duration; 
  

  public ActionAdapter(Player<?, ?, ?> player, int duration) {
    super();
    this.player = player;
    this.duration = duration;
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
    return duration;
  }

  @Override
  public void paint(int tick, float alpha) {
  }

  @Override
  public GameAction with(Animation... animations) {
    return this;
    
  }
}
