package com.starcor.hunan.msgsys.test;

import android.content.Context;
import android.content.Intent;
import com.starcor.config.MgtvVersion;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.utils.SharedPreferencesUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PublishingMessagesTest extends AbstractTest
{
  private static List<String> mTopics = new ArrayList();
  private String daddyHappyBirthDayContent = "夏洛特是一家便民排忧急救社的社长,她每天都有忙不完的工作。事业为上的她却冷落了家里的丈夫跟孩子，长期以来，她的丈夫都必须做家务还要照料三个孩子，这样的日子持续了两年。面对事业跟家庭，夏洛特该何去何从呢？";
  private String daddyHappyBirthDayTitle = "人生哲理电影《爸爸生日快乐》";
  private String detailContent = "唐朝晚期，宪宗驾崩，郭贵妃得大宦官马元贽帮助，拥立其子李宥为帝，刘三好父亲因支持宪宗立二子李恽为帝，被穆宗秋后算帐，充军塞外。其妻江采琼与七岁女儿三好和家婢姚金铃被没入宫中为奴。江氏历代做首饰闻名，采琼幼受庭训，练得一双巧手，被编入的司珍房……";
  private String detailTitle = "最新热播电视剧《宫心计》";
  private String interval = "5";
  private final String mAdminAction1 = "{\"mpns_admin_action\": \"update-action-levels\",\"mpns_admin_message\": {\"message\": 1,\"detail\": 1,\"page\": 1,\"video\": 1,\"topic\": 1}}";
  private final String mAdminAction2 = "{\"mpns_admin_action\": \"clear-messages\",\"mpns_admin_message\": {\"message_ids\": [\"2014033100010111111111123\", \"2014033100010111111111111\"]}}";
  private final String mAdminAction3 = "{\"mpns_admin_action\": \"disconnect\",\"mpns_admin_message\": {\"when\": \"1\",\"next_connect_time\": " + this.interval + "}}";
  private final String mAdminAction4 = "{\"mpns_admin_action\": \"disconnect\",\"mpns_admin_message\": {\"when\": \"2\",\"next_connect_time\": " + this.interval + "}}";
  private final String mAdminAction5 = "{\"mpns_admin_action\": \"clear-messages\",\"mpns_admin_message\": {\"message_ids\": [\"2014033100010111111111112\", \"df4b18c01518489883fb89cb0d653653\"]}}";
  private String pageContent = "尊敬的用户，芒果今日的最新影视信息已经更新完毕，请您查看！";
  private String pageTitle = "芒果影视信息最新更新";
  private final String privateData1 = "{\"publish_time\": \"2013-12-12 11:11:40\",\"message\":{\"action\":\"detail\",\"action_args\":\"assets_category=电视剧&asset_id=7380\",\"title\":" + this.detailTitle + "," + "\"body\":" + this.detailContent + "," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":0}," + "\"message_id\":\"df4b18c01518489883fb89cb0d653653\"}";
  private final String privateData2 = "{\"publish_time\": \"2013-12-12 11:12:13\",\"message\":{\"action\":\"message\",\"action_args\":\"text args\",\"title\":" + this.textTitle + "," + "\"body\":" + this.textContent + "," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":1}," + "\"message_id\":\"2014033100010111111111115\"}";
  private final String privateData3 = "{\"publish_time\": \"2013-12-12 11:11:13\",\"message\":{\"action\":\"topic\",\"action_args\":\"packet_id=537d67ae199e91d25c7049250db469df\",\"title\":" + this.specialTitle2 + "," + "\"body\":" + this.specialContent2 + "," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":0}," + "\"message_id\":\"2014033100010111111111145\"}";
  private final String publicData1 = "{\"publish_time\": \"2013-12-12 11:00:00\",\"message\":{\"action\":\"page\",\"action_args\":\"http://zt.hifuntv.com/\",\"title\":" + this.pageTitle + "," + "\"body\":" + this.pageContent + "," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":0}," + "\"message_id\":\"2014033100010111111111110\"}";
  private final String publicData2 = "{\"publish_time\": \"2013-12-12 11:11:10\",\"message\":{\"action\":\"video\",\"action_args\":\"asset_id=7562&clip_id=284180&file_id=295614\",\"title\":\"" + this.daddyHappyBirthDayTitle + "\"," + "\"body\":\"" + this.daddyHappyBirthDayContent + "\"," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":0}," + "\"message_id\":\"2014033100010111111111111\"}";
  private final String publicData3 = "{\"publish_time\": \"2013-12-12 11:11:13\",\"message\":{\"action\":\"message\",\"action_args\":\"text args\",\"title\":" + this.textTitle + "," + "\"body\":" + this.textContent + "," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":1}," + "\"message_id\":\"2014033100010111111111112\"}";
  private final String publicData4 = "{\"publish_time\": \"2013-12-12 11:11:13\",\"message\":{\"action\":\"topic\",\"action_args\":\"packet_id=531586182046ccb7bb8127035f5413e4\",\"title\":" + this.specialTitle1 + "," + "\"body\":" + this.specialContent1 + "," + "\"launch_image\":\"http:///image_url\"," + "\"alter\":0}," + "\"message_id\":\"2014033100010111111111123\"}";
  private final String publicData5 = "{\"publish_time\": \"2013-12-12 11:11:10\",\"message\":{\"action\":\"video\",\"action_args\":\"asset_id=7590&clip_id=284170&file_id=235614\",\"title\":\"芒果视频消息\",\"body\":\"芒果视频消息\",\"launch_image\":\"http:///image_url\",\"alter\":0},\"message_id\":\"2014033100010111111134567\"}";
  private final String publicData6 = "{\"publish_time\": \"2013-12-12 11:11:40\",\"message\":{\"action\":\"detail\",\"action_args\":\"assets_category=电视剧&asset_id=4080\",\"title\":芒果详情消息,\"body\":芒果详情消息,\"launch_image\":\"http:///image_url\",\"alter\":1},\"message_id\":\"df4b18c01518489883fb89cb0d653555\"}";
  private String specialContent1 = "贝瓦儿歌";
  private String specialContent2 = "三八妇女节专题";
  private String specialTitle1 = "专题-贝瓦儿歌";
  private String specialTitle2 = "三八妇女节专题1";
  private String textContent = " 北京视达科科技有限公司成立于2009年，是一家专注于数字新媒体领域的创新型国家级高科技企业，并通过ISO9001国际质量体系认证。专业的新媒体互联网技术平台提供商、终端设备生产商、互联网电视增值业务运营商。公司注册资本1000万元人民币。公司总部位于北京中关村软件园，在深圳、成都均设有分支机构，成都作为公司研发基地，已经和中科院计算所上海分所合作成立新媒体技术研发中心，主导研发新媒体技术平台及相关产品，服务广电、电视台、教育、酒店、医院等行业。";
  private String textTitle = "starcor简介";

  public PublishingMessagesTest(Context paramContext)
  {
    super(paramContext, PublishingMessagesTest.class.getSimpleName());
  }

  private void publishAdminTopic()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.starcor.hunan.publish_topic_msg");
    localIntent.putExtra("com.starcor.hunan.NEW_MSG_TOPIC", (String)mTopics.get(1));
    switch (new Random().nextInt(8))
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    }
    while (true)
    {
      getContext().sendBroadcast(localIntent);
      return;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", "{\"mpns_admin_action\": \"update-action-levels\",\"mpns_admin_message\": {\"message\": 1,\"detail\": 1,\"page\": 1,\"video\": 1,\"topic\": 1}}");
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", "{\"mpns_admin_action\": \"clear-messages\",\"mpns_admin_message\": {\"message_ids\": [\"2014033100010111111111123\", \"2014033100010111111111111\"]}}");
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.mAdminAction3);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.mAdminAction4);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.mAdminAction3);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.mAdminAction4);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", "{\"mpns_admin_action\": \"clear-messages\",\"mpns_admin_message\": {\"message_ids\": [\"2014033100010111111111112\", \"df4b18c01518489883fb89cb0d653653\"]}}");
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", "{\"mpns_admin_action\": \"clear-messages\",\"mpns_admin_message\": {\"message_ids\": [\"2014033100010111111111123\", \"2014033100010111111111111\"]}}");
    }
  }

  private void publishPrivateTopicData()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.starcor.hunan.publish_topic_msg");
    localIntent.putExtra("com.starcor.hunan.NEW_MSG_TOPIC", (String)mTopics.get(2));
    switch (new Random().nextInt(3))
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      getContext().sendBroadcast(localIntent);
      return;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.privateData1);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.privateData2);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.privateData3);
    }
  }

  private void publishPublicTopicData()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.starcor.hunan.publish_topic_msg");
    localIntent.putExtra("com.starcor.hunan.NEW_MSG_TOPIC", (String)mTopics.get(0));
    switch (new Random().nextInt(6))
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      Logger.i(this.SUB_TAG, "intent = " + localIntent.toString());
      getContext().sendBroadcast(localIntent);
      return;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.publicData1);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.publicData2);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.publicData3);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", this.publicData4);
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", "{\"publish_time\": \"2013-12-12 11:11:10\",\"message\":{\"action\":\"video\",\"action_args\":\"asset_id=7590&clip_id=284170&file_id=235614\",\"title\":\"芒果视频消息\",\"body\":\"芒果视频消息\",\"launch_image\":\"http:///image_url\",\"alter\":0},\"message_id\":\"2014033100010111111134567\"}");
      continue;
      localIntent.putExtra("com.starcor.hunan.NEW_MSG_BODY", "{\"publish_time\": \"2013-12-12 11:11:40\",\"message\":{\"action\":\"detail\",\"action_args\":\"assets_category=电视剧&asset_id=4080\",\"title\":芒果详情消息,\"body\":芒果详情消息,\"launch_image\":\"http:///image_url\",\"alter\":1},\"message_id\":\"df4b18c01518489883fb89cb0d653555\"}");
    }
  }

  private void publishTopicData()
  {
    Logger.i(this.SUB_TAG, "发布主题信息！");
    switch (new Random().nextInt(3))
    {
    default:
      return;
    case 0:
      Logger.i(this.SUB_TAG, "发布公共主题信息！");
      publishPublicTopicData();
      return;
    case 1:
      Logger.i(this.SUB_TAG, "发布私有主题信息！");
      publishPrivateTopicData();
      return;
    case 2:
    }
    Logger.i(this.SUB_TAG, "发布管理主题信息！");
    publishAdminTopic();
  }

  protected void init()
  {
    if (mTopics != null)
      mTopics.clear();
    String str = SharedPreferencesUtil.getString(getContext(), "mgtv_client_sp", "client_id");
    mTopics.add("/public/" + MgtvVersion.getVersion());
    mTopics.add("/protected/admin");
    mTopics.add("/private/" + str);
    Logger.i(this.SUB_TAG, "初始化主题数据结果：public=" + (String)mTopics.get(0) + "\nadmin=" + (String)mTopics.get(1) + "\nprivate=" + (String)mTopics.get(2));
  }

  protected void runSpecificTest()
  {
    publishTopicData();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.test.PublishingMessagesTest
 * JD-Core Version:    0.6.2
 */