package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class AbstractPlayer<G extends GameLogic, L extends Layout<CardSlot<?>>> implements
    Player {

  final protected String id;
  final protected G gameLogic;
  final protected ActionManager actionManager;
  final protected CardTable<G, L> cardTable;

  @Override
  public String id() {
    return id;
  }

  public AbstractPlayer(String name, G gameLogic, ActionManager actionManager,
      CardTable<G, L> cardTable) {
    super();
    this.id = name;
    this.gameLogic = gameLogic;
    this.actionManager = actionManager;
    this.cardTable = cardTable;
  }

  protected void shedule(final GameAction action) {
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
