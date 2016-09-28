package com.starcor.xul;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.xul.Factory.XulFactory;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ResultBuilder;
import com.starcor.xul.Factory.XulFactory.ResultBuilderContext;
import com.starcor.xul.Prop.XulBinding;
import com.starcor.xul.Prop.XulBinding._Builder;
import com.starcor.xul.Script.IScript;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.V8.V8ScriptContext;
import com.starcor.xul.Script.XulScriptFactory;
import com.starcor.xul.Utils.XulBindingSelector;
import com.starcor.xul.Utils.XulBindingSelector.IXulDataSelectContext;
import com.starcor.xul.Utils.XulCachedHashMap;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XulManager
{
  public static final XulFactory.ItemBuilder CommonDummyBuilder;
  public static final boolean DEBUG = false;
  public static final boolean DEBUG_BINDING = false;
  public static final boolean DEBUG_CANVAS_SAVE_RESTORE = false;
  public static final boolean DEBUG_V8_ENGINE = false;
  public static final boolean DEBUG_XUL_WORKER = false;
  public static final int HIT_EVENT_DOWN = 0;
  public static final int HIT_EVENT_DUMMY = -1;
  public static final int HIT_EVENT_SCROLL = 8;
  public static final int HIT_EVENT_UP = 1;
  public static final boolean PERFORMANCE_BENCH = false;
  public static final int SIZE_AUTO = 2147483646;
  public static final int SIZE_MATCH_CONTENT = 2147483645;
  public static final int SIZE_MATCH_PARENT = 2147483644;
  public static final int SIZE_MAX = 2147483547;
  private static final String TAG = XulManager.class.getSimpleName();
  private static File _baseTmpFile;
  private static XulCachedHashMap<String, XulComponent> _components;
  private static ArrayList<XulBinding> _globalBinding;
  private static XulManager _instance;
  private static IScriptContext _lastScriptContext;
  private static String _lastScriptContextType;
  private static int _pageHeight;
  private static int _pageWidth;
  private static XulCachedHashMap<String, XulPage> _pages = new XulCachedHashMap(128);
  private static _ScriptBuilder _scriptBuilder;
  private static final XulCachedHashMap<String, IScriptContext> _scriptContextMap;
  private static ArrayList<XulSelect> _selectors;
  private static int _targetPageHeight;
  private static int _targetPageWidth;

  static
  {
    _components = new XulCachedHashMap(64);
    _selectors = new ArrayList();
    _globalBinding = new ArrayList();
    CommonDummyBuilder = new XulFactory.ItemBuilder()
    {
      public Object finalItem()
      {
        return super.finalItem();
      }

      public boolean initialize(String paramAnonymousString, XulFactory.Attributes paramAnonymousAttributes)
      {
        return true;
      }

      public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramAnonymousIPullParser, String paramAnonymousString1, String paramAnonymousString2, XulFactory.Attributes paramAnonymousAttributes)
      {
        Log.d(XulManager.TAG, "drop sub item: " + paramAnonymousString2);
        return this;
      }

      public boolean pushText(String paramAnonymousString, XulFactory.IPullParser paramAnonymousIPullParser)
      {
        return super.pushText(paramAnonymousString, paramAnonymousIPullParser);
      }
    };
    _pageWidth = 1280;
    _pageHeight = 720;
    _targetPageWidth = 1280;
    _targetPageHeight = 720;
    _lastScriptContext = null;
    _lastScriptContextType = "";
    _scriptContextMap = new XulCachedHashMap();
    XulFactory.registerBuilder(XulManager.class, new _Factory());
    V8ScriptContext.register();
  }

  private static void _updateSelectors()
  {
    for (int i = 0; i < _selectors.size(); i++)
      ((XulSelect)_selectors.get(i)).setPriorityLevel(i + 1, 0);
    Iterator localIterator = _pages.values().iterator();
    while (localIterator.hasNext())
      ((XulPage)localIterator.next()).updateSelectorPriorityLevel();
  }

  public static void addComponent(XulComponent paramXulComponent)
  {
    _components.put(paramXulComponent.getId(), paramXulComponent);
  }

  public static XulManager build(InputStream paramInputStream)
    throws Exception
  {
    return (XulManager)XulFactory.build(XulManager.class, paramInputStream, "");
  }

  public static XulManager build(InputStream paramInputStream, String paramString)
    throws Exception
  {
    return (XulManager)XulFactory.build(XulManager.class, paramInputStream, paramString);
  }

  public static XulManager build(byte[] paramArrayOfByte)
    throws Exception
  {
    return (XulManager)XulFactory.build(XulManager.class, paramArrayOfByte, "");
  }

  public static XulManager build(byte[] paramArrayOfByte, String paramString)
    throws Exception
  {
    return (XulManager)XulFactory.build(XulManager.class, paramArrayOfByte, paramString);
  }

  public static boolean clear()
  {
    if (_instance != null)
      synchronized (_instance)
      {
        _pages.clear();
        _components.clear();
        _selectors.clear();
        _globalBinding.clear();
      }
    return true;
  }

  public static XulRenderContext createXulRender(String paramString, XulRenderContext.IXulRenderHandler paramIXulRenderHandler)
  {
    return createXulRender(paramString, paramIXulRenderHandler, 0, 0);
  }

  public static XulRenderContext createXulRender(String paramString, XulRenderContext.IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2)
  {
    return createXulRender(paramString, paramIXulRenderHandler, paramInt1, paramInt2, false, false);
  }

  public static XulRenderContext createXulRender(String paramString, XulRenderContext.IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    return createXulRender(paramString, paramIXulRenderHandler, paramInt1, paramInt2, paramBoolean, false);
  }

  public static XulRenderContext createXulRender(String paramString, XulRenderContext.IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return createXulRender(paramString, paramIXulRenderHandler, paramInt1, paramInt2, paramBoolean1, paramBoolean2, false);
  }

  public static XulRenderContext createXulRender(String paramString, XulRenderContext.IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    XulUtils.ticketMarker localticketMarker = new XulUtils.ticketMarker("BENCH!! createXulRender(" + paramString + ") ", true);
    localticketMarker.mark();
    XulPage localXulPage = (XulPage)_pages.get(paramString);
    XulRenderContext localXulRenderContext = null;
    if (localXulPage != null)
      localXulRenderContext = new XulRenderContext(localXulPage, _selectors, _globalBinding, paramIXulRenderHandler, paramInt1, paramInt2, paramBoolean1, paramBoolean2, paramBoolean3);
    localticketMarker.mark("createXulRender");
    Log.d(TAG, localticketMarker.toString());
    return localXulRenderContext;
  }

  public static XulComponent getComponent(String paramString)
  {
    return (XulComponent)_components.get(paramString);
  }

  public static float getGlobalXScalar()
  {
    return _pageWidth / _targetPageWidth;
  }

  public static float getGlobalYScalar()
  {
    return _pageHeight / _targetPageHeight;
  }

  public static int getPageHeight()
  {
    return _pageHeight;
  }

  public static int getPageWidth()
  {
    return _pageWidth;
  }

  static _ScriptBuilder getScriptBuilder()
  {
    if (_scriptBuilder == null)
      _scriptBuilder = new _ScriptBuilder();
    return _scriptBuilder;
  }

  public static IScriptContext getScriptContext(String paramString)
  {
    if ((_lastScriptContext != null) && (_lastScriptContextType.equals(paramString)))
      return _lastScriptContext;
    synchronized (_scriptContextMap)
    {
      IScriptContext localIScriptContext1 = (IScriptContext)_scriptContextMap.get(paramString);
      if (localIScriptContext1 != null)
      {
        _lastScriptContext = localIScriptContext1;
        _lastScriptContextType = paramString;
        return localIScriptContext1;
      }
    }
    IScriptContext localIScriptContext2 = XulScriptFactory.createScriptContext(paramString);
    localIScriptContext2.init();
    _scriptContextMap.put(paramString, localIScriptContext2);
    _lastScriptContext = localIScriptContext2;
    _lastScriptContextType = paramString;
    return localIScriptContext2;
  }

  public static ArrayList<XulSelect> getSelectors()
  {
    return _selectors;
  }

  public static File getTempPath(String paramString1, String paramString2)
  {
    File localFile = new File(_baseTmpFile, paramString1);
    localFile.mkdirs();
    return new File(localFile, paramString2);
  }

  public static void initPage(String paramString)
  {
    XulPage localXulPage = (XulPage)_pages.get(paramString);
    if (localXulPage != null)
      localXulPage.initPage();
  }

  public static boolean isXulLoaded()
  {
    return !_pages.isEmpty();
  }

  public static boolean isXulLoaded(String paramString)
  {
    if (_pages.isEmpty());
    Iterator localIterator2;
    do
      while (!localIterator2.hasNext())
      {
        return false;
        if (TextUtils.isEmpty(paramString))
          return isXulLoaded();
        Iterator localIterator1 = _pages.values().iterator();
        while (localIterator1.hasNext())
          if (paramString.equals(((XulPage)localIterator1.next()).getOwnerId()))
            return true;
        localIterator2 = _components.values().iterator();
      }
    while (!paramString.equals(((XulComponent)localIterator2.next()).getOwnerId()));
    return true;
  }

  public static boolean isXulPageLoaded(String paramString)
  {
    return _pages.containsKey(paramString);
  }

  public static boolean loadXul(InputStream paramInputStream, String paramString)
  {
    XulUtils.ticketMarker localticketMarker = new XulUtils.ticketMarker("BENCH!! ", true);
    localticketMarker.mark();
    try
    {
      build(paramInputStream, paramString);
      _updateSelectors();
      localticketMarker.mark("loadXul");
      Log.d(TAG, localticketMarker.toString());
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean loadXul(String paramString1, String paramString2)
  {
    XulUtils.ticketMarker localticketMarker = new XulUtils.ticketMarker("BENCH!! ", true);
    localticketMarker.mark();
    try
    {
      build(paramString1.getBytes("utf-8"), paramString2);
      _updateSelectors();
      localticketMarker.mark("loadXul");
      Log.d(TAG, localticketMarker.toString());
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static ArrayList<XulDataNode> queryGlobalBinding(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    return XulBindingSelector.selectData(new XulBindingSelector.IXulDataSelectContext()
    {
      public XulBinding getBindingById(String paramAnonymousString)
      {
        int i = 0;
        int j = XulManager._globalBinding.size();
        while (i < j)
        {
          XulBinding localXulBinding = (XulBinding)XulManager._globalBinding.get(i);
          if (paramAnonymousString.equals(localXulBinding.getId()))
            return localXulBinding;
          i++;
        }
        return null;
      }

      public XulBinding getDefaultBinding()
      {
        return null;
      }

      public boolean isEmpty()
      {
        return (XulManager._globalBinding == null) || (XulManager._globalBinding.isEmpty());
      }
    }
    , paramString, localArrayList);
  }

  public static String queryGlobalBindingString(String paramString)
  {
    ArrayList localArrayList = queryGlobalBinding(paramString);
    if ((localArrayList == null) || (localArrayList.isEmpty()))
      return null;
    return ((XulDataNode)localArrayList.get(0)).getValue();
  }

  public static void setBaseTempPath(File paramFile)
  {
    _baseTmpFile = paramFile;
  }

  public static void setPageSize(int paramInt1, int paramInt2)
  {
    _pageWidth = paramInt1;
    _pageHeight = paramInt2;
  }

  public void addGlobalBinding(XulBinding paramXulBinding)
  {
    if (!_globalBinding.contains(paramXulBinding))
      _globalBinding.add(paramXulBinding);
  }

  public void addPage(XulPage paramXulPage)
  {
    _pages.put(paramXulPage.getId(), paramXulPage);
  }

  public void addSelector(XulSelect paramXulSelect)
  {
    _selectors.add(paramXulSelect);
  }

  static class _Builder extends XulFactory.ItemBuilder
  {
    private final XulFactory.ResultBuilderContext _ctx;

    public _Builder(XulFactory.ResultBuilderContext paramResultBuilderContext)
    {
      this._ctx = paramResultBuilderContext;
    }

    public Object finalItem()
    {
      return XulManager._instance;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      if (XulManager._instance == null)
        XulManager.access$102(new XulManager(null));
      String str = paramAttributes.getValue("screen");
      Pattern localPattern = Pattern.compile("(\\d+)x(\\d+)");
      if (TextUtils.isEmpty(str))
        str = "1280x720";
      Matcher localMatcher = localPattern.matcher(str);
      if (localMatcher.matches())
      {
        XulManager.access$302(XulUtils.tryParseInt(localMatcher.group(1)));
        XulManager.access$402(XulUtils.tryParseInt(localMatcher.group(2)));
        return true;
      }
      XulManager.access$302(1280);
      XulManager.access$402(720);
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      if (paramString2.equals("page"))
      {
        XulPage._Builder local_Builder = new XulPage._Builder(this._ctx, XulManager._instance, XulManager._targetPageWidth, XulManager._targetPageHeight);
        local_Builder.initialize(paramString2, paramAttributes);
        return local_Builder;
      }
      if (paramString2.equals("component"))
      {
        XulComponent._Builder local_Builder2 = XulComponent._Builder.create(this._ctx, XulManager._instance);
        local_Builder2.initialize(paramString2, paramAttributes);
        return local_Builder2;
      }
      if (paramString2.equals("selector"))
        return new XulFactory.ItemBuilder()
        {
          public Object finalItem()
          {
            return null;
          }

          public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramAnonymousIPullParser, String paramAnonymousString1, String paramAnonymousString2, XulFactory.Attributes paramAnonymousAttributes)
          {
            if (paramAnonymousString2.equals("select"))
            {
              XulSelect._Builder local_Builder = XulSelect._Builder.create(XulManager._Builder.this._ctx, XulManager._instance);
              local_Builder.initialize(paramAnonymousString2, paramAnonymousAttributes);
              return local_Builder;
            }
            return XulManager.CommonDummyBuilder;
          }

          public boolean pushText(String paramAnonymousString, XulFactory.IPullParser paramAnonymousIPullParser)
          {
            return super.pushText(paramAnonymousString, paramAnonymousIPullParser);
          }
        };
      if (paramString2.equals("binding"))
      {
        XulBinding._Builder local_Builder1 = XulBinding._Builder.create(XulManager._instance);
        local_Builder1.initialize(paramString2, paramAttributes);
        return local_Builder1;
      }
      if (paramString2.equals("script"))
      {
        XulManager._ScriptBuilder local_ScriptBuilder = XulManager.getScriptBuilder();
        local_ScriptBuilder.initialize(paramString2, paramAttributes);
        return local_ScriptBuilder;
      }
      if (paramString2.equals("import"));
      return XulManager.CommonDummyBuilder;
    }
  }

  static class _Factory extends XulFactory.ResultBuilder
  {
    public XulFactory.ItemBuilder build(XulFactory.ResultBuilderContext paramResultBuilderContext, String paramString, XulFactory.Attributes paramAttributes)
    {
      if (paramString.equals("starcor.xul"))
      {
        XulManager._Builder local_Builder = new XulManager._Builder(paramResultBuilderContext);
        local_Builder.initialize(paramString, paramAttributes);
        return local_Builder;
      }
      return null;
    }
  }

  static class _ScriptBuilder extends XulFactory.ItemBuilder
  {
    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      String str1 = paramAttributes.getValue("type");
      String str2 = paramAttributes.getValue("src");
      if (str1.startsWith("script/"))
      {
        IScriptContext localIScriptContext = XulManager.getScriptContext(str1.substring(7));
        if (localIScriptContext == null);
        InputStream localInputStream;
        do
        {
          return false;
          localInputStream = XulWorker.loadData(str2, true, new String[0]);
        }
        while (localInputStream == null);
        if (str2.startsWith("file:///.assets/"));
        IScript localIScript;
        for (String str3 = str2.substring(16); ; str3 = str2)
        {
          localIScript = localIScriptContext.compileScript(localInputStream, str3, 1);
          if (0 != 0)
            null.mark("compile");
          if (localIScript != null)
            break label130;
          if (0 == 0)
            break;
          Log.d("BENCH!!!", null.toString());
          return false;
        }
        label130: localIScript.run(localIScriptContext, null);
        if (0 != 0)
        {
          null.mark("run");
          Log.d("BENCH!!!", null.toString());
        }
      }
      return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulManager
 * JD-Core Version:    0.6.2
 */