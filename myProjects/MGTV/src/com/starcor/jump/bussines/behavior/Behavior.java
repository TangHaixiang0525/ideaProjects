package com.starcor.jump.bussines.behavior;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.bussines.manager.Bussines;
import com.starcor.jump.bussines.CommonBussines;
import com.starcor.jump.bussines.data.CommonData;

public class Behavior
{
  private static final String TAG = Behavior.class.getSimpleName();
  private CommonData mBussinesData;

  public Behavior(CommonData paramCommonData)
  {
    this.mBussinesData = paramCommonData;
  }

  private BehaviorData generateBehaveData(String paramString, BaseSelector paramBaseSelector)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      paramBaseSelector.bindData(this.mBussinesData);
      paramBaseSelector.selector(paramString);
    }
    return paramBaseSelector.extraData;
  }

  private BehaviorData getBehaveData()
  {
    BehaviorData localBehaviorData1 = generateBehaveData(this.mBussinesData.action, new ActionSelector());
    if (!TextUtils.isEmpty(localBehaviorData1.cmd))
      return localBehaviorData1;
    BehaviorData localBehaviorData2 = generateBehaveData(this.mBussinesData.jsonAction, new jsonActionSelector());
    if (!TextUtils.isEmpty(localBehaviorData2.cmd))
      return localBehaviorData2;
    BehaviorData localBehaviorData3 = generateBehaveData(this.mBussinesData.commonCmd, new CommonCmdSelector());
    if (!TextUtils.isEmpty(localBehaviorData3.cmd))
      return localBehaviorData3;
    BehaviorData localBehaviorData4 = generateBehaveData(this.mBussinesData.tclCmd, new TCLCmdSelector());
    if (!TextUtils.isEmpty(localBehaviorData4.cmd))
      return localBehaviorData4;
    localBehaviorData4.cmd = BehaviorCmd.APP_ENTER_BUSSINES_ERROR_SHOW;
    return localBehaviorData4;
  }

  private CommonBussines getBussines(BehaviorData paramBehaviorData)
  {
    ClassLoader localClassLoader = CommonBussines.class.getClassLoader();
    String str = refactorBussinesName(paramBehaviorData.cmd);
    try
    {
      CommonBussines localCommonBussines = (CommonBussines)localClassLoader.loadClass(str).newInstance();
      this.mBussinesData.setBehaviorData(paramBehaviorData);
      printBehaviorData(paramBehaviorData);
      return localCommonBussines;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Log.e(TAG, "error is: " + localException.getMessage());
      Log.e(TAG, "class name is: " + str);
    }
    return null;
  }

  private void printBehaviorData(BehaviorData paramBehaviorData)
  {
    Log.d(TAG, "data cmd is: " + paramBehaviorData.cmd);
    Log.d(TAG, "data what is: " + paramBehaviorData.what);
    Log.d(TAG, "data object is: " + paramBehaviorData.extra);
  }

  private String refactorBussinesName(String paramString)
  {
    return paramString;
  }

  public Bussines getBussines()
  {
    return getBussines(getBehaveData());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.behavior.Behavior
 * JD-Core Version:    0.6.2
 */