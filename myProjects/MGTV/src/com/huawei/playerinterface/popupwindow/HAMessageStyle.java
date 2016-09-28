package com.huawei.playerinterface.popupwindow;

public class HAMessageStyle
{
  public String STYLE_BKCOLOR = "#000000";
  public boolean STYLE_FOCUSABLE = false;
  public String STYLE_FONTCOLOR = "#FFFFFF";
  public float STYLE_FONTSIZE = 18.0F;
  public int STYLE_HEIGHT = 0;
  public int STYLE_OPACITY = 100;
  public boolean STYLE_TOUCHABLE = false;
  public int STYLE_WIDTH = 0;
  public int STYLE_X = 0;
  public int STYLE_Y = 0;

  public boolean equals(HAMessageStyle paramHAMessageStyle)
  {
    return (paramHAMessageStyle.STYLE_OPACITY == this.STYLE_OPACITY) && (paramHAMessageStyle.STYLE_FONTSIZE == this.STYLE_FONTSIZE) && (paramHAMessageStyle.STYLE_X == this.STYLE_X) && (paramHAMessageStyle.STYLE_Y == this.STYLE_Y) && (paramHAMessageStyle.STYLE_BKCOLOR == this.STYLE_BKCOLOR) && (paramHAMessageStyle.STYLE_FONTCOLOR == this.STYLE_FONTCOLOR);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.popupwindow.HAMessageStyle
 * JD-Core Version:    0.6.2
 */