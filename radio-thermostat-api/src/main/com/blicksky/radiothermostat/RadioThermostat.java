package com.blicksky.radiothermostat;

import javax.inject.Named;

import feign.RequestLine;
import feign.Response;

interface RadioThermostat {

	@RequestLine("GET /tstat")
	StateResponse getState();
	
	@RequestLine("GET /tstat/model")
	ModelResponse getModel();
	
	@RequestLine("GET /tstat/program/{mode}")
	Response getProgram( @Named("mode") String mode );
	
	/*
	@RequestLine("GET /tstat/program/{mode}/{day}")
	String getProgram( @Named("mode") String mode, @Named("day") int day );
	*/
	
	static class StateResponse {

		static class Time {
			int day;
			int hour;
			int minute;
		}
		
		float temp;
		int tmode;
		int fmode;
		int override;
		int hold;
		int tstate;
		int fstate;
		Time time;
	}
	
	static class ModelResponse {
		String model;
	}
}
