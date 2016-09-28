package com.starcor.xul.ScriptWrappr;

import android.graphics.RectF;
import com.starcor.xul.Prop.XulPropNameCache;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Script.XulScriptableObject;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptSetter;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulViewScriptableObject<T extends XulView> extends XulScriptableObjectWrapper<T>
{
  public XulViewScriptableObject(T paramT)
  {
    super(paramT);
  }

  @ScriptMethod("addClass")
  public Boolean _script_addClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    boolean bool1 = false;
    int i = 0;
    int j = paramIScriptArguments.size();
    while (true)
      if (i < j)
        try
        {
          boolean bool2 = ((XulView)this._xulItem).addClass(paramIScriptArguments.getString(i));
          if ((bool2) || (bool1));
          for (bool1 = true; ; bool1 = false)
          {
            i++;
            break;
          }
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
    if (bool1)
      ((XulView)this._xulItem).resetRender();
    return Boolean.valueOf(bool1);
  }

  @ScriptMethod("blinkClass")
  public Boolean _script_blinkClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() <= 0))
      return Boolean.FALSE;
    XulViewRender localXulViewRender = ((XulView)this._xulItem).getRender();
    if (localXulViewRender == null)
      return Boolean.FALSE;
    String[] arrayOfString = new String[paramIScriptArguments.size()];
    int i = 0;
    int j = paramIScriptArguments.size();
    while (i < j)
    {
      arrayOfString[i] = paramIScriptArguments.getString(i);
      i++;
    }
    localXulViewRender.blinkClass(arrayOfString);
    return Boolean.TRUE;
  }

  @ScriptMethod("dispatchEvent")
  public Boolean _script_dispatchEvent(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() <= 0))
      return Boolean.FALSE;
    String str1 = paramIScriptArguments.getString(0);
    String str2;
    if (paramIScriptArguments.size() > 1)
    {
      str2 = paramIScriptArguments.getString(1);
      if (paramIScriptArguments.size() <= 2)
        break label99;
    }
    label99: for (String str3 = paramIScriptArguments.getString(2); ; str3 = "")
    {
      return Boolean.valueOf(((XulView)this._xulItem).getOwnerPage().dispatchAction((XulView)this._xulItem, str1, str2, str3));
      str2 = "";
      break;
    }
  }

  @ScriptMethod("findParentByClass")
  public Boolean _script_findParentByClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    XulArea localXulArea = ((XulView)this._xulItem).findParentByClass(str);
    if (localXulArea != null)
    {
      paramIScriptArguments.setResult(localXulArea.getScriptableObject(paramIScriptContext.getScriptType()));
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @ScriptMethod("findParentById")
  public Boolean _script_findParentById(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    XulArea localXulArea = ((XulView)this._xulItem).findParentById(str);
    if (localXulArea != null)
    {
      paramIScriptArguments.setResult(localXulArea.getScriptableObject(paramIScriptContext.getScriptType()));
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @ScriptMethod("findParentByType")
  public Boolean _script_findParentByType(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    XulArea localXulArea = ((XulView)this._xulItem).findParentByType(str);
    if (localXulArea != null)
    {
      paramIScriptArguments.setResult(localXulArea.getScriptableObject(paramIScriptContext.getScriptType()));
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @ScriptMethod("fireEvent")
  public Boolean _script_fireEvent(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() <= 0))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    if (paramIScriptArguments.size() == 1)
      XulPage.invokeAction((XulView)this._xulItem, str);
    while (true)
    {
      return Boolean.TRUE;
      int i = paramIScriptArguments.size();
      Object[] arrayOfObject = new Object[i - 1];
      int j = 1;
      if (j < i)
      {
        IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(j);
        if (localIScriptableObject == null)
          arrayOfObject[(j - 1)] = paramIScriptArguments.getString(j);
        while (true)
        {
          j++;
          break;
          arrayOfObject[(j - 1)] = localIScriptableObject;
        }
      }
      XulPage.invokeActionWithArgs((XulView)this._xulItem, str, arrayOfObject);
    }
  }

  @ScriptMethod("getAction")
  public Boolean _script_getAction(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getStringId(0);
    if (i < 0)
    {
      String str = paramIScriptArguments.getString(0);
      i = XulPropNameCache.name2Id(str);
      paramIScriptContext.addIndexedString(str, i);
    }
    paramIScriptArguments.setResult(((XulView)this._xulItem).getActionString(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getAttr")
  public Boolean _script_getAttr(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getStringId(0);
    if (i < 0)
    {
      String str = paramIScriptArguments.getString(0);
      i = XulPropNameCache.name2Id(str);
      paramIScriptContext.addIndexedString(str, i);
    }
    paramIScriptArguments.setResult(((XulView)this._xulItem).getAttrString(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getData")
  public Boolean _script_getData(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getStringId(0);
    if (i < 0)
    {
      String str = paramIScriptArguments.getString(0);
      i = XulPropNameCache.name2Id(str);
      paramIScriptContext.addIndexedString(str, i);
    }
    paramIScriptArguments.setResult(((XulView)this._xulItem).getDataString(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getStyle")
  public Boolean _script_getStyle(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getStringId(0);
    if (i < 0)
    {
      String str = paramIScriptArguments.getString(0);
      i = XulPropNameCache.name2Id(str);
      paramIScriptContext.addIndexedString(str, i);
    }
    paramIScriptArguments.setResult(((XulView)this._xulItem).getStyleString(i));
    return Boolean.TRUE;
  }

  @ScriptGetter("binding")
  public String _script_getter_binding(IScriptContext paramIScriptContext)
  {
    return ((XulView)this._xulItem).getBinding();
  }

  @ScriptMethod("classList")
  public Object _script_getter_classList(IScriptContext paramIScriptContext)
  {
    return ((XulView)this._xulItem).getAllClass();
  }

  @ScriptGetter("hasFocus")
  public Boolean _script_getter_hasFocus(IScriptContext paramIScriptContext)
  {
    return Boolean.valueOf(((XulView)this._xulItem).hasFocus());
  }

  @ScriptGetter("id")
  public String _script_getter_id(IScriptContext paramIScriptContext)
  {
    return ((XulView)this._xulItem).getId();
  }

  @ScriptGetter("isBindingReady")
  public Boolean _script_getter_isBindingReady(IScriptContext paramIScriptContext)
  {
    return Boolean.valueOf(((XulView)this._xulItem).isBindingCtxReady());
  }

  @ScriptGetter("isBindingSuccess")
  public Boolean _script_getter_isBindingSuccess(IScriptContext paramIScriptContext)
  {
    return Boolean.valueOf(((XulView)this._xulItem).isBindingSuccess());
  }

  @ScriptGetter("isEnabled")
  public Boolean _script_getter_isEnabled(IScriptContext paramIScriptContext)
  {
    return Boolean.valueOf(((XulView)this._xulItem).isEnabled());
  }

  @ScriptGetter("isFocused")
  public Boolean _script_getter_isFocus(IScriptContext paramIScriptContext)
  {
    return Boolean.valueOf(((XulView)this._xulItem).isFocused());
  }

  @ScriptGetter("isFocusable")
  public Boolean _script_getter_isFocusable(IScriptContext paramIScriptContext)
  {
    return Boolean.valueOf(((XulView)this._xulItem).focusable());
  }

  @ScriptGetter("isVisible")
  public Boolean _script_getter_isVisible(IScriptContext paramIScriptContext)
  {
    for (Object localObject = (XulView)this._xulItem; (localObject != null) && (((XulView)localObject).isVisible()); localObject = ((XulView)localObject).getParent());
    if (localObject == null);
    for (boolean bool = true; ; bool = false)
      return Boolean.valueOf(bool);
  }

  @ScriptGetter("ownerPage")
  public Object _script_getter_ownerPage(IScriptContext paramIScriptContext)
  {
    return ((XulView)this._xulItem).getOwnerPage().getScriptableObject(paramIScriptContext.getScriptType());
  }

  @ScriptGetter("parent")
  public Object _script_getter_parent(IScriptContext paramIScriptContext)
  {
    if ((this._xulItem instanceof XulPage))
      return null;
    ((XulView)this._xulItem);
    XulArea localXulArea;
    do
      localXulArea = ((XulView)this._xulItem).getParent();
    while ((localXulArea instanceof XulLayout));
    return localXulArea.getScriptableObject(paramIScriptContext.getScriptType());
  }

  @ScriptGetter("text")
  public String _script_getter_text(IScriptContext paramIScriptContext)
  {
    return ((XulView)this._xulItem).getAttrString("text");
  }

  @ScriptGetter("type")
  public String _script_getter_type(IScriptContext paramIScriptContext)
  {
    return ((XulView)this._xulItem).getType();
  }

  @ScriptGetter("viewHeight")
  public Integer _script_getter_viewHeight(IScriptContext paramIScriptContext)
  {
    XulViewRender localXulViewRender = ((XulView)this._xulItem).getRender();
    if (localXulViewRender == null)
      return Integer.valueOf(0);
    return Integer.valueOf(XulUtils.roundToInt(XulUtils.calRectHeight(localXulViewRender.getFocusRect()) / localXulViewRender.getYScalar()));
  }

  @ScriptGetter("viewWidth")
  public Integer _script_getter_viewWidth(IScriptContext paramIScriptContext)
  {
    XulViewRender localXulViewRender = ((XulView)this._xulItem).getRender();
    if (localXulViewRender == null)
      return Integer.valueOf(0);
    return Integer.valueOf(XulUtils.roundToInt(XulUtils.calRectWidth(localXulViewRender.getFocusRect()) / localXulViewRender.getXScalar()));
  }

  @ScriptGetter("viewX")
  public Integer _script_getter_viewX(IScriptContext paramIScriptContext)
  {
    XulViewRender localXulViewRender = ((XulView)this._xulItem).getRender();
    if (localXulViewRender == null)
      return Integer.valueOf(0);
    return Integer.valueOf(XulUtils.roundToInt(localXulViewRender.getFocusRect().left / localXulViewRender.getXScalar()));
  }

  @ScriptGetter("viewY")
  public Integer _script_getter_viewY(IScriptContext paramIScriptContext)
  {
    XulViewRender localXulViewRender = ((XulView)this._xulItem).getRender();
    if (localXulViewRender == null)
      return Integer.valueOf(0);
    return Integer.valueOf(XulUtils.roundToInt(localXulViewRender.getFocusRect().top / localXulViewRender.getYScalar()));
  }

  @ScriptMethod("hasClass")
  public Boolean _script_hasClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = 0;
    int j = paramIScriptArguments.size();
    while (i < j)
    {
      if (!((XulView)this._xulItem).hasClass(paramIScriptArguments.getString(i)))
      {
        paramIScriptArguments.setResult(Boolean.FALSE.booleanValue());
        return Boolean.TRUE;
      }
      i++;
    }
    paramIScriptArguments.setResult(Boolean.TRUE.booleanValue());
    return Boolean.TRUE;
  }

  @ScriptMethod("isChildOf")
  public Boolean _script_isChildOf(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() != 1))
      return Boolean.FALSE;
    IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(0);
    if (localIScriptableObject == null)
      return Boolean.FALSE;
    XulView localXulView = (XulView)localIScriptableObject.getObjectValue().getUnwrappedObject();
    paramIScriptArguments.setResult(((XulView)this._xulItem).isChildOf(localXulView));
    return Boolean.TRUE;
  }

  @ScriptMethod("killFocus")
  public Boolean _script_killFocus(IScriptContext paramIScriptContext)
  {
    if (((this._xulItem instanceof XulPage)) || ((this._xulItem instanceof XulLayout)))
      return Boolean.FALSE;
    if (!((XulView)this._xulItem).isFocused())
      return Boolean.FALSE;
    ((XulView)this._xulItem).getRootLayout().requestFocus(null);
    return Boolean.TRUE;
  }

  @ScriptMethod("removeClass")
  public Boolean _script_removeClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    boolean bool1 = false;
    int i = 0;
    int j = paramIScriptArguments.size();
    while (true)
      if (i < j)
        try
        {
          boolean bool2 = ((XulView)this._xulItem).removeClass(paramIScriptArguments.getString(i));
          if ((bool2) || (bool1));
          for (bool1 = true; ; bool1 = false)
          {
            i++;
            break;
          }
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
    if (bool1)
      ((XulView)this._xulItem).resetRender();
    return Boolean.valueOf(bool1);
  }

  @ScriptMethod("requestFocus")
  public Boolean _script_requestFocus(IScriptContext paramIScriptContext)
  {
    if (((this._xulItem instanceof XulPage)) || ((this._xulItem instanceof XulLayout)))
      return Boolean.FALSE;
    if (((XulView)this._xulItem).isFocused())
      return Boolean.FALSE;
    ((XulView)this._xulItem).getRootLayout().requestFocus((XulView)this._xulItem);
    return Boolean.TRUE;
  }

  @ScriptMethod("setAttr")
  public Boolean _script_setAttr(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() != 2))
      return Boolean.FALSE;
    String str1 = paramIScriptArguments.getString(1);
    int i = paramIScriptArguments.getStringId(0);
    if (i < 0)
    {
      String str2 = paramIScriptArguments.getString(0);
      i = XulPropNameCache.name2Id(str2);
      paramIScriptContext.addIndexedString(str2, i);
    }
    ((XulView)this._xulItem).setAttr(i, str1);
    ((XulView)this._xulItem).resetRender();
    return Boolean.TRUE;
  }

  @ScriptMethod("setStyle")
  public Boolean _script_setStyle(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() != 2))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getStringId(0);
    String str1 = paramIScriptArguments.getString(1);
    if (i < 0)
    {
      String str2 = paramIScriptArguments.getString(0);
      i = XulPropNameCache.name2Id(str2);
      paramIScriptContext.addIndexedString(str2, i);
    }
    ((XulView)this._xulItem).setStyle(i, str1);
    ((XulView)this._xulItem).resetRender();
    return Boolean.TRUE;
  }

  @ScriptSetter("isEnabled")
  public void _script_setter_isEnabled(IScriptContext paramIScriptContext, Object paramObject)
  {
    if ((paramObject != null) && (!"false".equalsIgnoreCase(String.valueOf(paramObject))));
    for (boolean bool = true; ; bool = false)
    {
      if (((XulView)this._xulItem).isEnabled() != bool)
      {
        ((XulView)this._xulItem).setEnabled(bool);
        ((XulView)this._xulItem).resetRender();
      }
      return;
    }
  }

  @ScriptSetter("text")
  public String _script_setter_text(IScriptContext paramIScriptContext, Object paramObject)
  {
    String str = String.valueOf(paramObject);
    ((XulView)this._xulItem).setAttr("text", str);
    ((XulView)this._xulItem).resetRender();
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulViewScriptableObject
 * JD-Core Version:    0.6.2
 */