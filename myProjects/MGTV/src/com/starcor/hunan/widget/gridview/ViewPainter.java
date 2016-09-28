package com.starcor.hunan.widget.gridview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.view.View;
import com.starcor.hunan.App;
import com.starcor.hunan.widget.gridview.drawers.ItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.ScrollItemDrawer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPainter
{
  private static int INDICATOR_HEIGHT = 0;
  private static int INDICATOR_WIDTH = 0;
  private static final long SCROLL_BAR_DURATION = 3000L;
  Paint backgroundPaint;
  int contentLeftOffset;
  private ContentProperties contentProperties;
  Rect contentRect;
  int contentTopOffset;
  Rect focusedRect;
  boolean hideScrollBar = false;
  DataAdapter mDataAdapter;
  View mView;
  private MarqueeTextOffsetListener marqueeTextOffsetListener;
  private NewCustomGridView.OnMovingToEdgeListener onMovingToEdgeListener;
  int pagerLeftOffset;
  private PagerProperties pagerProperties;
  int pagerTopOffset;
  Rect pagersRect;
  Timer scrollBarHideTimer;

  public ViewPainter(View paramView, DataAdapter paramDataAdapter)
  {
    this.mView = paramView;
    this.mDataAdapter = paramDataAdapter;
    this.backgroundPaint = new Paint();
    this.marqueeTextOffsetListener = new MarqueeTextOffsetListener();
  }

  private void drawEmptyContent(Canvas paramCanvas, Rect paramRect, ContentProperties paramContentProperties)
  {
    Bitmap localBitmap = paramContentProperties.getEmptyImage();
    if (localBitmap != null)
    {
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      paramCanvas.drawBitmap(localBitmap, paramRect.centerX() - localBitmap.getWidth() / 2, paramRect.centerY() - localBitmap.getHeight() / 2, this.backgroundPaint);
      paramCanvas.restore();
    }
  }

  private void drawIndicator(Canvas paramCanvas, Rect paramRect)
  {
    paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    if (this.contentProperties.isHorizontalScrollIndicatorEnabled())
    {
      Bitmap localBitmap3 = this.contentProperties.getIndicatorLeft();
      Bitmap localBitmap4 = this.contentProperties.getIndicatorRight();
      if ((localBitmap3 == null) || (localBitmap4 == null))
        return;
      if ((this.contentLeftOffset > 0) || (this.contentProperties.isLoopFocusEnabled()))
        paramCanvas.drawBitmap(localBitmap3, paramRect.left + App.Operation(5.0F), paramRect.centerY() - localBitmap3.getHeight() / 2, this.backgroundPaint);
      int i = this.contentLeftOffset + paramRect.width();
      if ((this.pagerProperties != null) && (this.pagerProperties.getPagerType() == PagerProperties.PagerType.LEFT))
        i += this.pagerProperties.getItemWidth();
      if ((this.contentProperties.isLoopFocusEnabled()) || (i < this.contentProperties.getItemRectByPositionNoMargin(paramRect, -1 + this.mDataAdapter.getSelectedPageItemCount()).right))
        paramCanvas.drawBitmap(localBitmap4, paramRect.right - INDICATOR_WIDTH - App.Operation(5.0F), paramRect.centerY() - localBitmap4.getHeight() / 2, this.backgroundPaint);
    }
    while (true)
    {
      paramCanvas.restore();
      return;
      Bitmap localBitmap1 = this.contentProperties.getIndicatorTop();
      Bitmap localBitmap2 = this.contentProperties.getIndicatorBottom();
      if ((localBitmap1 == null) || (localBitmap2 == null))
        break;
      if ((this.contentProperties.isLoopFocusEnabled()) || (this.contentTopOffset > 0))
        paramCanvas.drawBitmap(localBitmap1, paramRect.centerX() - localBitmap1.getWidth() / 2, paramRect.top + App.Operation(5.0F), this.backgroundPaint);
      if ((this.contentProperties.isLoopFocusEnabled()) || (this.contentTopOffset + paramRect.height() < this.contentProperties.getItemRectByPositionNoMargin(paramRect, -1 + this.mDataAdapter.getSelectedPageItemCount()).bottom))
        paramCanvas.drawBitmap(localBitmap2, paramRect.centerX() - localBitmap2.getWidth() / 2, paramRect.bottom - INDICATOR_HEIGHT + App.Operation(5.0F), this.backgroundPaint);
    }
  }

  private void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Properties paramProperties)
  {
    ItemDrawer localItemDrawer = ItemDrawerFactory.createItemDrawer(paramProperties.getItemType());
    Bitmap localBitmap1;
    int i;
    int j;
    Typeface localTypeface1;
    int k;
    int m;
    Bitmap localBitmap2;
    Typeface localTypeface2;
    if (paramItemData.isFocused())
    {
      localBitmap1 = paramProperties.getItemFocusedBackground();
      i = paramProperties.getItemFocusedTextSize();
      j = paramProperties.getItemFocusedTextColor();
      localTypeface1 = Typeface.create(Typeface.DEFAULT, paramProperties.getItemFocusedTypeFaceStyle());
      k = paramProperties.getDetailFocusedTextSize();
      m = paramProperties.getDetailFocusedTextColor();
      localBitmap2 = paramProperties.getDetailFocusedBackground();
      localTypeface2 = Typeface.create(Typeface.DEFAULT, paramProperties.getDetailFocusedTypeFaceStyle());
    }
    while (true)
    {
      Bitmap localBitmap3 = getHorizontalDivider(paramRect, paramProperties);
      Bitmap localBitmap4 = getVerticalDivider(paramRect, paramProperties);
      localItemDrawer.drawItem(paramCanvas, paramItemData, paramRect, paramProperties.getTextAlign(), i, j, localTypeface1, k, m, localTypeface2, localBitmap2, localBitmap1, paramProperties.getItemDrawingListener(), this.marqueeTextOffsetListener, localBitmap3, localBitmap4);
      return;
      if (paramItemData.isSelected())
      {
        localBitmap1 = paramProperties.getItemSelectedBackground();
        i = paramProperties.getItemSelectedTextSize();
        j = paramProperties.getItemSelectedTextColor();
        localTypeface1 = Typeface.create(Typeface.DEFAULT, paramProperties.getItemSelectedTypeFaceStyle());
        k = paramProperties.getDetailSelectedTextSize();
        m = paramProperties.getDetailSelectedTextColor();
        localBitmap2 = paramProperties.getDetailSelectedBackground();
        localTypeface2 = Typeface.create(Typeface.DEFAULT, paramProperties.getDetailSelectedTypeFaceStyle());
      }
      else
      {
        localBitmap1 = paramProperties.getItemNormalBackground();
        i = paramProperties.getItemNormalTextSize();
        j = paramProperties.getItemNormalTextColor();
        localTypeface1 = Typeface.create(Typeface.DEFAULT, paramProperties.getItemNormalTypeFaceStyle());
        k = paramProperties.getDetailNormalTextSize();
        m = paramProperties.getDetailNormalTextColor();
        localBitmap2 = paramProperties.getDetailNormalBackground();
        localTypeface2 = Typeface.create(Typeface.DEFAULT, paramProperties.getDetailNormalTypeFaceStyle());
      }
    }
  }

  private void drawScrollBar(Canvas paramCanvas, Rect paramRect)
  {
    if (this.hideScrollBar)
      this.hideScrollBar = false;
    NinePatchDrawable localNinePatchDrawable;
    do
    {
      return;
      if (this.scrollBarHideTimer != null)
      {
        this.scrollBarHideTimer.cancel();
        this.scrollBarHideTimer.purge();
        this.scrollBarHideTimer = null;
      }
      this.scrollBarHideTimer = new Timer();
      this.scrollBarHideTimer.schedule(new HideScrollBarTask(), 3000L);
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      if (!this.contentProperties.isVerticalScrollBarEnabled())
        break;
      localNinePatchDrawable = this.contentProperties.getVerticalScrollBarImage();
    }
    while (localNinePatchDrawable == null);
    float f1 = this.contentProperties.getItemRectByPositionNoMargin(paramRect, -1 + this.mDataAdapter.getSelectedPageItemCount()).bottom - paramRect.top;
    float f2 = paramRect.height();
    if (f1 > f2)
    {
      int i = (int)Math.ceil(f2 * f2 / f1);
      if (i < App.Operation(18.0F))
        i = App.Operation(18.0F);
      int j = paramRect.top + (int)Math.ceil(f2 * this.contentTopOffset / f1) + App.Operation(8.0F);
      int k = localNinePatchDrawable.getIntrinsicWidth();
      int m = paramRect.right - k - App.Operation(8.0F);
      Rect localRect = new Rect(m, j, m + k, j + i);
      if (localRect.bottom > paramRect.bottom - App.Operation(8.0F))
        localRect.offset(0, paramRect.bottom - localRect.bottom - App.Operation(8.0F));
      if (isItemVisible(this.contentProperties.getItemRectByPositionNoMargin(paramRect, -1 + this.mDataAdapter.getSelectedPageItemCount())))
        localRect.offsetTo(localRect.left, paramRect.bottom - i - App.Operation(8.0F));
      localNinePatchDrawable.setBounds(localRect);
      localNinePatchDrawable.draw(paramCanvas);
    }
    paramCanvas.restore();
  }

  private void drawScrollItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Properties paramProperties)
  {
    ((ScrollItemDrawer)ItemDrawerFactory.createItemDrawer(paramProperties.getItemType())).setArrows(paramProperties.getIndicatorLeft(), paramProperties.getIndicatorRight());
    drawItem(paramCanvas, paramItemData, paramRect, paramProperties);
  }

  private void getDrawingRect(Rect paramRect, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramRect.left = (paramInt1 + paramRect.left);
    paramRect.right -= paramInt2;
    paramRect.top = (paramInt3 + paramRect.top);
    paramRect.bottom -= paramInt4;
  }

  private Bitmap getHorizontalDivider(Rect paramRect, Properties paramProperties)
  {
    if (paramProperties.getHorizontalDivider() == null);
    while (true)
    {
      return null;
      Rect localRect;
      if ((paramProperties instanceof ContentProperties))
      {
        localRect = new Rect(this.contentRect);
        localRect.offset(this.contentLeftOffset, this.contentTopOffset);
      }
      while (paramRect.bottom < -10 + localRect.bottom)
      {
        return paramProperties.getHorizontalDivider();
        localRect = new Rect(this.pagersRect);
        localRect.offset(this.pagerLeftOffset, this.pagerTopOffset);
      }
    }
  }

  private int getOffsetX(boolean paramBoolean)
  {
    if (paramBoolean)
      return this.contentProperties.getItemWidth();
    return this.pagerProperties.getItemWidth();
  }

  private int getOffsetY(boolean paramBoolean)
  {
    if (paramBoolean)
      return this.contentProperties.getItemHeight();
    return this.pagerProperties.getItemHeight();
  }

  private void getPagerRect(Rect paramRect1, Rect paramRect2, Rect paramRect3, PagerProperties paramPagerProperties)
  {
    if (paramPagerProperties.getPagerType() == PagerProperties.PagerType.LEFT)
    {
      paramRect2.set(paramRect1.left, paramRect1.top, paramRect1.left + paramPagerProperties.getItemWidth(), paramRect1.bottom);
      paramRect3.set(paramRect2.right, paramRect1.top, paramRect1.right, paramRect1.bottom);
      return;
    }
    if (paramPagerProperties.getPagerType() == PagerProperties.PagerType.TOP)
    {
      paramRect2.set(paramRect1.left, paramRect1.top, paramRect1.right, paramRect1.top + paramPagerProperties.getItemHeight());
      paramRect3.set(paramRect1.left, paramRect2.bottom, paramRect1.right, paramRect1.bottom);
      return;
    }
    paramRect2.set(paramRect1.left, paramRect1.top, paramRect1.left + paramPagerProperties.getItemWidth(), paramRect1.top + paramPagerProperties.getItemHeight());
    paramRect3.set(paramRect1.left, paramRect2.bottom, paramRect1.right, paramRect1.bottom);
  }

  private Bitmap getVerticalDivider(Rect paramRect, Properties paramProperties)
  {
    if (paramProperties.getVerticalDivider() == null);
    while (true)
    {
      return null;
      Rect localRect;
      if ((paramProperties instanceof ContentProperties))
      {
        localRect = new Rect(this.contentRect);
        localRect.offset(this.contentLeftOffset, this.contentTopOffset);
      }
      while (paramRect.right < -10 + localRect.right)
      {
        return paramProperties.getVerticalDivider();
        localRect = new Rect(this.pagersRect);
        localRect.offset(this.pagerLeftOffset, this.pagerTopOffset);
      }
    }
  }

  private boolean isItemVisible(Rect paramRect)
  {
    Rect localRect = new Rect(this.contentRect);
    localRect.offset(this.contentLeftOffset, this.contentTopOffset);
    return localRect.contains(paramRect);
  }

  private boolean isMovingFocusFromContent2Pager(int paramInt)
  {
    PagerProperties localPagerProperties = this.pagerProperties;
    boolean bool1 = false;
    if (localPagerProperties != null)
    {
      boolean bool2 = this.mDataAdapter.focusOnPager();
      bool1 = false;
      if (!bool2)
      {
        int i = this.mDataAdapter.getFocusedItem().getPageIndex();
        int j = this.contentProperties.getItemRowPosition(i);
        int k = this.contentProperties.getItemColumnPosition(i);
        if ((this.pagerProperties.getPagerType() != PagerProperties.PagerType.LEFT) || (paramInt != 17) || (k != 0))
        {
          PagerProperties.PagerType localPagerType1 = this.pagerProperties.getPagerType();
          PagerProperties.PagerType localPagerType2 = PagerProperties.PagerType.TOP;
          bool1 = false;
          if (localPagerType1 == localPagerType2)
          {
            bool1 = false;
            if (paramInt == 33)
            {
              bool1 = false;
              if (j != 0);
            }
          }
        }
        else
        {
          bool1 = true;
        }
      }
    }
    return bool1;
  }

  private boolean isMovingFocusFromPager2Content(int paramInt)
  {
    return (this.mDataAdapter.focusOnPager()) && (((this.pagerProperties.getPagerType() == PagerProperties.PagerType.LEFT) && (paramInt == 66)) || ((this.pagerProperties.getPagerType() == PagerProperties.PagerType.TOP) && (paramInt == 130)));
  }

  private boolean isPageVisible(Rect paramRect)
  {
    Rect localRect = new Rect(this.pagersRect);
    localRect.offset(this.pagerLeftOffset, this.pagerTopOffset);
    return localRect.contains(paramRect);
  }

  private void tryDrawBackground(Canvas paramCanvas, Rect paramRect, Bitmap paramBitmap)
  {
    paramCanvas.save();
    if (paramBitmap == null)
      return;
    paramCanvas.drawBitmap(paramBitmap, null, paramRect, this.backgroundPaint);
    paramCanvas.restore();
  }

  public void calculateOffset(boolean paramBoolean)
  {
    int k;
    int m;
    if (this.mDataAdapter.focusOnPager())
    {
      k = this.pagerLeftOffset;
      m = this.pagerTopOffset;
      if (this.focusedRect.right > this.pagersRect.right + this.pagerLeftOffset)
      {
        k += getPagerScrollOffsetX();
        if (this.focusedRect.bottom <= this.pagersRect.height() + this.pagerTopOffset)
          break label206;
        m += getPagerScrollOffsetY();
        label84: if (k != this.pagerLeftOffset)
        {
          if (!paramBoolean)
            break label240;
          this.pagerLeftOffset = (this.focusedRect.left - this.pagersRect.left);
        }
        label116: if (m != this.pagerTopOffset)
        {
          if (!paramBoolean)
            break label249;
          this.pagerTopOffset = (this.focusedRect.top - this.pagersRect.top);
        }
        label148: if (!isPageVisible(this.focusedRect))
          calculateOffset(true);
      }
    }
    label305: label566: label572: 
    while (true)
    {
      this.mView.invalidate();
      return;
      if (this.focusedRect.left >= this.pagerLeftOffset + this.pagersRect.left)
        break;
      k -= getPagerScrollOffsetX();
      break;
      label206: if (this.focusedRect.top >= this.pagerTopOffset + this.pagersRect.top)
        break label84;
      m -= getPagerScrollOffsetY();
      break label84;
      label240: this.pagerLeftOffset = k;
      break label116;
      label249: this.pagerTopOffset = m;
      break label148;
      int i = this.contentLeftOffset;
      int j = this.contentTopOffset;
      if (this.focusedRect.right - this.contentRect.left > this.contentRect.width() + this.contentLeftOffset)
      {
        i += getItemScrollOffsetX();
        if (this.focusedRect.bottom - this.contentRect.top <= this.contentRect.height() + this.contentTopOffset)
          break label504;
        j += getItemScrollOffsetY();
        label342: if (i != this.contentLeftOffset)
        {
          if (!paramBoolean)
            break label536;
          this.contentLeftOffset = (this.focusedRect.left - this.contentRect.left);
        }
        label373: if (j != this.contentTopOffset)
        {
          if (!paramBoolean)
            break label566;
          if (this.mDataAdapter.getFocusedItem().getPageIndex() <= this.mDataAdapter.getSelectedPageItemCount() - getVisibleItemRowCount())
            break label544;
          this.contentTopOffset = (this.contentProperties.getItemRectByPositionNoMargin(this.contentRect, 1 + (this.mDataAdapter.getFocusedItem().getPageIndex() - getVisibleItemRowCount())).top - this.contentRect.top);
        }
      }
      while (true)
      {
        if (isItemVisible(this.focusedRect))
          break label572;
        calculateOffset(true);
        break;
        if (this.focusedRect.left >= this.contentLeftOffset + this.contentRect.left)
          break label305;
        i -= getItemScrollOffsetX();
        break label305;
        label504: if (this.focusedRect.top >= this.contentTopOffset + this.contentRect.top)
          break label342;
        j -= getItemScrollOffsetY();
        break label342;
        this.contentLeftOffset = i;
        break label373;
        this.contentTopOffset = (this.focusedRect.top - this.contentRect.top);
        continue;
        this.contentTopOffset = j;
      }
    }
  }

  public void clearContentOffset()
  {
    this.contentLeftOffset = 0;
    this.contentTopOffset = 0;
  }

  public void clearFocus()
  {
    this.mDataAdapter.clearFocus();
    this.focusedRect = null;
    if (this.marqueeTextOffsetListener != null)
      this.marqueeTextOffsetListener.stopMarquee();
  }

  public void destroy()
  {
    if (this.scrollBarHideTimer != null)
    {
      this.scrollBarHideTimer.cancel();
      this.scrollBarHideTimer = null;
    }
    if (this.marqueeTextOffsetListener != null)
    {
      this.marqueeTextOffsetListener.stopMarquee();
      this.marqueeTextOffsetListener = null;
    }
  }

  public void drawContent(Canvas paramCanvas, Rect paramRect, ContentProperties paramContentProperties)
  {
    this.contentProperties = paramContentProperties;
    Rect localRect1 = new Rect(paramRect);
    getDrawingRect(localRect1, paramContentProperties.marginLeft, paramContentProperties.marginRight, paramContentProperties.marginTop, paramContentProperties.marginBottom);
    if ((paramContentProperties.isHorizontalScrollIndicatorEnabled()) || (paramContentProperties.isVerticalScrollIndicatorEnabled()))
      drawIndicator(paramCanvas, localRect1);
    if (paramContentProperties.isHorizontalScrollIndicatorEnabled())
    {
      INDICATOR_WIDTH = paramContentProperties.getIndicatorLeft().getWidth();
      localRect1.left += INDICATOR_WIDTH + App.Operation(5.0F);
      localRect1.right -= INDICATOR_WIDTH + App.Operation(5.0F);
    }
    if (paramContentProperties.isVerticalScrollIndicatorEnabled())
    {
      INDICATOR_HEIGHT = paramContentProperties.getIndicatorTop().getHeight();
      localRect1.top += INDICATOR_HEIGHT + App.Operation(5.0F);
      localRect1.bottom -= INDICATOR_HEIGHT + App.Operation(5.0F);
    }
    this.contentRect = localRect1;
    int j;
    if ((this.mView.hasFocus()) && (this.focusedRect == null))
    {
      if (this.mDataAdapter.getFocusedItem() == null)
        break label337;
      j = this.mDataAdapter.getFocusedItem().getPageIndex();
      if (!this.mDataAdapter.focusOnPager())
        break label365;
      setFocusPageAtPosition(j);
    }
    while (true)
    {
      paramCanvas.save();
      paramCanvas.clipRect(localRect1);
      tryDrawBackground(paramCanvas, localRect1, paramContentProperties.getBackground());
      paramCanvas.translate(-this.contentLeftOffset, -this.contentTopOffset);
      for (int i = 0; i < this.mDataAdapter.getSelectedPageItemCount(); i++)
      {
        Rect localRect2 = paramContentProperties.getItemRectByPosition(localRect1, i);
        if (isItemVisible(localRect2))
          drawItem(paramCanvas, this.mDataAdapter.getItemAt(this.mDataAdapter.getSelectedPageIndex(), i), localRect2, paramContentProperties);
      }
      label337: if (this.mDataAdapter.getSelectedItem() != null)
      {
        j = this.mDataAdapter.getSelectedItemIndex();
        break;
      }
      j = 0;
      break;
      label365: setFocusItemAtPosition(j);
    }
    paramCanvas.restore();
    if ((paramContentProperties.isHorizontalScrollBarEnabled()) || (paramContentProperties.isVerticalScrollBarEnabled()))
      drawScrollBar(paramCanvas, localRect1);
  }

  public void drawPagers(Canvas paramCanvas, Rect paramRect, PagerProperties paramPagerProperties)
  {
    this.pagerProperties = paramPagerProperties;
    Rect localRect1 = new Rect(paramRect);
    tryDrawBackground(paramCanvas, localRect1, paramPagerProperties.getBackground());
    getDrawingRect(localRect1, paramPagerProperties.marginLeft, paramPagerProperties.marginRight, paramPagerProperties.marginTop, paramPagerProperties.marginBottom);
    this.pagersRect = localRect1;
    paramCanvas.save();
    paramCanvas.translate(-this.pagerLeftOffset, -this.pagerTopOffset);
    if (paramPagerProperties.getPagerType() == PagerProperties.PagerType.SCROLL)
    {
      drawScrollItem(paramCanvas, this.mDataAdapter.getPageAt(this.mDataAdapter.getSelectedPageIndex()), localRect1, paramPagerProperties);
      return;
    }
    for (int i = 0; i < this.mDataAdapter.getPageCount(); i++)
    {
      Rect localRect2 = paramPagerProperties.getItemRectByPosition(localRect1, i);
      if (isPageVisible(localRect2))
        drawItem(paramCanvas, this.mDataAdapter.getPageAt(i), localRect2, paramPagerProperties);
    }
    paramCanvas.restore();
  }

  public void drawView(Canvas paramCanvas, Rect paramRect, PagerProperties paramPagerProperties, ContentProperties paramContentProperties)
  {
    Rect localRect1 = new Rect();
    Rect localRect2 = new Rect(paramRect);
    if ((paramPagerProperties != null) && (this.mDataAdapter.getPageCount() != 0))
    {
      getPagerRect(paramRect, localRect1, localRect2, paramPagerProperties);
      drawPagers(paramCanvas, localRect1, paramPagerProperties);
    }
    if (this.mDataAdapter.getSelectedPageItemCount() != 0)
    {
      drawContent(paramCanvas, localRect2, paramContentProperties);
      return;
    }
    drawEmptyContent(paramCanvas, localRect2, paramContentProperties);
  }

  public int findItemPositionBy(int paramInt1, int paramInt2)
  {
    if (this.contentRect != null)
    {
      int i = paramInt1;
      int j = paramInt2;
      if (this.mDataAdapter.focusOnPager())
      {
        i = paramInt1 - this.pagerLeftOffset + this.contentLeftOffset;
        j = paramInt2 - this.pagerTopOffset + this.contentTopOffset;
      }
      for (int k = 0; k < this.mDataAdapter.getSelectedPageItemCount(); k++)
        if (this.contentProperties.getItemRectByPositionNoMargin(this.contentRect, k).contains(i, j))
        {
          setFocusItemAtPosition(k);
          return k;
        }
    }
    return -1;
  }

  public int findItemPositionBy(Rect paramRect)
  {
    int i = -1;
    if (this.contentRect != null)
    {
      Rect localRect1 = new Rect(paramRect);
      Object localObject = null;
      if (this.mDataAdapter.focusOnPager())
        localRect1.offset(this.contentLeftOffset - this.pagerLeftOffset, this.contentTopOffset - this.pagerTopOffset);
      int j = 0;
      if (j < this.mDataAdapter.getSelectedPageItemCount())
      {
        Rect localRect2 = this.contentProperties.getItemRectByPositionNoMargin(this.contentRect, j);
        if (localRect2.intersect(localRect1))
        {
          if (this.mDataAdapter.focusOnPager())
          {
            Rect localRect3 = new Rect(this.contentRect);
            localRect3.offset(this.contentLeftOffset, this.contentTopOffset);
            if (localRect3.contains(localRect2))
              return j;
          }
          if (localObject != null)
            break label159;
          localObject = localRect2;
          i = j;
        }
        while (true)
        {
          j++;
          break;
          label159: if (localRect2.width() * localRect2.height() > localObject.width() * localObject.height())
          {
            localObject = localRect2;
            i = j;
          }
        }
      }
    }
    return i;
  }

  public List<Integer> findItemsPositionBy(Rect paramRect)
  {
    ArrayList localArrayList = new ArrayList();
    if (this.contentRect != null)
    {
      Rect localRect = new Rect(paramRect);
      if (this.mDataAdapter.focusOnPager())
        localRect.offset(this.contentLeftOffset - this.pagerLeftOffset, this.contentTopOffset - this.pagerTopOffset);
      for (int i = 0; i < this.mDataAdapter.getSelectedPageItemCount(); i++)
        if (Rect.intersects(this.contentProperties.getItemRectByPositionNoMargin(this.contentRect, i), localRect))
          localArrayList.add(Integer.valueOf(i));
    }
    return localArrayList;
  }

  public int findPagePositionBy(int paramInt1, int paramInt2)
  {
    if ((this.pagersRect != null) && (this.pagerProperties.isPagerFocusable()))
    {
      int i = paramInt1;
      int j = paramInt2;
      if (!this.mDataAdapter.focusOnPager())
      {
        i = paramInt1 - this.contentLeftOffset + this.pagerLeftOffset;
        j = paramInt2 - this.contentTopOffset + this.pagerTopOffset;
      }
      for (int k = 0; k < this.mDataAdapter.getPageCount(); k++)
        if (this.pagerProperties.getItemRectByPositionNoMargin(this.pagersRect, k).contains(i, j))
        {
          setFocusPageAtPosition(k);
          return k;
        }
    }
    return -1;
  }

  public int findPagePositionBy(Rect paramRect)
  {
    int i = -1;
    if ((this.pagersRect != null) && (this.pagerProperties.isPagerFocusable()))
    {
      Rect localRect1 = new Rect(paramRect);
      Object localObject = null;
      if (!this.mDataAdapter.focusOnPager())
        localRect1.offset(this.pagerLeftOffset - this.contentLeftOffset, this.pagerTopOffset - this.contentTopOffset);
      int j = 0;
      if (j < this.mDataAdapter.getPageCount())
      {
        Rect localRect2 = this.pagerProperties.getItemRectByPositionNoMargin(this.pagersRect, j);
        if (localRect2.intersect(localRect1))
        {
          if (localObject != null)
            break label120;
          localObject = localRect2;
          i = j;
        }
        while (true)
        {
          j++;
          break;
          label120: if (localRect2.width() * localRect2.height() > localObject.width() * localObject.height())
          {
            localObject = localRect2;
            i = j;
          }
        }
      }
    }
    return i;
  }

  public List<Integer> findPagesPositionBy(Rect paramRect)
  {
    ArrayList localArrayList = new ArrayList();
    if ((this.pagersRect != null) && (this.pagerProperties.isPagerFocusable()))
    {
      Rect localRect = new Rect(paramRect);
      if (!this.mDataAdapter.focusOnPager())
        localRect.offset(this.pagerLeftOffset - this.contentLeftOffset, this.pagerTopOffset - this.contentTopOffset);
      for (int i = 0; i < this.mDataAdapter.getPageCount(); i++)
        if (Rect.intersects(this.pagerProperties.getItemRectByPositionNoMargin(this.pagersRect, i), localRect))
          localArrayList.add(Integer.valueOf(i));
    }
    return localArrayList;
  }

  public int getContentLeftOffset()
  {
    return this.contentLeftOffset;
  }

  public int getContentTopOffset()
  {
    return this.contentTopOffset;
  }

  public int getFirstVisibleItem()
  {
    for (int i = 0; i < this.mDataAdapter.getSelectedPageItemCount(); i++)
      if (isItemVisible(this.contentProperties.getItemRectByPosition(this.contentRect, i)))
        return i;
    return -1;
  }

  public Rect getFocusedRect()
  {
    if (this.focusedRect == null)
      return new Rect();
    Rect localRect = new Rect(this.focusedRect);
    if (this.mDataAdapter.focusOnPager())
    {
      localRect.offset(-this.pagerLeftOffset, -this.pagerTopOffset);
      return localRect;
    }
    localRect.offset(-this.contentLeftOffset, -this.contentTopOffset);
    return localRect;
  }

  public int getItemScrollOffsetX()
  {
    if (this.contentProperties.getScrollType() == Properties.ScrollType.ITEM)
      return this.contentProperties.getItemWidth();
    return this.contentRect.width();
  }

  public int getItemScrollOffsetY()
  {
    if (this.contentProperties.getScrollType() == Properties.ScrollType.ITEM)
      return this.contentProperties.getItemHeight();
    return this.contentRect.height();
  }

  public int getPagerLeftOffset()
  {
    return this.pagerLeftOffset;
  }

  public int getPagerScrollOffsetX()
  {
    if (this.pagerProperties.getScrollType() == Properties.ScrollType.ITEM)
      return this.pagerProperties.getItemWidth();
    return this.pagersRect.width();
  }

  public int getPagerScrollOffsetY()
  {
    if (this.pagerProperties.getScrollType() == Properties.ScrollType.ITEM)
      return this.pagerProperties.getItemHeight();
    return this.pagersRect.height();
  }

  public int getPagerTopOffset()
  {
    return this.pagerTopOffset;
  }

  public int getViewHeight()
  {
    return this.contentProperties.getItemHeight() * this.contentProperties.getRowCount();
  }

  public int getViewWidth()
  {
    return this.contentProperties.getItemWidth() * this.contentProperties.getColumnCount();
  }

  public int getVisibleItemColumnCount()
  {
    int k;
    if (this.contentProperties == null)
    {
      k = 0;
      return k;
    }
    if (this.contentProperties.getItemOrientation() == Properties.ItemOrientation.HORIZONTAL);
    for (int i = 1; ; i = this.contentProperties.getRowCount())
    {
      int j = getFirstVisibleItem();
      for (k = 0; isItemVisible(this.contentProperties.getItemRectByPosition(this.contentRect, j)); k++)
        j += i;
      break;
    }
  }

  public int getVisibleItemRowCount()
  {
    int k;
    if (this.contentProperties == null)
    {
      k = 0;
      return k;
    }
    if (this.contentProperties.getItemOrientation() == Properties.ItemOrientation.HORIZONTAL);
    for (int i = this.contentProperties.getColumnCount(); ; i = 1)
    {
      int j = getFirstVisibleItem();
      for (k = 0; isItemVisible(this.contentProperties.getItemRectByPosition(this.contentRect, j)); k++)
        j += i;
      break;
    }
  }

  public void makeItemVisible(int paramInt)
  {
  }

  public boolean moveFocus(int paramInt)
  {
    return moveFocus(this.focusedRect, paramInt);
  }

  public boolean moveFocus(Rect paramRect, int paramInt)
  {
    if (paramRect == null);
    do
    {
      return false;
      Rect localRect = new Rect(paramRect);
      int i = localRect.width();
      int j = localRect.height();
      int k = 0;
      switch (paramInt)
      {
      default:
      case 130:
      case 33:
      case 17:
      case 66:
      }
      while (setFocusCloseTo(localRect, 0, k))
      {
        return true;
        localRect.offset(0, j);
        k = 0;
        continue;
        localRect.offset(0, -j);
        k = 0;
        continue;
        localRect.offset(-i, 0);
        k = 0;
        continue;
        localRect.offset(i, 0);
        k = localRect.height();
      }
    }
    while (this.onMovingToEdgeListener == null);
    this.onMovingToEdgeListener.moveToEdge(paramInt);
    return false;
  }

  public void setContentLeftOffset(int paramInt)
  {
    this.contentLeftOffset = paramInt;
  }

  public void setContentTopOffset(int paramInt)
  {
    this.contentTopOffset = paramInt;
  }

  public boolean setFocusAt(int paramInt1, int paramInt2)
  {
    int i = findPagePositionBy(paramInt1, paramInt2);
    int j = findItemPositionBy(paramInt1, paramInt2);
    if (i != -1)
    {
      setFocusPageAtPosition(i);
      return true;
    }
    if (j != -1)
    {
      setFocusItemAtPosition(j);
      return true;
    }
    return false;
  }

  public boolean setFocusAt(Rect paramRect, boolean paramBoolean)
  {
    int i = findPagePositionBy(paramRect);
    int j = findItemPositionBy(paramRect);
    if (!paramBoolean)
    {
      if (!this.mDataAdapter.focusOnPager())
        break label45;
      j = -1;
    }
    while (j != -1)
    {
      setFocusItemAtPosition(j);
      return true;
      label45: i = -1;
    }
    if (i != -1)
    {
      setFocusPageAtPosition(i);
      return true;
    }
    return false;
  }

  public boolean setFocusCloseTo(int paramInt1, int paramInt2)
  {
    return setFocusAt(paramInt1, paramInt2);
  }

  public boolean setFocusCloseTo(Rect paramRect, int paramInt1, int paramInt2)
  {
    boolean bool1 = true;
    if ((paramInt1 == 0) && (paramInt2 == 0))
    {
      bool1 = setFocusAt(paramRect, bool1);
      return bool1;
    }
    for (boolean bool2 = true; ; bool2 = false)
    {
      if ((paramRect.top < 0) || (paramRect.left < 0))
        break label96;
      if (setFocusAt(paramRect, bool2))
        break;
      paramRect.left -= paramInt1;
      paramRect.right = (paramInt1 + paramRect.right);
      paramRect.top -= paramInt2;
      paramRect.bottom = (paramInt2 + paramRect.bottom);
    }
    label96: return false;
  }

  public boolean setFocusItemAtPosition(int paramInt)
  {
    if (this.contentRect == null);
    while (!this.mDataAdapter.setFocusedItem(paramInt))
      return false;
    this.focusedRect = this.contentProperties.getItemRectByPositionNoMargin(this.contentRect, paramInt);
    calculateOffset(false);
    return true;
  }

  public boolean setFocusPageAtPosition(int paramInt)
  {
    if ((this.pagersRect == null) || (this.mDataAdapter.getFocusedItem() == null))
      return false;
    this.mDataAdapter.setFocusedPage(paramInt);
    this.focusedRect = this.pagerProperties.getItemRectByPositionNoMargin(this.pagersRect, paramInt);
    calculateOffset(false);
    return true;
  }

  public void setOnMovingToEdgeListener(NewCustomGridView.OnMovingToEdgeListener paramOnMovingToEdgeListener)
  {
    this.onMovingToEdgeListener = paramOnMovingToEdgeListener;
  }

  public void setPagerLeftOffset(int paramInt)
  {
    this.pagerLeftOffset = paramInt;
  }

  public void setPagerTopOffset(int paramInt)
  {
    this.pagerTopOffset = paramInt;
  }

  class HideScrollBarTask extends TimerTask
  {
    HideScrollBarTask()
    {
    }

    public void run()
    {
      ViewPainter.this.hideScrollBar = true;
      ViewPainter.this.mView.postInvalidate();
    }
  }

  public class MarqueeTextOffsetListener
  {
    private int length;
    private int marqueeOffset;
    Runnable marqueeThread = new Runnable()
    {
      public void run()
      {
        int i = 0;
        try
        {
          while (i < ViewPainter.MarqueeTextOffsetListener.this.repeatTimes)
          {
            Thread.sleep(30L);
            ViewPainter.MarqueeTextOffsetListener.access$120(ViewPainter.MarqueeTextOffsetListener.this, App.Operation(1.0F));
            ViewPainter.this.mView.postInvalidate();
            if (Math.abs(ViewPainter.MarqueeTextOffsetListener.this.marqueeOffset) > ViewPainter.MarqueeTextOffsetListener.this.length)
            {
              ViewPainter.MarqueeTextOffsetListener.access$102(ViewPainter.MarqueeTextOffsetListener.this, 0);
              i++;
            }
          }
        }
        catch (InterruptedException localInterruptedException)
        {
        }
      }
    };
    private int repeatTimes;
    private Thread runningThread = null;
    private ItemData savedItemData;

    public MarqueeTextOffsetListener()
    {
    }

    public int getCurrentOffset()
    {
      return this.marqueeOffset;
    }

    public void startMarquee(int paramInt1, int paramInt2, ItemData paramItemData)
    {
      if ((paramItemData != null) && (paramItemData.equals(this.savedItemData)))
        return;
      this.marqueeOffset = 0;
      this.savedItemData = paramItemData;
      if (this.runningThread != null)
        this.runningThread.interrupt();
      this.repeatTimes = paramInt1;
      this.length = paramInt2;
      this.runningThread = new Thread(this.marqueeThread);
      this.runningThread.start();
    }

    public void stopMarquee()
    {
      if (this.runningThread != null)
        this.runningThread.interrupt();
      this.marqueeOffset = 0;
      this.savedItemData = null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.ViewPainter
 * JD-Core Version:    0.6.2
 */