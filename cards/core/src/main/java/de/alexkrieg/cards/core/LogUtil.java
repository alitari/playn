package de.alexkrieg.cards.core;

import playn.core.Layer;
import pythagoras.f.Point;

public class LogUtil {
  
  public static String logString(int level,LayerEntity le) {
    return stringBufferLevel(level).append(le+"("+ logString(le.layer())+")").toString();
  }

  private static StringBuffer stringBufferLevel(int level) {
    StringBuffer strb = new StringBuffer();
    if ( level == 0) return strb;
    strb.append(String.format("%"+level+"s", "-"));
    return strb;
  }

  private static String logString(Layer l) {
    Point screenPoint = Layer.Util.layerToScreen(l,l.tx(),l.ty());
    String str = "Layer: origin(x" + l.originX() +"/"+screenPoint.x+",y" + l.originY() + "/"+screenPoint.y+"), translation(" + l.tx()
        + "," + l.ty() + "), rotation:" + l.rotation() + " scale:(" + l.scaleX() + "," + l.scaleY()
        + ")";
    return str;
  }
  
  public static String logString(int level, LayerEntityContainer<?,?> le) {
    StringBuffer strb = stringBufferLevel(level).append(logString(0,(LayerEntity) le));
    int i = 1;
    for ( LayerEntity l: le.childs()) {
      strb.append("\n"). append(i++).append(".").append( l instanceof LayerEntityContainer<?,?> ?  logString(level+2,(LayerEntityContainer<?,?>)l):logString(level+2,l));
    }
    return strb.toString();
  }
  
  
//  
  public static String logString(CardGame<?,?,?> game) {
    StringBuffer strb = new StringBuffer(game.toString());
    strb.append("(\n-- actionManager:").append(logString(game.actionManager)).append("\n");
    strb.append("-- table:").append(logString(2,game.cardTable)).append("\n");
    strb.append("-- players:").append(logString(game.playerRegistry)).append("\n");
    strb.append("\n");
    return strb.toString();
    
  }
  
  
  public static String  logString(PlayerRegistry<?> playerRegistry) {
    StringBuffer strb = new StringBuffer(playerRegistry.getClass().getSimpleName());
    return strb.toString();
  }

  public static String logString(ActionManager actionManager) {
    StringBuffer strb = new StringBuffer(actionManager.getClass().getSimpleName());
    return strb.toString();
  }

}
