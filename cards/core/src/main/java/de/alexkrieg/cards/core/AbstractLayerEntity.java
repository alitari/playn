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

public abstract class AbstractLayerEntity implements LayerEntity {

	protected Layer layer;

	LayerEntityContainer<?, ?> container;

	protected AbstractLayerEntity() {
	}
	
	
	
	
	@Override
  public String toString() {
    return String.valueOf(System.identityHashCode(this));
  }




  @Override
	public LayerEntityContainer<?, ?> getContainer() {
		return container;
	}


	@Override
	public void setContainer(LayerEntityContainer<?, ?> container) {
		this.container = container;
	}


	@Override
	public Layer layer() {
		return layer;
	}

	@Override
	public void init() {
		if (isInitialized()) return;
			this.layer = createLayer();
	}
	
	public boolean isInitialized() {
	  return this.layer != null;
	}

	protected abstract Layer createLayer();

}
