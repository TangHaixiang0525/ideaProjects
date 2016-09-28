package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.starcor.core.domain.BarrageMeta;
import com.starcor.core.utils.BarrageTools;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.xul.XulUtils;
import java.util.ArrayList;
import java.util.List;

public class BarrageView extends View
{
  private static final int MSG_UPDATE = 256;
  public static final int SCREEN_HEIGHT = App.Operation(720.0F);
  public static final int SCREEN_WIDTH = App.Operation(1280.0F);
  private static final String TAG = BarrageView.class.getSimpleName();
  private int TIMER_INTERVAL = 16;
  private boolean isDebug = false;
  private boolean isOn = true;
  private boolean isPause = false;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 256:
      }
      BarrageView.this.updateView();
    }
  };
  private boolean mIsStop = false;
  private Thread mUpdateThread = null;
  private int[] mYpos = new int[0];
  private int per_row_count = 10;
  private ArrayList<BarrageRowView> rowViewArrayList = null;

  public BarrageView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(16777215);
    init();
  }

  private void clearAll()
  {
    if (this.rowViewArrayList != null)
    {
      int i = this.rowViewArrayList.size();
      for (int j = 0; j < i; j++)
        ((BarrageRowView)this.rowViewArrayList.get(j)).clear();
    }
  }

  private void drawRowView(Canvas paramCanvas)
  {
    long l = 0L;
    if (this.isDebug)
      l = XulUtils.timestamp();
    int i = this.rowViewArrayList.size();
    for (int j = 0; j < i; j++)
      ((BarrageRowView)this.rowViewArrayList.get(j)).draw(paramCanvas);
    if (this.isDebug)
      Logger.i(TAG, "drawRowView end!" + (XulUtils.timestamp() - l));
  }

  private int getCurrentCount()
  {
    int i = 0;
    int j = 0;
    int k = this.rowViewArrayList.size();
    while (i < k)
    {
      j += ((BarrageRowView)this.rowViewArrayList.get(i)).getSize();
      i++;
    }
    return j;
  }

  private void init()
  {
    this.rowViewArrayList = new ArrayList();
    this.per_row_count = BarrageTools.getInstance().getMaxCount();
    this.mYpos = BarrageTools.getInstance().getYPosList();
    for (int i = 0; i < this.mYpos.length; i++)
      this.rowViewArrayList.add(new BarrageRowView(this.mYpos[i]));
  }

  private void updateView()
  {
    int i = 0;
    synchronized (this.rowViewArrayList)
    {
      int j = this.rowViewArrayList.size();
      while (i < j)
      {
        ((BarrageRowView)this.rowViewArrayList.get(i)).updatePos();
        i++;
      }
      postInvalidate();
      return;
    }
  }

  public void addData(List<BarrageMeta> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      Logger.i(TAG, "addData is empty!");
    do
    {
      return;
      int i = 12 * this.per_row_count - getCurrentCount();
      if (i < 1)
      {
        Logger.i(TAG, "addData count < 1!");
        return;
      }
      if (paramList.size() > i)
        paramList = paramList.subList(0, i - 1);
      int j = 0;
      int k = 0;
      int m = this.rowViewArrayList.size();
      int n = paramList.size();
      Logger.i(TAG, "addData dataSize = " + n);
      while (j < m)
      {
        for (int i1 = this.per_row_count - ((BarrageRowView)this.rowViewArrayList.get(j)).getSize(); i1 > 0; i1--)
          if (k <= n - 1)
          {
            BarrageMetaView localBarrageMetaView = new BarrageMetaView((BarrageMeta)paramList.get(k));
            ((BarrageRowView)this.rowViewArrayList.get(j)).addView(localBarrageMetaView);
            k++;
          }
        j++;
      }
    }
    while (this.mUpdateThread != null);
    this.mIsStop = false;
    this.mUpdateThread = new UpdateThread(null);
    this.mUpdateThread.start();
    Logger.i(TAG, "start!");
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawRowView(paramCanvas);
  }

  public void pause()
  {
    this.isPause = true;
  }

  public void resume()
  {
    this.isPause = false;
  }

  public void setBarrageOff()
  {
    setVisibility(4);
  }

  public void setBarrageOn()
  {
    setVisibility(0);
  }

  public void stop()
  {
    Logger.i(TAG, "stop!");
    try
    {
      if (this.mUpdateThread != null)
      {
        this.mIsStop = true;
        this.mUpdateThread.interrupt();
        this.mUpdateThread = null;
      }
      clearAll();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private class UpdateThread extends Thread
  {
    private UpdateThread()
    {
    }

    public void run()
    {
      try
      {
        while (!BarrageView.this.mIsStop)
        {
          if ((!BarrageView.this.isPause) && (BarrageView.this.isOn))
            BarrageView.this.mHandler.sendEmptyMessage(256);
          Thread.sleep(BarrageView.this.TIMER_INTERVAL);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        BarrageView.access$202(BarrageView.this, true);
        localInterruptedException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.BarrageView
 * JD-Core Version:    0.6.2
 */