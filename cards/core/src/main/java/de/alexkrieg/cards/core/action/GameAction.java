package de.alexkrieg.cards.core.action;

public interface GameAction {
	
	void execute();

	int getDuration();

	void paint(int tick, float alpha);
	
	

}
