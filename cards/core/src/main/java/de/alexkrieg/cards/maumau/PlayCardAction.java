package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;

@Deprecated
public class PlayCardAction extends CardMoveAction2<StackLayout> {

  final MaumauPlayer player;
  final MaumauCardGame game;

  
  public PlayCardAction(MaumauPlayer player, Card card, MaumauCardGame game) {
    super(card,  null);
    card.setSide(Card.Side.Image);
    this.player = player;
    this.game = game;
  }

  @Override
  public void execute() {
    super.execute();
//    game.state.currentCard = layerEntity;
//    boolean clockDirection = game.state.direction == MaumauCardGame.State.Direction.Clock;
//    if (layerEntity.value.rank() == 8) {
//      game.state.direction = clockDirection ? MaumauCardGame.State.Direction.AgainstClock
//          : MaumauCardGame.State.Direction.Clock;
//    }
//    game.state.waitingForPlayer = clockDirection ? game.playerRegistry().getNextPlayerOf(player)
//        : game.playerRegistry().getPreviousPlayerOf(player);
  }

}
