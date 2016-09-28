package com.google.gson;

import com.google.gson.internal.ParameterizedTypeHandlerMap;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.MiniGson;
import com.google.gson.internal.bind.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapter.Factory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

final class GsonToMiniGsonTypeAdapterFactory
  implements TypeAdapter.Factory
{
  private final JsonDeserializationContext deserializationContext;
  private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
  private final JsonSerializationContext serializationContext;
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;

  public GsonToMiniGsonTypeAdapterFactory(final Gson paramGson, ParameterizedTypeHandlerMap<JsonSerializer<?>> paramParameterizedTypeHandlerMap, ParameterizedTypeHandlerMap<JsonDeserializer<?>> paramParameterizedTypeHandlerMap1)
  {
    this.serializers = paramParameterizedTypeHandlerMap;
    this.deserializers = paramParameterizedTypeHandlerMap1;
    this.deserializationContext = new JsonDeserializationContext()
    {
      public <T> T deserialize(JsonElement paramAnonymousJsonElement, Type paramAnonymousType)
        throws JsonParseException
      {
        return paramGson.fromJson(paramAnonymousJsonElement, paramAnonymousType);
      }
    };
    this.serializationContext = new JsonSerializationContext()
    {
      public JsonElement serialize(Object paramAnonymousObject)
      {
        return paramGson.toJsonTree(paramAnonymousObject);
      }

      public JsonElement serialize(Object paramAnonymousObject, Type paramAnonymousType)
      {
        return paramGson.toJsonTree(paramAnonymousObject, paramAnonymousType);
      }
    };
  }

  public <T> TypeAdapter<T> create(final MiniGson paramMiniGson, final TypeToken<T> paramTypeToken)
  {
    final Type localType = paramTypeToken.getType();
    final JsonSerializer localJsonSerializer = (JsonSerializer)this.serializers.getHandlerFor(localType, false);
    final JsonDeserializer localJsonDeserializer = (JsonDeserializer)this.deserializers.getHandlerFor(localType, false);
    if ((localJsonSerializer == null) && (localJsonDeserializer == null))
      return null;
    return new TypeAdapter()
    {
      private TypeAdapter<T> delegate;

      private TypeAdapter<T> delegate()
      {
        TypeAdapter localTypeAdapter1 = this.delegate;
        if (localTypeAdapter1 != null)
          return localTypeAdapter1;
        TypeAdapter localTypeAdapter2 = paramMiniGson.getNextAdapter(GsonToMiniGsonTypeAdapterFactory.this, paramTypeToken);
        this.delegate = localTypeAdapter2;
        return localTypeAdapter2;
      }

      public T read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (localJsonDeserializer == null)
          return delegate().read(paramAnonymousJsonReader);
        JsonElement localJsonElement = Streams.parse(paramAnonymousJsonReader);
        if (localJsonElement.isJsonNull())
          return null;
        return localJsonDeserializer.deserialize(localJsonElement, localType, GsonToMiniGsonTypeAdapterFactory.this.deserializationContext);
      }

      public void write(JsonWriter paramAnonymousJsonWriter, T paramAnonymousT)
        throws IOException
      {
        if (localJsonSerializer == null)
        {
          delegate().write(paramAnonymousJsonWriter, paramAnonymousT);
          return;
        }
        if (paramAnonymousT == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        Streams.write(localJsonSerializer.serialize(paramAnonymousT, localType, GsonToMiniGsonTypeAdapterFactory.this.serializationContext), paramAnonymousJsonWriter);
      }
    };
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.GsonToMiniGsonTypeAdapterFactory
 * JD-Core Version:    0.6.2
 */