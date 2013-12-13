package de.alexkrieg.cards.maumau;

import react.UnitSlot;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.layout.NESWLayout;


public class MaumauCardtable extends CardTable<MaumauCardGame,NESWLayout> {
  
  
  public MaumauCardtable(MaumauCardGame cardGame, NESWLayout layout) {
    super(cardGame, layout);
  }

  @Override
  protected void fillHUDRoot(final Root root) {
    Group buttons;
    root.add(new Label("Card Table"), buttons = new Group(AxisLayout.vertical().offStretch())).addStyles(
        Style.TEXT_WRAP.is(true));

    Button button = new Button("Start");
    buttons.add(button);
    button.clicked().connect(new UnitSlot() {
      @Override
      public void onEmit() {
        root.add(new Label("Game started"));

      }
    });
  }


}
