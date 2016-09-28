package com.sohu.upload;

import com.sohu.upload.b.b;
import com.sohu.upload.b.c;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

final class a extends Thread
{
  public void run()
  {
    try
    {
      InputStream localInputStream = com.sohu.upload.b.a.a("http://hui.sohu.com/5adop/mo/ss?appname=sogouinput&v=1000&p=android", null);
      if (localInputStream == null)
        return;
      String str1 = new String(c.a(localInputStream));
      com.sohu.upload.a.a.a("switchUrl_Json:", str1);
      String str2 = c.f(str1);
      com.sohu.upload.a.a.a("change_Json:" + str2);
      JSONObject localJSONObject = new JSONObject(str2);
      if (localJSONObject.has("LOG"))
        b.a("log", localJSONObject.getString("LOG"));
      if (localJSONObject.has("POSTBACK"))
        b.a("postback", localJSONObject.getString("POSTBACK"));
      com.sohu.upload.a.a.a("switch loaded complete...");
      return;
    }
    catch (JSONException localJSONException)
    {
      com.sohu.upload.a.a.b("�������翪�ص��쳣:" + localJSONException.getMessage());
      return;
    }
    catch (IOException localIOException)
    {
      com.sohu.upload.a.a.b("�������翪�ص��쳣:" + localIOException.getMessage());
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�������翪�ص��쳣:" + localException.getMessage());
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.a
 * JD-Core Version:    0.6.2
 */