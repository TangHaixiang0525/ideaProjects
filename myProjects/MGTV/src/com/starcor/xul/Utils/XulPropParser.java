package com.starcor.xul.Utils;

import android.graphics.DashPathEffect;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulProp;
import com.starcor.xul.Prop.XulPropNameCache;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.XulUtils;
import java.util.ArrayList;
import java.util.Arrays;

public class XulPropParser
{
  private static final String TAG = XulPropParser.class.getSimpleName();
  static XulCachedHashMap<String, ParseInvoker> _xulAttrParserMap;
  static XulCachedHashMap<String, ParseInvoker> _xulStyleParserMap = new XulCachedHashMap();

  static
  {
    _xulAttrParserMap = new XulCachedHashMap();
    _xulStyleParserMap.put("animation-text-change", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_AnimationTextChange.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("fix-half-char", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FixHalfChar.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("padding", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_PaddingMargin.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("margin", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_PaddingMargin.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("preferred-focus-padding", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_PaddingMargin.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("do-not-match-text", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_DoNotMatchText.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("padding-left", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("padding-top", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("padding-right", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("padding-bottom", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("margin-left", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("margin-top", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("margin-right", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("margin-bottom", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_PaddingMarginVal.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("animation-scale", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("display", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_Display.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("scale", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_Scale.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("line-height", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_LineHeight.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-size", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontSize.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-weight", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontWeight.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-color", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontColor.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-align", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontAlign.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-shadow", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontShadow.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-scale-x", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontScaleX.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-style-strike", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_FontStyleStrike.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-style-italic", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-style-underline", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("start-indent", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("end-indent", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("font-resample", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("background-color", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_BackgroundColor.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("background-image", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_BackgroundImage.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("border", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_Border.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("border-dash-pattern", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedStyle_Border_Dash_Pattern.doParse((XulStyle)paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("z-index", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Integer.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("opacity", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-x", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-y", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-z", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-center-x", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-center-y", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-center-z", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("rotate-center", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("translate-x", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("translate-y", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("translate", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("round-rect", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("quiver", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("quiver-mode", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_AnimationMode.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("lighting-color-filter", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_HexArray.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("preload", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulStyleParserMap.put("keep-focus-visible", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    ParseInvoker local54 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Img_Align.doParse((XulAttr)paramAnonymousT);
      }
    };
    ParseInvoker local55 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Img_RoundRect.doParse((XulAttr)paramAnonymousT);
      }
    };
    ParseInvoker local56 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Img_Shadow.doParse((XulAttr)paramAnonymousT);
      }
    };
    ParseInvoker local57 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Img_SizeVal.doParse((XulAttr)paramAnonymousT);
      }
    };
    ParseInvoker local58 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_PaddingMargin.doParse(paramAnonymousT);
      }
    };
    ParseInvoker local59 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Img_FadeIn.doParse((XulAttr)paramAnonymousT);
      }
    };
    ParseInvoker local60 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Img_AutoHide.doParse((XulAttr)paramAnonymousT);
      }
    };
    ParseInvoker local61 = new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    };
    for (int i = 0; i < 8; i++)
    {
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXAlign[i]), local54);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXWidth[i]), local57);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXHeight[i]), local57);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXShadow[i]), local56);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXRoundRect[i]), local55);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXPadding[i]), local58);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXAutoHide[i]), local60);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXFadeIn[i]), local59);
      _xulAttrParserMap.put(XulPropNameCache.id2Name(com.starcor.xul.Render.XulImageRender._imgXReuse[i]), local61);
    }
    _xulAttrParserMap.put("auto-scroll", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("marquee", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Text_Marquee.doParse((XulAttr)paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("direction", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Direction.doParse((XulAttr)paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("x", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_XY.doParse((XulAttr)paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("y", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_XY.doParse((XulAttr)paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("width", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_WidthHeight.doParse((XulAttr)paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("height", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_WidthHeight.doParse((XulAttr)paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("enabled", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_Enabled.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("animation", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("animation-mode", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedAttr_AnimationMode.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("max-layers", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Integer.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("align", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("lock-focus", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("minimum-item", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Integer.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("cache-pages", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_Float.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("animation-moving", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("animation-sizing", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("loop", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("indicator.align", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_FloatArray.doParse(paramAnonymousT);
      }
    });
    _xulAttrParserMap.put("indicator", new ParseInvoker()
    {
      <T extends XulProp> Object doParse(T paramAnonymousT)
      {
        return XulPropParser.xulParsedProp_booleanValue.doParse(paramAnonymousT);
      }
    });
  }

  public static Object parse(XulAttr paramXulAttr)
  {
    ParseInvoker localParseInvoker = (ParseInvoker)_xulAttrParserMap.get(paramXulAttr.getName());
    if (localParseInvoker == null)
    {
      Log.w(TAG, "unsupported attr " + paramXulAttr);
      return null;
    }
    return localParseInvoker.doParse(paramXulAttr);
  }

  public static Object parse(XulStyle paramXulStyle)
  {
    ParseInvoker localParseInvoker = (ParseInvoker)_xulStyleParserMap.get(paramXulStyle.getName());
    if (localParseInvoker == null)
    {
      Log.w(TAG, "unsupported style " + paramXulStyle);
      return null;
    }
    return localParseInvoker.doParse(paramXulStyle);
  }

  static abstract class ParseInvoker
  {
    abstract <T extends XulProp> Object doParse(T paramT);
  }

  public static class xulParsedAttr_AnimationMode
  {
    public String mode;
    public float[] params;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedAttr_AnimationMode localxulParsedAttr_AnimationMode = new xulParsedAttr_AnimationMode();
      String str = paramXulProp.getStringValue();
      float[] arrayOfFloat;
      if (!TextUtils.isEmpty(str))
      {
        int i = str.indexOf(':');
        arrayOfFloat = new float[6];
        localxulParsedAttr_AnimationMode.params = arrayOfFloat;
        if (i <= 0)
          break label123;
        String[] arrayOfString = str.substring(i + 1).split(",");
        str = str.substring(0, i);
        int j = Math.min(arrayOfString.length, arrayOfFloat.length);
        for (int k = 0; k < j; k++)
          arrayOfFloat[k] = XulUtils.tryParseFloat(arrayOfString[k], 1.0F);
        Arrays.fill(arrayOfFloat, j, arrayOfFloat.length, 1.0F);
      }
      while (true)
      {
        localxulParsedAttr_AnimationMode.mode = str;
        return localxulParsedAttr_AnimationMode;
        label123: Arrays.fill(arrayOfFloat, 1.0F);
      }
    }
  }

  public static class xulParsedAttr_Direction
  {
    public boolean reverse;
    public boolean vertical;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Direction localxulParsedAttr_Direction = new xulParsedAttr_Direction();
      String str = paramXulAttr.getStringValue();
      if ("vertical".equals(str))
      {
        localxulParsedAttr_Direction.vertical = true;
        localxulParsedAttr_Direction.reverse = false;
        return localxulParsedAttr_Direction;
      }
      if ("horizontal".equals(str))
      {
        localxulParsedAttr_Direction.vertical = false;
        localxulParsedAttr_Direction.reverse = false;
        return localxulParsedAttr_Direction;
      }
      if ("reverse-vertical".equals(str))
      {
        localxulParsedAttr_Direction.vertical = true;
        localxulParsedAttr_Direction.reverse = true;
        return localxulParsedAttr_Direction;
      }
      if ("reverse-horizontal".equals(str))
      {
        localxulParsedAttr_Direction.vertical = false;
        localxulParsedAttr_Direction.reverse = true;
        return localxulParsedAttr_Direction;
      }
      localxulParsedAttr_Direction.vertical = false;
      localxulParsedAttr_Direction.reverse = false;
      return localxulParsedAttr_Direction;
    }
  }

  public static class xulParsedAttr_Enabled
  {
    public boolean val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedAttr_Enabled localxulParsedAttr_Enabled = new xulParsedAttr_Enabled();
      if ((!"false".equals(paramXulProp.getStringValue())) && (!"disabled".equals(paramXulProp.getStringValue())));
      for (boolean bool = true; ; bool = false)
      {
        localxulParsedAttr_Enabled.val = bool;
        return localxulParsedAttr_Enabled;
      }
    }
  }

  public static class xulParsedAttr_Img_Align
  {
    public float xAlign = 0.5F;
    public float yAlign = 0.5F;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Img_Align localxulParsedAttr_Img_Align = new xulParsedAttr_Img_Align();
      String[] arrayOfString = paramXulAttr.getStringValue().split(",");
      if (arrayOfString.length == 2)
      {
        localxulParsedAttr_Img_Align.xAlign = XulUtils.tryParseFloat(arrayOfString[0], 0.5F);
        localxulParsedAttr_Img_Align.yAlign = XulUtils.tryParseFloat(arrayOfString[1], 0.5F);
      }
      while (arrayOfString.length != 1)
        return localxulParsedAttr_Img_Align;
      float f = XulUtils.tryParseFloat(arrayOfString[0], 0.5F);
      localxulParsedAttr_Img_Align.yAlign = f;
      localxulParsedAttr_Img_Align.xAlign = f;
      return localxulParsedAttr_Img_Align;
    }
  }

  public static class xulParsedAttr_Img_AutoHide
  {
    public boolean enabled;
    public int target;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Img_AutoHide localxulParsedAttr_Img_AutoHide = new xulParsedAttr_Img_AutoHide();
      String str = paramXulAttr.getStringValue();
      if ("below".equals(str))
      {
        localxulParsedAttr_Img_AutoHide.enabled = true;
        localxulParsedAttr_Img_AutoHide.target = -1;
        return localxulParsedAttr_Img_AutoHide;
      }
      if ((!TextUtils.isEmpty(str)) && (TextUtils.isDigitsOnly(str)))
      {
        localxulParsedAttr_Img_AutoHide.enabled = true;
        localxulParsedAttr_Img_AutoHide.target = XulUtils.tryParseInt(str);
        localxulParsedAttr_Img_AutoHide.target = Math.min(Math.max(-1, localxulParsedAttr_Img_AutoHide.target), 8);
        return localxulParsedAttr_Img_AutoHide;
      }
      localxulParsedAttr_Img_AutoHide.enabled = false;
      return localxulParsedAttr_Img_AutoHide;
    }
  }

  public static class xulParsedAttr_Img_FadeIn
  {
    public int duration;
    public boolean enabled;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Img_FadeIn localxulParsedAttr_Img_FadeIn = new xulParsedAttr_Img_FadeIn();
      String str = paramXulAttr.getStringValue();
      if (("true".equals(str)) || ("enabled".equals(str)))
      {
        localxulParsedAttr_Img_FadeIn.enabled = true;
        localxulParsedAttr_Img_FadeIn.duration = 300;
        return localxulParsedAttr_Img_FadeIn;
      }
      if ((TextUtils.isEmpty(str)) || ("disabled".equals(str)) || ("false".equals(str)))
      {
        localxulParsedAttr_Img_FadeIn.enabled = false;
        localxulParsedAttr_Img_FadeIn.duration = 0;
        return localxulParsedAttr_Img_FadeIn;
      }
      if (TextUtils.isDigitsOnly(str))
      {
        localxulParsedAttr_Img_FadeIn.enabled = true;
        localxulParsedAttr_Img_FadeIn.duration = XulUtils.tryParseInt(str, 300);
        return localxulParsedAttr_Img_FadeIn;
      }
      localxulParsedAttr_Img_FadeIn.enabled = false;
      localxulParsedAttr_Img_FadeIn.duration = 300;
      return localxulParsedAttr_Img_FadeIn;
    }
  }

  public static class xulParsedAttr_Img_RoundRect
  {
    float[] scaledVals;
    float[] val;
    double xScalar;
    double yScalar;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Img_RoundRect localxulParsedAttr_Img_RoundRect = new xulParsedAttr_Img_RoundRect();
      String str1 = paramXulAttr.getStringValue();
      if (str1 != null)
      {
        String[] arrayOfString = str1.split(",");
        if ((arrayOfString.length == 2) || (arrayOfString.length == 4) || (arrayOfString.length == 8))
        {
          localxulParsedAttr_Img_RoundRect.val = new float[arrayOfString.length];
          int i = 0;
          int j = arrayOfString.length;
          while (i < j)
          {
            String str2 = arrayOfString[i];
            localxulParsedAttr_Img_RoundRect.val[i] = XulUtils.tryParseFloat(str2, (0.0F / 0.0F));
            i++;
          }
        }
      }
      return localxulParsedAttr_Img_RoundRect;
    }

    public float[] getRoundRadius(double paramDouble1, double paramDouble2)
    {
      if (this.val == null)
        return null;
      if ((this.scaledVals != null) && (Math.abs(this.xScalar - paramDouble1) < 9.999999747378752E-005D) && (Math.abs(this.yScalar - paramDouble2) < 9.999999747378752E-005D))
        return this.scaledVals;
      if (this.val.length == 4)
      {
        arrayOfFloat = new float[8];
        int m = 0;
        int n = this.val.length;
        while (m < n)
        {
          float f = this.val[m];
          arrayOfFloat[(m * 2)] = f;
          arrayOfFloat[(1 + m * 2)] = f;
          m++;
        }
      }
      float[] arrayOfFloat = (float[])this.val.clone();
      int i = 0;
      int j = arrayOfFloat.length;
      while (i < j)
      {
        arrayOfFloat[i] = ((float)(paramDouble1 * arrayOfFloat[i]));
        int k = i + 1;
        arrayOfFloat[k] = ((float)(paramDouble2 * arrayOfFloat[k]));
        i += 2;
      }
      this.scaledVals = arrayOfFloat;
      this.xScalar = paramDouble1;
      this.yScalar = paramDouble2;
      return arrayOfFloat;
    }
  }

  public static class xulParsedAttr_Img_Shadow
  {
    public int color = -16777216;
    public float size = 0.0F;
    public float xOffset = 0.0F;
    public float yOffset = 0.0F;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Img_Shadow localxulParsedAttr_Img_Shadow = new xulParsedAttr_Img_Shadow();
      String[] arrayOfString = paramXulAttr.getStringValue().split(",");
      if (arrayOfString.length == 4)
      {
        localxulParsedAttr_Img_Shadow.xOffset = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
        localxulParsedAttr_Img_Shadow.yOffset = XulUtils.tryParseFloat(arrayOfString[1], 0.0F);
        localxulParsedAttr_Img_Shadow.size = XulUtils.tryParseFloat(arrayOfString[2], 0.0F);
        localxulParsedAttr_Img_Shadow.color = ((int)XulUtils.tryParseHex(arrayOfString[3], -16777216L));
      }
      do
      {
        return localxulParsedAttr_Img_Shadow;
        if (arrayOfString.length == 3)
        {
          localxulParsedAttr_Img_Shadow.xOffset = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
          localxulParsedAttr_Img_Shadow.yOffset = XulUtils.tryParseFloat(arrayOfString[1], 0.0F);
          localxulParsedAttr_Img_Shadow.size = XulUtils.tryParseFloat(arrayOfString[2], 0.0F);
          return localxulParsedAttr_Img_Shadow;
        }
        if (arrayOfString.length == 2)
        {
          localxulParsedAttr_Img_Shadow.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
          localxulParsedAttr_Img_Shadow.color = ((int)XulUtils.tryParseHex(arrayOfString[1], -16777216L));
          return localxulParsedAttr_Img_Shadow;
        }
      }
      while (arrayOfString.length != 1);
      localxulParsedAttr_Img_Shadow.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
      return localxulParsedAttr_Img_Shadow;
    }
  }

  public static class xulParsedAttr_Img_SizeVal
  {
    public int val;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Img_SizeVal localxulParsedAttr_Img_SizeVal = new xulParsedAttr_Img_SizeVal();
      String str = paramXulAttr.getStringValue();
      if ("match_content".equals(str))
      {
        localxulParsedAttr_Img_SizeVal.val = 2147483645;
        return localxulParsedAttr_Img_SizeVal;
      }
      if ("match_parent".equals(str))
      {
        localxulParsedAttr_Img_SizeVal.val = 2147483644;
        return localxulParsedAttr_Img_SizeVal;
      }
      localxulParsedAttr_Img_SizeVal.val = XulUtils.tryParseInt(str);
      return localxulParsedAttr_Img_SizeVal;
    }
  }

  public static class xulParsedAttr_Text_Marquee
  {
    public int delay = 0;
    public int direction = 1;
    public int interval = 0;
    public int space = 0;
    public int speed = 0;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_Text_Marquee localxulParsedAttr_Text_Marquee = new xulParsedAttr_Text_Marquee();
      String[] arrayOfString = paramXulAttr.getStringValue().split(",");
      int i;
      String str4;
      if (arrayOfString.length == 5)
      {
        if ("inverse".equals(arrayOfString[0]))
          localxulParsedAttr_Text_Marquee.direction = -1;
        i = 1;
        String str1 = arrayOfString[(i + 0)];
        String str2 = arrayOfString[(i + 1)];
        String str3 = arrayOfString[(i + 2)];
        str4 = arrayOfString[(i + 3)];
        localxulParsedAttr_Text_Marquee.speed = XulUtils.tryParseInt(str1, 0);
        localxulParsedAttr_Text_Marquee.delay = XulUtils.tryParseInt(str2, 0);
        localxulParsedAttr_Text_Marquee.interval = XulUtils.tryParseInt(str3, 0);
        if (!str4.endsWith("%"))
          break label167;
      }
      label167: for (localxulParsedAttr_Text_Marquee.space = (-XulUtils.tryParseInt(str4.substring(0, -1 + str4.length()), 0)); ; localxulParsedAttr_Text_Marquee.space = XulUtils.tryParseInt(str4, 0))
      {
        if ((localxulParsedAttr_Text_Marquee.speed != 0) && (localxulParsedAttr_Text_Marquee.speed < 100))
          localxulParsedAttr_Text_Marquee.speed = 100;
        do
          return localxulParsedAttr_Text_Marquee;
        while (arrayOfString.length != 4);
        i = 0;
        break;
      }
    }
  }

  public static class xulParsedAttr_WidthHeight
  {
    public int val;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_WidthHeight localxulParsedAttr_WidthHeight = new xulParsedAttr_WidthHeight();
      String str = paramXulAttr.getStringValue();
      if (TextUtils.isEmpty(str))
      {
        localxulParsedAttr_WidthHeight.val = 2147483646;
        return localxulParsedAttr_WidthHeight;
      }
      if ("match_parent".equals(str))
      {
        localxulParsedAttr_WidthHeight.val = 2147483644;
        return localxulParsedAttr_WidthHeight;
      }
      if ("match_content".equals(str))
      {
        localxulParsedAttr_WidthHeight.val = 2147483646;
        return localxulParsedAttr_WidthHeight;
      }
      localxulParsedAttr_WidthHeight.val = XulUtils.tryParseInt(str, 2147483646);
      return localxulParsedAttr_WidthHeight;
    }
  }

  public static class xulParsedAttr_XY
  {
    public int val;

    public static Object doParse(XulAttr paramXulAttr)
    {
      xulParsedAttr_XY localxulParsedAttr_XY = new xulParsedAttr_XY();
      String str = paramXulAttr.getStringValue();
      if (TextUtils.isEmpty(str))
      {
        localxulParsedAttr_XY.val = 2147483646;
        return localxulParsedAttr_XY;
      }
      localxulParsedAttr_XY.val = XulUtils.tryParseInt(str, 2147483646);
      return localxulParsedAttr_XY;
    }
  }

  public static class xulParsedProp_Float
  {
    public float val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedProp_Float localxulParsedProp_Float = new xulParsedProp_Float();
      localxulParsedProp_Float.val = XulUtils.tryParseFloat(paramXulProp.getStringValue(), (0.0F / 0.0F));
      return localxulParsedProp_Float;
    }

    public float tryGetVal(float paramFloat)
    {
      if (Float.isNaN(this.val))
        return paramFloat;
      return this.val;
    }
  }

  public static class xulParsedProp_FloatArray
  {
    public float[] val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedProp_FloatArray localxulParsedProp_FloatArray = new xulParsedProp_FloatArray();
      String str1 = paramXulProp.getStringValue();
      if (str1 != null)
      {
        String[] arrayOfString = str1.split(",");
        localxulParsedProp_FloatArray.val = new float[arrayOfString.length];
        int i = 0;
        int j = arrayOfString.length;
        while (i < j)
        {
          String str2 = arrayOfString[i];
          localxulParsedProp_FloatArray.val[i] = XulUtils.tryParseFloat(str2, (0.0F / 0.0F));
          i++;
        }
      }
      return localxulParsedProp_FloatArray;
    }

    public int getLength()
    {
      if (this.val == null)
        return 0;
      return this.val.length;
    }

    public float tryGetVal(int paramInt, float paramFloat)
    {
      if (paramInt >= this.val.length);
      float f;
      do
      {
        return paramFloat;
        f = this.val[paramInt];
      }
      while (Float.isNaN(f));
      return f;
    }
  }

  public static class xulParsedProp_HexArray
  {
    public long[] val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedProp_HexArray localxulParsedProp_HexArray = new xulParsedProp_HexArray();
      String str1 = paramXulProp.getStringValue();
      if (str1 != null)
      {
        String[] arrayOfString = str1.split(",");
        localxulParsedProp_HexArray.val = new long[arrayOfString.length];
        int i = 0;
        int j = arrayOfString.length;
        while (i < j)
        {
          String str2 = arrayOfString[i];
          localxulParsedProp_HexArray.val[i] = XulUtils.tryParseHex(str2, -9223372036854775808L);
          i++;
        }
      }
      return localxulParsedProp_HexArray;
    }

    public int getLength()
    {
      if (this.val == null)
        return 0;
      return this.val.length;
    }

    public long tryGetVal(int paramInt, long paramLong)
    {
      if (paramInt >= this.val.length);
      long l;
      do
      {
        return paramLong;
        l = this.val[paramInt];
      }
      while (-9223372036854775808L == l);
      return l;
    }
  }

  public static class xulParsedProp_Integer
  {
    public int val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedProp_Integer localxulParsedProp_Integer = new xulParsedProp_Integer();
      localxulParsedProp_Integer.val = XulUtils.tryParseInt(paramXulProp.getStringValue(), 0);
      return localxulParsedProp_Integer;
    }
  }

  public static class xulParsedProp_IntegerArray
  {
    public int[] val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedProp_IntegerArray localxulParsedProp_IntegerArray = new xulParsedProp_IntegerArray();
      String str1 = paramXulProp.getStringValue();
      if (str1 != null)
      {
        String[] arrayOfString = str1.split(",");
        localxulParsedProp_IntegerArray.val = new int[arrayOfString.length];
        int i = 0;
        int j = arrayOfString.length;
        while (i < j)
        {
          String str2 = arrayOfString[i];
          localxulParsedProp_IntegerArray.val[i] = XulUtils.tryParseInt(str2, 0);
          i++;
        }
      }
      return localxulParsedProp_IntegerArray;
    }
  }

  public static class xulParsedProp_PaddingMargin
  {
    public int bottom;
    public int left;
    public int right;
    public int top;

    public static Object doParse(XulProp paramXulProp)
    {
      String[] arrayOfString = paramXulProp.getStringValue().split(",");
      int i;
      int m;
      int k;
      int j;
      if (arrayOfString.length == 1)
      {
        int i5 = XulUtils.tryParseInt(arrayOfString[0], 0);
        i = i5;
        m = i5;
        k = i5;
        j = i5;
      }
      while (true)
      {
        xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin = new xulParsedProp_PaddingMargin();
        localxulParsedProp_PaddingMargin.left = j;
        localxulParsedProp_PaddingMargin.right = k;
        localxulParsedProp_PaddingMargin.top = m;
        localxulParsedProp_PaddingMargin.bottom = i;
        return localxulParsedProp_PaddingMargin;
        if (arrayOfString.length == 2)
        {
          int i3 = XulUtils.tryParseInt(arrayOfString[0], 0);
          int i4 = XulUtils.tryParseInt(arrayOfString[1], 0);
          m = i3;
          j = i4;
          k = i4;
          i = i3;
        }
        else if (arrayOfString.length == 3)
        {
          int n = XulUtils.tryParseInt(arrayOfString[0], 0);
          int i1 = XulUtils.tryParseInt(arrayOfString[1], 0);
          int i2 = XulUtils.tryParseInt(arrayOfString[2], 0);
          m = n;
          j = i1;
          k = i1;
          i = i2;
        }
        else if (arrayOfString.length == 4)
        {
          m = XulUtils.tryParseInt(arrayOfString[0], 0);
          j = XulUtils.tryParseInt(arrayOfString[1], 0);
          k = XulUtils.tryParseInt(arrayOfString[2], 0);
          i = XulUtils.tryParseInt(arrayOfString[3], 0);
        }
        else
        {
          i = 0;
          j = 0;
          k = 0;
          m = 0;
        }
      }
    }
  }

  public static class xulParsedProp_booleanValue
  {
    public boolean val;

    public static Object doParse(XulProp paramXulProp)
    {
      xulParsedProp_booleanValue localxulParsedProp_booleanValue = new xulParsedProp_booleanValue();
      if (("true".equals(paramXulProp.getStringValue())) || ("enabled".equals(paramXulProp.getStringValue())));
      for (boolean bool = true; ; bool = false)
      {
        localxulParsedProp_booleanValue.val = bool;
        return localxulParsedProp_booleanValue;
      }
    }
  }

  public static class xulParsedStyle_AnimationTextChange
  {
    public boolean val = true;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_AnimationTextChange localxulParsedStyle_AnimationTextChange = new xulParsedStyle_AnimationTextChange();
      localxulParsedStyle_AnimationTextChange.val = true;
      return localxulParsedStyle_AnimationTextChange;
    }
  }

  public static class xulParsedStyle_BackgroundColor
  {
    public int val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_BackgroundColor localxulParsedStyle_BackgroundColor = new xulParsedStyle_BackgroundColor();
      localxulParsedStyle_BackgroundColor.val = ((int)XulUtils.tryParseHex(paramXulStyle.getStringValue(), 0L));
      return localxulParsedStyle_BackgroundColor;
    }
  }

  public static class xulParsedStyle_BackgroundImage
  {
    public String url;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_BackgroundImage localxulParsedStyle_BackgroundImage = new xulParsedStyle_BackgroundImage();
      localxulParsedStyle_BackgroundImage.url = paramXulStyle.getStringValue().trim();
      return localxulParsedStyle_BackgroundImage;
    }
  }

  public static class xulParsedStyle_Border
  {
    public int color = -16777216;
    public float pos = 0.5F;
    public float size = 0.0F;
    public float xRadius = 0.0F;
    public float yRadius = 0.0F;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_Border localxulParsedStyle_Border = new xulParsedStyle_Border();
      String[] arrayOfString = paramXulStyle.getStringValue().split(",");
      if (arrayOfString.length == 5)
      {
        localxulParsedStyle_Border.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
        localxulParsedStyle_Border.color = ((int)XulUtils.tryParseHex(arrayOfString[1], 0L));
        localxulParsedStyle_Border.xRadius = XulUtils.tryParseFloat(arrayOfString[2], 0.0F);
        localxulParsedStyle_Border.yRadius = XulUtils.tryParseFloat(arrayOfString[3], 0.0F);
        localxulParsedStyle_Border.pos = XulUtils.tryParseFloat(arrayOfString[4], 0.0F);
      }
      do
      {
        return localxulParsedStyle_Border;
        if (arrayOfString.length == 4)
        {
          localxulParsedStyle_Border.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
          localxulParsedStyle_Border.color = ((int)XulUtils.tryParseHex(arrayOfString[1], 0L));
          localxulParsedStyle_Border.xRadius = XulUtils.tryParseFloat(arrayOfString[2], 0.0F);
          localxulParsedStyle_Border.yRadius = XulUtils.tryParseFloat(arrayOfString[3], 0.0F);
          return localxulParsedStyle_Border;
        }
        if (arrayOfString.length == 3)
        {
          localxulParsedStyle_Border.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
          localxulParsedStyle_Border.color = ((int)XulUtils.tryParseHex(arrayOfString[1], 0L));
          localxulParsedStyle_Border.pos = XulUtils.tryParseFloat(arrayOfString[2], 0.0F);
          return localxulParsedStyle_Border;
        }
        if (arrayOfString.length == 2)
        {
          localxulParsedStyle_Border.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
          localxulParsedStyle_Border.color = ((int)XulUtils.tryParseHex(arrayOfString[1], 0L));
          return localxulParsedStyle_Border;
        }
      }
      while (arrayOfString.length != 1);
      localxulParsedStyle_Border.size = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
      return localxulParsedStyle_Border;
    }
  }

  public static class xulParsedStyle_Border_Dash_Pattern
  {
    static final int MAX_CACHED_EFFECT_OBJECT = 4;
    private ArrayList<Pair<Integer, DashPathEffect>> _cachedEffectObject;
    public float[] pattern;
    public float phase = 0.0F;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_Border_Dash_Pattern localxulParsedStyle_Border_Dash_Pattern = new xulParsedStyle_Border_Dash_Pattern();
      String[] arrayOfString = paramXulStyle.getStringValue().split(",");
      if (arrayOfString.length < 2);
      while (true)
      {
        return localxulParsedStyle_Border_Dash_Pattern;
        if (arrayOfString.length == 2)
        {
          localxulParsedStyle_Border_Dash_Pattern.pattern = new float[2];
          localxulParsedStyle_Border_Dash_Pattern.pattern[0] = XulUtils.tryParseFloat(arrayOfString[0], 5.0F);
          localxulParsedStyle_Border_Dash_Pattern.pattern[1] = XulUtils.tryParseFloat(arrayOfString[1], 5.0F);
          return localxulParsedStyle_Border_Dash_Pattern;
        }
        localxulParsedStyle_Border_Dash_Pattern.pattern = new float[-1 + arrayOfString.length];
        localxulParsedStyle_Border_Dash_Pattern.phase = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
        for (int i = 0; i < localxulParsedStyle_Border_Dash_Pattern.pattern.length; i++)
          localxulParsedStyle_Border_Dash_Pattern.pattern[i] = XulUtils.tryParseFloat(arrayOfString[(i + 1)], 5.0F);
      }
    }

    public DashPathEffect getEffectObjectByXYScalar(float paramFloat1, float paramFloat2)
    {
      if (this.pattern == null)
        return null;
      if (this._cachedEffectObject == null)
        this._cachedEffectObject = new ArrayList();
      int i = XulUtils.roundToInt(100.0F * paramFloat1);
      for (int j = 0; j < this._cachedEffectObject.size(); j++)
      {
        Pair localPair = (Pair)this._cachedEffectObject.get(j);
        if (((Integer)localPair.first).intValue() == i)
        {
          if (j != 0)
          {
            this._cachedEffectObject.remove(j);
            this._cachedEffectObject.add(0, localPair);
          }
          return (DashPathEffect)localPair.second;
        }
      }
      while (this._cachedEffectObject.size() >= 4)
        this._cachedEffectObject.remove(-1 + this._cachedEffectObject.size());
      float[] arrayOfFloat = new float[this.pattern.length];
      for (int k = 0; k < this.pattern.length; k++)
        arrayOfFloat[k] = (paramFloat1 * this.pattern[k]);
      DashPathEffect localDashPathEffect = new DashPathEffect(arrayOfFloat, paramFloat1 * this.phase);
      this._cachedEffectObject.add(Pair.create(Integer.valueOf(i), localDashPathEffect));
      return localDashPathEffect;
    }
  }

  public static class xulParsedStyle_Display
  {
    public DisplayMode mode;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_Display localxulParsedStyle_Display = new xulParsedStyle_Display();
      if ("none".equals(paramXulStyle.getStringValue()))
      {
        localxulParsedStyle_Display.mode = DisplayMode.None;
        return localxulParsedStyle_Display;
      }
      localxulParsedStyle_Display.mode = DisplayMode.Block;
      return localxulParsedStyle_Display;
    }

    public static enum DisplayMode
    {
      static
      {
        Block = new DisplayMode("Block", 1);
        DisplayMode[] arrayOfDisplayMode = new DisplayMode[2];
        arrayOfDisplayMode[0] = None;
        arrayOfDisplayMode[1] = Block;
      }
    }
  }

  public static class xulParsedStyle_DoNotMatchText
  {
    public boolean doNotMatchHeight;
    public boolean doNotMatchWidth;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_DoNotMatchText localxulParsedStyle_DoNotMatchText = new xulParsedStyle_DoNotMatchText();
      String str = paramXulStyle.getStringValue();
      if (str == null)
      {
        localxulParsedStyle_DoNotMatchText.doNotMatchWidth = false;
        localxulParsedStyle_DoNotMatchText.doNotMatchHeight = false;
        return localxulParsedStyle_DoNotMatchText;
      }
      String[] arrayOfString = str.split(",");
      switch (arrayOfString.length)
      {
      default:
        localxulParsedStyle_DoNotMatchText.doNotMatchWidth = arrayOfString[0].trim().equals("true");
        localxulParsedStyle_DoNotMatchText.doNotMatchHeight = arrayOfString[1].trim().equals("true");
        return localxulParsedStyle_DoNotMatchText;
      case 0:
        localxulParsedStyle_DoNotMatchText.doNotMatchWidth = false;
        localxulParsedStyle_DoNotMatchText.doNotMatchHeight = false;
        return localxulParsedStyle_DoNotMatchText;
      case 1:
      }
      boolean bool = arrayOfString[0].trim().equals("true");
      localxulParsedStyle_DoNotMatchText.doNotMatchHeight = bool;
      localxulParsedStyle_DoNotMatchText.doNotMatchWidth = bool;
      return localxulParsedStyle_DoNotMatchText;
    }
  }

  public static class xulParsedStyle_FixHalfChar
  {
    public boolean val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FixHalfChar localxulParsedStyle_FixHalfChar = new xulParsedStyle_FixHalfChar();
      localxulParsedStyle_FixHalfChar.val = "true".equals(paramXulStyle.getStringValue());
      return localxulParsedStyle_FixHalfChar;
    }
  }

  public static class xulParsedStyle_FontAlign
  {
    public float xAlign = 0.0F;
    public float yAlign = 0.0F;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontAlign localxulParsedStyle_FontAlign = new xulParsedStyle_FontAlign();
      String[] arrayOfString = paramXulStyle.getStringValue().split(",");
      if (arrayOfString.length == 2)
      {
        localxulParsedStyle_FontAlign.xAlign = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
        localxulParsedStyle_FontAlign.yAlign = XulUtils.tryParseFloat(arrayOfString[1], 0.0F);
      }
      while (arrayOfString.length != 1)
        return localxulParsedStyle_FontAlign;
      float f = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
      localxulParsedStyle_FontAlign.yAlign = f;
      localxulParsedStyle_FontAlign.xAlign = f;
      return localxulParsedStyle_FontAlign;
    }
  }

  public static class xulParsedStyle_FontColor
  {
    public int val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontColor localxulParsedStyle_FontColor = new xulParsedStyle_FontColor();
      localxulParsedStyle_FontColor.val = ((int)XulUtils.tryParseHex(paramXulStyle.getStringValue(), 0L));
      return localxulParsedStyle_FontColor;
    }
  }

  public static class xulParsedStyle_FontScaleX
  {
    public float val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontScaleX localxulParsedStyle_FontScaleX = new xulParsedStyle_FontScaleX();
      localxulParsedStyle_FontScaleX.val = XulUtils.tryParseFloat(paramXulStyle.getStringValue(), 1.0F);
      return localxulParsedStyle_FontScaleX;
    }
  }

  public static class xulParsedStyle_FontShadow
  {
    public int color = -16777216;
    public float size = 0.0F;
    public float xOffset = 0.0F;
    public float yOffset = 0.0F;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontShadow localxulParsedStyle_FontShadow = new xulParsedStyle_FontShadow();
      String[] arrayOfString = paramXulStyle.getStringValue().split(",");
      if (arrayOfString.length == 4)
      {
        localxulParsedStyle_FontShadow.xOffset = XulUtils.tryParseFloat(arrayOfString[0], 0.0F);
        localxulParsedStyle_FontShadow.yOffset = XulUtils.tryParseFloat(arrayOfString[1], 0.0F);
        localxulParsedStyle_FontShadow.size = XulUtils.tryParseFloat(arrayOfString[2], 0.0F);
        localxulParsedStyle_FontShadow.color = ((int)XulUtils.tryParseHex(arrayOfString[3], 0L));
      }
      return localxulParsedStyle_FontShadow;
    }
  }

  public static class xulParsedStyle_FontSize
  {
    public float val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontSize localxulParsedStyle_FontSize = new xulParsedStyle_FontSize();
      localxulParsedStyle_FontSize.val = XulUtils.tryParseFloat(paramXulStyle.getStringValue(), 24.0F);
      return localxulParsedStyle_FontSize;
    }
  }

  public static class xulParsedStyle_FontStyleStrike
  {
    public boolean val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontStyleStrike localxulParsedStyle_FontStyleStrike = new xulParsedStyle_FontStyleStrike();
      localxulParsedStyle_FontStyleStrike.val = "true".equals(paramXulStyle.getStringValue());
      return localxulParsedStyle_FontStyleStrike;
    }
  }

  public static class xulParsedStyle_FontWeight
  {
    public float val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_FontWeight localxulParsedStyle_FontWeight = new xulParsedStyle_FontWeight();
      localxulParsedStyle_FontWeight.val = XulUtils.tryParseFloat(paramXulStyle.getStringValue(), 24.0F);
      return localxulParsedStyle_FontWeight;
    }
  }

  public static class xulParsedStyle_LineHeight
  {
    public float val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_LineHeight localxulParsedStyle_LineHeight = new xulParsedStyle_LineHeight();
      localxulParsedStyle_LineHeight.val = XulUtils.tryParseFloat(paramXulStyle.getStringValue(), 1.0F);
      return localxulParsedStyle_LineHeight;
    }
  }

  public static class xulParsedStyle_PaddingMarginVal
  {
    public int val;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_PaddingMarginVal localxulParsedStyle_PaddingMarginVal = new xulParsedStyle_PaddingMarginVal();
      localxulParsedStyle_PaddingMarginVal.val = XulUtils.tryParseInt(paramXulStyle.getStringValue());
      return localxulParsedStyle_PaddingMarginVal;
    }
  }

  public static class xulParsedStyle_Scale
  {
    public float xAlign = 0.5F;
    public float xScalar = 1.0F;
    public float yAlign = 0.5F;
    public float yScalar = 1.0F;

    public static Object doParse(XulStyle paramXulStyle)
    {
      xulParsedStyle_Scale localxulParsedStyle_Scale = new xulParsedStyle_Scale();
      String str = paramXulStyle.getStringValue();
      if (TextUtils.isEmpty(str))
        return localxulParsedStyle_Scale;
      String[] arrayOfString = str.split(",");
      switch (arrayOfString.length)
      {
      default:
        return localxulParsedStyle_Scale;
      case 1:
        float f = XulUtils.tryParseFloat(arrayOfString[0], 1.0F);
        localxulParsedStyle_Scale.yScalar = f;
        localxulParsedStyle_Scale.xScalar = f;
        localxulParsedStyle_Scale.yAlign = 0.5F;
        localxulParsedStyle_Scale.xAlign = 0.5F;
        return localxulParsedStyle_Scale;
      case 2:
        localxulParsedStyle_Scale.xScalar = XulUtils.tryParseFloat(arrayOfString[0], 1.0F);
        localxulParsedStyle_Scale.yScalar = XulUtils.tryParseFloat(arrayOfString[1], 1.0F);
        localxulParsedStyle_Scale.yAlign = 0.5F;
        localxulParsedStyle_Scale.xAlign = 0.5F;
        return localxulParsedStyle_Scale;
      case 3:
        localxulParsedStyle_Scale.xScalar = XulUtils.tryParseFloat(arrayOfString[0], 1.0F);
        localxulParsedStyle_Scale.yScalar = XulUtils.tryParseFloat(arrayOfString[0], 1.0F);
        localxulParsedStyle_Scale.xAlign = XulUtils.tryParseFloat(arrayOfString[1], 0.5F);
        localxulParsedStyle_Scale.yAlign = XulUtils.tryParseFloat(arrayOfString[2], 0.5F);
        return localxulParsedStyle_Scale;
      case 4:
      }
      localxulParsedStyle_Scale.xScalar = XulUtils.tryParseFloat(arrayOfString[0], 1.0F);
      localxulParsedStyle_Scale.yScalar = XulUtils.tryParseFloat(arrayOfString[1], 1.0F);
      localxulParsedStyle_Scale.xAlign = XulUtils.tryParseFloat(arrayOfString[2], 0.5F);
      localxulParsedStyle_Scale.yAlign = XulUtils.tryParseFloat(arrayOfString[3], 0.5F);
      return localxulParsedStyle_Scale;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulPropParser
 * JD-Core Version:    0.6.2
 */