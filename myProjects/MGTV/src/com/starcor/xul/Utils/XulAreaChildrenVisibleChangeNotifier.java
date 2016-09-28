package com.starcor.xul.Utils;

import android.util.Pair;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulView;
import java.util.ArrayList;

public class XulAreaChildrenVisibleChangeNotifier extends XulArea.XulAreaIterator
{
  private static XulAreaChildrenVisibleChangeNotifier g_notifier;
  XulArea _eventSource = null;
  boolean _isVisible = false;
  ArrayList<Pair<XulArea, Boolean>> notifyStack = new ArrayList();

  public static XulAreaChildrenVisibleChangeNotifier getNotifier()
  {
    if (g_notifier == null)
      g_notifier = new XulAreaChildrenVisibleChangeNotifier();
    return g_notifier;
  }

  private void notifyItem(XulView paramXulView)
  {
    if (!paramXulView.isVisible());
    XulViewRender localXulViewRender;
    do
    {
      return;
      localXulViewRender = paramXulView.getRender();
    }
    while (localXulViewRender == null);
    localXulViewRender.onVisibilityChanged(this._isVisible, this._eventSource);
  }

  public void begin(boolean paramBoolean, XulArea paramXulArea)
  {
    if (this._eventSource != null)
      this.notifyStack.add(Pair.create(this._eventSource, Boolean.valueOf(this._isVisible)));
    this._isVisible = paramBoolean;
    this._eventSource = paramXulArea;
  }

  public void end()
  {
    if (this.notifyStack.isEmpty())
    {
      this._eventSource = null;
      return;
    }
    Pair localPair = (Pair)this.notifyStack.remove(-1 + this.notifyStack.size());
    this._isVisible = ((Boolean)localPair.second).booleanValue();
    this._eventSource = ((XulArea)localPair.first);
  }

  public boolean onXulArea(int paramInt, XulArea paramXulArea)
  {
    notifyItem(paramXulArea);
    return true;
  }

  public boolean onXulItem(int paramInt, XulItem paramXulItem)
  {
    notifyItem(paramXulItem);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulAreaChildrenVisibleChangeNotifier
 * JD-Core Version:    0.6.2
 */