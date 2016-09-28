package com.starcor.xul;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.starcor.xul.Graphics.BitmapTools;
import com.starcor.xul.Graphics.XulAnimationDrawable;
import com.starcor.xul.Graphics.XulBitmapDrawable;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Graphics.XulSVGDrawable;
import com.starcor.xul.Utils.XulBufferedInputStream;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.Utils.XulMemoryOutputStream;
import com.starcor.xul.Utils.XulSimpleStack;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import pl.droidsonroids.gif.GifDrawable;

public class XulWorker
{
  private static final float[] EMPTY_ROUND_RECT_RADIUS;
  private static final String TAG = XulWorker.class.getSimpleName();
  private static final String XUL_DOWNLOAD_WORKER = "XUL Download Worker";
  private static final String XUL_DRAWABLE_WORKER = "XUL Drawable Worker";
  private static final LinkedList<Pair<Long, Object>> _autoCleanList;
  private static XulSimpleStack<XulDownloadOutputBuffer> _downloadBufferList;
  static final Object _downloadWorkerWaitObj;
  static volatile Thread[] _downloadWorkers;
  private static final ArrayList<IXulWorkItemSource> _downloader;
  static final Object _drawableWorkerWaitObj;
  static volatile Thread[] _drawableWorkers;
  private static IXulWorkerHandler _handler;
  static acceptAllHostnameVerifier _hostVerifier;
  static IXulWorkItemSource _internalDrawableDownloadHandler;
  private static final ArrayList<DownloadItem> _pendingDownloadTask;
  private static final ArrayList<DrawableItem> _pendingDrawableTask;
  private static final XulCachedHashMap<String, ArrayList<DrawableItem>> _pendingScheduledImagedTask;
  private static final XulCachedHashMap<String, ArrayList<DownloadItem>> _scheduledDownloadTask;
  private static final XulCachedHashMap<String, ArrayList<DrawableItem>> _scheduledDrawableTask;
  private static SSLContext _sslCtx;
  private static volatile long _suspendTime;
  private static final WeakDrawableCache _weakCachedDrawable;
  private static WeakHashMap<Object, Object> _weakRefObjects;
  static AtomicInteger _workerCounter;

  static
  {
    EMPTY_ROUND_RECT_RADIUS = new float[8];
    _downloader = new ArrayList();
    _pendingDownloadTask = new ArrayList();
    _pendingDrawableTask = new ArrayList();
    _scheduledDownloadTask = new XulCachedHashMap();
    _scheduledDrawableTask = new XulCachedHashMap();
    _pendingScheduledImagedTask = new XulCachedHashMap();
    _weakCachedDrawable = new WeakDrawableCache(null);
    _downloadWorkers = new Thread[8];
    _drawableWorkers = new Thread[2];
    _drawableWorkerWaitObj = new Object();
    _downloadWorkerWaitObj = new Object();
    _autoCleanList = new LinkedList();
    _weakRefObjects = new WeakHashMap();
    _suspendTime = 0L;
    _internalDrawableDownloadHandler = new IXulWorkItemSource()
    {
      static
      {
        if (!XulWorker.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        assert ((paramAnonymousDownloadItem instanceof XulWorker.DrawableItem));
        return ((XulWorker.DrawableItem)paramAnonymousDownloadItem).__ownerDrawableHandler.getAppData(paramAnonymousDownloadItem, paramAnonymousString);
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        assert ((paramAnonymousDownloadItem instanceof XulWorker.DrawableItem));
        return ((XulWorker.DrawableItem)paramAnonymousDownloadItem).__ownerDrawableHandler.getAssets(paramAnonymousDownloadItem, paramAnonymousString);
      }

      public XulWorker.DownloadItem getDownloadItem()
      {
        return null;
      }

      public XulWorker.DrawableItem getDrawableItem()
      {
        return null;
      }

      public void onDownload(XulWorker.DownloadItem paramAnonymousDownloadItem, InputStream paramAnonymousInputStream)
      {
        assert ((paramAnonymousDownloadItem instanceof XulWorker.DrawableItem));
        XulWorker.DrawableItem localDrawableItem = (XulWorker.DrawableItem)paramAnonymousDownloadItem;
        localDrawableItem.__dataStream = paramAnonymousInputStream;
        XulWorker._addPendingDrawableItem(localDrawableItem);
      }

      public void onDrawableLoaded(XulWorker.DrawableItem paramAnonymousDrawableItem, XulDrawable paramAnonymousXulDrawable)
      {
      }

      public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        assert ((paramAnonymousDownloadItem instanceof XulWorker.DrawableItem));
        return ((XulWorker.DrawableItem)paramAnonymousDownloadItem).__ownerDrawableHandler.resolve(paramAnonymousDownloadItem, paramAnonymousString);
      }
    };
    _workerCounter = new AtomicInteger(0);
    _sslCtx = null;
    _hostVerifier = null;
    _downloadBufferList = new XulSimpleStack(16);
    ThreadGroup localThreadGroup = new ThreadGroup("XUL");
    for (int i = 0; i < _downloadWorkers.length; i++)
    {
      _downloadWorkers[i] = new Thread(localThreadGroup, "XUL Download Worker")
      {
        public void run()
        {
          XulWorker._downloadWorkerRun();
        }
      };
      _downloadWorkers[i].start();
    }
    for (int j = 0; j < _drawableWorkers.length; j++)
    {
      _drawableWorkers[j] = new Thread(localThreadGroup, "XUL Drawable Worker")
      {
        public void run()
        {
          XulWorker._drawableWorkerRun();
        }
      };
      _drawableWorkers[j].setPriority(3);
      _drawableWorkers[j].start();
    }
    try
    {
      _sslCtx = SSLContext.getInstance("SSL");
      SSLContext localSSLContext = _sslCtx;
      TrustManager[] arrayOfTrustManager = new TrustManager[1];
      arrayOfTrustManager[0] = new acceptAllTrustManager();
      localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
      _hostVerifier = new acceptAllHostnameVerifier(null);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private static void _addDrawableToCache(String paramString, XulDrawable paramXulDrawable)
  {
    if ((paramXulDrawable == null) || (!paramXulDrawable.cacheable()))
      return;
    WeakDrawableCache localWeakDrawableCache = _weakCachedDrawable;
    if (paramXulDrawable == null);
    while (true)
    {
      try
      {
        _weakCachedDrawable.remove(paramString);
        return;
      }
      finally
      {
      }
      _weakCachedDrawable.put(paramString, paramXulDrawable);
    }
  }

  private static void _addPendingDownloadTask(DownloadItem paramDownloadItem)
  {
    synchronized (_pendingDownloadTask)
    {
      _pendingDownloadTask.add(paramDownloadItem);
      return;
    }
  }

  static void _addPendingDrawableItem(DrawableItem paramDrawableItem)
  {
    synchronized (_pendingDrawableTask)
    {
      _pendingDrawableTask.add(paramDrawableItem);
      _notifyDrawableWorker();
      return;
    }
  }

  private static boolean _addToDownloadSchedule(DownloadItem paramDownloadItem)
  {
    while (true)
    {
      synchronized (_scheduledDownloadTask)
      {
        ArrayList localArrayList = (ArrayList)_scheduledDownloadTask.get(paramDownloadItem.url);
        if (localArrayList == null)
        {
          localArrayList = new ArrayList();
          _scheduledDownloadTask.put(paramDownloadItem.url, localArrayList);
        }
        if (!localArrayList.isEmpty())
        {
          bool = true;
          localArrayList.add(paramDownloadItem);
          return bool;
        }
      }
      boolean bool = false;
    }
  }

  private static boolean _addToImageSchedule(DrawableItem paramDrawableItem)
  {
    if (paramDrawableItem.reusable)
      return false;
    boolean bool;
    synchronized (_scheduledDrawableTask)
    {
      String str = paramDrawableItem.url;
      ArrayList localArrayList = (ArrayList)_scheduledDrawableTask.get(str);
      bool = false;
      if (localArrayList == null)
      {
        localArrayList = (ArrayList)_pendingScheduledImagedTask.remove(str);
        if (localArrayList == null)
          localArrayList = new ArrayList();
        _scheduledDrawableTask.put(str, localArrayList);
        localArrayList.add(paramDrawableItem);
        return bool;
      }
      if (!localArrayList.isEmpty())
        bool = true;
    }
    while (true)
    {
      break;
      bool = false;
    }
  }

  private static void _cleanAutoCleanList(long paramLong)
  {
    LinkedList localLinkedList = _autoCleanList;
    for (int i = 0; ; i++)
      try
      {
        if (i < _autoCleanList.size())
        {
          if (paramLong > ((Long)((Pair)_autoCleanList.get(i)).first).longValue())
          {
            _autoCleanList.remove(i);
            i++;
          }
        }
        else
          return;
      }
      finally
      {
      }
  }

  private static void _doDownload(_downloadContext param_downloadContext, DownloadItem paramDownloadItem)
  {
    _doDownload(param_downloadContext, paramDownloadItem, null);
  }

  private static void _doDownload(_downloadContext param_downloadContext, DownloadItem paramDownloadItem, XulDownloadParams paramXulDownloadParams)
  {
    Pattern localPattern1 = param_downloadContext.assetsPat;
    Pattern localPattern2 = param_downloadContext.appDataPat;
    byte[] arrayOfByte1 = param_downloadContext.downloadBuffer;
    Object localObject1 = paramDownloadItem.url;
    Object localObject2 = paramDownloadItem.__cacheKey;
    String[] arrayOfString = null;
    boolean bool1 = false;
    byte[] arrayOfByte2 = null;
    if (paramXulDownloadParams != null)
    {
      bool1 = paramXulDownloadParams.post;
      arrayOfByte2 = paramXulDownloadParams.postBody;
      arrayOfString = paramXulDownloadParams.extHeaders;
    }
    if (TextUtils.isEmpty((CharSequence)localObject1))
      return;
    if (((String)localObject1).startsWith("@color:"))
    {
      _notifyDownloadResult(paramDownloadItem, null);
      return;
    }
    while (true)
    {
      String str1 = _internalResolvePath(paramDownloadItem, (String)localObject1);
      if (TextUtils.isEmpty(str1))
        break;
      localObject1 = str1;
    }
    if (!((String)localObject1).equals(paramDownloadItem.__resolvedPath))
    {
      paramDownloadItem.__resolvedPath = ((String)localObject1);
      localObject2 = null;
    }
    if (TextUtils.isEmpty((CharSequence)localObject2))
    {
      localObject2 = _internalCalCacheKey(paramDownloadItem, (String)localObject1);
      paramDownloadItem.__cacheKey = ((String)localObject2);
    }
    if (TextUtils.isEmpty((CharSequence)localObject2))
      localObject2 = localObject1;
    Matcher localMatcher1 = localPattern1.matcher((CharSequence)localObject1);
    if (localMatcher1.matches())
    {
      String str5 = localMatcher1.group(1);
      String str6 = "assets-" + XulUtils.calMD5(str5);
      boolean bool2 = paramDownloadItem.isDirect;
      InputStream localInputStream3 = null;
      if (!bool2)
      {
        localInputStream3 = _internalLoadCachedData(str6);
        if (0 != 0)
          null.mark("cache[" + localInputStream3 + "]");
      }
      if (localInputStream3 == null)
      {
        localInputStream3 = _internalGetAssets(paramDownloadItem, str5);
        if ((localInputStream3 != null) && (!paramDownloadItem.noFileCache))
        {
          if (0 != 0)
            null.mark("assets[" + localInputStream3 + "]");
          _internalStoreCachedData(str6, localInputStream3);
          if (0 != 0)
            null.mark("load[" + localInputStream3 + "]");
        }
      }
      try
      {
        localInputStream3.reset();
        if (0 != 0)
        {
          null.mark("finish[" + localInputStream3 + "]");
          Log.d(TAG, null.toString());
        }
        _notifyDownloadResult(paramDownloadItem, localInputStream3);
        return;
      }
      catch (Exception localException4)
      {
        while (true)
          localException4.printStackTrace();
      }
    }
    Matcher localMatcher2 = localPattern2.matcher((CharSequence)localObject1);
    if (localMatcher2.matches())
    {
      _notifyDownloadResult(paramDownloadItem, _internalGetAppData(paramDownloadItem, localMatcher2.group(1)));
      return;
    }
    String str2 = XulUtils.calMD5((String)localObject2);
    if (!paramDownloadItem.isDirect)
    {
      InputStream localInputStream2 = _internalLoadCachedData(str2);
      if (localInputStream2 != null)
      {
        _notifyDownloadResult(paramDownloadItem, localInputStream2);
        return;
      }
    }
    HttpURLConnection localHttpURLConnection = null;
    try
    {
      URL localURL = new URL((String)localObject1);
      localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      localHttpURLConnection.setReadTimeout(8000);
      localHttpURLConnection.setConnectTimeout(4000);
      localHttpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
      if (arrayOfString != null)
      {
        int k = 0;
        int m = arrayOfString.length;
        while (k + 1 < m)
        {
          localHttpURLConnection.setRequestProperty(arrayOfString[(k + 0)], arrayOfString[(k + 1)]);
          k += 2;
        }
      }
      if (bool1)
      {
        localHttpURLConnection.setRequestMethod("POST");
        if (arrayOfByte2 != null)
        {
          localHttpURLConnection.setDoOutput(true);
          localHttpURLConnection.getOutputStream().write(arrayOfByte2);
        }
      }
      if ((localHttpURLConnection instanceof HttpsURLConnection))
      {
        HttpsURLConnection localHttpsURLConnection = (HttpsURLConnection)localHttpURLConnection;
        localHttpsURLConnection.setHostnameVerifier(_hostVerifier);
        if (_sslCtx != null)
          localHttpsURLConnection.setSSLSocketFactory(_sslCtx.getSocketFactory());
      }
      i = localHttpURLConnection.getHeaderFieldInt("Content-Length", -1);
      String str4 = localHttpURLConnection.getHeaderField("Content-Encoding");
      localObject3 = localHttpURLConnection.getInputStream();
      if ("gzip".equalsIgnoreCase(str4))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream((InputStream)localObject3);
        localXulDownloadOutputBuffer = obtainDownloadBuffer(32768);
        localObject3 = localGZIPInputStream;
        while (true)
        {
          j = ((InputStream)localObject3).read(arrayOfByte1);
          if (j <= 0)
            break;
          localXulDownloadOutputBuffer.write(arrayOfByte1, 0, j);
        }
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      String str3 = "Invalid url: " + (String)localObject1;
      if (paramXulDownloadParams != null)
      {
        paramXulDownloadParams.responseCode = 500;
        paramXulDownloadParams.responseMsg = str3;
      }
      if (localHttpURLConnection == null);
    }
    catch (Exception localException1)
    {
      try
      {
        do
        {
          Object localObject3;
          XulDownloadOutputBuffer localXulDownloadOutputBuffer;
          int j;
          do
            while (true)
            {
              localHttpURLConnection.disconnect();
              _notifyDownloadResult(paramDownloadItem, null);
              return;
              if (i <= 0)
                break;
              localXulDownloadOutputBuffer = obtainDownloadBuffer(Math.max(i, 1024));
            }
          while (j > 0);
          ((InputStream)localObject3).close();
          InputStream localInputStream1 = localXulDownloadOutputBuffer.toInputStream();
          if (paramXulDownloadParams != null)
          {
            paramXulDownloadParams.responseCode = localHttpURLConnection.getResponseCode();
            paramXulDownloadParams.responseMsg = localHttpURLConnection.getResponseMessage();
            paramXulDownloadParams.responseHeaders = localHttpURLConnection.getHeaderFields();
          }
          if (localInputStream1 != null)
          {
            _internalStoreCachedData(str2, localInputStream1);
            localInputStream1.reset();
            _notifyDownloadResult(paramDownloadItem, localInputStream1);
          }
          try
          {
            localHttpURLConnection.disconnect();
            return;
          }
          catch (Exception localException3)
          {
            localException3.printStackTrace();
            return;
          }
          localException1 = localException1;
          localException1.printStackTrace();
        }
        while (paramXulDownloadParams == null);
        paramXulDownloadParams.responseCode = 500;
        paramXulDownloadParams.responseMsg = localException1.getMessage();
      }
      catch (Exception localException2)
      {
        while (true)
        {
          localException2.printStackTrace();
          continue;
          int i = 8192;
        }
      }
    }
  }

  static void _downloadWorkerRun()
  {
    _downloadContext local_downloadContext = new _downloadContext();
    while (_downloadWorkers != null)
    {
      boolean bool = _workerCounter.compareAndSet(0, 1);
      DownloadItem localDownloadItem = _getPendingDownloadItem();
      if (bool)
      {
        long l = XulUtils.timestamp();
        _cleanAutoCleanList(l);
        BitmapTools.cleanRecycledBitmaps(l);
      }
      if (localDownloadItem == null)
      {
        if (bool)
        {
          _threadWait(50);
          _workerCounter.set(0);
        }
        else
        {
          _threadWait(2147483647);
        }
      }
      else
      {
        if (bool)
        {
          _workerCounter.set(0);
          _notifyDownloadWorker();
        }
        _doDownload(local_downloadContext, localDownloadItem);
      }
    }
  }

  static void _drawableWorkerRun()
  {
    Pattern localPattern1 = Pattern.compile("^.+\\.9(\\.png)?$", 2);
    Pattern localPattern2 = Pattern.compile("^.+\\.svg$", 2);
    Pattern localPattern3 = Pattern.compile("^.+\\.gif$", 2);
    Pattern localPattern4 = Pattern.compile("^.+\\.ani(\\.zip)?$", 2);
    Paint localPaint1 = new Paint();
    localPaint1.setFlags(3);
    localPaint1.setAntiAlias(true);
    localPaint1.setColor(-1);
    Paint localPaint2 = new Paint();
    localPaint2.setFlags(3);
    localPaint2.setAntiAlias(true);
    localPaint2.setColor(-1);
    localPaint2.setStyle(Paint.Style.FILL);
    localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    Paint localPaint3 = new Paint();
    localPaint3.setFlags(3);
    localPaint3.setAntiAlias(true);
    localPaint3.setColor(-1);
    localPaint3.setStyle(Paint.Style.FILL);
    localPaint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    Paint localPaint4 = new Paint();
    localPaint4.setFlags(3);
    localPaint4.setAntiAlias(true);
    localPaint4.setColor(-16777216);
    localPaint4.setStyle(Paint.Style.FILL);
    localPaint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
    Paint localPaint5 = new Paint();
    localPaint5.setFlags(3);
    localPaint5.setAntiAlias(true);
    localPaint5.setColor(-16777216);
    localPaint5.setStyle(Paint.Style.FILL);
    localPaint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    XulBufferedInputStream localXulBufferedInputStream = null;
    Canvas localCanvas = new Canvas();
    float[] arrayOfFloat1 = new float[2];
    float[] arrayOfFloat2 = new float[8];
    Rect localRect = new Rect();
    while (true)
      if (_drawableWorkers != null)
      {
        DrawableItem localDrawableItem = _getPendingDrawableItem();
        if (localDrawableItem == null)
        {
          _threadWait(2147483647);
        }
        else if (!_addToImageSchedule(localDrawableItem))
        {
          String str = localDrawableItem.url;
          if ((!TextUtils.isEmpty(str)) && (str.startsWith("@color:")))
          {
            int i2 = localDrawableItem.width;
            int i3 = localDrawableItem.height;
            label389: Object localObject7;
            String[] arrayOfString;
            int i4;
            float f6;
            float f7;
            if (i2 == 0)
              if (i3 == 0)
              {
                i3 = 1;
                i2 = i3;
                localObject7 = localDrawableItem._roundRectRadius;
                if (localObject7 == null)
                  localObject7 = EMPTY_ROUND_RECT_RADIUS;
                arrayOfString = str.substring(7).split(",");
                i4 = (int)XulUtils.tryParseHex(arrayOfString[0], -16777216L);
                f6 = localDrawableItem.scalarX;
                f7 = localDrawableItem.scalarY;
                if (arrayOfString.length != 3)
                  break label545;
                float[] arrayOfFloat6 = new float[2];
                arrayOfFloat6[0] = (f6 * XulUtils.tryParseFloat(arrayOfString[1], localObject7[0]));
                arrayOfFloat6[1] = (f7 * XulUtils.tryParseFloat(arrayOfString[2], localObject7[1]));
                localObject7 = arrayOfFloat6;
              }
            while (true)
            {
              _finishSchedule(localDrawableItem, XulDrawable.fromColor(i4, i2, i3, (float[])localObject7, localDrawableItem.url, str));
              break;
              i2 = i3;
              break label389;
              if (i3 != 0)
                break label389;
              i3 = i2;
              break label389;
              label545: if (arrayOfString.length == 5)
              {
                float[] arrayOfFloat5 = new float[8];
                arrayOfFloat5[0] = (f6 * XulUtils.tryParseFloat(arrayOfString[1], localObject7[0]));
                arrayOfFloat5[1] = (f7 * XulUtils.tryParseFloat(arrayOfString[1], localObject7[1]));
                arrayOfFloat5[2] = (f6 * XulUtils.tryParseFloat(arrayOfString[2], localObject7[2]));
                arrayOfFloat5[3] = (f7 * XulUtils.tryParseFloat(arrayOfString[2], localObject7[3]));
                arrayOfFloat5[4] = (f6 * XulUtils.tryParseFloat(arrayOfString[3], localObject7[4]));
                arrayOfFloat5[5] = (f7 * XulUtils.tryParseFloat(arrayOfString[3], localObject7[5]));
                arrayOfFloat5[6] = (f6 * XulUtils.tryParseFloat(arrayOfString[4], localObject7[6]));
                arrayOfFloat5[7] = (f7 * XulUtils.tryParseFloat(arrayOfString[4], localObject7[7]));
                localObject7 = arrayOfFloat5;
              }
              else if (arrayOfString.length == 9)
              {
                float[] arrayOfFloat4 = new float[8];
                arrayOfFloat4[0] = (f6 * XulUtils.tryParseFloat(arrayOfString[1], localObject7[0]));
                arrayOfFloat4[1] = (f7 * XulUtils.tryParseFloat(arrayOfString[2], localObject7[1]));
                arrayOfFloat4[2] = (f6 * XulUtils.tryParseFloat(arrayOfString[3], localObject7[2]));
                arrayOfFloat4[3] = (f7 * XulUtils.tryParseFloat(arrayOfString[4], localObject7[3]));
                arrayOfFloat4[4] = (f6 * XulUtils.tryParseFloat(arrayOfString[5], localObject7[4]));
                arrayOfFloat4[5] = (f7 * XulUtils.tryParseFloat(arrayOfString[6], localObject7[5]));
                arrayOfFloat4[6] = (f6 * XulUtils.tryParseFloat(arrayOfString[7], localObject7[6]));
                arrayOfFloat4[7] = (f7 * XulUtils.tryParseFloat(arrayOfString[8], localObject7[7]));
                localObject7 = arrayOfFloat4;
              }
            }
          }
          _drawableWorkerWaitSuspend();
          XulDrawable localXulDrawable1 = loadDrawableFromCache(str);
          if (localXulDrawable1 != null)
          {
            _finishSchedule(localDrawableItem, localXulDrawable1);
          }
          else
          {
            Object localObject1 = localDrawableItem.__dataStream;
            if (localObject1 != null)
            {
              if (localXulBufferedInputStream != null)
                break label1010;
              localXulBufferedInputStream = new XulBufferedInputStream((InputStream)localObject1, 65536);
              localObject1 = localXulBufferedInputStream;
            }
            while (true)
            {
              _drawableWorkerWaitSuspend();
              Matcher localMatcher1 = localPattern2.matcher(localDrawableItem.__resolvedPath);
              if ((localMatcher1 == null) || (!localMatcher1.matches()))
                break label1034;
              int n = localDrawableItem.width;
              int i1 = localDrawableItem.height;
              _finishSchedule(localDrawableItem, XulSVGDrawable.buildSVGDrawable((InputStream)localObject1, localDrawableItem.url, str, n, i1));
              break;
              try
              {
                label1010: localXulBufferedInputStream.resetInputStream((InputStream)localObject1);
                localObject1 = localXulBufferedInputStream;
              }
              catch (IOException localIOException)
              {
                localIOException.printStackTrace();
              }
            }
            label1034: _drawableWorkerWaitSuspend();
            if (_isGIF(localPattern3, localDrawableItem.__resolvedPath, (InputStream)localObject1))
            {
              int k = localDrawableItem.width;
              int m = localDrawableItem.height;
              Object localObject6 = null;
              if (localObject1 != null);
              try
              {
                GifDrawable localGifDrawable = new GifDrawable((InputStream)localObject1);
                XulDrawable localXulDrawable2 = XulAnimationDrawable.buildAnimation(localGifDrawable, localDrawableItem.url, str, k, m);
                localObject6 = localXulDrawable2;
                _finishSchedule(localDrawableItem, localObject6);
              }
              catch (Exception localException4)
              {
                while (true)
                {
                  localException4.printStackTrace();
                  localObject6 = null;
                }
              }
            }
            else
            {
              _drawableWorkerWaitSuspend();
              Matcher localMatcher2 = localPattern4.matcher(localDrawableItem.__resolvedPath);
              if ((localMatcher2 != null) && (localMatcher2.matches()))
              {
                _finishSchedule(localDrawableItem, XulAnimationDrawable.buildAnimation((InputStream)localObject1, localDrawableItem.url, str));
              }
              else
              {
                _drawableWorkerWaitSuspend();
                Matcher localMatcher3 = localPattern1.matcher(localDrawableItem.__resolvedPath);
                if ((localMatcher3 != null) && (localMatcher3.matches()))
                {
                  Object localObject5 = null;
                  if (localObject1 != null);
                  try
                  {
                    Bitmap localBitmap4 = BitmapTools.decodeStream((InputStream)localObject1, Bitmap.Config.ARGB_8888, 0, 0);
                    localObject5 = localBitmap4;
                    _finishSchedule(localDrawableItem, XulDrawable.fromNinePitchBitmap(localObject5, localDrawableItem.url, str));
                  }
                  catch (Exception localException3)
                  {
                    while (true)
                    {
                      localException3.printStackTrace();
                      localObject5 = null;
                    }
                  }
                }
                else
                {
                  _drawableWorkerWaitSuspend();
                  Object localObject2 = null;
                  if (localObject1 != null);
                  try
                  {
                    boolean bool = _internalPreloadImage(localDrawableItem, localRect);
                    localObject2 = null;
                    if (bool);
                    Bitmap localBitmap3;
                    for (localObject2 = BitmapTools.decodeStream((InputStream)localObject1, Bitmap.Config.ARGB_8888, XulUtils.calRectWidth(localRect), XulUtils.calRectHeight(localRect), localDrawableItem.target_width, localDrawableItem.target_height); ; localObject2 = localBitmap3)
                    {
                      _drawableWorkerWaitSuspend();
                      Bitmap localBitmap2 = _internalPreprocessImage(localDrawableItem, (Bitmap)localObject2);
                      if ((localBitmap2 != null) && (localBitmap2 != localObject2))
                      {
                        BitmapTools.recycleBitmap((Bitmap)localObject2);
                        localObject2 = localBitmap2;
                      }
                      _drawableWorkerWaitSuspend();
                      if (localObject2 != null)
                        break label1449;
                      if (localDrawableItem.__retryCounter >= 1)
                        break label1736;
                      _removeAndHangupDrawableSchedule(localDrawableItem);
                      localDrawableItem.__retryCounter = (1 + localDrawableItem.__retryCounter);
                      localDrawableItem.isDirect = true;
                      _addPendingDownloadTask(localDrawableItem);
                      _notifyDownloadWorker();
                      break;
                      localBitmap3 = BitmapTools.decodeStream((InputStream)localObject1, Bitmap.Config.ARGB_8888, localDrawableItem.width, localDrawableItem.height, localDrawableItem.target_width, localDrawableItem.target_height);
                    }
                  }
                  catch (Exception localException2)
                  {
                    while (true)
                      localException2.printStackTrace();
                    label1449: Object localObject3;
                    float f1;
                    label1661: Object localObject4;
                    if (localDrawableItem._roundRectRadius != null)
                    {
                      localObject3 = localDrawableItem._roundRectRadius;
                      f1 = localDrawableItem.shadowSize;
                      if ((localDrawableItem.height == 0) && (localDrawableItem.width == 0))
                      {
                        float f2 = 1.0F;
                        float f3 = 1.0F;
                        if (localDrawableItem.target_height != 0)
                          if (localDrawableItem.target_width != 0)
                          {
                            f2 = ((Bitmap)localObject2).getWidth() / localDrawableItem.target_width;
                            f3 = ((Bitmap)localObject2).getHeight() / localDrawableItem.target_height;
                            if (localObject3.length != 2)
                              break label1661;
                          }
                        for (float[] arrayOfFloat3 = arrayOfFloat1; ; arrayOfFloat3 = arrayOfFloat2)
                        {
                          int i = 0;
                          int j = localObject3.length;
                          while (i < j)
                          {
                            float f4 = localObject3[i];
                            float f5 = localObject3[(i + 1)];
                            arrayOfFloat3[i] = (f4 * f2);
                            arrayOfFloat3[(i + 1)] = (f5 * f3);
                            i += 2;
                          }
                          f3 = ((Bitmap)localObject2).getHeight() / localDrawableItem.target_height;
                          f2 = f3;
                          break;
                          if (localDrawableItem.target_width == 0)
                            break;
                          f3 = ((Bitmap)localObject2).getWidth() / localDrawableItem.target_width;
                          f2 = f3;
                          break;
                        }
                        localObject3 = arrayOfFloat3;
                        f1 *= Math.max(f2, f3);
                      }
                      localObject4 = localObject2;
                      if (f1 <= 0.5D)
                        break label1756;
                    }
                    try
                    {
                      label1736: label1756: Bitmap localBitmap1;
                      for (localObject2 = toRoundCornerShadowBitmap(localCanvas, localPaint1, localPaint2, localPaint5, localPaint4, (Bitmap)localObject2, (float[])localObject3, f1, localDrawableItem.shadowColor); ; localObject2 = localBitmap1)
                      {
                        if (localObject2 != localObject4)
                          BitmapTools.recycleBitmap(localObject4);
                        _finishSchedule(localDrawableItem, XulDrawable.fromBitmap((Bitmap)localObject2, localDrawableItem.url, str));
                        break;
                        localBitmap1 = toRoundCornerBitmap(localCanvas, localPaint3, (Bitmap)localObject2, (float[])localObject3);
                      }
                    }
                    catch (Exception localException1)
                    {
                      while (true)
                        localException1.printStackTrace();
                    }
                  }
                }
              }
            }
          }
        }
      }
  }

  static void _drawableWorkerWaitSuspend()
  {
    while (isDrawableWorkersSuspended())
      _threadWait(10);
  }

  private static void _finishSchedule(DownloadItem paramDownloadItem, InputStream paramInputStream)
  {
    while (true)
    {
      DownloadItem localDownloadItem;
      synchronized (_scheduledDownloadTask)
      {
        ArrayList localArrayList = (ArrayList)_scheduledDownloadTask.remove(paramDownloadItem.url);
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext())
          return;
        localDownloadItem = (DownloadItem)localIterator.next();
      }
      try
      {
        localDownloadItem.__ownerDownloadHandler.onDownload(localDownloadItem, paramInputStream);
        localDownloadItem.__ownerDownloadHandler = null;
        continue;
        localObject = finally;
        throw localObject;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  private static void _finishSchedule(DrawableItem paramDrawableItem, XulDrawable paramXulDrawable)
  {
    if (paramDrawableItem.reusable)
      if (paramDrawableItem.__dataStream == null);
    while (true)
    {
      try
      {
        paramDrawableItem.__dataStream.close();
        paramDrawableItem.__dataStream = null;
      }
      catch (IOException localIOException2)
      {
        try
        {
          paramDrawableItem.__ownerDrawableHandler.onDrawableLoaded(paramDrawableItem, paramXulDrawable);
          paramDrawableItem.__ownerDownloadHandler = null;
          paramDrawableItem.__ownerDrawableHandler = null;
          return;
          localIOException2 = localIOException2;
          localIOException2.printStackTrace();
          continue;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          continue;
        }
      }
      ArrayList localArrayList;
      synchronized (_scheduledDrawableTask)
      {
        localArrayList = (ArrayList)_scheduledDrawableTask.remove(paramDrawableItem.url);
        _addDrawableToCache(paramDrawableItem.url, paramXulDrawable);
        if (paramDrawableItem.__dataStream == null);
      }
      try
      {
        paramDrawableItem.__dataStream.close();
        paramDrawableItem.__dataStream = null;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext())
          continue;
        localDrawableItem = (DrawableItem)localIterator.next();
      }
      catch (IOException localIOException1)
      {
        try
        {
          while (true)
          {
            DrawableItem localDrawableItem;
            localDrawableItem.__ownerDrawableHandler.onDrawableLoaded(localDrawableItem, paramXulDrawable);
            localDrawableItem.__ownerDownloadHandler = null;
            localDrawableItem.__ownerDrawableHandler = null;
          }
          localObject = finally;
          throw localObject;
          localIOException1 = localIOException1;
          localIOException1.printStackTrace();
        }
        catch (Exception localException1)
        {
          while (true)
            localException1.printStackTrace();
        }
      }
    }
  }

  // ERROR //
  static DownloadItem _getPendingDownloadItem()
  {
    // Byte code:
    //   0: getstatic 79	com/starcor/xul/XulWorker:_pendingDownloadTask	Ljava/util/ArrayList;
    //   3: astore_1
    //   4: aload_1
    //   5: monitorenter
    //   6: getstatic 79	com/starcor/xul/XulWorker:_pendingDownloadTask	Ljava/util/ArrayList;
    //   9: invokevirtual 231	java/util/ArrayList:isEmpty	()Z
    //   12: ifne +20 -> 32
    //   15: getstatic 79	com/starcor/xul/XulWorker:_pendingDownloadTask	Ljava/util/ArrayList;
    //   18: iconst_0
    //   19: invokevirtual 854	java/util/ArrayList:remove	(I)Ljava/lang/Object;
    //   22: checkcast 218	com/starcor/xul/XulWorker$DownloadItem
    //   25: astore 17
    //   27: aload_1
    //   28: monitorexit
    //   29: aload 17
    //   31: areturn
    //   32: aload_1
    //   33: monitorexit
    //   34: getstatic 81	com/starcor/xul/XulWorker:_pendingDrawableTask	Ljava/util/ArrayList;
    //   37: astore_3
    //   38: aload_3
    //   39: monitorenter
    //   40: getstatic 81	com/starcor/xul/XulWorker:_pendingDrawableTask	Ljava/util/ArrayList;
    //   43: invokevirtual 855	java/util/ArrayList:size	()I
    //   46: iconst_5
    //   47: if_icmple +19 -> 66
    //   50: aload_3
    //   51: monitorexit
    //   52: aconst_null
    //   53: areturn
    //   54: astore_2
    //   55: aload_1
    //   56: monitorexit
    //   57: aload_2
    //   58: athrow
    //   59: astore_0
    //   60: aload_0
    //   61: invokevirtual 188	java/lang/Exception:printStackTrace	()V
    //   64: aconst_null
    //   65: areturn
    //   66: aload_3
    //   67: monitorexit
    //   68: iconst_0
    //   69: istore 5
    //   71: getstatic 77	com/starcor/xul/XulWorker:_downloader	Ljava/util/ArrayList;
    //   74: astore 6
    //   76: aload 6
    //   78: monitorenter
    //   79: iload 5
    //   81: getstatic 77	com/starcor/xul/XulWorker:_downloader	Ljava/util/ArrayList;
    //   84: invokevirtual 855	java/util/ArrayList:size	()I
    //   87: if_icmplt +49 -> 136
    //   90: aload 6
    //   92: monitorexit
    //   93: iconst_0
    //   94: istore 12
    //   96: getstatic 77	com/starcor/xul/XulWorker:_downloader	Ljava/util/ArrayList;
    //   99: astore 13
    //   101: aload 13
    //   103: monitorenter
    //   104: iload 12
    //   106: getstatic 77	com/starcor/xul/XulWorker:_downloader	Ljava/util/ArrayList;
    //   109: invokevirtual 855	java/util/ArrayList:size	()I
    //   112: if_icmplt +163 -> 275
    //   115: aload 13
    //   117: monitorexit
    //   118: goto -54 -> 64
    //   121: astore 14
    //   123: aload 13
    //   125: monitorexit
    //   126: aload 14
    //   128: athrow
    //   129: astore 4
    //   131: aload_3
    //   132: monitorexit
    //   133: aload 4
    //   135: athrow
    //   136: getstatic 77	com/starcor/xul/XulWorker:_downloader	Ljava/util/ArrayList;
    //   139: iload 5
    //   141: invokevirtual 856	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   144: checkcast 841	com/starcor/xul/XulWorker$IXulWorkItemSource
    //   147: astore 8
    //   149: aload 6
    //   151: monitorexit
    //   152: aload 8
    //   154: invokeinterface 859 1 0
    //   159: astore 9
    //   161: aload 9
    //   163: ifnull +88 -> 251
    //   166: aload 9
    //   168: aload 9
    //   170: aload 9
    //   172: getfield 240	com/starcor/xul/XulWorker$DrawableItem:url	Ljava/lang/String;
    //   175: invokestatic 329	com/starcor/xul/XulWorker:_internalCalCacheKey	(Lcom/starcor/xul/XulWorker$DownloadItem;Ljava/lang/String;)Ljava/lang/String;
    //   178: putfield 860	com/starcor/xul/XulWorker$DrawableItem:__cacheKey	Ljava/lang/String;
    //   181: aload 9
    //   183: getstatic 125	com/starcor/xul/XulWorker:_internalDrawableDownloadHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   186: putfield 851	com/starcor/xul/XulWorker$DrawableItem:__ownerDownloadHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   189: aload 9
    //   191: aload 8
    //   193: putfield 847	com/starcor/xul/XulWorker$DrawableItem:__ownerDrawableHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   196: aload 9
    //   198: getfield 239	com/starcor/xul/XulWorker$DrawableItem:reusable	Z
    //   201: ifne +120 -> 321
    //   204: aload 9
    //   206: getfield 240	com/starcor/xul/XulWorker$DrawableItem:url	Ljava/lang/String;
    //   209: invokestatic 702	com/starcor/xul/XulWorker:loadDrawableFromCache	(Ljava/lang/String;)Lcom/starcor/xul/Graphics/XulDrawable;
    //   212: astore 10
    //   214: aload 10
    //   216: ifnull +105 -> 321
    //   219: aload 9
    //   221: getfield 847	com/starcor/xul/XulWorker$DrawableItem:__ownerDrawableHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   224: aload 9
    //   226: aload 10
    //   228: invokeinterface 850 3 0
    //   233: aload 9
    //   235: aconst_null
    //   236: putfield 847	com/starcor/xul/XulWorker$DrawableItem:__ownerDrawableHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   239: aload 9
    //   241: aconst_null
    //   242: putfield 851	com/starcor/xul/XulWorker$DrawableItem:__ownerDownloadHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   245: aload 9
    //   247: aconst_null
    //   248: putfield 706	com/starcor/xul/XulWorker$DrawableItem:__dataStream	Ljava/io/InputStream;
    //   251: iinc 5 1
    //   254: goto -183 -> 71
    //   257: astore 7
    //   259: aload 6
    //   261: monitorexit
    //   262: aload 7
    //   264: athrow
    //   265: astore 11
    //   267: aload 11
    //   269: invokevirtual 188	java/lang/Exception:printStackTrace	()V
    //   272: goto -39 -> 233
    //   275: getstatic 77	com/starcor/xul/XulWorker:_downloader	Ljava/util/ArrayList;
    //   278: iload 12
    //   280: invokevirtual 856	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   283: checkcast 841	com/starcor/xul/XulWorker$IXulWorkItemSource
    //   286: astore 15
    //   288: aload 13
    //   290: monitorexit
    //   291: aload 15
    //   293: invokeinterface 863 1 0
    //   298: astore 16
    //   300: aload 16
    //   302: ifnull +13 -> 315
    //   305: aload 16
    //   307: aload 15
    //   309: putfield 839	com/starcor/xul/XulWorker$DownloadItem:__ownerDownloadHandler	Lcom/starcor/xul/XulWorker$IXulWorkItemSource;
    //   312: aload 16
    //   314: areturn
    //   315: iinc 12 1
    //   318: goto -222 -> 96
    //   321: aload 9
    //   323: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   6	29	54	finally
    //   32	34	54	finally
    //   55	57	54	finally
    //   0	6	59	java/lang/Exception
    //   34	40	59	java/lang/Exception
    //   57	59	59	java/lang/Exception
    //   71	79	59	java/lang/Exception
    //   96	104	59	java/lang/Exception
    //   126	129	59	java/lang/Exception
    //   133	136	59	java/lang/Exception
    //   152	161	59	java/lang/Exception
    //   166	214	59	java/lang/Exception
    //   233	251	59	java/lang/Exception
    //   262	265	59	java/lang/Exception
    //   267	272	59	java/lang/Exception
    //   291	300	59	java/lang/Exception
    //   305	312	59	java/lang/Exception
    //   104	118	121	finally
    //   123	126	121	finally
    //   275	291	121	finally
    //   40	52	129	finally
    //   66	68	129	finally
    //   131	133	129	finally
    //   79	93	257	finally
    //   136	152	257	finally
    //   259	262	257	finally
    //   219	233	265	java/lang/Exception
  }

  static DrawableItem _getPendingDrawableItem()
  {
    synchronized (_pendingDrawableTask)
    {
      if (_pendingDrawableTask.isEmpty())
        return null;
      DrawableItem localDrawableItem = (DrawableItem)_pendingDrawableTask.remove(0);
      return localDrawableItem;
    }
  }

  private static String _internalCalCacheKey(DownloadItem paramDownloadItem, String paramString)
  {
    if (_handler == null)
      return null;
    try
    {
      String str = _handler.calCacheKey(paramString);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static InputStream _internalGetAppData(DownloadItem paramDownloadItem, String paramString)
  {
    try
    {
      InputStream localInputStream2 = paramDownloadItem.__ownerDownloadHandler.getAppData(paramDownloadItem, paramString);
      localObject = localInputStream2;
      if ((localObject != null) || (_handler == null));
    }
    catch (Exception localException1)
    {
      try
      {
        InputStream localInputStream1 = _handler.getAppData(paramString);
        Object localObject = localInputStream1;
        if (waitPendingStream((InputStream)localObject))
        {
          return localObject;
          localException1 = localException1;
          localException1.printStackTrace();
          localObject = null;
        }
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }
    return null;
  }

  private static InputStream _internalGetAssets(DownloadItem paramDownloadItem, String paramString)
  {
    try
    {
      InputStream localInputStream2 = paramDownloadItem.__ownerDownloadHandler.getAssets(paramDownloadItem, paramString);
      localObject = localInputStream2;
      if ((localObject != null) || (_handler == null));
    }
    catch (Exception localException1)
    {
      Object localObject;
      do
        try
        {
          InputStream localInputStream1 = _handler.getAssets(paramString);
          localObject = localInputStream1;
          return localObject;
          localException1 = localException1;
          localException1.printStackTrace();
          localObject = null;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
      while (waitPendingStream((InputStream)localObject));
    }
    return null;
  }

  private static InputStream _internalLoadCachedData(String paramString)
  {
    if (_handler == null)
      return null;
    try
    {
      InputStream localInputStream = _handler.loadCachedData(paramString);
      return localInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static boolean _internalPreloadImage(DrawableItem paramDrawableItem, Rect paramRect)
  {
    if (_handler == null)
      return false;
    try
    {
      boolean bool = _handler.preloadImage(paramDrawableItem, paramRect);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private static Bitmap _internalPreprocessImage(DrawableItem paramDrawableItem, Bitmap paramBitmap)
  {
    if (_handler == null)
      return null;
    try
    {
      Bitmap localBitmap = _handler.preprocessImage(paramDrawableItem, paramBitmap);
      return localBitmap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static String _internalResolvePath(DownloadItem paramDownloadItem, String paramString)
  {
    try
    {
      String str2 = paramDownloadItem.__ownerDownloadHandler.resolve(paramDownloadItem, paramString);
      if (str2 != null)
        return str2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        if (_handler == null)
          return null;
        String str1 = _handler.resolvePath(paramDownloadItem, paramString);
        return str1;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    return null;
  }

  private static boolean _internalStoreCachedData(String paramString, InputStream paramInputStream)
  {
    if (_handler == null)
      return false;
    try
    {
      boolean bool = _handler.storeCachedData(paramString, paramInputStream);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  static boolean _isGIF(Pattern paramPattern, String paramString, InputStream paramInputStream)
  {
    Matcher localMatcher = paramPattern.matcher(paramString);
    if ((localMatcher != null) && (localMatcher.matches()))
      return true;
    if ((paramInputStream != null) && (paramInputStream.markSupported()))
      try
      {
        paramInputStream.mark(64);
        if ((paramInputStream.read() == 71) && (paramInputStream.read() == 73) && (paramInputStream.read() == 70) && (paramInputStream.read() == 56) && (paramInputStream.read() == 57) && (paramInputStream.read() == 97))
        {
          paramInputStream.reset();
          return true;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    while (true)
    {
      return false;
      paramInputStream.reset();
    }
  }

  static void _notifyAllDownloadWorker()
  {
    try
    {
      synchronized (_downloadWorkerWaitObj)
      {
        _downloadWorkerWaitObj.notifyAll();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  static void _notifyAllDrawableWorker()
  {
    try
    {
      synchronized (_drawableWorkerWaitObj)
      {
        _drawableWorkerWaitObj.notifyAll();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  static void _notifyDownloadResult(DownloadItem paramDownloadItem, InputStream paramInputStream)
  {
    try
    {
      paramDownloadItem.__ownerDownloadHandler.onDownload(paramDownloadItem, paramInputStream);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  static void _notifyDownloadWorker()
  {
    try
    {
      synchronized (_downloadWorkerWaitObj)
      {
        _downloadWorkerWaitObj.notify();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  static void _notifyDrawableWorker()
  {
    try
    {
      synchronized (_drawableWorkerWaitObj)
      {
        _drawableWorkerWaitObj.notify();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static void _removeAndHangupDrawableSchedule(DrawableItem paramDrawableItem)
  {
    synchronized (_scheduledDrawableTask)
    {
      String str = paramDrawableItem.url;
      ArrayList localArrayList = (ArrayList)_scheduledDrawableTask.remove(str);
      if (localArrayList == null)
        return;
      localArrayList.remove(paramDrawableItem);
      _pendingScheduledImagedTask.put(str, localArrayList);
      return;
    }
  }

  // ERROR //
  static void _threadWait(int paramInt)
  {
    // Byte code:
    //   0: invokestatic 923	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   3: invokevirtual 926	java/lang/Thread:getName	()Ljava/lang/String;
    //   6: astore_2
    //   7: ldc 11
    //   9: aload_2
    //   10: invokevirtual 326	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   13: ifeq +22 -> 35
    //   16: getstatic 108	com/starcor/xul/XulWorker:_downloadWorkerWaitObj	Ljava/lang/Object;
    //   19: astore 5
    //   21: aload 5
    //   23: monitorenter
    //   24: getstatic 108	com/starcor/xul/XulWorker:_downloadWorkerWaitObj	Ljava/lang/Object;
    //   27: iload_0
    //   28: i2l
    //   29: invokevirtual 929	java/lang/Object:wait	(J)V
    //   32: aload 5
    //   34: monitorexit
    //   35: ldc 14
    //   37: aload_2
    //   38: invokevirtual 326	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   41: ifeq +19 -> 60
    //   44: getstatic 106	com/starcor/xul/XulWorker:_drawableWorkerWaitObj	Ljava/lang/Object;
    //   47: astore_3
    //   48: aload_3
    //   49: monitorenter
    //   50: getstatic 106	com/starcor/xul/XulWorker:_drawableWorkerWaitObj	Ljava/lang/Object;
    //   53: iload_0
    //   54: i2l
    //   55: invokevirtual 929	java/lang/Object:wait	(J)V
    //   58: aload_3
    //   59: monitorexit
    //   60: return
    //   61: astore 6
    //   63: aload 5
    //   65: monitorexit
    //   66: aload 6
    //   68: athrow
    //   69: astore_1
    //   70: aload_1
    //   71: invokevirtual 188	java/lang/Exception:printStackTrace	()V
    //   74: return
    //   75: astore 4
    //   77: aload_3
    //   78: monitorexit
    //   79: aload 4
    //   81: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   24	35	61	finally
    //   63	66	61	finally
    //   0	24	69	java/lang/Exception
    //   35	50	69	java/lang/Exception
    //   66	69	69	java/lang/Exception
    //   79	82	69	java/lang/Exception
    //   50	60	75	finally
    //   77	79	75	finally
  }

  public static void addDrawableToCache(String paramString, XulDrawable paramXulDrawable)
  {
    addDrawableToCache(paramString, paramXulDrawable, 5000);
  }

  public static void addDrawableToCache(String paramString, XulDrawable paramXulDrawable, int paramInt)
  {
    _addDrawableToCache(paramString, paramXulDrawable);
    synchronized (_autoCleanList)
    {
      _autoCleanList.add(Pair.create(Long.valueOf(paramInt + XulUtils.timestamp()), paramXulDrawable));
      return;
    }
  }

  public static void addDrawableToCache(String paramString, XulDrawable paramXulDrawable, Object paramObject)
  {
    _addDrawableToCache(paramString, paramXulDrawable);
    _weakRefObjects.put(paramObject, paramXulDrawable);
  }

  public static void clearDrawableCachePermanently()
  {
    synchronized (_weakCachedDrawable)
    {
      _weakCachedDrawable.clear();
      return;
    }
  }

  public static boolean isDrawableCached(String paramString)
  {
    synchronized (_weakCachedDrawable)
    {
      boolean bool = _weakCachedDrawable.containsKey(paramString);
      return bool;
    }
  }

  private static boolean isDrawableWorkersSuspended()
  {
    if ((_suspendTime > 0L) && (XulUtils.timestamp() >= _suspendTime))
      _suspendTime = 0L;
    while (_suspendTime <= 0L)
      return false;
    return true;
  }

  public static InputStream loadData(String paramString, IXulWorkItemSource paramIXulWorkItemSource, boolean paramBoolean, String[] paramArrayOfString)
  {
    _syncDownloadItem local_syncDownloadItem = new _syncDownloadItem(paramString, paramIXulWorkItemSource);
    local_syncDownloadItem.noFileCache = paramBoolean;
    local_syncDownloadItem.isDirect = paramBoolean;
    _doDownload(new _downloadContext(), local_syncDownloadItem, new XulDownloadParams(false, null, paramArrayOfString));
    return local_syncDownloadItem._result;
  }

  public static InputStream loadData(String paramString, IXulWorkItemSource paramIXulWorkItemSource, String[] paramArrayOfString)
  {
    return loadData(paramString, paramIXulWorkItemSource, false, paramArrayOfString);
  }

  public static InputStream loadData(String paramString, boolean paramBoolean, XulDownloadParams paramXulDownloadParams)
  {
    _syncDownloadItem local_syncDownloadItem = new _syncDownloadItem(paramString, null);
    local_syncDownloadItem.noFileCache = paramBoolean;
    local_syncDownloadItem.isDirect = paramBoolean;
    _doDownload(new _downloadContext(), local_syncDownloadItem, paramXulDownloadParams);
    return local_syncDownloadItem._result;
  }

  public static InputStream loadData(String paramString, boolean paramBoolean, String[] paramArrayOfString)
  {
    return loadData(paramString, null, paramBoolean, paramArrayOfString);
  }

  public static InputStream loadData(String paramString, String[] paramArrayOfString)
  {
    return loadData(paramString, false, paramArrayOfString);
  }

  public static XulDrawable loadDrawableFromCache(String paramString)
  {
    XulDrawable localXulDrawable = null;
    if (0 == 0)
      synchronized (_weakCachedDrawable)
      {
        localXulDrawable = _weakCachedDrawable.get(paramString);
        if ((localXulDrawable != null) && (localXulDrawable.isRecycled()))
        {
          _weakCachedDrawable.remove(paramString);
          return null;
        }
      }
    return localXulDrawable;
  }

  public static XulDownloadOutputBuffer obtainDownloadBuffer(int paramInt)
  {
    try
    {
      XulDownloadOutputBuffer localXulDownloadOutputBuffer = (XulDownloadOutputBuffer)_downloadBufferList.pop();
      if (localXulDownloadOutputBuffer == null)
        localXulDownloadOutputBuffer = new XulDownloadOutputBuffer(paramInt);
      while (true)
      {
        return localXulDownloadOutputBuffer;
        localXulDownloadOutputBuffer.reset(paramInt);
      }
    }
    finally
    {
    }
  }

  public static InputStream postData(String paramString, IXulWorkItemSource paramIXulWorkItemSource, boolean paramBoolean, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    _syncDownloadItem local_syncDownloadItem = new _syncDownloadItem(paramString, paramIXulWorkItemSource);
    local_syncDownloadItem.noFileCache = paramBoolean;
    local_syncDownloadItem.isDirect = paramBoolean;
    _doDownload(new _downloadContext(), local_syncDownloadItem, new XulDownloadParams(true, paramArrayOfByte, paramArrayOfString));
    return local_syncDownloadItem._result;
  }

  public static InputStream postData(String paramString, IXulWorkItemSource paramIXulWorkItemSource, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    return postData(paramString, paramIXulWorkItemSource, false, paramArrayOfByte, paramArrayOfString);
  }

  public static InputStream postData(String paramString, boolean paramBoolean, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    return postData(paramString, null, paramBoolean, paramArrayOfByte, paramArrayOfString);
  }

  public static InputStream postData(String paramString, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    return postData(paramString, false, paramArrayOfByte, paramArrayOfString);
  }

  private static void recycleDownloadBuffer(XulDownloadOutputBuffer paramXulDownloadOutputBuffer)
  {
    try
    {
      _downloadBufferList.push(paramXulDownloadOutputBuffer);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void registerDownloader(IXulWorkItemSource paramIXulWorkItemSource)
  {
    synchronized (_downloader)
    {
      _downloader.add(0, paramIXulWorkItemSource);
      _notifyDownloadWorker();
      return;
    }
  }

  public static void removeDrawableCache(String paramString)
  {
  }

  public static void removeDrawableCachePermanently(String paramString)
  {
    if ("*".equals(paramString))
      clearDrawableCachePermanently();
    while (true)
    {
      return;
      synchronized (_weakCachedDrawable)
      {
        XulDrawable localXulDrawable = _weakCachedDrawable.remove(paramString);
        if ((localXulDrawable == null) || (!(localXulDrawable instanceof XulBitmapDrawable)))
          continue;
        BitmapTools.recycleBitmap(XulBitmapDrawable.detachBitmap((XulBitmapDrawable)localXulDrawable));
        localXulDrawable.recycle();
        return;
      }
    }
  }

  public static void resumeDrawableWorkers()
  {
    if (_suspendTime == 0L);
    do
    {
      return;
      _suspendTime = 0L;
    }
    while (_pendingDrawableTask.isEmpty());
    synchronized (_pendingDrawableTask)
    {
      if (!_pendingDrawableTask.isEmpty())
        _notifyDrawableWorker();
      return;
    }
  }

  public static void setHandler(IXulWorkerHandler paramIXulWorkerHandler)
  {
    _handler = paramIXulWorkerHandler;
  }

  public static void suspendDrawableWorker(int paramInt)
  {
    long l = XulUtils.timestamp() + paramInt;
    if (l > _suspendTime)
      _suspendTime = l;
  }

  private static Bitmap toRoundCornerBitmap(Canvas paramCanvas, Paint paramPaint, Bitmap paramBitmap, float[] paramArrayOfFloat)
  {
    if ((paramBitmap.isMutable()) && (paramBitmap.hasAlpha()))
      return toRoundCornerMutableBitmap(paramCanvas, paramPaint, paramBitmap, paramArrayOfFloat);
    Bitmap localBitmap = BitmapTools.createBitmapFromRecycledBitmaps(paramBitmap.getWidth(), paramBitmap.getHeight());
    paramCanvas.setBitmap(localBitmap);
    Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    paramCanvas.drawBitmap(paramBitmap, localRect, localRect, null);
    return toRoundCornerMutableBitmap(paramCanvas, paramPaint, localBitmap, paramArrayOfFloat);
  }

  private static Bitmap toRoundCornerMutableBitmap(Canvas paramCanvas, Paint paramPaint, Bitmap paramBitmap, float[] paramArrayOfFloat)
  {
    paramCanvas.setBitmap(paramBitmap);
    RectF localRectF = new RectF(1.0F, 1.0F, 1.0F, 1.0F);
    if (paramArrayOfFloat.length == 2)
    {
      float[] arrayOfFloat = new float[8];
      for (int i = 0; i < 4; i++)
      {
        arrayOfFloat[(0 + i * 2)] = paramArrayOfFloat[0];
        arrayOfFloat[(1 + i * 2)] = paramArrayOfFloat[1];
        paramArrayOfFloat = arrayOfFloat;
      }
    }
    paramCanvas.save();
    paramCanvas.translate(-1.0F, -1.0F);
    RoundRectShape localRoundRectShape = new RoundRectShape(null, localRectF, paramArrayOfFloat);
    localRoundRectShape.resize(2 + paramBitmap.getWidth(), 2 + paramBitmap.getHeight());
    localRoundRectShape.draw(paramCanvas, paramPaint);
    paramCanvas.restore();
    paramCanvas.setBitmap(null);
    return paramBitmap;
  }

  private static Bitmap toRoundCornerShadowBitmap(Canvas paramCanvas, Paint paramPaint1, Paint paramPaint2, Paint paramPaint3, Paint paramPaint4, Bitmap paramBitmap, float[] paramArrayOfFloat, float paramFloat, int paramInt)
  {
    int i = 2 * XulUtils.roundToInt(paramFloat);
    Bitmap localBitmap = BitmapTools.createBitmapFromRecycledBitmaps(i + paramBitmap.getWidth(), i + paramBitmap.getHeight());
    paramCanvas.setBitmap(localBitmap);
    Rect localRect1 = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    Rect localRect2 = new Rect(localRect1);
    XulUtils.offsetRect(localRect2, i / 2, i / 2);
    RectF localRectF = new RectF(localRect2);
    if (paramArrayOfFloat.length == 2)
    {
      float f1 = paramArrayOfFloat[1];
      float f2 = paramArrayOfFloat[0];
      paramCanvas.drawRoundRect(localRectF, f2, f1, paramPaint1);
      paramCanvas.drawBitmap(paramBitmap, localRect1, localRect2, paramPaint2);
      XulUtils.saveCanvasLayer(paramCanvas, 0, 0, localBitmap.getWidth(), localBitmap.getHeight(), paramPaint1, 4);
      paramPaint4.setShadowLayer(paramFloat, 0.0F, 0.0F, paramInt);
      paramCanvas.drawRoundRect(localRectF, f2, f1, paramPaint4);
      paramCanvas.drawRoundRect(localRectF, f2, f1, paramPaint3);
      XulUtils.restoreCanvas(paramCanvas);
    }
    while (true)
    {
      paramCanvas.setBitmap(null);
      return localBitmap;
      RoundRectShape localRoundRectShape = new RoundRectShape(paramArrayOfFloat, null, null);
      localRoundRectShape.resize(localRectF.width(), localRectF.height());
      XulUtils.saveCanvas(paramCanvas);
      paramCanvas.translate(localRectF.left, localRectF.top);
      localRoundRectShape.draw(paramCanvas, paramPaint1);
      paramCanvas.translate(-localRectF.left, -localRectF.top);
      paramCanvas.drawBitmap(paramBitmap, localRect1, localRect2, paramPaint2);
      XulUtils.saveCanvasLayer(paramCanvas, 0, 0, localBitmap.getWidth(), localBitmap.getHeight(), paramPaint1, 4);
      paramPaint4.setShadowLayer(paramFloat, 0.0F, 0.0F, paramInt);
      paramCanvas.translate(localRectF.left, localRectF.top);
      localRoundRectShape.draw(paramCanvas, paramPaint4);
      localRoundRectShape.draw(paramCanvas, paramPaint3);
      XulUtils.restoreCanvas(paramCanvas);
      XulUtils.restoreCanvas(paramCanvas);
    }
  }

  public static void unregisterDownloader(IXulWorkItemSource paramIXulWorkItemSource)
  {
    synchronized (_downloader)
    {
      _downloader.remove(paramIXulWorkItemSource);
      return;
    }
  }

  private static boolean waitPendingStream(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return false;
    if ((paramInputStream instanceof XulPendingInputStream))
    {
      XulPendingInputStream localXulPendingInputStream = (XulPendingInputStream)paramInputStream;
      localXulPendingInputStream.checkPending();
      return localXulPendingInputStream.isReady();
    }
    return true;
  }

  public static class DownloadItem
  {
    String __cacheKey = null;
    XulWorker.IXulWorkItemSource __ownerDownloadHandler = null;
    String __resolvedPath = "";
    int __retryCounter = 0;
    public boolean isDirect = false;
    public boolean noFileCache = false;
    public String url;

    public String getInternalCacheKey()
    {
      return this.__cacheKey;
    }

    public String getInternalResolvedPath()
    {
      return this.__resolvedPath;
    }
  }

  public static class DrawableItem extends XulWorker.DownloadItem
  {
    InputStream __dataStream = null;
    XulWorker.IXulWorkItemSource __ownerDrawableHandler = null;
    float[] _roundRectRadius;
    public int height = 0;
    public boolean reusable = false;
    public float scalarX = 1.0F;
    public float scalarY = 1.0F;
    public int shadowColor = -16777216;
    public float shadowSize = 0.0F;
    public int target_height = 0;
    public int target_width = 0;
    public int width = 0;

    public void setRoundRect(float paramFloat1, float paramFloat2)
    {
      this._roundRectRadius = new float[2];
      this._roundRectRadius[0] = paramFloat1;
      this._roundRectRadius[1] = paramFloat2;
    }

    public void setRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      setRoundRect(paramFloat1, paramFloat1, paramFloat2, paramFloat2, paramFloat3, paramFloat3, paramFloat4, paramFloat4);
    }

    public void setRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
    {
      this._roundRectRadius = new float[8];
      this._roundRectRadius[0] = paramFloat1;
      this._roundRectRadius[1] = paramFloat2;
      this._roundRectRadius[2] = paramFloat3;
      this._roundRectRadius[3] = paramFloat4;
      this._roundRectRadius[4] = paramFloat5;
      this._roundRectRadius[5] = paramFloat6;
      this._roundRectRadius[6] = paramFloat7;
      this._roundRectRadius[7] = paramFloat8;
    }

    public void setRoundRect(float[] paramArrayOfFloat)
    {
      this._roundRectRadius = paramArrayOfFloat;
    }
  }

  public static abstract interface IXulWorkItemSource
  {
    public abstract InputStream getAppData(XulWorker.DownloadItem paramDownloadItem, String paramString);

    public abstract InputStream getAssets(XulWorker.DownloadItem paramDownloadItem, String paramString);

    public abstract XulWorker.DownloadItem getDownloadItem();

    public abstract XulWorker.DrawableItem getDrawableItem();

    public abstract void onDownload(XulWorker.DownloadItem paramDownloadItem, InputStream paramInputStream);

    public abstract void onDrawableLoaded(XulWorker.DrawableItem paramDrawableItem, XulDrawable paramXulDrawable);

    public abstract String resolve(XulWorker.DownloadItem paramDownloadItem, String paramString);
  }

  public static abstract interface IXulWorkerHandler
  {
    public abstract String calCacheKey(String paramString);

    public abstract InputStream getAppData(String paramString);

    public abstract InputStream getAssets(String paramString);

    public abstract InputStream loadCachedData(String paramString);

    public abstract boolean preloadImage(XulWorker.DrawableItem paramDrawableItem, Rect paramRect);

    public abstract Bitmap preprocessImage(XulWorker.DrawableItem paramDrawableItem, Bitmap paramBitmap);

    public abstract String resolvePath(XulWorker.DownloadItem paramDownloadItem, String paramString);

    public abstract boolean storeCachedData(String paramString, InputStream paramInputStream);
  }

  private static class WeakDrawableCache
  {
    XulCachedHashMap<String, WeakReference<XulDrawable>> _cache = new XulCachedHashMap();

    private WeakReference<XulDrawable> getWeakReference(XulDrawable paramXulDrawable)
    {
      return new WeakReference(paramXulDrawable);
    }

    public void clear()
    {
      this._cache.clear();
    }

    public boolean containsKey(String paramString)
    {
      return this._cache.containsKey(paramString);
    }

    public XulDrawable get(String paramString)
    {
      WeakReference localWeakReference = (WeakReference)this._cache.get(paramString);
      if (localWeakReference == null)
        return null;
      return (XulDrawable)localWeakReference.get();
    }

    public void put(String paramString, XulDrawable paramXulDrawable)
    {
      this._cache.put(paramString, getWeakReference(paramXulDrawable));
    }

    public XulDrawable remove(String paramString)
    {
      WeakReference localWeakReference = (WeakReference)this._cache.remove(paramString);
      if (localWeakReference == null)
        return null;
      XulDrawable localXulDrawable = (XulDrawable)localWeakReference.get();
      localWeakReference.clear();
      return localXulDrawable;
    }
  }

  public static class XulDownloadOutputBuffer extends XulMemoryOutputStream
  {
    public XulDownloadOutputBuffer(int paramInt)
    {
      super();
    }

    public void onClose()
    {
      XulWorker.recycleDownloadBuffer(this);
    }
  }

  public static class XulDownloadParams
  {
    public String[] extHeaders;
    public boolean post = false;
    public byte[] postBody;
    public int responseCode;
    public Map<String, List<String>> responseHeaders;
    public String responseMsg;

    public XulDownloadParams(boolean paramBoolean, byte[] paramArrayOfByte, String[] paramArrayOfString)
    {
      this.post = paramBoolean;
      this.postBody = paramArrayOfByte;
      this.extHeaders = paramArrayOfString;
    }
  }

  static class _downloadContext
  {
    Pattern appDataPat = Pattern.compile("^file:///\\.app/(.+)$");
    Pattern assetsPat = Pattern.compile("^file:///\\.assets/(.+)$");
    byte[] downloadBuffer = new byte[1024];
  }

  private static class _syncDownloadItem extends XulWorker.DownloadItem
  {
    private XulWorker.IXulWorkItemSource _nextSource;
    private InputStream _result;
    XulWorker.IXulWorkItemSource syncSource = new XulWorker.IXulWorkItemSource()
    {
      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        if (XulWorker._syncDownloadItem.this._nextSource != null)
          return XulWorker._syncDownloadItem.this._nextSource.getAppData(paramAnonymousDownloadItem, paramAnonymousString);
        return null;
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        if (XulWorker._syncDownloadItem.this._nextSource != null)
          return XulWorker._syncDownloadItem.this._nextSource.getAssets(paramAnonymousDownloadItem, paramAnonymousString);
        return null;
      }

      public XulWorker.DownloadItem getDownloadItem()
      {
        return null;
      }

      public XulWorker.DrawableItem getDrawableItem()
      {
        return null;
      }

      public void onDownload(XulWorker.DownloadItem paramAnonymousDownloadItem, InputStream paramAnonymousInputStream)
      {
        XulWorker._syncDownloadItem.access$002((XulWorker._syncDownloadItem)paramAnonymousDownloadItem, paramAnonymousInputStream);
      }

      public void onDrawableLoaded(XulWorker.DrawableItem paramAnonymousDrawableItem, XulDrawable paramAnonymousXulDrawable)
      {
      }

      public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        if (XulWorker._syncDownloadItem.this._nextSource != null)
          return XulWorker._syncDownloadItem.this._nextSource.resolve(paramAnonymousDownloadItem, paramAnonymousString);
        return null;
      }
    };

    _syncDownloadItem(String paramString, XulWorker.IXulWorkItemSource paramIXulWorkItemSource)
    {
      this.url = paramString;
      this.__ownerDownloadHandler = this.syncSource;
      this._nextSource = paramIXulWorkItemSource;
    }
  }

  private static class acceptAllHostnameVerifier
    implements HostnameVerifier
  {
    public boolean verify(String paramString, SSLSession paramSSLSession)
    {
      return true;
    }
  }

  public static class acceptAllTrustManager
    implements TrustManager, X509TrustManager
  {
    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
      throws CertificateException
    {
    }

    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
      throws CertificateException
    {
    }

    public X509Certificate[] getAcceptedIssuers()
    {
      return null;
    }

    public boolean isClientTrusted(X509Certificate[] paramArrayOfX509Certificate)
    {
      return true;
    }

    public boolean isServerTrusted(X509Certificate[] paramArrayOfX509Certificate)
    {
      return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulWorker
 * JD-Core Version:    0.6.2
 */