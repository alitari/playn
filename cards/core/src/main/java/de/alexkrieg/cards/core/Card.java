package de.alexkrieg.cards.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.awt.image.ImagingOpException;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;

public class Card extends AbstractLayerEntity implements HasSizeEntity {
	public final Value value;

	@Override
	public HasSize hasSize() {
		return (HasSize) layer();
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
		ImageLayer imagelayer = value.createImageLayer();
		imagelayer.setOrigin(imagelayer.width() / 2, imagelayer.height() / 2);
		return imagelayer;
	}

	@Override
	public String toString() {
		return CardGame.logString(this);
	}

	public static enum Value {
		_ad, _ah, _as, _ac, _kd, _kh, _ks, _kc, _qd, _qh, _qs, _qc, _jd, _jh, _js, _jc, _td, _th, _ts, _tc, _9d, _9h, _9s, _9c, _8d, _8h, _8s, _8c, _7d, _7h, _7s, _7c, _6d, _6h, _6s, _6c, _5d, _5h, _5s, _5c, _4d, _4h, _4s, _4c, _3d, _3h, _3s, _3c, _2d, _2h, _2s, _2c;

		static private String DIR_CARDS = "images/cs2/";

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
			return name().substring(1) + ".png";
		}

		public ImageLayer createImageLayer() {
			ImageLayer imageLayer = graphics().createImageLayer(createImage());
			imageLayer.setWidth(72);
			imageLayer.setHeight(96);
			float width = imageLayer.width();
			return imageLayer;

		}

		public Image createImage() {
			Image bgImage = assets().getImage(DIR_CARDS + filename());
			float width = bgImage.width();
			return bgImage;
		}

	}

}
