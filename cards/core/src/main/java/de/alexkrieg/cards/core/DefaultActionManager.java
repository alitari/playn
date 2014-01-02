package de.alexkrieg.cards.core;

import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.util.Filter;
import static de.alexkrieg.cards.core.util.Filter.*;

public class DefaultActionManager<L extends Layout<CardSlot<?>>, P extends Player<L, P, G>, G extends GameLogic<L, P, G>>
    implements ActionManager {

  final int capacity;

  final private GameLogic<L, P, G> gameLogic;

  @Inject
  public DefaultActionManager(GameLogic<L, P, G> gameLogic) {
    this.capacity = 51;
    this.gameLogic = gameLogic;
  }

  Map<Integer, LinkedList<GameAction>> actions = new HashMap<Integer, LinkedList<GameAction>>();

  public List<GameAction> allScheduled() {
    List<GameAction> result = new ArrayList<GameAction>();
    for (List<GameAction> al : actions.values()) {
      if (al != null) {
        result.addAll(al);
      }
    }
    return result;
  }

  @Override
  public List<GameAction> findScheduled(Filter<GameAction> filter) {
    return applyFilter(filter, allScheduled(), new ArrayList<GameAction>());
  }

  @Override
  public void schedule(GameAction action) {
    int duration = action.getDuration();
    LinkedList<GameAction> linkedList = actions.get(duration);
    if (linkedList == null) {
      linkedList = new LinkedList<GameAction>();
      actions.put(duration, linkedList);
    }
    linkedList.add(action);
  }

  @Override
  public void executeActions() {
    LinkedList<GameAction> actionsToExecute = actions.get(0);
    if (actionsToExecute != null) {
      while (!actionsToExecute.isEmpty()) {
        execute(actionsToExecute.removeFirst());
      }
    }

    for (int i = 0; i < capacity; i++) {
      LinkedList<GameAction> listToDown = actions.get(i + 1);
      actions.put(i, listToDown);
    }
  }

  @Override
  public void paintActions(float alpha) {
    Iterator<Entry<Integer, LinkedList<GameAction>>> all = actions.entrySet().iterator();

    while (all.hasNext()) {
      Entry<Integer, LinkedList<GameAction>> nextEntry = all.next();
      if (nextEntry.getValue() != null) {
        for (GameAction action : nextEntry.getValue()) {
          action.paint(action.getDuration() - nextEntry.getKey(), alpha);
        }
      }
    }
  }

  private void execute(GameAction action) {
    if (action instanceof GameLogicAction) {
      try {
        gameLogic.executeAction((GameLogicAction) action);
      } catch (Exception e) {
        log().error("Exception during execution of  " + action, e);
      }
    } else {
      action.execute();
    }
  }

}
