package de.alexkrieg.playn.cardgame.core;

import playn.core.Layer;

public interface LayerEntity {
	Layer layer();
	
    float width();
    
    float height();
    
    void init();

}
