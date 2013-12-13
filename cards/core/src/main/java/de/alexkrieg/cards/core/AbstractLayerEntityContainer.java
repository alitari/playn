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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import playn.core.GroupLayer;
import playn.core.Layer;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.layout.Layout;

public abstract class AbstractLayerEntityContainer<T extends LayerEntity, L extends Layout<T>>
    extends AbstractLayerEntity implements LayerEntityContainer<T, L> {

  private float width = 0;
  private float height = 0;

  protected final L layout;

  protected Set<T> childs;

  protected AbstractLayerEntityContainer(L layout, float width, float height) {
    super();
    childs = new HashSet<T>();
    this.layout = layout;
    this.layout.setContainer(this);
    this.width = width;
    this.height = height;
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
  public Set<T> childs() {
    return childs;
  }

  @Override
  public L layout() {
    return this.layout;
  }

  @Override
  public void init() {
    if (isInitialized()) return;
    super.init();
    List<Layer> layers = new ArrayList<Layer>();
    fillWithLayers(layers);
    GroupLayer groupLayer = (GroupLayer) layer();
    for (Layer l : layers) {
      groupLayer.add(l);
    }
    groupLayer.setOrigin(width / 2, height / 2);

  }

  protected abstract void fillWithLayers(List<Layer> layers);

  @Override
  protected Layer createLayer() {
    return graphics().createGroupLayer();
  }

  @Override
  public void remove(T child) {
    child.setContainer(null);
    childs.remove(child);
  }

  @Override
  public void put(T child, Object param) {
    child.init();
    LayerEntityContainer<T, ?> childContainer = (LayerEntityContainer<T, ?>) child.getContainer();
    if (childContainer != null) {
      childContainer.remove(child);
    }
    child.setContainer(this);

    layout.recalc(child, param);
    put(child, layout.x(child), layout.y(child), layout.rot(child), layout.scale(child));
    childs.add(child);
  }

  private void put(T child, float x, float y, float rot, float scale) {
    Layer l = child.layer();
    Transform t = l.transform();
    t.setTx(x);
    t.setTy(y);
    t.setRotation(rot);
    t.setUniformScale(scale);

    ((GroupLayer) layer()).add(l);
  }

}
