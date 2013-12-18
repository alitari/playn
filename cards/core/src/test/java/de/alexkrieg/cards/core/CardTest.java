package de.alexkrieg.cards.core;

import static de.alexkrieg.cards.core.Card.matches;
import static de.alexkrieg.cards.core.Card.matchesNot;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import playn.java.JavaPlatform;
import de.alexkrieg.cards.core.Card.Value;

public class CardTest {

  static {
    JavaPlatform.register();
  }

  @Test
  public void testInit() {
    Card card = new Card(Value._2c);
    assertThat(card.layer(), nullValue());
    card.init();
    assertThat(card.layer(), notNullValue());

  }

  @Test
  public void matchesFilter() throws Exception {
    List<Card> cards = new ArrayList<Card>();
    cards.add(new Card(Value._3d));
    cards.add(new Card(Value._4c));
    cards.add(new Card(Value._5s));
    cards.add(new Card(Value._6s));

    assertThat(matches(new Card(Value._7d), cards).get(0).value, is(Value._3d));
    assertThat(matches(new Card(Value._td), cards).get(0).value, is(Value._3d));
    assertThat(matches(new Card(Value._7c),cards).get(0).value,is(Value._4c));
    assertThat(matches(new Card(Value._js),cards).get(0).value,is(Value._5s));
    assertThat(matches(new Card(Value._js),cards).get(1).value,is(Value._6s));
    assertThat(matches(new Card(Value._qh),cards).isEmpty(),is(true));

  }
  
  @Test
  public void matchesNotFilter() throws Exception {
    List<Card> cards = new ArrayList<Card>();
    Card card3d = new Card(Value._3d);
    Card card4c = new Card(Value._4c);
    Card card5s = new Card(Value._5s);
    Card card6s = new Card(Value._6s);
    cards.add(card3d);
    cards.add(card4c);
    cards.add(card5s);
    cards.add(card6s);

    assertThat(matchesNot(new Card(Value._7d), cards), not(hasItem(card3d)));
    assertThat(matchesNot(new Card(Value._7d), cards), hasItems(card4c,card5s,card6s));

  }


}
