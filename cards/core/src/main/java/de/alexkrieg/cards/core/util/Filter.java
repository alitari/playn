package de.alexkrieg.cards.core.util;

import java.util.List;


public abstract class Filter<T> {
  
  public abstract boolean apply(T candidate, List<T> cardSet);
  
  

  public static class And<T> extends Filter<T> {
    final Filter<T> f1;
    final Filter<T> f2;
    final boolean and;

    public And(Filter<T> f1, Filter<T> f2, boolean and) {
      super();
      this.f1 = f1;
      this.f2 = f2;
      this.and = and;
    }

    @Override
    public boolean apply(T candidate, List<T> cardSet) {
      boolean apply1 = f1.apply(candidate, cardSet);
      boolean apply2 = f2.apply(candidate, cardSet);
      return (!and && (apply1 || apply2)) || (and && apply1 && apply2);
    }
  }

  public static class Not<T> extends Filter<T> {
    final Filter<T> f;

    public Not(Filter<T> f) {
      super();
      this.f = f;
    }

    @Override
    public boolean apply(T candidate, List<T> cardSet) {
      return !f.apply(candidate, cardSet);
    }
  }

  
  
  public static <T> List<T> applyFilter(Filter<T> f, List<T> cards,List<T> results) {
    for (T c : cards) {
      if (f.apply(c, results)) {
        results.add(c);
      }
    }
    return results;
  }


}
