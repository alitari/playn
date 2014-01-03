package de.alexkrieg.cards.core;

import static playn.core.PlayN.invokeLater;

import java.util.List;

import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.GameAction.TypeFilter;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.util.Filter;
import de.alexkrieg.cards.core.util.Filter.And;

public abstract class AbstractPlayer<L extends Layout<CardSlot<?>>, P extends Player<L, P, G>, G extends GameLogic<L, P, G>>
    implements Player<L, P, G> {

  public final String id;

  @Override
  public String id() {
    return id;
  }

  public AbstractPlayer(String id) {
    super();
    this.id = id;
  }

  protected void sheduleOnce(final ActionManager actionManager, final GameLogicAction<?> action) {
    sheduleOnce(-1, actionManager, action);
  }

  protected void sheduleOnce(int millis, final ActionManager actionManager,
      final GameLogicAction<?> action) {
    if (findMyScheduledFromType(actionManager, action.getClass()).isEmpty()) {
      shedule(millis, actionManager, action);
    }
  }

  protected void sheduleOnce(final ActionManager actionManager, final GameAction<?> action) {
    sheduleOnce(-1, actionManager, action);
  }

  protected void sheduleOnce(int millis, final ActionManager actionManager, final GameAction<?> action) {
    if (findScheduledFromType(actionManager, action.getClass()).isEmpty()) {
      shedule(millis, actionManager, action);
    }
  }

  protected void shedule(final ActionManager actionManager, final GameAction<?> action) {
    shedule(-1, actionManager, action);
  }

  protected void shedule(final int millis, final ActionManager actionManager,
      final GameAction<?> action) {
    invokeLater(new Runnable() {
      @Override
      public void run() {
        if (millis == -1) {
          actionManager.schedule(action);
        } else {
          actionManager.scheduleFuture(millis, action);
        }
      }
    });
  }

  protected List<GameAction<?>> findMyScheduledFromType(ActionManager actionManager, Class<?> type) {
    Filter<GameAction<?>> filter = new Filter.And<GameAction<?>>(new GameAction.TypeFilter(type),
        new GameLogicAction.PlayerFilter(this), true);
    List<GameAction<?>> findall = actionManager.findScheduled(filter);
    findall.addAll(actionManager.findOnWait(filter));
    return findall;
  }

  protected List<GameAction<?>> findScheduledFromType(ActionManager actionManager, Class<?> type) {
    Filter<GameAction<?>> filter = new GameLogicAction.TypeFilter(type);
    List<GameAction<?>> findall = actionManager.findScheduled(filter);
    findall.addAll(actionManager.findOnWait(filter));
    return findall;
  }

  protected boolean noActionScheduled(ActionManager actionManager, Class<?> type) {
    return findScheduledFromType(actionManager, type).isEmpty();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(id=" + id + ")";
  }

}
