package com.sohu.upload.b;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class a
{
  public static InputStream a(String paramString1, String paramString2)
  {
    HttpGet localHttpGet;
    if ((paramString2 != null) && (!"".equals(paramString2)))
      localHttpGet = new HttpGet(paramString1 + "?" + paramString2);
    while (true)
    {
      HttpParams localHttpParams = localHttpGet.getParams();
      HttpConnectionParams.setConnectionTimeout(localHttpParams, com.sohu.upload.consts.a.g);
      HttpConnectionParams.setSoTimeout(localHttpParams, com.sohu.upload.consts.a.g);
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      try
      {
        com.sohu.upload.a.a.a("Before response....");
        HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
        int i = localHttpResponse.getStatusLine().getStatusCode();
        com.sohu.upload.a.a.a("After response....��Ӧ��::" + i);
        if ((i == 200) || (i == 201))
        {
          InputStream localInputStream = localHttpResponse.getEntity().getContent();
          return localInputStream;
          localHttpGet = new HttpGet(paramString1);
        }
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        localClientProtocolException.printStackTrace();
        return null;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return null;
      }
    }
    return null;
  }

  public static boolean a(String paramString, HashMap<String, String> paramHashMap)
  {
    HttpPost localHttpPost = new HttpPost(paramString);
    ArrayList localArrayList = new ArrayList();
    if (paramHashMap != null)
    {
      Iterator localIterator = paramHashMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(), (String)localEntry.getValue()));
      }
    }
    com.sohu.upload.a.a.b("DEMO:", "�ϴ��������");
    return false;
    try
    {
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      localDefaultHttpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(com.sohu.upload.consts.a.g));
      localDefaultHttpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(com.sohu.upload.consts.a.g));
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      com.sohu.upload.a.a.b("ע�᷵����:" + localHttpResponse.getStatusLine().getStatusCode());
      int i = localHttpResponse.getStatusLine().getStatusCode();
      if (i == 200)
        return true;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localClientProtocolException.getMessage());
      return false;
    }
    catch (IOException localIOException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localIOException.getMessage());
      return false;
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.b.a
 * JD-Core Version:    0.6.2
 */