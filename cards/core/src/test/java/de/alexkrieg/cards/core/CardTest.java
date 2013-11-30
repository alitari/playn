package de.alexkrieg.cards.core;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CardTest {

	@Test
	public void testInit() {
		Card card = new Card(Card.Value._2c);
		assertThat(card.layer(),nullValue());
		card.init();
		//assertThat(card.layer(),notNullValue());
		
	}

}
