package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.MessageBoardData;
import com.starcor.core.domain.MessageBoardDataBody;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetMessageBoardDataSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private MessageBoardData message = new MessageBoardData();

  private void getMsgList(JSONObject paramJSONObject)
  {
    try
    {
      JSONArray localJSONArray = new JSONArray(paramJSONObject.getString("content"));
      Logger.i("getMsgList_1", "jsonArray_itemList:" + localJSONArray.toString());
      this.message.messageBoardDataList = new ArrayList();
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        MessageBoardDataBody localMessageBoardDataBody = new MessageBoardDataBody();
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        Logger.i("getMsgList_1", "jo_2:" + localJSONObject.toString());
        if (localJSONObject.has("username"))
          localMessageBoardDataBody.username = localJSONObject.getString("username");
        if (localJSONObject.has("content"))
          localMessageBoardDataBody.content = localJSONObject.getString("content");
        if (localJSONObject.has("dt"))
          localMessageBoardDataBody.dt = localJSONObject.getString("dt");
        if (localJSONObject.has("phone"))
          localMessageBoardDataBody.phone = localJSONObject.getString("phone");
        this.message.messageBoardDataList.add(localMessageBoardDataBody);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

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
      Logger.i("GetMessageBoardDataSAXParserJson", "jsonString:" + str);
      JSONObject localJSONObject = new JSONObject(str);
      if (localJSONObject.has("size"))
        this.message.size = localJSONObject.getString("size");
      if (localJSONObject.has("content"))
        getMsgList(localJSONObject);
      Log.e("GetMessageBoardDataSAXParserJson", "GetMessageBoardDataSAXParserJson解析器解析得到的对象：message:" + this.message.toString());
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
 * Qualified Name:     com.starcor.core.parser.json.GetMessageBoardDataSAXParserJson
 * JD-Core Version:    0.6.2
 */