package com.blicksky.radiothermostat;

import java.util.Calendar;

import com.blicksky.radiothermostat.Constants.FanOperatingMode;
import com.blicksky.radiothermostat.Constants.FanOperatingState;
import com.blicksky.radiothermostat.Constants.HVACOperatingState;
import com.blicksky.radiothermostat.Constants.ThermostatOperatingMode;
import com.blicksky.radiothermostat.Constants.ThermostatProgramMode;
import com.blicksky.radiothermostat.RadioThermostat.StateResponse;

import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;


public class FeignRadioThermostatClient {
	
	private final RadioThermostat thermostat;
	
	public FeignRadioThermostatClient( final String ipAddress ) {
		this.thermostat = Feign.builder()
							.decoder(new GsonDecoder())
							.target( RadioThermostat.class, "http://" + ipAddress );
	}
	
	
	
	public ThermostatState getThermostatState() {
		StateResponse state = this.thermostat.getState();
		
		ThermostatOperatingMode thermostatOperatingMode = this.getEnumValueByOrdinal( ThermostatOperatingMode.class, state.tmode);
		FanOperatingMode fanOperatingMode = this.getEnumValueByOrdinal( FanOperatingMode.class, state.fmode);
		HVACOperatingState hvacOperatingState = this.getEnumValueByOrdinal( HVACOperatingState.class, state.tmode);
		FanOperatingState fanOperatingState = this.getEnumValueByOrdinal( FanOperatingState.class, state.fmode);

		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, DayOfWeekConverter.convertToCalendarDayOfWeek( state.time.day ) );
		cal.set(Calendar.HOUR_OF_DAY, state.time.hour);
		cal.set(Calendar.MINUTE, state.time.minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new ThermostatState(state.temp, thermostatOperatingMode, fanOperatingMode, state.override != 0, state.hold != 0, hvacOperatingState, fanOperatingState, cal.getTime());
	}

	
	public String getThermostatModel() {
		return this.thermostat.getModel().model;
	}
	
	/*
	public String getThermostatProgram( final String thermostatAddress,  final ThermostatProgramMode mode, final int day ) {
		final int thermostatDay = DayOfWeekConverter.convertToThermostatDayOfWeek( day );
		final String path = MessageFormat.format( THERMOSTAT_PROGRAM_DAY_PATH, mode.toString().toLowerCase(), DAY_OF_WEEK_IDENTIFIERS[thermostatDay] );
	
		final String response = executeHttpGet( thermostatAddress, path );
		
		// TODO: return a new datatype representing this data
		return response;
	}
	*/
	
	public String getThermostatProgram( final ThermostatProgramMode mode ) {
		Response program = this.thermostat.getProgram( mode.toString().toLowerCase() );
		
		return program.body().toString();
	}

	private <T extends Enum<?>> T getEnumValueByOrdinal( Class<T> enumType, int ordinal ) {
		T value = null;
		
		T[] values = enumType.getEnumConstants();
		if( ordinal >= 0 && ordinal < values.length ) {
			value = values[ordinal];
		}
		
		return value;
	}
	
}