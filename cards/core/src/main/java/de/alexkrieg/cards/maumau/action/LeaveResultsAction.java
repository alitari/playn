package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Player;
import de.alexkrieg.cards.maumau.MaumauCardtable;

public class LeaveResultsAction extends ActionAdapter {

  final MaumauCardtable cardTable;

  public LeaveResultsAction() {
    this(null,null);
  }

  public LeaveResultsAction(MaumauCardtable cardTable, Player<?, ?, ?> player) {
    super(player);
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
