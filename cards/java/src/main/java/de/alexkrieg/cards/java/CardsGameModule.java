package de.alexkrieg.cards.java;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.alexkrieg.cards.core.ActionManager;
import de.alexkrieg.cards.core.layout.NESWLayout;
import de.alexkrieg.cards.maumau.MaumauActionManager;
import de.alexkrieg.cards.maumau.MaumauCardtable;
import de.alexkrieg.cards.maumau.MaumauGameLogic;
import de.alexkrieg.cards.maumau.MaumauPlayerRegistry;

public class CardsGameModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MaumauPlayerRegistry.class).in(Singleton.class);
    bind(MaumauCardtable.class).in(Singleton.class);
    bind(ActionManager.class).to(MaumauActionManager.class).in(Singleton.class);
    bind(MaumauGameLogic.class).in(Singleton.class);
    // bind(new
    // TypeLiteral<CardTable<NESWLayout,MaumauRobotPlayer,MaumauGameLogic>>(){}).to(MaumauCardtable.class).in(Singleton.class);
    // bind(new
    // TypeLiteral<PlayerRegistry<NESWLayout,MaumauRobotPlayer,MaumauGameLogic>>(){}).to(MaumauPlayerRegistry.class).in(Singleton.class);
    // bind(new TypeLiteral<GameLogic<NESWLayout,
    // MaumauRobotPlayer,MaumauGameLogic>>(){}).to(MaumauGameLogic.class).in(Singleton.class);
  }

  @Provides
  NESWLayout provideNESWLayout() {
    NESWLayout neswLayout = new NESWLayout(10);
    return neswLayout;
  }

}
