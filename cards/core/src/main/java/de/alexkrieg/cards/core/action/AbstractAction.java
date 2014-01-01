package de.alexkrieg.cards.core.action;


import de.alexkrieg.cards.core.Player;
import playn.core.Layer;

public abstract class AbstractAction implements GameAction {

	protected final Layer layer;
	protected final int duration;	
	protected final Player<?,?,?> player;
	
	public AbstractAction(Layer layer, int duration,Player<?,?,?> player) {
		super();
		this.layer = layer;
		this.duration = duration;
		this.player = player;
	}
	

	@Override
  public Player<?, ?, ?> player() {
    return player;
  }


  @Override
	public void paint(int tick,float alpha) {
		float actionAlpha = tick == 0 ? 0 :(float)((float)tick/(duration+1)) +(float)((float)alpha/(duration+1));
		paintWithActionAlpha(actionAlpha);
	
	}

  protected void paintWithActionAlpha(float actionAlpha) {
  }


	@Override
	public int getDuration() {
		return duration;
	}
	
	public GameAction merge(GameAction action) {
	  return new Merge(this, action); 
	}
	
	
		

}
