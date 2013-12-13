package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;

public class PlayCardAction extends CardMoveAction2<StackLayout> {
  
  final MaumauPlayer player;
  final MaumauCardGame game;

  public PlayCardAction( MaumauPlayer player, Card card, MaumauCardGame game) {
    super(card, 10, game.talon);
    this.player = player;
    this.game = game;
  }

  @Override
  public void execute() {
    super.execute();
    game.state.currentCard = layerEntity;
    if ( layerEntity.value.rank()==8) {
      game.state.direction = game.state.direction == MaumauCardGame.State.Direction.Clock ? MaumauCardGame.State.Direction.AgainstClock :MaumauCardGame.State.Direction.Clock;
    }
//    game.state.waitingForPlayer = game.
  }
  
  
  
  
  

}
