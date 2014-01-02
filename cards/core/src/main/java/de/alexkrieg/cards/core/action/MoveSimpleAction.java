package de.alexkrieg.cards.core.action;

import playn.core.Layer;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.layout.AbsolutLayout;

public class MoveSimpleAction<T extends LayerEntity, L extends AbsolutLayout<T>> extends TransformAction {

  protected final T layerEntity;
  protected final L absolutLayout;
  protected final AbsolutLayout.Attr dest;

  public MoveSimpleAction(T layerEntity, L absolutLayout, AbsolutLayout.Attr dest  , int duration) {
    super(layerEntity == null ? null : layerEntity.layer(), duration);
    this.layerEntity = layerEntity;
    this.absolutLayout = absolutLayout;
    this.dest = dest;
    if (layerEntity != null ) {
      layerEntity.setInUseOfAction(this);
      setDestTransform(calcTransform());
    }
  }

  private Transform calcTransform() {
    absolutLayout.recalc(layerEntity, dest);
    Layer sourceLayer = layerEntity.layer();
    
    Transform transform = sourceLayer.transform().copy();
    transform.setTranslation(absolutLayout.x(layerEntity), absolutLayout.y(layerEntity));
    transform.setRotation(absolutLayout.rot(layerEntity));    
    transform.setUniformScale(absolutLayout.scale(layerEntity));
    
    return transform;
  }

  
  @Override
  public void execute() {
    if (layerEntity != null ) {
      layerEntity.setInUseOfAction(null);
    }
    
  }

}
