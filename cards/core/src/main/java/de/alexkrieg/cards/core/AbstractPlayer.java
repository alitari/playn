package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;
import de.alexkrieg.cards.core.action.GameAction;

public abstract class AbstractPlayer<L extends GameLogic> implements Player {
  
  final protected String id;
  final protected  L gameLogic;
  final protected   ActionManager actionManager;
  

  @Override
  public String id() {
    return id;
  }


  public AbstractPlayer(String name, L gameLogic ,ActionManager actionManager) {
    super();
    this.id = name;
    this.gameLogic = gameLogic;
    this.actionManager = actionManager;
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
    return getClass().getSimpleName()+"(id="+id+")";
  }
  
  

}
