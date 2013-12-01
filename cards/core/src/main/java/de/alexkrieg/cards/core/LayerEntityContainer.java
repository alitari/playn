package de.alexkrieg.cards.core;

import java.util.Collection;

import de.alexkrieg.cards.core.layout.Layout;

public interface LayerEntityContainer<T extends LayerEntity, L extends Layout<T>> extends LayerEntity {
	
	Collection<T> childs();
	
	float height() ;
	
	float width() ;
	
	L layout();
	
	void put(T child, Object param);

}
