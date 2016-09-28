package com.huawei.playerinterface;

import android.content.Context;
import android.view.SurfaceHolder;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.decoderConfig.DecoderMatch;
import com.huawei.playerinterface.version.PlayerVersion;
import com.huawei.so.OTTProxy;
import java.util.Locale;

public class MediaFactory
{
  private static final int CONTENT_HLS = 100;
  private static final int CONTENT_HSS = 101;
  private static final int CONTENT_OTHER = 102;
  private static final int CONTENT_UNKOWN = -1;
  public static final String HAPLAYER_VERSION_DEBUG = "debug";
  public static final int HA_ERR = -1;
  public static final int HA_NO_SUPPORT = 1;
  public static final int HA_OK = 0;
  public static final int PLAYER_CODEC_AUTO = 0;
  public static final int PLAYER_CODEC_NATIVE = 4;
  public static final int PLAYER_CODEC_PE = 1;
  public static final int PLAYER_CODEC_PE_SKIA = 5;
  public static final int PLAYER_CODEC_SF = 2;
  public static final int PLAYER_CODEC_SHARK = 3;
  public static final String TAG = "HAPlayer_MediaFactory";
  private static int m_contenttype = -1;

  private static IHAPlayer Create(int paramInt)
  {
    DmpLog.i("HAPlayer_MediaFactory", "HAPlayer version:" + PlayerVersion.getVer());
    DmpLog.i("HAPlayer_MediaFactory", "HAPlayer create valid codecMode: " + paramInt);
    OTTProxy.loadso();
    int i;
    switch (paramInt)
    {
    default:
      return new PowerPlayer(0);
    case 3:
      if (m_contenttype == 100)
        return new SharkPlayer();
    case 4:
      if (m_contenttype == 101)
        return new HSSPlayer();
      return new NativePlayer();
    case 1:
      i = 0;
    case 5:
    case 2:
    }
    while (i != -1)
      switch (m_contenttype)
      {
      default:
        return new PowerPlayer(i);
        i = 1;
        continue;
        i = 2;
        break;
      case 100:
        return new PowerPlayer(i);
      case 101:
        HSSPlayer.load();
        return new PEHSSPlayer(i);
      }
    return new NativePlayer();
  }

  public static IHAPlayer Create(Context paramContext, int paramInt, String paramString)
  {
    DmpLog.i("HAPlayer_MediaFactory", "HAPlayer create codecMode: " + paramInt);
    parserUrl(paramString);
    return Create(GetValidDecoder(paramContext, paramInt));
  }

  public static int GetAdapterDecoder(Context paramContext, String paramString)
  {
    if (paramString == null)
    {
      DmpLog.w("HAPlayer_MediaFactory", "GetAdapterDecoder Err: url is null");
      return 0;
    }
    parserUrl(paramString);
    if (m_contenttype != 102)
      return GetValidDecoder(paramContext, 0);
    DmpLog.i("HAPlayer_MediaFactory", "GetAdapterDecoder decodeMode:4");
    return 4;
  }

  public static int[] GetDecoderList(Context paramContext)
  {
    return new DecoderMatch(paramContext).getDecoderList();
  }

  public static String GetHAPlayerVersion()
  {
    return PlayerVersion.getVer();
  }

  public static int GetValidDecoder(Context paramContext, int paramInt)
  {
    if (paramInt == 0)
      paramInt = new DecoderMatch(paramContext).getDecoder();
    return paramInt;
  }

  public static int PlayReadyGetRootLicense(String paramString1, String paramString2, String paramString3)
  {
    return HSSPlayer.nativeGetRootLicense(paramString1, paramString2, paramString3);
  }

  public static int PlayReadyJoinDomain(String paramString1, String paramString2, String paramString3)
  {
    return HSSPlayer.nativeJoinDomain(paramString1, paramString2, paramString3);
  }

  public static void SetSurfaceHolderFormat(Context paramContext, SurfaceHolder paramSurfaceHolder, int paramInt)
  {
    DmpLog.i("HAPlayer_MediaFactory", "SetSurfaceHolderFormat in codecMode:" + paramInt);
    int i = GetValidDecoder(paramContext, paramInt);
    DmpLog.i("HAPlayer_MediaFactory", "SetSurfaceHolderFormat out valid codecMode:" + i);
    paramSurfaceHolder.setFormat(1);
    switch (i)
    {
    default:
      DmpLog.e("HAPlayer_MediaFactory", "get validCodec err :" + i + " input codec mode:" + paramInt);
      return;
    case 1:
    case 5:
      paramSurfaceHolder.setType(2);
      return;
    case 2:
    case 3:
    case 4:
    }
    paramSurfaceHolder.setType(3);
  }

  public static void UpdateDecoder(Context paramContext, String paramString)
  {
    new DecoderMatch(paramContext, paramString).UpdateConfigFile();
  }

  private static void parserUrl(String paramString)
  {
    String str = paramString.toLowerCase(Locale.getDefault());
    m_contenttype = 102;
    if (str.indexOf("http://") != -1)
    {
      if (str.indexOf(".m3u8") == -1)
        break label39;
      m_contenttype = 100;
    }
    label39: 
    while (str.indexOf("/manifest") == -1)
      return;
    m_contenttype = 101;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.MediaFactory
 * JD-Core Version:    0.6.2
 */