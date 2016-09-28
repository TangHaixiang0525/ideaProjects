package com.google.gson.internal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParameterizedTypeHandlerMap<T>
{
  private static final Logger logger = Logger.getLogger(ParameterizedTypeHandlerMap.class.getName());
  private boolean modifiable = true;
  private final Map<Type, T> systemMap = new HashMap();
  private final List<Pair<Class<?>, T>> systemTypeHierarchyList = new ArrayList();
  private final Map<Type, T> userMap = new HashMap();
  private final List<Pair<Class<?>, T>> userTypeHierarchyList = new ArrayList();

  private void appendList(StringBuilder paramStringBuilder, List<Pair<Class<?>, T>> paramList)
  {
    int i = 1;
    Iterator localIterator = paramList.iterator();
    if (localIterator.hasNext())
    {
      Pair localPair = (Pair)localIterator.next();
      if (i != 0)
        i = 0;
      while (true)
      {
        paramStringBuilder.append(typeToString((Type)localPair.first)).append(':');
        paramStringBuilder.append(localPair.second);
        break;
        paramStringBuilder.append(',');
      }
    }
  }

  private void appendMap(StringBuilder paramStringBuilder, Map<Type, T> paramMap)
  {
    int i = 1;
    Iterator localIterator = paramMap.entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (i != 0)
        i = 0;
      while (true)
      {
        paramStringBuilder.append(typeToString((Type)localEntry.getKey())).append(':');
        paramStringBuilder.append(localEntry.getValue());
        break;
        paramStringBuilder.append(',');
      }
    }
  }

  private T getHandlerForTypeHierarchy(Class<?> paramClass, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      Iterator localIterator2 = this.userTypeHierarchyList.iterator();
      while (localIterator2.hasNext())
      {
        Pair localPair2 = (Pair)localIterator2.next();
        if (((Class)localPair2.first).isAssignableFrom(paramClass))
          return localPair2.second;
      }
    }
    Iterator localIterator1 = this.systemTypeHierarchyList.iterator();
    while (localIterator1.hasNext())
    {
      Pair localPair1 = (Pair)localIterator1.next();
      if (((Class)localPair1.first).isAssignableFrom(paramClass))
        return localPair1.second;
    }
    return null;
  }

  private static <T> int getIndexOfAnOverriddenHandler(Class<?> paramClass, List<Pair<Class<?>, T>> paramList)
  {
    for (int i = -1 + paramList.size(); i >= 0; i--)
      if (paramClass.isAssignableFrom((Class)((Pair)paramList.get(i)).first))
        return i;
    return -1;
  }

  private static <T> int getIndexOfSpecificHandlerForTypeHierarchy(Class<?> paramClass, List<Pair<Class<?>, T>> paramList)
  {
    for (int i = -1 + paramList.size(); i >= 0; i--)
      if (paramClass.equals(((Pair)paramList.get(i)).first))
        return i;
    return -1;
  }

  private String typeToString(Type paramType)
  {
    return .Gson.Types.getRawType(paramType).getSimpleName();
  }

  public ParameterizedTypeHandlerMap<T> copyOf()
  {
    try
    {
      ParameterizedTypeHandlerMap localParameterizedTypeHandlerMap = new ParameterizedTypeHandlerMap();
      localParameterizedTypeHandlerMap.systemMap.putAll(this.systemMap);
      localParameterizedTypeHandlerMap.userMap.putAll(this.userMap);
      localParameterizedTypeHandlerMap.systemTypeHierarchyList.addAll(this.systemTypeHierarchyList);
      localParameterizedTypeHandlerMap.userTypeHierarchyList.addAll(this.userTypeHierarchyList);
      return localParameterizedTypeHandlerMap;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public T getHandlerFor(Type paramType, boolean paramBoolean)
  {
    if (!paramBoolean);
    try
    {
      Object localObject6 = this.userMap.get(paramType);
      Object localObject5;
      if (localObject6 != null)
        localObject5 = localObject6;
      while (true)
      {
        return localObject5;
        Object localObject2 = this.systemMap.get(paramType);
        if (localObject2 != null)
        {
          localObject5 = localObject2;
        }
        else
        {
          Class localClass = .Gson.Types.getRawType(paramType);
          if (localClass != paramType)
          {
            Object localObject3 = getHandlerFor(localClass, paramBoolean);
            if (localObject3 != null)
              localObject5 = localObject3;
          }
          else
          {
            Object localObject4 = getHandlerForTypeHierarchy(localClass, paramBoolean);
            localObject5 = localObject4;
          }
        }
      }
    }
    finally
    {
    }
  }

  public boolean hasSpecificHandlerFor(Type paramType)
  {
    try
    {
      if (!this.userMap.containsKey(paramType))
      {
        boolean bool2 = this.systemMap.containsKey(paramType);
        if (!bool2);
      }
      else
      {
        bool1 = true;
        return bool1;
      }
      boolean bool1 = false;
    }
    finally
    {
    }
  }

  public ParameterizedTypeHandlerMap<T> makeUnmodifiable()
  {
    try
    {
      this.modifiable = false;
      return this;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void register(Type paramType, T paramT, boolean paramBoolean)
  {
    try
    {
      if (!this.modifiable)
        throw new IllegalStateException("Attempted to modify an unmodifiable map.");
    }
    finally
    {
    }
    if (hasSpecificHandlerFor(paramType))
      logger.log(Level.WARNING, "Overriding the existing type handler for {0}", paramType);
    if (paramBoolean);
    for (Map localMap = this.systemMap; ; localMap = this.userMap)
    {
      localMap.put(paramType, paramT);
      return;
    }
  }

  public void registerForTypeHierarchy(Pair<Class<?>, T> paramPair, boolean paramBoolean)
  {
    try
    {
      if (!this.modifiable)
        throw new IllegalStateException("Attempted to modify an unmodifiable map.");
    }
    finally
    {
    }
    if (paramBoolean);
    for (List localList = this.systemTypeHierarchyList; ; localList = this.userTypeHierarchyList)
    {
      int i = getIndexOfSpecificHandlerForTypeHierarchy((Class)paramPair.first, localList);
      if (i >= 0)
      {
        logger.log(Level.WARNING, "Overriding the existing type handler for {0}", paramPair.first);
        localList.remove(i);
      }
      int j = getIndexOfAnOverriddenHandler((Class)paramPair.first, localList);
      if (j < 0)
        break;
      throw new IllegalArgumentException("The specified type handler for type " + paramPair.first + " hides the previously registered type hierarchy handler for " + ((Pair)localList.get(j)).first + ". Gson does not allow this.");
    }
    localList.add(0, paramPair);
  }

  public void registerForTypeHierarchy(Class<?> paramClass, T paramT, boolean paramBoolean)
  {
    try
    {
      registerForTypeHierarchy(new Pair(paramClass, paramT), paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void registerIfAbsent(ParameterizedTypeHandlerMap<T> paramParameterizedTypeHandlerMap)
  {
    try
    {
      if (!this.modifiable)
        throw new IllegalStateException("Attempted to modify an unmodifiable map.");
    }
    finally
    {
    }
    Iterator localIterator1 = paramParameterizedTypeHandlerMap.userMap.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry2 = (Map.Entry)localIterator1.next();
      if (!this.userMap.containsKey(localEntry2.getKey()))
        register((Type)localEntry2.getKey(), localEntry2.getValue(), false);
    }
    Iterator localIterator2 = paramParameterizedTypeHandlerMap.systemMap.entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
      if (!this.systemMap.containsKey(localEntry1.getKey()))
        register((Type)localEntry1.getKey(), localEntry1.getValue(), true);
    }
    for (int i = -1 + paramParameterizedTypeHandlerMap.userTypeHierarchyList.size(); ; i--)
      if (i >= 0)
      {
        Pair localPair1 = (Pair)paramParameterizedTypeHandlerMap.userTypeHierarchyList.get(i);
        if (getIndexOfSpecificHandlerForTypeHierarchy((Class)localPair1.first, this.userTypeHierarchyList) < 0)
          registerForTypeHierarchy(localPair1, false);
      }
      else
      {
        for (int j = -1 + paramParameterizedTypeHandlerMap.systemTypeHierarchyList.size(); j >= 0; j--)
        {
          Pair localPair2 = (Pair)paramParameterizedTypeHandlerMap.systemTypeHierarchyList.get(j);
          if (getIndexOfSpecificHandlerForTypeHierarchy((Class)localPair2.first, this.systemTypeHierarchyList) < 0)
            registerForTypeHierarchy(localPair2, true);
        }
        return;
      }
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("{userTypeHierarchyList:{");
    appendList(localStringBuilder, this.userTypeHierarchyList);
    localStringBuilder.append("},systemTypeHierarchyList:{");
    appendList(localStringBuilder, this.systemTypeHierarchyList);
    localStringBuilder.append("},userMap:{");
    appendMap(localStringBuilder, this.userMap);
    localStringBuilder.append("},systemMap:{");
    appendMap(localStringBuilder, this.systemMap);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.ParameterizedTypeHandlerMap
 * JD-Core Version:    0.6.2
 */