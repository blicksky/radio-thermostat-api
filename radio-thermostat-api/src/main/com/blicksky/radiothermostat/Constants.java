package com.blicksky.radiothermostat;

public class Constants {
	
	public enum ThermostatOperatingMode implements OrdinalBasedEnum {
		OFF, HEAT, COOL, AUTO;
	}
	
	public enum HVACOperatingState implements OrdinalBasedEnum {
		OFF, HEAT, COOL;
	}
	
	public enum FanOperatingMode implements OrdinalBasedEnum {
		Auto, AUTO_CIRCULATE, ON;
	}
	
	public enum FanOperatingState implements OrdinalBasedEnum {
		OFF, ON;
	}
	
	public enum ThermostatProgramMode {
		HEAT, COOL
	}
}