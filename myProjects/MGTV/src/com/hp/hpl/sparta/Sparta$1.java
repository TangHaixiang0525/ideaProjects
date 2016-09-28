package com.hp.hpl.sparta;

import java.util.Hashtable;

class Sparta$1
  implements Sparta.Internment
{
  private final Hashtable strings_ = new Hashtable();

  public String intern(String paramString)
  {
    String str = (String)this.strings_.get(paramString);
    if (str == null)
    {
      this.strings_.put(paramString, paramString);
      return paramString;
    }
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.Sparta.1
 * JD-Core Version:    0.6.2
 */