package com.starcor.core.parser.json;

import android.util.Log;
import com.starcor.core.domain.SystemMessage;
import com.starcor.core.domain.SystemMessageBody;
import com.starcor.core.domain.SystemMessageBody.VideoDetail;
import com.starcor.core.domain.SystemMessageBody.VideoId;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetSystemMessageSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private SystemMessage message = new SystemMessage();

  private void getMsgList(JSONObject paramJSONObject)
  {
    try
    {
      JSONArray localJSONArray1 = new JSONArray(paramJSONObject.getString("msg_list"));
      this.message.systemMsgList = new ArrayList();
      for (int i = 0; i < localJSONArray1.length(); i++)
      {
        SystemMessageBody localSystemMessageBody = new SystemMessageBody();
        JSONArray localJSONArray2 = localJSONArray1.getJSONArray(i);
        localSystemMessageBody.msgID = localJSONArray2.getString(0);
        JSONObject localJSONObject = new JSONObject(localJSONArray2.getString(1));
        if (localJSONObject.has("title"))
          localSystemMessageBody.title = localJSONObject.getString("title");
        if (localJSONObject.has("body"))
          localSystemMessageBody.content = localJSONObject.getString("body");
        if (localJSONObject.has("type"))
          localSystemMessageBody.type = localJSONObject.getString("type");
        if (localJSONObject.has("publishtime"))
          localSystemMessageBody.publishTime = localJSONObject.getString("publishtime");
        if (localJSONObject.has("typevalue"))
        {
          String str = localJSONObject.getString("typevalue");
          if ("video".equals(localSystemMessageBody.type))
          {
            Logger.i("GetSystemMessageSAXParserJson", "typeValue=" + str);
            Matcher localMatcher3 = Pattern.compile("asset_id=" + "*([^&?]*)", 2).matcher(str);
            if (localMatcher3.find())
            {
              localSystemMessageBody.videoId.assetId = localMatcher3.group(0).toLowerCase();
              localSystemMessageBody.videoId.assetId = localSystemMessageBody.videoId.assetId.substring("asset_id=".length(), localSystemMessageBody.videoId.assetId.length());
            }
            Matcher localMatcher4 = Pattern.compile("clip_id=" + "*([^&?]*)", 2).matcher(str);
            if (localMatcher4.find())
            {
              localSystemMessageBody.videoId.clipId = localMatcher4.group(0).toLowerCase();
              localSystemMessageBody.videoId.clipId = localSystemMessageBody.videoId.clipId.substring("clip_id=".length(), localSystemMessageBody.videoId.clipId.length());
            }
            Matcher localMatcher5 = Pattern.compile("file_id=" + "*([^&?]*)", 2).matcher(str);
            if (localMatcher5.find())
            {
              localSystemMessageBody.videoId.fileId = localMatcher5.group(0).toLowerCase();
              localSystemMessageBody.videoId.fileId = localSystemMessageBody.videoId.fileId.substring("file_id=".length(), localSystemMessageBody.videoId.fileId.length());
            }
          }
          if ("detail".equals(localSystemMessageBody.type))
          {
            Matcher localMatcher1 = Pattern.compile("asset_id=" + "*([^&?]*)", 2).matcher(str);
            if (localMatcher1.find())
            {
              localSystemMessageBody.videoDetail.assetId = localMatcher1.group(0).toLowerCase();
              localSystemMessageBody.videoDetail.assetId = localSystemMessageBody.videoDetail.assetId.substring("asset_id=".length(), localSystemMessageBody.videoDetail.assetId.length());
            }
            Matcher localMatcher2 = Pattern.compile("assets_category=" + "*([^&?]*)", 2).matcher(str);
            if (localMatcher2.find())
            {
              localSystemMessageBody.videoDetail.assetsCategory = localMatcher2.group(0).toLowerCase();
              localSystemMessageBody.videoDetail.assetsCategory = localSystemMessageBody.videoDetail.assetsCategory.substring("assets_category=".length(), localSystemMessageBody.videoDetail.assetsCategory.length());
            }
          }
          if ("page".equals(localSystemMessageBody.type))
            localSystemMessageBody.webPage.url = str;
        }
        this.message.systemMsgList.add(localSystemMessageBody);
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
      Logger.i("GetSystemMessageSAXParserJson", "jsonString:" + str);
      JSONObject localJSONObject = new JSONObject(str);
      if (localJSONObject.has("emsg"))
        this.message.errorMsg = localJSONObject.getString("emsg");
      if (localJSONObject.has("error"))
        this.message.errorCode = Integer.valueOf(localJSONObject.getString("error")).intValue();
      if (localJSONObject.has("lmsgid"))
        this.message.msgID = localJSONObject.getString("lmsgid");
      if (localJSONObject.has("msg_list"))
        getMsgList(localJSONObject);
      Log.e("GetSystemMessageSAXParserJson", "GetSystemMessageSAXParserJson解析器解析得到的对象：message:" + this.message.toString());
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
 * Qualified Name:     com.starcor.core.parser.json.GetSystemMessageSAXParserJson
 * JD-Core Version:    0.6.2
 */