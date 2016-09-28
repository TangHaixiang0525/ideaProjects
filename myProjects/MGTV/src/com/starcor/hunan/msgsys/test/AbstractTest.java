package com.starcor.hunan.msgsys.test;

import android.content.Context;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.interfaces.ITest;

public abstract class AbstractTest
  implements ITest
{
  private static final String TAG = AbstractTest.class.getSimpleName();
  protected String SUB_TAG = null;
  private Context mContext = null;

  public AbstractTest(Context paramContext, String paramString)
  {
    this.mContext = paramContext;
    if (paramString == null);
    for (this.SUB_TAG = TAG; ; this.SUB_TAG = paramString)
    {
      init();
      return;
    }
  }

  protected Context getContext()
  {
    return this.mContext;
  }

  protected abstract void init();

  protected abstract void runSpecificTest();

  public void runTest()
  {
    Logger.i(TAG, "开始执行具体的测试！");
    runSpecificTest();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.test.AbstractTest
 * JD-Core Version:    0.6.2
 */