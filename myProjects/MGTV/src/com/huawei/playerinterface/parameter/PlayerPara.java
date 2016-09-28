package com.huawei.playerinterface.parameter;

import android.util.SparseArray;

public class PlayerPara
{
  public SparseArray<String> bitrateMap = null;
  public int contentType = 0;
  public int decodeType;
  public int errReportCnt = 0;
  public int historyPlayPoint = -1;
  public int iBufferTime = 800;
  public boolean inPlayState = false;
  public boolean isInternalStart = false;
  public int mediaDuration = -1;
  public String packagePath;
  public long pauseTimeRecorder;
  public int playBitrate = 0;
  public int playPosition = 0;
  public String playUrl;
  public int scaleMode = 0;
  public int screenHeight = 0;
  public int screenWidth = 0;
  public int tvSeekTime = 0;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.parameter.PlayerPara
 * JD-Core Version:    0.6.2
 */