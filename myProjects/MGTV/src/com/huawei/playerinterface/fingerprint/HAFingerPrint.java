package com.huawei.playerinterface.fingerprint;

import android.content.Context;
import android.view.SurfaceView;
import android.widget.TextView;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.PlayerLogic;
import com.huawei.playerinterface.parameter.HASetParam;
import com.huawei.playerinterface.popupwindow.HAMessageStyle;
import com.huawei.playerinterface.popupwindow.HAShowOnPopupWindow;

public class HAFingerPrint extends HAShowOnPopupWindow
{
  private TextView MessageView;
  private String TAG = "HAFingerPrint";
  private int duration = 0;
  private int enable = 0;
  private FingerPrintPara fingerPrintPara;
  private int height = 0;
  private int interval = 0;
  private boolean isDismiss = false;
  private boolean isShow = false;
  private SurfaceView mSurfaceView;
  private String message = "";
  private HAMessageStyle style;
  private int tickDuration = 0;
  private int tickInterval = 0;
  private int width = 0;

  public HAFingerPrint(Context paramContext, SurfaceView paramSurfaceView, HAMessageStyle paramHAMessageStyle, FingerPrintPara paramFingerPrintPara)
  {
    super(paramContext, paramSurfaceView, paramHAMessageStyle);
    this.fingerPrintPara = paramFingerPrintPara;
    this.mSurfaceView = paramSurfaceView;
    this.style = paramHAMessageStyle;
    init();
  }

  private int getRandomData(int paramInt)
  {
    return (int)(Math.random() * (paramInt - -paramInt) + -paramInt);
  }

  private void getpopupWindowLocal()
  {
    this.style.STYLE_X = getRandomData(this.width);
    this.style.STYLE_Y = getRandomData(this.height);
  }

  private void init()
  {
    this.enable = this.fingerPrintPara.FIGPRT_ENABLE;
    this.interval = (1000 * this.fingerPrintPara.FIGPRT_INTERVAL);
    this.duration = (1000 * (int)this.fingerPrintPara.FIGPRT_DURATION);
    this.width = (this.mSurfaceView.getWidth() / 2);
    this.height = (this.mSurfaceView.getHeight() / 2);
    DmpLog.d(this.TAG, "width = " + this.width + "height = " + this.height);
    this.MessageView = ((TextView)super.getContentView());
    this.MessageView.setPadding(15, 5, 15, 5);
  }

  private void isDismissPopupWindow()
  {
    if ((this.isShow) && (this.isDismiss))
    {
      int i = this.tickDuration;
      this.tickDuration = (i + 1);
      if (i > this.duration / PlayerLogic.getPlayerSysTick())
      {
        dismissPopupWindow();
        this.tickDuration = 0;
        this.isDismiss = false;
        DmpLog.d(this.TAG, "isDismissPopupWindow isshow= " + this.isShow);
      }
    }
  }

  private void showFingerPrint(String paramString)
  {
    if (paramString == null)
    {
      super.dismissPopupWindow();
      DmpLog.d(this.TAG, "userId is null! ");
      return;
    }
    super.showPopupWindowMessage(this.style, paramString);
    DmpLog.d(this.TAG, "userId = " + paramString);
  }

  public boolean isShowEnable()
  {
    return this.isShow;
  }

  public int setFingerPrintsetProperties(HASetParam paramHASetParam, Object paramObject)
  {
    switch (1.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return 1;
    case 1:
      if ((paramObject instanceof Integer))
      {
        this.fingerPrintPara.FIGPRT_ENABLE = ((Integer)paramObject).intValue();
        this.enable = this.fingerPrintPara.FIGPRT_ENABLE;
      }
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    }
    while (true)
    {
      return 0;
      DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not Integer");
      return -1;
      if ((paramObject instanceof Integer))
      {
        this.fingerPrintPara.FIGPRT_INTERVAL = ((Integer)paramObject).intValue();
        this.interval = (1000 * this.fingerPrintPara.FIGPRT_INTERVAL);
      }
      else
      {
        DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not Integer");
        return -1;
        if ((paramObject instanceof Object))
        {
          DmpLog.e(this.TAG, " setProperties() ->setProperties:" + paramObject);
          try
          {
            this.fingerPrintPara.FIGPRT_DURATION = Float.parseFloat(paramObject.toString());
            this.duration = ((int)(1000.0F * this.fingerPrintPara.FIGPRT_DURATION));
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
        else
        {
          DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not String");
          return -1;
          if ((paramObject instanceof Integer))
          {
            this.style.STYLE_OPACITY = ((Integer)paramObject).intValue();
          }
          else
          {
            DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not Integer");
            return -1;
            if ((paramObject instanceof Integer))
            {
              this.style.STYLE_FONTSIZE = ((Integer)paramObject).intValue();
            }
            else
            {
              DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not Integer");
              return -1;
              if ((paramObject instanceof String))
              {
                this.style.STYLE_BKCOLOR = ((String)paramObject);
              }
              else
              {
                DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not String");
                return -1;
                if ((paramObject instanceof String))
                {
                  this.style.STYLE_FONTCOLOR = ((String)paramObject);
                }
                else
                {
                  DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not String");
                  return -1;
                  if (!(paramObject instanceof String))
                    break;
                  this.fingerPrintPara.FIGPRT_USERID = ((String)paramObject);
                  this.message = this.fingerPrintPara.FIGPRT_USERID;
                }
              }
            }
          }
        }
      }
    }
    DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not String");
    return -1;
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

  public void updateFingerPrint()
  {
    if (!this.isShow)
      return;
    if (!this.isDismiss)
    {
      int i = this.tickInterval;
      this.tickInterval = (i + 1);
      if ((i > this.interval / PlayerLogic.getPlayerSysTick()) && (this.enable == 1))
      {
        getpopupWindowLocal();
        showFingerPrint(this.message);
        this.tickInterval = 0;
        this.isDismiss = true;
      }
    }
    isDismissPopupWindow();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.fingerprint.HAFingerPrint
 * JD-Core Version:    0.6.2
 */