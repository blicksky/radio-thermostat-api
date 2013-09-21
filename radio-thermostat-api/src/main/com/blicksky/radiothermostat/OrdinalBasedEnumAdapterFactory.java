package com.blicksky.radiothermostat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class OrdinalBasedEnumAdapterFactory implements TypeAdapterFactory {
	
	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
		TypeAdapter<T> typeAdapter = null;
		
		@SuppressWarnings("unchecked")
		final Class<T> rawType = (Class<T>) type.getRawType();
		if( !rawType.isEnum() || !OrdinalBasedEnum.class.isAssignableFrom(rawType) ) {
			return null;
		}
		
		try {
			final Method valuesMethod = rawType.getMethod("values");
			
			@SuppressWarnings("unchecked")
			final T[] enumValues = (T[])valuesMethod.invoke(null);

			typeAdapter = new TypeAdapter<T>() {
				@Override
				public T read(final JsonReader reader) throws IOException {
					T value = null;
					
					final int ordinal = reader.nextInt();
					
					if( ordinal >= 0 && ordinal < enumValues.length ) {
						value = enumValues[ordinal];
					}
					
					return value;
				}

				@Override
				public void write(final JsonWriter writer, T value) throws IOException {
					writer.value( value != null
						? ((Enum<?>)value).ordinal()
						: null
					);
				}
			};
		}
		catch( NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
			// TODO: log, but this should never happen
		}
		
		return typeAdapter;
	}
}