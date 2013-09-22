package com.blicksky.radiothermostat;

public class DayOfWeekConverter {
	
	// translate thermostat days (0-indexed starting at Mon) to Java Calendar days (1-indexed starting at Sun)
	public static int convertToCalendarDayOfWeek( final int thermostatDayOfWeek ) {
		return ( ( thermostatDayOfWeek + 1 ) % 7 ) + 1;
	}
	
	// translate Java Calendar days (1-indexed starting at Sun) to thermostat days (0-indexed starting at Mon)
	public static int convertToThermostatDayOfWeek( final int calendarDayOfWeek ) {
		return ( calendarDayOfWeek + 5 ) % 7;
	}
}