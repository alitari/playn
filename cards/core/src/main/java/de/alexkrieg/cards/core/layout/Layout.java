package de.alexkrieg.cards.core.layout;

import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.LayerEntityContainer;

public abstract class Layout<T extends LayerEntity> {
	enum Orientation {
		horizontal, vertical;
	}

	protected LayerEntityContainer<?, ?>  container;
	
	protected float x;
	protected float y;
	protected float rot;
	protected float scale;

	public void setContainer( LayerEntityContainer<?, ?> container) {
		this.container = container;
	}

	public float x(T child) {
		return x;
	}

	public float y(T child) {
		return y;
	}

	public float rot(T child) {
		return rot;
	}

	public float scale(T child) {
		return scale;
	}

	public abstract void recalc(T child, Object param );

}
