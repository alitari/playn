package de.alexkrieg.cards.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;

public class Thing extends AbstractLayerEntity implements HasSizeEntity {

  static private String DIR = "images/objects/";

  
  public final String name;

  final private int width;

  final private int height;

  final private int animationSteps;
  
  private int currentAnimationStep;
  
  
  ImageLayer[] imageLayer;

  @Override
  public HasSize hasSize() {
    return (HasSize) imageLayer[0];
  }

  public Thing(String id,String name,int width,int height, int animationSteps) {
    super(id);
    this.name = name;
    this.width = width;
    this.height = height;
    this.animationSteps = animationSteps;
  }

  @Override
  public void init() {
    if (isInitialized()) return;
    imageLayer = new ImageLayer[this.animationSteps];
    super.init();
  }
  
  public void nextAnimationStep() {
    imageLayer[currentAnimationStep].setVisible(false);
    currentAnimationStep = currentAnimationStep == animationSteps ? 0: currentAnimationStep +1; 
    imageLayer[currentAnimationStep].setVisible(true);
  }

  @Override
  protected Layer createLayer() {
    GroupLayer groupLayer = graphics().createGroupLayer();
    for ( int i = 0; i< animationSteps;i++) {
      ImageLayer imageLayer = createImageLayer(createImage(name,i));
      groupLayer.add(imageLayer);
    }
    groupLayer.setOrigin( width / 2, height / 2);
    return groupLayer;
  }

  public ImageLayer createImageLayer(Image image) {
    ImageLayer imageLayer = graphics().createImageLayer(image);
    imageLayer.setWidth(width);
    imageLayer.setHeight(height);
    return imageLayer;
  }

  public Image createImage(String fileName, int i) {
    Image bgImage = assets().getImage(DIR + fileName+String.format("%02d", i)+".png");
    return bgImage;
  }
  
  @Override
  public String toString() {
    return getClass().getSimpleName() + "(value=" + id() + ",name="+name+")";
  }
  
  public static class TomTom extends Thing {

    public TomTom(String id) {
      super(id, "TomTom", 32, 32, 4);
    }
    
  }

  
}
