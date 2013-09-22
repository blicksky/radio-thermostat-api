package com.blicksky.radiothermostat;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ThermostatStateDeserializer {

	// defines thermostat response field names to ThermostatState field names
	private static final Map<String,String> PROPERTY_TRANSLATIONS = new HashMap<String,String>();
	static {
		PROPERTY_TRANSLATIONS.put( "temp",		"currentTemperature" );
		PROPERTY_TRANSLATIONS.put( "tmode",		"thermostatOperatingMode" );
		PROPERTY_TRANSLATIONS.put( "fmode",		"fanOperatingMode" );
		PROPERTY_TRANSLATIONS.put( "tstate",	"hvacOperatingState" );
		PROPERTY_TRANSLATIONS.put( "fstate",	"fanOperatingState" );
		PROPERTY_TRANSLATIONS.put( "override",	"targetTemperatureOverrideEnabled" );
		PROPERTY_TRANSLATIONS.put( "hold",		"targetTemperatureHoldEnabled" );
	}
	
	private class ThermostatStateTypeAdapterFactory extends PropertyTranslatorTypeAdapterFactory<ThermostatState> {
		public ThermostatStateTypeAdapterFactory() {
			super( ThermostatState.class, PROPERTY_TRANSLATIONS );
		}
		
		// converts a field container an integer value into a boolean field, where 0 is false and anything else is true 
		private void convertIntegerFieldToBoolean( final JsonObject jsonObject, final String fieldName ) {
			if( jsonObject.has(fieldName) ) {
				jsonObject.addProperty( fieldName, jsonObject.get(fieldName).getAsInt() != 0 );
			}
		}
		
		@Override
		protected JsonElement afterReadTranslation(final JsonElement deserialized) {
			final JsonObject jsonObject = deserialized.getAsJsonObject();
			
			convertIntegerFieldToBoolean( jsonObject, "targetTemperatureOverrideEnabled" );
			convertIntegerFieldToBoolean( jsonObject, "targetTemperatureHoldEnabled" );
			
			final JsonObject timeJson = jsonObject.getAsJsonObject("time");
			final Calendar time = Calendar.getInstance();

			time.set(Calendar.DAY_OF_WEEK, DayOfWeekConverter.convertToCalendarDayOfWeek( timeJson.get("day").getAsInt() ) ); 
			time.set(Calendar.HOUR_OF_DAY, timeJson.get("hour").getAsInt());
			time.set(Calendar.MINUTE, timeJson.get("minute").getAsInt());
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.MILLISECOND, 0);
			
			jsonObject.addProperty("time", time.getTimeInMillis());
			
			return jsonObject;
		}
	}
	
	private class DateDeserializer implements JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			return new Date(json.getAsLong());
		}
	}
	
	private final Gson gson = new GsonBuilder()
		.registerTypeAdapterFactory( new ThermostatStateTypeAdapterFactory() )
		.registerTypeAdapterFactory( new OrdinalBasedEnumAdapterFactory() )
		.registerTypeAdapter( Date.class, new DateDeserializer() )
		.create();
	
	public ThermostatState deserialize( final String json ) {
		return gson.fromJson(json, ThermostatState.class);
	}
	
}