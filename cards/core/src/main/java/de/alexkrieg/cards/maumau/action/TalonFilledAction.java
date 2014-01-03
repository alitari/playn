package de.alexkrieg.cards.maumau.action;

import de.alexkrieg.cards.core.Word;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.action.MoveSimpleAction;
import de.alexkrieg.cards.core.layout.AbsolutLayout;
import de.alexkrieg.cards.maumau.MaumauRobotPlayer;

public class TalonFilledAction extends MoveSimpleAction<Word, AbsolutLayout<Word>> implements GameLogicAction<Word> {

  private final MaumauRobotPlayer player;

  public TalonFilledAction() {
    this(null, null, null,0,null);
  }

  public TalonFilledAction(Word word, AbsolutLayout<Word> layout, AbsolutLayout.Attr dest , int duration, MaumauRobotPlayer player) {
    super(word,layout,dest,duration);
    this.player = player;
  }

  @Override
  public MaumauRobotPlayer player() {
    return this.player;
  }
  

}
