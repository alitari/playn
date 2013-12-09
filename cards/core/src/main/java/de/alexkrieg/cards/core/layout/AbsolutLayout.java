package de.alexkrieg.cards.core.layout;

import de.alexkrieg.cards.core.LayerEntity;

public class AbsolutLayout<T extends LayerEntity> extends Layout<T> {

	public static class Attr {
		public final float x;
		public final float y;
		public final float rot;
		public final float scale;
		
		
		public Attr(float x, float y, float rot, float scale) {
			super();
			this.x = x;
			this.y = y;
			this.rot = rot;
			this.scale = scale;
		}
	}
	
	public AbsolutLayout() {
		super();
		
	}

	@Override
	public void recalc(T child,Object p) {
		Attr attr = (Attr) p;
		x = attr.x;
		y = attr.y;
		rot = attr.rot;
		scale = attr.scale;
	}

}
