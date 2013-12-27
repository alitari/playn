package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.action.GameAction;

class ActionAdapter implements GameAction {

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
