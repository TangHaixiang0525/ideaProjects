package com.starcor.core.http;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;

public class BitmapCache
{
  private static BitmapCache mBitmapCache = null;
  private Context mContext;
  private LruCache<String, Bitmap> mMemoryCache;

  private BitmapCache(Context paramContext)
  {
    this.mContext = paramContext;
    int i = 1048576 * ((ActivityManager)this.mContext.getSystemService("activity")).getMemoryClass() / 2;
    Logger.d("CacheSize" + i / 1024 + "K");
    this.mMemoryCache = new LruCache(i)
    {
      protected int sizeOf(String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        return paramAnonymousBitmap.getRowBytes() * paramAnonymousBitmap.getHeight();
      }
    };
  }

  private Bitmap getBitmapFromMemCache(String paramString)
  {
    return (Bitmap)this.mMemoryCache.get(paramString);
  }

  public static BitmapCache getInstance(Context paramContext)
  {
    try
    {
      if (mBitmapCache == null)
        mBitmapCache = new BitmapCache(paramContext.getApplicationContext());
      BitmapCache localBitmapCache = mBitmapCache;
      return localBitmapCache;
    }
    finally
    {
    }
  }

  public void addBitmapToMemoryCache(String paramString, Bitmap paramBitmap)
  {
    if ((getBitmapFromMemCache(paramString) == null) && (paramBitmap != null))
      this.mMemoryCache.put(paramString, paramBitmap);
  }

  public Bitmap getBitmapFromCache(String paramString)
  {
    Bitmap localBitmap;
    if (TextUtils.isEmpty(paramString))
      localBitmap = null;
    while (true)
    {
      return localBitmap;
      localBitmap = getBitmapFromMemCache(paramString);
      if (localBitmap == null)
        try
        {
          localBitmap = BitmapService.getInstance(this.mContext).getBitmap(paramString);
          if (localBitmap != null)
          {
            addBitmapToMemoryCache(paramString, localBitmap);
            return localBitmap;
          }
        }
        catch (Exception localException)
        {
          Logger.d("读取图片失败，图片文件名称：" + paramString);
        }
    }
    return localBitmap;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.http.BitmapCache
 * JD-Core Version:    0.6.2
 */