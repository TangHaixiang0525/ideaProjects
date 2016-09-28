package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.Wrapper.XulPageSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;

public class XulPageSliderScriptableObject extends XulAreaScriptableObject
{
  XulPageSliderAreaWrapper _wrapper;

  public XulPageSliderScriptableObject(XulArea paramXulArea)
  {
    super(paramXulArea);
  }

  private boolean _initWrapper()
  {
    if (this._wrapper != null)
      return true;
    this._wrapper = XulPageSliderAreaWrapper.fromXulView((XulView)this._xulItem);
    if (this._wrapper != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  @ScriptGetter("currentPage")
  public int _script_getter_currentPage(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return -1;
    return this._wrapper.getCurrentPage();
  }

  @ScriptGetter("pageCount")
  public int _script_getter_pageCount(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return -1;
    return this._wrapper.getPageCount();
  }

  @ScriptMethod("invokeImageGC")
  public Boolean _script_invokeImageGC(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    if (paramIScriptArguments.size() != 1)
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    this._wrapper.invokeImageGC(i);
    return Boolean.TRUE;
  }

  @ScriptMethod("setCurrentPage")
  public Boolean _script_setCurrentPage(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    if (paramIScriptArguments.size() != 1)
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    return Boolean.valueOf(this._wrapper.setCurrentPage(i));
  }

  @ScriptMethod("slideDown")
  public Boolean _script_slideDown(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.slideDown();
    return Boolean.TRUE;
  }

  @ScriptMethod("slideLeft")
  public Boolean _script_slideLeft(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.slideLeft();
    return Boolean.TRUE;
  }

  @ScriptMethod("slideNext")
  public Boolean _script_slideNext(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.slideNext();
    return Boolean.TRUE;
  }

  @ScriptMethod("slidePrev")
  public Boolean _script_slidePrev(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.slidePrev();
    return Boolean.valueOf(true);
  }

  @ScriptMethod("slideRight")
  public Boolean _script_slideRight(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.slideRight();
    return Boolean.TRUE;
  }

  @ScriptMethod("slideUp")
  public Boolean _script_slideUp(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.slideUp();
    return Boolean.TRUE;
  }

  @ScriptMethod("syncPages")
  public Boolean _script_syncPages(IScriptContext paramIScriptContext)
  {
    if (!_initWrapper())
      return Boolean.FALSE;
    this._wrapper.syncPages();
    return Boolean.TRUE;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulPageSliderScriptableObject
 * JD-Core Version:    0.6.2
 */