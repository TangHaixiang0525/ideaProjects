package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.Wrapper.XulImageItemWrapper;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulView;

public class XulImageScriptableObject extends XulItemScriptableObject
{
  XulImageItemWrapper _wrapper;

  public XulImageScriptableObject(XulItem paramXulItem)
  {
    super(paramXulItem);
  }

  private boolean _initWrapper()
  {
    if (this._wrapper != null)
      return true;
    this._wrapper = XulImageItemWrapper.fromXulView((XulView)this._xulItem);
    if (this._wrapper != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  @ScriptMethod("getImageHeight")
  public Boolean _script_getImageHeight(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.getImageHeight(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getImageOpacity")
  public Boolean _script_getImageOpacity(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.getImageOpacity(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getImageResolvedUrl")
  public Boolean _script_getImageResolvedUrl(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.getImageResolvedUrl(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getImageUrl")
  public Boolean _script_getImageUrl(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.getImageUrl(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("getImageWidth")
  public Boolean _script_getImageWidth(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.getImageWidth(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("hasImageLayer")
  public Boolean _script_hasImageLayer(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.hasImageLayer(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("isImageLoaded")
  public Boolean _script_isImageLoaded(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.isImageLoaded(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("isImageVisible")
  public Boolean _script_isImageVisible(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.isImageVisible(i));
    return Boolean.TRUE;
  }

  @ScriptMethod("resetAnimation")
  public Boolean _script_resetAnimation(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((!_initWrapper()) || (paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    int i = paramIScriptArguments.getInteger(0);
    paramIScriptArguments.setResult(this._wrapper.resetAnimation(i));
    return Boolean.TRUE;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulImageScriptableObject
 * JD-Core Version:    0.6.2
 */