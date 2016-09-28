package com.starcor.core.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.ei.libs.bitmap.EIBitmapUtil;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.image.BitmapTools;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;

public final class BitmapService
{
  private static final String TAG = BitmapService.class.getSimpleName();
  private static BitmapService mBitmapService;
  private Context mContext;

  private BitmapService(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static BitmapService getInstance(Context paramContext)
  {
    if (mBitmapService == null)
      mBitmapService = new BitmapService(paramContext.getApplicationContext());
    return mBitmapService;
  }

  public Bitmap getAssetsBitmap(String paramString)
  {
    try
    {
      Bitmap localBitmap = BitmapFactory.decodeStream(this.mContext.getAssets().open(paramString));
      return localBitmap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public Bitmap getBitmap(File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()))
      return null;
    if (AppFuncCfg.FUNCTION_BITMAP_FROM_JNI)
      return EIBitmapUtil.decodeFile(paramFile.getAbsolutePath());
    return BitmapTools.decodeFile(paramFile.getAbsolutePath());
  }

  public Bitmap getBitmap(String paramString)
  {
    File localFile = new File(GlobalEnv.getInstance().getPicCachePath(), paramString);
    int j;
    for (int i = 1; ; i = j)
    {
      InputStream localInputStream;
      FileOutputStream localFileOutputStream;
      if ((!localFile.exists()) || (localFile.length() == 0L))
        try
        {
          localInputStream = this.mContext.getAssets().open(paramString);
          localFileOutputStream = new FileOutputStream(localFile);
          byte[] arrayOfByte = new byte[1024];
          while (true)
          {
            int k = localInputStream.read(arrayOfByte);
            if (k == -1)
              break;
            localFileOutputStream.write(arrayOfByte, 0, k);
          }
        }
        catch (Exception localException1)
        {
          Logger.w(TAG, "文件读取出错，请检查图片文件是否正确。文件路径：" + localFile.getAbsolutePath());
        }
      while (true)
      {
        Bitmap localBitmap;
        if (AppFuncCfg.FUNCTION_BITMAP_FROM_JNI)
        {
          localBitmap = EIBitmapUtil.decodeFile(localFile.getAbsolutePath());
          if (localBitmap == null)
            localFile.delete();
          j = i - 1;
          if ((i <= 0) || (localBitmap != null))
            return localBitmap;
        }
        else
        {
          try
          {
            localFileOutputStream.flush();
            localFileOutputStream.getFD().sync();
            label176: localFileOutputStream.close();
            localInputStream.close();
            continue;
            localBitmap = BitmapTools.decodeFile(localFile.getAbsolutePath());
          }
          catch (Exception localException2)
          {
            break label176;
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.BitmapService
 * JD-Core Version:    0.6.2
 */