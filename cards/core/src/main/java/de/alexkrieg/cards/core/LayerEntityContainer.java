package de.alexkrieg.cards.core;

import java.util.Collection;

import de.alexkrieg.cards.core.layout.Layout;

public interface LayerEntityContainer<T extends LayerEntity> extends LayerEntity {
	
	Collection<T> childs();
	
	Layout<T> layout();
	
	void put(T child, Object param);

}
