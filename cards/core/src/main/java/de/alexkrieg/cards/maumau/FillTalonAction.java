package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;

public class FillTalonAction extends CardMoveAction2<StackLayout> {

  final MaumauRobotPlayer player;

  public FillTalonAction(MaumauRobotPlayer player, Card card, MaumauCardtable table) {
    super(card,  table.talon);
    card.setSide(Card.Side.Back);
    this.player = player;
  }

  @Override
  public void execute() {
    super.execute();
//    game.gameLogic.mode = game.playSlot.childs().size() == 1 ? MaumauCardGame.State.Mode.Playn
//        : MaumauCardGame.State.Mode.Refill;
  }

}
