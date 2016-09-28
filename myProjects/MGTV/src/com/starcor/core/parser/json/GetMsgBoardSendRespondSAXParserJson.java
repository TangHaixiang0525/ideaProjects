package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.MessageBoardSendRepond;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import org.json.JSONObject;

public class GetMsgBoardSendRespondSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private MessageBoardSendRepond message = new MessageBoardSendRepond();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      String str = new String(paramArrayOfByte);
      Logger.i("GetMsgBoardSendRespondSAXParserJson", "jsonString:" + str);
      JSONObject localJSONObject = new JSONObject(str);
      if (localJSONObject.has("err"))
        this.message.err = localJSONObject.getString("err");
      if (localJSONObject.has("code"))
        this.message.code = localJSONObject.getString("code");
      if (localJSONObject.has("msg"))
        this.message.msg = localJSONObject.getString("msg");
      Log.e("GetMsgBoardSendRespondSAXParserJson", "解析器解析得到的对象：message:" + this.message.toString());
      return this.message;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetMsgBoardSendRespondSAXParserJson
 * JD-Core Version:    0.6.2
 */