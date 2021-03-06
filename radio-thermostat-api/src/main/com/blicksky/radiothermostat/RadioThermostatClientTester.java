package com.blicksky.radiothermostat;

import com.blicksky.radiothermostat.Constants.ThermostatProgramMode;

public class RadioThermostatClientTester {
	
	public static void main( String[] args ) {
	
		/*
		{
			final RadioThermostatClient thermostatClient = new RadioThermostatClient();
			
			ThermostatState state = thermostatClient.getThermostatState("10.10.10.1");
			System.out.println("Current Temperature: " + state.getCurrentTemperature() );
			
			String model = thermostatClient.getThermostatModel("10.10.10.1");
			System.out.println("Model: " + model);
			
			String thursdayProgram = thermostatClient.getThermostatProgram("10.10.10.1", ThermostatProgramMode.Heat, Calendar.THURSDAY);
			System.out.println("Thursday's Program: " + thursdayProgram);
			
			String weekProgram = thermostatClient.getThermostatProgram("10.10.10.1", ThermostatProgramMode.Heat);
			System.out.println("Week's Program: " + weekProgram);
		}
		*/
		
		
		{
			FeignRadioThermostatClient client = new FeignRadioThermostatClient("10.10.10.1");
			
			ThermostatState state = client.getThermostatState();
			System.out.println("Current Temperature: " + state.getCurrentTemperature() );
			
			String model = client.getThermostatModel();
			System.out.println("Model: " + model);
			
			String weekProgram = client.getThermostatProgram( ThermostatProgramMode.HEAT );
			System.out.println("Week's Program: " + weekProgram);
		}
	}
}
