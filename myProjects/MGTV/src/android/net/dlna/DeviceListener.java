package android.net.dlna;

public abstract interface DeviceListener
{
  public abstract void OnDisappeared(MediaRendererController paramMediaRendererController);

  public abstract void OnDisappeared(MediaServerController paramMediaServerController);

  public abstract void OnDiscovered(MediaRendererController paramMediaRendererController);

  public abstract void OnDiscovered(MediaServerController paramMediaServerController);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.DeviceListener
 * JD-Core Version:    0.6.2
 */