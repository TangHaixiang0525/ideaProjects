package com.starcor.core.epgapi;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.entity.mime.content.StringBody;

public class StringParams
{
  private String name;
  private String value;

  public StringParams(String paramString)
  {
    this.name = paramString;
  }

  public StringBody getBody()
  {
    try
    {
      if (this.value != null)
        return new StringBody(this.value);
      StringBody localStringBody = new StringBody("");
      return localStringBody;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return null;
  }

  public String getName()
  {
    return this.name;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }

  public String toFirstString()
  {
    try
    {
      if (!TextUtils.isEmpty(this.value))
        return this.name + "=" + URLEncoder.encode(this.value, "utf-8");
      String str = this.name + "=";
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return this.name + "=" + this.value;
  }

  public String toString()
  {
    try
    {
      if (!TextUtils.isEmpty(this.value))
        return "&" + this.name + "=" + URLEncoder.encode(this.value, "utf-8");
      String str = "&" + this.name + "=";
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return "&" + this.name + "=" + this.value;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.StringParams
 * JD-Core Version:    0.6.2
 */