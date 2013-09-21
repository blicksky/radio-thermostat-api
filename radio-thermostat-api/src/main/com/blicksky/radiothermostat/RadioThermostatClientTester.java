package com.blicksky.radiothermostat;

import org.apache.http.impl.client.DefaultHttpClient;

public class RadioThermostatClientTester {
	
	public static void main( String[] args ) {
	
		final DefaultHttpClient httpClient = new DefaultHttpClient();
	
		try {
			final RadioThermostatClient thermostatClient = new RadioThermostatClient( httpClient );
			
			ThermostatState state = thermostatClient.getThermostatState("10.10.10.1");
			System.out.println("Current Temperature: " + state.getCurrentTemperature() );
			
			String model = thermostatClient.getThermostatModel("10.10.10.1");
			System.out.println(model);
		}
		finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
}
