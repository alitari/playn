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

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.layout.NESWLayout;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import pythagoras.f.Transform;

public class CardTable extends CardContainer<CardSlot> {

	private final Map<Card, CardTableAction> actions = new HashMap<Card, CardTableAction>();


	private final Map<LayerEntity, LayerEntityAction<Card>> actions2 = new HashMap<LayerEntity, LayerEntityAction<Card>>();

	
	public CardTable() {
		super();
	}
	
	
	

	@Override
	public void init() {
		super.init();
		layer().setOrigin(0, 0);
	}




	@Override
	protected void fillWithLayers(List<Layer> layers) {
		layers.add(createImageLayer());

	}

	protected ImageLayer createImageLayer() {
		Image image = assets().getImage("images/cardtable.png");

		final ImageLayer imageLayer = graphics().createImageLayer(image);
		imageLayer.setWidth(500L);
		imageLayer.setHeight(300L);
		return imageLayer;
	}

	@Override
	protected Layout<CardSlot> createLayout() {
		return new NESWLayout(10);
	}

	public void put(CardTableAction cta) {
		actions.put(cta.card, cta);
	}
	
	public void put(LayerEntityAction<Card> cta) {
		actions2.put(cta.le, cta);
	}
	
	public void paint(float alpha) {
		for (LayerEntityAction<Card> lea : actions2.values()) {
			Transform t = lea.le.layer().transform();	
			float x = lea.origX + alpha *( lea.x-lea.origX);
			float y = lea.origY+ alpha *( lea.y-lea.origY);
			float rot = lea.origRot + alpha *( lea.rot-lea.origRot);
			float scale = lea.origScale + alpha *( lea.scale-lea.origScale);
			
			t.setTx( x);
			t.setTy( y);
			t.setRotation(rot);
			t.setUniformScale(scale);
			
		}
	}

	public void update(float delta) {
		for (CardTableAction cta : new ArrayList<CardTableAction>(
				actions.values())) {
			cta.update();
			if (cta.isEnd) {
				actions.remove(cta);
			}
		}
		
		for (LayerEntityAction<Card> lea : new ArrayList<LayerEntityAction<Card>>(actions2.values())) {
			if (lea.isEnd) {
				actions2.remove(lea);
			} else {
				lea.update();
			}
		}
	}
	

	
}
