package com.starcor.hunan.widget;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.starcor.core.utils.BarrageTools;
import com.starcor.hunan.App;
import java.util.ArrayList;

public class BarrageRowView
{
  private static final String TAG = BarrageRowView.class.getSimpleName();
  private BarrageMetaView.DrawStateCallback mCallBack = new BarrageMetaView.DrawStateCallback()
  {
    public void finish(BarrageMetaView paramAnonymousBarrageMetaView, RectF paramAnonymousRectF)
    {
      BarrageRowView.this.getIndex(paramAnonymousBarrageMetaView);
    }
  };
  private int pos = 0;
  private ArrayList<BarrageMetaView> viewArrayList = new ArrayList();

  public BarrageRowView(int paramInt)
  {
    this.pos = paramInt;
  }

  private int getIndex(BarrageMetaView paramBarrageMetaView)
  {
    int i = this.viewArrayList.size();
    for (int j = 0; j < i; j++)
      if (paramBarrageMetaView.isEqual((BarrageMetaView)this.viewArrayList.get(j)))
        return j;
    return -1;
  }

  public void addView(BarrageMetaView paramBarrageMetaView)
  {
    paramBarrageMetaView.setTop(this.pos);
    paramBarrageMetaView.setDrawCallback(this.mCallBack);
    this.viewArrayList.add(paramBarrageMetaView);
  }

  public void clear()
  {
    if (this.viewArrayList != null)
      this.viewArrayList.clear();
  }

  public void draw(Canvas paramCanvas)
  {
    int i = 0;
    int j = 0;
    int k = BarrageTools.SCREEN_X;
    float f1 = 0.0F;
    int m = this.viewArrayList.size();
    while (i < m)
    {
      BarrageMetaView localBarrageMetaView = (BarrageMetaView)this.viewArrayList.get(i);
      if (localBarrageMetaView == null)
      {
        i++;
      }
      else
      {
        if (i == 0)
          localBarrageMetaView.draw(paramCanvas);
        while (true)
        {
          j = localBarrageMetaView.getRight();
          f1 = localBarrageMetaView.getStep();
          k = localBarrageMetaView.getWidth();
          break;
          if (localBarrageMetaView.getLeft() == BarrageTools.SCREEN_X)
          {
            if (j >= BarrageTools.SCREEN_X - BarrageTools.ROW_X_SPACE)
              break label183;
            if (localBarrageMetaView.getWidth() <= k)
            {
              localBarrageMetaView.draw(paramCanvas);
            }
            else
            {
              float f2 = localBarrageMetaView.getStep() - f1;
              int i1 = App.Operation(1280.0F) - j;
              if ((f2 < 0.0F) || ((int)(12.0F * (50.0F * f2)) >= i1))
                break label183;
              localBarrageMetaView.draw(paramCanvas);
            }
          }
          else
          {
            localBarrageMetaView.draw(paramCanvas);
          }
        }
      }
    }
    label183: for (int n = -1 + this.viewArrayList.size(); n >= 0; n--)
      if (((BarrageMetaView)this.viewArrayList.get(n)).getRight() < 0)
        this.viewArrayList.remove(n);
  }

  public BarrageMetaView getLastView()
  {
    int i = -1 + this.viewArrayList.size();
    if (i > -1)
      return (BarrageMetaView)this.viewArrayList.get(i);
    return null;
  }

  public int getSize()
  {
    return this.viewArrayList.size();
  }

  public void updatePos()
  {
    int i = this.viewArrayList.size();
    for (int j = 0; j < i; j++)
      if (this.viewArrayList.get(j) != null)
        ((BarrageMetaView)this.viewArrayList.get(j)).updatePos();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.BarrageRowView
 * JD-Core Version:    0.6.2
 */