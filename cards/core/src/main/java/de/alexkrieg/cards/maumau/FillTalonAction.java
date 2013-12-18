package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;

public class FillTalonAction extends CardMoveAction2<StackLayout> {

  final MaumauPlayer player;
  final MaumauCardGame game;

  public FillTalonAction(MaumauPlayer player, Card card, MaumauCardGame game) {
    super(card, 10, game.talon);
    card.setSide(Card.Side.Back);
    this.player = player;
    this.game = game;
  }

  @Override
  public void execute() {
    super.execute();
//    game.gameLogic.mode = game.playSlot.childs().size() == 1 ? MaumauCardGame.State.Mode.Playn
//        : MaumauCardGame.State.Mode.Refill;
  }

}
