package com.huawei.playerinterface;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import com.huawei.dmpbase.DmpBase;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.parameter.HAGetParam;
import com.huawei.playerinterface.parameter.HASetParam;
import com.huawei.playerinterface.parameter.PlayerPara;
import java.io.PrintStream;
import java.util.Arrays;

public class SharkPlayer extends NativePlayer
{
  private static final String LIBSHARKPLAYER = "sharkplayer";
  private static boolean LibLoaded = false;
  public static final int SHARK_NATIVE = 0;
  private static final String TAG = "HAPlayer_SharkPlayerApp";
  private static boolean mIsDebugMode = true;
  private final int MESSAGE_MEDIA_TO_START = 900;
  private String inputUrl;
  private boolean mIsPlayerReleased = false;
  private Handler sharkHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (SharkPlayer.this.mediaPlayer == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 900:
      }
      DmpLog.i("HAPlayer_SharkPlayerApp", "sharkHandler playPara.playPosition: " + SharkPlayer.this.playPara.playPosition);
      SharkPlayer.this.startPlay((String)paramAnonymousMessage.obj);
    }
  };
  private SharkPlayer shark_play = this;

  static
  {
    LibLoaded = false;
    try
    {
      System.loadLibrary("sharkplayer");
      LibLoaded = true;
      Log.i("HAPlayer_SharkPlayerApp", "Succeed to load library sharkplayer.");
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      System.out.println(localUnsatisfiedLinkError.getMessage());
      Log.e("HAPlayer_SharkPlayerApp", "Failed to load library sharkplayer!");
    }
  }

  private void createBitrateMap()
  {
    if (this.playPara.bitrateMap != null);
    while (true)
    {
      return;
      String str = nativeGetBitratesArray();
      DmpLog.d("HAPlayer_SharkPlayerApp", "getBitrateArray:" + str);
      if ((str == null) || (str.equals("")))
      {
        this.playPara.bitrateMap = null;
        return;
      }
      this.playPara.bitrateMap = new SparseArray();
      String[] arrayOfString = str.split("\\f");
      int i = 1;
      this.playPara.playBitrate = Integer.valueOf(arrayOfString[0]).intValue();
      while (i < arrayOfString.length)
        try
        {
          this.playPara.bitrateMap.put(Integer.valueOf(arrayOfString[i]).intValue(), arrayOfString[(i + 1)]);
          i += 2;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          DmpLog.e("HAPlayer_SharkPlayerApp", localNumberFormatException.toString());
        }
    }
  }

  private int getRatioHeight()
  {
    return nativeGetRatioHeight();
  }

  private int getRatioWidth()
  {
    return nativeGetRatioWidth();
  }

  public static native int nativeCreatePlayer(String paramString);

  public static native void nativeDestroyPlayer();

  public static native String nativeGetBitratesArray();

  public static native String nativeGetPlayerUrl();

  public static native int nativeGetRatioHeight();

  public static native int nativeGetRatioWidth();

  public static native void nativeSwitchBitrate(String paramString);

  private void releaseSharkPlayer()
  {
    stopSharkPlayer();
    DmpBase.Close();
  }

  private void sendStartMessage(String paramString)
  {
    Message localMessage = new Message();
    localMessage.what = 900;
    localMessage.obj = paramString;
    this.sharkHandler.sendMessage(localMessage);
  }

  private void setDesignatedBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_SharkPlayerApp", "SharkPlayer -> setDesignatedBitrate() : bitrate = " + paramInt);
    if (this.mediaPlayer == null)
    {
      DmpLog.e("HAPlayer_SharkPlayerApp", "Player -> setDesignatedBitrate() failed, mediaPlayer is null");
      return;
    }
    this.playPara.playBitrate = paramInt;
    this.playPara.playUrl = ((String)this.playPara.bitrateMap.get(this.playPara.playBitrate));
    this.playPara.playPosition = getCurrentPosition();
    if (getDuration() == 0)
      this.playPara.playPosition = 0;
    reset();
    DmpLog.i("HAPlayer_SharkPlayerApp", "SharkPlayer -> setDesignatedBitrate() player reset ");
    stopSharkPlayer();
    prepareAsync();
  }

  private void setMaxBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_SharkPlayerApp", "PowerPlayer -> setMaxBitrate() : maxBitrate = " + paramInt);
  }

  private void setMinBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_SharkPlayerApp", "PowerPlayer -> setMinBitrate() : minBitrate = " + paramInt);
  }

  private void startPlay(String paramString)
  {
    this.playPara.playUrl = paramString;
    this.postionRecorder = 0;
    this.playingNotBuffering = false;
    reset();
    super.setDataSource();
    this.mediaPlayer.setDisplay(this.mSurfaceView.getHolder());
    this.mediaPlayer.prepareAsync();
    getBitrateArray();
    logicStart();
  }

  private void startSharkPlay(String paramString)
  {
    DmpLog.i("HAPlayer_SharkPlayerApp", "startPlay url= " + paramString);
    nativeCreatePlayer(paramString);
  }

  private void stopSharkPlayer()
  {
    DmpLog.i("HAPlayer_SharkPlayerApp", "stopSharkPlayer");
    nativeDestroyPlayer();
  }

  int[] getBitrateArray()
  {
    createBitrateMap();
    if (this.playPara.bitrateMap == null)
      return new int[0];
    int[] arrayOfInt = new int[this.playPara.bitrateMap.size()];
    int i = 0;
    try
    {
      while (i < this.playPara.bitrateMap.size())
      {
        arrayOfInt[i] = this.playPara.bitrateMap.keyAt(i);
        i++;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DmpLog.e("HAPlayer_SharkPlayerApp", localNumberFormatException.toString());
      Arrays.sort(arrayOfInt);
    }
    return arrayOfInt;
  }

  public Object getProperties(HAGetParam paramHAGetParam)
  {
    switch (2.$SwitchMap$com$huawei$playerinterface$parameter$HAGetParam[paramHAGetParam.ordinal()])
    {
    default:
      return super.getProperties(paramHAGetParam);
    case 1:
      return getBitrateArray();
    case 2:
    }
    return Integer.valueOf(this.playPara.playBitrate);
  }

  public void prepare()
  {
    DmpLog.i("HAPlayer_SharkPlayerApp", "sharkplayer prepare");
    startSharkPlay(this.playPara.playUrl);
    new getUrlThread().start();
  }

  public void release()
  {
    super.release();
    releaseSharkPlayer();
  }

  public void seekToOnly(int paramInt)
  {
    DmpLog.i("HAPlayer_SharkPlayerApp", "sharkplayer seekToOnly: " + paramInt + " playType:" + this.playPara.contentType);
    if (this.playPara.mediaDuration == paramInt)
    {
      DmpLog.i("HAPlayer_SharkPlayerApp", "Seek to end of file!");
      paramInt -= 1000;
    }
    switch (this.playPara.contentType)
    {
    default:
      super.seekToOnly(paramInt);
      return;
    case 2:
    }
    DmpLog.w("HAPlayer_SharkPlayerApp", "not support tstv");
  }

  protected void setDataSource()
  {
    DmpBase.Open(this.context);
  }

  public int setProperties(HASetParam paramHASetParam, Object paramObject)
  {
    DmpLog.i("HAPlayer_SharkPlayerApp", "shark -> setProperties() : key = " + paramHASetParam);
    switch (2.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return super.setProperties(paramHASetParam, paramObject);
    case 1:
      if ((paramObject instanceof Integer))
        setMaxBitrate(((Integer)paramObject).intValue());
      break;
    case 2:
    case 3:
    }
    while (true)
    {
      return 1;
      DmpLog.e("HAPlayer_SharkPlayerApp", "shark -> setProperties() ->MAX_BITRATE: failed,value is not Integer");
      return -1;
      if ((paramObject instanceof Integer))
      {
        setMinBitrate(((Integer)paramObject).intValue());
      }
      else
      {
        DmpLog.e("HAPlayer_SharkPlayerApp", "shark -> setProperties() ->MIN_BITRATE: failed,value is not Integer");
        return -1;
        if (!(paramObject instanceof Integer))
          break;
        if (((Integer)paramObject).intValue() == 0)
        {
          DmpLog.i("HAPlayer_SharkPlayerApp", "DESIGNATED_BITRATE value type is wrong");
          return -1;
        }
        setDesignatedBitrate(((Integer)paramObject).intValue());
      }
    }
    DmpLog.e("HAPlayer_SharkPlayerApp", "shark -> setProperties() ->SCALE_MODE: failed,value is not Integer");
    return -1;
  }

  protected void setRatioScale()
  {
    this.playPara.scaleMode = 0;
    if ((getRatioWidth() == 0) || (getRatioHeight() == 0))
    {
      setAutoVideoScree(this.mediaPlayer.getVideoWidth(), this.mediaPlayer.getVideoHeight());
      return;
    }
    setAutoVideoScree(getRatioWidth(), getRatioHeight());
  }

  public void stop()
  {
    super.stop();
    stopSharkPlayer();
  }

  public void suspend()
  {
    super.suspend();
    DmpLog.i("HAPlayer_SharkPlayerApp", "sharkplayer suspend");
    stopSharkPlayer();
  }

  protected class getUrlThread extends Thread
  {
    protected getUrlThread()
    {
    }

    public void run()
    {
      DmpLog.i("HAPlayer_SharkPlayerApp", "getUrlThread run...");
      String str = SharkPlayer.nativeGetPlayerUrl();
      DmpLog.i("HAPlayer_SharkPlayerApp", "getUrlThread url:" + str);
      if ((str != null) && (SharkPlayer.this.mediaPlayer != null))
        SharkPlayer.this.shark_play.sendStartMessage(str);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.SharkPlayer
 * JD-Core Version:    0.6.2
 */