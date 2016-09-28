package com.starcor.jump.bussines;

import android.content.Context;
import android.content.Intent;
import com.starcor.bussines.manager.Bussines;
import com.starcor.jump.bussines.data.CommonData;
import com.starcor.message.Message;

public class BussinesMessage extends Message
{
  Bussines mBussines;
  BussinesCallback mCallback;
  Context mContext;
  CommonData mData;
  Intent mIntent;
  Object mSrouceObj;

  public BussinesMessage(Object paramObject)
  {
    Class localClass = paramObject.getClass();
    setSourceClass(localClass);
    setSourceName(localClass.getSimpleName());
    this.mSrouceObj = paramObject;
    setExcuteMode(2);
    setFlags(2);
  }

  public BussinesMessage(Object paramObject, Context paramContext)
  {
    this(paramObject);
    this.mContext = paramContext;
  }

  public static void sendMessage(Context paramContext, Intent paramIntent)
  {
  }

  public Bussines getBussines()
  {
    return this.mBussines;
  }

  public CommonData getData()
  {
    return this.mData;
  }

  public Intent getIntent()
  {
    return this.mIntent;
  }

  public void setBussines(Bussines paramBussines)
  {
    this.mBussines = paramBussines;
  }

  public void setCallback(BussinesCallback paramBussinesCallback)
  {
    this.mCallback = paramBussinesCallback;
  }

  public void setData(CommonData paramCommonData)
  {
    this.mData = paramCommonData;
  }

  public void setIntent(Intent paramIntent)
  {
    this.mIntent = paramIntent;
  }

  public static abstract interface BussinesCallback
  {
    public abstract void onError(String paramString);

    public abstract void onSuccess();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.BussinesMessage
 * JD-Core Version:    0.6.2
 */