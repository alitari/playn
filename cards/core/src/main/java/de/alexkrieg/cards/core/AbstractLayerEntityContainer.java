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

import static de.alexkrieg.cards.core.util.Filter.applyFilter;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Layer;
import pythagoras.f.Transform;
import de.alexkrieg.cards.core.action.GameLogicAction;
import de.alexkrieg.cards.core.layout.Layout;
import de.alexkrieg.cards.core.util.Filter;

public abstract class AbstractLayerEntityContainer<T extends LayerEntity, L extends Layout<T>>
    extends AbstractLayerEntity implements LayerEntityContainer<T, L> {

  private float width = 0;
  private float height = 0;

  protected final L layout;

  protected List<T> childs;

  protected AbstractLayerEntityContainer(String id, L layout, float width, float height) {
    super(id);
    childs = new ArrayList<T>();
    this.layout = layout;
    this.layout.setContainer(this);
    this.width = width;
    this.height = height;
  }

  @Override
  public String toString() {
    return "(layout=" + layout + "," + super.toString() + ")";
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
  public List<T> childs() {
    return childs;
  }

  public List<T> filterChilds(Filter<T> filter) {
    return applyFilter(filter, childs, new ArrayList<T>());
  }

  public T findChild(final String id) {
    List<T> result = filterChilds(new Filter<T>() {
      @Override
      public boolean apply(T candidate, List<T> cardSet) {
        return candidate.id().equals(id);
      }
    });
    return result.isEmpty() ? null : result.get(0);
  }

  @Override
  public List<T> getLastUnusedChilds(int count) {
    return getUnusedChilds(count, true);
  }

  @Override
  public List<T> getFirstUnusedChilds(int count) {
    return getUnusedChilds(count, false);
  }

  private List<T> getUnusedChilds(int count, boolean fromTop) {
    List<T> results = new ArrayList<T>();
    for (int i = fromTop ? childs.size() - 1 : 0; fromTop ? i >= 0 : i < childs.size(); i = fromTop
        ? i - 1 : i + 1) {
      T child = childs.get(i);
      if (child.getInUseAction() == null) {
        results.add(child);
        if (results.size() >= count) {
          return results;
        }
      }
    }
    return results;
  }

  @Override
  public L layout() {
    return this.layout;
  }

  @Override
  public void init() {
    if (isInitialized())
      return;
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
    if (layout.needsRecalcWhenRemove()) {
      for (T c : childs) {
        layout.recalc(c, null);
        Layer l = c.layer();
        transform(l, layout.x(child), layout.y(child), layout.rot(child), layout.scale(child));
        l.setDepth(layout.depth(child));
        l.setAlpha(layout.alpha(child));
      }
    }
  }

  @Override
  public void put(T child, Object param) {
    child.init();
    LayerEntityContainer<T, ?> childContainer = (LayerEntityContainer<T, ?>) child.getContainer();
    if (childContainer != null && childContainer!= this) {
      childContainer.remove(child);
    }
    child.setContainer(this);

    layout.recalc(child, param);

    Layer l = child.layer();
    transform(l, layout.x(child), layout.y(child), layout.rot(child), layout.scale(child));
    l.setDepth(layout.depth(child));
    l.setAlpha(layout.alpha(child));
    
    ((GroupLayer) layer()).add(l);
    
    childs.add(child);
  }

  private void transform(Layer l, float x, float y, float rot, float scale) {
    Transform t = l.transform();
    t.setTx(x);
    t.setTy(y);
    t.setRotation(rot);
    t.setUniformScale(scale);

  }

  // @Override
  // public GroupLayer layer() {
  // return layer;
  // }

  @Override
  public void removeAll() {
    childs.clear();
    ((GroupLayer) layer()).clear();
  }

}
