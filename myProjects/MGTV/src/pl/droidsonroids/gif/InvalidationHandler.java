package pl.droidsonroids.gif;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

class InvalidationHandler extends Handler
{
  private final WeakReference<GifDrawable> mDrawableRef;

  public InvalidationHandler(GifDrawable paramGifDrawable)
  {
    super(Looper.getMainLooper());
    this.mDrawableRef = new WeakReference(paramGifDrawable);
  }

  public void handleMessage(Message paramMessage)
  {
    GifDrawable localGifDrawable = (GifDrawable)this.mDrawableRef.get();
    if (localGifDrawable != null)
      localGifDrawable.invalidateSelf();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.InvalidationHandler
 * JD-Core Version:    0.6.2
 */