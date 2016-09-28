package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITvFactory extends IInterface
{
  public abstract boolean changeWbParameterWhenSourceChange()
    throws RemoteException;

  public abstract boolean enableUartDebug()
    throws RemoteException;

  public abstract boolean execAutoAdc()
    throws RemoteException;

  public abstract boolean execSetInputSource(int paramInt)
    throws RemoteException;

  public abstract int get3DSelfAdaptiveLevel()
    throws RemoteException;

  public abstract int getAdcBlueGain()
    throws RemoteException;

  public abstract int getAdcBlueOffset()
    throws RemoteException;

  public abstract int getAdcGreenGain()
    throws RemoteException;

  public abstract int getAdcGreenOffset()
    throws RemoteException;

  public abstract int getAdcIndex()
    throws RemoteException;

  public abstract int getAdcPhase()
    throws RemoteException;

  public abstract int getAdcRedGain()
    throws RemoteException;

  public abstract int getAdcRedOffset()
    throws RemoteException;

  public abstract int getAefc43()
    throws RemoteException;

  public abstract int getAefc44()
    throws RemoteException;

  public abstract int getAefc66Bit76()
    throws RemoteException;

  public abstract int getAefc6EBit3210()
    throws RemoteException;

  public abstract int getAefc6EBit7654()
    throws RemoteException;

  public abstract int getAefcA0()
    throws RemoteException;

  public abstract int getAefcA1()
    throws RemoteException;

  public abstract int getAefcCb()
    throws RemoteException;

  public abstract int getAefcCfBit2Atv()
    throws RemoteException;

  public abstract int getAefcCfBit2Av()
    throws RemoteException;

  public abstract int getAefcD4()
    throws RemoteException;

  public abstract int getAefcD5Bit2()
    throws RemoteException;

  public abstract int getAefcD7HighBound()
    throws RemoteException;

  public abstract int getAefcD7LowBound()
    throws RemoteException;

  public abstract int getAefcD8Bit3210()
    throws RemoteException;

  public abstract int getAefcD9Bit0()
    throws RemoteException;

  public abstract int getAudioDspVersion()
    throws RemoteException;

  public abstract int getAudioHiDevMode()
    throws RemoteException;

  public abstract int getAudioNrThreshold()
    throws RemoteException;

  public abstract int getAudioPrescale()
    throws RemoteException;

  public abstract int getAudioSifThreshold()
    throws RemoteException;

  public abstract String getBoardType()
    throws RemoteException;

  public abstract int getChinaDescramblerBox()
    throws RemoteException;

  public abstract String getCompileTime()
    throws RemoteException;

  public abstract int getCurveType()
    throws RemoteException;

  public abstract int getDelayReduce()
    throws RemoteException;

  public abstract boolean getDtvAvAbnormalDelay()
    throws RemoteException;

  public abstract int getFactoryPreSetFeature()
    throws RemoteException;

  public abstract int getGainDistributionThreshold()
    throws RemoteException;

  public abstract int getLvdsModulation()
    throws RemoteException;

  public abstract int getLvdsPercentage()
    throws RemoteException;

  public abstract boolean getLvdsenable()
    throws RemoteException;

  public abstract boolean getMiuEnable()
    throws RemoteException;

  public abstract int getMiuModulation()
    throws RemoteException;

  public abstract int getMiuPercentage()
    throws RemoteException;

  public abstract int getOsdV0Nonlinear()
    throws RemoteException;

  public abstract int getOsdV100Nonlinear()
    throws RemoteException;

  public abstract int getOsdV25Nonlinear()
    throws RemoteException;

  public abstract int getOsdV50Nonlinear()
    throws RemoteException;

  public abstract int getOsdV75Nonlinear()
    throws RemoteException;

  public abstract int getOverScanHPosition()
    throws RemoteException;

  public abstract int getOverScanHSize()
    throws RemoteException;

  public abstract int getOverScanSourceType()
    throws RemoteException;

  public abstract int getOverScanVPosition()
    throws RemoteException;

  public abstract int getOverScanVSize()
    throws RemoteException;

  public abstract int getPanelSwing()
    throws RemoteException;

  public abstract String getPanelType()
    throws RemoteException;

  public abstract int getPeqFoCoarse(int paramInt)
    throws RemoteException;

  public abstract int getPeqFoFine(int paramInt)
    throws RemoteException;

  public abstract int getPeqGain(int paramInt)
    throws RemoteException;

  public abstract int getPeqQ(int paramInt)
    throws RemoteException;

  public abstract int getPowerOnMode()
    throws RemoteException;

  public abstract String getSoftWareVersion()
    throws RemoteException;

  public abstract int getTestPattern()
    throws RemoteException;

  public abstract boolean getUartOnOff()
    throws RemoteException;

  public abstract int getVdDspVersion()
    throws RemoteException;

  public abstract int getVifAgcRef()
    throws RemoteException;

  public abstract boolean getVifAsiaSignalOption()
    throws RemoteException;

  public abstract int getVifClampGainOvNegative()
    throws RemoteException;

  public abstract int getVifCrKi()
    throws RemoteException;

  public abstract int getVifCrKp()
    throws RemoteException;

  public abstract boolean getVifCrKpKiAdjust()
    throws RemoteException;

  public abstract int getVifCrThreshold()
    throws RemoteException;

  public abstract boolean getVifOverModulation()
    throws RemoteException;

  public abstract int getVifTop()
    throws RemoteException;

  public abstract int getVifVersion()
    throws RemoteException;

  public abstract int getVifVgaMaximum()
    throws RemoteException;

  public abstract boolean getWatchDogMode()
    throws RemoteException;

  public abstract int getWbBlueGain()
    throws RemoteException;

  public abstract int getWbBlueOffset()
    throws RemoteException;

  public abstract int getWbGreenGain()
    throws RemoteException;

  public abstract int getWbGreenOffset()
    throws RemoteException;

  public abstract int getWbRedGain()
    throws RemoteException;

  public abstract int getWbRedOffset()
    throws RemoteException;

  public abstract boolean restoreToDefault()
    throws RemoteException;

  public abstract boolean set3DSelfAdaptiveLevel(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcBlueGain(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcBlueOffset(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcGreenGain(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcGreenOffset(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcIndex(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcPhase(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcRedGain(int paramInt)
    throws RemoteException;

  public abstract boolean setAdcRedOffset(int paramInt)
    throws RemoteException;

  public abstract boolean setAefc43(int paramInt)
    throws RemoteException;

  public abstract boolean setAefc44(int paramInt)
    throws RemoteException;

  public abstract boolean setAefc66Bit76(int paramInt)
    throws RemoteException;

  public abstract boolean setAefc6EBit3210(int paramInt)
    throws RemoteException;

  public abstract boolean setAefc6EBit7654(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcA0(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcA1(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcCB(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcCfBit2Atv(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcCfBit2Av(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcD4(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcD5Bit2(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcD7HighBound(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcD7LowBound(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcD8Bit3210(int paramInt)
    throws RemoteException;

  public abstract boolean setAefcD9Bit0(int paramInt)
    throws RemoteException;

  public abstract boolean setAudioDspVersion(int paramInt)
    throws RemoteException;

  public abstract boolean setAudioHiDevMode(int paramInt)
    throws RemoteException;

  public abstract boolean setAudioNrThreshold(int paramInt)
    throws RemoteException;

  public abstract boolean setAudioPrescale(int paramInt)
    throws RemoteException;

  public abstract boolean setAudioSifThreshold(int paramInt)
    throws RemoteException;

  public abstract boolean setChinaDescramblerBox(int paramInt)
    throws RemoteException;

  public abstract boolean setCurveType(int paramInt)
    throws RemoteException;

  public abstract boolean setDelayReduce(int paramInt)
    throws RemoteException;

  public abstract boolean setDtvAvAbnormalDelay(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setEnvironment(String paramString1, String paramString2)
    throws RemoteException;

  public abstract boolean setFactoryPreSetFeature(int paramInt)
    throws RemoteException;

  public abstract boolean setGainDistributionThreshold(int paramInt)
    throws RemoteException;

  public abstract boolean setLvdsEnable(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setLvdsModulation(int paramInt)
    throws RemoteException;

  public abstract boolean setLvdsPercentage(int paramInt)
    throws RemoteException;

  public abstract boolean setMiuEnable(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setMiuModulation(int paramInt)
    throws RemoteException;

  public abstract boolean setMiuPercentage(int paramInt)
    throws RemoteException;

  public abstract boolean setOsdV0Nonlinear(int paramInt)
    throws RemoteException;

  public abstract boolean setOsdV100Nonlinear(int paramInt)
    throws RemoteException;

  public abstract boolean setOsdV25Nonlinear(int paramInt)
    throws RemoteException;

  public abstract boolean setOsdV50Nonlinear(int paramInt)
    throws RemoteException;

  public abstract boolean setOsdV75Nonlinear(int paramInt)
    throws RemoteException;

  public abstract boolean setOverScanHPosition(int paramInt)
    throws RemoteException;

  public abstract boolean setOverScanHSize(int paramInt)
    throws RemoteException;

  public abstract boolean setOverScanSourceType(int paramInt)
    throws RemoteException;

  public abstract boolean setOverScanVPosition(int paramInt)
    throws RemoteException;

  public abstract boolean setOverScanVSize(int paramInt)
    throws RemoteException;

  public abstract boolean setPanelSwing(int paramInt)
    throws RemoteException;

  public abstract boolean setPeqFoCoarse(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean setPeqFoFine(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean setPeqGain(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean setPeqQ(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean setPowerOnMode(int paramInt)
    throws RemoteException;

  public abstract boolean setTestPattern(int paramInt)
    throws RemoteException;

  public abstract boolean setUartOnOff(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setVdDspVersion(int paramInt)
    throws RemoteException;

  public abstract boolean setVifAgcRef(int paramInt)
    throws RemoteException;

  public abstract boolean setVifAsiaSignalOption(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setVifClampGainOvNegative(int paramInt)
    throws RemoteException;

  public abstract boolean setVifCrKi(int paramInt)
    throws RemoteException;

  public abstract boolean setVifCrKp(int paramInt)
    throws RemoteException;

  public abstract boolean setVifCrKpKiAdjust(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setVifCrThreshold(int paramInt)
    throws RemoteException;

  public abstract boolean setVifOverModulation(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setVifTop(int paramInt)
    throws RemoteException;

  public abstract boolean setVifVersion(int paramInt)
    throws RemoteException;

  public abstract boolean setVifVgaMaximum(int paramInt)
    throws RemoteException;

  public abstract boolean setWatchDogMode(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setWbBlueGain(int paramInt)
    throws RemoteException;

  public abstract boolean setWbBlueOffset(int paramInt)
    throws RemoteException;

  public abstract boolean setWbGreenGain(int paramInt)
    throws RemoteException;

  public abstract boolean setWbGreenOffset(int paramInt)
    throws RemoteException;

  public abstract boolean setWbRedGain(int paramInt)
    throws RemoteException;

  public abstract boolean setWbRedOffset(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvFactory
  {
    public static ITvFactory asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvFactory
 * JD-Core Version:    0.6.2
 */