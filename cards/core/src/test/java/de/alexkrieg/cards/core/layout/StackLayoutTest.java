package de.alexkrieg.cards.core.layout;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import playn.java.JavaPlatform;
import de.alexkrieg.cards.core.LayerEntity;

public class StackLayoutTest {
  
  private LayerEntity layerEntity;

  static {
    JavaPlatform.register();
  }
  
    
  
  @Before
  public void setup() {
    layerEntity = mock(LayerEntity.class);
  }

  @Test
  public void test() {
  }

}
