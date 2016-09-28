package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import com.google.gson.internal.ParameterizedTypeHandlerMap;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.TypeAdapter.Factory;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class GsonBuilder
{
  private static final ExposeAnnotationDeserializationExclusionStrategy exposeAnnotationDeserializationExclusionStrategy = new ExposeAnnotationDeserializationExclusionStrategy();
  private static final ExposeAnnotationSerializationExclusionStrategy exposeAnnotationSerializationExclusionStrategy = new ExposeAnnotationSerializationExclusionStrategy();
  private static final InnerClassExclusionStrategy innerClassExclusionStrategy = new InnerClassExclusionStrategy();
  private boolean complexMapKeySerialization = false;
  private String datePattern;
  private int dateStyle;
  private final Set<ExclusionStrategy> deserializeExclusionStrategies = new HashSet();
  private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
  private boolean escapeHtmlChars;
  private boolean excludeFieldsWithoutExposeAnnotation;
  private FieldNamingStrategy2 fieldNamingPolicy;
  private boolean generateNonExecutableJson;
  private double ignoreVersionsAfter;
  private final ParameterizedTypeHandlerMap<InstanceCreator<?>> instanceCreators;
  private LongSerializationPolicy longSerializationPolicy;
  private ModifierBasedExclusionStrategy modifierBasedExclusionStrategy;
  private boolean prettyPrinting;
  private final Set<ExclusionStrategy> serializeExclusionStrategies = new HashSet();
  private boolean serializeInnerClasses;
  private boolean serializeNulls;
  private boolean serializeSpecialFloatingPointValues;
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;
  private int timeStyle;
  private final List<TypeAdapter.Factory> typeAdapterFactories = new ArrayList();

  public GsonBuilder()
  {
    this.deserializeExclusionStrategies.add(Gson.DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY);
    this.deserializeExclusionStrategies.add(Gson.DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY);
    this.serializeExclusionStrategies.add(Gson.DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY);
    this.serializeExclusionStrategies.add(Gson.DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY);
    this.ignoreVersionsAfter = -1.0D;
    this.serializeInnerClasses = true;
    this.prettyPrinting = false;
    this.escapeHtmlChars = true;
    this.modifierBasedExclusionStrategy = Gson.DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY;
    this.excludeFieldsWithoutExposeAnnotation = false;
    this.longSerializationPolicy = LongSerializationPolicy.DEFAULT;
    this.fieldNamingPolicy = Gson.DEFAULT_NAMING_POLICY;
    this.instanceCreators = new ParameterizedTypeHandlerMap();
    this.serializers = new ParameterizedTypeHandlerMap();
    this.deserializers = new ParameterizedTypeHandlerMap();
    this.serializeNulls = false;
    this.dateStyle = 2;
    this.timeStyle = 2;
    this.serializeSpecialFloatingPointValues = false;
    this.generateNonExecutableJson = false;
  }

  private static void addTypeAdaptersForDate(String paramString, int paramInt1, int paramInt2, ParameterizedTypeHandlerMap<JsonSerializer<?>> paramParameterizedTypeHandlerMap, ParameterizedTypeHandlerMap<JsonDeserializer<?>> paramParameterizedTypeHandlerMap1)
  {
    DefaultTypeAdapters.DefaultDateTypeAdapter localDefaultDateTypeAdapter;
    if ((paramString != null) && (!"".equals(paramString.trim())))
      localDefaultDateTypeAdapter = new DefaultTypeAdapters.DefaultDateTypeAdapter(paramString);
    while (true)
    {
      if (localDefaultDateTypeAdapter != null)
      {
        registerIfAbsent(java.util.Date.class, paramParameterizedTypeHandlerMap, localDefaultDateTypeAdapter);
        registerIfAbsent(java.util.Date.class, paramParameterizedTypeHandlerMap1, localDefaultDateTypeAdapter);
        registerIfAbsent(Timestamp.class, paramParameterizedTypeHandlerMap, localDefaultDateTypeAdapter);
        registerIfAbsent(Timestamp.class, paramParameterizedTypeHandlerMap1, localDefaultDateTypeAdapter);
        registerIfAbsent(java.sql.Date.class, paramParameterizedTypeHandlerMap, localDefaultDateTypeAdapter);
        registerIfAbsent(java.sql.Date.class, paramParameterizedTypeHandlerMap1, localDefaultDateTypeAdapter);
      }
      return;
      localDefaultDateTypeAdapter = null;
      if (paramInt1 != 2)
      {
        localDefaultDateTypeAdapter = null;
        if (paramInt2 != 2)
          localDefaultDateTypeAdapter = new DefaultTypeAdapters.DefaultDateTypeAdapter(paramInt1, paramInt2);
      }
    }
  }

  private <T> GsonBuilder registerDeserializer(Type paramType, JsonDeserializer<T> paramJsonDeserializer, boolean paramBoolean)
  {
    this.deserializers.register(paramType, new JsonDeserializerExceptionWrapper(paramJsonDeserializer), paramBoolean);
    return this;
  }

  private <T> GsonBuilder registerDeserializerForTypeHierarchy(Class<?> paramClass, JsonDeserializer<T> paramJsonDeserializer, boolean paramBoolean)
  {
    this.deserializers.registerForTypeHierarchy(paramClass, new JsonDeserializerExceptionWrapper(paramJsonDeserializer), paramBoolean);
    return this;
  }

  private static <T> void registerIfAbsent(Class<?> paramClass, ParameterizedTypeHandlerMap<T> paramParameterizedTypeHandlerMap, T paramT)
  {
    if (!paramParameterizedTypeHandlerMap.hasSpecificHandlerFor(paramClass))
      paramParameterizedTypeHandlerMap.register(paramClass, paramT, false);
  }

  private <T> GsonBuilder registerInstanceCreator(Type paramType, InstanceCreator<? extends T> paramInstanceCreator, boolean paramBoolean)
  {
    this.instanceCreators.register(paramType, paramInstanceCreator, paramBoolean);
    return this;
  }

  private <T> GsonBuilder registerInstanceCreatorForTypeHierarchy(Class<?> paramClass, InstanceCreator<? extends T> paramInstanceCreator, boolean paramBoolean)
  {
    this.instanceCreators.registerForTypeHierarchy(paramClass, paramInstanceCreator, paramBoolean);
    return this;
  }

  private <T> GsonBuilder registerSerializer(Type paramType, JsonSerializer<T> paramJsonSerializer, boolean paramBoolean)
  {
    this.serializers.register(paramType, paramJsonSerializer, paramBoolean);
    return this;
  }

  private <T> GsonBuilder registerSerializerForTypeHierarchy(Class<?> paramClass, JsonSerializer<T> paramJsonSerializer, boolean paramBoolean)
  {
    this.serializers.registerForTypeHierarchy(paramClass, paramJsonSerializer, paramBoolean);
    return this;
  }

  private GsonBuilder registerTypeAdapter(Type paramType, Object paramObject, boolean paramBoolean)
  {
    if (((paramObject instanceof JsonSerializer)) || ((paramObject instanceof JsonDeserializer)) || ((paramObject instanceof InstanceCreator)) || ((paramObject instanceof TypeAdapter.Factory)));
    for (boolean bool = true; ; bool = false)
    {
      .Gson.Preconditions.checkArgument(bool);
      if ((!Primitives.isPrimitive(paramType)) && (!Primitives.isWrapperType(paramType)))
        break;
      throw new IllegalArgumentException("Cannot register type adapters for " + paramType);
    }
    if ((paramObject instanceof InstanceCreator))
      registerInstanceCreator(paramType, (InstanceCreator)paramObject, paramBoolean);
    if ((paramObject instanceof JsonSerializer))
      registerSerializer(paramType, (JsonSerializer)paramObject, paramBoolean);
    if ((paramObject instanceof JsonDeserializer))
      registerDeserializer(paramType, (JsonDeserializer)paramObject, paramBoolean);
    if ((paramObject instanceof TypeAdapter.Factory))
      this.typeAdapterFactories.add((TypeAdapter.Factory)paramObject);
    return this;
  }

  private GsonBuilder registerTypeHierarchyAdapter(Class<?> paramClass, Object paramObject, boolean paramBoolean)
  {
    if (((paramObject instanceof JsonSerializer)) || ((paramObject instanceof JsonDeserializer)) || ((paramObject instanceof InstanceCreator)));
    for (boolean bool = true; ; bool = false)
    {
      .Gson.Preconditions.checkArgument(bool);
      if ((paramObject instanceof InstanceCreator))
        registerInstanceCreatorForTypeHierarchy(paramClass, (InstanceCreator)paramObject, paramBoolean);
      if ((paramObject instanceof JsonSerializer))
        registerSerializerForTypeHierarchy(paramClass, (JsonSerializer)paramObject, paramBoolean);
      if ((paramObject instanceof JsonDeserializer))
        registerDeserializerForTypeHierarchy(paramClass, (JsonDeserializer)paramObject, paramBoolean);
      return this;
    }
  }

  public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy paramExclusionStrategy)
  {
    this.deserializeExclusionStrategies.add(paramExclusionStrategy);
    return this;
  }

  public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy paramExclusionStrategy)
  {
    this.serializeExclusionStrategies.add(paramExclusionStrategy);
    return this;
  }

  public Gson create()
  {
    LinkedList localLinkedList1 = new LinkedList(this.deserializeExclusionStrategies);
    LinkedList localLinkedList2 = new LinkedList(this.serializeExclusionStrategies);
    localLinkedList1.add(this.modifierBasedExclusionStrategy);
    localLinkedList2.add(this.modifierBasedExclusionStrategy);
    if (!this.serializeInnerClasses)
    {
      localLinkedList1.add(innerClassExclusionStrategy);
      localLinkedList2.add(innerClassExclusionStrategy);
    }
    if (this.ignoreVersionsAfter != -1.0D)
    {
      VersionExclusionStrategy localVersionExclusionStrategy = new VersionExclusionStrategy(this.ignoreVersionsAfter);
      localLinkedList1.add(localVersionExclusionStrategy);
      localLinkedList2.add(localVersionExclusionStrategy);
    }
    if (this.excludeFieldsWithoutExposeAnnotation)
    {
      localLinkedList1.add(exposeAnnotationDeserializationExclusionStrategy);
      localLinkedList2.add(exposeAnnotationSerializationExclusionStrategy);
    }
    addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, this.serializers, this.deserializers);
    return new Gson(new DisjunctionExclusionStrategy(localLinkedList1), new DisjunctionExclusionStrategy(localLinkedList2), this.fieldNamingPolicy, this.instanceCreators.copyOf().makeUnmodifiable(), this.serializeNulls, this.serializers.copyOf().makeUnmodifiable(), this.deserializers.copyOf().makeUnmodifiable(), this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, this.typeAdapterFactories);
  }

  public GsonBuilder disableHtmlEscaping()
  {
    this.escapeHtmlChars = false;
    return this;
  }

  public GsonBuilder disableInnerClassSerialization()
  {
    this.serializeInnerClasses = false;
    return this;
  }

  public GsonBuilder enableComplexMapKeySerialization()
  {
    this.complexMapKeySerialization = true;
    return this;
  }

  public GsonBuilder excludeFieldsWithModifiers(int[] paramArrayOfInt)
  {
    this.modifierBasedExclusionStrategy = new ModifierBasedExclusionStrategy(paramArrayOfInt);
    return this;
  }

  public GsonBuilder excludeFieldsWithoutExposeAnnotation()
  {
    this.excludeFieldsWithoutExposeAnnotation = true;
    return this;
  }

  public GsonBuilder generateNonExecutableJson()
  {
    this.generateNonExecutableJson = true;
    return this;
  }

  public GsonBuilder registerTypeAdapter(Type paramType, Object paramObject)
  {
    return registerTypeAdapter(paramType, paramObject, false);
  }

  public GsonBuilder registerTypeHierarchyAdapter(Class<?> paramClass, Object paramObject)
  {
    return registerTypeHierarchyAdapter(paramClass, paramObject, false);
  }

  public GsonBuilder serializeNulls()
  {
    this.serializeNulls = true;
    return this;
  }

  public GsonBuilder serializeSpecialFloatingPointValues()
  {
    this.serializeSpecialFloatingPointValues = true;
    return this;
  }

  public GsonBuilder setDateFormat(int paramInt)
  {
    this.dateStyle = paramInt;
    this.datePattern = null;
    return this;
  }

  public GsonBuilder setDateFormat(int paramInt1, int paramInt2)
  {
    this.dateStyle = paramInt1;
    this.timeStyle = paramInt2;
    this.datePattern = null;
    return this;
  }

  public GsonBuilder setDateFormat(String paramString)
  {
    this.datePattern = paramString;
    return this;
  }

  public GsonBuilder setExclusionStrategies(ExclusionStrategy[] paramArrayOfExclusionStrategy)
  {
    List localList = Arrays.asList(paramArrayOfExclusionStrategy);
    this.serializeExclusionStrategies.addAll(localList);
    this.deserializeExclusionStrategies.addAll(localList);
    return this;
  }

  public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy paramFieldNamingPolicy)
  {
    return setFieldNamingStrategy(paramFieldNamingPolicy.getFieldNamingPolicy());
  }

  GsonBuilder setFieldNamingStrategy(FieldNamingStrategy2 paramFieldNamingStrategy2)
  {
    this.fieldNamingPolicy = new SerializedNameAnnotationInterceptingNamingPolicy(paramFieldNamingStrategy2);
    return this;
  }

  public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy paramFieldNamingStrategy)
  {
    return setFieldNamingStrategy(new FieldNamingStrategy2Adapter(paramFieldNamingStrategy));
  }

  public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy paramLongSerializationPolicy)
  {
    this.longSerializationPolicy = paramLongSerializationPolicy;
    return this;
  }

  public GsonBuilder setPrettyPrinting()
  {
    this.prettyPrinting = true;
    return this;
  }

  public GsonBuilder setVersion(double paramDouble)
  {
    this.ignoreVersionsAfter = paramDouble;
    return this;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.GsonBuilder
 * JD-Core Version:    0.6.2
 */