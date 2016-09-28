package com.starcor.ui.testspeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;
import com.starcor.hunan.widget.HighLightButton;
import com.starcor.hunan.widget.HighLightTextView;
import java.util.ArrayList;

public class TestSpeedDrawView extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  private static final String TAG = "TestSpeedDrawView";
  private int MaxServerNum = 3;
  private int MaxSpeedNum = 8;
  private Context mConext;
  private View.OnClickListener mOnClickListener;
  private int mServerCount;
  private ArrayList<Integer> mSpeeds;
  private int maxP;
  private int maxY;
  private ArrayList<Integer> mtimeShiftSpeeds;
  private int offsetInternal = 100;
  private int offsetLeft = 60;
  private Paint paint = new Paint();
  private int perSpaceWidth = 6;
  private int perSpeedWidth = 25;
  private int speedsXStart;
  private int speedsXend;
  private int startX = 50;
  private int startY = 100;
  private float stepNumY;
  private int stepY;
  private int stopX = 820;
  private int stopY = 550;
  private HighLightButton tv_retest;

  public TestSpeedDrawView(Context paramContext, ArrayList<Integer> paramArrayList1, ArrayList<Integer> paramArrayList2, int paramInt)
  {
    super(paramContext);
    this.mServerCount = paramInt;
    setBackgroundColor(2434341);
    this.mConext = paramContext;
    this.mSpeeds = paramArrayList1;
    this.mtimeShiftSpeeds = paramArrayList2;
    setOrientation(1);
    initDrawData();
    HighLightTextView localHighLightTextView1 = new HighLightTextView(this.mConext);
    localHighLightTextView1.setText(Html.fromHtml("<font color='#e5e5e5'>本次优化的结果：已为您选择最佳线路    网速 </font><font color='#ffa800'>" + getMax() + "kb/s</font>"));
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, App.Operation(50.0F));
    localLayoutParams1.leftMargin = App.Operation(100.0F);
    addView(localHighLightTextView1, localLayoutParams1);
    LinearLayout localLinearLayout = new LinearLayout(this.mConext);
    localLinearLayout.setOrientation(0);
    addView(localLinearLayout, new LinearLayout.LayoutParams(-1, -1));
    HighLightTextView localHighLightTextView2 = new HighLightTextView(this.mConext);
    String str;
    if (getMax() > 4000)
      str = "<font color='#cdcdcd'>您当前网速适合观看 </font>" + "<font color='#ffa800'>高清影片</font>";
    while (true)
    {
      localHighLightTextView2.setText(Html.fromHtml(str));
      LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-2, App.Operation(50.0F));
      localLayoutParams2.leftMargin = App.Operation(100.0F);
      localLinearLayout.addView(localHighLightTextView2, localLayoutParams2);
      this.tv_retest = new HighLightButton(this.mConext);
      this.tv_retest.setText("再次优化");
      this.tv_retest.setId(2131165205);
      this.tv_retest.setFocusable(true);
      this.tv_retest.setBackgroundResource(2130837507);
      this.tv_retest.setGravity(17);
      this.tv_retest.setTextColor(-7631989);
      this.tv_retest.setNextFocusUpId(this.tv_retest.getId());
      this.tv_retest.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            paramAnonymousView.setBackgroundResource(2130837508);
            TestSpeedDrawView.this.tv_retest.setTextColor(-1);
            return;
          }
          paramAnonymousView.setBackgroundResource(2130837507);
          TestSpeedDrawView.this.tv_retest.setTextColor(-7631989);
        }
      });
      this.tv_retest.requestFocus();
      this.tv_retest.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          TestSpeedDrawView.this.mOnClickListener.onClick(paramAnonymousView);
        }
      });
      LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(App.Operation(150.0F), App.Operation(50.0F));
      localLayoutParams3.leftMargin = App.Operation(5.0F);
      localLinearLayout.addView(this.tv_retest, localLayoutParams3);
      return;
      if ((getMax() < 4000) && (getMax() > 2000))
        str = "<font color='#cdcdcd'>您当前网速适合观看 </font>" + "<font color='#ffa800'>标清影片</font>";
      else
        str = "<font color='#cdcdcd'>网速较慢，建议与当地运营商联系。</font>";
    }
  }

  private void drawBaseLine(Canvas paramCanvas, Paint paramPaint)
  {
    paramPaint.setColor(-8618884);
    paramPaint.setStrokeWidth(App.Operation(4.0F));
    paramCanvas.drawLine(App.OperationFolat(this.startX), App.OperationFolat(this.stopY), App.OperationFolat(this.stopX), App.OperationFolat(this.stopY), paramPaint);
    paramCanvas.drawLine(App.OperationFolat(this.startX), App.OperationFolat(this.startY), App.OperationFolat(this.startX), App.OperationFolat(this.stopY), paramPaint);
    paramPaint.setStrokeWidth(App.Operation(1.0F));
  }

  private void drawBitmap(String paramString, Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Bitmap localBitmap = BitmapService.getInstance(this.mConext).getBitmap(paramString);
    Rect localRect = new Rect();
    localRect.left = 0;
    localRect.right = App.Operation(localBitmap.getWidth());
    localRect.top = 0;
    localRect.bottom = App.Operation(localBitmap.getHeight());
    paramCanvas.drawBitmap(localBitmap, localRect, new Rect(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4), this.paint);
  }

  private void drawLines(Canvas paramCanvas, Paint paramPaint)
  {
    paramPaint.setColor(-9671572);
    paramPaint.setTextSize(App.Operation(30.0F));
    if (getMaxY() < 8)
    {
      paramCanvas.drawLine(App.OperationFolat(this.startX), App.OperationFolat((int)num2position(2.0D)), App.OperationFolat(-100 + this.stopX), App.OperationFolat((int)num2position(2.0D)), paramPaint);
      paramCanvas.drawText("标清", App.OperationFolat(-90 + this.stopX), App.OperationFolat((int)(5.0D + num2position(2.0D))), paramPaint);
      paramCanvas.drawLine(App.OperationFolat(-30 + this.stopX), App.OperationFolat((int)num2position(2.0D)), App.OperationFolat(this.stopX), App.OperationFolat((int)num2position(2.0D)), paramPaint);
      paramCanvas.drawLine(App.Operation(this.startX), App.OperationFolat((int)num2position(4.0D)), App.OperationFolat(-100 + this.stopX), App.OperationFolat((int)num2position(4.0D)), paramPaint);
      paramCanvas.drawText("高清", App.OperationFolat(-90 + this.stopX), App.OperationFolat((int)(5.0D + num2position(4.0D))), paramPaint);
      paramCanvas.drawLine(App.OperationFolat(-30 + this.stopX), App.OperationFolat((int)num2position(4.0D)), App.OperationFolat(this.stopX), App.OperationFolat((int)num2position(4.0D)), paramPaint);
    }
    while ((getMaxY() <= 8) || (getMaxY() >= 20))
      return;
    paramCanvas.drawLine(App.OperationFolat(this.startX), App.OperationFolat((int)num2position(4.0D)), App.OperationFolat(-100 + this.stopX), App.OperationFolat((int)num2position(4.0D)), paramPaint);
    paramCanvas.drawText("高清", App.OperationFolat(-90 + this.stopX), App.OperationFolat((int)(5.0D + num2position(4.0D))), paramPaint);
    paramCanvas.drawLine(App.OperationFolat(-30 + this.stopX), App.OperationFolat((int)num2position(4.0D)), App.OperationFolat(this.stopX), App.OperationFolat((int)num2position(4.0D)), paramPaint);
  }

  private int drawPerSpeed(Canvas paramCanvas, Paint paramPaint, ArrayList<Integer> paramArrayList, int paramInt)
  {
    int i = -1;
    if ((paramArrayList == null) || (paramArrayList.size() == 0))
    {
      Paint localPaint1 = new Paint();
      localPaint1.setStrokeWidth(App.Operation(4.0F));
      localPaint1.setColor(-1703918);
      localPaint1.setAntiAlias(true);
      Logger.i("TestSpeedDrawView", "draw error");
      paramCanvas.drawLine(App.Operation(paramInt + this.perSpeedWidth), App.OperationFolat(-13 + this.stopY), App.Operation(13 + (paramInt + this.perSpeedWidth)), App.OperationFolat(13 + this.stopY), localPaint1);
      paramCanvas.drawLine(App.Operation(paramInt + this.perSpeedWidth), App.OperationFolat(13 + this.stopY), App.Operation(13 + (paramInt + this.perSpeedWidth)), App.OperationFolat(-13 + this.stopY), localPaint1);
      this.speedsXStart = (paramInt + this.perSpeedWidth);
      this.speedsXend = (13 + (paramInt + this.perSpeedWidth));
      return -1;
    }
    int j = 0;
    int k;
    if (j < paramArrayList.size())
    {
      k = ((Integer)paramArrayList.get(j)).intValue();
      Logger.i("TestSpeedDrawView", "!!! num = " + k);
      if (k != 0);
    }
    while (true)
    {
      j++;
      break;
      if (i + 1 > this.MaxSpeedNum)
      {
        if (i != -1)
          break label887;
        Paint localPaint2 = new Paint();
        localPaint2.setStrokeWidth(App.Operation(4.0F));
        localPaint2.setColor(-1703918);
        localPaint2.setAntiAlias(true);
        Logger.i("TestSpeedDrawView", "draw error");
        paramCanvas.drawLine(App.Operation(paramInt + this.perSpeedWidth), App.OperationFolat(-13 + this.stopY), App.Operation(13 + (paramInt + this.perSpeedWidth)), App.OperationFolat(13 + this.stopY), localPaint2);
        paramCanvas.drawLine(App.Operation(paramInt + this.perSpeedWidth), App.OperationFolat(13 + this.stopY), App.Operation(13 + (paramInt + this.perSpeedWidth)), App.OperationFolat(-13 + this.stopY), localPaint2);
        this.speedsXStart = (paramInt + this.perSpeedWidth);
        this.speedsXend = (13 + (paramInt + this.perSpeedWidth));
        return -1;
      }
      i++;
      double d = k / 1000.0F;
      Logger.i("TestSpeedDrawView", " drawNum :" + d + "  height:" + App.Operation((int)num2position(d)));
      Paint localPaint3 = new Paint();
      localPaint3.setAntiAlias(true);
      localPaint3.setColor(-1);
      int m = this.perSpeedWidth / 2;
      int n = -2 + this.perSpeedWidth / 2;
      this.speedsXStart = (paramInt + (this.perSpaceWidth + this.perSpeedWidth) - m);
      int i1 = (int)(num2position(d) + n);
      int i2 = this.stopY - n;
      if (i1 > i2)
        i1 = -2 + (this.stopY - n);
      Rect localRect1 = new Rect(App.Operation(paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1) - m), App.Operation(i1 + 2), App.Operation(m + (paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1))), App.Operation(this.stopY));
      paramCanvas.drawRect(localRect1, localPaint3);
      paramPaint.setColor(-10395295);
      paramCanvas.drawCircle(App.Operation(paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1)), App.Operation(i1), App.Operation(m), localPaint3);
      paramCanvas.drawCircle(App.Operation(paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1)), App.Operation(i1), App.Operation(n), paramPaint);
      Rect localRect2 = new Rect(App.Operation(paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1) - n), App.Operation(i1), App.Operation(n + (paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1))), App.Operation(this.stopY));
      paramCanvas.drawRect(localRect2, paramPaint);
      this.speedsXend = (m + (paramInt + (this.perSpaceWidth + this.perSpeedWidth) * (i + 1)));
    }
    label887: return 0;
  }

  private void drawSpeed(Canvas paramCanvas, Paint paramPaint)
  {
    int i = drawPerSpeed(paramCanvas, paramPaint, this.mSpeeds, this.startX + this.offsetLeft + (this.MaxSpeedNum - getValidNum(this.mSpeeds)) / 2 * this.perSpeedWidth);
    drawXText(paramCanvas, paramPaint, this.speedsXStart + (this.speedsXend - this.speedsXStart) / 2, "点播");
    int j = drawPerSpeed(paramCanvas, paramPaint, this.mtimeShiftSpeeds, this.speedsXend + this.offsetInternal + (this.MaxSpeedNum - getValidNum(this.mtimeShiftSpeeds)) / 2 * this.perSpeedWidth);
    drawXText(paramCanvas, paramPaint, this.speedsXStart + (this.speedsXend - this.speedsXStart) / 2, "回看");
    if ((i == -1) && (j == -1))
      return;
    int k = getMax();
    double d = k / 1000.0F;
    Logger.i("TestSpeedDrawView", "!!! max = " + k);
    if (this.mServerCount <= 1)
    {
      drawBitmap("speed_line.png", paramCanvas, App.Operation(200 + this.startX), App.Operation((int)(num2position(d) - 13.0D)), App.Operation(400.0F), App.Operation(13.0F));
      drawBitmap("speed_left.png", paramCanvas, App.Operation(20 + this.startX), App.Operation((int)(num2position(d) - 45.0D)), App.Operation(200.0F), App.Operation(47.0F));
      Paint localPaint2 = new Paint();
      localPaint2.setTextSize(App.OperationFolat(20.0F));
      localPaint2.setColor(-1);
      localPaint2.setFakeBoldText(true);
      localPaint2.setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
      if (getMax() > 4000)
      {
        paramCanvas.drawText("高清  " + getMax() + "kb/s", App.Operation(40 + this.startX), App.Operation((int)(num2position(d) - 15.0D)), localPaint2);
        return;
      }
      if ((getMax() > 2000) && (getMax() < 4000))
      {
        paramCanvas.drawText("标清  " + getMax() + "kb/s", App.Operation(40 + this.startX), App.Operation((int)(num2position(d) - 15.0D)), localPaint2);
        return;
      }
      paramCanvas.drawText(getMax() + "kb/s", App.Operation(40 + this.startX), App.Operation((int)(num2position(d) - 15.0D)), localPaint2);
      return;
    }
    drawBitmap("speed_line.png", paramCanvas, App.Operation(3 + this.startX), App.Operation((int)(num2position(d) - 13.0D)), App.Operation(-280 + this.stopX - this.startX), App.Operation(13.0F));
    drawBitmap("speed_right.png", paramCanvas, App.Operation(-280 + this.stopX), App.Operation((int)(num2position(d) - 45.0D)), App.Operation(200.0F), App.Operation(47.0F));
    Paint localPaint1 = new Paint();
    localPaint1.setTextSize(App.OperationFolat(20.0F));
    localPaint1.setColor(-1);
    localPaint1.setFakeBoldText(true);
    localPaint1.setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    if (getMax() > 4000)
    {
      paramCanvas.drawText("高清  " + getMax() + "kb/s", App.Operation(-250 + this.stopX), App.Operation((int)(num2position(d) - 15.0D)), localPaint1);
      return;
    }
    if ((getMax() > 2000) && (getMax() < 4000))
    {
      paramCanvas.drawText("标清 " + getMax() + "kb/s", App.Operation(-250 + this.stopX), App.Operation((int)(num2position(d) - 15.0D)), localPaint1);
      return;
    }
    paramCanvas.drawText(getMax() + "kb/s", App.Operation(-250 + this.stopX), App.Operation((int)(num2position(d) - 15.0D)), localPaint1);
  }

  private void drawXText(Canvas paramCanvas, Paint paramPaint, int paramInt, String paramString)
  {
    paramPaint.setColor(-2368288);
    paramPaint.setTextSize(App.Operation(22.0F));
    Rect localRect = new Rect();
    paramPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    paramCanvas.drawText(paramString, App.OperationFolat(paramInt - localRect.width() / 2), App.OperationFolat(40 + this.stopY), paramPaint);
  }

  private void drawYNum(Canvas paramCanvas, Paint paramPaint)
  {
    paramPaint.setColor(-5395027);
    paramPaint.setTextSize(App.Operation(24.0F));
    for (int i = 0; i < 5; i++)
    {
      Logger.i("TestSpeedDrawView", " maxy:" + this.maxY + "  drawYNum " + this.maxY / 4 * (4 - i));
      paramCanvas.drawText(this.maxY / 4 * (4 - i) + "M", App.OperationFolat(-50 + this.startX), App.OperationFolat(7.0F + (this.stepNumY * (i + 1) + this.startY)), paramPaint);
    }
  }

  private int getMax()
  {
    int i = getMaxVod();
    int j = getMaxTimeShift();
    if (i > j)
      return i;
    return j;
  }

  private int getMaxTimeShift()
  {
    int i = 0;
    for (int j = 0; (this.mtimeShiftSpeeds != null) && (j < this.mtimeShiftSpeeds.size()); j++)
      if (i < ((Integer)this.mtimeShiftSpeeds.get(j)).intValue())
      {
        i = ((Integer)this.mtimeShiftSpeeds.get(j)).intValue();
        this.maxP = j;
      }
    return i;
  }

  private int getMaxVod()
  {
    int i = 0;
    for (int j = 0; (this.mSpeeds != null) && (j < this.mSpeeds.size()); j++)
      if (i < ((Integer)this.mSpeeds.get(j)).intValue())
      {
        i = ((Integer)this.mSpeeds.get(j)).intValue();
        this.maxP = j;
      }
    return i;
  }

  private int getMaxY()
  {
    int i = 1 + (int)Math.ceil(getMax()) / 1000;
    if (i % 4 == 1)
      i += 3;
    if (i % 4 == 2)
      i += 2;
    if (i % 4 == 3)
      i++;
    if (i < 4)
      i = 4;
    return i;
  }

  private int getStep()
  {
    int i = this.maxY;
    int j = 0;
    if (i != 0)
      j = (this.stopY - this.startY) / this.maxY;
    return j;
  }

  private int getValidNum(ArrayList<Integer> paramArrayList)
  {
    int i = 0;
    for (int j = 0; (paramArrayList != null) && (j < paramArrayList.size()); j++)
      if (((Integer)paramArrayList.get(j)).intValue() != 0)
        i++;
    return i;
  }

  private int getXstep()
  {
    return (-200 + this.stopX - this.startX) / (1 + this.mServerCount);
  }

  private void initDrawData()
  {
    this.maxY = getMaxY();
    this.stepNumY = ((this.stopY - this.startY) / 5);
    this.stepY = getStep();
    Log.i("TestSpeedDrawView", "initData maxY:" + this.maxY + "  step:" + this.stepY);
  }

  private double num2position(double paramDouble)
  {
    double d = (this.stopY - (this.stepNumY + this.startY)) / this.maxY;
    Log.i("TestSpeedDrawView", "num2position num:" + paramDouble + "  pos:" + paramDouble * d);
    return this.stopY - paramDouble * d;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Paint localPaint = new Paint();
    localPaint.setColor(-7829368);
    localPaint.setAntiAlias(true);
    localPaint.setTextSize(App.OperationFolat(20.0F));
    drawBaseLine(paramCanvas, localPaint);
    drawYNum(paramCanvas, localPaint);
    drawLines(paramCanvas, localPaint);
    drawSpeed(paramCanvas, localPaint);
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
    this.tv_retest.setNextFocusLeftId(paramHighLightButton.getId());
  }

  public void setOnButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mOnClickListener = paramOnClickListener;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.testspeed.TestSpeedDrawView
 * JD-Core Version:    0.6.2
 */