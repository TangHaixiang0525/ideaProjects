package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulMassiveScriptableObject extends XulAreaScriptableObject
{
  XulMassiveAreaWrapper _massiveAdapter;

  public XulMassiveScriptableObject(XulArea paramXulArea)
  {
    super(paramXulArea);
    this._massiveAdapter = XulMassiveAreaWrapper.fromXulView(paramXulArea);
  }

  @ScriptMethod("clear")
  public Boolean _script_clear(IScriptContext paramIScriptContext)
  {
    if (this._massiveAdapter == null)
      return Boolean.FALSE;
    this._massiveAdapter.clear();
    return Boolean.TRUE;
  }

  @ScriptMethod("getItemIdx")
  public Boolean _script_getItemIdx(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if (this._massiveAdapter == null)
      return Boolean.FALSE;
    IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(0);
    if (localIScriptableObject == null)
      paramIScriptArguments.setResult(-1);
    while (true)
    {
      return Boolean.TRUE;
      XulView localXulView = (XulView)((XulViewScriptableObject)localIScriptableObject.getObjectValue())._xulItem;
      paramIScriptArguments.setResult(this._massiveAdapter.getItemIdx(localXulView));
    }
  }

  @ScriptGetter("isFixed")
  public Boolean _script_getter_isFixed(IScriptContext paramIScriptContext)
  {
    if (this._massiveAdapter == null)
      return Boolean.FALSE;
    return Boolean.valueOf(this._massiveAdapter.fixedItem());
  }

  @ScriptGetter("itemNum")
  public Integer _script_getter_itemNum(IScriptContext paramIScriptContext)
  {
    if (this._massiveAdapter == null)
      return Integer.valueOf(0);
    return Integer.valueOf(this._massiveAdapter.itemNum());
  }

  @ScriptMethod("makeChildVisible")
  public Boolean _script_makeChildVisible(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if (this._massiveAdapter == null)
      return Boolean.FALSE;
    int i = paramIScriptArguments.size();
    if ((i < 2) || (i > 6))
      return Boolean.FALSE;
    IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(0);
    XulArea localXulArea;
    if (localIScriptableObject == null)
      localXulArea = ((XulArea)this._xulItem).findParentByType("slider");
    while (localXulArea == null)
    {
      return Boolean.FALSE;
      XulView localXulView = (XulView)((XulViewScriptableObject)localIScriptableObject.getObjectValue())._xulItem;
      boolean bool1 = localXulView instanceof XulArea;
      localXulArea = null;
      if (bool1)
        localXulArea = (XulArea)localXulView;
    }
    int j = paramIScriptArguments.getInteger(1);
    switch (i)
    {
    default:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      return Boolean.TRUE;
      this._massiveAdapter.makeChildVisible(localXulArea, j);
      continue;
      String str2 = paramIScriptArguments.getString(2);
      if ("true".equals(str2))
      {
        this._massiveAdapter.makeChildVisible(localXulArea, j, true);
      }
      else if ("false".equals(str2))
      {
        this._massiveAdapter.makeChildVisible(localXulArea, j, false);
      }
      else
      {
        this._massiveAdapter.makeChildVisible(localXulArea, j, XulUtils.tryParseFloat(str2, 0.0F), true);
        continue;
        float f5 = paramIScriptArguments.getFloat(2);
        String str1 = paramIScriptArguments.getString(3);
        if ("true".equals(str1))
        {
          this._massiveAdapter.makeChildVisible(localXulArea, j, f5, true);
        }
        else if ("false".equals(str1))
        {
          this._massiveAdapter.makeChildVisible(localXulArea, j, f5, false);
        }
        else
        {
          this._massiveAdapter.makeChildVisible(localXulArea, j, f5, XulUtils.tryParseFloat(str1, 0.0F), true);
          continue;
          float f3 = paramIScriptArguments.getFloat(2);
          float f4 = paramIScriptArguments.getFloat(3);
          this._massiveAdapter.makeChildVisible(localXulArea, j, f3, f4);
          continue;
          float f1 = paramIScriptArguments.getFloat(2);
          float f2 = paramIScriptArguments.getFloat(3);
          boolean bool2 = paramIScriptArguments.getBoolean(4);
          this._massiveAdapter.makeChildVisible(localXulArea, j, f1, f2, bool2);
        }
      }
    }
  }

  @ScriptMethod("syncContentView")
  public Boolean _script_removeItem(IScriptContext paramIScriptContext)
  {
    if (this._massiveAdapter == null)
      return Boolean.FALSE;
    this._massiveAdapter.syncContentView();
    return Boolean.TRUE;
  }

  @ScriptMethod("removeItem")
  public Boolean _script_removeItem(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if (this._massiveAdapter == null)
      return Boolean.FALSE;
    IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(0);
    if (localIScriptableObject == null)
      this._massiveAdapter.removeItem(XulUtils.tryParseInt(paramIScriptArguments.getString(0), -1));
    while (true)
    {
      return Boolean.TRUE;
      XulView localXulView = (XulView)((XulViewScriptableObject)localIScriptableObject.getObjectValue())._xulItem;
      this._massiveAdapter.removeItem(localXulView);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulMassiveScriptableObject
 * JD-Core Version:    0.6.2
 */