package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITvAudio extends IInterface
{
  public abstract boolean getAvcMode()
    throws RemoteException;

  public abstract int getBalance()
    throws RemoteException;

  public abstract int getBass()
    throws RemoteException;

  public abstract int getBassSwitch()
    throws RemoteException;

  public abstract int getBassVolume()
    throws RemoteException;

  public abstract int getDtvOutputMode()
    throws RemoteException;

  public abstract int getEarPhoneVolume()
    throws RemoteException;

  public abstract int getEqBand10k()
    throws RemoteException;

  public abstract int getEqBand120()
    throws RemoteException;

  public abstract int getEqBand1500()
    throws RemoteException;

  public abstract int getEqBand500()
    throws RemoteException;

  public abstract int getEqBand5k()
    throws RemoteException;

  public abstract int getPowerOnOffMusic()
    throws RemoteException;

  public abstract int getSeparateHear()
    throws RemoteException;

  public abstract int getSoundMode()
    throws RemoteException;

  public abstract int getSpdifOutMode()
    throws RemoteException;

  public abstract int getSurroundMode()
    throws RemoteException;

  public abstract int getTreble()
    throws RemoteException;

  public abstract int getTrueBass()
    throws RemoteException;

  public abstract int getWallmusic()
    throws RemoteException;

  public abstract void registerOnAudioEventListener(int paramInt)
    throws RemoteException;

  public abstract boolean setAvcMode(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setBalance(int paramInt)
    throws RemoteException;

  public abstract boolean setBass(int paramInt)
    throws RemoteException;

  public abstract boolean setBassSwitch(int paramInt)
    throws RemoteException;

  public abstract boolean setBassVolume(int paramInt)
    throws RemoteException;

  public abstract void setDtvOutputMode(int paramInt)
    throws RemoteException;

  public abstract boolean setEarPhoneVolume(int paramInt)
    throws RemoteException;

  public abstract boolean setEqBand10k(int paramInt)
    throws RemoteException;

  public abstract boolean setEqBand120(int paramInt)
    throws RemoteException;

  public abstract boolean setEqBand1500(int paramInt)
    throws RemoteException;

  public abstract boolean setEqBand500(int paramInt)
    throws RemoteException;

  public abstract boolean setEqBand5k(int paramInt)
    throws RemoteException;

  public abstract boolean setPowerOnOffMusic(int paramInt)
    throws RemoteException;

  public abstract boolean setSeparateHear(int paramInt)
    throws RemoteException;

  public abstract boolean setSoundMode(int paramInt)
    throws RemoteException;

  public abstract boolean setSpdifOutMode(int paramInt)
    throws RemoteException;

  public abstract boolean setSurroundMode(int paramInt)
    throws RemoteException;

  public abstract boolean setTreble(int paramInt)
    throws RemoteException;

  public abstract boolean setTrueBass(int paramInt)
    throws RemoteException;

  public abstract boolean setWallmusic(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvAudio
  {
    public static ITvAudio asInterface(IBinder paramIBinder)
    {
      throw new RuntimeException("stub");
    }

    public IBinder asBinder()
    {
      throw new RuntimeException("stub");
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.ITvAudio
 * JD-Core Version:    0.6.2
 */