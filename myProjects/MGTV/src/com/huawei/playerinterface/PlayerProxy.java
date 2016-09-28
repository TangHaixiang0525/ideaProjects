package com.huawei.playerinterface;

import com.huawei.dmpbase.DmpLog;
import com.huawei.so.OTTProxy;
import java.util.Arrays;

public class PlayerProxy
{
  private static final String TAG = "HAPlayer_PlayerProxy";

  public PlayerProxy()
  {
    DmpLog.i("HAPlayer_PlayerProxy", "PlayerProxy -> PlayerProxy()");
  }

  protected void initProxy()
  {
    DmpLog.d("HAPlayer_PlayerProxy", "PlayerProxy -> initProxy():native_proxy_init()");
    OTTProxy.native_proxy_init();
  }

  protected void proxyClose()
  {
    DmpLog.d("HAPlayer_PlayerProxy", "PlayerProxy -> proxyClose():native_proxy_tsClose()");
    OTTProxy.native_proxy_tsClose();
  }

  protected int[] proxyGetBandwidthArray()
  {
    String str = "";
    for (int i = 0; ((str == null) || (str.equals(""))) && (i < 3); i++)
    {
      str = OTTProxy.native_proxy_get_original_bitrate();
      DmpLog.i("HAPlayer_PlayerProxy", "PlayerProxy -> proxyGetBandwidthArray(): native_proxy_get_original_bitrate() -- strBitrate = " + str + ",  reGetBandwidthTime=" + i);
    }
    if ((str == null) || (str.equals("")))
    {
      DmpLog.e("HAPlayer_PlayerProxy", "proxyGetBandwidthArray: get no bitrate data");
      return new int[0];
    }
    String[] arrayOfString = str.split(",");
    int[] arrayOfInt = new int[arrayOfString.length];
    int j = 0;
    try
    {
      while (j < arrayOfString.length)
      {
        if ("".equals(arrayOfString[j]));
        arrayOfInt[j] = Integer.valueOf(arrayOfString[j]).intValue();
        j++;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DmpLog.e("HAPlayer_PlayerProxy", localNumberFormatException.toString());
      Arrays.sort(arrayOfInt);
    }
    return arrayOfInt;
  }

  protected String proxyGetPlayUrl(String paramString, int paramInt, long paramLong, float paramFloat)
  {
    if (paramString == null)
    {
      DmpLog.d("HAPlayer_PlayerProxy", "PlayerProxy ->  GetProxyPlayUrl():failed, url is null!");
      paramString = "";
    }
    DmpLog.i("HAPlayer_PlayerProxy", "PlayerProxy ->  GetProxyPlayUrl():native_proxy_tsOpen---> playUrl=" + paramString + ", playType=" + paramInt + ",  tstvTime=" + paramLong + ",bitrateMultiple=" + paramFloat);
    return OTTProxy.native_proxy_tsOpen(paramString, paramInt, paramLong / 1000L, paramFloat);
  }

  protected void proxySeek(int paramInt)
  {
    DmpLog.i("HAPlayer_PlayerProxy", "PlayerProxy -> proxySeek():native_proxy_set_seektime = " + paramInt / 1000);
    OTTProxy.native_proxy_set_seektime(paramInt / 1000L);
  }

  protected void proxySetFilterBitrate(int paramInt1, int paramInt2)
  {
    DmpLog.d("HAPlayer_PlayerProxy", "PlayerProxy -> proxySetFilterBitrate():lowBitrate = " + paramInt1 + ",highBitrate = " + paramInt2);
    OTTProxy.native_proxy_set_filterbitrate(paramInt1, paramInt2);
  }

  public void proxySetFixBitrate(int paramInt)
  {
    DmpLog.d("HAPlayer_PlayerProxy", "PlayerProxy -> proxySetFixBitrate(): native_proxy_set_manualbitrate,bitrate =" + paramInt);
    OTTProxy.native_proxy_set_manualbitrate(paramInt);
  }

  public void proxySetLogLevel(int paramInt)
  {
    OTTProxy.native_proxy_set_loglevel(paramInt);
  }

  protected void proxySetPreferedAudioLang(String paramString)
  {
    OTTProxy.native_proxy_set_audio_preferedLang(paramString);
  }

  protected void proxySetPreferedSubtitleLang(String paramString)
  {
    OTTProxy.native_proxy_set_subtitle_preferedLang(paramString);
  }

  public void proxySetTimeZone(int paramInt)
  {
    OTTProxy.native_proxy_set_ntpdiff(paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.PlayerProxy
 * JD-Core Version:    0.6.2
 */