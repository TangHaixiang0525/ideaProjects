package com.starcor.media.player;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.starcor.core.domain.AdInfos;
import com.starcor.core.domain.ImageAd;
import com.starcor.core.domain.PlayInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.Video;
import com.starcor.core.domain.VideoAd;
import com.starcor.hunan.MplayerV2.IMplayerV2Listener;
import com.starcor.player.MediaPlayerAdapter;
import com.starcor.player.MediaPlayerAdapter.OnCompletionListener;
import com.starcor.player.MediaPlayerAdapter.OnErrorListener;
import com.starcor.player.MediaPlayerAdapter.OnPreparedListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MplayerADView extends RelativeLayout
{
  private final int MSG_AD_TIMER = 1;
  private final int MSG_BUFFERING = 2;
  private final int MSG_FINISH_BUFFERING = 3;
  private AdInfos adInfosBefore;
  private AdInfos adInfosPause;
  private int adTime;
  private boolean buffering = false;
  private HashMap<String, Drawable> cachedImage = new HashMap();
  private Timer checkBufferingTimer;
  private int currentPlayingVideoIndex = 0;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1)
      {
        MplayerADView.access$006(MplayerADView.this);
        MplayerADView.this.tvTimer.setText("广告时间：" + MplayerADView.this.adTime + "s");
        if (MplayerADView.this.adTime <= 0)
        {
          MplayerADView.this.mediaPlayerCore.stop();
          MplayerADView.this.stopTimer();
          MplayerADView.this.stopCheckBufferingTimer();
          if (MplayerADView.this.listener != null)
            MplayerADView.this.listener.onFinish();
        }
      }
      do
      {
        do
        {
          return;
          if (paramAnonymousMessage.what == 2)
          {
            System.out.println("buffering ");
            MplayerADView.access$602(MplayerADView.this, true);
            if (MplayerADView.this.lsnr != null)
            {
              MplayerADView.this.lsnr.onLoadingViewDisplay(0);
              return;
            }
          }
        }
        while (paramAnonymousMessage.what != 3);
        MplayerADView.access$602(MplayerADView.this, false);
        System.out.println("finish buffering ");
      }
      while (MplayerADView.this.lsnr == null);
      MplayerADView.this.lsnr.onLoadingViewDisplay(8);
    }
  };
  private ImageSlider imageSlider = null;
  private long lastPosition = 0L;
  private OnPlayAdFinishListener listener = null;
  private MplayerV2.IMplayerV2Listener lsnr;
  private MediaPlayerCore mediaPlayerCore;
  private ImageView pauseImage;
  private PlayerIntentParams playerIntentParams;
  private View rootView;
  private Timer timer;
  private TextView tvTimer;
  private List<String> urls = new ArrayList();

  public MplayerADView(Context paramContext, MplayerV2.IMplayerV2Listener paramIMplayerV2Listener, OnPlayAdFinishListener paramOnPlayAdFinishListener)
  {
    super(paramContext);
    this.listener = paramOnPlayAdFinishListener;
    this.lsnr = paramIMplayerV2Listener;
    initView();
  }

  private Drawable LoadImageFromWebOperations(String paramString)
  {
    try
    {
      Drawable localDrawable = Drawable.createFromStream((InputStream)new URL(paramString).getContent(), "src name");
      return localDrawable;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private void addUrl(String paramString)
  {
    try
    {
      this.urls.add(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void doGetPlayUrls(Video paramVideo, final boolean paramBoolean)
  {
    ServerApiManager.i().ApiRequestVideoPlay(paramVideo.getVideoId(), paramVideo.getVideoType(), "", "", 0, "", "", "", "", "", "", new SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        MplayerADView.this.playNextVideo();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, PlayInfo paramAnonymousPlayInfo)
      {
        if (paramBoolean)
          if (!TextUtils.isEmpty(paramAnonymousPlayInfo.playUrl))
          {
            MplayerADView.this.addUrl(paramAnonymousPlayInfo.playUrl.replaceAll("\\/", "/"));
            MplayerADView.this.startPlayBeforeAdVideo();
          }
        while (TextUtils.isEmpty(paramAnonymousPlayInfo.playUrl))
        {
          do
            return;
          while ((MplayerADView.this.getUrlsSize() != 0) || (MplayerADView.this.listener == null));
          MplayerADView.this.listener.onFinish();
          return;
        }
        MplayerADView.this.addUrl(paramAnonymousPlayInfo.playUrl.replaceAll("\\/", "/"));
      }
    });
  }

  private String getNextVideoUrl()
  {
    try
    {
      if (this.currentPlayingVideoIndex >= -1 + this.urls.size());
      for (this.currentPlayingVideoIndex = 0; ; this.currentPlayingVideoIndex = (1 + this.currentPlayingVideoIndex))
      {
        String str = (String)this.urls.get(this.currentPlayingVideoIndex);
        return str;
      }
    }
    finally
    {
    }
  }

  private void getPlayUrl()
  {
    int i = this.adInfosBefore.getVideoAdList().getVideos().size();
    int j = 0;
    if (j < i)
    {
      Video localVideo = (Video)this.adInfosBefore.getVideoAdList().getVideos().get(j);
      if (j == i - 1);
      for (boolean bool = true; ; bool = false)
      {
        doGetPlayUrls(localVideo, bool);
        j++;
        break;
      }
    }
  }

  private int getUrlsSize()
  {
    try
    {
      int i = this.urls.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void initView()
  {
    this.rootView = LayoutInflater.from(getContext()).inflate(2130903066, this);
    this.tvTimer = ((TextView)this.rootView.findViewById(2131165292));
    this.pauseImage = ((ImageView)this.rootView.findViewById(2131165293));
  }

  private void loadNetworkImage(final String paramString)
  {
    new AsyncTask()
    {
      protected Drawable doInBackground(String[] paramAnonymousArrayOfString)
      {
        if (paramAnonymousArrayOfString == null)
          return null;
        if (MplayerADView.this.cachedImage.get(paramAnonymousArrayOfString[0]) == null)
          return MplayerADView.this.LoadImageFromWebOperations(paramAnonymousArrayOfString[0]);
        return (Drawable)MplayerADView.this.cachedImage.get(paramAnonymousArrayOfString[0]);
      }

      protected void onPostExecute(Drawable paramAnonymousDrawable)
      {
        if (paramAnonymousDrawable != null)
        {
          MplayerADView.this.cachedImage.put(paramString, paramAnonymousDrawable);
          MplayerADView.this.pauseImage.setImageDrawable(paramAnonymousDrawable);
        }
      }
    }
    .execute(new String[] { paramString });
  }

  private void playNextVideo()
  {
    stopTimer();
    stopCheckBufferingTimer();
    this.lsnr.onLoadingViewDisplay(0);
    this.lsnr.onSpeedTextDisplay(4, "");
    String str = getNextVideoUrl();
    this.mediaPlayerCore.stop();
    this.mediaPlayerCore.reset();
    this.mediaPlayerCore.setVideoURI(str);
  }

  private void startCheckBufferingTimer()
  {
    stopCheckBufferingTimer();
    this.checkBufferingTimer = new Timer();
    this.checkBufferingTimer.schedule(new TimerTask()
    {
      public void run()
      {
        int i = MplayerADView.this.mediaPlayerCore.getCurrentPosition();
        if (MplayerADView.this.lastPosition == i)
          MplayerADView.this.handler.obtainMessage(2).sendToTarget();
        while (true)
        {
          MplayerADView.access$2102(MplayerADView.this, i);
          return;
          MplayerADView.this.handler.obtainMessage(3).sendToTarget();
        }
      }
    }
    , 0L, 1000L);
  }

  private void startPlayBeforeAdVideo()
  {
    playNextVideo();
  }

  private void startTimer()
  {
    if (this.timer != null)
    {
      this.timer.cancel();
      this.timer = null;
    }
    this.timer = new Timer();
    this.timer.schedule(new TimerTask()
    {
      public void run()
      {
        if (MplayerADView.this.buffering)
          return;
        MplayerADView.this.handler.sendEmptyMessage(1);
      }
    }
    , 0L, 1000L);
    this.tvTimer.setVisibility(0);
    setVisibility(0);
  }

  private void stopCheckBufferingTimer()
  {
    if (this.checkBufferingTimer != null)
    {
      this.checkBufferingTimer.cancel();
      this.checkBufferingTimer = null;
    }
  }

  private void stopTimer()
  {
    if (this.timer != null)
    {
      this.timer.cancel();
      this.timer = null;
    }
    this.tvTimer.setVisibility(8);
    setVisibility(8);
  }

  public void bindMediaPlayerCore(final MediaPlayerCore paramMediaPlayerCore)
  {
    this.mediaPlayerCore = paramMediaPlayerCore;
    this.mediaPlayerCore.setOnPreparedListener(new MediaPlayerAdapter.OnPreparedListener()
    {
      public void onPrepared(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
      {
        MplayerADView.this.startTimer();
        MplayerADView.this.startCheckBufferingTimer();
        MplayerADView.this.lsnr.onLoadingViewDisplay(4);
        MplayerADView.this.lsnr.onSpeedTextDisplay(4, "");
        paramMediaPlayerCore.start();
      }
    });
    this.mediaPlayerCore.setOnErrorListener(new MediaPlayerAdapter.OnErrorListener()
    {
      public boolean onError(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        MplayerADView.this.playNextVideo();
        return false;
      }
    });
    this.mediaPlayerCore.setOnCompletionListener(new MediaPlayerAdapter.OnCompletionListener()
    {
      public void onCompletion(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
      {
        MplayerADView.this.playNextVideo();
      }
    });
  }

  public void bindPlayInfo(PlayerIntentParams paramPlayerIntentParams)
  {
    this.playerIntentParams = paramPlayerIntentParams;
  }

  public void dismissPuaseImage()
  {
    this.pauseImage.setVisibility(8);
    setVisibility(8);
    if (this.imageSlider != null)
      this.imageSlider.stopSlide();
  }

  public void playStartAd()
  {
    if (this.adInfosBefore == null)
      throw new NullPointerException("you should call setAdInfosBefore() to set value first");
    getPlayUrl();
  }

  public void setPauseAdInfo(AdInfos paramAdInfos)
  {
    this.adInfosPause = paramAdInfos;
  }

  public void setStartAdPlayInfo(AdInfos paramAdInfos, int paramInt)
  {
    this.adInfosBefore = paramAdInfos;
    this.adTime = paramInt;
  }

  public void showPauseImage()
  {
    setVisibility(0);
    this.pauseImage.setVisibility(0);
    postDelayed(new Runnable()
    {
      public void run()
      {
        ImageAd localImageAd;
        if (MplayerADView.this.adInfosPause != null)
        {
          localImageAd = MplayerADView.this.adInfosPause.getIamgeAdList();
          if (localImageAd != null)
            break label26;
        }
        label26: ArrayList localArrayList;
        do
        {
          do
          {
            return;
            if (!localImageAd.getAction().equals("static"))
              break;
          }
          while ((localImageAd.getImageUrls() == null) || (localImageAd.getImageUrls().size() <= 0));
          MplayerADView.this.loadNetworkImage((String)localImageAd.getImageUrls().get(0));
          return;
          localArrayList = localImageAd.getImageUrls();
        }
        while (localArrayList == null);
        if (MplayerADView.this.imageSlider == null)
          MplayerADView.access$1702(MplayerADView.this, new MplayerADView.ImageSlider(MplayerADView.this, localArrayList, localImageAd.getInterval(), MplayerADView.this.pauseImage));
        MplayerADView.this.imageSlider.startSlide();
      }
    }
    , 2000L);
  }

  private class ImageSlider
  {
    private int delayTime;
    private Handler handler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (MplayerADView.ImageSlider.this.imageView != null)
          MplayerADView.ImageSlider.this.showImage((String)MplayerADView.ImageSlider.this.urls.get(MplayerADView.ImageSlider.this.index));
      }
    };
    private ImageView imageView;
    private int index = -1;
    private Timer timer;
    private List<String> urls;

    public ImageSlider(int paramImageView, ImageView arg3)
    {
      this.urls = paramImageView;
      int i;
      this.delayTime = i;
      Object localObject;
      this.imageView = localObject;
    }

    private void showImage(final String paramString)
    {
      new AsyncTask()
      {
        protected Drawable doInBackground(String[] paramAnonymousArrayOfString)
        {
          if (paramAnonymousArrayOfString == null)
            return null;
          if (MplayerADView.this.cachedImage.get(paramAnonymousArrayOfString[0]) == null)
            return MplayerADView.this.LoadImageFromWebOperations(paramAnonymousArrayOfString[0]);
          return (Drawable)MplayerADView.this.cachedImage.get(paramAnonymousArrayOfString[0]);
        }

        protected void onPostExecute(Drawable paramAnonymousDrawable)
        {
          if (paramAnonymousDrawable != null)
          {
            MplayerADView.this.cachedImage.put(paramString, paramAnonymousDrawable);
            MplayerADView.this.pauseImage.setImageDrawable(paramAnonymousDrawable);
          }
        }
      }
      .execute(new String[] { paramString });
    }

    private void startTimer()
    {
      if (this.timer == null)
        this.timer = new Timer();
      this.timer.schedule(new TimerTask()
      {
        public void run()
        {
          MplayerADView.ImageSlider.access$2304(MplayerADView.ImageSlider.this);
          if (MplayerADView.ImageSlider.this.index > -1 + MplayerADView.ImageSlider.this.urls.size())
            MplayerADView.ImageSlider.access$2302(MplayerADView.ImageSlider.this, 0);
          MplayerADView.ImageSlider.this.handler.sendEmptyMessage(0);
        }
      }
      , 0L, 1000 * this.delayTime);
    }

    public void startSlide()
    {
      startTimer();
    }

    public void stopSlide()
    {
      if (this.timer != null)
      {
        this.timer.cancel();
        this.timer = null;
      }
    }
  }

  public static abstract interface OnPlayAdFinishListener
  {
    public abstract void onFinish();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerADView
 * JD-Core Version:    0.6.2
 */