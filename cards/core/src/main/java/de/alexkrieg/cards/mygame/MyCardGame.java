package de.alexkrieg.cards.mygame;

import static playn.core.PlayN.pointer;

import java.util.Iterator;

import playn.core.ImageLayer;
import playn.core.Pointer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class MyCardGame extends CardGame<NESWLayout,MyPlayer> {

	ImageLayer clayer;

	int mouseCount = 0;

	protected CardSlot<?> slot;

	public static final int UPDATE_RATE = 25;

	
	final CardSlot<TiledCardsRotatedLayout> csNorth = new CardSlot<TiledCardsRotatedLayout>("North ",new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> csEast = new CardSlot<TiledCardsRotatedLayout>("East",new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> csSouth = new CardSlot<TiledCardsRotatedLayout>("South",new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> csWest = new CardSlot<TiledCardsRotatedLayout>("West",new TiledCardsRotatedLayout(0, 10));
  final CardSlot<TiledCardsRotatedLayout> csCenter = new CardSlot<TiledCardsRotatedLayout>("Center",new TiledCardsRotatedLayout(0, 10));
	
	
	@Override
	public void init() {
	  super.init();
		

		// add a listener for pointer (mouse, touch) input
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				
					Iterator<CardSlot<?>> cardSlotIter = cardTable.childs()
							.iterator();
					int sloNr = mouseCount % 4;
					CardSlot<?> cardSlot0 = cardSlotIter.next();
					CardSlot<?> csNorth = cardSlot0; 
					for ( int i = 0; i < sloNr;i++) {
					  cardSlot0 = cardSlotIter.next();
					}
					Card c0 = new Card(Card.Value.values()[mouseCount%52]);
					csNorth.put(c0 ,null);
					CardMoveAction2 cardMoveAction = new CardMoveAction2(c0,25, cardSlot0);
					schedule(cardMoveAction);
					mouseCount++;
				
			}
		});

	}
	
	
  @Override
  protected CardTable<NESWLayout> createCardTable() {
	  CardTable<NESWLayout> table = new CardTable<NESWLayout>(this,new NESWLayout(10));
	  table.init();
    table.put(csNorth, NESWLayout.NESW.N);
    table.put(csEast, NESWLayout.NESW.E);
    table.put(csSouth, NESWLayout.NESW.S);
    table.put(csWest, NESWLayout.NESW.W);
    table.put(csCenter, NESWLayout.NESW.C);
    return table;
	  
	}


  @Override
  protected PlayerRegistry<MyPlayer> createPlayerRegistry() {
    PlayerRegistry<MyPlayer> playerRegistry2 = new PlayerRegistry<MyPlayer>();
    MyPlayer myPlayer = new MyPlayer("Alex");
    playerRegistry2.register(myPlayer);
    return playerRegistry2;
  }

}
