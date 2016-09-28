package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector
{
  private boolean compact;
  private final BitMatrix image;
  private int nbCenterLayers;
  private int nbDataBlocks;
  private int nbLayers;
  private int shift;

  public Detector(BitMatrix paramBitMatrix)
  {
    this.image = paramBitMatrix;
  }

  private static void correctParameterData(boolean[] paramArrayOfBoolean, boolean paramBoolean)
    throws NotFoundException
  {
    int i;
    int j;
    int k;
    int[] arrayOfInt;
    if (paramBoolean)
    {
      i = 7;
      j = 2;
      k = i - j;
      arrayOfInt = new int[i];
    }
    for (int m = 0; ; m++)
    {
      if (m >= i)
        break label93;
      int i5 = 1;
      int i6 = 1;
      while (true)
        if (i6 <= 4)
        {
          if (paramArrayOfBoolean[(4 + 4 * m - i6)] != 0)
            arrayOfInt[m] = (i5 + arrayOfInt[m]);
          i5 <<= 1;
          i6++;
          continue;
          i = 10;
          j = 4;
          break;
        }
    }
    while (true)
    {
      label93: int n;
      try
      {
        new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(arrayOfInt, k);
        n = 0;
        if (n >= j)
          break;
        int i1 = 1;
        int i2 = 1;
        if (i2 > 4)
          break label188;
        int i3 = 4 + n * 4 - i2;
        if ((i1 & arrayOfInt[n]) == i1)
        {
          i4 = 1;
          paramArrayOfBoolean[i3] = i4;
          i1 <<= 1;
          i2++;
          continue;
        }
      }
      catch (ReedSolomonException localReedSolomonException)
      {
        throw NotFoundException.getNotFoundInstance();
      }
      int i4 = 0;
      continue;
      label188: n++;
    }
  }

  private static float distance(Point paramPoint1, Point paramPoint2)
  {
    return (float)Math.sqrt((paramPoint1.x - paramPoint2.x) * (paramPoint1.x - paramPoint2.x) + (paramPoint1.y - paramPoint2.y) * (paramPoint1.y - paramPoint2.y));
  }

  private void extractParameters(Point[] paramArrayOfPoint)
    throws NotFoundException
  {
    boolean[] arrayOfBoolean1 = sampleLine(paramArrayOfPoint[0], paramArrayOfPoint[1], 1 + 2 * this.nbCenterLayers);
    boolean[] arrayOfBoolean2 = sampleLine(paramArrayOfPoint[1], paramArrayOfPoint[2], 1 + 2 * this.nbCenterLayers);
    boolean[] arrayOfBoolean3 = sampleLine(paramArrayOfPoint[2], paramArrayOfPoint[3], 1 + 2 * this.nbCenterLayers);
    boolean[] arrayOfBoolean4 = sampleLine(paramArrayOfPoint[3], paramArrayOfPoint[0], 1 + 2 * this.nbCenterLayers);
    if ((arrayOfBoolean1[0] != 0) && (arrayOfBoolean1[(2 * this.nbCenterLayers)] != 0))
      this.shift = 0;
    while (this.compact)
    {
      boolean[] arrayOfBoolean7 = new boolean[28];
      int k = 0;
      while (true)
        if (k < 7)
        {
          arrayOfBoolean7[k] = arrayOfBoolean1[(k + 2)];
          arrayOfBoolean7[(k + 7)] = arrayOfBoolean2[(k + 2)];
          arrayOfBoolean7[(k + 14)] = arrayOfBoolean3[(k + 2)];
          arrayOfBoolean7[(k + 21)] = arrayOfBoolean4[(k + 2)];
          k++;
          continue;
          if ((arrayOfBoolean2[0] != 0) && (arrayOfBoolean2[(2 * this.nbCenterLayers)] != 0))
          {
            this.shift = 1;
            break;
          }
          if ((arrayOfBoolean3[0] != 0) && (arrayOfBoolean3[(2 * this.nbCenterLayers)] != 0))
          {
            this.shift = 2;
            break;
          }
          if ((arrayOfBoolean4[0] != 0) && (arrayOfBoolean4[(2 * this.nbCenterLayers)] != 0))
          {
            this.shift = 3;
            break;
          }
          throw NotFoundException.getNotFoundInstance();
        }
      arrayOfBoolean6 = new boolean[28];
      for (int m = 0; m < 28; m++)
        arrayOfBoolean6[m] = arrayOfBoolean7[((m + 7 * this.shift) % 28)];
    }
    boolean[] arrayOfBoolean5 = new boolean[40];
    for (int i = 0; i < 11; i++)
    {
      if (i < 5)
      {
        arrayOfBoolean5[i] = arrayOfBoolean1[(i + 2)];
        arrayOfBoolean5[(i + 10)] = arrayOfBoolean2[(i + 2)];
        arrayOfBoolean5[(i + 20)] = arrayOfBoolean3[(i + 2)];
        arrayOfBoolean5[(i + 30)] = arrayOfBoolean4[(i + 2)];
      }
      if (i > 5)
      {
        arrayOfBoolean5[(i - 1)] = arrayOfBoolean1[(i + 2)];
        arrayOfBoolean5[(-1 + (i + 10))] = arrayOfBoolean2[(i + 2)];
        arrayOfBoolean5[(-1 + (i + 20))] = arrayOfBoolean3[(i + 2)];
        arrayOfBoolean5[(-1 + (i + 30))] = arrayOfBoolean4[(i + 2)];
      }
    }
    boolean[] arrayOfBoolean6 = new boolean[40];
    for (int j = 0; j < 40; j++)
      arrayOfBoolean6[j] = arrayOfBoolean5[((j + 10 * this.shift) % 40)];
    correctParameterData(arrayOfBoolean6, this.compact);
    getParameters(arrayOfBoolean6);
  }

  private Point[] getBullEyeCornerPoints(Point paramPoint)
    throws NotFoundException
  {
    Object localObject1 = paramPoint;
    Object localObject2 = paramPoint;
    Object localObject3 = paramPoint;
    Object localObject4 = paramPoint;
    boolean bool1 = true;
    this.nbCenterLayers = 1;
    Point localPoint1;
    Point localPoint2;
    Point localPoint3;
    Point localPoint4;
    if (this.nbCenterLayers < 9)
    {
      localPoint1 = getFirstDifferent((Point)localObject1, bool1, 1, -1);
      localPoint2 = getFirstDifferent((Point)localObject2, bool1, 1, 1);
      localPoint3 = getFirstDifferent((Point)localObject3, bool1, -1, 1);
      localPoint4 = getFirstDifferent((Point)localObject4, bool1, -1, -1);
      if (this.nbCenterLayers > 2)
      {
        float f2 = distance(localPoint4, localPoint1) * this.nbCenterLayers / (distance((Point)localObject4, (Point)localObject1) * (2 + this.nbCenterLayers));
        if ((f2 >= 0.75D) && (f2 <= 1.25D) && (isWhiteOrBlackRectangle(localPoint1, localPoint2, localPoint3, localPoint4)));
      }
    }
    else
    {
      if ((this.nbCenterLayers == 5) || (this.nbCenterLayers == 7))
        break label208;
      throw NotFoundException.getNotFoundInstance();
    }
    localObject1 = localPoint1;
    localObject2 = localPoint2;
    localObject3 = localPoint3;
    localObject4 = localPoint4;
    if (!bool1);
    for (bool1 = true; ; bool1 = false)
    {
      this.nbCenterLayers = (1 + this.nbCenterLayers);
      break;
    }
    label208: if (this.nbCenterLayers == 5);
    int k;
    int m;
    int n;
    int i1;
    int i4;
    int i5;
    int i6;
    int i7;
    for (boolean bool2 = true; ; bool2 = false)
    {
      this.compact = bool2;
      float f1 = 1.5F / (-3 + 2 * this.nbCenterLayers);
      int i = ((Point)localObject1).x - ((Point)localObject3).x;
      int j = ((Point)localObject1).y - ((Point)localObject3).y;
      k = round(((Point)localObject3).x - f1 * i);
      m = round(((Point)localObject3).y - f1 * j);
      n = round(((Point)localObject1).x + f1 * i);
      i1 = round(((Point)localObject1).y + f1 * j);
      int i2 = ((Point)localObject2).x - ((Point)localObject4).x;
      int i3 = ((Point)localObject2).y - ((Point)localObject4).y;
      i4 = round(((Point)localObject4).x - f1 * i2);
      i5 = round(((Point)localObject4).y - f1 * i3);
      i6 = round(((Point)localObject2).x + f1 * i2);
      i7 = round(((Point)localObject2).y + f1 * i3);
      if ((isValid(n, i1)) && (isValid(i6, i7)) && (isValid(k, m)) && (isValid(i4, i5)))
        break;
      throw NotFoundException.getNotFoundInstance();
    }
    return new Point[] { new Point(n, i1, null), new Point(i6, i7, null), new Point(k, m, null), new Point(i4, i5, null) };
  }

  private int getColor(Point paramPoint1, Point paramPoint2)
  {
    float f1 = distance(paramPoint1, paramPoint2);
    float f2 = (paramPoint2.x - paramPoint1.x) / f1;
    float f3 = (paramPoint2.y - paramPoint1.y) / f1;
    int i = 0;
    float f4 = paramPoint1.x;
    float f5 = paramPoint1.y;
    boolean bool = this.image.get(paramPoint1.x, paramPoint1.y);
    for (int j = 0; j < f1; j++)
    {
      f4 += f2;
      f5 += f3;
      if (this.image.get(round(f4), round(f5)) != bool)
        i++;
    }
    float f6 = i / f1;
    if ((f6 > 0.1D) && (f6 < 0.9D))
      return 0;
    if (f6 <= 0.1D)
    {
      if (bool)
        return 1;
      return -1;
    }
    if (bool)
      return -1;
    return 1;
  }

  private Point getFirstDifferent(Point paramPoint, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramPoint.x;
    int j = paramInt2 + paramPoint.y;
    while ((isValid(i, j)) && (this.image.get(i, j) == paramBoolean))
    {
      i += paramInt1;
      j += paramInt2;
    }
    int k = i - paramInt1;
    int m = j - paramInt2;
    while ((isValid(k, m)) && (this.image.get(k, m) == paramBoolean))
      k += paramInt1;
    int n = k - paramInt1;
    while ((isValid(n, m)) && (this.image.get(n, m) == paramBoolean))
      m += paramInt2;
    return new Point(n, m - paramInt2, null);
  }

  private Point getMatrixCenter()
  {
    try
    {
      ResultPoint[] arrayOfResultPoint2 = new WhiteRectangleDetector(this.image).detect();
      localResultPoint1 = arrayOfResultPoint2[0];
      localResultPoint2 = arrayOfResultPoint2[1];
      localResultPoint3 = arrayOfResultPoint2[2];
      localResultPoint4 = arrayOfResultPoint2[3];
      k = round((localResultPoint1.getX() + localResultPoint4.getX() + localResultPoint2.getX() + localResultPoint3.getX()) / 4.0F);
      m = round((localResultPoint1.getY() + localResultPoint4.getY() + localResultPoint2.getY() + localResultPoint3.getY()) / 4.0F);
    }
    catch (NotFoundException localNotFoundException1)
    {
      try
      {
        ResultPoint[] arrayOfResultPoint1 = new WhiteRectangleDetector(this.image, 15, k, m).detect();
        localResultPoint5 = arrayOfResultPoint1[0];
        localResultPoint6 = arrayOfResultPoint1[1];
        localResultPoint7 = arrayOfResultPoint1[2];
        localResultPoint8 = arrayOfResultPoint1[3];
        return new Point(round((localResultPoint5.getX() + localResultPoint8.getX() + localResultPoint6.getX() + localResultPoint7.getX()) / 4.0F), round((localResultPoint5.getY() + localResultPoint8.getY() + localResultPoint6.getY() + localResultPoint7.getY()) / 4.0F), null);
        localNotFoundException1 = localNotFoundException1;
        int i = this.image.getWidth() / 2;
        int j = this.image.getHeight() / 2;
        ResultPoint localResultPoint1 = getFirstDifferent(new Point(i + 7, j - 7, null), false, 1, -1).toResultPoint();
        ResultPoint localResultPoint2 = getFirstDifferent(new Point(i + 7, j + 7, null), false, 1, 1).toResultPoint();
        ResultPoint localResultPoint3 = getFirstDifferent(new Point(i - 7, j + 7, null), false, -1, 1).toResultPoint();
        ResultPoint localResultPoint4 = getFirstDifferent(new Point(i - 7, j - 7, null), false, -1, -1).toResultPoint();
      }
      catch (NotFoundException localNotFoundException2)
      {
        while (true)
        {
          int k;
          int m;
          ResultPoint localResultPoint5 = getFirstDifferent(new Point(k + 7, m - 7, null), false, 1, -1).toResultPoint();
          ResultPoint localResultPoint6 = getFirstDifferent(new Point(k + 7, m + 7, null), false, 1, 1).toResultPoint();
          ResultPoint localResultPoint7 = getFirstDifferent(new Point(k - 7, m + 7, null), false, -1, 1).toResultPoint();
          ResultPoint localResultPoint8 = getFirstDifferent(new Point(k - 7, m - 7, null), false, -1, -1).toResultPoint();
        }
      }
    }
  }

  private ResultPoint[] getMatrixCornerPoints(Point[] paramArrayOfPoint)
    throws NotFoundException
  {
    int i = 2 * this.nbLayers;
    int j;
    float f;
    int m;
    label65: int i2;
    label95: int i4;
    int i5;
    int i6;
    int i7;
    int i9;
    label201: int i10;
    int i11;
    if (this.nbLayers > 4)
    {
      j = 1;
      f = (j + i + (-4 + this.nbLayers) / 8) / (2.0F * this.nbCenterLayers);
      int k = paramArrayOfPoint[0].x - paramArrayOfPoint[2].x;
      if (k <= 0)
        break label367;
      m = 1;
      int n = k + m;
      int i1 = paramArrayOfPoint[0].y - paramArrayOfPoint[2].y;
      if (i1 <= 0)
        break label373;
      i2 = 1;
      int i3 = i1 + i2;
      i4 = round(paramArrayOfPoint[2].x - f * n);
      i5 = round(paramArrayOfPoint[2].y - f * i3);
      i6 = round(paramArrayOfPoint[0].x + f * n);
      i7 = round(paramArrayOfPoint[0].y + f * i3);
      int i8 = paramArrayOfPoint[1].x - paramArrayOfPoint[3].x;
      if (i8 <= 0)
        break label379;
      i9 = 1;
      i10 = i8 + i9;
      i11 = paramArrayOfPoint[1].y - paramArrayOfPoint[3].y;
      if (i11 <= 0)
        break label385;
    }
    int i14;
    int i15;
    int i16;
    int i17;
    label385: for (int i12 = 1; ; i12 = -1)
    {
      int i13 = i11 + i12;
      i14 = round(paramArrayOfPoint[3].x - f * i10);
      i15 = round(paramArrayOfPoint[3].y - f * i13);
      i16 = round(paramArrayOfPoint[1].x + f * i10);
      i17 = round(paramArrayOfPoint[1].y + f * i13);
      if ((isValid(i6, i7)) && (isValid(i16, i17)) && (isValid(i4, i5)) && (isValid(i14, i15)))
        break label391;
      throw NotFoundException.getNotFoundInstance();
      j = 0;
      break;
      label367: m = -1;
      break label65;
      label373: i2 = -1;
      break label95;
      label379: i9 = -1;
      break label201;
    }
    label391: ResultPoint[] arrayOfResultPoint = new ResultPoint[4];
    arrayOfResultPoint[0] = new ResultPoint(i6, i7);
    arrayOfResultPoint[1] = new ResultPoint(i16, i17);
    arrayOfResultPoint[2] = new ResultPoint(i4, i5);
    arrayOfResultPoint[3] = new ResultPoint(i14, i15);
    return arrayOfResultPoint;
  }

  private void getParameters(boolean[] paramArrayOfBoolean)
  {
    int i;
    if (this.compact)
      i = 2;
    for (int j = 6; ; j = 11)
    {
      for (int k = 0; k < i; k++)
      {
        this.nbLayers <<= 1;
        if (paramArrayOfBoolean[k] != 0)
          this.nbLayers = (1 + this.nbLayers);
      }
      i = 5;
    }
    for (int m = i; m < i + j; m++)
    {
      this.nbDataBlocks <<= 1;
      if (paramArrayOfBoolean[m] != 0)
        this.nbDataBlocks = (1 + this.nbDataBlocks);
    }
    this.nbLayers = (1 + this.nbLayers);
    this.nbDataBlocks = (1 + this.nbDataBlocks);
  }

  private boolean isValid(int paramInt1, int paramInt2)
  {
    return (paramInt1 >= 0) && (paramInt1 < this.image.getWidth()) && (paramInt2 > 0) && (paramInt2 < this.image.getHeight());
  }

  private boolean isWhiteOrBlackRectangle(Point paramPoint1, Point paramPoint2, Point paramPoint3, Point paramPoint4)
  {
    Point localPoint1 = new Point(paramPoint1.x - 3, 3 + paramPoint1.y, null);
    Point localPoint2 = new Point(paramPoint2.x - 3, paramPoint2.y - 3, null);
    Point localPoint3 = new Point(3 + paramPoint3.x, paramPoint3.y - 3, null);
    Point localPoint4 = new Point(3 + paramPoint4.x, 3 + paramPoint4.y, null);
    int i = getColor(localPoint4, localPoint1);
    if (i == 0);
    while ((getColor(localPoint1, localPoint2) != i) || (getColor(localPoint2, localPoint3) != i) || (getColor(localPoint3, localPoint4) != i))
      return false;
    return true;
  }

  private static int round(float paramFloat)
  {
    return (int)(0.5F + paramFloat);
  }

  private BitMatrix sampleGrid(BitMatrix paramBitMatrix, ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4)
    throws NotFoundException
  {
    int i;
    if (this.compact)
      i = 11 + 4 * this.nbLayers;
    while (true)
    {
      GridSampler localGridSampler = GridSampler.getInstance();
      float f1 = i - 0.5F;
      float f2 = i - 0.5F;
      float f3 = i - 0.5F;
      float f4 = i - 0.5F;
      float f5 = paramResultPoint1.getX();
      float f6 = paramResultPoint1.getY();
      float f7 = paramResultPoint4.getX();
      float f8 = paramResultPoint4.getY();
      float f9 = paramResultPoint3.getX();
      float f10 = paramResultPoint3.getY();
      float f11 = paramResultPoint2.getX();
      float f12 = paramResultPoint2.getY();
      return localGridSampler.sampleGrid(paramBitMatrix, i, i, 0.5F, 0.5F, f1, 0.5F, f2, f3, 0.5F, f4, f5, f6, f7, f8, f9, f10, f11, f12);
      if (this.nbLayers <= 4)
        i = 15 + 4 * this.nbLayers;
      else
        i = 15 + (4 * this.nbLayers + 2 * (1 + (-4 + this.nbLayers) / 8));
    }
  }

  private boolean[] sampleLine(Point paramPoint1, Point paramPoint2, int paramInt)
  {
    boolean[] arrayOfBoolean = new boolean[paramInt];
    float f1 = distance(paramPoint1, paramPoint2);
    float f2 = f1 / (paramInt - 1);
    float f3 = f2 * (paramPoint2.x - paramPoint1.x) / f1;
    float f4 = f2 * (paramPoint2.y - paramPoint1.y) / f1;
    float f5 = paramPoint1.x;
    float f6 = paramPoint1.y;
    for (int i = 0; i < paramInt; i++)
    {
      arrayOfBoolean[i] = this.image.get(round(f5), round(f6));
      f5 += f3;
      f6 += f4;
    }
    return arrayOfBoolean;
  }

  public AztecDetectorResult detect()
    throws NotFoundException
  {
    Point[] arrayOfPoint = getBullEyeCornerPoints(getMatrixCenter());
    extractParameters(arrayOfPoint);
    ResultPoint[] arrayOfResultPoint = getMatrixCornerPoints(arrayOfPoint);
    return new AztecDetectorResult(sampleGrid(this.image, arrayOfResultPoint[(this.shift % 4)], arrayOfResultPoint[((3 + this.shift) % 4)], arrayOfResultPoint[((2 + this.shift) % 4)], arrayOfResultPoint[((1 + this.shift) % 4)]), arrayOfResultPoint, this.compact, this.nbDataBlocks, this.nbLayers);
  }

  private static class Point
  {
    public final int x;
    public final int y;

    private Point(int paramInt1, int paramInt2)
    {
      this.x = paramInt1;
      this.y = paramInt2;
    }

    public ResultPoint toResultPoint()
    {
      return new ResultPoint(this.x, this.y);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.aztec.detector.Detector
 * JD-Core Version:    0.6.2
 */