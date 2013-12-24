package de.alexkrieg.cards.maumau;

import javax.inject.Inject;

import playn.core.ImageLayer;
import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.PlayerRegistry;
import de.alexkrieg.cards.core.layout.NESWLayout;

public class MaumauCardGame extends CardGame<NESWLayout, MaumauPlayer,MaumauGameLogic> {
  
  @Inject
  public MaumauCardGame(ActionManager actionManager) {
    super(actionManager);
  }
  
  public MaumauCardGame() {
    // not used 
    super(null);
  }
  
  


  public static final String TALON_NAME = "Talon";


  ImageLayer clayer;

  int mouseCount = 0;


  public static final int UPDATE_RATE = 25;

  

  @Override
  protected MaumauCardtable createCardTable() {
    MaumauCardtable table = new MaumauCardtable(actionManager, new NESWLayout(10));
    table.init();
    return table;
  }
  
  public MaumauCardtable table() {
    return (MaumauCardtable) cardTable;
  }
  
  

  @Override
  protected PlayerRegistry<MaumauPlayer> createPlayerRegistry() {
    
    PlayerRegistry<MaumauPlayer> playerRegistry2 = new PlayerRegistry<MaumauPlayer>();
    MaumauRobotPlayer myPlayer = new MaumauRobotPlayer("Player 1", gameLogic,actionManager ,table(), table().slotPlayer1 );
    myPlayer.setDealer(true);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 2", gameLogic,actionManager , table(),table().slotPlayer2);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 3", gameLogic,actionManager , table(),table().slotPlayer3);
    playerRegistry2.register(myPlayer);

    myPlayer = new MaumauRobotPlayer("Player 4", gameLogic,actionManager , table(),table().slotPlayer4);
    playerRegistry2.register(myPlayer);

    return playerRegistry2;
  }



  @Override
  public String toString() {
    return getClass().getSimpleName()+"( game="+gameLogic+")";
  }


  @Override
  protected MaumauGameLogic createGameLogic() {
    MaumauGameLogic maumauGameLogic = new MaumauGameLogic();
    try {
      maumauGameLogic.configure();
    } catch (Exception e) {
     throw new RuntimeException("Problem during gamelogic configuration",e);
    }
    return maumauGameLogic;
  }
  
  

}
