package de.alexkrieg.cards.core;


public abstract class GameHUD extends AbstractLayerEntityContainer<HUDSegment> {
	
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
	protected Layout<HUDSegment> createLayout() {
		return new AbsolutLayout();
	} 



}
