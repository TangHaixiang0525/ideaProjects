package com.starcor.media.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.widget.FilmListView;
import java.util.ArrayList;

public class MplayerRecommendView extends FilmListView
{
  private static final String TAG = "MplayerRecommendView";
  private static int TITLE_TEXT_SIZE = App.Operation(26.0F);
  Context context;
  ArrayList<FilmListItem> items;
  String quitNotice;
  private Paint quitNoticePaint;
  String title;
  private Paint titlePaint;

  public MplayerRecommendView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initViews();
  }

  public MplayerRecommendView(Context paramContext, AttributeSet paramAttributeSet)
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
    this.titlePaint.setTextSize(TITLE_TEXT_SIZE);
    this.quitNoticePaint = new Paint();
    this.quitNoticePaint.setColor(-1);
    this.quitNoticePaint.setAntiAlias(true);
    this.quitNoticePaint.setTextSize(App.Operation(20.0F));
    this.title = "推荐影片";
    this.quitNotice = "按\"返回\"键退出";
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = App.Operation(10.0F);
    int j = App.Operation(25.0F);
    int k = i + (int)this.titlePaint.measureText(this.title) + TITLE_TEXT_SIZE;
    Logger.i("MplayerRecommendView", "onDraw() titleY:" + j + ", quitNoticeX:" + k);
    if (this.title != null)
      paramCanvas.drawText(this.title, i, j, this.titlePaint);
    if (this.quitNotice != null)
      paramCanvas.drawText(this.quitNotice, k, j, this.quitNoticePaint);
    super.onDraw(paramCanvas);
  }

  public void setTextSize(int paramInt1, int paramInt2)
  {
    TITLE_TEXT_SIZE = paramInt1;
    this.titlePaint.setTextSize(TITLE_TEXT_SIZE);
    postInvalidate();
  }

  public void setTitle(String paramString1, String paramString2)
  {
    this.title = paramString1;
    this.quitNotice = paramString2;
    postInvalidate();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerRecommendView
 * JD-Core Version:    0.6.2
 */