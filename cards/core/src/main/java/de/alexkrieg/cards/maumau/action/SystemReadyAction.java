package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Word;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.action.MoveSimpleAction;
import de.alexkrieg.cards.core.layout.AbsolutLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class SystemReadyAction extends MoveSimpleAction<Word, AbsolutLayout<Word>> implements GameLogicAction {

  
  private final MaumauRobotPlayer player;

  public SystemReadyAction() {
    this(null, null, null,0,null);
  }

  public SystemReadyAction(Word word, AbsolutLayout<Word> layout, AbsolutLayout.Attr dest , int duration, MaumauRobotPlayer player) {
    super(word,layout,dest,duration);
    this.player = player;
  }

  @Override
  public MaumauRobotPlayer player() {
    return this.player;
  }

  
  
  
  

}
