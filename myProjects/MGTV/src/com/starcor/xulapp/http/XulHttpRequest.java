package com.starcor.xulapp.http;

import android.text.TextUtils;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class XulHttpRequest
{
  public String fragment;
  public String host;
  public String method = "get";
  public String path;
  public int port = -1;
  public LinkedHashMap<String, String> queries;
  public String schema = "http";

  public XulHttpRequest addQueryString(String paramString1, String paramString2)
  {
    if (this.queries == null)
      this.queries = new LinkedHashMap();
    this.queries.put(paramString1, paramString2);
    return this;
  }

  public StringBuilder buildHostString(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(this.host);
    if (this.port > 0)
      paramStringBuilder.append(":").append(this.port);
    return paramStringBuilder;
  }

  public StringBuilder buildQueryString(StringBuilder paramStringBuilder)
  {
    int i = 0;
    if (this.queries != null)
    {
      Iterator localIterator = this.queries.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (i > 0)
          paramStringBuilder.append("&");
        i++;
        paramStringBuilder.append(URLEncoder.encode((String)localEntry.getKey()));
        if (localEntry.getValue() != null)
          paramStringBuilder.append("=").append(URLEncoder.encode((String)localEntry.getValue()));
      }
    }
    return paramStringBuilder;
  }

  public String getHostString()
  {
    return buildHostString(new StringBuilder()).toString();
  }

  public String getQueryString()
  {
    return buildQueryString(new StringBuilder()).toString();
  }

  public String getQueryString(String paramString)
  {
    if (this.queries == null)
      return null;
    return (String)this.queries.get(paramString);
  }

  public XulHttpRequest makeClone()
  {
    XulHttpRequest localXulHttpRequest = makeCloneNoQueryString();
    if (this.queries != null)
      localXulHttpRequest.queries = ((LinkedHashMap)this.queries.clone());
    return localXulHttpRequest;
  }

  public XulHttpRequest makeCloneNoQueryString()
  {
    XulHttpRequest localXulHttpRequest = new XulHttpRequest();
    localXulHttpRequest.method = this.method;
    localXulHttpRequest.schema = this.schema;
    localXulHttpRequest.host = this.host;
    localXulHttpRequest.port = this.port;
    localXulHttpRequest.path = this.path;
    localXulHttpRequest.fragment = this.fragment;
    return localXulHttpRequest;
  }

  public String removeQueryString(String paramString)
  {
    if (this.queries == null)
      return null;
    return (String)this.queries.remove(paramString);
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.schema).append("://");
    buildHostString(localStringBuilder);
    if (!TextUtils.isEmpty(this.path))
      localStringBuilder.append(this.path);
    if ((this.queries != null) && (!this.queries.isEmpty()))
    {
      localStringBuilder.append("?");
      buildQueryString(localStringBuilder);
    }
    if (!TextUtils.isEmpty(this.fragment))
      localStringBuilder.append("#").append(this.fragment);
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.http.XulHttpRequest
 * JD-Core Version:    0.6.2
 */