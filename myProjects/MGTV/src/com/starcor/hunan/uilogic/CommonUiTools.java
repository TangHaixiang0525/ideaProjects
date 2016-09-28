package com.starcor.hunan.uilogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.http.BitmapCache;
import com.starcor.hunan.App;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class CommonUiTools
{
  public static final String EFFECT_BLUR = "effect:blur:";
  public static final String EFFECT_CIRCLE = "effect:circle:";
  public static final String EFFECT_HEXAGON = "effect:hexagon:";
  public static final String EFFECT_MIRROR = "effect:mirror:";
  public static final String EFFECT_PARALLELOGRAM = "effect:parallelogram:";
  public static final String EFFECT_POLYGON = "effect:polygon:";
  public static final String SCALE_PREFIX = "scale:";
  public static final String SIFT_PREFIX = "sift:";

  public static Bitmap createImage(String paramString, int paramInt1, int paramInt2)
  {
    new Hashtable().put(EncodeHintType.CHARACTER_SET, "utf-8");
    while (true)
    {
      int i;
      int j;
      int[] arrayOfInt;
      int k;
      try
      {
        BitMatrix localBitMatrix = new MultiFormatWriter().encode(paramString, BarcodeFormat.QR_CODE, paramInt1, paramInt2);
        i = localBitMatrix.getWidth();
        j = localBitMatrix.getHeight();
        arrayOfInt = new int[i * j];
        k = 0;
        break label188;
        if (m < paramInt1)
          if (localBitMatrix.get(m, k))
            arrayOfInt[(m + k * paramInt1)] = -16777216;
          else if ((m > 30) && (k > 30) && (m < paramInt1 - 30) && (k < paramInt2 - 30))
            arrayOfInt[(m + k * paramInt1)] = -1;
      }
      catch (WriterException localWriterException)
      {
        localWriterException.printStackTrace();
        return null;
      }
      k++;
      label188: 
      while (k >= j)
      {
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        localBitmap.setPixels(arrayOfInt, 0, i, 0, 0, i, j);
        return localBitmap;
      }
      int m = 0;
      continue;
      m++;
    }
  }

  public static Bitmap createServiceImage(String paramString, int paramInt1, int paramInt2)
  {
    while (true)
    {
      int[] arrayOfInt;
      int j;
      int k;
      int m;
      int n;
      int i1;
      int i2;
      try
      {
        QRCodeWriter localQRCodeWriter = new QRCodeWriter();
        if ((paramString == null) || ("".equals(paramString)) || (paramString.length() < 1))
          break label595;
        BitMatrix localBitMatrix1 = localQRCodeWriter.encode(paramString, BarcodeFormat.QR_CODE, paramInt1, paramInt2);
        System.out.println("w:" + localBitMatrix1.getWidth() + "h:" + localBitMatrix1.getHeight());
        Hashtable localHashtable = new Hashtable();
        localHashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix localBitMatrix2 = new QRCodeWriter().encode(paramString, BarcodeFormat.QR_CODE, paramInt1, paramInt2, localHashtable);
        arrayOfInt = new int[paramInt1 * paramInt2];
        int i = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        i1 = 0;
        if (i1 >= paramInt2)
          break label327;
        ArrayList localArrayList = new ArrayList();
        i2 = 0;
        if (i2 < paramInt1)
        {
          if (localBitMatrix2.get(i2, i1))
          {
            arrayOfInt[(i2 + i1 * paramInt1)] = -16777216;
            if (i2 <= m)
              break label597;
            m = i2;
            break label597;
            int i4 = localArrayList.size();
            if (i3 < i4)
            {
              i3++;
              continue;
            }
            localArrayList.clear();
            if (i != 0)
              break label614;
            j = i2;
            k = i1;
            i = 1;
            break label614;
          }
          arrayOfInt[(i2 + i1 * paramInt1)] = -1;
          if ((i2 <= j) || (i2 >= m))
            break label614;
          localArrayList.add(Integer.valueOf(i2 + i1 * paramInt1));
          arrayOfInt[(i2 + i1 * paramInt1)] = -1;
        }
      }
      catch (WriterException localWriterException)
      {
        localWriterException.printStackTrace();
        return null;
      }
      i1++;
      continue;
      label327: Bitmap localBitmap1 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      localBitmap1.setPixels(arrayOfInt, 0, paramInt1, 0, 0, paramInt1, paramInt2);
      Bitmap localBitmap2 = BitmapFactory.decodeResource(App.getAppContext().getResources(), 2130837621).copy(Bitmap.Config.ARGB_8888, true);
      Canvas localCanvas = new Canvas(localBitmap2);
      Paint localPaint = new Paint();
      localPaint.setColor(-16777216);
      localCanvas.drawBitmap(localBitmap1, new Rect(App.Operation(40.0F), App.Operation(40.0F), App.Operation(-40 + localBitmap1.getWidth()), App.Operation(-40 + localBitmap1.getHeight())), new Rect(j - 40, k - 42, m + 64, n + 68), localPaint);
      Bitmap localBitmap3 = BitmapFactory.decodeResource(App.getInstance().getResources(), 2130837678);
      int i5 = 3 * (paramInt1 / 7);
      int i6 = 4 * (paramInt1 / 7);
      int i7 = 3 * (paramInt2 / 7);
      int i8 = 4 * (paramInt2 / 7);
      new Paint().setColor(-1);
      Rect localRect1 = new Rect(0, 0, localBitmap3.getWidth(), localBitmap3.getHeight());
      Rect localRect2 = new Rect(i5 + 10, i7, i6 + 10, i8);
      localCanvas.drawBitmap(localBitmap3, localRect1, localRect2, new Paint());
      return localBitmap2;
      label595: return null;
      label597: if (i1 > n)
        n = i1;
      int i3 = 0;
      continue;
      label614: i2++;
    }
  }

  public static Bitmap getFlagBimtap(Context paramContext, FilmListItem paramFilmListItem)
  {
    Bitmap localBitmap;
    if (paramFilmListItem.billing == 1)
      localBitmap = BitmapCache.getInstance(paramContext).getBitmapFromCache("flag_vip.png");
    boolean bool2;
    do
    {
      String str;
      boolean bool1;
      do
      {
        return localBitmap;
        str = paramFilmListItem.corner_mark;
        bool1 = TextUtils.isEmpty(str);
        localBitmap = null;
      }
      while (bool1);
      if (str.contains("3D"))
        return BitmapCache.getInstance(paramContext).getBitmapFromCache("flag_3d.png");
      bool2 = str.contains("HD");
      localBitmap = null;
    }
    while (!bool2);
    return BitmapCache.getInstance(paramContext).getBitmapFromCache("flag_hd.png");
  }

  public static String processCompressImageUrl(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    String str1;
    int i;
    if ((paramString1 != null) && (paramString1.length() > 0))
    {
      str1 = paramString1;
      i = paramInt2;
      if (!TextUtils.isEmpty(str1))
        break label39;
    }
    label39: int j;
    do
    {
      return str1;
      str1 = paramString2;
      i = paramInt2 / 2;
      break;
      j = str1.lastIndexOf("/");
    }
    while (j == -1);
    String str2 = str1.substring(0, j);
    String str3 = str1.substring(j, str1.length());
    return str2 + "/" + paramInt1 + "x" + i + str3;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.CommonUiTools
 * JD-Core Version:    0.6.2
 */