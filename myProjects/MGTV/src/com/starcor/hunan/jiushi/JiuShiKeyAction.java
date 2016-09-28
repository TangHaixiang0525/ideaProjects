package com.starcor.hunan.jiushi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.starcor.core.key.GlobalKeyReceiver.GlobalKeyInfo;
import com.starcor.core.key.GlobalKeyReceiver.GlobalKeyInfo.ActionType;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.uilogic.CommonMethod;

public class JiuShiKeyAction
{
  private static final String DIANSHIJU = "电视剧";
  private static final String DIANYING = "电影";
  private static final String HUIKAN = "回看";
  private static final String PACKAGE_KLOK = "com.lutongnet.kalaok2";
  private static final String PACKAGE_YXLY = "app.android.applicationxc";
  private static final String TAG = "JiuShiKeyAction";
  private static final String TZLC_TZLC = "com.android.dazhihui";
  private static final String ZONGYI = "综艺";

  public static void processKeyAction(GlobalKeyReceiver.GlobalKeyInfo paramGlobalKeyInfo)
  {
    Logger.i("JiuShiKeyAction", "processKeyAction keyCode:" + paramGlobalKeyInfo.keyCode);
    if ("down".equals(paramGlobalKeyInfo.keyAction.value()));
    switch (paramGlobalKeyInfo.keyCode)
    {
    case 708:
    case 710:
    case 711:
    default:
      return;
    case 701:
      CommonMethod.startCategoryActivityByName("综艺");
      return;
    case 702:
      CommonMethod.startCategoryActivityByName("回看");
      return;
    case 703:
      CommonMethod.startCategoryActivityByName("电影");
      return;
    case 704:
      CommonMethod.startCategoryActivityByName("电视剧");
      return;
    case 705:
      toJiuShiApp("app.android.applicationxc");
      return;
    case 706:
      toJiuShiApp("com.lutongnet.kalaok2");
      return;
    case 707:
      toJiuShiApp("com.android.dazhihui");
      return;
    case 709:
      CommonMethod.startMainActivity();
      return;
    case 712:
    }
    startManGoApp();
  }

  public static void startManGoApp()
  {
    Logger.i("JiuShiKeyAction", "startManGoApp");
    Intent localIntent = new Intent();
    localIntent.addFlags(268435456);
    localIntent.setAction("com.starcor.hunan.logic_event.ctrl_in_current");
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.ctrl_in_current");
    localIntent.addFlags(8388608);
    App.getInstance().getApplicationContext().startActivity(localIntent);
  }

  private static void toJiuShiApp(String paramString)
  {
    Logger.i("JiuShiKeyAction", "toJiuShiApp packageName:" + paramString);
    Intent localIntent = App.getInstance().getApplicationContext().getPackageManager().getLaunchIntentForPackage(paramString);
    if (localIntent != null)
    {
      localIntent.addFlags(8388608);
      App.getInstance().getApplicationContext().startActivity(localIntent);
      return;
    }
    Toast.makeText(App.getInstance().getApplicationContext(), "应用暂未集成,敬请期待!", 0).show();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.jiushi.JiuShiKeyAction
 * JD-Core Version:    0.6.2
 */