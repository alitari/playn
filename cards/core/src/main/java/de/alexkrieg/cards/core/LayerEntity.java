package de.alexkrieg.cards.core;

import playn.core.Layer;

public interface LayerEntity {
	Layer layer();
	
    void init();
    
    void setContainer( LayerEntityContainer<? , ? > container);

	LayerEntityContainer<? ,?> getContainer();

}
