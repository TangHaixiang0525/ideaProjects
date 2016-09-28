package com.hp.hpl.sparta;

public abstract interface ParseSource
{
  public static final ParseLog DEFAULT_LOG = new DefaultLog();
  public static final int MAXLOOKAHEAD = 40 + "<?xml version=\"1.0\" encoding=\"\"".length();

  public abstract int getLineNumber();

  public abstract String getSystemId();

  public abstract String toString();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.ParseSource
 * JD-Core Version:    0.6.2
 */