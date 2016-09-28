package com.starcor.xul;

import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Utils.XulCircleQueue;
import java.lang.ref.WeakReference;
import java.util.Queue;

public class XulSuspendTaskCollector
{
  private boolean _finished = false;
  private int _recycleLevel = 0;
  private WeakReference<XulView> _root;
  private Queue<WeakReference<XulView>> _stack = new XulCircleQueue();
  private WeakReference<XulView> _targetView;

  public void doSuspendWork()
  {
    label53: if (this._finished)
    {
      return;
      break label53;
      break label53;
      break label53;
      break label53;
    }
    while ((XulView)this._root.get() == null);
    long l = XulUtils.timestamp_us();
    if (this._stack.isEmpty())
      this._stack.add(this._root);
    label120: XulView localXulView1;
    do
    {
      int i = 0;
      i++;
      break label120;
      if ((i % 32 == 0) && (XulUtils.timestamp_us() - l >= 400L))
        break;
      if (this._targetView != null)
      {
        localWeakReference = this._targetView;
        this._targetView = null;
        XulView localXulView2 = (XulView)localWeakReference.get();
        if (localXulView2 == null)
          break;
        localXulViewRender = localXulView2._render;
        if (localXulViewRender == null)
          break;
        if (!localXulViewRender.doSuspendRecycle(this._recycleLevel))
          break;
        this._targetView = localWeakReference;
        return;
      }
      WeakReference localWeakReference = (WeakReference)this._stack.poll();
      if (localWeakReference == null)
      {
        this._finished = true;
        return;
      }
      localXulView1 = (XulView)localWeakReference.get();
      if (localXulView1 == null)
        break;
      XulViewRender localXulViewRender = localXulView1._render;
      if (localXulViewRender == null)
        break;
    }
    while (!(localXulView1 instanceof XulArea));
    XulArea.XulElementArray localXulElementArray = ((XulArea)localXulView1)._children;
    int j = localXulElementArray.size();
    XulElement[] arrayOfXulElement = (XulElement[])localXulElementArray.getArray();
    int k = 0;
    label229: XulElement localXulElement;
    if (k < j)
    {
      localXulElement = arrayOfXulElement[k];
      if (!(localXulElement instanceof XulArea))
        break label275;
      this._stack.add(((XulView)localXulElement).getWeakReference());
    }
    while (true)
    {
      k++;
      break label229;
      break;
      label275: if ((localXulElement instanceof XulView))
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

  public void reset()
  {
    this._finished = false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulSuspendTaskCollector
 * JD-Core Version:    0.6.2
 */