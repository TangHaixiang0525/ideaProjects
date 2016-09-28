package com.starcor.xul.Render;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.xul.Events.XulActionEvent;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Utils.XulAreaChildrenCollectorAllFocusable;
import com.starcor.xul.Utils.XulAreaChildrenCollectorByClass;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class XulGroupRender extends XulAreaRender
{
  public static final String DEFAULT_GROUP_CHECKED_CLASS = "group-checked";
  private static final String TAG = XulGroupRender.class.getSimpleName();
  private static XulAreaChildrenCollectorByClass _collectCheckedChildren = new XulAreaChildrenCollectorByClass();
  private static XulAreaChildrenCollectorAllFocusable _focusCollector = new XulAreaChildrenCollectorAllFocusable();
  String _defaultCheckStateClass;
  GroupMode _defaultGroupMode = GroupMode.GM_CHECK;
  XulCachedHashMap<String, GroupInfo> _groupClsMap;

  public XulGroupRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
  }

  private void addGroup(String paramString1, String paramString2, String paramString3)
  {
    if (this._groupClsMap == null)
      this._groupClsMap = new XulCachedHashMap();
    GroupMode localGroupMode = GroupMode.GM_CHECK;
    if ("check".equals(paramString1))
      localGroupMode = GroupMode.GM_CHECK;
    while (true)
    {
      GroupInfo localGroupInfo = new GroupInfo();
      localGroupInfo.groupClass = paramString2;
      localGroupInfo.mode = localGroupMode;
      localGroupInfo.checkClass = paramString3;
      this._groupClsMap.put(paramString2, localGroupInfo);
      return;
      if ("radio".equals(paramString1))
        localGroupMode = GroupMode.GM_RADIO;
      else
        Log.w(TAG, "unsupported group mode! " + paramString1);
    }
  }

  private boolean checkClick(GroupInfo paramGroupInfo, XulView paramXulView)
  {
    if (paramGroupInfo.mode != GroupMode.GM_CHECK)
      return false;
    String str = paramGroupInfo.checkClass;
    if (TextUtils.isEmpty(str))
      str = this._defaultCheckStateClass;
    if (paramXulView.hasClass(str))
      if (paramXulView.removeClass(str))
      {
        notifyUncheckedEvent(paramXulView);
        paramXulView.resetRender();
      }
    while (true)
    {
      return true;
      notifyUncheckedEvent(paramXulView);
      continue;
      if (paramXulView.addClass(str))
      {
        notifyCheckedEvent(paramXulView);
        paramXulView.resetRender();
      }
      else
      {
        notifyCheckedEvent(paramXulView);
      }
    }
  }

  private GroupInfo getGroupInfoByView(XulView paramXulView)
  {
    GroupInfo localGroupInfo;
    if (this._groupClsMap == null)
    {
      localGroupInfo = new GroupInfo();
      localGroupInfo.groupClass = null;
      localGroupInfo.mode = this._defaultGroupMode;
      localGroupInfo.checkClass = this._defaultCheckStateClass;
      return localGroupInfo;
    }
    Iterator localIterator = this._groupClsMap.keySet().iterator();
    String str;
    do
    {
      boolean bool = localIterator.hasNext();
      localGroupInfo = null;
      if (!bool)
        break;
      str = (String)localIterator.next();
    }
    while (!paramXulView.hasClass(str));
    return (GroupInfo)this._groupClsMap.get(str);
  }

  private void notifyCheckedEvent(XulView paramXulView)
  {
    XulPage.invokeActionNoPopup(paramXulView, "checked");
  }

  private void notifyUncheckedEvent(XulView paramXulView)
  {
    XulPage.invokeActionNoPopup(paramXulView, "unchecked");
  }

  private boolean radioClick(GroupInfo paramGroupInfo, XulView paramXulView)
  {
    if (paramGroupInfo.mode != GroupMode.GM_RADIO)
      return false;
    String str = paramGroupInfo.checkClass;
    if (TextUtils.isEmpty(str))
      str = this._defaultCheckStateClass;
    int i;
    label76: XulView localXulView;
    if (paramGroupInfo.groupClass == null)
    {
      _collectCheckedChildren.begin(str);
      this._area.eachChild(_collectCheckedChildren);
      XulArrayList localXulArrayList = _collectCheckedChildren.end();
      if (localXulArrayList == null)
        break label183;
      i = 0;
      int j = localXulArrayList.size();
      if (i >= j)
        break label183;
      localXulView = (XulView)localXulArrayList.get(i);
      if (localXulView != paramXulView)
        break label142;
    }
    while (true)
    {
      i++;
      break label76;
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = paramGroupInfo.groupClass;
      arrayOfString[1] = str;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      break;
      label142: if (localXulView.hasClass(str))
        if (localXulView.removeClass(str))
        {
          notifyUncheckedEvent(localXulView);
          localXulView.resetRender();
        }
        else
        {
          notifyUncheckedEvent(localXulView);
        }
    }
    label183: _collectCheckedChildren.clear();
    if (!paramXulView.hasClass(str))
    {
      if (!paramXulView.addClass(str))
        break label216;
      notifyCheckedEvent(paramXulView);
      paramXulView.resetRender();
    }
    while (true)
    {
      return true;
      label216: notifyCheckedEvent(paramXulView);
    }
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "group", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulGroupRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulGroupRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
    XulRenderFactory.registerBuilder("area", "radio", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulGroupRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        XulGroupRender localXulGroupRender = new XulGroupRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
        localXulGroupRender._defaultGroupMode = XulGroupRender.GroupMode.GM_RADIO;
        return localXulGroupRender;
      }
    });
    XulRenderFactory.registerBuilder("area", "check", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulGroupRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        XulGroupRender localXulGroupRender = new XulGroupRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
        localXulGroupRender._defaultGroupMode = XulGroupRender.GroupMode.GM_CHECK;
        return localXulGroupRender;
      }
    });
  }

  public ArrayList<ArrayList<XulView>> getAllCheckedGroups()
  {
    ArrayList localArrayList = new ArrayList();
    if (this._groupClsMap == null)
    {
      _collectCheckedChildren.begin(this._defaultCheckStateClass);
      this._area.eachChild(_collectCheckedChildren);
      localArrayList.add((ArrayList)_collectCheckedChildren.end().clone());
      _collectCheckedChildren.clear();
      return localArrayList;
    }
    Iterator localIterator = this._groupClsMap.values().iterator();
    while (localIterator.hasNext())
    {
      GroupInfo localGroupInfo = (GroupInfo)localIterator.next();
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = localGroupInfo.groupClass;
      arrayOfString[1] = localGroupInfo.checkClass;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      this._area.eachChild(_collectCheckedChildren);
      localArrayList.add((ArrayList)_collectCheckedChildren.end().clone());
    }
    _collectCheckedChildren.clear();
    return localArrayList;
  }

  public ArrayList<XulView> getAllCheckedItems()
  {
    ArrayList localArrayList = new ArrayList();
    if (this._groupClsMap == null)
    {
      _collectCheckedChildren.begin(this._defaultCheckStateClass);
      this._area.eachChild(_collectCheckedChildren);
      localArrayList.addAll(_collectCheckedChildren.end());
      _collectCheckedChildren.clear();
      return localArrayList;
    }
    Iterator localIterator = this._groupClsMap.values().iterator();
    while (localIterator.hasNext())
    {
      GroupInfo localGroupInfo = (GroupInfo)localIterator.next();
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = localGroupInfo.groupClass;
      arrayOfString[1] = localGroupInfo.checkClass;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      this._area.eachChild(_collectCheckedChildren);
      localArrayList.addAll(_collectCheckedChildren.end());
    }
    _collectCheckedChildren.clear();
    return localArrayList;
  }

  public ArrayList<XulView> getAllGroupItems()
  {
    ArrayList localArrayList = new ArrayList();
    if (this._groupClsMap == null)
    {
      _focusCollector.begin();
      this._area.eachChild(_focusCollector);
      localArrayList.addAll(_focusCollector.end());
      _focusCollector.clear();
      return localArrayList;
    }
    Iterator localIterator = this._groupClsMap.values().iterator();
    while (localIterator.hasNext())
    {
      GroupInfo localGroupInfo = (GroupInfo)localIterator.next();
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = localGroupInfo.groupClass;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      this._area.eachChild(_collectCheckedChildren);
      localArrayList.addAll(_collectCheckedChildren.end());
    }
    _collectCheckedChildren.clear();
    return localArrayList;
  }

  public ArrayList<ArrayList<XulView>> getAllGroups()
  {
    ArrayList localArrayList = new ArrayList();
    if (this._groupClsMap == null)
    {
      _focusCollector.begin();
      this._area.eachChild(_focusCollector);
      localArrayList.add((ArrayList)_focusCollector.end().clone());
      _focusCollector.clear();
      return localArrayList;
    }
    Iterator localIterator = this._groupClsMap.values().iterator();
    while (localIterator.hasNext())
    {
      GroupInfo localGroupInfo = (GroupInfo)localIterator.next();
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = localGroupInfo.groupClass;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      this._area.eachChild(_collectCheckedChildren);
      XulArrayList localXulArrayList = _collectCheckedChildren.end();
      localXulArrayList.add((XulView)localXulArrayList.clone());
    }
    _collectCheckedChildren.clear();
    return localArrayList;
  }

  public ArrayList<XulView> getGroupByItem(XulView paramXulView)
  {
    if (!this._area.hasChild(paramXulView));
    GroupInfo localGroupInfo;
    do
    {
      return null;
      if (this._groupClsMap == null)
      {
        _focusCollector.begin();
        this._area.eachChild(_focusCollector);
        XulArrayList localXulArrayList = _focusCollector.end();
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.addAll(localXulArrayList);
        _focusCollector.clear();
        return localArrayList2;
      }
      localGroupInfo = getGroupInfoByView(paramXulView);
    }
    while (localGroupInfo == null);
    ArrayList localArrayList1 = new ArrayList();
    XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = localGroupInfo.groupClass;
    localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
    this._area.eachChild(_collectCheckedChildren);
    localArrayList1.addAll(_collectCheckedChildren.end());
    _collectCheckedChildren.clear();
    return localArrayList1;
  }

  public boolean isChecked(XulView paramXulView)
  {
    GroupInfo localGroupInfo = getGroupInfoByView(paramXulView);
    if (localGroupInfo == null)
      return false;
    return paramXulView.hasClass(localGroupInfo.checkClass);
  }

  public boolean onChildDoActionEvent(XulActionEvent paramXulActionEvent)
  {
    if ("click".equals(paramXulActionEvent.action))
    {
      XulView localXulView = paramXulActionEvent.eventSource;
      GroupInfo localGroupInfo = getGroupInfoByView(localXulView);
      if (localGroupInfo != null)
      {
        switch (4.$SwitchMap$com$starcor$xul$Render$XulGroupRender$GroupMode[localGroupInfo.mode.ordinal()])
        {
        default:
        case 1:
        case 2:
        }
        do
        {
          do
            return true;
          while (!checkClick(localGroupInfo, localXulView));
          paramXulActionEvent.noPopup = true;
          return true;
        }
        while (!radioClick(localGroupInfo, localXulView));
        paramXulActionEvent.noPopup = true;
        return true;
      }
    }
    return false;
  }

  public void setAllChecked()
  {
    if (this._groupClsMap == null)
    {
      _focusCollector.begin();
      this._area.eachChild(_focusCollector);
      XulArrayList localXulArrayList2 = _focusCollector.end();
      for (int j = 0; ; j++)
      {
        XulView localXulView2;
        if (j < localXulArrayList2.size())
        {
          localXulView2 = (XulView)localXulArrayList2.get(j);
          if (!localXulView2.hasClass(this._defaultCheckStateClass))
          {
            if (!localXulView2.addClass(this._defaultCheckStateClass))
              break label108;
            notifyCheckedEvent(localXulView2);
            localXulView2.resetRender();
          }
        }
        while (this._defaultGroupMode == GroupMode.GM_RADIO)
        {
          _focusCollector.clear();
          return;
          label108: notifyCheckedEvent(localXulView2);
        }
      }
    }
    Iterator localIterator = this._groupClsMap.values().iterator();
    label274: label281: 
    while (localIterator.hasNext())
    {
      GroupInfo localGroupInfo = (GroupInfo)localIterator.next();
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = localGroupInfo.groupClass;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      this._area.eachChild(_collectCheckedChildren);
      XulArrayList localXulArrayList1 = _collectCheckedChildren.end();
      int i = 0;
      label201: XulView localXulView1;
      if (i < localXulArrayList1.size())
      {
        localXulView1 = (XulView)localXulArrayList1.get(i);
        if (!localXulView1.hasClass(localGroupInfo.checkClass))
        {
          if (!localXulView1.addClass(localGroupInfo.checkClass))
            break label274;
          notifyCheckedEvent(localXulView1);
          localXulView1.resetRender();
        }
      }
      while (true)
      {
        if (localGroupInfo.mode == GroupMode.GM_RADIO)
          break label281;
        i++;
        break label201;
        break;
        notifyCheckedEvent(localXulView1);
      }
    }
    _collectCheckedChildren.clear();
  }

  public void setAllUnchecked()
  {
    if (this._groupClsMap == null)
    {
      _collectCheckedChildren.begin(this._defaultCheckStateClass);
      this._area.eachChild(_collectCheckedChildren);
      XulArrayList localXulArrayList2 = _collectCheckedChildren.end();
      int j = 0;
      if (j < localXulArrayList2.size())
      {
        XulView localXulView2 = (XulView)localXulArrayList2.get(j);
        if (localXulView2.hasClass(this._defaultCheckStateClass))
        {
          if (!localXulView2.removeClass(this._defaultCheckStateClass))
            break label101;
          notifyUncheckedEvent(localXulView2);
          localXulView2.resetRender();
        }
        while (true)
        {
          j++;
          break;
          label101: notifyUncheckedEvent(localXulView2);
        }
      }
      _collectCheckedChildren.clear();
      return;
    }
    Iterator localIterator = this._groupClsMap.values().iterator();
    if (localIterator.hasNext())
    {
      GroupInfo localGroupInfo = (GroupInfo)localIterator.next();
      XulAreaChildrenCollectorByClass localXulAreaChildrenCollectorByClass = _collectCheckedChildren;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = localGroupInfo.groupClass;
      arrayOfString[1] = localGroupInfo.checkClass;
      localXulAreaChildrenCollectorByClass.begin(false, arrayOfString);
      this._area.eachChild(_collectCheckedChildren);
      XulArrayList localXulArrayList1 = _collectCheckedChildren.end();
      int i = 0;
      label203: XulView localXulView1;
      if (i < localXulArrayList1.size())
      {
        localXulView1 = (XulView)localXulArrayList1.get(i);
        if (localXulView1.hasClass(localGroupInfo.checkClass))
        {
          if (!localXulView1.removeClass(localGroupInfo.checkClass))
            break label266;
          notifyUncheckedEvent(localXulView1);
          localXulView1.resetRender();
        }
      }
      while (true)
      {
        i++;
        break label203;
        break;
        label266: notifyUncheckedEvent(localXulView1);
      }
    }
    _collectCheckedChildren.clear();
  }

  public void setChecked(XulView paramXulView)
  {
    GroupInfo localGroupInfo = getGroupInfoByView(paramXulView);
    if (localGroupInfo == null);
    while (paramXulView.hasClass(localGroupInfo.checkClass))
      return;
    switch (4.$SwitchMap$com$starcor$xul$Render$XulGroupRender$GroupMode[localGroupInfo.mode.ordinal()])
    {
    default:
      return;
    case 1:
      checkClick(localGroupInfo, paramXulView);
      return;
    case 2:
    }
    radioClick(localGroupInfo, paramXulView);
  }

  public void setUnchecked(XulView paramXulView)
  {
    GroupInfo localGroupInfo = getGroupInfoByView(paramXulView);
    if (localGroupInfo == null);
    while (!paramXulView.hasClass(localGroupInfo.checkClass))
      return;
    switch (4.$SwitchMap$com$starcor$xul$Render$XulGroupRender$GroupMode[localGroupInfo.mode.ordinal()])
    {
    default:
      return;
    case 1:
      checkClick(localGroupInfo, paramXulView);
      return;
    case 2:
    }
    radioClick(localGroupInfo, paramXulView);
  }

  public void syncData()
  {
    if (!_isDataChanged());
    label170: 
    while (true)
    {
      return;
      super.syncData();
      String str1 = this._area.getAttrString("checked-class");
      if (TextUtils.isEmpty(str1));
      for (this._defaultCheckStateClass = "group-checked"; ; this._defaultCheckStateClass = str1.trim())
        while (true)
        {
          if (this._groupClsMap != null)
            break label170;
          XulAttr localXulAttr = this._area.getAttr("group");
          if (localXulAttr == null)
            break;
          try
          {
            Iterator localIterator = ((ArrayList)localXulAttr.getValue()).iterator();
            while (localIterator.hasNext())
            {
              XulDataNode localXulDataNode = (XulDataNode)localIterator.next();
              if ("group".equals(localXulDataNode.getName()))
              {
                String str2 = localXulDataNode.getValue();
                if (!TextUtils.isEmpty(str2))
                {
                  String[] arrayOfString = str2.split(",");
                  if (arrayOfString.length == 3)
                    addGroup(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
                }
              }
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            return;
          }
        }
    }
  }

  class GroupInfo
  {
    String checkClass;
    String groupClass;
    XulGroupRender.GroupMode mode;

    GroupInfo()
    {
    }
  }

  static enum GroupMode
  {
    static
    {
      GM_CHECK = new GroupMode("GM_CHECK", 1);
      GroupMode[] arrayOfGroupMode = new GroupMode[2];
      arrayOfGroupMode[0] = GM_RADIO;
      arrayOfGroupMode[1] = GM_CHECK;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulGroupRender
 * JD-Core Version:    0.6.2
 */