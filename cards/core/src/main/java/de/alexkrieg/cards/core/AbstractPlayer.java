package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;

import java.util.List;

import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.util.Filter;

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

  protected void sheduleOnce(final ActionManager actionManager, final GameLogicAction action) {
    if ( findMyScheduledFromType(actionManager, action.getClass()).isEmpty()) {
      shedule(actionManager, action);
    }
  }
  
  protected void sheduleOnce(final ActionManager actionManager, final GameAction action) {
    if ( findScheduledFromType(actionManager, action.getClass()).isEmpty()) {
      shedule(actionManager, action);
    }
  }
  
  
  protected void shedule(final ActionManager actionManager, final GameAction action) {
    invokeLater(new Runnable() {
      @Override
      public void run() {
        actionManager.schedule(action);
      }
    });
  }
  
  protected List<GameAction> findMyScheduledFromType(ActionManager actionManager,Class<?> type) {
    return actionManager.findScheduled(new Filter.And<GameAction>(new GameAction.TypeFilter(type), new GameLogicAction.PlayerFilter(this), true));
  }
  
  protected List<GameAction> findScheduledFromType(ActionManager actionManager,Class<?> type) {
    return actionManager.findScheduled(new GameLogicAction.TypeFilter(type));
  }
  
  protected boolean noActionScheduled(ActionManager actionManager,Class<?> type) {
    return findScheduledFromType(actionManager, type).isEmpty();
  }
  
  
  
  

  

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(id=" + id + ")";
  }

}
