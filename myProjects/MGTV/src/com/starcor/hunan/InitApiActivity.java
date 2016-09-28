package com.starcor.hunan;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.jump.bussines.BussinesMessage;
import com.starcor.message.MessageHandler;

public class InitApiActivity extends DialogActivity
{
  private static final int KILL_ACTIVITY = 256;
  private static final String TAG = InitApiActivity.class.getSimpleName();
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 256:
      }
      Logger.i(InitApiActivity.TAG, "KILL_ACTIVITY");
      InitApiActivity.this.finish();
    }
  };

  private Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramResources.openRawResource(paramInt, localTypedValue);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTargetDensity = localTypedValue.density;
    return BitmapFactory.decodeResource(paramResources, paramInt, localOptions);
  }

  private void doNext()
  {
    BussinesMessage localBussinesMessage = new BussinesMessage(this);
    Intent localIntent = getIntent();
    localIntent.putExtra("isFromInit", true);
    localBussinesMessage.setIntent(localIntent);
    MessageHandler.instance().doNotify(localBussinesMessage);
    this.mHandler.sendEmptyMessageDelayed(256, 1000L);
  }

  private void showLoaddingImage()
  {
    RelativeLayout localRelativeLayout = new RelativeLayout(this);
    localRelativeLayout.setGravity(17);
    ImageView localImageView = new ImageView(this.context);
    Bitmap localBitmap = decodeResource(getResources(), 2130837541);
    localImageView.setLayoutParams(new LinearLayout.LayoutParams(App.Operation(localBitmap.getWidth()), App.Operation(localBitmap.getHeight())));
    localImageView.setImageBitmap(localBitmap);
    localRelativeLayout.addView(localImageView);
    setContentView(localRelativeLayout);
    showLoaddingDialog();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Logger.i(TAG, "onCreate!");
    if ((AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO) && ((!getIntent().getBooleanExtra("is_need_api", true)) || (GlobalLogic.getInstance().isAppInterfaceReady())))
    {
      showLoaddingImage();
      new Thread()
      {
        public void run()
        {
          try
          {
            Thread.sleep(500L);
            InitApiActivity.this.doNext();
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            while (true)
              localInterruptedException.printStackTrace();
          }
        }
      }
      .start();
      return;
    }
    if (GlobalLogic.getInstance().isAppInterfaceReady())
    {
      doNext();
      return;
    }
    if (AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO)
      showLoaddingImage();
    while (true)
    {
      App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
      return;
      showLoaddingDialog();
    }
  }

  protected void onProgressDialogDismissWithBackPressed()
  {
    finish();
  }

  private class OnApiOk
    implements InitApiData.onApiOKListener
  {
    private OnApiOk()
    {
    }

    public void onApiOk(int paramInt)
    {
      if (paramInt == 1)
      {
        Logger.i(InitApiActivity.TAG, "onApiOk!");
        String str = GeneralUtils.getTopActivity(InitApiActivity.this);
        Logger.i(InitApiActivity.TAG, "isFinishing=" + InitApiActivity.this.isFinishing() + ",topActivity=" + str);
        if (GeneralUtils.isMgtvApp(str))
          InitApiActivity.this.doNext();
      }
    }

    public void onGetApiDataError()
    {
      Logger.i(InitApiActivity.TAG, "onGetApiDataError!");
    }

    public void onNeedFinishActivity()
    {
      InitApiActivity.this.finish();
    }
  }

  private class OnServiceOK
    implements App.OnServiceOKListener
  {
    private OnServiceOK()
    {
    }

    public void onServiceOK()
    {
      Logger.i(InitApiActivity.TAG, "onServiceOK!");
      new InitApiData(InitApiActivity.this).setOnApiOkListener(new InitApiActivity.OnApiOk(InitApiActivity.this, null));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.InitApiActivity
 * JD-Core Version:    0.6.2
 */