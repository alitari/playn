package de.alexkrieg.cards.mygame;

import static playn.core.PlayN.pointer;

import java.util.Iterator;

import playn.core.ImageLayer;
import playn.core.Pointer;
import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.GameHUD;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class MyCardGame extends CardGame {

	ImageLayer clayer;

	int mouseCount = 0;

	protected CardSlot<?> slot;

	public static final int UPDATE_RATE = 25;

	@Override
	public void init() {
		super.init();
		// graphics().setSize((int)cardTable.width, (int)cardTable.height);
		final CardSlot<TiledCardsRotatedLayout> csNorth = new CardSlot<TiledCardsRotatedLayout>("North ",new TiledCardsRotatedLayout(0, 10));
		cardTable.put(csNorth, NESWLayout.NESW.N);
		CardSlot<TiledCardsRotatedLayout> csEast = new CardSlot<TiledCardsRotatedLayout>("East",new TiledCardsRotatedLayout(0, 10));
		cardTable.put(csEast, NESWLayout.NESW.E);
		CardSlot<TiledCardsRotatedLayout> csSouth = new CardSlot<TiledCardsRotatedLayout>("South",new TiledCardsRotatedLayout(0, 10));
		cardTable.put(csSouth, NESWLayout.NESW.S);
		CardSlot<TiledCardsRotatedLayout> csWest = new CardSlot<TiledCardsRotatedLayout>("West",new TiledCardsRotatedLayout(0, 10));
		cardTable.put(csWest, NESWLayout.NESW.W);
		CardSlot<TiledCardsRotatedLayout> csCenter = new CardSlot<TiledCardsRotatedLayout>("Center",new TiledCardsRotatedLayout(0, 10));
		cardTable.put(csCenter, NESWLayout.NESW.C);

		// add a listener for pointer (mouse, touch) input
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				
					Iterator<CardSlot<?>> cardSlotIter = cardTable.childs()
							.iterator();
					int sloNr = mouseCount % 4;
					CardSlot<?> cardSlot0 = cardSlotIter.next();
					for ( int i = 0; i < sloNr;i++) {
					  cardSlot0 = cardSlotIter.next();
					}
					Card c0 = new Card(Card.Value.values()[mouseCount%52]);
					csNorth.put(c0 ,null);
					CardMoveAction2 cardMoveAction = new CardMoveAction2(c0,25, cardSlot0);
					schedule(cardMoveAction);
					mouseCount++;
				
				
				// LayerEntityAction<Card> action = new
				// LayerEntityAction<Card>(c,cardSlot,1,null);

				// cardTable.put(action);
				// // cardTable.childs.get(0).put(c,nul

				// log().info("c0:"+c0);
				// log().info("c1:"+c1);
				// log().info("c2:"+c2);
				// log().info("c3:"+c3);
				//
				// log().info("cs0:"+cardSlot0);
				// log().info("cs1:"+cardSlot1);
				// log().info("cs2:"+cardSlot2);
				// log().info("cs3:"+cardSlot3);

				// Move move1 = new CardTableAction.Move(c, event.x(),
				// event.y(), 50,2f,2f);
				// Move move2 = new CardTableAction.Move(c, 100, 100,
				// 50,2f,-2f);
				// Move move3 = new CardTableAction.Move(c, event.x(),
				// event.y(), 50,2f,0);
				// cardTable.put(new CardTableAction.Sequence(new
				// CardTableAction[]{move1,move2,move3}));
			}
		});

	}

	protected CardTable<NESWLayout> createCardTable() {
		return new CardTable<NESWLayout>(new NESWLayout(10));
	}

	@Override
	protected GameHUD createGameHUD() {
		return new MyGameHUD(cardTable);
	}

}
