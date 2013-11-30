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

import java.util.ArrayList;
import java.util.List;

import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;
import playn.core.SurfaceLayer;

public abstract class CardContainer<T extends LayerEntity> extends
		AbstractLayerEntityContainer<T> {

	protected ImageLayer imageLayer;
	protected SurfaceLayer surfaceLayer;

	protected CardContainer() {
		super();
	}

	

	@Override
	public void init() {
		super.init();
		List<Layer> layers = new ArrayList<Layer>();
		fillWithLayers(layers);
		for (Layer l : layers) {
			if (!(l instanceof ImageLayer)) {
				groupLayer.add(l);
			}
			if (l instanceof HasSize) {
				HasSize si = (HasSize) l;
				width = Math.max(width, si.width());
				height = Math.max(height, si.height());

			}
		}
		groupLayer.setOrigin(width / 2, height / 2);

	}

	protected abstract void fillWithLayers(List<Layer> layers);

}
