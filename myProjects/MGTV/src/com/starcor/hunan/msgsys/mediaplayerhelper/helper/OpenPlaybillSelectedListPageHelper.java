package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.config.AppFuncCfg;
import com.starcor.hunan.XULActivity;
import org.json.JSONObject;

public class OpenPlaybillSelectedListPageHelper extends MediaAssetHelperBase
{
  private static final String KEY_PACKET_ID = "packetId";
  private static final String KEY_SHOW_ICON = "showIcon";

  public OpenPlaybillSelectedListPageHelper()
  {
    super(OpenPlaybillSelectedListPageHelper.class.getSimpleName());
  }

  public OpenPlaybillSelectedListPageHelper(Context paramContext)
  {
    super(paramContext, OpenPlaybillSelectedListPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("media_asset_id");
      String str2 = this.mParamsBundle.getString("name");
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("packetId", str1);
      localJSONObject.put("name", str2);
      localJSONObject.put("showIcon", AppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON);
      Intent localIntent = new Intent(this.mContext, XULActivity.class);
      localIntent.putExtra("xulPageId", "GetPlaybillSelected");
      localIntent.putExtra("xulData", localJSONObject.toString());
      localIntent.putExtra("packetId", str1);
      localIntent.addFlags(8388608);
      this.mContext.startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPlaybillSelectedListPageHelper
 * JD-Core Version:    0.6.2
 */