package com.hp.hpl.sparta;

import java.io.PrintStream;

class DefaultLog
  implements ParseLog
{
  public void error(String paramString1, String paramString2, int paramInt)
  {
    System.err.println(paramString2 + "(" + paramInt + "): " + paramString1 + " (ERROR)");
  }

  public void note(String paramString1, String paramString2, int paramInt)
  {
    System.out.println(paramString2 + "(" + paramInt + "): " + paramString1 + " (NOTE)");
  }

  public void warning(String paramString1, String paramString2, int paramInt)
  {
    System.out.println(paramString2 + "(" + paramInt + "): " + paramString1 + " (WARNING)");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.DefaultLog
 * JD-Core Version:    0.6.2
 */