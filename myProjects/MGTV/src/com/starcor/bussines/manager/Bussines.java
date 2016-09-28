package com.starcor.bussines.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.starcor.common.IntentDataManager;
import com.starcor.utils.Logger;
import java.io.Serializable;

public abstract class Bussines
{
  protected static final int EXCUTE_TYPE_ACVITIVTY = 1;
  protected static final int EXCUTE_TYPE_RECEVIER = 3;
  protected static final int EXCUTE_TYPE_SERVICE = 2;
  protected static final String TAG = Bussines.class.getSimpleName();
  protected BussinesData _data;
  private int _excuteType;
  private Class<?> _startClass;
  private boolean isFinished = false;
  private Intent mExcuteIntent;

  private void adaptData(BussinesData paramBussinesData)
  {
    if (compareDataObject(this._data, paramBussinesData))
      return;
    paramBussinesData.cloneData(this._data);
    this._data = paramBussinesData;
  }

  private boolean compareDataObject(BussinesData paramBussinesData1, BussinesData paramBussinesData2)
  {
    return paramBussinesData1.getClass().getName().equals(paramBussinesData2.getClass().getName());
  }

  private void startComp()
  {
    Context localContext = this._data.getContext();
    while (true)
    {
      try
      {
        int i = this._excuteType;
        switch (i)
        {
        default:
          Logger.i(TAG, "start comp success");
          return;
        case 1:
          localContext.startActivity(this.mExcuteIntent);
          continue;
        case 2:
        case 3:
        }
      }
      catch (Exception localException)
      {
        Logger.i(TAG, "start comp error");
        Logger.e(TAG, "error is: " + localException.getMessage());
        localException.printStackTrace();
        return;
      }
      localContext.startService(this.mExcuteIntent);
      continue;
      localContext.sendBroadcast(this.mExcuteIntent);
    }
  }

  private void startComponentForAction()
  {
    startComp();
  }

  private void startComponentForClass()
  {
    this.mExcuteIntent.setClass(this._data.getContext(), this._startClass);
    startComp();
  }

  protected void addFlags(int paramInt)
  {
    this.mExcuteIntent.addFlags(paramInt);
  }

  public void bind(BussinesData paramBussinesData)
  {
    paramBussinesData.start();
    if (this.mExcuteIntent == null)
    {
      this.mExcuteIntent = new Intent();
      if (paramBussinesData.getOriginalData() != null)
        this.mExcuteIntent.putExtras(paramBussinesData.getOriginalData());
    }
    paramBussinesData.changeBussines = null;
    paramBussinesData.needChange = false;
    this._data = paramBussinesData;
  }

  protected void changeBussines(Bussines paramBussines)
  {
    if (paramBussines == null)
      return;
    this._data.changeBussines = paramBussines;
    this._data.needChange = true;
    finish();
  }

  protected boolean changeBussines(Bussines paramBussines, BussinesData paramBussinesData)
  {
    if ((paramBussines == null) || (paramBussinesData == null))
      return false;
    adaptData(paramBussinesData);
    changeBussines(paramBussines);
    return true;
  }

  protected abstract void commonStart();

  protected void dumpIntentData()
  {
    IntentDataManager.dumpIntentData(this.mExcuteIntent);
  }

  public BussinesData excute()
  {
    if (this.isFinished)
      return this._data;
    if (this._startClass != null)
      startComponentForClass();
    while (true)
    {
      dumpIntentData();
      return this._data;
      startComponentForAction();
    }
  }

  protected void finish()
  {
    this.isFinished = true;
  }

  protected boolean innerCommonStart()
  {
    return true;
  }

  protected void putExtra(String paramString, byte paramByte)
  {
    this.mExcuteIntent.putExtra(paramString, paramByte);
  }

  protected void putExtra(String paramString, char paramChar)
  {
    this.mExcuteIntent.putExtra(paramString, paramChar);
  }

  protected void putExtra(String paramString, double paramDouble)
  {
    this.mExcuteIntent.putExtra(paramString, paramDouble);
  }

  protected void putExtra(String paramString, float paramFloat)
  {
    this.mExcuteIntent.putExtra(paramString, paramFloat);
  }

  protected void putExtra(String paramString, int paramInt)
  {
    this.mExcuteIntent.putExtra(paramString, paramInt);
  }

  protected void putExtra(String paramString, long paramLong)
  {
    this.mExcuteIntent.putExtra(paramString, paramLong);
  }

  protected void putExtra(String paramString, Parcelable paramParcelable)
  {
    this.mExcuteIntent.putExtra(paramString, paramParcelable);
  }

  protected void putExtra(String paramString, Serializable paramSerializable)
  {
    this.mExcuteIntent.putExtra(paramString, paramSerializable);
  }

  protected void putExtra(String paramString, CharSequence paramCharSequence)
  {
    this.mExcuteIntent.putExtra(paramString, paramCharSequence);
  }

  protected void putExtra(String paramString1, String paramString2)
  {
    this.mExcuteIntent.putExtra(paramString1, paramString2);
  }

  protected void putExtra(String paramString, short paramShort)
  {
    this.mExcuteIntent.putExtra(paramString, paramShort);
  }

  protected void putExtra(String paramString, boolean paramBoolean)
  {
    this.mExcuteIntent.putExtra(paramString, paramBoolean);
  }

  protected void putExtra(String paramString, Parcelable[] paramArrayOfParcelable)
  {
    this.mExcuteIntent.putExtra(paramString, paramArrayOfParcelable);
  }

  protected void reStartProcess()
  {
    start();
    excute();
  }

  protected void setAction(String paramString)
  {
    this.mExcuteIntent.setAction(paramString);
  }

  protected void setClassForActivity(Class<?> paramClass)
  {
    this._startClass = paramClass;
    this._excuteType = 1;
  }

  protected void setClassForRecevier(Class<?> paramClass)
  {
    this._startClass = paramClass;
    this._excuteType = 3;
  }

  protected void setClassForService(Class<?> paramClass)
  {
    this._startClass = paramClass;
    this._excuteType = 2;
  }

  protected void setData(BussinesData paramBussinesData)
  {
    adaptData(paramBussinesData);
    paramBussinesData.start();
  }

  protected void setExcuteType(int paramInt)
  {
    this._excuteType = paramInt;
  }

  BussinesData start()
  {
    if (!innerCommonStart())
      return this._data;
    commonStart();
    switch (this._data.getInitForType())
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return this._data;
      startFromReciever();
      continue;
      startFromActivity();
    }
  }

  protected abstract void startFromActivity();

  protected abstract void startFromReciever();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.bussines.manager.Bussines
 * JD-Core Version:    0.6.2
 */