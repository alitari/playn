package de.alexkrieg.cards.maumau;

import static playn.core.PlayN.pointer;

import java.util.Iterator;

import playn.core.ImageLayer;
import playn.core.Pointer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.PlayerRegistry;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.StackLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class MaumauCardGame extends CardGame<NESWLayout, MaumauPlayer> {

  ImageLayer clayer;

  int mouseCount = 0;

  protected CardSlot<?> slot;

  public static final int UPDATE_RATE = 25;

  final CardSlot<TiledCardsRotatedLayout> slotPlayer1 = new CardSlot<TiledCardsRotatedLayout>(
      "North ", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> slotPlayer2 = new CardSlot<TiledCardsRotatedLayout>(
      "East", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> slotPlayer3 = new CardSlot<TiledCardsRotatedLayout>(
      "South", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> slotPlayer4 = new CardSlot<TiledCardsRotatedLayout>(
      "West", new TiledCardsRotatedLayout(0, 10));
  final CardSlot<StackLayout> talon = new CardSlot<StackLayout>("Center", new StackLayout(2, 2));

  public static class State {
    static enum Direction {
      Clock, AgainstClock;
    }

    Card currentCard;
    MaumauPlayer waitingForPlayer;
    Direction direction;

  }

  public State state = new State();

  @Override
  public void init() {
    super.init();

    // add a listener for pointer (mouse, touch) input
    pointer().setListener(new Pointer.Adapter() {
      @Override
      public void onPointerEnd(Pointer.Event event) {

        Iterator<CardSlot<?>> cardSlotIter = cardTable.childs().iterator();
        int sloNr = mouseCount % 4;
        CardSlot<?> cardSlot0 = cardSlotIter.next();
        CardSlot<?> csNorth = cardSlot0;
        for (int i = 0; i < sloNr; i++) {
          cardSlot0 = cardSlotIter.next();
        }
        Card c0 = new Card(Card.Value.values()[mouseCount % 52]);
        csNorth.put(c0, null);
        CardMoveAction2 cardMoveAction = new CardMoveAction2(c0, 25, cardSlot0);
        schedule(cardMoveAction);
        mouseCount++;

      }
    });

  }

  @Override
  protected CardTable<?, NESWLayout> createCardTable() {
    MaumauCardtable table = new MaumauCardtable(this, new NESWLayout(10));
    table.init();
    table.put(slotPlayer1, NESWLayout.NESW.N);
    table.put(slotPlayer2, NESWLayout.NESW.E);
    table.put(slotPlayer3, NESWLayout.NESW.S);
    table.put(slotPlayer4, NESWLayout.NESW.W);
//    table.put(talon, NESWLayout.NESW.C);
    return table;

  }

  @Override
  protected PlayerRegistry<MaumauPlayer> createPlayerRegistry() {
    PlayerRegistry<MaumauPlayer> playerRegistry2 = new PlayerRegistry<MaumauPlayer>();
    MaumauPlayer myPlayer = new MaumauRobotPlayer("Player North", this, slotPlayer1);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player East", this, slotPlayer2);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player South", this, slotPlayer3);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player West", this, slotPlayer4);
    playerRegistry2.register(myPlayer);

    return playerRegistry2;
  }

}
