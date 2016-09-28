package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

final class UpperCaseNamingPolicy extends RecursiveFieldNamingPolicy
{
  protected String translateName(String paramString, Type paramType, Collection<Annotation> paramCollection)
  {
    return paramString.toUpperCase();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.UpperCaseNamingPolicy
 * JD-Core Version:    0.6.2
 */