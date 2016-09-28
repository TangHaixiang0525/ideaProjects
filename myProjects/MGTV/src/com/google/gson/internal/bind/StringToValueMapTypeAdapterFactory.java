package com.google.gson.internal.bind;

import com.google.gson.internal..Gson.Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class StringToValueMapTypeAdapterFactory
  implements TypeAdapter.Factory
{
  private final ConstructorConstructor constructorConstructor;

  public StringToValueMapTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor)
  {
    this.constructorConstructor = paramConstructorConstructor;
  }

  public <T> TypeAdapter<T> create(MiniGson paramMiniGson, TypeToken<T> paramTypeToken)
  {
    Type localType = paramTypeToken.getType();
    if (!(localType instanceof ParameterizedType));
    Type[] arrayOfType;
    do
    {
      Class localClass;
      do
      {
        return null;
        localClass = paramTypeToken.getRawType();
      }
      while (!Map.class.isAssignableFrom(localClass));
      arrayOfType = .Gson.Types.getMapKeyAndValueTypes(localType, localClass);
    }
    while (arrayOfType[0] != String.class);
    return new Adapter(paramMiniGson.getAdapter(TypeToken.get(arrayOfType[1])), this.constructorConstructor.getConstructor(paramTypeToken));
  }

  private final class Adapter<V> extends TypeAdapter<Map<String, V>>
  {
    private final ObjectConstructor<? extends Map<String, V>> constructor;
    private final TypeAdapter<V> valueTypeAdapter;

    public Adapter(ObjectConstructor<? extends Map<String, V>> arg2)
    {
      Object localObject1;
      this.valueTypeAdapter = localObject1;
      Object localObject2;
      this.constructor = localObject2;
    }

    public Map<String, V> read(JsonReader paramJsonReader)
      throws IOException
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Map localMap = (Map)this.constructor.construct();
      paramJsonReader.beginObject();
      while (paramJsonReader.hasNext())
        localMap.put(paramJsonReader.nextName(), this.valueTypeAdapter.read(paramJsonReader));
      paramJsonReader.endObject();
      return localMap;
    }

    public void write(JsonWriter paramJsonWriter, Map<String, V> paramMap)
      throws IOException
    {
      if (paramMap == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      paramJsonWriter.beginObject();
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramJsonWriter.name((String)localEntry.getKey());
        this.valueTypeAdapter.write(paramJsonWriter, localEntry.getValue());
      }
      paramJsonWriter.endObject();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.StringToValueMapTypeAdapterFactory
 * JD-Core Version:    0.6.2
 */