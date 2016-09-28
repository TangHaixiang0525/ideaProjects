package com.google.gson;

import com.google.gson.annotations.Expose;

final class ExposeAnnotationSerializationExclusionStrategy
  implements ExclusionStrategy
{
  public boolean shouldSkipClass(Class<?> paramClass)
  {
    return false;
  }

  public boolean shouldSkipField(FieldAttributes paramFieldAttributes)
  {
    Expose localExpose = (Expose)paramFieldAttributes.getAnnotation(Expose.class);
    if (localExpose == null);
    while (!localExpose.serialize())
      return true;
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.ExposeAnnotationSerializationExclusionStrategy
 * JD-Core Version:    0.6.2
 */