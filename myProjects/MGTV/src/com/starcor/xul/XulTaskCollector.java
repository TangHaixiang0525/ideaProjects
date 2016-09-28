package com.starcor.xul;

import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Utils.XulCircleQueue;
import java.lang.ref.WeakReference;
import java.util.Queue;

public class XulTaskCollector
{
  private boolean _finished = false;
  private WeakReference<XulView> _root;
  private Queue<WeakReference<XulView>> _stack = new XulCircleQueue();
  private WeakReference<XulView> _targetView;

  public void addPendingItem(XulView paramXulView)
  {
    this._stack.add(paramXulView.getWeakReference());
  }

  XulWorker.DrawableItem collectPendingDrawableItem()
  {
    if ((XulView)this._root.get() == null)
      return null;
    long l = XulUtils.timestamp_us();
    if (this._stack.isEmpty())
    {
      this._stack.add(this._root);
      this._finished = false;
    }
    int i = 0;
    label74: WeakReference localWeakReference;
    XulViewRender localXulViewRender;
    do
    {
      XulView localXulView2;
      do
      {
        i++;
        if ((i % 32 == 0) && (XulUtils.timestamp_us() - l >= 400L))
          return null;
        if (this._targetView == null)
          break;
        localWeakReference = this._targetView;
        this._targetView = null;
        localXulView2 = (XulView)localWeakReference.get();
      }
      while (localXulView2 == null);
      localXulViewRender = localXulView2._render;
    }
    while ((localXulViewRender == null) || (localXulViewRender.rejectTest()));
    XulView localXulView1;
    do
    {
      XulWorker.DrawableItem localDrawableItem = localXulViewRender.getPendingImageItem();
      if (localDrawableItem == null)
        break;
      this._targetView = localWeakReference;
      return localDrawableItem;
      localWeakReference = (WeakReference)this._stack.poll();
      if (localWeakReference == null)
      {
        this._finished = true;
        break label74;
      }
      localXulView1 = (XulView)localWeakReference.get();
      if (localXulView1 == null)
        break;
      localXulViewRender = localXulView1._render;
      if ((localXulViewRender == null) || (localXulViewRender.rejectTest()))
        break;
    }
    while ((localXulViewRender.collectPendingItems(this)) || (!(localXulView1 instanceof XulArea)));
    XulArea.XulElementArray localXulElementArray = ((XulArea)localXulView1)._children;
    int j = localXulElementArray.size();
    XulElement[] arrayOfXulElement = (XulElement[])localXulElementArray.getArray();
    int k = 0;
    label259: XulElement localXulElement;
    if (k < j)
    {
      localXulElement = arrayOfXulElement[k];
      if (!(localXulElement instanceof XulArea))
        break label305;
      this._stack.add(((XulView)localXulElement).getWeakReference());
    }
    while (true)
    {
      k++;
      break label259;
      break;
      label305: if ((localXulElement instanceof XulView))
        this._stack.add(((XulView)localXulElement).getWeakReference());
    }
  }

  public void init(XulView paramXulView)
  {
    WeakReference localWeakReference = paramXulView.getWeakReference();
    if (localWeakReference.equals(this._root))
      return;
    this._root = localWeakReference;
    this._stack.clear();
    this._targetView = null;
    this._finished = false;
  }

  boolean isFinished()
  {
    return this._finished;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulTaskCollector
 * JD-Core Version:    0.6.2
 */