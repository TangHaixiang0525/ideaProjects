package com.starcor.jump.bussines.simple;

import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.ActivityAdapterV4;
import com.starcor.jump.bussines.CommonBussines;

public class ShowFavoriteBussines extends CommonBussines
{
  private static final String TAG = ShowFavoriteBussines.class.getSimpleName();

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.show_collect_list");
    setClassForActivity(ActivityAdapterV4.getInstance().getMyMediaActivity());
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.ShowFavoriteBussines
 * JD-Core Version:    0.6.2
 */