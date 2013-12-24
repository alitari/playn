package de.alexkrieg.cards.java;

import com.google.inject.AbstractModule;

import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.DefaultActionManager;

public class CardsGameModule extends AbstractModule {
  
  @Override
  protected void configure() {
      bind(ActionManager.class).to(DefaultActionManager.class);
  }

}
