package de.alexkrieg.cards.maumau;

import java.util.HashSet;
import java.util.Set;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;

public class MaumauRobotPlayer extends MaumauPlayer {
  

  

  public MaumauRobotPlayer(String name, MaumauCardGame game, CardSlot<?> myCards) {
    super(name, game, myCards);
  }

  @Override
  protected Card cardDecision() {
    boolean isitmyTurn = isItMyTurn( );
    Card card = ( !isitmyTurn)? lookForTakeOverCard() : lookForCard();
    return card;
  }

  private Card lookForCard() {
    Card card = game.state.currentCard;
    Set<Card> candidates = findCandidates(card);
    return !candidates.isEmpty()? selectFromCandidates(candidates): (Card) null;
  }

  private Card selectFromCandidates(Set<Card> candidates) {
    // TODO: add tactics here 
    return candidates.iterator().next();
  }

  private Set<Card> findCandidates(Card card) {
    Set<Card> candidates = Card.applyFilter(new Card.SuitFilter(card.value.suit()), myCards.childs());
    candidates.addAll(Card.applyFilter(new Card.RankFilter(card.value.rank()), myCards.childs()));
    return candidates;
  }

  private Card lookForTakeOverCard() {
    return null;
  }

  private boolean isItMyTurn() {
    return game.state.waitingForPlayer == this;
  }

}
