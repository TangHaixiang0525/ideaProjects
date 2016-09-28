package com.google.gson.internal.bind;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MiniGson
{
  private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal()
  {
    protected Map<TypeToken<?>, MiniGson.FutureTypeAdapter<?>> initialValue()
    {
      return new HashMap();
    }
  };
  private final List<TypeAdapter.Factory> factories;

  private MiniGson(Builder paramBuilder)
  {
    ConstructorConstructor localConstructorConstructor = new ConstructorConstructor();
    ArrayList localArrayList = new ArrayList();
    if (paramBuilder.addDefaultFactories)
    {
      localArrayList.add(TypeAdapters.BOOLEAN_FACTORY);
      localArrayList.add(TypeAdapters.INTEGER_FACTORY);
      localArrayList.add(TypeAdapters.DOUBLE_FACTORY);
      localArrayList.add(TypeAdapters.FLOAT_FACTORY);
      localArrayList.add(TypeAdapters.LONG_FACTORY);
      localArrayList.add(TypeAdapters.STRING_FACTORY);
    }
    localArrayList.addAll(paramBuilder.factories);
    if (paramBuilder.addDefaultFactories)
    {
      localArrayList.add(new CollectionTypeAdapterFactory(localConstructorConstructor));
      localArrayList.add(new StringToValueMapTypeAdapterFactory(localConstructorConstructor));
      localArrayList.add(ArrayTypeAdapter.FACTORY);
      localArrayList.add(ObjectTypeAdapter.FACTORY);
      localArrayList.add(new ReflectiveTypeAdapterFactory(localConstructorConstructor));
    }
    this.factories = Collections.unmodifiableList(localArrayList);
  }

  public <T> TypeAdapter<T> getAdapter(TypeToken<T> paramTypeToken)
  {
    Map localMap = (Map)this.calls.get();
    FutureTypeAdapter localFutureTypeAdapter1 = (FutureTypeAdapter)localMap.get(paramTypeToken);
    if (localFutureTypeAdapter1 != null)
      return localFutureTypeAdapter1;
    FutureTypeAdapter localFutureTypeAdapter2 = new FutureTypeAdapter();
    localMap.put(paramTypeToken, localFutureTypeAdapter2);
    try
    {
      Iterator localIterator = this.factories.iterator();
      while (localIterator.hasNext())
      {
        TypeAdapter localTypeAdapter = ((TypeAdapter.Factory)localIterator.next()).create(this, paramTypeToken);
        if (localTypeAdapter != null)
        {
          localFutureTypeAdapter2.setDelegate(localTypeAdapter);
          return localTypeAdapter;
        }
      }
      throw new IllegalArgumentException("This MiniGSON cannot handle " + paramTypeToken);
    }
    finally
    {
      localMap.remove(paramTypeToken);
    }
  }

  public <T> TypeAdapter<T> getAdapter(Class<T> paramClass)
  {
    return getAdapter(TypeToken.get(paramClass));
  }

  public List<TypeAdapter.Factory> getFactories()
  {
    return this.factories;
  }

  public <T> TypeAdapter<T> getNextAdapter(TypeAdapter.Factory paramFactory, TypeToken<T> paramTypeToken)
  {
    int i = 0;
    Iterator localIterator = this.factories.iterator();
    while (localIterator.hasNext())
    {
      TypeAdapter.Factory localFactory = (TypeAdapter.Factory)localIterator.next();
      if (i == 0)
      {
        if (localFactory == paramFactory)
          i = 1;
      }
      else
      {
        TypeAdapter localTypeAdapter = localFactory.create(this, paramTypeToken);
        if (localTypeAdapter != null)
          return localTypeAdapter;
      }
    }
    throw new IllegalArgumentException("This MiniGSON cannot serialize " + paramTypeToken);
  }

  public static final class Builder
  {
    boolean addDefaultFactories = true;
    private final List<TypeAdapter.Factory> factories = new ArrayList();

    public MiniGson build()
    {
      return new MiniGson(this, null);
    }

    public Builder factory(TypeAdapter.Factory paramFactory)
    {
      this.factories.add(paramFactory);
      return this;
    }

    public <T> Builder typeAdapter(TypeToken<T> paramTypeToken, TypeAdapter<T> paramTypeAdapter)
    {
      this.factories.add(TypeAdapters.newFactory(paramTypeToken, paramTypeAdapter));
      return this;
    }

    public <T> Builder typeAdapter(Class<T> paramClass, TypeAdapter<T> paramTypeAdapter)
    {
      this.factories.add(TypeAdapters.newFactory(paramClass, paramTypeAdapter));
      return this;
    }

    public <T> Builder typeHierarchyAdapter(Class<T> paramClass, TypeAdapter<T> paramTypeAdapter)
    {
      this.factories.add(TypeAdapters.newTypeHierarchyFactory(paramClass, paramTypeAdapter));
      return this;
    }

    public Builder withoutDefaultFactories()
    {
      this.addDefaultFactories = false;
      return this;
    }
  }

  static class FutureTypeAdapter<T> extends TypeAdapter<T>
  {
    private TypeAdapter<T> delegate;

    public T read(JsonReader paramJsonReader)
      throws IOException
    {
      if (this.delegate == null)
        throw new IllegalStateException();
      return this.delegate.read(paramJsonReader);
    }

    public void setDelegate(TypeAdapter<T> paramTypeAdapter)
    {
      if (this.delegate != null)
        throw new AssertionError();
      this.delegate = paramTypeAdapter;
    }

    public void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      if (this.delegate == null)
        throw new IllegalStateException();
      this.delegate.write(paramJsonWriter, paramT);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.MiniGson
 * JD-Core Version:    0.6.2
 */