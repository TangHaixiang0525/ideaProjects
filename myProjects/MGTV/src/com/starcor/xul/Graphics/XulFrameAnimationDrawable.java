package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.XulUtils;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XulFrameAnimationDrawable extends XulAnimationDrawable
{
  static Pattern frameLinePattern = Pattern.compile("^([F])(?:\\+(\\d+))?,(\\d+),(.+?)(?:,(\\+)?\\((\\d+),(\\d+)(?:,(\\d+),(\\d+))?\\))?(?:,(\\+)?\\((\\d+),(\\d+)(?:,(\\d+),(\\d+))?\\))?$", 2);
  ArrayList<XulDrawable> _frameImgs = new ArrayList();
  ArrayList<frameInfo> _frames = new ArrayList();
  Rect _rc = new Rect();
  int _repeat = 1;

  public static XulFrameAnimationDrawable buildAnimation(XulAnimationDrawable.AnimationPackage paramAnimationPackage)
  {
    XulFrameAnimationDrawable localXulFrameAnimationDrawable = null;
    ArrayList localArrayList = paramAnimationPackage.getAniDesc();
    XulCachedHashMap localXulCachedHashMap = new XulCachedHashMap();
    int i = 0;
    String str1;
    String[] arrayOfString;
    if (i < localArrayList.size())
    {
      str1 = (String)localArrayList.get(i);
      if (i != 0)
        break label124;
      arrayOfString = str1.split(",");
      if ((arrayOfString.length == 4) && ("frame".equals(arrayOfString[0])));
    }
    else
    {
      return localXulFrameAnimationDrawable;
    }
    localXulFrameAnimationDrawable = new XulFrameAnimationDrawable();
    localXulFrameAnimationDrawable._rc.set(0, 0, XulUtils.tryParseInt(arrayOfString[1], 0), XulUtils.tryParseInt(arrayOfString[2], 0));
    localXulFrameAnimationDrawable._repeat = XulUtils.tryParseInt(arrayOfString[3], 1);
    label124: Matcher localMatcher;
    do
    {
      i++;
      break;
      localMatcher = frameLinePattern.matcher(str1);
    }
    while (!localMatcher.matches());
    localMatcher.group(1);
    String str2 = localMatcher.group(2);
    String str3 = localMatcher.group(3);
    String str4 = localMatcher.group(4);
    String str5 = localMatcher.group(5);
    String str6 = localMatcher.group(6);
    String str7 = localMatcher.group(7);
    String str8 = localMatcher.group(8);
    String str9 = localMatcher.group(9);
    String str10 = localMatcher.group(10);
    String str11 = localMatcher.group(11);
    String str12 = localMatcher.group(12);
    String str13 = localMatcher.group(13);
    String str14 = localMatcher.group(14);
    frameInfo localframeInfo = new frameInfo(null);
    localframeInfo.delay = XulUtils.tryParseInt(str3, 30);
    Integer localInteger;
    label303: int k;
    int m;
    if (str4 == null)
    {
      localInteger = Integer.valueOf(-1 + localXulFrameAnimationDrawable._frameImgs.size());
      localframeInfo.frameImg = localInteger.intValue();
      if ("+".equals(str5))
        localframeInfo.isSrcOffset = true;
      if ("+".equals(str10))
        localframeInfo.isDstOffset = true;
      int j = localInteger.intValue();
      k = 0;
      m = 0;
      if (j >= 0)
      {
        XulDrawable localXulDrawable = (XulDrawable)localXulFrameAnimationDrawable._frameImgs.get(localInteger.intValue());
        m = localXulDrawable.getWidth();
        k = localXulDrawable.getHeight();
      }
      if (!localframeInfo.isSrcOffset)
        break label590;
      localframeInfo.src.set(XulUtils.tryParseInt(str6, 0), XulUtils.tryParseInt(str7, 0), XulUtils.tryParseInt(str8, 0), XulUtils.tryParseInt(str9, 0));
      label434: if (!localframeInfo.isDstOffset)
        break label684;
      if ((str14 != null) || (str13 != null) || (str11 != null) || (str12 != null))
        break label637;
      localframeInfo.dst = null;
    }
    while (true)
    {
      int n = XulUtils.tryParseInt(str2, 1);
      if (n <= 0)
        n = 1;
      if (n > 1000)
        n = 1000;
      while (n > 0)
      {
        n--;
        localXulFrameAnimationDrawable._frames.add(localframeInfo);
      }
      break;
      localInteger = (Integer)localXulCachedHashMap.get(str4);
      if (localInteger != null)
        break label303;
      Bitmap localBitmap = paramAnimationPackage.loadFrameImage(str4);
      if (localBitmap != null)
      {
        localXulFrameAnimationDrawable._frameImgs.add(XulBitmapDrawable.fromBitmap(localBitmap, "", ""));
        localInteger = Integer.valueOf(-1 + localXulFrameAnimationDrawable._frameImgs.size());
        break label303;
      }
      localInteger = Integer.valueOf(-1);
      break label303;
      label590: localframeInfo.src.set(0, 0, XulUtils.tryParseInt(str8, m), XulUtils.tryParseInt(str9, k));
      XulUtils.offsetRect(localframeInfo.src, XulUtils.tryParseInt(str6, 0), XulUtils.tryParseInt(str7, 0));
      break label434;
      label637: localframeInfo.dst = new Rect();
      localframeInfo.dst.set(XulUtils.tryParseInt(str11, 0), XulUtils.tryParseInt(str12, 0), XulUtils.tryParseInt(str13, 0), XulUtils.tryParseInt(str14, 0));
      continue;
      label684: if ((str14 == null) && (str13 == null) && (str11 == null) && (str12 == null))
      {
        localframeInfo.dst = null;
      }
      else
      {
        localframeInfo.dst = new Rect();
        localframeInfo.dst.set(0, 0, XulUtils.tryParseInt(str13, XulUtils.calRectWidth(localXulFrameAnimationDrawable._rc)), XulUtils.tryParseInt(str14, XulUtils.calRectHeight(localXulFrameAnimationDrawable._rc)));
        XulUtils.offsetRect(localframeInfo.dst, XulUtils.tryParseInt(str11, 0), XulUtils.tryParseInt(str12, 0));
      }
    }
  }

  public XulAnimationDrawable.AnimationDrawingContext createDrawingCtx()
  {
    frameAnimationDrawingCtx localframeAnimationDrawingCtx = new frameAnimationDrawingCtx(null);
    localframeAnimationDrawingCtx.frames = this._frames;
    localframeAnimationDrawingCtx.aniRc = this._rc;
    localframeAnimationDrawingCtx.maxRepeat = this._repeat;
    localframeAnimationDrawingCtx.updateAnimation(XulUtils.timestamp());
    return localframeAnimationDrawingCtx;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    return false;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    return false;
  }

  public boolean drawAnimation(XulAnimationDrawable.AnimationDrawingContext paramAnimationDrawingContext, XulDC paramXulDC, Rect paramRect, Paint paramPaint)
  {
    if ((paramAnimationDrawingContext == null) || (!(paramAnimationDrawingContext instanceof frameAnimationDrawingCtx)))
      return false;
    frameAnimationDrawingCtx localframeAnimationDrawingCtx = (frameAnimationDrawingCtx)paramAnimationDrawingContext;
    int i = localframeAnimationDrawingCtx.frameIdx;
    frameInfo localframeInfo = (frameInfo)this._frames.get(i);
    Rect localRect1 = localframeAnimationDrawingCtx.lastSrc;
    Rect localRect2 = localframeAnimationDrawingCtx.lastDst;
    XulDrawable localXulDrawable = (XulDrawable)this._frameImgs.get(localframeInfo.frameImg);
    int j = XulUtils.calRectWidth(paramRect);
    int k = XulUtils.calRectHeight(paramRect);
    int m = XulUtils.calRectWidth(this._rc);
    int n = XulUtils.calRectHeight(this._rc);
    if ((localXulDrawable != null) && (m > 0) && (n > 0))
    {
      float f1 = j / m;
      float f2 = k / n;
      paramXulDC.drawBitmap(localXulDrawable, localRect1.left, localRect1.top, XulUtils.calRectWidth(localRect1), XulUtils.calRectHeight(localRect1), paramRect.left + f1 * localRect2.left, paramRect.top + f2 * localRect2.top, f1 * XulUtils.calRectWidth(localRect2), f2 * XulUtils.calRectHeight(localRect1), paramPaint);
    }
    return false;
  }

  public boolean drawAnimation(XulAnimationDrawable.AnimationDrawingContext paramAnimationDrawingContext, XulDC paramXulDC, RectF paramRectF, Paint paramPaint)
  {
    if ((paramAnimationDrawingContext == null) || (!(paramAnimationDrawingContext instanceof frameAnimationDrawingCtx)))
      return false;
    frameAnimationDrawingCtx localframeAnimationDrawingCtx = (frameAnimationDrawingCtx)paramAnimationDrawingContext;
    int i = localframeAnimationDrawingCtx.frameIdx;
    frameInfo localframeInfo = (frameInfo)this._frames.get(i);
    Rect localRect1 = localframeAnimationDrawingCtx.lastSrc;
    Rect localRect2 = localframeAnimationDrawingCtx.lastDst;
    XulDrawable localXulDrawable = (XulDrawable)this._frameImgs.get(localframeInfo.frameImg);
    float f1 = XulUtils.calRectWidth(paramRectF);
    float f2 = XulUtils.calRectHeight(paramRectF);
    int j = XulUtils.calRectWidth(this._rc);
    int k = XulUtils.calRectHeight(this._rc);
    if ((localXulDrawable != null) && (j > 0) && (k > 0))
    {
      float f3 = f1 / j;
      float f4 = f2 / k;
      paramXulDC.drawBitmap(localXulDrawable, localRect1.left, localRect1.top, XulUtils.calRectWidth(localRect1), XulUtils.calRectHeight(localRect1), paramRectF.left + f3 * localRect2.left, paramRectF.top + f4 * localRect2.top, f3 * XulUtils.calRectWidth(localRect2), f4 * XulUtils.calRectHeight(localRect1), paramPaint);
    }
    return false;
  }

  public int getHeight()
  {
    return XulUtils.calRectHeight(this._rc);
  }

  public int getWidth()
  {
    return XulUtils.calRectWidth(this._rc);
  }

  private static class frameAnimationDrawingCtx extends XulAnimationDrawable.AnimationDrawingContext
  {
    Rect aniRc;
    int frameIdx = -1;
    ArrayList<XulFrameAnimationDrawable.frameInfo> frames;
    Rect lastDst = new Rect();
    Rect lastSrc = new Rect();
    long lastUpdateTimestamp = 0L;
    int maxRepeat;
    int repeat = 0;
    boolean reset = false;

    private void updateFrameRect(XulFrameAnimationDrawable.frameInfo paramframeInfo)
    {
      Rect localRect1 = paramframeInfo.src;
      Rect localRect2 = paramframeInfo.dst;
      if (paramframeInfo.isSrcOffset)
      {
        Rect localRect7 = this.lastSrc;
        localRect7.left += localRect1.left;
        Rect localRect8 = this.lastSrc;
        localRect8.top += localRect1.top;
        Rect localRect9 = this.lastSrc;
        localRect9.right += localRect1.left + localRect1.right;
        Rect localRect10 = this.lastSrc;
        localRect10.bottom += localRect1.top + localRect1.bottom;
      }
      while (localRect2 == null)
      {
        return;
        XulUtils.copyRect(localRect1, this.lastSrc);
      }
      if (paramframeInfo.isDstOffset)
      {
        Rect localRect3 = this.lastDst;
        localRect3.left += localRect2.left;
        Rect localRect4 = this.lastDst;
        localRect4.top += localRect2.top;
        Rect localRect5 = this.lastDst;
        localRect5.right += localRect2.left + localRect2.right;
        Rect localRect6 = this.lastDst;
        localRect6.bottom += localRect2.top + localRect2.bottom;
        return;
      }
      XulUtils.copyRect(localRect2, this.lastDst);
    }

    public boolean isAnimationFinished()
    {
      if (this.frames.size() <= 1);
      do
      {
        return true;
        if (this.reset)
          return false;
      }
      while ((this.maxRepeat != 0) && (this.repeat >= this.maxRepeat));
      return false;
    }

    public void reset()
    {
      this.reset = true;
    }

    public boolean updateAnimation(long paramLong)
    {
      if (this.frames.size() <= 1)
        return false;
      if ((this.frameIdx < 0) || (this.reset))
      {
        this.reset = false;
        XulUtils.copyRect(this.aniRc, this.lastSrc);
        XulUtils.copyRect(this.aniRc, this.lastDst);
        this.frameIdx = 0;
        this.repeat = 0;
      }
      while (true)
      {
        XulFrameAnimationDrawable.frameInfo localframeInfo = (XulFrameAnimationDrawable.frameInfo)this.frames.get(this.frameIdx);
        updateFrameRect(localframeInfo);
        this.lastUpdateTimestamp = (paramLong + localframeInfo.delay);
        return true;
        if (((this.maxRepeat != 0) && (this.repeat >= this.maxRepeat)) || (paramLong <= this.lastUpdateTimestamp))
          break;
        this.frameIdx = (1 + this.frameIdx);
        if (this.frameIdx >= this.frames.size())
        {
          this.repeat = (1 + this.repeat);
          if ((this.maxRepeat != 0) && (this.repeat >= this.maxRepeat))
            break label206;
          this.frameIdx = 0;
          XulUtils.copyRect(this.aniRc, this.lastSrc);
          XulUtils.copyRect(this.aniRc, this.lastDst);
        }
      }
      label206: this.frameIdx = (-1 + this.frameIdx);
      return false;
    }
  }

  private static class frameInfo
  {
    int delay;
    Rect dst;
    int frameImg;
    boolean isDstOffset = false;
    boolean isSrcOffset = false;
    Rect src = new Rect();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulFrameAnimationDrawable
 * JD-Core Version:    0.6.2
 */