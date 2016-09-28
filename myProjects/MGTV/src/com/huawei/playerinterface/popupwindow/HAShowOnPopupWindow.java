package com.huawei.playerinterface.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class HAShowOnPopupWindow extends PopupWindow
{
  private static final int POPUP_WINDOW_DISMISS = 1;
  private static final int POPUP_WINDOW_SHOW_MESSAGE = 3;
  private static final int POPUP_WINDOW_UPDATE_STYLE = 2;
  private static final String TAG = "HAPlayer_PopupWindow";
  private Context context;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((this == null) || (HAShowOnPopupWindow.this.context == null) || (HAShowOnPopupWindow.this.mSurfaceView == null));
      do
      {
        do
        {
          return;
          switch (paramAnonymousMessage.what)
          {
          default:
            return;
          case 1:
          case 2:
          case 3:
          }
        }
        while ((HAShowOnPopupWindow.this == null) || (HAShowOnPopupWindow.this.context == null));
        try
        {
          HAShowOnPopupWindow.this.dismiss();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
        HAShowOnPopupWindow.access$202(HAShowOnPopupWindow.this, (HAMessageStyle)paramAnonymousMessage.obj);
        HAShowOnPopupWindow.this.setPopupWindowFacade();
        return;
      }
      while ((this == null) || (HAShowOnPopupWindow.this.mSurfaceView == null));
      HAShowOnPopupWindow.this.showMessageView.setText((String)paramAnonymousMessage.obj);
      HAShowOnPopupWindow.this.showAtLocation(HAShowOnPopupWindow.this.mSurfaceView, 17, HAShowOnPopupWindow.this.messageStyle.STYLE_X, HAShowOnPopupWindow.this.messageStyle.STYLE_Y);
    }
  };
  private SurfaceView mSurfaceView;
  private HAMessageStyle messageStyle;
  private TextView showMessageView = null;

  public HAShowOnPopupWindow(Context paramContext, SurfaceView paramSurfaceView, HAMessageStyle paramHAMessageStyle)
  {
    super(new TextView(paramContext), paramHAMessageStyle.STYLE_WIDTH, paramHAMessageStyle.STYLE_HEIGHT);
    this.context = paramContext;
    this.mSurfaceView = paramSurfaceView;
    this.messageStyle = paramHAMessageStyle;
    setPopupWindowFacade();
  }

  public void dismissPopupWindow()
  {
    this.mHandler.sendEmptyMessage(1);
  }

  public HAMessageStyle getMessageStyle()
  {
    return this.messageStyle;
  }

  public void setMessageStyle(HAMessageStyle paramHAMessageStyle)
  {
    if (paramHAMessageStyle == null)
      return;
    Message localMessage = new Message();
    localMessage.what = 2;
    localMessage.obj = paramHAMessageStyle;
    this.mHandler.sendMessage(localMessage);
  }

  public void setPopupWindowFacade()
  {
    try
    {
      setBackgroundDrawable(new ColorDrawable(Color.parseColor(this.messageStyle.STYLE_BKCOLOR)));
      this.showMessageView.setTextSize(this.messageStyle.STYLE_FONTSIZE);
      this.showMessageView.setTextColor(Color.parseColor(this.messageStyle.STYLE_FONTCOLOR));
      getBackground().setAlpha(this.messageStyle.STYLE_OPACITY);
      this.showMessageView.setGravity(17);
      setOutsideTouchable(false);
      setAnimationStyle(16973826);
      update();
      setTouchable(this.messageStyle.STYLE_TOUCHABLE);
      setFocusable(this.messageStyle.STYLE_FOCUSABLE);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void showPopupWindowMessage(HAMessageStyle paramHAMessageStyle, String paramString)
  {
    setMessageStyle(paramHAMessageStyle);
    if (paramString != null)
    {
      Message localMessage = new Message();
      localMessage.what = 3;
      localMessage.obj = paramString;
      this.mHandler.sendMessage(localMessage);
      return;
    }
    dismissPopupWindow();
  }

  public void showPopupWindowMessage(String paramString)
  {
    if (paramString != null)
    {
      Message localMessage = new Message();
      localMessage.what = 3;
      localMessage.obj = paramString;
      this.mHandler.sendMessage(localMessage);
      return;
    }
    dismissPopupWindow();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.popupwindow.HAShowOnPopupWindow
 * JD-Core Version:    0.6.2
 */