package com.starcor.xul.Prop;

import android.text.TextUtils;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulSelect;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulFocus extends XulProp
{
  public static final int MODE_DYNAMIC = 16;
  public static final int MODE_FOCUSABLE = 8;
  public static final int MODE_LOOP = 2;
  public static final int MODE_NEARBY = 1;
  public static final int MODE_NOFOCUS = 4;
  public static final int MODE_PRIORITY = 32;
  public static final int MODE_SEARCH_ORDER_DNP = 4096;
  public static final int MODE_SEARCH_ORDER_DPN = 0;
  public static final int MODE_SEARCH_ORDER_MASK = 61440;
  public static final int MODE_SEARCH_ORDER_NDP = 8192;
  public static final int MODE_SEARCH_ORDER_NPD = 12288;
  public static final int MODE_SEARCH_ORDER_PDN = 16384;
  public static final int MODE_SEARCH_ORDER_PND = 20480;
  public static final int MODE_WRAP = 64;
  boolean _defaultFocused = false;
  int _focusPriority = -1;
  int _mode = 33;

  public void bindNextFocus(String paramString, XulSelect paramXulSelect)
  {
  }

  public boolean focusable()
  {
    return 8 == (0x8 & this._mode);
  }

  public int getFocusMode()
  {
    return this._mode;
  }

  public int getFocusOrder()
  {
    return 0xF000 & this._mode;
  }

  public int getFocusPriority()
  {
    return this._focusPriority;
  }

  public boolean hasModeBits(int paramInt)
  {
    return (paramInt & this._mode) != 0;
  }

  public boolean isDefaultFocused()
  {
    return this._defaultFocused;
  }

  public XulFocus makeClone()
  {
    return this;
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulFactory.ItemBuilder.FinalCallback<XulFocus> _callback;
    XulFocus _focus;

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulSelect paramXulSelect)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulSelect);
      return local_Builder;
    }

    public static _Builder create(XulView paramXulView)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulView);
      return local_Builder;
    }

    private void init(final XulSelect paramXulSelect)
    {
      this._focus = new XulFocus();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulFocus paramAnonymousXulFocus)
        {
          paramXulSelect.addProp(paramAnonymousXulFocus);
        }
      };
    }

    private void init(final XulView paramXulView)
    {
      this._focus = new XulFocus();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulFocus paramAnonymousXulFocus)
        {
          paramXulView.addInplaceProp(paramAnonymousXulFocus);
        }
      };
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._callback = null;
      _recycled_builder._focus = null;
    }

    public Object finalItem()
    {
      XulFocus localXulFocus = this._focus;
      XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
      recycle(this);
      localFinalCallback.onFinal(localXulFocus);
      return localXulFocus;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._focus._nameId = XulPropNameCache.name2Id("focus");
      String str1 = paramAttributes.getValue("mode");
      int j;
      int m;
      if (!TextUtils.isEmpty(str1))
      {
        String[] arrayOfString = str1.split(",");
        int i = 0;
        j = 0;
        int k = 0;
        if (k < arrayOfString.length)
        {
          String str3 = arrayOfString[k];
          if ("nearby".equals(str3))
          {
            j |= 1;
            i = 0x1 | i * 16;
          }
          while (true)
          {
            k++;
            break;
            if ("~nearby".equals(str3))
            {
              j &= -2;
            }
            else if ("dynamic".equals(str3))
            {
              j |= 16;
              i = 0x2 | i * 16;
            }
            else if ("~dynamic".equals(str3))
            {
              j &= -17;
            }
            else if ("priority".equals(str3))
            {
              j |= 32;
              i = 0x3 | i * 16;
            }
            else if ("~priority".equals(str3))
            {
              j &= -33;
            }
            else if ("loop".equals(str3))
            {
              j |= 2;
            }
            else if ("wrap".equals(str3))
            {
              j |= 64;
            }
            else if ("nofocus".equals(str3))
            {
              j = 0xFFFFFFF7 & (j | 0x4);
            }
            else if ("focusable".equals(str3))
            {
              j = 0xFFFFFFFB & (j | 0x8);
            }
          }
        }
        switch (i & 0xFFF)
        {
        default:
          m = j | 0x0;
        case 1:
        case 18:
        case 291:
        case 19:
        case 306:
        case 33:
        case 531:
        case 2:
        case 35:
        case 561:
        case 49:
        case 786:
        case 3:
        case 50:
        case 801:
        }
      }
      while (true)
      {
        this._focus._mode = m;
        if ("true".equals(paramAttributes.getValue("focused")))
          this._focus._defaultFocused = true;
        String str2 = paramAttributes.getValue("priority");
        if ((TextUtils.isEmpty(str2)) || (!TextUtils.isDigitsOnly(str2)))
          break;
        this._focus._focusPriority = XulUtils.tryParseInt(str2, -1);
        return true;
        m = j | 0x2000;
        continue;
        m = j | 0x3000;
        continue;
        m = j | 0x1000;
        continue;
        m = j | 0x0;
        continue;
        m = j | 0x5000;
        continue;
        m = j | 0x4000;
      }
      this._focus._focusPriority = -1;
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      return XulManager.CommonDummyBuilder;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Prop.XulFocus
 * JD-Core Version:    0.6.2
 */