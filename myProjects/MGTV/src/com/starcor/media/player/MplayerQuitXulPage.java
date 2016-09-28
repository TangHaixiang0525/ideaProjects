package com.starcor.media.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.PlayerQuitsVideoInfo;
import com.starcor.core.domain.QuitVideoIndexsParams;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.opendownload.encrypt.json.JSONObject;
import com.starcor.report.Column.RecommendColumn;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsAPIGetQuitVideoIndexsTask.ISccmsAPIGetQuitVideoIndexsTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MplayerQuitXulPage extends View
{
  private static final int RECOMMEND_FILM_NUM = 5;
  private static final int RECOMMEND_GET_FILM_NUM = 20;
  private static final String TAG = MplayerQuitXulPage.class.getSimpleName();
  private static ArrayList<FilmListItem> recommendFilms;
  private String categoryId = "";
  private boolean isEmptyRecommend = false;
  private Context mContext;
  private String mediaAssetId = "";
  private OnItemClickListener onItemClickListener;
  private XulView recommendFilmList;
  private HashMap<String, String> recommendReportData = new HashMap();
  private Rect rect;
  private XulRenderContext renderContext;
  private String videoId = "";
  private int videoType = 0;
  private XulMassiveAreaWrapper xulRecommendFilmListWrapper;

  public MplayerQuitXulPage(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    setFocusable(true);
  }

  public MplayerQuitXulPage(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    setFocusable(true);
  }

  public MplayerQuitXulPage(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    setFocusable(true);
  }

  private void buildRecommendFilms(QuitVideoIndexsParams paramQuitVideoIndexsParams)
  {
    label4: Iterator localIterator;
    if (paramQuitVideoIndexsParams == null)
    {
      return;
    }
    else
    {
      while (true)
      {
        recommendFilms = new ArrayList();
        if (paramQuitVideoIndexsParams.videoList.size() == 0)
        {
          Logger.w(TAG, "返回数据列表为空，显示默认海报！");
          for (int j = 0; j < 5; j++)
          {
            FilmListItem localFilmListItem3 = new FilmListItem();
            recommendFilms.add(localFilmListItem3);
          }
        }
        else
        {
          if (paramQuitVideoIndexsParams.videoList.size() >= 5)
            break;
          for (int i = -1 + paramQuitVideoIndexsParams.videoList.size(); i < 5; i++)
          {
            FilmListItem localFilmListItem2 = new FilmListItem();
            recommendFilms.add(localFilmListItem2);
          }
        }
      }
      localIterator = paramQuitVideoIndexsParams.videoList.iterator();
    }
    while (true)
    {
      if (!localIterator.hasNext())
        break label4;
      PlayerQuitsVideoInfo localPlayerQuitsVideoInfo = (PlayerQuitsVideoInfo)localIterator.next();
      if (localPlayerQuitsVideoInfo == null)
        break;
      FilmListItem localFilmListItem1 = new FilmListItem();
      localFilmListItem1.video_id = localPlayerQuitsVideoInfo.id;
      try
      {
        localFilmListItem1.video_type = Integer.valueOf(localPlayerQuitsVideoInfo.video_type).intValue();
        localFilmListItem1.film_name = localPlayerQuitsVideoInfo.name;
        if (!TextUtils.isEmpty(localPlayerQuitsVideoInfo.img_v))
        {
          localFilmListItem1.v_img_url = localPlayerQuitsVideoInfo.img_v;
          localFilmListItem1.category_id = localPlayerQuitsVideoInfo.category_id;
          localFilmListItem1.corner_mark_img_url = localPlayerQuitsVideoInfo.corner_mark_img;
          localFilmListItem1.corner_mark = localPlayerQuitsVideoInfo.corner_mark;
          localFilmListItem1.import_id = localPlayerQuitsVideoInfo.assets_import_id;
          localFilmListItem1.serial_id = localPlayerQuitsVideoInfo.serial_id;
          localFilmListItem1.estimateId = paramQuitVideoIndexsParams.estimateId;
          localFilmListItem1.artithmeticId = paramQuitVideoIndexsParams.artithmeticId;
          if (recommendFilms == null)
            continue;
          recommendFilms.add(localFilmListItem1);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localFilmListItem1.video_type = 0;
          continue;
          localFilmListItem1.h_img_url = localPlayerQuitsVideoInfo.img_h;
        }
      }
    }
  }

  private void initView()
  {
    this.renderContext = XulManager.createXulRender("MplayerQuitPage", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void invalidate(Rect paramAnonymousRect)
      {
        if (paramAnonymousRect == null)
        {
          MplayerQuitXulPage.this.invalidate();
          return;
        }
        MplayerQuitXulPage.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        JSONObject localJSONObject = null;
        if (paramAnonymousString3 != null);
        while (true)
        {
          try
          {
            localJSONObject = new JSONObject(paramAnonymousString3);
            if (!"click".equals(paramAnonymousString1))
              break;
            if ("MplayerQuitPage_menu_button_on_click".equals(paramAnonymousString2))
            {
              String str = localJSONObject.optString("menu_name");
              if ("quit".equals(str))
              {
                MplayerQuitXulPage.this.onItemClickListener.onQuitClick();
                MplayerQuitXulPage.this.saveEntranceSrc(paramAnonymousXulView);
                return;
              }
              if (!"next_episode".equals(str))
                continue;
              MplayerQuitXulPage.this.saveEntranceSrc(paramAnonymousXulView);
              if ((MplayerQuitXulPage.this.mContext instanceof DialogActivity))
                ((DialogActivity)MplayerQuitXulPage.this.mContext).click_view = paramAnonymousXulView;
              MplayerQuitXulPage.this.onItemClickListener.onNextEpisodeClick();
              continue;
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            return;
          }
          if ("open_detail_page".equals(paramAnonymousString2))
          {
            MplayerQuitXulPage.this.recommendReportData.put("pos", localJSONObject.optString("idx"));
            MplayerQuitXulPage.this.recommendReportData.put("page", "0");
            MplayerQuitXulPage.this.recommendReportData.put("limit", "5");
            MplayerQuitXulPage.this.recommendReportData.put("hitid", localJSONObject.optString("videoId"));
            MplayerQuitXulPage.this.recommendReportData.put("ohitid", localJSONObject.optString("importId"));
            MplayerQuitXulPage.this.recommendReportData.put("sohitid", localJSONObject.optString("seriesId"));
            if ((MplayerQuitXulPage.this.mContext instanceof DialogActivity))
              ((DialogActivity)MplayerQuitXulPage.this.mContext).reportClickRecommend(RecommendColumn.MPLAYER_QUIT_RECOMMEND_REGION, MplayerQuitXulPage.this.recommendReportData);
            if (localJSONObject.optString("videoId").trim().length() > 0)
            {
              FilmListItem localFilmListItem = new FilmListItem();
              localFilmListItem.video_id = localJSONObject.optString("videoId");
              localFilmListItem.video_type = Integer.valueOf(localJSONObject.optString("videoType")).intValue();
              localFilmListItem.film_name = localJSONObject.optString("name");
              localFilmListItem.viewType = Integer.valueOf(localJSONObject.optString("viewType")).intValue();
              Log.d(MplayerQuitXulPage.TAG, "open_detail_page:" + localFilmListItem.toString());
              MplayerQuitXulPage.this.saveEntranceSrc(paramAnonymousXulView);
              if ((MplayerQuitXulPage.this.mContext instanceof DialogActivity))
                ((DialogActivity)MplayerQuitXulPage.this.mContext).click_view = paramAnonymousXulView;
              MplayerQuitXulPage.this.onItemClickListener.onRecommendItemClick(0, localFilmListItem);
            }
          }
        }
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        MplayerQuitXulPage.access$002(MplayerQuitXulPage.this, MplayerQuitXulPage.this.renderContext.findItemById("film_list"));
        MplayerQuitXulPage.access$202(MplayerQuitXulPage.this, XulMassiveAreaWrapper.fromXulView(MplayerQuitXulPage.this.recommendFilmList));
        Logger.i(MplayerQuitXulPage.TAG, "onRenderIsReady()");
        MplayerQuitXulPage.this.loadFilms();
      }

      public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void uiRun(Runnable paramAnonymousRunnable)
      {
        this._mHandler.post(paramAnonymousRunnable);
      }

      public void uiRun(Runnable paramAnonymousRunnable, int paramAnonymousInt)
      {
        this._mHandler.postDelayed(paramAnonymousRunnable, paramAnonymousInt);
      }
    }
    , XulManager.getPageWidth(), XulManager.getPageHeight());
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams.addRule(15);
    setLayoutParams(localLayoutParams);
  }

  private void reportShowRecom(String paramString, HashMap<String, String> paramHashMap)
  {
    paramHashMap.put("pos", "");
    paramHashMap.put("page", "0");
    paramHashMap.put("limit", "5");
    paramHashMap.put("hitid", "");
    paramHashMap.put("ohitid", "");
    paramHashMap.put("sohitid", "");
    if ((this.mContext instanceof DialogActivity))
      ((DialogActivity)this.mContext).reportShowRecommend(paramString, paramHashMap);
  }

  private void saveEntranceSrc(XulView paramXulView)
  {
    String str = "";
    if (paramXulView != null)
      str = ReportArea.getValue(paramXulView.getAttrString("page_area"));
    if (!TextUtils.isEmpty(str))
      ReportInfo.getInstance().setEntranceSrc(str);
  }

  public void addRecommendData(ArrayList<FilmListItem> paramArrayList)
  {
    Logger.i(TAG, "loadRecommendData()");
    if (this.xulRecommendFilmListWrapper == null)
      Logger.e(TAG, "loadRecommendData() recommendFilmListView is null");
    String str1;
    String str2;
    String str3;
    int i;
    XulDataNode localXulDataNode;
    do
    {
      return;
      this.xulRecommendFilmListWrapper.clear();
      str1 = "";
      str2 = "";
      str3 = "";
      i = 0;
      if (i >= 5)
        break;
      localXulDataNode = XulDataNode.obtainDataNode("item");
      localXulDataNode.setAttribute("idx", String.valueOf(i + 1));
    }
    while (paramArrayList == null);
    if (i < paramArrayList.size())
    {
      FilmListItem localFilmListItem = (FilmListItem)paramArrayList.get(i);
      if (!TextUtils.isEmpty(localFilmListItem.video_id))
        str1 = str1 + "," + localFilmListItem.video_id;
      if (!TextUtils.isEmpty(localFilmListItem.import_id))
        str2 = str2 + "," + localFilmListItem.import_id;
      if (!TextUtils.isEmpty(localFilmListItem.serial_id))
        str3 = str3 + "," + localFilmListItem.serial_id;
      localXulDataNode.setAttribute("name", localFilmListItem.film_name);
      localXulDataNode.setAttribute("video_id", localFilmListItem.video_id);
      localXulDataNode.setAttribute("view_type", localFilmListItem.viewType);
      localXulDataNode.setAttribute("video_type", localFilmListItem.video_type);
      localXulDataNode.setAttribute("img_v", localFilmListItem.v_img_url);
      localXulDataNode.setAttribute("img_h", localFilmListItem.h_img_url);
      localXulDataNode.setAttribute("corner_mark", localFilmListItem.corner_mark);
      localXulDataNode.setAttribute("corner_mark_img", localFilmListItem.corner_mark_img_url);
      localXulDataNode.setAttribute("import_id", localFilmListItem.import_id);
      localXulDataNode.setAttribute("serial_id", localFilmListItem.serial_id);
      localXulDataNode.setAttribute("display_mode", "block");
    }
    while (true)
    {
      this.xulRecommendFilmListWrapper.addItem(localXulDataNode);
      i++;
      break;
      localXulDataNode.setAttribute("name", "");
      localXulDataNode.setAttribute("video_id", "");
      localXulDataNode.setAttribute("view_type", "");
      localXulDataNode.setAttribute("video_type", "");
      localXulDataNode.setAttribute("import_id", "");
      localXulDataNode.setAttribute("serial_id", "");
      localXulDataNode.setAttribute("display_mode", "none");
    }
    HashMap localHashMap1 = this.recommendReportData;
    String str4;
    HashMap localHashMap2;
    if (paramArrayList.size() > 0)
    {
      str4 = ((FilmListItem)paramArrayList.get(0)).artithmeticId;
      localHashMap1.put("ver", str4);
      localHashMap2 = this.recommendReportData;
      if (paramArrayList.size() <= 0)
        break label662;
    }
    label662: for (String str5 = ((FilmListItem)paramArrayList.get(0)).estimateId; ; str5 = "")
    {
      localHashMap2.put("reqid", str5);
      HashMap localHashMap3 = this.recommendReportData;
      if (str1.length() > 1)
        str1 = str1.substring(1);
      localHashMap3.put("rcdata", str1);
      HashMap localHashMap4 = this.recommendReportData;
      if (str2.length() > 1)
        str2 = str2.substring(1);
      localHashMap4.put("orcdata", str2);
      HashMap localHashMap5 = this.recommendReportData;
      if (str3.length() > 1)
        str3 = str3.substring(1);
      localHashMap5.put("sorcdata", str3);
      this.xulRecommendFilmListWrapper.syncContentView();
      return;
      str4 = "";
      break;
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Log.d("View", "NewmenuView dispatchKeyEvent " + paramKeyEvent.getKeyCode() + "hasFocus = " + hasFocus());
    if ((this.renderContext != null) && (this.renderContext.onKeyEvent(paramKeyEvent)))
    {
      Log.i("View", "NewmenuView dispatchKeyEvent 按键已处理");
      return true;
    }
    Log.i("View", "NewmenuView dispatchKeyEvent 按键未处理");
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void loadFilms()
  {
    ServerApiManager.i().APIGetQuitVideoIndexs(this.mediaAssetId, this.categoryId, this.videoId, this.videoType, 5, new SccmsAPIGetQuitVideoIndexsTask.ISccmsAPIGetQuitVideoIndexsTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(MplayerQuitXulPage.TAG, "APIGetQuitVideoIndexs.onError():" + paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, QuitVideoIndexsParams paramAnonymousQuitVideoIndexsParams)
      {
        if (paramAnonymousQuitVideoIndexsParams == null)
        {
          Logger.i(MplayerQuitXulPage.TAG, "result=null");
          MplayerQuitXulPage.access$802(MplayerQuitXulPage.this, true);
        }
        do
        {
          return;
          MplayerQuitXulPage.access$802(MplayerQuitXulPage.this, false);
        }
        while ((!(MplayerQuitXulPage.this.mContext instanceof Activity)) || (((Activity)MplayerQuitXulPage.this.mContext).isFinishing()));
        MplayerQuitXulPage.this.buildRecommendFilms(paramAnonymousQuitVideoIndexsParams);
        MplayerQuitXulPage.this.addRecommendData(MplayerQuitXulPage.recommendFilms);
      }
    });
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    initView();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.renderContext != null)
      this.renderContext.destroy();
    this.renderContext = null;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    int[] arrayOfInt = { -1308622848, -671088640, -16777216 };
    localPaint.setShader(new LinearGradient(0.0F, 0.0F, 0.0F, App.Operation(getHeight()), arrayOfInt, new float[] { 0.0F, 0.5F, 1.0F }, Shader.TileMode.REPEAT));
    if (this.renderContext != null)
    {
      if (this.rect != null)
        break label150;
      this.rect = new Rect(0, 0, App.Operation(getWidth()), App.Operation(getHeight()));
    }
    while (true)
    {
      paramCanvas.drawRect(this.rect, localPaint);
      if (this.renderContext.beginDraw(paramCanvas, this.rect))
        this.renderContext.endDraw();
      return;
      label150: this.rect.set(0, 0, App.Operation(getWidth()), App.Operation(getHeight()));
    }
  }

  public void setDefaultFocus()
  {
    if (this.renderContext != null)
    {
      XulView localXulView = this.renderContext.findItemById("menu_btn_quit");
      this.renderContext.getLayout().requestFocus(localXulView);
    }
  }

  public void setNextEpisodeVisibility(int paramInt)
  {
    try
    {
      if (this.renderContext != null)
      {
        XulView localXulView = this.renderContext.findItemById("menu_btn_next_episode");
        if (paramInt == 0)
          localXulView.setStyle("display", "block");
        while (true)
        {
          localXulView.resetRender();
          return;
          localXulView.setStyle("display", "none");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.onItemClickListener = paramOnItemClickListener;
  }

  public void setPlayerInfos(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.mediaAssetId = paramString1;
    this.categoryId = paramString2;
    this.videoId = paramString3;
    this.videoType = paramInt;
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if ((paramInt == 0) && (!this.isEmptyRecommend))
      reportShowRecom(RecommendColumn.MPLAYER_QUIT_RECOMMEND_REGION, this.recommendReportData);
  }

  public static abstract interface OnItemClickListener
  {
    public abstract void onNextEpisodeClick();

    public abstract void onQuitClick();

    public abstract void onRecommendItemClick(int paramInt, Object paramObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerQuitXulPage
 * JD-Core Version:    0.6.2
 */