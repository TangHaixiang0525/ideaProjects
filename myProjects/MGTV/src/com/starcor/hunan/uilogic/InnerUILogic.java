package com.starcor.hunan.uilogic;

import android.content.Context;
import android.content.Intent;

public class InnerUILogic
{
  private static final String TAG = "InnerUILogic";

  public static Intent createCollectIntent(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, ActivityAdapterV4.getInstance().getMyMediaActivity());
    localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_collect_list");
    localIntent.addFlags(8388608);
    return localIntent;
  }

  public static Intent createPlayIntent(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, ActivityAdapterV4.getInstance().getMyMediaActivity());
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_play_list");
    localIntent.addFlags(8388608);
    return localIntent;
  }

  public static boolean showCollectList(Context paramContext)
  {
    paramContext.startActivity(createCollectIntent(paramContext));
    return true;
  }

  public static boolean showMyOrder(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, ActivityAdapterV4.getInstance().getMyMediaActivity());
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_my_order");
    localIntent.addFlags(8388608);
    paramContext.startActivity(localIntent);
    return true;
  }

  public static boolean showPlayList(Context paramContext)
  {
    paramContext.startActivity(createPlayIntent(paramContext));
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.InnerUILogic
 * JD-Core Version:    0.6.2
 */