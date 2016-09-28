package com.huawei.playerinterface;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import com.huawei.PEPlayerInterface.OnPEPlayerEventListener;
import com.huawei.PEPlayerInterface.PEAspectRatio;
import com.huawei.PEPlayerInterface.PEDisplay;
import com.huawei.PEPlayerInterface.PEError;
import com.huawei.PEPlayerInterface.PEOttCaConfig;
import com.huawei.PEPlayerInterface.PEPlayer;
import com.huawei.PEPlayerInterface.PEVideoInfo;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.entity.DRMInfo;
import com.huawei.playerinterface.parameter.HAGetParam;
import com.huawei.playerinterface.parameter.HASetParam;
import com.huawei.playerinterface.parameter.PlayerPara;
import com.huawei.playerinterface.parameter.ProxyPara;
import com.huawei.playerinterface.version.PlayerVersion;
import com.huawei.so.OTTProxy;
import java.io.IOException;
import java.util.Locale;

public class PowerPlayer extends PlayerLogic
  implements IHAPlayer, OnPEPlayerEventListener, SurfaceHolder.Callback
{
  protected static final int HARDWARE_DECODE = 2;
  protected static final int SOFTWARE_OPENGL_DECODE = 0;
  protected static final int SOFTWARE_SKIA_DECODER = 1;
  private static final String TAG = "HAPlayer_PowerPlayer";
  protected static final int UNKNOWN_DECODE = -1;
  private static String lock = "LOCKABLE";
  private final int defaultBufferSize = 800;
  private Handler eventHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1:
      case 2:
      case 3:
      case 100:
      case 200:
      case 4:
      case 5:
      case 6:
      }
      int i;
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                      return;
                    while (PowerPlayer.this.mOnPreparedListener == null);
                    if (!PowerPlayer.this.playPara.isInternalStart)
                    {
                      DmpLog.d("HAPlayer_PowerPlayer", "handleMessage HAInternalMessage.HA_MESSAGE_PREPARED");
                      PowerPlayer.this.mOnPreparedListener.onPrepared(PowerPlayer.this);
                      PowerPlayer.this.playPara.isInternalStart = true;
                      return;
                    }
                    DmpLog.d("HAPlayer_PowerPlayer", "handleMessage HAInternalMessage.HA_MESSAGE_PREPARED but internal state, needn't send msg");
                    return;
                  }
                  while (PowerPlayer.this.mOnCompletionListener == null);
                  PowerPlayer.this.mOnCompletionListener.onCompletion(PowerPlayer.this);
                  DmpLog.d("HAPlayer_PowerPlayer", "handleMessage HAInternalMessage.HA_MESSAGE_PLAYBACK_COMPLETE");
                  return;
                }
                while (PowerPlayer.this.mOnBufferingUpdateListener == null);
                DmpLog.i("HAPlayer_PowerPlayer", "handleMessage HAInternalMessage.HA_MESSAGE_BUFFERING_UPDATE, buffering:" + paramAnonymousMessage.arg1);
                PowerPlayer.this.mOnBufferingUpdateListener.onBufferingUpdate(PowerPlayer.this, Integer.valueOf(paramAnonymousMessage.arg1).intValue());
                return;
                if (PowerPlayer.this.mOnErrorListener == null)
                  break;
                DmpLog.e("HAPlayer_PowerPlayer", "handleMessage HAInternalMessage.HA_MESSAGE_ERROR, ErrType = " + Integer.valueOf(paramAnonymousMessage.arg1) + ", ErrCode = " + Integer.valueOf(paramAnonymousMessage.arg2) + " errReportCnt:" + PowerPlayer.this.playPara.errReportCnt);
              }
              while (PowerPlayer.this.playPara.errReportCnt != 0);
              PowerPlayer.this.playPara.errReportCnt = 1;
              PowerPlayer.this.mOnErrorListener.onError(PowerPlayer.this, paramAnonymousMessage.arg1, Integer.valueOf(paramAnonymousMessage.arg2).intValue());
              return;
              DmpLog.e("HAPlayer_PowerPlayer", "HAInternalMessage.HA_MESSAGE_ERROR but mOnErrorListener is null!");
              return;
            }
            while (PowerPlayer.this.mOnInfoListener == null);
            DmpLog.d("HAPlayer_PowerPlayer", "handleMessage HAInternalMessage.HA_MESSAGE_INFO, InfoType = " + Integer.valueOf(paramAnonymousMessage.arg1) + ", InfoCode = " + Integer.valueOf(paramAnonymousMessage.arg2));
            PowerPlayer.this.mOnInfoListener.onInfo(PowerPlayer.this, Integer.valueOf(paramAnonymousMessage.arg1).intValue(), Integer.valueOf(paramAnonymousMessage.arg2).intValue());
            return;
          }
          while (PowerPlayer.this.mOnSeekCompleteListener == null);
          PowerPlayer.this.mOnSeekCompleteListener.onSeekComplete(PowerPlayer.this);
          DmpLog.d("HAPlayer_PowerPlayer", "handleMessage seek complete");
          return;
        }
        while (PowerPlayer.this.mOnVideoSizeChangedListener == null);
        DmpLog.d("HAPlayer_PowerPlayer", "video size, width = " + Integer.valueOf(paramAnonymousMessage.arg1) + ", height = " + Integer.valueOf(paramAnonymousMessage.arg2));
        PowerPlayer.this.setVideoScalingMode(PowerPlayer.this.playPara.scaleMode);
        PowerPlayer.this.mOnVideoSizeChangedListener.onVideoSizeChanged(PowerPlayer.this, Integer.valueOf(paramAnonymousMessage.arg1).intValue(), Integer.valueOf(paramAnonymousMessage.arg2).intValue());
        return;
        i = PowerPlayer.this.getCurrentPosition();
      }
      while (i <= 0);
      PowerPlayer.this.playPara.playPosition = i;
    }
  };
  private boolean firstPlay = true;
  DRMInfo mDRM = new DRMInfo();
  private IHAPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
  private IHAPlayer.OnCompletionListener mOnCompletionListener;
  private IHAPlayer.OnErrorListener mOnErrorListener;
  private IHAPlayer.OnInfoListener mOnInfoListener;
  private IHAPlayer.OnPreparedListener mOnPreparedListener;
  private IHAPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
  private IHAPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
  protected PEPlayer pePlayer;

  public PowerPlayer(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " PowerPlayer(): decodeType = " + paramInt);
    this.playPara.errReportCnt = 0;
    this.playPara.isInternalStart = false;
    this.playPara.mediaDuration = 0;
    this.playPara.decodeType = paramInt;
    this.playPara.historyPlayPoint = -1;
    this.useProxy = 1;
  }

  private boolean initPEPlayer()
  {
    if (this.pePlayer != null)
    {
      this.pePlayer.PEPlayer_Stop();
      this.pePlayer.PEPlayer_Release();
    }
    this.pePlayer = PEPlayer.PEPlayer_Init(this);
    return this.pePlayer != null;
  }

  private void onError(int paramInt1, int paramInt2, int paramInt3)
  {
    Message localMessage = new Message();
    localMessage.what = paramInt1;
    localMessage.arg1 = paramInt2;
    localMessage.arg1 = paramInt3;
    this.eventHandler.sendMessage(localMessage);
  }

  private void onRenderSkia()
  {
    if (this.pePlayer != null)
      this.pePlayer.PEPlayer_Do(492159960, this.mSurfaceView.getHolder());
  }

  private void restartPlayer(boolean paramBoolean)
  {
    DmpLog.i("HAPlayer_PowerPlayer", "restartPlayer content type:" + this.playPara.contentType);
    this.playPara.errReportCnt = 0;
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "restartPlayer err: pePlayer is null");
      return;
    }
    this.pePlayer.PEPlayer_SetUrl(this.proxyPara.playUrl);
    setDRMInfo(this.mDRM);
    setPEPara();
    setConfigInfo(this.mSurfaceView.getHolder());
    if (paramBoolean)
      this.pePlayer.PEPlayer_SetParam(558728097, this.mSurfaceView.getHolder().getSurface());
    if (this.playPara.contentType == 0)
    {
      this.pePlayer.PEPlayer_SetParam(208754334, Integer.valueOf(this.playPara.playPosition));
      DmpLog.i("HAPlayer_PowerPlayer", "restartPlayer vod, play position:" + this.playPara.playPosition);
    }
    this.pePlayer.PEPlayer_Start();
  }

  private void setConfigInfo(SurfaceHolder paramSurfaceHolder)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setConfigInfo()");
    if ((this.pePlayer == null) || (paramSurfaceHolder == null))
    {
      if (this.pePlayer == null)
        DmpLog.e("HAPlayer_PowerPlayer", "setConfigInfo(): pePlayer is null");
      if (paramSurfaceHolder == null)
        DmpLog.e("HAPlayer_PowerPlayer", "setConfigInfo(): surfaceHolder is null");
    }
    while (this.playPara.decodeType == 2)
      return;
    Rect localRect = paramSurfaceHolder.getSurfaceFrame();
    PEDisplay localPEDisplay = new PEDisplay();
    localPEDisplay.pixFormat = 474268352;
    localPEDisplay.x = 0;
    localPEDisplay.y = 0;
    localPEDisplay.width = (localRect.right - localRect.left);
    localPEDisplay.height = (localRect.bottom - localRect.top);
    DmpLog.d("HAPlayer_PowerPlayer", "setConfigInfo():PESetConfig.PE_CONFIG_SET_VIDEO_DISPLAY, pixFormat=" + localPEDisplay.pixFormat + " width=" + localPEDisplay.width + " height=" + localPEDisplay.height);
    this.pePlayer.PEPlayer_SetParam(239982230, localPEDisplay);
    this.pePlayer.PEPlayer_RedrawFrame();
  }

  private void setDRMInfo(DRMInfo paramDRMInfo)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setDRMInfo()");
    if (paramDRMInfo == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "setDRMInfo(): fail,mDRMInfo is null ");
      return;
    }
    if ((paramDRMInfo.getDRMType() != null) && (!paramDRMInfo.getDRMType().equals("Verimatrix")))
    {
      DmpLog.e("HAPlayer_PowerPlayer", "setDRMInfo():  failed,DRMConfig.DRMType = " + paramDRMInfo.getDRMType());
      return;
    }
    if (this.pePlayer != null)
    {
      if ((TextUtils.isEmpty(this.mDRM.getServer())) || (TextUtils.isEmpty(this.mDRM.getCompanyName())) || (TextUtils.isEmpty(this.mDRM.getDeviceId())) || (TextUtils.isEmpty(this.mDRM.getLogPath())))
      {
        DmpLog.e("HAPlayer_PowerPlayer", "setDRMInfo(): failed, args exist null");
        return;
      }
      PEOttCaConfig localPEOttCaConfig = new PEOttCaConfig();
      localPEOttCaConfig.server = this.mDRM.getServer();
      localPEOttCaConfig.companyName = this.mDRM.getCompanyName();
      localPEOttCaConfig.deviceId = this.mDRM.getDeviceId();
      localPEOttCaConfig.storePath = this.mDRM.getLogPath();
      DmpLog.d("HAPlayer_PowerPlayer", "setDRMInfo():server=" + localPEOttCaConfig.server + ", companyName=" + localPEOttCaConfig.companyName + ", deviceId=" + localPEOttCaConfig.deviceId + ", storePath=" + localPEOttCaConfig.storePath);
      this.pePlayer.PEPlayer_SetParam(244181210, "ott");
      this.pePlayer.PEPlayer_SetParam(244163292, localPEOttCaConfig);
      return;
    }
    DmpLog.e("HAPlayer_PowerPlayer", "setDRMInfo(): failed,peplayer is null");
  }

  private void setDesignatedBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " setDesignatedBitrate() : bitrate = " + paramInt);
    if (this.pePlayer != null)
    {
      if ((1 < this.pePlayer.PEPlayer_GetState()) && (this.playPara.contentType == 0))
      {
        if (paramInt == 0)
        {
          this.pePlayer.PEPlayer_SetParam(210073166, Integer.valueOf(0));
          return;
        }
        this.pePlayer.PEPlayer_SetParam(210073166, Integer.valueOf(1));
        this.pePlayer.PEPlayer_Switch(204309409, Integer.valueOf(paramInt));
      }
    }
    else
      DmpLog.e("HAPlayer_PowerPlayer", "setDesignatedBitrate fail: pePlayer is null");
    if (this.playProxy == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "Player -> setDesignatedBitrate() failed: playproxy is null");
      return;
    }
    this.proxyPara.fixBandwidth = paramInt;
    DmpLog.d("HAPlayer_PowerPlayer", " setDesignatedBitrate(),peplayer state = " + this.pePlayer.PEPlayer_GetState());
    if (1 >= this.pePlayer.PEPlayer_GetState())
    {
      DmpLog.d("HAPlayer_PowerPlayer", " setDesignatedBitrate(),peplayer state is PEState.PE_STATE_INIT,set proxySetFixBitrate only");
      this.playProxy.proxySetFixBitrate(paramInt);
      return;
    }
    this.pePlayer.PEPlayer_Stop();
    this.playProxy.proxySetFixBitrate(paramInt);
    if (this.playPara.contentType == 2)
      this.playProxy.proxySeek(this.playPara.tvSeekTime);
    restartPlayer(false);
  }

  private void setMaxBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " setMaxBitrate() : maxBitrate = " + paramInt);
    if (this.playProxy != null)
      this.playProxy.proxySetFilterBitrate(this.proxyPara.minBitrate, paramInt);
    while (true)
    {
      this.proxyPara.maxBitrate = paramInt;
      return;
      if (this.pePlayer != null)
        this.pePlayer.PEPlayer_SetParam(388899617, Integer.valueOf(paramInt));
      else
        DmpLog.e("HAPlayer_PowerPlayer", "pePlayer is null!");
    }
  }

  private void setMinBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " setMinBitrate() : minBitrate = " + paramInt);
    if (this.playProxy != null)
      this.playProxy.proxySetFilterBitrate(paramInt, this.proxyPara.maxBitrate);
    this.proxyPara.minBitrate = paramInt;
  }

  private void setPEPara()
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setPEPara() decodeType:" + this.playPara.decodeType);
    if (Build.VERSION.SDK_INT < 9)
    {
      DmpLog.d("HAPlayer_PowerPlayer", "setPEPara() Android skd:" + Build.VERSION.SDK + "use AUDIOTRACK");
      this.pePlayer.PEPlayer_SetParam(188318423, "AUDIOTRACK");
    }
    while (this.playPara.decodeType == 2)
    {
      this.pePlayer.PEPlayer_SetParam(541725389, Integer.valueOf(1));
      this.pePlayer.PEPlayer_SetParam(540639959, "STAGEFRIGHT");
      return;
      DmpLog.d("HAPlayer_PowerPlayer", "setPEPara() Android skd:" + Build.VERSION.SDK + "use OPENSLES");
      this.pePlayer.PEPlayer_SetParam(188318423, "OPENSLES");
    }
    if (this.playPara.decodeType == 0)
    {
      this.pePlayer.PEPlayer_SetParam(540639959, "OPENGLES");
      return;
    }
    this.pePlayer.PEPlayer_SetParam(540639959, "SKIA");
  }

  private int setVideoAspectRatio(int paramInt1, int paramInt2, int paramInt3)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "PowerPlayer - > setVideoAspectRatio(): type = " + paramInt1);
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "PowerPlayer - > setVideoAspectRatio() : failed, pePlayer == null");
      return -1;
    }
    PEAspectRatio localPEAspectRatio = new PEAspectRatio();
    localPEAspectRatio.mode = paramInt1;
    localPEAspectRatio.heightRatio = paramInt2;
    localPEAspectRatio.widthRatio = paramInt3;
    return this.pePlayer.PEPlayer_SetParam(192259021, localPEAspectRatio);
  }

  private void setVideoScale(int paramInt1, int paramInt2, int paramInt3)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setVideoScale(): type = " + paramInt1 + ", width = " + paramInt2 + ", height = " + paramInt3);
    if (paramInt1 == 0)
      if (this.pePlayer == null)
        DmpLog.e("HAPlayer_PowerPlayer", "setVideoScale(): failed,pePlayer is null");
    label133: label148: 
    while (this.playPara == null)
    {
      return;
      if (this.mSurfaceView == null)
      {
        DmpLog.e("HAPlayer_PowerPlayer", "setVideoScale(): failed,mSurfaceView is null");
        return;
      }
      DmpLog.d("HAPlayer_PowerPlayer", "setVideoScale() ->: pePlayer.PEPlayer_GetInfo(PEGetConfig.PE_CONFIG_GET_VIDEO_INFO)");
      PEVideoInfo localPEVideoInfo = (PEVideoInfo)this.pePlayer.PEPlayer_GetInfo(541951001);
      if (localPEVideoInfo == null)
      {
        DmpLog.e("HAPlayer_PowerPlayer", "setVideoScale(): failed,videoInfo is null");
        return;
      }
      int i;
      int j;
      ViewGroup.LayoutParams localLayoutParams2;
      if (localPEVideoInfo.displayWidthRatio == 0)
      {
        i = localPEVideoInfo.width;
        if (localPEVideoInfo.displayHeightRatio != 0)
          break label260;
        j = localPEVideoInfo.height;
        if ((j == 0) || (this.playPara == null) || (this.playPara.screenHeight == 0))
          break label268;
        localLayoutParams2 = this.mSurfaceView.getLayoutParams();
        if (i * this.playPara.screenHeight <= j * this.playPara.screenWidth)
          break label270;
        localLayoutParams2.height = (2 * (j * this.playPara.screenWidth / i / 2));
      }
      for (localLayoutParams2.width = (2 * (this.playPara.screenWidth / 2)); ; localLayoutParams2.width = (2 * (i * this.playPara.screenHeight / j / 2)))
      {
        this.mSurfaceView.setLayoutParams(localLayoutParams2);
        return;
        i = localPEVideoInfo.displayWidthRatio;
        break label133;
        j = localPEVideoInfo.displayHeightRatio;
        break label148;
        break;
        localLayoutParams2.height = (2 * (this.playPara.screenHeight / 2));
      }
    }
    label260: label268: label270: ViewGroup.LayoutParams localLayoutParams1 = this.mSurfaceView.getLayoutParams();
    localLayoutParams1.height = (2 * (this.playPara.screenHeight / 2));
    localLayoutParams1.width = (2 * (this.playPara.screenWidth / 2));
    this.mSurfaceView.setLayoutParams(localLayoutParams1);
  }

  private void setVideoScalingMode(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setVideoScalingMode : mode = " + paramInt);
    if (this.playPara == null)
    {
      DmpLog.d("HAPlayer_PowerPlayer", "setVideoScalingMode :fialed,playPara is null");
      return;
    }
    this.playPara.scaleMode = paramInt;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      if (2 == this.playPara.decodeType)
      {
        setVideoScale(0, 0, 0);
        return;
      }
      break;
    case 1:
      if (2 == this.playPara.decodeType)
      {
        setVideoScale(1, 0, 0);
        return;
      }
      setVideoAspectRatio(1, 0, 0);
      return;
    }
    setVideoAspectRatio(0, 0, 0);
  }

  private int switchPEcode2Hacode(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "switchPEcode2Hacode:peErrorcode = " + paramInt);
    int i;
    switch (paramInt)
    {
    default:
      i = 1;
    case 526440014:
    case 526452190:
    case 325386966:
    case 325445081:
    case 325429021:
    case 439207759:
    case 238868259:
    case 325182424:
    case 260695000:
    }
    while (true)
    {
      DmpLog.d("HAPlayer_PowerPlayer", "switchPEcode2Hacode:haErrorCode = " + i);
      return i;
      i = 101;
      continue;
      i = 102;
      continue;
      i = 103;
      continue;
      i = 104;
      continue;
      i = 105;
      continue;
      i = 106;
      continue;
      i = 107;
      continue;
      i = 108;
      continue;
      i = 109;
    }
  }

  public void OnPEPlayerEvent(int paramInt)
  {
    Message localMessage = this.eventHandler.obtainMessage();
    switch (paramInt)
    {
    case 189629465:
    default:
    case 443610766:
    case 205649550:
    case 443864209:
    case 442041560:
    case 205587672:
      do
      {
        do
        {
          int i;
          do
          {
            return;
            DmpLog.i("HAPlayer_PowerPlayer", "OnPEPlayerEvent() prepared message");
            this.eventHandler.sendEmptyMessage(1);
            return;
            if (this.pePlayer == null)
            {
              DmpLog.e("HAPlayer_PowerPlayer", "OnPEPlayerEvent(): pelayer is null");
              return;
            }
            i = this.pePlayer.PEPlayer_GetState();
          }
          while ((i == 4) || (i == 5));
          int j = ((Integer)this.pePlayer.PEPlayer_GetInfo(209520535)).intValue();
          if (i == 3)
          {
            localMessage.what = 3;
            if (j >= 0)
              break label235;
          }
          for (int k = 0; ; k = j * 100 / 800)
          {
            localMessage.arg1 = k;
            this.eventHandler.sendMessage(localMessage);
            if ((!this.playPara.inPlayState) || (j < 800))
              break;
            DmpLog.d("HAPlayer_PowerPlayer", "OnPEPlayerEvent(): pePlayer.PEPlayer_Play()");
            this.pePlayer.PEPlayer_Play();
            return;
          }
          this.eventHandler.sendEmptyMessage(6);
          return;
          this.eventHandler.sendEmptyMessage(2);
          return;
        }
        while ((this.pePlayer == null) || (this.pePlayer.PEPlayer_GetState() == 4) || (this.pePlayer.PEPlayer_GetState() == 5));
        if ((this.pePlayer.PEPlayer_GetState() == 3) && (this.mOnBufferingUpdateListener != null))
        {
          localMessage.what = 3;
          localMessage.arg1 = 100;
          this.eventHandler.sendMessage(localMessage);
        }
      }
      while (!this.playPara.inPlayState);
      this.pePlayer.PEPlayer_Play();
      return;
    case 541951001:
      localMessage.what = 200;
      try
      {
        PEVideoInfo localPEVideoInfo = (PEVideoInfo)this.pePlayer.PEPlayer_GetInfo(541951001);
        localMessage.arg1 = localPEVideoInfo.width;
        localMessage.arg2 = localPEVideoInfo.height;
        this.eventHandler.sendMessage(localMessage);
        return;
      }
      catch (Exception localException)
      {
        while (true)
        {
          localMessage.arg1 = 0;
          localMessage.arg2 = 0;
        }
      }
    case 259114588:
      label235: if (this.pePlayer != null)
      {
        DmpLog.e("HAPlayer_PowerPlayer", "OnPEPlayerEvent(): PEEvent.PE_EVENT_ERROR = 259114588");
        PEError localPEError = (PEError)this.pePlayer.PEPlayer_GetLastError();
        localMessage.what = 100;
        localMessage.arg1 = switchPEcode2Hacode(localPEError.code);
        localMessage.arg2 = localPEError.spec;
        this.eventHandler.sendMessage(localMessage);
        return;
      }
      DmpLog.e("HAPlayer_PowerPlayer", "OnPEPlayerEvent(),pePlayer is null");
      return;
    case 477451467:
    }
    onRenderSkia();
  }

  public int getCurrentPosition()
  {
    switch (this.playPara.contentType)
    {
    default:
      return 0;
    case 0:
      while (true)
      {
        synchronized (lock)
        {
          if (this.pePlayer != null)
          {
            if (4 == this.pePlayer.PEPlayer_GetState())
              this.playPara.playPosition = ((Integer)this.pePlayer.PEPlayer_GetInfo(442120087)).intValue();
            i = this.playPara.playPosition;
            return i;
          }
        }
        DmpLog.e("HAPlayer_PowerPlayer", "getCurrentPosition() :pePlayer is null");
        int i = 0;
      }
    case 1:
      return this.playPara.mediaDuration;
    case 2:
    }
    if (this.playPara.pauseTimeRecorder > 0L)
      return this.playPara.mediaDuration - getTSTVSeekTime();
    return this.playPara.mediaDuration - this.playPara.tvSeekTime;
  }

  public int getDuration()
  {
    DmpLog.d("HAPlayer_PowerPlayer", "getDuration() :playPara.contentType = " + this.playPara.contentType + " playPara.mediaDuration:" + this.playPara.mediaDuration);
    if (this.playPara.mediaDuration > 0)
      return this.playPara.mediaDuration;
    synchronized (lock)
    {
      if (this.pePlayer == null)
      {
        DmpLog.e("HAPlayer_PowerPlayer", "getDuration() : getDuration() failed, pepleyer is null");
        return 0;
      }
    }
    this.playPara.mediaDuration = ((Integer)this.pePlayer.PEPlayer_GetInfo(243122910)).intValue();
    DmpLog.i("HAPlayer_PowerPlayer", "getDuration:" + this.playPara.mediaDuration);
    return this.playPara.mediaDuration;
  }

  public Object getProperties(HAGetParam paramHAGetParam)
  {
    if (this.pePlayer == null)
      DmpLog.w("HAPlayer_PowerPlayer", "getProperties fail: pePlayer is null");
    switch (2.$SwitchMap$com$huawei$playerinterface$parameter$HAGetParam[paramHAGetParam.ordinal()])
    {
    default:
      return null;
    case 1:
      if (this.playProxy != null)
        return this.playProxy.proxyGetBandwidthArray();
      if (this.pePlayer != null)
        return this.pePlayer.PEPlayer_GetInfo(210069342);
    case 2:
      if (this.pePlayer != null)
        return (Integer)this.pePlayer.PEPlayer_GetInfo(240768654);
    case 3:
      if (this.pePlayer != null)
        return (Integer)this.pePlayer.PEPlayer_GetInfo(209520535);
    case 4:
      if (this.pePlayer != null)
        return (Integer)this.pePlayer.PEPlayer_GetInfo(442118926);
    case 5:
      if (this.pePlayer != null)
        return this.pePlayer.PEPlayer_GetInfo(192505694);
    case 6:
      if (this.pePlayer != null)
        return this.pePlayer.PEPlayer_GetInfo(494716318);
    case 7:
      if (this.pePlayer != null)
      {
        this.pePlayer.PEPlayer_SetParam(543802960, Integer.valueOf(1));
        return this.pePlayer.PEPlayer_GetInfo(540346013);
      }
      break;
    case 8:
    }
    return PlayerVersion.getVer();
  }

  public int getVideoHeight()
  {
    DmpLog.d("HAPlayer_PowerPlayer", "getVideoHeight()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "getVideoHeight():failed,peplayer is null");
      return 0;
    }
    PEVideoInfo localPEVideoInfo = (PEVideoInfo)this.pePlayer.PEPlayer_GetInfo(541951001);
    if (localPEVideoInfo == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "getVideoHeight():failed,videoInfo is null");
      return 0;
    }
    return localPEVideoInfo.height;
  }

  public int getVideoWidth()
  {
    DmpLog.d("HAPlayer_PowerPlayer", "getVideoWidth()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "getVideoWidth():failed,peplayer is null");
      return 0;
    }
    PEVideoInfo localPEVideoInfo = (PEVideoInfo)this.pePlayer.PEPlayer_GetInfo(541951001);
    if (localPEVideoInfo == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "getVideoWidth(): failed,videoInfo is null");
      return 0;
    }
    return localPEVideoInfo.width;
  }

  public boolean isPlaying()
  {
    DmpLog.d("HAPlayer_PowerPlayer", "isPlaying()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "isPlaying() : failed, pepleyer is null");
      return false;
    }
    if (4 == this.pePlayer.PEPlayer_GetState())
    {
      DmpLog.d("HAPlayer_PowerPlayer", "isPlaying():isplaying = true");
      return true;
    }
    DmpLog.d("HAPlayer_PowerPlayer", "isPlaying():isplaying = false,pePlayer.PEPlayer_GetState() = " + this.pePlayer.PEPlayer_GetState());
    return false;
  }

  public void pause()
  {
    DmpLog.d("HAPlayer_PowerPlayer", " pause()");
    if (this.pePlayer == null)
      DmpLog.e("HAPlayer_PowerPlayer", " pause() ： failed, peplayer is null");
    this.playPara.inPlayState = false;
    DmpLog.i("HAPlayer_PowerPlayer", "pause contentType" + this.playPara.contentType);
    switch (this.playPara.contentType)
    {
    case 1:
    default:
    case 2:
    case 0:
    }
    do
    {
      do
        return;
      while (this.pePlayer == null);
      this.pePlayer.PEPlayer_Pause();
      this.playPara.pauseTimeRecorder = getPlayTimeTick();
      return;
    }
    while (this.pePlayer == null);
    this.pePlayer.PEPlayer_Pause();
  }

  public void prepare()
  {
    DmpLog.d("HAPlayer_PowerPlayer", " prepare()");
    if (this.playProxy != null)
    {
      this.proxyPara.playUrl = this.playProxy.proxyGetPlayUrl(this.playPara.playUrl, this.playPara.contentType, this.playPara.mediaDuration, 10.0F);
      setNeedUpdateProxyCode(true);
    }
    while (true)
    {
      DmpLog.d("HAPlayer_PowerPlayer", " prepare() -> pePlayer.PEPlayer_SetUrl(): url = " + this.proxyPara.playUrl);
      if (this.pePlayer != null)
        break;
      DmpLog.w("HAPlayer_PowerPlayer", "prepare but peplayer is null");
      return;
      this.proxyPara.playUrl = this.playPara.playUrl;
    }
    if (this.pePlayer.PEPlayer_SetUrl(this.proxyPara.playUrl) == -1)
    {
      DmpLog.e("HAPlayer_PowerPlayer", " prepare() -> pePlayer.PEPlayer_SetUrl() : failed");
      return;
    }
    this.firstPlay = true;
    this.playPara.inPlayState = false;
    if ((this.playPara.historyPlayPoint >= 0) && (this.playPara.contentType != 1))
    {
      DmpLog.i("HAPlayer_PowerPlayer", " prepare() historyPlayPoint:" + this.playPara.historyPlayPoint);
      if (this.playPara.contentType != 2)
        break label298;
      if (this.playProxy != null)
      {
        this.playPara.tvSeekTime = (this.playPara.mediaDuration - this.playPara.historyPlayPoint);
        this.playProxy.proxySeek(this.playPara.tvSeekTime);
      }
    }
    while (true)
    {
      this.playPara.historyPlayPoint = -1;
      DmpLog.d("HAPlayer_PowerPlayer", " prepare() -> pePlayer.PEPlayer_Start()");
      this.pePlayer.PEPlayer_Start();
      super.logicStart();
      return;
      label298: if (this.playPara.historyPlayPoint > 0)
        this.pePlayer.PEPlayer_SetParam(208754334, Integer.valueOf(this.playPara.historyPlayPoint));
    }
  }

  public void prepareAsync()
  {
    DmpLog.d("HAPlayer_PowerPlayer", " prepareAsync()");
    prepare();
  }

  public void release()
  {
    DmpLog.d("HAPlayer_PowerPlayer", " release()");
    super.logicRelease();
    synchronized (lock)
    {
      if (this.pePlayer != null)
      {
        this.mOnPreparedListener = null;
        this.mOnCompletionListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.pePlayer.PEPlayer_Release();
        this.pePlayer = null;
        this.mDRM = null;
        this.mSurfaceView.getHolder().removeCallback(this);
        this.mSurfaceView = null;
        this.useProxy = 1;
        this.playPara.errReportCnt = 0;
        DmpLog.d("HAPlayer_PowerPlayer", "release(): playProxy.proxyClose()");
        if (this.playProxy != null)
        {
          setNeedUpdateProxyCode(false);
          this.playProxy.proxyClose();
          this.playProxy = null;
        }
      }
      else
      {
        DmpLog.e("HAPlayer_PowerPlayer", "release(): failed, pepleyer is null");
      }
    }
    DmpLog.w("HAPlayer_PowerPlayer", "release():  playProxy is null,not close proxy");
  }

  public void resume()
  {
    super.logicResume();
    DmpLog.i("HAPlayer_PowerPlayer", "resume");
    this.playPara.inPlayState = true;
    resumeOnly();
  }

  public void resume(int paramInt)
  {
    super.logicResume();
    DmpLog.i("HAPlayer_PowerPlayer", "resume play:" + paramInt);
    PlayerPara localPlayerPara;
    if (paramInt >= 0)
    {
      localPlayerPara = this.playPara;
      if (paramInt != 0)
        break label54;
    }
    label54: for (boolean bool = false; ; bool = true)
    {
      localPlayerPara.inPlayState = bool;
      resumeOnly();
      return;
    }
  }

  public void resumeOnly()
  {
    DmpLog.d("HAPlayer_PowerPlayer", "resumeOnly()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "resume() :failed, pepleyer is null");
      return;
    }
    if (2 == this.playPara.contentType)
    {
      if (this.playProxy == null)
        break label94;
      this.playProxy.proxySeek(getTSTVSeekTime());
      DmpLog.d("HAPlayer_PowerPlayer", "resume():pePlayer.PEPlayer_SetUrl:playerurl = " + this.proxyPara.playUrl);
    }
    while (true)
    {
      restartPlayer(true);
      super.logicResume();
      return;
      label94: DmpLog.e("HAPlayer_PowerPlayer", "resume tstv fail: palyproxy is null");
    }
  }

  public void seekTo(int paramInt)
  {
    DmpLog.i("HAPlayer_PowerPlayer", "seekto");
    this.playPara.inPlayState = true;
    seekToOnly(paramInt);
  }

  public void seekTo(int paramInt1, int paramInt2)
  {
    DmpLog.i("HAPlayer_PowerPlayer", "seekto play:" + paramInt2);
    PlayerPara localPlayerPara;
    if (paramInt2 >= 0)
    {
      localPlayerPara = this.playPara;
      if (paramInt2 != 0)
        break label53;
    }
    label53: for (boolean bool = false; ; bool = true)
    {
      localPlayerPara.inPlayState = bool;
      seekToOnly(paramInt1);
      return;
    }
  }

  protected void seekToOnly(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "seekToOnly(): timeStamp =" + paramInt);
    if (this.pePlayer == null)
      DmpLog.e("HAPlayer_PowerPlayer", "seekTo(): failed, pepleyer is null");
    do
    {
      do
      {
        return;
        DmpLog.d("HAPlayer_PowerPlayer", "seekTo(): playPara.contentType " + this.playPara.contentType);
        if (paramInt > 2)
          paramInt--;
        switch (this.playPara.contentType)
        {
        default:
          return;
        case 0:
          this.pePlayer.PEPlayer_SeekTo(paramInt);
          this.playPara.playPosition = paramInt;
          DmpLog.d("HAPlayer_PowerPlayer", "seekTo():vod send seekcomplete to listener");
        case 2:
        case 1:
        }
      }
      while (this.eventHandler == null);
      this.eventHandler.sendEmptyMessage(4);
      return;
      this.playPara.pauseTimeRecorder = 0L;
      this.playPara.tvSeekTime = (this.playPara.mediaDuration - paramInt);
      if (this.playProxy == null)
      {
        DmpLog.w("HAPlayer_PowerPlayer", "seekTo(" + paramInt + "), playProxy is null");
        if (this.playPara.tvSeekTime == 0)
          this.playPara.tvSeekTime = 1;
        while (true)
        {
          this.pePlayer.PEPlayer_SeekTo(this.playPara.tvSeekTime);
          return;
          if (this.playPara.tvSeekTime == this.playPara.mediaDuration)
            this.playPara.tvSeekTime = (-1 + this.playPara.mediaDuration);
        }
      }
      this.pePlayer.PEPlayer_Stop();
      this.playProxy.proxySeek(this.playPara.tvSeekTime);
      restartPlayer(false);
      DmpLog.d("HAPlayer_PowerPlayer", "seekTo():TSTV send seekcomplete to listener");
    }
    while (this.eventHandler == null);
    this.eventHandler.sendEmptyMessage(4);
    return;
    Message localMessage = new Message();
    localMessage.what = 200;
    localMessage.arg1 = 801;
    this.eventHandler.sendMessage(localMessage);
  }

  public void setDataSource(Context paramContext, String paramString)
    throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
  {
    this.context = paramContext;
    this.playPara.isInternalStart = false;
    if ((this.playPara == null) || (this.proxyPara == null))
    {
      DmpLog.e("HAPlayer_PowerPlayer", " playPara==null ||  proxyPara==null");
      onError(100, 1, 0);
      return;
    }
    if (paramString == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", " setDataSource() : path is null");
      onError(100, 1, 0);
      return;
    }
    String str = paramString.toLowerCase(Locale.getDefault());
    if ((str.indexOf(".m3u8") != -1) && (str.indexOf("http://") != -1) && (this.useProxy == 1))
    {
      this.playProxy = new PlayerProxy();
      if (this.playProxy != null)
        this.playProxy.initProxy();
    }
    DmpLog.d("HAPlayer_PowerPlayer", " setDataSource() : playUrl = " + paramString);
    if (paramContext != null)
      initPEPlayer();
    while (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", " setDataSource() : pePlayer is null");
      onError(100, 1, 0);
      return;
      DmpLog.e("HAPlayer_PowerPlayer", " setDataSource() : setDataSource failed, context is null");
    }
    this.playPara.playUrl = paramString;
    setMaxBitrate(this.proxyPara.maxBitrate);
    setMinBitrate(this.proxyPara.minBitrate);
  }

  public void setDisplay(SurfaceView paramSurfaceView)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " setDisplay()");
    if ((this.pePlayer == null) || (paramSurfaceView == null))
    {
      DmpLog.e("HAPlayer_PowerPlayer", "Player -> setDisplay() : pePlayer is null");
      return;
    }
    this.mSurfaceView = paramSurfaceView;
    this.mSurfaceView.getHolder().addCallback(this);
    recordScreenSize();
    setPEPara();
    setConfigInfo(this.mSurfaceView.getHolder());
    this.pePlayer.PEPlayer_SetParam(558728097, this.mSurfaceView.getHolder().getSurface());
    super.logicPrepare(this.eventHandler);
  }

  public void setOnBufferingUpdateListener(IHAPlayer.OnBufferingUpdateListener paramOnBufferingUpdateListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnBufferingUpdateListener()");
    this.mOnBufferingUpdateListener = paramOnBufferingUpdateListener;
  }

  public void setOnCompletionListener(IHAPlayer.OnCompletionListener paramOnCompletionListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnCompletionListener()");
    this.mOnCompletionListener = paramOnCompletionListener;
  }

  public void setOnErrorListener(IHAPlayer.OnErrorListener paramOnErrorListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnErrorListener()");
    this.mOnErrorListener = paramOnErrorListener;
  }

  public void setOnInfoListener(IHAPlayer.OnInfoListener paramOnInfoListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnInfoListener()");
    this.mOnInfoListener = paramOnInfoListener;
  }

  public void setOnPreparedListener(IHAPlayer.OnPreparedListener paramOnPreparedListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnPreparedListener()");
    this.mOnPreparedListener = paramOnPreparedListener;
  }

  public void setOnSeekCompleteListener(IHAPlayer.OnSeekCompleteListener paramOnSeekCompleteListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnSeekCompleteListener()");
    this.mOnSeekCompleteListener = paramOnSeekCompleteListener;
  }

  public void setOnVideoSizeChangedListener(IHAPlayer.OnVideoSizeChangedListener paramOnVideoSizeChangedListener)
  {
    DmpLog.d("HAPlayer_PowerPlayer", "setOnVideoSizeChangedListener()");
    this.mOnVideoSizeChangedListener = paramOnVideoSizeChangedListener;
  }

  public int setProperties(HASetParam paramHASetParam, Object paramObject)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " setProperties() : key = " + paramHASetParam);
    switch (2.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return super.setProperties(paramHASetParam, paramObject);
    case 1:
      if ((paramObject instanceof Integer))
        this.playPara.contentType = ((Integer)paramObject).intValue();
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    }
    while (true)
    {
      return 1;
      DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->VIDEO_TYPE: failed,value is not Integer");
      return -1;
      if ((paramObject instanceof Integer))
      {
        this.playPara.mediaDuration = ((Integer)paramObject).intValue();
      }
      else
      {
        DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->TSTV_LENGTH: failed,value is not Integer");
        return -1;
        if ((paramObject instanceof Integer))
        {
          setMaxBitrate(((Integer)paramObject).intValue());
        }
        else
        {
          DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->MAX_BITRATE: failed,value is not Integer");
          return -1;
          if ((paramObject instanceof Integer))
          {
            setMinBitrate(((Integer)paramObject).intValue());
          }
          else
          {
            DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->MIN_BITRATE: failed,value is not Integer");
            return -1;
            if ((paramObject instanceof DRMInfo))
            {
              this.mDRM = ((DRMInfo)paramObject);
              setDRMInfo(this.mDRM);
            }
            else
            {
              DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->SCALE_MODE: failed,value is not DRMInfo");
              return -1;
              if ((paramObject instanceof Integer))
              {
                setVideoScalingMode(((Integer)paramObject).intValue());
              }
              else
              {
                DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->SCALE_MODE: failed,value is not Integer");
                return -1;
                if ((paramObject instanceof Integer))
                {
                  setDesignatedBitrate(((Integer)paramObject).intValue());
                }
                else
                {
                  DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->SCALE_MODE: failed,value is not Integer");
                  return -1;
                  if ((paramObject instanceof Integer))
                  {
                    this.pePlayer.PEPlayer_SetParam(388899617, paramObject);
                  }
                  else
                  {
                    DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->SCALE_MODE: failed,value is not Integer");
                    return -1;
                    if ((paramObject instanceof String))
                    {
                      this.pePlayer.PEPlayer_Switch(192735132, paramObject);
                      DmpLog.i("HAPlayer_PowerPlayer", " setProperties() ->AUDIO_TRACK:" + paramObject);
                    }
                    else
                    {
                      DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->AUDIO_TRACK: failed,value is not String");
                      return -1;
                      if ((paramObject instanceof String))
                      {
                        this.pePlayer.PEPlayer_Switch(494716828, paramObject);
                        DmpLog.i("HAPlayer_PowerPlayer", " setProperties() ->SUBTITLES_TRACK:" + paramObject);
                      }
                      else
                      {
                        DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->SUBTITLES_TRACK: failed,value is not String");
                        return -1;
                        if ((paramObject instanceof Integer))
                        {
                          this.pePlayer.PEPlayer_SetParam(490595149, paramObject);
                          DmpLog.i("HAPlayer_PowerPlayer", " setProperties() ->SUBTITLES_TRACK:" + paramObject);
                        }
                        else
                        {
                          DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->SUBTITLES_TRACK: failed,value is not integer");
                          return -1;
                          if ((paramObject instanceof Long))
                          {
                            DmpLog.i("HAPlayer_PowerPlayer", " setProperties() ->TIME_DIFF_UTC:" + paramObject);
                            if (this.playProxy != null)
                            {
                              Long localLong = (Long)paramObject;
                              this.playProxy.proxySetTimeZone(localLong.intValue());
                            }
                          }
                          else
                          {
                            DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->TIME_DIFF_UTC: failed,value is not Integer");
                            return -1;
                            if ((paramObject instanceof Integer))
                            {
                              this.useProxy = ((Integer)paramObject).intValue();
                            }
                            else
                            {
                              DmpLog.e("HAPlayer_PowerPlayer", "shark -> setProperties() ->PROXY_ON: failed,value is not Integer");
                              return -1;
                              if ((paramObject instanceof Integer))
                              {
                                this.playPara.historyPlayPoint = ((Integer)paramObject).intValue();
                              }
                              else
                              {
                                DmpLog.e("HAPlayer_PowerPlayer", "shark -> setProperties() ->HISTORY_PLAY_POINT: failed,value is not Integer");
                                return -1;
                                if ((paramObject instanceof String))
                                {
                                  if (this.playProxy != null)
                                  {
                                    this.playProxy.proxySetPreferedAudioLang((String)paramObject);
                                    DmpLog.i("HAPlayer_PowerPlayer", "shark -> setProperties() AUDIO_PREFER_LANG value:" + (String)paramObject);
                                  }
                                  else
                                  {
                                    DmpLog.w("HAPlayer_PowerPlayer", "shark -> setProperties() AUDIO_PREFER_LANG fail proxyhandle is null");
                                    return -1;
                                  }
                                }
                                else
                                {
                                  DmpLog.e("HAPlayer_PowerPlayer", "shark -> setProperties() ->AUDIO_PREFER_LANG: failed,value is not String");
                                  return -1;
                                  if ((paramObject instanceof String))
                                  {
                                    if (this.playProxy != null)
                                    {
                                      this.playProxy.proxySetPreferedSubtitleLang((String)paramObject);
                                      DmpLog.i("HAPlayer_PowerPlayer", "shark -> setProperties() TEXT_PREFER_LANG value:" + (String)paramObject);
                                    }
                                    else
                                    {
                                      DmpLog.w("HAPlayer_PowerPlayer", "shark -> setProperties() TEXT_PREFER_LANG fail proxyhandle is null");
                                      return -1;
                                    }
                                  }
                                  else
                                  {
                                    DmpLog.e("HAPlayer_PowerPlayer", "shark -> setProperties() ->TEXT_PREFER_LANG: failed,value is not String");
                                    return -1;
                                    if (!(paramObject instanceof Integer))
                                      break;
                                    this.pePlayer.PEPlayer_SetParam(225048025, paramObject);
                                    DmpLog.i("HAPlayer_PowerPlayer", " setProperties() ->PERFORMANCE_ADAPTIVE:" + paramObject);
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    DmpLog.e("HAPlayer_PowerPlayer", " setProperties() ->PERFORMANCE_ADAPTIVE: failed,value is not integer");
    return -1;
  }

  public void start()
  {
    DmpLog.d("HAPlayer_PowerPlayer", " start()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", " start() ：failed, pePlayer is null");
      return;
    }
    this.playPara.inPlayState = true;
    if (this.firstPlay)
    {
      this.firstPlay = false;
      return;
    }
    int i = this.pePlayer.PEPlayer_GetState();
    DmpLog.d("HAPlayer_PowerPlayer", " start():playerState = " + i);
    switch (this.playPara.contentType)
    {
    case 1:
    default:
      return;
    case 0:
      DmpLog.d("HAPlayer_PowerPlayer", " start():playerstate is PEState.PE_STATE_PAUSE,call -> pePlayer.PEPlayer_Play();");
      this.pePlayer.PEPlayer_Play();
      return;
    case 2:
    }
    if (this.playProxy != null)
      this.playProxy.proxySeek(getTSTVSeekTime());
    if (this.playPara.tvSeekTime >= this.playPara.mediaDuration)
    {
      if (this.playProxy == null)
      {
        this.pePlayer.PEPlayer_SeekTo(-1 + this.playPara.mediaDuration);
        return;
      }
      this.pePlayer.PEPlayer_Stop();
      restartPlayer(false);
      return;
    }
    this.pePlayer.PEPlayer_Play();
  }

  public void start(int paramInt)
  {
    DmpLog.d("HAPlayer_PowerPlayer", " start(timeStamp): timeStamp = " + paramInt);
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", " start(timeStamp): failed, pePlayer is null");
      return;
    }
    this.firstPlay = false;
    DmpLog.d("HAPlayer_PowerPlayer", " start(timeStamp),playPara.contentType = " + this.playPara.contentType);
    switch (this.playPara.contentType)
    {
    case 1:
    default:
    case 0:
    case 2:
    }
    while (true)
    {
      this.playPara.inPlayState = true;
      return;
      if (paramInt > 0)
      {
        DmpLog.d("HAPlayer_PowerPlayer", " start(timeStamp) -> pePlayer.PEPlayer_SeekTo: timeStamp = " + paramInt);
        this.pePlayer.PEPlayer_SeekTo(paramInt);
        this.playPara.playPosition = paramInt;
        continue;
        if ((paramInt > 0) && (this.playProxy != null))
        {
          this.playPara.tvSeekTime = (this.playPara.mediaDuration - paramInt);
          seekTo(this.playPara.tvSeekTime);
        }
      }
    }
  }

  public void stop()
  {
    super.logicStop();
    DmpLog.d("HAPlayer_PowerPlayer", " stop()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "stop() : failed, pepleyer is null");
      return;
    }
    this.pePlayer.PEPlayer_Stop();
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    DmpLog.i("HAPlayer_PowerPlayer", "PowerPlayer surfaceChanged");
    setConfigInfo(paramSurfaceHolder);
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    DmpLog.i("HAPlayer_PowerPlayer", "PowerPlayer surfaceCreated");
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    DmpLog.i("HAPlayer_PowerPlayer", "PowerPlayer surfaceDestroyed");
  }

  public void suspend()
  {
    super.logicSuspend();
    DmpLog.d("HAPlayer_PowerPlayer", "suspend()");
    if (this.pePlayer == null)
    {
      DmpLog.e("HAPlayer_PowerPlayer", "suspend(): failed, pePlayer is null");
      return;
    }
    if (6 == this.pePlayer.PEPlayer_GetState())
      this.playPara.playPosition = 0;
    if (this.playPara.contentType == 2)
      this.playPara.pauseTimeRecorder = getPlayTimeTick();
    DmpLog.d("HAPlayer_PowerPlayer", "suspend():pePlayer.PEPlayer_Stop()");
    this.pePlayer.PEPlayer_Stop();
  }

  protected void updateProxyCode()
  {
    if ((this.playProxy == null) || (this.pePlayer == null))
    {
      setNeedUpdateProxyCode(false);
      return;
    }
    int i = OTTProxy.native_proxy_get_callback();
    switch (i)
    {
    case 6:
    case 7:
    case 8:
    default:
      return;
    case 0:
      DmpLog.i("HAPlayer_PowerPlayer", "getProxyCodeThread rcv code 0will return");
    case 1:
    case 3:
    case 5:
    case 2:
    case 4:
      while (true)
      {
        setNeedUpdateProxyCode(true);
        return;
        Message localMessage3 = new Message();
        DmpLog.e("HAPlayer_PowerPlayer", "getProxyCodeThread rcv code " + i + " will return");
        localMessage3.what = 100;
        localMessage3.arg1 = 104;
        localMessage3.arg2 = 0;
        this.eventHandler.sendMessage(localMessage3);
        continue;
        DmpLog.e("HAPlayer_PowerPlayer", "getProxyCodeThread rcv code " + i + " will return");
        continue;
        Message localMessage2 = new Message();
        DmpLog.e("HAPlayer_PowerPlayer", "getProxyCodeThread rcv code " + i + " will return");
        localMessage2.what = 100;
        localMessage2.arg1 = 105;
        localMessage2.arg2 = 400;
        this.eventHandler.sendMessage(localMessage2);
      }
    case 9:
    }
    DmpLog.w("HAPlayer_PowerPlayer", "getProxyCodeThread rcv code TS_LOWBANDWIDTH");
    Message localMessage1 = new Message();
    localMessage1.what = 200;
    localMessage1.arg1 = 2;
    localMessage1.arg2 = 0;
    this.eventHandler.sendMessage(localMessage1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.PowerPlayer
 * JD-Core Version:    0.6.2
 */