package com.blicksky.radiothermostat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.blicksky.radiothermostat.Constants.ThermostatProgramMode;
import com.google.gson.Gson;

public class RadioThermostatClient {
	
	private static final String THERMOSTAT_STATE_PATH = "/tstat";
	private static final String THERMOSTAT_MODEL_PATH = "/tstat/model";
	private static final String THERMOSTAT_PROGRAM_DAY_PATH = "/tstat/program/{0}/{1}";
	private static final String THERMOSTAT_PROGRAM_WEEK_PATH = "/tstat/program/{0}";
	
	private static final String[] DAY_OF_WEEK_IDENTIFIERS = { "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
	
	private final HttpClient httpClient;
	
	private Gson gson = new Gson();
	
	private final ThermostatStateDeserializer stateDeserializer = new ThermostatStateDeserializer();
	
	private class ThermostatModelResponse {
		public String model;
	}
	
	public RadioThermostatClient() {
		this( new DefaultHttpClient() );
	}
	
	public RadioThermostatClient( final HttpClient httpClient ) {
		this.httpClient = httpClient;
	}
	
	public ThermostatState getThermostatState( final String thermostatAddress ) {
		final String response = executeHttpGet( thermostatAddress, THERMOSTAT_STATE_PATH );
		
		return stateDeserializer.deserialize(response);
	}
	
	public String getThermostatProgram( final String thermostatAddress,  final ThermostatProgramMode mode, final int day ) {
		final int thermostatDay = DayOfWeekConverter.convertToThermostatDayOfWeek( day );
		final String path = MessageFormat.format( THERMOSTAT_PROGRAM_DAY_PATH, mode.toString().toLowerCase(), DAY_OF_WEEK_IDENTIFIERS[thermostatDay] );
	
		final String response = executeHttpGet( thermostatAddress, path );
		
		// TODO: return a new datatype representing this data
		return response;
	}
	
	public String getThermostatProgram( final String thermostatAddress,  final ThermostatProgramMode mode ) {
		final String path = MessageFormat.format( THERMOSTAT_PROGRAM_WEEK_PATH, mode.toString().toLowerCase() );
	
		final String response = executeHttpGet( thermostatAddress, path );
		
		// TODO: return a new datatype representing this data
		return response;
	}
	
	public String getThermostatModel( final String thermostatAddress ) {
		final String response = executeHttpGet( thermostatAddress, THERMOSTAT_MODEL_PATH );
		
		final ThermostatModelResponse thermostatModelResponse = this.gson.fromJson( response, ThermostatModelResponse.class);
		
		return thermostatModelResponse.model;
	}
	
	private String executeHttpGet( final String thermostatAddress, final String path ) {
		String response = null;
		
		try {
			final HttpGet httpget = new HttpGet( new URI("http", thermostatAddress, path, null) );
			
			final ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = this.httpClient.execute(httpget, responseHandler);
		}
		catch( URISyntaxException | IOException exception ) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		
		return response;
	}
	
}