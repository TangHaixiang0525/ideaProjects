package com.starcor.bussines.manager;

import com.starcor.common.IntentDataManager;
import com.starcor.utils.Logger;

public class BussinesManager
{
  public static final int INIT_FROM_ACTIVITY = 2;
  public static final int INIT_FROM_RECEIVER = 1;
  public static final int INIT_FROM_SERVICE = 3;
  protected static final String TAG = BussinesManager.class.getSimpleName();
  private Bussines _targetBussines;

  private BussinesData innerExcute(Bussines paramBussines)
  {
    if (paramBussines == null)
      return null;
    return paramBussines.excute();
  }

  private BussinesData innerStart(Bussines paramBussines, BussinesData paramBussinesData)
  {
    Logger.d(TAG, "start");
    this._targetBussines = paramBussines;
    this._targetBussines.bind(paramBussinesData);
    printInnerData(paramBussinesData);
    return this._targetBussines.start();
  }

  private void printInnerData(BussinesData paramBussinesData)
  {
    switch (paramBussinesData.getInitForType())
    {
    default:
    case 2:
    case 3:
    case 1:
    }
    while (true)
    {
      Logger.i(TAG, "intent is:" + IntentDataManager.buildIntentExtraToString(paramBussinesData.getIntent()));
      return;
      Logger.i(TAG, String.format("init type name is: %s", new Object[] { "INIT_FROM_ACTIVITY" }));
      continue;
      Logger.i(TAG, String.format("init type name is: %s", new Object[] { "INIT_FROM_SERVICE" }));
      continue;
      Logger.i(TAG, String.format("init type name is: %s", new Object[] { "INIT_FROM_RECEIVER" }));
    }
  }

  private BussinesData reStart(BussinesData paramBussinesData)
  {
    Logger.d(TAG, "reStart");
    Bussines localBussines = paramBussinesData.changeBussines;
    start(localBussines, paramBussinesData);
    return innerExcute(localBussines);
  }

  public BussinesData excute()
  {
    for (BussinesData localBussinesData = innerExcute(this._targetBussines); ; localBussinesData = reStart(localBussinesData))
      if ((localBussinesData.changeBussines == null) || (!localBussinesData.needChange))
        return localBussinesData;
  }

  public BussinesData start(Bussines paramBussines, BussinesData paramBussinesData)
  {
    return innerStart(paramBussines, paramBussinesData);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.bussines.manager.BussinesManager
 * JD-Core Version:    0.6.2
 */