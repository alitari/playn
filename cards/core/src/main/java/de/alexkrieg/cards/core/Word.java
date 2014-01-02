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
import de.alexkrieg.cards.core.layout.WordLayout;
import de.alexkrieg.cards.core.layout.WordLayout.Attr;

public class Word extends AbstractLayerEntityContainer<Letter, WordLayout> {
  
  private final String fontName;

  public Word(String id, int gap,String fontName) {
    super(id, new WordLayout(gap), graphics().width(), graphics().height());
    this.fontName = fontName;
  }

  @Override
  protected void fillWithLayers(List<Layer> layers) {
    layers.add(createCanvasLayer());
  }

  protected Layer createCanvasLayer() {
    ImageLayer layer = graphics().createImageLayer();
    layer.setSize(width(), height());
    return layer;
  }

  @Override
  public void init() {
    if (isInitialized())
      return;
    super.init();
    layer().setOrigin(0, 0);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(id=" + id() + "," + super.toString() + ")";
  }

  public void setText(String text) {
    Attr attr = new WordLayout.Attr(1, 0, 1);
    for (int i = 0; i < text.length(); i++) {
      put(new Letter(text.charAt(i),this.fontName), attr);
    }
  }

}
