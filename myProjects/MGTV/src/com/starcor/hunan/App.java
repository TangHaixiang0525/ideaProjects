package com.starcor.hunan;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.ai.AIDataCacheTask;
import com.starcor.core.epgapi.GlobalApiTask;
import com.starcor.core.exception.CrashHandler;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.service.DownLoadService.MyBinder;
import com.starcor.core.utils.CacheUtils;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.drm.MarlinDrmManager;
import com.starcor.hunan.opendownload.encrypt.EncryptTools;
import com.starcor.hunan.opendownload.logupload.LogCacheManger;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.service.apidetect.data.ApiDetectGlobalEnv;
import com.starcor.hunan.service.apidetect.server.ApiDetectServerApiManager;
import com.starcor.hunan.widget.FilmListView.ItemPicVMirrorProcessor;
import com.starcor.hunan.xul.XULResPrefetchManager;
import com.starcor.jump.bussines.BussinesObserver;
import com.starcor.message.MessageHandler;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportExecutor;
import com.starcor.report.ReportService;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.service.InitService;
import com.starcor.ui.UITools;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DownloadItem;
import com.starcor.xul.XulWorker.DrawableItem;
import com.starcor.xul.XulWorker.IXulWorkerHandler;
import com.starcor.xul.widget.XulCustomRender;
import com.starcor.xulapp.debug.XulDebugServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.TimeZone;

public class App extends Application
{
  public static String CURRENT_LANG = "zh-cn";
  public static final int DESIGN_HEIGHT = 720;
  public static final int DESIGN_WIDTH = 1280;
  public static int SCREEN_HEIGHT = 0;
  public static int SCREEN_WIDTH = 0;
  public static final String TAG = "Application";
  private static App application;
  private static boolean isInitCompleted = false;
  public static Context mContext;
  private OnServiceOKListener mOnserOkListener;
  private ServiceConnection taskConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      if (paramAnonymousIBinder != null)
      {
        App.access$002(App.this, ((DownLoadService.MyBinder)paramAnonymousIBinder).getService());
        if (App.this.mOnserOkListener != null)
          App.this.mOnserOkListener.onServiceOK();
        Logger.i("Application", "onServiceConnected success name:" + paramAnonymousComponentName);
      }
      App.access$202(App.this, 0);
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      App.access$002(App.this, null);
      App.access$202(App.this, 2);
      Logger.e("Application", "onServiceDisconnected taskService");
    }
  };
  private int taskConnectionState = -1;
  private DownLoadService taskService = null;

  static
  {
    SCREEN_HEIGHT = 720;
  }

  public static int Operation(float paramFloat)
  {
    return Operation2((int)paramFloat);
  }

  public static int Operation2(int paramInt)
  {
    if (DeviceInfo.isHUOLE_G1())
      return (int)(0.5F + 720.0F * (1.0F * paramInt / 720.0F));
    return (int)(0.5F + SCREEN_HEIGHT * (1.0F * paramInt / 720.0F));
  }

  public static int OperationDiy(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return Operation2(paramInt1);
    return (int)(0.5F + SCREEN_HEIGHT * (1.0F * paramInt1 / paramInt2));
  }

  public static float OperationFolat(float paramFloat)
  {
    return Operation2((int)paramFloat);
  }

  public static int OperationHeight(int paramInt)
  {
    return Operation2(paramInt);
  }

  public static int OperationWidth(int paramInt)
  {
    return Operation2(paramInt);
  }

  public static Context getAppContext()
  {
    return mContext;
  }

  public static App getInstance()
  {
    if (application == null)
      Logger.e("Application", "getInstance and app is null");
    return application;
  }

  private HashMap<String, String> getSiftPosterUrl(String paramString)
  {
    HashMap localHashMap = new HashMap();
    String[] arrayOfString1 = paramString.substring(5).split(",");
    if (arrayOfString1 == null)
    {
      localHashMap = null;
      return localHashMap;
    }
    int i = arrayOfString1.length;
    int j = 0;
    label34: String[] arrayOfString2;
    if (j < i)
    {
      arrayOfString2 = arrayOfString1[j].split("=");
      if (arrayOfString2.length != 1)
        break label76;
      localHashMap.put(arrayOfString2[0], "");
    }
    while (true)
    {
      j++;
      break label34;
      break;
      label76: localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
    }
  }

  private void initXul()
  {
    XulWorker.setHandler(new XulWorker.IXulWorkerHandler()
    {
      HashMap<String, Boolean> _assetAvailableMap = new HashMap();
      HashMap<String, InputStream> _cachedAssetsStream = new HashMap();
      AssetManager assets = App.this.getAssets();
      SparseArray<Canvas> canvasSparseArray = new SparseArray();
      Paint clipPaint = new Paint(2);
      Paint commonPaint = new Paint(3);
      Bitmap greyMaskBitmap = BitmapFactory.decodeResource(App.this.getResources(), 2130837545);
      Bitmap hexagonBitmap = BitmapFactory.decodeResource(App.this.getResources(), 2130837549);
      Paint paint = new Paint(2);
      Bitmap parallelogram = BitmapFactory.decodeResource(App.this.getResources(), 2130837610);
      FilmListView.ItemPicVMirrorProcessor vMirrorProcessor = new FilmListView.ItemPicVMirrorProcessor();

      private InputStream _openAssets(String paramAnonymousString)
      {
        synchronized (this._assetAvailableMap)
        {
          InputStream localInputStream1 = (InputStream)this._cachedAssetsStream.remove(paramAnonymousString);
          if (localInputStream1 != null)
            return localInputStream1;
          Boolean localBoolean = (Boolean)this._assetAvailableMap.get(paramAnonymousString);
          if ((localBoolean != null) && (!localBoolean.booleanValue()))
            return null;
        }
        try
        {
          InputStream localInputStream2 = this.assets.open(paramAnonymousString);
          return localInputStream2;
          localObject = finally;
          throw localObject;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
        return null;
      }

      private String _resolveAssets(String paramAnonymousString)
      {
        if ((paramAnonymousString.startsWith("xul/")) || (paramAnonymousString.startsWith("xul-1280x720/")))
          return null;
        String[] arrayOfString1;
        String[] arrayOfString2;
        int i;
        if (App.SCREEN_WIDTH < 1920)
        {
          arrayOfString1 = new String[4];
          arrayOfString1[0] = ("xul-1280x720/" + paramAnonymousString + "/" + App.CURRENT_LANG);
          arrayOfString1[1] = ("xul-1280x720/" + paramAnonymousString);
          arrayOfString1[2] = ("xul/" + paramAnonymousString + "/" + App.CURRENT_LANG);
          arrayOfString1[3] = ("xul/" + paramAnonymousString);
          arrayOfString2 = arrayOfString1;
          i = arrayOfString2.length;
        }
        label471: label473: for (int j = 0; ; j++)
        {
          String str1;
          while (true)
          {
            if (j >= i)
              break label471;
            str1 = arrayOfString2[j];
            synchronized (this._assetAvailableMap)
            {
              Boolean localBoolean = (Boolean)this._assetAvailableMap.get(str1);
              if (localBoolean != null)
              {
                if (!localBoolean.booleanValue())
                  break label473;
                return "file:///.assets/" + str1;
                arrayOfString1 = new String[4];
                arrayOfString1[0] = ("xul/" + paramAnonymousString + "/" + App.CURRENT_LANG);
                arrayOfString1[1] = ("xul/" + paramAnonymousString);
                arrayOfString1[2] = ("xul-1280x720/" + paramAnonymousString + "/" + App.CURRENT_LANG);
                arrayOfString1[3] = ("xul-1280x720/" + paramAnonymousString);
              }
            }
          }
          try
          {
            InputStream localInputStream = this.assets.open(str1);
            synchronized (this._assetAvailableMap)
            {
              this._assetAvailableMap.put(str1, Boolean.TRUE);
              this._cachedAssetsStream.put(str1, localInputStream);
              String str2 = "file:///.assets/" + str1;
              return str2;
            }
          }
          catch (IOException localIOException)
          {
            synchronized (this._assetAvailableMap)
            {
              this._assetAvailableMap.put(str1, Boolean.FALSE);
            }
          }
          return null;
        }
      }

      private Bitmap getCircleBitmap(Bitmap paramAnonymousBitmap)
      {
        Bitmap localBitmap;
        int i;
        if (paramAnonymousBitmap.getWidth() > paramAnonymousBitmap.getHeight())
        {
          localBitmap = Bitmap.createBitmap(paramAnonymousBitmap.getHeight(), paramAnonymousBitmap.getHeight(), Bitmap.Config.ARGB_8888);
          i = (int)Thread.currentThread().getId();
        }
        while (true)
        {
          Canvas localCanvas;
          synchronized (this.canvasSparseArray)
          {
            localCanvas = (Canvas)this.canvasSparseArray.get(i);
            if (localCanvas == null)
            {
              localCanvas = new Canvas();
              this.canvasSparseArray.put(i, localCanvas);
            }
            localCanvas.setBitmap(localBitmap);
            if (paramAnonymousBitmap.getWidth() > paramAnonymousBitmap.getHeight())
            {
              localCanvas.drawCircle(paramAnonymousBitmap.getHeight() / 2, paramAnonymousBitmap.getHeight() / 2, paramAnonymousBitmap.getHeight() / 2, this.commonPaint);
              localCanvas.drawBitmap(paramAnonymousBitmap, new Rect(paramAnonymousBitmap.getWidth() / 2 - paramAnonymousBitmap.getHeight() / 2, 0, paramAnonymousBitmap.getWidth() / 2 - paramAnonymousBitmap.getHeight() / 2 + paramAnonymousBitmap.getHeight(), paramAnonymousBitmap.getHeight()), new Rect(0, 0, paramAnonymousBitmap.getHeight(), paramAnonymousBitmap.getHeight()), this.paint);
              localCanvas.setBitmap(null);
              return localBitmap;
              localBitmap = Bitmap.createBitmap(paramAnonymousBitmap.getWidth(), paramAnonymousBitmap.getWidth(), Bitmap.Config.ARGB_8888);
            }
          }
          localCanvas.drawCircle(paramAnonymousBitmap.getWidth() / 2, paramAnonymousBitmap.getWidth() / 2, paramAnonymousBitmap.getWidth() / 2, this.commonPaint);
          localCanvas.drawBitmap(paramAnonymousBitmap, new Rect(0, 0, paramAnonymousBitmap.getWidth(), paramAnonymousBitmap.getWidth()), new Rect(0, 0, paramAnonymousBitmap.getWidth(), paramAnonymousBitmap.getWidth()), this.paint);
        }
      }

      private Bitmap getClipBitmap(Bitmap paramAnonymousBitmap1, Bitmap paramAnonymousBitmap2, boolean paramAnonymousBoolean)
      {
        Bitmap localBitmap = Bitmap.createBitmap(paramAnonymousBitmap1.getWidth(), paramAnonymousBitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        int i = (int)Thread.currentThread().getId();
        synchronized (this.canvasSparseArray)
        {
          Canvas localCanvas = (Canvas)this.canvasSparseArray.get(i);
          if (localCanvas == null)
          {
            localCanvas = new Canvas();
            this.canvasSparseArray.put(i, localCanvas);
          }
          localCanvas.setBitmap(localBitmap);
          localCanvas.drawBitmap(paramAnonymousBitmap1, null, new Rect(0, 0, paramAnonymousBitmap1.getWidth(), paramAnonymousBitmap1.getHeight()), null);
          if (paramAnonymousBoolean)
          {
            localCanvas.save();
            localCanvas.clipRect(0.0F, 0.74F * paramAnonymousBitmap1.getHeight(), paramAnonymousBitmap1.getWidth(), paramAnonymousBitmap1.getHeight());
            localCanvas.drawColor(-7254785);
            localCanvas.restore();
          }
          localCanvas.drawBitmap(paramAnonymousBitmap2, null, new Rect(0, 0, paramAnonymousBitmap1.getWidth(), paramAnonymousBitmap1.getHeight()), this.clipPaint);
          localCanvas.setBitmap(null);
          return localBitmap;
        }
      }

      public String calCacheKey(String paramAnonymousString)
      {
        if (paramAnonymousString == null)
          paramAnonymousString = "";
        int j;
        do
        {
          int i;
          do
          {
            return paramAnonymousString;
            i = paramAnonymousString.indexOf("http://");
          }
          while (i < 0);
          j = paramAnonymousString.indexOf('/', i + 7);
        }
        while (j < 0);
        return paramAnonymousString.substring(j);
      }

      public InputStream getAppData(String paramAnonymousString)
      {
        return AppInputStream.getInstance().getAppInputStream(paramAnonymousString);
      }

      public InputStream getAssets(String paramAnonymousString)
      {
        return _openAssets(paramAnonymousString);
      }

      public InputStream loadCachedData(String paramAnonymousString)
      {
        try
        {
          FileInputStream localFileInputStream = new FileInputStream(new File(GlobalEnv.getInstance().getPicCachePath(), paramAnonymousString));
          return localFileInputStream;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
        }
        return null;
      }

      public boolean preloadImage(XulWorker.DrawableItem paramAnonymousDrawableItem, Rect paramAnonymousRect)
      {
        boolean bool = paramAnonymousDrawableItem.url.startsWith("sift:");
        String str1 = null;
        if (bool)
        {
          new HashMap();
          HashMap localHashMap = App.this.getSiftPosterUrl(paramAnonymousDrawableItem.url);
          str1 = null;
          if (localHashMap != null)
            if (!TextUtils.isEmpty((CharSequence)localHashMap.get("img_v")))
              break label149;
        }
        label149: for (String str2 = "true"; ; str2 = "false")
        {
          str1 = String.valueOf(str2);
          if ((!paramAnonymousDrawableItem.url.startsWith("effect:mirror:")) && ((str1 == null) || (!"true".equals(str1))))
            break;
          int i = paramAnonymousDrawableItem.width;
          int j = paramAnonymousDrawableItem.height;
          if ((i == 0) && (j == 0))
            i = paramAnonymousDrawableItem.target_width;
          paramAnonymousRect.set(0, 0, i, 0);
          return true;
        }
        return false;
      }

      public Bitmap preprocessImage(XulWorker.DrawableItem paramAnonymousDrawableItem, Bitmap paramAnonymousBitmap)
      {
        if ((paramAnonymousBitmap == null) || (paramAnonymousBitmap.getWidth() == 0) || (paramAnonymousBitmap.getHeight() == 0))
          return null;
        boolean bool = paramAnonymousDrawableItem.url.startsWith("sift:");
        String str1 = null;
        String str2;
        if (bool)
        {
          new HashMap();
          HashMap localHashMap = App.this.getSiftPosterUrl(paramAnonymousDrawableItem.url);
          str1 = null;
          if (localHashMap != null)
          {
            if (!TextUtils.isEmpty((CharSequence)localHashMap.get("img_v")))
              break label199;
            str2 = "true";
            str1 = String.valueOf(str2);
          }
        }
        if ((paramAnonymousDrawableItem.url.startsWith("effect:mirror:")) || ((str1 != null) && ("true".equals(str1))))
        {
          int i = paramAnonymousDrawableItem.width;
          int j = paramAnonymousDrawableItem.height;
          if (i == 0)
            if (j == 0)
            {
              i = paramAnonymousDrawableItem.target_width;
              j = paramAnonymousDrawableItem.target_height;
            }
          while (true)
          {
            if ((i == 0) || (j == 0))
            {
              i = paramAnonymousBitmap.getWidth();
              j = paramAnonymousBitmap.getHeight();
            }
            return this.vMirrorProcessor.postloadProcess(0, Integer.valueOf(0), i, j, paramAnonymousBitmap);
            label199: str2 = "false";
            break;
            i = j * paramAnonymousBitmap.getWidth() / paramAnonymousBitmap.getHeight();
            continue;
            if (j == 0)
              i = i * paramAnonymousBitmap.getHeight() / paramAnonymousBitmap.getWidth();
          }
        }
        if (paramAnonymousDrawableItem.url.startsWith("effect:hexagon:"))
        {
          if (this.hexagonBitmap == null)
            return null;
          return getClipBitmap(paramAnonymousBitmap, this.hexagonBitmap, false);
        }
        if (paramAnonymousDrawableItem.url.startsWith("effect:parallelogram:"))
        {
          if (this.parallelogram == null)
            return null;
          return getClipBitmap(paramAnonymousBitmap, this.parallelogram, true);
        }
        if (paramAnonymousDrawableItem.url.startsWith("effect:circle:"))
          return getCircleBitmap(paramAnonymousBitmap);
        if (paramAnonymousDrawableItem.url.startsWith("effect:blur:"))
        {
          int k = paramAnonymousBitmap.getWidth() / 4;
          int m = paramAnonymousBitmap.getHeight() / 4;
          Bitmap localBitmap3 = Bitmap.createBitmap(k, m, Bitmap.Config.ARGB_8888);
          int n = (int)Thread.currentThread().getId();
          synchronized (this.canvasSparseArray)
          {
            Canvas localCanvas2 = (Canvas)this.canvasSparseArray.get(n);
            if (localCanvas2 == null)
            {
              localCanvas2 = new Canvas();
              this.canvasSparseArray.put(n, localCanvas2);
            }
            localCanvas2.setBitmap(localBitmap3);
            localCanvas2.drawBitmap(paramAnonymousBitmap, null, new Rect(0, 0, k, m), null);
            ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(localBitmap3.getByteCount());
            localBitmap3.copyPixelsToBuffer(localByteBuffer);
            XulUtils.doBlurOnBuffer(localByteBuffer, k, m, 2);
            localByteBuffer.rewind();
            localBitmap3.copyPixelsFromBuffer(localByteBuffer);
            localCanvas2.save();
            localCanvas2.drawBitmap(this.greyMaskBitmap, null, new Rect(0, (int)(0.3F * m), k, m), null);
            localCanvas2.restore();
            localCanvas2.setBitmap(null);
            return localBitmap3;
          }
        }
        if (paramAnonymousDrawableItem.url.startsWith("effect:polygon:"))
        {
          Bitmap localBitmap1 = BitmapFactory.decodeStream(AppInputStream.getInstance().getAppInputStream("skins/background"));
          if (localBitmap1 == null)
            return null;
          Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, (int)(0.213F * localBitmap1.getHeight()), (int)(0.137F * localBitmap1.getWidth()), (int)(0.53F * localBitmap1.getHeight()));
          Canvas localCanvas1 = null;
          if (paramAnonymousBitmap != null)
            localCanvas1 = new Canvas(paramAnonymousBitmap);
          localCanvas1.save();
          Rect localRect1 = new Rect(0, 0, localBitmap2.getWidth(), localBitmap2.getHeight());
          Rect localRect2 = new Rect(0, 0, paramAnonymousBitmap.getWidth(), paramAnonymousBitmap.getHeight());
          Paint localPaint = new Paint(2);
          localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
          localCanvas1.drawBitmap(localBitmap2, localRect1, localRect2, localPaint);
          localCanvas1.restore();
          return paramAnonymousBitmap;
        }
        return null;
      }

      public String resolvePath(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        String str1;
        if ("file:///.app/splash/logo".equals(paramAnonymousString))
          if (DeviceInfo.isMANGO_DOWNLOAD())
            str1 = "file:///.assets/splash/download_splash_img.png";
        label189: 
        do
        {
          return str1;
          return "file:///.assets/splash/splash_img.png";
          if ("file:///.app/splash/boot_ad_img".equals(paramAnonymousString))
          {
            XulDrawable localXulDrawable = XulDrawable.fromBitmap(BitmapFactory.decodeFile(new File(GlobalEnv.getInstance().getPicCachePath(), "boot_ad_image").getAbsolutePath()), paramAnonymousString, paramAnonymousString);
            XulWorker.addDrawableToCache(paramAnonymousString, localXulDrawable);
            return "";
          }
          HashMap localHashMap;
          if (paramAnonymousString.startsWith("sift:"))
          {
            new HashMap();
            localHashMap = App.this.getSiftPosterUrl(paramAnonymousString);
            if (localHashMap != null)
              if ((!TextUtils.isEmpty((CharSequence)localHashMap.get("img_v"))) || (TextUtils.isEmpty((CharSequence)localHashMap.get("img_h"))))
                break label189;
          }
          for (paramAnonymousString = "effect:mirror:" + (String)localHashMap.get("img_h"); "scale:".equals(paramAnonymousString); paramAnonymousString = "scale:" + (String)localHashMap.get("img_v"))
            return "";
          if ((paramAnonymousString.startsWith("scale:")) && ((paramAnonymousDownloadItem instanceof XulWorker.DrawableItem)))
          {
            XulWorker.DrawableItem localDrawableItem = (XulWorker.DrawableItem)paramAnonymousDownloadItem;
            String str2 = paramAnonymousString.substring(6);
            int k;
            int j;
            if ((localDrawableItem.target_width != 0) && (localDrawableItem.target_height != 0))
            {
              k = localDrawableItem.target_width;
              j = localDrawableItem.target_height;
            }
            while (true)
            {
              if ((k != 0) && (j != 0))
              {
                int n = str2.lastIndexOf('/');
                if (n > 0)
                {
                  Object[] arrayOfObject = new Object[4];
                  arrayOfObject[0] = str2.substring(0, n);
                  arrayOfObject[1] = Integer.valueOf(k);
                  arrayOfObject[2] = Integer.valueOf(j);
                  int i1 = n + 1;
                  arrayOfObject[3] = str2.substring(i1);
                  str2 = String.format("%s/%dx%d/%s", arrayOfObject);
                }
              }
              return str2;
              int i = localDrawableItem.width;
              j = 0;
              k = 0;
              if (i != 0)
              {
                int m = localDrawableItem.height;
                j = 0;
                k = 0;
                if (m != 0)
                {
                  k = localDrawableItem.width;
                  j = localDrawableItem.height;
                }
              }
            }
          }
          if (paramAnonymousString.startsWith("effect:mirror:"))
            return paramAnonymousString.substring(14);
          if (paramAnonymousString.startsWith("effect:hexagon:"))
            return paramAnonymousString.substring(15);
          if (paramAnonymousString.startsWith("effect:parallelogram:"))
            return paramAnonymousString.substring(21);
          if (paramAnonymousString.startsWith("effect:blur:"))
            return paramAnonymousString.substring(12);
          if (paramAnonymousString.startsWith("effect:polygon:"))
            return paramAnonymousString.substring(15);
          if (paramAnonymousString.startsWith("effect:circle:"))
            return paramAnonymousString.substring(14);
          if (paramAnonymousString.equals("file:///.app/ext_toolbar"))
          {
            if (AppFuncCfg.FUNCTION_DISPLAY_EXT_TOOLBAR)
              return "file:///.assets/ext_toolbar.xml";
            return null;
          }
          if (paramAnonymousString.startsWith("file:///.assets/"))
            return _resolveAssets(paramAnonymousString.substring(16));
          if ("file:///.app/cached-api/n36".equals(paramAnonymousString))
          {
            if (GlobalLogic.getInstance().getMetaData() != null)
              return "file:///.app/api/n36";
            return "file:///.assets/api/n36.xml";
          }
          if ("file:///.app/language/default".equals(paramAnonymousString))
            return "file:///.assets/lang/" + App.CURRENT_LANG + "/default.xml";
          if ("file:///.app/skins/about-logo".equals(paramAnonymousString))
            return "file:///.assets/about/about_logo.png";
          if (!"file:///.app/skins/weather-icon".equals(paramAnonymousString))
            break;
          str1 = MainActivityV2.getWeatherIcon();
        }
        while (!TextUtils.isEmpty(str1));
        Log.i("weatherIcon", "weatherIcon=null");
        return "file:///.assets/weather/na.png";
        return null;
      }

      // ERROR //
      public boolean storeCachedData(String paramAnonymousString, InputStream paramAnonymousInputStream)
      {
        // Byte code:
        //   0: new 539	java/io/FileOutputStream
        //   3: dup
        //   4: new 292	java/io/File
        //   7: dup
        //   8: invokestatic 297	com/starcor/core/logic/GlobalEnv:getInstance	()Lcom/starcor/core/logic/GlobalEnv;
        //   11: invokevirtual 300	com/starcor/core/logic/GlobalEnv:getPicCachePath	()Ljava/lang/String;
        //   14: aload_1
        //   15: invokespecial 303	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
        //   18: invokespecial 540	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
        //   21: astore_3
        //   22: sipush 1024
        //   25: newarray byte
        //   27: astore 8
        //   29: aload_2
        //   30: aload 8
        //   32: invokevirtual 544	java/io/InputStream:read	([B)I
        //   35: istore 10
        //   37: iload 10
        //   39: ifle +12 -> 51
        //   42: aload_3
        //   43: aload 8
        //   45: iconst_0
        //   46: iload 10
        //   48: invokevirtual 548	java/io/FileOutputStream:write	([BII)V
        //   51: iload 10
        //   53: ifgt -24 -> 29
        //   56: aload_3
        //   57: invokevirtual 551	java/io/FileOutputStream:flush	()V
        //   60: aload_3
        //   61: invokevirtual 554	java/io/FileOutputStream:close	()V
        //   64: iconst_1
        //   65: ireturn
        //   66: astore 9
        //   68: aload 9
        //   70: invokevirtual 555	java/lang/Exception:printStackTrace	()V
        //   73: goto -17 -> 56
        //   76: astore 7
        //   78: aload 7
        //   80: invokevirtual 556	java/io/FileNotFoundException:printStackTrace	()V
        //   83: new 292	java/io/File
        //   86: dup
        //   87: invokestatic 297	com/starcor/core/logic/GlobalEnv:getInstance	()Lcom/starcor/core/logic/GlobalEnv;
        //   90: invokevirtual 300	com/starcor/core/logic/GlobalEnv:getPicCachePath	()Ljava/lang/String;
        //   93: aload_1
        //   94: invokespecial 303	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
        //   97: astore 5
        //   99: aload 5
        //   101: invokevirtual 559	java/io/File:exists	()Z
        //   104: ifeq +9 -> 113
        //   107: aload 5
        //   109: invokevirtual 562	java/io/File:delete	()Z
        //   112: pop
        //   113: ldc_w 564
        //   116: new 155	java/lang/StringBuilder
        //   119: dup
        //   120: invokespecial 156	java/lang/StringBuilder:<init>	()V
        //   123: ldc_w 566
        //   126: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: aload_1
        //   130: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   133: invokevirtual 170	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   136: invokestatic 571	com/starcor/core/utils/Logger:w	(Ljava/lang/String;Ljava/lang/String;)V
        //   139: iconst_0
        //   140: ireturn
        //   141: astore 4
        //   143: aload 4
        //   145: invokevirtual 137	java/io/IOException:printStackTrace	()V
        //   148: goto -65 -> 83
        //   151: astore 4
        //   153: goto -10 -> 143
        //   156: astore 7
        //   158: goto -80 -> 78
        //
        // Exception table:
        //   from	to	target	type
        //   29	37	66	java/lang/Exception
        //   42	51	66	java/lang/Exception
        //   22	29	76	java/io/FileNotFoundException
        //   29	37	76	java/io/FileNotFoundException
        //   42	51	76	java/io/FileNotFoundException
        //   56	64	76	java/io/FileNotFoundException
        //   68	73	76	java/io/FileNotFoundException
        //   0	22	141	java/io/IOException
        //   22	29	151	java/io/IOException
        //   29	37	151	java/io/IOException
        //   42	51	151	java/io/IOException
        //   56	64	151	java/io/IOException
        //   68	73	151	java/io/IOException
        //   0	22	156	java/io/FileNotFoundException
      }
    });
    XulManager.setBaseTempPath(getDir("xul_temp", 0));
    XULResPrefetchManager.init();
    XulCustomRender.register();
  }

  public static boolean isInitCompleted()
  {
    return isInitCompleted;
  }

  public static boolean isNeedRequestEpg()
  {
    return System.currentTimeMillis() - InitService.getStartTime() > 43200000L;
  }

  private void onVersionChanged()
  {
    CacheUtils.clearPic();
    CacheUtils.clearMetaData();
  }

  private void registerMessageObserver()
  {
    MessageHandler.instance().register(Integer.valueOf(2), new BussinesObserver());
  }

  public static void setInitCompleted(boolean paramBoolean)
  {
    isInitCompleted = paramBoolean;
  }

  private void startCheckVersion()
  {
    String str1 = GlobalLogic.getInstance().readPackageVersion();
    String str2 = DeviceInfo.getMGTVVersion();
    Logger.i("startCheckVersion cur_version=" + str2 + ",old_version=" + str1);
    if ((TextUtils.isEmpty(str1)) || (!str2.equals(str1)))
      onVersionChanged();
    GlobalLogic.getInstance().writePackageVersion(str2);
  }

  private void unregisterMessageObserver()
  {
    MessageHandler.instance().clear();
  }

  protected void finalize()
    throws Throwable
  {
    super.finalize();
    Logger.i("Application", "finalize");
    if (AIDataCacheTask.isInstanced())
      AIDataCacheTask.i().unInit();
    if (UserCPLLogic.isInstanced())
      UserCPLLogic.getInstance().unInit();
    if (this.taskService != null)
      unbindService(this.taskConnection);
    ReportExecutor.getInstance().stop();
    MarlinDrmManager.getInstance().destroy(this);
  }

  public DownLoadService getTaskService()
  {
    return this.taskService;
  }

  public void initApp()
  {
    GeneralUtils.markTime("initApp");
    UserCPLLogic.getInstance();
    LogCacheManger.getInstance().init(this);
    GlobalEnv.getInstance().configLogger();
    ApiDetectGlobalEnv.getInstance().init(this);
    GlobalApiTask.getInstance().init(this);
    EncryptTools.init();
    ReservationService.getinstance().init(this);
    DialogActivity.initErrorCode();
    GeneralUtils.markTime("initApp");
  }

  public boolean isServiceComplete()
  {
    if ((this.taskConnectionState != 0) && (this.taskConnectionState != 2))
    {
      Logger.i("Application", "taskConnectionState:" + this.taskConnectionState);
      return false;
    }
    return true;
  }

  public void onCreate()
  {
    GeneralUtils.markTime("App");
    super.onCreate();
    XulDebugServer.startUp();
    application = this;
    mContext = this;
    Logger.i("Application", "app onCreate this:" + this);
    DeviceInfo.init();
    startService(new Intent(this, ReportService.class));
    ReportArea.init();
    bindService(new Intent(this, DownLoadService.class), this.taskConnection, 1);
    this.taskConnectionState = 1;
    Logger.i("Application", "bindService DownLoadService");
    Logger.i("Application", "SCREEN_WIDTH=" + getResources().getDisplayMetrics().widthPixels + "   SCREEN_HEIGHT=" + getResources().getDisplayMetrics().heightPixels);
    Logger.i("Application", "Device API Level : " + Build.VERSION.SDK_INT);
    int i = getResources().getDisplayMetrics().heightPixels;
    int j = getResources().getDisplayMetrics().widthPixels;
    switch (i)
    {
    default:
      SCREEN_WIDTH = j;
      SCREEN_HEIGHT = i;
    case 672:
    case 1008:
    }
    while (true)
    {
      UITools.init(j, i);
      initXul();
      TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
      GlobalEnv.getInstance().init(this);
      GlobalLogic.getInstance().init(this);
      ServerApiManager.i().init();
      ApiDetectServerApiManager.i().init();
      AIDataCacheTask.i().init(this, 440110);
      AIDataCacheTask.i().setAiLevel(2);
      com.starcor.core.ai.AIDataCacheTask.MediaAssetParam.videoSize = 12;
      com.starcor.core.ai.AIDataCacheTask.MediaAssetParam.specialSize = 50;
      com.starcor.core.ai.AIDataCacheTask.MediaAssetParam.totalCategory = 30;
      com.starcor.core.ai.AIDataCacheTask.MediaAssetParam.categoryType = VideoListActivityV2.REQUEST_STATIC_CATEGORY_TYPE;
      CrashHandler.getInstance();
      SystemTimeManager.getInstance();
      ReportExecutor.getInstance();
      SplashActivity.init();
      startCheckVersion();
      registerMessageObserver();
      return;
      SCREEN_WIDTH = 1280;
      SCREEN_HEIGHT = 720;
      continue;
      SCREEN_WIDTH = 1920;
      SCREEN_HEIGHT = 1080;
    }
  }

  public void onTerminate()
  {
    unregisterMessageObserver();
  }

  public void setOnserviceOKListener(OnServiceOKListener paramOnServiceOKListener)
  {
    if ((this.taskService != null) && (paramOnServiceOKListener != null))
      paramOnServiceOKListener.onServiceOK();
    this.mOnserOkListener = paramOnServiceOKListener;
  }

  public static abstract interface OnServiceOKListener
  {
    public abstract void onServiceOK();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.App
 * JD-Core Version:    0.6.2
 */