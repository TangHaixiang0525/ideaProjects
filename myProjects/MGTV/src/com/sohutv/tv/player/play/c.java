package com.sohutv.tv.player.play;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;
import com.sohu.logger.log.OutputLog;
import com.sohutv.tv.player.ad.SohuTVAdvertise;
import com.sohutv.tv.player.ad.SohuTVAdvertise.PlaybackState;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.interfaces.ISkyworthPlayerCallback;
import com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback;
import com.sohutv.tv.player.interfaces.IVideoListener;
import com.sohutv.tv.player.interfaces.PlayerCallback;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer.OnBufferingUpdateListener;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer.OnCompletionListener;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer.OnErrorListener;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer.OnInfoListener;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer.OnPreparedListener;
import com.tianci.framework.player.kernel.jni.SkyBjPlayer.OnSeekCompleteListener;
import java.util.Map;

public final class c extends a
{
  public static int b = 0;
  private int A = 0;
  private int B = 0;
  private int C = 0;
  private int D = 0;
  private final SkyBjPlayer.OnCompletionListener E = new SkyBjPlayer.OnCompletionListener()
  {
    public void onCompletion(SkyBjPlayer paramAnonymousSkyBjPlayer)
    {
      c.b = 5;
      c.d(c.this, 5);
      if (c.this.c != null)
        c.this.c.hide();
      Log.i("Sohuplayer", "SkyBjPlayer onCompletion 回调1");
      if (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO)
      {
        c.a(c.this, false);
        if (c.c(c.this) != null)
        {
          Log.i("Sohuplayer", "SkyBjPlayer onCompletion 回调2");
          c.c(c.this).onCompletion(paramAnonymousSkyBjPlayer);
        }
      }
      if (c.this.a != null)
        c.this.a.onVideoCompletion();
    }
  };
  private final SkyBjPlayer.OnErrorListener F = new SkyBjPlayer.OnErrorListener()
  {
    public boolean onError(SkyBjPlayer paramAnonymousSkyBjPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      OutputLog.d("SkyworthVideoView", "Error: " + paramAnonymousInt1 + "," + paramAnonymousInt2);
      c.b = -1;
      c.d(c.this, -1);
      if (c.this.c != null)
        c.this.c.hide();
      if (c.c(c.this) != null)
        c.c(c.this).onError(paramAnonymousSkyBjPlayer, paramAnonymousInt1, paramAnonymousInt2);
      return true;
    }
  };
  private final SkyBjPlayer.OnInfoListener G = new SkyBjPlayer.OnInfoListener()
  {
    public boolean onInfo(SkyBjPlayer paramAnonymousSkyBjPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return c.c(c.this).onInfo(paramAnonymousSkyBjPlayer, paramAnonymousInt1, paramAnonymousInt2);
    }
  };
  private final SkyBjPlayer.OnBufferingUpdateListener H = new SkyBjPlayer.OnBufferingUpdateListener()
  {
    public void onBufferingUpdate(SkyBjPlayer paramAnonymousSkyBjPlayer, int paramAnonymousInt)
    {
      c.e(c.this, paramAnonymousInt);
      if (c.c(c.this) != null);
    }
  };
  private final SkyBjPlayer.OnSeekCompleteListener I = new SkyBjPlayer.OnSeekCompleteListener()
  {
    public void onSeekComplete(SkyBjPlayer paramAnonymousSkyBjPlayer)
    {
      if (c.c(c.this) != null)
        c.c(c.this).OnSeekCompleteListener();
    }
  };
  private SohuMediaController J;
  protected SohuMediaController c;
  int d = 1;
  int e = 2;
  MediaPlayer.OnVideoSizeChangedListener f = new MediaPlayer.OnVideoSizeChangedListener()
  {
    public void onVideoSizeChanged(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      c.a(c.this, paramAnonymousMediaPlayer.getVideoWidth());
      c.b(c.this, paramAnonymousMediaPlayer.getVideoHeight());
      OutputLog.i("@@@@@@@@@@", "onVideoSizeChanged video size: " + c.a(c.this) + 'x' + c.b(c.this));
      if ((c.a(c.this) != 0) && (c.b(c.this) != 0))
      {
        c.this.getHolder().setFixedSize(c.a(c.this), c.b(c.this));
        c.this.requestLayout();
      }
      if (c.c(c.this) != null)
        c.c(c.this).onVideoSizeChanged(paramAnonymousMediaPlayer, paramAnonymousInt1, paramAnonymousInt2);
    }
  };
  SkyBjPlayer.OnPreparedListener g = new SkyBjPlayer.OnPreparedListener()
  {
    public void onPrepared(SkyBjPlayer paramAnonymousSkyBjPlayer)
    {
      c.b = 2;
      if (c.this.c != null)
        c.this.c.setEnabled(true);
      c.a(c.this, paramAnonymousSkyBjPlayer.getVideoWidth());
      c.b(c.this, paramAnonymousSkyBjPlayer.getVideoHeight());
      OutputLog.i("@@@@@@@@@@", "video size: " + c.a(c.this) + "/" + c.b(c.this));
      if ((c.a(c.this) != 0) && (c.b(c.this) != 0))
      {
        OutputLog.i("@@@@@@@@@@", "Surface size: " + c.d(c.this) + "/" + c.e(c.this));
        c.this.getHolder().setFixedSize(c.a(c.this), c.b(c.this));
        if ((c.d(c.this) == c.a(c.this)) && (c.e(c.this) == c.b(c.this)))
        {
          OutputLog.i("@@@@@@@@@@", "mTargetState: " + c.f(c.this));
          if (c.f(c.this) == 3)
            c.g(c.this);
        }
      }
      while (true)
      {
        c.c(c.this, com.sohutv.tv.player.util.a.a.c);
        if (c.c(c.this) != null)
          c.c(c.this).onPrepared(paramAnonymousSkyBjPlayer);
        if (c.this.a != null)
          c.this.a.onVideoPrepared();
        return;
        if (c.f(c.this) == 3)
          c.g(c.this);
      }
    }
  };
  SurfaceHolder.Callback h = new SurfaceHolder.Callback()
  {
    public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      int i = 1;
      c.f(c.this, paramAnonymousInt2);
      c.g(c.this, paramAnonymousInt3);
      OutputLog.d("@@@@@@@@@@", "SohuTVVideoView surfaceChanged " + c.d(c.this) + "x" + c.e(c.this));
      if ((c.h(c.this) != null) && (paramAnonymousSurfaceHolder != null) && (paramAnonymousSurfaceHolder.getSurface().isValid()));
      try
      {
        c.h(c.this).setDisplay(paramAnonymousSurfaceHolder);
        if (c.f(c.this) == 3)
        {
          j = i;
          if ((c.a(c.this) != paramAnonymousInt2) || (c.b(c.this) != paramAnonymousInt3))
            break label213;
          if ((c.h(c.this) != null) && (j != 0) && (i != 0))
            c.g(c.this);
          if (c.c(c.this) != null)
            c.c(c.this).surfaceChanged(paramAnonymousSurfaceHolder, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          continue;
          int j = 0;
          continue;
          label213: i = 0;
        }
      }
    }

    public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
    {
      OutputLog.e("lifeCycle", "surfaceCreated");
      c.a(c.this, paramAnonymousSurfaceHolder);
      c.i(c.this);
      if (c.c(c.this) != null)
        c.c(c.this).surfaceCreated(paramAnonymousSurfaceHolder);
    }

    public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
    {
      OutputLog.e("lifeCycle", "surfaceDestroyed");
      if (c.j(c.this) != null)
        c.j(c.this).surfaceDestroyed(paramAnonymousSurfaceHolder);
      c.a(c.this, null);
      if (c.this.c != null)
        c.this.c.hide();
      if ((c.h(c.this) != null) && (c.k(c.this)));
      try
      {
        c.b(c.this, true);
        c.h(c.this).stop();
        c.a(c.this, false);
        if (c.c(c.this) != null)
          c.c(c.this).surfaceDestroyed(paramAnonymousSurfaceHolder);
        if (c.this.a != null)
          c.this.a.onVideoStop();
        c.this.a(true);
        return;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        while (true)
          localIllegalStateException.printStackTrace();
      }
    }
  };
  private final String i = "SkyworthVideoView";
  private Uri j;
  private int k = 0;
  private SurfaceHolder l = null;
  private SkyBjPlayer m = null;
  private int n;
  private int o;
  private int p;
  private int q;
  private int r;
  private SurfaceHolder.Callback s;
  private Context t;
  private boolean u = false;
  private ISkyworthPlayerCallback v;
  private int w = 2;
  private int x;
  private int y;
  private int z = 0;

  public c(Context paramContext)
  {
    super(paramContext);
    this.t = paramContext;
    b();
  }

  private void a(Uri paramUri)
  {
    a(paramUri, null);
  }

  private void a(Uri paramUri, Map<String, String> paramMap)
  {
    this.j = paramUri;
    d();
    requestLayout();
    invalidate();
  }

  private void a(SohuMediaController paramSohuMediaController)
  {
    if (this.c != null)
      this.c.hide();
    this.c = paramSohuMediaController;
    e();
  }

  private void a(String paramString)
  {
    a(Uri.parse(paramString));
  }

  private void b()
  {
    Log.i("Sohuplayer", "init skyworth videoview");
    this.n = 0;
    this.o = 0;
    getHolder().addCallback(this.h);
    getHolder().setType(3);
    b = 0;
    this.k = 0;
    Display localDisplay = ((WindowManager)this.t.getSystemService("window")).getDefaultDisplay();
    this.A = localDisplay.getHeight();
    this.z = localDisplay.getWidth();
  }

  private void b(int paramInt1, int paramInt2)
  {
    Log.i("Sohuplayer", "setVideoScaleFrameLayout left=" + paramInt1 + " top=" + paramInt2);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    localLayoutParams.leftMargin = paramInt1;
    localLayoutParams.rightMargin = paramInt1;
    localLayoutParams.topMargin = paramInt2;
    localLayoutParams.bottomMargin = paramInt2;
    setLayoutParams(localLayoutParams);
  }

  private void b(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      com.sohutv.tv.player.util.a.a.f.setDuration(0);
      com.sohutv.tv.player.util.a.a.f.setCurrentPosition(0);
    }
    while (com.sohutv.tv.player.util.a.a.f == null)
      return;
    com.sohutv.tv.player.util.a.a.f.setDuration(i());
    com.sohutv.tv.player.util.a.a.f.setCurrentPosition(j());
  }

  private void c()
  {
    OutputLog.d("test", "stopPlayback method");
    if (this.u)
      this.u = false;
    if (this.m != null)
      OutputLog.d("test", "stopPlayback method, MediaPlayer is not null");
    try
    {
      b(true);
      this.m.stop();
      this.m.release();
      this.m = null;
      b = 0;
      this.k = 0;
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        localIllegalStateException.printStackTrace();
    }
  }

  private void c(int paramInt)
  {
    if (l())
      OutputLog.e("pos2 = " + paramInt);
    try
    {
      this.m.seekTo(paramInt);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
    }
  }

  // ERROR //
  private void d()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 149	com/sohutv/tv/player/play/c:j	Landroid/net/Uri;
    //   4: ifnull +10 -> 14
    //   7: aload_0
    //   8: getfield 71	com/sohutv/tv/player/play/c:l	Landroid/view/SurfaceHolder;
    //   11: ifnonnull +4 -> 15
    //   14: return
    //   15: aload_0
    //   16: iconst_1
    //   17: putfield 87	com/sohutv/tv/player/play/c:D	I
    //   20: new 336	android/content/Intent
    //   23: dup
    //   24: ldc_w 338
    //   27: invokespecial 340	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   30: astore_1
    //   31: aload_1
    //   32: ldc_w 342
    //   35: ldc_w 344
    //   38: invokevirtual 348	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   41: pop
    //   42: aload_0
    //   43: getfield 135	com/sohutv/tv/player/play/c:t	Landroid/content/Context;
    //   46: aload_1
    //   47: invokevirtual 352	android/content/Context:sendBroadcast	(Landroid/content/Intent;)V
    //   50: aload_0
    //   51: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   54: ifnull +22 -> 76
    //   57: aload_0
    //   58: getfield 75	com/sohutv/tv/player/play/c:u	Z
    //   61: ifeq +15 -> 76
    //   64: aload_0
    //   65: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   68: invokevirtual 309	com/tianci/framework/player/kernel/jni/SkyBjPlayer:stop	()V
    //   71: aload_0
    //   72: iconst_0
    //   73: putfield 75	com/sohutv/tv/player/play/c:u	Z
    //   76: aload_0
    //   77: iconst_0
    //   78: invokevirtual 354	com/sohutv/tv/player/play/c:a	(Z)V
    //   81: aload_0
    //   82: new 306	com/tianci/framework/player/kernel/jni/SkyBjPlayer
    //   85: dup
    //   86: invokespecial 355	com/tianci/framework/player/kernel/jni/SkyBjPlayer:<init>	()V
    //   89: putfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   92: aload_0
    //   93: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   96: aload_0
    //   97: getfield 103	com/sohutv/tv/player/play/c:g	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnPreparedListener;
    //   100: invokevirtual 359	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setOnPreparedListener	(Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnPreparedListener;)V
    //   103: aload_0
    //   104: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   107: aload_0
    //   108: getfield 108	com/sohutv/tv/player/play/c:E	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnCompletionListener;
    //   111: invokevirtual 363	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setOnCompletionListener	(Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnCompletionListener;)V
    //   114: aload_0
    //   115: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   118: aload_0
    //   119: getfield 113	com/sohutv/tv/player/play/c:F	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnErrorListener;
    //   122: invokevirtual 367	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setOnErrorListener	(Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnErrorListener;)V
    //   125: aload_0
    //   126: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   129: aload_0
    //   130: getfield 118	com/sohutv/tv/player/play/c:G	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnInfoListener;
    //   133: invokevirtual 371	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setOnInfoListener	(Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnInfoListener;)V
    //   136: aload_0
    //   137: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   140: aload_0
    //   141: getfield 128	com/sohutv/tv/player/play/c:I	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnSeekCompleteListener;
    //   144: invokevirtual 375	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setOnSeekCompleteListener	(Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnSeekCompleteListener;)V
    //   147: aload_0
    //   148: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   151: aload_0
    //   152: getfield 123	com/sohutv/tv/player/play/c:H	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnBufferingUpdateListener;
    //   155: invokevirtual 379	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setOnBufferingUpdateListener	(Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnBufferingUpdateListener;)V
    //   158: aload_0
    //   159: iconst_0
    //   160: putfield 381	com/sohutv/tv/player/play/c:r	I
    //   163: aload_0
    //   164: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   167: aload_0
    //   168: getfield 135	com/sohutv/tv/player/play/c:t	Landroid/content/Context;
    //   171: aload_0
    //   172: getfield 149	com/sohutv/tv/player/play/c:j	Landroid/net/Uri;
    //   175: invokevirtual 385	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setDataSource	(Landroid/content/Context;Landroid/net/Uri;)V
    //   178: aload_0
    //   179: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   182: aload_0
    //   183: getfield 71	com/sohutv/tv/player/play/c:l	Landroid/view/SurfaceHolder;
    //   186: invokevirtual 389	com/tianci/framework/player/kernel/jni/SkyBjPlayer:setDisplay	(Landroid/view/SurfaceHolder;)V
    //   189: aload_0
    //   190: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   193: iconst_1
    //   194: ldc_w 391
    //   197: invokevirtual 395	com/tianci/framework/player/kernel/jni/SkyBjPlayer:prepare	(ILjava/lang/String;)V
    //   200: iconst_1
    //   201: putstatic 59	com/sohutv/tv/player/play/c:b	I
    //   204: aload_0
    //   205: invokevirtual 397	com/sohutv/tv/player/play/c:a	()V
    //   208: return
    //   209: astore 8
    //   211: ldc 65
    //   213: new 228	java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial 230	java/lang/StringBuilder:<init>	()V
    //   220: ldc_w 399
    //   223: invokevirtual 236	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: aload_0
    //   227: getfield 149	com/sohutv/tv/player/play/c:j	Landroid/net/Uri;
    //   230: invokevirtual 402	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   233: invokevirtual 245	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   236: aload 8
    //   238: invokestatic 405	com/sohu/logger/log/OutputLog:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   241: iconst_m1
    //   242: putstatic 59	com/sohutv/tv/player/play/c:b	I
    //   245: aload_0
    //   246: iconst_m1
    //   247: putfield 69	com/sohutv/tv/player/play/c:k	I
    //   250: aload_0
    //   251: getfield 113	com/sohutv/tv/player/play/c:F	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnErrorListener;
    //   254: aload_0
    //   255: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   258: iconst_1
    //   259: iconst_0
    //   260: invokeinterface 411 4 0
    //   265: pop
    //   266: return
    //   267: astore 10
    //   269: aload 10
    //   271: invokevirtual 315	java/lang/IllegalStateException:printStackTrace	()V
    //   274: goto -203 -> 71
    //   277: astore 5
    //   279: aload 5
    //   281: invokevirtual 315	java/lang/IllegalStateException:printStackTrace	()V
    //   284: iconst_m1
    //   285: putstatic 59	com/sohutv/tv/player/play/c:b	I
    //   288: aload_0
    //   289: iconst_m1
    //   290: putfield 69	com/sohutv/tv/player/play/c:k	I
    //   293: aload_0
    //   294: getfield 113	com/sohutv/tv/player/play/c:F	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnErrorListener;
    //   297: aload_0
    //   298: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   301: iconst_1
    //   302: iconst_0
    //   303: invokeinterface 411 4 0
    //   308: pop
    //   309: aload_0
    //   310: getfield 89	com/sohutv/tv/player/play/c:d	I
    //   313: aload_0
    //   314: getfield 91	com/sohutv/tv/player/play/c:e	I
    //   317: if_icmpge -303 -> 14
    //   320: ldc_w 413
    //   323: ldc_w 415
    //   326: invokestatic 188	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   329: pop
    //   330: aload_0
    //   331: iconst_1
    //   332: aload_0
    //   333: getfield 89	com/sohutv/tv/player/play/c:d	I
    //   336: iadd
    //   337: putfield 89	com/sohutv/tv/player/play/c:d	I
    //   340: aload_0
    //   341: invokespecial 151	com/sohutv/tv/player/play/c:d	()V
    //   344: return
    //   345: astore_3
    //   346: aload_3
    //   347: invokevirtual 416	java/lang/Exception:printStackTrace	()V
    //   350: iconst_m1
    //   351: putstatic 59	com/sohutv/tv/player/play/c:b	I
    //   354: aload_0
    //   355: iconst_m1
    //   356: putfield 69	com/sohutv/tv/player/play/c:k	I
    //   359: aload_0
    //   360: getfield 113	com/sohutv/tv/player/play/c:F	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer$OnErrorListener;
    //   363: aload_0
    //   364: getfield 73	com/sohutv/tv/player/play/c:m	Lcom/tianci/framework/player/kernel/jni/SkyBjPlayer;
    //   367: iconst_1
    //   368: iconst_0
    //   369: invokeinterface 411 4 0
    //   374: pop
    //   375: return
    //
    // Exception table:
    //   from	to	target	type
    //   81	208	209	java/lang/IllegalArgumentException
    //   64	71	267	java/lang/IllegalStateException
    //   81	208	277	java/lang/IllegalStateException
    //   81	208	345	java/lang/Exception
  }

  private void e()
  {
    if (this.c != null)
      if (!(getParent() instanceof View))
        break label39;
    label39: for (Object localObject = (View)getParent(); ; localObject = this)
    {
      a(this.c, (View)localObject, l());
      return;
    }
  }

  private void f()
  {
    if (this.c.isShowing())
    {
      this.c.hide();
      return;
    }
    this.c.show();
  }

  private void g()
  {
    OutputLog.d("dlna", "SohuTVVideoView start method");
    if (l())
      OutputLog.d("dlna", "SohuTVVideoView start method, mCurrentState = " + b);
    try
    {
      this.m.start();
      b = 3;
      this.u = true;
      this.k = 3;
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        localIllegalStateException.printStackTrace();
    }
  }

  private void h()
  {
    OutputLog.d("test", "SohuTVVideoView pause method");
    if ((l()) && (k()));
    try
    {
      this.m.pause();
      b = 4;
      this.k = 4;
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        localIllegalStateException.printStackTrace();
    }
  }

  private int i()
  {
    boolean bool = l();
    int i1 = 0;
    if (bool);
    try
    {
      int i2 = this.m.getDuration();
      i1 = i2;
      return i1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  private int j()
  {
    boolean bool = l();
    int i1 = 0;
    if (bool);
    try
    {
      int i2 = this.m.getCurrentPosition();
      i1 = i2;
      return i1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  private boolean k()
  {
    try
    {
      boolean bool1 = l();
      boolean bool2 = false;
      if (bool1)
      {
        boolean bool3 = this.m.isPlaying();
        bool2 = false;
        if (bool3)
          bool2 = true;
      }
      return bool2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private boolean l()
  {
    try
    {
      if ((this.m != null) && (b != -1) && (b != 0))
      {
        int i1 = b;
        if (i1 != 1)
          return true;
      }
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public double a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d1 = paramDouble1 / paramDouble3;
    double d2 = paramDouble2 / paramDouble4;
    if (d1 > d2)
      return d2;
    return d1;
  }

  protected void a()
  {
    e();
  }

  public void a(int paramInt)
  {
    this.w = paramInt;
    requestLayout();
  }

  public void a(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }

  protected void a(SohuMediaController paramSohuMediaController, View paramView, boolean paramBoolean)
  {
    paramSohuMediaController.setAnchorView(paramView);
    paramSohuMediaController.setEnabled(paramBoolean);
  }

  public void a(boolean paramBoolean)
  {
    if (this.m != null);
    try
    {
      this.m.reset();
      this.m.release();
      this.m = null;
      b = 0;
      if (paramBoolean)
        this.k = 0;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void b(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      b(this.B, this.C);
      return;
      Log.i("Sohuplayer", "SKY_CFG_TV_DISPLAY_MODE_16_9");
      this.B = 0;
      this.C = 0;
      continue;
      Log.i("Sohuplayer", "SKY_CFG_TV_DISPLAY_MODE_4_3");
      double d1 = a(this.z, this.A, 4 * (this.A / 3), this.A);
      this.B = ((int)((this.z - d1 * (4 * (this.A / 3))) / 2.0D));
      this.C = ((int)((this.A - d1 * this.A) / 2.0D));
      continue;
      Log.i("Sohuplayer", "SKY_CFG_TV_DISPLAY_MODE_AUTO");
      if ((this.m.getVideoWidth() > this.z) || (this.m.getVideoHeight() > this.A))
      {
        float f1 = this.m.getVideoWidth() / this.m.getVideoHeight();
        int i1 = this.z;
        int i2 = (int)(i1 / f1);
        int i3 = this.A;
        int i4 = (int)(f1 * i3);
        if ((i2 > this.A) && (i4 <= this.z))
        {
          this.B = ((this.z - i4) / 2);
          this.C = 0;
        }
        else if ((i4 > this.z) && (i2 <= this.A))
        {
          this.B = 0;
          this.C = ((this.A - i2) / 2);
        }
        else if ((i2 <= this.A) && (i4 <= this.z))
        {
          if (i1 * i2 > i3 * i4)
          {
            this.B = 0;
            this.C = ((this.A - i2) / 2);
          }
          else
          {
            this.B = ((this.z - i4) / 2);
            this.C = 0;
          }
        }
        else
        {
          this.B = 0;
          this.C = 0;
        }
      }
      else if (this.z / this.m.getVideoWidth() > this.A / this.m.getVideoHeight())
      {
        this.B = ((this.z - this.m.getVideoWidth() * this.A / this.m.getVideoHeight()) / 2);
        this.C = 0;
      }
      else
      {
        this.B = 0;
        this.C = ((this.A - this.m.getVideoHeight() * this.z / this.m.getVideoWidth()) / 2);
      }
    }
  }

  public int getSCurrentPosition()
  {
    return j();
  }

  public int getSDuration()
  {
    return i();
  }

  public int getSVideoHeight()
  {
    if (this.m != null)
      return this.m.getVideoHeight();
    return 0;
  }

  public int getSVideoWidth()
  {
    if (this.m != null)
      return this.m.getVideoWidth();
    return 0;
  }

  public SurfaceView getSurfaceView()
  {
    return this;
  }

  public boolean isSPlaying()
  {
    return k();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((l()) && (this.c != null) && ((paramInt == 66) || (paramInt == 23)))
      f();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void pauseSPlay()
  {
    h();
  }

  public void resumeSPlay()
  {
    g();
  }

  public void seekSPositionTo(int paramInt)
  {
    c(paramInt);
  }

  public void setDimension(int paramInt1, int paramInt2)
  {
    a(paramInt1, paramInt2);
    a(5);
  }

  public void setDimensionAuto()
  {
    b(2);
  }

  public void setDimensionFullScreen()
  {
    b(0);
  }

  public void setDimensionOriginal()
  {
    b(2);
  }

  public void setDimension_16_9()
  {
    b(0);
  }

  public void setDimension_4_3()
  {
    b(1);
  }

  public void setSMediaController(SohuMediaController paramSohuMediaController, ISohuMediaControllerCallback paramISohuMediaControllerCallback)
  {
    this.J = paramSohuMediaController;
    this.J.setMediaPlayer(paramISohuMediaControllerCallback);
    a(this.J);
  }

  public void setSVideoPath(String paramString)
  {
    a(paramString);
  }

  public void setmIPlayerCallback(PlayerCallback paramPlayerCallback)
  {
    this.v = ((ISkyworthPlayerCallback)paramPlayerCallback);
  }

  public void startSPlay()
  {
    g();
  }

  public void stopSPlay()
  {
    c();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.play.c
 * JD-Core Version:    0.6.2
 */