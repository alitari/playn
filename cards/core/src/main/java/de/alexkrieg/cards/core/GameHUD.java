package de.alexkrieg.cards.core;

import de.alexkrieg.cards.core.layout.AbsolutLayout;


public abstract class GameHUD extends AbstractLayerEntityContainer<HUDSegment,AbsolutLayout> {
	
	final protected CardTable cardTable;

	public GameHUD(CardTable cardTable) {
		super();
		this.cardTable = cardTable;
	}
	
	@Override
	public void init() {
		super.init();
		width = cardTable.width();
		height = cardTable.height();
	}

	@Override
	protected AbsolutLayout createLayout() {
		return new AbsolutLayout();
	} 



}
