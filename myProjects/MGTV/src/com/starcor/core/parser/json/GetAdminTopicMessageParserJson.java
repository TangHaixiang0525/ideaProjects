package com.starcor.core.parser.json;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.admintopic.AdminTopicMessageData;
import com.starcor.hunan.msgsys.data.admintopic.AdminTopicSubData;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetAdminTopicMessageParserJson
{
  private static final String ADMIN_ACTION_KEY = "mpns_admin_action";
  private static final String ADMIN_MESSAGE = "mpns_admin_message";
  private static final String DETAIL_KEY = "detail";
  private static final String MESSAGE_KEY = "message";
  private static final String NEXT_CONNECT_TIME_KEY = "next_connect_time";
  private static final String PAGE_KEY = "page";
  private static final String SPECIAL_KEY = "topic";
  private static final String TAG = GetAdminTopicMessageParserJson.class.getSimpleName();
  private static final String VIDEO_KEY = "video";
  private static final String WHEN_KEY = "when";

  public static AdminTopicMessageData parser(String paramString)
  {
    if ((paramString == null) || (TextUtils.isEmpty(paramString)))
    {
      Logger.i(TAG, "parser() jsonString 为 null或者为空");
      return null;
    }
    AdminTopicMessageData localAdminTopicMessageData = new AdminTopicMessageData();
    while (true)
    {
      String str;
      JSONObject localJSONObject2;
      try
      {
        Logger.i(TAG, "jsonString:" + paramString);
        JSONObject localJSONObject1 = new JSONObject(paramString);
        str = localJSONObject1.optString("mpns_admin_action");
        localAdminTopicMessageData.setMpns_admin_action(str);
        if (str != null)
        {
          localJSONObject2 = localJSONObject1.getJSONObject("mpns_admin_message");
          if (str.equals("update-action-levels"))
          {
            localAdminTopicMessageData.getMpns_admin_message().setMessage(localJSONObject2.optInt("message"));
            localAdminTopicMessageData.getMpns_admin_message().setPage(localJSONObject2.optInt("page"));
            localAdminTopicMessageData.getMpns_admin_message().setDetail(localJSONObject2.optInt("detail"));
            localAdminTopicMessageData.getMpns_admin_message().setVideo(localJSONObject2.optInt("video"));
            localAdminTopicMessageData.getMpns_admin_message().setTopic(localJSONObject2.optInt("topic"));
          }
        }
        else
        {
          Logger.i(TAG, "解析完毕后的adminTopicData为" + localAdminTopicMessageData.toString());
          return localAdminTopicMessageData;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return localAdminTopicMessageData;
      }
      if (str.equals("clear-messages"))
      {
        JSONArray localJSONArray = localJSONObject2.optJSONArray("message_ids");
        if (localJSONArray != null)
        {
          int i = localJSONArray.length();
          if (i > 0)
          {
            String[] arrayOfString = new String[i];
            for (int j = 0; j < i; j++)
              arrayOfString[j] = ((String)localJSONArray.get(j));
            localAdminTopicMessageData.getMpns_admin_message().setMessage_ids(arrayOfString);
          }
        }
      }
      else if (str.equals("disconnect"))
      {
        localAdminTopicMessageData.getMpns_admin_message().setWhen(localJSONObject2.optString("when"));
        localAdminTopicMessageData.getMpns_admin_message().setNext_connect_time(localJSONObject2.optInt("next_connect_time"));
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetAdminTopicMessageParserJson
 * JD-Core Version:    0.6.2
 */