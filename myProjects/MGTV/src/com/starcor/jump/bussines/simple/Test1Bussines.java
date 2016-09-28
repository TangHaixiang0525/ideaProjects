package com.starcor.jump.bussines.simple;

import android.util.Log;
import com.starcor.jump.bussines.CommonBussines;
import com.starcor.jump.bussines.data.CommonData;

public class Test1Bussines extends CommonBussines
{
  private static final String TAG = Test1Bussines.class.getSimpleName();

  protected void commonStart()
  {
    setData(new CommonData());
  }

  protected void startFromActivity()
  {
    Log.i(TAG, "startFromActivity");
  }

  protected void startFromReciever()
  {
    Log.i(TAG, "startFromReciever");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.Test1Bussines
 * JD-Core Version:    0.6.2
 */