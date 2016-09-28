package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import java.util.Collection;
import java.util.Iterator;

final class DisjunctionExclusionStrategy
  implements ExclusionStrategy
{
  private final Collection<ExclusionStrategy> strategies;

  DisjunctionExclusionStrategy(Collection<ExclusionStrategy> paramCollection)
  {
    this.strategies = ((Collection).Gson.Preconditions.checkNotNull(paramCollection));
  }

  public boolean shouldSkipClass(Class<?> paramClass)
  {
    Iterator localIterator = this.strategies.iterator();
    while (localIterator.hasNext())
      if (((ExclusionStrategy)localIterator.next()).shouldSkipClass(paramClass))
        return true;
    return false;
  }

  public boolean shouldSkipField(FieldAttributes paramFieldAttributes)
  {
    Iterator localIterator = this.strategies.iterator();
    while (localIterator.hasNext())
      if (((ExclusionStrategy)localIterator.next()).shouldSkipField(paramFieldAttributes))
        return true;
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.DisjunctionExclusionStrategy
 * JD-Core Version:    0.6.2
 */