package de.alexkrieg.cards.core.action;

import playn.core.Layer;
import pythagoras.f.Point;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.LayerEntity;
import de.alexkrieg.cards.core.LayerEntityContainer;
import de.alexkrieg.cards.core.layout.Layout;

public class MoveAction<T extends LayerEntity, L extends Layout<T>> extends TransformAction {

  protected final T layerEntity;
  protected final LayerEntityContainer<T, L> destination;

  public MoveAction(T layerEntity, int duration, LayerEntityContainer<T, L> destination) {
    super(layerEntity == null ? null : layerEntity.layer(), duration);
    this.layerEntity = layerEntity;
    this.destination = destination;
    if (layerEntity != null && destination != null) {
      layerEntity.setInUseOfAction(this);
      setDestTransform(calcTransform());
    }
  }

  private Transform calcTransform() {
    L layout = destination.layout();
    layout.recalc(layerEntity, null);
    Layer destLayer = destination.layer();
    Point destScreen = Layer.Util.layerToScreen(destLayer, layout.x(layerEntity),
        layout.y(layerEntity));
    Point destPoint = new Point();
    Layer.Util.screenToLayer(layerEntity.getContainer().layer(), destScreen, destPoint);
    Transform transform = layerEntity.layer().transform().copy();
    transform.setTranslation(destPoint.x, destPoint.y);
    return transform;
  }

  @Override
  public void execute() {
    if (destination != null && layerEntity != null) {
      destination.put(layerEntity, null);
      layerEntity.setInUseOfAction(null);
    }
  }

}
