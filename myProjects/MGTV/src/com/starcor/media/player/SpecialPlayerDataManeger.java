package com.starcor.media.player;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.SpecialCategoryContent;
import com.starcor.core.domain.SpecialPlayerData;
import com.starcor.core.domain.SpecialPlayerData.UiStyle;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.SpecialActivityV2;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class SpecialPlayerDataManeger
{
  private static final String TAG = SpecialPlayerDataManeger.class.getSimpleName();
  private static SpecialPlayerDataManeger _instance = null;
  private List<SpecialPlayerData> specialPlayerDataList = new ArrayList();

  private List<SpecialCategoryContent> findCategoryContentList(SpecialCategoryContent paramSpecialCategoryContent, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramSpecialCategoryContent != null) && (this.specialPlayerDataList != null))
    {
      Object localObject = new ArrayList();
      Iterator localIterator1 = this.specialPlayerDataList.iterator();
      while (localIterator1.hasNext())
      {
        SpecialPlayerData localSpecialPlayerData = (SpecialPlayerData)localIterator1.next();
        if (localSpecialPlayerData.specialCategoryId.equals(paramSpecialCategoryContent.special_category_id))
          localObject = localSpecialPlayerData.info;
      }
      if (paramBoolean)
      {
        if (localObject != null)
        {
          Iterator localIterator2 = ((List)localObject).iterator();
          while (localIterator2.hasNext())
          {
            SpecialCategoryContent localSpecialCategoryContent = (SpecialCategoryContent)localIterator2.next();
            if (isPlayable(localSpecialCategoryContent))
              localArrayList.add(localSpecialCategoryContent);
          }
        }
      }
      else if (localObject != null)
        localArrayList.addAll((Collection)localObject);
    }
    return localArrayList;
  }

  public static SpecialPlayerDataManeger getInstance()
  {
    if (_instance == null)
      _instance = new SpecialPlayerDataManeger();
    return _instance;
  }

  private boolean isPlayable(SpecialCategoryContent paramSpecialCategoryContent)
  {
    if (paramSpecialCategoryContent != null)
    {
      Logger.d(TAG, "isPlayable action: " + paramSpecialCategoryContent.action);
      return "m_open_player".equals(paramSpecialCategoryContent.action);
    }
    return false;
  }

  public void addItem(boolean paramBoolean, List<SpecialCategoryContent> paramList, XulMassiveAreaWrapper paramXulMassiveAreaWrapper)
  {
    if (paramList == null)
      return;
    int i = 1;
    Iterator localIterator = paramList.iterator();
    label16: SpecialCategoryContent localSpecialCategoryContent;
    XulDataNode localXulDataNode;
    String str1;
    label117: String str2;
    label293: String str3;
    if (localIterator.hasNext())
    {
      localSpecialCategoryContent = (SpecialCategoryContent)localIterator.next();
      localXulDataNode = XulDataNode.obtainDataNode("item");
      str1 = localSpecialCategoryContent.video_name;
      if (("m_open_player".equals(localSpecialCategoryContent.action)) && (localSpecialCategoryContent.video_type == 0))
        str1 = localSpecialCategoryContent.watch_focus;
      if (!paramBoolean)
        break label447;
      localXulDataNode.setAttribute("name", i + "." + str1);
      localXulDataNode.setAttribute("video_id", localSpecialCategoryContent.video_id);
      localXulDataNode.setAttribute("video_index", localSpecialCategoryContent.video_index);
      localXulDataNode.setAttribute("video_type", localSpecialCategoryContent.video_type + "");
      localXulDataNode.setAttribute("video_name", localSpecialCategoryContent.video_name);
      localXulDataNode.setAttribute("packet_category_id", localSpecialCategoryContent.category_id);
      localXulDataNode.setAttribute("packet_id", localSpecialCategoryContent.package_id);
      localXulDataNode.setAttribute("special_id", localSpecialCategoryContent.special_id);
      localXulDataNode.setAttribute("category_id", localSpecialCategoryContent.category_id);
      localXulDataNode.setAttribute("img_corner", localSpecialCategoryContent.img_corner);
      localXulDataNode.setAttribute("watch_focus", localSpecialCategoryContent.watch_focus);
      localXulDataNode.setAttribute("special_category_id", localSpecialCategoryContent.special_category_id);
      if (!TextUtils.isEmpty(localSpecialCategoryContent.view_type))
        break label460;
      str2 = "0";
      localXulDataNode.setAttribute("view_type", str2);
      localXulDataNode.setAttribute("ui_style", localSpecialCategoryContent.ui_style);
      localXulDataNode.setAttribute("ex_url", localSpecialCategoryContent.ex_url);
      localXulDataNode.setAttribute("duration", localSpecialCategoryContent.duration);
      localXulDataNode.setAttribute("action", localSpecialCategoryContent.action);
      localXulDataNode.setAttribute("img_default", localSpecialCategoryContent.img_default);
      localXulDataNode.setAttribute("index", i - 1);
      if ((!"m_open_player".equals(localSpecialCategoryContent.action)) || (localSpecialCategoryContent.video_type != 0))
        break label477;
      if (!UserCPLLogic.getInstance().isSpecialVideoPlayed(localSpecialCategoryContent.video_id, localSpecialCategoryContent.video_index))
        break label470;
      str3 = "1";
      label424: localXulDataNode.setAttribute("isplayed", str3);
    }
    while (true)
    {
      paramXulMassiveAreaWrapper.addItem(localXulDataNode);
      i++;
      break label16;
      break;
      label447: localXulDataNode.setAttribute("name", str1);
      break label117;
      label460: str2 = localSpecialCategoryContent.view_type;
      break label293;
      label470: str3 = "0";
      break label424;
      label477: localXulDataNode.setAttribute("isplayed", "0");
    }
  }

  public String buildSpecialCategory(List<SpecialPlayerData> paramList)
  {
    while (true)
    {
      try
      {
        XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        localXmlSerializer.startTag(null, "ui_mode");
        if (((SpecialPlayerData)paramList.get(0)).uiStyle == SpecialPlayerData.UiStyle.DISPLAY_UI_IMAGE)
        {
          i = 1;
          String str1 = String.valueOf(i);
          localXmlSerializer.attribute("", "type", str1);
          localXmlSerializer.endTag(null, "ui_mode");
          writeNodeValue(localXmlSerializer, "ui_mode", str1);
          int j = paramList.size();
          int k = 0;
          if (k < j)
          {
            SpecialPlayerData localSpecialPlayerData = (SpecialPlayerData)paramList.get(k);
            localXmlSerializer.startTag(null, "item");
            writeNodeValue(localXmlSerializer, "category_id", localSpecialPlayerData.specialCategoryId);
            writeNodeValue(localXmlSerializer, "name", localSpecialPlayerData.specialName);
            writeNodeValue(localXmlSerializer, "idx", k + "");
            localXmlSerializer.endTag(null, "item");
            k++;
            continue;
          }
          localXmlSerializer.endTag(null, "data");
          localXmlSerializer.endDocument();
          localXmlSerializer.flush();
          Logger.i(TAG, "buildSpecialCategory list " + localStringWriter.toString());
          String str2 = localStringWriter.toString();
          return str2;
        }
      }
      catch (Exception localException)
      {
        Logger.e(TAG, "buildSpecialCategory list error!");
        return "";
      }
      int i = 0;
    }
  }

  public SpecialCategoryContent buildSpecialContent(SpecialTopicPkgCntLstStruct paramSpecialTopicPkgCntLstStruct)
  {
    SpecialCategoryContent localSpecialCategoryContent = new SpecialCategoryContent();
    localSpecialCategoryContent.video_id = paramSpecialTopicPkgCntLstStruct.video_id;
    localSpecialCategoryContent.video_index = XulUtils.tryParseInt(paramSpecialTopicPkgCntLstStruct.video_index);
    localSpecialCategoryContent.video_name = paramSpecialTopicPkgCntLstStruct.name;
    localSpecialCategoryContent.video_id = paramSpecialTopicPkgCntLstStruct.video_id;
    localSpecialCategoryContent.video_type = XulUtils.tryParseInt(paramSpecialTopicPkgCntLstStruct.video_type, 0);
    localSpecialCategoryContent.package_id = paramSpecialTopicPkgCntLstStruct.packet_id;
    localSpecialCategoryContent.category_id = paramSpecialTopicPkgCntLstStruct.packet_category_id;
    localSpecialCategoryContent.img_corner = paramSpecialTopicPkgCntLstStruct.img_corner;
    localSpecialCategoryContent.watch_focus = paramSpecialTopicPkgCntLstStruct.watch_focus;
    String str1;
    String str2;
    if (TextUtils.isEmpty(paramSpecialTopicPkgCntLstStruct.view_type))
    {
      str1 = "0";
      localSpecialCategoryContent.view_type = str1;
      localSpecialCategoryContent.ui_style = (paramSpecialTopicPkgCntLstStruct.ui_style + "");
      if (!TextUtils.isEmpty(paramSpecialTopicPkgCntLstStruct.imgh))
        break label261;
      if (!TextUtils.isEmpty(paramSpecialTopicPkgCntLstStruct.imgv))
        break label252;
      str2 = paramSpecialTopicPkgCntLstStruct.imgv;
      label157: localSpecialCategoryContent.img_default = str2;
      if (!"video".equals(paramSpecialTopicPkgCntLstStruct.type))
        break label312;
      if ((!TextUtils.isEmpty(paramSpecialTopicPkgCntLstStruct.video_type)) && (!"1".equals(paramSpecialTopicPkgCntLstStruct.video_type)))
        break label270;
      localSpecialCategoryContent.action = "m_open_tv_player";
    }
    while (true)
    {
      if ((!TextUtils.isEmpty(paramSpecialTopicPkgCntLstStruct.time_len)) && (!paramSpecialTopicPkgCntLstStruct.time_len.equals("null")))
        localSpecialCategoryContent.duration = getInstance().getTime(paramSpecialTopicPkgCntLstStruct.time_len);
      return localSpecialCategoryContent;
      str1 = paramSpecialTopicPkgCntLstStruct.view_type;
      break;
      label252: str2 = paramSpecialTopicPkgCntLstStruct.imgs;
      break label157;
      label261: str2 = paramSpecialTopicPkgCntLstStruct.imgh;
      break label157;
      label270: if ((TextUtils.isEmpty(paramSpecialTopicPkgCntLstStruct.video_index)) || ("-1".equals(paramSpecialTopicPkgCntLstStruct.video_index)))
      {
        localSpecialCategoryContent.action = "m_open_detail_page";
      }
      else
      {
        localSpecialCategoryContent.action = "m_open_player";
        continue;
        label312: if ("web".equals(paramSpecialTopicPkgCntLstStruct.type))
        {
          localSpecialCategoryContent.action = "m_open_web";
          localSpecialCategoryContent.ex_url = paramSpecialTopicPkgCntLstStruct.ex_url;
        }
        else
        {
          if ("preview".equals(paramSpecialTopicPkgCntLstStruct.type))
          {
            localSpecialCategoryContent.action = "m_open_player";
            if (localSpecialCategoryContent.video_index < 0);
            for (int i = 0; ; i = localSpecialCategoryContent.video_index)
            {
              localSpecialCategoryContent.video_index = i;
              break;
            }
          }
          if ("special".equals(paramSpecialTopicPkgCntLstStruct.type))
            localSpecialCategoryContent.action = "m_open_special_page";
        }
      }
    }
  }

  public void clearSpecialPlayerDataList()
  {
    if (this.specialPlayerDataList != null)
      this.specialPlayerDataList = null;
  }

  public SpecialCategoryContent getContentItem(List<SpecialPlayerData> paramList, String paramString1, int paramInt, String paramString2)
  {
    if ((paramList != null) && (paramList.size() > 0))
    {
      Iterator localIterator1 = paramList.iterator();
      SpecialCategoryContent localSpecialCategoryContent;
      do
      {
        Iterator localIterator2;
        while (!localIterator2.hasNext())
        {
          SpecialPlayerData localSpecialPlayerData;
          do
          {
            if (!localIterator1.hasNext())
              break;
            localSpecialPlayerData = (SpecialPlayerData)localIterator1.next();
          }
          while (!localSpecialPlayerData.specialCategoryId.equals(paramString2));
          localIterator2 = localSpecialPlayerData.info.iterator();
        }
        localSpecialCategoryContent = (SpecialCategoryContent)localIterator2.next();
      }
      while ((!localSpecialCategoryContent.video_id.equals(paramString1)) || (localSpecialCategoryContent.video_index != paramInt));
      return localSpecialCategoryContent;
    }
    return null;
  }

  public SpecialCategoryContent getFirstPlay(List<SpecialCategoryContent> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return null;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      SpecialCategoryContent localSpecialCategoryContent = (SpecialCategoryContent)localIterator.next();
      if ((localSpecialCategoryContent.action == "m_open_player") && (localSpecialCategoryContent.video_type == 0))
        return localSpecialCategoryContent;
    }
    return null;
  }

  public SpecialCategoryContent getNextPlay(SpecialCategoryContent paramSpecialCategoryContent, String paramString)
  {
    if (paramSpecialCategoryContent == null);
    label228: label230: 
    do
    {
      List localList;
      int i;
      do
      {
        return null;
        localList = findCategoryContentList(paramSpecialCategoryContent, true);
        i = localList.size();
        Logger.d(TAG, "getNextPlay, targetList size: " + i);
      }
      while (i == 0);
      int j = -1;
      for (int k = 0; ; k++)
        if (k < i)
        {
          SpecialCategoryContent localSpecialCategoryContent = (SpecialCategoryContent)localList.get(k);
          if ((localSpecialCategoryContent != null) && (localSpecialCategoryContent.video_id.equals(paramSpecialCategoryContent.video_id)) && (localSpecialCategoryContent.video_index == paramSpecialCategoryContent.video_index))
            j = k;
        }
        else
        {
          Logger.d(TAG, "getNextPlay curIndex: " + j);
          if (!"list_cycle".equals(paramString))
            break label230;
          Logger.d(TAG, "getNextPlay MODE_LIST_CYCLE");
          if (j < 0)
            break label228;
          if (j + 1 >= localList.size())
            break;
          Logger.d(TAG, "getNextPlay curIndex + 1");
          return (SpecialCategoryContent)localList.get(j + 1);
        }
      Logger.d(TAG, "getNextPlay 0");
      return (SpecialCategoryContent)localList.get(0);
      return paramSpecialCategoryContent;
      if ("random".equals(paramString))
      {
        Logger.d(TAG, "getNextPlay MODE_RANDOM");
        if (i > 1)
        {
          int m = (int)(100.0D * Math.random() % i);
          if (m == j)
            m = (m + 3) % i;
          Logger.d(TAG, "getNextPlay randomIndex: " + m + ", curIndex: " + j);
          return (SpecialCategoryContent)localList.get(m);
        }
        return paramSpecialCategoryContent;
      }
    }
    while (!"single_cycle".equals(paramString));
    Logger.d(TAG, "getNextPlay MODE_SINGLE_CYCLE");
    return paramSpecialCategoryContent;
  }

  public String getTime(String paramString)
  {
    int i = Integer.parseInt(paramString);
    if (i <= 0)
      return " ";
    int j = i / 3600;
    int k = (i - j * 3600) / 60;
    int m = i % 60;
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1;
    String str3;
    label134: StringBuilder localStringBuilder3;
    if (j < 10)
    {
      str1 = "0" + j;
      String str2 = str1 + ":";
      StringBuilder localStringBuilder2 = new StringBuilder().append(str2);
      if (k >= 10)
        break label215;
      str3 = "0" + k;
      String str4 = str3 + ":";
      localStringBuilder3 = new StringBuilder().append(str4);
      if (m >= 10)
        break label225;
    }
    label215: label225: for (String str5 = "0" + m; ; str5 = String.valueOf(m))
    {
      return str5;
      str1 = String.valueOf(j);
      break;
      str3 = String.valueOf(k);
      break label134;
    }
  }

  public void goToDetail(Context paramContext, XulView paramXulView)
  {
    MovieData localMovieData = new MovieData();
    localMovieData.videoId = paramXulView.getDataString("video_id");
    localMovieData.videoType = XulUtils.tryParseInt(paramXulView.getDataString("video_type"));
    localMovieData.viewType = XulUtils.tryParseInt(paramXulView.getDataString("view_type"));
    localMovieData.name = paramXulView.getDataString("video_name");
    localMovieData.indexUIStyle = paramXulView.getDataString("ui_style");
    localMovieData.categoryId = paramXulView.getDataString("category_id");
    localMovieData.packageId = paramXulView.getDataString("packet_category_id");
    Intent localIntent = new Intent(paramContext, DetailPageActivity.class);
    localIntent.putExtra("xulPageId", "DetailPage");
    localIntent.putExtra("xulData", "");
    localIntent.putExtra("movieData", localMovieData);
    localIntent.addFlags(8388608);
    paramContext.startActivity(localIntent);
  }

  public void goToLivePlayer(Context paramContext, XulView paramXulView)
  {
    String str = paramXulView.getDataString("video_id");
    int i = XulUtils.tryParseInt(paramXulView.getDataString("video_type"));
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.videoId = str;
    localPlayerIntentParams.nns_videoInfo.videoType = i;
    localPlayerIntentParams.nns_videoInfo.categoryId = paramXulView.getDataString("category_id");
    localPlayerIntentParams.nns_videoInfo.packageId = paramXulView.getDataString("packet_category_id");
    localPlayerIntentParams.nns_videoInfo.name = paramXulView.getDataString("video_name");
    localPlayerIntentParams.mode = 3;
    Intent localIntent = new Intent().setClass(paramContext, ActivityAdapter.getInstance().getMPlayer());
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    localIntent.addFlags(8388608);
    paramContext.startActivity(localIntent);
  }

  public void goToSpecial(Context paramContext, XulView paramXulView)
  {
    MetadataInfo localMetadataInfo = new MetadataInfo();
    localMetadataInfo.category_id = paramXulView.getDataString("packet_category_id");
    localMetadataInfo.packet_id = paramXulView.getDataString("packet_id");
    localMetadataInfo.uiStyle = XulUtils.tryParseInt(paramXulView.getDataString("ui_style"));
    localMetadataInfo.name = paramXulView.getDataString("video_name");
    Logger.i(TAG, "goToSpecial info=" + localMetadataInfo.toString());
    Intent localIntent = new Intent(paramContext, SpecialActivityV2.class);
    localIntent.putExtra("cmd_is_from_out", "false");
    localIntent.putExtra("MetaDataInfo", localMetadataInfo);
    localIntent.addFlags(8388608);
    paramContext.startActivity(localIntent);
  }

  public void goToWeb(Context paramContext, XulView paramXulView)
  {
    MetadataInfo localMetadataInfo = new MetadataInfo();
    localMetadataInfo.url = paramXulView.getDataString("ex_url");
    localMetadataInfo.name = paramXulView.getDataString("video_name");
    localMetadataInfo.action_type = "web";
    Logger.i(TAG, "goToWeb info=" + localMetadataInfo.toString());
    Intent localIntent = new Intent(paramContext, ActivityAdapter.getInstance().getWebActivity());
    localIntent.putExtra("cmd_is_from_out", "false");
    localIntent.putExtra("MetaDataInfo", localMetadataInfo);
    localIntent.addFlags(8388608);
    paramContext.startActivity(localIntent);
  }

  public boolean isChecked(XulDataNode paramXulDataNode, SpecialCategoryContent paramSpecialCategoryContent)
  {
    if (paramXulDataNode == null);
    int i;
    int j;
    String str1;
    String str2;
    do
    {
      return false;
      i = XulUtils.tryParseInt(paramXulDataNode.getAttributeValue("video_index"));
      j = XulUtils.tryParseInt(paramXulDataNode.getAttributeValue("index"));
      str1 = paramXulDataNode.getAttributeValue("video_id");
      str2 = paramXulDataNode.getAttributeValue("special_category_id");
    }
    while ((paramSpecialCategoryContent == null) || (i != paramSpecialCategoryContent.video_index) || (!str1.equals(paramSpecialCategoryContent.video_id)) || (!str2.equals(paramSpecialCategoryContent.special_category_id)) || (j != paramSpecialCategoryContent.index));
    return true;
  }

  public boolean isPlayed(XulView paramXulView)
  {
    int i = 1;
    if (paramXulView == null);
    XulDataNode localXulDataNode;
    do
    {
      return false;
      localXulDataNode = (XulDataNode)paramXulView.getBindingData().get(0);
    }
    while (localXulDataNode == null);
    if (XulUtils.tryParseInt(localXulDataNode.getAttributeValue("isplayed")) == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public void resetMassiveList(XulMassiveAreaWrapper paramXulMassiveAreaWrapper)
  {
    XulArea localXulArea = paramXulMassiveAreaWrapper.getAsArea().findParentByType("slider");
    XulSliderAreaWrapper.fromXulView(localXulArea).scrollTo(0, false);
    localXulArea.setDynamicFocus(null);
    paramXulMassiveAreaWrapper.clear();
  }

  public void setPlayed(XulView paramXulView)
  {
    XulDataNode localXulDataNode = (XulDataNode)paramXulView.getBindingData().get(0);
    if (localXulDataNode == null)
      return;
    localXulDataNode.setAttribute("isplayed", "1");
  }

  public void setSpecialPlayerDataList(List<SpecialPlayerData> paramList)
  {
    this.specialPlayerDataList = paramList;
  }

  protected void writeNodeValue(XmlSerializer paramXmlSerializer, String paramString1, String paramString2)
    throws IOException
  {
    if (paramString2 == null)
      paramString2 = "";
    paramXmlSerializer.startTag(null, paramString1);
    paramXmlSerializer.text(paramString2);
    paramXmlSerializer.endTag(null, paramString1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.SpecialPlayerDataManeger
 * JD-Core Version:    0.6.2
 */