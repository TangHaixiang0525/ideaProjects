package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.CaptureThumbnailResult;
import com.mstar.android.tvapi.common.vo.EnumPvrStatus;
import com.mstar.android.tvapi.common.vo.PvrFileInfo;
import com.mstar.android.tvapi.common.vo.PvrPlaybackSpeed.EnumPvrPlaybackSpeed;
import com.mstar.android.tvapi.common.vo.PvrUsbDeviceLabel.EnumPvrUsbDeviceLabel;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public final class PvrManager
{
  public static final int E_FILE_SYSTEM_TYPE_EXFAT = 7;
  public static final int E_FILE_SYSTEM_TYPE_EXT2 = 3;
  public static final int E_FILE_SYSTEM_TYPE_EXT3 = 4;
  public static final int E_FILE_SYSTEM_TYPE_EXT4 = 8;
  public static final int E_FILE_SYSTEM_TYPE_INVALID = 9;
  public static final int E_FILE_SYSTEM_TYPE_JFFS2 = 1;
  public static final int E_FILE_SYSTEM_TYPE_MSDOS = 5;
  public static final int E_FILE_SYSTEM_TYPE_NTFS = 6;
  public static final int E_FILE_SYSTEM_TYPE_UNKNOWN = 0;
  public static final int E_FILE_SYSTEM_TYPE_VFAT = 2;
  public static final int PVR_FILE_INFO_SORT_CHANNEL = 3;
  public static final int PVR_FILE_INFO_SORT_FILENAME = 0;
  public static final int PVR_FILE_INFO_SORT_LCN = 2;
  public static final int PVR_FILE_INFO_SORT_MAX_KEY = 5;
  public static final int PVR_FILE_INFO_SORT_PROGRAM = 4;
  public static final int PVR_FILE_INFO_SORT_TIME = 1;

  protected static PvrManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native boolean assignThumbnailFileInfoHandler(String paramString)
    throws TvCommonException;

  public final native CaptureThumbnailResult captureThumbnail()
    throws TvCommonException;

  public native boolean changeDevice(short paramShort)
    throws TvCommonException;

  public final native int checkUsbSpeed()
    throws TvCommonException;

  public final native void clearMetadata()
    throws TvCommonException;

  public final native boolean createMetadata(String paramString)
    throws TvCommonException;

  public final native void deletefile(int paramInt, String paramString)
    throws TvCommonException;

  public final native void doPlaybackFastBackward()
    throws TvCommonException;

  public final native void doPlaybackFastForward()
    throws TvCommonException;

  public final native void doPlaybackJumpBackward()
    throws TvCommonException;

  public final native void doPlaybackJumpForward()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native int getCurPlaybackTimeInSecond()
    throws TvCommonException;

  public final native String getCurPlaybackingFileName()
    throws TvCommonException;

  public final native int getCurRecordTimeInSecond()
    throws TvCommonException;

  public final native String getCurRecordingFileName()
    throws TvCommonException;

  public final native int getEstimateRecordRemainingTime()
    throws TvCommonException;

  public final native String getFileEventName(String paramString)
    throws TvCommonException;

  public final native int getFileLcn(int paramInt)
    throws TvCommonException;

  public final native String getFileServiceName(String paramString)
    throws TvCommonException;

  public final native int getMetadataSortKey()
    throws TvCommonException;

  public final PvrPlaybackSpeed.EnumPvrPlaybackSpeed getPlaybackSpeed()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native PvrFileInfo getPvrFileInfo(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native int getPvrFileNumber()
    throws TvCommonException;

  public final native String getPvrMountPath()
    throws TvCommonException;

  public final native int getRecordedFileDurationTime(String paramString)
    throws TvCommonException;

  public final native String getThumbnailDisplay(int paramInt)
    throws TvCommonException;

  public final native int getThumbnailNumber()
    throws TvCommonException;

  public final native String getThumbnailPath(int paramInt)
    throws TvCommonException;

  public final native int[] getThumbnailTimeStamp(int paramInt)
    throws TvCommonException;

  public final native short getUsbDeviceIndex()
    throws TvCommonException;

  public final PvrUsbDeviceLabel.EnumPvrUsbDeviceLabel getUsbDeviceLabel(int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native int getUsbDeviceNumber()
    throws TvCommonException;

  public final native int getUsbPartitionNumber()
    throws TvCommonException;

  public final native boolean isAlwaysTimeShiftPlaybackPaused()
    throws TvCommonException;

  public final native boolean isAlwaysTimeShiftRecording()
    throws TvCommonException;

  public final native boolean isMetadataSortAscending()
    throws TvCommonException;

  public final native boolean isPlaybackParentalLock()
    throws TvCommonException;

  public final native boolean isPlaybackPaused()
    throws TvCommonException;

  public final native boolean isPlaybacking()
    throws TvCommonException;

  public final native boolean isRecordPaused()
    throws TvCommonException;

  public final native boolean isRecording()
    throws TvCommonException;

  public final native boolean isTimeShiftRecording()
    throws TvCommonException;

  public final native boolean jumpPlaybackTime(int paramInt)
    throws TvCommonException;

  public final native boolean jumpToThumbnail(int paramInt)
    throws TvCommonException;

  public final EnumPvrStatus pauseAlwaysTimeShiftPlayback(boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native short pauseAlwaysTimeShiftRecord()
    throws TvCommonException;

  public final native void pausePlayback()
    throws TvCommonException;

  public final native void pauseRecord()
    throws TvCommonException;

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void resumePlayback()
    throws TvCommonException;

  public final native void resumeRecord()
    throws TvCommonException;

  public native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public final native void setMetadataSortAscending(boolean paramBoolean)
    throws TvCommonException;

  public final native void setMetadataSortKey(int paramInt)
    throws TvCommonException;

  public void setOnPvrEventListener(OnPvrEventListener paramOnPvrEventListener)
  {
    throw new RuntimeException("stub");
  }

  public final void setPlaybackSpeed(PvrPlaybackSpeed.EnumPvrPlaybackSpeed paramEnumPvrPlaybackSpeed)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setPlaybackWindow(VideoWindowType paramVideoWindowType, int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native boolean setPvrParams(String paramString, short paramShort)
    throws TvCommonException;

  public final native void setRecordAll(boolean paramBoolean)
    throws TvCommonException;

  public final native void setTimeShiftFileSize(long paramLong)
    throws TvCommonException;

  public final native short startAlwaysTimeShiftPlayback()
    throws TvCommonException;

  public final native short startAlwaysTimeShiftRecord()
    throws TvCommonException;

  public EnumPvrStatus startPlayback(String paramString)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPvrStatus startPlayback(String paramString, int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPvrStatus startPlayback(String paramString, int paramInt1, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void startPlaybackLoop(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final EnumPvrStatus startRecord()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EnumPvrStatus startTimeShiftPlayback()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public EnumPvrStatus startTimeShiftRecord()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void stepInPlayback()
    throws TvCommonException;

  public final native void stopAlwaysTimeShiftPlayback()
    throws TvCommonException;

  public final native short stopAlwaysTimeShiftRecord()
    throws TvCommonException;

  public final native void stopPlayback()
    throws TvCommonException;

  public final native void stopPlaybackLoop()
    throws TvCommonException;

  public final native boolean stopPvr()
    throws TvCommonException;

  public final native void stopRecord()
    throws TvCommonException;

  public final native void stopTimeShift()
    throws TvCommonException;

  public final native void stopTimeShiftPlayback()
    throws TvCommonException;

  public final native void stopTimeShiftRecord()
    throws TvCommonException;

  protected static enum EVENT
  {
  }

  public static abstract interface OnPvrEventListener
  {
    public abstract boolean onPvrNotifyFormatFinished(PvrManager paramPvrManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onPvrNotifyPlaybackBegin(PvrManager paramPvrManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onPvrNotifyPlaybackStop(PvrManager paramPvrManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onPvrNotifyUsbInserted(PvrManager paramPvrManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onPvrNotifyUsbRemoved(PvrManager paramPvrManager, int paramInt1, int paramInt2, int paramInt3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.PvrManager
 * JD-Core Version:    0.6.2
 */