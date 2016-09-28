package com.starcor.core.domain;

import java.util.ArrayList;
import java.util.List;

public class SpecialPlayerData
{
  public static final int DISPLAY_UI_IMAGE = 4097;
  public static final int DISPLAY_UI_TEXT = 4096;
  public List<SpecialCategoryContent> info = new ArrayList();
  public boolean isDisplayNum = false;
  public String specialCategoryId = "";
  public String specialId = "";
  public String specialName = "";
  public UiStyle uiStyle = UiStyle.DISPLAY_UI_TEXT;

  public static enum UiStyle
  {
    static
    {
      DISPLAY_UI_IMAGE = new UiStyle("DISPLAY_UI_IMAGE", 1);
      UiStyle[] arrayOfUiStyle = new UiStyle[2];
      arrayOfUiStyle[0] = DISPLAY_UI_TEXT;
      arrayOfUiStyle[1] = DISPLAY_UI_IMAGE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SpecialPlayerData
 * JD-Core Version:    0.6.2
 */