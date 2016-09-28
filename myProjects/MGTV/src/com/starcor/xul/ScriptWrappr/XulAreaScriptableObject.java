package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptArray;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class XulAreaScriptableObject extends XulViewScriptableObject<XulArea>
{
  private WeakHashMap<XulArrayList<XulView>, IScriptArray> _cachedScriptArray;

  public XulAreaScriptableObject(XulArea paramXulArea)
  {
    super(paramXulArea);
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
    for (XulView localXulView = XulPage.findFirstItemByClass((XulArea)this._xulItem, str); ; localXulView = XulPage.findFirstItemByClass((XulArea)this._xulItem, arrayOfString))
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
    XulView localXulView = ((XulArea)this._xulItem).getOwnerPage().findItemById((XulArea)this._xulItem, str);
    if (localXulView == null)
      return Boolean.FALSE;
    paramIScriptArguments.setResult(localXulView.getScriptableObject(paramIScriptContext.getScriptType()));
    return Boolean.TRUE;
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
    for (XulArrayList localXulArrayList = XulPage.findItemsByClass((XulArea)this._xulItem, str); ; localXulArrayList = XulPage.findItemsByClass((XulArea)this._xulItem, arrayOfString))
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
        arrayOfString[i] = paramIScriptArguments.getString(i);
        i++;
      }
    }
  }

  @ScriptMethod("getDynamicFocus")
  public Boolean _script_getDynamicFocus(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    XulView localXulView = ((XulArea)this._xulItem).getDynamicFocus();
    if (localXulView == null)
      return Boolean.FALSE;
    paramIScriptArguments.setResult(localXulView.getScriptableObject(paramIScriptContext.getScriptType()));
    return Boolean.TRUE;
  }

  @ScriptGetter("children")
  public Object _script_getter_children(IScriptContext paramIScriptContext)
  {
    final ArrayList localArrayList = new ArrayList();
    final String str = paramIScriptContext.getScriptType();
    ((XulArea)this._xulItem).eachChild(new XulArea.XulAreaIterator()
    {
      private void collectChild(XulView paramAnonymousXulView)
      {
        IScriptableObject localIScriptableObject = paramAnonymousXulView.getScriptableObject(str);
        localArrayList.add(localIScriptableObject);
      }

      public boolean onXulArea(int paramAnonymousInt, XulArea paramAnonymousXulArea)
      {
        collectChild(paramAnonymousXulArea);
        return true;
      }

      public boolean onXulItem(int paramAnonymousInt, XulItem paramAnonymousXulItem)
      {
        collectChild(paramAnonymousXulItem);
        return true;
      }
    });
    return localArrayList;
  }

  @ScriptMethod("setDynamicFocus")
  public Boolean _script_setDynamicFocus(IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments)
  {
    IScriptableObject localIScriptableObject = paramIScriptArguments.getScriptableObject(0);
    if (localIScriptableObject == null)
      return Boolean.valueOf(((XulArea)this._xulItem).setDynamicFocus(null));
    XulView localXulView = (XulView)((XulViewScriptableObject)localIScriptableObject.getObjectValue())._xulItem;
    ((XulArea)this._xulItem).setDynamicFocus(localXulView);
    return Boolean.TRUE;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulAreaScriptableObject
 * JD-Core Version:    0.6.2
 */