package com.starcor.ui.testspeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.util.ArrayList;

public class ConnectView extends LinearLayout
{
  private static final int MSG_REFRESH = 1;
  private static final String TAG = "ConnectView";
  private Bitmap bit_error;
  private Bitmap bit_ok;
  private Bitmap bit_point_green;
  private Bitmap bit_point_red;
  private Bitmap bit_point_white;
  private int greenPoint;
  private Context mContext;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
      }
      if (ConnectView.this.greenPoint == 5)
      {
        for (int i = 0; i < 5; i++)
          ((ImageView)ConnectView.this.getChildAt(i)).setImageBitmap(ConnectView.this.bit_point_white);
        ConnectView.access$002(ConnectView.this, 0);
      }
      Logger.i("ConnectView", "handleMessage what=1 greenPoint:" + ConnectView.this.greenPoint);
      ((ImageView)ConnectView.this.getChildAt(ConnectView.this.greenPoint)).setImageBitmap(ConnectView.this.bit_point_green);
      ConnectView.access$008(ConnectView.this);
    }
  };
  private boolean needConnect = true;

  public ConnectView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initViews();
  }

  private void initViews()
  {
    setGravity(17);
    setOrientation(0);
    ArrayList localArrayList = new ArrayList();
    this.bit_point_green = BitmapService.getInstance(this.mContext).getBitmap("point_connect.png");
    this.bit_point_white = BitmapService.getInstance(this.mContext).getBitmap("point_unconnect.png");
    this.bit_point_red = BitmapService.getInstance(this.mContext).getBitmap("point_connect_error.png");
    this.bit_ok = BitmapService.getInstance(this.mContext).getBitmap("connect_ok.png");
    this.bit_error = BitmapService.getInstance(this.mContext).getBitmap("connect_error.png");
    for (int i = 0; i < 5; i++)
    {
      ImageView localImageView = new ImageView(this.mContext);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(App.Operation(7.0F), App.Operation(7.0F));
      localLayoutParams.leftMargin = App.Operation(10.0F);
      localArrayList.add(localImageView);
      localImageView.setId(i);
      localImageView.setImageBitmap(this.bit_point_white);
      addView(localImageView, localLayoutParams);
    }
  }

  public void initView()
  {
    for (int i = 0; i < 5; i++)
    {
      ((ImageView)getChildAt(i)).setImageBitmap(this.bit_point_white);
      getChildAt(2).getLayoutParams().width = App.Operation(7.0F);
      getChildAt(2).getLayoutParams().height = App.Operation(7.0F);
    }
    this.needConnect = false;
  }

  public void resetView()
  {
    this.needConnect = false;
    getChildAt(2).getLayoutParams().width = App.Operation(7.0F);
    getChildAt(2).getLayoutParams().height = App.Operation(7.0F);
    for (int i = 0; i < 5; i++)
      ((ImageView)getChildAt(i)).setImageBitmap(this.bit_point_white);
  }

  public void startconnect()
  {
    this.needConnect = true;
    new Thread()
    {
      public void run()
      {
        while (ConnectView.this.needConnect)
        {
          ConnectView.this.mHandler.sendEmptyMessage(1);
          try
          {
            sleep(500L);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }
    .start();
  }

  public void stopConnect(boolean paramBoolean)
  {
    this.greenPoint = 0;
    this.needConnect = false;
    if (paramBoolean)
    {
      for (int j = 0; j < 5; j++)
        ((ImageView)getChildAt(j)).setImageBitmap(this.bit_point_green);
      LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(App.Operation(18.0F), App.Operation(19.0F));
      localLayoutParams2.leftMargin = App.Operation(10.0F);
      getChildAt(2).setLayoutParams(localLayoutParams2);
      ((ImageView)getChildAt(2)).setImageBitmap(this.bit_ok);
      return;
    }
    for (int i = 0; i < 5; i++)
      ((ImageView)getChildAt(i)).setImageBitmap(this.bit_point_red);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(App.Operation(18.0F), App.Operation(19.0F));
    localLayoutParams1.leftMargin = App.Operation(10.0F);
    getChildAt(2).setLayoutParams(localLayoutParams1);
    ((ImageView)getChildAt(2)).setImageBitmap(this.bit_error);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.testspeed.ConnectView
 * JD-Core Version:    0.6.2
 */