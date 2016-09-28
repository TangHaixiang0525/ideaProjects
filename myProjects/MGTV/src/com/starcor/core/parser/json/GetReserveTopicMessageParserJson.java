package com.starcor.core.parser.json;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.MessageButtonBody;
import com.starcor.hunan.msgsys.data.reservetopic.ReserveTopicMessageData;
import com.starcor.hunan.msgsys.data.reservetopic.ReserveTopicMessageRawBody;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetReserveTopicMessageParserJson
{
  private static final String TAG = GetReserveTopicMessageParserJson.class.getSimpleName();

  public static ReserveTopicMessageData parser(String paramString)
  {
    Logger.i(TAG, "reserve topic parser()");
    if ((paramString == null) || (TextUtils.isEmpty(paramString)))
    {
      Logger.i(TAG, "parser() jsonString 为 null或者为空");
      return null;
    }
    Logger.i(TAG, "开始解析预约频道消息数据" + paramString);
    ReserveTopicMessageData localReserveTopicMessageData = new ReserveTopicMessageData();
    while (true)
    {
      ReserveTopicMessageRawBody localReserveTopicMessageRawBody;
      ArrayList localArrayList;
      int j;
      MessageButtonBody localMessageButtonBody;
      String str2;
      try
      {
        Logger.i(TAG, "jsonString:" + paramString);
        JSONObject localJSONObject1 = new JSONObject(paramString);
        localReserveTopicMessageData.setMessage_id(localJSONObject1.optString("message_id"));
        localReserveTopicMessageData.setPublish_time(localJSONObject1.optString("publish_time"));
        if (TextUtils.isEmpty(localReserveTopicMessageData.getMessage_id()))
        {
          Logger.i(TAG, "服务器发来的预约消息ID为空！为非法消息数据！");
          return null;
        }
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("message");
        if (localJSONObject2 == null)
          break label625;
        localReserveTopicMessageRawBody = new ReserveTopicMessageRawBody();
        localReserveTopicMessageRawBody.setAction(localJSONObject2.optString("action"));
        localReserveTopicMessageRawBody.setAction_args(localJSONObject2.optString("action_args"));
        localReserveTopicMessageRawBody.setTitle(localJSONObject2.optString("title"));
        localReserveTopicMessageRawBody.setBody(localJSONObject2.optString("body"));
        localReserveTopicMessageRawBody.setLaunch_image(localJSONObject2.optString("launch_image"));
        localReserveTopicMessageRawBody.setAlter(localJSONObject2.optInt("alter"));
        localReserveTopicMessageRawBody.setDialogway(localJSONObject2.optInt("dialogway"));
        localReserveTopicMessageRawBody.setDialogway_args(localJSONObject2.optString("dialogway_args"));
        localReserveTopicMessageRawBody.setExt(localJSONObject2.optString("ext"));
        localReserveTopicMessageRawBody.setMessage_type(localJSONObject2.optString("message_type"));
        JSONArray localJSONArray = localJSONObject2.optJSONArray("buttons");
        if (localJSONArray == null)
          break label619;
        int i = localJSONArray.length();
        Logger.i(TAG, "buttonArray size=" + i);
        if (i <= 0)
          break label619;
        localArrayList = new ArrayList();
        j = 0;
        if (j >= i)
          break label612;
        JSONObject localJSONObject3 = localJSONArray.optJSONObject(j);
        if (localJSONObject3 == null)
          break label655;
        localMessageButtonBody = new MessageButtonBody();
        String str1 = localJSONObject3.optString("actions");
        str2 = localJSONObject3.optString("args");
        localMessageButtonBody.setActions(str1);
        if ((!"m_open_popstar_list_page".equals(str1)) && (!"m_about_page".equals(str1)) && (!"m_open_user_personalized_recom".equals(str1)) && (!"m_open_login_page".equals(str1)) && (!"m_user_manager_page".equals(str1)) && (!"m_weixin_weibo_page".equals(str1)) && (!"m_fuwu_xieyi_page".equals(str1)) && (!"m_open_buy_vip_page".equals(str1)) && (!"m_open_exchange_card_page".equals(str1)) && (!"m_open_popstar_search_page".equals(str1)))
          break label602;
        if (TextUtils.isEmpty(str2))
        {
          localMessageButtonBody.setArgs("action=" + str1);
          localMessageButtonBody.setLabel(localJSONObject3.optString("label"));
          Logger.i(TAG, "msgBtnBody=" + localMessageButtonBody.toString());
          localArrayList.add(localMessageButtonBody);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return localReserveTopicMessageData;
      }
      localMessageButtonBody.setArgs(str2);
      continue;
      label602: localMessageButtonBody.setArgs(str2);
      continue;
      label612: localReserveTopicMessageRawBody.setMessageButtonBodies(localArrayList);
      label619: localReserveTopicMessageData.setMessage(localReserveTopicMessageRawBody);
      label625: Logger.i(TAG, "解析完毕后的reserveTopicMessageData为" + localReserveTopicMessageData.toString());
      return localReserveTopicMessageData;
      label655: j++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetReserveTopicMessageParserJson
 * JD-Core Version:    0.6.2
 */