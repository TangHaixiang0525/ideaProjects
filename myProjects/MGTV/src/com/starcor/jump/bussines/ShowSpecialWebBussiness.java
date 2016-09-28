package com.starcor.jump.bussines;

import com.starcor.common.IntentDataManager;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.hunan.InitApiActivity;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.jump.bussines.data.CommonData;
import com.starcor.utils.Logger;

public class ShowSpecialWebBussiness extends CommonBussines
{
  protected void commonStart()
  {
    String str = this._data.manager.getStringValue("subjectUrl");
    MetadataInfo localMetadataInfo = new MetadataInfo();
    localMetadataInfo.url = str;
    localMetadataInfo.action_type = "web";
    putExtra("MetaDataInfo", localMetadataInfo);
    if ((!this._data.isFromWeiXin) && (!this._data.isFromWeb));
    for (int i = 1; (AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO) && (i != 0) && (!this._data.manager.getBooleanValue("isFromInit", false)); i = 0)
    {
      putExtra("is_need_api", false);
      Logger.i("ShowSpecialWebBussiness !isShowed, go to InitApiActivity!");
      setClassForActivity(InitApiActivity.class);
      return;
    }
    setClassForActivity(ActivityAdapter.getInstance().getWebActivity());
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.ShowSpecialWebBussiness
 * JD-Core Version:    0.6.2
 */