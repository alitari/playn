package de.alexkrieg.cards.core.action;

import de.alexkrieg.cards.core.LayerEntity;

public class VisibiltyAction<T extends LayerEntity> extends AbstractAction<T> {

  final boolean visibleOn;
  

  public VisibiltyAction(T layerEntity, int duration, boolean visibleOn) {
    super(layerEntity, duration);
    this.visibleOn = visibleOn;
  }

  @Override
  public void execute() {
    layerEntity().layer().setVisible(visibleOn);
  }

}
