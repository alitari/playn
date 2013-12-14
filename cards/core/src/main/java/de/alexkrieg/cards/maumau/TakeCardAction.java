package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class TakeCardAction extends CardMoveAction2<TiledCardsRotatedLayout> {

  final MaumauPlayer player;
  final MaumauCardGame game;

  public TakeCardAction(MaumauPlayer player, Card card, MaumauCardGame game) {
    super(card, 10, player.myCards);
    card.setSide(Card.Side.Back);
    this.player = player;
    this.game = game;
  }

  @Override
  public void execute() {
    super.execute();
    boolean clockDirection = game.state.direction == MaumauCardGame.State.Direction.Clock;
    game.state.waitingForPlayer = clockDirection ? game.playerRegistry().getNextPlayerOf(player)
        : game.playerRegistry().getPreviousPlayerOf(player);
  }

}
