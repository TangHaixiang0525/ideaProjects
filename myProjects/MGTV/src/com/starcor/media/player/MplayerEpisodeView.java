package com.starcor.media.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DetailPageActivity.DisplayStyle;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;
import java.util.List;

public class MplayerEpisodeView extends View
{
  public static final String FOCUS_BG_COLOR = "FF7484E7";
  public static final String FOCUS_TEXT_COLOR = "FFFFFFFF";
  public static final String LOCKED_TEXT_COLOR = "FFB4B7D5";
  public static final String NORMAL_BG_COLOR = "00FFFFFF";
  public static final String NORMAL_TEXT_COLOR = "FFB4B7D5";
  public static final String SELECTED_BG_COLOR = "FF292A41";
  private static final String TAG = MplayerEpisodeView.class.getSimpleName();
  private DetailPageActivity.DisplayStyle episodeType = DetailPageActivity.DisplayStyle.NUMBER_EPISODE;
  private boolean isOnAttached;
  private String[] lockChargeListNum = null;
  private OnItemClickListener lsnr;
  private String pageId = "";
  private Rect rect;
  private XulRenderContext renderContext;
  private int selectedIndex = -1;
  private List<VideoIndex> videoIndexes;

  public MplayerEpisodeView(Context paramContext)
  {
    super(paramContext);
  }

  public MplayerEpisodeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MplayerEpisodeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void buildEpisodeData()
  {
    XulMassiveAreaWrapper localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(this.renderContext.findItemById("episode"));
    if ((this.videoIndexes != null) && (this.videoIndexes.size() > 0))
    {
      int i = 0;
      if (i < this.videoIndexes.size())
      {
        VideoIndex localVideoIndex = (VideoIndex)this.videoIndexes.get(i);
        XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
        if (this.episodeType.equals(DetailPageActivity.DisplayStyle.NUMBER_EPISODE))
        {
          localXulDataNode.setAttribute("name", localVideoIndex.name);
          label93: localXulDataNode.setAttribute("video_index", String.valueOf(i));
          if (this.selectedIndex != i)
            break label334;
          localXulDataNode.setAttribute("isSelected", "true");
          localXulDataNode.setAttribute("background-color", "FF292A41");
          label133: if (!isItemLocked(localVideoIndex.index))
            break label357;
          localXulDataNode.setAttribute("isLocked", "true");
          localXulDataNode.setAttribute("font-color", "FFB4B7D5");
        }
        while (true)
        {
          localXulMassiveAreaWrapper.addItem(localXulDataNode);
          i++;
          break;
          if (this.episodeType.equals(DetailPageActivity.DisplayStyle.WATCH_FOCUS))
          {
            localXulDataNode.setAttribute("name", localVideoIndex.desc);
            break label93;
          }
          if (this.episodeType.equals(DetailPageActivity.DisplayStyle.WATCH_FOCUS_DATE))
          {
            localXulDataNode.setAttribute("name", "第" + GlobalLogic.getInstance().getTargetDateString(localVideoIndex) + "期: " + localVideoIndex.desc);
            break label93;
          }
          if (this.episodeType.equals(DetailPageActivity.DisplayStyle.WATCH_FOCUS_EPISODE))
          {
            localXulDataNode.setAttribute("name", localVideoIndex.name + " " + localVideoIndex.desc);
            break label93;
          }
          localXulDataNode.setAttribute("name", localVideoIndex.name);
          break label93;
          label334: localXulDataNode.setAttribute("isSelected", "false");
          localXulDataNode.setAttribute("background-color", "00FFFFFF");
          break label133;
          label357: localXulDataNode.setAttribute("isLocked", "false");
          localXulDataNode.setAttribute("font-color", "FFB4B7D5");
        }
      }
      localXulMassiveAreaWrapper.syncContentView();
    }
  }

  private void initViews()
  {
    if ((TextUtils.isEmpty(this.pageId)) || (!this.isOnAttached))
      return;
    this.renderContext = XulManager.createXulRender(this.pageId, new XulRenderContext.IXulRenderHandler()
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
          MplayerEpisodeView.this.invalidate();
          return;
        }
        MplayerEpisodeView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (paramAnonymousString2.equals("episode_open_video"))
        {
          int i = XulUtils.tryParseInt(paramAnonymousString3);
          boolean bool1 = "true".equals(paramAnonymousXulView.getAttrString("isLocked"));
          boolean bool2 = false;
          if (bool1)
          {
            Logger.d(MplayerEpisodeView.TAG, "剧集有锁");
            bool2 = true;
          }
          MplayerEpisodeView.this.setSelectedItem(i);
          if (MplayerEpisodeView.this.lsnr != null)
          {
            if (paramAnonymousXulView != null)
            {
              String str = ReportArea.getValue(paramAnonymousXulView.getAttrString("page_area"));
              if (!TextUtils.isEmpty(str))
                ReportInfo.getInstance().setEntranceSrc(str);
            }
            MplayerEpisodeView.this.lsnr.onItemClick(i, bool2);
          }
        }
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        MplayerEpisodeView.this.buildEpisodeData();
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

      class Command
      {
        String index;
        String name;

        Command()
        {
        }
      }
    }
    , XulManager.getPageWidth(), XulManager.getPageHeight());
    XulPage localXulPage = this.renderContext.getPage();
    int i = localXulPage.getX();
    int j = localXulPage.getY();
    int k = localXulPage.getWidth();
    int m = localXulPage.getHeight();
    if (i >= 2147483547)
      i = 0;
    if (j >= 2147483547)
      j = 0;
    if (k >= 2147483547)
      k = localXulPage.getPageWidth() - i;
    if (m >= 2147483547)
      m = localXulPage.getPageHeight() - j;
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((int)(k * localXulPage.getXScalar()), (int)(m * localXulPage.getYScalar()));
    localLayoutParams.addRule(15);
    setLayoutParams(localLayoutParams);
  }

  private boolean isItemLocked(int paramInt)
  {
    if (paramInt <= -1)
    {
      Logger.e(TAG, "数字剧集解析异常");
      paramInt = 0;
    }
    if (this.lockChargeListNum == null);
    while (true)
    {
      return false;
      if (this.lockChargeListNum.length > 0)
        for (int i = 0; i < this.lockChargeListNum.length; i++)
          if (paramInt + 1 == Integer.parseInt(this.lockChargeListNum[i]))
            return true;
    }
  }

  private void requestXulFocus()
  {
    XulView localXulView;
    final XulMassiveAreaWrapper localXulMassiveAreaWrapper;
    int i;
    if ((this.renderContext != null) && (this.renderContext.isReady()) && (this.selectedIndex != -1))
    {
      localXulView = this.renderContext.findItemById("episode");
      if (localXulView != null)
      {
        localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulView);
        i = localXulMassiveAreaWrapper.itemNum();
      }
    }
    for (final int j = 0; ; j++)
      if (j < i)
      {
        localXulMassiveAreaWrapper.getItem(j);
        if (j == this.selectedIndex)
          localXulMassiveAreaWrapper.makeChildVisible(localXulView.findParentByType("slider"), j, 0.5F, (0.0F / 0.0F), false, new Runnable()
          {
            public void run()
            {
              XulView localXulView = localXulMassiveAreaWrapper.getItemView(j);
              MplayerEpisodeView.this.renderContext.getLayout().requestFocus(localXulView);
            }
          });
      }
      else
      {
        return;
      }
  }

  private void updateEpisode()
  {
    if ((this.renderContext == null) || (!this.renderContext.isReady()))
      Logger.e(TAG, "updateEpisode() render未初始化完成");
    XulView localXulView1;
    do
    {
      return;
      localXulView1 = this.renderContext.findItemById("episode");
    }
    while (localXulView1 == null);
    XulMassiveAreaWrapper localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulView1);
    int i = localXulMassiveAreaWrapper.itemNum();
    int j = 0;
    label54: XulView localXulView2;
    boolean bool;
    int k;
    label107: String str1;
    label126: String str2;
    label145: String str3;
    label164: String str4;
    label197: String str5;
    label215: String str6;
    if (j < i)
    {
      XulDataNode localXulDataNode = localXulMassiveAreaWrapper.getItem(j);
      localXulView2 = localXulMassiveAreaWrapper.getItemView(j);
      bool = isItemLocked(XulUtils.tryParseInt(localXulDataNode.getAttribute("video_index").getValue()));
      if (j != this.selectedIndex)
        break label333;
      k = 1;
      localXulDataNode.setAttribute("font-color", "FFB4B7D5");
      if (k == 0)
        break label339;
      str1 = "true";
      localXulDataNode.setAttribute("isSelected", str1);
      if (!bool)
        break label346;
      str2 = "true";
      localXulDataNode.setAttribute("isLocked", str2);
      if (k == 0)
        break label353;
      str3 = "FF292A41";
      localXulDataNode.setAttribute("background-color", str3);
      if (localXulView2 != null)
      {
        localXulView2.setStyle("font-color", "FFB4B7D5");
        if (!bool)
          break label360;
        str4 = "true";
        localXulView2.setAttr("isLocked", str4);
        if (!bool)
          break label367;
        str5 = "true";
        localXulView2.setAttr("img.0.visible", str5);
        localXulView2.setAttr("img.1.visible", "false");
        if (k == 0)
          break label374;
        str6 = "FF292A41";
        label244: localXulView2.setStyle("background-color", str6);
        if (k == 0)
          break label381;
      }
    }
    label333: label339: label346: label353: label360: label367: label374: label381: for (String str7 = "true"; ; str7 = "false")
    {
      localXulView2.setAttr("isSelected", str7);
      if (localXulView2.hasFocus())
      {
        localXulView2.setStyle("background-color", "FF7484E7");
        localXulView2.setStyle("font-color", "FFFFFFFF");
        if (bool)
        {
          localXulView2.setAttr("img.0.visible", "false");
          localXulView2.setAttr("img.1.visible", "true");
        }
      }
      localXulView2.resetRender();
      j++;
      break label54;
      break;
      k = 0;
      break label107;
      str1 = "false";
      break label126;
      str2 = "false";
      break label145;
      str3 = "00FFFFFF";
      break label164;
      str4 = "false";
      break label197;
      str5 = "false";
      break label215;
      str6 = "00FFFFFF";
      break label244;
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Log.d("View", "MplayerEpisodeView dispatchKeyEvent " + paramKeyEvent.getKeyCode() + "hasFocus = " + hasFocus());
    if ((this.renderContext != null) && (this.renderContext.onKeyEvent(paramKeyEvent)))
    {
      Log.i("View", "MplayerEpisodeView dispatchKeyEvent 按键已处理");
      return true;
    }
    Log.i("View", "MplayerEpisodeView dispatchKeyEvent 按键未处理");
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.isOnAttached = true;
    initViews();
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
    if (this.renderContext != null)
    {
      if (this.rect != null)
        break label58;
      this.rect = new Rect(0, 0, getWidth(), getHeight());
    }
    while (true)
    {
      if (this.renderContext.beginDraw(paramCanvas, this.rect))
        this.renderContext.endDraw();
      return;
      label58: this.rect.set(0, 0, getWidth(), getHeight());
    }
  }

  public void setEpisodeData(int paramInt1, String paramString, List<VideoIndex> paramList, int paramInt2)
  {
    this.episodeType = GlobalLogic.getInstance().getDisplayStyle(paramInt1, paramString);
    if (this.episodeType == DetailPageActivity.DisplayStyle.NUMBER_EPISODE);
    for (this.pageId = "MplayerEpisodeView"; ; this.pageId = "MplayerEpisodeWatchingFocusView")
    {
      this.selectedIndex = paramInt2;
      this.videoIndexes = paramList;
      initViews();
      return;
    }
  }

  public void setLockChargeListNum(String[] paramArrayOfString)
  {
    this.lockChargeListNum = paramArrayOfString;
    updateEpisode();
  }

  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.lsnr = paramOnItemClickListener;
  }

  public void setSelectedItem(int paramInt)
  {
    this.selectedIndex = paramInt;
    updateEpisode();
  }

  public void setVisibility(int paramInt)
  {
    if (paramInt == 0)
      requestXulFocus();
    super.setVisibility(paramInt);
  }

  public static abstract interface OnItemClickListener
  {
    public abstract void onItemClick(int paramInt, boolean paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerEpisodeView
 * JD-Core Version:    0.6.2
 */