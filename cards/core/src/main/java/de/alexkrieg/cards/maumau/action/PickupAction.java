package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.CardSlot;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.action.GameAction;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class PickupAction extends GameAction.Merge {

  public PickupAction() {
    // default constructor needed for framework
    super(null, null);
  }

  public PickupAction(MaumauRobotPlayer player, Card card1, Card card2,
      CardSlot<TiledCardsRotatedLayout> playerSlot,int duration) {
    super(new PickupCardAction(card1, playerSlot, player,duration), new PickupCardAction(card2, playerSlot,
        player,duration));
  }

  @Override
  public MaumauRobotPlayer player() {
    return (MaumauRobotPlayer) super.player();
  }

  public static class PickupCardAction extends CardMoveAction2<TiledCardsRotatedLayout> {

    public PickupCardAction() {
      super(null, null, null,0);
    }

    public PickupCardAction(Card card, CardSlot<TiledCardsRotatedLayout> playerSlot,
        MaumauRobotPlayer player,int duration) {
      super(card, playerSlot, player,duration);
      card.setSide(Card.Side.Image);
    }

    @Override
    protected void recalcLayout(TiledCardsRotatedLayout layout) {
      layout.recalc(layerEntity, null);

    }

  }

}
