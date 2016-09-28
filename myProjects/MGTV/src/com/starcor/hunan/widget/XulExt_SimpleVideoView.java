package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.hunan.App;
import com.starcor.media.player.MediaPlayerCore;
import com.starcor.player.MediaPlayerAdapter;
import com.starcor.player.MediaPlayerAdapter.OnCompletionListener;
import com.starcor.player.MediaPlayerAdapter.OnErrorListener;
import com.starcor.player.MediaPlayerAdapter.OnPreparedListener;
import com.starcor.xul.IXulExternalView;

public class XulExt_SimpleVideoView extends RelativeLayout
  implements IXulExternalView
{
  private Context context;
  private boolean isFullScreen;
  private Rect originalRect;
  private MediaPlayerCore playerCore;
  private PlayerEvent playerEvent;

  public XulExt_SimpleVideoView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initViews();
  }

  private void initViews()
  {
    this.playerCore = new MediaPlayerCore(this.context);
    this.playerCore.setOnPreparedListener(new MediaPlayerAdapter.OnPreparedListener()
    {
      public void onPrepared(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
      {
        XulExt_SimpleVideoView.this.playerCore.start();
        if (XulExt_SimpleVideoView.this.playerEvent != null)
          XulExt_SimpleVideoView.this.playerEvent.onPrepared();
      }
    });
    this.playerCore.setOnCompletionListener(new MediaPlayerAdapter.OnCompletionListener()
    {
      public void onCompletion(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
      {
        if (XulExt_SimpleVideoView.this.playerEvent != null)
          XulExt_SimpleVideoView.this.playerEvent.onCompletion();
      }
    });
    this.playerCore.setOnErrorListener(new MediaPlayerAdapter.OnErrorListener()
    {
      public boolean onError(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (XulExt_SimpleVideoView.this.playerEvent != null)
          XulExt_SimpleVideoView.this.playerEvent.onError();
        return true;
      }
    });
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    addView(this.playerCore, localLayoutParams);
  }

  public void destroy()
  {
  }

  public void extDestroy()
  {
    destroy();
  }

  public void extHide()
  {
    setVisibility(8);
  }

  public void extMoveTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.leftMargin = paramInt1;
    localLayoutParams.topMargin = paramInt2;
    localLayoutParams.width = paramInt3;
    localLayoutParams.height = paramInt4;
    requestLayout();
  }

  public void extMoveTo(Rect paramRect)
  {
    this.originalRect = new Rect(paramRect);
    extMoveTo(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
  }

  public void extOnBlur()
  {
    clearFocus();
  }

  public void extOnFocus()
  {
    requestFocus();
  }

  public boolean extOnKeyEvent(KeyEvent paramKeyEvent)
  {
    return dispatchKeyEvent(paramKeyEvent);
  }

  public void extShow()
  {
    setVisibility(0);
  }

  public void extSyncData()
  {
  }

  public void fullScreen()
  {
    this.isFullScreen = true;
    extMoveTo(0, 0, App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
  }

  public String getAttr(String paramString1, String paramString2)
  {
    return paramString2;
  }

  public boolean isComeToEnd()
  {
    if (this.playerCore.isInPlayState())
    {
      int i = this.playerCore.getPlayState();
      if ((i != 5) && (10000 + this.playerCore.getCurrentPosition() > this.playerCore.getDuration()))
        return true;
    }
    return false;
  }

  public boolean isFullScreen()
  {
    return this.isFullScreen;
  }

  public void restoreSize()
  {
    if (this.originalRect != null)
    {
      this.isFullScreen = false;
      extMoveTo(this.originalRect.left, this.originalRect.top, this.originalRect.width(), this.originalRect.height());
    }
  }

  public boolean setAttr(String paramString1, String paramString2)
  {
    return false;
  }

  public void setOnPlayerEventListener(PlayerEvent paramPlayerEvent)
  {
    this.playerEvent = paramPlayerEvent;
  }

  public void setUrl(String paramString)
  {
    this.playerCore.setVideoURI(paramString);
  }

  public void stopMedia()
  {
    this.playerCore.stop();
  }

  public static abstract interface PlayerEvent
  {
    public abstract void onCompletion();

    public abstract void onError();

    public abstract void onPrepared();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.XulExt_SimpleVideoView
 * JD-Core Version:    0.6.2
 */