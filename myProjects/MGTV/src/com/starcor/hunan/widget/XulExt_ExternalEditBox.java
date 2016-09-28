package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_PaddingMargin;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_BackgroundColor;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontColor;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontSize;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_PaddingMarginVal;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulExt_ExternalEditBox extends EditText
  implements IXulExternalView
{
  XulView _view;

  public XulExt_ExternalEditBox(Context paramContext, XulView paramXulView)
  {
    super(paramContext);
    this._view = paramXulView;
    double d1 = paramXulView.getOwnerPage().getXScalar();
    double d2 = paramXulView.getOwnerPage().getYScalar();
    setInputType(524288);
    XulAttr localXulAttr1 = this._view.getAttr("max-lines");
    int i = 1;
    if (localXulAttr1 != null)
      i = XulUtils.tryParseInt(localXulAttr1.getStringValue(), i);
    if (i == 1)
    {
      setSingleLine(true);
      XulAttr localXulAttr2 = this._view.getAttr("text");
      if (localXulAttr2 != null)
        setText(localXulAttr2.getStringValue());
      XulAttr localXulAttr3 = this._view.getAttr("hint-text");
      if (localXulAttr3 != null)
        setHint(localXulAttr3.getStringValue());
      XulAttr localXulAttr4 = this._view.getAttr("max-length");
      if (localXulAttr4 != null)
      {
        InputFilter[] arrayOfInputFilter = new InputFilter[1];
        arrayOfInputFilter[0] = new InputFilter.LengthFilter(Integer.valueOf(localXulAttr4.getStringValue()).intValue());
        setFilters(arrayOfInputFilter);
      }
      XulAttr localXulAttr5 = this._view.getAttr("space-division");
      if (localXulAttr5 != null)
      {
        int i1 = -1;
        if (localXulAttr4 != null)
          i1 = Integer.valueOf(localXulAttr4.getStringValue()).intValue();
        int i2 = Integer.valueOf(localXulAttr5.getStringValue()).intValue();
        DivisionTextWatcher localDivisionTextWatcher = new DivisionTextWatcher(i2, i1);
        addTextChangedListener(localDivisionTextWatcher);
      }
      XulStyle localXulStyle1 = paramXulView.getStyle("font-color");
      if (localXulStyle1 != null)
        setTextColor(((XulPropParser.xulParsedStyle_FontColor)localXulStyle1.getParsedValue()).val);
      String str = paramXulView.getStyleString("hint-text-color");
      if (!TextUtils.isEmpty(str))
      {
        long l = XulUtils.tryParseHex(str, -1L);
        if (l == -1L)
          l = getCurrentTextColor();
        setHintTextColor((int)l);
      }
      XulStyle localXulStyle2 = this._view.getStyle("font-size");
      if (localXulStyle2 != null)
        setTextSize(0, (float)(d1 * ((XulPropParser.xulParsedStyle_FontSize)localXulStyle2.getParsedValue()).val));
      XulStyle localXulStyle3 = this._view.getStyle("background-color");
      if (localXulStyle3 == null)
        break label620;
      setBackgroundColor(((XulPropParser.xulParsedStyle_BackgroundColor)localXulStyle3.getParsedValue()).val);
    }
    while (true)
    {
      XulStyle localXulStyle4 = paramXulView.getStyle("padding");
      int j = 4;
      int k = j;
      int m = j;
      int n = j;
      if (localXulStyle4 != null)
      {
        XulPropParser.xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin = (XulPropParser.xulParsedProp_PaddingMargin)localXulStyle4.getParsedValue();
        n = localxulParsedProp_PaddingMargin.left;
        m = localxulParsedProp_PaddingMargin.top;
        k = localxulParsedProp_PaddingMargin.right;
        j = localxulParsedProp_PaddingMargin.bottom;
      }
      XulStyle localXulStyle5 = paramXulView.getStyle("padding-left");
      XulStyle localXulStyle6 = paramXulView.getStyle("padding-top");
      XulStyle localXulStyle7 = paramXulView.getStyle("padding-right");
      XulStyle localXulStyle8 = paramXulView.getStyle("padding-bottom");
      if (localXulStyle5 != null)
        n = ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle5.getParsedValue()).val;
      if (localXulStyle6 != null)
        m = ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle6.getParsedValue()).val;
      if (localXulStyle7 != null)
        k = ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle7.getParsedValue()).val;
      if (localXulStyle8 != null)
        j = ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle8.getParsedValue()).val;
      setPadding(XulUtils.roundToInt(d1 * n), XulUtils.roundToInt(d2 * m), XulUtils.roundToInt(d1 * k), XulUtils.roundToInt(d2 * j));
      setImeOptions(0x2000000 | getImeOptions());
      return;
      setMaxLines(i);
      break;
      label620: setBackgroundColor(-318767105);
    }
  }

  public void extDestroy()
  {
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
    switch (paramKeyEvent.getKeyCode())
    {
    default:
      return false;
    case 19:
    case 20:
    case 21:
    case 22:
    }
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
    if ("text".equals(paramString1))
      paramString2 = getText().toString();
    return paramString2;
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (paramBoolean)
    {
      XulLayout localXulLayout = this._view.getRootLayout();
      if (localXulLayout != null)
      {
        localXulLayout.requestFocus(this._view);
        if ((getText() instanceof Editable))
          Selection.setSelection(getText(), getText().length());
      }
    }
  }

  public boolean setAttr(String paramString1, String paramString2)
  {
    if ("text".equals(paramString1))
    {
      setText(paramString2);
      return true;
    }
    if ("hint-text".equals(paramString1))
    {
      setHint(paramString2);
      return true;
    }
    return false;
  }

  private class DivisionTextWatcher
    implements TextWatcher
  {
    private int _changePos;
    private int _deletePos;
    private int callLevel = 0;
    private int division_length = 0;
    private boolean isInsert = true;
    private int max_length = -1;

    public DivisionTextWatcher(int paramInt1, int arg3)
    {
      this.division_length = paramInt1;
      int i;
      this.max_length = i;
    }

    public void afterTextChanged(Editable paramEditable)
    {
      this.callLevel = (1 + this.callLevel);
      int i = 0;
      if (i < paramEditable.length())
      {
        int k;
        label44: int m;
        if ((i > 0) && (i % (1 + this.division_length) == this.division_length))
        {
          k = 1;
          if (paramEditable.charAt(i) != ' ')
            break label78;
          m = 1;
          label59: if (m != k)
            break label84;
        }
        while (true)
        {
          i++;
          break;
          k = 0;
          break label44;
          label78: m = 0;
          break label59;
          label84: if (this.isInsert)
          {
            if (k != 0)
            {
              if ((this.max_length != -1) && (paramEditable.length() == this.max_length));
              for (int n = i; ; n++)
                if (n < paramEditable.length())
                {
                  if (paramEditable.charAt(n) == ' ')
                    paramEditable.delete(n, n + 1);
                }
                else
                {
                  paramEditable.insert(i, " ");
                  i++;
                  break;
                }
            }
            paramEditable.delete(i, i + 1);
            i--;
          }
          else
          {
            this.isInsert = true;
            if ((i == this._changePos) && (k != 0))
            {
              paramEditable.delete(this._deletePos, 1 + this._deletePos);
              i--;
            }
          }
        }
      }
      while ((paramEditable.length() > 0) && (paramEditable.charAt(-1 + paramEditable.length()) == ' '))
      {
        int j = paramEditable.length();
        paramEditable.delete(j - 1, j);
      }
      this.callLevel = (-1 + this.callLevel);
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      if (this.callLevel == 0)
        if (paramInt3 <= 0)
          break label43;
      label43: for (boolean bool = true; ; bool = false)
      {
        this.isInsert = bool;
        this._changePos = paramInt1;
        if (paramInt1 != XulExt_ExternalEditBox.this.getSelectionStart())
          break;
        this._deletePos = paramInt1;
        return;
      }
      this._deletePos = (paramInt1 - 1);
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.XulExt_ExternalEditBox
 * JD-Core Version:    0.6.2
 */