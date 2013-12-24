package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class AbstractPlayer<L extends Layout<CardSlot<?>>, P extends Player<L,P,G>, G extends GameLogic<L,P,G>>implements
    Player<L,P,G> {

  public final String id;

  @Override
  public String id() {
    return id;
  }

  public AbstractPlayer(String id) {
    super();
    this.id = id;
  }

  protected void shedule(final ActionManager actionManager, final GameAction action) {
    invokeLater(new Runnable() {
      @Override
      public void run() {
        actionManager.schedule(action);
      }
    });
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(id=" + id + ")";
  }

}
