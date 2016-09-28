package com.starcor.xul;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Prop.XulBinding;
import com.starcor.xul.Prop.XulFocus;
import com.starcor.xul.Prop.XulFocus._Builder;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.Utils.XulSimpleArray;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class XulRenderContext
{
  public static final int EVENT_PENDING_IMAGE_LOADED = 1;
  static final String TAG;
  private static Paint _defAlphaPicPaint;
  private static Paint _defPicPaint;
  private static Paint _defShadowTextPaint;
  private static Paint _defSolidPaint;
  private static Paint _defSolidShadowPaint;
  private static Paint _defStrokePaint;
  private static Paint _defTextPaint;
  private static Typeface _defTypeFace;
  private static XulCachedHashMap<String, TypefaceInfo> _font_map;
  private static PointF _motionEventsHitTestPt;
  AnimationList _animation = new AnimationList(64);
  long _animationTimestamp;
  RenderViewList _changedViews = new RenderViewList(512);
  XulCachedHashMap<XulView, XulView> _dirtyViews = new XulCachedHashMap(64);
  XulWorker.IXulWorkItemSource _downloader = new XulWorker.IXulWorkItemSource()
  {
    public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
    {
      if (XulRenderContext.this._handler != null)
        return XulRenderContext.this._handler.getAppData(paramAnonymousDownloadItem, paramAnonymousString);
      return null;
    }

    public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
    {
      if (XulRenderContext.this._handler != null)
        return XulRenderContext.this._handler.getAssets(paramAnonymousDownloadItem, paramAnonymousString);
      return null;
    }

    public XulWorker.DownloadItem getDownloadItem()
    {
      return XulRenderContext.this._getPendingItem();
    }

    public XulWorker.DrawableItem getDrawableItem()
    {
      return XulRenderContext.this._getPendingImageItem();
    }

    public void onDownload(XulWorker.DownloadItem paramAnonymousDownloadItem, InputStream paramAnonymousInputStream)
    {
      XulRenderContext.this._onPendingItemReady(paramAnonymousDownloadItem, paramAnonymousInputStream);
    }

    public void onDrawableLoaded(XulWorker.DrawableItem paramAnonymousDrawableItem, XulDrawable paramAnonymousXulDrawable)
    {
      XulRenderContext.this._onPendingImageItemReady(paramAnonymousDrawableItem, paramAnonymousXulDrawable);
    }

    public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
    {
      if (XulRenderContext.this._handler != null)
        return XulRenderContext.this._handler.resolve(paramAnonymousDownloadItem, paramAnonymousString);
      return null;
    }
  };
  private ArrayList<WeakReference<XulView>> _findViewCache = new ArrayList();
  ArrayList<XulSelect> _globalSelectors;
  private IXulRenderHandler _handler;
  private boolean _initialized = false;
  XulPage _page;
  volatile int _pendingImageCollectingRound = 0;
  int _pendingImageFinishingRound = 0;
  volatile int _pendingImageItemNum = 0;
  private boolean _readyToDraw = false;
  int _scheduledLayoutFinishTasksProtectRange = 0;
  XulSimpleArray<Runnable> _scheduledLayoutFinishedTasks = new XulSimpleArray()
  {
    protected Runnable[] allocArrayBuf(int paramAnonymousInt)
    {
      return new Runnable[paramAnonymousInt];
    }
  };
  XulSuspendTaskCollector _suspendTaskCollector;
  boolean _suspended = false;
  XulTaskCollector _taskCollector;
  XulUtils.ticketMarker _tsMarker;
  private XulDC _xulDC = new XulDC(this);
  XulPage.IActionCallback actionCallback = new XulPage.IActionCallback()
  {
    public void doAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
    {
      if (XulRenderContext.this._handler == null)
        return;
      try
      {
        XulRenderContext.this._handler.onDoAction(paramAnonymousXulView, paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousObject);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  };
  boolean lastDownHandled;
  int lastKeyAction;
  int lastKeyCode;
  WeakReference<XulView> lastKeyEventView;
  WeakReference<XulView> lastMotionDownEventView;

  static
  {
    if (!XulRenderContext.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      TAG = XulRenderContext.class.getSimpleName();
      _font_map = new XulCachedHashMap();
      _motionEventsHitTestPt = new PointF();
      return;
    }
  }

  public XulRenderContext(XulPage paramXulPage, ArrayList<XulSelect> paramArrayList, ArrayList<XulBinding> paramArrayList1, IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2)
  {
    this(paramXulPage, paramArrayList, paramArrayList1, paramIXulRenderHandler, paramInt1, paramInt2, false, false);
  }

  public XulRenderContext(XulPage paramXulPage, ArrayList<XulSelect> paramArrayList, ArrayList<XulBinding> paramArrayList1, IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramXulPage, paramArrayList, paramArrayList1, paramIXulRenderHandler, paramInt1, paramInt2, paramBoolean1, paramBoolean2, false);
  }

  public XulRenderContext(XulPage paramXulPage, ArrayList<XulSelect> paramArrayList, ArrayList<XulBinding> paramArrayList1, IXulRenderHandler paramIXulRenderHandler, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this._globalSelectors = paramArrayList;
    this._page = paramXulPage.makeClone(this, paramInt1, paramInt2);
    this._handler = paramIXulRenderHandler;
    if (!paramBoolean2)
      this._page.setGlobalBindings(paramArrayList1);
    this._suspended = paramBoolean1;
    if (paramBoolean3)
      return;
    initXulRender();
  }

  private boolean _applyBinding()
  {
    assert (this._page != null);
    if (!this._page.applyBinding(this.actionCallback))
      return false;
    if (this._page == null)
    {
      Log.w(TAG, "Page has been destroyed during binding events!");
      return true;
    }
    return _onBindingFinished();
  }

  private XulWorker.DrawableItem _getPendingImageItem()
  {
    try
    {
      boolean bool = this._suspended;
      XulWorker.DrawableItem localDrawableItem = null;
      if (bool);
      while (true)
      {
        return localDrawableItem;
        XulPage localXulPage = this._page;
        localDrawableItem = null;
        if (localXulPage != null)
        {
          XulLayout localXulLayout = this._page.getLayout();
          localDrawableItem = null;
          if (localXulLayout != null)
          {
            if (this._taskCollector == null)
            {
              this._taskCollector = new XulTaskCollector();
              this._taskCollector.init(this._page.getLayout());
            }
            localDrawableItem = this._taskCollector.collectPendingDrawableItem();
            if (this._taskCollector.isFinished())
              this._pendingImageCollectingRound = (1 + this._pendingImageCollectingRound);
            if (localDrawableItem != null)
              this._pendingImageItemNum = (1 + this._pendingImageItemNum);
          }
        }
      }
    }
    finally
    {
    }
  }

  private XulWorker.DownloadItem _getPendingItem()
  {
    Object localObject1 = null;
    try
    {
      if (this._suspended)
      {
        if (this._suspendTaskCollector == null)
          this._suspendTaskCollector = new XulSuspendTaskCollector();
        this._suspendTaskCollector.init(this._page.getLayout());
        this._suspendTaskCollector.doSuspendWork();
      }
      while (true)
      {
        return localObject1;
        XulPage localXulPage = this._page;
        localObject1 = null;
        if (localXulPage != null)
        {
          XulWorker.DownloadItem localDownloadItem = this._page.getPendingItem();
          localObject1 = localDownloadItem;
        }
      }
    }
    finally
    {
    }
  }

  private boolean _onBindingFinished()
  {
    XulPage localXulPage = this._page;
    localXulPage.applySelectors();
    localXulPage.prepareRender(this);
    localXulPage.getLayout().initFocus();
    doLayout();
    syncData();
    if (!this._readyToDraw)
      uiRun(new Runnable()
      {
        public void run()
        {
          XulPage localXulPage = XulRenderContext.this._page;
          if (localXulPage == null)
          {
            Log.e(XulRenderContext.TAG, "page already destroyed before ready!");
            return;
          }
          XulPage.invokeActionNoPopup(localXulPage, "ready", XulRenderContext.this.actionCallback);
          if (XulRenderContext.this._handler != null)
            XulRenderContext.this._handler.onRenderIsReady();
          XulRenderContext.access$402(XulRenderContext.this, true);
        }
      });
    invalidate(null);
    return true;
  }

  private void _onPendingImageItemReady(final XulWorker.DrawableItem paramDrawableItem, final XulDrawable paramXulDrawable)
  {
    uiRun(new Runnable()
    {
      public void run()
      {
        XulRenderContext localXulRenderContext1 = XulRenderContext.this;
        int i = -1 + localXulRenderContext1._pendingImageItemNum;
        localXulRenderContext1._pendingImageItemNum = i;
        if ((i == 0) && (XulRenderContext.this._pendingImageCollectingRound > 1))
        {
          XulRenderContext localXulRenderContext2 = XulRenderContext.this;
          localXulRenderContext2._pendingImageFinishingRound = (1 + localXulRenderContext2._pendingImageFinishingRound);
          XulRenderContext.IXulRenderHandler localIXulRenderHandler = XulRenderContext.this._handler;
          if (localIXulRenderHandler != null)
            localIXulRenderHandler.onRenderEvent(1, XulRenderContext.this._pendingImageFinishingRound, 0, null);
        }
        if ((paramDrawableItem instanceof XulRenderDrawableItem))
          ((XulRenderDrawableItem)paramDrawableItem).onImageReady(paramXulDrawable);
      }
    });
  }

  private void _onPendingItemReady(final XulWorker.DownloadItem paramDownloadItem, final InputStream paramInputStream)
  {
    if (this._page == null)
      return;
    uiRun(new Runnable()
    {
      public void run()
      {
        XulPage localXulPage = XulRenderContext.this._page;
        if (localXulPage == null);
        boolean bool;
        do
        {
          return;
          bool = localXulPage.isBindingFinished();
        }
        while (!localXulPage.setPendingItemData(paramDownloadItem, paramInputStream, XulRenderContext.this.actionCallback));
        if (localXulPage != XulRenderContext.this._page)
        {
          Log.w(XulRenderContext.TAG, "Page has been destroyed during binding events! " + localXulPage.toString());
          return;
        }
        if (bool)
        {
          XulRenderContext.this.doLayout();
          XulRenderContext.this.syncData();
          XulRenderContext.this.invalidate(null);
          return;
        }
        XulRenderContext.this._onBindingFinished();
      }
    });
  }

  public static void addTypeFace(String paramString, Typeface paramTypeface)
  {
    TypefaceInfo localTypefaceInfo = new TypefaceInfo(null);
    localTypefaceInfo.name = paramString;
    localTypefaceInfo.typeface = paramTypeface;
    _font_map.put(paramString, localTypefaceInfo);
  }

  public static Paint getDefAlphaPicPaint()
  {
    if (_defAlphaPicPaint == null)
    {
      _defAlphaPicPaint = new Paint();
      _defAlphaPicPaint.setFlags(3);
      _defAlphaPicPaint.setColor(-16777216);
      _defAlphaPicPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    return _defAlphaPicPaint;
  }

  public static Paint getDefPicPaint()
  {
    if (_defPicPaint == null)
    {
      _defPicPaint = new Paint();
      _defPicPaint.setFlags(3);
      _defPicPaint.setColor(-16777216);
      _defPicPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    return _defPicPaint;
  }

  public static Paint getDefShadowTextPaint()
  {
    if (_defShadowTextPaint == null)
    {
      _defShadowTextPaint = new Paint();
      _defShadowTextPaint.setAntiAlias(true);
      _defShadowTextPaint.setSubpixelText(true);
      Typeface localTypeface = getDefTypeFace();
      if (localTypeface != null)
        _defShadowTextPaint.setTypeface(localTypeface);
      _defShadowTextPaint.setShadowLayer(3.0F, 0.0F, 0.0F, -16777216);
    }
    return _defShadowTextPaint;
  }

  public static Paint getDefSolidPaint()
  {
    if (_defSolidPaint == null)
    {
      _defSolidPaint = new Paint();
      _defSolidPaint.setAntiAlias(true);
      _defSolidPaint.setColor(-1);
      _defSolidPaint.setStrokeWidth(1.0F);
      _defSolidPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    return _defSolidPaint;
  }

  public static Paint getDefSolidShadowPaint()
  {
    if (_defSolidShadowPaint == null)
    {
      _defSolidShadowPaint = new Paint();
      _defSolidShadowPaint.setAntiAlias(true);
      _defSolidShadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    return _defSolidShadowPaint;
  }

  public static Paint getDefStrokePaint()
  {
    if (_defStrokePaint == null)
    {
      _defStrokePaint = new Paint();
      _defStrokePaint.setColor(-1);
      _defStrokePaint.setStrokeWidth(2.0F);
      _defStrokePaint.setAntiAlias(true);
      _defStrokePaint.setStyle(Paint.Style.STROKE);
    }
    return _defStrokePaint;
  }

  public static Paint getDefTextPaint()
  {
    if (_defTextPaint == null)
    {
      _defTextPaint = new Paint();
      Typeface localTypeface = getDefTypeFace();
      if (localTypeface != null)
        _defTextPaint.setTypeface(localTypeface);
      _defTextPaint.setAntiAlias(true);
      _defTextPaint.setSubpixelText(true);
    }
    return _defTextPaint;
  }

  public static Typeface getDefTypeFace()
  {
    return _defTypeFace;
  }

  public static Paint getShadowTextPaintByName(String paramString)
  {
    Paint localPaint1;
    if (paramString == null)
      localPaint1 = getDefShadowTextPaint();
    TypefaceInfo localTypefaceInfo;
    do
    {
      return localPaint1;
      localTypefaceInfo = (TypefaceInfo)_font_map.get(paramString);
      if (localTypefaceInfo == null)
        return getDefShadowTextPaint();
      localPaint1 = localTypefaceInfo.paint;
    }
    while (localPaint1 != null);
    Paint localPaint2 = new Paint();
    localTypefaceInfo.paint = localPaint2;
    localPaint2.setTypeface(localTypefaceInfo.typeface);
    localPaint2.setAntiAlias(true);
    localPaint2.setSubpixelText(true);
    localPaint2.setShadowLayer(3.0F, 0.0F, 0.0F, -16777216);
    return localPaint2;
  }

  public static Paint getTextPaintByName(String paramString)
  {
    Paint localPaint1;
    if (paramString == null)
      localPaint1 = getDefTextPaint();
    TypefaceInfo localTypefaceInfo;
    do
    {
      return localPaint1;
      localTypefaceInfo = (TypefaceInfo)_font_map.get(paramString);
      if (localTypefaceInfo == null)
        return getDefTextPaint();
      localPaint1 = localTypefaceInfo.paint;
    }
    while (localPaint1 != null);
    Paint localPaint2 = new Paint();
    localTypefaceInfo.paint = localPaint2;
    localPaint2.setTypeface(localTypefaceInfo.typeface);
    localPaint2.setAntiAlias(true);
    localPaint2.setSubpixelText(true);
    return localPaint2;
  }

  private WeakReference<XulView> getWeakReference(XulView paramXulView)
  {
    if (paramXulView == null)
      return null;
    return paramXulView.getWeakReference();
  }

  public static void setDefTypeFace(Typeface paramTypeface)
  {
    _defTypeFace = paramTypeface;
  }

  public static void suspendDrawableWorker()
  {
    XulWorker.suspendDrawableWorker(80);
  }

  private void syncData()
  {
    for (int i = 0; (i < 3) && (!this._changedViews.isEmpty()); i++)
    {
      int j = this._changedViews.size();
      for (int k = 0; k < j; k++)
        ((XulViewRender)this._changedViews.get(k)).doSyncData();
      this._changedViews.remove(0, j);
    }
  }

  public void addAnimation(IXulAnimation paramIXulAnimation)
  {
    if (this._animation.contains(paramIXulAnimation))
      return;
    this._animation.add(paramIXulAnimation);
  }

  public long animationTimestamp()
  {
    return this._animationTimestamp;
  }

  public boolean beginDraw(Canvas paramCanvas, Rect paramRect)
  {
    if (!initDraw())
      return false;
    return beginDrawEx(paramCanvas, paramRect);
  }

  public boolean beginDrawEx(Canvas paramCanvas, Rect paramRect)
  {
    if (this._tsMarker != null)
      this._tsMarker.mark("");
    this._xulDC.beginDraw(paramCanvas);
    if (this._tsMarker != null)
      this._tsMarker.mark("begin");
    this._page.draw(this._xulDC, paramRect, 0, 0);
    if (this._tsMarker != null)
      this._tsMarker.mark("page");
    return true;
  }

  public IXulExternalView createExternalView(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, XulView paramXulView)
  {
    if (this._handler == null)
      return null;
    return this._handler.createExternalView(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramXulView);
  }

  public void destroy()
  {
    this._readyToDraw = false;
    XulWorker.unregisterDownloader(this._downloader);
    if (this._page != null)
    {
      this._page.destroy();
      this._page = null;
    }
    this._handler = null;
  }

  public void doLayout()
  {
    XulPage localXulPage = this._page;
    if (localXulPage == null);
    int i;
    int j;
    do
    {
      XulLayout localXulLayout;
      do
      {
        return;
        localXulLayout = localXulPage.getLayout();
      }
      while ((localXulLayout == null) || (localXulLayout.getRender() == null));
      localXulPage.doLayout(0, 0);
      i = this._scheduledLayoutFinishTasksProtectRange;
      j = this._scheduledLayoutFinishedTasks.size();
    }
    while (j <= 0);
    this._scheduledLayoutFinishTasksProtectRange = j;
    int k = i;
    while (true)
      if (k < j)
      {
        Runnable localRunnable = (Runnable)this._scheduledLayoutFinishedTasks.get(k);
        try
        {
          localRunnable.run();
          k++;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    this._scheduledLayoutFinishedTasks.remove(i, j);
    this._scheduledLayoutFinishTasksProtectRange = i;
  }

  public void endDraw()
  {
    if (this._tsMarker != null)
      this._tsMarker.mark("delay");
    this._xulDC.endDraw();
    if (this._tsMarker != null)
      this._tsMarker.mark("end");
    if (this._tsMarker != null)
      Log.d(TAG, this._tsMarker.toString());
  }

  public XulView findCustomItemByExtView(IXulExternalView paramIXulExternalView)
  {
    if (this._page == null)
      return null;
    return this._page.findCustomItemByExtView(paramIXulExternalView);
  }

  public XulView findItemById(String paramString)
  {
    if (this._page == null)
      return null;
    return this._page.findItemById(paramString);
  }

  public XulArrayList<XulView> findItemsByClass(String[] paramArrayOfString)
  {
    if (this._page == null)
      return null;
    return this._page.findItemsByClass(paramArrayOfString);
  }

  ArrayList<WeakReference<XulView>> findViewsByPoint(final int paramInt, final PointF paramPointF)
  {
    this._findViewCache.clear();
    this._page.getLayout().eachChild(new XulArea.XulAreaIterator()
    {
      public boolean onXulArea(int paramAnonymousInt, XulArea paramAnonymousXulArea)
      {
        if (paramAnonymousXulArea.hitTest(paramInt, paramPointF.x, paramPointF.y))
        {
          float f1 = paramPointF.x;
          float f2 = paramPointF.y;
          paramAnonymousXulArea.hitTestTranslate(paramPointF);
          XulRenderContext.this._findViewCache.add(paramAnonymousXulArea.getWeakReference());
          paramAnonymousXulArea.eachChild(this);
          paramPointF.set(f1, f2);
        }
        return super.onXulArea(paramAnonymousInt, paramAnonymousXulArea);
      }

      public boolean onXulItem(int paramAnonymousInt, XulItem paramAnonymousXulItem)
      {
        if (paramAnonymousXulItem.hitTest(paramInt, paramPointF.x, paramPointF.y))
          XulRenderContext.this._findViewCache.add(paramAnonymousXulItem.getWeakReference());
        return super.onXulItem(paramAnonymousInt, paramAnonymousXulItem);
      }
    });
    return this._findViewCache;
  }

  public XulPage.IActionCallback getDefaultActionCallback()
  {
    return this.actionCallback;
  }

  public XulLayout getLayout()
  {
    return this._page.getLayout();
  }

  public XulPage getPage()
  {
    return this._page;
  }

  public int getPageHeight()
  {
    return this._page.getPageHeight();
  }

  public int getPageWidth()
  {
    return this._page.getPageWidth();
  }

  public float getXScalar()
  {
    return this._page.getXScalar();
  }

  public float getYScalar()
  {
    return this._page.getYScalar();
  }

  public boolean initDraw()
  {
    if (this._tsMarker != null)
      this._tsMarker.reset();
    if (this._page == null)
      return false;
    if (this._tsMarker != null)
      this._tsMarker.mark();
    if (!this._dirtyViews.isEmpty());
    doLayout();
    if (this._tsMarker != null)
      this._tsMarker.mark("layout");
    syncData();
    if (this._tsMarker != null)
      this._tsMarker.mark("syncData");
    XulLayout localXulLayout = this._page.getLayout();
    if (localXulLayout != null)
    {
      XulViewRender localXulViewRender = localXulLayout.getRender();
      if ((localXulViewRender != null) && (localXulViewRender.isLayoutChanged()))
        this._page.markDirtyView();
    }
    return true;
  }

  public void initXulRender()
  {
    if (this._initialized)
      return;
    this._initialized = true;
    this._page.preloadBinding(this._downloader);
    XulPage.invokeActionNoPopup(this._page, "load", this.actionCallback);
    if (!_applyBinding())
    {
      this._page.applySelectors();
      this._page.prepareRender(this);
      this._page.getLayout().initFocus();
      doLayout();
      syncData();
    }
    XulWorker.registerDownloader(this._downloader);
    uiRun(new Runnable()
    {
      Rect _updateRect = new Rect();

      private void updateAnimation()
      {
        long l = XulRenderContext.this.animationTimestamp();
        int i = XulRenderContext.this._animation.size();
        int j = 0;
        while (j < i)
          if (!((IXulAnimation)XulRenderContext.this._animation.get(j)).updateAnimation(l))
          {
            XulRenderContext.this._animation.remove(j);
            i--;
          }
          else
          {
            j++;
          }
      }

      private void updateDirtyRect()
      {
        this._updateRect.set(99999, 99999, 0, 0);
        Iterator localIterator = XulRenderContext.this._dirtyViews.values().iterator();
        while (localIterator.hasNext())
        {
          RectF localRectF = ((XulView)localIterator.next()).getRender().getUpdateRect();
          if (this._updateRect.left > localRectF.left)
            this._updateRect.left = XulUtils.roundToInt(localRectF.left);
          if (this._updateRect.top > localRectF.top)
            this._updateRect.top = XulUtils.roundToInt(localRectF.top);
          if (this._updateRect.right < localRectF.right)
            this._updateRect.right = XulUtils.roundToInt(localRectF.right);
          if (this._updateRect.bottom < localRectF.bottom)
            this._updateRect.bottom = XulUtils.roundToInt(localRectF.bottom);
        }
        XulRenderContext.this._dirtyViews.clear();
        XulRenderContext.this.invalidate(this._updateRect);
      }

      private void updateWholeView()
      {
        XulRenderContext.this._dirtyViews.clear();
        if (!XulRenderContext.this.initDraw())
          return;
        XulWorker.suspendDrawableWorker(16);
        XulRenderContext.this.invalidate(null);
      }

      public void run()
      {
        XulPage localXulPage = XulRenderContext.this._page;
        if (localXulPage == null)
          return;
        if (XulRenderContext.this._suspended)
        {
          XulRenderContext.this.uiRun(this, 50);
          return;
        }
        long l1 = XulUtils.timestamp();
        if (XulRenderContext.this._readyToDraw)
        {
          long l3 = 0xFFFFFFF8 & 4L + ()(1.0F * (float)l1);
          if (l3 != XulRenderContext.this._animationTimestamp)
          {
            XulRenderContext.this._animationTimestamp = l3;
            if (!XulRenderContext.this._animation.isEmpty())
              updateAnimation();
          }
          if (!XulRenderContext.this._dirtyViews.isEmpty())
            updateWholeView();
          XulLayout localXulLayout = localXulPage.getLayout();
          if ((localXulLayout != null) && (localXulLayout.getRender().isLayoutChanged()))
            updateWholeView();
        }
        long l2 = XulUtils.timestamp() - l1;
        if (l2 >= 16L)
        {
          XulRenderContext.this.uiRun(this);
          return;
        }
        XulRenderContext.this.uiRun(this, (int)((float)(12L - l2) / 1.0F));
      }
    }
    , 20);
  }

  public void invalidate(Rect paramRect)
  {
    if (this._handler == null)
      return;
    this._handler.invalidate(paramRect);
  }

  public boolean isDestroyed()
  {
    return this._page == null;
  }

  public boolean isReady()
  {
    return this._readyToDraw;
  }

  public boolean isSuspended()
  {
    return this._suspended;
  }

  public void markDataChanged(XulViewRender paramXulViewRender)
  {
    this._changedViews.add(paramXulViewRender);
  }

  public void markDirtyView(XulView paramXulView)
  {
    this._dirtyViews.put(paramXulView, paramXulView);
  }

  public XulCreator newXulCreator()
  {
    return new XulCreator();
  }

  public boolean onKeyEvent(KeyEvent paramKeyEvent)
  {
    XulLayout localXulLayout;
    boolean bool;
    if (this._page != null)
    {
      localXulLayout = this._page.getLayout();
      if (localXulLayout != null);
    }
    else
    {
      this.lastKeyAction = -1;
      this.lastKeyCode = -1;
      this.lastKeyEventView = null;
      this.lastDownHandled = false;
      bool = false;
      return bool;
    }
    int i = paramKeyEvent.getAction();
    int j = paramKeyEvent.getKeyCode();
    XulView localXulView1 = localXulLayout.getFocus();
    if (i == 0)
      if (localXulLayout.onKeyEvent(paramKeyEvent))
      {
        this.lastKeyAction = i;
        this.lastKeyCode = j;
        this.lastKeyEventView = getWeakReference(localXulView1);
        bool = true;
        this.lastDownHandled = bool;
      }
    do
    {
      this.lastKeyAction = i;
      this.lastKeyCode = j;
      this.lastKeyEventView = getWeakReference(localXulView1);
      if (!bool)
        break;
      suspendDrawableWorker();
      return bool;
      switch (j)
      {
      default:
        bool = false;
        break;
      case 4:
        bool = this._page.popStates();
        if (!bool)
          break;
        XulPage.invokeActionNoPopup(this._page, "statesRestored");
        break;
      case 21:
        bool = localXulLayout.moveFocus(XulLayout.FocusDirection.MOVE_LEFT);
        break;
      case 22:
        bool = localXulLayout.moveFocus(XulLayout.FocusDirection.MOVE_RIGHT);
        break;
      case 19:
        bool = localXulLayout.moveFocus(XulLayout.FocusDirection.MOVE_UP);
        break;
      case 20:
        bool = localXulLayout.moveFocus(XulLayout.FocusDirection.MOVE_DOWN);
        break;
      case 23:
      case 66:
        bool = localXulLayout.doClick(this.actionCallback);
        break;
        bool = false;
      }
    }
    while (i != 1);
    WeakReference localWeakReference = this.lastKeyEventView;
    XulView localXulView2 = null;
    if (localWeakReference == null)
    {
      label313: if (localXulView2 == localXulView1)
        break label348;
      bool = this.lastDownHandled;
    }
    while (true)
    {
      this.lastDownHandled = false;
      break;
      localXulView2 = (XulView)this.lastKeyEventView.get();
      break label313;
      label348: if (localXulLayout.onKeyEvent(paramKeyEvent))
      {
        this.lastKeyAction = i;
        this.lastKeyCode = j;
        this.lastKeyEventView = getWeakReference(localXulView1);
        bool = true;
      }
      else
      {
        bool = this.lastDownHandled;
      }
    }
  }

  public boolean onMotionEvent(MotionEvent paramMotionEvent)
  {
    XulLayout localXulLayout;
    if (this._page != null)
    {
      localXulLayout = this._page.getLayout();
      if (localXulLayout != null);
    }
    else
    {
      this.lastMotionDownEventView = null;
      return false;
    }
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    default:
    case 8:
    case 0:
    case 1:
    }
    while (true)
    {
      return false;
      _motionEventsHitTestPt.set(f1, f2);
      ArrayList localArrayList3 = findViewsByPoint(8, _motionEventsHitTestPt);
      if (localArrayList3.isEmpty())
        return false;
      float f3 = paramMotionEvent.getAxisValue(10);
      float f4 = paramMotionEvent.getAxisValue(9);
      for (int n = -1 + localArrayList3.size(); n >= 0; n--)
      {
        XulView localXulView3 = (XulView)((WeakReference)localArrayList3.get(n)).get();
        if ((localXulView3 != null) && (localXulView3.handleScrollEvent(f3, f4)))
        {
          localArrayList3.clear();
          localXulView3.resetRender();
          return true;
        }
      }
      this.lastMotionDownEventView = null;
      _motionEventsHitTestPt.set(f1, f2);
      ArrayList localArrayList2 = findViewsByPoint(0, _motionEventsHitTestPt);
      if (localArrayList2.isEmpty())
        return false;
      int k = 0;
      int m = localArrayList2.size();
      while (k < m)
      {
        XulView localXulView2 = (XulView)((WeakReference)localArrayList2.get(k)).get();
        if ((localXulView2 != null) && (localXulView2.focusable()))
        {
          this.lastMotionDownEventView = localXulView2.getWeakReference();
          localXulLayout.requestFocus(localXulView2);
          localArrayList2.clear();
          return true;
        }
        k++;
      }
      _motionEventsHitTestPt.set(f1, f2);
      ArrayList localArrayList1 = findViewsByPoint(1, _motionEventsHitTestPt);
      if ((localArrayList1.isEmpty()) || (this.lastMotionDownEventView == null))
      {
        this.lastMotionDownEventView = null;
        return false;
      }
      int i = 0;
      int j = localArrayList1.size();
      while (i < j)
      {
        XulView localXulView1 = (XulView)((WeakReference)localArrayList1.get(i)).get();
        if ((localXulView1 != null) && (localXulView1.focusable()))
        {
          localArrayList1.clear();
          if ((XulView)this.lastMotionDownEventView.get() == localXulView1)
            localXulLayout.doClick(this.actionCallback);
          return true;
        }
        i++;
      }
    }
  }

  public void refreshBinding(String paramString)
  {
    if (this._page == null)
      return;
    this._page.refreshBinding(paramString);
  }

  public void refreshBinding(String paramString, XulDataNode paramXulDataNode)
  {
    if (this._page == null)
      return;
    this._page.refreshBinding(paramString, paramXulDataNode);
    this._page.applyBinding(getDefaultActionCallback());
  }

  public void refreshBinding(String paramString1, String paramString2)
  {
    if (this._page == null)
      return;
    this._page.refreshBinding(paramString1, paramString2);
  }

  public void removeAnimation(IXulAnimation paramIXulAnimation)
  {
    this._animation.remove(paramIXulAnimation);
  }

  public void scheduleLayoutFinishedTask(Runnable paramRunnable)
  {
    this._scheduledLayoutFinishedTasks.add(paramRunnable);
  }

  public void setPageSize(int paramInt1, int paramInt2)
  {
    this._page.setPageSize(paramInt1, paramInt2);
  }

  public void suspend(boolean paramBoolean)
  {
    if (this._suspended == paramBoolean)
      return;
    if (this._suspendTaskCollector != null)
      this._suspendTaskCollector.reset();
    this._suspended = paramBoolean;
  }

  public void uiRun(Runnable paramRunnable)
  {
    if (this._handler == null)
      try
      {
        paramRunnable.run();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    this._handler.uiRun(paramRunnable);
  }

  public void uiRun(Runnable paramRunnable, int paramInt)
  {
    if (this._handler == null)
    {
      Log.e(TAG, "uiRun(delay:" + paramInt + ") - handler is null!!");
      return;
    }
    this._handler.uiRun(paramRunnable, paramInt);
  }

  public void updateHandler(IXulRenderHandler paramIXulRenderHandler)
  {
    this._handler = paramIXulRenderHandler;
  }

  private static class AnimationList extends XulSimpleArray<IXulAnimation>
  {
    public AnimationList(int paramInt)
    {
      super();
    }

    protected IXulAnimation[] allocArrayBuf(int paramInt)
    {
      return new IXulAnimation[paramInt];
    }
  }

  public static abstract interface IXulRenderHandler
  {
    public abstract IXulExternalView createExternalView(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, XulView paramXulView);

    public abstract InputStream getAppData(XulWorker.DownloadItem paramDownloadItem, String paramString);

    public abstract InputStream getAssets(XulWorker.DownloadItem paramDownloadItem, String paramString);

    public abstract void invalidate(Rect paramRect);

    public abstract void onDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject);

    public abstract void onRenderEvent(int paramInt1, int paramInt2, int paramInt3, Object paramObject);

    public abstract void onRenderIsReady();

    public abstract String resolve(XulWorker.DownloadItem paramDownloadItem, String paramString);

    public abstract void uiRun(Runnable paramRunnable);

    public abstract void uiRun(Runnable paramRunnable, int paramInt);
  }

  private static class RenderViewList extends XulSimpleArray<XulViewRender>
  {
    public RenderViewList(int paramInt)
    {
      super();
    }

    protected XulViewRender[] allocArrayBuf(int paramInt)
    {
      return new XulViewRender[paramInt];
    }
  }

  private static class TypefaceInfo
  {
    String name;
    Paint paint;
    Typeface typeface;
  }

  public class XulCreator
  {
    private XulSimpleArray<XulView> _newNodes = new XulSimpleArray(32)
    {
      protected XulView[] allocArrayBuf(int paramAnonymousInt)
      {
        return new XulView[paramAnonymousInt];
      }
    };

    public XulCreator()
    {
    }

    protected void addNewNode(XulArea paramXulArea)
    {
      XulView[] arrayOfXulView = (XulView[])this._newNodes.getArray();
      for (int i = -1 + this._newNodes.size(); i >= 0; i--)
        if (arrayOfXulView[i].isChildOf(paramXulArea))
          this._newNodes.remove(i);
      this._newNodes.add(paramXulArea);
    }

    protected void addNewNode(XulItem paramXulItem)
    {
      XulView[] arrayOfXulView = (XulView[])this._newNodes.getArray();
      int i = -1 + this._newNodes.size();
      if (i >= 0)
      {
        XulView localXulView = arrayOfXulView[i];
        if ((localXulView instanceof XulItem));
        while (!paramXulItem.isChildOf(localXulView))
        {
          i--;
          break;
        }
        return;
      }
      this._newNodes.add(paramXulItem);
    }

    public XulArea createChildArea(XulArea paramXulArea, int paramInt, String paramString)
    {
      return createChildArea(paramXulArea, paramInt, null, paramString);
    }

    public XulArea createChildArea(XulArea paramXulArea, int paramInt, String paramString1, String paramString2)
    {
      return createChildArea(paramXulArea, paramInt, paramString1, paramString2, null);
    }

    public XulArea createChildArea(XulArea paramXulArea, int paramInt, String paramString1, String paramString2, String paramString3)
    {
      XulArea localXulArea = new XulArea(paramXulArea, paramInt, paramString2);
      localXulArea._id = paramString1;
      localXulArea._binding = paramString3;
      addNewNode(localXulArea);
      return localXulArea;
    }

    public XulArea createChildArea(XulArea paramXulArea, String paramString)
    {
      return createChildArea(paramXulArea, -1, null, paramString);
    }

    public XulArea createChildArea(XulArea paramXulArea, String paramString1, String paramString2)
    {
      return createChildArea(paramXulArea, -1, paramString1, paramString2, null);
    }

    public XulArea createChildArea(XulArea paramXulArea, String paramString1, String paramString2, String paramString3)
    {
      return createChildArea(paramXulArea, -1, paramString1, paramString2, paramString3);
    }

    public XulItem createChildItem(XulArea paramXulArea, int paramInt, String paramString)
    {
      return createChildItem(paramXulArea, paramInt, paramString, null, null);
    }

    public XulItem createChildItem(XulArea paramXulArea, int paramInt, String paramString1, String paramString2)
    {
      return createChildItem(paramXulArea, paramInt, paramString1, paramString2, null);
    }

    public XulItem createChildItem(XulArea paramXulArea, int paramInt, String paramString1, String paramString2, String paramString3)
    {
      XulItem localXulItem = new XulItem(paramXulArea, paramInt);
      localXulItem._id = paramString1;
      localXulItem._type = paramString2;
      localXulItem._binding = paramString3;
      addNewNode(localXulItem);
      return localXulItem;
    }

    public XulItem createChildItem(XulArea paramXulArea, String paramString)
    {
      return createChildItem(paramXulArea, -1, null, paramString);
    }

    public XulItem createChildItem(XulArea paramXulArea, String paramString1, String paramString2)
    {
      return createChildItem(paramXulArea, -1, paramString1, paramString2, null);
    }

    public XulItem createChildItem(XulArea paramXulArea, String paramString1, String paramString2, String paramString3)
    {
      return createChildItem(paramXulArea, -1, paramString1, paramString2, paramString3);
    }

    public XulFocus createFocusProp(XulView paramXulView, String paramString)
    {
      return createFocusProp(paramXulView, paramString, null);
    }

    public XulFocus createFocusProp(XulView paramXulView, final String paramString1, final String paramString2)
    {
      XulFocus._Builder local_Builder = XulFocus._Builder.create(paramXulView);
      local_Builder.initialize("item", new XulFactory.Attributes()
      {
        public int getLength()
        {
          return 0;
        }

        public String getName(int paramAnonymousInt)
        {
          return null;
        }

        public String getValue(int paramAnonymousInt)
        {
          return null;
        }

        public String getValue(String paramAnonymousString)
        {
          if ("mode".equals(paramAnonymousString))
            return paramString1;
          if ("priority".equals(paramAnonymousString))
            return paramString2;
          return null;
        }
      });
      return (XulFocus)local_Builder.finalItem();
    }

    public void finish()
    {
      XulView[] arrayOfXulView = (XulView[])this._newNodes.getArray();
      XulRenderContext localXulRenderContext = XulRenderContext.this;
      int i = 0;
      int j = this._newNodes.size();
      if (i < j)
      {
        XulView localXulView = arrayOfXulView[i];
        localXulView.prepareRender(localXulRenderContext);
        if ((localXulView instanceof XulArea))
        {
          XulRenderContext.this._page.addSelectorTargets((XulArea)localXulView);
          XulRenderContext.this._page.applySelectors((XulArea)localXulView);
        }
        while (true)
        {
          i++;
          break;
          XulRenderContext.this._page.addSelectorTarget((XulItem)localXulView);
          XulRenderContext.this._page.applySelectors((XulItem)localXulView);
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulRenderContext
 * JD-Core Version:    0.6.2
 */