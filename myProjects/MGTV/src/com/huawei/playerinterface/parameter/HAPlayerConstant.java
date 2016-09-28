package com.huawei.playerinterface.parameter;

public class HAPlayerConstant
{
  public static class ErrorCode
  {
    public static final int MEDIA_ERROR_DRM_FAILED = 107;
    public static final int MEDIA_ERROR_EXTERNAL = 109;
    public static final int MEDIA_ERROR_INTERNAL = 108;
    public static final int MEDIA_ERROR_IO_FAILED = 103;
    public static final int MEDIA_ERROR_IO_TIMEOUT = 104;
    public static final int MEDIA_ERROR_OUTPUT_BLOCKING = 110;
    public static final int MEDIA_ERROR_PARSE_FAILED = 106;
    public static final int MEDIA_ERROR_PROTOCOL_SPEC = 105;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED_CODEC = 101;
    public static final int MEDIA_ERROR_UNSUPPORTED_FORMAT = 102;
  }

  public static class InfoCode
  {
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_LOW_DOWNLOAD_SPEED = 2;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
  }

  public static class MediaTrack
  {
    public static final int AUDIO = 1;
    public static final int VIDEO = 2;
  }

  public static class ProxyCode
  {
    public static final int EXIT_FLAG = 0;
    public static final int INDEX_HMS_ERROR = 2;
    public static final int INDEX_TIMEOUT = 1;
    public static final int PLAYLIST_HSM_ERROR = 4;
    public static final int PLAYLIST_NOT_UPDATED = 5;
    public static final int PLAYLIST_TIMEOUT = 3;
    public static final int TS_HMS_ERROR = 7;
    public static final int TS_LOWBANDWIDTH = 9;
    public static final int TS_TIMEOUT = 6;
  }

  public static class ScaleMode
  {
    public static final int FULL_SCREEN = 1;
    public static final int KEEP_SCALE;
  }

  public static class VideoType
  {
    public static final int OTHER = 3;
    public static final int TSTV = 2;
    public static final int TV = 1;
    public static final int VOD;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.parameter.HAPlayerConstant
 * JD-Core Version:    0.6.2
 */