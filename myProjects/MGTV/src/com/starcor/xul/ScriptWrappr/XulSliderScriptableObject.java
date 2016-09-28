package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Script.XulScriptableObject;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;

public class XulSliderScriptableObject extends XulAreaScriptableObject
{
  XulSliderAreaWrapper _wrapper;

  public XulSliderScriptableObject(XulArea paramXulArea)
  {
    super(paramXulArea);
  }

  private boolean _initWrapper()
  {
    if (this._wrapper != null)
      return true;
    this._wrapper = XulSliderAreaWrapper.fromXulView((XulView)this._xulItem);
    if (this._wrapper != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  @ScriptMethod("activateScrollBar")
  public Boolean _script_activateScrollBar(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.activateScrollBar();
    return Boolean.TRUE;
  }

  @ScriptGetter("scrollDelta")
  public int _script_getter_scrollDelta(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return -1;
    XulViewRender localXulViewRender = ((XulArea)this._xulItem).getRender();
    if (localXulViewRender == null)
      return 0;
    if (this._wrapper.isVertical());
    for (double d = localXulViewRender.getYScalar(); ; d = localXulViewRender.getXScalar())
    {
      float f = (float)d;
      return (int)(this._wrapper.getScrollDelta() / f);
    }
  }

  @ScriptGetter("scrollPos")
  public int _script_getter_scrollPos(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return -1;
    XulViewRender localXulViewRender = ((XulArea)this._xulItem).getRender();
    if (localXulViewRender == null)
      return 0;
    if (this._wrapper.isVertical());
    for (double d = localXulViewRender.getYScalar(); ; d = localXulViewRender.getXScalar())
    {
      float f = (float)d;
      return (int)(this._wrapper.getScrollPos() / f);
    }
  }

  @ScriptGetter("scrollRange")
  public int _script_getter_scrollRange(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return -1;
    XulViewRender localXulViewRender = ((XulArea)this._xulItem).getRender();
    if (localXulViewRender == null)
      return 0;
    if (this._wrapper.isVertical());
    for (double d = localXulViewRender.getYScalar(); ; d = localXulViewRender.getXScalar())
    {
      float f = (float)d;
      return (int)(this._wrapper.getScrollRange() / f);
    }
  }

  @ScriptMethod("makeChildVisible")
  public Boolean _script_makeChildVisible(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(0);
    if (localIScriptableObject == null)
      return Boolean.FALSE;
    switch (paramIScriptArguments.size())
    {
    default:
      return Boolean.FALSE;
    case 1:
      return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject()));
    case 2:
      String str2 = paramIScriptArguments.getString(1);
      if ("true".equals(str2))
        return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), true));
      if ("false".equals(str2))
        return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), false));
      return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), paramIScriptArguments.getFloat(1)));
    case 3:
      String str1 = paramIScriptArguments.getString(2);
      if ("true".equals(str1))
        return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), paramIScriptArguments.getFloat(1), true));
      if ("false".equals(str1))
        return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), paramIScriptArguments.getFloat(1), false));
      return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), paramIScriptArguments.getFloat(1), paramIScriptArguments.getFloat(2)));
    case 4:
    }
    return Boolean.valueOf(this._wrapper.makeChildVisible((XulView)localIScriptableObject.getObjectValue().getUnwrappedObject(), paramIScriptArguments.getFloat(1), paramIScriptArguments.getFloat(2), paramIScriptArguments.getBoolean(3)));
  }

  @ScriptMethod("scrollByPage")
  public Boolean _script_scrollByPage(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    int i = paramIScriptArguments.size();
    if ((!_initWrapper()) || (i < 1) || (i > 2))
      return Boolean.FALSE;
    int j = paramIScriptArguments.getInteger(0);
    if (j == 0)
      return Boolean.FALSE;
    boolean bool = true;
    if (i == 2)
      bool = paramIScriptArguments.getBoolean(1);
    if (((XulArea)this._xulItem).getRender() == null)
      return Boolean.FALSE;
    this._wrapper.scrollByPage(j, bool);
    return Boolean.TRUE;
  }

  @ScriptMethod("scrollTo")
  public Boolean _script_scrollTo(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    int i = paramIScriptArguments.size();
    if ((!_initWrapper()) || (i < 1) || (i > 2))
      return Boolean.FALSE;
    int j = paramIScriptArguments.getInteger(0);
    if (j < 0)
      return Boolean.FALSE;
    boolean bool = true;
    if (i == 2)
      bool = paramIScriptArguments.getBoolean(1);
    XulViewRender localXulViewRender = ((XulArea)this._xulItem).getRender();
    if (localXulViewRender == null)
      return Boolean.FALSE;
    if (this._wrapper.isVertical());
    for (double d = localXulViewRender.getYScalar(); ; d = localXulViewRender.getXScalar())
    {
      float f = (float)d;
      this._wrapper.scrollTo((int)(f * j), bool);
      return Boolean.TRUE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulSliderScriptableObject
 * JD-Core Version:    0.6.2
 */