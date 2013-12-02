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

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.alexkrieg.cards.core.layout.Layout;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;
import pythagoras.f.Transform;

public abstract class AbstractLayerEntityContainer<T extends LayerEntity, L extends Layout<T>>
		extends AbstractLayerEntity implements LayerEntityContainer<T,L> {


	
	private float width = 0;
	private float height = 0;

	protected L cl;

	protected List<T> childs;

	protected AbstractLayerEntityContainer() {
		childs = new ArrayList<T>();
	}
	
	@Override
	public float height() {
		return height;
	}
	
	@Override
	public float width() {
		return width;
	}
	
	

	@Override
	public String toString() {
		return CardGame.logString(this);
	}

	@Override
	public Collection<T> childs() {
		return childs;
	}

	@Override
	public L layout() {
		return this.cl;
	}



	@Override
	public void init() {
		super.init();
		this.cl = createLayout();
		
		List<Layer> layers = new ArrayList<Layer>();
		fillWithLayers(layers);
		for (Layer l : layers) {
			if (!(l instanceof ImageLayer)) {
				((GroupLayer)layer()).add(l);
			}
			if (l instanceof HasSize) {
				HasSize si = (HasSize) l;
				width = Math.max(width, si.width());
				height = Math.max(height, si.height());

			}
		}
		this.cl.setContainer(this);
		((GroupLayer)layer()).setOrigin(width / 2, height / 2);

	}
	
	protected abstract void fillWithLayers(List<Layer> layers);
	
	protected Layer createLayer() {
		return graphics().createGroupLayer();
	}

	protected abstract L createLayout();

	public void put(T child, Object param) {
		child.init();
		child.setContainer(this);
		cl.recalc(child, param);
		put(child, cl.x(child), cl.y(child), cl.rot(child), cl.scale(child));
		childs.add(child);
	}

	private void put(T child, float x, float y, float rot, float scale) {
		Layer l = child.layer();
		Transform t = l.transform();
		t.setTx(x);
		t.setTy(y);
		t.setRotation(rot);
		t.setUniformScale(scale);

		((GroupLayer)layer()).add(l);
	}

}
