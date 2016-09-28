package android.net.samba;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.mstar.android.storage.MStorageManager;
import java.net.UnknownHostException;
import java.util.ArrayList;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class SmbDevice
  implements Parcelable
{
  public static final Parcelable.Creator<SmbDevice> CREATOR;
  public static final int FLAG_WRITEABLE = 1;

  public SmbDevice()
  {
  }

  public SmbDevice(Parcel paramParcel)
  {
  }

  public SmbDevice(String paramString)
  {
  }

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public String getAddress()
  {
    throw new RuntimeException("stub");
  }

  public SmbAuthentication getAuth()
  {
    throw new RuntimeException("stub");
  }

  public String getHostName()
    throws UnknownHostException
  {
    throw new RuntimeException("stub");
  }

  public ArrayList<SmbShareFolder> getSharefolderList()
  {
    throw new RuntimeException("stub");
  }

  public boolean hasPassword()
  {
    throw new RuntimeException("stub");
  }

  public boolean isActive()
  {
    throw new RuntimeException("stub");
  }

  public boolean isHost(SmbFile paramSmbFile)
    throws SmbException
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

  public int mount(SmbAuthentication paramSmbAuthentication, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void mount(SmbAuthentication paramSmbAuthentication)
  {
    throw new RuntimeException("stub");
  }

  public String remotePath()
  {
    throw new RuntimeException("stub");
  }

  public void setAddress(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setAuth(SmbAuthentication paramSmbAuthentication)
  {
    throw new RuntimeException("stub");
  }

  public void setHostName(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setOnRecvMsg(OnRecvMsg paramOnRecvMsg)
  {
    throw new RuntimeException("stub");
  }

  public void setStorageManager(MStorageManager paramMStorageManager)
  {
    throw new RuntimeException("stub");
  }

  public boolean testPassword(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public int unmount()
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.samba.SmbDevice
 * JD-Core Version:    0.6.2
 */