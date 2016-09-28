package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import com.google.gson.internal.Pair;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class FieldAttributes
{
  private static final Cache<Pair<Class<?>, String>, Collection<Annotation>> ANNOTATION_CACHE = new LruCache(getMaxCacheSize());
  private static final String MAX_CACHE_PROPERTY_NAME = "com.google.gson.annotation_cache_size_hint";
  private Collection<Annotation> annotations;
  private final Class<?> declaredType;
  private final Class<?> declaringClazz;
  private final Field field;
  private Type genericType;
  private final boolean isSynthetic;
  private final int modifiers;
  private final String name;

  FieldAttributes(Class<?> paramClass, Field paramField)
  {
    this.declaringClazz = ((Class).Gson.Preconditions.checkNotNull(paramClass));
    this.name = paramField.getName();
    this.declaredType = paramField.getType();
    this.isSynthetic = paramField.isSynthetic();
    this.modifiers = paramField.getModifiers();
    this.field = paramField;
  }

  private static <T extends Annotation> T getAnnotationFromArray(Collection<Annotation> paramCollection, Class<T> paramClass)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Annotation localAnnotation = (Annotation)localIterator.next();
      if (localAnnotation.annotationType() == paramClass)
        return localAnnotation;
    }
    return null;
  }

  private static int getMaxCacheSize()
  {
    try
    {
      int i = Integer.parseInt(System.getProperty("com.google.gson.annotation_cache_size_hint", String.valueOf(2000)));
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return 2000;
  }

  Object get(Object paramObject)
    throws IllegalAccessException
  {
    return this.field.get(paramObject);
  }

  public <T extends Annotation> T getAnnotation(Class<T> paramClass)
  {
    return getAnnotationFromArray(getAnnotations(), paramClass);
  }

  public Collection<Annotation> getAnnotations()
  {
    if (this.annotations == null)
    {
      Pair localPair = new Pair(this.declaringClazz, this.name);
      Collection localCollection = (Collection)ANNOTATION_CACHE.getElement(localPair);
      if (localCollection == null)
      {
        localCollection = Collections.unmodifiableCollection(Arrays.asList(this.field.getAnnotations()));
        ANNOTATION_CACHE.addElement(localPair, localCollection);
      }
      this.annotations = localCollection;
    }
    return this.annotations;
  }

  public Class<?> getDeclaredClass()
  {
    return this.declaredType;
  }

  public Type getDeclaredType()
  {
    if (this.genericType == null)
      this.genericType = this.field.getGenericType();
    return this.genericType;
  }

  public Class<?> getDeclaringClass()
  {
    return this.declaringClazz;
  }

  @Deprecated
  Field getFieldObject()
  {
    return this.field;
  }

  public String getName()
  {
    return this.name;
  }

  public boolean hasModifier(int paramInt)
  {
    return (paramInt & this.modifiers) != 0;
  }

  boolean isSynthetic()
  {
    return this.isSynthetic;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.FieldAttributes
 * JD-Core Version:    0.6.2
 */