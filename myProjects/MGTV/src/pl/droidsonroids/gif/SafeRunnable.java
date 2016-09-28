package pl.droidsonroids.gif;

abstract class SafeRunnable
  implements Runnable
{
  final GifDrawable mGifDrawable;

  SafeRunnable(GifDrawable paramGifDrawable)
  {
    this.mGifDrawable = paramGifDrawable;
  }

  protected abstract void doWork();

  public final void run()
  {
    try
    {
      if (!this.mGifDrawable.isRecycled())
        doWork();
      return;
    }
    catch (Throwable localThrowable)
    {
      Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
      if (localUncaughtExceptionHandler != null)
        localUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), localThrowable);
      throw localThrowable;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.SafeRunnable
 * JD-Core Version:    0.6.2
 */