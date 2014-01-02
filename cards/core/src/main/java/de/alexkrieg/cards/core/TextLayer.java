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

import static playn.core.PlayN.graphics;

import java.util.List;

import playn.core.ImageLayer;
import playn.core.Layer;
import de.alexkrieg.cards.core.layout.Layout;

public class TextLayer<L extends Layout<Word>> extends AbstractLayerEntityContainer<Word, L> {


  public TextLayer( String id,  L layout) {
    super(id, layout, graphics().width(), graphics().height());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + super.toString() + ")";
  }

  @Override
  public void init() {
    if (isInitialized())
      return;
    super.init();
    layer().setOrigin(0, 0);
  }

  

  @Override
  protected void fillWithLayers(List<Layer> layers) {
    layers.add(createImageLayer());
  }

  protected Layer createImageLayer() {
    ImageLayer layer = graphics().createImageLayer();
    layer.setSize(width(), height());
    return layer;
  }
  
  
  
  
  
}
