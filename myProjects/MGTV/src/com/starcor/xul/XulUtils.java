package com.starcor.xul;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import com.starcor.xul.Graphics.XulDC;
import java.nio.Buffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

public class XulUtils
{
  public static final String STR_EMPTY = "";
  static WeakHashMap<Canvas, ArrayList<CanvasSaveInfo>> _saveCanvasStack;
  static WeakHashMap<Canvas, ArrayList<CanvasSaveInfo>> _saveCanvasStackFull;
  private static final WeakHashMap<Thread, MessageDigestCtx> _threadHashMap = new WeakHashMap();
  private static char[] hexCharMap;

  static
  {
    System.loadLibrary("starcor_xul");
    hexCharMap = new char[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  }

  private static void _doSaveInfo(WeakHashMap<Canvas, ArrayList<CanvasSaveInfo>> paramWeakHashMap, CanvasSaveInfo paramCanvasSaveInfo)
  {
    if (paramWeakHashMap != null)
    {
      ArrayList localArrayList = (ArrayList)paramWeakHashMap.get(paramCanvasSaveInfo.canvas);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList();
        paramWeakHashMap.put(paramCanvasSaveInfo.canvas, localArrayList);
      }
      localArrayList.add(paramCanvasSaveInfo);
    }
  }

  private static void _dumpFullCanvasStack(Canvas paramCanvas)
  {
    ArrayList localArrayList = (ArrayList)_saveCanvasStackFull.get(paramCanvas);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      CanvasSaveInfo localCanvasSaveInfo = (CanvasSaveInfo)localIterator.next();
      StackTraceElement[] arrayOfStackTraceElement1 = localCanvasSaveInfo.stackInfo.getStackTrace();
      Log.e("canvas", "save at: " + localCanvasSaveInfo.canvas);
      int i = arrayOfStackTraceElement1.length;
      for (int j = 0; j < i; j++)
      {
        StackTraceElement localStackTraceElement2 = arrayOfStackTraceElement1[j];
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localStackTraceElement2.toString();
        Log.e("canvas", String.format(">  %s", arrayOfObject2));
      }
      if (localCanvasSaveInfo.restoreInfo != null)
      {
        StackTraceElement[] arrayOfStackTraceElement2 = localCanvasSaveInfo.restoreInfo.getStackTrace();
        Log.e("canvas", "restore at: " + localCanvasSaveInfo.canvas);
        int k = arrayOfStackTraceElement2.length;
        for (int m = 0; m < k; m++)
        {
          StackTraceElement localStackTraceElement1 = arrayOfStackTraceElement2[m];
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localStackTraceElement1.toString();
          Log.e("canvas", String.format(">    %s", arrayOfObject1));
        }
      }
      Log.e("canvas", "--------------------------------------");
    }
    localArrayList.clear();
  }

  private static native boolean _native_do_blur(Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3);

  private static native boolean _native_do_fast_blur(Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3);

  private static void _saveInfo(Canvas paramCanvas)
  {
    CanvasSaveInfo localCanvasSaveInfo = new CanvasSaveInfo(paramCanvas, null);
    _doSaveInfo(_saveCanvasStack, localCanvasSaveInfo);
    _doSaveInfo(_saveCanvasStackFull, localCanvasSaveInfo);
  }

  private static void _saveInfo(Canvas paramCanvas, XulDC paramXulDC)
  {
    CanvasSaveInfo localCanvasSaveInfo = new CanvasSaveInfo(paramCanvas, paramXulDC);
    _doSaveInfo(_saveCanvasStack, localCanvasSaveInfo);
    _doSaveInfo(_saveCanvasStackFull, localCanvasSaveInfo);
  }

  public static String calMD5(String paramString)
  {
    String str = calMD5(paramString.getBytes());
    if (str == null)
      return paramString;
    return str;
  }

  public static String calMD5(byte[] paramArrayOfByte)
  {
    return String.valueOf(getMD5().digest(paramArrayOfByte));
  }

  public static float calRectHeight(RectF paramRectF)
  {
    return paramRectF.bottom - paramRectF.top;
  }

  public static int calRectHeight(Rect paramRect)
  {
    return paramRect.bottom - paramRect.top;
  }

  public static float calRectWidth(RectF paramRectF)
  {
    return paramRectF.right - paramRectF.left;
  }

  public static int calRectWidth(Rect paramRect)
  {
    return paramRect.right - paramRect.left;
  }

  public static int ceilToInt(float paramFloat)
  {
    return (int)Math.ceil(paramFloat);
  }

  public static void copyRect(Rect paramRect1, Rect paramRect2)
  {
    paramRect2.left = paramRect1.left;
    paramRect2.top = paramRect1.top;
    paramRect2.right = paramRect1.right;
    paramRect2.bottom = paramRect1.bottom;
  }

  public static void copyRect(Rect paramRect, RectF paramRectF)
  {
    paramRectF.left = paramRect.left;
    paramRectF.top = paramRect.top;
    paramRectF.right = paramRect.right;
    paramRectF.bottom = paramRect.bottom;
  }

  public static void copyRect(RectF paramRectF, Rect paramRect)
  {
    paramRect.left = roundToInt(paramRectF.left);
    paramRect.top = roundToInt(paramRectF.top);
    paramRect.right = roundToInt(paramRectF.right);
    paramRect.bottom = roundToInt(paramRectF.bottom);
  }

  public static void copyRect(RectF paramRectF1, RectF paramRectF2)
  {
    paramRectF2.left = paramRectF1.left;
    paramRectF2.top = paramRectF1.top;
    paramRectF2.right = paramRectF1.right;
    paramRectF2.bottom = paramRectF1.bottom;
  }

  public static boolean doBlurOnBuffer(Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3)
  {
    return _native_do_fast_blur(paramBuffer, paramInt1, paramInt2, paramInt3 * 2);
  }

  public static String getCachedString(String paramString)
  {
    return paramString;
  }

  private static MessageDigestCtx getMD5()
  {
    MessageDigestCtx localMessageDigestCtx1;
    synchronized (_threadHashMap)
    {
      Thread localThread = Thread.currentThread();
      localMessageDigestCtx1 = (MessageDigestCtx)_threadHashMap.get(localThread);
      if (localMessageDigestCtx1 == null)
        try
        {
          MessageDigestCtx localMessageDigestCtx2 = new MessageDigestCtx(MessageDigest.getInstance("md5"));
          _threadHashMap.put(localThread, localMessageDigestCtx2);
          return localMessageDigestCtx2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
          localNoSuchAlgorithmException.printStackTrace();
          return null;
        }
    }
    localMessageDigestCtx1.reset();
    return localMessageDigestCtx1;
  }

  public static boolean intersects(Rect paramRect1, Rect paramRect2)
  {
    return (paramRect1.left < paramRect2.right) && (paramRect2.left < paramRect1.right) && (paramRect1.top < paramRect2.bottom) && (paramRect2.top < paramRect1.bottom);
  }

  public static boolean intersects(RectF paramRectF1, RectF paramRectF2)
  {
    return (paramRectF1.left < paramRectF2.right) && (paramRectF2.left < paramRectF1.right) && (paramRectF1.top < paramRectF2.bottom) && (paramRectF2.top < paramRectF1.bottom);
  }

  public static boolean isEqual(float paramFloat1, float paramFloat2)
  {
    return Math.abs(paramFloat1 - paramFloat2) < 1.0E-005F;
  }

  public static String join(String paramString, Iterable paramIterable)
  {
    return TextUtils.join(paramString, paramIterable);
  }

  public static String join(String paramString, String[] paramArrayOfString)
  {
    return TextUtils.join(paramString, paramArrayOfString);
  }

  public static void offsetRect(Rect paramRect, int paramInt1, int paramInt2)
  {
    paramRect.left = (paramInt1 + paramRect.left);
    paramRect.right = (paramInt1 + paramRect.right);
    paramRect.top = (paramInt2 + paramRect.top);
    paramRect.bottom = (paramInt2 + paramRect.bottom);
  }

  public static void offsetRect(RectF paramRectF, float paramFloat1, float paramFloat2)
  {
    paramRectF.left = (paramFloat1 + paramRectF.left);
    paramRectF.right = (paramFloat1 + paramRectF.right);
    paramRectF.top = (paramFloat2 + paramRectF.top);
    paramRectF.bottom = (paramFloat2 + paramRectF.bottom);
  }

  public static void resizeRect(Rect paramRect, int paramInt1, int paramInt2)
  {
    paramRect.right = (paramInt1 + paramRect.left);
    paramRect.bottom = (paramInt2 + paramRect.top);
  }

  public static void restoreCanvas(Canvas paramCanvas)
  {
    if (_saveCanvasStack != null)
    {
      ArrayList localArrayList = (ArrayList)_saveCanvasStack.get(paramCanvas);
      if ((localArrayList == null) || (localArrayList.size() == 0))
      {
        Log.e("canvas", "restore canvas error 0 !!!");
        Log.e("canvas", "restore-----------------------------------");
        new Exception().printStackTrace();
        Log.e("canvas", "end---------------------------------------");
        _dumpFullCanvasStack(paramCanvas);
      }
      while (true)
      {
        return;
        CanvasSaveInfo localCanvasSaveInfo = (CanvasSaveInfo)localArrayList.remove(-1 + localArrayList.size());
        localCanvasSaveInfo.restoreInfo = new Exception();
        if (!localCanvasSaveInfo.canvas.equals(paramCanvas))
        {
          Log.e("canvas", "restore canvas error 1 !!!");
          Log.e("canvas", "save-----------------------------------");
          localCanvasSaveInfo.stackInfo.printStackTrace();
          Log.e("canvas", "restore-----------------------------------");
          new Exception().printStackTrace();
          Log.e("canvas", "end---------------------------------------");
          _dumpFullCanvasStack(paramCanvas);
        }
        try
        {
          paramCanvas.restore();
          if (localArrayList.isEmpty())
          {
            ((ArrayList)_saveCanvasStackFull.get(paramCanvas)).clear();
            return;
          }
        }
        catch (Exception localException)
        {
          while (true)
          {
            Log.e("canvas", "restore canvas error 2 !!!");
            Log.e("canvas", "save-----------------------------------");
            localCanvasSaveInfo.stackInfo.printStackTrace();
            Log.e("canvas", "restore-----------------------------------");
            localException.printStackTrace();
            Log.e("canvas", "end---------------------------------------");
            _dumpFullCanvasStack(paramCanvas);
          }
        }
      }
    }
    paramCanvas.restore();
  }

  public static int roundToInt(double paramDouble)
  {
    if (paramDouble >= 0.0D)
      return (int)(paramDouble + 0.5D);
    return (int)(paramDouble - 0.5D);
  }

  public static int roundToInt(float paramFloat)
  {
    if (paramFloat >= 0.0F)
      return (int)(paramFloat + 0.5F);
    return (int)(paramFloat - 0.5F);
  }

  public static long roundToLong(double paramDouble)
  {
    return Math.round(paramDouble);
  }

  public static void saveCanvas(Canvas paramCanvas)
  {
    if (_saveCanvasStack != null)
      _saveInfo(paramCanvas);
    paramCanvas.save();
  }

  public static void saveCanvas(Canvas paramCanvas, int paramInt)
  {
    if (_saveCanvasStack != null)
      _saveInfo(paramCanvas);
    paramCanvas.save(paramInt);
  }

  public static void saveCanvas(XulDC paramXulDC)
  {
    Canvas localCanvas = paramXulDC.getCanvas();
    if (_saveCanvasStack != null)
      _saveInfo(localCanvas, paramXulDC);
    localCanvas.save();
  }

  public static void saveCanvas(XulDC paramXulDC, int paramInt)
  {
    Canvas localCanvas = paramXulDC.getCanvas();
    if (_saveCanvasStack != null)
      _saveInfo(localCanvas, paramXulDC);
    localCanvas.save(paramInt);
  }

  public static void saveCanvasLayer(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint paramPaint, int paramInt5)
  {
    if (_saveCanvasStack != null)
      _saveInfo(paramCanvas);
    paramCanvas.saveLayer(paramInt1, paramInt2, paramInt3, paramInt4, paramPaint, paramInt5);
  }

  public static void saveCanvasLayer(Canvas paramCanvas, RectF paramRectF, Paint paramPaint, int paramInt)
  {
    if (_saveCanvasStack != null)
      _saveInfo(paramCanvas);
    paramCanvas.saveLayer(paramRectF, paramPaint, paramInt);
  }

  public static long timestamp()
  {
    return System.nanoTime() / 1000000L;
  }

  public static long timestamp_us()
  {
    return System.nanoTime() / 1000L;
  }

  public static double tryParseDouble(String paramString, double paramDouble)
  {
    if (TextUtils.isEmpty(paramString))
      return paramDouble;
    try
    {
      double d = Double.parseDouble(paramString);
      return d;
    }
    catch (Exception localException)
    {
    }
    return paramDouble;
  }

  public static float tryParseFloat(String paramString, float paramFloat)
  {
    if (TextUtils.isEmpty(paramString))
      return paramFloat;
    try
    {
      float f = Float.parseFloat(paramString);
      return f;
    }
    catch (Exception localException)
    {
    }
    return paramFloat;
  }

  public static long tryParseHex(String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return paramLong;
    try
    {
      long l = Long.parseLong(paramString, 16);
      return l;
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  public static int tryParseInt(String paramString)
  {
    return tryParseInt(paramString, 0);
  }

  public static int tryParseInt(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString))
      return paramInt;
    try
    {
      int i = Integer.parseInt(paramString);
      return i;
    }
    catch (Exception localException)
    {
    }
    return paramInt;
  }

  public static long tryParseLong(String paramString)
  {
    return tryParseLong(paramString, 0L);
  }

  public static long tryParseLong(String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return paramLong;
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  static class CanvasSaveInfo
  {
    Canvas canvas;
    XulDC dc;
    Exception restoreInfo;
    Exception stackInfo;

    public CanvasSaveInfo(Canvas paramCanvas, XulDC paramXulDC)
    {
      this.canvas = paramCanvas;
      this.dc = paramXulDC;
      this.stackInfo = new Exception();
    }
  }

  private static class MessageDigestCtx
  {
    MessageDigest digest;
    char[] digestStr = new char[32];

    public MessageDigestCtx(MessageDigest paramMessageDigest)
    {
      this.digest = paramMessageDigest;
    }

    public char[] digest(byte[] paramArrayOfByte)
    {
      byte[] arrayOfByte = this.digest.digest(paramArrayOfByte);
      for (int i = 0; i < 16; i++)
      {
        int j = 0xFF & arrayOfByte[i];
        this.digestStr[(0 + i * 2)] = XulUtils.hexCharMap[(j / 16)];
        this.digestStr[(1 + i * 2)] = XulUtils.hexCharMap[(j % 16)];
      }
      return this.digestStr;
    }

    public void reset()
    {
      this.digest.reset();
    }
  }

  public static class ticketMarker
  {
    static final int _MarkersLimitation = 64;
    String _infoHdr;
    String[] _n = new String[64];
    int _pos = 0;
    int _precision = 1000;
    long[] _t = new long[64];

    public ticketMarker()
    {
      this._infoHdr = "(ms) ";
    }

    public ticketMarker(String paramString, boolean paramBoolean)
    {
      if (paramString == null)
        paramString = "";
      if (paramBoolean)
      {
        this._infoHdr = (paramString + "(ms) ");
        this._precision = 1000;
        return;
      }
      this._infoHdr = (paramString + "(us) ");
      this._precision = 1;
    }

    public void mark()
    {
      mark(null);
    }

    public void mark(String paramString)
    {
      if (paramString == null)
        paramString = String.valueOf(this._pos);
      this._n[this._pos] = paramString;
      this._t[this._pos] = XulUtils.timestamp_us();
      this._pos = (1 + this._pos);
    }

    public void reset()
    {
      this._pos = 0;
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this._infoHdr);
      localStringBuilder.append((this._t[(-1 + this._pos)] - this._t[0]) / this._precision);
      localStringBuilder.append(" - ");
      for (int i = 1; i < this._pos; i++)
      {
        if (i > 1)
          localStringBuilder.append(", ");
        localStringBuilder.append(this._n[i]);
        localStringBuilder.append(":");
        localStringBuilder.append((this._t[i] - this._t[(i - 1)]) / this._precision);
      }
      return localStringBuilder.toString();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulUtils
 * JD-Core Version:    0.6.2
 */