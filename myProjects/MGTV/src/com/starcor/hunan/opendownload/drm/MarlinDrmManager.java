package com.starcor.hunan.opendownload.drm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.Runtime;
import com.intertrust.wasabi.Runtime.Property;
import com.intertrust.wasabi.drm.Engine;
import com.intertrust.wasabi.media.PlaylistProxy;
import com.intertrust.wasabi.media.PlaylistProxy.MediaSourceParams;
import com.intertrust.wasabi.media.PlaylistProxy.MediaSourceType;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarlinDrmManager
{
  private static final String TAG = MarlinDrmManager.class.getSimpleName();
  private static final MarlinDrmManager mManger = new MarlinDrmManager();
  private SampleHTTPProxy httpProxy;
  private Runnable initTask = new Runnable()
  {
    public void run()
    {
      try
      {
        Logger.w(MarlinDrmManager.TAG, "marlin initTask, start time:" + System.currentTimeMillis());
        Runtime.setProperty(Runtime.Property.ROOTED_OK, Boolean.valueOf(true));
        Runtime.initialize(App.getAppContext().getDir("wasabi", 0).getAbsolutePath());
        Engine localEngine = new Engine();
        if (!localEngine.isPersonalized())
        {
          Runtime.personalize();
          Logger.d(MarlinDrmManager.TAG, "marlin initTask, Runtime.personalize succeed!");
        }
        localEngine.destroy();
        MarlinDrmManager.access$102(MarlinDrmManager.this, new PlaylistProxy());
        MarlinDrmManager.this.playlistProxy.start();
        MarlinDrmManager.access$202(MarlinDrmManager.this, true);
        Logger.w(MarlinDrmManager.TAG, "marlin initTask, end time:" + System.currentTimeMillis());
        return;
      }
      catch (NullPointerException localNullPointerException)
      {
        Logger.e(MarlinDrmManager.TAG, localNullPointerException.getLocalizedMessage(), localNullPointerException);
        return;
      }
      catch (ErrorCodeException localErrorCodeException)
      {
        Logger.e(MarlinDrmManager.TAG, localErrorCodeException.getLocalizedMessage(), localErrorCodeException);
      }
    }
  };
  private boolean initialized;
  private PlaylistProxy playlistProxy;
  private ExecutorService pool;

  private MarlinDrmManager()
  {
    try
    {
      this.httpProxy = new SampleHTTPProxy();
      this.httpProxy.start();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private int checkTaskValidity(PlayUrlTask paramPlayUrlTask)
  {
    PlayUrlParams localPlayUrlParams = paramPlayUrlTask.params;
    if (localPlayUrlParams == null)
    {
      Logger.i(TAG, "checkTaskValidity PlayUrlParams is null");
      return -1;
    }
    if (localPlayUrlParams.getDrmGetUrlCallback() == null)
    {
      Logger.i(TAG, "checkTaskValidity DrmGetUrlCallback is null");
      return -1;
    }
    if (TextUtils.isEmpty(localPlayUrlParams.getPlayUrl()))
    {
      Logger.i(TAG, "checkTaskValidity getPlayUrl is empty");
      return -1;
    }
    if (TextUtils.isEmpty(localPlayUrlParams.getIdxUrl()))
    {
      Logger.i(TAG, "checkTaskValidity getIdxUrl is empty");
      return -1;
    }
    if (TextUtils.isEmpty(localPlayUrlParams.getActionToken()))
    {
      Logger.i(TAG, "checkTaskValidity getActionToken is empty");
      return -1;
    }
    return 0;
  }

  public static MarlinDrmManager getInstance()
  {
    try
    {
      MarlinDrmManager localMarlinDrmManager = mManger;
      return localMarlinDrmManager;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void destroy(Context paramContext)
  {
    if (!this.initialized)
    {
      Logger.w(TAG, "destroy Uninitialized exception, call init()");
      return;
    }
    try
    {
      this.initialized = false;
      this.playlistProxy.stop();
      this.playlistProxy = null;
      Runtime.shutdown();
      this.pool.shutdownNow();
      this.pool = null;
      Logger.i(TAG, "mEngine destory");
      return;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, localException.getLocalizedMessage(), localException);
    }
  }

  public void init()
  {
    Logger.d(TAG, "MarlinDrmManager init");
    this.pool = Executors.newSingleThreadExecutor();
    this.pool.execute(this.initTask);
  }

  public void requestPlayUrlByDRM(PlayInfoV2 paramPlayInfoV2, DrmGetUrlCallback paramDrmGetUrlCallback)
  {
    PlayUrlTask localPlayUrlTask = new PlayUrlTask(new PlayUrlParams(paramPlayInfoV2, paramDrmGetUrlCallback));
    if (!this.initialized)
      Logger.w(TAG, "requestPlayUrlByDRM Uninitialized exception, call init()");
    while (checkTaskValidity(localPlayUrlTask) != 0)
      return;
    this.pool.execute(localPlayUrlTask);
  }

  public class PlayUrlTask
    implements Runnable
  {
    final PlayUrlParams params;

    public PlayUrlTask(PlayUrlParams arg2)
    {
      Object localObject;
      this.params = localObject;
    }

    private void acquireLicenseByToken(String paramString)
    {
      if (!MarlinDrmManager.this.initialized)
      {
        Logger.w(MarlinDrmManager.TAG, "acquireLicenseByToken Uninitialized exception, call init()");
        return;
      }
      Logger.d(MarlinDrmManager.TAG, "acquireLicenseByToken token: " + paramString);
      try
      {
        Engine localEngine = new Engine();
        localEngine.addTransactionListener(new MarlinTransactionListener());
        localEngine.processServiceToken(paramString);
        localEngine.destroy();
        return;
      }
      catch (ErrorCodeException localErrorCodeException)
      {
        Logger.e(MarlinDrmManager.TAG, localErrorCodeException.getLocalizedMessage(), localErrorCodeException);
        return;
      }
      catch (Exception localException)
      {
        Logger.e(MarlinDrmManager.TAG, localException.getLocalizedMessage(), localException);
      }
    }

    public void run()
    {
      com.starcor.config.AppFuncCfg.FUNCTION_OTTTV_PROXY = false;
      String str1 = this.params.getActionToken();
      String str2 = this.params.getCid();
      if (TextUtils.isEmpty(str2))
      {
        Logger.e(MarlinDrmManager.TAG, "PlayUrlTask, cid is null!");
        return;
      }
      try
      {
        Runtime.checkLicense(str2);
        bool = true;
        Logger.d(MarlinDrmManager.TAG, "hasLicense=" + bool);
        if (!bool)
          acquireLicenseByToken(str1);
        localMediaSourceType = PlaylistProxy.MediaSourceType.SINGLE_FILE;
        if ("HLS".equals(this.params.getSourceType()))
          localMediaSourceType = PlaylistProxy.MediaSourceType.HLS;
        if ("DASH".equals(this.params.getSourceType()))
          localMediaSourceType = PlaylistProxy.MediaSourceType.DASH;
        localMediaSourceParams = new PlaylistProxy.MediaSourceParams();
        localMediaSourceParams.sourceContentType = this.params.getSourceContentType();
        if ("TS2".equals(this.params.getSourceType()))
          localMediaSourceParams.bbtsIndexUrl = this.params.getIdxUrl();
        i = 200;
        str3 = "";
      }
      catch (ErrorCodeException localErrorCodeException)
      {
        try
        {
          PlaylistProxy.MediaSourceType localMediaSourceType;
          PlaylistProxy.MediaSourceParams localMediaSourceParams;
          String str3;
          PlaylistProxy localPlaylistProxy = MarlinDrmManager.this.playlistProxy;
          if (localPlaylistProxy != null)
          {
            str3 = localPlaylistProxy.makeUrl(this.params.getPlayUrl(), localMediaSourceType, localMediaSourceParams);
            Logger.i(MarlinDrmManager.TAG, "PlayUrlTask.run, drmPlayUrl:" + str3);
          }
          final int j = i;
          final String str4 = str3;
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public void run()
            {
              MarlinDrmManager.PlayUrlTask.this.params.getDrmGetUrlCallback().onDrmGetUrl(j, str4);
            }
          });
          return;
          localErrorCodeException = localErrorCodeException;
          boolean bool = false;
        }
        catch (Exception localException)
        {
          while (true)
          {
            int i = -1;
            Logger.e(MarlinDrmManager.TAG, localException.getLocalizedMessage(), localException);
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.drm.MarlinDrmManager
 * JD-Core Version:    0.6.2
 */