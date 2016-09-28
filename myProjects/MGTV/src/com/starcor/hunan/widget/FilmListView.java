package com.starcor.hunan.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.SearchListItem;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.service.TempFileManager;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.xul.Graphics.BitmapTools;
import com.starcor.xul.XulUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;

public class FilmListView extends View
  implements View.OnKeyListener, View.OnFocusChangeListener, View.OnTouchListener
{
  public static final int DIRECTION_DOWN = 3;
  public static final int DIRECTION_LEFT = 0;
  public static final int DIRECTION_RIGHT = 2;
  public static final int DIRECTION_UP = 1;
  public static int FLAGS_ALWAYS_SCALE_FOCUS = 0;
  public static int FLAGS_ALWAYS_SHOW_CURSOR = 0;
  public static int FLAGS_ENABLE_CURSOR_ANIMATION = 0;
  public static int FLAGS_FIXED_CURSOR_POS = 0;
  public static int FLAGS_HORIZONTAL_SCROLL = 0;
  public static int FLAGS_LOOP_CURSOR = 0;
  public static int FLAGS_NO_CURSOR = 0;
  public static int FLAGS_NO_FADE_IN_EFFECT = 0;
  public static int FLAGS_NO_SCROLL_ANIMATION = 0;
  public static int FLAGS_SHOW_PLACE_HOLDER = 0;
  public static final String TAG = FilmListView.class.getSimpleName();
  private static HashMap<String, Bitmap> _cachedBkgBmp;
  static Long cacheSweepTime;
  static int handlerChangedCounter = 0;
  static Thread[] imageDecodeWorkers;
  static final ArrayList<imageDownloadHandler> imageDownloadHandlers = new ArrayList();
  static Thread[] imageDownloadWorkers;
  private static File localCachePath;
  public static int maxCachePicCount = 0;
  public static final int timerInterval = 30;
  private float _xScalar = 1.0F;
  private float _yScalar = 1.0F;
  private int activeItemTextBGColor = -9008952;
  private int activeItemTextColor = -1;
  int autoMoving = 0;
  private int backgroundShadowSize = 5;
  Paint bmpPaint = new Paint(3);
  ColorMatrix cMatrix = new ColorMatrix();
  Runnable checkRedraw;
  private int columnInterval = 185;
  protected int cursorAnimationStep = 0;
  private Bitmap defaultBkg;
  private Shape defaultPosterShape;
  ShapeDrawable defaultShapeDrawable = new ShapeDrawable();
  protected Bitmap defaultWideBkg;
  imageDownloadHandler downloadHandler;
  Paint firstLinePaint = new Paint(3);
  private int flags;
  private Bitmap focusImage;
  private Bitmap focusImageForWide;
  Paint focusShadowPaint = new Paint(3);
  private float focusXScalar;
  private float focusYScalar;
  Paint focusedSelectionPaint = new Paint(3);
  private int inactiveItemTextBGColor = -14079703;
  private int inactiveItemTextColor = -2130706433;
  Paint infoPaint = new Paint(3);
  private int initialScrollTextOffset = -30;
  private int intervalScrollTextOffset = -100;
  protected int itemBottomPadding = 5;
  protected int itemLeftPadding = 5;
  private int itemPicHeight = 205;
  private ItemPicProcessor itemPicProcessor;
  protected float itemPicRoundRect = App.OperationFolat(6.5F);
  private int itemPicWidth = 145;
  protected int itemRightPadding = 5;
  private ItemTextColorizer itemTextColorizer;
  protected int itemTopPadding = 5;
  ArrayList<ViewItem> items = new ArrayList();
  private int itemsPerLine = 5;
  private int lastDownKey;
  long lastDrawTime;
  private int lastKeyAction;
  int leftPosition = 0;
  private int lineInterval = 275;
  private Context mContext;
  private final Shape markShape = new RoundRectShape(new float[] { 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.0F }, null, null);
  private int maxPreloadLines = 5;
  private int maxVisibleLines = 2;
  int movingCounter = 0;
  int oldSelectedItem = 0;
  private OnClickListener onClickListener;
  private OnDrawItemListener onDrawItemListener;
  private OnScrollingListener onScrollingListener;
  private OnSelectionChangedListener onSelectionChangedListener;
  private OnTitleChangeListener onTitleChangeListener;
  private OnViewScrollListener onViewScrollListener;
  Integer refreshRequest = Integer.valueOf(0);
  private int scrollTextSpacing = 40;
  Paint secondLinePaint = new Paint(1);
  int selectedItem = 0;
  private int selectionBoxColor;
  private int selectionBoxFocusColor;
  private float selectionBoxSize;
  Paint selectionPaint = new Paint(3);
  private ShapeDrawable shapeDrawable = new ShapeDrawable();
  private boolean showAbstractInfo;
  int targetLeftPosition = 0;
  int targetTopPosition = 0;
  Paint textBGPaint = new Paint(3);
  private Drawable textBkg;
  private int[] textColorBuffer;
  int textOffset = 0;
  private int textOffsetY;
  Paint textPaint = new Paint(3);
  float textYOffset = -1.0F;
  int topPosition = 0;
  float[] unSelect = { 0.5F, 0.0F, 0.0F, 0.0F, 0.5F, 0.0F, 0.5F, 0.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.5F, 0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F };
  private Shape updateInfoShape;
  private int wideItemCount = 0;
  private int xOffsetLeft = 0;
  private int xOffsetRight = 0;
  private int yOffset = 10;

  static
  {
    FLAGS_NO_SCROLL_ANIMATION = 4096;
    FLAGS_NO_FADE_IN_EFFECT = 8192;
    FLAGS_ALWAYS_SHOW_CURSOR = 16384;
    FLAGS_SHOW_PLACE_HOLDER = 32768;
    FLAGS_HORIZONTAL_SCROLL = 256;
    FLAGS_ENABLE_CURSOR_ANIMATION = 512;
    FLAGS_FIXED_CURSOR_POS = 1024;
    FLAGS_NO_CURSOR = 2048;
    FLAGS_LOOP_CURSOR = 16;
    FLAGS_ALWAYS_SCALE_FOCUS = 32;
    imageDownloadWorkers = new Thread[6];
    imageDecodeWorkers = new Thread[2];
    cacheSweepTime = Long.valueOf(timestamp());
    handlerChangedCounter = 0;
    _cachedBkgBmp = new HashMap();
    localCachePath = App.getAppContext().getDir("cached_pic", 0);
    for (int i = 0; i < imageDownloadWorkers.length; i++)
    {
      imageDownloadWorkers[i] = new Thread(new Runnable()
      {
        public void run()
        {
          byte[] arrayOfByte1 = null;
          byte[] arrayOfByte2 = null;
          if (FilmListView.imageDownloadWorkers != null)
          {
            int i = 0;
            int j = FilmListView.handlerChangedCounter;
            while (true)
            {
              label114: FilmListView.imageDownloadHandler localimageDownloadHandler;
              int m;
              synchronized (FilmListView.imageDownloadHandlers)
              {
                int k;
                while (true)
                {
                  k = FilmListView.imageDownloadHandlers.size();
                  if (j != FilmListView.handlerChangedCounter)
                  {
                    Logger.i(FilmListView.TAG, "handler changed " + j + " - " + FilmListView.handlerChangedCounter);
                    j = FilmListView.handlerChangedCounter;
                    i = 0;
                  }
                  if (i < k)
                    break label114;
                  try
                  {
                    Thread.sleep(100L, 0);
                  }
                  catch (Exception localException1)
                  {
                    localException1.printStackTrace();
                  }
                }
                break;
                localimageDownloadHandler = (FilmListView.imageDownloadHandler)FilmListView.imageDownloadHandlers.get(-1 + (k - i));
                m = 1;
                if ((j != FilmListView.handlerChangedCounter) || (m == 0) || (localimageDownloadHandler.isTerminated))
                  break label211;
                if (arrayOfByte1 != null);
              }
              try
              {
                arrayOfByte1 = new byte[65536];
                if (arrayOfByte2 == null)
                  arrayOfByte2 = new byte[4096];
                boolean bool = localimageDownloadHandler.doLoadImage(arrayOfByte1, arrayOfByte2);
                m = bool;
                continue;
                localObject = finally;
                throw localObject;
              }
              catch (Exception localException2)
              {
                localException2.printStackTrace();
              }
              continue;
              label211: i++;
            }
          }
        }
      });
      imageDownloadWorkers[i].setName("FilmListView Downloader [" + i + "]");
      imageDownloadWorkers[i].setPriority(1);
      imageDownloadWorkers[i].start();
    }
    for (int j = 0; j < imageDecodeWorkers.length; j++)
    {
      imageDecodeWorkers[j] = new Thread(new Runnable()
      {
        // ERROR //
        public void run()
        {
          // Byte code:
          //   0: new 17	com/starcor/hunan/widget/FilmListView$DecodeContext
          //   3: dup
          //   4: aconst_null
          //   5: invokespecial 20	com/starcor/hunan/widget/FilmListView$DecodeContext:<init>	(Lcom/starcor/hunan/widget/FilmListView$1;)V
          //   8: astore_1
          //   9: getstatic 24	com/starcor/hunan/widget/FilmListView:imageDownloadWorkers	[Ljava/lang/Thread;
          //   12: ifnull +232 -> 244
          //   15: getstatic 28	com/starcor/hunan/widget/FilmListView:imageDownloadHandlers	Ljava/util/ArrayList;
          //   18: astore_2
          //   19: aload_2
          //   20: monitorenter
          //   21: iconst_0
          //   22: istore_3
          //   23: getstatic 28	com/starcor/hunan/widget/FilmListView:imageDownloadHandlers	Ljava/util/ArrayList;
          //   26: invokevirtual 34	java/util/ArrayList:size	()I
          //   29: istore 5
          //   31: aconst_null
          //   32: astore 6
          //   34: iload_3
          //   35: iload 5
          //   37: if_icmpge +26 -> 63
          //   40: getstatic 28	com/starcor/hunan/widget/FilmListView:imageDownloadHandlers	Ljava/util/ArrayList;
          //   43: iload_3
          //   44: invokevirtual 38	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   47: checkcast 40	com/starcor/hunan/widget/FilmListView$imageDownloadHandler
          //   50: astore 6
          //   52: aload 6
          //   54: getfield 44	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:decodeTargets	Ljava/util/LinkedList;
          //   57: invokevirtual 50	java/util/LinkedList:isEmpty	()Z
          //   60: ifne +20 -> 80
          //   63: aload_2
          //   64: monitorexit
          //   65: aload 6
          //   67: ifnull +26 -> 93
          //   70: aload 6
          //   72: aload_1
          //   73: invokevirtual 54	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:doDecode	(Lcom/starcor/hunan/widget/FilmListView$DecodeContext;)Z
          //   76: pop
          //   77: goto -68 -> 9
          //   80: iinc 3 1
          //   83: goto -60 -> 23
          //   86: astore 4
          //   88: aload_2
          //   89: monitorexit
          //   90: aload 4
          //   92: athrow
          //   93: invokestatic 58	com/starcor/hunan/widget/FilmListView:access$100	()J
          //   96: getstatic 62	com/starcor/hunan/widget/FilmListView:cacheSweepTime	Ljava/lang/Long;
          //   99: invokevirtual 67	java/lang/Long:longValue	()J
          //   102: lsub
          //   103: invokestatic 73	java/lang/Math:abs	(J)J
          //   106: ldc2_w 74
          //   109: lcmp
          //   110: ifle +114 -> 224
          //   113: invokestatic 58	com/starcor/hunan/widget/FilmListView:access$100	()J
          //   116: invokestatic 79	java/lang/Long:valueOf	(J)Ljava/lang/Long;
          //   119: putstatic 62	com/starcor/hunan/widget/FilmListView:cacheSweepTime	Ljava/lang/Long;
          //   122: invokestatic 83	com/starcor/hunan/widget/FilmListView:access$200	()Ljava/io/File;
          //   125: invokevirtual 89	java/io/File:listFiles	()[Ljava/io/File;
          //   128: astore 8
          //   130: aload 8
          //   132: arraylength
          //   133: getstatic 93	com/starcor/hunan/widget/FilmListView:maxCachePicCount	I
          //   136: if_icmple +88 -> 224
          //   139: aload 8
          //   141: new 95	com/starcor/hunan/widget/FilmListView$2$1
          //   144: dup
          //   145: aload_0
          //   146: invokespecial 98	com/starcor/hunan/widget/FilmListView$2$1:<init>	(Lcom/starcor/hunan/widget/FilmListView$2;)V
          //   149: invokestatic 104	java/util/Arrays:sort	([Ljava/lang/Object;Ljava/util/Comparator;)V
          //   152: iconst_0
          //   153: istore 9
          //   155: iload 9
          //   157: aload 8
          //   159: arraylength
          //   160: getstatic 93	com/starcor/hunan/widget/FilmListView:maxCachePicCount	I
          //   163: isub
          //   164: if_icmpge +60 -> 224
          //   167: getstatic 24	com/starcor/hunan/widget/FilmListView:imageDownloadWorkers	[Ljava/lang/Thread;
          //   170: astore 10
          //   172: aload 10
          //   174: ifnull +50 -> 224
          //   177: getstatic 108	com/starcor/hunan/widget/FilmListView:TAG	Ljava/lang/String;
          //   180: new 110	java/lang/StringBuilder
          //   183: dup
          //   184: invokespecial 111	java/lang/StringBuilder:<init>	()V
          //   187: ldc 113
          //   189: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   192: aload 8
          //   194: iload 9
          //   196: aaload
          //   197: invokevirtual 121	java/io/File:getPath	()Ljava/lang/String;
          //   200: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   203: invokevirtual 124	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   206: invokestatic 130	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
          //   209: aload 8
          //   211: iload 9
          //   213: aaload
          //   214: invokevirtual 133	java/io/File:delete	()Z
          //   217: pop
          //   218: iinc 9 1
          //   221: goto -66 -> 155
          //   224: ldc2_w 134
          //   227: iconst_0
          //   228: invokestatic 141	java/lang/Thread:sleep	(JI)V
          //   231: goto -222 -> 9
          //   234: astore 7
          //   236: aload 7
          //   238: invokevirtual 144	java/lang/Exception:printStackTrace	()V
          //   241: goto -232 -> 9
          //   244: return
          //   245: astore 11
          //   247: goto -29 -> 218
          //
          // Exception table:
          //   from	to	target	type
          //   23	31	86	finally
          //   40	63	86	finally
          //   63	65	86	finally
          //   88	90	86	finally
          //   93	152	234	java/lang/Exception
          //   155	172	234	java/lang/Exception
          //   224	231	234	java/lang/Exception
          //   177	218	245	java/lang/Exception
        }
      });
      imageDecodeWorkers[j].setName("FilmListView Decoder [" + j + "]");
      imageDecodeWorkers[j].setPriority(1);
      imageDecodeWorkers[j].start();
    }
  }

  public FilmListView(Context paramContext)
  {
    super(paramContext);
    float[] arrayOfFloat1 = new float[8];
    arrayOfFloat1[0] = 0.0F;
    arrayOfFloat1[1] = 0.0F;
    arrayOfFloat1[2] = 0.0F;
    arrayOfFloat1[3] = 0.0F;
    arrayOfFloat1[4] = this.itemPicRoundRect;
    arrayOfFloat1[5] = this.itemPicRoundRect;
    arrayOfFloat1[6] = this.itemPicRoundRect;
    arrayOfFloat1[7] = this.itemPicRoundRect;
    this.updateInfoShape = new RoundRectShape(arrayOfFloat1, null, null);
    float[] arrayOfFloat2 = new float[8];
    arrayOfFloat2[0] = this.itemPicRoundRect;
    arrayOfFloat2[1] = this.itemPicRoundRect;
    arrayOfFloat2[2] = this.itemPicRoundRect;
    arrayOfFloat2[3] = this.itemPicRoundRect;
    arrayOfFloat2[4] = 0.0F;
    arrayOfFloat2[5] = 0.0F;
    arrayOfFloat2[6] = 0.0F;
    arrayOfFloat2[7] = 0.0F;
    this.defaultPosterShape = new RoundRectShape(arrayOfFloat2, null, null);
    this.focusImage = null;
    this.focusImageForWide = null;
    this.focusXScalar = 1.0F;
    this.focusYScalar = 1.0F;
    this.selectionBoxColor = -14533785;
    this.selectionBoxFocusColor = -8349721;
    this.selectionBoxSize = App.OperationFolat(4.0F);
    this.showAbstractInfo = false;
    this.flags = FLAGS_ALWAYS_SHOW_CURSOR;
    this.onScrollingListener = null;
    this.onClickListener = null;
    this.onSelectionChangedListener = null;
    this.checkRedraw = new Runnable()
    {
      public void run()
      {
        if (FilmListView.this.checkRedraw == null)
          return;
        switch (FilmListView.this.autoMoving)
        {
        default:
        case 1:
        case 2:
        }
        int j;
        int k;
        while (true)
        {
          int i3;
          synchronized (FilmListView.this.refreshRequest)
          {
            if (FilmListView.this.refreshRequest.intValue() > 0)
            {
              FilmListView.this.refreshRequest = Integer.valueOf(0);
              FilmListView.this.invalidate();
            }
            j = Math.max((int)((FilmListView.access$100() - FilmListView.this.lastDrawTime) / 30L), 1);
            if ((FilmListView.this.isCursorAnimationEnabled()) && (FilmListView.this.oldSelectedItem != FilmListView.this.selectedItem))
            {
              FilmListView localFilmListView5 = FilmListView.this;
              localFilmListView5.cursorAnimationStep = (j + localFilmListView5.cursorAnimationStep);
              if (FilmListView.this.cursorAnimationStep > 6)
                FilmListView.this.cursorAnimationStep = 6;
            }
            if (!FilmListView.this.isScrollAnimationEnabled())
              break label614;
            k = 0;
            int m = 0;
            if ((m >= j) || (FilmListView.this.targetTopPosition == FilmListView.this.topPosition))
              break;
            i3 = FilmListView.this.targetTopPosition - FilmListView.this.topPosition;
            if (Math.abs(i3 / 3) > 4)
            {
              i3 /= 3;
              FilmListView localFilmListView4 = FilmListView.this;
              localFilmListView4.topPosition = (i3 + localFilmListView4.topPosition);
              k += i3;
              m++;
              continue;
              FilmListView localFilmListView6 = FilmListView.this;
              int i4 = 1 + localFilmListView6.movingCounter;
              localFilmListView6.movingCounter = i4;
              if (i4 % 20 != 0)
                continue;
              FilmListView.this.cursorUp();
              continue;
              FilmListView localFilmListView1 = FilmListView.this;
              int i = 1 + localFilmListView1.movingCounter;
              localFilmListView1.movingCounter = i;
              if (i % 20 != 0)
                continue;
              FilmListView.this.cursorDown();
            }
          }
          if (Math.abs(i3) > 4)
            i3 = (int)(Math.signum(i3) * 4);
        }
        int n = 0;
        int i1 = 0;
        if ((i1 < j) && (FilmListView.this.targetLeftPosition != FilmListView.this.leftPosition))
        {
          int i2 = FilmListView.this.targetLeftPosition - FilmListView.this.leftPosition;
          if (Math.abs(i2 / 3) > 4)
            i2 /= 3;
          while (true)
          {
            FilmListView localFilmListView3 = FilmListView.this;
            localFilmListView3.leftPosition = (i2 + localFilmListView3.leftPosition);
            n += i2;
            i1++;
            break;
            if (Math.abs(i2) > 4)
              i2 = (int)(Math.signum(i2) * 4);
          }
        }
        FilmListView.this.scrollTo(FilmListView.this.leftPosition, FilmListView.this.topPosition);
        if ((n != 0) || (k != 0))
          FilmListView.this.onViewScroll(FilmListView.this.leftPosition, FilmListView.this.topPosition);
        if (FilmListView.this.isFocused())
        {
          if (FilmListView.this.textOffset != 0)
            break label687;
          FilmListView.this.textOffset = FilmListView.this.intervalScrollTextOffset;
        }
        while (true)
        {
          FilmListView.this.postDelayed(FilmListView.this.checkRedraw, 30L);
          return;
          label614: FilmListView.this.topPosition = FilmListView.this.targetTopPosition;
          FilmListView.this.leftPosition = FilmListView.this.targetLeftPosition;
          FilmListView.this.onViewScroll(FilmListView.this.leftPosition, FilmListView.this.topPosition);
          FilmListView.this.scrollTo(FilmListView.this.leftPosition, FilmListView.this.topPosition);
          break;
          label687: if (FilmListView.this.textOffset < 0)
          {
            FilmListView localFilmListView2 = FilmListView.this;
            localFilmListView2.textOffset = (1 + localFilmListView2.textOffset);
            if (FilmListView.this.textOffset >= 0)
            {
              FilmListView.this.textOffset = 1;
              FilmListView.this.requestUpdate();
            }
          }
        }
      }
    };
    this.onDrawItemListener = null;
    this.itemPicProcessor = null;
    this.itemTextColorizer = null;
    this.textColorBuffer = null;
    this.mContext = paramContext;
    init();
  }

  public FilmListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    float[] arrayOfFloat1 = new float[8];
    arrayOfFloat1[0] = 0.0F;
    arrayOfFloat1[1] = 0.0F;
    arrayOfFloat1[2] = 0.0F;
    arrayOfFloat1[3] = 0.0F;
    arrayOfFloat1[4] = this.itemPicRoundRect;
    arrayOfFloat1[5] = this.itemPicRoundRect;
    arrayOfFloat1[6] = this.itemPicRoundRect;
    arrayOfFloat1[7] = this.itemPicRoundRect;
    this.updateInfoShape = new RoundRectShape(arrayOfFloat1, null, null);
    float[] arrayOfFloat2 = new float[8];
    arrayOfFloat2[0] = this.itemPicRoundRect;
    arrayOfFloat2[1] = this.itemPicRoundRect;
    arrayOfFloat2[2] = this.itemPicRoundRect;
    arrayOfFloat2[3] = this.itemPicRoundRect;
    arrayOfFloat2[4] = 0.0F;
    arrayOfFloat2[5] = 0.0F;
    arrayOfFloat2[6] = 0.0F;
    arrayOfFloat2[7] = 0.0F;
    this.defaultPosterShape = new RoundRectShape(arrayOfFloat2, null, null);
    this.focusImage = null;
    this.focusImageForWide = null;
    this.focusXScalar = 1.0F;
    this.focusYScalar = 1.0F;
    this.selectionBoxColor = -14533785;
    this.selectionBoxFocusColor = -8349721;
    this.selectionBoxSize = App.OperationFolat(4.0F);
    this.showAbstractInfo = false;
    this.flags = FLAGS_ALWAYS_SHOW_CURSOR;
    this.onScrollingListener = null;
    this.onClickListener = null;
    this.onSelectionChangedListener = null;
    this.checkRedraw = new Runnable()
    {
      public void run()
      {
        if (FilmListView.this.checkRedraw == null)
          return;
        switch (FilmListView.this.autoMoving)
        {
        default:
        case 1:
        case 2:
        }
        int j;
        int k;
        while (true)
        {
          int i3;
          synchronized (FilmListView.this.refreshRequest)
          {
            if (FilmListView.this.refreshRequest.intValue() > 0)
            {
              FilmListView.this.refreshRequest = Integer.valueOf(0);
              FilmListView.this.invalidate();
            }
            j = Math.max((int)((FilmListView.access$100() - FilmListView.this.lastDrawTime) / 30L), 1);
            if ((FilmListView.this.isCursorAnimationEnabled()) && (FilmListView.this.oldSelectedItem != FilmListView.this.selectedItem))
            {
              FilmListView localFilmListView5 = FilmListView.this;
              localFilmListView5.cursorAnimationStep = (j + localFilmListView5.cursorAnimationStep);
              if (FilmListView.this.cursorAnimationStep > 6)
                FilmListView.this.cursorAnimationStep = 6;
            }
            if (!FilmListView.this.isScrollAnimationEnabled())
              break label614;
            k = 0;
            int m = 0;
            if ((m >= j) || (FilmListView.this.targetTopPosition == FilmListView.this.topPosition))
              break;
            i3 = FilmListView.this.targetTopPosition - FilmListView.this.topPosition;
            if (Math.abs(i3 / 3) > 4)
            {
              i3 /= 3;
              FilmListView localFilmListView4 = FilmListView.this;
              localFilmListView4.topPosition = (i3 + localFilmListView4.topPosition);
              k += i3;
              m++;
              continue;
              FilmListView localFilmListView6 = FilmListView.this;
              int i4 = 1 + localFilmListView6.movingCounter;
              localFilmListView6.movingCounter = i4;
              if (i4 % 20 != 0)
                continue;
              FilmListView.this.cursorUp();
              continue;
              FilmListView localFilmListView1 = FilmListView.this;
              int i = 1 + localFilmListView1.movingCounter;
              localFilmListView1.movingCounter = i;
              if (i % 20 != 0)
                continue;
              FilmListView.this.cursorDown();
            }
          }
          if (Math.abs(i3) > 4)
            i3 = (int)(Math.signum(i3) * 4);
        }
        int n = 0;
        int i1 = 0;
        if ((i1 < j) && (FilmListView.this.targetLeftPosition != FilmListView.this.leftPosition))
        {
          int i2 = FilmListView.this.targetLeftPosition - FilmListView.this.leftPosition;
          if (Math.abs(i2 / 3) > 4)
            i2 /= 3;
          while (true)
          {
            FilmListView localFilmListView3 = FilmListView.this;
            localFilmListView3.leftPosition = (i2 + localFilmListView3.leftPosition);
            n += i2;
            i1++;
            break;
            if (Math.abs(i2) > 4)
              i2 = (int)(Math.signum(i2) * 4);
          }
        }
        FilmListView.this.scrollTo(FilmListView.this.leftPosition, FilmListView.this.topPosition);
        if ((n != 0) || (k != 0))
          FilmListView.this.onViewScroll(FilmListView.this.leftPosition, FilmListView.this.topPosition);
        if (FilmListView.this.isFocused())
        {
          if (FilmListView.this.textOffset != 0)
            break label687;
          FilmListView.this.textOffset = FilmListView.this.intervalScrollTextOffset;
        }
        while (true)
        {
          FilmListView.this.postDelayed(FilmListView.this.checkRedraw, 30L);
          return;
          label614: FilmListView.this.topPosition = FilmListView.this.targetTopPosition;
          FilmListView.this.leftPosition = FilmListView.this.targetLeftPosition;
          FilmListView.this.onViewScroll(FilmListView.this.leftPosition, FilmListView.this.topPosition);
          FilmListView.this.scrollTo(FilmListView.this.leftPosition, FilmListView.this.topPosition);
          break;
          label687: if (FilmListView.this.textOffset < 0)
          {
            FilmListView localFilmListView2 = FilmListView.this;
            localFilmListView2.textOffset = (1 + localFilmListView2.textOffset);
            if (FilmListView.this.textOffset >= 0)
            {
              FilmListView.this.textOffset = 1;
              FilmListView.this.requestUpdate();
            }
          }
        }
      }
    };
    this.onDrawItemListener = null;
    this.itemPicProcessor = null;
    this.itemTextColorizer = null;
    this.textColorBuffer = null;
    setDrawPlaceHolder(paramAttributeSet.getAttributeBooleanValue("starcor", "draw_place_holder", false));
    this.mContext = paramContext;
    init();
  }

  public FilmListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    float[] arrayOfFloat1 = new float[8];
    arrayOfFloat1[0] = 0.0F;
    arrayOfFloat1[1] = 0.0F;
    arrayOfFloat1[2] = 0.0F;
    arrayOfFloat1[3] = 0.0F;
    arrayOfFloat1[4] = this.itemPicRoundRect;
    arrayOfFloat1[5] = this.itemPicRoundRect;
    arrayOfFloat1[6] = this.itemPicRoundRect;
    arrayOfFloat1[7] = this.itemPicRoundRect;
    this.updateInfoShape = new RoundRectShape(arrayOfFloat1, null, null);
    float[] arrayOfFloat2 = new float[8];
    arrayOfFloat2[0] = this.itemPicRoundRect;
    arrayOfFloat2[1] = this.itemPicRoundRect;
    arrayOfFloat2[2] = this.itemPicRoundRect;
    arrayOfFloat2[3] = this.itemPicRoundRect;
    arrayOfFloat2[4] = 0.0F;
    arrayOfFloat2[5] = 0.0F;
    arrayOfFloat2[6] = 0.0F;
    arrayOfFloat2[7] = 0.0F;
    this.defaultPosterShape = new RoundRectShape(arrayOfFloat2, null, null);
    this.focusImage = null;
    this.focusImageForWide = null;
    this.focusXScalar = 1.0F;
    this.focusYScalar = 1.0F;
    this.selectionBoxColor = -14533785;
    this.selectionBoxFocusColor = -8349721;
    this.selectionBoxSize = App.OperationFolat(4.0F);
    this.showAbstractInfo = false;
    this.flags = FLAGS_ALWAYS_SHOW_CURSOR;
    this.onScrollingListener = null;
    this.onClickListener = null;
    this.onSelectionChangedListener = null;
    this.checkRedraw = new Runnable()
    {
      public void run()
      {
        if (FilmListView.this.checkRedraw == null)
          return;
        switch (FilmListView.this.autoMoving)
        {
        default:
        case 1:
        case 2:
        }
        int j;
        int k;
        while (true)
        {
          int i3;
          synchronized (FilmListView.this.refreshRequest)
          {
            if (FilmListView.this.refreshRequest.intValue() > 0)
            {
              FilmListView.this.refreshRequest = Integer.valueOf(0);
              FilmListView.this.invalidate();
            }
            j = Math.max((int)((FilmListView.access$100() - FilmListView.this.lastDrawTime) / 30L), 1);
            if ((FilmListView.this.isCursorAnimationEnabled()) && (FilmListView.this.oldSelectedItem != FilmListView.this.selectedItem))
            {
              FilmListView localFilmListView5 = FilmListView.this;
              localFilmListView5.cursorAnimationStep = (j + localFilmListView5.cursorAnimationStep);
              if (FilmListView.this.cursorAnimationStep > 6)
                FilmListView.this.cursorAnimationStep = 6;
            }
            if (!FilmListView.this.isScrollAnimationEnabled())
              break label614;
            k = 0;
            int m = 0;
            if ((m >= j) || (FilmListView.this.targetTopPosition == FilmListView.this.topPosition))
              break;
            i3 = FilmListView.this.targetTopPosition - FilmListView.this.topPosition;
            if (Math.abs(i3 / 3) > 4)
            {
              i3 /= 3;
              FilmListView localFilmListView4 = FilmListView.this;
              localFilmListView4.topPosition = (i3 + localFilmListView4.topPosition);
              k += i3;
              m++;
              continue;
              FilmListView localFilmListView6 = FilmListView.this;
              int i4 = 1 + localFilmListView6.movingCounter;
              localFilmListView6.movingCounter = i4;
              if (i4 % 20 != 0)
                continue;
              FilmListView.this.cursorUp();
              continue;
              FilmListView localFilmListView1 = FilmListView.this;
              int i = 1 + localFilmListView1.movingCounter;
              localFilmListView1.movingCounter = i;
              if (i % 20 != 0)
                continue;
              FilmListView.this.cursorDown();
            }
          }
          if (Math.abs(i3) > 4)
            i3 = (int)(Math.signum(i3) * 4);
        }
        int n = 0;
        int i1 = 0;
        if ((i1 < j) && (FilmListView.this.targetLeftPosition != FilmListView.this.leftPosition))
        {
          int i2 = FilmListView.this.targetLeftPosition - FilmListView.this.leftPosition;
          if (Math.abs(i2 / 3) > 4)
            i2 /= 3;
          while (true)
          {
            FilmListView localFilmListView3 = FilmListView.this;
            localFilmListView3.leftPosition = (i2 + localFilmListView3.leftPosition);
            n += i2;
            i1++;
            break;
            if (Math.abs(i2) > 4)
              i2 = (int)(Math.signum(i2) * 4);
          }
        }
        FilmListView.this.scrollTo(FilmListView.this.leftPosition, FilmListView.this.topPosition);
        if ((n != 0) || (k != 0))
          FilmListView.this.onViewScroll(FilmListView.this.leftPosition, FilmListView.this.topPosition);
        if (FilmListView.this.isFocused())
        {
          if (FilmListView.this.textOffset != 0)
            break label687;
          FilmListView.this.textOffset = FilmListView.this.intervalScrollTextOffset;
        }
        while (true)
        {
          FilmListView.this.postDelayed(FilmListView.this.checkRedraw, 30L);
          return;
          label614: FilmListView.this.topPosition = FilmListView.this.targetTopPosition;
          FilmListView.this.leftPosition = FilmListView.this.targetLeftPosition;
          FilmListView.this.onViewScroll(FilmListView.this.leftPosition, FilmListView.this.topPosition);
          FilmListView.this.scrollTo(FilmListView.this.leftPosition, FilmListView.this.topPosition);
          break;
          label687: if (FilmListView.this.textOffset < 0)
          {
            FilmListView localFilmListView2 = FilmListView.this;
            localFilmListView2.textOffset = (1 + localFilmListView2.textOffset);
            if (FilmListView.this.textOffset >= 0)
            {
              FilmListView.this.textOffset = 1;
              FilmListView.this.requestUpdate();
            }
          }
        }
      }
    };
    this.onDrawItemListener = null;
    this.itemPicProcessor = null;
    this.itemTextColorizer = null;
    this.textColorBuffer = null;
    setDrawPlaceHolder(paramAttributeSet.getAttributeBooleanValue("starcor", "draw_place_holder", false));
    this.mContext = paramContext;
    init();
  }

  private int _getInitialTextOffset()
  {
    if (this.initialScrollTextOffset == 0)
      return 1;
    return this.initialScrollTextOffset;
  }

  private void drawColorText(Canvas paramCanvas, String paramString, int[] paramArrayOfInt, float paramFloat1, float paramFloat2, Paint paramPaint)
  {
    if ((paramString.length() < 1) || (paramString.length() > paramArrayOfInt.length))
      return;
    float[] arrayOfFloat = new float[paramString.length()];
    paramPaint.getTextWidths(paramString, arrayOfFloat);
    int i = 0;
    int j = paramString.length();
    do
    {
      int k = i;
      int m = paramArrayOfInt[k];
      for (float f = arrayOfFloat[k]; ; f += arrayOfFloat[i])
      {
        i++;
        if ((i >= j) || (m != paramArrayOfInt[i]))
          break;
      }
      paramPaint.setColor(m);
      paramCanvas.drawText(paramString, k, i, paramFloat1, paramFloat2, paramPaint);
      paramFloat1 += f;
    }
    while (i < j);
  }

  private void drawFocusText(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, int paramInt1, int paramInt2, ViewItem paramViewItem)
  {
    if ((paramRect1 == null) || (paramRect2 == null))
      return;
    this.textPaint.setColor(this.activeItemTextColor);
    this.textBGPaint.setColor(this.activeItemTextBGColor);
    Rect localRect1;
    int k;
    label300: Rect localRect2;
    label398: Rect localRect3;
    float f;
    if (paramViewItem.coverBmp != null)
    {
      localRect1 = new Rect(paramRect2);
      this.bmpPaint.setColorFilter(null);
      if ((paramViewItem.coverWidth != paramViewItem.coverBmp.getWidth()) || (paramViewItem.coverHeight != paramViewItem.coverBmp.getHeight()))
      {
        RectF localRectF = new RectF();
        int i = XulUtils.roundToInt(paramViewItem.coverBmp.getWidth() * this._xScalar);
        int j = XulUtils.roundToInt(paramViewItem.coverBmp.getHeight() * this._yScalar);
        localRectF.set(localRect1.right - i - paramViewItem.coverXOffset, localRect1.top + paramViewItem.coverYOffset, localRect1.right - paramViewItem.coverXOffset, j + (localRect1.top + paramViewItem.coverYOffset));
        localRect1.set(0, 0, paramViewItem.coverBmp.getWidth(), paramViewItem.coverBmp.getHeight());
        paramCanvas.drawBitmap(paramViewItem.coverBmp, localRect1, localRectF, this.bmpPaint);
      }
    }
    else
    {
      paramCanvas.save();
      k = (int)(0.23F * (paramRect2.bottom - paramRect2.top));
      if (paramRect1.bottom - paramRect2.bottom <= k)
        break label627;
      paramCanvas.clipRect(new Rect(paramRect2.left, paramRect2.top, paramRect2.right, paramRect1.bottom));
      localRect2 = new Rect(paramRect2.left, paramRect2.bottom, paramRect2.right, k + paramRect2.bottom);
      if (this.itemPicRoundRect <= 0.5D)
        break label661;
      this.shapeDrawable.setShape(this.updateInfoShape);
      this.shapeDrawable.getPaint().setColor(this.activeItemTextBGColor);
      this.shapeDrawable.setBounds(localRect2);
      paramCanvas.save();
      paramCanvas.clipRect(localRect2);
      this.shapeDrawable.draw(paramCanvas);
      paramCanvas.restore();
      localRect3 = new Rect();
      if (paramViewItem.title != null)
        this.textPaint.getTextBounds(paramViewItem.title, 0, paramViewItem.title.length(), localRect3);
      Paint.FontMetrics localFontMetrics = this.textPaint.getFontMetrics();
      int m = (int)Math.ceil(localFontMetrics.bottom - localFontMetrics.top);
      f = paramRect2.bottom + (k - m) / 2 - localFontMetrics.top;
      if (localRect3.width() <= paramRect1.width())
        break label674;
      drawItemText(paramCanvas, paramViewItem, paramRect1.left - paramInt2, f, this.textPaint);
      if (paramRect1.left - paramInt2 + localRect3.width() + this.scrollTextSpacing < paramRect1.right)
        drawItemText(paramCanvas, paramViewItem, paramRect1.left - paramInt2 + localRect3.width() + this.scrollTextSpacing, f, this.textPaint);
    }
    while (true)
    {
      paramCanvas.restore();
      return;
      paramCanvas.drawBitmap(paramViewItem.coverBmp, localRect1.right - paramViewItem.coverWidth - paramViewItem.coverXOffset, localRect1.top + paramViewItem.coverYOffset, this.bmpPaint);
      break;
      label627: paramCanvas.clipRect(new Rect(paramRect2.left, paramRect2.top, paramRect2.right, k + paramRect2.bottom));
      break label300;
      label661: paramCanvas.drawRect(localRect2, this.textBGPaint);
      break label398;
      label674: drawItemText(paramCanvas, paramViewItem, paramRect1.left + (paramRect1.width() - localRect3.width()) / 2, f, this.textPaint);
    }
  }

  private void drawItemText(Canvas paramCanvas, ViewItem paramViewItem, float paramFloat1, float paramFloat2, Paint paramPaint)
  {
    if (paramViewItem.title == null)
      return;
    if (this.itemTextColorizer != null)
    {
      if ((this.textColorBuffer == null) || (this.textColorBuffer.length < paramViewItem.title.length()))
        this.textColorBuffer = new int[0xFFFFFE0 & 31 + paramViewItem.title.length()];
      int i = paramPaint.getColor();
      for (int j = -1 + paramViewItem.title.length(); j >= 0; j--)
        this.textColorBuffer[j] = i;
      if (this.itemTextColorizer.getItemTextColors(paramViewItem.idx, paramViewItem.itemData, paramViewItem.title, this.textColorBuffer, i))
      {
        drawColorText(paramCanvas, paramViewItem.title, this.textColorBuffer, paramFloat1, paramFloat2, paramPaint);
        paramPaint.setColor(i);
        return;
      }
    }
    paramCanvas.drawText(paramViewItem.title, 0, paramViewItem.title.length(), paramFloat1, paramFloat2, paramPaint);
  }

  public static String getPicCachePath()
  {
    if (localCachePath != null)
      return localCachePath.toString();
    return "";
  }

  private boolean isAlwaysScaleFocus()
  {
    return (this.flags & FLAGS_ALWAYS_SCALE_FOCUS) == FLAGS_ALWAYS_SCALE_FOCUS;
  }

  private boolean isCursorAlwaysVisible()
  {
    return (this.flags & FLAGS_ALWAYS_SHOW_CURSOR) == FLAGS_ALWAYS_SHOW_CURSOR;
  }

  private boolean isCursorFixedPos()
  {
    return (this.flags & FLAGS_FIXED_CURSOR_POS) == FLAGS_FIXED_CURSOR_POS;
  }

  private boolean isCursorLooping()
  {
    return (this.flags & FLAGS_LOOP_CURSOR) == FLAGS_LOOP_CURSOR;
  }

  private boolean isFadeInEnabled()
  {
    return (this.flags & FLAGS_NO_FADE_IN_EFFECT) != FLAGS_NO_FADE_IN_EFFECT;
  }

  private boolean isHorizontalScrollEnabled()
  {
    return (this.flags & FLAGS_HORIZONTAL_SCROLL) == FLAGS_HORIZONTAL_SCROLL;
  }

  private boolean isItemLoadable(ViewItem paramViewItem)
  {
    return (paramViewItem.rcItem.top >= this.yOffset + this.topPosition - this.maxPreloadLines * this.lineInterval) && (paramViewItem.rcItem.bottom <= this.yOffset + this.topPosition + getHeight() + this.maxPreloadLines * this.lineInterval);
  }

  private boolean isItemVisible(ViewItem paramViewItem)
  {
    int i = getHeight();
    return (paramViewItem.rcItem.bottom > this.targetTopPosition) && (paramViewItem.rcItem.top < i + this.targetTopPosition);
  }

  private boolean isPlaceHolderVisible()
  {
    return (this.flags & FLAGS_SHOW_PLACE_HOLDER) == FLAGS_SHOW_PLACE_HOLDER;
  }

  private boolean isScrollAnimationEnabled()
  {
    return (this.flags & FLAGS_NO_SCROLL_ANIMATION) != FLAGS_NO_SCROLL_ANIMATION;
  }

  private void loadDefaultBkg()
  {
    this.defaultBkg = loadBkgBitmap(this.itemPicWidth, this.itemPicHeight, "default_filmlist_item_bkg.jpg");
  }

  private void loadDefaultWideBkg()
  {
    this.defaultWideBkg = loadBkgBitmap(this.itemPicWidth + this.columnInterval, this.itemPicHeight, "wide_filmlist_item_bkg.jpg");
  }

  private void moveToNextItem(Rect paramRect)
  {
    if (1 + paramRect.left / this.columnInterval < this.itemsPerLine)
    {
      paramRect.offset(this.columnInterval, 0);
      return;
    }
    paramRect.offsetTo(this.xOffsetLeft + this.itemLeftPadding, paramRect.top + this.lineInterval);
  }

  private void onClick()
  {
    if ((this.onClickListener != null) && (this.selectedItem < this.items.size()))
    {
      ViewItem localViewItem = (ViewItem)this.items.get(this.selectedItem);
      this.onClickListener.onClick(localViewItem.idx, localViewItem.itemData);
    }
  }

  private void onDrawItem(Canvas paramCanvas, ViewItem paramViewItem, Rect paramRect1, Rect paramRect2, ListViewDrawingPhase paramListViewDrawingPhase)
  {
    if (this.onDrawItemListener == null)
      return;
    this.onDrawItemListener.onDrawItem(paramViewItem.idx, paramViewItem.itemData, paramCanvas, paramRect1, paramRect2, paramListViewDrawingPhase);
  }

  private void onScrolling(int paramInt)
  {
    if (this.onScrollingListener != null)
      this.onScrollingListener.onScrolling(getFirstVisibleLine(), getSelectedItem(), paramInt);
  }

  private void onSelectionChanged()
  {
    if ((this.onSelectionChangedListener != null) && (this.selectedItem < this.items.size()))
    {
      ViewItem localViewItem = (ViewItem)this.items.get(this.selectedItem);
      this.onSelectionChangedListener.onSelectionChanged(localViewItem.idx, localViewItem.itemData);
    }
  }

  private void onTitleChange(boolean paramBoolean)
  {
    if (this.onTitleChangeListener != null)
      this.onTitleChangeListener.onTitleChange(paramBoolean);
  }

  private void onViewScroll(int paramInt1, int paramInt2)
  {
    if (this.onViewScrollListener != null)
      this.onViewScrollListener.onViewScroll(paramInt1, paramInt2);
  }

  private void pageDown()
  {
    int i = getHeight() / this.lineInterval;
    int j = this.targetTopPosition;
    for (int k = 0; ; k++)
      if ((k >= i) || (!cursorDown()))
      {
        int m = Math.abs(j - this.targetTopPosition) / this.lineInterval;
        int n = Math.abs(((ViewItem)this.items.get(-1 + getItemCount())).rcItem.top - ((ViewItem)this.items.get(getSelectedItem())).rcItem.top) / this.lineInterval;
        int i2;
        for (int i1 = Math.min(i - m, n); ; i1 = i2)
        {
          i2 = i1 - 1;
          if (i1 <= 0)
            break;
          scrollDown();
        }
      }
  }

  private void pageUp()
  {
    int i = getHeight() / this.lineInterval;
    int j = this.targetTopPosition;
    for (int k = 0; ; k++)
      if ((k >= i) || (!cursorUp()))
      {
        int m = Math.abs(j - this.targetTopPosition) / this.lineInterval;
        int n = Math.abs(((ViewItem)this.items.get(0)).rcItem.top - ((ViewItem)this.items.get(getSelectedItem())).rcItem.top) / this.lineInterval;
        int i2;
        for (int i1 = Math.min(i - m, n); ; i1 = i2)
        {
          i2 = i1 - 1;
          if (i1 <= 0)
            break;
          scrollUp();
        }
      }
  }

  static int registerImageDownloadHandler(imageDownloadHandler paramimageDownloadHandler)
  {
    synchronized (imageDownloadHandlers)
    {
      imageDownloadHandlers.add(paramimageDownloadHandler);
      handlerChangedCounter = 1 + handlerChangedCounter;
      int i = -1 + imageDownloadHandlers.size();
      return i;
    }
  }

  private void reloadItemPic(int paramInt)
  {
    if (this.items.size() <= paramInt)
      return;
    ViewItem localViewItem = (ViewItem)this.items.get(paramInt);
    if (localViewItem.bmp != null)
    {
      BitmapTools.recycleBitmap(localViewItem.bmp);
      localViewItem.bmp = null;
    }
    localViewItem.reload = true;
    localViewItem.loading = false;
  }

  private void requestUpdate(ViewItem paramViewItem)
  {
    if (isItemVisible(paramViewItem))
    {
      if (isFadeInEnabled());
      for (paramViewItem.fadeInStep = 1; ; paramViewItem.fadeInStep = 0)
      {
        requestUpdate();
        return;
      }
    }
    paramViewItem.fadeInStep = 0;
  }

  private boolean scrollDown()
  {
    if (this.items.isEmpty())
      return false;
    if ((((ViewItem)this.items.get(-1 + this.items.size())).rcItem.bottom - (this.targetTopPosition + this.lineInterval)) / this.lineInterval < (getHeight() - 2 * this.yOffset) / this.lineInterval)
      return false;
    this.targetTopPosition += this.lineInterval;
    onScrolling(3);
    return true;
  }

  private boolean scrollUp()
  {
    if (this.targetTopPosition <= 0)
      return false;
    this.targetTopPosition -= this.lineInterval;
    if (this.targetTopPosition < 0)
      this.targetTopPosition = 0;
    onScrolling(1);
    return true;
  }

  private boolean scrollXtoVisible()
  {
    if (this.items.isEmpty())
      return false;
    if (!isHorizontalScrollEnabled())
      return false;
    int i = (getWidth() - (this.xOffsetLeft + this.xOffsetRight)) / this.columnInterval;
    Rect localRect = ((ViewItem)this.items.get(this.selectedItem)).rcItem;
    if (localRect.left - this.targetLeftPosition >= i * this.columnInterval + this.xOffsetLeft);
    for (this.targetLeftPosition = (localRect.right - i * this.columnInterval - this.xOffsetLeft); ; this.targetLeftPosition = (localRect.left - this.xOffsetLeft))
    {
      return true;
      if (localRect.left >= this.targetLeftPosition + this.xOffsetLeft)
        break;
    }
    return false;
  }

  private static long timestamp()
  {
    return System.nanoTime() / 1000000L;
  }

  static void unregisterImageDownloadHandler(imageDownloadHandler paramimageDownloadHandler)
  {
    synchronized (imageDownloadHandlers)
    {
      imageDownloadHandlers.remove(paramimageDownloadHandler);
      paramimageDownloadHandler.terminate();
      handlerChangedCounter = 1 + handlerChangedCounter;
      return;
    }
  }

  ViewItem acquirePendingItem()
  {
    int i = getFirstVisibleItem();
    int j = i + this.maxVisibleLines * this.itemsPerLine;
    int k = this.maxPreloadLines * this.itemsPerLine;
    int m;
    int n;
    synchronized (this.items)
    {
      m = this.items.size();
      n = i;
      if ((n >= j) || (n >= m))
        break label454;
    }
    synchronized ((ViewItem)this.items.get(n))
    {
      if (isItemLoadable(???))
      {
        if ((???.reload == true) && (???.loading != true))
        {
          ???.loading = true;
          return ???;
        }
        if ((???.bmp == null) && (???.loading != true))
        {
          ???.loading = true;
          return ???;
          localObject1 = finally;
          throw localObject1;
        }
      }
    }
    while (true)
    {
      int i1;
      if (i1 < k)
      {
        int i2 = -1 + (i - i1);
        int i3 = j + i1;
        if ((i2 >= 0) && (i2 < m));
        synchronized ((ViewItem)this.items.get(i2))
        {
          if (!isItemLoadable(???))
            break label460;
          if ((???.reload == true) && (???.loading != true))
          {
            ???.loading = true;
            return ???;
          }
          if ((???.bmp == null) && (???.loading != true))
          {
            ???.loading = true;
            return ???;
          }
          if ((i3 < 0) || (i3 >= m))
            break label460;
          synchronized ((ViewItem)this.items.get(i3))
          {
            if (isItemLoadable(???));
          }
        }
        if ((???.reload == true) && (???.loading != true))
        {
          ???.loading = true;
          return ???;
        }
        if ((???.bmp == null) && (???.loading != true))
        {
          ???.loading = true;
          return ???;
        }
      }
      else
      {
        return null;
        n++;
        break;
        label454: i1 = 0;
        continue;
      }
      label460: i1++;
    }
  }

  public void addFlags(int paramInt)
  {
    this.flags = (paramInt | this.flags);
  }

  public int addItem(String paramString1, String paramString2, Object paramObject)
  {
    return addItem(paramString1, paramString2, paramObject, null, 0, 0);
  }

  public int addItem(String paramString1, String paramString2, Object paramObject, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    return addItem(false, paramString1, paramString2, paramObject, paramBitmap, paramInt1, paramInt2);
  }

  public int addItem(boolean paramBoolean, String paramString1, String paramString2, Object paramObject, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    ViewItem localViewItem1 = new ViewItem();
    localViewItem1.picUrl = paramString2;
    localViewItem1.title = paramString1;
    localViewItem1.itemData = paramObject;
    localViewItem1.isWide = paramBoolean;
    synchronized (this.items)
    {
      int i = this.items.size();
      if (paramBoolean);
      for (int j = 2 * this.columnInterval; ; j = this.columnInterval)
      {
        if (paramBoolean)
          this.wideItemCount = (1 + this.wideItemCount);
        if (i > 0)
          break;
        k = this.xOffsetLeft;
        m = this.yOffset;
        if (k + j > this.xOffsetLeft + this.columnInterval * this.itemsPerLine)
        {
          k = this.xOffsetLeft;
          m += this.lineInterval;
        }
        localViewItem1.idx = i;
        localViewItem1.rcItem = new Rect(k, m, k + j, m + this.lineInterval);
        localViewItem1.coverBmp = paramBitmap;
        localViewItem1.coverXOffset = paramInt1;
        localViewItem1.coverYOffset = paramInt2;
        this.items.add(localViewItem1);
        if ((localViewItem1.rcItem.bottom > this.topPosition) && (localViewItem1.rcItem.top < this.topPosition + getHeight()) && (localViewItem1.rcItem.left > this.leftPosition) && (localViewItem1.rcItem.right < this.leftPosition + getWidth()))
          requestUpdate();
        handlerChangedCounter = 1 + handlerChangedCounter;
        return i;
      }
      ViewItem localViewItem2 = (ViewItem)this.items.get(i - 1);
      int k = localViewItem2.rcItem.right;
      int m = localViewItem2.rcItem.top;
    }
  }

  public int addWideItem(String paramString1, String paramString2, Object paramObject, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    return addItem(true, paramString1, paramString2, paramObject, paramBitmap, paramInt1, paramInt2);
  }

  public boolean cursorDown()
  {
    if (this.items.isEmpty())
      return false;
    this.oldSelectedItem = this.selectedItem;
    Rect localRect = ((ViewItem)this.items.get(this.selectedItem)).rcItem;
    int i = 999999999;
    int j = this.selectedItem;
    int k = Math.min(this.selectedItem + this.itemsPerLine, -1 + this.items.size());
    if (k > this.selectedItem)
    {
      ViewItem localViewItem2 = (ViewItem)this.items.get(k);
      if (localViewItem2.rcItem.top == localRect.top);
      while (true)
      {
        k--;
        break;
        int i3 = Math.abs(localViewItem2.rcItem.left - localRect.left) + this.columnInterval * Math.abs(localViewItem2.rcItem.top - localRect.top);
        if (i3 <= i)
        {
          i = i3;
          j = k;
        }
      }
    }
    if (j == this.selectedItem)
      if (isCursorLooping())
      {
        int m = -1;
        int n = Math.min(this.items.size(), this.itemsPerLine);
        int i1 = 0;
        if (i1 < n)
        {
          ViewItem localViewItem1 = (ViewItem)this.items.get(i1);
          if (m == -1)
          {
            m = localViewItem1.rcItem.top;
            label243: if (localViewItem1.rcItem.top < localRect.top)
              break label280;
          }
          while (true)
          {
            i1++;
            break;
            if (localViewItem1.rcItem.top == m)
              break label243;
            continue;
            label280: int i2 = Math.abs(localViewItem1.rcItem.left - localRect.left) + this.columnInterval * Math.abs(localViewItem1.rcItem.top - localRect.top);
            if (i2 <= i)
            {
              i = i2;
              j = i1;
            }
          }
        }
        if (j == this.selectedItem)
          return false;
        this.targetTopPosition = 0;
      }
    while (true)
    {
      this.selectedItem = j;
      this.textOffset = _getInitialTextOffset();
      requestUpdate();
      onSelectionChanged();
      Logger.i("this.getHeight() =" + getHeight());
      if (((ViewItem)this.items.get(this.selectedItem)).rcItem.bottom - this.topPosition <= getHeight())
        break;
      return scrollDown();
      return false;
      if ((getTotalLines() > 1) && (getCursorLine() == 0) && (this.onTitleChangeListener != null))
        this.onTitleChangeListener.onTitleChange(false);
    }
    return true;
  }

  public boolean cursorLeft()
  {
    if (this.items.isEmpty())
      return false;
    this.oldSelectedItem = this.selectedItem;
    Rect localRect = ((ViewItem)this.items.get(this.selectedItem)).rcItem;
    int i = 10000;
    int j = this.selectedItem;
    int k = Math.max(this.selectedItem - this.itemsPerLine, 0);
    if (k < this.selectedItem)
    {
      ViewItem localViewItem2 = (ViewItem)this.items.get(k);
      if (localViewItem2.rcItem.top != localRect.top);
      while (true)
      {
        k++;
        break;
        int i2 = Math.abs(localViewItem2.rcItem.left - localRect.left);
        if (i2 < i)
        {
          i = i2;
          j = k;
        }
      }
    }
    if (j == this.selectedItem)
      if (isCursorLooping())
      {
        int m = 0;
        int n = Math.min(this.selectedItem + this.itemsPerLine, -1 + this.items.size());
        if (n > this.selectedItem)
        {
          ViewItem localViewItem1 = (ViewItem)this.items.get(n);
          if (localViewItem1.rcItem.top != localRect.top);
          while (true)
          {
            n--;
            break;
            int i1 = Math.abs(localViewItem1.rcItem.left - localRect.left);
            if (i1 > m)
            {
              m = i1;
              j = n;
            }
          }
        }
        if (j == this.selectedItem)
          return false;
      }
      else
      {
        return false;
      }
    this.selectedItem = j;
    this.textOffset = _getInitialTextOffset();
    scrollXtoVisible();
    requestUpdate();
    onSelectionChanged();
    return true;
  }

  public boolean cursorRight()
  {
    if (this.items.isEmpty())
      return false;
    this.oldSelectedItem = this.selectedItem;
    Rect localRect = ((ViewItem)this.items.get(this.selectedItem)).rcItem;
    int i = 10000;
    int j = this.selectedItem;
    int k = Math.min(this.selectedItem + this.itemsPerLine, -1 + this.items.size());
    if (k > this.selectedItem)
    {
      ViewItem localViewItem2 = (ViewItem)this.items.get(k);
      if (localViewItem2.rcItem.top != localRect.top);
      while (true)
      {
        k--;
        break;
        int i2 = Math.abs(localViewItem2.rcItem.left - localRect.left);
        if (i2 < i)
        {
          i = i2;
          j = k;
        }
      }
    }
    if (j == this.selectedItem)
      if (isCursorLooping())
      {
        int m = 0;
        int n = Math.max(this.selectedItem - this.itemsPerLine, 0);
        if (n < this.selectedItem)
        {
          ViewItem localViewItem1 = (ViewItem)this.items.get(n);
          if (localViewItem1.rcItem.top != localRect.top);
          while (true)
          {
            n++;
            break;
            int i1 = Math.abs(localViewItem1.rcItem.left - localRect.left);
            if (i1 > m)
            {
              m = i1;
              j = n;
            }
          }
        }
        if (j == this.selectedItem)
          return false;
      }
      else
      {
        return false;
      }
    this.selectedItem = j;
    this.textOffset = _getInitialTextOffset();
    scrollXtoVisible();
    requestUpdate();
    onSelectionChanged();
    return true;
  }

  public boolean cursorUp()
  {
    if (this.items.isEmpty())
      return false;
    this.oldSelectedItem = this.selectedItem;
    Rect localRect = ((ViewItem)this.items.get(this.selectedItem)).rcItem;
    int i = 999999999;
    int j = this.selectedItem;
    int i1;
    label103: ViewItem localViewItem2;
    if ((this.wideItemCount == 1) && ((this.selectedItem == 5) || (this.selectedItem == 6)))
    {
      j = 0;
      if (j != this.selectedItem)
        break label596;
      if (!isCursorLooping())
        break label594;
      int n = -1;
      i1 = -1 + this.items.size();
      if ((i1 < 0) || (i1 < this.items.size() - this.itemsPerLine))
        break label467;
      localViewItem2 = (ViewItem)this.items.get(i1);
      if (n == -1)
        n = localViewItem2.rcItem.top;
      if (localViewItem2.rcItem.top > localRect.top)
        break label434;
    }
    while (true)
    {
      i1--;
      break label103;
      if ((this.wideItemCount == 2) && (this.selectedItem <= 7))
      {
        if ((this.selectedItem == 4) || (this.selectedItem == 5))
        {
          j = 0;
          break;
        }
        if ((this.selectedItem != 6) && (this.selectedItem != 7))
          break;
        j = 1;
        break;
      }
      if ((this.wideItemCount == 3) && (this.selectedItem <= 8))
      {
        if ((this.selectedItem == 3) || (this.selectedItem == 4))
        {
          j = 0;
          break;
        }
        if ((this.selectedItem == 5) || (this.selectedItem == 6))
        {
          j = 1;
          break;
        }
        if ((this.selectedItem != 7) && (this.selectedItem != 8))
          break;
        j = 2;
        break;
      }
      int k = Math.max(this.selectedItem - this.itemsPerLine, 0);
      label335: ViewItem localViewItem1;
      if (k < this.selectedItem)
      {
        localViewItem1 = (ViewItem)this.items.get(k);
        if (localViewItem1.rcItem.top != localRect.top)
          break label379;
      }
      while (true)
      {
        k++;
        break label335;
        break;
        label379: int m = Math.abs(localViewItem1.rcItem.left - localRect.left) + this.columnInterval * Math.abs(localViewItem1.rcItem.top - localRect.top);
        if (m <= i)
        {
          i = m;
          j = k;
        }
      }
      label434: int i3 = Math.abs(localViewItem2.rcItem.left - localRect.left);
      if (i3 <= i)
      {
        i = i3;
        j = i1;
      }
    }
    label467: if (j == this.selectedItem)
      return false;
    int i2 = getHeight() / this.lineInterval;
    this.targetTopPosition = (((ViewItem)this.items.get(j)).rcItem.top - this.yOffset - (i2 - 1) * this.lineInterval);
    if (this.targetTopPosition < 0)
      this.targetTopPosition = 0;
    while (true)
    {
      this.selectedItem = j;
      this.textOffset = _getInitialTextOffset();
      requestUpdate();
      onSelectionChanged();
      if (((ViewItem)this.items.get(this.selectedItem)).rcItem.top - this.topPosition >= this.yOffset)
        break;
      return scrollUp();
      label594: return false;
      label596: if ((getTotalLines() > 1) && (getCursorLine() == 1) && (this.onTitleChangeListener != null))
        this.onTitleChangeListener.onTitleChange(true);
    }
    return true;
  }

  public void destroy()
  {
    this.checkRedraw = null;
    unregisterImageDownloadHandler(this.downloadHandler);
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      ViewItem localViewItem = (ViewItem)localIterator.next();
      if (localViewItem.bmp != null)
      {
        BitmapTools.recycleBitmap(localViewItem.bmp);
        localViewItem.bmp = null;
      }
    }
    this.items.clear();
    if (this.itemPicProcessor != null)
    {
      this.itemPicProcessor.destroy();
      this.itemPicProcessor = null;
    }
    if (this.textBkg != null)
      this.textBkg = null;
    super.setOnKeyListener(null);
    this.onClickListener = null;
    this.onScrollingListener = null;
    this.onSelectionChangedListener = null;
  }

  void drawMultiLineText(Canvas paramCanvas, String paramString, float paramFloat1, float paramFloat2, Paint paramPaint)
  {
    String[] arrayOfString = paramString.split("\n");
    float f1 = -paramPaint.ascent() + paramPaint.descent();
    if ((paramPaint.getStyle() == Paint.Style.FILL_AND_STROKE) || (paramPaint.getStyle() == Paint.Style.STROKE))
      f1 += paramPaint.getStrokeWidth();
    float f2 = f1 * 0.1F;
    for (int i = 0; i < arrayOfString.length; i++)
      paramCanvas.drawText(arrayOfString[i], paramFloat1, paramFloat2 + (f1 + f2) * i, paramPaint);
  }

  protected void drawPlaceHolderPic(Canvas paramCanvas, Rect paramRect, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.bmpPaint.setAlpha(255);
    Rect localRect1 = new Rect(paramRect);
    localRect1.inset(-this.backgroundShadowSize, -this.backgroundShadowSize);
    Rect localRect2 = new Rect(localRect1);
    Bitmap localBitmap;
    if (paramBoolean1)
    {
      if (this.defaultWideBkg == null)
        loadDefaultWideBkg();
      if (this.defaultWideBkg != null)
        localBitmap = this.defaultWideBkg;
    }
    while (true)
    {
      localRect2.offsetTo((localBitmap.getWidth() - localRect1.width()) / 2, (localBitmap.getHeight() - localRect1.height()) / 2);
      paramCanvas.drawBitmap(localBitmap, localRect2, localRect1, this.bmpPaint);
      return;
      if (this.defaultBkg == null)
        loadDefaultBkg();
      localBitmap = this.defaultBkg;
      continue;
      if (this.defaultBkg == null)
        loadDefaultBkg();
      localBitmap = this.defaultBkg;
    }
  }

  public int findItemIdx(ItemComparable paramItemComparable)
  {
    if (this.items.isEmpty())
      return -1;
    for (int i = -1 + getItemCount(); i >= 0; i--)
      if (paramItemComparable.compare(((ViewItem)this.items.get(i)).itemData))
        return ((ViewItem)this.items.get(i)).idx;
    return -1;
  }

  public int getColumns()
  {
    if (this.itemsPerLine <= 0)
      this.itemsPerLine = 5;
    return this.itemsPerLine;
  }

  public int getCursorLine()
  {
    if (this.items.isEmpty());
    while (this.selectedItem >= this.items.size())
      return 0;
    return (-1 + (((ViewItem)this.items.get(this.selectedItem)).rcItem.top + this.lineInterval) - this.yOffset) / this.lineInterval;
  }

  public int getFirstVisibleIndex()
  {
    if (this.columnInterval > 0)
      return this.targetLeftPosition / this.columnInterval;
    return 0;
  }

  protected int getFirstVisibleItem()
  {
    int i = (this.topPosition - this.yOffset) / this.lineInterval * this.itemsPerLine;
    if (i < 0)
      i = 0;
    if (i >= this.items.size())
      i = this.items.size();
    int j = 0;
    while (i - j > 1)
    {
      int k = (j + i) / 2;
      Rect localRect = ((ViewItem)this.items.get(k)).rcItem;
      if (localRect.top > this.topPosition - this.yOffset)
        i = k;
      else if (localRect.bottom <= this.topPosition - this.yOffset)
        j = k;
      else if (localRect.left > this.leftPosition - this.xOffsetLeft)
        i = k;
      else
        j = k;
    }
    return j;
  }

  public int getFirstVisibleLine()
  {
    return this.targetTopPosition / this.lineInterval;
  }

  public int getFlags()
  {
    return this.flags;
  }

  int getItemByXY(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + this.xOffsetLeft;
    int j = paramInt2 + this.yOffset + this.topPosition;
    Rect localRect = new Rect();
    int k = 0;
    ViewItem localViewItem;
    if (k < this.items.size())
    {
      localViewItem = (ViewItem)this.items.get(k);
      if (localViewItem != null);
    }
    else
    {
      return -1;
    }
    if (localViewItem.isPlaceholder);
    while (true)
    {
      k++;
      break;
      localRect.set(localViewItem.rcItem.left + this.itemLeftPadding, localViewItem.rcItem.top + this.itemTopPadding, localViewItem.rcItem.right - this.itemRightPadding, localViewItem.rcItem.bottom - this.itemBottomPadding);
      boolean bool1 = localViewItem.isWide;
      if (k == this.selectedItem);
      for (boolean bool2 = true; getPicRectFromItemRect(bool1, localRect, bool2).contains(i, j); bool2 = false)
        return k;
    }
  }

  public int getItemCount()
  {
    return this.items.size();
  }

  protected Rect getPicRectFromItemRect(boolean paramBoolean1, Rect paramRect, boolean paramBoolean2)
  {
    if (paramBoolean1);
    for (int i = this.itemPicWidth + this.columnInterval; ; i = this.itemPicWidth)
    {
      Rect localRect = new Rect(paramRect.left, paramRect.top, i + paramRect.left, paramRect.top + this.itemPicHeight);
      localRect.offset((paramRect.width() - i) / 2, 0);
      if ((paramBoolean2) && ((Math.abs(this.focusXScalar - 1.0F) > 0.01F) || (Math.abs(this.focusYScalar - 1.0F) > 0.01F)))
      {
        int j = Math.round(localRect.width() * (this.focusXScalar - 1.0F) / 2.0F);
        int k = Math.round(localRect.height() * (this.focusYScalar - 1.0F) / 2.0F);
        this.textOffsetY = k;
        localRect.left -= j;
        localRect.right = (j + localRect.right);
        localRect.top -= k;
        localRect.bottom = (k + localRect.bottom);
      }
      return localRect;
    }
  }

  public int getScrollTextIntervalDelay()
  {
    return 30 * Math.abs(this.intervalScrollTextOffset);
  }

  public int getScrollTextSpacing()
  {
    return this.scrollTextSpacing;
  }

  public int getScrollTextStartDelay()
  {
    return 30 * Math.abs(this.initialScrollTextOffset);
  }

  public int getScrollXRange()
  {
    int i = Math.min(this.itemsPerLine, this.items.size()) * this.columnInterval - (getWidth() - (this.xOffsetLeft + this.xOffsetRight));
    if (i > 0)
      return i;
    return 0;
  }

  public int getScrollYRange()
  {
    if (this.items.isEmpty())
      return 0;
    return getTotalLines() * this.lineInterval - (getWidth() - this.yOffset);
  }

  public int getSelectedItem()
  {
    return this.selectedItem;
  }

  public int getTotalLines()
  {
    if (this.items.isEmpty())
      return 0;
    return ((ViewItem)this.items.get(-1 + this.items.size())).rcItem.bottom / this.lineInterval;
  }

  public boolean hasWideItem(Object paramObject)
  {
    synchronized (this.items)
    {
      Iterator localIterator = this.items.iterator();
      while (localIterator.hasNext())
      {
        ViewItem localViewItem = (ViewItem)localIterator.next();
        if ((localViewItem.isWide) && (localViewItem.itemData.equals(paramObject)))
          return true;
      }
      return false;
    }
  }

  void init()
  {
    setFocusable(true);
    setFocusableInTouchMode(true);
    this.selectionPaint.setStyle(Paint.Style.STROKE);
    this.selectionPaint.setAntiAlias(true);
    this.selectionPaint.setColor(this.selectionBoxColor);
    this.selectionPaint.setStrokeWidth(this.selectionBoxSize);
    this.focusedSelectionPaint.setStyle(Paint.Style.STROKE);
    this.focusedSelectionPaint.setAntiAlias(true);
    this.focusedSelectionPaint.setColor(this.selectionBoxFocusColor);
    this.focusedSelectionPaint.setStrokeWidth(this.selectionBoxSize);
    this.focusShadowPaint.setStyle(Paint.Style.STROKE);
    this.focusShadowPaint.setShadowLayer(App.Operation(8.0F), 0.5F, 1.5F, -16777216);
    this.focusShadowPaint.setAntiAlias(true);
    this.focusShadowPaint.setStrokeWidth(6.5F);
    this.focusShadowPaint.setColor(-16777216);
    this.textPaint.setColor(-7829368);
    this.textPaint.setTextSize(20.0F);
    this.textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.textPaint.setShadowLayer(2.5F, 0.0F, 0.0F, -16777216);
    this.textPaint.setAntiAlias(true);
    this.bmpPaint.setFilterBitmap(true);
    this.bmpPaint.setAntiAlias(true);
    this.cMatrix.set(this.unSelect);
    this.firstLinePaint.setColor(-1);
    this.firstLinePaint.setTextSize(App.Operation(22.0F));
    this.firstLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.firstLinePaint.setShadowLayer(2.5F, 0.0F, 0.0F, -16777216);
    this.firstLinePaint.setAntiAlias(true);
    this.secondLinePaint.setColor(-7829368);
    this.secondLinePaint.setTextSize(App.Operation(20.0F));
    this.secondLinePaint.setShadowLayer(2.5F, 0.0F, 0.0F, -16777216);
    this.secondLinePaint.setAntiAlias(true);
    setOnKeyListener(this);
    setOnTouchListener(this);
    setOnFocusChangeListener(this);
    this.downloadHandler = new imageDownloadHandler(null);
    registerImageDownloadHandler(this.downloadHandler);
    this.checkRedraw.run();
  }

  protected boolean isCursorAnimationEnabled()
  {
    return (this.flags & FLAGS_ENABLE_CURSOR_ANIMATION) == FLAGS_ENABLE_CURSOR_ANIMATION;
  }

  public Bitmap loadBkgBitmap(int paramInt1, int paramInt2, String paramString)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Integer.valueOf(paramInt1);
    arrayOfObject[2] = Integer.valueOf(paramInt2);
    String str = String.format("%s,%dx%d", arrayOfObject);
    Bitmap localBitmap1 = (Bitmap)_cachedBkgBmp.get(str);
    if (localBitmap1 != null)
      return localBitmap1;
    try
    {
      float f = App.OperationFolat(2.0F);
      localBitmap1 = BitmapTools.decodeStream(getResources().getAssets().open(paramString, 1), Bitmap.Config.ARGB_8888, paramInt1, paramInt2);
      Bitmap localBitmap2 = Bitmap.createBitmap(paramInt1 + 2 * this.backgroundShadowSize, paramInt2 + 2 * this.backgroundShadowSize, Bitmap.Config.ARGB_8888);
      int i = (localBitmap1.getWidth() - localBitmap2.getWidth()) / 2;
      int j = (localBitmap1.getHeight() - localBitmap2.getHeight()) / 2;
      Canvas localCanvas = new Canvas(localBitmap2);
      localCanvas.translate(-i, -j);
      RectF localRectF = new RectF(this.backgroundShadowSize, this.backgroundShadowSize, paramInt1 + this.backgroundShadowSize, paramInt2 + this.backgroundShadowSize);
      localRectF.offset(i, j);
      Rect localRect = new Rect(this.backgroundShadowSize, this.backgroundShadowSize, paramInt1 + this.backgroundShadowSize, paramInt2 + this.backgroundShadowSize);
      localRect.offset(i, j);
      Paint localPaint = new Paint(3);
      this.defaultShapeDrawable.setShape(this.defaultPosterShape);
      this.defaultShapeDrawable.getPaint().setColor(-278896544);
      this.defaultShapeDrawable.setBounds(localRect);
      localCanvas.save();
      localCanvas.clipRect(localRectF);
      this.defaultShapeDrawable.draw(localCanvas);
      localCanvas.restore();
      localPaint.reset();
      localPaint.setAntiAlias(true);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localPaint.setShader(new BitmapShader(localBitmap1, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
      localCanvas.drawRoundRect(localRectF, f, f, localPaint);
      localCanvas.setBitmap(null);
      _cachedBkgBmp.put(str, localBitmap2);
      return localBitmap2;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localBitmap1;
  }

  protected void onDetachedFromWindow()
  {
    destroy();
    super.onDetachedFromWindow();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    long l1 = timestamp();
    long l2 = l1 - this.lastDrawTime;
    this.lastDrawTime = l1;
    int i = getFirstVisibleItem();
    int j = this.items.size() - i;
    int k = getHeight();
    int m = getWidth();
    Rect localRect1 = null;
    Paint.FontMetrics localFontMetrics = this.textPaint.getFontMetrics();
    int n = (int)Math.ceil(localFontMetrics.bottom - localFontMetrics.top);
    int i1 = 0;
    Rect localRect2 = null;
    Rect localRect3 = null;
    int i2 = 0;
    int i3 = 0;
    ViewItem localViewItem5;
    if (i3 < j)
    {
      localViewItem5 = (ViewItem)this.items.get(i + i3);
      if (localViewItem5 != null);
    }
    else
    {
      if (!isPlaceHolderVisible())
        break label2653;
      if (localRect1 != null)
        break label2644;
      localRect1 = new Rect(this.xOffsetLeft + this.itemLeftPadding, this.yOffset + this.itemTopPadding, this.columnInterval - this.itemRightPadding, this.lineInterval - this.itemBottomPadding);
    }
    while (true)
    {
      if (localRect1.top - this.topPosition >= k)
        break label2653;
      drawPlaceHolderPic(paramCanvas, getPicRectFromItemRect(false, localRect1, false), false, false);
      moveToNextItem(localRect1);
      continue;
      if (localViewItem5.isPlaceholder);
      boolean bool3;
      label349: 
      do
      {
        i3++;
        break;
        if ((i + i3 == this.selectedItem) && ((isFocused()) || (isAlwaysScaleFocus())));
        for (bool3 = true; ; bool3 = false)
        {
          localRect1 = new Rect(localViewItem5.rcItem.left + this.itemLeftPadding, localViewItem5.rcItem.top + this.itemTopPadding, localViewItem5.rcItem.right - this.itemRightPadding, localViewItem5.rcItem.bottom - this.itemBottomPadding);
          if (localRect1.top - this.topPosition <= k)
            break label349;
          j = i3;
          break;
        }
      }
      while ((localRect1.right < this.leftPosition) || (localRect1.left > m + this.leftPosition));
      Rect localRect9 = getPicRectFromItemRect(localViewItem5.isWide, localRect1, bool3);
      Rect localRect10 = new Rect(localRect9);
      onDrawItem(paramCanvas, localViewItem5, localRect1, localRect10, ListViewDrawingPhase.ITEM_PRE_DRAW_PHASE);
      drawPlaceHolderPic(paramCanvas, localRect10, localViewItem5.isWide, bool3);
      if (localViewItem5.fadeInStep != 0)
      {
        localViewItem5.fadeInStep = ((int)(localViewItem5.fadeInStep + (1L + (l2 - 30L)) / 30L));
        if (localViewItem5.fadeInStep >= 8)
          localViewItem5.fadeInStep = 0;
      }
      label487: Rect localRect12;
      label702: int i9;
      label773: label921: Rect localRect17;
      label1023: Rect localRect13;
      float f1;
      int i23;
      label1304: String str5;
      if (localViewItem5.bmp == null)
      {
        if (localViewItem5.coverBmp != null)
        {
          localRect12 = new Rect(localRect9);
          ListViewDrawingPhase localListViewDrawingPhase1 = ListViewDrawingPhase.ITEM_COVER_PRE_DRAW_PHASE;
          onDrawItem(paramCanvas, localViewItem5, localRect1, localRect12, localListViewDrawingPhase1);
          if ((localViewItem5.coverWidth == localViewItem5.coverBmp.getWidth()) && (localViewItem5.coverHeight == localViewItem5.coverBmp.getHeight()))
            break label2390;
          RectF localRectF = new RectF();
          int i7 = XulUtils.roundToInt(localViewItem5.coverBmp.getWidth() * this._xScalar);
          int i8 = XulUtils.roundToInt(localViewItem5.coverBmp.getHeight() * this._yScalar);
          localRectF.set(localRect12.right - i7 - localViewItem5.coverXOffset, localRect12.top + localViewItem5.coverYOffset, localRect12.right - localViewItem5.coverXOffset, i8 + (localRect12.top + localViewItem5.coverYOffset));
          localRect12.set(0, 0, localViewItem5.coverBmp.getWidth(), localViewItem5.coverBmp.getHeight());
          paramCanvas.drawBitmap(localViewItem5.coverBmp, localRect12, localRectF, this.bmpPaint);
          ListViewDrawingPhase localListViewDrawingPhase2 = ListViewDrawingPhase.ITEM_COVER_POST_DRAW_PHASE;
          onDrawItem(paramCanvas, localViewItem5, localRect1, localRect12, localListViewDrawingPhase2);
        }
        onDrawItem(paramCanvas, localViewItem5, localRect1, localRect10, ListViewDrawingPhase.ITEM_POST_DRAW_PHASE);
        paramCanvas.save();
        if ((!bool3) || (!isFocused()))
          break label2436;
        this.textPaint.setColor(this.activeItemTextColor);
        this.textBGPaint.setColor(this.activeItemTextBGColor);
        if ((bool3) && ((Math.abs(this.focusXScalar - 1.0F) > 0.01F) || (Math.abs(this.focusYScalar - 1.0F) > 0.01F)))
        {
          Math.round(localRect9.width() * (this.focusXScalar - 1.0F) / 2.0F);
          i1 = Math.round(localRect9.height() * (this.focusYScalar - 1.0F) / 2.0F);
        }
        i9 = (int)(0.23F * (localRect10.bottom - localRect10.top));
        if (bool3)
          break label2512;
        if (localRect1.bottom - localRect10.bottom <= i9)
          break label2461;
        paramCanvas.clipRect(new Rect(localRect10.left, localRect10.top, localRect10.right, localRect1.bottom));
        localRect17 = new Rect(localRect10.left, localRect10.bottom, localRect10.right, i9 + localRect10.bottom);
        if (this.itemPicRoundRect <= 0.5D)
          break label2499;
        this.shapeDrawable.setShape(this.updateInfoShape);
        this.shapeDrawable.getPaint().setColor(this.inactiveItemTextBGColor);
        this.shapeDrawable.setBounds(localRect17);
        paramCanvas.save();
        paramCanvas.clipRect(localRect17);
        this.shapeDrawable.draw(paramCanvas);
        paramCanvas.restore();
        localRect13 = new Rect();
        if (localViewItem5.title != null)
          this.textPaint.getTextBounds(localViewItem5.title, 0, localViewItem5.title.length(), localRect13);
        f1 = localRect10.bottom + (i9 - n) / 2 - localFontMetrics.top;
        paramCanvas.save();
        paramCanvas.clipRect(5 + localRect10.left, localViewItem5.rcItem.top, -5 + localRect10.right, localViewItem5.rcItem.bottom);
        if (localRect13.width() <= localRect10.width())
          break label2564;
        int i22 = this.textOffset;
        i23 = 0;
        if (i22 > 0)
        {
          int i24 = i + i3;
          int i25 = this.selectedItem;
          i23 = 0;
          if (i24 == i25)
          {
            i23 = this.textOffset;
            i2 = i23;
            this.textOffset = ((int)(this.textOffset + (1L + (1L + (l2 - 30L)) / 30L)));
            if (this.textOffset > localRect13.width() + this.scrollTextSpacing)
              this.textOffset = 0;
            invalidate();
          }
        }
        if (!bool3)
        {
          if (localRect10.left - i23 + localRect13.width() + this.scrollTextSpacing >= localRect10.right)
            break label2537;
          drawItemText(paramCanvas, localViewItem5, 5 + localRect10.left - i23 + localRect13.width() + this.scrollTextSpacing, f1, this.textPaint);
        }
        paramCanvas.restore();
        paramCanvas.restore();
        if ((localViewItem5.itemData instanceof SearchListItem))
        {
          this.infoPaint.setColor(-1);
          this.infoPaint.setTextSize(App.Operation(14.0F));
          this.infoPaint.setTypeface(Typeface.SANS_SERIF);
          this.infoPaint.setStyle(Paint.Style.FILL_AND_STROKE);
          String str4 = ((SearchListItem)localViewItem5.itemData).corner_mark;
          if (!TextUtils.isEmpty(str4))
          {
            str5 = GlobalLogic.getInstance().getVideoQualityValue(str4);
            if ((!str5.equals("3D")) && (!str5.equals("4K")))
              break label2607;
          }
        }
      }
      label2390: label2537: float f2;
      for (int i18 = localRect10.left + App.Operation(39.0F); ; i18 = localRect10.left + App.Operation(21.0F + f2))
      {
        if (!TextUtils.isEmpty(str5))
        {
          int i19 = localRect10.left + App.Operation(8.0F);
          int i20 = localRect10.top + App.Operation(5.0F);
          int i21 = localRect10.top + App.Operation(32.0F);
          Rect localRect16 = new Rect(i19, i20, i18, i21);
          this.shapeDrawable.setShape(this.markShape);
          this.shapeDrawable.getPaint().setColor(-939524096);
          this.shapeDrawable.setBounds(localRect16);
          paramCanvas.save();
          paramCanvas.clipRect(localRect16);
          this.shapeDrawable.draw(paramCanvas);
          paramCanvas.restore();
          paramCanvas.drawText(str5, localRect10.left + App.Operation(15.0F), localRect10.top + App.Operation(23.0F), this.infoPaint);
        }
        if (!(localViewItem5.itemData instanceof FilmListItem))
          break;
        this.infoPaint.setTypeface(Typeface.SANS_SERIF);
        this.infoPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        FilmListItem localFilmListItem = (FilmListItem)localViewItem5.itemData;
        boolean bool4 = "recom".equals(localFilmListItem.corner_mark);
        String str1 = localFilmListItem.media_mode;
        if (!TextUtils.isEmpty(str1))
        {
          String str3 = GlobalLogic.getInstance().getMediaModeString(str1);
          if (!TextUtils.isEmpty(str3))
          {
            int i14 = localRect10.left + App.Operation(48.0F);
            if ((str3.equals("3D")) || (str3.equals("4K")))
              i14 = localRect10.left + App.Operation(40.0F);
            int i15 = localRect10.left + App.Operation(8.0F);
            int i16 = localRect10.top + App.Operation(8.0F);
            int i17 = localRect10.top + App.Operation(32.0F);
            Rect localRect15 = new Rect(i15, i16, i14, i17);
            this.shapeDrawable.setShape(this.markShape);
            this.shapeDrawable.getPaint().setColor(-939524096);
            this.shapeDrawable.setBounds(localRect15);
            paramCanvas.save();
            paramCanvas.clipRect(localRect15);
            this.shapeDrawable.draw(paramCanvas);
            paramCanvas.restore();
            this.infoPaint.setColor(-1);
            this.infoPaint.setTextSize(App.Operation(14.0F));
            paramCanvas.drawText(str3, localRect10.left + App.Operation(15.0F), localRect10.top + App.Operation(25.0F), this.infoPaint);
          }
        }
        if (!this.showAbstractInfo)
          break;
        String str2 = localFilmListItem.updateInfo;
        if (((str2 != null) && (str2.length() > 0)) || (bool4))
        {
          Rect localRect14 = new Rect(localRect10.left, localRect10.bottom - App.Operation(35.0F), localRect10.right, 1 + localRect10.bottom);
          this.shapeDrawable.setShape(this.updateInfoShape);
          this.shapeDrawable.getPaint().setColor(-671088640);
          this.shapeDrawable.setBounds(localRect14);
          paramCanvas.save();
          paramCanvas.clipRect(localRect14);
          this.shapeDrawable.draw(paramCanvas);
          paramCanvas.restore();
        }
        this.infoPaint.setTextSize(App.Operation(18.0F));
        this.infoPaint.setShadowLayer(2.5F, 0.0F, 0.0F, -16777216);
        this.infoPaint.setAntiAlias(true);
        if ((str2 != null) && (str2.length() > 0))
        {
          int i12 = localRect10.left + App.Operation(5.0F);
          int i13 = localRect10.bottom - App.Operation(10.0F);
          this.infoPaint.setColor(-7829368);
          paramCanvas.drawText(str2, i12, i13, this.infoPaint);
        }
        if (!bool4)
          break;
        int i10 = localRect10.right - App.Operation(25.0F);
        int i11 = localRect10.bottom - App.Operation(10.0F);
        this.infoPaint.setColor(-256);
        paramCanvas.drawText("荐", i10, i11, this.infoPaint);
        break;
        if (localViewItem5.fadeInStep != 0)
        {
          this.bmpPaint.setAlpha(32 * localViewItem5.fadeInStep);
          Rect localRect18 = new Rect(localRect10);
          localRect18.offsetTo((localViewItem5.bmp.getWidth() - localRect10.width()) / 2, (localViewItem5.bmp.getHeight() - localRect10.height()) / 2);
          paramCanvas.drawBitmap(localViewItem5.bmp, localRect18, localRect10, this.bmpPaint);
          localViewItem5.fadeInStep = (1 + localViewItem5.fadeInStep);
          if (localViewItem5.fadeInStep >= 8)
            localViewItem5.fadeInStep = 0;
          invalidate();
          break label487;
        }
        Rect localRect11 = new Rect(localRect10);
        localRect11.offsetTo((localViewItem5.bmp.getWidth() - localRect10.width()) / 2, (localViewItem5.bmp.getHeight() - localRect10.height()) / 2);
        paramCanvas.drawBitmap(localViewItem5.bmp, localRect11, localRect10, this.bmpPaint);
        break label487;
        paramCanvas.drawBitmap(localViewItem5.coverBmp, localRect12.right - localViewItem5.coverWidth - localViewItem5.coverXOffset, localRect12.top + localViewItem5.coverYOffset, this.bmpPaint);
        break label702;
        label2436: this.textPaint.setColor(this.inactiveItemTextColor);
        this.textBGPaint.setColor(this.inactiveItemTextBGColor);
        break label773;
        label2461: paramCanvas.clipRect(new Rect(localRect10.left, localRect10.top, localRect10.right, i9 + localRect10.bottom));
        break label921;
        label2499: paramCanvas.drawRect(localRect17, this.textBGPaint);
        break label1023;
        label2512: localRect2 = new Rect(localRect1);
        localRect3 = new Rect(localRect10);
        break label1023;
        drawItemText(paramCanvas, localViewItem5, 5 + localRect10.left - i23, f1, this.textPaint);
        break label1304;
        label2564: if (bool3)
          break label1304;
        drawItemText(paramCanvas, localViewItem5, 5 + localRect10.left + (localRect10.width() - localRect13.width()) / 2, f1, this.textPaint);
        break label1304;
        label2607: str5.length();
        f2 = this.infoPaint.measureText(str5);
      }
      label2644: moveToNextItem(localRect1);
    }
    label2653: ViewItem localViewItem3;
    if (this.selectedItem < this.items.size())
    {
      localViewItem3 = (ViewItem)this.items.get(this.selectedItem);
      if ((localViewItem3 != null) && (!localViewItem3.isPlaceholder))
        break label2777;
    }
    while (true)
    {
      for (int i4 = i + j + this.maxPreloadLines * this.itemsPerLine; ; i4++)
      {
        int i5 = this.items.size();
        if (i4 >= i5)
          break;
        ViewItem localViewItem2 = (ViewItem)this.items.get(i4);
        if (localViewItem2.bmp != null)
        {
          BitmapTools.recycleBitmap(localViewItem2.bmp);
          localViewItem2.bmp = null;
        }
        localViewItem2.fadeInStep = 0;
      }
      label2777: Rect localRect4 = new Rect(localViewItem3.rcItem.left + this.itemLeftPadding, localViewItem3.rcItem.top + this.itemTopPadding, localViewItem3.rcItem.right - this.itemRightPadding, localViewItem3.rcItem.bottom - this.itemBottomPadding);
      if ((localRect4.right >= this.leftPosition) && (localRect4.left <= m + this.leftPosition))
      {
        boolean bool1 = localViewItem3.isWide;
        boolean bool2;
        label2889: Rect localRect6;
        if ((isFocused()) || (isAlwaysScaleFocus()))
        {
          bool2 = true;
          Rect localRect5 = getPicRectFromItemRect(bool1, localRect4, bool2);
          localRect6 = new Rect(localRect5);
          if (!isCursorFixedPos())
            break label3035;
          localRect6.offsetTo(this.leftPosition + this.xOffsetLeft + this.itemLeftPadding + localRect6.left - localRect4.left, this.topPosition + this.yOffset + this.itemTopPadding + localRect6.top - localRect4.top);
        }
        while (true)
        {
          if ((this.flags & FLAGS_NO_CURSOR) == FLAGS_NO_CURSOR)
            break label3307;
          if ((this.focusImage == null) || (this.focusImageForWide == null))
            break label3309;
          if (!isFocused())
            break;
          drawFocusText(paramCanvas, localRect2, localRect3, i1, i2, localViewItem3);
          break;
          bool2 = false;
          break label2889;
          label3035: if ((isCursorAnimationEnabled()) && (this.oldSelectedItem != this.selectedItem))
          {
            ViewItem localViewItem4 = (ViewItem)this.items.get(this.oldSelectedItem);
            Rect localRect7 = new Rect(localViewItem4.rcItem.left + this.itemLeftPadding, localViewItem4.rcItem.top + this.itemTopPadding, localViewItem4.rcItem.right - this.itemRightPadding, localViewItem4.rcItem.bottom - this.itemBottomPadding);
            if (localRect7 != null)
            {
              Rect localRect8 = getPicRectFromItemRect(localViewItem4.isWide, localRect7, true);
              localRect6.left = ((localRect8.left * (6 - this.cursorAnimationStep) + localRect6.left * this.cursorAnimationStep) / 6);
              localRect6.right = ((localRect8.right * (6 - this.cursorAnimationStep) + localRect6.right * this.cursorAnimationStep) / 6);
              localRect6.top = ((localRect8.top * (6 - this.cursorAnimationStep) + localRect6.top * this.cursorAnimationStep) / 6);
              localRect6.bottom = ((localRect8.bottom * (6 - this.cursorAnimationStep) + localRect6.bottom * this.cursorAnimationStep) / 6);
            }
            if (this.cursorAnimationStep == 6)
            {
              this.oldSelectedItem = this.selectedItem;
              this.cursorAnimationStep = 0;
            }
            else
            {
              requestUpdate();
            }
          }
        }
        label3307: continue;
        label3309: if (isFocused())
          drawFocusText(paramCanvas, localRect2, localRect3, i1, i2, localViewItem3);
        else if (isCursorAlwaysVisible())
          drawFocusText(paramCanvas, localRect2, localRect3, i1, i2, localViewItem3);
      }
    }
    if (i > this.maxPreloadLines * this.itemsPerLine)
      for (int i6 = -1 + (i - this.maxPreloadLines * this.itemsPerLine); i6 >= 0; i6--)
      {
        ViewItem localViewItem1 = (ViewItem)this.items.get(i6);
        if (localViewItem1.bmp != null)
        {
          BitmapTools.recycleBitmap(localViewItem1.bmp);
          localViewItem1.bmp = null;
        }
        localViewItem1.fadeInStep = 0;
      }
  }

  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (!paramBoolean)
      this.textOffset = 0;
    while (true)
    {
      requestUpdate();
      return;
      this.textOffset = _getInitialTextOffset();
      onSelectionChanged();
    }
  }

  @SuppressLint({"Override"})
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    this.autoMoving = 0;
    if (paramMotionEvent.getPointerCount() != 1);
    int i;
    do
    {
      int j;
      do
      {
        do
        {
          return false;
          i = (int)paramMotionEvent.getY();
          j = (int)paramMotionEvent.getX();
          switch (paramMotionEvent.getAction())
          {
          case 9:
          case 2:
          case 3:
          case 4:
          case 8:
          default:
            Logger.i(TAG, "onHoverEvent MotionEvent.Other" + paramMotionEvent.getAction() + " " + paramMotionEvent.getActionIndex() + "  " + j + ", " + i);
            return false;
          case 5:
            Logger.i(TAG, "onHoverEvent MotionEvent.ACTION_POINTER_DOWN " + j + ", " + i);
            return false;
          case 6:
            Logger.i(TAG, "onHoverEvent MotionEvent.ACTION_POINTER_UP " + j + ", " + i);
            return false;
          case 0:
            Logger.i(TAG, "onHoverEvent MotionEvent.ACTION_DOWN " + j + ", " + i);
            return false;
          case 1:
            Logger.i(TAG, "MotionEvent.ACTION_UP " + j + ", " + i);
            return false;
          case 7:
            int k = getItemByXY(j, i);
            if ((k >= 0) && (k < this.items.size()))
            {
              this.oldSelectedItem = this.selectedItem;
              this.selectedItem = k;
              requestFocus();
              requestUpdate();
              onSelectionChanged();
            }
            if (i < App.Operation(35.0F))
            {
              this.autoMoving = 1;
              return false;
            }
            break;
          case 10:
          }
        }
        while (i <= getHeight() - App.Operation(35.0F));
        this.autoMoving = 2;
        return false;
      }
      while ((j <= 0) || (j >= getWidth()));
      if (i < 0)
      {
        cursorUp();
        return false;
      }
    }
    while (i <= getHeight());
    cursorDown();
    return false;
  }

  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    Logger.i(TAG, "onKey " + paramKeyEvent.getAction() + ", " + paramKeyEvent.getKeyCode());
    int i = paramKeyEvent.getKeyCode();
    int j = paramKeyEvent.getAction();
    if (j == 0)
    {
      this.lastKeyAction = 0;
      this.lastDownKey = i;
      if ((DeviceInfo.isHMD()) || (DeviceInfo.isSWT()));
      switch (i)
      {
      default:
        switch (i)
        {
        case 23:
        case 66:
        default:
        case 20:
        case 19:
        case 21:
        case 22:
        case 7:
        }
        break;
      case 92:
      case 93:
      }
    }
    while (j != 1)
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return false;
              pageUp();
              return true;
              pageDown();
              return true;
            }
            while (!cursorDown());
            return true;
          }
          while (!cursorUp());
          return true;
        }
        while (!cursorLeft());
        return true;
      }
      while (!cursorRight());
      return true;
      reloadItemPic(this.selectedItem);
      return false;
    }
    switch (i)
    {
    default:
      return false;
    case 23:
    case 66:
    }
    if ((this.lastDownKey == i) && (this.lastKeyAction == 0))
      onClick();
    this.lastKeyAction = 1;
    return true;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    this.autoMoving = 0;
    if (paramMotionEvent.getPointerCount() != 1);
    int i;
    int j;
    int k;
    do
    {
      return false;
      i = (int)paramMotionEvent.getY();
      j = (int)paramMotionEvent.getX();
      switch (paramMotionEvent.getAction())
      {
      case 2:
      case 3:
      case 4:
      default:
        Logger.i(TAG, "MotionEvent.Other" + paramMotionEvent.getAction() + " " + paramMotionEvent.getActionIndex() + "  " + j + ", " + i);
        return false;
      case 5:
        Logger.i(TAG, "MotionEvent.ACTION_POINTER_DOWN " + j + ", " + i);
        return false;
      case 6:
        Logger.i(TAG, "MotionEvent.ACTION_POINTER_UP " + j + ", " + i);
        return false;
      case 0:
        Logger.i(TAG, "MotionEvent.ACTION_DOWN " + j + ", " + i);
        k = getItemByXY(j, i);
      case 1:
      }
    }
    while ((k < 0) || (k >= this.items.size()));
    this.selectedItem = k;
    this.oldSelectedItem = k;
    paramView.requestFocus();
    requestUpdate();
    onSelectionChanged();
    onClick();
    return false;
    Logger.i(TAG, "MotionEvent.ACTION_UP " + j + ", " + i);
    return false;
  }

  public void removeAllItems()
  {
    ArrayList localArrayList1 = this.items;
    synchronized (this.items)
    {
      this.items = new ArrayList();
      this.topPosition = 0;
      this.targetTopPosition = 0;
      this.selectedItem = 0;
      this.oldSelectedItem = 0;
      this.textOffset = 0;
      this.textOffsetY = 0;
      this.wideItemCount = 0;
      Iterator localIterator = localArrayList1.iterator();
      while (localIterator.hasNext())
      {
        ViewItem localViewItem = (ViewItem)localIterator.next();
        if (localViewItem.bmp != null)
        {
          BitmapTools.recycleBitmap(localViewItem.bmp);
          localViewItem.bmp = null;
        }
      }
    }
    localArrayList1.clear();
    scrollTo(0, this.topPosition);
    requestUpdate();
  }

  public void removeAllItemsFixedPos()
  {
    ArrayList localArrayList1 = this.items;
    synchronized (this.items)
    {
      this.items = new ArrayList();
      Iterator localIterator = localArrayList1.iterator();
      while (localIterator.hasNext())
      {
        ViewItem localViewItem = (ViewItem)localIterator.next();
        if (localViewItem.bmp != null)
        {
          BitmapTools.recycleBitmap(localViewItem.bmp);
          localViewItem.bmp = null;
        }
      }
    }
    localArrayList1.clear();
    requestUpdate();
  }

  public void removeFlags(int paramInt)
  {
    this.flags &= (paramInt ^ 0xFFFFFFFF);
  }

  protected void requestUpdate()
  {
    synchronized (this.refreshRequest)
    {
      this.refreshRequest = Integer.valueOf(1 + this.refreshRequest.intValue());
      this.lastDrawTime = timestamp();
      return;
    }
  }

  public void setCursorAlwaysVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      addFlags(FLAGS_ALWAYS_SHOW_CURSOR);
      return;
    }
    removeFlags(FLAGS_ALWAYS_SHOW_CURSOR);
  }

  public void setDefaultBkg()
  {
    this.defaultBkg = loadBkgBitmap(this.itemPicWidth, this.itemPicHeight, "default_filmlist_item_bkg.jpg");
  }

  public void setDefaultBkg(String paramString)
  {
    this.defaultBkg = loadBkgBitmap(this.itemPicWidth, this.itemPicHeight, paramString);
  }

  public void setDrawPlaceHolder(boolean paramBoolean)
  {
    if (paramBoolean)
      addFlags(FLAGS_SHOW_PLACE_HOLDER);
    while (true)
    {
      requestUpdate();
      return;
      removeFlags(FLAGS_SHOW_PLACE_HOLDER);
    }
  }

  public void setFlags(int paramInt)
  {
    this.flags = paramInt;
  }

  public void setFocusImage(Bitmap paramBitmap)
  {
    this.focusImage = paramBitmap;
  }

  public void setFocusImageForWide(Bitmap paramBitmap)
  {
    this.focusImageForWide = paramBitmap;
  }

  public void setFocusScalar(float paramFloat)
  {
    this.focusXScalar = paramFloat;
    this.focusYScalar = paramFloat;
  }

  public void setFocusScalar(float paramFloat1, float paramFloat2)
  {
    this.focusXScalar = paramFloat1;
    this.focusYScalar = paramFloat2;
  }

  public void setItemCover(int paramInt, Bitmap paramBitmap)
  {
    setItemCover(paramInt, paramBitmap, 0, 0);
  }

  public void setItemCover(int paramInt1, Bitmap paramBitmap, int paramInt2, int paramInt3)
  {
    if (this.items.size() <= paramInt1);
    int i;
    int j;
    do
    {
      return;
      ViewItem localViewItem = (ViewItem)this.items.get(paramInt1);
      localViewItem.coverBmp = paramBitmap;
      localViewItem.coverXOffset = paramInt2;
      localViewItem.coverYOffset = paramInt3;
      i = getFirstVisibleLine();
      j = i + this.maxVisibleLines * this.itemsPerLine;
    }
    while ((i > paramInt1) || (paramInt1 >= j));
    requestUpdate();
  }

  public void setItemCoverUrl(int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    if (this.items.size() <= paramInt1);
    int i;
    int j;
    do
    {
      return;
      ViewItem localViewItem = (ViewItem)this.items.get(paramInt1);
      if ((localViewItem != null) && (!TextUtils.isEmpty(paramString)))
        localViewItem.corverUrl = paramString;
      i = getFirstVisibleLine();
      j = i + this.maxVisibleLines * this.itemsPerLine;
    }
    while ((i > paramInt1) || (paramInt1 >= j));
    requestUpdate();
  }

  public void setItemGrid(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    setItemGrid(paramInt1, paramInt2, new Rect(paramInt3, paramInt3, paramInt3, paramInt3), paramInt4, paramInt5);
  }

  public void setItemGrid(int paramInt1, int paramInt2, Rect paramRect, int paramInt3, int paramInt4)
  {
    this.columnInterval = paramInt1;
    this.lineInterval = paramInt2;
    this.itemLeftPadding = paramRect.left;
    this.itemTopPadding = paramRect.top;
    this.itemRightPadding = paramRect.right;
    this.itemBottomPadding = paramRect.bottom;
    this.itemPicWidth = paramInt3;
    this.itemPicHeight = paramInt4;
    this.defaultBkg = null;
    this.defaultWideBkg = null;
  }

  public void setItemPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.itemLeftPadding = paramInt1;
    this.itemRightPadding = paramInt3;
    this.itemTopPadding = paramInt2;
    this.itemBottomPadding = paramInt4;
  }

  public void setItemPicProcessor(ItemPicProcessor paramItemPicProcessor)
  {
    try
    {
      this.itemPicProcessor = paramItemPicProcessor;
      return;
    }
    finally
    {
    }
  }

  public void setItemPicRoundRect(float paramFloat)
  {
    this.itemPicRoundRect = paramFloat;
    this.updateInfoShape = null;
    this.updateInfoShape = new RoundRectShape(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, paramFloat, paramFloat, paramFloat, paramFloat }, null, null);
    this.defaultPosterShape = new RoundRectShape(new float[] { paramFloat, paramFloat, paramFloat, paramFloat, 0.0F, 0.0F, 0.0F, 0.0F }, null, null);
  }

  public void setItemTextColorizer(ItemTextColorizer paramItemTextColorizer)
  {
    this.itemTextColorizer = paramItemTextColorizer;
  }

  public void setOnDrawItemListener(OnDrawItemListener paramOnDrawItemListener)
  {
    this.onDrawItemListener = paramOnDrawItemListener;
  }

  public void setOnItemClickListener(OnClickListener paramOnClickListener)
  {
    this.onClickListener = paramOnClickListener;
  }

  public void setOnScrollingListener(OnScrollingListener paramOnScrollingListener)
  {
    this.onScrollingListener = paramOnScrollingListener;
  }

  public void setOnSelectionChangedListener(OnSelectionChangedListener paramOnSelectionChangedListener)
  {
    this.onSelectionChangedListener = paramOnSelectionChangedListener;
  }

  public void setOnTitleChangeListener(OnTitleChangeListener paramOnTitleChangeListener)
  {
    this.onTitleChangeListener = paramOnTitleChangeListener;
  }

  public void setOnViewScrollListener(OnViewScrollListener paramOnViewScrollListener)
  {
    this.onViewScrollListener = paramOnViewScrollListener;
  }

  public void setScrollTextDelay(int paramInt1, int paramInt2)
  {
    this.initialScrollTextOffset = (-Math.abs(paramInt1) / 30);
    this.intervalScrollTextOffset = (-Math.abs(paramInt2) / 30);
  }

  public void setScrollTextSpacing(int paramInt)
  {
    this.scrollTextSpacing = paramInt;
  }

  public void setSelectedItem(int paramInt)
  {
    this.selectedItem = paramInt;
    if (this.items != null)
    {
      if (this.selectedItem < this.items.size())
        break label69;
      this.selectedItem = (-1 + this.items.size());
    }
    while (true)
    {
      this.targetTopPosition = (((ViewItem)this.items.get(this.selectedItem)).rcItem.top - this.yOffset);
      return;
      label69: if (this.selectedItem < 0)
        this.selectedItem = 0;
    }
  }

  public void setSelectedItemFixedPos(int paramInt)
  {
    this.selectedItem = paramInt;
    if (this.items != null)
    {
      if (this.selectedItem < this.items.size())
        break label201;
      this.selectedItem = (-1 + this.items.size());
    }
    while (true)
    {
      ViewItem localViewItem = (ViewItem)this.items.get(this.selectedItem);
      Logger.i("setSelectedItemFixedPos" + this.targetTopPosition + "," + (localViewItem.rcItem.top - this.yOffset) + ",yOffset=" + this.yOffset);
      if ((localViewItem.rcItem.top < this.targetTopPosition) || (this.targetTopPosition + this.maxVisibleLines * this.lineInterval < localViewItem.rcItem.top))
      {
        this.targetTopPosition = (localViewItem.rcItem.top - this.yOffset);
        if (this.targetTopPosition < 0)
          this.targetTopPosition = 0;
        this.topPosition = this.targetTopPosition;
        scrollTo(this.leftPosition, this.topPosition);
      }
      return;
      label201: if (this.selectedItem < 0)
        this.selectedItem = 0;
    }
  }

  public void setShowAbstractInfo(boolean paramBoolean)
  {
    this.showAbstractInfo = paramBoolean;
  }

  public void setTextBkg(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      this.textBkg = new BitmapDrawable(paramBitmap);
      return;
    }
    this.textBkg = null;
  }

  public void setTextBold(boolean paramBoolean)
  {
    this.textPaint.setFakeBoldText(paramBoolean);
  }

  public void setTextDefaultColor(int paramInt)
  {
    this.inactiveItemTextColor = paramInt;
  }

  public void setTextHighlightColor(int paramInt)
  {
    this.activeItemTextColor = paramInt;
  }

  public void setTextShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
  {
    this.textPaint.setShadowLayer(paramFloat3, paramFloat1, paramFloat2, paramInt);
  }

  public void setTextSize(int paramInt)
  {
    this.textPaint.setTextSize(paramInt);
  }

  public void setTextTypeFace(Typeface paramTypeface)
  {
    this.textPaint.setTypeface(paramTypeface);
  }

  public void setTextYOffset(float paramFloat)
  {
    this.textYOffset = paramFloat;
  }

  public void setViewGrid(int paramInt1, int paramInt2)
  {
    this.itemsPerLine = paramInt1;
    this.maxVisibleLines = paramInt2;
  }

  public void setViewOffset(int paramInt1, int paramInt2)
  {
    setViewOffset(paramInt1, paramInt1, paramInt2);
  }

  public void setViewOffset(int paramInt1, int paramInt2, int paramInt3)
  {
    this.xOffsetLeft = paramInt1;
    this.xOffsetRight = paramInt2;
    this.yOffset = paramInt3;
  }

  public void set_xScalar(float paramFloat)
  {
    this._xScalar = paramFloat;
  }

  public void set_yScalar(float paramFloat)
  {
    this._yScalar = paramFloat;
  }

  private static class DecodeContext
  {
    Canvas canvas = new Canvas();
    float itemPicRoundRect;
    Paint paintSolid = new Paint(3);
    Paint paintSrcIn = new Paint(3);
    Shape posterShape;
    Rect rect = new Rect();
    RectF rectF = new RectF();
    Rect shapRect = new Rect();
    ShapeDrawable shapeDrawable = new ShapeDrawable();

    private DecodeContext()
    {
      float[] arrayOfFloat = new float[8];
      arrayOfFloat[0] = this.itemPicRoundRect;
      arrayOfFloat[1] = this.itemPicRoundRect;
      arrayOfFloat[2] = this.itemPicRoundRect;
      arrayOfFloat[3] = this.itemPicRoundRect;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = 0.0F;
      arrayOfFloat[6] = 0.0F;
      arrayOfFloat[7] = 0.0F;
      this.posterShape = new RoundRectShape(arrayOfFloat, null, null);
      this.paintSrcIn.setColor(-1);
      this.paintSrcIn.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    Bitmap toRoundCornerBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
    {
      if (paramBitmap == null)
        return null;
      this.itemPicRoundRect = paramFloat1;
      float[] arrayOfFloat = new float[8];
      arrayOfFloat[0] = this.itemPicRoundRect;
      arrayOfFloat[1] = this.itemPicRoundRect;
      arrayOfFloat[2] = this.itemPicRoundRect;
      arrayOfFloat[3] = this.itemPicRoundRect;
      arrayOfFloat[4] = 0.0F;
      arrayOfFloat[5] = 0.0F;
      arrayOfFloat[6] = 0.0F;
      arrayOfFloat[7] = 0.0F;
      this.posterShape = new RoundRectShape(arrayOfFloat, null, null);
      Bitmap localBitmap = BitmapTools.createBitmapFromRecycledBitmaps(paramInt1, paramInt2);
      this.rectF.set(0.0F, 0.0F, paramInt1, paramInt2);
      this.canvas.setBitmap(localBitmap);
      this.canvas.drawARGB(0, 0, 0, 0);
      this.rect.set(0, 0, paramInt1, paramInt2);
      this.shapRect.set(0, 0, paramInt1, paramInt2);
      this.rect.offsetTo((paramBitmap.getWidth() - paramInt1) / 2, (paramBitmap.getHeight() - paramInt2) / 2);
      this.shapeDrawable.setShape(this.posterShape);
      this.shapeDrawable.setBounds(this.shapRect);
      this.canvas.save();
      this.canvas.clipRect(this.shapRect);
      this.shapeDrawable.draw(this.canvas);
      this.canvas.restore();
      this.canvas.drawBitmap(paramBitmap, this.rect, this.rectF, this.paintSrcIn);
      this.canvas.setBitmap(null);
      return localBitmap;
    }
  }

  public static abstract interface ItemComparable
  {
    public abstract boolean compare(Object paramObject);
  }

  public static abstract interface ItemPicProcessor
  {
    public abstract void destroy();

    public abstract String getCachePrefix(int paramInt, Object paramObject);

    public abstract Bitmap postloadCacheProcess(int paramInt1, Object paramObject, int paramInt2, int paramInt3, Bitmap paramBitmap);

    public abstract Bitmap postloadProcess(int paramInt1, Object paramObject, int paramInt2, int paramInt3, Bitmap paramBitmap);

    public abstract void preloadMeasure(int paramInt, Object paramObject, Rect paramRect);
  }

  public static class ItemPicVMirrorProcessor
    implements FilmListView.ItemPicProcessor
  {
    public static final int[] COLORS = { -16777216, -100663296, -369098752, -1358954496, -1627389952, -285212672 };
    public static final float[] POSITIONS = { 0.0F, 0.15F, 0.35F, 0.55F, 0.95F, 1.0F };
    private boolean isDestroyed = false;
    private MyThreadLocal<Canvas> localCanvas = new MyThreadLocal();
    private MyThreadLocal<Paint> localPaint = new MyThreadLocal();

    public void destroy()
    {
      try
      {
        this.isDestroyed = true;
        this.localCanvas.removeAll();
        this.localPaint.removeAll();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public String getCachePrefix(int paramInt, Object paramObject)
    {
      return "vmirror-";
    }

    public Bitmap postloadCacheProcess(int paramInt1, Object paramObject, int paramInt2, int paramInt3, Bitmap paramBitmap)
    {
      return paramBitmap;
    }

    public Bitmap postloadProcess(int paramInt1, Object paramObject, int paramInt2, int paramInt3, Bitmap paramBitmap)
    {
      try
      {
        if (this.isDestroyed)
          return null;
        Canvas localCanvas1 = (Canvas)this.localCanvas.get();
        if (localCanvas1 == null)
        {
          localCanvas1 = new Canvas();
          this.localCanvas.set(localCanvas1);
        }
        Paint localPaint1 = (Paint)this.localPaint.get();
        if (localPaint1 == null)
        {
          localPaint1 = new Paint();
          localPaint1.setAntiAlias(true);
          localPaint1.setFilterBitmap(true);
          localPaint1.setDither(true);
          this.localPaint.set(localPaint1);
        }
        Bitmap localBitmap = BitmapTools.createBitmapFromRecycledBitmaps(paramInt2, paramInt3);
        localCanvas1.setBitmap(localBitmap);
        localCanvas1.save();
        localCanvas1.scale(1.0F, -1.0F);
        localCanvas1.translate(0.0F, -paramInt3);
        Rect localRect1 = new Rect(0, 0, paramInt2, paramInt3 / 2);
        Rect localRect2 = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
        localCanvas1.drawBitmap(paramBitmap, localRect2, localRect1, localPaint1);
        localCanvas1.restore();
        localPaint1.setShader(new LinearGradient(0.0F, 0.0F, 0.0F, paramInt3 / 2, COLORS, POSITIONS, Shader.TileMode.MIRROR));
        localCanvas1.drawRect(new Rect(0, paramInt3 / 2, paramInt2, paramInt3), localPaint1);
        localRect1.set(0, 0, paramInt2, paramInt3 / 2);
        localCanvas1.drawBitmap(paramBitmap, localRect2, localRect1, localPaint1);
        localCanvas1.setBitmap(null);
        BitmapTools.recycleBitmap(paramBitmap);
        return localBitmap;
      }
      finally
      {
      }
    }

    public void preloadMeasure(int paramInt, Object paramObject, Rect paramRect)
    {
      paramRect.bottom = ((paramRect.bottom + paramRect.top) / 2);
    }

    static class MyThreadLocal<T>
    {
      private HashMap<Long, T> threadMap = new HashMap();

      public T get()
      {
        try
        {
          Long localLong = Long.valueOf(Thread.currentThread().getId());
          Object localObject2 = this.threadMap.get(localLong);
          return localObject2;
        }
        finally
        {
          localObject1 = finally;
          throw localObject1;
        }
      }

      public void remove()
      {
        try
        {
          Long localLong = Long.valueOf(Thread.currentThread().getId());
          this.threadMap.remove(localLong);
          return;
        }
        finally
        {
          localObject = finally;
          throw localObject;
        }
      }

      public void removeAll()
      {
        try
        {
          this.threadMap.clear();
          return;
        }
        finally
        {
          localObject = finally;
          throw localObject;
        }
      }

      public void set(T paramT)
      {
        try
        {
          Long localLong = Long.valueOf(Thread.currentThread().getId());
          if (paramT == null)
            this.threadMap.remove(localLong);
          while (true)
          {
            return;
            this.threadMap.put(localLong, paramT);
          }
        }
        finally
        {
        }
      }
    }
  }

  public static abstract interface ItemTextColorizer
  {
    public abstract boolean getItemTextColors(int paramInt1, Object paramObject, String paramString, int[] paramArrayOfInt, int paramInt2);
  }

  public static enum ListViewDrawingPhase
  {
    static
    {
      ITEM_POST_DRAW_PHASE = new ListViewDrawingPhase("ITEM_POST_DRAW_PHASE", 1);
      ITEM_COVER_PRE_DRAW_PHASE = new ListViewDrawingPhase("ITEM_COVER_PRE_DRAW_PHASE", 2);
      ITEM_COVER_POST_DRAW_PHASE = new ListViewDrawingPhase("ITEM_COVER_POST_DRAW_PHASE", 3);
      ListViewDrawingPhase[] arrayOfListViewDrawingPhase = new ListViewDrawingPhase[4];
      arrayOfListViewDrawingPhase[0] = ITEM_PRE_DRAW_PHASE;
      arrayOfListViewDrawingPhase[1] = ITEM_POST_DRAW_PHASE;
      arrayOfListViewDrawingPhase[2] = ITEM_COVER_PRE_DRAW_PHASE;
      arrayOfListViewDrawingPhase[3] = ITEM_COVER_POST_DRAW_PHASE;
    }
  }

  public static abstract interface OnClickListener
  {
    public abstract void onClick(int paramInt, Object paramObject);
  }

  public static abstract interface OnDrawItemListener
  {
    public abstract void onDrawItem(int paramInt, Object paramObject, Canvas paramCanvas, Rect paramRect1, Rect paramRect2, FilmListView.ListViewDrawingPhase paramListViewDrawingPhase);
  }

  public static abstract interface OnScrollingListener
  {
    public abstract void onScrolling(int paramInt1, int paramInt2, int paramInt3);
  }

  public static abstract interface OnSelectionChangedListener
  {
    public abstract void onSelectionChanged(int paramInt, Object paramObject);
  }

  public static abstract interface OnTitleChangeListener
  {
    public abstract void onTitleChange(boolean paramBoolean);
  }

  public static abstract interface OnViewScrollListener
  {
    public abstract void onViewScroll(int paramInt1, int paramInt2);
  }

  class ViewItem
  {
    Bitmap bmp = null;
    String corverUrl;
    Bitmap coverBmp = null;
    int coverHeight = App.Operation(32.0F);
    int coverWidth = App.Operation(32.0F);
    int coverXOffset = 0;
    int coverYOffset = 0;
    int fadeInStep = 0;
    int idx;
    boolean isPlaceholder = false;
    boolean isWide = false;
    Object itemData;
    boolean loading = false;
    String picUrl;
    Rect rcItem;
    boolean reload = false;
    String title;

    ViewItem()
    {
    }
  }

  private class imageDownloadHandler
  {
    LinkedList<DecodeTarget> decodeTargets = new LinkedList();
    boolean isTerminated = false;
    HashMap<Long, HttpURLConnection> pendingConnection = new HashMap();

    private imageDownloadHandler()
    {
    }

    boolean doDecode(FilmListView.DecodeContext paramDecodeContext)
    {
      FilmListView.ViewItem localViewItem;
      boolean bool;
      FilmListView.ItemPicProcessor localItemPicProcessor;
      File localFile;
      int j;
      int k;
      Bitmap localBitmap;
      while (true)
      {
        synchronized (this.decodeTargets)
        {
          if (this.decodeTargets.isEmpty())
            return false;
          DecodeTarget localDecodeTarget = (DecodeTarget)this.decodeTargets.poll();
          localViewItem = localDecodeTarget.item;
          InputStream localInputStream = localDecodeTarget.decodeStream;
          bool = localDecodeTarget.doResample;
          localItemPicProcessor = localDecodeTarget.picProcessor;
          localFile = localDecodeTarget.cachedFile;
          if (!FilmListView.this.isItemLoadable(localViewItem))
            break label416;
          if (localViewItem.isWide)
          {
            i = FilmListView.this.itemPicWidth + FilmListView.this.columnInterval;
            Rect localRect = new Rect(0, 0, i, FilmListView.this.itemPicHeight);
            if (FilmListView.this.focusXScalar <= 1.2F)
              break label270;
            f1 = FilmListView.this.focusXScalar;
            if (FilmListView.this.focusYScalar <= 1.2F)
              break label276;
            f2 = FilmListView.this.focusYScalar;
            j = (int)(f1 * localRect.width());
            k = (int)(f2 * localRect.height());
            if (localItemPicProcessor != null)
              localItemPicProcessor.preloadMeasure(localViewItem.idx, localViewItem.itemData, localRect);
            localBitmap = BitmapTools.decodeStream(localInputStream, Bitmap.Config.RGB_565, j, k);
            if ((!this.isTerminated) || (bool))
              break;
            localViewItem.loading = false;
            return false;
          }
        }
        int i = FilmListView.this.itemPicWidth;
        continue;
        label270: float f1 = 1.0F;
        continue;
        label276: float f2 = 1.0F;
      }
      if (localBitmap != null)
        if (bool)
          if (localItemPicProcessor != null)
            localBitmap = localItemPicProcessor.postloadProcess(localViewItem.idx, localViewItem.itemData, j, k, localBitmap);
      while (true)
      {
        try
        {
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
          localBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
          localFileOutputStream.close();
          if (FilmListView.this.itemPicRoundRect > 0.5D)
          {
            float f3 = FilmListView.this.itemPicRoundRect;
            float f4 = FilmListView.this.itemPicRoundRect;
            localBitmap = paramDecodeContext.toRoundCornerBitmap(localBitmap, j, k, f3, f4);
          }
          localViewItem.bmp = localBitmap;
          FilmListView.this.requestUpdate(localViewItem);
          label416: localViewItem.loading = false;
          return true;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          continue;
        }
        if (localItemPicProcessor != null)
          localBitmap = localItemPicProcessor.postloadCacheProcess(localViewItem.idx, localViewItem.itemData, j, k, localBitmap);
        try
        {
          localFile.setLastModified(System.currentTimeMillis());
          TempFileManager.getInstance().addTempFile(localFile);
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
        continue;
        localFile.delete();
        localViewItem.bmp = null;
      }
    }

    // ERROR //
    boolean doLoadImage(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:isTerminated	Z
      //   4: ifne +336 -> 340
      //   7: aload_0
      //   8: getfield 18	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:this$0	Lcom/starcor/hunan/widget/FilmListView;
      //   11: getfield 217	com/starcor/hunan/widget/FilmListView:targetTopPosition	I
      //   14: aload_0
      //   15: getfield 18	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:this$0	Lcom/starcor/hunan/widget/FilmListView;
      //   18: getfield 220	com/starcor/hunan/widget/FilmListView:topPosition	I
      //   21: if_icmpne +319 -> 340
      //   24: aload_0
      //   25: getfield 18	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:this$0	Lcom/starcor/hunan/widget/FilmListView;
      //   28: invokevirtual 224	com/starcor/hunan/widget/FilmListView:acquirePendingItem	()Lcom/starcor/hunan/widget/FilmListView$ViewItem;
      //   31: astore_3
      //   32: aload_3
      //   33: ifnonnull +5 -> 38
      //   36: iconst_0
      //   37: ireturn
      //   38: aload_0
      //   39: monitorenter
      //   40: aload_0
      //   41: getfield 18	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:this$0	Lcom/starcor/hunan/widget/FilmListView;
      //   44: invokestatic 228	com/starcor/hunan/widget/FilmListView:access$700	(Lcom/starcor/hunan/widget/FilmListView;)Lcom/starcor/hunan/widget/FilmListView$ItemPicProcessor;
      //   47: astore 5
      //   49: aload_0
      //   50: monitorexit
      //   51: aload_3
      //   52: getfield 231	com/starcor/hunan/widget/FilmListView$ViewItem:reload	Z
      //   55: istore 6
      //   57: aload_3
      //   58: iconst_0
      //   59: putfield 231	com/starcor/hunan/widget/FilmListView$ViewItem:reload	Z
      //   62: getstatic 235	com/starcor/hunan/widget/FilmListView:TAG	Ljava/lang/String;
      //   65: new 237	java/lang/StringBuilder
      //   68: dup
      //   69: invokespecial 238	java/lang/StringBuilder:<init>	()V
      //   72: ldc 240
      //   74: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   77: invokestatic 250	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   80: invokevirtual 253	java/lang/Thread:getId	()J
      //   83: invokevirtual 256	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
      //   86: ldc_w 258
      //   89: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   92: aload_3
      //   93: getfield 261	com/starcor/hunan/widget/FilmListView$ViewItem:title	Ljava/lang/String;
      //   96: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   99: ldc_w 263
      //   102: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   105: aload_3
      //   106: getfield 266	com/starcor/hunan/widget/FilmListView$ViewItem:picUrl	Ljava/lang/String;
      //   109: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   112: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   115: invokestatic 276	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
      //   118: aload_3
      //   119: getfield 279	com/starcor/hunan/widget/FilmListView$ViewItem:corverUrl	Ljava/lang/String;
      //   122: ifnull +73 -> 195
      //   125: aload_3
      //   126: getfield 279	com/starcor/hunan/widget/FilmListView$ViewItem:corverUrl	Ljava/lang/String;
      //   129: invokevirtual 284	java/lang/String:length	()I
      //   132: ifle +63 -> 195
      //   135: aload_3
      //   136: getfield 279	com/starcor/hunan/widget/FilmListView$ViewItem:corverUrl	Ljava/lang/String;
      //   139: ldc_w 286
      //   142: invokevirtual 290	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   145: ifne +50 -> 195
      //   148: aload_0
      //   149: aload_3
      //   150: getfield 279	com/starcor/hunan/widget/FilmListView$ViewItem:corverUrl	Ljava/lang/String;
      //   153: invokevirtual 294	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:getCoverBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
      //   156: astore 37
      //   158: aload 37
      //   160: ifnull +35 -> 195
      //   163: aload_0
      //   164: getfield 23	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:isTerminated	Z
      //   167: ifne +28 -> 195
      //   170: aload_0
      //   171: getfield 18	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:this$0	Lcom/starcor/hunan/widget/FilmListView;
      //   174: aload_3
      //   175: getfield 114	com/starcor/hunan/widget/FilmListView$ViewItem:idx	I
      //   178: aload 37
      //   180: ldc_w 295
      //   183: invokestatic 301	com/starcor/hunan/App:Operation	(F)I
      //   186: ldc_w 295
      //   189: invokestatic 301	com/starcor/hunan/App:Operation	(F)I
      //   192: invokevirtual 305	com/starcor/hunan/widget/FilmListView:setItemCover	(ILandroid/graphics/Bitmap;II)V
      //   195: aload_3
      //   196: getfield 266	com/starcor/hunan/widget/FilmListView$ViewItem:picUrl	Ljava/lang/String;
      //   199: ifnull -199 -> 0
      //   202: aload_3
      //   203: getfield 266	com/starcor/hunan/widget/FilmListView$ViewItem:picUrl	Ljava/lang/String;
      //   206: invokestatic 310	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   209: ifne -209 -> 0
      //   212: new 312	java/net/URL
      //   215: dup
      //   216: aload_3
      //   217: getfield 266	com/starcor/hunan/widget/FilmListView$ViewItem:picUrl	Ljava/lang/String;
      //   220: invokespecial 315	java/net/URL:<init>	(Ljava/lang/String;)V
      //   223: astore 7
      //   225: new 196	java/io/File
      //   228: dup
      //   229: aload 7
      //   231: invokevirtual 318	java/net/URL:getPath	()Ljava/lang/String;
      //   234: invokespecial 319	java/io/File:<init>	(Ljava/lang/String;)V
      //   237: invokevirtual 322	java/io/File:getName	()Ljava/lang/String;
      //   240: astore 9
      //   242: aload 5
      //   244: ifnull +105 -> 349
      //   247: aload 5
      //   249: aload_3
      //   250: getfield 114	com/starcor/hunan/widget/FilmListView$ViewItem:idx	I
      //   253: aload_3
      //   254: getfield 118	com/starcor/hunan/widget/FilmListView$ViewItem:itemData	Ljava/lang/Object;
      //   257: invokeinterface 326 3 0
      //   262: astore 10
      //   264: aload 10
      //   266: invokestatic 310	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   269: ifne +25 -> 294
      //   272: new 237	java/lang/StringBuilder
      //   275: dup
      //   276: invokespecial 238	java/lang/StringBuilder:<init>	()V
      //   279: aload 10
      //   281: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   284: aload 9
      //   286: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   289: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   292: astore 9
      //   294: new 196	java/io/File
      //   297: dup
      //   298: invokestatic 330	com/starcor/hunan/widget/FilmListView:access$200	()Ljava/io/File;
      //   301: aload 9
      //   303: invokespecial 333	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   306: astore 11
      //   308: iload 6
      //   310: ifne +11 -> 321
      //   313: aload 11
      //   315: invokevirtual 336	java/io/File:exists	()Z
      //   318: ifne +642 -> 960
      //   321: aload_0
      //   322: getfield 28	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:pendingConnection	Ljava/util/HashMap;
      //   325: astore 12
      //   327: aload 12
      //   329: monitorenter
      //   330: aload_0
      //   331: getfield 23	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:isTerminated	Z
      //   334: ifeq +52 -> 386
      //   337: aload 12
      //   339: monitorexit
      //   340: iconst_0
      //   341: ireturn
      //   342: astore 4
      //   344: aload_0
      //   345: monitorexit
      //   346: aload 4
      //   348: athrow
      //   349: aload_3
      //   350: getfield 80	com/starcor/hunan/widget/FilmListView$ViewItem:isWide	Z
      //   353: ifeq -59 -> 294
      //   356: new 237	java/lang/StringBuilder
      //   359: dup
      //   360: invokespecial 238	java/lang/StringBuilder:<init>	()V
      //   363: ldc_w 338
      //   366: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   369: aload 9
      //   371: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   374: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   377: astore 36
      //   379: aload 36
      //   381: astore 9
      //   383: goto -89 -> 294
      //   386: aload 7
      //   388: invokevirtual 342	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   391: checkcast 344	java/net/HttpURLConnection
      //   394: astore 14
      //   396: aload_0
      //   397: getfield 28	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:pendingConnection	Ljava/util/HashMap;
      //   400: invokestatic 250	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   403: invokevirtual 253	java/lang/Thread:getId	()J
      //   406: invokestatic 350	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   409: aload 14
      //   411: invokevirtual 354	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   414: pop
      //   415: aload 12
      //   417: monitorexit
      //   418: aload 14
      //   420: sipush 8000
      //   423: invokevirtual 358	java/net/HttpURLConnection:setReadTimeout	(I)V
      //   426: aload 14
      //   428: sipush 4000
      //   431: invokevirtual 361	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   434: aload 14
      //   436: ldc_w 363
      //   439: ldc_w 365
      //   442: invokevirtual 368	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   445: aload 14
      //   447: ldc_w 370
      //   450: iconst_m1
      //   451: invokevirtual 374	java/net/HttpURLConnection:getHeaderFieldInt	(Ljava/lang/String;I)I
      //   454: istore 16
      //   456: aload 14
      //   458: ldc_w 376
      //   461: invokevirtual 380	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
      //   464: astore 17
      //   466: aload 14
      //   468: invokevirtual 384	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
      //   471: astore 18
      //   473: ldc_w 365
      //   476: aload 17
      //   478: invokevirtual 388	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
      //   481: ifeq +18 -> 499
      //   484: new 390	java/util/zip/GZIPInputStream
      //   487: dup
      //   488: aload 18
      //   490: invokespecial 393	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
      //   493: astore 19
      //   495: aload 19
      //   497: astore 18
      //   499: new 144	java/io/FileOutputStream
      //   502: dup
      //   503: aload 11
      //   505: invokespecial 147	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   508: astore 20
      //   510: iconst_0
      //   511: istore 21
      //   513: aload_1
      //   514: arraylength
      //   515: iload 16
      //   517: if_icmplt +135 -> 652
      //   520: aload 18
      //   522: aload_2
      //   523: invokevirtual 399	java/io/InputStream:read	([B)I
      //   526: istore 22
      //   528: iload 22
      //   530: iconst_m1
      //   531: if_icmpeq +171 -> 702
      //   534: aload_0
      //   535: getfield 23	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:isTerminated	Z
      //   538: ifne +164 -> 702
      //   541: iconst_0
      //   542: istore 23
      //   544: iload 23
      //   546: iload 22
      //   548: if_icmpge +94 -> 642
      //   551: aload_1
      //   552: iload 21
      //   554: iload 23
      //   556: iadd
      //   557: aload_2
      //   558: iload 23
      //   560: baload
      //   561: bastore
      //   562: iinc 23 1
      //   565: goto -21 -> 544
      //   568: astore 13
      //   570: aload 12
      //   572: monitorexit
      //   573: aload 13
      //   575: athrow
      //   576: astore 8
      //   578: aload 8
      //   580: invokevirtual 185	java/lang/Exception:printStackTrace	()V
      //   583: getstatic 235	com/starcor/hunan/widget/FilmListView:TAG	Ljava/lang/String;
      //   586: new 237	java/lang/StringBuilder
      //   589: dup
      //   590: invokespecial 238	java/lang/StringBuilder:<init>	()V
      //   593: ldc 240
      //   595: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   598: invokestatic 250	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   601: invokevirtual 253	java/lang/Thread:getId	()J
      //   604: invokevirtual 256	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
      //   607: ldc_w 401
      //   610: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   613: aload_3
      //   614: getfield 261	com/starcor/hunan/widget/FilmListView$ViewItem:title	Ljava/lang/String;
      //   617: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   620: ldc_w 263
      //   623: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   626: aload_3
      //   627: getfield 266	com/starcor/hunan/widget/FilmListView$ViewItem:picUrl	Ljava/lang/String;
      //   630: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   633: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   636: invokestatic 404	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
      //   639: goto -639 -> 0
      //   642: iload 21
      //   644: iload 22
      //   646: iadd
      //   647: istore 21
      //   649: goto -129 -> 520
      //   652: aload 18
      //   654: aload_2
      //   655: invokevirtual 399	java/io/InputStream:read	([B)I
      //   658: istore 35
      //   660: iload 35
      //   662: iconst_m1
      //   663: if_icmpeq +29 -> 692
      //   666: aload_0
      //   667: getfield 23	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:isTerminated	Z
      //   670: ifne +22 -> 692
      //   673: aload 20
      //   675: aload_2
      //   676: iconst_0
      //   677: iload 35
      //   679: invokevirtual 408	java/io/FileOutputStream:write	([BII)V
      //   682: iload 21
      //   684: iload 35
      //   686: iadd
      //   687: istore 21
      //   689: goto -37 -> 652
      //   692: aload 20
      //   694: invokevirtual 411	java/io/FileOutputStream:flush	()V
      //   697: aload 20
      //   699: invokevirtual 162	java/io/FileOutputStream:close	()V
      //   702: aload 18
      //   704: invokevirtual 412	java/io/InputStream:close	()V
      //   707: aload_0
      //   708: getfield 28	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:pendingConnection	Ljava/util/HashMap;
      //   711: astore 24
      //   713: aload 24
      //   715: monitorenter
      //   716: aload_0
      //   717: getfield 23	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:isTerminated	Z
      //   720: ifeq +17 -> 737
      //   723: aload 24
      //   725: monitorexit
      //   726: goto -386 -> 340
      //   729: astore 25
      //   731: aload 24
      //   733: monitorexit
      //   734: aload 25
      //   736: athrow
      //   737: aload_0
      //   738: getfield 28	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:pendingConnection	Ljava/util/HashMap;
      //   741: invokestatic 250	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   744: invokevirtual 253	java/lang/Thread:getId	()J
      //   747: invokestatic 350	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   750: invokevirtual 416	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
      //   753: pop
      //   754: aload 24
      //   756: monitorexit
      //   757: aload 14
      //   759: invokevirtual 419	java/net/HttpURLConnection:disconnect	()V
      //   762: iload 16
      //   764: ifle +61 -> 825
      //   767: iload 21
      //   769: iload 16
      //   771: if_icmple +54 -> 825
      //   774: getstatic 235	com/starcor/hunan/widget/FilmListView:TAG	Ljava/lang/String;
      //   777: new 237	java/lang/StringBuilder
      //   780: dup
      //   781: invokespecial 238	java/lang/StringBuilder:<init>	()V
      //   784: ldc_w 421
      //   787: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   790: iload 16
      //   792: invokevirtual 424	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   795: ldc_w 426
      //   798: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   801: iload 21
      //   803: invokevirtual 424	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   806: ldc_w 428
      //   809: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   812: aload_3
      //   813: getfield 266	com/starcor/hunan/widget/FilmListView$ViewItem:picUrl	Ljava/lang/String;
      //   816: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   819: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   822: invokestatic 404	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
      //   825: iload 21
      //   827: istore 27
      //   829: aload_1
      //   830: arraylength
      //   831: iload 27
      //   833: if_icmplt +113 -> 946
      //   836: new 430	org/apache/http/util/ByteArrayBuffer
      //   839: dup
      //   840: iload 27
      //   842: invokespecial 432	org/apache/http/util/ByteArrayBuffer:<init>	(I)V
      //   845: astore 28
      //   847: aload 28
      //   849: aload_1
      //   850: iconst_0
      //   851: iload 27
      //   853: invokevirtual 434	org/apache/http/util/ByteArrayBuffer:append	([BII)V
      //   856: new 436	java/io/ByteArrayInputStream
      //   859: dup
      //   860: aload 28
      //   862: invokevirtual 440	org/apache/http/util/ByteArrayBuffer:toByteArray	()[B
      //   865: invokespecial 443	java/io/ByteArrayInputStream:<init>	([B)V
      //   868: astore 29
      //   870: goto +115 -> 985
      //   873: new 50	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget
      //   876: dup
      //   877: aload_0
      //   878: invokespecial 446	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget:<init>	(Lcom/starcor/hunan/widget/FilmListView$imageDownloadHandler;)V
      //   881: astore 31
      //   883: aload 31
      //   885: aload 29
      //   887: putfield 58	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget:decodeStream	Ljava/io/InputStream;
      //   890: aload 31
      //   892: iload 30
      //   894: putfield 61	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget:doResample	Z
      //   897: aload 31
      //   899: aload_3
      //   900: putfield 54	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget:item	Lcom/starcor/hunan/widget/FilmListView$ViewItem;
      //   903: aload 31
      //   905: aload_0
      //   906: getfield 18	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:this$0	Lcom/starcor/hunan/widget/FilmListView;
      //   909: invokestatic 228	com/starcor/hunan/widget/FilmListView:access$700	(Lcom/starcor/hunan/widget/FilmListView;)Lcom/starcor/hunan/widget/FilmListView$ItemPicProcessor;
      //   912: putfield 65	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget:picProcessor	Lcom/starcor/hunan/widget/FilmListView$ItemPicProcessor;
      //   915: aload 31
      //   917: aload 11
      //   919: putfield 69	com/starcor/hunan/widget/FilmListView$imageDownloadHandler$DecodeTarget:cachedFile	Ljava/io/File;
      //   922: aload_0
      //   923: getfield 33	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:decodeTargets	Ljava/util/LinkedList;
      //   926: astore 32
      //   928: aload 32
      //   930: monitorenter
      //   931: aload_0
      //   932: getfield 33	com/starcor/hunan/widget/FilmListView$imageDownloadHandler:decodeTargets	Ljava/util/LinkedList;
      //   935: aload 31
      //   937: invokevirtual 449	java/util/LinkedList:add	(Ljava/lang/Object;)Z
      //   940: pop
      //   941: aload 32
      //   943: monitorexit
      //   944: iconst_1
      //   945: ireturn
      //   946: new 451	java/io/FileInputStream
      //   949: dup
      //   950: aload 11
      //   952: invokespecial 452	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   955: astore 29
      //   957: goto +28 -> 985
      //   960: new 451	java/io/FileInputStream
      //   963: dup
      //   964: aload 11
      //   966: invokespecial 452	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   969: astore 29
      //   971: iconst_0
      //   972: istore 30
      //   974: goto -101 -> 873
      //   977: astore 33
      //   979: aload 32
      //   981: monitorexit
      //   982: aload 33
      //   984: athrow
      //   985: iconst_1
      //   986: istore 30
      //   988: goto -115 -> 873
      //
      // Exception table:
      //   from	to	target	type
      //   40	51	342	finally
      //   344	346	342	finally
      //   330	340	568	finally
      //   386	418	568	finally
      //   570	573	568	finally
      //   212	242	576	java/lang/Exception
      //   247	294	576	java/lang/Exception
      //   294	308	576	java/lang/Exception
      //   313	321	576	java/lang/Exception
      //   321	330	576	java/lang/Exception
      //   349	379	576	java/lang/Exception
      //   418	495	576	java/lang/Exception
      //   499	510	576	java/lang/Exception
      //   513	520	576	java/lang/Exception
      //   520	528	576	java/lang/Exception
      //   534	541	576	java/lang/Exception
      //   551	562	576	java/lang/Exception
      //   573	576	576	java/lang/Exception
      //   652	660	576	java/lang/Exception
      //   666	682	576	java/lang/Exception
      //   692	702	576	java/lang/Exception
      //   702	716	576	java/lang/Exception
      //   734	737	576	java/lang/Exception
      //   757	762	576	java/lang/Exception
      //   774	825	576	java/lang/Exception
      //   829	870	576	java/lang/Exception
      //   873	931	576	java/lang/Exception
      //   946	957	576	java/lang/Exception
      //   960	971	576	java/lang/Exception
      //   982	985	576	java/lang/Exception
      //   716	726	729	finally
      //   731	734	729	finally
      //   737	757	729	finally
      //   931	944	977	finally
      //   979	982	977	finally
    }

    public Bitmap getCoverBitmap(String paramString)
    {
      while (true)
      {
        File localFile;
        try
        {
          URL localURL = new URL(paramString);
          String str1 = new File(localURL.getPath()).getName();
          String str2 = "cover-" + str1;
          localFile = new File(FilmListView.localCachePath, str2);
          if (localFile.exists())
            break label315;
          if (this.isTerminated)
            return null;
          HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
          localHttpURLConnection.setReadTimeout(8000);
          localHttpURLConnection.setConnectTimeout(4000);
          localHttpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
          int i = localHttpURLConnection.getHeaderFieldInt("Content-Length", -1);
          String str3 = localHttpURLConnection.getHeaderField("Content-Encoding");
          Object localObject = localHttpURLConnection.getInputStream();
          if ("gzip".equalsIgnoreCase(str3))
            localObject = new GZIPInputStream((InputStream)localObject);
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
          int j = 0;
          byte[] arrayOfByte = new byte[8192];
          int k = ((InputStream)localObject).read(arrayOfByte);
          if ((k != -1) && (!this.isTerminated))
          {
            localFileOutputStream.write(arrayOfByte, 0, k);
            j += k;
            continue;
          }
          localFileOutputStream.flush();
          localFileOutputStream.close();
          ((InputStream)localObject).close();
          if ((i > 0) && (j > i))
          {
            Logger.e(FilmListView.TAG, "error! download pic imageSize");
            localFileInputStream = new FileInputStream(localFile);
            if (!this.isTerminated)
              return BitmapTools.decodeStream(localFileInputStream, Bitmap.Config.RGB_565, 0, 0);
          }
          else
          {
            Logger.i(FilmListView.TAG, "download success!!");
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        return null;
        label315: FileInputStream localFileInputStream = new FileInputStream(localFile);
      }
    }

    void terminate()
    {
      this.isTerminated = true;
      synchronized (this.pendingConnection)
      {
        if (!this.pendingConnection.isEmpty())
          new Thread()
          {
            HashMap<Long, HttpURLConnection> oldConnections = FilmListView.imageDownloadHandler.this.pendingConnection;

            public void run()
            {
              Iterator localIterator = this.oldConnections.values().iterator();
              while (true)
                if (localIterator.hasNext())
                {
                  HttpURLConnection localHttpURLConnection = (HttpURLConnection)localIterator.next();
                  try
                  {
                    localHttpURLConnection.disconnect();
                    super.run();
                  }
                  catch (Exception localException)
                  {
                    while (true)
                      localException.printStackTrace();
                  }
                }
            }
          }
          .run();
        this.pendingConnection = new HashMap();
        return;
      }
    }

    class DecodeTarget
    {
      File cachedFile;
      InputStream decodeStream;
      boolean doResample;
      FilmListView.ViewItem item;
      FilmListView.ItemPicProcessor picProcessor;

      DecodeTarget()
      {
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.FilmListView
 * JD-Core Version:    0.6.2
 */