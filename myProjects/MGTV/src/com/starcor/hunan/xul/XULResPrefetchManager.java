package com.starcor.hunan.xul;

import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Utils.XulSimpleArray;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DownloadItem;
import com.starcor.xul.XulWorker.DrawableItem;
import com.starcor.xul.XulWorker.IXulWorkItemSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class XULResPrefetchManager
  implements XulWorker.IXulWorkItemSource
{
  static final int _DEFAULT_DRAWABLE_OBJECT_LIFE_MS = 1000;
  static final int _DOWNLOAD_ITEM_LIMIT = 16;
  static final int _DRAWABLE_ITEM_LIMIT = 16;
  static final int _MAX_CONCURRENCY = 2;
  static XULResPrefetchManager _inst;
  _XulDownloadItemList _downloadItems = new _XulDownloadItemList(16);
  _XulDrawableItemList _drawableItems = new _XulDrawableItemList(16);
  ArrayList<XulDrawable> _persistDrawableItems = new ArrayList();
  AtomicInteger _runningItems = new AtomicInteger(0);

  private XulWorker.DownloadItem _getDownloadItem()
  {
    while (true)
    {
      try
      {
        boolean bool = this._downloadItems.isEmpty();
        localDownloadItem = null;
        if (bool)
          return localDownloadItem;
        if (this._runningItems.incrementAndGet() > 2)
        {
          this._runningItems.decrementAndGet();
          localDownloadItem = null;
          continue;
        }
      }
      finally
      {
      }
      XulWorker.DownloadItem localDownloadItem = (XulWorker.DownloadItem)this._downloadItems.pop();
    }
  }

  private XulWorker.DrawableItem _getDrawableItem()
  {
    while (true)
    {
      try
      {
        boolean bool = this._drawableItems.isEmpty();
        localDrawableItem = null;
        if (bool)
          return localDrawableItem;
        if (this._runningItems.incrementAndGet() > 2)
        {
          this._runningItems.decrementAndGet();
          localDrawableItem = null;
          continue;
        }
      }
      finally
      {
      }
      XulWorker.DrawableItem localDrawableItem = (XulWorker.DrawableItem)this._drawableItems.pop();
    }
  }

  public static void init()
  {
    try
    {
      if (_inst == null)
      {
        _inst = new XULResPrefetchManager();
        XulWorker.registerDownloader(_inst);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void prefetch(String paramString)
  {
    try
    {
      if (_inst._downloadItems.size() >= 16)
        _inst._downloadItems.remove(0);
      XulWorker.DownloadItem localDownloadItem = new XulWorker.DownloadItem();
      localDownloadItem.url = paramString;
      _inst._downloadItems.add(localDownloadItem);
      return;
    }
    finally
    {
    }
  }

  public static void prefetchImage(String paramString)
  {
    prefetchImage(paramString, 0, 0);
  }

  public static void prefetchImage(String paramString, int paramInt1, int paramInt2)
  {
    prefetchImage(paramString, paramInt1, paramInt2, 0.0F, 0.0F);
  }

  public static void prefetchImage(String paramString, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    prefetchImage(paramString, paramInt1, paramInt2, 0.0F, 0.0F, 1000);
  }

  public static void prefetchImage(String paramString, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3)
  {
    while (true)
    {
      int j;
      try
      {
        _XulDrawableItemList local_XulDrawableItemList = _inst._drawableItems;
        int i = local_XulDrawableItemList.size();
        if (paramInt3 > 0)
        {
          j = 0;
          if ((j < i) && (i >= 16))
          {
            if (((_XulDrawableItem)local_XulDrawableItemList.get(j))._lifeMS <= 0)
              break label147;
            local_XulDrawableItemList.remove(j);
            i--;
            break label147;
          }
        }
        _XulDrawableItem local_XulDrawableItem = new _XulDrawableItem(null);
        local_XulDrawableItem.url = paramString;
        local_XulDrawableItem.width = paramInt1;
        local_XulDrawableItem.height = paramInt2;
        local_XulDrawableItem._lifeMS = paramInt3;
        if ((paramFloat1 > 0.1F) && (paramFloat2 > 0.1F))
          local_XulDrawableItem.setRoundRect(paramFloat1, paramFloat2);
        local_XulDrawableItemList.add(local_XulDrawableItem);
        return;
      }
      finally
      {
      }
      label147: j++;
    }
  }

  public static void prefetchImage(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    prefetchImage(paramString, paramInt1, paramInt2, 0.0F, 0.0F, paramInt3);
  }

  public InputStream getAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    return null;
  }

  public InputStream getAssets(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    return null;
  }

  public XulWorker.DownloadItem getDownloadItem()
  {
    return _getDownloadItem();
  }

  public XulWorker.DrawableItem getDrawableItem()
  {
    return _getDrawableItem();
  }

  public void onDownload(XulWorker.DownloadItem paramDownloadItem, InputStream paramInputStream)
  {
    this._runningItems.decrementAndGet();
  }

  public void onDrawableLoaded(XulWorker.DrawableItem paramDrawableItem, XulDrawable paramXulDrawable)
  {
    this._runningItems.decrementAndGet();
    int i = ((_XulDrawableItem)paramDrawableItem)._lifeMS;
    if (i > 0)
      XulWorker.addDrawableToCache(paramDrawableItem.url, paramXulDrawable, i);
    while (i >= 0)
      return;
    synchronized (this._persistDrawableItems)
    {
      this._persistDrawableItems.add(paramXulDrawable);
      return;
    }
  }

  public String resolve(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    return null;
  }

  private static class _XulDownloadItemList extends XulSimpleArray<XulWorker.DownloadItem>
  {
    public _XulDownloadItemList(int paramInt)
    {
      super();
    }

    protected XulWorker.DownloadItem[] allocArrayBuf(int paramInt)
    {
      return new XulWorker.DownloadItem[paramInt];
    }
  }

  private static class _XulDrawableItem extends XulWorker.DrawableItem
  {
    int _lifeMS;
  }

  private static class _XulDrawableItemList extends XulSimpleArray<XULResPrefetchManager._XulDrawableItem>
  {
    public _XulDrawableItemList(int paramInt)
    {
      super();
    }

    protected XULResPrefetchManager._XulDrawableItem[] allocArrayBuf(int paramInt)
    {
      return new XULResPrefetchManager._XulDrawableItem[paramInt];
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xul.XULResPrefetchManager
 * JD-Core Version:    0.6.2
 */