package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.internal..Gson.Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReflectiveTypeAdapterFactory
  implements TypeAdapter.Factory
{
  private final ConstructorConstructor constructorConstructor;

  public ReflectiveTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor)
  {
    this.constructorConstructor = paramConstructorConstructor;
  }

  private BoundField createBoundField(final MiniGson paramMiniGson, final Field paramField, String paramString, final TypeToken<?> paramTypeToken, boolean paramBoolean1, boolean paramBoolean2)
  {
    return new BoundField(paramString, paramBoolean1, paramBoolean2)
    {
      final TypeAdapter<?> typeAdapter = paramMiniGson.getAdapter(paramTypeToken);

      void read(JsonReader paramAnonymousJsonReader, Object paramAnonymousObject)
        throws IOException, IllegalAccessException
      {
        Object localObject = this.typeAdapter.read(paramAnonymousJsonReader);
        if ((localObject != null) || (!this.val$isPrimitive))
          paramField.set(paramAnonymousObject, localObject);
      }

      void write(JsonWriter paramAnonymousJsonWriter, Object paramAnonymousObject)
        throws IOException, IllegalAccessException
      {
        Object localObject = paramField.get(paramAnonymousObject);
        new TypeAdapterRuntimeTypeWrapper(paramMiniGson, this.typeAdapter, paramTypeToken.getType()).write(paramAnonymousJsonWriter, localObject);
      }
    };
  }

  private Map<String, BoundField> getBoundFields(MiniGson paramMiniGson, TypeToken<?> paramTypeToken, Class<?> paramClass)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (paramClass.isInterface());
    while (true)
    {
      return localLinkedHashMap;
      Type localType1 = paramTypeToken.getType();
      while (paramClass != Object.class)
      {
        Field[] arrayOfField = paramClass.getDeclaredFields();
        AccessibleObject.setAccessible(arrayOfField, true);
        int i = arrayOfField.length;
        int j = 0;
        if (j < i)
        {
          Field localField = arrayOfField[j];
          boolean bool1 = serializeField(paramClass, localField, localType1);
          boolean bool2 = deserializeField(paramClass, localField, localType1);
          if ((!bool1) && (!bool2));
          BoundField localBoundField2;
          do
          {
            j++;
            break;
            Type localType4 = paramTypeToken.getType();
            Type localType5 = localField.getGenericType();
            Type localType6 = .Gson.Types.resolve(localType4, paramClass, localType5);
            BoundField localBoundField1 = createBoundField(paramMiniGson, localField, getFieldName(paramClass, localField, localType1), TypeToken.get(localType6), bool1, bool2);
            localBoundField2 = (BoundField)localLinkedHashMap.put(localBoundField1.name, localBoundField1);
          }
          while (localBoundField2 == null);
          throw new IllegalArgumentException(localType1 + " declares multiple JSON fields named " + localBoundField2.name);
        }
        Type localType2 = paramTypeToken.getType();
        Type localType3 = paramClass.getGenericSuperclass();
        paramTypeToken = TypeToken.get(.Gson.Types.resolve(localType2, paramClass, localType3));
        paramClass = paramTypeToken.getRawType();
      }
    }
  }

  public <T> TypeAdapter<T> create(MiniGson paramMiniGson, TypeToken<T> paramTypeToken)
  {
    Class localClass = paramTypeToken.getRawType();
    if (!Object.class.isAssignableFrom(localClass))
      return null;
    return new Adapter(this.constructorConstructor.getConstructor(paramTypeToken), getBoundFields(paramMiniGson, paramTypeToken, localClass), null);
  }

  protected boolean deserializeField(Class<?> paramClass, Field paramField, Type paramType)
  {
    return !paramField.isSynthetic();
  }

  protected String getFieldName(Class<?> paramClass, Field paramField, Type paramType)
  {
    return paramField.getName();
  }

  protected boolean serializeField(Class<?> paramClass, Field paramField, Type paramType)
  {
    return !paramField.isSynthetic();
  }

  public final class Adapter<T> extends TypeAdapter<T>
  {
    private final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;
    private final ObjectConstructor<T> constructor;

    private Adapter(Map<String, ReflectiveTypeAdapterFactory.BoundField> arg2)
    {
      Object localObject1;
      this.constructor = localObject1;
      Object localObject2;
      this.boundFields = localObject2;
    }

    public T read(JsonReader paramJsonReader)
      throws IOException
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Object localObject = this.constructor.construct();
      try
      {
        paramJsonReader.beginObject();
        while (true)
        {
          if (!paramJsonReader.hasNext())
            break label111;
          String str = paramJsonReader.nextName();
          localBoundField = (ReflectiveTypeAdapterFactory.BoundField)this.boundFields.get(str);
          if ((localBoundField != null) && (localBoundField.deserialized))
            break;
          paramJsonReader.skipValue();
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        while (true)
        {
          ReflectiveTypeAdapterFactory.BoundField localBoundField;
          throw new JsonSyntaxException(localIllegalStateException);
          localBoundField.read(paramJsonReader, localObject);
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError(localIllegalAccessException);
      }
      label111: paramJsonReader.endObject();
      return localObject;
    }

    public void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      if (paramT == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      paramJsonWriter.beginObject();
      try
      {
        Iterator localIterator = this.boundFields.values().iterator();
        while (localIterator.hasNext())
        {
          ReflectiveTypeAdapterFactory.BoundField localBoundField = (ReflectiveTypeAdapterFactory.BoundField)localIterator.next();
          if (localBoundField.serialized)
          {
            paramJsonWriter.name(localBoundField.name);
            localBoundField.write(paramJsonWriter, paramT);
          }
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError();
      }
      paramJsonWriter.endObject();
    }
  }

  static abstract class BoundField
  {
    final boolean deserialized;
    final String name;
    final boolean serialized;

    protected BoundField(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.name = paramString;
      this.serialized = paramBoolean1;
      this.deserialized = paramBoolean2;
    }

    abstract void read(JsonReader paramJsonReader, Object paramObject)
      throws IOException, IllegalAccessException;

    abstract void write(JsonWriter paramJsonWriter, Object paramObject)
      throws IOException, IllegalAccessException;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
 * JD-Core Version:    0.6.2
 */