package com.sohu.mma.tracking.c;

import java.util.Comparator;

class b
  implements Comparator<String>
{
  public int a(String paramString1, String paramString2)
  {
    if (paramString1.length() > paramString2.length())
      return -1;
    return 1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.b
 * JD-Core Version:    0.6.2
 */