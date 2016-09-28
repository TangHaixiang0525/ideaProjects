package com.huawei.playerinterface;

import com.huawei.PEPlayerInterface.PEPlayer;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.HACaption.HACaption;
import com.huawei.playerinterface.parameter.HASetParam;

public class PEHSSPlayer extends PowerPlayer
{
  static final String TAG = "HAPlayer_PEHSSPlayer";
  private boolean mNeed2ShowText = false;

  public PEHSSPlayer(int paramInt)
  {
    super(paramInt);
  }

  private void startCaption()
  {
    DmpLog.i("HAPlayer_PEHSSPlayer", "startCaption");
    if (this.pePlayer == null)
      DmpLog.w("HAPlayer_PEHSSPlayer", "startCaption fail: player handle is null");
    String[] arrayOfString = (String[])this.pePlayer.PEPlayer_GetInfo(494716318);
    if (arrayOfString == null)
    {
      DmpLog.w("HAPlayer_PEHSSPlayer", "startCaption but text is null");
      return;
    }
    if ((arrayOfString.length > 0) && (this.playerCaption != null))
    {
      HSSPlayer.setTextSwitchOn(true);
      this.playerCaption.start();
      setNeedUpdateCaption(true);
      this.mNeed2ShowText = true;
      DmpLog.i("HAPlayer_PEHSSPlayer", "startCaption success");
      return;
    }
    DmpLog.w("HAPlayer_PEHSSPlayer", "startCaption fail playerCaption:" + this.pePlayer + " text.length:" + arrayOfString.length);
  }

  public void resumeOnly()
  {
    if (this.mNeed2ShowText)
      setNeedUpdateCaption(true);
    super.resumeOnly();
  }

  public int setProperties(HASetParam paramHASetParam, Object paramObject)
  {
    DmpLog.i("HAPlayer_PEHSSPlayer", "shark -> setProperties() : key = " + paramHASetParam);
    switch (1.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return super.setProperties(paramHASetParam, paramObject);
    case 1:
      if ((paramObject instanceof String))
      {
        HSSPlayer.setAudio((String)paramObject, 0);
        super.seekTo(super.getCurrentPosition());
      }
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return 1;
      DmpLog.e("HAPlayer_PEHSSPlayer", " setProperties() ->AUDIO_TRACK: failed,value is not String");
      return -1;
      if ((paramObject instanceof String))
      {
        HSSPlayer.setText((String)paramObject, getCurrentPosition());
        DmpLog.i("HAPlayer_PEHSSPlayer", " setProperties() ->SUBTITLES_TRACK:" + paramObject);
      }
      else
      {
        DmpLog.e("HAPlayer_PEHSSPlayer", " setProperties() ->SUBTITLES_TRACK: failed,value is not String");
        return -1;
        if ((paramObject instanceof String))
        {
          HSSPlayer.setPlayReadyLicenseHeader((String)paramObject);
          DmpLog.i("HAPlayer_PEHSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_HEADER:" + paramObject);
        }
        else
        {
          DmpLog.e("HAPlayer_PEHSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_HEADER: failed,value is not String");
          return -1;
          if ((paramObject instanceof String))
          {
            HSSPlayer.setPlayReadyLicenseCData((String)paramObject);
            DmpLog.i("HAPlayer_PEHSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_CDATA:" + paramObject);
          }
          else
          {
            DmpLog.e("HAPlayer_PEHSSPlayer", " setProperties() ->PLAYREADY_REQ_LICENSE_CDATA: failed,value is not String");
            return -1;
            if (!(paramObject instanceof String))
              break;
            HSSPlayer.setPlayReadyLicenseUrl((String)paramObject);
            DmpLog.i("HAPlayer_PEHSSPlayer", " setProperties() ->PLAYREADY_LICENSE_SERVER_URL:" + paramObject);
          }
        }
      }
    }
    DmpLog.e("HAPlayer_PEHSSPlayer", " setProperties() ->PLAYREADY_LICENSE_SERVER_URL: failed,value is not String");
    return -1;
  }

  public void start()
  {
    super.start();
    startCaption();
  }

  public void start(int paramInt)
  {
    super.start(paramInt);
    startCaption();
  }

  public void suspend()
  {
    setNeedUpdateCaption(false);
    super.suspend();
  }

  protected void updateCaption()
  {
    if (this.playerCaption == null)
      return;
    this.playerCaption.showCaptionMessage(HSSPlayer.getTextByPlayTime(getCurrentPosition() + PlayerLogic.getPlayerSysTick()));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.PEHSSPlayer
 * JD-Core Version:    0.6.2
 */