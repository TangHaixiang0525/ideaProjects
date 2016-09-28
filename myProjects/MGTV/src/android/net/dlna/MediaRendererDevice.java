package android.net.dlna;

import java.util.ArrayList;

public abstract interface MediaRendererDevice
{
  public abstract DeviceInfo GetDeviceInfo();

  public abstract void SetDeviceCapabilities(int paramInt, DeviceCapabilities paramDeviceCapabilities);

  public abstract void SetDeviceInfo(DeviceInfo paramDeviceInfo);

  public abstract void SetListener(MediaRendererDeviceListener paramMediaRendererDeviceListener);

  public abstract void SetMediaInfo(int paramInt, MediaInfo paramMediaInfo);

  public abstract void SetPositionInfo(int paramInt, PositionInfo paramPositionInfo);

  public abstract void SetProtocolInfo(ArrayList<ProtocolInfo> paramArrayList);

  public abstract void SetTransportInfo(int paramInt, TransportInfo paramTransportInfo);

  public abstract void SetTransportSettings(int paramInt, TransportSettings paramTransportSettings);

  public abstract void SetVolume(int paramInt, VolumeInfo paramVolumeInfo);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.MediaRendererDevice
 * JD-Core Version:    0.6.2
 */