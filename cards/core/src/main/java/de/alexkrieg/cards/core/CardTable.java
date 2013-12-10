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

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;
import playn.core.Layer;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import de.alexkrieg.cards.core.layout.Layout;

public class CardTable<L extends Layout<CardSlot<?>>> extends
    AbstractLayerEntityContainer<CardSlot<?>, L> {
  
  
  public static final Font TITLE_FONT = graphics().createFont("Helvetica", Font.Style.PLAIN, 36);
  

  protected final CardGame cardGame;
  
  

  private Interface iface;

  public CardTable(CardGame<?,?> cardGame, L layout) {
    super(layout, graphics().width(), graphics().height());
    this.cardGame = cardGame;
  }

  public Interface iface() {
    return iface;
  }

  @Override
  public void init() {
    if (isInitialized())
      return;
    iface = new Interface();
    super.init();
    layer().setOrigin(0, 0);
  }

  @Override
  protected void fillWithLayers(List<Layer> layers) {
    layers.add(createTableLayer());
    layers.add(createHUDLayer());
  }

    

  private Layer createHUDLayer() {
    // create our demo interface
    final Root root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet());
    root.setSize(width(), height());
    root.addStyles(Style.BACKGROUND.is(Background.blank().inset(5)));

    Group buttons;
    root.add(new Label("Card Table"), buttons = new Group(AxisLayout.vertical().offStretch())).addStyles(
        Style.TEXT_WRAP.is(true));

    Button button = new Button("Start");
    buttons.add(button);
    button.clicked().connect(new UnitSlot() {
      @Override
      public void onEmit() {
        root.add(new Label("Game started"));

      }
    });

    return root.layer;
  }

  protected Layer createTableLayer() {
    Image image = assets().getImage("images/cardtable.png");
    final ImageLayer imageLayer = graphics().createImageLayer(image);
    imageLayer.setWidth(width());
    imageLayer.setHeight(height());
    return imageLayer;
  }

  // @Override
  // protected NESWLayout createLayout() {
  // return new NESWLayout(10);
  // }

}
