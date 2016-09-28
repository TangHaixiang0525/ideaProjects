package com.google.gson.internal.bind;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T>
{
  public final T fromJson(String paramString)
    throws IOException
  {
    return read(new StringReader(paramString));
  }

  public T fromJsonElement(JsonElement paramJsonElement)
  {
    try
    {
      JsonElementReader localJsonElementReader = new JsonElementReader(paramJsonElement);
      localJsonElementReader.setLenient(true);
      Object localObject = read(localJsonElementReader);
      return localObject;
    }
    catch (IOException localIOException)
    {
      throw new JsonIOException(localIOException);
    }
  }

  public abstract T read(JsonReader paramJsonReader)
    throws IOException;

  public final T read(Reader paramReader)
    throws IOException
  {
    JsonReader localJsonReader = new JsonReader(paramReader);
    localJsonReader.setLenient(true);
    return read(localJsonReader);
  }

  public final String toJson(T paramT)
    throws IOException
  {
    StringWriter localStringWriter = new StringWriter();
    write(localStringWriter, paramT);
    return localStringWriter.toString();
  }

  public JsonElement toJsonElement(T paramT)
  {
    try
    {
      JsonElementWriter localJsonElementWriter = new JsonElementWriter();
      localJsonElementWriter.setLenient(true);
      write(localJsonElementWriter, paramT);
      JsonElement localJsonElement = localJsonElementWriter.get();
      return localJsonElement;
    }
    catch (IOException localIOException)
    {
      throw new JsonIOException(localIOException);
    }
  }

  public abstract void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException;

  public final void write(Writer paramWriter, T paramT)
    throws IOException
  {
    write(new JsonWriter(paramWriter), paramT);
  }

  public static abstract interface Factory
  {
    public abstract <T> TypeAdapter<T> create(MiniGson paramMiniGson, TypeToken<T> paramTypeToken);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.TypeAdapter
 * JD-Core Version:    0.6.2
 */