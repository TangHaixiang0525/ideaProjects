package com.mstar.android.tvapi.common.vo;

public class ScreenPixelInfo
{
  public boolean bShowRepWin;
  public EnumPixelRGBStage enStage;
  public int tmpStage;
  public short u16BCbMax;
  public short u16BCbMin;
  public short u16GYMax;
  public short u16GYMin;
  public short u16RCrMax;
  public short u16RCrMin;
  public short u16RepWinColor;
  public short u16ReportPixelInfo_Length;
  public short u16XEnd;
  public short u16XStart;
  public short u16YEnd;
  public short u16YStart;
  public long u32BCbSum;
  public long u32GYSum;
  public long u32RCrSum;
  public int u32ReportPixelInfo_Version;

  public static enum EnumPixelRGBStage
  {
    public static EnumPixelRGBStage valueOf(int paramInt)
    {
      throw new RuntimeException("stub");
    }

    public int getValue()
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.vo.ScreenPixelInfo
 * JD-Core Version:    0.6.2
 */