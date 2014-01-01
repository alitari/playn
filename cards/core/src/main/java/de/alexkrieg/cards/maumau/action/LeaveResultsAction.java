package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.maumau.MaumauCardtable;

public class LeaveResultsAction extends ActionAdapter {

  final MaumauCardtable cardTable;

  public LeaveResultsAction() {
    this(null,null,0);
  }

  public LeaveResultsAction(MaumauCardtable cardTable, Player<?, ?, ?> player,int duration) {
    super(player,duration);
    this.cardTable = cardTable;
  }

  @Override
  public void execute() {
    super.execute();
    if (cardTable != null) {
      cardTable.clear();
    }
  }
}
