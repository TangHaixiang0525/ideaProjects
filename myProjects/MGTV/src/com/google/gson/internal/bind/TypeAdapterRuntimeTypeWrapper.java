package com.google.gson.internal.bind;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T>
{
  private final MiniGson context;
  private final TypeAdapter<T> delegate;
  private final Type type;

  TypeAdapterRuntimeTypeWrapper(MiniGson paramMiniGson, TypeAdapter<T> paramTypeAdapter, Type paramType)
  {
    this.context = paramMiniGson;
    this.delegate = paramTypeAdapter;
    this.type = paramType;
  }

  public T read(JsonReader paramJsonReader)
    throws IOException
  {
    return this.delegate.read(paramJsonReader);
  }

  public void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException
  {
    Object localObject = this.delegate;
    Type localType = Reflection.getRuntimeTypeIfMoreSpecific(this.type, paramT);
    TypeAdapter localTypeAdapter;
    if (localType != this.type)
    {
      localTypeAdapter = this.context.getAdapter(TypeToken.get(localType));
      if ((localTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter))
        break label56;
      localObject = localTypeAdapter;
    }
    while (true)
    {
      ((TypeAdapter)localObject).write(paramJsonWriter, paramT);
      return;
      label56: if (!(this.delegate instanceof ReflectiveTypeAdapterFactory.Adapter))
        localObject = this.delegate;
      else
        localObject = localTypeAdapter;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper
 * JD-Core Version:    0.6.2
 */