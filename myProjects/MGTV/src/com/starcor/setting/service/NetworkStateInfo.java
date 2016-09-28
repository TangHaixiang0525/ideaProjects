package com.starcor.setting.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NetworkStateInfo
  implements Parcelable
{
  public static final Parcelable.Creator<NetworkStateInfo> CREATOR = new Parcelable.Creator()
  {
    public NetworkStateInfo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new NetworkStateInfo(paramAnonymousParcel, null);
    }

    public NetworkStateInfo[] newArray(int paramAnonymousInt)
    {
      return new NetworkStateInfo[paramAnonymousInt];
    }
  };
  public NetworkDetailedState detailedState;
  public NetworkState state;

  private NetworkStateInfo(Parcel paramParcel)
  {
    this.state = NetworkState.values()[paramParcel.readInt()];
    this.detailedState = NetworkDetailedState.values()[paramParcel.readInt()];
  }

  public NetworkStateInfo(NetworkState paramNetworkState, NetworkDetailedState paramNetworkDetailedState)
  {
    this.state = paramNetworkState;
    this.detailedState = paramNetworkDetailedState;
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.state.ordinal());
    paramParcel.writeInt(this.detailedState.ordinal());
  }

  public static enum NetworkDetailedState
  {
    static
    {
      CONNECTING = new NetworkDetailedState("CONNECTING", 2);
      AUTHENTICATING = new NetworkDetailedState("AUTHENTICATING", 3);
      OBTAINING_IPADDR = new NetworkDetailedState("OBTAINING_IPADDR", 4);
      CONNECTED = new NetworkDetailedState("CONNECTED", 5);
      SUSPENDED = new NetworkDetailedState("SUSPENDED", 6);
      DISCONNECTING = new NetworkDetailedState("DISCONNECTING", 7);
      DISCONNECTED = new NetworkDetailedState("DISCONNECTED", 8);
      FAILED = new NetworkDetailedState("FAILED", 9);
      NetworkDetailedState[] arrayOfNetworkDetailedState = new NetworkDetailedState[10];
      arrayOfNetworkDetailedState[0] = IDLE;
      arrayOfNetworkDetailedState[1] = SCANNING;
      arrayOfNetworkDetailedState[2] = CONNECTING;
      arrayOfNetworkDetailedState[3] = AUTHENTICATING;
      arrayOfNetworkDetailedState[4] = OBTAINING_IPADDR;
      arrayOfNetworkDetailedState[5] = CONNECTED;
      arrayOfNetworkDetailedState[6] = SUSPENDED;
      arrayOfNetworkDetailedState[7] = DISCONNECTING;
      arrayOfNetworkDetailedState[8] = DISCONNECTED;
      arrayOfNetworkDetailedState[9] = FAILED;
    }
  }

  public static enum NetworkState
  {
    static
    {
      CONNECTED = new NetworkState("CONNECTED", 1);
      SUSPENDED = new NetworkState("SUSPENDED", 2);
      DISCONNECTING = new NetworkState("DISCONNECTING", 3);
      DISCONNECTED = new NetworkState("DISCONNECTED", 4);
      UNKNOWN = new NetworkState("UNKNOWN", 5);
      NetworkState[] arrayOfNetworkState = new NetworkState[6];
      arrayOfNetworkState[0] = CONNECTING;
      arrayOfNetworkState[1] = CONNECTED;
      arrayOfNetworkState[2] = SUSPENDED;
      arrayOfNetworkState[3] = DISCONNECTING;
      arrayOfNetworkState[4] = DISCONNECTED;
      arrayOfNetworkState[5] = UNKNOWN;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.setting.service.NetworkStateInfo
 * JD-Core Version:    0.6.2
 */