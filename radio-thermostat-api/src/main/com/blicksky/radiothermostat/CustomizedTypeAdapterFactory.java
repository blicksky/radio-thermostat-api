package com.blicksky.radiothermostat;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class CustomizedTypeAdapterFactory<C> implements TypeAdapterFactory {
	private final Class<C> customizedType;

	public CustomizedTypeAdapterFactory( final Class<C> customizedType ) {
		this.customizedType = customizedType;
	}

	@SuppressWarnings("unchecked")
	// we use a runtime check to guarantee that 'C' and 'T' are equal
	public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		return this.matchType(type)
				? (TypeAdapter<T>) createCustomizedTypeAdapter( gson, (TypeToken<C>) type )
				: null;
	}

	private TypeAdapter<C> createCustomizedTypeAdapter( final Gson gson, final TypeToken<C> type ) {
		final TypeAdapter<C> delegateAdapter = gson.getDelegateAdapter(this, type);
		final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
		
		return new TypeAdapter<C>() {
			@Override
			public void write(final JsonWriter out, final C value) throws IOException {
				JsonElement tree = delegateAdapter.toJsonTree(value);
				tree = beforeWrite(value, tree);
				elementAdapter.write(out, tree);
			}

			@Override
			public C read(final JsonReader in) throws IOException {
				JsonElement tree = elementAdapter.read(in);
				tree = afterRead(tree);
				return delegateAdapter.fromJsonTree(tree);
			}
		};
	}
	
	/**
	 * Override this to customize which types are processed by this adapter
	 * @param type
	 * @return
	 */
	protected <T> boolean matchType( final TypeToken<T> type ) {
		return type.getRawType() == this.customizedType;
	}

	/**
	 * Override this to modify {@code toSerialize} before it is written to
	 * the outgoing JSON stream.
	 */
	protected JsonElement beforeWrite(final C source, final JsonElement toSerialize) {
		return toSerialize;
	}

	/**
	 * Override this to modify {@code deserialized} before it parsed into the
	 * application type.
	 */
	protected JsonElement afterRead(final JsonElement deserialized) {
		return deserialized;
	}
}