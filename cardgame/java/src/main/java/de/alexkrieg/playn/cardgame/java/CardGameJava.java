package de.alexkrieg.playn.cardgame.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;
import de.alexkrieg.playn.cardgame.core.MyCardGame;

public class CardGameJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    PlayN.run(new MyCardGame());
  }
}
