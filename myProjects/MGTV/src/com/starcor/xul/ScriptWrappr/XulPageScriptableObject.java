package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptArray;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Script.XulScriptableObject;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class XulPageScriptableObject extends XulViewScriptableObject<XulPage>
{
  private WeakHashMap<XulArrayList<XulView>, IScriptArray> _cachedScriptArray;

  public XulPageScriptableObject(XulPage paramXulPage)
  {
    super(paramXulPage);
  }

  @ScriptMethod("findFirstItemByClass")
  public Boolean _script_findFirstItemByClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str;
    if (paramIScriptArguments.size() == 1)
      str = paramIScriptArguments.getString(0);
    String[] arrayOfString;
    for (XulView localXulView = XulPage.findFirstItemByClass(((XulPage)this._xulItem).getLayout(), str); ; localXulView = XulPage.findFirstItemByClass(((XulPage)this._xulItem).getLayout(), arrayOfString))
    {
      if (localXulView != null)
        paramIScriptArguments.setResult(localXulView.getScriptableObject(paramIScriptContext.getScriptType()));
      return Boolean.TRUE;
      arrayOfString = new String[paramIScriptArguments.size()];
      int i = 0;
      int j = paramIScriptArguments.size();
      while (i < j)
      {
        arrayOfString[i] = paramIScriptArguments.getString(i);
        i++;
      }
    }
  }

  @ScriptMethod("findItemById")
  public Boolean _script_findItemById(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    XulView localXulView = ((XulPage)this._xulItem).findItemById(str);
    if (localXulView == null)
      return Boolean.FALSE;
    paramIScriptArguments.setResult(localXulView.getScriptableObject(paramIScriptContext.getScriptType()));
    return Boolean.FALSE;
  }

  @ScriptMethod("findItemsByClass")
  public Boolean _script_findItemsByClass(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str;
    if (paramIScriptArguments.size() == 1)
      str = paramIScriptArguments.getString(0);
    String[] arrayOfString;
    for (XulArrayList localXulArrayList = XulPage.findItemsByClass(((XulPage)this._xulItem).getLayout(), str); ; localXulArrayList = XulPage.findItemsByClass(((XulPage)this._xulItem).getLayout(), arrayOfString))
    {
      if (this._cachedScriptArray == null)
        this._cachedScriptArray = new WeakHashMap();
      IScriptArray localIScriptArray = (IScriptArray)this._cachedScriptArray.get(localXulArrayList);
      if (localIScriptArray == null)
      {
        localIScriptArray = paramIScriptContext.createScriptArray();
        localIScriptArray.addAll(localXulArrayList);
        this._cachedScriptArray.put(localXulArrayList, localIScriptArray);
      }
      paramIScriptArguments.setResult(localIScriptArray);
      return Boolean.TRUE;
      arrayOfString = new String[paramIScriptArguments.size()];
      int i = 0;
      int j = paramIScriptArguments.size();
      while (i < j)
      {
        arrayOfString[i] = String.valueOf(paramIScriptArguments.getString(i));
        i++;
      }
    }
  }

  @ScriptGetter("currentFocus")
  public Object _script_getter_currentFocus(IScriptContext paramIScriptContext)
  {
    XulView localXulView = ((XulPage)this._xulItem).getLayout().getFocus();
    if (localXulView == null)
      return null;
    return localXulView.getScriptableObject(paramIScriptContext.getScriptType());
  }

  @ScriptMethod("popAllStates")
  public Boolean _script_popAllStates(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments != null) && (paramIScriptArguments.size() == 1))
    {
      paramIScriptArguments.setResult(((XulPage)this._xulItem).popAllStates(paramIScriptArguments.getBoolean(0)));
      return Boolean.TRUE;
    }
    paramIScriptArguments.setResult(((XulPage)this._xulItem).popAllStates(false));
    return Boolean.TRUE;
  }

  @ScriptMethod("popStates")
  public Boolean _script_popStates(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments != null) && (paramIScriptArguments.size() == 1))
    {
      paramIScriptArguments.setResult(((XulPage)this._xulItem).popStates(paramIScriptArguments.getBoolean(0)));
      return Boolean.TRUE;
    }
    paramIScriptArguments.setResult(((XulPage)this._xulItem).popStates());
    return Boolean.TRUE;
  }

  @ScriptMethod("pushStates")
  public Boolean _script_pushStates(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() <= 2))
      return Boolean.FALSE;
    Object[] arrayOfObject = new Object[paramIScriptArguments.size()];
    int i = 0;
    int j = paramIScriptArguments.size();
    if (i < j)
    {
      IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(i);
      if (localIScriptableObject != null)
        arrayOfObject[i] = localIScriptableObject.getObjectValue().getUnwrappedObject();
      while (true)
      {
        i++;
        break;
        arrayOfObject[i] = paramIScriptArguments.getString(i);
      }
    }
    ((XulPage)this._xulItem).pushStates(arrayOfObject);
    return Boolean.TRUE;
  }

  @ScriptMethod("queryBindingDataString")
  public Boolean _script_queryBindingDataString(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    ArrayList localArrayList = ((XulPage)this._xulItem).queryBindingData(str);
    if ((localArrayList == null) || (localArrayList.isEmpty()))
      return Boolean.FALSE;
    paramIScriptArguments.setResult(((XulDataNode)localArrayList.get(0)).getValue());
    return Boolean.TRUE;
  }

  @ScriptMethod("refreshBinding")
  public Boolean _script_refreshBinding(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    if ((paramIScriptArguments == null) || (paramIScriptArguments.size() == 0) || (paramIScriptArguments.size() > 2))
      return Boolean.FALSE;
    String str = paramIScriptArguments.getString(0);
    switch (paramIScriptArguments.size())
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return Boolean.TRUE;
      ((XulPage)this._xulItem).refreshBinding(str);
      continue;
      ((XulPage)this._xulItem).refreshBinding(str, paramIScriptArguments.getString(1));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulPageScriptableObject
 * JD-Core Version:    0.6.2
 */