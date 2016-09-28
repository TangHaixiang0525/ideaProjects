package android.net.dlna;

import java.util.ArrayList;

public abstract interface MediaServerDevice
{
  public abstract void AddMediaFile(String paramString, MediaMetaData paramMediaMetaData);

  public abstract ShareItem Browse(String paramString);

  public abstract int DisableUpload();

  public abstract int EnableUpload();

  public abstract DeviceInfo GetDeviceInfo();

  public abstract void RemoveMediaFile(String paramString);

  public abstract void SetDeviceInfo(DeviceInfo paramDeviceInfo);

  public abstract void SetListener(MediaServerDeviceListener paramMediaServerDeviceListener);

  public abstract void SetPassword(String paramString);

  public abstract void SetProtocolInfo(ArrayList<ProtocolInfo> paramArrayList);

  public abstract int SetUploadItemSaveDir(String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.MediaServerDevice
 * JD-Core Version:    0.6.2
 */