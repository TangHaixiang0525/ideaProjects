package com.starcor.jump.bussines.simple;

import android.util.Log;
import com.starcor.jump.bussines.CommonBussines;

public class Test2Bussines extends CommonBussines
{
  private static final String TAG = Test2Bussines.class.getSimpleName();

  protected void commonStart()
  {
  }

  protected void startFromActivity()
  {
    Log.i(TAG, "startFromActivity");
  }

  protected void startFromReciever()
  {
    Log.i(TAG, "startFromReciever");
    changeBussines(new Test1Bussines(), this._data);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.Test2Bussines
 * JD-Core Version:    0.6.2
 */