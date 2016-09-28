package com.starcor.settings;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo
{
  boolean filtered;
  public int flags;
  public Drawable icon;
  public Intent intent;
  public CharSequence packageName;
  public CharSequence title;

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    AppInfo localAppInfo;
    do
    {
      return true;
      if (!(paramObject instanceof AppInfo))
        return false;
      localAppInfo = (AppInfo)paramObject;
    }
    while ((this.title.equals(localAppInfo.title)) && (this.intent.getComponent().getClassName().equals(localAppInfo.intent.getComponent().getClassName())));
    return false;
  }

  public int hashCode()
  {
    if (this.title != null);
    for (int i = this.title.hashCode(); ; i = 0)
    {
      String str = this.intent.getComponent().getClassName();
      int j = i * 31;
      int k = 0;
      if (str != null)
        k = str.hashCode();
      return j + k;
    }
  }

  public final void setActivity(ComponentName paramComponentName, int paramInt)
  {
    this.intent = new Intent("android.intent.action.MAIN");
    this.intent.addCategory("android.intent.category.LAUNCHER");
    this.intent.setComponent(paramComponentName);
    this.intent.setFlags(paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.AppInfo
 * JD-Core Version:    0.6.2
 */