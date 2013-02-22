package com.swiftmind.toggle.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class FxmlControllerTest {

	FxmlController sut;
	
	@Before
	public void setup() {
		sut = new FxmlController();
	}
	
	@Test
	public void testGetDateForDayInCurrentWeek() {
		LocalDate date = sut.getDateForDayInCurrentWeek("Do");
		assertNotNull(date);
		assertEquals(4, date.getDayOfWeek());
	}

}
