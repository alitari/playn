package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;
import de.alexkrieg.cards.core.action.Action;

public abstract class AbstractPlayer<G extends CardGame<?,?>> implements Player {
  
  final protected String id;
  
  protected final G game;
  

  @Override
  public String id() {
    return id;
  }


  public AbstractPlayer(String name, G game) {
    super();
    this.id = name;
    this.game = game;
  }


  protected void shedule(final Action action) {
    invokeLater(new Runnable() {
      @Override
      public void run() {
        game.schedule(action);
      }
    });
  }


  


  @Override
  public String toString() {
    
    return getClass().getSimpleName()+"(id="+id+")";
  }
  
  

}
