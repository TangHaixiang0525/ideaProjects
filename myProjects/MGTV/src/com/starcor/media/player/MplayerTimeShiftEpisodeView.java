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
import com.google.gson.Gson;
import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.utils.Logger;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class MplayerTimeShiftEpisodeView extends View
{
  private static final String CATEGORY_BINDING = "file:///.app/categoryList/category";
  private static final String COMMON_CATEGORY_ID = "common_channel";
  public static final String COMMON_CHANNEL = "常用频道";
  private static final String TAG = MplayerTimeShiftEpisodeView.class.getSimpleName();
  private LinkedHashMap<String, String> categoryMap = new LinkedHashMap();
  private ByteArrayInputStream channelData;
  private ChannelInfoV2 channelInfo;
  private ArrayList<ChannelItemInfoV2> commonChannels = new ArrayList();
  XulView firstDefaultView;
  private boolean isBindData;
  private boolean isCategoryRefreshed;
  private boolean isFirstTime = true;
  private onChannelItemClickListener lsnr;
  private MediaAssetsInfo mediaAssetsInfo;
  private String playingCategoryId = "";
  private Rect rect;
  private XulRenderContext renderContext;
  private String selectedCategoryId = "";
  private String selectedChanelItemId = "";

  public MplayerTimeShiftEpisodeView(Context paramContext)
  {
    super(paramContext);
  }

  public MplayerTimeShiftEpisodeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MplayerTimeShiftEpisodeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void checkData()
  {
    if ((this.mediaAssetsInfo == null) || (this.mediaAssetsInfo.cList == null) || (this.mediaAssetsInfo.cList.size() <= 0) || (this.channelInfo == null) || (this.channelInfo.channelList == null) || (this.channelInfo.channelList.size() <= 0))
      return;
    this.categoryMap.clear();
    int i = this.channelInfo.channelList.size();
    int j = 0;
    label81: ChannelItemInfoV2 localChannelItemInfoV2;
    if (j < i)
    {
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelInfo.channelList.get(j);
      if (!this.categoryMap.containsKey(localChannelItemInfoV2.categoryId))
        break label121;
    }
    while (true)
    {
      j++;
      break label81;
      break;
      label121: this.categoryMap.put(localChannelItemInfoV2.categoryId, findCategoryName(localChannelItemInfoV2.categoryId));
    }
  }

  private void dealSelectItem()
  {
    if (this.renderContext != null)
    {
      XulArrayList localXulArrayList1 = this.renderContext.findItemsByClass(new String[] { "left_area_action_class" });
      if ((localXulArrayList1 != null) && (localXulArrayList1.size() > 0))
      {
        XulArrayList localXulArrayList2 = this.renderContext.findItemsByClass(new String[] { "channel_item" });
        int i = localXulArrayList2.size();
        int j = 0;
        if (j < i)
        {
          final XulView localXulView = (XulView)localXulArrayList2.get(j);
          if (localXulView == null);
          while (true)
          {
            j++;
            break;
            String str = localXulView.getAttrString("id");
            if ((localXulView.getAttrString("categoryId").equals(this.playingCategoryId)) && (str.equals(this.selectedChanelItemId)))
            {
              localXulView.addClass("channel_item_selected");
              ((XulArea)this.renderContext.findItemById("right_content_layer")).setDynamicFocus(localXulView);
              this.renderContext.scheduleLayoutFinishedTask(new Runnable()
              {
                public void run()
                {
                  Logger.d("allen", "dealSelectItem()  requestFocus");
                  if ((MplayerTimeShiftEpisodeView.this.getVisibility() != 0) || (MplayerTimeShiftEpisodeView.this.isFirstTime))
                    if (localXulView.isVisible())
                      MplayerTimeShiftEpisodeView.this.requestFocus(localXulView);
                  do
                  {
                    MplayerTimeShiftEpisodeView.access$1402(MplayerTimeShiftEpisodeView.this, false);
                    do
                      return;
                    while (!MplayerTimeShiftEpisodeView.this.playingCategoryId.equals(localXulView.getAttrString("categoryId")));
                    XulSliderAreaWrapper.fromXulView((XulArea)MplayerTimeShiftEpisodeView.this.renderContext.findItemById("right_container")).makeChildVisible(localXulView);
                  }
                  while ((localXulView.hasFocus()) || (!MplayerTimeShiftEpisodeView.this.isFirstTime));
                  MplayerTimeShiftEpisodeView.this.requestFocus(localXulView);
                }
              });
              this.firstDefaultView = localXulView;
              localXulView.resetRender();
            }
            else if (localXulView.hasClass("channel_item_selected"))
            {
              localXulView.removeClass("channel_item_selected");
              localXulView.resetRender();
            }
          }
        }
      }
    }
  }

  private String findCategoryName(String paramString)
  {
    if ((this.mediaAssetsInfo == null) || (this.mediaAssetsInfo.cList == null) || (this.mediaAssetsInfo.cList.size() <= 0))
      return "";
    int i = this.mediaAssetsInfo.cList.size();
    for (int j = 0; j < i; j++)
    {
      CategoryItem localCategoryItem = (CategoryItem)this.mediaAssetsInfo.cList.get(j);
      if (paramString.equals(localCategoryItem.id))
        return localCategoryItem.name;
    }
    return "";
  }

  private String findCommonChannelCategoryId(String paramString)
  {
    if ((this.commonChannels == null) || (this.commonChannels.size() <= 0))
      return "";
    int i = this.commonChannels.size();
    for (int j = 0; j < i; j++)
    {
      ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)this.commonChannels.get(j);
      if (paramString.equals(localChannelItemInfoV2.id))
        return localChannelItemInfoV2.categoryId;
    }
    return "";
  }

  private ByteArrayInputStream getChannelDateByCategoryId(String paramString)
  {
    if ((this.mediaAssetsInfo == null) || (this.mediaAssetsInfo.cList == null) || (this.mediaAssetsInfo.cList.size() <= 0) || (this.channelInfo == null) || (this.channelInfo.channelList == null) || (this.channelInfo.channelList.size() <= 0))
      return null;
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "root");
      localXmlSerializer.startTag(null, "channel_list");
      if ("common_channel".equals(paramString))
      {
        Iterator localIterator2 = this.commonChannels.iterator();
        while (localIterator2.hasNext())
          writeChannelNode(localXmlSerializer, (ChannelItemInfoV2)localIterator2.next(), paramString);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    Iterator localIterator1 = this.channelInfo.channelList.iterator();
    while (localIterator1.hasNext())
    {
      ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)localIterator1.next();
      if ((localChannelItemInfoV2.categoryId != null) && (localChannelItemInfoV2.categoryId.equals(paramString)))
        writeChannelNode(localXmlSerializer, localChannelItemInfoV2, paramString);
    }
    localXmlSerializer.endTag(null, "channel_list");
    localXmlSerializer.endTag(null, "root");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private void initViews()
  {
    this.renderContext = XulManager.createXulRender("MplayerTimeShiftEpisodeView", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

      private void dealFirstSelected()
      {
        XulArrayList localXulArrayList;
        int i;
        if (MplayerTimeShiftEpisodeView.this.renderContext != null)
        {
          localXulArrayList = MplayerTimeShiftEpisodeView.this.renderContext.findItemsByClass(new String[] { "left_area_action_class" });
          if ((localXulArrayList != null) && (localXulArrayList.size() > 0))
            i = localXulArrayList.size();
        }
        for (int j = 0; ; j++)
          if (j < i)
          {
            XulView localXulView = (XulView)localXulArrayList.get(j);
            String str = localXulView.getAttrString("categoryId").trim();
            if (MplayerTimeShiftEpisodeView.this.playingCategoryId.equals(str))
              MplayerTimeShiftEpisodeView.this.firstDefaultView = localXulView;
          }
          else
          {
            MplayerTimeShiftEpisodeView.this.renderContext.scheduleLayoutFinishedTask(new Runnable()
            {
              public void run()
              {
                Logger.d("allen", "firstDefaultView()  requestFocus");
                MplayerTimeShiftEpisodeView.this.requestFocus(MplayerTimeShiftEpisodeView.this.firstDefaultView);
              }
            });
            return;
          }
      }

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        Logger.i(MplayerTimeShiftEpisodeView.TAG, "path = " + paramAnonymousString);
        if (paramAnonymousString.startsWith("channelList/channel/"))
        {
          String str = MplayerTimeShiftEpisodeView.this.parseCategory(paramAnonymousString);
          MplayerTimeShiftEpisodeView.access$1102(MplayerTimeShiftEpisodeView.this, str);
          MplayerTimeShiftEpisodeView.access$1202(MplayerTimeShiftEpisodeView.this, MplayerTimeShiftEpisodeView.this.getChannelDateByCategoryId(str));
          return MplayerTimeShiftEpisodeView.this.channelData;
        }
        if ("categoryList/category".equals(paramAnonymousString))
          return MplayerTimeShiftEpisodeView.this.getCategoryData();
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
          MplayerTimeShiftEpisodeView.this.invalidate();
          return;
        }
        MplayerTimeShiftEpisodeView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        Logger.i("MplayerTimeShiftEpisodeView", "command：" + paramAnonymousString3);
        if ("bindingFinished".equals(paramAnonymousString3))
        {
          XulPage.invokeAction(MplayerTimeShiftEpisodeView.this.renderContext.getLayout(), "appEvents:rightAreaBindingFinish");
          MplayerTimeShiftEpisodeView.this.dealSelectItem();
        }
        while (true)
        {
          return;
          if ("categoryBindingFinished".equals(paramAnonymousString3))
          {
            dealFirstSelected();
            return;
          }
          try
          {
            Command localCommand = (Command)new Gson().fromJson(paramAnonymousString3, Command.class);
            MplayerTimeShiftEpisodeView.access$502(MplayerTimeShiftEpisodeView.this, localCommand.categoryId);
            if (!MplayerTimeShiftEpisodeView.this.selectedChanelItemId.equals(localCommand.id))
            {
              MplayerTimeShiftEpisodeView.access$602(MplayerTimeShiftEpisodeView.this, localCommand.id);
              if (MplayerTimeShiftEpisodeView.this.lsnr != null)
              {
                if (paramAnonymousXulView != null)
                {
                  String str1 = ReportArea.getValue(paramAnonymousXulView.getAttrString("page_area"));
                  if (!TextUtils.isEmpty(str1))
                    ReportInfo.getInstance().setEntranceSrc(str1);
                }
                if ("common_channel".equals(localCommand.categoryId));
                for (String str2 = MplayerTimeShiftEpisodeView.this.findCommonChannelCategoryId(localCommand.id); ; str2 = localCommand.categoryId)
                {
                  MplayerTimeShiftEpisodeView.this.lsnr.onChannelItemClick(str2, MplayerTimeShiftEpisodeView.this.selectedChanelItemId);
                  return;
                }
              }
            }
          }
          catch (Exception localException)
          {
          }
        }
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        Logger.d(MplayerTimeShiftEpisodeView.TAG, "onRenderIsReady");
        if ((MplayerTimeShiftEpisodeView.this.isBindData) && (!MplayerTimeShiftEpisodeView.this.isCategoryRefreshed))
        {
          Logger.d(MplayerTimeShiftEpisodeView.TAG, "onRenderIsReady refreshBinding category");
          MplayerTimeShiftEpisodeView.this.renderContext.refreshBinding("category", "file:///.app/categoryList/category");
        }
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
        String categoryId;
        String id;
        String index;
        String name;

        Command()
        {
        }

        public String toString()
        {
          return "Command{categoryId='" + this.categoryId + '\'' + ", id='" + this.id + '\'' + ", index='" + this.index + '\'' + ", name='" + this.name + '\'' + '}';
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
    ((int)(i * localXulPage.getXScalar()));
    ((int)(j * localXulPage.getYScalar()));
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((int)(k * localXulPage.getXScalar()), (int)(m * localXulPage.getYScalar()));
    localLayoutParams.addRule(15);
    setLayoutParams(localLayoutParams);
  }

  private boolean isInCommonChannel(ChannelItemInfoV2 paramChannelItemInfoV2)
  {
    if ((this.commonChannels != null) && (this.commonChannels.size() > 0))
      return this.commonChannels.contains(paramChannelItemInfoV2);
    return false;
  }

  private String makeIdxString(int paramInt)
  {
    if (paramInt < 10)
      return "00" + paramInt;
    if (paramInt < 100)
      return "0" + paramInt;
    return String.valueOf(paramInt);
  }

  private String parseCategory(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    int i = paramString.lastIndexOf("/");
    if ((i >= 0) && (i < -1 + paramString.length()))
      return paramString.substring(i + 1);
    return "";
  }

  private void requestFocus(XulView paramXulView)
  {
    this.renderContext.getLayout().requestFocus(paramXulView);
  }

  private void writeChannelNode(XmlSerializer paramXmlSerializer, ChannelItemInfoV2 paramChannelItemInfoV2, String paramString)
    throws IOException
  {
    paramXmlSerializer.startTag(null, "channel");
    writeNodeValue(paramXmlSerializer, "name", paramChannelItemInfoV2.name);
    writeNodeValue(paramXmlSerializer, "id", paramChannelItemInfoV2.id);
    if ("common_channel".equals(paramString))
      writeNodeValue(paramXmlSerializer, "category_id", "common_channel");
    while (true)
    {
      writeNodeValue(paramXmlSerializer, "index", makeIdxString(paramChannelItemInfoV2.channelNum));
      paramXmlSerializer.endTag(null, "channel");
      return;
      writeNodeValue(paramXmlSerializer, "category_id", paramChannelItemInfoV2.categoryId);
    }
  }

  private void writeNodeValue(XmlSerializer paramXmlSerializer, String paramString1, String paramString2)
    throws IOException
  {
    if (paramString2 == null)
      paramString2 = "";
    paramXmlSerializer.startTag(null, paramString1);
    paramXmlSerializer.text(paramString2);
    paramXmlSerializer.endTag(null, paramString1);
  }

  public void addCommonChannel(ArrayList<ChannelItemInfoV2> paramArrayList)
  {
    this.commonChannels = paramArrayList;
  }

  public void bindData(MediaAssetsInfo paramMediaAssetsInfo, ChannelInfoV2 paramChannelInfoV2)
  {
    this.mediaAssetsInfo = paramMediaAssetsInfo;
    this.channelInfo = paramChannelInfoV2;
    checkData();
    this.isBindData = true;
    if ((this.renderContext != null) && (this.renderContext.isReady()))
    {
      Logger.d(TAG, "bindData refreshBinding category");
      this.renderContext.refreshBinding("category", "file:///.app/categoryList/category");
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Log.d("View", "MplayerTimeShiftEpisodeView dispatchKeyEvent " + paramKeyEvent.getKeyCode() + "hasFocus = " + hasFocus());
    if ((this.renderContext != null) && (this.renderContext.onKeyEvent(paramKeyEvent)))
    {
      Log.i("View", "MplayerTimeShiftEpisodeView dispatchKeyEvent 按键已处理");
      return true;
    }
    Log.i("View", "MplayerTimeShiftEpisodeView dispatchKeyEvent 按键未处理");
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public int findItem(Comparable<Object> paramComparable)
  {
    return 0;
  }

  public ByteArrayInputStream getCategoryData()
  {
    if ((this.categoryMap == null) || (this.categoryMap.size() <= 0))
      return null;
    this.isCategoryRefreshed = true;
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "root");
      localXmlSerializer.startTag(null, "category_list");
      if ((this.commonChannels != null) && (this.commonChannels.size() > 0))
      {
        localXmlSerializer.startTag(null, "category");
        writeNodeValue(localXmlSerializer, "name", "常用频道");
        writeNodeValue(localXmlSerializer, "id", "common_channel");
        localXmlSerializer.endTag(null, "category");
      }
      Iterator localIterator = this.categoryMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localXmlSerializer.startTag(null, "category");
        writeNodeValue(localXmlSerializer, "name", (String)localEntry.getValue());
        writeNodeValue(localXmlSerializer, "id", (String)localEntry.getKey());
        localXmlSerializer.endTag(null, "category");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localXmlSerializer.endTag(null, "category_list");
    localXmlSerializer.endTag(null, "root");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  public String getCurrentCategoryId()
  {
    return this.playingCategoryId;
  }

  public int getItemCount()
  {
    if ((this.channelInfo != null) && (this.channelInfo.channelList != null))
      return this.channelInfo.channelList.size();
    return 0;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
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

  public void removeAllItems()
  {
  }

  public void setOnChannelItemClickListener(onChannelItemClickListener paramonChannelItemClickListener)
  {
    this.lsnr = paramonChannelItemClickListener;
  }

  public void setSelectedItem(int paramInt, boolean paramBoolean)
  {
    ChannelItemInfoV2 localChannelItemInfoV22;
    if (paramBoolean)
      if ((this.commonChannels != null) && (this.commonChannels.size() > paramInt))
      {
        localChannelItemInfoV22 = (ChannelItemInfoV2)this.commonChannels.get(paramInt);
        this.playingCategoryId = "common_channel";
        this.selectedCategoryId = "common_channel";
      }
    ChannelItemInfoV2 localChannelItemInfoV21;
    for (this.selectedChanelItemId = localChannelItemInfoV22.id; ; this.selectedChanelItemId = localChannelItemInfoV21.id)
    {
      do
      {
        if ((this.renderContext != null) && (this.renderContext.isReady()))
          dealSelectItem();
        return;
      }
      while ((this.channelInfo == null) || (this.channelInfo.channelList == null) || (this.channelInfo.channelList.size() <= paramInt));
      localChannelItemInfoV21 = (ChannelItemInfoV2)this.channelInfo.channelList.get(paramInt);
      this.playingCategoryId = localChannelItemInfoV21.categoryId;
      this.selectedCategoryId = localChannelItemInfoV21.categoryId;
    }
  }

  public void setSelectedItem(Comparable<Object> paramComparable)
  {
  }

  public void setVisibility(int paramInt)
  {
    XulArrayList localXulArrayList1;
    if ((paramInt != 0) && (this.renderContext != null))
    {
      XulArea localXulArea1 = (XulArea)this.renderContext.findItemById("left_content_layer");
      if (localXulArea1 == null);
      do
      {
        return;
        localXulArrayList1 = XulPage.findItemsByClass(localXulArea1, "category_label");
      }
      while (localXulArrayList1 == null);
    }
    for (int i = 0; ; i++)
    {
      XulView localXulView1;
      if (i < localXulArrayList1.size())
      {
        localXulView1 = (XulView)localXulArrayList1.get(i);
        if (!this.playingCategoryId.equals(localXulView1.getAttrString("categoryId")))
          continue;
        if (!localXulView1.hasClass("category_label_selected"))
          break label180;
        XulArea localXulArea2 = (XulArea)this.renderContext.findItemById("right_content_layer");
        XulArrayList localXulArrayList2 = XulPage.findItemsByClass(localXulArea2, "channel_item");
        if (localXulArrayList2 != null)
        {
          Iterator localIterator = localXulArrayList2.iterator();
          while (localIterator.hasNext())
          {
            XulView localXulView2 = (XulView)localIterator.next();
            if (localXulView2.hasClass("channel_item_selected"))
            {
              localXulArea2.setDynamicFocus(localXulView2);
              requestFocus(localXulView2);
            }
          }
        }
      }
      while (true)
      {
        super.setVisibility(paramInt);
        return;
        label180: requestFocus(localXulView1);
      }
    }
  }

  public static abstract interface onChannelItemClickListener
  {
    public abstract void onChannelItemClick(String paramString1, String paramString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerTimeShiftEpisodeView
 * JD-Core Version:    0.6.2
 */