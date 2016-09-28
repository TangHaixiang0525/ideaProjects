package android.net.dlna;

import java.util.ArrayList;

public abstract interface DLNA
{
  public abstract void AsyncSearchDevice();

  public abstract MediaRendererDevice CreateMediaRendererDevice();

  public abstract MediaServerDevice CreateMediaServerDevice();

  public abstract void Finalize();

  public abstract String GetIP();

  public abstract ArrayList<MediaRendererController> GetMediaRendererControllerList();

  public abstract ArrayList<MediaServerController> GetMediaServerControllerList();

  public abstract int GetPort();

  public abstract String GetVersion();

  public abstract boolean Initialize(String paramString, int paramInt);

  public abstract void SetDescriptionFile(String paramString);

  public abstract void SetDeviceListener(DeviceListener paramDeviceListener);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.DLNA
 * JD-Core Version:    0.6.2
 */