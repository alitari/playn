package de.alexkrieg.cards.maumau;

import javax.inject.Inject;

import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.CardGame;
import de.alexkrieg.cards.core.Thing;
import de.alexkrieg.cards.core.Word;
import de.alexkrieg.cards.core.layout.AbsolutLayout;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.WordLayout.Attr;

public class MaumauCardGame extends CardGame<NESWLayout,MaumauRobotPlayer,MaumauGameLogic>{

  public MaumauCardGame() {
    super();
  }

  @Inject
  public MaumauCardGame(ActionManager actionManager,
      MaumauCardtable cardTable,
      MaumauPlayerRegistry playerRegistry,
      MaumauGameLogic gamelogic) {
    super(actionManager, cardTable, playerRegistry, gamelogic);
    // TODO Auto-generated constructor stub
  }
  
  public static enum Words {
    MauMauTitle("MauMau",5,"disko"),ArcadeTitle("ARCADE",2,"g7starforce"),Refilled("FILLED",2,"g7starforce"),PlayAgainst("YOUR OPPONENTS",3,"g7starforce"),
    Packy("PACKY",3,"g7starforce"),Woolen("WHOOLEN",3,"g7starforce"),Mighta("MIGHTA",3,"g7starforce");
    
    String text;
    String font;
    int gap;
    private Words(String text,int gap,String font) {
      this.text = text;
      this.font = font;
      this.gap = gap;
    }
  }
  
  public static Word createWord(Words word) {
    return createWord(word,null);
  }
  
  public static Word createWord(Words word, Attr attr) {
    Word maumau = new Word(word.name(),word.gap, word.font);
    maumau.init();
    maumau.setText(word.text,attr);
    return maumau;
  }

  @Override
  public void init() {
    super.init();
    this.textLayer.put(createWord(Words.MauMauTitle), new AbsolutLayout.Attr(220,-100,0,0.1F));
    this.textLayer.put(createWord(Words.ArcadeTitle), new AbsolutLayout.Attr(220,100,0,0.1F));
    this.textLayer.put(createWord(Words.Refilled), new AbsolutLayout.Attr(120,-300,0,0.5F));
    this.textLayer.put(createWord(Words.PlayAgainst,new Attr(1,0,1,0)), new AbsolutLayout.Attr(120,-300,0,0.5F));
    this.textLayer.put(createWord(Words.Packy), new AbsolutLayout.Attr(120,-300,0,0.5F));
    this.textLayer.put(createWord(Words.Woolen), new AbsolutLayout.Attr(120,-300,0,0.5F));
    this.textLayer.put(createWord(Words.Mighta), new AbsolutLayout.Attr(120,-300,0,0.5F));
    this.thingLayer.put(new MaumauThing.Packy()  , new AbsolutLayout.Attr(120,-300,0,1F));
    this.thingLayer.put(new MaumauThing.Woolen()  , new AbsolutLayout.Attr(120,-300,0,1F));
    this.thingLayer.put(new MaumauThing.Mighta()  , new AbsolutLayout.Attr(120,-300,0,1F));
    
    
  }
  
  
  
  

}
