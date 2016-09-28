package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulPageSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class RankListActivity extends XULActivity
{
  private static final String TAG = RankListActivity.class.getSimpleName();
  private int categoryItemSize;
  private FloatingDetailPageDialog floatingDetailPageDialog;
  private XulArrayList<XulView> indicates;
  private XulArrayList<XulView> parallelogramGroup;
  private XulView parallelogramSlider;
  private XulPendingInputStream rankListStructureStream = new XulPendingInputStream();
  private List<RankListStructure> rankListStructures = new ArrayList();
  private int targetIndex;
  private int task;

  private InputStream bindData()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "data");
      for (int i = 0; i < this.rankListStructures.size(); i++)
      {
        localXmlSerializer.startTag(null, "group");
        RankListStructure localRankListStructure = (RankListStructure)this.rankListStructures.get(i);
        writeNodeValue(localXmlSerializer, "title", localRankListStructure.name);
        for (int j = 0; j < localRankListStructure.items.size(); j++)
        {
          GetPlaybillSelectedListInfo.Item localItem = (GetPlaybillSelectedListInfo.Item)localRankListStructure.items.get(j);
          localXmlSerializer.startTag(null, "item");
          writeNodeValue(localXmlSerializer, "url", "effect:parallelogram:" + localItem.img_v);
          writeNodeValue(localXmlSerializer, "name", localItem.name);
          writeNodeValue(localXmlSerializer, "id", String.valueOf(j));
          writeNodeValue(localXmlSerializer, "video_id", localItem.id);
          writeNodeValue(localXmlSerializer, "video_type", localItem.video_type);
          writeNodeValue(localXmlSerializer, "list_title", localRankListStructure.name);
          writeNodeValue(localXmlSerializer, "introduce", String.valueOf(localItem.info));
          writeNodeValue(localXmlSerializer, "actor", String.valueOf(localItem.video_actor));
          writeNodeValue(localXmlSerializer, "score", String.valueOf(localItem.user_score));
          localXmlSerializer.endTag(null, "item");
        }
        localXmlSerializer.endTag(null, "group");
      }
      localXmlSerializer.endTag(null, "data");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Log.d(TAG, "data:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void checkFinish()
  {
    this.task = (1 + this.task);
    if (this.task >= this.categoryItemSize)
    {
      if (this.rankListStructures.size() == 0)
      {
        this.rankListStructureStream.cancel();
        dismissLoaddingDialog();
      }
    }
    else
      return;
    Collections.sort(this.rankListStructures, new Comparator()
    {
      public int compare(RankListActivity.RankListStructure paramAnonymousRankListStructure1, RankListActivity.RankListStructure paramAnonymousRankListStructure2)
      {
        if (paramAnonymousRankListStructure1.index < paramAnonymousRankListStructure2.index)
          return -1;
        if (paramAnonymousRankListStructure1.index > paramAnonymousRankListStructure2.index)
          return 1;
        return 0;
      }
    });
    this.rankListStructureStream.setBaseStream(bindData());
    xulPostDelay(new Runnable()
    {
      public void run()
      {
        RankListActivity.this.enablePreloadPosters();
      }
    }
    , 2000L);
    dismissLoaddingDialog();
  }

  private void enablePreloadPosters()
  {
    Logger.i(TAG, "enablePreloadPosters");
    if (this.parallelogramSlider == null)
      this.parallelogramSlider = xulFindViewById("parallelogram_slider");
    if (this.parallelogramSlider != null)
    {
      this.parallelogramSlider.setAttr("preload-page", "enabled");
      this.parallelogramSlider.resetRender();
    }
  }

  private int findFocusView()
  {
    if (this.parallelogramGroup == null)
      this.parallelogramGroup = xulFindViewsByClass("parallelogram_group");
    int j;
    if (this.parallelogramGroup != null)
    {
      j = 0;
      if (j >= this.parallelogramGroup.size());
    }
    while (true)
    {
      try
      {
        XulView localXulView = (XulView)this.parallelogramGroup.get(j);
        int i;
        if ((localXulView.hasFocus()) && ((localXulView instanceof XulArea)))
        {
          XulArrayList localXulArrayList = XulPage.findItemsByClass((XulArea)localXulView, "poster_area");
          if (localXulArrayList != null)
            break label136;
          return -1;
          if (i < localXulArrayList.size())
          {
            boolean bool = ((XulView)localXulArrayList.get(i)).hasFocus();
            if (bool)
              break label134;
            i++;
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        j++;
      }
      break;
      i = -1;
      label134: return i;
      label136: i = 0;
    }
  }

  private void flipPages(int paramInt1, int paramInt2)
  {
    if (this.parallelogramSlider == null)
      this.parallelogramSlider = xulFindViewById("parallelogram_slider");
    if (this.parallelogramSlider == null);
    XulArrayList localXulArrayList;
    label146: 
    while (true)
    {
      return;
      XulPageSliderAreaWrapper localXulPageSliderAreaWrapper = XulPageSliderAreaWrapper.fromXulView(this.parallelogramSlider);
      if (paramInt1 == 19)
        localXulPageSliderAreaWrapper.slidePrev();
      while (true)
      {
        if ((!this.parallelogramSlider.hasFocus()) || (this.parallelogramGroup == null))
          break label146;
        int i = localXulPageSliderAreaWrapper.getCurrentPage();
        XulView localXulView = (XulView)this.parallelogramGroup.get(i);
        if (localXulView == null)
          break;
        localXulArrayList = XulPage.findItemsByClass((XulArea)localXulView, "poster_area");
        if (localXulArrayList == null)
          break;
        if (-1 + localXulArrayList.size() >= paramInt2)
          break label148;
        xulRequestFocus((XulView)localXulArrayList.get(-1 + localXulArrayList.size()));
        return;
        if (paramInt1 == 20)
          localXulPageSliderAreaWrapper.slideNext();
      }
    }
    label148: xulRequestFocus((XulView)localXulArrayList.get(paramInt2));
  }

  private void getCategoryInfo(String paramString1, String paramString2, final String paramString3, final int paramInt)
  {
    ServerApiManager.i().APIGetPlaybillSelectedList(paramString1, paramString2, 15, 0, GlobalLogic.getInstance().getAreaCode(), GlobalLogic.getInstance().getUserAttr(), "0", new SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i(RankListActivity.TAG, "SccmsApiGetPlaybillSelectedList error");
        RankListActivity.this.checkFinish();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        if (paramAnonymousGetPlaybillSelectedListInfo.items.size() > 0)
        {
          RankListActivity.RankListStructure localRankListStructure = new RankListActivity.RankListStructure(RankListActivity.this, null);
          localRankListStructure.index = paramInt;
          localRankListStructure.name = paramString3;
          localRankListStructure.items.addAll(paramAnonymousGetPlaybillSelectedListInfo.items);
          RankListActivity.this.rankListStructures.add(localRankListStructure);
        }
        RankListActivity.this.checkFinish();
      }
    });
  }

  private void openDetailPage(String paramString, int paramInt)
  {
    this.floatingDetailPageDialog = new FloatingDetailPageDialog(paramString, paramInt, this);
  }

  private void setIndicate2Recommend(int paramInt)
  {
    if (this.indicates == null)
      this.indicates = xulFindViewsByClass("indicate");
    int i = 0;
    if (i < this.indicates.size())
    {
      XulView localXulView = (XulView)this.indicates.get(i);
      if (i == paramInt)
      {
        localXulView.removeClass("common_mode");
        localXulView.addClass("recommend_mode");
      }
      while (true)
      {
        localXulView.resetRender();
        i++;
        break;
        localXulView.removeClass("recommend_mode");
        localXulView.addClass("common_mode");
      }
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (!xulIsReady())
      return false;
    if (paramKeyEvent.getAction() == 0)
    {
      if (paramKeyEvent.getKeyCode() == 19)
      {
        this.targetIndex = (-1 + this.targetIndex);
        if (this.targetIndex < 0)
          this.targetIndex = (-1 + this.categoryItemSize);
        setIndicate2Recommend(this.targetIndex);
        flipPages(paramKeyEvent.getKeyCode(), findFocusView());
        return true;
      }
      if (paramKeyEvent.getKeyCode() == 20)
      {
        this.targetIndex = (1 + this.targetIndex);
        if (this.targetIndex == this.categoryItemSize)
          this.targetIndex = 0;
        setIndicate2Recommend(this.targetIndex);
        flipPages(paramKeyEvent.getKeyCode(), findFocusView());
        return true;
      }
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showLoaddingDialog();
    ServerApiManager.i().APIGetMediaAssetsInfo(getIntent().getStringExtra("mediaAssetId"), new SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i(RankListActivity.TAG, "APIGetMediaAssetsInfo onError");
        RankListActivity.this.dismissLoaddingDialog();
        RankListActivity.this.rankListStructureStream.cancel();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, MediaAssetsInfo paramAnonymousMediaAssetsInfo)
      {
        Logger.i(RankListActivity.TAG, "APIGetMediaAssetsInfo success" + paramAnonymousMediaAssetsInfo.toString());
        if ((paramAnonymousMediaAssetsInfo == null) || (paramAnonymousMediaAssetsInfo.cList == null))
        {
          RankListActivity.this.dismissLoaddingDialog();
          RankListActivity.this.rankListStructureStream.cancel();
        }
        while (true)
        {
          return;
          RankListActivity.access$202(RankListActivity.this, paramAnonymousMediaAssetsInfo.cList.size());
          String str = paramAnonymousMediaAssetsInfo.package_id;
          for (int i = 0; i < RankListActivity.this.categoryItemSize; i++)
          {
            CategoryItem localCategoryItem = (CategoryItem)paramAnonymousMediaAssetsInfo.cList.get(i);
            RankListActivity.this.getCategoryInfo(str, localCategoryItem.id, localCategoryItem.name, i);
          }
        }
      }
    });
  }

  protected void onResume()
  {
    super.onResume();
    if (this.floatingDetailPageDialog != null)
      this.floatingDetailPageDialog.onResume();
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    if ("usr_cmd".equals(paramString2))
      try
      {
        JSONObject localJSONObject1 = new JSONObject(paramString3);
        localJSONObject2 = localJSONObject1;
        openDetailPage(localJSONObject2.optString("video_id"), localJSONObject2.optInt("video_type"));
        return true;
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          JSONObject localJSONObject2 = null;
        }
      }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("rank_data".equals(paramString))
      return this.rankListStructureStream;
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  private class RankListStructure
  {
    int index;
    List<GetPlaybillSelectedListInfo.Item> items = new ArrayList();
    String name;

    private RankListStructure()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.RankListActivity
 * JD-Core Version:    0.6.2
 */