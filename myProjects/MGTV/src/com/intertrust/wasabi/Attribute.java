package com.intertrust.wasabi;

public final class Attribute
{
  private String name;
  private Object value;

  Attribute(String paramString, Object paramObject)
  {
    this.name = paramString;
    this.value = paramObject;
  }

  public Attribute getChildByName(String paramString)
  {
    while (true)
    {
      int i;
      try
      {
        Attribute[] arrayOfAttribute = (Attribute[])this.value;
        i = 0;
        if (i >= arrayOfAttribute.length)
          break;
        if (paramString.equals(arrayOfAttribute[i].name))
          return arrayOfAttribute[i];
      }
      catch (ClassCastException localClassCastException)
      {
        return null;
      }
      i++;
    }
    return null;
  }

  public String getName()
  {
    return this.name;
  }

  public Object getValue()
  {
    return this.value;
  }

  public String toString()
  {
    String str1 = "{name:" + this.name + ", value:";
    String str3;
    if ((this.value instanceof Attribute[]))
    {
      str3 = str1 + "[";
      Attribute[] arrayOfAttribute = (Attribute[])this.value;
      for (int i = 0; i < arrayOfAttribute.length; i++)
      {
        if (i != 0)
          str3 = str3 + ", ";
        str3 = str3 + arrayOfAttribute[i].toString();
      }
    }
    for (String str2 = str3 + "]"; ; str2 = str1 + this.value.toString())
      return str2 + "}";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.Attribute
 * JD-Core Version:    0.6.2
 */