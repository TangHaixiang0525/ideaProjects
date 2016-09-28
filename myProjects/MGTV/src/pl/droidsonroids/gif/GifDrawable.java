package pl.droidsonroids.gif;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.MediaController.MediaPlayerControl;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GifDrawable extends Drawable
  implements Animatable, MediaController.MediaPlayerControl
{
  final Bitmap mBuffer;
  private final Rect mDstRect = new Rect();
  final ScheduledThreadPoolExecutor mExecutor;
  final InvalidationHandler mInvalidationHandler;
  final boolean mIsRenderingTriggeredOnDraw;
  volatile boolean mIsRunning = true;
  final ConcurrentLinkedQueue<AnimationListener> mListeners = new ConcurrentLinkedQueue();
  final GifInfoHandle mNativeInfoHandle;
  long mNextFrameRenderTime = -9223372036854775808L;
  protected final Paint mPaint = new Paint(6);
  private final Runnable mRenderTask = new RenderTask(this);
  private final Rect mSrcRect;
  private ColorStateList mTint;
  private PorterDuffColorFilter mTintFilter;
  private PorterDuff.Mode mTintMode;

  public GifDrawable(@Nullable ContentResolver paramContentResolver, @NonNull Uri paramUri)
    throws IOException
  {
    this(GifInfoHandle.openUri(paramContentResolver, paramUri, false), null, null, true);
  }

  public GifDrawable(@NonNull AssetFileDescriptor paramAssetFileDescriptor)
    throws IOException
  {
    this(GifInfoHandle.openAssetFileDescriptor(paramAssetFileDescriptor, false), null, null, true);
  }

  public GifDrawable(@NonNull AssetManager paramAssetManager, @NonNull String paramString)
    throws IOException
  {
    this(paramAssetManager.openFd(paramString));
  }

  public GifDrawable(@NonNull Resources paramResources, int paramInt)
    throws Resources.NotFoundException, IOException
  {
    this(paramResources.openRawResourceFd(paramInt));
  }

  public GifDrawable(@NonNull File paramFile)
    throws IOException
  {
    this(GifInfoHandle.openFile(paramFile.getPath(), false), null, null, true);
  }

  public GifDrawable(@NonNull FileDescriptor paramFileDescriptor)
    throws IOException
  {
    this(GifInfoHandle.openFd(paramFileDescriptor, 0L, false), null, null, true);
  }

  public GifDrawable(@NonNull InputStream paramInputStream)
    throws IOException
  {
    this(GifInfoHandle.openMarkableInputStream(paramInputStream, false), null, null, true);
  }

  public GifDrawable(@NonNull String paramString)
    throws IOException
  {
    this(GifInfoHandle.openFile(paramString, false), null, null, true);
  }

  public GifDrawable(@NonNull ByteBuffer paramByteBuffer)
    throws IOException
  {
    this(GifInfoHandle.openDirectByteBuffer(paramByteBuffer, false), null, null, true);
  }

  GifDrawable(GifInfoHandle paramGifInfoHandle, GifDrawable paramGifDrawable, ScheduledThreadPoolExecutor paramScheduledThreadPoolExecutor, boolean paramBoolean)
  {
    this.mIsRenderingTriggeredOnDraw = paramBoolean;
    Bitmap localBitmap;
    if (paramScheduledThreadPoolExecutor != null)
    {
      this.mExecutor = paramScheduledThreadPoolExecutor;
      this.mNativeInfoHandle = paramGifInfoHandle;
      localBitmap = null;
      if (paramGifDrawable == null);
    }
    while (true)
    {
      synchronized (paramGifDrawable.mNativeInfoHandle)
      {
        boolean bool = paramGifDrawable.mNativeInfoHandle.isRecycled();
        localBitmap = null;
        if (!bool)
        {
          int i = paramGifDrawable.mNativeInfoHandle.height;
          int j = this.mNativeInfoHandle.height;
          localBitmap = null;
          if (i >= j)
          {
            int k = paramGifDrawable.mNativeInfoHandle.width;
            int m = this.mNativeInfoHandle.width;
            localBitmap = null;
            if (k >= m)
            {
              paramGifDrawable.shutdown();
              localBitmap = paramGifDrawable.mBuffer;
              localBitmap.eraseColor(0);
            }
          }
        }
        if (localBitmap == null)
        {
          this.mBuffer = Bitmap.createBitmap(this.mNativeInfoHandle.width, this.mNativeInfoHandle.height, Bitmap.Config.ARGB_8888);
          this.mSrcRect = new Rect(0, 0, this.mNativeInfoHandle.width, this.mNativeInfoHandle.height);
          this.mInvalidationHandler = new InvalidationHandler(this);
          if (!this.mIsRenderingTriggeredOnDraw)
            break;
          this.mNextFrameRenderTime = 0L;
          return;
          paramScheduledThreadPoolExecutor = GifRenderingExecutor.getInstance();
        }
      }
      this.mBuffer = localBitmap;
    }
    this.mExecutor.execute(this.mRenderTask);
  }

  public GifDrawable(@NonNull byte[] paramArrayOfByte)
    throws IOException
  {
    this(GifInfoHandle.openByteArray(paramArrayOfByte, false), null, null, true);
  }

  @Nullable
  public static GifDrawable createFromResource(@NonNull Resources paramResources, int paramInt)
  {
    try
    {
      GifDrawable localGifDrawable = new GifDrawable(paramResources, paramInt);
      return localGifDrawable;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private void shutdown()
  {
    this.mIsRunning = false;
    this.mInvalidationHandler.removeMessages(0);
    this.mNativeInfoHandle.recycle();
  }

  private PorterDuffColorFilter updateTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    if ((paramColorStateList == null) || (paramMode == null))
      return null;
    return new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
  }

  public void addAnimationListener(@NonNull AnimationListener paramAnimationListener)
  {
    this.mListeners.add(paramAnimationListener);
  }

  public boolean canPause()
  {
    return true;
  }

  public boolean canSeekBackward()
  {
    return getNumberOfFrames() > 1;
  }

  public boolean canSeekForward()
  {
    return getNumberOfFrames() > 1;
  }

  public void draw(Canvas paramCanvas)
  {
    int i;
    if ((this.mTintFilter != null) && (this.mPaint.getColorFilter() == null))
    {
      this.mPaint.setColorFilter(this.mTintFilter);
      i = 1;
      if (this.mPaint.getShader() != null)
        break label141;
      paramCanvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
    }
    while (true)
    {
      if (i != 0)
        this.mPaint.setColorFilter(null);
      if ((this.mIsRenderingTriggeredOnDraw) && (this.mIsRunning) && (this.mNextFrameRenderTime != -9223372036854775808L))
      {
        long l = Math.max(0L, this.mNextFrameRenderTime - SystemClock.uptimeMillis());
        this.mNextFrameRenderTime = -9223372036854775808L;
        this.mExecutor.schedule(this.mRenderTask, l, TimeUnit.MILLISECONDS);
      }
      return;
      i = 0;
      break;
      label141: paramCanvas.drawRect(this.mDstRect, this.mPaint);
    }
  }

  @TargetApi(19)
  public long getAllocationByteCount()
  {
    long l = this.mNativeInfoHandle.getAllocationByteCount();
    if (Build.VERSION.SDK_INT >= 19)
      return l + this.mBuffer.getAllocationByteCount();
    return l + this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
  }

  public int getAlpha()
  {
    return this.mPaint.getAlpha();
  }

  public int getAudioSessionId()
  {
    return 0;
  }

  public int getBufferPercentage()
  {
    return 100;
  }

  public ColorFilter getColorFilter()
  {
    return this.mPaint.getColorFilter();
  }

  @Nullable
  public String getComment()
  {
    return this.mNativeInfoHandle.getComment();
  }

  public Bitmap getCurrentFrame()
  {
    return this.mBuffer.copy(this.mBuffer.getConfig(), this.mBuffer.isMutable());
  }

  public int getCurrentFrameIndex()
  {
    return this.mNativeInfoHandle.getCurrentFrameIndex();
  }

  public int getCurrentLoop()
  {
    return this.mNativeInfoHandle.getCurrentLoop();
  }

  public int getCurrentPosition()
  {
    return this.mNativeInfoHandle.getCurrentPosition();
  }

  public int getDuration()
  {
    return this.mNativeInfoHandle.getDuration();
  }

  @NonNull
  public GifError getError()
  {
    return GifError.fromCode(this.mNativeInfoHandle.getNativeErrorCode());
  }

  public int getFrameByteCount()
  {
    return this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
  }

  public int getFrameDuration(int paramInt)
  {
    return this.mNativeInfoHandle.getFrameDuration(paramInt);
  }

  public long getInputSourceByteCount()
  {
    return this.mNativeInfoHandle.getSourceLength();
  }

  public int getIntrinsicHeight()
  {
    return this.mNativeInfoHandle.height;
  }

  public int getIntrinsicWidth()
  {
    return this.mNativeInfoHandle.width;
  }

  public int getLoopCount()
  {
    return this.mNativeInfoHandle.getLoopCount();
  }

  public int getMinimumHeight()
  {
    return this.mNativeInfoHandle.height;
  }

  public int getMinimumWidth()
  {
    return this.mNativeInfoHandle.width;
  }

  public int getNumberOfFrames()
  {
    return this.mNativeInfoHandle.frameCount;
  }

  public int getOpacity()
  {
    return -2;
  }

  @NonNull
  public final Paint getPaint()
  {
    return this.mPaint;
  }

  public int getPixel(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= this.mNativeInfoHandle.width)
      throw new IllegalArgumentException("x must be < width");
    if (paramInt2 >= this.mNativeInfoHandle.height)
      throw new IllegalArgumentException("y must be < height");
    return this.mBuffer.getPixel(paramInt1, paramInt2);
  }

  public void getPixels(@NonNull int[] paramArrayOfInt)
  {
    this.mBuffer.getPixels(paramArrayOfInt, 0, this.mNativeInfoHandle.width, 0, 0, this.mNativeInfoHandle.width, this.mNativeInfoHandle.height);
  }

  public boolean isAnimationCompleted()
  {
    return this.mNativeInfoHandle.isAnimationCompleted();
  }

  public boolean isPlaying()
  {
    return this.mIsRunning;
  }

  public boolean isRecycled()
  {
    return this.mNativeInfoHandle.isRecycled();
  }

  public boolean isRunning()
  {
    return this.mIsRunning;
  }

  public boolean isStateful()
  {
    return (super.isStateful()) || ((this.mTint != null) && (this.mTint.isStateful()));
  }

  protected void onBoundsChange(Rect paramRect)
  {
    this.mDstRect.set(paramRect);
  }

  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    if ((this.mTint != null) && (this.mTintMode != null))
    {
      this.mTintFilter = updateTintFilter(this.mTint, this.mTintMode);
      return true;
    }
    return false;
  }

  public void pause()
  {
    stop();
  }

  public void recycle()
  {
    shutdown();
    this.mBuffer.recycle();
  }

  public boolean removeAnimationListener(AnimationListener paramAnimationListener)
  {
    return this.mListeners.remove(paramAnimationListener);
  }

  public void reset()
  {
    this.mExecutor.execute(new SafeRunnable(this)
    {
      public void doWork()
      {
        if (GifDrawable.this.mNativeInfoHandle.reset())
          GifDrawable.this.start();
      }
    });
  }

  public void seekTo(final int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("Position is not positive");
    this.mExecutor.execute(new SafeRunnable(this)
    {
      public void doWork()
      {
        GifDrawable.this.mNativeInfoHandle.seekToTime(paramInt, GifDrawable.this.mBuffer);
        this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(0, 0L);
      }
    });
  }

  public void seekToFrame(final int paramInt)
  {
    if (paramInt < 0)
      throw new IndexOutOfBoundsException("Frame index is not positive");
    this.mExecutor.execute(new SafeRunnable(this)
    {
      public void doWork()
      {
        GifDrawable.this.mNativeInfoHandle.seekToFrame(paramInt, GifDrawable.this.mBuffer);
        GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(0, 0L);
      }
    });
  }

  public Bitmap seekToFrameAndGet(int paramInt)
  {
    if (paramInt < 0)
      throw new IndexOutOfBoundsException("Frame index is not positive");
    synchronized (this.mNativeInfoHandle)
    {
      this.mNativeInfoHandle.seekToFrame(paramInt, this.mBuffer);
      Bitmap localBitmap = getCurrentFrame();
      this.mInvalidationHandler.sendEmptyMessageAtTime(0, 0L);
      return localBitmap;
    }
  }

  public Bitmap seekToPositionAndGet(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("Position is not positive");
    synchronized (this.mNativeInfoHandle)
    {
      this.mNativeInfoHandle.seekToTime(paramInt, this.mBuffer);
      Bitmap localBitmap = getCurrentFrame();
      this.mInvalidationHandler.sendEmptyMessageAtTime(0, 0L);
      return localBitmap;
    }
  }

  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
  }

  public void setColorFilter(@Nullable ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
  }

  public void setDither(boolean paramBoolean)
  {
    this.mPaint.setDither(paramBoolean);
    invalidateSelf();
  }

  public void setFilterBitmap(boolean paramBoolean)
  {
    this.mPaint.setFilterBitmap(paramBoolean);
    invalidateSelf();
  }

  public void setLoopCount(int paramInt)
  {
    this.mNativeInfoHandle.setLoopCount(paramInt);
  }

  public void setSpeed(float paramFloat)
  {
    this.mNativeInfoHandle.setSpeedFactor(paramFloat);
  }

  public void setTintList(ColorStateList paramColorStateList)
  {
    this.mTint = paramColorStateList;
    this.mTintFilter = updateTintFilter(paramColorStateList, this.mTintMode);
    invalidateSelf();
  }

  public void setTintMode(PorterDuff.Mode paramMode)
  {
    this.mTintMode = paramMode;
    this.mTintFilter = updateTintFilter(this.mTint, paramMode);
    invalidateSelf();
  }

  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    if (paramBoolean1)
    {
      if (paramBoolean2)
        reset();
      if (bool)
        start();
    }
    while (!bool)
      return bool;
    stop();
    return bool;
  }

  public void start()
  {
    this.mIsRunning = true;
    startAnimation(this.mNativeInfoHandle.restoreRemainder());
  }

  void startAnimation(long paramLong)
  {
    if (paramLong >= 0L)
    {
      if (this.mIsRenderingTriggeredOnDraw)
      {
        this.mNextFrameRenderTime = 0L;
        this.mInvalidationHandler.sendEmptyMessageAtTime(0, 0L);
      }
    }
    else
      return;
    while (this.mExecutor.getQueue().remove(this.mRenderTask));
    this.mExecutor.schedule(this.mRenderTask, paramLong, TimeUnit.MILLISECONDS);
  }

  public void stop()
  {
    this.mIsRunning = false;
    this.mInvalidationHandler.removeMessages(0);
    while (this.mExecutor.getQueue().remove(this.mRenderTask));
    this.mNativeInfoHandle.saveRemainder();
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Integer.valueOf(this.mNativeInfoHandle.width);
    arrayOfObject[1] = Integer.valueOf(this.mNativeInfoHandle.height);
    arrayOfObject[2] = Integer.valueOf(this.mNativeInfoHandle.frameCount);
    arrayOfObject[3] = Integer.valueOf(this.mNativeInfoHandle.getNativeErrorCode());
    return String.format(localLocale, "GIF: size: %dx%d, frames: %d, error: %d", arrayOfObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifDrawable
 * JD-Core Version:    0.6.2
 */