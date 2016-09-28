package com.mstar.android.media;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.view.SurfaceHolder;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

public class MMediaPlayer extends MediaPlayer
{
  public static final int MEDIA_INFO_AUDIO_UNSUPPORT = 1002;
  public static final int MEDIA_INFO_NETWORK_CACHE_PERCENT = 1001;
  public static final int MEDIA_INFO_SUBTITLE_UPDATA = 1000;
  public static final int MEDIA_INFO_VIDEO_UNSUPPORT = 1003;
  public static final int MEDIA_TRACK_TYPE_TIMEDBITMAP = 4;

  public static MMediaPlayer create(InputStream paramInputStream, SurfaceHolder paramSurfaceHolder)
  {
    throw new RuntimeException("stub");
  }

  public boolean DataSourceSwitch(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public boolean DemuxReset()
  {
    throw new RuntimeException("stub");
  }

  public boolean SetSeamlessMode(EnumPlayerSeamlessMode paramEnumPlayerSeamlessMode)
  {
    throw new RuntimeException("stub");
  }

  public void addTimedTextSource(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2, String paramString)
    throws IllegalArgumentException, IllegalStateException
  {
    throw new RuntimeException("stub");
  }

  public void addTimedTextSource(String paramString1, String paramString2)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new RuntimeException("stub");
  }

  public Bitmap captureVideoThumbnail(String paramString, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public void captureVideoThumbnailRelease()
  {
    throw new RuntimeException("stub");
  }

  public native int divx_GetAutochapter();

  public native int divx_GetAutochapterTime(int paramInt);

  public native int divx_GetChapter();

  public native int divx_GetChapterTime(int paramInt);

  public DivxDrmInfo divx_GetDrmInfo()
  {
    throw new RuntimeException("stub");
  }

  public native int divx_GetEdition();

  public void divx_GetResumePlay(DivxResumeInfo paramDivxResumeInfo)
  {
    throw new RuntimeException("stub");
  }

  public native int divx_GetTitle();

  public native void divx_SetAutochapter(int paramInt);

  public native void divx_SetChapter(int paramInt);

  public native void divx_SetEdition(int paramInt);

  public int divx_SetResumePlay(DivxResumeInfo paramDivxResumeInfo)
  {
    throw new RuntimeException("stub");
  }

  public native void divx_SetTitle(int paramInt);

  public SubtitleTrackInfo getAllSubtitleTrackInfo()
  {
    throw new RuntimeException("stub");
  }

  public String getAudioCodecType()
  {
    throw new RuntimeException("stub");
  }

  public AudioTrackInfo getAudioTrackInfo(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public native String getAudioTrackStringData(int paramInt);

  public native int getPlayMode();

  public native String getSubtitleData();

  public SubtitleTrackInfo getSubtitleTrackInfo(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public VideoCodecInfo getVideoInfo()
  {
    throw new RuntimeException("stub");
  }

  public native Bitmap native_captureMovieThumbnail(Parcel paramParcel1, Parcel paramParcel2);

  public native void native_divx_SetReplayFlag(boolean paramBoolean);

  public native int native_divx_SetResumePlay(Parcel paramParcel);

  public native void native_setExternalDataSourceInfo(Parcel paramParcel);

  public native void native_setVideoDisplayAspectRatio(int paramInt);

  public native void offSubtitleTrack();

  public native void onSubtitleTrack();

  public native void setAudioTrack(int paramInt);

  public void setDataSource(InputStream paramInputStream)
    throws IOException, IllegalArgumentException, IllegalStateException
  {
    throw new RuntimeException("stub");
  }

  public boolean setExternalDataSourceAppType(EN_DATASOURCE_APP_TYPE paramEN_DATASOURCE_APP_TYPE)
  {
    throw new RuntimeException("stub");
  }

  public boolean setExternalDataSourceAudioCodec(EN_MS_DATASOURCE_ES_AUDIO_CODEC paramEN_MS_DATASOURCE_ES_AUDIO_CODEC)
  {
    throw new RuntimeException("stub");
  }

  public boolean setExternalDataSourceContentType(EN_MS_DATASOURCE_CONTENT_TYPE paramEN_MS_DATASOURCE_CONTENT_TYPE)
  {
    throw new RuntimeException("stub");
  }

  public boolean setExternalDataSourceMediaFormat(EN_DATASOURCE_MEDIA_FORMAT_TYPE paramEN_DATASOURCE_MEDIA_FORMAT_TYPE)
  {
    throw new RuntimeException("stub");
  }

  public boolean setExternalDataSourcePlayerType(EN_MS_DATASOURCE_PLAYER_TYPE paramEN_MS_DATASOURCE_PLAYER_TYPE)
  {
    throw new RuntimeException("stub");
  }

  public boolean setExternalDataSourceVideoCodec(EN_MS_DATASOURCE_ES_VIDEO_CODEC paramEN_MS_DATASOURCE_ES_VIDEO_CODEC)
  {
    throw new RuntimeException("stub");
  }

  public native boolean setPlayMode(int paramInt);

  public native void setSubtitleDataSource(String paramString);

  public void setSubtitleDisplay(SurfaceHolder paramSurfaceHolder)
  {
    throw new RuntimeException("stub");
  }

  public native int setSubtitleSync(int paramInt);

  public native void setSubtitleTrack(int paramInt);

  public boolean setVideoDisplayAspectRatio(EnumVideoAspectRatio paramEnumVideoAspectRatio)
  {
    throw new RuntimeException("stub");
  }

  public static enum EN_DATASOURCE_APP_TYPE
  {
  }

  public static enum EN_DATASOURCE_MEDIA_FORMAT_TYPE
  {
  }

  public static enum EN_MOVIE_THUMBNAIL_FORMAT
  {
  }

  public static enum EN_MS_DATASOURCE_CONTENT_TYPE
  {
  }

  public static enum EN_MS_DATASOURCE_ES_AUDIO_CODEC
  {
  }

  public static enum EN_MS_DATASOURCE_ES_VIDEO_CODEC
  {
  }

  public static enum EN_MS_DATASOURCE_PLAYER_TYPE
  {
  }

  public static enum EnumPlayerSeamlessMode
  {
  }

  public static enum EnumVideoAspectRatio
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.media.MMediaPlayer
 * JD-Core Version:    0.6.2
 */