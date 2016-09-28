package com.starcor.xulapp.debug;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulData;
import com.starcor.xul.Prop.XulFocus;
import com.starcor.xul.Prop.XulProp;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.PropMap.IXulPropIterator;
import com.starcor.xul.Render.XulLayerRender;
import com.starcor.xul.Render.XulMassiveRender;
import com.starcor.xul.Render.XulSliderAreaRender;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerHandler;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerRequest;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerResponse;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class XulDebugMonitor
{
  private static final List<String>[] focusOrderMap = arrayOfList;
  private static final String[] xulPropStateMap = { "normal", "focused", "disabled", "-", "visible", "-", "-", "-", "invisible" };
  private volatile SimpleDateFormat _dateTimeFormat;
  private volatile Paint _debugInfoPaint;
  private volatile WeakReference<XulView> _debugInfoTarget;
  WeakHashMap<Object, PageInfo> _pages = new WeakHashMap();
  LinkedHashMap<Integer, PageInfo> _pagesById = new LinkedHashMap();
  private volatile Bitmap _snapshotBmp;
  Map<Integer, IXulDebuggableObject> _userObjectMap = new TreeMap();
  Map<Integer, WeakReference<XulView>> _viewMap = new TreeMap();

  static
  {
    List[] arrayOfList = new List[6];
    arrayOfList[0] = Arrays.asList(new String[] { "dynamic", "priority", "nearby" });
    arrayOfList[1] = Arrays.asList(new String[] { "dynamic", "nearby", "priority" });
    arrayOfList[2] = Arrays.asList(new String[] { "nearby", "dynamic", "priority" });
    arrayOfList[3] = Arrays.asList(new String[] { "nearby", "priority", "dynamic" });
    arrayOfList[4] = Arrays.asList(new String[] { "priority", "dynamic", "nearby" });
    arrayOfList[5] = Arrays.asList(new String[] { "priority", "nearby", "dynamic" });
  }

  private void dumpItem(XulView paramXulView, XmlSerializer paramXmlSerializer, LinkedHashMap<String, String> paramLinkedHashMap)
    throws Exception
  {
    if (paramXulView == null)
      return;
    XmlContentDumper localXmlContentDumper = new XmlContentDumper(paramXmlSerializer);
    if (paramLinkedHashMap != null)
    {
      String str = (String)paramLinkedHashMap.get("with-children");
      if ("true".equals((String)paramLinkedHashMap.get("skip-prop")))
        localXmlContentDumper.setNoProp(true);
      if ("true".equals(str))
        localXmlContentDumper.setNoChildren(false);
    }
    while (true)
    {
      localXmlContentDumper.dumpXulView(paramXulView);
      return;
      localXmlContentDumper.setNoChildren(true);
      continue;
      localXmlContentDumper.setNoChildren(true);
    }
  }

  private void dumpLayout(XulLayout paramXulLayout, XmlSerializer paramXmlSerializer, LinkedHashMap<String, String> paramLinkedHashMap)
    throws Exception
  {
    if (paramXulLayout == null)
      return;
    XmlContentDumper localXmlContentDumper = new XmlContentDumper(paramXmlSerializer);
    if ((paramLinkedHashMap != null) && ("true".equals((String)paramLinkedHashMap.get("skip-prop"))))
      localXmlContentDumper.setNoProp(true);
    localXmlContentDumper.dumpXulLayout(paramXulLayout);
  }

  private static String propStateFromId(int paramInt)
  {
    if (paramInt < 0)
      return "inline";
    return xulPropStateMap[paramInt];
  }

  public boolean addItemClass(int paramInt, final String[] paramArrayOfString, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new XulViewOpRunnable(paramArrayOfString)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        String[] arrayOfString = paramArrayOfString;
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++)
          if (paramAnonymousXulView.addClass(arrayOfString[j]))
            paramAnonymousXulView.resetRender();
      }
    });
  }

  public boolean closePage(int paramInt, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: new 165	com/starcor/xulapp/debug/XulDebugMonitor$14
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 168	com/starcor/xulapp/debug/XulDebugMonitor$14:<init>	(Lcom/starcor/xulapp/debug/XulDebugMonitor;)V
    //   10: invokevirtual 161	com/starcor/xulapp/debug/XulDebugMonitor:execUiOpAndWait	(ILcom/starcor/xulapp/debug/XulDebugMonitor$UiOpRunnable;)Z
    //   13: ireturn
  }

  public void drawDebugInfo(XulRenderContext paramXulRenderContext, Canvas paramCanvas)
  {
    if (this._debugInfoTarget == null);
    RectF localRectF;
    do
    {
      Object localObject;
      XulViewRender localXulViewRender1;
      do
      {
        do
        {
          return;
          localObject = (XulView)this._debugInfoTarget.get();
        }
        while (localObject == null);
        localRectF = ((XulView)localObject).getUpdateRc();
        localXulViewRender1 = ((XulView)localObject).getRender();
      }
      while ((localXulViewRender1 == null) || (localXulViewRender1.getRenderContext() != paramXulRenderContext));
      Rect localRect = localXulViewRender1.getPadding();
      float f1 = localXulViewRender1.getViewXScalar();
      float f2 = localXulViewRender1.getViewYScalar();
      while (localObject != null)
      {
        XulViewRender localXulViewRender2 = ((XulView)localObject).getRender();
        if ((localXulViewRender2 instanceof XulLayerRender))
        {
          XulLayerRender localXulLayerRender = (XulLayerRender)localXulViewRender2;
          f1 *= localXulViewRender2.getViewXScalar();
          f2 *= localXulViewRender2.getViewYScalar();
          localRectF.offset(localXulLayerRender.getDrawingOffsetX(), localXulLayerRender.getDrawingOffsetY());
        }
        localObject = ((XulView)localObject).getParent();
      }
      paramCanvas.save(2);
      paramCanvas.clipRect(localRectF);
      paramCanvas.drawColor(1895825407);
      paramCanvas.restore();
      paramCanvas.drawRect(localRectF, this._debugInfoPaint);
      localRectF.left += f1 * localRect.left;
      localRectF.top += f2 * localRect.top;
      localRectF.right -= f1 * localRect.right;
      localRectF.bottom -= f2 * localRect.bottom;
    }
    while ((localRectF.bottom <= localRectF.top) || (localRectF.right <= localRectF.left));
    paramCanvas.save(2);
    paramCanvas.clipRect(localRectF);
    paramCanvas.drawColor(1879048447);
    paramCanvas.restore();
  }

  public boolean dumpPageList(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localXmlSerializer.setOutput(paramXulHttpServerResponse.getBodyStream(), "utf-8");
      try
      {
        localXmlSerializer.startDocument("utf-8", Boolean.TRUE);
        localXmlSerializer.startTag(null, "pages");
        Iterator localIterator = this._pagesById.entrySet().iterator();
        while (localIterator.hasNext())
        {
          PageInfo localPageInfo = (PageInfo)((Map.Entry)localIterator.next()).getValue();
          Object localObject2 = localPageInfo.page.get();
          if (localObject2 != null)
          {
            localXmlSerializer.startTag(null, "page");
            localXmlSerializer.attribute(null, "id", Integer.toString(localPageInfo.id));
            XulDebugAdapter.writePageSpecifiedAttribute(localObject2, localXmlSerializer);
            localXmlSerializer.attribute(null, "status", localPageInfo.status);
            if (localPageInfo.firstResumedTime >= localPageInfo.createTime)
              localXmlSerializer.attribute(null, "resumeTime", Long.toString(localPageInfo.firstResumedTime - localPageInfo.createTime));
            if (localPageInfo.renderIsReadyTime >= localPageInfo.createTime)
              localXmlSerializer.attribute(null, "readyTime", Long.toString(localPageInfo.renderIsReadyTime - localPageInfo.createTime));
            if (localPageInfo.refreshCount > 0)
            {
              Object[] arrayOfObject = new Object[4];
              arrayOfObject[0] = Integer.valueOf(localPageInfo.refreshCount);
              arrayOfObject[1] = Double.valueOf(localPageInfo.totalDrawingDuration / localPageInfo.refreshCount / 1000.0D);
              arrayOfObject[2] = Double.valueOf(localPageInfo.minDrawingDuration / 1000.0D);
              arrayOfObject[3] = Double.valueOf(localPageInfo.maxDrawingDuration / 1000.0D);
              localXmlSerializer.attribute(null, "drawing", String.format("frames:%d, avg:%.2f, min:%.2f, max:%.2f", arrayOfObject));
            }
            localXmlSerializer.endTag(null, "page");
          }
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        bool = false;
        return bool;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        XmlSerializer localXmlSerializer;
        localException.printStackTrace();
        boolean bool = false;
        continue;
        localXmlSerializer.endTag(null, "pages");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        bool = true;
      }
    }
    finally
    {
    }
  }

  protected boolean execUiOpAndWait(int paramInt, UiOpRunnable paramUiOpRunnable)
  {
    while (true)
    {
      XulView localXulView;
      try
      {
        localXulView = xulViewFromId(paramInt);
        if (localXulView == null)
        {
          PageInfo localPageInfo = (PageInfo)this._pagesById.get(Integer.valueOf(paramInt));
          if (localPageInfo == null)
            return false;
          Object localObject2 = localPageInfo.page.get();
          paramUiOpRunnable.setCurrentPageInfo(localPageInfo);
          XulRenderContext localXulRenderContext = XulDebugAdapter.getPageRenderContext(localObject2);
          if (localXulRenderContext != null)
            paramUiOpRunnable.setCurrentPage(localXulRenderContext.getPage());
          if (paramUiOpRunnable.beginExec())
            break;
          return false;
        }
      }
      finally
      {
      }
      paramUiOpRunnable.setCurrentView(localXulView);
    }
    if (paramUiOpRunnable.start())
      return true;
    return paramUiOpRunnable.waitFinish();
  }

  public XulHttpServer.XulHttpServerResponse execUserObjectCommand(int paramInt, final String paramString, final XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, final XulHttpServer.XulHttpServerHandler paramXulHttpServerHandler)
  {
    while (true)
    {
      final XulHttpServer.XulHttpServerResponse[] arrayOfXulHttpServerResponse;
      final IXulDebuggableObject localIXulDebuggableObject;
      try
      {
        arrayOfXulHttpServerResponse = new XulHttpServer.XulHttpServerResponse[1];
        try
        {
          localIXulDebuggableObject = (IXulDebuggableObject)this._userObjectMap.get(Integer.valueOf(paramInt));
          if ((localIXulDebuggableObject == null) || (!localIXulDebuggableObject.isValid()))
          {
            this._userObjectMap.remove(Integer.valueOf(paramInt));
            return null;
          }
          if (localIXulDebuggableObject.runInMainThread())
          {
            final Semaphore localSemaphore = new Semaphore(0);
            XulDebugAdapter.postToMainLooper(new Runnable()
            {
              public void run()
              {
                try
                {
                  arrayOfXulHttpServerResponse[0] = localIXulDebuggableObject.execCommand(paramString, paramXulHttpServerRequest, paramXulHttpServerHandler);
                  localSemaphore.release();
                  return;
                }
                catch (Exception localException)
                {
                  while (true)
                    localException.printStackTrace();
                }
              }
            });
            localSemaphore.tryAcquire(20L, TimeUnit.SECONDS);
            XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = arrayOfXulHttpServerResponse[0];
            return localXulHttpServerResponse;
          }
        }
        finally
        {
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      arrayOfXulHttpServerResponse[0] = localIXulDebuggableObject.execCommand(paramString, paramXulHttpServerRequest, paramXulHttpServerHandler);
    }
  }

  public boolean fireEvent(int paramInt, final String paramString, final String[] paramArrayOfString, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new UiOpRunnable(paramArrayOfString)
    {
      boolean beginExec()
      {
        return (this._pageInfo != null) || (this._xulView != null);
      }

      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        if (paramAnonymousXulView == null)
          paramAnonymousXulView = paramAnonymousXulPage;
        if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
        {
          XulPage.invokeActionNoPopupWithArgs(paramAnonymousXulView, paramString, paramArrayOfString);
          return;
        }
        XulPage.invokeActionNoPopup(paramAnonymousXulView, paramString);
      }
    });
  }

  public boolean getItemContent(int paramInt, final XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, final XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new UiOpRunnable(paramXulHttpServerResponse)
    {
      boolean beginExec()
      {
        return (this._xulPage != null) || (this._xulView != null);
      }

      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        if (paramAnonymousXulView == null)
          paramAnonymousXulView = paramAnonymousXulPage.getLayout();
        XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localXmlSerializer.setOutput(paramXulHttpServerResponse.getBodyStream(), "utf-8");
        localXmlSerializer.startDocument("utf-8", Boolean.TRUE);
        XulDebugMonitor.this.dumpItem(paramAnonymousXulView, localXmlSerializer, paramXulHttpServerRequest.queries);
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
      }
    });
  }

  public boolean getPageLayout(int paramInt, final XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, final XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new XulPageOpRunnable(paramXulHttpServerResponse)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        if (paramAnonymousPageInfo == null);
        Object localObject;
        do
        {
          return;
          localObject = paramAnonymousPageInfo.page.get();
        }
        while (localObject == null);
        XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localXmlSerializer.setOutput(paramXulHttpServerResponse.getBodyStream(), "utf-8");
        localXmlSerializer.startDocument("utf-8", Boolean.TRUE);
        localXmlSerializer.startTag(null, "page");
        localXmlSerializer.attribute(null, "id", String.valueOf(paramAnonymousPageInfo.id));
        XulDebugAdapter.writePageSpecifiedAttribute(localObject, localXmlSerializer);
        localXmlSerializer.attribute(null, "status", paramAnonymousPageInfo.status);
        localXmlSerializer.attribute(null, "refreshTime", String.valueOf(paramAnonymousPageInfo.getRefreshTime()));
        XulDebugMonitor.this.dumpLayout(paramAnonymousXulPage.getLayout(), localXmlSerializer, paramXulHttpServerRequest.queries);
        localXmlSerializer.endTag(null, "page");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
      }
    });
  }

  // ERROR //
  public boolean getPageSnapshot(int paramInt, final XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, final XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 100	com/starcor/xulapp/debug/XulDebugMonitor:_snapshotBmp	Landroid/graphics/Bitmap;
    //   6: ifnonnull +19 -> 25
    //   9: aload_0
    //   10: invokestatic 537	com/starcor/xul/XulManager:getPageWidth	()I
    //   13: invokestatic 540	com/starcor/xul/XulManager:getPageHeight	()I
    //   16: getstatic 546	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   19: invokestatic 552	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   22: putfield 100	com/starcor/xulapp/debug/XulDebugMonitor:_snapshotBmp	Landroid/graphics/Bitmap;
    //   25: aload_0
    //   26: monitorexit
    //   27: new 554	com/starcor/xulapp/debug/XulDebugMonitor$1
    //   30: dup
    //   31: aload_0
    //   32: aload_2
    //   33: aload_3
    //   34: invokespecial 557	com/starcor/xulapp/debug/XulDebugMonitor$1:<init>	(Lcom/starcor/xulapp/debug/XulDebugMonitor;Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerRequest;Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse;)V
    //   37: astore 5
    //   39: aload_0
    //   40: getfield 100	com/starcor/xulapp/debug/XulDebugMonitor:_snapshotBmp	Landroid/graphics/Bitmap;
    //   43: astore 6
    //   45: aload 6
    //   47: monitorenter
    //   48: aload_0
    //   49: iload_1
    //   50: aload 5
    //   52: invokevirtual 161	com/starcor/xulapp/debug/XulDebugMonitor:execUiOpAndWait	(ILcom/starcor/xulapp/debug/XulDebugMonitor$UiOpRunnable;)Z
    //   55: istore 8
    //   57: aload 6
    //   59: monitorexit
    //   60: iload 8
    //   62: ireturn
    //   63: astore 4
    //   65: aload_0
    //   66: monitorexit
    //   67: aload 4
    //   69: athrow
    //   70: astore 7
    //   72: aload 6
    //   74: monitorexit
    //   75: aload 7
    //   77: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	25	63	finally
    //   25	27	63	finally
    //   65	67	63	finally
    //   48	60	70	finally
    //   72	75	70	finally
  }

  public boolean getUserObject(int paramInt, final XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    while (true)
    {
      final IXulDebuggableObject localIXulDebuggableObject;
      final XmlSerializer localXmlSerializer;
      try
      {
        try
        {
          localIXulDebuggableObject = (IXulDebuggableObject)this._userObjectMap.get(Integer.valueOf(paramInt));
          if ((localIXulDebuggableObject == null) || (!localIXulDebuggableObject.isValid()))
          {
            this._userObjectMap.remove(Integer.valueOf(paramInt));
            return false;
          }
          localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
          localXmlSerializer.setOutput(paramXulHttpServerResponse.getBodyStream(), "utf-8");
          localXmlSerializer.startDocument("utf-8", Boolean.TRUE);
          localXmlSerializer.startTag(null, "object");
          localXmlSerializer.startTag(null, "object");
          localXmlSerializer.attribute(null, "id", String.valueOf(paramInt));
          localXmlSerializer.attribute(null, "name", localIXulDebuggableObject.name());
          if (localIXulDebuggableObject.runInMainThread())
          {
            final Semaphore localSemaphore = new Semaphore(0);
            XulDebugAdapter.postToMainLooper(new Runnable()
            {
              public void run()
              {
                try
                {
                  localIXulDebuggableObject.buildDetailInfo(paramXulHttpServerRequest, localXmlSerializer);
                  localSemaphore.release();
                  return;
                }
                catch (Exception localException)
                {
                  while (true)
                    localException.printStackTrace();
                }
              }
            });
            localSemaphore.tryAcquire(20L, TimeUnit.SECONDS);
            localXmlSerializer.endDocument();
            localXmlSerializer.flush();
            return true;
          }
        }
        finally
        {
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      localIXulDebuggableObject.buildDetailInfo(paramXulHttpServerRequest, localXmlSerializer);
    }
  }

  // ERROR //
  public boolean highlightItem(int paramInt, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: ifne +53 -> 56
    //   6: aload_0
    //   7: getfield 172	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoTarget	Ljava/lang/ref/WeakReference;
    //   10: invokevirtual 177	java/lang/ref/WeakReference:get	()Ljava/lang/Object;
    //   13: checkcast 179	com/starcor/xul/XulView
    //   16: astore 12
    //   18: aload_0
    //   19: aconst_null
    //   20: putfield 172	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoTarget	Ljava/lang/ref/WeakReference;
    //   23: aload 12
    //   25: invokevirtual 187	com/starcor/xul/XulView:getRender	()Lcom/starcor/xul/Render/XulViewRender;
    //   28: invokevirtual 193	com/starcor/xul/Render/XulViewRender:getRenderContext	()Lcom/starcor/xul/XulRenderContext;
    //   31: astore 13
    //   33: aload 13
    //   35: new 579	com/starcor/xulapp/debug/XulDebugMonitor$15
    //   38: dup
    //   39: aload_0
    //   40: aload 13
    //   42: invokespecial 582	com/starcor/xulapp/debug/XulDebugMonitor$15:<init>	(Lcom/starcor/xulapp/debug/XulDebugMonitor;Lcom/starcor/xul/XulRenderContext;)V
    //   45: invokevirtual 585	com/starcor/xul/XulRenderContext:uiRun	(Ljava/lang/Runnable;)V
    //   48: iconst_1
    //   49: istore 9
    //   51: aload_0
    //   52: monitorexit
    //   53: iload 9
    //   55: ireturn
    //   56: aload_0
    //   57: iload_1
    //   58: invokevirtual 436	com/starcor/xulapp/debug/XulDebugMonitor:xulViewFromId	(I)Lcom/starcor/xul/XulView;
    //   61: astore 5
    //   63: aload 5
    //   65: astore 6
    //   67: aload 6
    //   69: ifnonnull +31 -> 100
    //   72: aload_0
    //   73: getfield 76	com/starcor/xulapp/debug/XulDebugMonitor:_pagesById	Ljava/util/LinkedHashMap;
    //   76: iload_1
    //   77: invokestatic 395	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   80: invokevirtual 125	java/util/LinkedHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   83: checkcast 341	com/starcor/xulapp/debug/XulDebugMonitor$PageInfo
    //   86: getfield 344	com/starcor/xulapp/debug/XulDebugMonitor$PageInfo:page	Ljava/lang/ref/WeakReference;
    //   89: invokevirtual 177	java/lang/ref/WeakReference:get	()Ljava/lang/Object;
    //   92: invokestatic 446	com/starcor/xulapp/debug/XulDebugAdapter:getPageRenderContext	(Ljava/lang/Object;)Lcom/starcor/xul/XulRenderContext;
    //   95: invokevirtual 589	com/starcor/xul/XulRenderContext:getLayout	()Lcom/starcor/xul/XulLayout;
    //   98: astore 6
    //   100: aload 6
    //   102: invokevirtual 187	com/starcor/xul/XulView:getRender	()Lcom/starcor/xul/Render/XulViewRender;
    //   105: invokevirtual 193	com/starcor/xul/Render/XulViewRender:getRenderContext	()Lcom/starcor/xul/XulRenderContext;
    //   108: astore 10
    //   110: aload 10
    //   112: astore 8
    //   114: aload 6
    //   116: ifnull +8 -> 124
    //   119: aload 8
    //   121: ifnonnull +9 -> 130
    //   124: iconst_0
    //   125: istore 9
    //   127: goto -76 -> 51
    //   130: aload_0
    //   131: getfield 242	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoPaint	Landroid/graphics/Paint;
    //   134: ifnonnull +55 -> 189
    //   137: aload_0
    //   138: new 591	android/graphics/Paint
    //   141: dup
    //   142: iconst_1
    //   143: invokespecial 592	android/graphics/Paint:<init>	(I)V
    //   146: putfield 242	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoPaint	Landroid/graphics/Paint;
    //   149: aload_0
    //   150: getfield 242	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoPaint	Landroid/graphics/Paint;
    //   153: ldc_w 593
    //   156: invokevirtual 596	android/graphics/Paint:setColor	(I)V
    //   159: aload_0
    //   160: getfield 242	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoPaint	Landroid/graphics/Paint;
    //   163: sipush 200
    //   166: invokevirtual 599	android/graphics/Paint:setAlpha	(I)V
    //   169: aload_0
    //   170: getfield 242	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoPaint	Landroid/graphics/Paint;
    //   173: ldc_w 600
    //   176: invokevirtual 604	android/graphics/Paint:setStrokeWidth	(F)V
    //   179: aload_0
    //   180: getfield 242	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoPaint	Landroid/graphics/Paint;
    //   183: getstatic 610	android/graphics/Paint$Style:STROKE	Landroid/graphics/Paint$Style;
    //   186: invokevirtual 614	android/graphics/Paint:setStyle	(Landroid/graphics/Paint$Style;)V
    //   189: aload_0
    //   190: aload 6
    //   192: invokevirtual 618	com/starcor/xul/XulView:getWeakReference	()Ljava/lang/ref/WeakReference;
    //   195: putfield 172	com/starcor/xulapp/debug/XulDebugMonitor:_debugInfoTarget	Ljava/lang/ref/WeakReference;
    //   198: aload 8
    //   200: new 620	com/starcor/xulapp/debug/XulDebugMonitor$16
    //   203: dup
    //   204: aload_0
    //   205: aload 8
    //   207: invokespecial 621	com/starcor/xulapp/debug/XulDebugMonitor$16:<init>	(Lcom/starcor/xulapp/debug/XulDebugMonitor;Lcom/starcor/xul/XulRenderContext;)V
    //   210: invokevirtual 585	com/starcor/xul/XulRenderContext:uiRun	(Ljava/lang/Runnable;)V
    //   213: iconst_1
    //   214: istore 9
    //   216: goto -165 -> 51
    //   219: astore 4
    //   221: aload_0
    //   222: monitorexit
    //   223: aload 4
    //   225: athrow
    //   226: astore 7
    //   228: aconst_null
    //   229: astore 8
    //   231: goto -117 -> 114
    //   234: astore 11
    //   236: goto -188 -> 48
    //
    // Exception table:
    //   from	to	target	type
    //   6	48	219	finally
    //   56	63	219	finally
    //   72	100	219	finally
    //   100	110	219	finally
    //   130	189	219	finally
    //   189	213	219	finally
    //   72	100	226	java/lang/Exception
    //   100	110	226	java/lang/Exception
    //   6	48	234	java/lang/Exception
  }

  // ERROR //
  public boolean listUserObjects(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 281	org/xmlpull/v1/XmlPullParserFactory:newInstance	()Lorg/xmlpull/v1/XmlPullParserFactory;
    //   5: invokevirtual 285	org/xmlpull/v1/XmlPullParserFactory:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
    //   8: astore 7
    //   10: aload 7
    //   12: aload_2
    //   13: invokevirtual 291	com/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse:getBodyStream	()Ljava/io/OutputStream;
    //   16: ldc_w 293
    //   19: invokeinterface 299 3 0
    //   24: aload 7
    //   26: ldc_w 293
    //   29: getstatic 305	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
    //   32: invokeinterface 309 3 0
    //   37: aload 7
    //   39: aconst_null
    //   40: ldc_w 626
    //   43: invokeinterface 315 3 0
    //   48: pop
    //   49: aload_0
    //   50: getfield 83	com/starcor/xulapp/debug/XulDebugMonitor:_userObjectMap	Ljava/util/Map;
    //   53: invokeinterface 627 1 0
    //   58: invokeinterface 325 1 0
    //   63: astore 9
    //   65: aload 9
    //   67: invokeinterface 331 1 0
    //   72: ifeq +166 -> 238
    //   75: aload 9
    //   77: invokeinterface 334 1 0
    //   82: checkcast 336	java/util/Map$Entry
    //   85: astore 11
    //   87: aload 11
    //   89: invokeinterface 339 1 0
    //   94: checkcast 475	com/starcor/xulapp/debug/IXulDebuggableObject
    //   97: astore 12
    //   99: aload 12
    //   101: invokeinterface 478 1 0
    //   106: ifne +28 -> 134
    //   109: aload 9
    //   111: invokeinterface 629 1 0
    //   116: goto -51 -> 65
    //   119: astore 6
    //   121: aload 6
    //   123: invokevirtual 630	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   126: iconst_0
    //   127: istore 5
    //   129: aload_0
    //   130: monitorexit
    //   131: iload 5
    //   133: ireturn
    //   134: aload 7
    //   136: aconst_null
    //   137: ldc_w 560
    //   140: invokeinterface 315 3 0
    //   145: pop
    //   146: aload 7
    //   148: aconst_null
    //   149: ldc_w 347
    //   152: aload 11
    //   154: invokeinterface 633 1 0
    //   159: invokestatic 636	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   162: invokeinterface 358 4 0
    //   167: pop
    //   168: aload 7
    //   170: aconst_null
    //   171: ldc_w 564
    //   174: aload 12
    //   176: invokeinterface 567 1 0
    //   181: invokeinterface 358 4 0
    //   186: pop
    //   187: aload 12
    //   189: aload_1
    //   190: aload 7
    //   192: invokeinterface 639 3 0
    //   197: pop
    //   198: aload 7
    //   200: aconst_null
    //   201: ldc_w 560
    //   204: invokeinterface 422 3 0
    //   209: pop
    //   210: goto -145 -> 65
    //   213: astore 4
    //   215: aload 4
    //   217: invokevirtual 425	java/io/IOException:printStackTrace	()V
    //   220: goto -94 -> 126
    //   223: astore_3
    //   224: aload_0
    //   225: monitorexit
    //   226: aload_3
    //   227: athrow
    //   228: astore 16
    //   230: aload 16
    //   232: invokevirtual 426	java/lang/Exception:printStackTrace	()V
    //   235: goto -37 -> 198
    //   238: aload 7
    //   240: aconst_null
    //   241: ldc_w 626
    //   244: invokeinterface 422 3 0
    //   249: pop
    //   250: aload 7
    //   252: invokeinterface 429 1 0
    //   257: aload 7
    //   259: invokeinterface 432 1 0
    //   264: iconst_1
    //   265: istore 5
    //   267: goto -138 -> 129
    //
    // Exception table:
    //   from	to	target	type
    //   2	65	119	org/xmlpull/v1/XmlPullParserException
    //   65	116	119	org/xmlpull/v1/XmlPullParserException
    //   134	187	119	org/xmlpull/v1/XmlPullParserException
    //   187	198	119	org/xmlpull/v1/XmlPullParserException
    //   198	210	119	org/xmlpull/v1/XmlPullParserException
    //   230	235	119	org/xmlpull/v1/XmlPullParserException
    //   238	264	119	org/xmlpull/v1/XmlPullParserException
    //   2	65	213	java/io/IOException
    //   65	116	213	java/io/IOException
    //   134	187	213	java/io/IOException
    //   187	198	213	java/io/IOException
    //   198	210	213	java/io/IOException
    //   230	235	213	java/io/IOException
    //   238	264	213	java/io/IOException
    //   2	65	223	finally
    //   65	116	223	finally
    //   121	126	223	finally
    //   134	187	223	finally
    //   187	198	223	finally
    //   198	210	223	finally
    //   215	220	223	finally
    //   230	235	223	finally
    //   238	264	223	finally
    //   187	198	228	java/lang/Exception
  }

  public void onPageCreate(Object paramObject)
  {
    try
    {
      PageInfo localPageInfo = new PageInfo(paramObject, paramObject.hashCode());
      this._pages.put(paramObject, localPageInfo);
      this._pagesById.put(Integer.valueOf(localPageInfo.id), localPageInfo);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void onPageDestroy(Object paramObject)
  {
    try
    {
      PageInfo localPageInfo = (PageInfo)this._pages.remove(paramObject);
      if (localPageInfo == null);
      while (true)
      {
        return;
        this._pagesById.remove(Integer.valueOf(localPageInfo.id));
        localPageInfo.status = "destroyed";
      }
    }
    finally
    {
    }
  }

  public void onPagePaused(Object paramObject)
  {
    try
    {
      PageInfo localPageInfo = (PageInfo)this._pages.get(paramObject);
      if (localPageInfo == null);
      while (true)
      {
        return;
        localPageInfo.status = "paused";
      }
    }
    finally
    {
    }
  }

  public void onPageRefreshed(Object paramObject, long paramLong)
  {
    try
    {
      PageInfo localPageInfo = (PageInfo)this._pages.get(paramObject);
      if (localPageInfo == null);
      while (true)
      {
        return;
        localPageInfo.onPageRefreshed(paramLong);
      }
    }
    finally
    {
    }
  }

  public void onPageRenderIsReady(Object paramObject)
  {
    try
    {
      PageInfo localPageInfo = (PageInfo)this._pages.get(paramObject);
      if (localPageInfo == null);
      while (true)
      {
        return;
        localPageInfo.onPageRenderIsReady();
      }
    }
    finally
    {
    }
  }

  public void onPageResumed(Object paramObject)
  {
    try
    {
      PageInfo localPageInfo = (PageInfo)this._pages.get(paramObject);
      if (localPageInfo == null);
      while (true)
      {
        return;
        localPageInfo.onResume();
      }
    }
    finally
    {
    }
  }

  public void onPageStopped(Object paramObject)
  {
    try
    {
      PageInfo localPageInfo = (PageInfo)this._pages.get(paramObject);
      if (localPageInfo == null);
      while (true)
      {
        return;
        localPageInfo.status = "stopped";
      }
    }
    finally
    {
    }
  }

  public boolean popPageState(int paramInt, final boolean paramBoolean, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new XulPageOpRunnable(paramBoolean)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        paramAnonymousXulPage.popStates(paramBoolean);
      }
    });
  }

  public boolean pushPageState(final String[] paramArrayOfString, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(XulUtils.tryParseInt(paramArrayOfString[0]), new XulPageOpRunnable(paramArrayOfString)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        Object[] arrayOfObject = new Object[paramArrayOfString.length];
        int i = 0;
        int j = paramArrayOfString.length;
        if (i < j)
        {
          Object localObject = paramArrayOfString[i];
          XulView localXulView;
          if (TextUtils.isDigitsOnly((CharSequence)localObject))
          {
            localXulView = XulDebugMonitor.this.xulViewFromId(XulUtils.tryParseInt((String)localObject));
            if (localXulView == null)
              label63: arrayOfObject[i] = localObject;
          }
          while (true)
          {
            i++;
            break;
            localObject = localXulView;
            break label63;
            arrayOfObject[i] = URLDecoder.decode((String)localObject, "utf-8");
          }
        }
        paramAnonymousXulPage.pushStates(arrayOfObject);
      }
    });
  }

  public boolean registerDebuggableObject(IXulDebuggableObject paramIXulDebuggableObject)
  {
    try
    {
      this._userObjectMap.put(Integer.valueOf(paramIXulDebuggableObject.hashCode()), paramIXulDebuggableObject);
      return true;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean removeItemClass(int paramInt, final String[] paramArrayOfString, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new XulViewOpRunnable(paramArrayOfString)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        String[] arrayOfString = paramArrayOfString;
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++)
          if (paramAnonymousXulView.removeClass(arrayOfString[j]))
            paramAnonymousXulView.resetRender();
      }
    });
  }

  public boolean requestItemFocus(int paramInt, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: new 704	com/starcor/xulapp/debug/XulDebugMonitor$8
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 705	com/starcor/xulapp/debug/XulDebugMonitor$8:<init>	(Lcom/starcor/xulapp/debug/XulDebugMonitor;)V
    //   10: invokevirtual 161	com/starcor/xulapp/debug/XulDebugMonitor:execUiOpAndWait	(ILcom/starcor/xulapp/debug/XulDebugMonitor$UiOpRunnable;)Z
    //   13: ireturn
  }

  public boolean sendKeyEvents(final String[] paramArrayOfString, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    int i = 0;
    Iterator localIterator = this._pagesById.entrySet().iterator();
    while (localIterator.hasNext())
      i = ((Integer)((Map.Entry)localIterator.next()).getKey()).intValue();
    return execUiOpAndWait(i, new XulPageOpRunnable(paramArrayOfString)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        Object localObject = paramAnonymousPageInfo.page.get();
        String[] arrayOfString1 = paramArrayOfString;
        int i = arrayOfString1.length;
        for (int j = 0; ; j++)
        {
          String[] arrayOfString2;
          int k;
          int m;
          String str;
          int i1;
          if (j < i)
          {
            arrayOfString2 = arrayOfString1[j].split(",");
            k = 2;
            if (!"down".equals(arrayOfString2[0]))
              break label105;
            k = 0;
            m = 1;
            int n = arrayOfString2.length;
            if (m >= n)
              continue;
            str = arrayOfString2[m];
            if (!"left".equals(str))
              break label141;
            i1 = 21;
          }
          while (true)
          {
            if (!XulDebugAdapter.isPageFinished(localObject))
              break label269;
            return;
            label105: if ("up".equals(arrayOfString2[0]))
            {
              k = 1;
              break;
            }
            if (!"click".equals(arrayOfString2[0]))
              break;
            k = 2;
            break;
            label141: if ("up".equals(str))
              i1 = 19;
            else if ("down".equals(str))
              i1 = 20;
            else if ("right".equals(str))
              i1 = 22;
            else if ("enter".equals(str))
              i1 = 66;
            else if ("ok".equals(str))
              i1 = 23;
            else if ("back".equals(str))
              i1 = 4;
            else if ("menu".equals(str))
              i1 = 82;
            else
              i1 = KeyEvent.keyCodeFromString(str);
          }
          label269: if (2 == k)
          {
            XulDebugAdapter.dispatchKeyEvent(localObject, new KeyEvent(0, i1));
            XulDebugAdapter.dispatchKeyEvent(localObject, new KeyEvent(1, i1));
          }
          while (true)
          {
            m++;
            break;
            XulDebugAdapter.dispatchKeyEvent(localObject, new KeyEvent(k, i1));
          }
        }
      }
    });
  }

  public boolean setItemAttr(int paramInt, final String paramString1, final String paramString2, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new XulViewOpRunnable(paramString1)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        paramAnonymousXulView.setAttr(paramString1, paramString2);
        paramAnonymousXulView.resetRender();
      }
    });
  }

  public boolean setItemStyle(int paramInt, final String paramString1, final String paramString2, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
  {
    return execUiOpAndWait(paramInt, new XulViewOpRunnable(paramString1)
    {
      protected void exec(XulDebugMonitor.PageInfo paramAnonymousPageInfo, XulPage paramAnonymousXulPage, XulView paramAnonymousXulView)
        throws Exception
      {
        paramAnonymousXulView.setStyle(paramString1, paramString2);
        paramAnonymousXulView.resetRender();
      }
    });
  }

  public XulView xulViewFromId(int paramInt)
  {
    WeakReference localWeakReference = (WeakReference)this._viewMap.get(Integer.valueOf(paramInt));
    XulView localXulView;
    if (localWeakReference == null)
      localXulView = null;
    do
    {
      return localXulView;
      localXulView = (XulView)localWeakReference.get();
    }
    while (localXulView != null);
    this._viewMap.remove(Integer.valueOf(paramInt));
    return localXulView;
  }

  public int xulViewToId(XulView paramXulView)
  {
    int i = paramXulView.hashCode();
    this._viewMap.put(Integer.valueOf(i), paramXulView.getWeakReference());
    return i;
  }

  class PageInfo
  {
    long createTime;
    long firstResumedTime;
    int id;
    long maxDrawingDuration;
    long minDrawingDuration = 2147483647L;
    WeakReference<Object> page;
    int refreshCount;
    long refreshTime;
    long renderIsReadyTime;
    String status;
    long totalDrawingDuration;

    public PageInfo(Object paramInt, int arg3)
    {
      this.page = new WeakReference(paramInt);
      int i;
      this.id = i;
      this.status = "create";
      this.createTime = XulUtils.timestamp();
    }

    public long getRefreshTime()
    {
      long l = XulUtils.timestamp() - this.refreshTime;
      return System.currentTimeMillis() - l;
    }

    public void onPageRefreshed(long paramLong)
    {
      this.refreshCount = (1 + this.refreshCount);
      this.totalDrawingDuration = (paramLong + this.totalDrawingDuration);
      if (paramLong > this.maxDrawingDuration)
        this.maxDrawingDuration = paramLong;
      if (paramLong < this.minDrawingDuration)
        this.minDrawingDuration = paramLong;
      this.refreshTime = XulUtils.timestamp();
    }

    public void onPageRenderIsReady()
    {
      this.renderIsReadyTime = XulUtils.timestamp();
    }

    public void onResume()
    {
      this.status = "resumed";
      if (this.firstResumedTime == 0L)
        this.firstResumedTime = XulUtils.timestamp();
    }
  }

  private static abstract class UiOpRunnable
    implements Runnable
  {
    XulDebugMonitor.PageInfo _pageInfo;
    volatile boolean _result = false;
    Semaphore _sem;
    XulPage _xulPage;
    XulView _xulView;

    protected boolean _internalWaitFinish()
    {
      try
      {
        boolean bool1 = this._sem.tryAcquire(20L, TimeUnit.SECONDS);
        boolean bool2 = false;
        if (bool1)
        {
          boolean bool3 = this._result;
          bool2 = false;
          if (bool3)
            bool2 = true;
        }
        return bool2;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
      return false;
    }

    boolean beginExec()
    {
      return true;
    }

    boolean endExec()
    {
      return true;
    }

    protected abstract void exec(XulDebugMonitor.PageInfo paramPageInfo, XulPage paramXulPage, XulView paramXulView)
      throws Exception;

    public void run()
    {
      try
      {
        exec(this._pageInfo, this._xulPage, this._xulView);
        this._result = true;
        this._sem.release();
        return;
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          this._result = false;
        }
      }
    }

    void setCurrentPage(XulPage paramXulPage)
    {
      this._xulPage = paramXulPage;
    }

    void setCurrentPageInfo(XulDebugMonitor.PageInfo paramPageInfo)
    {
      this._pageInfo = paramPageInfo;
    }

    void setCurrentView(XulView paramXulView)
    {
      this._xulView = paramXulView;
      if (paramXulView != null)
        this._xulPage = paramXulView.getOwnerPage();
    }

    void setFinished()
    {
      this._result = true;
    }

    boolean start()
    {
      if (this._result)
        return true;
      this._sem = new Semaphore(0);
      XulDebugAdapter.postToMainLooper(this);
      return false;
    }

    public boolean waitFinish()
    {
      return (_internalWaitFinish()) && (endExec());
    }
  }

  private class XmlContentDumper extends XulArea.XulAreaIterator
  {
    private boolean _noChildren = false;
    private boolean _noProp = false;
    ArrayList<String> focusMode = new ArrayList();
    final XmlSerializer writer;

    XmlContentDumper(XmlSerializer arg2)
    {
      Object localObject;
      this.writer = localObject;
    }

    private void _writeItemHead(String paramString, XulAction paramXulAction, int paramInt)
      throws IOException
    {
      this.writer.startTag(null, paramString);
      String str1 = paramXulAction.getName();
      if (!TextUtils.isEmpty(str1))
        this.writer.attribute(null, "action", str1);
      String str2 = paramXulAction.getType();
      if (!TextUtils.isEmpty(str2))
        this.writer.attribute(null, "type", str2);
      String str3 = paramXulAction.getBinding();
      if (!TextUtils.isEmpty(str3))
        this.writer.attribute(null, "binding", str3);
      int i = paramXulAction.getPriority();
      this.writer.attribute(null, "P", Integer.toHexString(i));
      this.writer.attribute(null, "S", XulDebugMonitor.propStateFromId(paramInt));
      String str4 = paramXulAction.getStringValue();
      if (!TextUtils.isEmpty(str4))
        this.writer.text(str4);
    }

    private void _writeItemHead(String paramString, XulFocus paramXulFocus, int paramInt)
      throws IOException
    {
      this.writer.startTag(null, paramString);
      int i = paramXulFocus.getFocusMode();
      this.focusMode.clear();
      int j = (0xF000 & i) / 4096;
      this.focusMode.addAll(XulDebugMonitor.focusOrderMap[j]);
      if (!paramXulFocus.hasModeBits(16))
        this.focusMode.remove("dynamic");
      if (!paramXulFocus.hasModeBits(1))
        this.focusMode.remove("nearby");
      if (!paramXulFocus.hasModeBits(32))
        this.focusMode.remove("priority");
      if (paramXulFocus.hasModeBits(8))
        this.focusMode.add("focusable");
      if (paramXulFocus.hasModeBits(4))
      {
        this.focusMode.remove("focusable");
        this.focusMode.add("nofocus");
      }
      if (paramXulFocus.hasModeBits(2))
        this.focusMode.add("loop");
      if (paramXulFocus.hasModeBits(64))
        this.focusMode.add("wrap");
      if (!this.focusMode.isEmpty())
        this.writer.attribute(null, "mode", XulUtils.join(",", this.focusMode));
      if (paramXulFocus.isDefaultFocused())
        this.writer.attribute(null, "focused", "true");
      int k = paramXulFocus.getFocusPriority();
      if (k > 0)
        this.writer.attribute(null, "priority", String.valueOf(k));
    }

    private void _writeItemHead(String paramString, XulProp paramXulProp, int paramInt)
      throws IOException
    {
      this.writer.startTag(null, paramString);
      String str1 = paramXulProp.getName();
      if (!TextUtils.isEmpty(str1))
        this.writer.attribute(null, "name", str1);
      String str2 = paramXulProp.getBinding();
      if (!TextUtils.isEmpty(str2))
        this.writer.attribute(null, "binding", str2);
      int i = paramXulProp.getPriority();
      this.writer.attribute(null, "P", Integer.toHexString(i));
      this.writer.attribute(null, "S", XulDebugMonitor.propStateFromId(paramInt));
      String str3 = paramXulProp.getStringValue();
      if (!TextUtils.isEmpty(str3))
        this.writer.text(str3);
    }

    private void _writeItemHead(String paramString, XulView paramXulView)
      throws IOException
    {
      this.writer.startTag(null, paramString);
      int i = XulDebugMonitor.this.xulViewToId(paramXulView);
      this.writer.attribute(null, "id", String.valueOf(i));
      String str1 = paramXulView.getId();
      if (!TextUtils.isEmpty(str1))
        this.writer.attribute(null, "xulId", str1);
      String str2 = paramXulView.getType();
      if (!TextUtils.isEmpty(str2))
        this.writer.attribute(null, "type", str2);
      String[] arrayOfString = paramXulView.getAllClass();
      if (arrayOfString != null)
        this.writer.attribute(null, "class", XulUtils.join(",", arrayOfString));
      String str3 = paramXulView.getBinding();
      if (!TextUtils.isEmpty(str3))
        this.writer.attribute(null, "binding", str3);
      if (paramXulView.isFocused())
        this.writer.attribute(null, "focused", "true");
      if (!paramXulView.isEnabled())
        this.writer.attribute(null, "disabled", "true");
      if ((paramXulView instanceof XulArea))
      {
        XulView localXulView = ((XulArea)paramXulView).getDynamicFocus();
        if ((localXulView != null) && (!localXulView.isDiscarded()))
          this.writer.attribute(null, "dynamicFocus", String.valueOf(XulDebugMonitor.this.xulViewToId(localXulView)));
        if (!"massive".equals(str2))
          break label464;
        i4 = ((XulMassiveRender)paramXulView.getRender()).getDataItemNum();
        this.writer.attribute(null, "dataItemNum", String.valueOf(i4));
      }
      label464: 
      while (!"slider".equals(str2))
      {
        int i4;
        if (!this._noProp)
        {
          IXulPropIterator local1 = new IXulPropIterator()
          {
            public void onProp(XulProp paramAnonymousXulProp, int paramAnonymousInt)
            {
              try
              {
                if ((paramAnonymousXulProp instanceof XulAttr))
                {
                  XulDebugMonitor.XmlContentDumper.this._writeItemHead("attr", paramAnonymousXulProp, paramAnonymousInt);
                  XulDebugMonitor.XmlContentDumper.this._writeItemTail("attr");
                  return;
                }
                if ((paramAnonymousXulProp instanceof XulStyle))
                {
                  XulDebugMonitor.XmlContentDumper.this._writeItemHead("style", paramAnonymousXulProp, paramAnonymousInt);
                  XulDebugMonitor.XmlContentDumper.this._writeItemTail("style");
                  return;
                }
              }
              catch (IOException localIOException)
              {
                localIOException.printStackTrace();
                return;
              }
              if ((paramAnonymousXulProp instanceof XulData))
              {
                XulDebugMonitor.XmlContentDumper.this._writeItemHead("data", paramAnonymousXulProp, paramAnonymousInt);
                XulDebugMonitor.XmlContentDumper.this._writeItemTail("data");
                return;
              }
              if ((paramAnonymousXulProp instanceof XulAction))
              {
                XulDebugMonitor.XmlContentDumper.this._writeItemHead("action", (XulAction)paramAnonymousXulProp, -1);
                XulDebugMonitor.XmlContentDumper.this._writeItemTail("action");
                return;
              }
              if ((paramAnonymousXulProp instanceof XulFocus))
              {
                XulDebugMonitor.XmlContentDumper.this._writeItemHead("focus", (XulFocus)paramAnonymousXulProp, -1);
                XulDebugMonitor.XmlContentDumper.this._writeItemTail("focus");
              }
            }
          };
          paramXulView.eachProp(local1);
        }
        return;
        if (!(paramXulView instanceof XulLayout))
          break;
        XulPage localXulPage = paramXulView.getOwnerPage();
        int j = localXulPage.getDesignPageHeight();
        int k = localXulPage.getDesignPageWidth();
        int m = localXulPage.getPageHeight();
        int n = localXulPage.getPageWidth();
        XmlSerializer localXmlSerializer1 = this.writer;
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(k);
        arrayOfObject1[1] = Integer.valueOf(j);
        localXmlSerializer1.attribute(null, "design", String.format("%dx%d", arrayOfObject1));
        XmlSerializer localXmlSerializer2 = this.writer;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(n);
        arrayOfObject2[1] = Integer.valueOf(m);
        localXmlSerializer2.attribute(null, "device", String.format("%dx%d", arrayOfObject2));
        break;
      }
      XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)paramXulView.getRender();
      boolean bool = localXulSliderAreaRender.isVertical();
      int i1 = localXulSliderAreaRender.getScrollPos();
      int i2 = localXulSliderAreaRender.getScrollRange();
      int i3 = localXulSliderAreaRender.getScrollDelta();
      XmlSerializer localXmlSerializer3 = this.writer;
      Object[] arrayOfObject3 = new Object[4];
      if (bool);
      for (String str4 = "V"; ; str4 = "H")
      {
        arrayOfObject3[0] = str4;
        arrayOfObject3[1] = Integer.valueOf(i1);
        arrayOfObject3[2] = Integer.valueOf(i2);
        arrayOfObject3[3] = Integer.valueOf(i3);
        localXmlSerializer3.attribute(null, "scroll", String.format("%s %d/%d delta:%d", arrayOfObject3));
        break;
      }
    }

    private void _writeItemTail(String paramString)
      throws IOException
    {
      this.writer.endTag(null, paramString);
    }

    public void dumpXulLayout(XulLayout paramXulLayout)
    {
      try
      {
        _writeItemHead("layout", paramXulLayout);
        paramXulLayout.eachChild(this);
        _writeItemTail("layout");
        return;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }

    public void dumpXulView(XulView paramXulView)
    {
      if ((paramXulView instanceof XulArea))
        onXulArea(0, (XulArea)paramXulView);
      while (!(paramXulView instanceof XulItem))
        return;
      onXulItem(0, (XulItem)paramXulView);
    }

    public boolean onXulArea(int paramInt, XulArea paramXulArea)
    {
      try
      {
        _writeItemHead("area", paramXulArea);
        if (!this._noChildren)
          paramXulArea.eachChild(this);
        _writeItemTail("area");
        return true;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }

    public boolean onXulItem(int paramInt, XulItem paramXulItem)
    {
      try
      {
        _writeItemHead("item", paramXulItem);
        _writeItemTail("item");
        return true;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }

    public void setNoChildren(boolean paramBoolean)
    {
      this._noChildren = paramBoolean;
    }

    public void setNoProp(boolean paramBoolean)
    {
      this._noProp = paramBoolean;
    }
  }

  private static abstract class XulPageOpRunnable extends XulDebugMonitor.UiOpRunnable
  {
    private XulPageOpRunnable()
    {
      super();
    }

    boolean beginExec()
    {
      return this._xulPage != null;
    }
  }

  private static abstract class XulViewOpRunnable extends XulDebugMonitor.UiOpRunnable
  {
    private XulViewOpRunnable()
    {
      super();
    }

    boolean beginExec()
    {
      return this._xulView != null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.debug.XulDebugMonitor
 * JD-Core Version:    0.6.2
 */