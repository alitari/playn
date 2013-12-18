package de.alexkrieg.cards.maumau;

import java.util.List;

import playn.core.ImageLayer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.GameLogic;
import de.alexkrieg.cards.core.PlayerRegistry;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class MaumauCardGame extends CardGame<NESWLayout, MaumauPlayer,MaumauGameLogic> {
  
  public static int PROP_DealedCardsCount = 6;

  ImageLayer clayer;

  int mouseCount = 0;


  public static final int UPDATE_RATE = 25;

  final CardSlot<TiledCardsRotatedLayout> slotPlayer1 = new CardSlot<TiledCardsRotatedLayout>(
      "Player1 ", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> slotPlayer2 = new CardSlot<TiledCardsRotatedLayout>(
      "Player2", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> slotPlayer3 = new CardSlot<TiledCardsRotatedLayout>(
      "Player3", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> slotPlayer4 = new CardSlot<TiledCardsRotatedLayout>(
      "Player4", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<StackLayout> talon = new CardSlot<StackLayout>("Talon", new StackLayout(2, 2));
  
  final CardSlot<StackLayout> playSlot = new CardSlot<StackLayout>("playSlot", new StackLayout(2, 2));


  @Override
  protected CardTable<?, NESWLayout> createCardTable() {
    MaumauCardtable table = new MaumauCardtable(gameLogic,actionManager, new NESWLayout(10));
    table.init();
    table.put(slotPlayer1, NESWLayout.NESW.N);
    table.put(slotPlayer2, NESWLayout.NESW.E);
    table.put(slotPlayer3, NESWLayout.NESW.S);
    table.put(slotPlayer4, NESWLayout.NESW.W);
    table.put(talon, NESWLayout.NESW.NE);
    table.put(playSlot, NESWLayout.NESW.C);
    
    return table;
  }
  
  

  @Override
  protected PlayerRegistry<MaumauPlayer> createPlayerRegistry() {
    PlayerRegistry<MaumauPlayer> playerRegistry2 = new PlayerRegistry<MaumauPlayer>();
    MaumauRobotPlayer myPlayer = new MaumauRobotPlayer("Player 1", gameLogic,actionManager , slotPlayer1);
    myPlayer.setDealer(true);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 1", gameLogic,actionManager , slotPlayer2);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 1", gameLogic,actionManager , slotPlayer3);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 1", gameLogic,actionManager , slotPlayer4);
    playerRegistry2.register(myPlayer);

    return playerRegistry2;
  }



  @Override
  public String toString() {
    return getClass().getSimpleName()+"( game="+gameLogic+")";
  }


  @Override
  protected MaumauGameLogic createGameLogic() {
    return new MaumauGameLogic();
  }
  
  

}
