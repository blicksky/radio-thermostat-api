package com.blicksky.radiothermostat;

import java.util.Calendar;

import org.apache.http.impl.client.DefaultHttpClient;

import com.blicksky.radiothermostat.Constants.ThermostatProgramMode;

public class RadioThermostatClientTester {
	
	public static void main( String[] args ) {
	
		final DefaultHttpClient httpClient = new DefaultHttpClient();
	
		try {
			final RadioThermostatClient thermostatClient = new RadioThermostatClient( httpClient );
			
			ThermostatState state = thermostatClient.getThermostatState("10.10.10.1");
			System.out.println("Current Temperature: " + state.getCurrentTemperature() );
			
			String model = thermostatClient.getThermostatModel("10.10.10.1");
			System.out.println("Model: " + model);
			
			String thursdayProgram = thermostatClient.getThermostatProgram("10.10.10.1", ThermostatProgramMode.Heat, Calendar.THURSDAY);
			System.out.println("Thursday's Program: " + thursdayProgram);
			
			String weekProgram = thermostatClient.getThermostatProgram("10.10.10.1", ThermostatProgramMode.Heat);
			System.out.println("Week's Program: " + weekProgram);
		}
		finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
}
