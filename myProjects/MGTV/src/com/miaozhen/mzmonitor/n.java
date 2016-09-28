package com.miaozhen.mzmonitor;

import android.content.Context;

class n
  implements Runnable
{
  n(Context paramContext, a parama)
  {
  }

  public void run()
  {
    if (d.a(this.a).a() == null)
      d.a(this.a).b();
    if (MZSdkProfile.isProfileInvalid(this.a))
    {
      long l = MZUtility.currentUnixTimestamp();
      if ((MZSdkProfile.configFile_nextUpdateTime == 0L) || (l > MZSdkProfile.configFile_nextUpdateTime))
      {
        MZSdkProfile.configFile_nextUpdateTime = l + 900L * ()Math.pow(2.0D, MZSdkProfile.configFile_update_retryTimes);
        if (MZSdkProfile.configFile_update_retryTimes < 2)
          MZSdkProfile.configFile_update_retryTimes = 1 + MZSdkProfile.configFile_update_retryTimes;
        MZSdkProfile.loadConfigFromNet(this.a);
      }
    }
    new q(this.a, this.b).b();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.n
 * JD-Core Version:    0.6.2
 */