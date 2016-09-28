package com.google.gson;

final class SyntheticFieldExclusionStrategy
  implements ExclusionStrategy
{
  private final boolean skipSyntheticFields;

  SyntheticFieldExclusionStrategy(boolean paramBoolean)
  {
    this.skipSyntheticFields = paramBoolean;
  }

  public boolean shouldSkipClass(Class<?> paramClass)
  {
    return false;
  }

  public boolean shouldSkipField(FieldAttributes paramFieldAttributes)
  {
    return (this.skipSyntheticFields) && (paramFieldAttributes.isSynthetic());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.SyntheticFieldExclusionStrategy
 * JD-Core Version:    0.6.2
 */