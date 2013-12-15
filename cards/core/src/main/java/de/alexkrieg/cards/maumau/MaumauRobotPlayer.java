package de.alexkrieg.cards.maumau;

import java.util.List;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class MaumauRobotPlayer extends MaumauPlayer {

  private boolean dealer = false;

 

  public MaumauRobotPlayer(String name, MaumauCardGame game,
      CardSlot<TiledCardsRotatedLayout> myCards) {
    super(name, game, myCards);
  }

  @Override
  public void update() {
    if (mustDeal()) {
      GameAction dealAction=null;
      if ( dealingFinished() ) {
        dealAction = new PlayCardAction(this, game.takeCardFromTalon(), game) {

          @Override
          public void execute() {
            super.execute();
            game.state.mode = MaumauCardGame.State.Mode.Playn;
          }
          
        };
      } else {
        dealAction = nextDealingAction();
      }
        game.schedule(dealAction);
    } else {
      super.update();
    }
  }

  private GameAction nextDealingAction() {
    if (game.talon.childs().isEmpty()) {
      return fillTalonAction;
    }
    game.state.waitingForPlayer = game.playerRegistry().getNextPlayerOf(
        game.state.waitingForPlayer == null ? this : game.state.waitingForPlayer);
    
    return new CardMoveAction2<TiledCardsRotatedLayout>(game.takeCardFromTalon(), 10,
        game.state.waitingForPlayer.myCards);
  }

  private boolean dealingFinished() {
    boolean slot1Complete = game.slotPlayer1.childs().size() == MaumauCardGame.PROP_DealedCardsCount;
    boolean slot2Complete = game.slotPlayer2.childs().size() == MaumauCardGame.PROP_DealedCardsCount;
    boolean slot3Complete = game.slotPlayer3.childs().size() == MaumauCardGame.PROP_DealedCardsCount;
    boolean slot4Complete = game.slotPlayer4.childs().size() == MaumauCardGame.PROP_DealedCardsCount;
    return slot1Complete && slot2Complete && slot3Complete && slot4Complete;
  }

  private boolean mustDeal() {
    return game.state.mode == MaumauCardGame.State.Mode.Dealing && isDealer();
  }

  @Override
  protected Card cardDecision() {
    boolean isitmyTurn = isItMyTurn();
    Card card = (!isitmyTurn) ? lookForTakeOverCard() : lookForCard();
    return card;
  }

  private Card lookForCard() {
    Card card = game.state.currentCard;
    List<Card> candidates = findCandidates(card);
    return !candidates.isEmpty() ? selectFromCandidates(candidates) : (Card) null;
  }

  private Card selectFromCandidates(List<Card> candidates) {
    // TODO: add tactics here
    return candidates.iterator().next();
  }

  private List<Card> findCandidates(Card card) {
    List<Card> candidates = Card.applyFilter(new Card.SuitFilter(card.value.suit()),
        myCards.childs());
    candidates.addAll(Card.applyFilter(new Card.RankFilter(card.value.rank()), myCards.childs()));
    return candidates;
  }

  private Card lookForTakeOverCard() {
    return null;
  }

  public boolean isDealer() {
    return dealer;
  }

  public void setDealer(boolean dealer) {
    this.dealer = dealer;
  }

}
