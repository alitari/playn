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
package de.alexkrieg.cards.core;

import static playn.core.PlayN.log;
import pythagoras.f.Transform;

public class LayerEntityAction<T extends LayerEntity> {
	
	
  final T le;
  final LayerEntityContainer<T> dest;
  final int duration;
  
  
  final float origRot;
  final float  origX;
  final float  origY;
  final float origScale;
  
  final float destRot;
  final float  destX;
  final float  destY;
  final float destScale;
  
  final Object param;
  
  
  float rot;
  float x;
  float  y;
  float scale;
  
  
  
  int tick;
  boolean isEnd;
  
  
  
  
  
  public LayerEntityAction(T obj,LayerEntityContainer<T> dest , int duration, Object param) {
	super();
	this.le = obj;
	this.dest = dest;
	this.tick =0;
	isEnd = false;
	this.duration = duration;
	this.param = param;
	le.init();
	Transform origt = le.layer().transform();
	origX = origt.tx();
	origY = origt.ty();
	origRot = origt.rotation();
	origScale = origt.uniformScale();
	
	Layout<T> layout = dest.layout();
	layout.recalc(obj, null);
	
	destX = layout.x(obj);
	destY = layout.y(obj);
	destRot = layout.rot(obj);
	destScale = layout.scale(obj);
	
}
  
  public void update() {
	  if ( tick == 0) {
		  startAction();
		  tick++;
	  } else if ( tick > duration) {
		  endAction();
	  } else {
		  float delta = tick/duration;
		  x = origX + delta*(destX -origX);
		  y = origY + delta*(destY -origY);
		  rot = rot + delta*(destRot -origRot);
		  scale = scale+  delta*(destScale -origScale);
		  tick++;
		  log().info("tick:"+this);
		  
		  
	  }
	  
  }

  protected void startAction() {
		log().info("startAction:"+this);
	  
  }
  
  
  
  protected void endAction() {
	  log().info("endAction:"+this);
	  
	  dest.put(le,param);
	  isEnd = true;
  }
  
  
 
  
  
}
