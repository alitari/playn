/**
 * Copyright 2011 The PlayN Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.alexkrieg.cards.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.List;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import de.alexkrieg.cards.core.layout.Layout;

public class CardTable<L extends Layout<CardSlot<?>>> extends
    AbstractLayerEntityContainer<CardSlot<?>, L> {

  public CardTable(L layout) {
    super(layout);
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

  // @Override
  // protected NESWLayout createLayout() {
  // return new NESWLayout(10);
  // }

}
