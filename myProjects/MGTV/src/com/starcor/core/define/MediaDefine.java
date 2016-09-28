package com.starcor.core.define;

public class MediaDefine
{
  public static final int DIMENSIONS_2D = 0;
  public static final int DIMENSIONS_3D = 100000;
  public static final int DIMENSIONS_3D_LR = 100001;
  public static final int DIMENSIONS_3D_UD = 100002;
  public static final String QUALITY_4K = "4K";
  public static final String QUALITY_HD = "HD";
  public static final String QUALITY_LOW = "LOW";
  public static final String QUALITY_SD = "SD";
  public static final String QUALITY_STD = "STD";

  public static boolean isDemension3D(int paramInt)
  {
    return (paramInt == 100000) || (paramInt == 100001) || (paramInt == 100002);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.define.MediaDefine
 * JD-Core Version:    0.6.2
 */