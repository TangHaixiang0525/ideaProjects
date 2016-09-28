package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.ActorStarInfoList;
import com.starcor.core.domain.ActorStarInfoList.ActorStarInfo;
import com.starcor.core.domain.SearchListItem;
import com.starcor.core.domain.SearchStruct;
import com.starcor.sccms.api.SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener;
import com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class StarDetailedActivityV2 extends BaseActivity
{
  private String actor;
  private String actorID;
  ByteArrayInputStream actorInfoStream = null;
  private String labelID;

  private void addItem(List<SearchListItem> paramList, StarType paramStarType)
  {
    if (paramList == null);
    XulArea localXulArea;
    do
    {
      return;
      StarType localStarType = StarType.WORKS;
      XulMassiveAreaWrapper localXulMassiveAreaWrapper = null;
      XulView localXulView = null;
      if (paramStarType == localStarType)
      {
        localXulView = xulFindViewById("actor_massive");
        localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulView);
      }
      localXulMassiveAreaWrapper.clear();
      for (int i = 0; i < paramList.size(); i++)
      {
        SearchListItem localSearchListItem = (SearchListItem)paramList.get(i);
        XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
        localXulDataNode.setAttribute("name", localSearchListItem.name);
        localXulDataNode.setAttribute("img_v", localSearchListItem.img_v);
        localXulDataNode.setAttribute("img_s", localSearchListItem.img_s);
        localXulDataNode.setAttribute("img_h", localSearchListItem.img_h);
        localXulMassiveAreaWrapper.addItem(localXulDataNode);
      }
      localXulMassiveAreaWrapper.syncContentView();
      localXulArea = localXulView.findParentById("title");
    }
    while (localXulArea == null);
    localXulArea.setAttr("text", "主演");
    localXulArea.resetRender();
  }

  private void getActionInfo()
  {
    ServerApiManager.i().APIGetActorStarInfoTask(this.actorID, "", new SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ActorStarInfoList paramAnonymousActorStarInfoList)
      {
        if ((paramAnonymousActorStarInfoList != null) && (paramAnonymousActorStarInfoList.lists != null) && (paramAnonymousActorStarInfoList.lists.size() > 0))
        {
          StarDetailedActivityV2.this.actorInfoStream = StarDetailedActivityV2.this.getStarInfo((ActorStarInfoList.ActorStarInfo)paramAnonymousActorStarInfoList.lists.get(0));
          StarDetailedActivityV2.this.xulRefreshBinding("actor_info", "file:///.app/api/get_actor_info");
        }
      }
    });
  }

  private void getFilmInfo()
  {
    ServerApiManager.i().APISearchStarListTask("", 0, 30, "", this.labelID, new SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SearchStruct paramAnonymousSearchStruct)
      {
        StarDetailedActivityV2.this.addItem(paramAnonymousSearchStruct.sItemList, StarDetailedActivityV2.StarType.WORKS);
      }
    });
  }

  private ByteArrayInputStream getStarInfo(ActorStarInfoList.ActorStarInfo paramActorStarInfo)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "info");
      writeNodeValue(localXmlSerializer, "image", paramActorStarInfo.img_v);
      writeNodeValue(localXmlSerializer, "nation", paramActorStarInfo.nation);
      writeNodeValue(localXmlSerializer, "astrology", paramActorStarInfo.constellation);
      writeNodeValue(localXmlSerializer, "blood_type", paramActorStarInfo.blood_type);
      writeNodeValue(localXmlSerializer, "height", paramActorStarInfo.height);
      writeNodeValue(localXmlSerializer, "weight", paramActorStarInfo.weight);
      writeNodeValue(localXmlSerializer, "home", paramActorStarInfo.area);
      writeNodeValue(localXmlSerializer, "birthday", paramActorStarInfo.birthday);
      writeNodeValue(localXmlSerializer, "works", paramActorStarInfo.works);
      localXmlSerializer.endTag(null, "info");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void initPageData()
  {
    this.actor = getIntent().getStringExtra("actor");
    this.actorID = getIntent().getStringExtra("actorID");
    this.labelID = getIntent().getStringExtra("labelID");
    if (TextUtils.isEmpty(this.actorID))
      return;
    getActionInfo();
    getFilmInfo();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("StarDetailedPageV2", true);
    initPageData();
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("api/get_actor_info".equals(paramString))
      return this.actorInfoStream;
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
  }

  private static enum StarType
  {
    static
    {
      GUEST = new StarType("GUEST", 1);
      StarType[] arrayOfStarType = new StarType[2];
      arrayOfStarType[0] = WORKS;
      arrayOfStarType[1] = GUEST;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.StarDetailedActivityV2
 * JD-Core Version:    0.6.2
 */