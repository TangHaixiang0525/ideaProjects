package com.huawei.playerinterface;

import android.content.Context;
import android.media.MediaPlayer;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.HACaption.HACaption;
import com.huawei.playerinterface.parameter.HAGetParam;
import com.huawei.playerinterface.parameter.HASetParam;
import com.huawei.playerinterface.parameter.PlayerPara;

public class HSSPlayer extends NativePlayer
{
  private static final int DRM_PARA_CCDATA = 1;
  private static final int DRM_PARA_HEADER = 0;
  private static final int DRM_PARA_LICENSEURL = 2;
  static final String EMCCLB = "SMPC";
  static final String HSSPKLB = "OttHsspk";
  static final String PLAYREADYLB = "OTTPR";
  static final String TAG = "HAPlayer_HSSPlayer";
  private static boolean isStartHssplay;
  private static boolean loadHssPlayerSuccess = true;
  private static boolean loadPlayReadySuccess = true;
  private boolean mHSSPrepared = false;
  private boolean mHaveTextStream = false;
  private boolean mNativePrepared = false;

  static
  {
    isStartHssplay = false;
    try
    {
      System.loadLibrary("OTTPR");
      DmpLog.i("HAPlayer_HSSPlayer", "Succeed to load library OTTPR");
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError2)
    {
      try
      {
        System.loadLibrary("OttHsspk");
        DmpLog.i("HAPlayer_HSSPlayer", "Succeed to load library OttHsspk");
      }
      catch (UnsatisfiedLinkError localUnsatisfiedLinkError2)
      {
        try
        {
          while (true)
          {
            System.loadLibrary("SMPC");
            DmpLog.i("HAPlayer_HSSPlayer", "Succeed to load library SMPC");
            return;
            localUnsatisfiedLinkError1 = localUnsatisfiedLinkError1;
            localUnsatisfiedLinkError1.printStackTrace();
            loadPlayReadySuccess = false;
            DmpLog.e("HAPlayer_HSSPlayer", "Failed to load library OTTPR");
            continue;
            localUnsatisfiedLinkError2 = localUnsatisfiedLinkError2;
            localUnsatisfiedLinkError2.printStackTrace();
            loadPlayReadySuccess = false;
            DmpLog.e("HAPlayer_HSSPlayer", "Failed to load library OttHsspk");
          }
        }
        catch (UnsatisfiedLinkError localUnsatisfiedLinkError3)
        {
          localUnsatisfiedLinkError3.printStackTrace();
          loadHssPlayerSuccess = false;
          DmpLog.e("HAPlayer_HSSPlayer", "Failed to load library SMPC");
        }
      }
    }
  }

  public static String GetVer()
  {
    if (loadPlayReadySuccess)
      return nativeGetHSSPKVer();
    return "not support";
  }

  protected static String[] getAudioTrack()
  {
    if (!loadPlayReadySuccess)
      return null;
    return getStrinArray(nativeGetAuidoTrack(), ",");
  }

  protected static int[] getBandwidthList()
  {
    int[] arrayOfInt;
    if (!loadPlayReadySuccess)
      arrayOfInt = null;
    while (true)
    {
      return arrayOfInt;
      String[] arrayOfString = getStrinArray(nativeGetBandwidthList(), ",");
      arrayOfInt = new int[arrayOfString.length];
      int i = 0;
      try
      {
        while (i < arrayOfString.length)
        {
          if ("".equals(arrayOfString[i]));
          arrayOfInt[i] = Integer.valueOf(arrayOfString[i]).intValue();
          i++;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DmpLog.e("HAPlayer_HSSPlayer", localNumberFormatException.toString());
      }
    }
    return arrayOfInt;
  }

  private static String[] getStrinArray(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.equals("")))
    {
      DmpLog.e("HAPlayer_HSSPlayer", "getStrinArray: have no useful data");
      return new String[0];
    }
    return paramString1.split(paramString2);
  }

  protected static String getTextByPlayTime(int paramInt)
  {
    if (loadPlayReadySuccess)
      return nativeGetTextByPlayTime(paramInt);
    return null;
  }

  protected static String[] getTextInfo()
  {
    if (!loadPlayReadySuccess)
      return null;
    return getStrinArray(nativeGetTextInfo(), ",");
  }

  protected static boolean isLoadSuccess()
  {
    return loadHssPlayerSuccess;
  }

  protected static boolean isPrepared()
  {
    if (isStartHssplay)
      return nativePrepared();
    return false;
  }

  protected static void load()
  {
  }

  protected static native String nativeGetAuidoTrack();

  protected static native String nativeGetBandwidthList();

  protected static native String nativeGetHSSPKVer();

  protected static native int nativeGetRootLicense(String paramString1, String paramString2, String paramString3);

  protected static native String nativeGetTextByPlayTime(int paramInt);

  protected static native String nativeGetTextInfo();

  protected static native int nativeGetTransportErrCode();

  protected static native int nativeInit();

  protected static native int nativeJoinDomain(String paramString1, String paramString2, String paramString3);

  protected static native int nativeLeaveDomain(String paramString1, String paramString2, String paramString3);

  protected static native String nativePlay(String paramString);

  protected static native boolean nativePrepared();

  protected static native void nativeRelease();

  protected static native void nativeSetAudio(String paramString, int paramInt);

  protected static native void nativeSetDrmPara(int paramInt, String paramString);

  protected static native int nativeSetPostion(int paramInt);

  protected static native void nativeSetText(String paramString, int paramInt);

  protected static native void nativeSetTextSwitch(boolean paramBoolean);

  protected static native int nativeStop();

  protected static void setAudio(String paramString, int paramInt)
  {
    if (loadPlayReadySuccess)
      nativeSetAudio(paramString, paramInt);
  }

  protected static void setPlayReadyLicenseCData(String paramString)
  {
    if (loadPlayReadySuccess)
      nativeSetDrmPara(1, paramString);
  }

  protected static void setPlayReadyLicenseHeader(String paramString)
  {
    if (loadPlayReadySuccess)
      nativeSetDrmPara(0, paramString);
  }

  protected static void setPlayReadyLicenseUrl(String paramString)
  {
    if (loadPlayReadySuccess)
      nativeSetDrmPara(2, paramString);
  }

  protected static void setText(String paramString, int paramInt)
  {
    if (loadPlayReadySuccess)
      nativeSetText(paramString, paramInt);
  }

  protected static void setTextSwitchOn(boolean paramBoolean)
  {
    if (loadPlayReadySuccess)
      nativeSetTextSwitch(paramBoolean);
  }

  private void smpcCreate()
  {
    if (!isLoadSuccess())
    {
      DmpLog.e("HAPlayer_HSSPlayer", "smpcCreate fail: load so fail");
      return;
    }
    if (!isStartHssplay)
    {
      isStartHssplay = true;
      nativeInit();
      DmpLog.i("HAPlayer_HSSPlayer", "smpcCreate success");
      return;
    }
    DmpLog.e("HAPlayer_HSSPlayer", "smpcCreate fail already start hssplayer");
  }

  private void smpcDestroy()
  {
    if (isStartHssplay)
    {
      nativeStop();
      nativeRelease();
      isStartHssplay = false;
      DmpLog.i("HAPlayer_HSSPlayer", "destroy");
      return;
    }
    DmpLog.w("HAPlayer_HSSPlayer", "destroy: hssplayer was not started");
  }

  private String smpcGetUrl(String paramString)
  {
    if (!isLoadSuccess())
      return null;
    if (isStartHssplay)
    {
      DmpLog.i("HAPlayer_HSSPlayer", "create url: " + paramString);
      return nativePlay(paramString);
    }
    DmpLog.e("HAPlayer_HSSPlayer", "create fail not started yet");
    return null;
  }

  private void smpcRelease()
  {
    if (isStartHssplay)
    {
      nativeRelease();
      isStartHssplay = false;
      DmpLog.i("HAPlayer_HSSPlayer", "release");
      return;
    }
    DmpLog.w("HAPlayer_HSSPlayer", "release: hssplayer was not started");
  }

  private void smpcSeekTo(int paramInt)
  {
    if (isStartHssplay)
      nativeSetPostion(paramInt);
  }

  private void smpcStop()
  {
    if (isStartHssplay)
    {
      nativeStop();
      DmpLog.i("HAPlayer_HSSPlayer", "stop");
      return;
    }
    DmpLog.w("HAPlayer_HSSPlayer", "stop: hssplayer was not started");
  }

  public Object getProperties(HAGetParam paramHAGetParam)
  {
    DmpLog.i("HAPlayer_HSSPlayer", "getProperties:" + paramHAGetParam);
    switch (1.$SwitchMap$com$huawei$playerinterface$parameter$HAGetParam[paramHAGetParam.ordinal()])
    {
    default:
      return super.getProperties(paramHAGetParam);
    case 1:
      return getBandwidthList();
    case 2:
      return Integer.valueOf(this.playPara.playBitrate);
    case 3:
      return getAudioTrack();
    case 4:
    }
    return getTextInfo();
  }

  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    DmpLog.i("HAPlayer_HSSPlayer", "onPrepared mHSSPrepared:" + this.mHSSPrepared + " mNativePrepared " + this.mNativePrepared);
    if (!this.mHSSPrepared)
    {
      this.mNativePrepared = true;
      return;
    }
    super.onPrepared(paramMediaPlayer);
  }

  public void release()
  {
    super.release();
    smpcDestroy();
  }

  public void seekToOnly(int paramInt)
  {
    int i = paramInt;
    DmpLog.i("HAPlayer_HSSPlayer", "seekToOnly");
    if (this.playPara.mediaDuration == paramInt)
    {
      DmpLog.i("HAPlayer_HSSPlayer", "Seek to end of file!");
      i -= 1000;
    }
    switch (this.playPara.contentType)
    {
    case 1:
    default:
    case 2:
    case 0:
    }
    while (true)
    {
      super.seekToOnly(i);
      return;
      this.playPara.pauseTimeRecorder = 0L;
      this.playPara.tvSeekTime = (this.playPara.mediaDuration - paramInt);
      this.playPara.playPosition = getCurrentPosition();
      smpcSeekTo(this.playPara.tvSeekTime);
      continue;
      smpcSeekTo(i);
    }
  }

  public void setDataSource(Context paramContext, String paramString)
  {
    this.mNativePrepared = false;
    this.mHSSPrepared = false;
    smpcCreate();
    this.playPara.playUrl = smpcGetUrl(paramString);
    setTextSwitchOn(true);
    setNeedUpdatePreparedState(true);
    super.setDataSource(paramContext, this.playPara.playUrl);
  }

  public int setProperties(HASetParam paramHASetParam, Object paramObject)
  {
    DmpLog.i("HAPlayer_HSSPlayer", "shark -> setProperties() : key = " + paramHASetParam);
    switch (1.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return super.setProperties(paramHASetParam, paramObject);
    case 1:
      if ((paramObject instanceof String))
      {
        setAudio((String)paramObject, getCurrentPosition());
        seekToOnly(getCurrentPosition());
        DmpLog.i("HAPlayer_HSSPlayer", " setProperties() ->AUDIO_TRACK:" + paramObject);
      }
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return 0;
      DmpLog.e("HAPlayer_HSSPlayer", " setProperties() ->AUDIO_TRACK: failed,value is not String");
      return -1;
      if ((paramObject instanceof String))
      {
        setText((String)paramObject, getCurrentPosition());
        DmpLog.i("HAPlayer_HSSPlayer", " setProperties() ->SUBTITLES_TRACK:" + paramObject);
      }
      else
      {
        DmpLog.e("HAPlayer_HSSPlayer", " setProperties() ->SUBTITLES_TRACK: failed,value is not String");
        return -1;
        if ((paramObject instanceof String))
        {
          setPlayReadyLicenseHeader((String)paramObject);
          DmpLog.i("HAPlayer_HSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_HEADER:" + paramObject);
        }
        else
        {
          DmpLog.e("HAPlayer_HSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_HEADER: failed,value is not String");
          return -1;
          if ((paramObject instanceof String))
          {
            setPlayReadyLicenseCData((String)paramObject);
            DmpLog.i("HAPlayer_HSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_CDATA:" + paramObject);
          }
          else
          {
            DmpLog.e("HAPlayer_HSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_CDATA: failed,value is not String");
            return -1;
            if (!(paramObject instanceof String))
              break;
            setPlayReadyLicenseUrl((String)paramObject);
            DmpLog.i("HAPlayer_HSSPlayer", " setProperties() ->PLAYREADY_LICENSE_SERVER_URL:" + paramObject);
          }
        }
      }
    }
    DmpLog.e("HAPlayer_HSSPlayer", " setProperties() ->PLAYREADY_LICENSE_SERVER_URL: failed,value is not String");
    return -1;
  }

  public void start()
  {
    this.playerCaption.start();
    if (this.playPara.historyPlayPoint > 0)
      smpcSeekTo(this.playPara.historyPlayPoint);
    super.start();
  }

  protected void updateCaption()
  {
    if (this.playerCaption == null)
      setNeedUpdateCaption(false);
    while (!this.playerCaption.isShowEnable())
      return;
    String str = getTextByPlayTime(getCurrentPosition() + PlayerLogic.getPlayerSysTick());
    if (this.playingNotBuffering)
    {
      if (str.length() != 0)
        break label78;
      if (isPlaying())
      {
        this.mediaPlayer.pause();
        onBufferingUpdate(null, 0);
      }
    }
    while (true)
    {
      this.playerCaption.showCaptionMessage(str);
      return;
      label78: if ((this.playPara.inPlayState) && (!isPlaying()))
      {
        this.mediaPlayer.start();
        onBufferingUpdate(null, 100);
      }
    }
  }

  protected void updatePreparedState()
  {
    if (!isPrepared())
      return;
    DmpLog.i("HAPlayer_HSSPlayer", "updatePreparedState mHSSPrepared:" + this.mHSSPrepared + " mNativePrepared " + this.mNativePrepared);
    if ((getTextInfo().length > 0) && (this.playerCaption != null))
      setNeedUpdateCaption(true);
    while (true)
    {
      setNeedUpdatePreparedState(false);
      if (!this.mHSSPrepared)
        this.mHSSPrepared = true;
      if (!this.mNativePrepared)
        break;
      onPrepared(this.mediaPlayer);
      return;
      setNeedUpdateCaption(false);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.HSSPlayer
 * JD-Core Version:    0.6.2
 */