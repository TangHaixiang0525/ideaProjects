package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

abstract class RecursiveFieldNamingPolicy
  implements FieldNamingStrategy2
{
  public final String translateName(FieldAttributes paramFieldAttributes)
  {
    return translateName(paramFieldAttributes.getName(), paramFieldAttributes.getDeclaredType(), paramFieldAttributes.getAnnotations());
  }

  protected abstract String translateName(String paramString, Type paramType, Collection<Annotation> paramCollection);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.RecursiveFieldNamingPolicy
 * JD-Core Version:    0.6.2
 */