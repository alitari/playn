package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.maumau.MaumauCardtable;

public class LeaveResultsAction extends ActionAdapter {

  final MaumauCardtable cardTable;

  public LeaveResultsAction() {
    this(null);
  }

  public LeaveResultsAction(MaumauCardtable cardTable) {
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
