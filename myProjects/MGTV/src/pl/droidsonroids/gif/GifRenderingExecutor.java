package pl.droidsonroids.gif;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

final class GifRenderingExecutor extends ScheduledThreadPoolExecutor
{
  private static volatile GifRenderingExecutor instance = null;

  private GifRenderingExecutor()
  {
    super(1, new ThreadPoolExecutor.DiscardPolicy());
  }

  public static GifRenderingExecutor getInstance()
  {
    if (instance == null);
    try
    {
      if (instance == null)
        instance = new GifRenderingExecutor();
      return instance;
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifRenderingExecutor
 * JD-Core Version:    0.6.2
 */