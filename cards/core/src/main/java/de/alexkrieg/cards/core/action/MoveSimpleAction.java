package de.alexkrieg.cards.core.action;

import playn.core.Layer;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.layout.Layout;

public class MoveSimpleAction<T extends LayerEntity, L extends Layout<T>> extends TransformAction<T> {

  protected final L layout;
  protected final Object dest;

  public MoveSimpleAction(T layerEntity, L absolutLayout, Object dest  , int duration) {
    super(layerEntity, duration);
    this.layout = absolutLayout;
    this.dest = dest;
    if (layerEntity != null ) {
      layerEntity.setInUseOfAction(this);
      setDestTransform(calcTransform());
    }
  }

  private Transform calcTransform() {
    layout.recalc(layerEntity(), dest);
    Layer sourceLayer = layerEntity().layer();
    
    Transform transform = sourceLayer.transform().copy();
    transform.setTranslation(layout.x(layerEntity()), layout.y(layerEntity()));
    transform.setRotation(layout.rot(layerEntity()));    
    transform.setUniformScale(layout.scale(layerEntity()));
    
    return transform;
  }

  
  @Override
  public void execute() {
    if (layerEntity() != null ) {
      layerEntity().setInUseOfAction(null);
    }
    
  }

}
