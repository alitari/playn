package de.alexkrieg.cards.core;

import static playn.core.PlayN.log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import de.alexkrieg.cards.core.action.GameAction;

public class DefaultActionManager implements ActionManager {

  final int capacity;
  
  private GameLogic gameLogic;

  public DefaultActionManager(int capacity) {
    this.capacity = capacity;
  }
  
  public DefaultActionManager() {
    this(50);
  }

  Map<Integer, LinkedList<GameAction>> actions = new HashMap<Integer, LinkedList<GameAction>>();

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
    try {
      gameLogic.executeAction(action);
    } catch (Exception e) {
      log().error("Exception during execution of  "+action,e);
    }
  }
  
  @Override
  public void setGameLogic(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
  }
  
}
