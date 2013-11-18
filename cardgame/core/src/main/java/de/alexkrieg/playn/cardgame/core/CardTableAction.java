/**
 * Copyright 2011 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.alexkrieg.playn.cardgame.core;

import java.util.ArrayList;
import java.util.List;

import playn.core.ImageLayer;
import playn.core.Layer;
import pythagoras.f.Transform;

public abstract class CardTableAction {
	
	
  final Card card;
  
  int tick;
  boolean isEnd;
  
  
  
  protected CardTableAction(Card card) {
	super();
	this.card = card;
	this.tick =0;
	isEnd = false;
}
  
  public void update() {
	  if ( tick == 0) {
		  startAction();
		  tick++;
	  }
	  if ( isEnd) {
		  endAction();
	  } else {
		  tick++;
		  isEnd = animate();
	  }
	  
  }

  abstract protected void startAction();
  
  abstract protected boolean animate();
  
  abstract protected void endAction();
  
  
  public static  class Move extends CardTableAction {
	  final float duration;
	  final float rotationtimes;
	  final float  ex;
	  final float  ey;
	  float scaladelta;
	  
	  float dx;
	  float dy;
	  float x;
	  float y;
	  
	  float originRotation;
	  float originScale;

	public Move(Card card,float ex, float ey, int duration, float rotationtimes,float scaledelta) {
		super(card);
		
		this.duration = duration;
		Layer il = card.layer();
		Transform t = il.transform();
		this.rotationtimes = rotationtimes;
		this.scaladelta = scaledelta;
		this.ex = ex;
		this.ey = ey;
		
	}
	@Override
	protected void startAction() {
		Layer il = card.layer();
		Transform t = il.transform();
		originRotation = t.rotation();
		originScale = t.uniformScale();
		x = t.tx();
		y = t.ty();
		dx = (ex - x)/duration ;
		dy = (ey - y)/duration ;
	}

	@Override
	protected boolean animate() {
		Layer il = card.layer();
		x+=dx;
		y+=dy;
		Transform t = il.transform();
		t.setTx(x);
		t.setTy(y);
		
		t.setRotation( originRotation +( rotationtimes* (float)Math.PI*2 *((tick-1)/duration)));
		t.setUniformScale(originScale+scaladelta*(tick-1)/duration);
		return tick > duration; 
	}

	@Override
	protected void endAction() {
	}
	
	
  };
  
  public static class Sequence extends CardTableAction {
	  
	  final List<CardTableAction> actions;

	public Sequence(CardTableAction[] actions) {
		super(actions[0].card);
		this.actions = new ArrayList<CardTableAction>();
		for ( CardTableAction cta: actions) {
			this.actions.add(cta);
		}
	}
	
	

	@Override
	public void update() {
		if ( isEnd) return;
		CardTableAction action = actions.get(0);
		action.update();
		if ( action.isEnd) {
			actions.remove(0);
			if ( actions.size()==0) {
              isEnd =true;				
			}
		} 
	}



	@Override
	protected void startAction() {
	}

	@Override
	protected boolean animate() {
		return false;
	}

	@Override
	protected void endAction() {
	}
	  
  }
  
  
}
