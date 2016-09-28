package android.net.samba;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SmbShareFolder extends SambaFile
  implements Parcelable
{
  public static final Parcelable.Creator<SmbShareFolder> CREATOR;

  public SmbShareFolder(SmbDevice paramSmbDevice)
  {
  }

  public SmbShareFolder(SmbDevice paramSmbDevice, String paramString)
  {
  }

  public SmbShareFolder(Parcel paramParcel)
  {
  }

  public boolean canRead()
  {
    throw new RuntimeException("stub");
  }

  public boolean canWrite()
  {
    throw new RuntimeException("stub");
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public SmbAuthentication getAuth()
  {
    throw new RuntimeException("stub");
  }

  public SmbDevice getSmbDevice()
  {
    throw new RuntimeException("stub");
  }

  public boolean isMounted()
  {
    throw new RuntimeException("stub");
  }

  public String localPath()
  {
    throw new RuntimeException("stub");
  }

  public void mount(SmbAuthentication paramSmbAuthentication, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public String remotePath()
  {
    throw new RuntimeException("stub");
  }

  public void unmount()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.samba.SmbShareFolder
 * JD-Core Version:    0.6.2
 */