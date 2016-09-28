package com.starcor.player;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class SpecialPlayerParamsService
{
  private static SpecialPlayerParamsService mService = null;
  Map<String, SpecialContinuousPlayingLogic> logicMap = new HashMap();

  public static SpecialPlayerParamsService getInstance()
  {
    if (mService == null)
      mService = new SpecialPlayerParamsService();
    return mService;
  }

  public void addSpecialContinuousPlayingLogic(String paramString, SpecialContinuousPlayingLogic paramSpecialContinuousPlayingLogic)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramSpecialContinuousPlayingLogic == null))
      return;
    this.logicMap.put(paramString, paramSpecialContinuousPlayingLogic);
  }

  public SpecialContinuousPlayingLogic getSpecialContinuousPlayingLogic(String paramString)
  {
    return (SpecialContinuousPlayingLogic)this.logicMap.remove(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.SpecialPlayerParamsService
 * JD-Core Version:    0.6.2
 */