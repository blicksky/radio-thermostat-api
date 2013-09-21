package com.blicksky.radiothermostat;

import java.text.MessageFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ThermostatStateDeserializerTest {
	
	private ThermostatStateDeserializer deserializer = null;
	
	@Before
	public void setUp() {
		this.deserializer = new ThermostatStateDeserializer();
	}
	
	@Test
	public void testWhenDayIndicesAreParsedInThermostatStateResponsesThenCorrectDayOfWeekValueIsSetInDateObject() {
		// day constants in the array indices that the thermostat day value corresponds to 
		final int[] days = {
			Calendar.MONDAY,
			Calendar.TUESDAY,
			Calendar.WEDNESDAY,
			Calendar.THURSDAY, 
			Calendar.FRIDAY,
			Calendar.SATURDAY,
			Calendar.SUNDAY
		};
		
		final Calendar calendar = Calendar.getInstance();
		
		// test for each day of the week
		for( int day = 0; day < 7; ++day ) {
			// create JSON for the day and deserialize it
			ThermostatState thermostatState = this.deserializer.deserialize( getThermostatStateTimeJson(day, 12, 30) );
			
			calendar.setTime( thermostatState.getTime() );
			
			Assert.assertEquals( calendar.get( Calendar.DAY_OF_WEEK ), days[day] );
		}
	}
	
	private String getThermostatStateTimeJson( final int day, final int hour, final int minute ) {
		return MessageFormat.format( "'{' time: '{' day: {0}, hour: {1}, minute: {2} '}' '}'", day, hour, minute );
	}
	
}