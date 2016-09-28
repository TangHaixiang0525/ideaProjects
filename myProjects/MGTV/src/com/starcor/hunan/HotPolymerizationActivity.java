package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.core.domain.UserAttr;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAttrTask.ISccmsApiGetUserAttrTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulPageSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class HotPolymerizationActivity extends XULActivity
{
  private final float PER_PAGE_SIZE = 10.0F;
  private final String TAG = HotPolymerizationActivity.class.getSimpleName();
  private FloatingDetailPageDialog floatingDetailPageDialog;
  private XulPendingInputStream inputStream = new XulPendingInputStream();

  private InputStream buildData(GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo)
  {
    int i = paramGetPlaybillSelectedListInfo.items.size();
    if (i == 0)
      return null;
    SparseArray localSparseArray = new SparseArray();
    ArrayList localArrayList1 = null;
    for (int j = 0; j < i; j++)
    {
      if (j % 10.0F == 0.0F)
      {
        localArrayList1 = new ArrayList();
        localSparseArray.put(j / 10, localArrayList1);
      }
      if (localArrayList1 != null)
        localArrayList1.add(paramGetPlaybillSelectedListInfo.items.get(j));
    }
    while (true)
    {
      int m;
      try
      {
        XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        int k = 0;
        if (k < 10.0F)
        {
          localXmlSerializer.startTag(null, "group");
          m = 0;
          if (m < localSparseArray.size())
          {
            ArrayList localArrayList2 = (ArrayList)localSparseArray.get(m);
            if ((localArrayList2 == null) || (localArrayList2.size() <= k))
              break label514;
            localXmlSerializer.startTag(null, "item");
            GetPlaybillSelectedListInfo.Item localItem = (GetPlaybillSelectedListInfo.Item)localArrayList2.get(k);
            if (localItem.name == null)
            {
              str1 = "";
              writeNodeValue(localXmlSerializer, "name", str1);
              writeNodeValue(localXmlSerializer, "image", "effect:hexagon:" + localItem.img_v);
              localXmlSerializer.startTag(null, "arg_list");
              writeArgsValue(localXmlSerializer, "video_id", localItem.video_id);
              writeArgsValue(localXmlSerializer, "video_type", localItem.video_type);
              writeNodeValue(localXmlSerializer, "name", str1);
              writeArgsValue(localXmlSerializer, "ui_style", String.valueOf(localItem.ui_style));
              writeArgsValue(localXmlSerializer, "media_asset_id", localItem.media_assets_id);
              writeArgsValue(localXmlSerializer, "category_id", localItem.category_id);
              localXmlSerializer.endTag(null, "arg_list");
              localXmlSerializer.endTag(null, "item");
              break label514;
            }
            String str1 = localItem.name.trim();
            continue;
          }
          localXmlSerializer.endTag(null, "group");
          k++;
          continue;
        }
        localXmlSerializer.endTag(null, "data");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str2 = localStringWriter.toString();
        Log.d(this.TAG, "info:" + str2);
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str2.getBytes("UTF-8"));
        return localByteArrayInputStream;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      label514: m++;
    }
  }

  private Pair<Integer, XulView> findFocusView()
  {
    XulArrayList localXulArrayList = xulFindViewsByClass("h-flip-page-slider");
    if (localXulArrayList != null)
    {
      int i = 0;
      while (i < localXulArrayList.size())
        try
        {
          XulView localXulView = (XulView)localXulArrayList.get(i);
          if (localXulView.hasFocus())
          {
            Pair localPair = new Pair(Integer.valueOf(i), localXulView);
            return localPair;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          i++;
        }
    }
    return new Pair(Integer.valueOf(-1), null);
  }

  private void flipPages(final FlipMode paramFlipMode, final XulView paramXulView)
  {
    Iterator localIterator = xulFindViewsByClass("h-flip-page-slider").iterator();
    while (localIterator.hasNext())
    {
      final XulView localXulView = (XulView)localIterator.next();
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          XulPageSliderAreaWrapper localXulPageSliderAreaWrapper = XulPageSliderAreaWrapper.fromXulView(localXulView);
          if (paramFlipMode == HotPolymerizationActivity.FlipMode.PRE)
            localXulPageSliderAreaWrapper.slidePrev();
          while (true)
          {
            if (localXulView.hasFocus())
              HotPolymerizationActivity.this.pageSliderRequestFocus(paramXulView);
            return;
            if (paramFlipMode == HotPolymerizationActivity.FlipMode.NEXT)
              localXulPageSliderAreaWrapper.slideNext();
          }
        }
      }
      , (int)(500.0D * Math.random()));
    }
  }

  public static void getUserAttrTask()
  {
    ServerApiManager.i().APIGetUserAttr(new SccmsApiGetUserAttrTask.ISccmsApiGetUserAttrTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserAttr paramAnonymousUserAttr)
      {
        GlobalLogic.getInstance().setAreaCode(paramAnonymousUserAttr.areaCode);
        GlobalLogic.getInstance().setUserAttr(paramAnonymousUserAttr.attr);
      }
    });
  }

  private void getUserData()
  {
    ServerApiManager.i().APIGetPlaybillSelectedList(getIntent().getStringExtra("packetId"), getIntent().getStringExtra("categoryId"), 30, 0, GlobalLogic.getInstance().getAreaCode(), GlobalLogic.getInstance().getUserAttr(), "0", new SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i(HotPolymerizationActivity.this.TAG, "SccmsApiGetPlaybillSelectedList error");
        HotPolymerizationActivity.this.dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        HotPolymerizationActivity.this.inputStream.setBaseStream(HotPolymerizationActivity.this.buildData(paramAnonymousGetPlaybillSelectedListInfo));
        HotPolymerizationActivity.this.dismissLoaddingDialog();
      }
    });
  }

  private void openDetailPage(Bundle paramBundle)
  {
    this.floatingDetailPageDialog = new FloatingDetailPageDialog(paramBundle.getString("video_id"), XulUtils.tryParseInt(paramBundle.getString("video_type")), this);
  }

  private void pageSliderRequestFocus(XulView paramXulView)
  {
    if (paramXulView == null);
    int i;
    XulArrayList localXulArrayList;
    do
    {
      do
      {
        return;
        i = XulPageSliderAreaWrapper.fromXulView(paramXulView).getCurrentPage();
      }
      while (!(paramXulView instanceof XulArea));
      localXulArrayList = XulPage.findItemsByClass((XulArea)paramXulView, "poster");
      Logger.i(this.TAG, "posterList.size" + localXulArrayList.size() + "currentPage" + i);
    }
    while (i >= localXulArrayList.size());
    xulRequestFocus((XulView)localXulArrayList.get(i));
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      if (paramKeyEvent.getKeyCode() != 21)
        break label78;
      Logger.i(this.TAG, "ACTION_DOWN left");
      Pair localPair2 = findFocusView();
      if ((((Integer)localPair2.first).intValue() == 0) || (((Integer)localPair2.first).intValue() == 5))
        flipPages(FlipMode.PRE, (XulView)localPair2.second);
    }
    while (true)
    {
      return super.dispatchKeyEvent(paramKeyEvent);
      label78: if (paramKeyEvent.getKeyCode() == 22)
      {
        Logger.i(this.TAG, "ACTION_DOWN right");
        Pair localPair1 = findFocusView();
        if ((((Integer)localPair1.first).intValue() == 4) || (((Integer)localPair1.first).intValue() == 9))
          flipPages(FlipMode.NEXT, (XulView)localPair1.second);
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showLoaddingDialog();
    getUserData();
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
    {
      if ((paramObject instanceof XulDataNode));
      for (Bundle localBundle = xulArgListToBundle((XulDataNode)paramObject); ; localBundle = new Bundle())
      {
        openDetailPage(localBundle);
        return true;
      }
    }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("polymerization_data".equals(paramString))
      return this.inputStream;
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  private static enum FlipMode
  {
    static
    {
      NEXT = new FlipMode("NEXT", 1);
      FlipMode[] arrayOfFlipMode = new FlipMode[2];
      arrayOfFlipMode[0] = PRE;
      arrayOfFlipMode[1] = NEXT;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.HotPolymerizationActivity
 * JD-Core Version:    0.6.2
 */