package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.action.GameAction;
import playn.core.Layer;

public interface LayerEntity {
  Layer layer();

  void init();

  void setContainer(LayerEntityContainer<?, ?> container);

  LayerEntityContainer<?, ?> getContainer();

  void setInUseOfAction(GameAction action);

  GameAction getInUseAction();

}
