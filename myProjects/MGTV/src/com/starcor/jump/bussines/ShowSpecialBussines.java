package com.starcor.jump.bussines;

import com.starcor.common.IntentDataManager;
import com.starcor.config.AppFuncCfg;
import com.starcor.hunan.InitApiActivity;
import com.starcor.hunan.SpecialActivityV2;
import com.starcor.jump.bussines.data.CommonData;
import com.starcor.utils.Logger;

public class ShowSpecialBussines extends CommonBussines
{
  private static final String TAG = ShowSpecialBussines.class.getSimpleName();

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.show_special_top");
    putExtra("subjectId", this._data.manager.getStringValue("subjectId"));
    putExtra("subjectName", this._data.manager.getStringValue("subjectName"));
    putExtra("subjectBgImg", this._data.manager.getStringValue("subjectBgImg"));
    if ((!this._data.isFromWeiXin) && (!this._data.isFromWeb));
    for (int i = 1; (AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO) && (i != 0) && (!this._data.manager.getBooleanValue("isFromInit", false)); i = 0)
    {
      Logger.i("ShowSpecialBussines !isShowed, go to InitApiActivity!");
      setClassForActivity(InitApiActivity.class);
      return;
    }
    setClassForActivity(SpecialActivityV2.class);
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.ShowSpecialBussines
 * JD-Core Version:    0.6.2
 */