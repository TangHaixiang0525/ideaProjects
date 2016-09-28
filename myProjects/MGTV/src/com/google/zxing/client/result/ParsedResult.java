package com.google.zxing.client.result;

public abstract class ParsedResult
{
  private final ParsedResultType type;

  protected ParsedResult(ParsedResultType paramParsedResultType)
  {
    this.type = paramParsedResultType;
  }

  public static void maybeAppend(String paramString, StringBuilder paramStringBuilder)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      if (paramStringBuilder.length() > 0)
        paramStringBuilder.append('\n');
      paramStringBuilder.append(paramString);
    }
  }

  public static void maybeAppend(String[] paramArrayOfString, StringBuilder paramStringBuilder)
  {
    if (paramArrayOfString != null)
    {
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++)
      {
        String str = paramArrayOfString[j];
        if ((str != null) && (str.length() > 0))
        {
          if (paramStringBuilder.length() > 0)
            paramStringBuilder.append('\n');
          paramStringBuilder.append(str);
        }
      }
    }
  }

  public abstract String getDisplayResult();

  public ParsedResultType getType()
  {
    return this.type;
  }

  public String toString()
  {
    return getDisplayResult();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ParsedResult
 * JD-Core Version:    0.6.2
 */