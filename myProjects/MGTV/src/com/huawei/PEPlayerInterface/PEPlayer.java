package com.huawei.PEPlayerInterface;

import android.util.Log;

public class PEPlayer
{
  private static final String TAG = "PEPlayer";
  private static int isLoad = 0;
  private static final String libPE = "PE";
  private static final String libPEHttpBase = "PEHttpBase";
  private static final String libPEJni = "PEJni";
  private static final String libPELog = "PELog";
  private static final String libavcodec = "avcodec";
  private static final String libavutil = "avutil";
  private static PEPlayer player = null;
  private OnPEPlayerEventListener onEventListener = null;

  static
  {
    isLoad = 0;
    try
    {
      System.loadLibrary("PELog");
      Log.i("PEPlayer", "try to load PELog");
      System.loadLibrary("PEHttpBase");
      Log.i("PEPlayer", "try to load PEHttpBase");
      System.loadLibrary("PE");
      Log.i("PEPlayer", "try to load PE");
      System.loadLibrary("PEJni");
      Log.i("PEPlayer", "try to load PEJni");
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError1)
    {
      try
      {
        while (true)
        {
          System.loadLibrary("avutil");
          Log.i("PEPlayer", "try to load avutil");
          System.loadLibrary("avcodec");
          Log.i("PEPlayer", "try to load avcodec");
          return;
          localUnsatisfiedLinkError1 = localUnsatisfiedLinkError1;
          isLoad = -1;
          Log.e("PEPlayer", "load library failed!" + localUnsatisfiedLinkError1);
        }
      }
      catch (UnsatisfiedLinkError localUnsatisfiedLinkError2)
      {
        Log.e("PEPlayer", "load library failed!" + localUnsatisfiedLinkError2);
      }
    }
  }

  private native void DoNative(int paramInt, Object paramObject);

  private native Object GetDataClassNative(int paramInt);

  private native Object GetInfoClassNative(int paramInt);

  private native int[] GetInfoIntArrayNative(int paramInt);

  private native int GetInfoIntegerNative(int paramInt);

  private native Object[] GetInfoStringArrayNative(int paramInt);

  private native Object GetLastErrorNative();

  private native int GetStateNative();

  private static native String GetVersion();

  private native int InitNative();

  private void PEPlayerNotifyFunc(int paramInt)
  {
    if (player == null)
      return;
    this.onEventListener.OnPEPlayerEvent(paramInt);
  }

  public static String PEPlayer_GetVersion()
  {
    if (isLoad == 0)
      return GetVersion();
    return "not support";
  }

  public static PEPlayer PEPlayer_Init(OnPEPlayerEventListener paramOnPEPlayerEventListener)
  {
    PEPlayer localPEPlayer;
    if (player != null)
      localPEPlayer = player;
    int j;
    do
    {
      int i;
      do
      {
        return localPEPlayer;
        i = isLoad;
        localPEPlayer = null;
      }
      while (-1 == i);
      player = new PEPlayer();
      j = player.InitNative();
      localPEPlayer = null;
    }
    while (j != 0);
    player.onEventListener = paramOnPEPlayerEventListener;
    return player;
  }

  private native int PauseNative();

  private native int PlayNative();

  private native int RedrawFrameNative();

  private native void ReleaseNative();

  private native int SeekToNative(int paramInt);

  private native int SelectNative(String paramString);

  private native int SetParamNative(int paramInt, Object paramObject);

  private native int SetUrlNative(String paramString);

  private native int StartNative();

  private native int StopNative();

  private native int SwitchNative(int paramInt, Object paramObject);

  // ERROR //
  public void PEPlayer_Do(int paramInt, Object paramObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: tableswitch	default:+17 -> 20, 492159960:+20->23
    //   21: monitorexit
    //   22: return
    //   23: aload_2
    //   24: ifnull -4 -> 20
    //   27: aload_2
    //   28: checkcast 139	android/view/SurfaceHolder
    //   31: astore 4
    //   33: aload 4
    //   35: invokeinterface 143 1 0
    //   40: astore 5
    //   42: aload 5
    //   44: ifnull -24 -> 20
    //   47: aload_0
    //   48: iload_1
    //   49: aload 5
    //   51: invokespecial 145	com/huawei/PEPlayerInterface/PEPlayer:DoNative	(ILjava/lang/Object;)V
    //   54: aload 4
    //   56: aload 5
    //   58: invokeinterface 149 2 0
    //   63: goto -43 -> 20
    //   66: astore_3
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_3
    //   70: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   20	22	66	finally
    //   27	42	66	finally
    //   47	63	66	finally
    //   67	69	66	finally
  }

  public Object PEPlayer_GetData(PEDataType paramPEDataType)
  {
    if (player == null)
      return null;
    return GetDataClassNative(paramPEDataType.ordinal());
  }

  public Object PEPlayer_GetInfo(int paramInt)
  {
    if (player == null)
      return null;
    switch (paramInt)
    {
    default:
      return null;
    case 189629465:
      return GetInfoClassNative(paramInt);
    case 192514007:
    case 209520535:
    case 210077655:
    case 240768654:
    case 243122910:
    case 442118926:
    case 442120087:
    case 494716439:
    case 539821725:
    case 540346013:
      return Integer.valueOf(GetInfoIntegerNative(paramInt));
    case 210069342:
      return GetInfoIntArrayNative(paramInt);
    case 192505694:
    case 494716318:
      return GetInfoStringArrayNative(paramInt);
    case 541951001:
    }
    return GetInfoClassNative(paramInt);
  }

  public Object PEPlayer_GetLastError()
  {
    if (player == null)
      return null;
    return GetLastErrorNative();
  }

  public int PEPlayer_GetState()
  {
    if (player == null)
      return -1;
    return GetStateNative();
  }

  public int PEPlayer_Pause()
  {
    if (player == null)
      return -1;
    return PauseNative();
  }

  public int PEPlayer_Play()
  {
    if (player == null)
      return -1;
    return PlayNative();
  }

  public int PEPlayer_RedrawFrame()
  {
    if (player == null)
      return -1;
    return RedrawFrameNative();
  }

  public void PEPlayer_Release()
  {
    if (player != null)
    {
      ReleaseNative();
      player = null;
    }
  }

  public int PEPlayer_SeekTo(int paramInt)
  {
    if (player == null)
      return -1;
    return SeekToNative(paramInt);
  }

  public int PEPlayer_Select(String paramString)
  {
    if (player == null)
      return -1;
    return SelectNative(paramString);
  }

  public int PEPlayer_SetParam(int paramInt, Object paramObject)
  {
    if ((player == null) || (paramObject == null))
      return -1;
    return SetParamNative(paramInt, paramObject);
  }

  public int PEPlayer_SetUrl(String paramString)
  {
    if ((player == null) || (paramString == null))
      return -1;
    return SetUrlNative(paramString);
  }

  public int PEPlayer_Start()
  {
    if (player == null)
      return -1;
    return StartNative();
  }

  public int PEPlayer_Stop()
  {
    if (player == null)
      return -1;
    return StopNative();
  }

  public int PEPlayer_Switch(int paramInt, Object paramObject)
  {
    if (player == null)
      return -1;
    return SwitchNative(paramInt, paramObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.PEPlayerInterface.PEPlayer
 * JD-Core Version:    0.6.2
 */