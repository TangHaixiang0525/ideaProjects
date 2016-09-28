package com.starcor.jump.bussines.behavior;

import android.text.TextUtils;
import com.starcor.jump.bussines.data.CommonData;

public abstract class BaseSelector
{
  private static final String TAG = BaseSelector.class.getSimpleName();
  protected BehaviorData extraData;
  protected boolean isFromWeb;
  protected CommonData mBussinesData;
  protected int videoType;

  public BaseSelector()
  {
    if (this.extraData == null)
      this.extraData = new BehaviorData();
  }

  public void bindData(CommonData paramCommonData)
  {
    this.mBussinesData = paramCommonData;
  }

  protected boolean compareKey(String paramString1, String paramString2)
  {
    String str1 = wrapKey(paramString1);
    String str2 = wrapKey(paramString2);
    boolean bool1 = TextUtils.isEmpty(str1);
    boolean bool2 = false;
    if (!bool1)
    {
      boolean bool3 = TextUtils.isEmpty(str2);
      bool2 = false;
      if (!bool3)
      {
        int i = str1.compareTo(str2);
        bool2 = false;
        if (i == 0)
          bool2 = true;
      }
    }
    return bool2;
  }

  public BehaviorData getExtraData()
  {
    return this.extraData;
  }

  public abstract void selector(String paramString);

  public void setExtraData(BehaviorData paramBehaviorData)
  {
    this.extraData = paramBehaviorData;
  }

  public String wrapKey(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString))
      return paramString;
    if (paramString.contains("_"))
      str = paramString.replace("_", "");
    return str.toLowerCase();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.behavior.BaseSelector
 * JD-Core Version:    0.6.2
 */