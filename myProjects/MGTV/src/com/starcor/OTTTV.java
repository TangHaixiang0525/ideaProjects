package com.starcor;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import java.io.File;
import java.io.InputStream;

public class OTTTV
{
  public static boolean Debug = false;
  private static final String TAG = "OTTTV";
  private static boolean _isInitialized = false;
  public static final String port;

  static
  {
    if (AppFuncCfg.FUNCTION_OTTTV_PROXY);
    try
    {
      System.loadLibrary("_All_imgoTV_nn_tv_client");
      if ((DeviceInfo.isDFIM()) || (DeviceInfo.isYingChiGAL_A01()))
      {
        port = "7778";
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
      if (DeviceInfo.isTCL_RT2995())
      {
        port = "7878";
        return;
      }
      port = "7777";
    }
  }

  public static void getStreamInfo(final Handler paramHandler, final int paramInt1, int paramInt2)
  {
    if (paramHandler == null)
      return;
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          HttpStack localHttpStack = new HttpStack();
          InputStream localInputStream = localHttpStack.getInputStreamFromURL(this.val$url);
          Message localMessage = new Message();
          localMessage.what = paramInt1;
          GetStreamInfoResult localGetStreamInfoResult = null;
          if (localInputStream != null)
          {
            localGetStreamInfoResult = new StreamInfoResultParser().parser(localInputStream);
            localMessage.obj = localGetStreamInfoResult;
          }
          if (localGetStreamInfoResult == null)
            localGetStreamInfoResult = new GetStreamInfoResult();
          localGetStreamInfoResult.httpcode = localHttpStack.getStatusCode();
          paramHandler.sendMessage(localMessage);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }).start();
  }

  public static boolean haveSdcard()
  {
    try
    {
      boolean bool1 = Environment.getExternalStorageState().equals("mounted");
      boolean bool2 = false;
      if (bool1)
        bool2 = true;
      return bool2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static int init(Context paramContext)
  {
    if (_isInitialized)
      return 0;
    _isInitialized = true;
    String str = "";
    if (haveSdcard())
      str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "nn_tv_log" + File.separator;
    if (!new File(str).isDirectory())
      str = GlobalEnv.getInstance().getLogPath();
    Logger.i("OTTTV", "start logPath:" + str);
    boolean bool = AppFuncCfg.FUNCTION_OTTTV_PROXY;
    int i = 0;
    if (bool)
      i = start(Integer.parseInt(port), 25, str, "mgtv_" + DeviceInfo.getFactory(), "");
    if (i != 0)
      Logger.e("OTTTV", "native start ret:" + i);
    Logger.i("OTTTV", "启动中间件：" + i);
    return i;
  }

  public static int shutdown()
  {
    if ((AppFuncCfg.FUNCTION_OTTTV_PROXY) && (_isInitialized))
      return stop();
    return -1;
  }

  public static native int start(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3);

  public static native int stop();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.OTTTV
 * JD-Core Version:    0.6.2
 */