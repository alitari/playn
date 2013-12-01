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

import java.util.List;

import playn.core.ImageLayer;
import playn.core.Layer;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;

public class CardSlot extends CardContainer<Card,TiledCardsRotatedLayout> {

	String name;
	
	public CardSlot(String name) {
		super();
		this.name = name;
	}

	@Override
	protected void fillWithLayers(List<Layer> layers) {
		layers.add(createCanvasLayer());

	}

	protected Layer createCanvasLayer() {
		ImageLayer layer = graphics().createImageLayer();
		layer.setSize(100, 100);
		
//		ca.clear();// Color.blue.getRGB());
//		layer. setFillColor(Color.green.getRGB());
//		ca.fillRect(0, 0, cl.width(), cl.height());
//		ca.setFillColor(Color.black.getRGB());
//		ca.drawText(name, 10, cl.width() / 2);
////		
		return layer;
	}

	@Override
	protected TiledCardsRotatedLayout createLayout() {
		return new TiledCardsRotatedLayout(0, 10);
	}

}
