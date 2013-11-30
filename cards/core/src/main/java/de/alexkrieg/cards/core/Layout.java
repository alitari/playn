package de.alexkrieg.cards.core;

public abstract class Layout<T extends LayerEntity> {
	enum Orientation {
		horizontal, vertical;
	}

	LayerEntityContainer<T> container;

	protected float x;
	protected float y;
	protected float rot;
	protected float scale;

	void setCardContainer(LayerEntityContainer<T> cc) {
		container = cc;
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
