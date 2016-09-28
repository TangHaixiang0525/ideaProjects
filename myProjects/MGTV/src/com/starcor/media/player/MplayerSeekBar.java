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
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.service.SystemTimeManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MplayerSeekBar extends View
  implements View.OnTouchListener
{
  public static final int MAX_PROGRESS_RANG = 10000;
  private static final int MOVING_CMD_GO = 1;
  private static final int MOVING_CMD_INVALID = 0;
  private static final int MOVING_CMD_STOP = 2;
  private static final int MOVING_DIRECTION_BACK = 2;
  private static final int MOVING_DIRECTION_FRONT = 1;
  private static final int MOVING_DIRECTION_INVALID = 0;
  private static final int PLAYER_FAST_TIMER = 1280;
  public static final int PLAY_STATUS_FFORWARD = 3;
  public static final int PLAY_STATUS_NORMAL = 1;
  public static final int PLAY_STATUS_PAUSE = 2;
  public static final int PLAY_STATUS_REWIND = 4;
  private static final int RESPONSE_SEEK_DELAY = 1000;
  public static final int S_2_MS = 1000;
  private static final String TAG = "MplayerSeekBar";
  public static final int TIME_NODE_ACTION_JUMPHEAD = 1;
  public static final int TIME_NODE_ACTION_JUMPTAIL = 2;
  public static final int TIME_NODE_ACTION_TRAIL = 3;
  public static final int UI_MODE_PLAYBACK = 2;
  public static final int UI_MODE_TIMESHIFT = 3;
  public static final int UI_MODE_VOD = 1;
  private static final String defaultRightTime = "00:00:00";
  private static final PaintFlagsDrawFilter paintFlag = new PaintFlagsDrawFilter(0, 3);
  private int SeekFlage = 0;
  private int bkgHeight = 0;
  private int bkgWidth = 0;
  private int checkClickInterval = 100;
  Context context;
  int count = 0;
  private int curMovingCmd = 0;
  private int curMovingDirection = 0;
  private int curPlayPos = 0;
  private Paint definitionPaint;
  private int freePlayUiPos = 10000;
  private Animation inAnimation;
  private boolean isCalcKeyUpTime = false;
  private boolean isKeyDown;
  private boolean isPreMode = false;
  private long keyDownTime = 0L;
  private long keyDownTimeLen = 0L;
  private int keyResponseTimes;
  private long lastKeyUpTime = 0L;
  private int leftTimeWidth = App.Operation(85.0F);
  private Bitmap line;
  private Rect lineSrcRect;
  IMplayerSeekBarListener lsnr = null;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1280:
      }
      MplayerSeekBar.this.playerTimerFastTask();
    }
  };
  private Runnable mRun = new Runnable()
  {
    public void run()
    {
      MplayerSeekBar.this.lsnr.onUserSeekEnd(MplayerSeekBar.this.playProgressPos);
      MplayerSeekBar.access$202(MplayerSeekBar.this, MplayerSeekBar.this.seekStopPos);
      MplayerSeekBar.access$402(MplayerSeekBar.this, MplayerSeekBar.this.seekTime);
      MplayerSeekBar.access$602(MplayerSeekBar.this, MplayerSeekBar.access$302(MplayerSeekBar.this, 0));
      MplayerSeekBar.access$702(MplayerSeekBar.this, 0);
      MplayerSeekBar.access$802(MplayerSeekBar.this, 0);
      MplayerSeekBar.access$902(MplayerSeekBar.this, 1);
      MplayerSeekBar.this.lsnr.notifyCurrentState(MplayerSeekBar.this.playStatusFlag);
      MplayerSeekBar.this.postInvalidate();
    }
  };
  private int normalInterval = 100;
  private int paddingBottom = App.Operation(60.0F);
  private int paddingLeft = 0;
  private int paddingRight = 0;
  private int playProgressLeft;
  private Paint playProgressPaint;
  private long playProgressPos;
  private int playProgressRight;
  private int playProgressWidth = 0;
  private int playStatusFlag = 0;
  private String playingTime;
  private double prePlayNode = -1.0D;
  private int progressBarHeight = 0;
  private int progressBarWidth = 0;
  private int progressBottom = 0;
  private int progressTop = 0;
  private Paint remainProgressPaint;
  private String rightSideTime = "00:00:00";
  private Paint rightSideTimeTextPaint;
  private int rightTimeWidth = App.Operation(85.0F);
  Runnable runnable = new Runnable()
  {
    public void run()
    {
      if (1 == MplayerSeekBar.this.curMovingCmd)
        MplayerSeekBar.this.playerTimerFastTask();
      MplayerSeekBar.this.mHandler.postDelayed(this, MplayerSeekBar.this.timerInterval);
    }
  };
  final int seekBarHeight = App.Operation(720.0F);
  private int seekEndPos = this.playProgressRight;
  private Paint seekPaint;
  private int seekPos = 0;
  private String seekProgram;
  private Paint seekProgramPaint;
  private Rect seekRect;
  private int seekStartPos = 0;
  private int seekStopPos = 0;
  private String seekTime;
  private int seekTimeBottom;
  private Paint seekTimeTailTextPaint;
  private boolean threadExitFlag = false;
  private Bitmap thumb;
  private int thumbDisplay = 0;
  private Paint thumbPaint;
  private Rect thumbRect;
  private Rect thumbSrcRect;
  private List<?> timeNodeData;
  private Paint timeNodePaint;
  private int timerInterval = 1000;
  private int uiMode = 0;

  public MplayerSeekBar(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initViews();
  }

  public MplayerSeekBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    initViews();
  }

  private void SeekBarToWhere()
  {
    if (this.seekStopPos + this.playProgressLeft > this.seekStartPos)
      this.SeekFlage = 1;
    while (true)
    {
      this.seekEndPos = (this.seekStopPos + this.playProgressLeft);
      return;
      if (this.seekStopPos + this.playProgressLeft < this.seekStartPos)
        this.SeekFlage = 2;
    }
  }

  private int calculateSeekStep()
  {
    long l1 = System.currentTimeMillis();
    long l2 = l1 - this.keyDownTime;
    if (l2 <= 2000L)
    {
      Logger.i("MplayerSeekBar", "Now sysTime:" + l1 + ",timeLen :" + l2 + ",step == 30;");
      return 30;
    }
    Logger.i("MplayerSeekBar", "Now sysTime:" + l1 + ",timeLen：" + l2 + ",step == 50;");
    return 50;
  }

  private boolean checkEndOfFForwardOrRewind(boolean paramBoolean)
  {
    if (this.curMovingCmd != 1)
      return true;
    long l = System.currentTimeMillis();
    if (this.isCalcKeyUpTime)
    {
      if (paramBoolean)
        this.lastKeyUpTime = l;
      if ((this.lastKeyUpTime > 0L) && (l - this.lastKeyUpTime > 100L))
      {
        this.curMovingCmd = 2;
        return true;
      }
    }
    else if (paramBoolean)
    {
      this.curMovingCmd = 2;
      return true;
    }
    if (1 == this.curMovingCmd)
      this.keyDownTimeLen = (l - this.keyDownTime);
    Logger.i("MplayerSeekBar", "Now keyDownTimeLen is：" + this.keyDownTimeLen);
    return false;
  }

  private int checkUiPos(int paramInt)
  {
    return paramInt;
  }

  private Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramResources.openRawResource(paramInt, localTypedValue);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTargetDensity = localTypedValue.density;
    return BitmapFactory.decodeResource(paramResources, paramInt, localOptions);
  }

  private void drawPrePlayNode(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    if ((!this.isPreMode) || (this.prePlayNode == -1.0D));
    do
    {
      return;
      this.freePlayUiPos = ((int)(10000.0D * this.prePlayNode));
    }
    while ((this.freePlayUiPos < 0) || (this.freePlayUiPos > 10000));
    int i = App.Operation(4.0F);
    int j = App.Operation(4.0F);
    int k = paramInt2 + this.progressBarHeight / 2 - i / 2;
    int m = paramInt1 + this.freePlayUiPos * this.progressBarWidth / 10000;
    if (m < this.playProgressRight)
      this.timeNodePaint.setColor(-1);
    while (true)
    {
      Rect localRect = new Rect(m, k, m + j, k + i);
      paramCanvas.save();
      paramCanvas.clipRect(localRect);
      paramCanvas.drawCircle(localRect.centerX(), localRect.centerY(), App.Operation(2.0F), this.timeNodePaint);
      paramCanvas.restore();
      return;
      this.timeNodePaint.setColor(-9466381);
    }
  }

  private void drawTimeNode(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    if (this.timeNodeData == null);
    int i;
    int j;
    int k;
    int n;
    while (true)
    {
      return;
      i = App.Operation(4.0F);
      j = App.Operation(4.0F);
      k = paramInt2 - i / 2 + this.progressBarHeight / 2;
      for (int m = 0; m < this.timeNodeData.size(); m++)
      {
        long l = ((Long)this.timeNodeData.get(m)).longValue();
        n = this.lsnr.playProgress2uiProgress(l);
        if ((n >= 0) && (n <= 10000))
          break label107;
      }
    }
    label107: int i1 = paramInt1 + n * this.progressBarWidth / 10000;
    if (i1 < this.playProgressRight)
      this.timeNodePaint.setColor(-1);
    while (true)
    {
      Rect localRect = new Rect(i1, k, i1 + j, k + i);
      paramCanvas.save();
      paramCanvas.clipRect(localRect);
      paramCanvas.drawCircle(localRect.centerX(), localRect.centerY(), App.Operation(2.0F), this.timeNodePaint);
      paramCanvas.restore();
      break;
      this.timeNodePaint.setColor(-9466381);
    }
  }

  private String getRightSideTimeForTimeShift()
  {
    if (this.uiMode == 3)
    {
      long l = SystemTimeManager.getCurrentServerTime();
      return new SimpleDateFormat("HH:mm:ss").format(new Date(l));
    }
    return this.rightSideTime;
  }

  private long getSeekPos()
  {
    int i = getSingleClickStep(calculateSeekStep());
    if (2 == this.curMovingDirection)
      if (this.curPlayPos > i)
        this.seekPos = (this.curPlayPos - i);
    while (true)
    {
      if (1 == this.uiMode)
        this.seekPos = checkUiPos(this.seekPos);
      Logger.i("MplayerSeekBar", "getSeekPos() dir:" + this.curMovingDirection + ", seekPos:" + this.seekPos + ", curPlayPos:" + this.curPlayPos + ", step:" + i);
      this.curPlayPos = this.seekPos;
      return this.seekPos;
      this.seekPos = 0;
      continue;
      this.seekPos = (i + this.curPlayPos);
      if (this.seekPos > 10000)
        this.seekPos = 10000;
    }
  }

  private int getSingleClickStep(int paramInt)
  {
    if (this.uiMode == 3)
      return paramInt;
    long l = System.currentTimeMillis() - this.keyDownTime;
    if ((l > 0L) && (l < 250L))
    {
      if (this.keyResponseTimes != 0)
        break label90;
      Logger.i("MplayerSeekBar", "timeLen:" + l);
    }
    label90: for (paramInt = this.lsnr.playProgress2uiProgress(5000L); ; paramInt = 0)
    {
      this.keyResponseTimes = (1 + this.keyResponseTimes);
      return paramInt;
    }
  }

  private void initViews()
  {
    setBackgroundResource(2130837585);
    this.thumbPaint = new Paint();
    this.thumbPaint.setAlpha(255);
    this.thumb = decodeResource(getResources(), 2130837587);
    this.thumbRect = new Rect(0, 0, 0, 0);
    this.thumbSrcRect = new Rect(0, 0, this.thumb.getWidth(), this.thumb.getHeight());
    this.line = decodeResource(getResources(), 2130837586);
    this.lineSrcRect = new Rect(0, 0, this.line.getWidth(), this.line.getHeight());
    this.playProgressPaint = new Paint();
    this.playProgressPaint.setColor(-9466381);
    this.playProgressPaint.setAntiAlias(true);
    this.remainProgressPaint = new Paint();
    this.remainProgressPaint.setColor(-1722130317);
    this.remainProgressPaint.setStyle(Paint.Style.STROKE);
    this.remainProgressPaint.setStrokeWidth(App.Operation(4.0F));
    this.remainProgressPaint.setAntiAlias(true);
    this.seekTimeTailTextPaint = new Paint();
    this.seekTimeTailTextPaint.setTextSize(App.Operation(25.0F));
    this.seekTimeTailTextPaint.setColor(-3218950);
    this.seekTimeTailTextPaint.setAntiAlias(true);
    this.rightSideTimeTextPaint = new Paint();
    this.rightSideTimeTextPaint.setTextSize(App.Operation(21.0F));
    this.rightSideTimeTextPaint.setColor(-3684409);
    this.rightSideTimeTextPaint.setAntiAlias(true);
    this.timeNodePaint = new Paint();
    this.timeNodePaint.setColor(-1);
    this.timeNodePaint.setAntiAlias(true);
    this.definitionPaint = new Paint();
    this.definitionPaint.setColor(-855638017);
    this.definitionPaint.setAntiAlias(true);
    this.definitionPaint.setTextSize(App.Operation(24.0F));
    this.seekProgramPaint = new Paint();
    this.seekProgramPaint.setColor(-1711276033);
    this.seekProgramPaint.setAntiAlias(true);
    this.seekProgramPaint.setTextSize(App.Operation(22.0F));
    this.seekPaint = new Paint();
    this.seekPaint.setColor(-10922129);
    this.seekPaint.setAntiAlias(true);
    this.inAnimation = AnimationUtils.loadAnimation(this.context, 2130968578);
  }

  private void playerTimerFastTask()
  {
    refreshProgressByOperation(false);
  }

  private void printDebugLog(String paramString)
  {
    int i = this.count;
    this.count = (i + 1);
    if (i % 10 == 0)
      Logger.d("MplayerSeekBar", "bin.jing debug " + paramString);
  }

  private void refreshProgress(long paramLong)
  {
    this.curPlayPos = this.lsnr.playProgress2uiProgress(paramLong);
    if (1 == this.uiMode)
    {
      this.curPlayPos = checkUiPos(this.curPlayPos);
      if (this.isPreMode)
        Logger.d("MplayerSeekBar", "refreshProgress curPlayPos: " + this.curPlayPos + ", freePlayUiPos: " + this.freePlayUiPos);
      if ((this.freePlayUiPos <= this.curPlayPos) && (this.isPreMode))
      {
        this.curMovingCmd = 0;
        this.curPlayPos = this.freePlayUiPos;
        this.lsnr.onPlayToPreNode();
        return;
      }
    }
    if (this.curPlayPos > 10000)
      this.curPlayPos = 10000;
    this.playingTime = this.lsnr.getPosDiscribByPlayPos(paramLong);
    this.seekProgram = this.lsnr.getCurrentProgramName(paramLong);
    this.playProgressWidth = (this.curPlayPos * this.progressBarWidth / 10000);
    invalidate();
  }

  private void refreshProgressByOperation(boolean paramBoolean)
  {
    if (this.curMovingCmd == 0);
    boolean bool;
    do
    {
      return;
      bool = checkEndOfFForwardOrRewind(paramBoolean);
      seekTo((int)getSeekPos(), bool);
    }
    while (!bool);
    this.timerInterval = this.normalInterval;
    this.keyDownTime = 0L;
    this.keyResponseTimes = 0;
    this.keyDownTimeLen = 0L;
    this.lastKeyUpTime = 0L;
  }

  private void startFForward()
  {
    if (3 == this.playStatusFlag)
      return;
    this.playStatusFlag = 3;
    this.lsnr.notifyCurrentState(this.playStatusFlag);
    this.lsnr.onUserSeekStart();
    this.mHandler.removeCallbacks(this.mRun);
    this.playProgressRight = (this.playProgressWidth + this.playProgressLeft);
    this.seekStartPos = this.playProgressRight;
    this.curMovingDirection = 1;
    this.curMovingCmd = 1;
    if (0L == this.keyDownTime)
    {
      this.keyDownTime = System.currentTimeMillis();
      Logger.i("MplayerSeekBar", "startFForward() 按键时间点为：" + this.keyDownTime);
    }
    this.timerInterval = this.checkClickInterval;
  }

  private void startPlayerTimer()
  {
    this.mHandler.post(this.runnable);
  }

  private void startRewind()
  {
    if (4 == this.playStatusFlag)
      return;
    this.playStatusFlag = 4;
    this.lsnr.notifyCurrentState(this.playStatusFlag);
    this.lsnr.onUserSeekStart();
    this.mHandler.removeCallbacks(this.mRun);
    this.playProgressRight = (this.playProgressWidth + this.playProgressLeft);
    this.seekStartPos = this.playProgressRight;
    this.curMovingDirection = 2;
    this.curMovingCmd = 1;
    if (0L == this.keyDownTime)
    {
      this.keyDownTime = System.currentTimeMillis();
      Logger.i("MplayerSeekBar", "startRewind() 按键时间点为：" + this.keyDownTime);
    }
    this.timerInterval = this.checkClickInterval;
  }

  public void display()
  {
    if (getVisibility() == 0)
      return;
    Logger.d("MplayerSeekBar", "display");
    this.lsnr.notifyCurrentState(this.playStatusFlag);
    setVisibility(0);
    startAnimation(this.inAnimation);
  }

  public void displayThumb(int paramInt)
  {
    Logger.i("MplayerSeekBar", "displayThumb() display:" + paramInt + ", thumbDisplay:" + this.thumbDisplay);
    if (paramInt == this.thumbDisplay)
      return;
    this.thumbDisplay = paramInt;
    invalidate();
  }

  public int getMax()
  {
    return 10000;
  }

  public void hide()
  {
    if (getVisibility() == 4)
      return;
    Logger.d("MplayerSeekBar", "hide");
    this.lsnr.notifyCurrentState(-1);
    setVisibility(4);
  }

  public int init(IMplayerSeekBarListener paramIMplayerSeekBarListener, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramIMplayerSeekBarListener == null)
      return -1;
    this.lsnr = paramIMplayerSeekBarListener;
    this.normalInterval = paramInt1;
    this.checkClickInterval = paramInt2;
    this.timerInterval = paramInt1;
    startPlayerTimer();
    this.playStatusFlag = 1;
    paramIMplayerSeekBarListener.notifyCurrentState(this.playStatusFlag);
    return 0;
  }

  public void initUIPara(int paramInt1, int paramInt2, int paramInt3)
  {
    this.uiMode = paramInt1;
    this.paddingLeft = App.Operation(60.0F);
    this.paddingRight = App.Operation(60.0F);
    this.bkgWidth = paramInt2;
    this.bkgHeight = paramInt3;
    this.playProgressLeft = (this.paddingLeft + this.leftTimeWidth + App.Operation(28.0F));
    Rect localRect = new Rect();
    this.rightSideTimeTextPaint.getTextBounds("00:00:00", 0, "00:00:00".length(), localRect);
    this.progressBarWidth = (paramInt2 - this.paddingLeft - this.paddingRight - this.rightTimeWidth - this.leftTimeWidth - App.Operation(60.0F));
    this.progressBarHeight = App.Operation(20.0F);
    this.progressTop = (App.Operation(720.0F) - this.progressBarHeight - this.paddingBottom);
    this.progressBottom = (this.progressTop + this.progressBarHeight);
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (getVisibility() == 4)
      return;
    paramCanvas.setDrawFilter(paintFlag);
    this.playProgressRight = (this.playProgressWidth + this.playProgressLeft);
    int i = this.playProgressRight;
    int j = this.bkgWidth - this.rightTimeWidth - this.paddingRight - App.Operation(28.0F);
    RectF localRectF1 = new RectF(i - App.Operation(1.0F), this.progressTop, j - App.Operation(2.0F), this.progressBottom);
    paramCanvas.save();
    paramCanvas.clipRect(localRectF1);
    paramCanvas.drawRect(localRectF1, this.remainProgressPaint);
    paramCanvas.restore();
    RectF localRectF2 = new RectF(this.playProgressLeft, this.progressTop, this.playProgressRight, this.progressBottom);
    RectF localRectF3 = new RectF(localRectF2);
    if ((this.playingTime != null) && (this.playingTime.length() > 0))
    {
      int n = (int)MplayerTools.getTextDrawingBaseline(this.rightSideTimeTextPaint, localRectF3);
      this.rightSideTimeTextPaint.setTextAlign(Paint.Align.RIGHT);
      paramCanvas.drawText(this.playingTime, this.playProgressLeft - App.Operation(28.0F), n, this.rightSideTimeTextPaint);
    }
    paramCanvas.save();
    paramCanvas.clipRect(localRectF2);
    paramCanvas.drawRect(localRectF2, this.playProgressPaint);
    paramCanvas.restore();
    SeekBarToWhere();
    if (this.SeekFlage == 1)
    {
      this.seekRect = new Rect(this.playProgressRight, this.progressTop, this.seekEndPos, this.progressBottom);
      paramCanvas.drawRect(this.seekRect, this.seekPaint);
      label298: if ((this.seekStartPos == this.seekStopPos) && (this.playStatusFlag != 3) && (this.playStatusFlag != 4))
        this.seekEndPos = this.playProgressRight;
      drawTimeNode(paramCanvas, this.playProgressLeft, this.progressTop);
      drawPrePlayNode(paramCanvas, this.playProgressLeft, this.progressTop);
      this.thumbRect.left = (this.seekEndPos - App.Operation(76.0F));
      this.thumbRect.bottom = (this.progressTop - App.Operation(13.0F));
      this.thumbRect.top = (this.thumbRect.bottom - App.Operation(51.0F));
      this.thumbRect.right = (this.thumbRect.left + App.Operation(150.0F));
      paramCanvas.drawBitmap(this.thumb, this.thumbSrcRect, this.thumbRect, this.thumbPaint);
      Rect localRect1 = new Rect(this.seekEndPos - App.Operation(1.0F), this.thumbRect.bottom, this.seekEndPos + App.Operation(1.0F), this.progressTop);
      paramCanvas.drawBitmap(this.line, this.lineSrcRect, localRect1, this.thumbPaint);
      if (this.curMovingDirection == 0)
        this.seekTime = this.playingTime;
      if ((this.seekTime == null) || (this.seekTime.length() == 0))
        this.seekTime = "00:00:00";
      if ((this.seekTime != null) && (this.seekTime.length() > 0))
      {
        this.seekTimeBottom = (MplayerTools.getTextDrawingBaseline(this.seekTimeTailTextPaint, this.thumbRect) - App.Operation(5.0F));
        this.seekTimeTailTextPaint.setTextAlign(Paint.Align.CENTER);
        paramCanvas.drawText(this.seekTime, this.seekEndPos, this.seekTimeBottom, this.seekTimeTailTextPaint);
      }
      if (!TextUtils.isEmpty(this.seekProgram))
      {
        int m = this.thumbRect.right + App.Operation(15.0F);
        Rect localRect2 = new Rect();
        this.seekProgramPaint.getTextBounds(this.seekProgram, 0, this.seekProgram.length(), localRect2);
        if (m + localRect2.width() >= this.bkgWidth)
          break label835;
        this.seekProgramPaint.setTextAlign(Paint.Align.LEFT);
        paramCanvas.drawText(this.seekProgram, m, this.seekTimeBottom, this.seekProgramPaint);
      }
    }
    while (true)
    {
      this.rightSideTime = getRightSideTimeForTimeShift();
      if (this.rightSideTime == null)
        break;
      int k = (int)MplayerTools.getTextDrawingBaseline(this.rightSideTimeTextPaint, localRectF3);
      this.rightSideTimeTextPaint.setTextAlign(Paint.Align.LEFT);
      paramCanvas.drawText(this.rightSideTime, j + App.Operation(28.0F), k, this.rightSideTimeTextPaint);
      return;
      if (this.SeekFlage != 2)
        break label298;
      this.seekRect = new Rect(this.seekEndPos, this.progressTop, this.playProgressRight, this.progressBottom);
      paramCanvas.drawRect(this.seekRect, this.seekPaint);
      break label298;
      label835: this.seekProgramPaint.setTextAlign(Paint.Align.RIGHT);
      paramCanvas.drawText(this.seekProgram, this.thumbRect.left - App.Operation(15.0F), this.seekTimeBottom, this.seekProgramPaint);
    }
  }

  public boolean onInputEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      Logger.i("MplayerSeekBar", "onInputEvent() keyDown code:" + paramKeyEvent.getKeyCode());
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        Logger.i("MplayerSeekBar", "onInputEvent() keyDown 事件未被处理");
        return false;
      case 21:
        display();
        if ((this.uiMode == 3) || (this.curPlayPos > 0))
          break;
      case 22:
        while (true)
        {
          Logger.i("MplayerSeekBar", "onInputEvent() keyDown 事件被处理");
          return true;
          startRewind();
          continue;
          display();
          startFForward();
        }
      case 23:
      case 66:
        this.isKeyDown = true;
        display();
        return true;
      case 85:
      }
      display();
      return true;
    }
    if (paramKeyEvent.getAction() == 1)
    {
      Logger.i("MplayerSeekBar", "onInputEvent() keyUp code:" + paramKeyEvent.getKeyCode());
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        Logger.i("MplayerSeekBar", "onInputEvent() keyUp 事件未被处理");
        return false;
      case 21:
        refreshProgressByOperation(true);
      case 22:
        while (true)
        {
          Logger.i("MplayerSeekBar", "onInputEvent() keyUp 事件被处理");
          return true;
          refreshProgressByOperation(true);
        }
      case 4:
        hide();
        return true;
      case 23:
      case 66:
        if (!this.isKeyDown)
        {
          this.isKeyDown = false;
          return true;
        }
        this.isKeyDown = false;
        this.lsnr.onUserPauseOrStart();
        return true;
      case 85:
      }
      this.lsnr.onUserPauseOrStart();
      return true;
    }
    return false;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    return false;
  }

  public void refreshProgressByPlay(long paramLong)
  {
    if (this.curMovingCmd != 0)
      return;
    refreshProgress(paramLong);
  }

  public void reset()
  {
    Logger.i("MplayerSeekBar", "reset()");
    if (3 == this.uiMode)
    {
      this.playProgressWidth = this.progressBarWidth;
      this.seekTime = "00:00:00";
      this.rightSideTime = "00:00:00";
      this.playingTime = "00:00:00";
      this.seekStopPos = 0;
      this.curMovingDirection = 0;
      this.curMovingCmd = 0;
      this.keyDownTime = 0L;
      this.keyDownTimeLen = 0L;
      this.lastKeyUpTime = 0L;
      this.isKeyDown = false;
      this.isPreMode = false;
      this.prePlayNode = -1.0D;
      if (this.progressBarWidth <= 0)
        break label149;
    }
    label149: for (this.curPlayPos = (10000 * this.playProgressWidth / this.progressBarWidth); ; this.curPlayPos = 0)
    {
      this.seekPos = 0;
      this.playStatusFlag = 1;
      this.lsnr.notifyCurrentState(this.playStatusFlag);
      invalidate();
      return;
      this.playProgressWidth = 0;
      break;
    }
  }

  public int seekTo(int paramInt, boolean paramBoolean)
  {
    if ((this.freePlayUiPos <= paramInt) && (this.isPreMode))
    {
      this.curMovingCmd = 0;
      this.curPlayPos = this.freePlayUiPos;
      this.lsnr.onPlayToPreNode();
      return 0;
    }
    this.seekStopPos = (paramInt * this.progressBarWidth / 10000);
    this.playProgressPos = this.lsnr.uiProgress2PlayProgress(paramInt);
    Logger.i("MplayerSeekBar", "seekTo playProgressWidth = " + this.playProgressWidth + ", uiPos = " + paramInt + ", playProgressPos = " + this.playProgressPos);
    this.seekTime = this.lsnr.getPosDiscribByPlayPos(this.playProgressPos);
    this.seekProgram = this.lsnr.getCurrentProgramName(this.playProgressPos);
    if (paramBoolean)
    {
      this.playStatusFlag = 1;
      this.mHandler.removeCallbacks(this.mRun);
      this.mHandler.postDelayed(this.mRun, 1000L);
    }
    invalidate();
    return 0;
  }

  public void setPrePlayNode(boolean paramBoolean, double paramDouble)
  {
    Logger.d("MplayerSeekBar", "setPrePlayNode isPreMode: " + paramBoolean + ", prePlayNode: " + paramDouble);
    this.isPreMode = paramBoolean;
    this.prePlayNode = paramDouble;
    if (paramBoolean)
      this.freePlayUiPos = ((int)(10000.0D * paramDouble));
  }

  public void setRightSideTime(String paramString)
  {
    this.rightSideTime = paramString;
  }

  public void setTimeNodeData(List<?> paramList1, List<?> paramList2)
  {
    this.timeNodeData = paramList1;
  }

  public void setVideoDiscribForVod(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString2 != null) && (paramString3 == null));
  }

  public void unInit()
  {
    this.threadExitFlag = true;
    this.mHandler.removeCallbacks(this.runnable);
  }

  public void updatePlayStatus(int paramInt)
  {
    this.playStatusFlag = paramInt;
    this.lsnr.notifyCurrentState(this.playStatusFlag);
    invalidate();
  }

  public static abstract interface IMplayerSeekBarListener
  {
    public abstract String getCurrentProgramName(long paramLong);

    public abstract String getPosDiscribByPlayPos(long paramLong);

    public abstract void notifyCurrentState(int paramInt);

    public abstract void onPlayToPreNode();

    public abstract void onUserPauseOrStart();

    public abstract void onUserSeekEnd(long paramLong);

    public abstract void onUserSeekStart();

    public abstract int playProgress2uiProgress(long paramLong);

    public abstract long uiProgress2PlayProgress(int paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerSeekBar
 * JD-Core Version:    0.6.2
 */