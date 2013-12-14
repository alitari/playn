package de.alexkrieg.cards.maumau;

import java.util.List;

import playn.core.ImageLayer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.PlayerRegistry;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class MaumauCardGame extends CardGame<NESWLayout, MaumauPlayer> {
  
  public static int PROP_DealedCardsCount = 6;

  ImageLayer clayer;

  int mouseCount = 0;

  protected CardSlot<?> slot;

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

  public static class State {
    static enum Direction {
      Clock, AgainstClock;
    }
    
    static enum Mode {
      Attract, Dealing, Playn, Refill;
    }
    
    Mode mode = Mode.Attract;
    Card currentCard;
    MaumauPlayer waitingForPlayer;
    Direction direction;
    
    @Override
    public String toString() {
      return "State [mode=" + mode + ", currentCard=" + currentCard + ", waitingForPlayer="
          + waitingForPlayer + ", direction=" + direction + "]";
    }
    
    
  }

  public State state = new State();

  @Override
  public void init() {
    super.init();

    // add a listener for pointer (mouse, touch) input
//    pointer().setListener(new Pointer.Adapter() {
//      @Override
//      public void onPointerEnd(Pointer.Event event) {
//
//        Iterator<CardSlot<?>> cardSlotIter = cardTable.childs().iterator();
//        int sloNr = mouseCount % 4;
//        CardSlot<?> cardSlot0 = cardSlotIter.next();
//        CardSlot<?> csNorth = cardSlot0;
//        for (int i = 0; i < sloNr; i++) {
//          cardSlot0 = cardSlotIter.next();
//        }
//        Card c0 = new Card(Card.Value.values()[mouseCount % 52]);
//        csNorth.put(c0, null);
//        CardMoveAction2 cardMoveAction = new CardMoveAction2(c0, 25, cardSlot0);
//        schedule(cardMoveAction);
//        mouseCount++;
//
//      }
//    });

  }
  
  




  @Override
  protected CardTable<?, NESWLayout> createCardTable() {
    MaumauCardtable table = new MaumauCardtable(this, new NESWLayout(10));
    table.init();
    table.put(slotPlayer1, NESWLayout.NESW.N);
    table.put(slotPlayer2, NESWLayout.NESW.E);
    table.put(slotPlayer3, NESWLayout.NESW.S);
    table.put(slotPlayer4, NESWLayout.NESW.W);
    table.put(talon, NESWLayout.NESW.NE);
    table.put(playSlot, NESWLayout.NESW.C);
    
    return table;
  }
  
  
  public Card takeCardFromTalon() {
    List<Card> childs = talon.childs();
    return childs.remove(childs.size()-1);
  }

  @Override
  protected PlayerRegistry<MaumauPlayer> createPlayerRegistry() {
    PlayerRegistry<MaumauPlayer> playerRegistry2 = new PlayerRegistry<MaumauPlayer>();
    MaumauRobotPlayer myPlayer = new MaumauRobotPlayer("Player 1", this, slotPlayer1);
    myPlayer.setDealer(true);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 2", this, slotPlayer2);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 3", this, slotPlayer3);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 4", this, slotPlayer4);
    playerRegistry2.register(myPlayer);

    return playerRegistry2;
  }



  @Override
  public String toString() {
    return getClass().getSimpleName()+"( state="+state+")";
  }
  
  

}
