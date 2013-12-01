package de.alexkrieg.cards.mygame;

import static playn.core.PlayN.*;

import java.util.Iterator;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.GameHUD;
import de.alexkrieg.cards.core.Card.Value;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.NESWLayout.NESW;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Pointer;

public class MyCardGame extends CardGame {

	ImageLayer clayer;

	int mouseCount = 0;

	protected CardSlot slot;
	
	
	 public static final int UPDATE_RATE = 25;

	 

	@Override
	public void init() {
		super.init();
		//graphics().setSize((int)cardTable.width, (int)cardTable.height);
		cardTable.put(new CardSlot("North "),NESWLayout.NESW.N);
		cardTable.put(new CardSlot("East"),NESWLayout.NESW.E);
		cardTable.put(new CardSlot("South"),NESWLayout.NESW.S);
		cardTable.put(new CardSlot("West"),NESWLayout.NESW.W);
		cardTable.put(new CardSlot("Center"),NESWLayout.NESW.C);
		

		// add a listener for pointer (mouse, touch) input
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				mouseCount+=4;
				Card c0 = new Card(Card.Value.values()[mouseCount]);
				Card c1 = new Card(Card.Value.values()[mouseCount+1]);
				Card c2 = new Card(Card.Value.values()[mouseCount+2]);
				Card c3 = new Card(Card.Value.values()[mouseCount+3]);
				
				Iterator<CardSlot> cardSlotIter = cardTable.childs().iterator();
				CardSlot cardSlot0 = cardSlotIter.next();
				CardSlot cardSlot1 = cardSlotIter.next();
				CardSlot cardSlot2 = cardSlotIter.next();
				CardSlot cardSlot3 = cardSlotIter.next();
				cardSlot0.put(c0, null);
				cardSlot1.put(c1, null);
				cardSlot2.put(c2, null);
				cardSlot3.put(c3, null);
				//LayerEntityAction<Card> action = new LayerEntityAction<Card>(c,cardSlot,1,null);  

							
//				cardTable.put(action);
////				cardTable.childs.get(0).put(c,nul

//				log().info("c0:"+c0);
//				log().info("c1:"+c1);
//				log().info("c2:"+c2);
//				log().info("c3:"+c3);
//				
//				log().info("cs0:"+cardSlot0);
//				log().info("cs1:"+cardSlot1);
//				log().info("cs2:"+cardSlot2);
//				log().info("cs3:"+cardSlot3);
				

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

	protected CardTable createCardTable() {
		return new CardTable();
	}

	@Override
	protected GameHUD createGameHUD() {
		return new MyGameHUD(cardTable);
	}

	

}
