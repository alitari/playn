package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Card;
import de.alexkrieg.cards.core.action.CardMoveAction2;
import de.alexkrieg.cards.core.layout.StackLayout;

public class FillTalonAction extends CardMoveAction2<StackLayout> {


  public FillTalonAction(MaumauRobotPlayer player, Card card, MaumauCardtable table,int duration) {
    super(card,  table.talon,player,duration);
    card.setSide(Card.Side.Back);
  }
  
  

  @Override
  public MaumauRobotPlayer  player() {
    return (MaumauRobotPlayer) player; 
  }



  @Override
  public void execute() {
    super.execute();
//    game.gameLogic.mode = game.playSlot.childs().size() == 1 ? MaumauCardGame.State.Mode.Playn
//        : MaumauCardGame.State.Mode.Refill;
  }

  @Override
  protected void recalcLayout(StackLayout layout) {
    layout.recalc(layerEntity, null);
  }

}
