package de.alexkrieg.cards.core.action;

public interface Action {
	
	void execute();

	int getDuration();

	void paint(int tick, float alpha);
	
	

}
