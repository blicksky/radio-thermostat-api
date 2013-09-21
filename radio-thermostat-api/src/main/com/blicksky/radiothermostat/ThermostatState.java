package com.blicksky.radiothermostat;

import java.util.Date;

import com.blicksky.radiothermostat.Constants.FanOperatingMode;
import com.blicksky.radiothermostat.Constants.FanOperatingState;
import com.blicksky.radiothermostat.Constants.HVACOperatingState;
import com.blicksky.radiothermostat.Constants.ThermostatOperatingMode;


//{"temp":68.50,"tmode":0,"fmode":0,"override":0,"hold":0,"tstate":0,"fstate":0,"time":{"day":0,"hour":20,"minute":8},"t_type_post":0}

public class ThermostatState {
	
	private final float currentTemperature;
	private final ThermostatOperatingMode thermostatOperatingMode;
	private final FanOperatingMode fanOperatingMode;
	private final boolean targetTemperatureOverrideEnabled;
	private final boolean targetTemperatureHoldEnabled;
	private final HVACOperatingState hvacOperatingState;
	private final FanOperatingState fanOperatingState;
	private final Date time;
	
	public ThermostatState(float currentTemperature, ThermostatOperatingMode thermostatOperatingMode, FanOperatingMode fanOperatingMode, boolean targetTemperatureOverrideEnabled, boolean targetTemperatureHoldEnabled, HVACOperatingState hvacOperatingState, FanOperatingState fanOperatingState, Date time) {
		this.currentTemperature = currentTemperature;
		this.thermostatOperatingMode = thermostatOperatingMode;
		this.fanOperatingMode = fanOperatingMode;
		this.targetTemperatureOverrideEnabled = targetTemperatureOverrideEnabled;
		this.targetTemperatureHoldEnabled = targetTemperatureHoldEnabled;
		this.hvacOperatingState = hvacOperatingState;
		this.fanOperatingState = fanOperatingState;
		this.time = time;
	}

	public float getCurrentTemperature() {
		return currentTemperature;
	}

	public ThermostatOperatingMode getThermostatOperatingMode() {
		return thermostatOperatingMode;
	}

	public FanOperatingMode getFanOperatingMode() {
		return fanOperatingMode;
	}

	public boolean isTargetTemperatureOverrideEnabled() {
		return targetTemperatureOverrideEnabled;
	}

	public boolean isTargetTemperatureHoldEnabled() {
		return targetTemperatureHoldEnabled;
	}

	public HVACOperatingState getHvacOperatingState() {
		return hvacOperatingState;
	}

	public FanOperatingState getFanOperatingState() {
		return fanOperatingState;
	}

	public Date getTime() {
		return time;
	}
	
}