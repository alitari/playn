package de.alexkrieg.cards.core;

import playn.core.Layer;
import de.alexkrieg.cards.core.action.GameAction;

public interface LayerEntity {
  
  String id();
  
  Layer layer();

  void init();

  void setContainer(LayerEntityContainer<?, ?> container);

  LayerEntityContainer<?, ?> getContainer();

  void setInUseOfAction(GameAction action);

  GameAction getInUseAction();
  
  

}
