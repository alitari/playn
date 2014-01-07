package de.alexkrieg.cards.core.action;

import pythagoras.f.Transform;
import de.alexkrieg.cards.core.LayerEntity;

public abstract class TransformAction<T extends LayerEntity> extends AbstractAction<T> {

	protected final Transform origTransform;
	protected Transform destTransform;

	public TransformAction(T layerEntity, int duration) {
		super(layerEntity, duration);
		origTransform = layerEntity != null ?layerEntity.layer().transform(): null;
	}
	
	public Transform getDestTransform() {
		return destTransform;
	}

	public void setDestTransform(Transform destTransform) {
		this.destTransform = destTransform;
	}


	@Override
	protected void paintWithActionAlpha(float actionAlpha) {
	  if ( origTransform == null) return;
		Transform transform = origTransform.lerp(destTransform, actionAlpha);
		float[] matrix = new float[6];
		transform.get(matrix);
		layerEntity().layer().transform().setTransform(matrix[0], matrix[1], matrix[2],
				matrix[3], matrix[4], matrix[5]);

	}

}
