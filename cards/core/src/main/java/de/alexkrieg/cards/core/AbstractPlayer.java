package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;
import de.alexkrieg.cards.core.action.Action;

public abstract class AbstractPlayer<G extends CardGame<?,?>> implements Player<G> {
  
  final protected String id;
  
  
  

  @Override
  public String id() {
    return id;
  }


  public AbstractPlayer(String name) {
    super();
    this.id = name;
  }


  protected void act(final G cardGame ,final Action action) {
    invokeLater(new Runnable() {
      @Override
      public void run() {
        cardGame.schedule(action);
      }
    });
  }

}
