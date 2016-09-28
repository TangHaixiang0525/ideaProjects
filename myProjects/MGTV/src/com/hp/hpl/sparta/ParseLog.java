package com.hp.hpl.sparta;

public abstract interface ParseLog
{
  public abstract void error(String paramString1, String paramString2, int paramInt);

  public abstract void note(String paramString1, String paramString2, int paramInt);

  public abstract void warning(String paramString1, String paramString2, int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.ParseLog
 * JD-Core Version:    0.6.2
 */