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
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.encrypt.json.JSONObject;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class MplayerMenuView extends View
{
  private static final String BINDING_MAIN_MENU = "file:///.app/main_menu_list/main_menu";
  private static final String BINDING_SUB_MENU = "file:///.app/sub_menu_list/sub_menu";
  private static final String DOUBLE_LINE_TAG = "D";
  public static final int ITEM_AUDIO_TRACK = 32;
  public static final int ITEM_BULLET_SCREEN = 512;
  public static final int ITEM_CHANGE_SCREEN_RATIO = 2;
  public static final int ITEM_CHANGE_VIDEO_DEFINITION = 4;
  public static final int ITEM_INTERACTIVE = 128;
  public static final int ITEM_JUMP_HEAD_OR_TAIL = 1;
  public static final int ITEM_LIKE_VIDEO = 16;
  public static final int ITEM_PLAY_MODE = 256;
  public static final int ITEM_RECOMMEND_VIDEO = 8;
  public static final int ITEM_SUBTITLE = 64;
  public static final String KEY_BULLET_SCREEN = "bullet_screen";
  public static final String KEY_DEFINITION = "definition";
  public static final String KEY_INTERACTIVE = "interactive";
  public static final String KEY_JUMP_HEAD_OR_TAIL = "jump_head_tail";
  public static final String KEY_LIKE = "like";
  public static final String KEY_PLAY_MODE = "play_mode";
  public static final String KEY_RECOMMEND = "recommend";
  public static final String KEY_SCREEN_RATIO = "screen_ratio";
  private static final String SINGLE_LINE_TAG = "S";
  private static final String TAG = MplayerMenuView.class.getSimpleName();
  private HashMap<String, String> definition = new HashMap();
  boolean firstTimeShow = true;
  private int itemFlag;
  ArrayList<MainItem> items = new ArrayList();
  private OnItemClickListener lsnr;
  private ByteArrayInputStream mainMenuByteArrayInputStream;
  private ArrayList<MenuItem> menuItemsStatesList = new ArrayList();
  private Rect rect;
  private XulRenderContext renderContext;
  HashMap<Integer, String> stateMap = new HashMap();
  private String subMenuIdToChange;
  private ByteArrayInputStream subMenuInputStream;

  public MplayerMenuView(Context paramContext)
  {
    super(paramContext);
    setFocusable(true);
    initStateName();
  }

  public MplayerMenuView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setFocusable(true);
    initStateName();
  }

  public MplayerMenuView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setFocusable(true);
    initStateName();
  }

  private void fillBulletScreenStates()
  {
    Iterator localIterator = this.menuItemsStatesList.iterator();
    while (localIterator.hasNext())
    {
      MenuItem localMenuItem = (MenuItem)localIterator.next();
      if (localMenuItem.key == 512)
      {
        this.definition = localMenuItem.state;
        this.subMenuInputStream = getSubMenuItemsData();
        if (this.renderContext != null)
          this.renderContext.refreshBinding("sub_menu", "file:///.app/sub_menu_list/sub_menu");
      }
    }
  }

  private void fillDefinitionStates()
  {
    Iterator localIterator = this.menuItemsStatesList.iterator();
    while (localIterator.hasNext())
    {
      MenuItem localMenuItem = (MenuItem)localIterator.next();
      if (localMenuItem.key == 4)
      {
        this.definition = localMenuItem.state;
        this.subMenuInputStream = getSubMenuItemsData();
        if (this.renderContext != null)
          this.renderContext.refreshBinding("sub_menu", "file:///.app/sub_menu_list/sub_menu");
      }
    }
  }

  private LinkedHashMap<String, String> findCurrentSubMenuData()
  {
    if (TextUtils.isEmpty(this.subMenuIdToChange));
    while (true)
    {
      return null;
      int i = this.menuItemsStatesList.size();
      for (int j = 0; j < i; j++)
      {
        MenuItem localMenuItem = (MenuItem)this.menuItemsStatesList.get(j);
        if (this.subMenuIdToChange.equalsIgnoreCase(String.valueOf(localMenuItem.key)))
          return localMenuItem.state;
      }
    }
  }

  private String findKeyById(int paramInt)
  {
    int i = this.items.size();
    for (int j = 0; j < i; j++)
    {
      MainItem localMainItem = (MainItem)this.items.get(j);
      if (localMainItem.id == paramInt)
        return localMainItem.key;
    }
    return "";
  }

  private String findMenuItemNameByValue(int paramInt, String paramString)
  {
    Iterator localIterator = this.menuItemsStatesList.iterator();
    while (localIterator.hasNext())
    {
      MenuItem localMenuItem = (MenuItem)localIterator.next();
      if (localMenuItem.key == paramInt)
        return (String)localMenuItem.state.get(paramString);
    }
    return null;
  }

  private ByteArrayInputStream getMainMenuItemsData(ArrayList<MainItem> paramArrayList)
  {
    if (paramArrayList == null)
      return null;
    while (true)
    {
      int i;
      try
      {
        XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "root");
        localXmlSerializer.startTag(null, "menu_list");
        i = 0;
        if (i < paramArrayList.size())
        {
          MainItem localMainItem = (MainItem)paramArrayList.get(i);
          if (localMainItem != null)
          {
            localXmlSerializer.startTag(null, "item");
            localXmlSerializer.attribute("", "type", localMainItem.type);
            writeNodeValue(localXmlSerializer, "title", localMainItem.title);
            writeNodeValue(localXmlSerializer, "desc", localMainItem.desc);
            writeNodeValue(localXmlSerializer, "state", localMainItem.value);
            writeNodeValue(localXmlSerializer, "key", localMainItem.key);
            writeNodeValue(localXmlSerializer, "id", localMainItem.id + "");
            localXmlSerializer.endTag(null, "item");
          }
        }
        else
        {
          localXmlSerializer.endTag(null, "menu_list");
          localXmlSerializer.endTag(null, "root");
          localXmlSerializer.endDocument();
          localXmlSerializer.flush();
          ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
          return localByteArrayInputStream;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      i++;
    }
  }

  private ByteArrayInputStream getSubMenuItemsData()
  {
    LinkedHashMap localLinkedHashMap = findCurrentSubMenuData();
    if (localLinkedHashMap == null)
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
      localXmlSerializer.startTag(null, "menu_list");
      Iterator localIterator = localLinkedHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "desc", (String)localLinkedHashMap.get(str));
        writeNodeValue(localXmlSerializer, "value", str);
        localXmlSerializer.endTag(null, "item");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localXmlSerializer.endTag(null, "menu_list");
    localXmlSerializer.endTag(null, "root");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private void initStateName()
  {
    this.menuItemsStatesList.clear();
    MenuItem localMenuItem1 = new MenuItem(null);
    localMenuItem1.key = 1;
    LinkedHashMap localLinkedHashMap1 = new LinkedHashMap();
    localLinkedHashMap1.put("on", "允许");
    localLinkedHashMap1.put("off", "不允许");
    localMenuItem1.state = localLinkedHashMap1;
    this.menuItemsStatesList.add(localMenuItem1);
    MenuItem localMenuItem2 = new MenuItem(null);
    localMenuItem2.key = 2;
    LinkedHashMap localLinkedHashMap2 = new LinkedHashMap();
    localLinkedHashMap2.put("full", "全屏");
    localLinkedHashMap2.put("16v9", "16 : 9");
    localLinkedHashMap2.put("4v3", "4 : 3");
    localLinkedHashMap2.put("235", "2.35 : 1");
    localLinkedHashMap2.put("default", "原始比例");
    localMenuItem2.state = localLinkedHashMap2;
    this.menuItemsStatesList.add(localMenuItem2);
    MenuItem localMenuItem3 = new MenuItem(null);
    localMenuItem3.key = 256;
    LinkedHashMap localLinkedHashMap3 = new LinkedHashMap();
    localLinkedHashMap3.put("list_cycle", "列表循环");
    localLinkedHashMap3.put("random", "随机播放 ");
    localLinkedHashMap3.put("single_cycle", "单个循环");
    localMenuItem3.state = localLinkedHashMap3;
    this.menuItemsStatesList.add(localMenuItem3);
    MenuItem localMenuItem4 = new MenuItem(null);
    localMenuItem4.key = 16;
    LinkedHashMap localLinkedHashMap4 = new LinkedHashMap();
    localLinkedHashMap4.put("dislike", "取消喜欢");
    localLinkedHashMap4.put("like", "喜欢");
    localMenuItem4.state = localLinkedHashMap4;
    this.menuItemsStatesList.add(localMenuItem4);
    MenuItem localMenuItem5 = new MenuItem(null);
    localMenuItem5.key = 128;
    this.menuItemsStatesList.add(localMenuItem5);
    MenuItem localMenuItem6 = new MenuItem(null);
    localMenuItem6.key = 512;
    LinkedHashMap localLinkedHashMap5 = new LinkedHashMap();
    localLinkedHashMap5.put("open", "打开");
    localLinkedHashMap5.put("close", "关闭");
    localMenuItem6.state = localLinkedHashMap5;
    this.menuItemsStatesList.add(localMenuItem6);
    MenuItem localMenuItem7 = new MenuItem(null);
    localMenuItem7.key = 8;
    LinkedHashMap localLinkedHashMap6 = new LinkedHashMap();
    localLinkedHashMap6.put("on", "我要推荐");
    localMenuItem7.state = localLinkedHashMap6;
    this.menuItemsStatesList.add(localMenuItem7);
  }

  private void initView()
  {
    this.renderContext = XulManager.createXulRender("MplayerMenuView", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        Logger.d(MplayerMenuView.TAG, "getAppData path: " + paramAnonymousString);
        if ("sub_menu_list/sub_menu".equalsIgnoreCase(paramAnonymousString))
        {
          MplayerMenuView.access$602(MplayerMenuView.this, MplayerMenuView.this.getSubMenuItemsData());
          return MplayerMenuView.this.subMenuInputStream;
        }
        if ("main_menu_list/main_menu".equals(paramAnonymousString))
        {
          MplayerMenuView.access$802(MplayerMenuView.this, MplayerMenuView.this.getMainMenuItemsData(MplayerMenuView.this.items));
          return MplayerMenuView.this.mainMenuByteArrayInputStream;
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
          MplayerMenuView.this.invalidate();
          return;
        }
        MplayerMenuView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        Logger.i(MplayerMenuView.TAG, "onDoAction action: " + paramAnonymousString1 + ", type: " + paramAnonymousString2 + ", command：" + paramAnonymousString3);
        Object localObject = null;
        if (paramAnonymousString3 != null);
        try
        {
          JSONObject localJSONObject = new JSONObject(paramAnonymousString3);
          localObject = localJSONObject;
          if (paramAnonymousString1.equals("refreshSubMenu"))
            if (localObject != null)
            {
              String str4 = localObject.optString("id");
              if ((!TextUtils.isEmpty(str4)) && (!str4.equals(MplayerMenuView.this.subMenuIdToChange)))
              {
                MplayerMenuView.access$302(MplayerMenuView.this, str4);
                MplayerMenuView.this.renderContext.refreshBinding("sub_menu", "file:///.app/sub_menu_list/sub_menu");
              }
            }
          String str1;
          do
          {
            do
            {
              String str2;
              do
              {
                do
                {
                  do
                  {
                    return;
                    if (!paramAnonymousString1.equals("refreshMainMenu"))
                      break;
                  }
                  while (localObject == null);
                  str2 = localObject.optString("value");
                }
                while (TextUtils.isEmpty(str2));
                MplayerMenuView.this.setItemState(Integer.parseInt(MplayerMenuView.this.subMenuIdToChange), str2);
              }
              while (MplayerMenuView.this.lsnr == null);
              String str3 = MplayerMenuView.this.findKeyById(Integer.valueOf(MplayerMenuView.this.subMenuIdToChange).intValue());
              MplayerMenuView.this.lsnr.onItemClick(str3, str2);
              return;
            }
            while ((!paramAnonymousString1.equals("mainMenuClick")) || (localObject == null));
            str1 = localObject.optString("key");
          }
          while (MplayerMenuView.this.lsnr == null);
          MplayerMenuView.this.lsnr.onItemClick(str1, "");
          return;
        }
        catch (JSONException localJSONException)
        {
          while (true)
            localObject = null;
        }
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        MplayerMenuView.this.restoreToLeftArea();
        Logger.d(MplayerMenuView.TAG, "onRenderIsReady...");
        MplayerMenuView.this.renderContext.refreshBinding("main_menu", "file:///.app/main_menu_list/main_menu");
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
        String key;
        String value;

        Command()
        {
        }

        public String toString()
        {
          return "key = " + this.key + ", value = " + this.value;
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

  private void refreshMainMenuImpl()
  {
    this.items.clear();
    if (hasItem(4).booleanValue())
    {
      MainItem localMainItem1 = new MainItem();
      localMainItem1.type = "D";
      localMainItem1.title = "清晰度";
      String str1 = (String)this.stateMap.get(Integer.valueOf(4));
      localMainItem1.desc = findMenuItemNameByValue(4, str1);
      localMainItem1.value = str1;
      localMainItem1.key = "definition";
      localMainItem1.id = 4;
      this.items.add(localMainItem1);
    }
    if (hasItem(2).booleanValue())
    {
      MainItem localMainItem2 = new MainItem();
      localMainItem2.type = "D";
      localMainItem2.title = "画面比例";
      String str2 = (String)this.stateMap.get(Integer.valueOf(2));
      localMainItem2.desc = findMenuItemNameByValue(2, str2);
      localMainItem2.value = str2;
      localMainItem2.key = "screen_ratio";
      localMainItem2.id = 2;
      this.items.add(localMainItem2);
    }
    if (hasItem(1).booleanValue())
    {
      MainItem localMainItem3 = new MainItem();
      localMainItem3.type = "D";
      localMainItem3.title = "跳过片头片尾";
      String str3 = (String)this.stateMap.get(Integer.valueOf(1));
      localMainItem3.desc = findMenuItemNameByValue(1, str3);
      localMainItem3.value = str3;
      localMainItem3.key = "jump_head_tail";
      localMainItem3.id = 1;
      this.items.add(localMainItem3);
    }
    if (hasItem(512).booleanValue())
    {
      MainItem localMainItem4 = new MainItem();
      localMainItem4.type = "D";
      localMainItem4.title = "弹幕";
      String str4 = (String)this.stateMap.get(Integer.valueOf(512));
      localMainItem4.desc = findMenuItemNameByValue(512, str4);
      localMainItem4.value = str4;
      localMainItem4.key = "bullet_screen";
      localMainItem4.id = 512;
      this.items.add(localMainItem4);
    }
    if (hasItem(256).booleanValue())
    {
      MainItem localMainItem5 = new MainItem();
      localMainItem5.type = "D";
      localMainItem5.title = "播放顺序";
      String str5 = (String)this.stateMap.get(Integer.valueOf(256));
      localMainItem5.desc = findMenuItemNameByValue(256, str5);
      localMainItem5.value = str5;
      localMainItem5.key = "play_mode";
      localMainItem5.id = 256;
      this.items.add(localMainItem5);
    }
    if (hasItem(16).booleanValue())
    {
      MainItem localMainItem6 = new MainItem();
      localMainItem6.type = "S";
      localMainItem6.title = "";
      String str6 = (String)this.stateMap.get(Integer.valueOf(16));
      localMainItem6.desc = findMenuItemNameByValue(16, str6);
      localMainItem6.value = str6;
      localMainItem6.key = "like";
      localMainItem6.id = 16;
      this.items.add(localMainItem6);
    }
    if (hasItem(128).booleanValue())
    {
      MainItem localMainItem7 = new MainItem();
      localMainItem7.type = "S";
      localMainItem7.title = "互动";
      localMainItem7.desc = "互动";
      localMainItem7.value = "";
      localMainItem7.key = "interactive";
      localMainItem7.id = 128;
      this.items.add(localMainItem7);
    }
    if (hasItem(8).booleanValue())
    {
      MainItem localMainItem8 = new MainItem();
      localMainItem8.type = "S";
      localMainItem8.title = "";
      String str7 = (String)this.stateMap.get(Integer.valueOf(8));
      localMainItem8.desc = findMenuItemNameByValue(8, str7);
      localMainItem8.value = str7;
      localMainItem8.key = "recommend";
      localMainItem8.id = 8;
      this.items.add(localMainItem8);
    }
    if ((this.renderContext != null) && (this.renderContext.isReady()))
    {
      Logger.d(TAG, "menu render is ready. refresh..");
      post(new Runnable()
      {
        public void run()
        {
          Logger.d(MplayerMenuView.TAG, "post refresh menu...");
          if (MplayerMenuView.this.renderContext != null)
            MplayerMenuView.this.renderContext.refreshBinding("main_menu", "file:///.app/main_menu_list/main_menu");
        }
      });
    }
  }

  private void restoreToLeftArea()
  {
    if (this.renderContext != null)
      this.renderContext.onKeyEvent(new KeyEvent(0, 21));
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

  public void addItemFlag(int paramInt)
  {
    this.itemFlag = (paramInt | this.itemFlag);
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

  public int getItemFlag()
  {
    return this.itemFlag;
  }

  public Boolean hasItem(int paramInt)
  {
    if ((paramInt & this.itemFlag) != 0);
    for (boolean bool = true; ; bool = false)
      return Boolean.valueOf(bool);
  }

  public boolean isRenderReady()
  {
    return (this.renderContext != null) && (this.renderContext.isReady());
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

  public void refreshView()
  {
    Logger.d(TAG, "refreshView...");
    refreshMainMenuImpl();
  }

  public void removeItemByFlag(int paramInt)
  {
    this.itemFlag &= (paramInt ^ 0xFFFFFFFF);
    restoreToLeftArea();
  }

  public void setItemFlags(int paramInt)
  {
    this.itemFlag = paramInt;
  }

  public void setItemState(int paramInt, String paramString)
  {
    if (hasItem(paramInt).booleanValue())
      this.stateMap.put(Integer.valueOf(paramInt), paramString);
  }

  public void setItemStates(int paramInt, LinkedHashMap<String, String> paramLinkedHashMap)
  {
    Iterator localIterator = this.menuItemsStatesList.iterator();
    MenuItem localMenuItem2;
    do
    {
      boolean bool = localIterator.hasNext();
      localObject = null;
      if (!bool)
        break;
      localMenuItem2 = (MenuItem)localIterator.next();
    }
    while (localMenuItem2.key != paramInt);
    Object localObject = localMenuItem2;
    if (localObject != null)
      this.menuItemsStatesList.remove(localObject);
    MenuItem localMenuItem1 = new MenuItem(null);
    localMenuItem1.key = paramInt;
    localMenuItem1.state = paramLinkedHashMap;
    this.menuItemsStatesList.add(localMenuItem1);
    if (paramInt == 4)
      fillDefinitionStates();
    while (paramInt != 512)
      return;
    fillBulletScreenStates();
  }

  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.lsnr = paramOnItemClickListener;
  }

  public void setVisibility(int paramInt)
  {
    restoreToLeftArea();
    if ((paramInt != 0) && (this.renderContext != null))
    {
      XulArea localXulArea = (XulArea)this.renderContext.findItemById("right_content_area");
      if (localXulArea != null)
      {
        XulArrayList localXulArrayList = XulPage.findItemsByClass(localXulArea, "sub_menu_item");
        if (localXulArrayList != null)
        {
          Iterator localIterator = localXulArrayList.iterator();
          while (localIterator.hasNext())
          {
            XulView localXulView = (XulView)localIterator.next();
            if (localXulView.hasClass("sub_menu_item_selected"))
            {
              localXulArea.setDynamicFocus(localXulView);
              XulSliderAreaWrapper.fromXulView(localXulArea).makeChildVisible(localXulView);
            }
          }
        }
      }
    }
    super.setVisibility(paramInt);
  }

  class MainItem
  {
    String desc;
    int id;
    String key;
    String title;
    String type;
    String value;

    MainItem()
    {
    }
  }

  private class MenuItem
  {
    int key;
    LinkedHashMap<String, String> state = new LinkedHashMap();

    private MenuItem()
    {
    }
  }

  public static abstract interface OnItemClickListener
  {
    public abstract void onItemClick(String paramString1, String paramString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerMenuView
 * JD-Core Version:    0.6.2
 */