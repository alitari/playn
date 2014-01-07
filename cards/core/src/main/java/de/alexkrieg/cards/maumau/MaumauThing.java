package de.alexkrieg.cards.maumau;

import de.alexkrieg.cards.core.Thing;

public class MaumauThing extends Thing {

  public MaumauThing(String id) {
    super(id, id, 96, 96, 1);
  }

  public static class Packy extends MaumauThing {
    public static String ID = "packy";

    public Packy() {
      super(ID);
    }
  }

  public static class Mighta extends MaumauThing {
    public static String ID = "mighta";

    public Mighta() {
      super(ID);
    }
  }

  public static class Woolen extends MaumauThing {
    public static String ID = "woolen";

    public Woolen() {
      super(ID);
    }
  }

}
