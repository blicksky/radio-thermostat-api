package com.blicksky.radiothermostat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import com.google.gson.Gson;

public class RadioThermostatClient {
	
	private static final String THERMOSTAT_STATE_PATH = "/tstat";
	private static final String THERMOSTAT_MODEL_PATH = "/tstat/model";
	
	private final HttpClient httpClient;
	
	private Gson gson = new Gson();
	
	private final ThermostatStateDeserializer deserializer = new ThermostatStateDeserializer();
	
	private class ThermostatModelResponse {
		public String model;
	}
	
	public RadioThermostatClient( final HttpClient httpClient ) {
		this.httpClient = httpClient;
	}
	
	public ThermostatState getThermostatState( final String thermostatAddress ) {
		final String response = executeHttpGet( thermostatAddress, THERMOSTAT_STATE_PATH );
		
		return deserializer.deserialize(response);
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