package com.starcor.hunan;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.SpecialCategoryContent;
import com.starcor.core.domain.SpecialPlayerData;
import com.starcor.core.domain.SpecialPlayerData.UiStyle;
import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.domain.SpecialTopicTreeCategory;
import com.starcor.core.domain.SpecialTopicTreeInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.Tools;
import com.starcor.hunan.widget.MplayerDialog;
import com.starcor.hunan.widget.MplayerDialog.MplayerDialogListener;
import com.starcor.hunan.widget.TokenDialog;
import com.starcor.hunan.widget.TokenDialog.TokenDialogListener;
import com.starcor.media.player.SpecialPlayerDataManeger;
import com.starcor.report.ReportInfo;
import com.starcor.report.ReportPageInfo;
import com.starcor.report.pay.PayReportHelper;
import com.starcor.report.pay.VodPayInfo;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicColumnTreeTask.ISccmsApiGetSpecialTopicColumnTreeTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulViewIterator;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SpecialPlayerActivity extends DialogActivity
{
  public static final String ALL_CATEGPRY = "ALL";
  private static final int DELAY_FOCUS_TIME = 200;
  private static final int HIDE_QUIT_DELAY = 2000;
  private static final int MSG_DELAY_FOCUS = 19;
  private static final int MSG_HIDE_QUIT = 16;
  private static final int MSG_MASSIVE_DELAY = 20;
  private static final int MSG_MASSIVE_UPDATE = 18;
  private static final int MSG_SWITCH_CATEGORY = 17;
  private static final int SWITCH_CATEGORY_DELAY = 300;
  private static final String TAG = SpecialPlayerActivity.class.getSimpleName();
  public static String cid = "";
  private static final String pageId = "SpecialPlayer";
  public static String ref = "";
  private TokenDialog.TokenDialogListener buyDialogListener = new TokenDialog.TokenDialogListener()
  {
    public void onCancel(int paramAnonymousInt)
    {
      Logger.i(SpecialPlayerActivity.TAG, "buyDialogListener onCancel!type：" + paramAnonymousInt);
      SpecialPlayerActivity.this.onDialogCancel();
      if ((paramAnonymousInt == 20003) || (paramAnonymousInt == 2009))
      {
        SpecialPlayerActivity.this.displayPayTip(true);
        XulView localXulView = SpecialPlayerActivity.this.xulGetFocus();
        if ((localXulView != null) && (localXulView.getId().equals("window")))
          SpecialPlayerActivity.this.xulRequestFocus(SpecialPlayerActivity.this.massiveWrapper.getItemView(SpecialPlayerActivity.this.mPlayIndex));
      }
    }

    public void onPositiveClick(int paramAnonymousInt)
    {
      if ((paramAnonymousInt == 20003) || (paramAnonymousInt == 2009))
        SpecialPlayerActivity.this.reportBuy();
    }
  };
  private XulArea categorySlider = null;
  private XulSliderAreaWrapper categoryWrapper = null;
  private XulView checkItem = null;
  private String dialogMessage = "";
  private MplayerDialog.MplayerDialogListener errorDialogListener = new MplayerDialog.MplayerDialogListener()
  {
    public void onCancel(int paramAnonymousInt)
    {
      Logger.i(SpecialPlayerActivity.TAG, "errorDialogListener onCancel!");
      SpecialPlayerActivity.this.onDialogCancel();
    }
  };
  private XulView focusCategoryItem = null;
  private XulView focusItem = null;
  private XulView fullTipView = null;
  private boolean hasCategory = true;
  private boolean isDisplayNum = false;
  private String isIncludeAll = "1";
  private long lastBackPressedTime = 0L;
  public String mFocusCategoryId = "";
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 16:
      case 17:
      case 18:
      case 19:
      }
      do
      {
        do
        {
          do
          {
            return;
            SpecialPlayerActivity.this.setQuitTipsDisplay(false);
            SpecialPlayerActivity.access$102(SpecialPlayerActivity.this, 0L);
            return;
          }
          while (SpecialPlayerActivity.this.isFinishing());
          if (SpecialPlayerActivity.this.mHandler.hasMessages(17))
          {
            SpecialPlayerActivity.this.mHandler.removeMessages(17);
            SpecialPlayerActivity.this.mHandler.sendEmptyMessageDelayed(17, 300L);
            return;
          }
          SpecialPlayerActivity.this.mHandler.removeMessages(19);
          SpecialPlayerActivity.this.updateSpecialView();
          SpecialPlayerActivity.this.updateItems(false);
          return;
        }
        while (SpecialPlayerActivity.this.isFinishing());
        if (SpecialPlayerActivity.this.mHandler.hasMessages(18))
        {
          SpecialPlayerActivity.this.mHandler.removeMessages(18);
          SpecialPlayerActivity.this.mHandler.sendEmptyMessageDelayed(18, 20L);
          return;
        }
        SpecialPlayerActivity.this.massive.getRender().getRenderContext().scheduleLayoutFinishedTask(new Runnable()
        {
          public void run()
          {
            SpecialPlayerActivity.this.clearItems();
          }
        });
        return;
      }
      while (SpecialPlayerActivity.this.isFinishing());
      if (SpecialPlayerActivity.this.mHandler.hasMessages(19))
      {
        SpecialPlayerActivity.this.mHandler.removeMessages(19);
        SpecialPlayerActivity.this.mHandler.sendEmptyMessageDelayed(19, 200L);
        return;
      }
      SpecialPlayerActivity.this.setFontFocus();
    }
  };
  private boolean mIsLoadSuccess = false;
  private XulArea mLeftContent = null;
  public String mPlayCategoryId = "";
  private SpecialCategoryContent mPlayContent = null;
  public int mPlayIndex = -1;
  private XulArea mRightContent = null;
  private String mSpecialBindingId = "";
  private String mSpecialId = "";
  private ArrayList<SpecialTopicPkgCntLstStruct> mSpecialSrcList = new ArrayList();
  private VideoInfo mVideoInfo;
  private XulArea massive = null;
  private XulMassiveAreaWrapper massiveWrapper = null;
  private MplayerPayControl.MovieCouponDialogListener movieCouponDialogListener = new MplayerPayControl.MovieCouponDialogListener()
  {
    public void onCancel()
    {
      Logger.i(SpecialPlayerActivity.TAG, "movieCouponDialogListener onCancel!");
      SpecialPlayerActivity.this.onDialogCancel();
      SpecialPlayerActivity.this.displayPayTip(true);
      XulView localXulView = SpecialPlayerActivity.this.xulGetFocus();
      if ((localXulView != null) && (localXulView.getId().equals("window")))
        SpecialPlayerActivity.this.xulRequestFocus(SpecialPlayerActivity.this.massiveWrapper.getItemView(SpecialPlayerActivity.this.mPlayIndex));
    }

    public void onSuccess()
    {
      ReportInfo.getInstance().setEntranceSrc("I8");
      SpecialPlayerActivity.this.continuePlay();
    }
  };
  private MplayerEx mplayer = null;
  private MplayerDialog mplayerDialog = null;
  private XulView mplayerView = null;
  private int pageIndex = 0;
  private int pageNum = 1;
  private int pageSize = 300;
  private MplayerPayControl payControl = null;
  private long preViewTime = 0L;
  private XulArea quitView = null;
  private MplayerEx.ScreenMode screenMode = MplayerEx.ScreenMode.HALF_SCREEN;
  public List<SpecialPlayerData> specialList = new ArrayList();
  private XulArea specialSlider = null;
  private XulSliderAreaWrapper specialWrapper = null;
  private TokenDialog tokenDialog = null;
  private SpecialPlayerData.UiStyle uiStyle = SpecialPlayerData.UiStyle.DISPLAY_UI_TEXT;
  private XulView watchFocus = null;

  private void clearItems()
  {
    if (this.massive == null)
      return;
    Logger.i(TAG, "[update]:clearItems");
    this.massive.eachView(new XulArea.XulViewIterator()
    {
      public boolean onXulView(int paramAnonymousInt, XulView paramAnonymousXulView)
      {
        XulDataNode localXulDataNode = (XulDataNode)paramAnonymousXulView.getBindingData().get(0);
        SpecialPlayerActivity.this.updateItem(paramAnonymousXulView, SpecialPlayerDataManeger.getInstance().isChecked(localXulDataNode, SpecialPlayerActivity.this.mPlayContent));
        return super.onXulView(paramAnonymousInt, paramAnonymousXulView);
      }
    });
  }

  private void continuePlay()
  {
    if (this.mplayer == null)
      return;
    this.mplayer.start(this.mPlayContent, this.preViewTime);
  }

  private void delayToWindowFocus()
  {
    xulPostDelay(new Runnable()
    {
      public void run()
      {
        if ((SpecialPlayerActivity.this.isFocusOnWindow()) && (!SpecialPlayerActivity.this.isFullScreen()))
        {
          SpecialPlayerActivity.this.fullTipView.setStyle("display", "block");
          SpecialPlayerActivity.this.fullTipView.resetRender();
        }
      }
    }
    , 300L);
  }

  private void displayPayTip(boolean paramBoolean)
  {
    if (this.mplayer != null)
      this.mplayer.displayPayTip(paramBoolean);
    XulView localXulView;
    if ((this.fullTipView != null) && (isFocusOnWindow()))
    {
      localXulView = this.fullTipView;
      if (!paramBoolean)
        break label58;
    }
    label58: for (String str = "none"; ; str = "block")
    {
      localXulView.setStyle("display", str);
      this.fullTipView.resetRender();
      return;
    }
  }

  private void doPlay(XulView paramXulView)
  {
    String str = paramXulView.getDataString("video_id");
    int i = XulUtils.tryParseInt(paramXulView.getDataString("video_index"));
    int j = XulUtils.tryParseInt(paramXulView.getDataString("index"));
    if (this.mPlayIndex != -1)
      ReportInfo.getInstance().setEntranceSrc("I3");
    if (startPlay(str, i, j, this.mFocusCategoryId))
    {
      SpecialPlayerDataManeger.getInstance().setPlayed(paramXulView);
      paramXulView.findItemById("name").addClass("massive-name-watch");
      paramXulView.findItemById("duration").addClass("massive-duration-watch");
      paramXulView.resetRender();
    }
  }

  private void getSpecialCategory(String paramString)
  {
    Logger.i(TAG, "getSpecialCategory-->" + paramString);
    ServerApiManager.i().APIGetSpecialTopicColumnTreeData(paramString, new SccmsApiGetSpecialTopicColumnTreeTask.ISccmsApiGetSpecialTopicColumnTreeTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        if (SpecialPlayerActivity.this.isFinishing())
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialCategory isFinishing !");
          return;
        }
        Logger.e(SpecialPlayerActivity.TAG, "getSpecialCategory onError" + paramAnonymousServerApiCommonError.toString());
        SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialCategory onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SpecialTopicTreeInfo paramAnonymousSpecialTopicTreeInfo)
      {
        if (SpecialPlayerActivity.this.isFinishing())
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialCategory isFinishing !");
          return;
        }
        if (paramAnonymousSpecialTopicTreeInfo == null)
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialCategory onSuccess result null");
          SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialCategory onSuccess result null", paramAnonymousServerApiTaskInfo, null);
          return;
        }
        Logger.i(SpecialPlayerActivity.TAG, "getSpecialCategory-->onSuccess!");
        SpecialPlayerActivity.this.dismissLoaddingDialog();
        SpecialPlayerActivity.this.onApiFinished(paramAnonymousSpecialTopicTreeInfo);
      }
    });
  }

  private void getSpecialContent(int paramInt)
  {
    ServerApiManager.i().APIGetSpecialTopicPkgContentLstData(this.mSpecialId, paramInt + "", this.pageSize + "", "default", this.isIncludeAll, new SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        if (SpecialPlayerActivity.this.isFinishing())
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialContent isFinishing !");
          return;
        }
        Logger.e(SpecialPlayerActivity.TAG, "getSpecialContent ---->onError");
        SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialContent ---->onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SpecialTopicPkgCntLstInfo paramAnonymousSpecialTopicPkgCntLstInfo)
      {
        if (SpecialPlayerActivity.this.isFinishing())
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialContent isFinishing !");
          return;
        }
        if ((paramAnonymousSpecialTopicPkgCntLstInfo == null) || (paramAnonymousSpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs == null) || (paramAnonymousSpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs.size() == 0))
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialContent---> result is null");
          SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialContent---> result is null", paramAnonymousServerApiTaskInfo, null);
          return;
        }
        SpecialPlayerActivity.this.processResult(paramAnonymousSpecialTopicPkgCntLstInfo);
      }
    });
  }

  private void getSpecialInfo(final String paramString)
  {
    ServerApiManager.i().APIGetSpecialTopicPutData(paramString, null, "asset", new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        if (SpecialPlayerActivity.this.isFinishing())
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialInfo isFinishing !");
          return;
        }
        Logger.e(SpecialPlayerActivity.TAG, "getSpecialInfo ---->onError");
        SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialInfo ---->onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
      {
        if (SpecialPlayerActivity.this.isFinishing())
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialInfo isFinishing !");
          return;
        }
        if ((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0))
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialInfo ---->onSuccess result null");
          SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialInfo ---->onSuccess result null", paramAnonymousServerApiTaskInfo, null);
          return;
        }
        Iterator localIterator = paramAnonymousArrayList.iterator();
        SpecialTopicPutInfo localSpecialTopicPutInfo;
        do
        {
          boolean bool = localIterator.hasNext();
          localObject = null;
          if (!bool)
            break;
          localSpecialTopicPutInfo = (SpecialTopicPutInfo)localIterator.next();
        }
        while (!paramString.equals(localSpecialTopicPutInfo.binding_id));
        Object localObject = localSpecialTopicPutInfo;
        if (localObject == null)
        {
          Logger.e(SpecialPlayerActivity.TAG, "getSpecialInfo ---->specialTopicInfo null!");
          SpecialPlayerActivity.this.showErrorDialog("1002007", "getSpecialInfo ---->specialTopicInfo null!", paramAnonymousServerApiTaskInfo, null);
          return;
        }
        SpecialPlayerActivity.access$2102(SpecialPlayerActivity.this, localObject.id);
        SpecialPlayerActivity.this.setSpecialBkg(localObject.poster);
        SpecialPlayerActivity.this.getSpecialContent(SpecialPlayerActivity.this.pageIndex);
      }
    });
  }

  private void hideFocusItem()
  {
    this.focusItem.setStyle("display", "none");
    this.focusItem.resetRender();
  }

  private void initSpecialList(SpecialTopicTreeInfo paramSpecialTopicTreeInfo)
  {
    if ((paramSpecialTopicTreeInfo != null) && (paramSpecialTopicTreeInfo.specialTpcTreeCatge != null) && (paramSpecialTopicTreeInfo.specialTpcTreeCatge.size() > 0))
    {
      Iterator localIterator2 = paramSpecialTopicTreeInfo.specialTpcTreeCatge.iterator();
      while (localIterator2.hasNext())
      {
        SpecialTopicTreeCategory localSpecialTopicTreeCategory = (SpecialTopicTreeCategory)localIterator2.next();
        if (!TextUtils.isEmpty(localSpecialTopicTreeCategory.id))
        {
          SpecialPlayerData localSpecialPlayerData2 = new SpecialPlayerData();
          localSpecialPlayerData2.specialCategoryId = localSpecialTopicTreeCategory.id;
          localSpecialPlayerData2.specialName = localSpecialTopicTreeCategory.name;
          localSpecialPlayerData2.specialId = this.mSpecialId;
          localSpecialPlayerData2.uiStyle = this.uiStyle;
          localSpecialPlayerData2.isDisplayNum = this.isDisplayNum;
          localSpecialPlayerData2.info = new ArrayList();
          int j = 0;
          Iterator localIterator3 = this.mSpecialSrcList.iterator();
          while (localIterator3.hasNext())
          {
            SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct2 = (SpecialTopicPkgCntLstStruct)localIterator3.next();
            if (localSpecialTopicTreeCategory.id.equals(localSpecialTopicPkgCntLstStruct2.category_id))
            {
              SpecialCategoryContent localSpecialCategoryContent2 = SpecialPlayerDataManeger.getInstance().buildSpecialContent(localSpecialTopicPkgCntLstStruct2);
              localSpecialCategoryContent2.special_category_id = localSpecialPlayerData2.specialCategoryId;
              localSpecialCategoryContent2.special_id = this.mSpecialId;
              localSpecialCategoryContent2.index = j;
              localSpecialPlayerData2.info.add(localSpecialCategoryContent2);
              j++;
            }
          }
          if (localSpecialPlayerData2.info.size() > 0)
            this.specialList.add(localSpecialPlayerData2);
        }
      }
      setSpecialYPos();
      return;
    }
    SpecialPlayerData localSpecialPlayerData1 = new SpecialPlayerData();
    localSpecialPlayerData1.specialCategoryId = "ALL";
    localSpecialPlayerData1.specialId = this.mSpecialId;
    localSpecialPlayerData1.uiStyle = this.uiStyle;
    localSpecialPlayerData1.isDisplayNum = this.isDisplayNum;
    localSpecialPlayerData1.specialName = "";
    localSpecialPlayerData1.info = new ArrayList();
    int i = 0;
    Iterator localIterator1 = this.mSpecialSrcList.iterator();
    while (localIterator1.hasNext())
    {
      SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct1 = (SpecialTopicPkgCntLstStruct)localIterator1.next();
      SpecialCategoryContent localSpecialCategoryContent1 = SpecialPlayerDataManeger.getInstance().buildSpecialContent(localSpecialTopicPkgCntLstStruct1);
      localSpecialCategoryContent1.special_category_id = localSpecialPlayerData1.specialCategoryId;
      localSpecialCategoryContent1.special_id = this.mSpecialId;
      localSpecialCategoryContent1.index = i;
      localSpecialPlayerData1.info.add(localSpecialCategoryContent1);
      i++;
    }
    this.specialList.add(localSpecialPlayerData1);
  }

  private void initSpecialView()
  {
    XulView localXulView1 = xulFindViewById("component_image");
    XulView localXulView2 = xulFindViewById("component_text");
    if ((localXulView1 == null) || (localXulView2 == null))
    {
      Logger.e(TAG, "initSpecialView null!");
      return;
    }
    if (this.uiStyle == SpecialPlayerData.UiStyle.DISPLAY_UI_IMAGE)
    {
      localXulView2.setStyle("display", "none");
      localXulView2.resetRender();
    }
    for (this.massive = ((XulArea)localXulView1.findItemById("massive")); ; this.massive = ((XulArea)localXulView2.findItemById("massive")))
    {
      this.massiveWrapper = XulMassiveAreaWrapper.fromXulView(this.massive);
      return;
      localXulView1.setStyle("display", "none");
      localXulView1.resetRender();
    }
  }

  private void initTitle(String paramString)
  {
    XulView localXulView = xulFindViewById("title");
    if (localXulView != null)
    {
      localXulView.setAttr("text", paramString);
      localXulView.resetRender();
    }
  }

  private void initUiStyle(SpecialTopicTreeInfo paramSpecialTopicTreeInfo)
  {
    if (paramSpecialTopicTreeInfo != null)
    {
      if (paramSpecialTopicTreeInfo.display_num.equals("1"))
        this.isDisplayNum = true;
      if (paramSpecialTopicTreeInfo.index_ui.equals("x2"))
        this.uiStyle = SpecialPlayerData.UiStyle.DISPLAY_UI_IMAGE;
    }
  }

  private void initView()
  {
    this.mplayerView = xulFindViewById("MplayerEx");
    this.quitView = ((XulArea)xulFindViewById("quit_tips"));
    this.specialSlider = ((XulArea)xulFindViewById("special_list"));
    this.specialWrapper = XulSliderAreaWrapper.fromXulView(this.specialSlider);
    this.checkItem = xulFindViewById("@checked-item-special");
    this.focusItem = xulFindViewById("@focus-item-special");
    this.focusCategoryItem = xulFindViewById("@focus-item-category");
    this.watchFocus = xulFindViewById("watch_desc");
    this.categorySlider = ((XulArea)xulFindViewById("category_list"));
    this.categoryWrapper = XulSliderAreaWrapper.fromXulView(this.categorySlider);
    this.mLeftContent = ((XulArea)xulFindViewById("left-content"));
    this.mRightContent = ((XulArea)xulFindViewById("right-content"));
    this.fullTipView = xulFindViewById("fullscreen-tip");
    this.mplayer = ((MplayerEx)xulFindViewById("MplayerEx").getExternalView());
    this.mplayer.setMPlayerOutListener(new MplayerListener(null));
    this.mplayer.setOnScreenModeChangeListener(new MplayerEx.OnScreenModeChangeListener()
    {
      public void onScreenModeChange(MplayerEx.ScreenMode paramAnonymousScreenMode)
      {
        SpecialPlayerActivity.access$1502(SpecialPlayerActivity.this, paramAnonymousScreenMode);
        SpecialPlayerActivity localSpecialPlayerActivity = SpecialPlayerActivity.this;
        if (paramAnonymousScreenMode == MplayerEx.ScreenMode.FULL_SCREEN);
        for (boolean bool = true; ; bool = false)
        {
          localSpecialPlayerActivity.onScreenModeChange(bool);
          return;
        }
      }
    });
  }

  private boolean isChangeCategory(SpecialCategoryContent paramSpecialCategoryContent)
  {
    return !paramSpecialCategoryContent.special_category_id.equals(this.mFocusCategoryId);
  }

  private boolean isChangeVideo(SpecialCategoryContent paramSpecialCategoryContent)
  {
    return (!paramSpecialCategoryContent.video_id.equals(this.mPlayContent.video_id)) || (paramSpecialCategoryContent.video_index != this.mPlayContent.video_index);
  }

  private boolean isFocusOnCategory()
  {
    XulView localXulView = xulGetFocus();
    if (localXulView != null)
    {
      XulArea localXulArea = localXulView.findParentByType("slider");
      if ((localXulArea != null) && ("category_list".equals(localXulArea.getId())))
        return true;
    }
    return false;
  }

  private boolean isFocusOnFirstCategory()
  {
    XulView localXulView = xulGetFocus();
    if (localXulView != null)
    {
      XulArea localXulArea = localXulView.findParentByType("slider");
      if (localXulArea != null)
      {
        Logger.i(TAG, "slider.getId:" + localXulArea.getId());
        if (("category_list".equals(localXulArea.getId())) && ("0".equals(localXulView.getDataString("idx"))))
          return true;
      }
    }
    return false;
  }

  private boolean isFocusOnWindow()
  {
    XulView localXulView = xulGetFocus();
    return (localXulView != null) && ("window".equals(localXulView.getId()));
  }

  private boolean isFullScreen()
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder().append("isFullScreen ");
    if (this.screenMode == MplayerEx.ScreenMode.FULL_SCREEN);
    for (boolean bool = true; ; bool = false)
    {
      Logger.i(str, bool);
      if (this.screenMode != MplayerEx.ScreenMode.FULL_SCREEN)
        break;
      return true;
    }
    return false;
  }

  private boolean isPayTipsVisible()
  {
    if (this.mplayer != null)
      return this.mplayer.isPayTipVisible();
    return false;
  }

  private void loadData()
  {
    this.mSpecialBindingId = getIntent().getStringExtra("special_binding_id");
    if (!TextUtils.isEmpty(this.mSpecialBindingId))
    {
      getSpecialInfo(this.mSpecialBindingId);
      return;
    }
    this.mSpecialId = getIntent().getStringExtra("special_id");
    if (TextUtils.isEmpty(this.mSpecialId))
    {
      showErrorDialog("1002007", "loadData ---->packet_id is null");
      return;
    }
    setSpecialBkg(getIntent().getStringExtra("poster"));
    getSpecialContent(this.pageIndex);
  }

  private void onApiFinished(SpecialTopicTreeInfo paramSpecialTopicTreeInfo)
  {
    initUiStyle(paramSpecialTopicTreeInfo);
    initTitle(paramSpecialTopicTreeInfo.name);
    initSpecialView();
    initSpecialList(paramSpecialTopicTreeInfo);
    if ((this.specialList == null) || (this.specialList.size() == 0))
      showErrorDialog("1002007", "getSpecialCategory onSuccess specialList null", null, null);
    do
    {
      do
      {
        return;
        this.mIsLoadSuccess = true;
        reportPageLoad(true);
        if (this.mplayer != null)
        {
          this.mplayer.bindData(this.specialList);
          SpecialPlayerDataManeger.getInstance().setSpecialPlayerDataList(this.specialList);
        }
        if ("ALL".equals(((SpecialPlayerData)this.specialList.get(0)).specialCategoryId))
          this.hasCategory = false;
        if ((xulIsReady()) && (this.hasCategory))
          xulRefreshBinding("categroy-data", "file:///.app/info/category_data");
        this.mPlayContent = SpecialPlayerDataManeger.getInstance().getFirstPlay(((SpecialPlayerData)this.specialList.get(0)).info);
      }
      while (this.mPlayContent == null);
      Logger.i(TAG, "first play video-->" + this.mPlayContent.video_name);
      this.mFocusCategoryId = this.mPlayContent.special_category_id;
      this.mPlayCategoryId = this.mPlayContent.special_category_id;
      updateSpecialView();
      updateItems(true);
      this.mPlayIndex = this.mPlayContent.index;
    }
    while (this.mplayer == null);
    this.mplayer.start(this.mPlayContent);
  }

  private void onDialogCancel()
  {
    if ((isFullScreen()) && (this.mplayer != null))
      this.mplayer.changeScreenMode(MplayerEx.ScreenMode.HALF_SCREEN);
  }

  private void onScreenModeChange(boolean paramBoolean)
  {
    boolean bool1 = true;
    Logger.i(TAG, "onScreenModeChange:" + paramBoolean);
    updatePageName(paramBoolean);
    String str4;
    String str3;
    label94: String str2;
    label133: boolean bool2;
    if (this.mLeftContent != null)
    {
      XulArea localXulArea3 = this.mLeftContent;
      if (paramBoolean)
      {
        str4 = "none";
        localXulArea3.setStyle("display", str4);
        this.mLeftContent.resetRender();
      }
    }
    else
    {
      if (this.specialSlider != null)
      {
        XulArea localXulArea2 = this.specialSlider;
        if (!paramBoolean)
          break label268;
        str3 = "none";
        localXulArea2.setStyle("display", str3);
        this.specialSlider.resetRender();
      }
      if (this.categorySlider != null)
      {
        XulArea localXulArea1 = this.categorySlider;
        if (!paramBoolean)
          break label276;
        str2 = "none";
        localXulArea1.setStyle("display", str2);
        this.categorySlider.resetRender();
      }
      if (!isPayTipsVisible())
        break label290;
      if (paramBoolean)
        break label284;
      bool2 = bool1;
      label164: displayPayTip(bool2);
      label170: if (this.mplayerView != null)
      {
        if (!paramBoolean)
          break label338;
        setQuitTipsDisplay(false);
        this.mplayerView.addClass("window-size-full");
        this.mplayerView.removeClass("window-size-small");
        label208: this.mplayerView.resetRender();
      }
      if (paramBoolean)
        break label386;
      if (!isChangeCategory(this.mPlayContent))
        break label368;
      setCurrentPlayContent(this.mPlayContent);
      updateCategoryView(false);
      updateSpecialView();
      if (isFocusOnWindow())
        break label363;
      updateItems(bool1);
    }
    label268: label276: label284: label290: label338: label363: label368: 
    while (this.focusItem == null)
    {
      while (true)
      {
        return;
        str4 = "block";
        break;
        str3 = "block";
        break label94;
        str2 = "block";
        break label133;
        bool2 = false;
        break label164;
        if (!isFocusOnWindow())
          break label170;
        XulView localXulView = this.fullTipView;
        if (paramBoolean);
        for (String str1 = "none"; ; str1 = "block")
        {
          localXulView.setStyle("display", str1);
          this.fullTipView.resetRender();
          break;
        }
        this.mplayerView.addClass("window-size-small");
        this.mplayerView.removeClass("window-size-full");
        break label208;
        bool1 = false;
      }
      if (!isFocusOnWindow());
      while (true)
      {
        updateItems(bool1);
        return;
        bool1 = false;
      }
    }
    label386: this.focusItem.setStyle("display", "none");
    this.focusItem.resetRender();
  }

  private String printContent()
  {
    if (this.mPlayContent == null)
      return "null";
    return this.mPlayContent.video_name + "," + this.mPlayContent.video_id + "," + this.mPlayContent.video_index + "," + this.mPlayContent.special_category_id;
  }

  private void processAuthFail(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt)
  {
    if (paramInt == 1)
    {
      if (isCanUseCoupon(paramVideoInfo))
      {
        paramVideoInfo.is_trylook = "0";
        this.payControl.useMovieCoupon(paramSpecialCategoryContent, paramVideoInfo, 0L, this.movieCouponDialogListener);
        this.dialogMessage = this.payControl.dialogMessage;
        return;
      }
      showBuyDialog(paramSpecialCategoryContent, paramVideoInfo, this.buyDialogListener);
      return;
    }
    if ((paramInt == -101) || (paramInt == -100))
    {
      this.tokenDialog.setListener(this.buyDialogListener);
      if (paramInt == -101)
        this.tokenDialog.setType(20000);
      while (true)
      {
        this.tokenDialog.setIsNeedQuit(false);
        this.tokenDialog.show();
        this.dialogMessage = this.tokenDialog.getDialogMsg();
        return;
        this.tokenDialog.setType(20001);
      }
    }
    this.mplayerDialog.setListener(this.errorDialogListener);
    this.mplayerDialog.setType(paramInt);
    this.mplayerDialog.show();
    this.dialogMessage = this.mplayerDialog.getDialogMsg();
  }

  private boolean processBackKey()
  {
    if (this.lastBackPressedTime == 0L)
    {
      setQuitTipsDisplay(true);
      this.lastBackPressedTime = XulUtils.timestamp();
      return true;
    }
    this.lastBackPressedTime = 0L;
    return false;
  }

  private void processIntent()
  {
    Intent localIntent = getIntent();
    if (TextUtils.isEmpty(localIntent.getStringExtra("cmd_is_from_out")))
    {
      if (localIntent.getBooleanExtra("flag_action_from_mgtv", false))
        cid = "webview";
      ref = "apk";
      return;
    }
    if (localIntent.getBooleanExtra("isFromWeiXin", false))
    {
      ref = "IMGO";
      cid = "wechat";
      return;
    }
    cid = "factory";
    ref = Tools.getOutPlayOriginal() + "-2";
  }

  private boolean processLeftKey()
  {
    if (isPayTipsVisible())
    {
      XulView localXulView = xulGetFocus();
      if (localXulView != null)
      {
        XulArea localXulArea = localXulView.findParentByType("slider");
        Logger.i(TAG, "slider.getId:" + localXulArea.getId());
        if (localXulArea != null)
        {
          if ("special_list".equals(localXulArea.getId()));
          while (("category_list".equals(localXulArea.getId())) && ("0".equals(localXulView.getDataString("idx"))))
            return true;
        }
      }
    }
    return false;
  }

  private void processResult(SpecialTopicPkgCntLstInfo paramSpecialTopicPkgCntLstInfo)
  {
    int i = XulUtils.tryParseInt(paramSpecialTopicPkgCntLstInfo.count_num);
    if (i <= 0)
    {
      Logger.e(TAG, "getSpecialContent---> count <= 0");
      showErrorDialog("1002007", "getSpecialContent---> count <= 0");
      return;
    }
    if (i < this.pageSize);
    for (this.pageNum = 1; ; this.pageNum = ((int)Math.ceil(i / this.pageSize)))
    {
      this.mSpecialSrcList.addAll(paramSpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs);
      if (this.pageIndex < -1 + this.pageNum)
        break;
      getSpecialCategory(this.mSpecialId);
      return;
    }
    this.pageIndex = (1 + this.pageIndex);
    getSpecialContent(this.pageIndex);
  }

  private boolean processUserCmd(XulView paramXulView, String paramString)
  {
    String str1;
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      str1 = localJSONObject.getString("cmd");
      if ("m_switch_category".equals(str1))
      {
        String str2 = localJSONObject.getString("category_id");
        if (!TextUtils.isEmpty(str2))
        {
          this.mFocusCategoryId = str2;
          this.mHandler.sendEmptyMessageDelayed(17, 300L);
        }
      }
      else
      {
        if (isPayTipsVisible())
          displayPayTip(false);
        if ("m_open_player".equals(str1))
          doPlay(paramXulView);
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return false;
    }
    hideFocusItem();
    if ("m_open_tv_player".equals(str1))
      SpecialPlayerDataManeger.getInstance().goToLivePlayer(this, paramXulView);
    if ("m_open_detail_page".equals(str1))
      SpecialPlayerDataManeger.getInstance().goToDetail(this, paramXulView);
    else if ("m_open_special_page".equals(str1))
      SpecialPlayerDataManeger.getInstance().goToSpecial(this, paramXulView);
    else if ("m_open_web".equals(str1))
      SpecialPlayerDataManeger.getInstance().goToWeb(this, paramXulView);
    return true;
  }

  private void reportBuy()
  {
    VodPayInfo localVodPayInfo = new VodPayInfo();
    VideoIndex localVideoIndex = GlobalLogic.getInstance().getCurrentVideoIndex(this.mVideoInfo.indexList, this.mPlayContent.video_index);
    if (localVideoIndex != null)
    {
      localVodPayInfo.vid = localVideoIndex.id;
      localVodPayInfo.ovid = localVideoIndex.import_id;
    }
    if (this.mVideoInfo != null)
    {
      localVodPayInfo.plid = this.mVideoInfo.videoId;
      localVodPayInfo.oplid = this.mVideoInfo.import_id;
      localVodPayInfo.def = this.mVideoInfo.definition;
      localVodPayInfo.cid = this.mVideoInfo.packageId;
    }
    while (true)
    {
      PayReportHelper.reportBuy(this.curPageInfo.page, this.lastPageInfo.page, localVodPayInfo, null);
      return;
      if (this.mPlayContent != null)
      {
        localVodPayInfo.plid = this.mPlayContent.video_id;
        localVodPayInfo.oplid = this.mPlayContent.import_id;
        localVodPayInfo.def = GlobalLogic.getInstance().getQuality();
        localVodPayInfo.cid = this.mPlayContent.package_id;
      }
    }
  }

  private void reportPageExit()
  {
    reportExit(this.isLoadSuccess, null);
  }

  private void reportPageLoad(boolean paramBoolean)
  {
    this.isLoadSuccess = paramBoolean;
    reportLoad(paramBoolean, null);
  }

  private void setCheckItemPos(XulView paramXulView, int paramInt)
  {
    setItemPos(this.checkItem, paramXulView, paramInt);
    this.checkItem.setStyle("display", "block");
    this.checkItem.resetRender();
  }

  private void setCurrentPlayContent(SpecialCategoryContent paramSpecialCategoryContent)
  {
    this.mPlayContent = paramSpecialCategoryContent;
    this.mPlayCategoryId = paramSpecialCategoryContent.special_category_id;
    this.mFocusCategoryId = this.mPlayCategoryId;
    this.mPlayIndex = paramSpecialCategoryContent.index;
  }

  private void setFocusItemPos(XulView paramXulView, int paramInt)
  {
    setItemPos(this.focusItem, paramXulView, paramInt);
    this.focusItem.setStyle("display", "block");
    this.focusItem.resetRender();
  }

  private void setFontFocus()
  {
    XulView localXulView1 = xulGetFocus();
    if (localXulView1 != null)
    {
      XulView localXulView2 = localXulView1.findItemById("name");
      XulView localXulView3 = localXulView1.findItemById("duration");
      if (localXulView2 != null)
      {
        localXulView2.addClass("massive-name-focus");
        localXulView2.resetRender();
      }
      if (localXulView3 != null)
      {
        localXulView3.addClass("massive-duration-focus");
        localXulView3.resetRender();
      }
    }
  }

  private void setItemPos(XulView paramXulView1, XulView paramXulView2, int paramInt)
  {
    if ((paramXulView2 == null) || (paramXulView1 == null))
      return;
    try
    {
      XulViewRender localXulViewRender = paramXulView2.getRender();
      double d = localXulViewRender.getYScalar();
      int i = XulUtils.roundToInt(this.specialWrapper.getScrollPos() / d) + XulUtils.roundToInt(this.specialWrapper.getScrollDelta() / d);
      int j = XulUtils.roundToInt(this.massiveWrapper.getItemRect(paramInt).top / d);
      int k = XulUtils.tryParseInt(this.specialSlider.getAttrString("y"));
      int m = i + j - k;
      if (m < 0)
        m = 0;
      paramXulView1.setAttr("y", String.valueOf(m));
      paramXulView1.setAttr("height", String.valueOf((int)(localXulViewRender.getHeight() / d)));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void setQuitTipsDisplay(boolean paramBoolean)
  {
    if (paramBoolean)
      this.mHandler.sendEmptyMessageDelayed(16, 2000L);
    XulArea localXulArea = this.quitView;
    if (paramBoolean);
    for (String str = "block"; ; str = "none")
    {
      localXulArea.setStyle("display", str);
      this.quitView.resetRender();
      return;
    }
  }

  private void setSpecialBkg(String paramString)
  {
    Logger.i(TAG, "setSpecialBkg url:" + paramString);
    if (TextUtils.isEmpty(paramString))
      return;
    XulView localXulView = xulFindViewById("background_image");
    if (localXulView != null)
    {
      localXulView.setStyle("background-image", paramString);
      localXulView.resetRender();
      return;
    }
    Logger.e("setSpecialBkg view is null");
  }

  private void setSpecialYPos()
  {
    if (this.specialSlider == null)
      return;
    this.specialSlider.setAttr("y", "226");
    this.specialSlider.resetRender();
  }

  private void showErrorDialog(String paramString1, String paramString2)
  {
    showErrorDialog(paramString1, paramString2, null, null);
  }

  private void showErrorDialog(String paramString1, String paramString2, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    this.mIsLoadSuccess = false;
    reportPageLoad(false);
    dismissLoaddingDialog();
    showErrorDialogWithReport(11, paramString1, paramString2, paramServerApiTaskInfo, paramServerApiCommonError);
  }

  private boolean startPlay(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    this.preViewTime = 0L;
    this.mPlayIndex = paramInt2;
    this.mPlayCategoryId = this.mFocusCategoryId;
    SpecialCategoryContent localSpecialCategoryContent = SpecialPlayerDataManeger.getInstance().getContentItem(this.specialList, paramString1, paramInt1, paramString2);
    if ((localSpecialCategoryContent == null) || (this.mplayer == null))
      return false;
    this.mPlayContent = localSpecialCategoryContent;
    this.mplayer.start(this.mPlayContent);
    return true;
  }

  private void updateCategoryView(boolean paramBoolean)
  {
    if (!this.hasCategory)
      return;
    int i = this.categorySlider.getChildNum();
    int j = 0;
    label18: XulView localXulView;
    if (j < i)
    {
      localXulView = this.categorySlider.getChild(j);
      if ((localXulView != null) && (!TextUtils.isEmpty(localXulView.getDataString("category_id"))))
        break label58;
    }
    while (true)
    {
      j++;
      break label18;
      break;
      label58: if (this.mPlayCategoryId.equals(localXulView.getDataString("category_id")))
      {
        Logger.i(TAG, "[update]--->addClass category:" + printContent());
        localXulView.addClass("category-item-checked");
        localXulView.addClass("category_group_item_checked");
        localXulView.resetRender();
        if (paramBoolean)
          this.focusCategoryItem.setStyle("display", "block");
        while (true)
        {
          this.categorySlider.setDynamicFocus(localXulView);
          this.focusCategoryItem.resetRender();
          this.categoryWrapper.makeChildVisible(localXulView, 0.5F, false);
          break;
          this.focusCategoryItem.setStyle("display", "none");
        }
      }
      localXulView.removeClass("category-item-checked");
      localXulView.removeClass("category_group_item_checked");
      localXulView.resetRender();
    }
  }

  private void updateItem(XulView paramXulView, boolean paramBoolean)
  {
    if (!(paramXulView instanceof XulArea))
      return;
    XulView localXulView1 = paramXulView.findItemById("name");
    XulView localXulView2 = paramXulView.findItemById("duration");
    XulView localXulView3 = paramXulView.findItemById("isplaying");
    if (SpecialPlayerDataManeger.getInstance().isPlayed(paramXulView))
    {
      localXulView1.addClass("massive-name-watch");
      localXulView2.addClass("massive-duration-watch");
      if (!paramBoolean)
        break label221;
      Logger.i(TAG, "[update]--->check:" + printContent());
      paramXulView.addClass("list_group_item_checked");
      localXulView3.setStyle("display", "block");
      localXulView1.addClass("massive-name-checked");
      localXulView2.addClass("massive-name-checked");
      if ((this.watchFocus != null) && (paramXulView.getBindingData().get(0) != null))
      {
        this.watchFocus.setAttr("text", ((XulDataNode)paramXulView.getBindingData().get(0)).getAttributeValue("watch_focus"));
        this.watchFocus.resetRender();
      }
    }
    while (true)
    {
      localXulView1.resetRender();
      localXulView2.resetRender();
      localXulView3.resetRender();
      paramXulView.resetRender();
      return;
      localXulView1.removeClass("massive-name-watch");
      localXulView2.removeClass("massive-duration-watch");
      break;
      label221: localXulView1.removeClass("massive-name-checked");
      localXulView1.removeClass("massive-name-focus");
      localXulView2.removeClass("massive-name-checked");
      localXulView2.removeClass("massive-name-focus");
      localXulView3.setStyle("display", "none");
      paramXulView.removeClass("list_group_item_checked");
    }
  }

  private void updateItems(final boolean paramBoolean)
  {
    if ((this.massive == null) || (this.massiveWrapper == null))
      return;
    this.massive.getRender().getRenderContext().scheduleLayoutFinishedTask(new Runnable()
    {
      public void run()
      {
        while (true)
        {
          int j;
          try
          {
            int i = SpecialPlayerActivity.this.massiveWrapper.itemNum();
            j = 0;
            if (j < i)
            {
              XulDataNode localXulDataNode = SpecialPlayerActivity.this.massiveWrapper.getItem(j);
              if (localXulDataNode == null)
                break label121;
              if (!SpecialPlayerDataManeger.getInstance().isChecked(localXulDataNode, SpecialPlayerActivity.this.mPlayContent))
                break label102;
              final int k = j;
              SpecialPlayerActivity.this.massiveWrapper.makeChildVisible(SpecialPlayerActivity.this.specialSlider, j, 0.5F, 0.0F, false, new Runnable()
              {
                public void run()
                {
                  XulView localXulView = SpecialPlayerActivity.this.massiveWrapper.getItemView(k);
                  SpecialPlayerActivity.this.setCheckItemPos(localXulView, k);
                  SpecialPlayerDataManeger.getInstance().setPlayed(localXulView);
                  SpecialPlayerDataManeger.getInstance().setPlayed(localXulView);
                  SpecialPlayerActivity.this.updateItem(localXulView, true);
                  SpecialPlayerActivity.this.specialSlider.setDynamicFocus(localXulView);
                  SpecialPlayerActivity.this.mRightContent.setDynamicFocus(localXulView);
                  if (SpecialPlayerActivity.11.this.val$isSetFocusPos)
                  {
                    Logger.i(SpecialPlayerActivity.TAG, "[update]--->focus:" + SpecialPlayerActivity.this.printContent());
                    SpecialPlayerActivity.this.setFocusItemPos(localXulView, k);
                    SpecialPlayerActivity.this.xulRequestFocus(localXulView);
                    SpecialPlayerActivity.this.specialWrapper.makeChildVisible(localXulView, false);
                  }
                }
              });
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
          return;
          label102: SpecialPlayerActivity.this.updateItem(SpecialPlayerActivity.this.massiveWrapper.getItemView(j), false);
          label121: j++;
        }
      }
    });
    this.massive.getRender().getRenderContext().doLayout();
  }

  private void updatePageName(boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = "MH_F"; ; str = "MH")
    {
      setCurPageName(str);
      return;
    }
  }

  private void updateSpecialView()
  {
    if ((TextUtils.isEmpty(this.mFocusCategoryId)) || (this.massiveWrapper == null))
      return;
    SpecialPlayerDataManeger.getInstance().resetMassiveList(this.massiveWrapper);
    if ((this.specialList != null) && (this.specialList.size() > 0))
    {
      Iterator localIterator = this.specialList.iterator();
      while (localIterator.hasNext())
      {
        SpecialPlayerData localSpecialPlayerData = (SpecialPlayerData)localIterator.next();
        if (localSpecialPlayerData.specialCategoryId.equals(this.mFocusCategoryId))
          SpecialPlayerDataManeger.getInstance().addItem(localSpecialPlayerData.isDisplayNum, localSpecialPlayerData.info, this.massiveWrapper);
      }
    }
    Logger.i(TAG, "[update]--->updateSpecialView size:" + this.massiveWrapper.itemNum());
    this.massiveWrapper.syncContentView();
  }

  public void clearDialogMessage()
  {
    this.dialogMessage = "";
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if ((this.mplayer != null) && (this.mplayer.isFullScreen()))
      bool = this.mplayer.dispatchKeyEvent(paramKeyEvent);
    do
    {
      return bool;
      if ((paramKeyEvent.getAction() != 0) || (4 != paramKeyEvent.getKeyCode()))
        break;
    }
    while (processBackKey());
    do
    {
      do
        return super.dispatchKeyEvent(paramKeyEvent);
      while ((paramKeyEvent.getAction() != 0) || (21 != paramKeyEvent.getKeyCode()));
      if ((paramKeyEvent.getRepeatCount() > 0) && (isFocusOnFirstCategory()))
        break;
    }
    while (!processLeftKey());
    return bool;
  }

  public String getDialogMessage()
  {
    return this.dialogMessage;
  }

  public boolean isCanUseCoupon(VideoInfo paramVideoInfo)
  {
    int i = paramVideoInfo.coupon_count;
    int j = paramVideoInfo.asset_type;
    Logger.i(TAG, "isCanUseCoupon: " + i + ",type:" + j);
    return (GlobalLogic.getInstance().isCanUseMovieCoupon(i, j)) && ((GlobalLogic.getInstance().isVip()) || (i > 0));
  }

  protected void onCreate(Bundle paramBundle)
  {
    Logger.d(TAG, "onCreate");
    super.onCreate(paramBundle);
    showLoaddingDialog();
    initXul("SpecialPlayer", true);
    this.payControl = new MplayerPayControl(this);
    this.tokenDialog = new TokenDialog(this);
    this.mplayerDialog = new MplayerDialog(this);
    processIntent();
  }

  protected void onDestroy()
  {
    super.onDestroy();
    SpecialPlayerDataManeger.getInstance().clearSpecialPlayerDataList();
    Logger.d(TAG, "onDestroy");
  }

  protected void onPause()
  {
    super.onPause();
    Logger.d(TAG, "onPause");
  }

  protected void onRestart()
  {
    boolean bool = true;
    String str = ReportInfo.getInstance().getEntranceSrc();
    super.onRestart();
    Logger.d(TAG, "onRestart");
    if ((isFullScreen()) && (this.mplayer != null))
      this.mplayer.setHalfScreen();
    reportPageLoad(this.mIsLoadSuccess);
    if (isPayTipsVisible())
      displayPayTip(false);
    if ((this.mPlayContent != null) && (isChangeCategory(this.mPlayContent)))
    {
      updateCategoryView(false);
      updateSpecialView();
      if (!isFocusOnWindow())
      {
        updateItems(bool);
        if ((this.mPlayContent == null) || (!this.mPlayContent.video_id.equals(GlobalLogic.getInstance().getLoginedVideoId())))
          break label175;
        ReportInfo.getInstance().setEntranceSrc("I7");
      }
    }
    while (true)
    {
      GlobalLogic.getInstance().setLoginedVideoId("");
      continuePlay();
      return;
      bool = false;
      break;
      if (!isFocusOnWindow());
      while (true)
      {
        updateItems(bool);
        break;
        bool = false;
      }
      label175: if ("I8".equals(str))
        ReportInfo.getInstance().setEntranceSrc(str);
      else
        ReportInfo.getInstance().setEntranceSrc("I3");
    }
  }

  protected void onStart()
  {
    super.onStart();
    Logger.d(TAG, "onStart");
    updatePageName(isFullScreen());
  }

  protected void onStop()
  {
    Logger.d(TAG, "onStop");
    reportPageExit();
    if (this.mplayer != null)
      this.mplayer.stop();
    if ((this.mPlayContent != null) && (isChangeCategory(this.mPlayContent)))
    {
      Logger.i(TAG, "[update]: onStop!");
      setCurrentPlayContent(this.mPlayContent);
      updateCategoryView(false);
      updateSpecialView();
    }
    ReportInfo.getInstance().setEntranceSrc("I3");
    super.onStop();
  }

  public void showBuyDialog(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, TokenDialog.TokenDialogListener paramTokenDialogListener)
  {
    PurchaseParam localPurchaseParam = new PurchaseParam(true, paramVideoInfo.videoId, String.valueOf(paramVideoInfo.videoType));
    localPurchaseParam.setImport_id(paramSpecialCategoryContent.import_id);
    localPurchaseParam.setIndex(paramSpecialCategoryContent.video_index);
    localPurchaseParam.setVideoName(paramVideoInfo.name);
    localPurchaseParam.setPackageId(paramVideoInfo.packageId);
    localPurchaseParam.setCategoryId(paramVideoInfo.categoryId);
    localPurchaseParam.specialId = paramSpecialCategoryContent.special_id;
    VideoIndex localVideoIndex = GlobalLogic.getInstance().getCurrentVideoIndex(paramVideoInfo.indexList, this.mPlayContent.video_index);
    if (localVideoIndex != null)
      localPurchaseParam.index_import_id = localVideoIndex.import_id;
    localPurchaseParam.def = paramVideoInfo.definition;
    this.tokenDialog.setType(20003);
    this.tokenDialog.setPurchaseInfo(localPurchaseParam);
    this.tokenDialog.setIsNeedQuit(true);
    this.tokenDialog.setListener(paramTokenDialogListener);
    this.tokenDialog.show();
    this.dialogMessage = this.tokenDialog.getDialogMsg();
  }

  public void showPreViewDialog(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, long paramLong)
  {
    PurchaseParam localPurchaseParam = new PurchaseParam(true, paramVideoInfo.videoId, String.valueOf(paramVideoInfo.videoType));
    localPurchaseParam.setImport_id(paramSpecialCategoryContent.import_id);
    localPurchaseParam.setIndex(paramSpecialCategoryContent.video_index);
    localPurchaseParam.setVideoName(paramVideoInfo.name);
    localPurchaseParam.setVideoPlayedTime(paramLong);
    localPurchaseParam.setPackageId(paramVideoInfo.packageId);
    localPurchaseParam.setCategoryId(paramVideoInfo.categoryId);
    localPurchaseParam.specialId = paramSpecialCategoryContent.special_id;
    VideoIndex localVideoIndex = GlobalLogic.getInstance().getCurrentVideoIndex(paramVideoInfo.indexList, this.mPlayContent.video_index);
    if (localVideoIndex != null)
      localPurchaseParam.index_import_id = localVideoIndex.import_id;
    localPurchaseParam.def = paramVideoInfo.definition;
    this.tokenDialog.setType(2009);
    this.tokenDialog.setListener(this.buyDialogListener);
    this.tokenDialog.setPurchaseInfo(localPurchaseParam);
    this.tokenDialog.setIsNeedQuit(true);
    this.tokenDialog.show();
    this.dialogMessage = this.tokenDialog.getDialogMsg();
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "action:" + paramString1 + ",type:" + paramString2 + ",command:" + paramString3);
    if (paramXulView == null);
    while (true)
    {
      return true;
      if (!"m_open_fullscreen".equals(paramString2))
        break;
      if (isPayTipsVisible())
        this.payControl.startPay(this.mPlayContent, 0L, this.mVideoInfo);
      while (this.fullTipView != null)
      {
        this.fullTipView.setStyle("dislay", "none");
        this.fullTipView.resetRender();
        return true;
        this.mplayer.setFullScreen();
      }
    }
    if ("on_window_focus".equals(paramString2))
    {
      delayToWindowFocus();
      return true;
    }
    if ("massiveUpdated".equals(paramString1))
    {
      this.mHandler.sendEmptyMessageDelayed(18, 20L);
      return true;
    }
    if ("delay_focus".equals(paramString2))
    {
      Logger.i(TAG, "MSG_DELAY_FOCUS");
      this.mHandler.sendEmptyMessageDelayed(19, 200L);
      return true;
    }
    if ("user_cmd".equals(paramString1))
    {
      processUserCmd(paramXulView, paramString3);
      return true;
    }
    Logger.i(TAG, "no action:" + paramString1);
    return true;
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("info/category_data".equals(paramString))
      try
      {
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(SpecialPlayerDataManeger.getInstance().buildSpecialCategory(this.specialList).getBytes("UTF-8"));
        return localByteArrayInputStream;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    Logger.d(TAG, "xulOnRenderIsReady");
    super.xulOnRenderIsReady();
    initView();
    loadData();
  }

  private class MplayerListener
    implements MplayerEx.IMplayerOutListener
  {
    private MplayerListener()
    {
    }

    public void onAuthFail(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt)
    {
      if (SpecialPlayerActivity.this.isFinishing())
        return;
      Logger.e(SpecialPlayerActivity.TAG, "onAuthFail--->" + paramVideoInfo.videoId + ",authState:" + paramInt);
      SpecialPlayerActivity.access$4502(SpecialPlayerActivity.this, paramVideoInfo);
      SpecialPlayerActivity.this.processAuthFail(paramSpecialCategoryContent, paramVideoInfo, paramInt);
    }

    public void onDisplayError(boolean paramBoolean, String paramString)
    {
      Logger.e(SpecialPlayerActivity.TAG, "onDisplayError---> errorCode:" + paramString);
      if ((SpecialPlayerActivity.this.mplayerDialog == null) || (SpecialPlayerActivity.this.isFinishing()))
        return;
      SpecialPlayerActivity.this.mplayerDialog.setListener(SpecialPlayerActivity.this.errorDialogListener);
      SpecialPlayerActivity.this.mplayerDialog.setErrorCode(paramString);
      SpecialPlayerActivity.this.mplayerDialog.show();
      SpecialPlayerActivity.access$4302(SpecialPlayerActivity.this, SpecialPlayerActivity.this.mplayerDialog.getDialogMsg());
    }

    public void onPreviewComplete(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt)
    {
      if (SpecialPlayerActivity.this.isFinishing())
        return;
      Logger.i(SpecialPlayerActivity.TAG, "onPreviewComplete---> videoInfo:" + paramVideoInfo.name);
      SpecialPlayerActivity.access$4402(SpecialPlayerActivity.this, paramInt);
      SpecialPlayerActivity.access$4502(SpecialPlayerActivity.this, paramVideoInfo);
      if (SpecialPlayerActivity.this.isCanUseCoupon(paramVideoInfo))
      {
        paramVideoInfo.is_trylook = "1";
        SpecialPlayerActivity.this.payControl.useMovieCoupon(paramSpecialCategoryContent, paramVideoInfo, SpecialPlayerActivity.this.preViewTime, SpecialPlayerActivity.this.movieCouponDialogListener);
        return;
      }
      SpecialPlayerActivity.this.showPreViewDialog(paramSpecialCategoryContent, paramVideoInfo, SpecialPlayerActivity.this.preViewTime);
    }

    public void onStartPlay(SpecialCategoryContent paramSpecialCategoryContent)
    {
      boolean bool1 = true;
      if ((paramSpecialCategoryContent == null) || (SpecialPlayerActivity.this.isFinishing()));
      do
      {
        return;
        Logger.i(SpecialPlayerActivity.TAG, "[update]: doViewChange!");
        if (SpecialPlayerActivity.this.isPayTipsVisible())
          SpecialPlayerActivity.this.displayPayTip(false);
        if (SpecialPlayerActivity.this.isChangeCategory(paramSpecialCategoryContent))
        {
          Logger.i(SpecialPlayerActivity.TAG, "[update]: isChangeCategory!");
          if (SpecialPlayerActivity.this.isFullScreen())
          {
            SpecialPlayerActivity.this.setCurrentPlayContent(paramSpecialCategoryContent);
            SpecialPlayerActivity.this.updateCategoryView(false);
            SpecialPlayerActivity.this.updateSpecialView();
            SpecialPlayerActivity localSpecialPlayerActivity = SpecialPlayerActivity.this;
            if (!SpecialPlayerActivity.this.isFocusOnWindow());
            while (true)
            {
              localSpecialPlayerActivity.updateItems(bool1);
              return;
              bool1 = false;
            }
          }
        }
      }
      while (!SpecialPlayerActivity.this.isChangeVideo(paramSpecialCategoryContent));
      SpecialPlayerActivity.this.setCurrentPlayContent(paramSpecialCategoryContent);
      if ((!SpecialPlayerActivity.this.isFocusOnWindow()) && (!SpecialPlayerActivity.this.isFocusOnCategory()));
      for (boolean bool2 = bool1; ; bool2 = false)
      {
        SpecialPlayerActivity.this.updateItems(bool2);
        return;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.SpecialPlayerActivity
 * JD-Core Version:    0.6.2
 */