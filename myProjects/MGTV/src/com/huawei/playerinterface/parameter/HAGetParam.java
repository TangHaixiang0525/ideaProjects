package com.huawei.playerinterface.parameter;

public enum HAGetParam
{
  static
  {
    DOWNLOAD_SPEED = new HAGetParam("DOWNLOAD_SPEED", 1);
    BUFFER_LENTH = new HAGetParam("BUFFER_LENTH", 2);
    PLAY_BITRATE = new HAGetParam("PLAY_BITRATE", 3);
    AUDIO_TRACK_INFO = new HAGetParam("AUDIO_TRACK_INFO", 4);
    SUBTITLES_TRACK_INFO = new HAGetParam("SUBTITLES_TRACK_INFO", 5);
    VIDOE_FPS = new HAGetParam("VIDOE_FPS", 6);
    PLAYER_VERSION = new HAGetParam("PLAYER_VERSION", 7);
    HAGetParam[] arrayOfHAGetParam = new HAGetParam[8];
    arrayOfHAGetParam[0] = MEDIA_BITRATES;
    arrayOfHAGetParam[1] = DOWNLOAD_SPEED;
    arrayOfHAGetParam[2] = BUFFER_LENTH;
    arrayOfHAGetParam[3] = PLAY_BITRATE;
    arrayOfHAGetParam[4] = AUDIO_TRACK_INFO;
    arrayOfHAGetParam[5] = SUBTITLES_TRACK_INFO;
    arrayOfHAGetParam[6] = VIDOE_FPS;
    arrayOfHAGetParam[7] = PLAYER_VERSION;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.parameter.HAGetParam
 * JD-Core Version:    0.6.2
 */