package com.sohutv.tv.player.play;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import com.sohu.logger.log.OutputLog;
import com.sohutv.tv.player.ad.SohuTVAdvertise;
import com.sohutv.tv.player.ad.SohuTVAdvertise.PlaybackState;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback;
import com.sohutv.tv.player.interfaces.ISohuTVPlayerCallback;
import com.sohutv.tv.player.interfaces.IVideoListener;
import com.sohutv.tv.player.interfaces.PlayerCallback;
import java.io.IOException;
import java.util.Map;

public final class b extends a
{
  public static int b = 0;
  private int A = 0;
  private final MediaPlayer.OnCompletionListener B = new MediaPlayer.OnCompletionListener()
  {
    public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
    {
      Log.i("Sohuplayer", "onCompletion 回调");
      if (b.this.a != null)
        b.this.a.onVideoCompletion();
      while (true)
      {
        b.b = 5;
        b.e(b.this, 5);
        if (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO)
        {
          b.a(b.this, false);
          if (b.d(b.this) == null)
            break;
          Log.i("Sohuplayer", "正片 onCompletion");
          b.d(b.this).onCompletion(paramAnonymousMediaPlayer);
        }
        return;
        Log.e("Sohuplayer", "onCompletion mIVideoListener is null");
      }
      Log.e("Sohuplayer", "onCompletion mIPlayerCallback is null");
    }
  };
  private final MediaPlayer.OnErrorListener C = new MediaPlayer.OnErrorListener()
  {
    public boolean onError(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      OutputLog.d("AndroidVideoView", "Error: " + paramAnonymousInt1 + "," + paramAnonymousInt2);
      b.b = -1;
      b.e(b.this, -1);
      if (b.d(b.this) != null)
        b.d(b.this).onError(paramAnonymousMediaPlayer, paramAnonymousInt1, paramAnonymousInt2);
      while (true)
      {
        return true;
        Log.e("Sohuplayer", "onError mIPlayerCallback is null");
      }
    }
  };
  private final MediaPlayer.OnInfoListener D = new MediaPlayer.OnInfoListener()
  {
    public boolean onInfo(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (b.d(b.this) == null)
        return false;
      return b.d(b.this).onInfo(paramAnonymousMediaPlayer, paramAnonymousInt1, paramAnonymousInt2);
    }
  };
  private final MediaPlayer.OnBufferingUpdateListener E = new MediaPlayer.OnBufferingUpdateListener()
  {
    public void onBufferingUpdate(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt)
    {
      b.f(b.this, paramAnonymousInt);
      if (b.d(b.this) != null)
        b.d(b.this).onBufferingUpdate(paramAnonymousMediaPlayer, paramAnonymousInt);
    }
  };
  private final MediaPlayer.OnSeekCompleteListener F = new MediaPlayer.OnSeekCompleteListener()
  {
    public void onSeekComplete(MediaPlayer paramAnonymousMediaPlayer)
    {
      if (b.d(b.this) != null)
      {
        b.d(b.this).OnSeekCompleteListener();
        return;
      }
      Log.e("Sohuplayer", "onSeekComplete mIPlayerCallback is null");
    }
  };
  private SohuMediaController G;
  int c = 0;
  int d = 0;
  int e = 1;
  int f = 2;
  MediaPlayer.OnVideoSizeChangedListener g = new MediaPlayer.OnVideoSizeChangedListener()
  {
    public void onVideoSizeChanged(MediaPlayer paramAnonymousMediaPlayer, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      b.a(b.this, paramAnonymousMediaPlayer.getVideoWidth());
      b.b(b.this, paramAnonymousMediaPlayer.getVideoHeight());
      OutputLog.i("@@@@@@@@@@", "onVideoSizeChanged video size: " + b.a(b.this) + 'x' + b.b(b.this));
      if ((b.a(b.this) != 0) && (b.b(b.this) != 0))
        ((Activity)b.c(b.this)).runOnUiThread(new Runnable()
        {
          public void run()
          {
            b.this.getHolder().setFixedSize(b.a(b.this), b.b(b.this));
          }
        });
      if (b.d(b.this) != null)
        b.d(b.this).onVideoSizeChanged(paramAnonymousMediaPlayer, paramAnonymousInt1, paramAnonymousInt2);
    }
  };
  MediaPlayer.OnPreparedListener h = new MediaPlayer.OnPreparedListener()
  {
    public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
    {
      b.b = 2;
      b.a(b.this, paramAnonymousMediaPlayer.getVideoWidth());
      b.b(b.this, paramAnonymousMediaPlayer.getVideoHeight());
      OutputLog.i("@@@@@@@@@@", "video size: " + b.a(b.this) + "/" + b.b(b.this));
      if ((b.a(b.this) != 0) && (b.b(b.this) != 0))
      {
        OutputLog.i("@@@@@@@@@@", "Surface size: " + b.e(b.this) + "/" + b.f(b.this));
        b.this.getHolder().setFixedSize(b.a(b.this), b.b(b.this));
        if ((b.e(b.this) == b.a(b.this)) && (b.f(b.this) == b.b(b.this)))
        {
          OutputLog.i("@@@@@@@@@@", "mTargetState: " + b.g(b.this));
          if (b.g(b.this) != 3);
        }
        if (b.this.a == null)
          break label318;
        b.this.a.onVideoPrepared();
        label261: if (SohuTVAdvertise.b != SohuTVAdvertise.PlaybackState.INVIDEO)
          break label329;
        b.c(b.this, com.sohutv.tv.player.util.a.a.c);
      }
      while (true)
      {
        if (b.d(b.this) == null)
          break label351;
        b.d(b.this).onPrepared(paramAnonymousMediaPlayer);
        return;
        if (b.g(b.this) != 3)
          break;
        break;
        label318: Log.e("Sohuplayer", "onPrepared mIVideoListener is null");
        break label261;
        label329: if (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.ADPLAYING)
          b.d(b.this, com.sohutv.tv.player.util.a.a.d);
      }
      label351: Log.e("Sohuplayer", "onPrepared mIPlayerCallback is null");
    }
  };
  SurfaceHolder.Callback i = new SurfaceHolder.Callback()
  {
    public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      b.g(b.this, paramAnonymousInt2);
      b.h(b.this, paramAnonymousInt3);
      Log.e("lifeCycle", "SohuTVVideoView surfaceChanged " + b.e(b.this) + "x" + b.f(b.this));
      if ((b.h(b.this) != null) && (paramAnonymousSurfaceHolder != null) && (paramAnonymousSurfaceHolder.getSurface().isValid()));
      try
      {
        Log.e("lifeCycle", "setDisplay");
        b.h(b.this).setDisplay(paramAnonymousSurfaceHolder);
        if (b.d(b.this) != null)
          b.d(b.this).surfaceChanged(paramAnonymousSurfaceHolder, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
    {
      Log.e("lifeCycle", "surfaceCreated");
      b.a(b.this, paramAnonymousSurfaceHolder);
      b.i(b.this);
      if (b.d(b.this) != null)
        b.d(b.this).surfaceCreated(paramAnonymousSurfaceHolder);
    }

    public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
    {
      Log.e("lifeCycle", "surfaceDestroyed");
      b.a(b.this, null);
      if ((b.h(b.this) != null) && (b.j(b.this)));
      try
      {
        b.b(b.this, true);
        b.h(b.this).stop();
        if (b.d(b.this) != null)
          b.d(b.this).surfaceDestroyed(paramAnonymousSurfaceHolder);
        if (b.this.a != null)
        {
          b.this.a.onVideoStop();
          b.this.a(true);
          return;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          continue;
          Log.e("Sohuplayer", "surfaceDestroyed mIVideoListener is null");
        }
      }
    }
  };
  private final String j = "AndroidVideoView";
  private Uri k;
  private int l = 0;
  private SurfaceHolder m = null;
  private MediaPlayer n = null;
  private int o;
  private int p;
  private int q;
  private int r;
  private int s;
  private Context t;
  private boolean u = false;
  private ISohuTVPlayerCallback v;
  private boolean w = false;
  private int x = 2;
  private int y;
  private int z;

  public b(Context paramContext)
  {
    super(paramContext);
    this.t = paramContext;
    c();
  }

  private void a(Uri paramUri)
  {
    a(paramUri, null);
  }

  private void a(Uri paramUri, Map<String, String> paramMap)
  {
    this.k = paramUri;
    e();
    ((Activity)this.t).runOnUiThread(new Runnable()
    {
      public void run()
      {
        b.this.requestLayout();
        b.this.invalidate();
      }
    });
  }

  private void a(SohuMediaController paramSohuMediaController)
  {
  }

  private void a(String paramString)
  {
    a(Uri.parse(paramString));
  }

  private void c()
  {
    Log.i("Sohuplayer", "init android videoview");
    this.o = 0;
    this.p = 0;
    getHolder().addCallback(this.i);
    getHolder().setType(3);
    b = 0;
    this.l = 0;
    Display localDisplay = ((WindowManager)this.t.getSystemService("window")).getDefaultDisplay();
    this.d = localDisplay.getHeight();
    this.c = localDisplay.getWidth();
    if ((this.d >= 672) && (this.d < 720))
      this.d = 720;
  }

  private void c(int paramInt)
  {
    if ((l()) && (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO))
    {
      OutputLog.i("pos2 = " + paramInt);
      try
      {
        this.n.seekTo(paramInt);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    }
    OutputLog.e("not in PlaybackState or not INVIDEO");
  }

  private void c(boolean paramBoolean)
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

  private void d()
  {
    OutputLog.i("Sohuplayer", "stopPlayback method");
    if (this.u)
      this.u = false;
    if (this.n != null)
      OutputLog.i("Sohuplayer", "stopPlayback method, MediaPlayer is not null");
    try
    {
      c(true);
      this.n.stop();
      this.n.release();
      this.n = null;
      b = 0;
      this.l = 0;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void d(int paramInt)
  {
    if ((l()) && (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.ADPLAYING))
    {
      OutputLog.i("pos2 = " + paramInt);
      try
      {
        this.n.seekTo(paramInt);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    }
    OutputLog.e("not in PlaybackState or not ADPLAYING");
  }

  private void e()
  {
    if ((this.k == null) || (this.m == null));
    while (true)
    {
      return;
      this.A = 1;
      Intent localIntent = new Intent("com.android.music.musicservicecommand");
      localIntent.putExtra("command", "pause");
      this.t.sendBroadcast(localIntent);
      a(false);
      try
      {
        this.n = new MediaPlayer();
        this.n.setOnPreparedListener(this.h);
        this.n.setOnVideoSizeChangedListener(this.g);
        this.n.setOnCompletionListener(this.B);
        this.n.setOnErrorListener(this.C);
        this.n.setOnInfoListener(this.D);
        this.n.setOnSeekCompleteListener(this.F);
        this.n.setOnBufferingUpdateListener(this.E);
        this.s = 0;
        this.n.setDataSource(this.t, this.k);
        this.n.setDisplay(this.m);
        this.n.setAudioStreamType(3);
        this.n.setScreenOnWhilePlaying(true);
        this.n.prepareAsync();
        b = 1;
        a();
        return;
      }
      catch (IOException localIOException)
      {
        OutputLog.w("AndroidVideoView", "Unable to open content: " + this.k, localIOException);
        b = -1;
        this.l = -1;
        this.C.onError(this.n, 1, 0);
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        OutputLog.w("AndroidVideoView", "Unable to open content: " + this.k, localIllegalArgumentException);
        b = -1;
        this.l = -1;
        this.C.onError(this.n, 1, 0);
        return;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
        b = -1;
        this.l = -1;
        this.C.onError(this.n, 1, 0);
        if (this.e < this.f)
        {
          Log.i("sohu_videoview", "尝试重新启动mediaplayer 1 次");
          this.e = (1 + this.e);
          e();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        b = -1;
        this.l = -1;
        this.C.onError(this.n, 1, 0);
      }
    }
  }

  private void f()
  {
  }

  private void g()
  {
    OutputLog.i("Sohuplayer", "SohuTVVideoView start method");
    if (l())
      OutputLog.i("Sohuplayer", "SohuTVVideoView start method, mCurrentState = " + b);
    try
    {
      this.n.start();
      b = 3;
      this.u = true;
      this.l = 3;
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        localIllegalStateException.printStackTrace();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void h()
  {
    OutputLog.d("test", "SohuTVVideoView pause method");
    if ((l()) && (k()));
    try
    {
      this.n.pause();
      b = 4;
      this.l = 4;
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        localIllegalStateException.printStackTrace();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private int i()
  {
    boolean bool = l();
    int i1 = 0;
    if (bool);
    try
    {
      int i2 = this.n.getDuration();
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
      int i2 = this.n.getCurrentPosition();
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
        boolean bool3 = this.n.isPlaying();
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
      if ((this.n != null) && (b != -1) && (b != 0))
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

  protected void a()
  {
    f();
  }

  public void a(int paramInt)
  {
    this.x = paramInt;
    b(paramInt);
  }

  public void a(int paramInt1, int paramInt2)
  {
    this.y = paramInt1;
    this.z = paramInt2;
  }

  public void a(boolean paramBoolean)
  {
    if (this.n != null);
    try
    {
      this.n.reset();
      this.n.release();
      this.n = null;
      b = 0;
      if (paramBoolean)
        this.l = 0;
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
    int i1 = getSVideoWidth();
    int i2 = getSVideoHeight();
    Log.d("AndroidVideoView", "====================  changeSurfaceSize: ar=" + paramInt + " " + i1 + "x" + i2);
    if ((i1 <= 0) || (i2 <= 0))
      return;
    int i3 = this.c;
    int i4 = this.d;
    int i5;
    int i6;
    label163: ViewGroup.LayoutParams localLayoutParams;
    switch (paramInt)
    {
    default:
      return;
    case 1:
      i2 = i4;
      i1 = i3;
      i5 = -1;
      i6 = -1;
      if ((i6 > 0) && (i5 > 0))
      {
        if (i1 / i2 > i6 / i5)
          i1 = i2 * i6 / i5;
      }
      else
      {
        Log.d("AndroidVideoView", "====================  changeSurfaceParams: " + i1 + "x" + i2);
        localLayoutParams = getLayoutParams();
        if (paramInt != 1)
          break label323;
        localLayoutParams.width = -1;
        localLayoutParams.height = -1;
      }
      break;
    case 6:
    case 4:
    case 3:
    case 2:
    case 5:
    }
    while (true)
    {
      setLayoutParams(localLayoutParams);
      invalidate();
      return;
      int i7 = i2;
      i2 = i4;
      i5 = i7;
      int i8 = i1;
      i1 = i3;
      i6 = i8;
      break;
      i2 = i4;
      i5 = 3;
      i1 = i3;
      i6 = 4;
      break;
      i2 = i4;
      i5 = 9;
      i1 = i3;
      i6 = 16;
      break;
      i5 = -1;
      i6 = -1;
      break;
      i2 = i4;
      i1 = i3;
      i5 = -1;
      i6 = -1;
      break;
      i2 = i1 * i5 / i6;
      break label163;
      label323: if (paramInt == 5)
      {
        localLayoutParams.width = this.y;
        localLayoutParams.height = this.z;
      }
      else
      {
        localLayoutParams.width = i1;
        localLayoutParams.height = i2;
      }
    }
  }

  public void b(boolean paramBoolean)
  {
    this.w = paramBoolean;
  }

  public boolean b()
  {
    return this.w;
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
    try
    {
      MediaPlayer localMediaPlayer = this.n;
      int i1 = 0;
      if (localMediaPlayer != null)
      {
        boolean bool = l();
        i1 = 0;
        if (bool)
        {
          int i2 = this.n.getVideoHeight();
          i1 = i2;
        }
      }
      return i1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public int getSVideoWidth()
  {
    try
    {
      MediaPlayer localMediaPlayer = this.n;
      int i1 = 0;
      if (localMediaPlayer != null)
      {
        boolean bool = l();
        i1 = 0;
        if (bool)
        {
          int i2 = this.n.getVideoWidth();
          i1 = i2;
        }
      }
      return i1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
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

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Log.i("lifeCycle", "onAttachedToWindow");
  }

  protected void onDetachedFromWindow()
  {
    if (!this.w)
      super.onDetachedFromWindow();
    Log.i("lifeCycle", "onDetachedFromWindow");
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onWindowVisibilityChanged(int paramInt)
  {
    if (!this.w)
      super.onWindowVisibilityChanged(paramInt);
    Log.i("lifeCycle", "onWindowVisibilityChanged:" + paramInt);
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

  public void selectTrack(int paramInt)
  {
    try
    {
      if ((this.n != null) && (l()))
        this.n.selectTrack(paramInt);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setDimension(int paramInt1, int paramInt2)
  {
    a(paramInt1, paramInt2);
    a(5);
  }

  public void setDimensionAuto()
  {
    a(6);
  }

  public void setDimensionFullScreen()
  {
    a(1);
  }

  public void setDimensionOriginal()
  {
    a(2);
  }

  public void setDimension_16_9()
  {
    a(3);
  }

  public void setDimension_4_3()
  {
    a(4);
  }

  public void setSMediaController(SohuMediaController paramSohuMediaController, ISohuMediaControllerCallback paramISohuMediaControllerCallback)
  {
    this.G = paramSohuMediaController;
    this.G.setMediaPlayer(paramISohuMediaControllerCallback);
    a(this.G);
  }

  public void setSVideoPath(String paramString)
  {
    a(paramString);
  }

  public void setmIPlayerCallback(PlayerCallback paramPlayerCallback)
  {
    this.v = ((ISohuTVPlayerCallback)paramPlayerCallback);
  }

  public void startSPlay()
  {
    g();
  }

  public void stopSPlay()
  {
    d();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.play.b
 * JD-Core Version:    0.6.2
 */