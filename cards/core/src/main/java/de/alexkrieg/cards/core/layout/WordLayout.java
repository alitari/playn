package de.alexkrieg.cards.core.layout;

import java.util.List;

import de.alexkrieg.cards.core.Letter;

public class WordLayout extends Layout<Letter> {

	final int gap;
	
	float alpha;

	public WordLayout( int gap) {
		super();
		this.gap = gap;
	}

	
	public static class Attr {
	  
    public final int line;
    public final float rot;
    public final float scale;
    private final float alpha;
    
    
    public Attr(int line, float rot, float scale,float alpha) {
      super();
      this.line = line;
      this.rot = rot;
      this.scale = scale;
      this.alpha = alpha;
    }
  }
	
	
	@Override
	public void recalc(Letter child,Object p) {
	  Attr attr = p == null ? new Attr(1,0,1,255):(Attr) p ;
		List<?> childs = container.childs();
    int index =  childs.contains(child)? childs.indexOf(child):childs.size() ;
		x =  child.layer().originX() +( index * (child.hasSize().width() + gap));
		y = child.hasSize().height() * attr.line;
		rot = attr.rot;
		scale = attr.scale;
		alpha = attr.alpha;
		
	}


  @Override
  public float alpha(Letter child) {
    return alpha;
  }
	
	

	

}
