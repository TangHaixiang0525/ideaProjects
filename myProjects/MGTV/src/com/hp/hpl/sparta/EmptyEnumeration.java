package com.hp.hpl.sparta;

import java.util.Enumeration;
import java.util.NoSuchElementException;

class EmptyEnumeration
  implements Enumeration
{
  public boolean hasMoreElements()
  {
    return false;
  }

  public Object nextElement()
  {
    throw new NoSuchElementException();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.EmptyEnumeration
 * JD-Core Version:    0.6.2
 */