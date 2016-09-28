package com.starcor.jump.bussines.simple;

import com.starcor.common.IntentDataManager;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.hunan.InitApiActivity;
import com.starcor.hunan.StarDetailedActivity;
import com.starcor.jump.bussines.CommonBussines;
import com.starcor.jump.bussines.data.CommonData;

public class ShowStarBussines extends CommonBussines
{
  private static final String TAG = ShowStarBussines.class.getSimpleName();

  protected void commonStart()
  {
    if (!GlobalLogic.getInstance().isAppInterfaceReady())
    {
      setClassForActivity(InitApiActivity.class);
      return;
    }
    putExtra("labelID_V2", this._data.manager.getStringValue("labelID"));
    setClassForActivity(StarDetailedActivity.class);
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.ShowStarBussines
 * JD-Core Version:    0.6.2
 */