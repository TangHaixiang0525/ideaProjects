package com.starcor.media.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.service.SystemTimeManager;
import java.util.Calendar;

public class MplayerTitleView extends View
{
  public static final int COLOR_MAIN_TEXT = -1711276033;
  public static final int COLOR_QUALITY_COLOR = -13552330;
  private static final int DEFINITION_BKG_ROUND_CORNER = 0;
  private static final int ICON_PANDDING = 0;
  public static final String MENU_TEXT_NOTICE = "菜单";
  public static final String MENU_UP_SHIFT_TEXT_NOTICE = "选台";
  public static final String MENU_UP_TEXT_NOTICE = "选集";
  private static final String TAG = "MplayerTitleView";
  public static final int TEXTSIZE_VICE_TITLE = 0;
  public static final int TEXT_SIZE_MAIN_TITLE = 0;
  public static final int TEXT_SIZE_TOTAL_TITLE = 0;
  public static final int UI_MODE_PLAYBACK = 2;
  public static final int UI_MODE_TIMESHIFT = 3;
  public static final int UI_MODE_VOD = 1;
  private Paint bgPaint;
  private Calendar calendar;
  private String channelName = null;
  private int channelNameLeft = 0;
  private Paint channelNamePaint;
  private String channelNum = null;
  private Paint channelNumPaint;
  Context context;
  private String currentTime = "00:00";
  private int currentTimeLeft = 0;
  private Paint currentTimePaint;
  private String definition = null;
  private Paint definitionBkgPaint;
  private RectF definitionBkgRect;
  private int definitionLeft = 0;
  private Paint definitionPaint;
  private Animation inAnimation;
  private Bitmap indicatorIcon;
  private Rect indicatorIconDstRect;
  private String mainTitle = null;
  private Paint mainTitlePaint;
  private Bitmap menuIcon;
  private Rect menuIconDstRect;
  private Rect menuIconSrcRect;
  private String menuKey = "";
  private float menuNoticeLeft;
  private Paint menuNoticePaint;
  private int paddingLeft = 0;
  private int paddingTop = 0;
  private String programName = null;
  private int programNameLeft = 0;
  private Paint programNamePaint;
  private int seperatorLineLeft = 0;
  private Paint seperatorLinePaint;
  private Rect seperatorLineRect;
  private String titleBarInfo = null;
  private Paint totalTitlePaint;
  private int uiMode = 0;
  private Bitmap upIcon;
  private String viceTitle = null;
  private int viceTitleLeft = 0;
  private Paint viceTitlePaint;

  static
  {
    TEXTSIZE_VICE_TITLE = App.Operation(28.0F);
  }

  public MplayerTitleView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initViews();
  }

  public MplayerTitleView(Context paramContext, AttributeSet paramAttributeSet)
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

  private void drawInTimeshiftMode(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
    Rect localRect1 = new Rect(0, this.paddingTop, 0, App.Operation(60.0F));
    int i = MplayerTools.getTextDrawingBaseline(this.channelNumPaint, localRect1);
    if ((this.channelNum != null) && (!this.channelNum.isEmpty()))
    {
      paramCanvas.drawText(this.channelNum, this.paddingLeft, i, this.channelNumPaint);
      this.seperatorLineRect.top = (localRect1.centerY() - App.Operation(27.0F));
      this.seperatorLineRect.bottom = (localRect1.centerY() + App.Operation(30.0F));
      paramCanvas.save();
      paramCanvas.clipRect(this.seperatorLineRect);
      paramCanvas.drawRect(this.seperatorLineRect, this.seperatorLinePaint);
      paramCanvas.restore();
    }
    if ((this.channelName != null) && (!this.channelName.isEmpty()))
    {
      Rect localRect4 = new Rect(this.channelNameLeft, localRect1.top, this.channelNameLeft + App.Operation(10.0F), App.Operation(33.0F));
      paramCanvas.drawText(this.channelName, this.channelNameLeft, MplayerTools.getTextDrawingBaseline(this.channelNamePaint, localRect4), this.channelNamePaint);
    }
    if (this.programName != null)
    {
      Rect localRect2 = new Rect(this.programNameLeft, i - App.Operation(19.0F), App.Operation(10.0F), i);
      paramCanvas.drawText(this.programName, this.programNameLeft, MplayerTools.getTextDrawingBaseline(this.programNamePaint, localRect2), this.programNamePaint);
    }
    if (this.menuKey != null)
    {
      int k = MplayerTools.getTextDrawingBaseline(this.menuNoticePaint, localRect1) + App.Operation(4.0F);
      paramCanvas.drawText("菜单", this.menuNoticeLeft, k, this.menuNoticePaint);
      this.menuIconSrcRect.left = 0;
      this.menuIconSrcRect.right = this.menuIcon.getWidth();
      this.menuIconSrcRect.top = 0;
      this.menuIconSrcRect.bottom = this.menuIcon.getHeight();
      Paint.FontMetricsInt localFontMetricsInt = this.menuNoticePaint.getFontMetricsInt();
      int m = localFontMetricsInt.bottom - localFontMetricsInt.top;
      int n = localRect1.centerY();
      this.menuIconDstRect.left = ((int)(this.menuNoticeLeft - m - ICON_PANDDING));
      this.menuIconDstRect.right = (m + this.menuIconDstRect.left);
      this.menuIconDstRect.bottom = (n + m / 2 + App.Operation(4.0F));
      this.menuIconDstRect.top = (this.menuIconDstRect.bottom - m);
      paramCanvas.drawBitmap(this.menuIcon, this.menuIconSrcRect, this.menuIconDstRect, this.bgPaint);
      float f = this.menuIconDstRect.left - getTextWidth("选台", this.menuNoticePaint.getTextSize()) - 2 * ICON_PANDDING;
      paramCanvas.drawText("选台", f, k, this.menuNoticePaint);
      Rect localRect3 = new Rect();
      localRect3.right = ((int)(f - ICON_PANDDING));
      localRect3.bottom = (n + m / 2 + App.Operation(4.0F));
      localRect3.left = (localRect3.right - m);
      localRect3.top = (localRect3.bottom - m);
      paramCanvas.drawBitmap(this.upIcon, new Rect(0, 0, this.upIcon.getWidth(), this.upIcon.getHeight()), localRect3, this.bgPaint);
    }
    if (this.currentTime != null)
    {
      int j = MplayerTools.getTextDrawingBaseline(this.currentTimePaint, localRect1);
      paramCanvas.drawText(this.currentTime, this.currentTimeLeft, j, this.currentTimePaint);
    }
  }

  private void drawInVodMode(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
    Rect localRect1 = new Rect(0, 0, 0, App.Operation(150.0F));
    char[] arrayOfChar = new char[0];
    if (this.indicatorIcon != null)
    {
      int i4 = localRect1.centerY();
      this.indicatorIconDstRect.left = this.paddingLeft;
      this.indicatorIconDstRect.right = (this.indicatorIconDstRect.left + App.Operation(this.indicatorIcon.getWidth()));
      this.indicatorIconDstRect.top = (i4 - App.Operation(this.indicatorIcon.getHeight()) / 2);
      this.indicatorIconDstRect.bottom = (i4 + App.Operation(this.indicatorIcon.getHeight()) / 2);
      paramCanvas.drawBitmap(this.indicatorIcon, null, this.indicatorIconDstRect, this.bgPaint);
    }
    String str1 = this.titleBarInfo;
    int i = 0;
    int i3;
    if (str1 != null)
    {
      i3 = MplayerTools.getTextDrawingBaseline(this.totalTitlePaint, localRect1);
      if (getTextWidth(this.titleBarInfo, this.totalTitlePaint.getTextSize()) <= App.Operation(764.0F))
        break label871;
      i = 1;
      arrayOfChar = MplayerTools.getTextWidthPos(this.totalTitlePaint, this.titleBarInfo, App.Operation(764.0F));
      if (arrayOfChar != null)
        paramCanvas.drawText(arrayOfChar, 0, arrayOfChar.length, this.indicatorIconDstRect.right + App.Operation(11.0F), i3, this.totalTitlePaint);
    }
    int j = this.indicatorIconDstRect.right + App.Operation(11.0F);
    if (i != 0);
    for (String str2 = String.valueOf(arrayOfChar); ; str2 = this.titleBarInfo)
    {
      refreshTitlePosition(j, str2);
      if (!TextUtils.isEmpty(this.definition))
      {
        int i2 = localRect1.centerY();
        float f2 = this.definitionPaint.getTextSize() + App.Operation(10.0F);
        float f3 = this.definitionPaint.measureText(this.definition);
        this.definitionBkgRect = new RectF();
        this.definitionBkgRect.left = (this.definitionLeft - ICON_PANDDING);
        this.definitionBkgRect.right = (f3 + this.definitionLeft + ICON_PANDDING);
        this.definitionBkgRect.bottom = (i2 + f2 / 2.0F);
        this.definitionBkgRect.top = (this.definitionBkgRect.bottom - f2);
        float f4 = MplayerTools.getTextDrawingBaseline(this.definitionPaint, localRect1);
        paramCanvas.drawRoundRect(this.definitionBkgRect, DEFINITION_BKG_ROUND_CORNER, DEFINITION_BKG_ROUND_CORNER, this.definitionBkgPaint);
        paramCanvas.drawText(this.definition, this.definitionLeft, f4, this.definitionPaint);
      }
      if (this.currentTime != null)
      {
        int i1 = MplayerTools.getTextDrawingBaseline(this.currentTimePaint, localRect1);
        paramCanvas.drawText(this.currentTime, this.currentTimeLeft, i1, this.currentTimePaint);
      }
      if (this.menuKey != null)
      {
        int k = MplayerTools.getTextDrawingBaseline(this.menuNoticePaint, localRect1) + App.Operation(4.0F);
        paramCanvas.drawText("菜单", this.menuNoticeLeft, k, this.menuNoticePaint);
        this.menuIconSrcRect.left = 0;
        this.menuIconSrcRect.right = this.menuIcon.getWidth();
        this.menuIconSrcRect.top = 0;
        this.menuIconSrcRect.bottom = this.menuIcon.getHeight();
        Paint.FontMetricsInt localFontMetricsInt = this.menuNoticePaint.getFontMetricsInt();
        int m = localFontMetricsInt.bottom - localFontMetricsInt.top;
        int n = localRect1.centerY();
        this.menuIconDstRect.left = ((int)(this.menuNoticeLeft - m - ICON_PANDDING));
        this.menuIconDstRect.right = (m + this.menuIconDstRect.left);
        this.menuIconDstRect.bottom = (n + m / 2 + App.Operation(4.0F));
        this.menuIconDstRect.top = (this.menuIconDstRect.bottom - m);
        paramCanvas.drawBitmap(this.menuIcon, this.menuIconSrcRect, this.menuIconDstRect, this.bgPaint);
        String str3 = "选集";
        if (2 == this.uiMode)
          str3 = "选台";
        float f1 = this.menuIconDstRect.left - getTextWidth(str3, this.menuNoticePaint.getTextSize()) - 2 * ICON_PANDDING;
        paramCanvas.drawText(str3, f1, k, this.menuNoticePaint);
        Rect localRect2 = new Rect();
        localRect2.right = ((int)(f1 - ICON_PANDDING));
        localRect2.bottom = (n + m / 2 + App.Operation(4.0F));
        localRect2.left = (localRect2.right - m);
        localRect2.top = (localRect2.bottom - m);
        paramCanvas.drawBitmap(this.upIcon, new Rect(0, 0, this.upIcon.getWidth(), this.upIcon.getHeight()), localRect2, this.bgPaint);
      }
      return;
      label871: paramCanvas.drawText(this.titleBarInfo, this.indicatorIconDstRect.right + App.Operation(11.0F), i3, this.totalTitlePaint);
      i = 0;
      break;
    }
  }

  private int getTextWidth(String paramString, float paramFloat)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramFloat <= 0.0F))
      return 0;
    Paint localPaint = new Paint();
    localPaint.setTextSize(paramFloat);
    Rect localRect = new Rect();
    localPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    return localRect.width();
  }

  private void initViews()
  {
    this.calendar = Calendar.getInstance();
    this.bgPaint = new Paint();
    this.bgPaint.setAlpha(255);
    setBackgroundResource(2130837588);
    this.menuIcon = decodeResource(getResources(), 2130837580);
    this.menuIconSrcRect = new Rect(0, 0, 0, 0);
    this.menuIconDstRect = new Rect(0, 0, 0, 0);
    this.upIcon = decodeResource(getResources(), 2130837581);
    this.indicatorIcon = decodeResource(getResources(), 2130837589);
    this.indicatorIconDstRect = new Rect(0, 0, 0, 0);
    this.mainTitlePaint = new Paint();
    this.mainTitlePaint.setColor(-1711276033);
    this.mainTitlePaint.setAntiAlias(true);
    this.mainTitlePaint.setTextSize(TEXT_SIZE_MAIN_TITLE);
    this.totalTitlePaint = new Paint();
    this.totalTitlePaint.setColor(-1711276033);
    this.totalTitlePaint.setAntiAlias(true);
    this.totalTitlePaint.setTextSize(TEXT_SIZE_TOTAL_TITLE);
    this.viceTitlePaint = new Paint();
    this.viceTitlePaint.setColor(-1711276033);
    this.viceTitlePaint.setAntiAlias(true);
    this.viceTitlePaint.setTextSize(TEXTSIZE_VICE_TITLE);
    this.definitionPaint = new Paint();
    this.definitionPaint.setColor(-13552330);
    this.definitionPaint.setAntiAlias(true);
    this.definitionPaint.setTextSize(App.Operation(16.0F));
    this.channelNumPaint = new Paint();
    this.channelNumPaint.setColor(-1711276033);
    this.channelNumPaint.setAntiAlias(true);
    this.channelNumPaint.setTextSize(App.Operation(80.0F));
    this.programNamePaint = new Paint();
    this.programNamePaint.setColor(-1711276033);
    this.programNamePaint.setAntiAlias(true);
    this.programNamePaint.setTextSize(App.Operation(19.0F));
    this.channelNamePaint = new Paint();
    this.channelNamePaint.setColor(-1711276033);
    this.channelNamePaint.setAntiAlias(true);
    this.channelNamePaint.setTextSize(App.Operation(36.0F));
    this.seperatorLinePaint = new Paint();
    this.seperatorLinePaint.setColor(-1711276033);
    this.seperatorLinePaint.setAntiAlias(true);
    this.currentTimePaint = new Paint();
    this.currentTimePaint.setColor(-7697784);
    this.currentTimePaint.setAntiAlias(true);
    this.currentTimePaint.setTextSize(App.Operation(36.0F));
    this.menuNoticePaint = new Paint();
    this.menuNoticePaint.setColor(-6908266);
    this.menuNoticePaint.setAntiAlias(true);
    this.menuNoticePaint.setTextSize(App.Operation(22.0F));
    this.definitionBkgPaint = new Paint();
    this.definitionBkgPaint.setColor(-1711276033);
    this.definitionBkgPaint.setAntiAlias(true);
    this.definitionBkgPaint.setStyle(Paint.Style.FILL);
    Rect localRect = new Rect(0, 0, 0, 0);
    this.calendar.setTimeInMillis(SystemTimeManager.getCurrentServerTime());
    this.currentTime = DateFormat.format("kk:mm", this.calendar).toString();
    this.currentTimePaint.getTextBounds(this.currentTime, 0, this.currentTime.length(), localRect);
    this.inAnimation = AnimationUtils.loadAnimation(this.context, 2130968579);
  }

  private void refreshTitlePosition(int paramInt, String paramString)
  {
    this.definitionLeft = (paramInt + getTextWidth(paramString, this.totalTitlePaint.getTextSize()) + App.Operation(16.0F));
  }

  public void display()
  {
    if (getVisibility() == 0)
      return;
    setVisibility(0);
    startAnimation(this.inAnimation);
  }

  public void hide()
  {
    if (getVisibility() == 4)
      return;
    setVisibility(4);
  }

  public void initMenuNoticeText(String paramString1, String paramString2, String paramString3)
  {
    this.menuKey = paramString3;
    postInvalidate();
  }

  public void initUIPara(int paramInt1, int paramInt2, int paramInt3)
  {
    this.uiMode = paramInt1;
    this.paddingLeft = App.Operation(61.0F);
    this.paddingTop = App.Operation(36.0F);
    this.currentTimeLeft = (App.Operation(1280.0F) - App.Operation(60.0F) - getTextWidth(this.currentTime, this.currentTimePaint.getTextSize()));
    this.menuNoticeLeft = (this.currentTimeLeft - App.Operation(27.0F) - getTextWidth("菜单", this.menuNoticePaint.getTextSize()));
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (getVisibility() == 4);
    do
    {
      return;
      if ((1 == this.uiMode) || (2 == this.uiMode))
      {
        drawInVodMode(paramCanvas);
        return;
      }
    }
    while (3 != this.uiMode);
    drawInTimeshiftMode(paramCanvas);
  }

  public boolean onInputEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      Logger.i("MplayerTitleView", "onInputEvent() keyDown code:" + paramKeyEvent.getKeyCode());
      display();
      Logger.i("MplayerTitleView", "onInputEvent() keyDown 事件被处理");
      return true;
    }
    if (paramKeyEvent.getAction() == 1)
    {
      Logger.i("MplayerTitleView", "onInputEvent() keyUp code:" + paramKeyEvent.getKeyCode());
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        Logger.i("MplayerTitleView", "onInputEvent() keyUp 事件未被处理");
        return false;
      case 4:
      }
      hide();
      return true;
    }
    return false;
  }

  public void setCurrentTime()
  {
    this.calendar.setTimeInMillis(SystemTimeManager.getCurrentServerTime());
    this.currentTime = DateFormat.format("kk:mm", this.calendar).toString();
    invalidate();
  }

  public void setVideoDiscribForTimeshift(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null))
    {
      Logger.i("MplayerTitleView", "setVideoDiscribForTimeshift() para is null");
      return;
    }
    this.channelNum = paramString1;
    this.programName = paramString2;
    this.channelName = paramString3;
    Rect localRect = new Rect(0, 0, 0, 0);
    if (paramString1.length() > 0)
      this.channelNumPaint.getTextBounds(paramString1, 0, paramString1.length(), localRect);
    this.seperatorLineLeft = (this.paddingLeft + localRect.width() + App.Operation(20.0F));
    this.programNameLeft = (this.seperatorLineLeft + App.Operation(2.0F) + App.Operation(15.0F));
    this.channelNameLeft = this.programNameLeft;
    this.seperatorLineRect = new Rect(this.seperatorLineLeft, this.paddingTop, this.seperatorLineLeft + App.Operation(2.0F), this.paddingTop + App.Operation(60.0F));
  }

  public void setVideoDiscribForVod(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null))
      return;
    this.titleBarInfo = (paramString1 + " " + paramString2);
    this.definition = paramString3;
    invalidate();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerTitleView
 * JD-Core Version:    0.6.2
 */