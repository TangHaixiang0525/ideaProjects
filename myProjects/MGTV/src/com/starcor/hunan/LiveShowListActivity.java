package com.starcor.hunan;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class LiveShowListActivity extends XULActivity
{
  private static final String TAG = LiveShowListActivity.class.getSimpleName();
  private static final int TIMEOUT_RESET_INDICATOR = 400;
  Handler mHandler = new Handler();
  private XulView pageIndicatorDown;
  private XulView pageIndicatorUp;
  Runnable resetDownPageIndicator = new Runnable()
  {
    public void run()
    {
      LiveShowListActivity.this.pageIndicatorDown.setAttr("img.1.visible", "false");
      LiveShowListActivity.this.pageIndicatorDown.setAttr("img.0.visible", "true");
      LiveShowListActivity.this.pageIndicatorDown.resetRender();
    }
  };
  Runnable resetUpPageIndicator = new Runnable()
  {
    public void run()
    {
      LiveShowListActivity.this.pageIndicatorUp.setAttr("img.1.visible", "false");
      LiveShowListActivity.this.pageIndicatorUp.setAttr("img.0.visible", "true");
      LiveShowListActivity.this.pageIndicatorUp.resetRender();
    }
  };

  private InputStream getInitData()
    throws JSONException
  {
    Intent localIntent = getIntent();
    String str1 = localIntent.getStringExtra("packetId");
    String str2 = localIntent.getStringExtra("categoryId");
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIGetPlaybillSelectedList(str1, str2, 9999, 0, new SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener()
    {
      private InputStream buildLiveShowListData(GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        while (true)
        {
          int j;
          try
          {
            localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
            localStringWriter = new StringWriter();
            localXmlSerializer.setOutput(localStringWriter);
            localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
            localXmlSerializer.startTag(null, "data");
            int i = paramAnonymousGetPlaybillSelectedListInfo.items.size();
            LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "num", "" + i);
            j = 1;
            Iterator localIterator = paramAnonymousGetPlaybillSelectedListInfo.items.iterator();
            if (localIterator.hasNext())
            {
              GetPlaybillSelectedListInfo.Item localItem = (GetPlaybillSelectedListInfo.Item)localIterator.next();
              if ((TextUtils.isEmpty(localItem.begin_time)) || (TextUtils.isEmpty(localItem.end_time)))
              {
                localXmlSerializer.startTag(null, "item");
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "id", "" + j);
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "film", localItem.img_h);
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "superscript", localItem.img_v);
                localXmlSerializer.startTag(null, "arg_list");
                LiveShowListActivity.this.writeArgsValue(localXmlSerializer, "special_id", localItem.id);
                LiveShowListActivity.this.writeArgsValue(localXmlSerializer, "link_url", localItem.url);
                localXmlSerializer.endTag(null, "arg_list");
                localXmlSerializer.endTag(null, "item");
              }
              else
              {
                localItem.begin_time = localItem.begin_time.replace("-", "").replace(":", "").replace(" ", "");
                localItem.end_time = localItem.end_time.replace("-", "").replace(":", "").replace(" ", "");
                localXmlSerializer.startTag(null, "item");
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "id", "" + j);
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "film", localItem.img_h);
                String[] arrayOfString = LiveShowListActivity.getPlayState(i, j, localItem.id, localItem.begin_time, localItem.end_time);
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "state", arrayOfString[0]);
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "text", arrayOfString[1]);
                LiveShowListActivity.this.writeNodeValue(localXmlSerializer, "superscript", localItem.img_v);
                localXmlSerializer.startTag(null, "arg_list");
                LiveShowListActivity.this.writeArgsValue(localXmlSerializer, "special_id", localItem.id);
                LiveShowListActivity.this.writeArgsValue(localXmlSerializer, "link_url", localItem.url);
                LiveShowListActivity.this.writeArgsValue(localXmlSerializer, "begin_time", localItem.begin_time);
                LiveShowListActivity.this.writeArgsValue(localXmlSerializer, "end_time", localItem.end_time);
                localXmlSerializer.endTag(null, "arg_list");
                localXmlSerializer.endTag(null, "item");
              }
            }
          }
          catch (IOException localIOException)
          {
            XmlSerializer localXmlSerializer;
            StringWriter localStringWriter;
            localIOException.printStackTrace();
            return null;
            localXmlSerializer.endTag(null, "data");
            localXmlSerializer.endDocument();
            localXmlSerializer.flush();
            String str = localStringWriter.toString();
            Log.d("lx", "info:" + str);
            ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            return localByteArrayInputStream;
          }
          catch (XmlPullParserException localXmlPullParserException)
          {
            localXmlPullParserException.printStackTrace();
            continue;
          }
          j++;
        }
      }

      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        Intent localIntent = new Intent("ACTION_SHOW_ERROR_DIALOG");
        App.getAppContext().sendBroadcast(localIntent);
        Logger.e(LiveShowListActivity.TAG, "APIGetPlaybillSelectedList ERROR");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        localXulPendingInputStream.setBaseStream(buildLiveShowListData(paramAnonymousGetPlaybillSelectedListInfo));
      }
    });
    return localXulPendingInputStream;
  }

  public static String[] getPlayState(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
  {
    String[] arrayOfString = new String[2];
    long l1 = ReservationService.time2Reservation(paramString2) - GlobalEnv.getInstance().getReservationDelayNotifyTime();
    if (ReservationService.getinstance().findReservation(l1, paramString1) != null)
    {
      arrayOfString[0] = "file:///.assets/liveshow/liveshow_state_reservation.png";
      arrayOfString[1] = "已预约";
    }
    String str2;
    while (true)
    {
      str2 = arrayOfString[0].substring(0, -4 + arrayOfString[0].length());
      if (paramInt2 != 1)
        break;
      arrayOfString[0] = (str2 + "_bottom.png");
      return arrayOfString;
      long l2 = SystemTimeManager.getCurrentServerTime();
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeInMillis(l2);
      String str1 = new SimpleDateFormat("yyyyMMddHHmmss").format(localCalendar.getTime());
      if (paramString2.compareTo(str1) > 0)
      {
        arrayOfString[0] = "file:///.assets/liveshow/liveshow_state_no_release.png";
        arrayOfString[1] = "未上映";
      }
      else if (paramString3.compareTo(str1) < 0)
      {
        arrayOfString[0] = "file:///.assets/liveshow/liveshow_state_play.png";
        arrayOfString[1] = "播放";
      }
      else
      {
        arrayOfString[0] = "file:///.assets/liveshow/liveshow_state_live.png";
        arrayOfString[1] = "直播中";
      }
    }
    if (paramInt2 == paramInt1)
    {
      arrayOfString[0] = (str2 + "_top.png");
      return arrayOfString;
    }
    arrayOfString[0] = (str2 + "_mid.png");
    return arrayOfString;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      Logger.d(TAG, "dispatchKeyEvent, keycode=" + paramKeyEvent.getKeyCode());
      switch (paramKeyEvent.getKeyCode())
      {
      default:
      case 19:
      case 20:
      }
    }
    while (true)
    {
      return super.dispatchKeyEvent(paramKeyEvent);
      this.pageIndicatorUp.setAttr("img.1.visible", "true");
      this.pageIndicatorUp.setAttr("img.0.visible", "false");
      this.pageIndicatorUp.resetRender();
      this.mHandler.removeCallbacks(this.resetUpPageIndicator);
      this.mHandler.postDelayed(this.resetUpPageIndicator, 400L);
      continue;
      this.pageIndicatorDown.setAttr("img.1.visible", "true");
      this.pageIndicatorDown.setAttr("img.0.visible", "false");
      this.pageIndicatorDown.resetRender();
      this.mHandler.removeCallbacks(this.resetDownPageIndicator);
      this.mHandler.postDelayed(this.resetDownPageIndicator, 400L);
    }
  }

  protected void onRestart()
  {
    super.onRestart();
    xulReloadPage();
    Logger.d(TAG, "LiveShowListActivity onRestart xulReloadPage");
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if (paramString.startsWith("info/get_live_show_list_page_info"))
      try
      {
        InputStream localInputStream = getInitData();
        return localInputStream;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    this.pageIndicatorUp = xulFindViewById("page_indicator_up");
    this.pageIndicatorDown = xulFindViewById("page_indicator_down");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.LiveShowListActivity
 * JD-Core Version:    0.6.2
 */