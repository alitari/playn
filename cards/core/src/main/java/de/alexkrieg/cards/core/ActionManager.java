package de.alexkrieg.cards.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.alexkrieg.cards.core.action.GameAction;

public interface ActionManager {

  void schedule(GameAction action);

  void executeActions();

  void paintActions(float alpha);

  void setGameLogic(GameLogic gameLogic);

  public static class Util {
    public static List<Class<?>> allActionsClasses(String pckgname) {

      String path = new String(pckgname);
      if (!path.startsWith("/")) {
        path = "/" + path;
      }
      path = path.replace('.', '/');

      List<Class<?>> actionClasses = new ArrayList<Class<?>>();
      URL url = GameAction.class.getResource(path);
      File directory = new File(url.getFile());

      if (directory.exists()) {
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
          if (files[i].endsWith("Action.class")) {
            String classname = files[i].substring(0, files[i].length() - 6);
            try {
              Class<?> actionClass = Class.forName(pckgname + "." + classname);
              actionClasses.add(actionClass);
            } catch (ClassNotFoundException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }
      return actionClasses;
    }
  }

}
