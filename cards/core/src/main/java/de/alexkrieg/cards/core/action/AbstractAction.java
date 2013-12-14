package de.alexkrieg.cards.core.action;


import playn.core.Layer;

public abstract class AbstractAction implements Action {

	protected final Layer layer;
	protected final int duration;	
	
	public AbstractAction(Layer layer, int duration) {
		super();
		this.layer = layer;
		this.duration = duration;
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
	
	

}
