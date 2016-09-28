package com.starcor.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class KeyForMultValue<T, V> extends HashMap<T, List<V>>
{
  private static final long serialVersionUID = 1L;

  public void clear()
  {
    super.clear();
  }

  public List<V> get(Object paramObject)
  {
    return (List)super.get(paramObject);
  }

  public List<V> putAll(T paramT, V paramV)
  {
    if (!super.containsKey(paramT))
      super.put(paramT, new Vector());
    List localList = (List)super.get(paramT);
    localList.add(paramV);
    return (List)super.get(localList);
  }

  public void removeKey(T paramT)
  {
    super.remove(paramT);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.KeyForMultValue
 * JD-Core Version:    0.6.2
 */