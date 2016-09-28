package android.net.dlna;

import java.util.ArrayList;

public abstract interface MediaServerController
{
  public abstract void Authenticate(String paramString)
    throws ActionUnsupportedException, HostUnreachableException, AuthenticateFailedException;

  @Deprecated
  public abstract ArrayList<ShareObject> Browse(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3)
    throws ActionUnsupportedException, HostUnreachableException, MissingAuthenticationException;

  public abstract BrowseResult Browse_Ex(String paramString1, BrowseFlag paramBrowseFlag, String paramString2, int paramInt1, int paramInt2, String paramString3)
    throws ActionUnsupportedException, HostUnreachableException, MissingAuthenticationException;

  public abstract ShareItem CreateObject(String paramString1, String paramString2);

  public abstract DeviceInfo GetDeviceInfo()
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract ArrayList<ProtocolInfo> GetProtocolInfo()
    throws ActionUnsupportedException, HostUnreachableException;

  public abstract int TransferFile(String paramString1, String paramString2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.MediaServerController
 * JD-Core Version:    0.6.2
 */