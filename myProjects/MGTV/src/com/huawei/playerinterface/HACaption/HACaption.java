package com.huawei.playerinterface.HACaption;

import android.content.Context;
import android.view.SurfaceView;
import com.huawei.playerinterface.popupwindow.HAMessageStyle;
import com.huawei.playerinterface.popupwindow.HAShowOnPopupWindow;

public class HACaption extends HAShowOnPopupWindow
{
  private boolean isShow = false;
  private String popupMessage;

  public HACaption(Context paramContext, SurfaceView paramSurfaceView, HAMessageStyle paramHAMessageStyle)
  {
    super(paramContext, paramSurfaceView, paramHAMessageStyle);
  }

  public boolean isShowEnable()
  {
    return this.isShow;
  }

  public void setMessageStyle(HAMessageStyle paramHAMessageStyle)
  {
    super.setMessageStyle(paramHAMessageStyle);
    this.popupMessage = "";
  }

  public void showCaptionMessage(String paramString)
  {
    if (!this.isShow);
    do
    {
      return;
      if (paramString == null)
      {
        super.dismissPopupWindow();
        return;
      }
    }
    while ((paramString.equals(this.popupMessage)) && (paramString.length() == 0));
    this.popupMessage = paramString;
    super.showPopupWindowMessage(paramString);
  }

  public void start()
  {
    this.isShow = true;
  }

  public void stop()
  {
    dismissPopupWindow();
    this.isShow = false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.HACaption.HACaption
 * JD-Core Version:    0.6.2
 */