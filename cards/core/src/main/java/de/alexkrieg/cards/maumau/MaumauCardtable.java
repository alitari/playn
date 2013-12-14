package de.alexkrieg.cards.maumau;

import react.UnitSlot;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import de.alexkrieg.cards.core.CardTable;
import de.alexkrieg.cards.core.action.AbstractAction;
import de.alexkrieg.cards.core.action.Action;
import de.alexkrieg.cards.core.layout.NESWLayout;


public class MaumauCardtable extends CardTable<MaumauCardGame,NESWLayout> {
  
  
  final Action startDialing = new AbstractAction(null,0) {

    @Override
    public void execute() {
      cardGame.state.mode = MaumauCardGame.State.Mode.Dealing;
    }

    @Override
    protected void paintWithActionAlpha(float actionAlpha) {
      
    }
    
  };

  
  
  public MaumauCardtable(MaumauCardGame cardGame, NESWLayout layout) {
    super(cardGame, layout);
  }
  
  

  @Override
  public void update(int delta) {
    if ( iface != null) {
      boolean startScreen =  cardGame.state.mode == MaumauCardGame.State.Mode.Attract;
      iface.roots().iterator().next().setVisible(startScreen);
      super.update(delta);
    }
  }









  @Override
  protected void fillHUDRoot(final Root root) {
    Group buttons  = new Group(AxisLayout.vertical().offStretch());
    root.add(new Label("Card Table"), buttons ).addStyles(
        Style.TEXT_WRAP.is(true));

    Button button = new Button("Start");
    buttons.add(button);
    button.clicked().connect(new UnitSlot() {
      @Override
      public void onEmit() {
        cardGame.schedule(startDialing);
      }
    });
  }


}
