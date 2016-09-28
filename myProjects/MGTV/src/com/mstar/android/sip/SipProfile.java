package com.mstar.android.sip;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import java.text.ParseException;
import javax.sip.address.Address;
import javax.sip.address.SipURI;

public class SipProfile
  implements Parcelable, Serializable, Cloneable
{
  public static final Parcelable.Creator<SipProfile> CREATOR;

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public String getAuthUserName()
  {
    throw new RuntimeException("stub");
  }

  public boolean getAutoRegistration()
  {
    throw new RuntimeException("stub");
  }

  public int getCallingUid()
  {
    throw new RuntimeException("stub");
  }

  public String getDisplayName()
  {
    throw new RuntimeException("stub");
  }

  public String getPassword()
  {
    throw new RuntimeException("stub");
  }

  public int getPort()
  {
    throw new RuntimeException("stub");
  }

  public String getProfileName()
  {
    throw new RuntimeException("stub");
  }

  public String getProtocol()
  {
    throw new RuntimeException("stub");
  }

  public String getProxyAddress()
  {
    throw new RuntimeException("stub");
  }

  public boolean getSendKeepAlive()
  {
    throw new RuntimeException("stub");
  }

  public Address getSipAddress()
  {
    throw new RuntimeException("stub");
  }

  public String getSipDomain()
  {
    throw new RuntimeException("stub");
  }

  public SipURI getUri()
  {
    throw new RuntimeException("stub");
  }

  public String getUriString()
  {
    throw new RuntimeException("stub");
  }

  public String getUserName()
  {
    throw new RuntimeException("stub");
  }

  public void setCallingUid(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static class Builder
  {
    public Builder(SipProfile paramSipProfile)
    {
    }

    public Builder(String paramString)
      throws ParseException
    {
    }

    public Builder(String paramString1, String paramString2)
      throws ParseException
    {
    }

    public SipProfile build()
    {
      throw new RuntimeException("stub");
    }

    public Builder setAuthUserName(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public Builder setAutoRegistration(boolean paramBoolean)
    {
      throw new RuntimeException("stub");
    }

    public Builder setDisplayName(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public Builder setOutboundProxy(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public Builder setPassword(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public Builder setPort(int paramInt)
      throws IllegalArgumentException
    {
      throw new RuntimeException("stub");
    }

    public Builder setProfileName(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public Builder setProtocol(String paramString)
      throws IllegalArgumentException
    {
      throw new RuntimeException("stub");
    }

    public Builder setSendKeepAlive(boolean paramBoolean)
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.SipProfile
 * JD-Core Version:    0.6.2
 */