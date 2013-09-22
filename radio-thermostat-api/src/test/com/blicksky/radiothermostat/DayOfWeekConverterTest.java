package com.blicksky.radiothermostat;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DayOfWeekConverterTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testWhenThermostatDayOfWeekIndecesAreConverteredThenCorrectCalendarDayOfWeekIndecesAreReturned() {
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(0), Calendar.MONDAY );
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(1), Calendar.TUESDAY );
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(2), Calendar.WEDNESDAY );
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(3), Calendar.THURSDAY );
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(4), Calendar.FRIDAY );
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(5), Calendar.SATURDAY );
		Assert.assertEquals( DayOfWeekConverter.convertToCalendarDayOfWeek(6), Calendar.SUNDAY );
	}
	
	@Test
	public void testWhenCalendarDayOfWeekIndecesAreConverteredThenCorrectThermostatDayOfWeekIndecesAreReturned() {
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.MONDAY),		0 );
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.TUESDAY),		1 );
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.WEDNESDAY),	2 );
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.THURSDAY),	3 );
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.FRIDAY),		4 );
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.SATURDAY),	5 );
		Assert.assertEquals( DayOfWeekConverter.convertToThermostatDayOfWeek(Calendar.SUNDAY),		6 );
	}
	
}