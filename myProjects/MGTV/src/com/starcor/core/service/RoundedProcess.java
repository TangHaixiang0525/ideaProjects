package com.starcor.core.service;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.ei.libs.bitmap.EIBitmapUtil;
import com.starcor.core.utils.GeneralUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class RoundedProcess
{
  public static Bitmap Process(Bitmap paramBitmap, File paramFile)
  {
    GeneralUtils.markTime("Rounded");
    Bitmap localBitmap = EIBitmapUtil.createBitmap(GeneralUtils.getRoundedCornerBitmap(paramBitmap, 10));
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      localBitmap.compress(Bitmap.CompressFormat.PNG, 80, localFileOutputStream);
      localFileOutputStream.close();
      paramBitmap.recycle();
      GeneralUtils.markTime("Rounded");
      return localBitmap;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.RoundedProcess
 * JD-Core Version:    0.6.2
 */