package de.alexkrieg.cards.core.action;

import playn.core.Layer;
import pythagoras.f.Point;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.LayerEntityContainer;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class MoveAction<T extends LayerEntity, L extends Layout<T>> extends TransformAction {

  protected final T layerEntity;
  protected final LayerEntityContainer<T, L> destination;

  public MoveAction(T layerEntity, int duration, LayerEntityContainer<T, L> destination) {
    super(layerEntity == null ? null : layerEntity.layer(), duration);
    this.layerEntity = layerEntity;
    this.destination = destination;
    if (layerEntity != null && destination != null) {
      layerEntity.setInUseOfAction(this);
      setDestTransform(calcTransform());
      layerEntity.layer().parent().setDepth( destination.layer().depth()+1);
    }
  }

  private Transform calcTransform() {
    Layer destLayer = destination.layer();
    Layer sourceLayer = layerEntity.layer();
    Layer containerLayer = layerEntity.getContainer().layer();
    float sourceRot = containerLayer.transform().rotation();//+sourceLayer.transform().rotation();
    L layout = destination.layout();
    recalcLayout(layout);
    float destRot = destLayer.transform().rotation()-layout.rot(layerEntity);
 
    Point destScreen = Layer.Util.layerToScreen(destLayer, layout.x(layerEntity),
        layout.y(layerEntity));
    Point destPoint = new Point();
    Layer.Util.screenToLayer(containerLayer, destScreen, destPoint);
    Transform transform = sourceLayer.transform().copy();
    transform.setTranslation(destPoint.x, destPoint.y);
    // rotation diff
    transform.setRotation(sourceRot-destRot);    
    // scale
    transform.setUniformScale(layout.scale(layerEntity));
    
    return transform;
  }

  protected abstract void recalcLayout(L layout);
    
  

  @Override
  public void execute() {
    if (destination != null && layerEntity != null) {
      destination.put(layerEntity, null);
      layerEntity.setInUseOfAction(null);
    }
  }

}
