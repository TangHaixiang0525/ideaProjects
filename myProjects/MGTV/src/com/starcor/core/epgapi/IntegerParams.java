package com.starcor.core.epgapi;

public class IntegerParams
{
  private String name;
  private int value = -200;

  public IntegerParams(String paramString)
  {
    this.name = paramString;
  }

  public void setValue(int paramInt)
  {
    this.value = paramInt;
  }

  public String toString()
  {
    if (this.value < -100)
      return "&" + this.name + "=";
    return "&" + this.name + "=" + this.value;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.IntegerParams
 * JD-Core Version:    0.6.2
 */