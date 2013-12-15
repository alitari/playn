package de.alexkrieg.cards.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import de.alexkrieg.cards.core.action.GameAction;

class ActionManager {

  final int capacity;

  public ActionManager(int capacity) {
    this.capacity = capacity;
  }

  Map<Integer, LinkedList<GameAction>> actions = new HashMap<Integer, LinkedList<GameAction>>();

  void schedule(GameAction action) {
    int duration = action.getDuration();
    LinkedList<GameAction> linkedList = actions.get(duration);
    if (linkedList == null) {
      linkedList = new LinkedList<GameAction>();
      actions.put(duration, linkedList);
    }
    linkedList.add(action);
  }

  void executeActions() {
    LinkedList<GameAction> actionsToExecute = actions.get(0);
    if (actionsToExecute != null) {
      while (!actionsToExecute.isEmpty()) {
        actionsToExecute.removeFirst().execute();
      }
    }

    for (int i = 0; i < capacity; i++) {
      LinkedList<GameAction> listToDown = actions.get(i + 1);
      actions.put(i, listToDown);
    }
  }

  void paintActions(float alpha) {
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
}
