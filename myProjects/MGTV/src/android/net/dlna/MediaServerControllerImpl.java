package android.net.dlna;

import java.util.ArrayList;

class MediaServerControllerImpl
  implements MediaServerController
{
  public void Authenticate(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public ArrayList<ShareObject> Browse(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3)
  {
    throw new RuntimeException("stub");
  }

  public BrowseResult Browse_Ex(String paramString1, BrowseFlag paramBrowseFlag, String paramString2, int paramInt1, int paramInt2, String paramString3)
    throws ActionUnsupportedException, HostUnreachableException, MissingAuthenticationException
  {
    throw new RuntimeException("stub");
  }

  public ShareItem CreateObject(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public DeviceInfo GetDeviceInfo()
  {
    throw new RuntimeException("stub");
  }

  public ArrayList<ProtocolInfo> GetProtocolInfo()
  {
    throw new RuntimeException("stub");
  }

  public int TransferFile(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.dlna.MediaServerControllerImpl
 * JD-Core Version:    0.6.2
 */