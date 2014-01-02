package de.alexkrieg.cards.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.io.UnsupportedEncodingException;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Layer.HasSize;

public class Letter extends AbstractLayerEntity implements HasSizeEntity {

  static private String DIR_CARDS = "images/letters/";

  public final char code;
  
  public final String fontName;
  

  private ImageLayer imageLayer;

  @Override
  public HasSize hasSize() {
    return imageLayer;
  }

  public Letter(char val,String fontName) {
    super(String.valueOf(val));
    this.code = val;
    this.fontName = fontName;
  }

  @Override
  public void init() {
    super.init();
  }
  

  @Override
  protected Layer createLayer() {
    imageLayer = createImageLayer(createImage(this.code));
    GroupLayer groupLayer = graphics().createGroupLayer();
    groupLayer.add(imageLayer);
    groupLayer.setOrigin(hasSize().width() / 2, hasSize().height() / 2);
    return groupLayer;
  }

  public ImageLayer createImageLayer(Image image) {
    ImageLayer imageLayer = graphics().createImageLayer(image);
    imageLayer.setWidth(85);
    imageLayer.setHeight(77);
    return imageLayer;
  }

  public Image createImage(String fileName) {
    Image bgImage = assets().getImage(DIR_CARDS +fontName+"/"+fileName);
    return bgImage;
  }

  public Image createImage(char value) {
    return createImage(filename());
  }

  @Override
  public String toString() {

    return getClass().getSimpleName() + "(value=" + code + ")";
  }

  public String filename() {
    return "0x00"+Integer.toHexString(code).toUpperCase()+".png";
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    char c ='Z'; 
    byte code = (byte) c;
    String hexString = "0x00"+Integer.toHexString(code).toUpperCase();
    
//    String decimalstr = new String(new byte[]{ascii},"US-ASCII");
//    double val = Double.parseDouble(decimalstr);
//    String hexstr = Double.toHexString(val);
    System.out.println(hexString);

  }

}
