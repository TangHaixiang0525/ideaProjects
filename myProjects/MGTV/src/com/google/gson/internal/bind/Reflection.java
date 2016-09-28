package com.google.gson.internal.bind;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class Reflection
{
  public static Type getRuntimeTypeIfMoreSpecific(Type paramType, Object paramObject)
  {
    if ((paramObject != null) && ((paramType == Object.class) || ((paramType instanceof TypeVariable)) || ((paramType instanceof Class))))
      paramType = paramObject.getClass();
    return paramType;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.Reflection
 * JD-Core Version:    0.6.2
 */