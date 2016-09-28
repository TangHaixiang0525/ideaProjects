package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public class Detector
{
  private final BitMatrix image;
  private ResultPointCallback resultPointCallback;

  public Detector(BitMatrix paramBitMatrix)
  {
    this.image = paramBitMatrix;
  }

  private float calculateModuleSizeOneWay(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2)
  {
    float f1 = sizeOfBlackWhiteBlackRunBothWays((int)paramResultPoint1.getX(), (int)paramResultPoint1.getY(), (int)paramResultPoint2.getX(), (int)paramResultPoint2.getY());
    float f2 = sizeOfBlackWhiteBlackRunBothWays((int)paramResultPoint2.getX(), (int)paramResultPoint2.getY(), (int)paramResultPoint1.getX(), (int)paramResultPoint1.getY());
    if (Float.isNaN(f1))
      return f2 / 7.0F;
    if (Float.isNaN(f2))
      return f1 / 7.0F;
    return (f1 + f2) / 14.0F;
  }

  protected static int computeDimension(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, float paramFloat)
    throws NotFoundException
  {
    int i = 7 + (round(ResultPoint.distance(paramResultPoint1, paramResultPoint2) / paramFloat) + round(ResultPoint.distance(paramResultPoint1, paramResultPoint3) / paramFloat) >> 1);
    switch (i & 0x3)
    {
    case 1:
    default:
      return i;
    case 0:
      return i + 1;
    case 2:
      return i - 1;
    case 3:
    }
    throw NotFoundException.getNotFoundInstance();
  }

  public static PerspectiveTransform createTransform(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4, int paramInt)
  {
    float f1 = paramInt - 3.5F;
    float f2;
    float f3;
    float f4;
    if (paramResultPoint4 != null)
    {
      f2 = paramResultPoint4.getX();
      f3 = paramResultPoint4.getY();
      f4 = f1 - 3.0F;
    }
    for (float f5 = f4; ; f5 = f1)
    {
      return PerspectiveTransform.quadrilateralToQuadrilateral(3.5F, 3.5F, f1, 3.5F, f5, f4, 3.5F, f1, paramResultPoint1.getX(), paramResultPoint1.getY(), paramResultPoint2.getX(), paramResultPoint2.getY(), f2, f3, paramResultPoint3.getX(), paramResultPoint3.getY());
      f2 = paramResultPoint2.getX() - paramResultPoint1.getX() + paramResultPoint3.getX();
      f3 = paramResultPoint2.getY() - paramResultPoint1.getY() + paramResultPoint3.getY();
      f4 = f1;
    }
  }

  private static int round(float paramFloat)
  {
    return (int)(0.5F + paramFloat);
  }

  private static BitMatrix sampleGrid(BitMatrix paramBitMatrix, PerspectiveTransform paramPerspectiveTransform, int paramInt)
    throws NotFoundException
  {
    return GridSampler.getInstance().sampleGrid(paramBitMatrix, paramInt, paramInt, paramPerspectiveTransform);
  }

  private float sizeOfBlackWhiteBlackRun(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i;
    int j;
    int k;
    int m;
    int n;
    label74: int i1;
    label83: int i2;
    int i3;
    int i4;
    int i5;
    if (Math.abs(paramInt4 - paramInt2) > Math.abs(paramInt3 - paramInt1))
    {
      i = 1;
      if (i != 0)
      {
        int i12 = paramInt1;
        paramInt1 = paramInt2;
        paramInt2 = i12;
        int i13 = paramInt3;
        paramInt3 = paramInt4;
        paramInt4 = i13;
      }
      j = Math.abs(paramInt3 - paramInt1);
      k = Math.abs(paramInt4 - paramInt2);
      m = -j >> 1;
      if (paramInt1 >= paramInt3)
        break label193;
      n = 1;
      if (paramInt2 >= paramInt4)
        break label199;
      i1 = 1;
      i2 = 0;
      i3 = paramInt3 + n;
      i4 = paramInt1;
      i5 = paramInt2;
    }
    while (true)
    {
      if (i4 != i3)
      {
        int i8;
        label114: int i9;
        if (i != 0)
        {
          i8 = i5;
          if (i == 0)
            break label212;
          i9 = i4;
          label123: if (i2 != 1)
            break label219;
        }
        label193: label199: label212: label219: for (boolean bool1 = true; ; bool1 = false)
        {
          boolean bool2 = this.image.get(i8, i9);
          if (bool1 != bool2)
            break label228;
          if (i2 != 2)
            break label225;
          int i10 = i4 - paramInt1;
          int i11 = i5 - paramInt2;
          return (float)Math.sqrt(i10 * i10 + i11 * i11);
          i = 0;
          break;
          n = -1;
          break label74;
          i1 = -1;
          break label83;
          i8 = i4;
          break label114;
          i9 = i5;
          break label123;
        }
        label225: i2++;
        label228: m += k;
        if (m <= 0)
          break label298;
        if (i5 != paramInt4);
      }
      else
      {
        if (i2 != 2)
          break label308;
        int i6 = paramInt3 + n - paramInt1;
        int i7 = paramInt4 - paramInt2;
        return (float)Math.sqrt(i6 * i6 + i7 * i7);
      }
      i5 += i1;
      m -= j;
      label298: i4 += n;
    }
    label308: return (0.0F / 0.0F);
  }

  private float sizeOfBlackWhiteBlackRunBothWays(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    float f1 = sizeOfBlackWhiteBlackRun(paramInt1, paramInt2, paramInt3, paramInt4);
    float f2 = 1.0F;
    int i = paramInt1 - (paramInt3 - paramInt1);
    int j;
    float f3;
    if (i < 0)
    {
      f2 = paramInt1 / (paramInt1 - i);
      i = 0;
      j = (int)(paramInt2 - f2 * (paramInt4 - paramInt2));
      f3 = 1.0F;
      if (j >= 0)
        break label146;
      f3 = paramInt2 / (paramInt2 - j);
      j = 0;
    }
    while (true)
    {
      return f1 + sizeOfBlackWhiteBlackRun(paramInt1, paramInt2, (int)(paramInt1 + f3 * (i - paramInt1)), j) - 1.0F;
      if (i < this.image.getWidth())
        break;
      f2 = (-1 + this.image.getWidth() - paramInt1) / (i - paramInt1);
      i = -1 + this.image.getWidth();
      break;
      label146: if (j >= this.image.getHeight())
      {
        f3 = (-1 + this.image.getHeight() - paramInt2) / (j - paramInt2);
        j = -1 + this.image.getHeight();
      }
    }
  }

  protected float calculateModuleSize(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3)
  {
    return (calculateModuleSizeOneWay(paramResultPoint1, paramResultPoint2) + calculateModuleSizeOneWay(paramResultPoint1, paramResultPoint3)) / 2.0F;
  }

  public DetectorResult detect()
    throws NotFoundException, FormatException
  {
    return detect(null);
  }

  public DetectorResult detect(Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, FormatException
  {
    if (paramMap == null);
    for (ResultPointCallback localResultPointCallback = null; ; localResultPointCallback = (ResultPointCallback)paramMap.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK))
    {
      this.resultPointCallback = localResultPointCallback;
      return processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(paramMap));
    }
  }

  protected AlignmentPattern findAlignmentInRegion(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2)
    throws NotFoundException
  {
    int i = (int)(paramFloat2 * paramFloat1);
    int j = Math.max(0, paramInt1 - i);
    int k = Math.min(-1 + this.image.getWidth(), paramInt1 + i);
    if (k - j < paramFloat1 * 3.0F)
      throw NotFoundException.getNotFoundInstance();
    int m = Math.max(0, paramInt2 - i);
    int n = Math.min(-1 + this.image.getHeight(), paramInt2 + i);
    if (n - m < paramFloat1 * 3.0F)
      throw NotFoundException.getNotFoundInstance();
    return new AlignmentPatternFinder(this.image, j, m, k - j, n - m, paramFloat1, this.resultPointCallback).find();
  }

  protected BitMatrix getImage()
  {
    return this.image;
  }

  protected ResultPointCallback getResultPointCallback()
  {
    return this.resultPointCallback;
  }

  protected DetectorResult processFinderPatternInfo(FinderPatternInfo paramFinderPatternInfo)
    throws NotFoundException, FormatException
  {
    FinderPattern localFinderPattern1 = paramFinderPatternInfo.getTopLeft();
    FinderPattern localFinderPattern2 = paramFinderPatternInfo.getTopRight();
    FinderPattern localFinderPattern3 = paramFinderPatternInfo.getBottomLeft();
    float f1 = calculateModuleSize(localFinderPattern1, localFinderPattern2, localFinderPattern3);
    if (f1 < 1.0F)
      throw NotFoundException.getNotFoundInstance();
    int i = computeDimension(localFinderPattern1, localFinderPattern2, localFinderPattern3, f1);
    Version localVersion = Version.getProvisionalVersionForDimension(i);
    int j = -7 + localVersion.getDimensionForVersion();
    int k = localVersion.getAlignmentPatternCenters().length;
    Object localObject = null;
    int m;
    int n;
    int i1;
    float f5;
    if (k > 0)
    {
      float f2 = localFinderPattern2.getX() - localFinderPattern1.getX() + localFinderPattern3.getX();
      float f3 = localFinderPattern2.getY() - localFinderPattern1.getY() + localFinderPattern3.getY();
      float f4 = 1.0F - 3.0F / j;
      m = (int)(localFinderPattern1.getX() + f4 * (f2 - localFinderPattern1.getX()));
      n = (int)(localFinderPattern1.getY() + f4 * (f3 - localFinderPattern1.getY()));
      i1 = 4;
      localObject = null;
      if (i1 <= 16)
        f5 = i1;
    }
    while (true)
    {
      try
      {
        AlignmentPattern localAlignmentPattern = findAlignmentInRegion(f1, m, n, f5);
        localObject = localAlignmentPattern;
        PerspectiveTransform localPerspectiveTransform = createTransform(localFinderPattern1, localFinderPattern2, localFinderPattern3, localObject, i);
        BitMatrix localBitMatrix = sampleGrid(this.image, localPerspectiveTransform, i);
        if (localObject != null)
          break label274;
        arrayOfResultPoint = new ResultPoint[] { localFinderPattern3, localFinderPattern1, localFinderPattern2 };
        DetectorResult localDetectorResult = new DetectorResult(localBitMatrix, arrayOfResultPoint);
        return localDetectorResult;
      }
      catch (NotFoundException localNotFoundException)
      {
        i1 <<= 1;
      }
      break;
      label274: ResultPoint[] arrayOfResultPoint = { localFinderPattern3, localFinderPattern1, localFinderPattern2, localObject };
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.detector.Detector
 * JD-Core Version:    0.6.2
 */