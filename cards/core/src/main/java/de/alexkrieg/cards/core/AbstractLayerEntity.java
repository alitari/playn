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

import playn.core.Layer;

public abstract class AbstractLayerEntity 
		implements LayerEntity {

	protected Layer layer;

	protected float width = 0;
	protected float height = 0;



	protected AbstractLayerEntity() {
	}

	@Override
	public String toString() {
		return CardGame.logString(this);
	}


	@Override
	public Layer layer() {
		return layer;
	}

	@Override
	public float width() {
		return width;
	}

	@Override
	public float height() {
		return height;
	}

	@Override
	public void init() {
		this.layer = createLayer();
	}
	
	protected abstract Layer createLayer();


}
