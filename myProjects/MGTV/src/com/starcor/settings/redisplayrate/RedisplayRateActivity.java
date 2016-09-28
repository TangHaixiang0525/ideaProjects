package com.starcor.settings.redisplayrate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import com.starcor.setting.service.ISettingService;
import com.starcor.setting.service.ISettingService.Stub;
import com.starcor.setting.service.NetworkStateInfo;
import com.starcor.setting.service.NetworkStateInfo.NetworkState;
import com.starcor.settings.R.drawable;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;
import java.io.PrintStream;

public class RedisplayRateActivity extends Activity
{
  private static final int OUTPUT720_FULL_HEIGHT = 720;
  private static final int OUTPUT720_FULL_WIDTH = 1280;
  private static String TAG = RedisplayRateActivity.class.getSimpleName();
  private static final String sel_720poutput_height = "ubootenv.var.720poutputheight";
  private static final String sel_720poutput_width = "ubootenv.var.720poutputwidth";
  private static final String sel_720poutput_x = "ubootenv.var.720poutputx";
  private static final String sel_720poutput_y = "ubootenv.var.720poutputy";
  private static int zoom_pixel = 2;
  AlertDialog alertDialog;
  private Button bottomBtn;
  private Button centerBtn;
  private ServiceConnection connection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      RedisplayRateActivity.this.settingService = ISettingService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        System.out.println(RedisplayRateActivity.this.settingService.getNetworkStateInfo().state.toString() + "----Bind Success:" + RedisplayRateActivity.this.settingService);
        RedisplayRateActivity.this.initOutput();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        while (true)
        {
          System.out.println("RemoteException---" + localRemoteException.getMessage());
          localRemoteException.printStackTrace();
        }
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
    }
  };
  private Button leftBtn;
  private PositionCoor position_bak;
  private PositionCoor position_cur = new PositionCoor();
  private String pre_output_height = "";
  private String pre_output_width = "";
  private String pre_output_x = "";
  private String pre_output_y = "";
  private Button rightBtn;
  private ISettingService settingService = null;
  private Button topBtn;
  private boolean zoomFlag = true;

  private void initOutput()
  {
    try
    {
      this.pre_output_x = this.settingService.getSystemProperties("ubootenv.var.720poutputx");
      this.pre_output_y = this.settingService.getSystemProperties("ubootenv.var.720poutputy");
      this.pre_output_width = this.settingService.getSystemProperties("ubootenv.var.720poutputwidth");
      this.pre_output_height = this.settingService.getSystemProperties("ubootenv.var.720poutputheight");
      if (this.pre_output_x.equals(""))
        this.pre_output_x = "0";
      if (this.pre_output_y.equals(""))
        this.pre_output_y = "0";
      if (this.pre_output_width.equals(""))
        this.pre_output_width = String.valueOf(1280);
      if (this.pre_output_height.equals(""))
        this.pre_output_height = String.valueOf(720);
      this.position_cur.width = Integer.valueOf(this.pre_output_width).intValue();
      this.position_cur.height = Integer.valueOf(this.pre_output_height).intValue();
      this.position_cur.left = Integer.valueOf(this.pre_output_x).intValue();
      this.position_cur.top = Integer.valueOf(this.pre_output_y).intValue();
      this.position_cur.right = (-1 + (this.position_cur.width + this.position_cur.left));
      this.position_cur.bottom = (-1 + (this.position_cur.height + this.position_cur.top));
      this.position_bak = new PositionCoor(this.position_cur);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localRemoteException.printStackTrace();
    }
  }

  private void initView()
  {
    this.centerBtn = ((Button)findViewById(R.id.center));
    this.leftBtn = ((Button)findViewById(R.id.left));
    this.rightBtn = ((Button)findViewById(R.id.right));
    this.topBtn = ((Button)findViewById(R.id.top));
    this.bottomBtn = ((Button)findViewById(R.id.bottom));
    this.centerBtn.setOnKeyListener(new View.OnKeyListener()
    {
      public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 23))
          RedisplayRateActivity.this.onCenterKeyDown();
        while ((paramAnonymousKeyEvent.getAction() != 1) || (paramAnonymousInt != 23))
          return false;
        RedisplayRateActivity.this.centerBtn.setSelected(false);
        return false;
      }
    });
    this.centerBtn.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        default:
          return false;
        case 0:
          RedisplayRateActivity.this.onCenterKeyDown();
          return false;
        case 1:
        }
        RedisplayRateActivity.this.centerBtn.setSelected(false);
        return false;
      }
    });
    this.leftBtn.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        case 1:
        default:
        case 0:
        }
        while (true)
        {
          return false;
          RedisplayRateActivity.this.onLeftKeyDown();
        }
      }
    });
    this.rightBtn.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        case 1:
        default:
        case 0:
        }
        while (true)
        {
          return false;
          RedisplayRateActivity.this.onRightKeyDown();
        }
      }
    });
    this.topBtn.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        case 1:
        default:
        case 0:
        }
        while (true)
        {
          return false;
          RedisplayRateActivity.this.onUpKeyDown();
        }
      }
    });
    this.bottomBtn.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        case 1:
        default:
        case 0:
        }
        while (true)
        {
          return false;
          RedisplayRateActivity.this.onDownkeyDown();
        }
      }
    });
  }

  private void onCenterKeyDown()
  {
    boolean bool = true;
    if (this.zoomFlag)
    {
      this.leftBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_left));
      this.rightBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_right));
      this.topBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_top));
      this.bottomBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_bottom));
    }
    while (true)
    {
      this.centerBtn.setSelected(bool);
      if (this.zoomFlag)
        bool = false;
      this.zoomFlag = bool;
      return;
      this.leftBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_right));
      this.rightBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_left));
      this.topBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_bottom));
      this.bottomBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_btn_top));
    }
  }

  private void onDownkeyDown()
  {
    this.bottomBtn.setSelected(true);
    PositionCoor localPositionCoor2;
    if (this.zoomFlag)
      localPositionCoor2 = this.position_cur;
    PositionCoor localPositionCoor1;
    for (localPositionCoor2.bottom -= zoom_pixel; ; localPositionCoor1.bottom += zoom_pixel)
    {
      setPosition(this.position_cur.left, this.position_cur.top, this.position_cur.right, this.position_cur.bottom, 0);
      return;
      localPositionCoor1 = this.position_cur;
    }
  }

  private void onLeftKeyDown()
  {
    this.leftBtn.setSelected(true);
    PositionCoor localPositionCoor2;
    if (this.zoomFlag)
      localPositionCoor2 = this.position_cur;
    PositionCoor localPositionCoor1;
    for (localPositionCoor2.left += zoom_pixel; ; localPositionCoor1.left -= zoom_pixel)
    {
      setPosition(this.position_cur.left, this.position_cur.top, this.position_cur.right, this.position_cur.bottom, 0);
      return;
      localPositionCoor1 = this.position_cur;
    }
  }

  private void onRightKeyDown()
  {
    this.rightBtn.setSelected(true);
    PositionCoor localPositionCoor2;
    if (this.zoomFlag)
      localPositionCoor2 = this.position_cur;
    PositionCoor localPositionCoor1;
    for (localPositionCoor2.right -= zoom_pixel; ; localPositionCoor1.right += zoom_pixel)
    {
      setPosition(this.position_cur.left, this.position_cur.top, this.position_cur.right, this.position_cur.bottom, 0);
      return;
      localPositionCoor1 = this.position_cur;
    }
  }

  private void onUpKeyDown()
  {
    this.topBtn.setSelected(true);
    PositionCoor localPositionCoor2;
    if (this.zoomFlag)
      localPositionCoor2 = this.position_cur;
    PositionCoor localPositionCoor1;
    for (localPositionCoor2.top += zoom_pixel; ; localPositionCoor1.top -= zoom_pixel)
    {
      setPosition(this.position_cur.left, this.position_cur.top, this.position_cur.right, this.position_cur.bottom, 0);
      return;
      localPositionCoor1 = this.position_cur;
    }
  }

  private void savePosition()
  {
    this.position_cur.width = (1 + (this.position_cur.right - this.position_cur.left));
    this.position_cur.height = (1 + (this.position_cur.bottom - this.position_cur.top));
    if (this.position_cur.width % 2 == 1)
    {
      PositionCoor localPositionCoor2 = this.position_cur;
      localPositionCoor2.width = (-1 + localPositionCoor2.width);
    }
    if (this.position_cur.height % 2 == 1)
    {
      PositionCoor localPositionCoor1 = this.position_cur;
      localPositionCoor1.height = (-1 + localPositionCoor1.height);
    }
    try
    {
      this.settingService.saveRedisplayConfig(this.position_cur.left, this.position_cur.top, this.position_cur.width, this.position_cur.height);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  private void setPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramInt1 < 0)
      paramInt1 = 0;
    while (true)
    {
      if (paramInt2 < 0)
      {
        paramInt2 = 0;
        label12: if (paramInt3 >= 1200)
          break label132;
        paramInt3 = 1200;
        label23: if (paramInt4 >= 650)
          break label146;
        paramInt4 = 650;
        label36: Log.w("lx", "l=" + paramInt1 + ",t=" + paramInt2 + ",r=" + paramInt3 + ",b=" + paramInt4);
      }
      try
      {
        this.settingService.setRedisplayRate(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
        return;
        if (paramInt1 > 80)
        {
          paramInt1 = 80;
          continue;
          if (paramInt2 <= 70)
            break label12;
          paramInt2 = 70;
          break label12;
          label132: if (paramInt3 <= 1300)
            break label23;
          paramInt3 = 1300;
          break label23;
          label146: if (paramInt4 <= 750)
            break label36;
          paramInt4 = 750;
        }
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }
  }

  public void onBackPressed()
  {
    if (this.position_cur.equals(this.position_bak))
    {
      super.onBackPressed();
      return;
    }
    if (this.alertDialog != null)
      this.alertDialog.dismiss();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("娉ㄦ剰").setMessage("閲嶆樉鐜囧凡璁剧疆锛岀‘璁や繚瀛橈紵");
    localBuilder.setPositiveButton("鏄�", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        RedisplayRateActivity.this.savePosition();
        RedisplayRateActivity.this.onBackPressed();
        RedisplayRateActivity.this.alertDialog = null;
      }
    });
    localBuilder.setNegativeButton("鍚�", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        RedisplayRateActivity.this.setPosition(RedisplayRateActivity.this.position_bak.left, RedisplayRateActivity.this.position_bak.top, RedisplayRateActivity.this.position_bak.right, RedisplayRateActivity.this.position_bak.bottom, 0);
        RedisplayRateActivity.this.onBackPressed();
        RedisplayRateActivity.this.alertDialog = null;
      }
    });
    this.alertDialog = localBuilder.create();
    this.alertDialog.show();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_display_rate);
    initView();
    bindService(new Intent(ISettingService.class.getName()), this.connection, 1);
  }

  protected void onDestroy()
  {
    unbindService(this.connection);
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default:
    case 23:
    case 21:
    case 22:
    case 19:
    case 20:
    case 4:
    }
    while (true)
    {
      return true;
      onCenterKeyDown();
      continue;
      onLeftKeyDown();
      continue;
      onRightKeyDown();
      continue;
      onUpKeyDown();
      continue;
      onDownkeyDown();
      continue;
      onBackPressed();
    }
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default:
    case 21:
    case 22:
    case 19:
    case 20:
    case 23:
    }
    while (true)
    {
      return true;
      this.leftBtn.setSelected(false);
      continue;
      this.rightBtn.setSelected(false);
      continue;
      this.topBtn.setSelected(false);
      continue;
      this.bottomBtn.setSelected(false);
      continue;
      this.centerBtn.setSelected(false);
    }
  }

  protected void onPause()
  {
    super.onPause();
  }

  protected void onResume()
  {
    super.onResume();
  }

  // ERROR //
  public void writeFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 405	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 406	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: aload_3
    //   10: invokevirtual 410	java/io/File:exists	()Z
    //   13: ifne +4 -> 17
    //   16: return
    //   17: new 412	java/io/BufferedWriter
    //   20: dup
    //   21: new 414	java/io/FileWriter
    //   24: dup
    //   25: aload_3
    //   26: invokespecial 417	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   29: bipush 32
    //   31: invokespecial 420	java/io/BufferedWriter:<init>	(Ljava/io/Writer;I)V
    //   34: astore 4
    //   36: getstatic 55	com/starcor/settings/redisplayrate/RedisplayRateActivity:TAG	Ljava/lang/String;
    //   39: new 279	java/lang/StringBuilder
    //   42: dup
    //   43: ldc_w 422
    //   46: invokespecial 284	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   49: aload_1
    //   50: invokevirtual 293	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: ldc_w 424
    //   56: invokevirtual 293	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: aload_2
    //   60: invokevirtual 293	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: invokevirtual 300	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   66: invokestatic 429	com/starcor/settings/utils/Debug:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   69: pop
    //   70: aload 4
    //   72: aload_2
    //   73: invokevirtual 432	java/io/BufferedWriter:write	(Ljava/lang/String;)V
    //   76: aload 4
    //   78: invokevirtual 435	java/io/BufferedWriter:close	()V
    //   81: return
    //   82: astore 6
    //   84: getstatic 55	com/starcor/settings/redisplayrate/RedisplayRateActivity:TAG	Ljava/lang/String;
    //   87: new 279	java/lang/StringBuilder
    //   90: dup
    //   91: ldc_w 437
    //   94: invokespecial 284	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   97: aload_3
    //   98: invokevirtual 440	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   101: invokevirtual 300	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   104: invokestatic 443	com/starcor/settings/utils/Debug:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   107: pop
    //   108: return
    //   109: astore 5
    //   111: aload 4
    //   113: invokevirtual 435	java/io/BufferedWriter:close	()V
    //   116: aload 5
    //   118: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   17	36	82	java/io/IOException
    //   76	81	82	java/io/IOException
    //   111	119	82	java/io/IOException
    //   36	76	109	finally
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.redisplayrate.RedisplayRateActivity
 * JD-Core Version:    0.6.2
 */