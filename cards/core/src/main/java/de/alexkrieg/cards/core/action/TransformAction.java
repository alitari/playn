package de.alexkrieg.cards.core.action;

import playn.core.Layer;
import pythagoras.f.Transform;

public abstract class TransformAction extends AbstractAction {

	protected final Transform origTransform;
	protected Transform destTransform;

	public TransformAction(Layer layer, int duration) {
		super(layer, duration);
		origTransform = layer.transform();
	}
	
	public Transform getDestTransform() {
		return destTransform;
	}

	public void setDestTransform(Transform destTransform) {
		this.destTransform = destTransform;
	}


	@Override
	protected void paintWithActionAlpha(float actionAlpha) {
		Transform transform = origTransform.lerp(destTransform, actionAlpha);
		float[] matrix = new float[6];
		transform.get(matrix);
		layer.transform().setTransform(matrix[0], matrix[1], matrix[2],
				matrix[3], matrix[4], matrix[5]);

	}

}
