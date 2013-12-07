package de.alexkrieg.cards.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import de.alexkrieg.cards.core.action.Action;

class ActionManager {

  final int capacity;

  public ActionManager(int capacity) {
    this.capacity = capacity;
  }

  Map<Integer, LinkedList<Action>> actions = new HashMap<Integer, LinkedList<Action>>();

  void schedule(Action action) {
    int duration = action.getDuration();
    LinkedList<Action> linkedList = actions.get(duration);
    if (linkedList == null) {
      linkedList = new LinkedList<Action>();
      actions.put(duration, linkedList);
    }
    linkedList.add(action);
  }

  void executeActions() {
    LinkedList<Action> actionsToExecute = actions.get(0);
    if (actionsToExecute != null) {
      while (!actionsToExecute.isEmpty()) {
        actionsToExecute.removeFirst().execute();
      }
    }

    for (int i = 0; i < capacity; i++) {
      LinkedList<Action> listToDown = actions.get(i + 1);
      actions.put(i, listToDown);
    }
  }

  void paintActions(float alpha) {
    Iterator<Entry<Integer, LinkedList<Action>>> all = actions.entrySet().iterator();

    while (all.hasNext()) {
      Entry<Integer, LinkedList<Action>> nextEntry = all.next();
      if (nextEntry.getValue() != null) {
        for (Action action : nextEntry.getValue()) {
          action.paint(action.getDuration() - nextEntry.getKey(), alpha);
        }
      }
    }
  }
}
