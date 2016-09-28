package com.miaozhen.mzmonitor;

import java.util.Comparator;
import java.util.Map;

class p
  implements Comparator
{
  private p(o paramo)
  {
  }

  public int compare(Object paramObject1, Object paramObject2)
  {
    if (((Integer)o.a(this.a).get(paramObject1)).intValue() < ((Integer)o.a(this.a).get(paramObject2)).intValue())
      return 1;
    if (o.a(this.a).get(paramObject1) == o.a(this.a).get(paramObject2))
      return 0;
    return -1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.p
 * JD-Core Version:    0.6.2
 */