package com.sohu.upload.service;

import com.sohu.upload.b.b;
import com.sohu.upload.b.c;
import com.sohu.upload.b.d;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

class e extends Thread
{
  e(CountService paramCountService)
  {
  }

  public void run()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("imei", c.d());
    }
    catch (JSONException localJSONException6)
    {
      try
      {
        localJSONObject.put("imsi", c.e());
      }
      catch (JSONException localJSONException6)
      {
        try
        {
          label28: localJSONObject.put("hs_ip", c.n() + ":" + CountService.i(this.a));
        }
        catch (JSONException localJSONException6)
        {
          try
          {
            label66: localJSONObject.put("route_mac", c.i());
          }
          catch (JSONException localJSONException6)
          {
            try
            {
              label76: localJSONObject.put("route_ssid", c.h());
            }
            catch (JSONException localJSONException6)
            {
              try
              {
                label86: localJSONObject.put("timetag", System.currentTimeMillis() + "");
                label114: String str1 = "";
                if (localJSONObject != null)
                  str1 = c.c(localJSONObject.toString());
                localHashMap = new HashMap();
                localHashMap.put("content", str1);
              }
              catch (JSONException localJSONException6)
              {
                try
                {
                  while (true)
                  {
                    HashMap localHashMap;
                    boolean bool = c.m();
                    if (bool);
                    try
                    {
                      String str2 = b.b("");
                      String str3 = c.n();
                      new d(this.a, "��ǰ��ip��ַ��:" + str3);
                      com.sohu.upload.a.a.a("��ǰ��ip��ַ��:" + str3);
                      new d(this.a, "֮ǰע���ip��ַ��:" + str2);
                      com.sohu.upload.a.a.a("֮ǰ��ip��ַ��:" + str2);
                      if ((!"".equals(str3)) && (!str3.equals(str2)))
                      {
                        if (!com.sohu.upload.b.a.a("http://hui.sohu.com/mum/httpserverreg", localHashMap))
                          break label769;
                        if (!str3.equals(str2))
                        {
                          b.a(str3);
                          new d(this.a, "ip��ַ����ı���").start();
                          com.sohu.upload.a.a.b("ip��ַ����ı���");
                        }
                        StringBuilder localStringBuilder = new StringBuilder();
                        localStringBuilder.append("imei=");
                        localStringBuilder.append(c.d());
                        localStringBuilder.append("&hs_ip=");
                        localStringBuilder.append(c.n() + ":" + CountService.i(this.a));
                        localStringBuilder.append("&route_mac=");
                        localStringBuilder.append(c.i());
                        localStringBuilder.append("&route_ssid=");
                        localStringBuilder.append(c.h());
                        localStringBuilder.append("&platform=android");
                        localStringBuilder.append("&timetag=");
                        localStringBuilder.append(System.currentTimeMillis() + "");
                        new d(this.a, "ע���url:http://hui.sohu.com/mum/httpserverreg?" + localStringBuilder.toString()).start();
                        new d(this.a, "ע������ɹ�").start();
                        com.sohu.upload.a.a.a("ע������ɹ�");
                        com.sohu.upload.a.a.a("ע���url:http://hui.sohu.com/mum/httpserverreg?" + localStringBuilder.toString());
                      }
                      while (true)
                      {
                        return;
                        localJSONException1 = localJSONException1;
                        com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException1.getMessage());
                        break;
                        localJSONException2 = localJSONException2;
                        com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException2.getMessage());
                        break label28;
                        localJSONException3 = localJSONException3;
                        com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException3.getMessage());
                        break label66;
                        localJSONException4 = localJSONException4;
                        com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException4.getMessage());
                        break label76;
                        localJSONException5 = localJSONException5;
                        com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException5.getMessage());
                        break label86;
                        localJSONException6 = localJSONException6;
                        com.sohu.upload.a.a.b("ƴ��JSON���ֵ��쳣:" + localJSONException6.getMessage());
                        break label114;
                        label769: com.sohu.upload.a.a.b("ע�����ʧ��");
                      }
                    }
                    catch (Exception localException)
                    {
                      while (true)
                        com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",ע��ʧ��");
                    }
                  }
                }
                finally
                {
                }
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.e
 * JD-Core Version:    0.6.2
 */