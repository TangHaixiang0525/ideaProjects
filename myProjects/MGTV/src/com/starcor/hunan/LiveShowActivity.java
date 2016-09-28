package com.starcor.hunan;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.starcor.apitask.IApiTaskListener;
import com.starcor.apitask.info.LiveShowSpecialInfo;
import com.starcor.apitask.info.LiveShowSpecialInfo.SpecialItem;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulData;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveShowActivity extends XULActivity
{
  private static final String TAG = LiveShowActivity.class.getSimpleName();
  private boolean isLoadSuccess = false;
  Object saveUserdata;
  XulView saveView;
  ArrayList<Pair<String, String>> specialIds = new ArrayList();

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
      if (paramInt2 != 0)
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
    if (paramInt2 == paramInt1 - 1)
    {
      arrayOfString[0] = (str2 + "_top.png");
      return arrayOfString;
    }
    arrayOfString[0] = (str2 + "_mid.png");
    return arrayOfString;
  }

  private void refreshStatus()
  {
    if ((this.saveView == null) || (this.saveUserdata == null));
    String str1;
    String str2;
    String str3;
    int i;
    do
    {
      return;
      Logger.d(TAG, "refreshStatus, saveView=" + this.saveView + ", saveUserdata=" + this.saveUserdata);
      XulDataNode localXulDataNode1 = (XulDataNode)this.saveUserdata;
      str1 = "";
      str2 = "";
      str3 = "";
      XulDataNode localXulDataNode2 = localXulDataNode1.getFirstChild();
      if (localXulDataNode2 != null)
      {
        XulDataNode localXulDataNode3 = localXulDataNode2.getChildNode("k");
        if ("special_id".equals(localXulDataNode3.getValue()))
          str1 = localXulDataNode2.getChildNode("v").getValue();
        while (true)
        {
          localXulDataNode2 = localXulDataNode2.getNext();
          break;
          if ("begin_time1".equals(localXulDataNode3.getValue()))
            str2 = localXulDataNode2.getChildNode("v").getValue();
          else if ("end_time1".equals(localXulDataNode3.getValue()))
            str3 = localXulDataNode2.getChildNode("v").getValue();
        }
      }
      i = Integer.parseInt((String)this.saveView.getAttr("pos").getValue());
    }
    while (i <= 2);
    String[] arrayOfString = getPlayState(-3 + this.specialIds.size(), i - 3, str1, str2, str3);
    this.saveView.setAttr("img.3", arrayOfString[0]);
    this.saveView.setAttr("text", arrayOfString[1]);
  }

  private void reportPageExit(boolean paramBoolean)
  {
    reportExit(paramBoolean, null);
  }

  private void reportPageLoad(boolean paramBoolean)
  {
    reportLoad(paramBoolean, null);
  }

  protected void onRestart()
  {
    super.onRestart();
    refreshStatus();
    Logger.d(TAG, "LiveShowActivity onRestart");
    reportPageLoad(this.isLoadSuccess);
  }

  protected void onStop()
  {
    super.onStop();
    reportPageExit(this.isLoadSuccess);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    if ("jsCmd".equals(paramString2));
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString3);
      localJSONObject2 = localJSONObject1;
      if (("liveShowLoadDataFinish".equalsIgnoreCase(localJSONObject2.optString("cmd"))) && ((paramObject instanceof XulDataNode)))
      {
        String str = DialogActivity.xulArgListToBundle((XulDataNode)paramObject).getString("img3");
        paramXulView.setAttr("img.2", str);
        Logger.w(TAG, "liveShowLoadDataFinish, imgUrl---> " + str);
      }
      if (("usr_cmd".equals(paramString2)) && ("m_open_live_show_detail_page".equalsIgnoreCase(paramString3)))
      {
        this.saveView = paramXulView;
        this.saveUserdata = paramObject;
        Logger.w(TAG, "goto liveshow web");
      }
      return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        JSONObject localJSONObject2 = null;
      }
    }
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    XulArrayList localXulArrayList1 = xulFindViewsByClass("film_left_big");
    XulArrayList localXulArrayList2 = xulFindViewsByClass("film_left_small");
    XulArrayList localXulArrayList3 = xulFindViewsByClass("film_right");
    final ArrayList localArrayList1 = new ArrayList();
    localArrayList1.addAll(localXulArrayList1);
    localArrayList1.addAll(localXulArrayList2);
    localArrayList1.addAll(localXulArrayList3);
    this.specialIds.clear();
    final ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator = localArrayList1.iterator();
    while (localIterator.hasNext())
    {
      XulView localXulView = (XulView)localIterator.next();
      try
      {
        XulDataNode localXulDataNode;
        for (Object localObject = ((XulDataNode)localXulView.getData("userdata").getValue()).getFirstChild(); localObject != null; localObject = localXulDataNode)
        {
          if ("special_id".equals(((XulDataNode)localObject).getChildNode("k").getValue()))
          {
            String str1 = ((XulDataNode)localObject).getChildNode("v").getValue();
            localArrayList2.add(str1);
            String str2 = (String)localXulView.getAttr("pos").getValue();
            this.specialIds.add(Pair.create(str2, str1));
          }
          localXulDataNode = ((XulDataNode)localObject).getNext();
        }
      }
      catch (Exception localException)
      {
        Logger.d(TAG, localException.getMessage());
      }
    }
    ServerApiManager localServerApiManager = ServerApiManager.i();
    IApiTaskListener local1 = new IApiTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(LiveShowActivity.TAG, "APIGetSpecialInfoByIds failed!! " + paramAnonymousServerApiCommonError.toString());
        LiveShowActivity.access$102(LiveShowActivity.this, false);
        LiveShowActivity.this.reportPageLoad(LiveShowActivity.this.isLoadSuccess);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, LiveShowSpecialInfo paramAnonymousLiveShowSpecialInfo)
      {
        LiveShowActivity.access$102(LiveShowActivity.this, true);
        LiveShowActivity.this.reportPageLoad(LiveShowActivity.this.isLoadSuccess);
        Logger.d(LiveShowActivity.TAG, "APIGetSpecialInfoByIds success!! ");
        Iterator localIterator = LiveShowActivity.this.specialIds.iterator();
        while (localIterator.hasNext())
        {
          int i = Integer.parseInt((String)((Pair)localIterator.next()).first);
          LiveShowSpecialInfo.SpecialItem localSpecialItem = (LiveShowSpecialInfo.SpecialItem)paramAnonymousLiveShowSpecialInfo.specialItems.get(i);
          XulView localXulView1 = (XulView)localArrayList1.get(i);
          if ((localSpecialItem != null) && (!TextUtils.isEmpty(localSpecialItem.beginTime)) && (!TextUtils.isEmpty(localSpecialItem.endTime)))
          {
            localSpecialItem.beginTime = localSpecialItem.beginTime.replace("-", "").replace(":", "").replace(" ", "");
            localSpecialItem.endTime = localSpecialItem.endTime.replace("-", "").replace(":", "").replace(" ", "");
            if (i > 2)
            {
              String[] arrayOfString = LiveShowActivity.getPlayState(-3 + LiveShowActivity.this.specialIds.size(), i - 3, (String)localArrayList2.get(i), localSpecialItem.beginTime, localSpecialItem.endTime);
              localXulView1.setAttr("img.3", arrayOfString[0]);
              localXulView1.setAttr("text", arrayOfString[1]);
            }
            XulDataNode localXulDataNode1 = (XulDataNode)localXulView1.getData("userdata").getValue();
            if (localXulDataNode1.getAttribute("hasLinkUrl") == null)
            {
              XulDataNode localXulDataNode2 = localXulDataNode1.appendChild("a");
              localXulDataNode2.appendChild("k", "link_url");
              localXulDataNode2.appendChild("v", localSpecialItem.linkUrl);
              XulDataNode localXulDataNode3 = localXulDataNode1.appendChild("a");
              localXulDataNode3.appendChild("k", "begin_time1");
              localXulDataNode3.appendChild("v", localSpecialItem.beginTime);
              localXulDataNode1.setAttribute("hasLinkUrl", "true");
              XulDataNode localXulDataNode4 = localXulDataNode1.appendChild("a");
              localXulDataNode4.appendChild("k", "end_time1");
              localXulDataNode4.appendChild("v", localSpecialItem.endTime);
            }
            localXulView1.resetRender();
            int j = Math.min(-4 + LiveShowActivity.this.specialIds.size(), 4);
            XulView localXulView2 = LiveShowActivity.this.xulFindViewById("line");
            localXulView2.setAttr("height", "" + j * 135);
            localXulView2.resetRender();
          }
        }
      }
    };
    localServerApiManager.APIGetSpecialInfoByIds(local1, (String[])localArrayList2.toArray(new String[localArrayList2.size()]));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.LiveShowActivity
 * JD-Core Version:    0.6.2
 */