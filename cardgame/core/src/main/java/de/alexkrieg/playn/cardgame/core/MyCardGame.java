package de.alexkrieg.playn.cardgame.core;

import static playn.core.PlayN.*;
import de.alexkrieg.playn.cardgame.core.CardTableAction.Move;

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
				mouseCount++;
				Card c = new Card(Card.Value.values()[mouseCount]);
				CardSlot cardSlot = cardTable.childs.get(0);
				
				LayerEntityAction<Card> action = new LayerEntityAction<Card>(c,cardSlot,1,null);  
				
				cardTable.put(action);
//				cardTable.childs.get(0).put(c,nul

				log().info("clicked " + event);
				

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

	

}
