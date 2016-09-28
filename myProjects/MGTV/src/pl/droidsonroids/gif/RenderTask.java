package pl.droidsonroids.gif;

import android.os.SystemClock;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class RenderTask extends SafeRunnable
{
  private final Runnable mNotifyListenersTask = new Runnable()
  {
    public void run()
    {
      Iterator localIterator = RenderTask.this.mGifDrawable.mListeners.iterator();
      while (localIterator.hasNext())
        ((AnimationListener)localIterator.next()).onAnimationCompleted();
    }
  };

  RenderTask(GifDrawable paramGifDrawable)
  {
    super(paramGifDrawable);
  }

  public void doWork()
  {
    long l = this.mGifDrawable.mNativeInfoHandle.renderFrame(this.mGifDrawable.mBuffer);
    if (l >= 0L)
    {
      this.mGifDrawable.mNextFrameRenderTime = (l + SystemClock.uptimeMillis());
      if ((this.mGifDrawable.isVisible()) && (this.mGifDrawable.mIsRunning) && (!this.mGifDrawable.mIsRenderingTriggeredOnDraw))
        this.mGifDrawable.mExecutor.schedule(this, l, TimeUnit.MILLISECONDS);
      if ((!this.mGifDrawable.mListeners.isEmpty()) && (this.mGifDrawable.getCurrentFrameIndex() == -1 + this.mGifDrawable.mNativeInfoHandle.frameCount))
        this.mGifDrawable.scheduleSelf(this.mNotifyListenersTask, this.mGifDrawable.mNextFrameRenderTime);
    }
    while (true)
    {
      if ((this.mGifDrawable.isVisible()) && (!this.mGifDrawable.mInvalidationHandler.hasMessages(0)))
        this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(0, 0L);
      return;
      this.mGifDrawable.mNextFrameRenderTime = -9223372036854775808L;
      this.mGifDrawable.mIsRunning = false;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.RenderTask
 * JD-Core Version:    0.6.2
 */