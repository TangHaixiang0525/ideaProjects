package com.starcor.core.parser.json;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.MessageButtonBody;
import com.starcor.hunan.msgsys.data.publictopic.PublicTopicMessageData;
import com.starcor.hunan.msgsys.data.publictopic.PublicTopicMessageRawBody;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetPublicTopicMessageParserJson
{
  private static final String TAG = GetPublicTopicMessageParserJson.class.getSimpleName();

  public static PublicTopicMessageData parser(String paramString)
  {
    Logger.i(TAG, "public topic parser()");
    if ((paramString == null) || (TextUtils.isEmpty(paramString)))
    {
      Logger.i(TAG, "parser() jsonString 为 null或者为空");
      return null;
    }
    PublicTopicMessageData localPublicTopicMessageData = new PublicTopicMessageData();
    while (true)
    {
      PublicTopicMessageRawBody localPublicTopicMessageRawBody;
      ArrayList localArrayList;
      int j;
      MessageButtonBody localMessageButtonBody;
      String str2;
      try
      {
        Logger.i(TAG, "jsonString:" + paramString);
        JSONObject localJSONObject1 = new JSONObject(paramString);
        localPublicTopicMessageData.setMessage_id(localJSONObject1.optString("message_id"));
        localPublicTopicMessageData.setPublish_time(localJSONObject1.optString("publish_time"));
        if (TextUtils.isEmpty(localPublicTopicMessageData.getMessage_id()))
        {
          Logger.i(TAG, "服务器发来的公共消息ID为空！为非法消息数据！");
          return null;
        }
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("message");
        if (localJSONObject2 == null)
          break label600;
        localPublicTopicMessageRawBody = new PublicTopicMessageRawBody();
        localPublicTopicMessageRawBody.setAction(localJSONObject2.optString("action"));
        localPublicTopicMessageRawBody.setAction_args(localJSONObject2.optString("action_args"));
        localPublicTopicMessageRawBody.setTitle(localJSONObject2.optString("title"));
        localPublicTopicMessageRawBody.setBody(localJSONObject2.optString("body"));
        localPublicTopicMessageRawBody.setLaunch_image(localJSONObject2.optString("launch_image"));
        localPublicTopicMessageRawBody.setAlter(localJSONObject2.optInt("alter"));
        localPublicTopicMessageRawBody.setDialogway(localJSONObject2.optInt("dialogway"));
        localPublicTopicMessageRawBody.setDialogway_args(localJSONObject2.optString("dialogway_args"));
        localPublicTopicMessageRawBody.setExt(localJSONObject2.optString("ext"));
        localPublicTopicMessageRawBody.setMessage_type(localJSONObject2.optString("message_type"));
        JSONArray localJSONArray = localJSONObject2.optJSONArray("buttons");
        if (localJSONArray == null)
          break label594;
        int i = localJSONArray.length();
        Logger.i(TAG, "buttonArray size=" + i);
        if (i <= 0)
          break label594;
        localArrayList = new ArrayList();
        j = 0;
        if (j >= i)
          break label587;
        JSONObject localJSONObject3 = localJSONArray.optJSONObject(j);
        if (localJSONObject3 == null)
          break label630;
        localMessageButtonBody = new MessageButtonBody();
        String str1 = localJSONObject3.optString("actions");
        str2 = localJSONObject3.optString("args");
        localMessageButtonBody.setActions(str1);
        if ((!"m_open_popstar_list_page".equals(str1)) && (!"m_about_page".equals(str1)) && (!"m_open_user_personalized_recom".equals(str1)) && (!"m_open_login_page".equals(str1)) && (!"m_user_manager_page".equals(str1)) && (!"m_weixin_weibo_page".equals(str1)) && (!"m_fuwu_xieyi_page".equals(str1)) && (!"m_open_buy_vip_page".equals(str1)) && (!"m_open_exchange_card_page".equals(str1)) && (!"m_open_popstar_search_page".equals(str1)))
          break label577;
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
        return localPublicTopicMessageData;
      }
      localMessageButtonBody.setArgs(str2);
      continue;
      label577: localMessageButtonBody.setArgs(str2);
      continue;
      label587: localPublicTopicMessageRawBody.setMessageButtonBodies(localArrayList);
      label594: localPublicTopicMessageData.setMessage(localPublicTopicMessageRawBody);
      label600: Logger.i(TAG, "解析完毕后的publicTopicMessageData为" + localPublicTopicMessageData.toString());
      return localPublicTopicMessageData;
      label630: j++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetPublicTopicMessageParserJson
 * JD-Core Version:    0.6.2
 */