package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.StreamTools;
import java.io.InputStream;
import org.json.JSONObject;

public class CheckUserInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "CheckUserInfoSAXParserJson";
  private String device_id;
  private String mac_id;
  private UserInfo uInfo;

  public CheckUserInfoSAXParserJson(String paramString1, String paramString2)
  {
    this.device_id = paramString1;
    this.mac_id = paramString2;
  }

  public Result parser(InputStream paramInputStream)
  {
    Log.d("CheckUserInfoSAXParserJson", "执行了CheckUserInfoSAXParserJson这个解析器");
    if (paramInputStream == null)
      return null;
    this.uInfo = new UserInfo();
    try
    {
      JSONObject localJSONObject = new JSONObject(new String(StreamTools.getBytes(paramInputStream)));
      this.uInfo.device_id = this.device_id;
      this.uInfo.mac_id = this.mac_id;
      if (localJSONObject.has("id"))
        this.uInfo.user_id = localJSONObject.getString("id");
      if (localJSONObject.has("state"))
        this.uInfo.state = localJSONObject.getString("state");
      if (localJSONObject.has("token"))
        this.uInfo.nn_token = localJSONObject.getString("token");
      if (localJSONObject.has("web_token"))
        this.uInfo.web_token = localJSONObject.getString("web_token");
      Logger.d("CheckUserInfoSAXParserJson解析器解析得到的对象：userInfo=" + this.uInfo.toString());
      return this.uInfo;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        Logger.d("userInfo=" + this.uInfo.toString());
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.CheckUserInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */