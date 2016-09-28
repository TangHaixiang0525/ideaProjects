package com.starcor.xul.Prop;

import android.text.TextUtils;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.Factory.XulFactory.TextParser;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulDataNode._Builder;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import java.io.InputStream;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class XulBinding extends XulProp
{
  static XulFactory.TextParser _textParser = new XulFactory.TextParser();
  XulDataNode _data;
  String _dataUrl;
  String _id;
  boolean _preload = false;
  BindingStatus _state = BindingStatus.INITIAL;

  public XulBinding()
  {
  }

  public XulBinding(XulBinding paramXulBinding)
  {
    super(paramXulBinding);
    this._id = paramXulBinding._id;
    this._dataUrl = paramXulBinding._dataUrl;
    this._state = paramXulBinding._state;
    this._data = paramXulBinding._data;
    this._preload = paramXulBinding._preload;
  }

  private void _updateStateOnDataReady()
  {
    if (this._state == BindingStatus.REFRESHING)
    {
      this._state = BindingStatus.UPDATED;
      return;
    }
    this._state = BindingStatus.READY;
  }

  public static XulBinding createBinding(String paramString)
  {
    XulBinding localXulBinding = new XulBinding();
    localXulBinding._id = paramString;
    return localXulBinding;
  }

  static void parseJsonObject(Object paramObject, XulFactory.ItemBuilder paramItemBuilder, String paramString)
  {
    if ((paramObject instanceof JSONArray))
    {
      if (TextUtils.isEmpty(paramString))
        paramString = "array";
      XulFactory.ItemBuilder localItemBuilder3 = paramItemBuilder.pushSubItem(null, null, paramString, null);
      localItemBuilder3.initialize(paramString, null);
      JSONArray localJSONArray = (JSONArray)paramObject;
      int i = 0;
      while (true)
      {
        if (i >= localJSONArray.length())
          return;
        try
        {
          parseJsonObject(localJSONArray.get(i), localItemBuilder3, "value");
          i++;
        }
        catch (JSONException localJSONException2)
        {
          while (true)
            localJSONException2.printStackTrace();
        }
      }
    }
    if ((paramObject instanceof JSONObject))
    {
      if (TextUtils.isEmpty(paramString))
        paramString = "object";
      XulFactory.ItemBuilder localItemBuilder2 = paramItemBuilder.pushSubItem(null, null, paramString, null);
      localItemBuilder2.initialize(paramString, null);
      JSONObject localJSONObject = (JSONObject)paramObject;
      Iterator localIterator = localJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        try
        {
          parseJsonObject(localJSONObject.get(str), localItemBuilder2, str);
        }
        catch (JSONException localJSONException1)
        {
          localJSONException1.printStackTrace();
        }
      }
    }
    XulFactory.ItemBuilder localItemBuilder1 = paramItemBuilder.pushSubItem(null, null, paramString, null);
    localItemBuilder1.initialize(paramString, null);
    if (paramObject != null)
    {
      _textParser.text = String.valueOf(paramObject);
      localItemBuilder1.pushText(null, _textParser);
    }
    localItemBuilder1.finalItem();
  }

  public XulDataNode getData()
  {
    return this._data;
  }

  public String getDataUrl()
  {
    return this._dataUrl;
  }

  public String getId()
  {
    return this._id;
  }

  public boolean isDataReady()
  {
    return this._state == BindingStatus.READY;
  }

  public boolean isPreloadBinding()
  {
    return this._preload;
  }

  public boolean isRefreshing()
  {
    return this._state == BindingStatus.REFRESHING;
  }

  public boolean isRemoteData()
  {
    return !TextUtils.isEmpty(this._dataUrl);
  }

  public boolean isUpdated()
  {
    return this._state == BindingStatus.UPDATED;
  }

  public XulBinding makeClone()
  {
    return new XulBinding(this);
  }

  public boolean markReady()
  {
    if (this._state == BindingStatus.UPDATED)
    {
      this._state = BindingStatus.READY;
      return true;
    }
    return false;
  }

  public void refreshBinding()
  {
    this._state = BindingStatus.REFRESHING;
  }

  public void refreshBinding(XulDataNode paramXulDataNode)
  {
    setData(paramXulDataNode);
    this._state = BindingStatus.UPDATED;
  }

  public void refreshBinding(InputStream paramInputStream)
  {
    setData(paramInputStream);
    this._state = BindingStatus.UPDATED;
  }

  public void refreshBinding(String paramString)
  {
    this._dataUrl = paramString;
    this._data = null;
    this._state = BindingStatus.REFRESHING;
  }

  public void setData(XulDataNode paramXulDataNode)
  {
    _updateStateOnDataReady();
    this._data = paramXulDataNode;
    this._data.setOwnerBinding(this);
  }

  // ERROR //
  public void setData(InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 162	com/starcor/xul/Prop/XulBinding:_updateStateOnDataReady	()V
    //   4: new 171	java/io/InputStreamReader
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 173	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: astore_2
    //   13: new 175	java/lang/StringBuilder
    //   16: dup
    //   17: invokespecial 176	java/lang/StringBuilder:<init>	()V
    //   20: astore_3
    //   21: sipush 1024
    //   24: newarray char
    //   26: astore 18
    //   28: aload_2
    //   29: aload 18
    //   31: invokevirtual 180	java/io/InputStreamReader:read	([C)I
    //   34: istore 19
    //   36: iload 19
    //   38: ifle +23 -> 61
    //   41: aload_3
    //   42: aload 18
    //   44: iconst_0
    //   45: iload 19
    //   47: invokevirtual 184	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   50: pop
    //   51: goto -23 -> 28
    //   54: astore 4
    //   56: aload 4
    //   58: invokevirtual 185	java/lang/Exception:printStackTrace	()V
    //   61: aload_3
    //   62: invokevirtual 188	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: astore 5
    //   67: aload 5
    //   69: invokestatic 69	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   72: istore 6
    //   74: iload 6
    //   76: ifeq +124 -> 200
    //   79: iconst_0
    //   80: istore 15
    //   82: iload 6
    //   84: ifne +207 -> 291
    //   87: iload 15
    //   89: bipush 60
    //   91: if_icmpne +200 -> 291
    //   94: aload 5
    //   96: invokevirtual 192	java/lang/String:getBytes	()[B
    //   99: invokestatic 196	com/starcor/xul/XulDataNode:build	([B)Lcom/starcor/xul/XulDataNode;
    //   102: astore 17
    //   104: aload 17
    //   106: astore 8
    //   108: aload 8
    //   110: ifnonnull +166 -> 276
    //   113: iload 6
    //   115: ifne +161 -> 276
    //   118: aload 5
    //   120: invokevirtual 199	java/lang/String:trim	()Ljava/lang/String;
    //   123: astore 10
    //   125: aload 10
    //   127: invokestatic 69	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   130: ifne +69 -> 199
    //   133: aload 10
    //   135: iconst_0
    //   136: invokevirtual 203	java/lang/String:charAt	(I)C
    //   139: bipush 123
    //   141: if_icmpeq +14 -> 155
    //   144: aload 10
    //   146: iconst_0
    //   147: invokevirtual 203	java/lang/String:charAt	(I)C
    //   150: bipush 91
    //   152: if_icmpne +47 -> 199
    //   155: new 205	org/json/JSONTokener
    //   158: dup
    //   159: aload 10
    //   161: invokespecial 207	org/json/JSONTokener:<init>	(Ljava/lang/String;)V
    //   164: invokevirtual 210	org/json/JSONTokener:nextValue	()Ljava/lang/Object;
    //   167: astore 11
    //   169: aload_0
    //   170: invokestatic 216	com/starcor/xul/Prop/XulBinding$_Builder:create	(Lcom/starcor/xul/Prop/XulBinding;)Lcom/starcor/xul/Prop/XulBinding$_Builder;
    //   173: astore 12
    //   175: aload 11
    //   177: instanceof 98
    //   180: ifne +11 -> 191
    //   183: aload 11
    //   185: instanceof 63
    //   188: ifeq +50 -> 238
    //   191: aload 11
    //   193: aload 12
    //   195: aconst_null
    //   196: invokestatic 93	com/starcor/xul/Prop/XulBinding:parseJsonObject	(Ljava/lang/Object;Lcom/starcor/xul/Factory/XulFactory$ItemBuilder;Ljava/lang/String;)V
    //   199: return
    //   200: aload 5
    //   202: iconst_0
    //   203: invokevirtual 203	java/lang/String:charAt	(I)C
    //   206: istore 15
    //   208: goto -126 -> 82
    //   211: aload 5
    //   213: invokestatic 219	com/starcor/xul/XulDataNode:build	(Ljava/lang/String;)Lcom/starcor/xul/XulDataNode;
    //   216: astore 16
    //   218: aload 16
    //   220: astore 8
    //   222: goto -114 -> 108
    //   225: astore 7
    //   227: aload 7
    //   229: invokevirtual 185	java/lang/Exception:printStackTrace	()V
    //   232: aconst_null
    //   233: astore 8
    //   235: goto -127 -> 108
    //   238: ldc 221
    //   240: invokestatic 224	com/starcor/xul/XulDataNode:obtainDataNode	(Ljava/lang/String;)Lcom/starcor/xul/XulDataNode;
    //   243: astore 13
    //   245: aload 13
    //   247: aload 5
    //   249: invokevirtual 227	com/starcor/xul/XulDataNode:setValue	(Ljava/lang/String;)Lcom/starcor/xul/XulDataNode;
    //   252: pop
    //   253: aload_0
    //   254: aload 13
    //   256: putfield 44	com/starcor/xul/Prop/XulBinding:_data	Lcom/starcor/xul/XulDataNode;
    //   259: aload_0
    //   260: getfield 44	com/starcor/xul/Prop/XulBinding:_data	Lcom/starcor/xul/XulDataNode;
    //   263: aload_0
    //   264: invokevirtual 167	com/starcor/xul/XulDataNode:setOwnerBinding	(Lcom/starcor/xul/Prop/XulBinding;)V
    //   267: return
    //   268: astore 9
    //   270: aload 9
    //   272: invokevirtual 185	java/lang/Exception:printStackTrace	()V
    //   275: return
    //   276: aload_0
    //   277: aload 8
    //   279: putfield 44	com/starcor/xul/Prop/XulBinding:_data	Lcom/starcor/xul/XulDataNode;
    //   282: aload_0
    //   283: getfield 44	com/starcor/xul/Prop/XulBinding:_data	Lcom/starcor/xul/XulDataNode;
    //   286: aload_0
    //   287: invokevirtual 167	com/starcor/xul/XulDataNode:setOwnerBinding	(Lcom/starcor/xul/Prop/XulBinding;)V
    //   290: return
    //   291: iload 6
    //   293: ifne -82 -> 211
    //   296: aconst_null
    //   297: astore 8
    //   299: iload 15
    //   301: bipush 123
    //   303: if_icmpeq -195 -> 108
    //   306: aconst_null
    //   307: astore 8
    //   309: iload 15
    //   311: bipush 91
    //   313: if_icmpeq -205 -> 108
    //   316: goto -105 -> 211
    //
    // Exception table:
    //   from	to	target	type
    //   21	28	54	java/lang/Exception
    //   28	36	54	java/lang/Exception
    //   41	51	54	java/lang/Exception
    //   94	104	225	java/lang/Exception
    //   200	208	225	java/lang/Exception
    //   211	218	225	java/lang/Exception
    //   118	155	268	java/lang/Exception
    //   155	191	268	java/lang/Exception
    //   191	199	268	java/lang/Exception
    //   238	267	268	java/lang/Exception
  }

  public void setDataUrl(String paramString)
  {
    this._dataUrl = paramString;
    this._data = null;
    if (this._state != BindingStatus.REFRESHING)
      this._state = BindingStatus.INITIAL;
  }

  public void setEmptyData()
  {
    _updateStateOnDataReady();
    this._data = null;
  }

  static enum BindingStatus
  {
    static
    {
      BindingStatus[] arrayOfBindingStatus = new BindingStatus[4];
      arrayOfBindingStatus[0] = INITIAL;
      arrayOfBindingStatus[1] = READY;
      arrayOfBindingStatus[2] = REFRESHING;
      arrayOfBindingStatus[3] = UPDATED;
    }
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulBinding _binding;
    XulFactory.ItemBuilder.FinalCallback<XulBinding> _callback;
    String _content;

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulBinding paramXulBinding)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulBinding);
      return local_Builder;
    }

    public static _Builder create(XulManager paramXulManager)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulManager);
      return local_Builder;
    }

    public static _Builder create(XulPage paramXulPage)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulPage);
      return local_Builder;
    }

    private void init(XulBinding paramXulBinding)
    {
      this._binding = paramXulBinding;
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulBinding paramAnonymousXulBinding)
        {
        }
      };
      this._content = null;
    }

    private void init(final XulManager paramXulManager)
    {
      this._binding = new XulBinding();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulBinding paramAnonymousXulBinding)
        {
          paramXulManager.addGlobalBinding(paramAnonymousXulBinding);
        }
      };
      this._content = null;
    }

    private void init(final XulPage paramXulPage)
    {
      this._binding = new XulBinding();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulBinding paramAnonymousXulBinding)
        {
          paramXulPage.addBinding(paramAnonymousXulBinding);
        }
      };
      this._content = null;
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._callback = null;
      _recycled_builder._binding = null;
      _recycled_builder._content = null;
    }

    public Object finalItem()
    {
      String str;
      if (this._content != null)
      {
        str = this._content.trim();
        this._content = null;
      }
      try
      {
        Object localObject = new JSONTokener(str).nextValue();
        if (((localObject instanceof JSONObject)) || ((localObject instanceof JSONArray)))
        {
          XulBinding.parseJsonObject(localObject, this, null);
          str = null;
        }
        label58: if (!TextUtils.isEmpty(str))
          this._binding._dataUrl = str;
        XulBinding localXulBinding = this._binding;
        XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
        recycle(this);
        localFinalCallback.onFinal(localXulBinding);
        return localXulBinding;
      }
      catch (JSONException localJSONException)
      {
        break label58;
      }
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._binding._id = paramAttributes.getValue("id");
      this._binding._desc = paramAttributes.getValue("desc");
      this._binding._binding = paramAttributes.getValue("binding");
      this._binding._preload = "true".equals(paramAttributes.getValue("preload"));
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      XulDataNode._Builder local_Builder = XulDataNode._Builder.create(this._binding, paramString2);
      local_Builder.initialize(paramString2, paramAttributes);
      return local_Builder;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      if (this._content == null);
      for (this._content = paramIPullParser.getText(); ; this._content += paramIPullParser.getText())
        return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Prop.XulBinding
 * JD-Core Version:    0.6.2
 */