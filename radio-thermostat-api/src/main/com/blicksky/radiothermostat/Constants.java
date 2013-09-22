package com.blicksky.radiothermostat;

public class Constants {
	
	public enum ThermostatOperatingMode implements OrdinalBasedEnum {
		Off, Heat, Cool, Auto;
	}
	
	public enum HVACOperatingState implements OrdinalBasedEnum {
		Off, Heat, cool;
	}
	
	public enum FanOperatingMode implements OrdinalBasedEnum {
		Auto, AutoCirculate, On;
	}
	
	public enum FanOperatingState implements OrdinalBasedEnum {
		Off, On;
	}
	
	public enum ThermostatProgramMode {
		Heat, Cool
	}
}