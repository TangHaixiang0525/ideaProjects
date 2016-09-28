package com.starcor.media.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.core.domain.SpecialCategoryContent;
import com.starcor.core.domain.SpecialPlayerData;
import com.starcor.core.domain.SpecialPlayerData.UiStyle;
import com.starcor.core.utils.Logger;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulViewIterator;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SpecialPlayerEpisodeView extends View
{
  private static final int DELAY_FOCUS_TIME = 200;
  private static final int MSG_DELAY_FOCUS = 19;
  private static final int MSG_MASSIVE_DELAY = 20;
  private static final int MSG_MASSIVE_UPDATE = 20;
  private static final int MSG_SWITCH_CATEGORY = 17;
  private static final int MSG_SYNC_VIEWS = 18;
  private static final int SWITCH_CATEGORY_DELAY = 300;
  private static final String TAG = SpecialPlayerEpisodeView.class.getSimpleName();
  private static final String pageId = "SpecialPlayerEpisodeView";
  float align_point = 0.3824F;
  private XulArea categorySlider = null;
  private XulSliderAreaWrapper categoryWrapper = null;
  private boolean hasCategory = true;
  private boolean isNeedFocus = true;
  private XulArea layer_category = null;
  private XulView layer_image = null;
  private XulArea layer_special = null;
  private XulView leftCheck = null;
  private XulView leftFocus = null;
  private onItemClickListener lsr = null;
  private Context mContext = null;
  private String mFocusCategoryId = "";
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 17:
      case 18:
      case 20:
      case 19:
      }
      do
      {
        do
        {
          do
          {
            return;
            if (SpecialPlayerEpisodeView.this.mHandler.hasMessages(17))
            {
              SpecialPlayerEpisodeView.this.mHandler.removeMessages(17);
              SpecialPlayerEpisodeView.this.mHandler.sendEmptyMessageDelayed(17, 300L);
              return;
            }
            SpecialPlayerEpisodeView.this.mHandler.removeMessages(19);
            SpecialPlayerEpisodeView.this.updateSpecialView();
            return;
          }
          while (SpecialPlayerEpisodeView.this.isFinished());
          Logger.i(SpecialPlayerEpisodeView.TAG, "[update] msg MSG_SYNC_VIEWS");
          SpecialPlayerEpisodeView.access$402(SpecialPlayerEpisodeView.this, true);
          SpecialPlayerEpisodeView.this.checkIsUpdate();
          return;
        }
        while ((SpecialPlayerEpisodeView.this.isFinished()) || (!SpecialPlayerEpisodeView.this.isVisible()));
        if (SpecialPlayerEpisodeView.this.mHandler.hasMessages(18))
          SpecialPlayerEpisodeView.this.mHandler.removeMessages(18);
        if (SpecialPlayerEpisodeView.this.mHandler.hasMessages(18))
        {
          SpecialPlayerEpisodeView.this.mHandler.removeMessages(18);
          SpecialPlayerEpisodeView.this.mHandler.sendEmptyMessageDelayed(18, 300L);
          return;
        }
        SpecialPlayerEpisodeView.this.massive.getRender().getRenderContext().scheduleLayoutFinishedTask(new Runnable()
        {
          public void run()
          {
            SpecialPlayerEpisodeView.this.clearItems();
          }
        });
        return;
      }
      while (SpecialPlayerEpisodeView.this.isFinished());
      if (SpecialPlayerEpisodeView.this.mHandler.hasMessages(19))
      {
        SpecialPlayerEpisodeView.this.mHandler.removeMessages(19);
        SpecialPlayerEpisodeView.this.mHandler.sendEmptyMessageDelayed(19, 200L);
        return;
      }
      SpecialPlayerEpisodeView.this.setFontFocus();
    }
  };
  private String mPlayCategoryId = "";
  private SpecialCategoryContent mPlayContent = null;
  private int mPlayIndex = -1;
  private XulArea massive = null;
  private XulMassiveAreaWrapper massiveWrapper = null;
  private Rect rect;
  private XulRenderContext renderContext;
  private List<SpecialPlayerData> specialList = null;
  private XulArea specialSlider = null;
  private XulSliderAreaWrapper specialWrapper = null;

  public SpecialPlayerEpisodeView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public SpecialPlayerEpisodeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public SpecialPlayerEpisodeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }

  private void checkIsUpdate()
  {
    Logger.i(TAG, "[update]--->检测是否更新UI");
    if (this.mPlayContent == null)
    {
      this.isNeedFocus = false;
      Logger.i(TAG, "[update]--->mPlayContent null");
      return;
    }
    if (!isFocusOnSelectSpecial())
    {
      if (isFocusOnSelectCategory())
      {
        Logger.i(TAG, "[update]--->更新列表焦点");
        updateItems();
        return;
      }
      Logger.i(TAG, "[update]--->更新分类和列表");
      this.mFocusCategoryId = this.mPlayCategoryId;
      updateViews();
      return;
    }
    this.isNeedFocus = false;
    Logger.i(TAG, "[update]--->检测不更新UI");
  }

  private void clearItems()
  {
    if (this.massive == null)
      return;
    Logger.i(TAG, "[update] msg MSG_MASSIVE_UPDATE");
    this.massive.eachView(new XulArea.XulViewIterator()
    {
      public boolean onXulView(int paramAnonymousInt, XulView paramAnonymousXulView)
      {
        XulDataNode localXulDataNode = (XulDataNode)paramAnonymousXulView.getBindingData().get(0);
        SpecialPlayerEpisodeView.this.updateItem(paramAnonymousXulView, SpecialPlayerDataManeger.getInstance().isChecked(localXulDataNode, SpecialPlayerEpisodeView.this.mPlayContent));
        return super.onXulView(paramAnonymousInt, paramAnonymousXulView);
      }
    });
  }

  private void hideCategory()
  {
    if (this.hasCategory)
    {
      postDelayed(new Runnable()
      {
        public void run()
        {
          SpecialPlayerEpisodeView.this.layer_category.removeClass("visible");
          SpecialPlayerEpisodeView.this.layer_category.resetRender();
        }
      }
      , 200L);
      this.layer_image.setStyle("display", "none");
      this.layer_image.resetRender();
    }
  }

  private void hideSpecial()
  {
    if (this.hasCategory)
      this.layer_special.removeClass("visible-2");
    while (true)
    {
      this.layer_special.resetRender();
      return;
      this.layer_special.removeClass("visible");
    }
  }

  private void initView()
  {
    this.renderContext = XulManager.createXulRender("SpecialPlayerEpisodeView", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        if (paramAnonymousString.equals("info/category_data"))
        {
          String str = SpecialPlayerDataManeger.getInstance().buildSpecialCategory(SpecialPlayerEpisodeView.this.specialList);
          try
          {
            ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            return localByteArrayInputStream;
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            localUnsupportedEncodingException.printStackTrace();
          }
        }
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
          SpecialPlayerEpisodeView.this.invalidate();
          return;
        }
        SpecialPlayerEpisodeView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        Logger.i(SpecialPlayerEpisodeView.TAG, "onDoAction--->action=" + paramAnonymousString1 + ",type" + paramAnonymousString2 + ",command=" + paramAnonymousString3);
        if ("massiveUpdated".equals(paramAnonymousString1))
          if ((SpecialPlayerEpisodeView.this.isVisible()) && (!SpecialPlayerEpisodeView.this.mHandler.hasMessages(18)));
        String str1;
        do
        {
          do
          {
            while (true)
            {
              return;
              SpecialPlayerEpisodeView.this.mHandler.sendEmptyMessageDelayed(20, 20L);
              return;
              if ("delay_focus".equals(paramAnonymousString2))
              {
                Logger.i(SpecialPlayerEpisodeView.TAG, "MSG_DELAY_FOCUS");
                SpecialPlayerEpisodeView.this.mHandler.sendEmptyMessageDelayed(19, 200L);
                return;
              }
              if ("user_cmd".equals(paramAnonymousString1))
                try
                {
                  JSONObject localJSONObject = new JSONObject(paramAnonymousString3);
                  str1 = localJSONObject.getString("cmd");
                  if ("m_switch_category".equals(str1))
                  {
                    String str2 = localJSONObject.getString("category_id");
                    if ((TextUtils.isEmpty(str2)) || (SpecialPlayerEpisodeView.this.mFocusCategoryId.equals(str2)))
                      continue;
                    SpecialPlayerEpisodeView.access$1402(SpecialPlayerEpisodeView.this, str2);
                    SpecialPlayerEpisodeView.this.specialSlider.setEnabled(false);
                    SpecialPlayerEpisodeView.this.mHandler.sendEmptyMessageDelayed(17, 300L);
                  }
                }
                catch (JSONException localJSONException)
                {
                  localJSONException.printStackTrace();
                  return;
                }
            }
            if (!"m_open_player".equals(str1))
              break;
            SpecialPlayerEpisodeView.access$1602(SpecialPlayerEpisodeView.this, XulUtils.tryParseInt(paramAnonymousXulView.getDataString("index")));
            SpecialPlayerEpisodeView.access$1702(SpecialPlayerEpisodeView.this, SpecialPlayerEpisodeView.this.mFocusCategoryId);
          }
          while (SpecialPlayerEpisodeView.this.lsr == null);
          SpecialCategoryContent localSpecialCategoryContent = SpecialPlayerDataManeger.getInstance().getContentItem(SpecialPlayerEpisodeView.this.specialList, paramAnonymousXulView.getDataString("video_id"), XulUtils.tryParseInt(paramAnonymousXulView.getDataString("video_index")), SpecialPlayerEpisodeView.this.mPlayCategoryId);
          Logger.i(SpecialPlayerEpisodeView.TAG, "onItemClick---> CategoryId:" + SpecialPlayerEpisodeView.this.mPlayCategoryId + ",index:" + SpecialPlayerEpisodeView.this.mPlayIndex);
          SpecialPlayerEpisodeView.this.lsr.onItemClick(localSpecialCategoryContent);
          SpecialPlayerDataManeger.getInstance().setPlayed(paramAnonymousXulView);
          paramAnonymousXulView.findItemById("name").addClass("massive-name-watch");
          paramAnonymousXulView.findItemById("duration").addClass("massive-duration-watch");
          paramAnonymousXulView.resetRender();
          return;
          if (SpecialPlayerEpisodeView.this.isVisible())
            SpecialPlayerEpisodeView.this.setDisplay(false);
          if ("m_open_detail_page".equals(str1))
          {
            SpecialPlayerDataManeger.getInstance().goToDetail(SpecialPlayerEpisodeView.this.mContext, paramAnonymousXulView);
            return;
          }
          if ("m_open_special_page".equals(str1))
          {
            SpecialPlayerDataManeger.getInstance().goToSpecial(SpecialPlayerEpisodeView.this.mContext, paramAnonymousXulView);
            return;
          }
          if ("m_open_web".equals(str1))
          {
            SpecialPlayerDataManeger.getInstance().goToWeb(SpecialPlayerEpisodeView.this.mContext, paramAnonymousXulView);
            return;
          }
        }
        while (!"m_open_tv_player".equals(str1));
        SpecialPlayerDataManeger.getInstance().goToLivePlayer(SpecialPlayerEpisodeView.this.mContext, paramAnonymousXulView);
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        if ((SpecialPlayerEpisodeView.this.hasCategory) && (SpecialPlayerEpisodeView.this.renderContext != null))
          SpecialPlayerEpisodeView.this.renderContext.refreshBinding("categroy-data", "file:///.app/info/category_data");
        SpecialPlayerEpisodeView.this.onRenderIsReady();
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
    if (this.renderContext == null)
      return;
    setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
  }

  private boolean isFinished()
  {
    if ((this.mContext != null) && ((this.mContext instanceof Activity)) && (((Activity)this.mContext).isFinishing()))
    {
      Logger.i(TAG, "Activity isFinishing");
      return true;
    }
    return false;
  }

  private boolean isFocusOnSelectCategory()
  {
    XulView localXulView = xulGetFocus();
    return (localXulView != null) && (this.mPlayCategoryId.equals(localXulView.getDataString("special_category_id")));
  }

  private boolean isFocusOnSelectSpecial()
  {
    XulView localXulView = xulGetFocus();
    return (localXulView != null) && (this.mPlayContent.video_id.equals(localXulView.getDataString("video_id"))) && (this.mPlayContent.video_index == XulUtils.tryParseInt(localXulView.getDataString("video_index"))) && (this.mPlayCategoryId.equals(localXulView.getDataString("special_category_id")));
  }

  private void onRenderIsReady()
  {
    this.categorySlider = ((XulArea)this.renderContext.findItemById("category_list"));
    this.categoryWrapper = XulSliderAreaWrapper.fromXulView(this.categorySlider);
    this.specialSlider = ((XulArea)this.renderContext.findItemById("special_list"));
    this.specialWrapper = XulSliderAreaWrapper.fromXulView(this.specialSlider);
    this.layer_category = ((XulArea)this.renderContext.findItemById("layer-1"));
    this.layer_special = ((XulArea)this.renderContext.findItemById("layer-2"));
    this.layer_image = this.renderContext.findItemById("layer-3");
    this.leftFocus = this.renderContext.findItemById("left_focus_area");
    this.leftCheck = this.renderContext.findItemById("left_checked_area");
    if (this.mPlayContent != null)
      updateViews();
  }

  private String printContent()
  {
    if (this.mPlayContent == null)
      return "null";
    return this.mPlayContent.video_name + "," + this.mPlayContent.video_id + "," + this.mPlayContent.video_index + "," + this.mPlayContent.special_category_id;
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

  private void setSpecialDisplayStyle(SpecialPlayerData.UiStyle paramUiStyle)
  {
    XulView localXulView1 = xulFindViewById("component_image");
    XulView localXulView2 = xulFindViewById("component_text");
    XulView localXulView3 = xulFindViewById("right_focused_area");
    XulView localXulView4 = xulFindViewById("space_height");
    if (paramUiStyle == SpecialPlayerData.UiStyle.DISPLAY_UI_IMAGE)
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
      localXulView3.setAttr("y", "435");
      localXulView3.resetRender();
      localXulView4.setAttr("height", "432");
      localXulView4.resetRender();
      this.align_point = 0.4028F;
    }
  }

  private void showCategory()
  {
    if (this.hasCategory)
    {
      this.layer_category.addClass("visible");
      this.layer_category.resetRender();
      postDelayed(new Runnable()
      {
        public void run()
        {
          SpecialPlayerEpisodeView.this.layer_image.setAttr("x", "282");
          SpecialPlayerEpisodeView.this.layer_image.setStyle("display", "block");
          SpecialPlayerEpisodeView.this.layer_image.resetRender();
        }
      }
      , 200L);
    }
  }

  private void showSpecial()
  {
    if (this.hasCategory)
      this.layer_special.addClass("visible-2");
    while (true)
    {
      this.layer_special.resetRender();
      return;
      this.layer_special.addClass("visible");
    }
  }

  private void updateCategoryView()
  {
    if (!this.hasCategory);
    XulView localXulView1;
    while (true)
    {
      return;
      int i = this.categorySlider.getChildNum();
      for (int j = 0; j < i; j++)
      {
        localXulView1 = this.categorySlider.getChild(j);
        if ((localXulView1 != null) && (!TextUtils.isEmpty(localXulView1.getDataString("category_id"))))
          break label55;
      }
    }
    label55: XulView localXulView2 = localXulView1.findItemById("name");
    if (this.mPlayCategoryId.equals(localXulView1.getDataString("category_id")))
    {
      Logger.i(TAG, "[update]--->Category:" + printContent());
      if (this.leftFocus.removeClass("category-focus"))
        this.leftFocus.resetRender();
      if (this.leftCheck.addClass("category-checked-class"))
        this.leftCheck.resetRender();
      localXulView1.addClass("category_group_item_checked");
      if (localXulView2 != null)
      {
        localXulView2.addClass("category-item-checked");
        localXulView2.resetRender();
      }
      this.categorySlider.setDynamicFocus(localXulView1);
      this.categoryWrapper.makeChildVisible(localXulView1, 0.4074F, 0.0F, false);
    }
    while (true)
    {
      localXulView1.resetRender();
      break;
      localXulView1.removeClass("category_group_item_checked");
      if (localXulView2 != null)
      {
        localXulView2.removeClass("category-item-checked");
        localXulView2.resetRender();
      }
    }
  }

  private void updateFocus()
  {
    if (this.massive == null)
      return;
    Logger.i(TAG, "[update] updateFocus");
    this.massive.eachView(new XulArea.XulViewIterator()
    {
      public boolean onXulView(int paramAnonymousInt, XulView paramAnonymousXulView)
      {
        XulDataNode localXulDataNode = (XulDataNode)paramAnonymousXulView.getBindingData().get(0);
        if (SpecialPlayerDataManeger.getInstance().isChecked(localXulDataNode, SpecialPlayerEpisodeView.this.mPlayContent))
        {
          SpecialPlayerDataManeger.getInstance().setPlayed(paramAnonymousXulView);
          SpecialPlayerEpisodeView.this.updateItem(paramAnonymousXulView, true);
        }
        while (true)
        {
          return super.onXulView(paramAnonymousInt, paramAnonymousXulView);
          SpecialPlayerEpisodeView.this.updateItem(paramAnonymousXulView, false);
        }
      }
    });
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
        break label223;
      Logger.i(TAG, "[update]--->check:" + printContent());
      paramXulView.addClass("list_group_item_checked");
      localXulView3.setStyle("display", "block");
      localXulView1.addClass("massive-name-checked");
      localXulView2.addClass("massive-name-checked");
      if (this.isNeedFocus)
      {
        Logger.i(TAG, "[update]--->focus:" + printContent());
        this.specialSlider.setDynamicFocus(paramXulView);
        xulRequestFocus(paramXulView);
        this.isNeedFocus = false;
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
      label223: localXulView1.removeClass("massive-name-checked");
      localXulView1.removeClass("massive-name-focus");
      localXulView2.removeClass("massive-name-checked");
      localXulView2.removeClass("massive-duration-focus");
      localXulView3.setStyle("display", "none");
      paramXulView.removeClass("list_group_item_checked");
    }
  }

  private void updateItems()
  {
    this.massive.getRender().getRenderContext().scheduleLayoutFinishedTask(new Runnable()
    {
      public void run()
      {
        int i = SpecialPlayerEpisodeView.this.massiveWrapper.itemNum();
        int j = 0;
        if (j < i)
        {
          XulDataNode localXulDataNode = SpecialPlayerEpisodeView.this.massiveWrapper.getItem(j);
          if (localXulDataNode == null);
          while (true)
          {
            j++;
            break;
            if (SpecialPlayerDataManeger.getInstance().isChecked(localXulDataNode, SpecialPlayerEpisodeView.this.mPlayContent))
            {
              final int k = j;
              SpecialPlayerEpisodeView.this.massiveWrapper.makeChildVisible(SpecialPlayerEpisodeView.this.specialSlider, j, SpecialPlayerEpisodeView.this.align_point, 0.0F, false, new Runnable()
              {
                public void run()
                {
                  XulView localXulView = SpecialPlayerEpisodeView.this.massiveWrapper.getItemView(k);
                  SpecialPlayerDataManeger.getInstance().setPlayed(localXulView);
                  SpecialPlayerEpisodeView.this.updateItem(localXulView, true);
                  SpecialPlayerEpisodeView.this.specialSlider.setDynamicFocus(localXulView);
                }
              });
            }
            else
            {
              SpecialPlayerEpisodeView.this.updateItem(SpecialPlayerEpisodeView.this.massiveWrapper.getItemView(j), false);
            }
          }
        }
      }
    });
    this.massive.getRender().getRenderContext().doLayout();
  }

  private void updateSpecialView()
  {
    if (TextUtils.isEmpty(this.mFocusCategoryId))
      return;
    SpecialPlayerDataManeger.getInstance().resetMassiveList(this.massiveWrapper);
    this.specialSlider.setEnabled(true);
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
    Logger.i(TAG, "[update]--->size:" + this.massiveWrapper.itemNum());
    this.massiveWrapper.syncContentView();
    updateItems();
  }

  private XulView xulFindViewById(String paramString)
  {
    if (this.renderContext == null)
      return null;
    return this.renderContext.findItemById(paramString);
  }

  public void bindData(List<SpecialPlayerData> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0));
    do
    {
      return;
      this.specialList = paramList;
      setSpecialDisplayStyle(((SpecialPlayerData)paramList.get(0)).uiStyle);
      if ("ALL".equals(((SpecialPlayerData)paramList.get(0)).specialCategoryId))
        this.hasCategory = false;
    }
    while ((!this.hasCategory) || (this.renderContext == null) || (!this.renderContext.isReady()));
    this.renderContext.refreshBinding("categroy-data", "file:///.app/info/category_data");
  }

  public boolean isVisible()
  {
    if ((this.layer_category == null) || (this.layer_special == null));
    while ((this.renderContext == null) || (!this.renderContext.isReady()))
      return false;
    if (this.hasCategory)
      return this.layer_category.hasClass("visible");
    return this.layer_special.hasClass("visible");
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.renderContext == null)
      initView();
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

  public boolean onInputEvent(KeyEvent paramKeyEvent)
  {
    if ((this.renderContext != null) && (this.renderContext.onKeyEvent(paramKeyEvent)))
      return true;
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void setDisplay(boolean paramBoolean)
  {
    if ((this.renderContext == null) || (!this.renderContext.isReady()))
      return;
    Logger.i(TAG, "setDisplay--->" + paramBoolean + ",hasCategory:" + this.hasCategory);
    if (paramBoolean)
    {
      showCategory();
      showSpecial();
      updateCategoryView();
      updateFocus();
      return;
    }
    hideSpecial();
    hideCategory();
    this.mHandler.removeMessages(18);
    this.mHandler.removeMessages(20);
    this.mHandler.sendEmptyMessageDelayed(18, 300L);
  }

  public void setOnItemClickListener(onItemClickListener paramonItemClickListener)
  {
    this.lsr = paramonItemClickListener;
  }

  public void setPlayContent(SpecialCategoryContent paramSpecialCategoryContent)
  {
    this.mPlayContent = paramSpecialCategoryContent;
    this.mFocusCategoryId = this.mPlayContent.special_category_id;
    this.mPlayCategoryId = this.mFocusCategoryId;
    this.mPlayIndex = this.mPlayContent.index;
    Logger.i(TAG, "setPlayContent--->" + this.mPlayCategoryId);
    if ((this.renderContext != null) && (this.renderContext.isReady()) && (!isVisible()))
    {
      this.isNeedFocus = true;
      Logger.i(TAG, "[update]--->setPlayContent!" + printContent());
      checkIsUpdate();
    }
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
  }

  public void updateViews()
  {
    Logger.i(TAG, "updateViews:" + this.mPlayContent.video_name + "," + this.mPlayContent.video_id);
    if (this.hasCategory)
      updateCategoryView();
    updateSpecialView();
  }

  public boolean xulFireEvent(String paramString, Object[] paramArrayOfObject)
  {
    if ((this.renderContext == null) || (this.renderContext.getLayout() == null))
      return false;
    return XulPage.invokeActionWithArgs(this.renderContext.getLayout(), paramString, paramArrayOfObject);
  }

  public XulView xulGetFocus()
  {
    if (this.renderContext == null)
      return null;
    return this.renderContext.getLayout().getFocus();
  }

  public void xulRequestFocus(XulView paramXulView)
  {
    if ((this.renderContext == null) || (this.renderContext == null));
    while ((paramXulView == null) || (!paramXulView.isEnabled()) || (!paramXulView.isVisible()) || (!paramXulView.focusable()))
      return;
    this.renderContext.getLayout().requestFocus(paramXulView);
  }

  public static abstract interface onItemClickListener
  {
    public abstract void onItemClick(SpecialCategoryContent paramSpecialCategoryContent);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.SpecialPlayerEpisodeView
 * JD-Core Version:    0.6.2
 */