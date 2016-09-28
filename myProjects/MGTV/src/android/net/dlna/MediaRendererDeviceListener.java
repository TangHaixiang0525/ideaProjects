package android.net.dlna;

public abstract interface MediaRendererDeviceListener
{
  public abstract boolean OnGetDeviceCapabilities(int paramInt);

  public abstract boolean OnGetMediaInfo(int paramInt);

  public abstract boolean OnGetPositionInfo(int paramInt);

  public abstract boolean OnGetProtocolInfo();

  public abstract boolean OnGetTransportInfo(int paramInt);

  public abstract boolean OnGetTransportSettings(int paramInt);

  public abstract boolean OnGetVolume(int paramInt);

  public abstract boolean OnKeepAlive(int paramInt);

  public abstract boolean OnNext(int paramInt);

  public abstract boolean OnPause(int paramInt);

  public abstract boolean OnPlay(int paramInt, String paramString);

  public abstract boolean OnPrevious(int paramInt);

  public abstract boolean OnRotateClockwise(int paramInt);

  public abstract boolean OnRotateCounterClockwise(int paramInt);

  public abstract boolean OnSeek(int paramInt, SeekMode paramSeekMode, String paramString);

  public abstract boolean OnSelectPreset(int paramInt);

  public abstract boolean OnSetAVTransportURI(int paramInt, String paramString, ShareObject paramShareObject);

  public abstract boolean OnSetNextAVTransportURI(int paramInt, String paramString, ShareObject paramShareObject);

  public abstract boolean OnSetVolume(int paramInt, VolumeInfo paramVolumeInfo);

  public abstract boolean OnStop(int paramInt);

  public abstract boolean OnZoomIn(int paramInt);

  public abstract boolean OnZoomOut(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.MediaRendererDeviceListener
 * JD-Core Version:    0.6.2
 */