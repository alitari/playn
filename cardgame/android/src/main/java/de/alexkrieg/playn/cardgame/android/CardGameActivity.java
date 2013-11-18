package de.alexkrieg.playn.cardgame.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import de.alexkrieg.playn.cardgame.core.CardGame;
import de.alexkrieg.playn.cardgame.core.MyCardGame;

public class CardGameActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new MyCardGame());
  }
}
