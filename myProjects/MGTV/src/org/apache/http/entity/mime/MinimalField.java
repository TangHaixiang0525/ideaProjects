package org.apache.http.entity.mime;

public class MinimalField
{
  private final String name;
  private final String value;

  MinimalField(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.value = paramString2;
  }

  public String getBody()
  {
    return this.value;
  }

  public String getName()
  {
    return this.name;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.name);
    localStringBuilder.append(": ");
    localStringBuilder.append(this.value);
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.apache.http.entity.mime.MinimalField
 * JD-Core Version:    0.6.2
 */