package de.alexkrieg.cards.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.HashSet;
import java.util.Set;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;

public class Card extends AbstractLayerEntity implements HasSizeEntity {
  
  static private String DIR_CARDS = "images/cs2/";
  
	public final Value value;
	
	public static interface Filter {
	   boolean apply(Card candidate, Set<Card> cardSet );
	}
	
	public static class SuitFilter implements Filter {
	  
	  final char suit;

    public SuitFilter(char suit) {
      super();
      this.suit = suit;
    }

    @Override
    public boolean apply(Card candidate, Set<Card> cardSet) {
      return candidate.value.suit() == this.suit;
    }
	  
	}
	
	
public static class RankFilter implements Filter {
    
    final int rank;

    public RankFilter(int rank) {
      super();
      this.rank = rank;
    }

    @Override
    public boolean apply(Card candidate, Set<Card> cardSet) {
      return candidate.value.rank() == this.rank;
    }
    
  }

	public static Set<Card> applyFilter( Filter f, Set<Card> cards) {
	  Set<Card> result = new HashSet<Card>();
	  for ( Card c:cards) {
	    if ( f.apply(c, result)) {
	      result.add(c);
	    }
	  }
	  return result;
	}
	
	public static enum Side {
	  Image,Back;
	}
	
	private Side side;
	
	private ImageLayer imageLayer;
	private ImageLayer backsideLayer;

	@Override
	public HasSize hasSize() {
		return imageLayer;
	}

	public Card(String cardstr) {
		this(Value.cardEnum(cardstr));
	}

	public Card(Value val) {
		super();
		this.value = val;
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	protected Layer createLayer() {
		imageLayer = createImageLayer(createImage( this.value));
		backsideLayer = createImageLayer(createImage( "bsfv.png"));
		GroupLayer groupLayer = graphics().createGroupLayer();
		setSide(Side.Back);
		groupLayer.add(imageLayer);
		groupLayer.add(backsideLayer);
		return groupLayer;
	}
	
	public ImageLayer createImageLayer(Image image) {
    ImageLayer imageLayer = graphics().createImageLayer(image);
    imageLayer.setWidth(72);
    imageLayer.setHeight(96);
    return imageLayer;
  }

  public Image createImage(String fileName) {
    Image bgImage = assets().getImage(DIR_CARDS + fileName);
    return bgImage;
  }
  
  public Image createImage(Value value) {
    return createImage(value.filename());
  }
  
	@Override
	public String toString() {
		return CardGame.logString(this);
	}

	public Side getSide() {
    return side;
  }

  public void setSide(Side side) {
    this.side = side;
    imageLayer.setVisible(this.side == Side.Image);
    backsideLayer.setVisible(this.side == Side.Back);
  }

  public static enum Value {
		_ad, _ah, _as, _ac, _kd, _kh, _ks, _kc, _qd, _qh, _qs, _qc, _jd, _jh, _js, _jc, _td, _th, _ts, _tc, _9d, _9h, _9s, _9c, _8d, _8h, _8s, _8c, _7d, _7h, _7s, _7c, _6d, _6h, _6s, _6c, _5d, _5h, _5s, _5c, _4d, _4h, _4s, _4c, _3d, _3h, _3s, _3c, _2d, _2h, _2s, _2c;

		
    public int rank() {
			char c = name().charAt(1);
			if (c == 't')
				return 10;
			else if (c == 'j')
				return 11;
			else if (c == 'q')
				return 12;
			else if (c == 'k')
				return 13;
			else if (c == 'a')
				return 14;
			else
				return Integer.parseInt(String.valueOf(c));
		}

		public char suit() {
			return name().charAt(2);
		}

		public static Value cardEnum(String s) {
			return valueOf(s.substring(1));
		}
		
		

		public String filename() {
		  Value v = null;
			return name().substring(1) + ".png";
		}

		

	}

}
