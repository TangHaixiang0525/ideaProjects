package com.starcor.core.domain;

import java.util.HashMap;

public class TimeMapList<T, V> extends HashMap<T, KeyForMultValue<T, V>>
{
  private static final long serialVersionUID = 1L;

  public void clear()
  {
    super.clear();
  }

  public KeyForMultValue<T, V> get(Object paramObject)
  {
    return (KeyForMultValue)super.get(paramObject);
  }

  public KeyForMultValue<T, V> putAll(T paramT1, T paramT2, V paramV)
  {
    if (!super.containsKey(paramT1))
      super.put(paramT1, new KeyForMultValue());
    KeyForMultValue localKeyForMultValue = (KeyForMultValue)super.get(paramT1);
    localKeyForMultValue.putAll(paramT2, paramV);
    return (KeyForMultValue)super.get(localKeyForMultValue);
  }

  public void removeKey(T paramT)
  {
    super.remove(paramT);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.TimeMapList
 * JD-Core Version:    0.6.2
 */