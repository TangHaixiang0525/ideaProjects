package com.starcor.hunan.util;

import android.content.Context;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.ToastUtil;

public class LogSwitchSpecialKeyOperation extends SpecialKeyOperation
{
  private Context mContext;
  private SpecialKeyOperation.BaseMatchingListener mListener = new SpecialKeyOperation.BaseMatchingListener()
  {
    public void onKeySequenceMatchingSuccess()
    {
      LogSwitchSpecialKeyOperation.this.switchPrintLog();
    }
  };

  public LogSwitchSpecialKeyOperation(Context paramContext)
  {
    this.mContext = paramContext;
    addKeySequence(7);
    addKeySequence(12);
    addKeySequence(8);
    addKeySequence(13);
    setOnKeySequenceMatchingListener(this.mListener);
  }

  public void switchPrintLog()
  {
    GlobalEnv localGlobalEnv = GlobalEnv.getInstance();
    boolean bool;
    Context localContext;
    StringBuilder localStringBuilder;
    if (!AppFuncCfg.FUNCTION_OPEN_LOGGER)
    {
      bool = true;
      localGlobalEnv.setLogConfig(bool);
      localContext = this.mContext;
      localStringBuilder = new StringBuilder().append("日志输出开关已经");
      if (!AppFuncCfg.FUNCTION_OPEN_LOGGER)
        break label66;
    }
    label66: for (String str = "开启!"; ; str = "关闭!")
    {
      ToastUtil.showToast(localContext, str);
      return;
      bool = false;
      break;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.util.LogSwitchSpecialKeyOperation
 * JD-Core Version:    0.6.2
 */