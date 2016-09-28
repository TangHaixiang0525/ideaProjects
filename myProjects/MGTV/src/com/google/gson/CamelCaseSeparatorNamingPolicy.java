package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

final class CamelCaseSeparatorNamingPolicy extends RecursiveFieldNamingPolicy
{
  private final String separatorString;

  public CamelCaseSeparatorNamingPolicy(String paramString)
  {
    .Gson.Preconditions.checkNotNull(paramString);
    if (!"".equals(paramString));
    for (boolean bool = true; ; bool = false)
    {
      .Gson.Preconditions.checkArgument(bool);
      this.separatorString = paramString;
      return;
    }
  }

  protected String translateName(String paramString, Type paramType, Collection<Annotation> paramCollection)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((Character.isUpperCase(c)) && (localStringBuilder.length() != 0))
        localStringBuilder.append(this.separatorString);
      localStringBuilder.append(c);
    }
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.CamelCaseSeparatorNamingPolicy
 * JD-Core Version:    0.6.2
 */