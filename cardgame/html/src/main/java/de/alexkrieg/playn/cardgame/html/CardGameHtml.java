package de.alexkrieg.playn.cardgame.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import de.alexkrieg.playn.cardgame.core.CardGame;
import de.alexkrieg.playn.cardgame.core.MyCardGame;

public class CardGameHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("cardgame/");
    PlayN.run(new MyCardGame());
  }
}
