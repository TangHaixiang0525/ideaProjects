package com.starcor.media.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.MplayerEx.ScreenMode;

public class MplayerNoticeView extends View
{
  private static final String TAG = MplayerNoticeView.class.getSimpleName();
  private static final int animationSpeed = 2000;
  private float animationDegree = 0.0F;
  private Paint bgPaint;
  private Paint bgPaint2;
  private RectF bgRectf = new RectF();
  private int bkgHeight = 0;
  private int bkgWidth = 0;
  Context context;
  private boolean isAnimation;
  private boolean isEnableAnimation = false;
  private RectF loadingRect = new RectF(App.Operation(-15.0F), App.Operation(-15.0F), App.Operation(15.0F), App.Operation(15.0F));
  private int mAlarmTime = 0;
  private Handler mHandler = new Handler();
  private boolean needToShowTime = false;
  private int noticeTextLeft;
  private int noticeTextTop;
  private int paddingLeft = 0;
  private int paddingRight = 0;
  private String playNotice = "";
  private Paint playNoticePaint;
  private MplayerEx.ScreenMode screenMode;
  private Rect strRect = new Rect();
  private Paint timeTextPaint;
  private Rect timeTextRect;
  private Bitmap warnIcon;
  private RectF warnIconRect = new RectF();

  public MplayerNoticeView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    this.screenMode = MplayerEx.ScreenMode.FULL_SCREEN;
    initViews();
  }

  public MplayerNoticeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    initViews();
  }

  private Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramResources.openRawResource(paramInt, localTypedValue);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTargetDensity = localTypedValue.density;
    return BitmapFactory.decodeResource(paramResources, paramInt, localOptions);
  }

  private int getBgRadius()
  {
    if (this.screenMode == MplayerEx.ScreenMode.FULL_SCREEN);
    for (int i = 24; ; i = 26)
      return App.Operation(i);
  }

  private int getItemSpace()
  {
    if (this.screenMode == MplayerEx.ScreenMode.FULL_SCREEN);
    for (int i = 15; ; i = 20)
      return App.Operation(i);
  }

  private int getLimitTextLen()
  {
    if (this.screenMode == MplayerEx.ScreenMode.FULL_SCREEN);
    for (int i = 558; ; i = 372)
      return App.Operation(i);
  }

  private int getNoticeTextSize()
  {
    if (this.screenMode == MplayerEx.ScreenMode.FULL_SCREEN);
    for (int i = 25; ; i = 24)
      return App.Operation(i);
  }

  private String getTimeString()
  {
    if (this.mAlarmTime <= 0)
      return "";
    if (this.mAlarmTime < 10)
      return "0" + String.valueOf(this.mAlarmTime);
    return String.valueOf(this.mAlarmTime);
  }

  private void initViews()
  {
    this.playNoticePaint = new Paint();
    this.playNoticePaint.setColor(-5721146);
    this.playNoticePaint.setAntiAlias(true);
    this.playNoticePaint.setTextSize(getNoticeTextSize());
    this.timeTextPaint = new Paint();
    this.timeTextPaint.setColor(-1);
    this.timeTextPaint.setAntiAlias(true);
    this.timeTextPaint.setTextSize(App.Operation(20.0F));
    this.timeTextPaint.setTextAlign(Paint.Align.CENTER);
    this.bgPaint = new Paint();
    this.bgPaint.setColor(-669179080);
    this.bgPaint.setAntiAlias(true);
    this.bgPaint2 = new Paint();
    this.bgPaint2.setStrokeWidth(App.Operation(5.0F));
    this.bgPaint2.setAntiAlias(true);
    this.bgPaint2.setShader(new SweepGradient(0.0F, 0.0F, 813142765, -8940819));
    this.bgPaint2.setStyle(Paint.Style.STROKE);
    this.warnIcon = decodeResource(getResources(), 2130837676);
    this.timeTextRect = new Rect(0, 0, (int)this.loadingRect.width(), (int)this.loadingRect.height());
    this.timeTextPaint.getTextBounds("00", 0, 2, this.timeTextRect);
  }

  private void onModeChange()
  {
    this.playNoticePaint.setTextSize(getNoticeTextSize());
    invalidate();
  }

  public void changeMode(MplayerEx.ScreenMode paramScreenMode)
  {
    if (this.screenMode != paramScreenMode)
    {
      this.screenMode = paramScreenMode;
      onModeChange();
    }
  }

  public void initSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.paddingLeft = paramInt1;
    this.paddingRight = paramInt2;
    this.bkgWidth = paramInt3;
    this.bkgHeight = paramInt4;
    invalidate();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.isAnimation = false;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
    if ((this.playNotice == null) || (this.playNotice.length() <= 0));
    do
    {
      return;
      this.bgRectf.right = this.bkgWidth;
      this.bgRectf.bottom = this.bkgHeight;
      paramCanvas.drawRoundRect(this.bgRectf, getBgRadius(), getBgRadius(), this.bgPaint);
      if (!this.isEnableAnimation)
      {
        this.warnIconRect.left = this.paddingLeft;
        this.warnIconRect.top = ((this.bkgHeight - App.Operation(30.0F)) / 2);
        this.warnIconRect.bottom = (this.warnIconRect.top + App.Operation(30.0F));
        this.warnIconRect.right = (this.warnIconRect.left + App.Operation(30.0F));
        paramCanvas.drawBitmap(this.warnIcon, null, this.warnIconRect, this.bgPaint);
      }
      paramCanvas.drawText(this.playNotice, this.noticeTextLeft, this.noticeTextTop, this.playNoticePaint);
    }
    while (!this.isEnableAnimation);
    paramCanvas.save();
    paramCanvas.translate(this.paddingLeft + getItemSpace(), this.bkgHeight / 2);
    paramCanvas.rotate(this.animationDegree);
    paramCanvas.drawArc(this.loadingRect, 0.0F, 360.0F, false, this.bgPaint2);
    paramCanvas.restore();
    int i = this.paddingLeft + getItemSpace();
    float f = MplayerTools.getTextDrawingBaseline(this.timeTextPaint, this.bgRectf);
    paramCanvas.drawText(getTimeString(), i, f, this.timeTextPaint);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.playNotice != null)
      if (!this.isEnableAnimation)
        break label65;
    label65: for (this.bkgWidth = (this.paddingLeft + App.Operation(30.0F) + getItemSpace() + this.strRect.width() + getItemSpace() + this.paddingRight); ; this.bkgWidth = (this.paddingLeft + App.Operation(30.0F) + getItemSpace() + this.strRect.width() + this.paddingRight))
    {
      setMeasuredDimension(this.bkgWidth, this.bkgHeight);
      return;
    }
  }

  public String resolveNotice(String paramString)
  {
    String str = paramString;
    float f;
    char[] arrayOfChar;
    if (!TextUtils.isEmpty(paramString))
    {
      f = 0.0F;
      arrayOfChar = paramString.toCharArray();
    }
    for (int i = 0; ; i++)
      if (i < arrayOfChar.length)
      {
        f += this.playNoticePaint.measureText(arrayOfChar, i, 1);
        if (f > getLimitTextLen())
          str = paramString.substring(0, i - 1) + "...";
      }
      else
      {
        return str;
      }
  }

  public void setAlarmTime(int paramInt, boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.isEnableAnimation))
    {
      this.isEnableAnimation = paramBoolean;
      startAnimation();
    }
    while (true)
    {
      this.mAlarmTime = Math.abs(paramInt);
      if (this.mAlarmTime == 0)
        this.isEnableAnimation = false;
      return;
      this.isEnableAnimation = paramBoolean;
    }
  }

  public void setPaddingSize(int paramInt1, int paramInt2)
  {
    this.paddingLeft = paramInt1;
    this.paddingRight = paramInt2;
  }

  public void setPlayNotice(String paramString)
  {
    if (paramString == null)
    {
      Logger.i(TAG, "setPlayNotice() playNotice is null");
      return;
    }
    if (paramString.equals(this.playNotice))
    {
      Logger.i(TAG, "setPlayNotice() playNotice is equals playNotice:" + paramString);
      return;
    }
    Logger.i(TAG, "setPlayNotice() playNotice this.playNotice:" + this.playNotice);
    this.playNotice = paramString;
    this.playNoticePaint.getTextBounds(paramString, 0, paramString.length(), this.strRect);
    this.noticeTextTop = (this.bkgHeight / 2 + this.strRect.height() / 3);
    this.noticeTextLeft = (this.paddingLeft + App.Operation(30.0F) + getItemSpace());
    requestLayout();
    invalidate();
  }

  public void startAnimation()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (MplayerNoticeView.this.isEnableAnimation)
        {
          if ((MplayerNoticeView.this.getWindowVisibility() != 0) || (MplayerNoticeView.this.getVisibility() != 0))
            MplayerNoticeView.access$002(MplayerNoticeView.this, false);
        }
        else
          return;
        MplayerNoticeView.access$116(MplayerNoticeView.this, 7.5F);
        MplayerNoticeView.this.invalidate();
        MplayerNoticeView.this.mHandler.postDelayed(this, 41L);
      }
    });
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerNoticeView
 * JD-Core Version:    0.6.2
 */