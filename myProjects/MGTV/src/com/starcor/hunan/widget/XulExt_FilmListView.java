package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontColor;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontShadow;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontSize;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontWeight;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_Scale;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulExt_FilmListView extends FilmListView
  implements IXulExternalView
{
  public XulExt_FilmListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public XulExt_FilmListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public XulExt_FilmListView(Context paramContext, XulView paramXulView)
  {
    super(paramContext);
    double d1 = paramXulView.getOwnerPage().getXScalar();
    double d2 = paramXulView.getOwnerPage().getYScalar();
    XulStyle localXulStyle1 = paramXulView.getStyle("font-weight");
    boolean bool;
    String[] arrayOfString;
    if (localXulStyle1 != null)
    {
      if (d1 * ((XulPropParser.xulParsedStyle_FontWeight)localXulStyle1.getParsedValue()).val > 1.0D)
      {
        bool = true;
        setTextBold(bool);
      }
    }
    else
    {
      XulStyle localXulStyle2 = paramXulView.getStyle("font-shadow");
      if (localXulStyle2 != null)
      {
        XulPropParser.xulParsedStyle_FontShadow localxulParsedStyle_FontShadow = (XulPropParser.xulParsedStyle_FontShadow)localXulStyle2.getParsedValue();
        setTextShadow((float)(d1 * localxulParsedStyle_FontShadow.xOffset), (float)(d2 * localxulParsedStyle_FontShadow.yOffset), (float)(d1 * localxulParsedStyle_FontShadow.size), localxulParsedStyle_FontShadow.color);
      }
      XulStyle localXulStyle3 = paramXulView.getStyle("scale");
      if (localXulStyle3 != null)
      {
        XulPropParser.xulParsedStyle_Scale localxulParsedStyle_Scale = (XulPropParser.xulParsedStyle_Scale)localXulStyle3.getParsedValue();
        setFocusScalar(localxulParsedStyle_Scale.xScalar, localxulParsedStyle_Scale.yScalar);
      }
      XulStyle localXulStyle4 = paramXulView.getStyle("font-size");
      if (localXulStyle4 != null)
        setTextSize((int)(d1 * ((XulPropParser.xulParsedStyle_FontSize)localXulStyle4.getParsedValue()).val));
      arrayOfString = paramXulView.getAttrString("text-color").split(",");
      if (arrayOfString.length < 2)
        break label251;
      setTextDefaultColor((int)XulUtils.tryParseHex(arrayOfString[0], -2130706433L));
      setTextHighlightColor((int)XulUtils.tryParseHex(arrayOfString[1], -1L));
    }
    label251: XulStyle localXulStyle5;
    do
    {
      return;
      bool = false;
      break;
      if (arrayOfString.length == 1)
        setTextHighlightColor((int)XulUtils.tryParseHex(arrayOfString[0], -1L));
      localXulStyle5 = paramXulView.getStyle("font-color");
    }
    while (localXulStyle5 == null);
    setTextDefaultColor(((XulPropParser.xulParsedStyle_FontColor)localXulStyle5.getParsedValue()).val);
  }

  public void extDestroy()
  {
    destroy();
  }

  public void extHide()
  {
    setVisibility(8);
  }

  public void extMoveTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.leftMargin = paramInt1;
    localLayoutParams.topMargin = paramInt2;
    localLayoutParams.width = paramInt3;
    localLayoutParams.height = paramInt4;
    requestLayout();
  }

  public void extMoveTo(Rect paramRect)
  {
    extMoveTo(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
  }

  public void extOnBlur()
  {
    clearFocus();
  }

  public void extOnFocus()
  {
    requestFocus();
  }

  public boolean extOnKeyEvent(KeyEvent paramKeyEvent)
  {
    return dispatchKeyEvent(paramKeyEvent);
  }

  public void extShow()
  {
    setVisibility(0);
  }

  public void extSyncData()
  {
  }

  public String getAttr(String paramString1, String paramString2)
  {
    return paramString2;
  }

  public boolean setAttr(String paramString1, String paramString2)
  {
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.XulExt_FilmListView
 * JD-Core Version:    0.6.2
 */