package com.starcor.media.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;

public class MplayerDialogView extends View
{
  private static final float BACKGROUND_RADIUS = 10.0F;
  private static final float BUTTON_RADIUS = 5.0F;
  private static final String TAG = "MplayerDialogView";
  private final int BUTTON_HEIGHT = App.OperationHeight(44);
  private final int PADDING_BOTTOM = App.OperationHeight(30);
  private final int PADDING_LEFT = App.OperationHeight(30);
  private final int PADDING_TOP = App.OperationHeight(50);
  private final int SPACING_10 = App.OperationHeight(10);
  private final int SPACING_15 = App.OperationHeight(15);
  private Paint bgPaint;
  private RectF bgRect = new RectF();
  private String btnTextConfirm = "";
  private String btnTextProblemFeedback = "";
  private Paint buttonBgPaint;
  private Paint buttonProblemFeedbackPaint;
  private Paint buttonTextPaint;
  private Paint buttonUserFeedbackBgPaint;
  private RectF confirmButtonRect = new RectF();
  Context context;
  private int focusPosition = 0;
  private Paint linePaint;
  private IMplayerDialogViewListener lsnr;
  private String opNotice = null;
  private TextPaint opNoticePaint;
  private String quitNotice = null;
  private Paint quitNoticePaint;
  private String title = null;
  private Paint titlePaint;
  private RectF userFeedbackButtonRect = new RectF();

  public MplayerDialogView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initViews();
  }

  public MplayerDialogView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    initViews();
  }

  private void initViews()
  {
    this.titlePaint = new Paint();
    this.titlePaint.setColor(-1);
    this.titlePaint.setAntiAlias(true);
    this.titlePaint.setTextSize(App.OperationHeight(26));
    this.titlePaint.setShadowLayer(0.2F, 0.5F, 0.5F, -16777216);
    this.titlePaint.setFakeBoldText(true);
    this.linePaint = new Paint();
    this.linePaint.setColor(2113929215);
    this.linePaint.setAntiAlias(true);
    this.linePaint.setStrokeWidth(App.OperationHeight(2));
    this.linePaint.setShadowLayer(0.2F, 0.2F, 0.2F, -16777216);
    this.quitNoticePaint = new Paint();
    this.quitNoticePaint.setColor(-1);
    this.quitNoticePaint.setAntiAlias(true);
    this.quitNoticePaint.setTextSize(App.OperationHeight(20));
    this.quitNoticePaint.setShadowLayer(0.2F, 0.3F, 0.3F, -16777216);
    this.opNoticePaint = new TextPaint();
    this.opNoticePaint.setColor(-1);
    this.opNoticePaint.setAntiAlias(true);
    this.opNoticePaint.setTextSize(App.OperationHeight(22));
    this.opNoticePaint.setShadowLayer(0.2F, 0.2F, 0.2F, -16777216);
    this.buttonTextPaint = new Paint();
    this.buttonTextPaint.setColor(-1);
    this.buttonTextPaint.setAntiAlias(true);
    this.buttonTextPaint.setTextSize(App.OperationHeight(22));
    this.buttonTextPaint.setShadowLayer(0.2F, 0.5F, 0.5F, -16777216);
    this.bgPaint = new Paint();
    this.bgPaint.setColor(-667918944);
    this.bgPaint.setAntiAlias(true);
    this.bgPaint.setShadowLayer(0.2F, 0.2F, 0.2F, -16777216);
    this.buttonBgPaint = new Paint();
    this.buttonBgPaint.setColor(-13867794);
    this.buttonBgPaint.setAntiAlias(true);
    this.buttonBgPaint.setShadowLayer(0.2F, 0.2F, 0.2F, -16777216);
    this.buttonProblemFeedbackPaint = new Paint();
    this.buttonProblemFeedbackPaint.setColor(-1);
    this.buttonProblemFeedbackPaint.setAntiAlias(true);
    this.buttonProblemFeedbackPaint.setTextSize(App.OperationHeight(22));
    this.buttonProblemFeedbackPaint.setShadowLayer(0.2F, 0.5F, 0.5F, -16777216);
    this.buttonUserFeedbackBgPaint = new Paint();
    this.buttonUserFeedbackBgPaint.setColor(-11769967);
    this.buttonUserFeedbackBgPaint.setAntiAlias(true);
    this.buttonUserFeedbackBgPaint.setShadowLayer(0.2F, 0.2F, 0.2F, -16777216);
  }

  public void init(IMplayerDialogViewListener paramIMplayerDialogViewListener, String paramString1, String paramString2, String paramString3)
  {
    if ((paramIMplayerDialogViewListener == null) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)) || (TextUtils.isEmpty(paramString3)))
    {
      Logger.i("MplayerDialogView", "initText() lsnr or title or quitNotice or opNotice is null");
      return;
    }
    this.lsnr = paramIMplayerDialogViewListener;
    this.title = paramString1;
    this.quitNotice = paramString2;
    this.opNotice = paramString3;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    this.bgRect.right = getWidth();
    this.bgRect.bottom = getHeight();
    paramCanvas.drawRoundRect(this.bgRect, 10.0F, 10.0F, this.bgPaint);
    if (!TextUtils.isEmpty(this.title))
      paramCanvas.drawText(this.title, this.PADDING_LEFT, this.PADDING_TOP, this.titlePaint);
    if (!TextUtils.isEmpty(this.quitNotice))
    {
      float f11 = this.PADDING_LEFT + this.titlePaint.measureText(this.title) + this.SPACING_10;
      paramCanvas.drawText(this.quitNotice, f11, this.PADDING_TOP, this.quitNoticePaint);
    }
    float f1 = this.PADDING_LEFT - App.OperationHeight(10);
    float f2 = this.PADDING_TOP + this.SPACING_15;
    paramCanvas.drawLine(f1, f2, getWidth() - this.PADDING_LEFT + App.OperationHeight(10), this.PADDING_TOP + this.SPACING_15, this.linePaint);
    float f3 = f2 + this.SPACING_15;
    paramCanvas.save();
    if (!TextUtils.isEmpty(this.opNotice))
    {
      paramCanvas.translate(this.PADDING_LEFT, f3);
      new StaticLayout(this.opNotice, this.opNoticePaint, getWidth() - 2 * this.PADDING_LEFT, Layout.Alignment.ALIGN_NORMAL, 1.2F, 0.0F, true).draw(paramCanvas);
    }
    paramCanvas.restore();
    float f4 = this.buttonTextPaint.measureText(this.btnTextConfirm);
    float f5 = App.Operation(110.0F);
    this.confirmButtonRect.left = ((getWidth() - 2.0F * f5 - App.Operation(35.0F)) / 2.0F);
    this.confirmButtonRect.right = (f5 + this.confirmButtonRect.left);
    this.confirmButtonRect.top = (getHeight() - this.PADDING_BOTTOM - this.BUTTON_HEIGHT);
    this.confirmButtonRect.bottom = (this.confirmButtonRect.top + this.BUTTON_HEIGHT);
    Logger.e("MplayerDialogView", this.confirmButtonRect.toString());
    paramCanvas.drawRoundRect(this.confirmButtonRect, 5.0F, 5.0F, this.buttonBgPaint);
    float f6 = (f5 - f4) / 2.0F + this.confirmButtonRect.left;
    float f7 = (this.confirmButtonRect.height() - this.buttonTextPaint.getTextSize()) / 2.0F + this.confirmButtonRect.top + this.buttonTextPaint.getTextSize() - App.OperationHeight(2);
    paramCanvas.drawText(this.btnTextConfirm, f6, f7, this.buttonTextPaint);
    float f8 = this.buttonProblemFeedbackPaint.measureText(this.btnTextProblemFeedback);
    this.userFeedbackButtonRect.left = (this.confirmButtonRect.right + App.Operation(35.0F));
    this.userFeedbackButtonRect.right = (f5 + this.userFeedbackButtonRect.left);
    this.userFeedbackButtonRect.top = (getHeight() - this.PADDING_BOTTOM - this.BUTTON_HEIGHT);
    this.userFeedbackButtonRect.bottom = (this.userFeedbackButtonRect.top + this.BUTTON_HEIGHT);
    Logger.e("MplayerDialogView", this.userFeedbackButtonRect.toString());
    paramCanvas.drawRoundRect(this.userFeedbackButtonRect, 5.0F, 5.0F, this.buttonUserFeedbackBgPaint);
    float f9 = (f5 - f8) / 2.0F + this.userFeedbackButtonRect.left;
    float f10 = (this.userFeedbackButtonRect.height() - this.buttonProblemFeedbackPaint.getTextSize()) / 2.0F + this.userFeedbackButtonRect.top + this.buttonProblemFeedbackPaint.getTextSize() - App.OperationHeight(2);
    paramCanvas.drawText(this.btnTextProblemFeedback, f9, f10, this.buttonProblemFeedbackPaint);
  }

  public boolean onInputEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      Logger.i("MplayerDialogView", "onInputEvent() keyDown code:" + paramKeyEvent.getKeyCode());
      return true;
    }
    if (paramKeyEvent.getAction() == 1)
    {
      Logger.i("MplayerDialogView", "onInputEvent() keyUp code:" + paramKeyEvent.getKeyCode());
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        return false;
      case 4:
        this.lsnr.onCancel();
        return true;
      case 23:
      case 66:
        Logger.i("MplayerDialogView", "onInputEvent() keyUp code focusPosition=:" + this.focusPosition);
        this.lsnr.onConfirm(this.focusPosition);
        return true;
      case 22:
        this.buttonBgPaint.setColor(-11769967);
        this.buttonUserFeedbackBgPaint.setColor(-13867794);
        this.focusPosition = 1;
        invalidate();
        return true;
      case 21:
      }
      this.buttonUserFeedbackBgPaint.setColor(-11769967);
      this.buttonBgPaint.setColor(-13867794);
      this.focusPosition = 0;
      invalidate();
      return true;
    }
    return false;
  }

  public void setConfirmText(String paramString)
  {
    if (paramString != null)
      this.btnTextConfirm = paramString;
  }

  public void setProblemFeedbackText(String paramString)
  {
    if (paramString != null)
      this.btnTextProblemFeedback = paramString;
  }

  public static abstract interface IMplayerDialogViewListener
  {
    public abstract void onCancel();

    public abstract void onConfirm(int paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerDialogView
 * JD-Core Version:    0.6.2
 */