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

import de.alexkrieg.cards.core.action.CardTableAction;
import de.alexkrieg.cards.core.action.LayerEntityAction;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.core.layout.TiledCardsRotatedLayout;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import pythagoras.f.Transform;

public class CardTable extends AbstractLayerEntityContainer<CardSlot,NESWLayout> {

	
	
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
		imageLayer.setWidth(graphics().width());
		imageLayer.setHeight(graphics().height());
		return imageLayer;
	}

	@Override
	protected NESWLayout createLayout() {
		return new NESWLayout(10);
	}

	
	
//	public void paint(float alpha) {
//		for (LayerEntityAction<Card,TiledCardsRotatedLayout> lea : actions2.values()) {
//			Transform t = lea.le.layer().transform();	
//			float x = lea.origX + alpha *( lea.x-lea.origX);
//			float y = lea.origY+ alpha *( lea.y-lea.origY);
//			float rot = lea.origRot + alpha *( lea.rot-lea.origRot);
//			float scale = lea.origScale + alpha *( lea.scale-lea.origScale);
//			
//			t.setTx( x);
//			t.setTy( y);
//			t.setRotation(rot);
//			t.setUniformScale(scale);
//			
//		}
//	}

//	public void update(float delta) {
//		for (CardTableAction cta : new ArrayList<CardTableAction>(
//				actions.values())) {
//			cta.update();
//			if (cta.isEnd) {
//				actions.remove(cta);
//			}
//		}
//		
//		for (LayerEntityAction<Card,TiledCardsRotatedLayout> lea : new ArrayList<LayerEntityAction<Card,TiledCardsRotatedLayout>>(actions2.values())) {
//			if (lea.isEnd) {
//				actions2.remove(lea);
//			} else {
//				lea.update();
//			}
//		}
//	}
	

	
}
