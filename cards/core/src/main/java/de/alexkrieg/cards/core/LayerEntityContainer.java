package de.alexkrieg.cards.core;

import java.util.List;

import de.alexkrieg.cards.core.layout.Layout;

public interface LayerEntityContainer<T extends LayerEntity, L extends Layout<T>> extends LayerEntity {
	
	List<T> childs();
	
	float height() ;
	
	float width() ;
	
	L layout();
	
	void put(T child, Object param);
	
	void remove( T child);
	
	List<T> getLastUnusedChilds(int count);

  List<T> getFirstUnusedChilds(int count);
  
  void removeAll();
  

}
