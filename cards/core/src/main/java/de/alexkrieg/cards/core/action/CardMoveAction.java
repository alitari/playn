package de.alexkrieg.cards.core.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;

public class CardMoveAction implements Action {
	
	
	private final Card card;
	private final CardSlot destination;
	
	

public CardMoveAction(Card card, CardSlot destination) {

	super();
	this.card = card;
	this.destination = destination;

	}

	@Override
	public void execute() {
		destination.put( card,null);

	}

}
