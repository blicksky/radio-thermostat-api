package com.blicksky.radiothermostat;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public abstract class PropertyTranslatorTypeAdapterFactory<C> extends CustomizedTypeAdapterFactory<C> {

	final BiMap<String,String> translations = HashBiMap.create();
	
	public PropertyTranslatorTypeAdapterFactory(Class<C> customizedType, Map<String,String> translations) {
		super(customizedType);

		this.translations.putAll( translations );
	}
	
	protected JsonElement afterReadTranslation(final JsonElement deserialized) {
		return deserialized;
	}

	@Override
	protected final JsonElement afterRead(final JsonElement deserialized) {
		final JsonObject translatedJsonElement = new JsonObject();
		
		for( final Entry<String, JsonElement> property : deserialized.getAsJsonObject().entrySet() ) {
			String translatedPropertyName = property.getKey();
			
			if( this.translations.containsKey( property.getKey() ) ) {
				translatedPropertyName = this.translations.get( property.getKey() );
			}
			
			translatedJsonElement.add( translatedPropertyName, property.getValue() );
		}
		
		return afterReadTranslation( translatedJsonElement );
	}
	
	protected JsonElement afterWriteTranslation(final C source, final JsonElement toSeriaize) {
		return toSeriaize;
	}
	
	@Override
	protected final JsonElement beforeWrite(final C source, final JsonElement toSerialize) {
		final JsonObject translatedJsonElement = new JsonObject();
		
		for( final Entry<String, JsonElement> property : toSerialize.getAsJsonObject().entrySet() ) {
			String translatedPropertyName = property.getKey();
			
			if( this.translations.inverse().containsKey( property.getKey() ) ) {
				translatedPropertyName = this.translations.inverse().get( property.getKey() );
			}
			
			translatedJsonElement.add( translatedPropertyName, property.getValue() );
		}
		
		return afterWriteTranslation( source, translatedJsonElement );
	}
	
}