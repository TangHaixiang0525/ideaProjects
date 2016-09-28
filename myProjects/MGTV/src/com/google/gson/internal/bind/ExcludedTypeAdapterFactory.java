package com.google.gson.internal.bind;

import com.google.gson.ExclusionStrategy;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public final class ExcludedTypeAdapterFactory
  implements TypeAdapter.Factory
{
  private final ExclusionStrategy deserializationExclusionStrategy;
  private final ExclusionStrategy serializationExclusionStrategy;

  public ExcludedTypeAdapterFactory(ExclusionStrategy paramExclusionStrategy1, ExclusionStrategy paramExclusionStrategy2)
  {
    this.serializationExclusionStrategy = paramExclusionStrategy1;
    this.deserializationExclusionStrategy = paramExclusionStrategy2;
  }

  public <T> TypeAdapter<T> create(final MiniGson paramMiniGson, final TypeToken<T> paramTypeToken)
  {
    Class localClass = paramTypeToken.getRawType();
    final boolean bool1 = this.serializationExclusionStrategy.shouldSkipClass(localClass);
    final boolean bool2 = this.deserializationExclusionStrategy.shouldSkipClass(localClass);
    if ((!bool1) && (!bool2))
      return null;
    return new TypeAdapter()
    {
      private TypeAdapter<T> delegate;

      private TypeAdapter<T> delegate()
      {
        TypeAdapter localTypeAdapter1 = this.delegate;
        if (localTypeAdapter1 != null)
          return localTypeAdapter1;
        TypeAdapter localTypeAdapter2 = paramMiniGson.getNextAdapter(ExcludedTypeAdapterFactory.this, paramTypeToken);
        this.delegate = localTypeAdapter2;
        return localTypeAdapter2;
      }

      public T read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (bool2)
        {
          paramAnonymousJsonReader.skipValue();
          return null;
        }
        return delegate().read(paramAnonymousJsonReader);
      }

      public void write(JsonWriter paramAnonymousJsonWriter, T paramAnonymousT)
        throws IOException
      {
        if (bool1)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        delegate().write(paramAnonymousJsonWriter, paramAnonymousT);
      }
    };
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.ExcludedTypeAdapterFactory
 * JD-Core Version:    0.6.2
 */