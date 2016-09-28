package android.net.dlna;

import java.util.ArrayList;

public abstract interface MediaRendererController
{
  public abstract DeviceCapabilities GetDeviceCapabilities(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract DeviceInfo GetDeviceInfo()
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract MediaInfo GetMediaInfo(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract PositionInfo GetPositionInfo(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract ArrayList<ProtocolInfo> GetProtocolInfo()
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract TransportInfo GetTransportInfo(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract TransportSettings GetTransportSettings(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract int GetVolume(int paramInt, Channel paramChannel)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void Next(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void Pause(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void Play(int paramInt, String paramString)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void Previous(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void RotateClockwise(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void RotateCounterClockwise(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void Seek(int paramInt, SeekMode paramSeekMode, String paramString)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void SetAVTransportURI(int paramInt, String paramString1, String paramString2)
    throws ActionUnsupportedException, HostUnreachableException, ActionFailedException;

  public abstract void SetVolume(int paramInt, VolumeInfo paramVolumeInfo)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void Stop(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void ZoomIn(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract void ZoomOut(int paramInt)
    throws ActionUnsupportedException, HostUnreachableException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.MediaRendererController
 * JD-Core Version:    0.6.2
 */