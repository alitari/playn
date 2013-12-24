package de.alexkrieg.cards.core.action;

public interface GameAction {

  void execute();

  int getDuration();

  void paint(int tick, float alpha);

  public static class Merge implements GameAction {

    private final GameAction action1;
    private final GameAction action2;

    public Merge(GameAction action1, GameAction action2) {
      super();
      this.action1 = action1;
      this.action2 = action2;
    }

    @Override
    public void execute() {
      action1.execute();
      action2.execute();
    }

    @Override
    public int getDuration() {
      return Math.max(action1.getDuration(), action2.getDuration());
    }

    @Override
    public void paint(int tick, float alpha) {
      action1.paint(tick, alpha);
      action2.paint(tick, alpha);
    }

  }

}
