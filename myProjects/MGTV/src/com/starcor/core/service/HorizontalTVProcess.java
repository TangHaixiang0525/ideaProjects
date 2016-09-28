package com.starcor.core.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.ei.libs.bitmap.EIBitmapUtil;
import com.starcor.core.http.BitmapCache;
import com.starcor.core.interfaces.IPostProcessing;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HorizontalTVProcess
  implements IPostProcessing
{
  private static HorizontalTVProcess mHorizontalTVProcess = null;
  private Bitmap backGround = null;
  private Rect dst = null;
  private Context mContext;

  private HorizontalTVProcess(Context paramContext)
  {
    this.mContext = paramContext;
    this.backGround = BitmapCache.getInstance(this.mContext).getBitmapFromCache("playbill_default_h2.png");
    this.dst = new Rect();
    this.dst.left = ((-334 + this.backGround.getWidth()) / 2);
    this.dst.top = ((-191 + this.backGround.getHeight()) / 2);
    this.dst.bottom = (191 + this.dst.top);
    this.dst.right = (334 + this.dst.left);
  }

  public static HorizontalTVProcess getInstance(Context paramContext)
  {
    try
    {
      if (mHorizontalTVProcess == null)
        mHorizontalTVProcess = new HorizontalTVProcess(paramContext);
      HorizontalTVProcess localHorizontalTVProcess = mHorizontalTVProcess;
      return localHorizontalTVProcess;
    }
    finally
    {
    }
  }

  public Bitmap Process(Bitmap paramBitmap, File paramFile)
  {
    Bitmap localBitmap = EIBitmapUtil.createBitmap(this.backGround);
    new Canvas(localBitmap).drawBitmap(paramBitmap, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), this.dst, new Paint());
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      localBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
      localFileOutputStream.close();
      return localBitmap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localBitmap;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.HorizontalTVProcess
 * JD-Core Version:    0.6.2
 */