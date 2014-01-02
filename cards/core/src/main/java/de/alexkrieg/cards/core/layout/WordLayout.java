package de.alexkrieg.cards.core.layout;

import java.util.List;

import de.alexkrieg.cards.core.Letter;

public class WordLayout extends Layout<Letter> {

	final int gap;

	public WordLayout( int gap) {
		super();
		this.gap = gap;
	}

	
	public static class Attr {
	  
    public final int line;
    public final float rot;
    public final float scale;
    
    
    public Attr(int line, float rot, float scale) {
      super();
      this.line = line;
      this.rot = rot;
      this.scale = scale;
    }
  }
	
	
	@Override
	public void recalc(Letter child,Object p) {
	  Attr attr = (Attr) p;
		List<?> childs = container.childs();
    int index = childs.size() ;
		x =  child.layer().originX() +( index * (child.hasSize().width() + gap));
		y = child.hasSize().height() * attr.line;
		rot = attr.rot;
		scale = attr.scale;
	}

	

}
