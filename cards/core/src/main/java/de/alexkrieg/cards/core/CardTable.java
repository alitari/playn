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

import playn.core.Font;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.util.Clock;
import tripleplay.ui.Background;
import tripleplay.ui.Interface;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class CardTable<G extends GameLogic, L extends Layout<CardSlot<?>>> extends
    AbstractLayerEntityContainer<CardSlot<?>, L> {

  public static final Font TITLE_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 36);

  protected final G gameLogic;

  protected Interface iface;
  
  protected final ActionManager actionManager;

  public CardTable(G gameLogic, ActionManager actionManager, L layout) {
    super(layout, graphics().width(), graphics().height());
    this.gameLogic = gameLogic;
    this.actionManager = actionManager;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + super.toString() + ")";
  }

  @Override
  public void init() {
    if (isInitialized())
      return;
    iface = new Interface();
    super.init();
    layer().setOrigin(0, 0);
  }

  public void paint(Clock.Source _clock) {
    if (iface != null) {
      iface.paint(_clock);
    }
  }

  public void update(int delta) {
    if (iface != null) {
      iface.update(delta);
    }
  }

  @Override
  protected void fillWithLayers(List<Layer> layers) {
    layers.add(createTableLayer());
    layers.add(createHUDLayer());
  }

  protected Layer createHUDLayer() {
    // create our demo interface
    final Root root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet());
    root.setSize(width(), height());
    root.addStyles(Style.BACKGROUND.is(Background.blank().inset(5)));

    fillHUDRoot(root);
    // root.layer.setDepth(10000);
    return root.layer;
  }

  protected abstract void fillHUDRoot(final Root root);

  protected Layer createTableLayer() {
    Image image = assets().getImage("images/cardtable.png");
    final ImageLayer imageLayer = graphics().createImageLayer(image);
    imageLayer.setWidth(width());
    imageLayer.setHeight(height());
    return imageLayer;
  }

}
