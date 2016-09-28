package com.starcor.hunan.widget.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.starcor.hunan.App;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewCustomGridView extends View
{
  private Bitmap backgroundImage;
  private Paint backgroundPaint;
  private Rect contentRect;
  private Bitmap emptyImage;
  private boolean isEmpty;
  private boolean isLoading = false;
  private Bitmap loadingImage;
  private ContentProperties mContentProperties;
  private Context mContext;
  public DataAdapter mDataAdapter;
  private InputHandler mInputHandler;
  private PagerProperties mPagerProperties;
  private ViewPainter mViewPainter;
  private OnMovingToEdgeListener movingToEdgeListener;
  private OnItemSelectedListener onItemSelectedListener;
  private Rect pagerRect;
  private float rotate = 0.0F;
  private Matrix rotateMatrix = new Matrix();
  private int savedContentLeftOffset;
  private int savedContentTopOffset;
  private int savedItemFocusedPosition = -1;
  private int savedItemSelectedPosition = -1;
  private int savedPageFocusedPosition = -1;
  private int savedPageLeftOffset;
  private int savedPageSelectedPosition = -1;
  private int savedPageTopOffset;
  Paint textPaint = new Paint(3);
  private String tips;
  private Rect viewRect;

  public NewCustomGridView(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public NewCustomGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  public NewCustomGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext);
  }

  private void addItemToAdapter(ItemData paramItemData)
  {
    this.isEmpty = false;
    this.mDataAdapter.addItem(paramItemData);
  }

  private void addItemToAdapter(ItemData paramItemData, int paramInt)
  {
    this.isEmpty = false;
    this.mDataAdapter.addItem(paramItemData, paramInt);
  }

  private void addPageToAdapter(ItemData paramItemData)
  {
    this.isEmpty = false;
    this.mDataAdapter.addPage(paramItemData);
  }

  private void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.viewRect = new Rect();
    this.pagerRect = new Rect();
    this.contentRect = new Rect();
    this.backgroundPaint = new Paint();
    this.textPaint.setTextSize(App.Operation(60.0F));
    this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    this.textPaint.setColor(-9868951);
  }

  private void initHelper()
  {
    if ((this.mPagerProperties == null) && (this.mContentProperties == null))
      return;
    this.mDataAdapter = new DataAdapter(this.mContentProperties.getItemsPerPage());
    this.mDataAdapter.setMultiSelect(this.mContentProperties.isMultiSelect);
    this.mViewPainter = new ViewPainter(this, this.mDataAdapter);
    this.mViewPainter.setOnMovingToEdgeListener(this.movingToEdgeListener);
    this.mInputHandler = new InputHandler(new InputHandler.ActionPerformer()
    {
      private int findNextPageWhenScrollPage(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        int i;
        if (paramAnonymousInt3 > paramAnonymousInt4)
          i = -1;
        while (true)
        {
          return i;
          i = -1;
          if (paramAnonymousInt2 == 17)
          {
            i = paramAnonymousInt1 - 1;
            if (i < 0)
              i = -1 + NewCustomGridView.this.mDataAdapter.getPageCount();
          }
          while (NewCustomGridView.this.mDataAdapter.getItemCount(i) == 0)
          {
            return findNextPageWhenScrollPage(i, paramAnonymousInt2, paramAnonymousInt3 + 1, paramAnonymousInt4);
            if (paramAnonymousInt2 == 66)
            {
              i = paramAnonymousInt1 + 1;
              if (i >= NewCustomGridView.this.mDataAdapter.getPageCount())
                i = 0;
            }
          }
        }
      }

      public void click()
      {
        if (NewCustomGridView.this.mDataAdapter.getFocusedItem() == null)
          return;
        int i = NewCustomGridView.this.mDataAdapter.getFocusedItem().getPageIndex();
        if (NewCustomGridView.this.mDataAdapter.focusOnPager())
        {
          NewCustomGridView.this.mDataAdapter.itemClicked();
          NewCustomGridView.this.mViewPainter.clearContentOffset();
          if (NewCustomGridView.this.onItemSelectedListener != null)
            NewCustomGridView.this.onItemSelectedListener.onPageSelected(i);
          NewCustomGridView.this.invalidate();
        }
        while (true)
        {
          NewCustomGridView.this.invalidate();
          return;
          if ((NewCustomGridView.this.onItemSelectedListener != null) && (!NewCustomGridView.this.mDataAdapter.getItemAt(NewCustomGridView.this.mDataAdapter.getSelectedPageIndex(), i).isSelected()))
            NewCustomGridView.this.onItemSelectedListener.onItemSelected(i);
        }
      }

      public boolean moveFocus(int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        if (NewCustomGridView.this.hasFocus())
        {
          if ((NewCustomGridView.this.mPagerProperties != null) && (NewCustomGridView.this.mPagerProperties.getPagerType() == PagerProperties.PagerType.SCROLL) && ((paramAnonymousInt == 17) || (paramAnonymousInt == 66)))
          {
            int k = findNextPageWhenScrollPage(NewCustomGridView.this.getSelectedPagePosition(), paramAnonymousInt, 0, NewCustomGridView.this.mDataAdapter.getPageCount());
            if ((k >= 0) && (k < NewCustomGridView.this.mDataAdapter.getPageCount()))
            {
              ItemData localItemData = NewCustomGridView.this.mDataAdapter.getFocusedItem();
              int m = 0;
              if (localItemData != null)
                m = NewCustomGridView.this.mDataAdapter.getFocusedItem().getPageIndex();
              if (m >= NewCustomGridView.this.mDataAdapter.getItemCount(k))
              {
                m = -1 + NewCustomGridView.this.mDataAdapter.getItemCount(k);
                NewCustomGridView.this.mViewPainter.clearContentOffset();
              }
              NewCustomGridView.this.mDataAdapter.setSelectedPage(k);
              NewCustomGridView.this.mViewPainter.setFocusItemAtPosition(m);
              return true;
            }
          }
          int i;
          int j;
          if ((NewCustomGridView.this.mContentProperties.isLoopFocusEnabled()) && (paramAnonymousBoolean))
          {
            i = 1;
            j = NewCustomGridView.this.mDataAdapter.getFocusedItem().getPageIndex();
            switch (paramAnonymousInt)
            {
            default:
            case 130:
            case 33:
            }
          }
          do
          {
            do
            {
              return NewCustomGridView.this.mViewPainter.moveFocus(paramAnonymousInt);
              i = 0;
              break;
            }
            while (j != -1 + NewCustomGridView.this.mDataAdapter.getItemCount(NewCustomGridView.this.mDataAdapter.getSelectedPageIndex()));
            if (i != 0)
            {
              NewCustomGridView.this.mViewPainter.setFocusItemAtPosition(0);
              return true;
            }
            return NewCustomGridView.this.mViewPainter.moveFocus(paramAnonymousInt);
          }
          while (j != 0);
          if (i != 0)
          {
            NewCustomGridView.this.mViewPainter.setFocusItemAtPosition(-1 + NewCustomGridView.this.mDataAdapter.getItemCount(NewCustomGridView.this.mDataAdapter.getSelectedPageIndex()));
            return true;
          }
          return NewCustomGridView.this.mViewPainter.moveFocus(paramAnonymousInt);
        }
        return false;
      }
    });
  }

  public void addItem(String paramString, int paramInt, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData, paramInt);
  }

  public void addItem(String paramString, Bitmap paramBitmap, int paramInt, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString);
    localItemData.setPicture(paramBitmap);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData, paramInt);
  }

  public void addItem(String paramString, Bitmap paramBitmap, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString);
    localItemData.setPicture(paramBitmap);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData);
  }

  public void addItem(String paramString, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData);
  }

  public void addItem(String paramString1, String paramString2, int paramInt, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString1);
    localItemData.setDetail(paramString2);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData, paramInt);
  }

  public void addItem(String paramString1, String paramString2, Bitmap paramBitmap, int paramInt, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString1);
    localItemData.setDetail(paramString2);
    localItemData.setPicture(paramBitmap);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData, paramInt);
  }

  public void addItem(String paramString1, String paramString2, Object paramObject)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString1);
    localItemData.setDetail(paramString2);
    localItemData.setData(paramObject);
    addItemToAdapter(localItemData);
  }

  public void addPage(String paramString)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString);
    localItemData.setData(paramString);
    addPageToAdapter(localItemData);
  }

  public void addPage(String paramString1, String paramString2)
  {
    ItemData localItemData = new ItemData();
    localItemData.setTitle(paramString1);
    localItemData.setDetail(paramString2);
    localItemData.setData(paramString1);
    addPageToAdapter(localItemData);
  }

  public void addPages(List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      addPage((String)localIterator.next());
  }

  public void clearData()
  {
    this.mDataAdapter.clearData();
    this.mViewPainter.clearContentOffset();
    this.mViewPainter.clearFocus();
    this.isEmpty = true;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public ContentProperties getContentProperties()
  {
    return this.mContentProperties;
  }

  public int getFirstVisibleItem()
  {
    return this.mViewPainter.getFirstVisibleItem();
  }

  public Object getFocusedItemData()
  {
    if (this.mDataAdapter.getFocusedItem() != null)
      return this.mDataAdapter.getFocusedItem().getData();
    return null;
  }

  public int getFocusedItemPosition()
  {
    if (!this.mDataAdapter.focusOnPager())
    {
      ItemData localItemData = this.mDataAdapter.getFocusedItem();
      if (localItemData != null)
        return localItemData.getPageIndex();
    }
    return -1;
  }

  public int getFocusedPagePosition()
  {
    if (this.mDataAdapter.focusOnPager())
    {
      ItemData localItemData = this.mDataAdapter.getFocusedItem();
      if (localItemData != null)
        return localItemData.getPageIndex();
    }
    return -1;
  }

  public Object getItem(int paramInt)
  {
    if ((this.mDataAdapter == null) || (this.mDataAdapter.getItemAt(this.mDataAdapter.getSelectedPageIndex(), paramInt) == null))
      return null;
    return this.mDataAdapter.getItemAt(this.mDataAdapter.getSelectedPageIndex(), paramInt).getData();
  }

  public int getItemCount()
  {
    return this.mDataAdapter.getItemCount(this.mDataAdapter.getSelectedPageIndex());
  }

  public ItemData getItemDataFromList(int paramInt)
  {
    return this.mDataAdapter.getItemAt(this.mDataAdapter.getSelectedPageIndex(), paramInt);
  }

  public Object getItemFromAll(int paramInt)
  {
    return this.mDataAdapter.getItemAt(paramInt).getData();
  }

  public int getItemPositionInAllItems(int paramInt)
  {
    return this.mDataAdapter.getItemPositionInAllItems(paramInt);
  }

  public Object getPage(int paramInt)
  {
    return this.mDataAdapter.getPageAt(paramInt).getData();
  }

  public List getPageDataList(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < this.mDataAdapter.getItemCount(paramInt); i++)
      localArrayList.add(this.mDataAdapter.getItemAt(paramInt, i).getData());
    return localArrayList;
  }

  public PagerProperties getPagerProperties()
  {
    return this.mPagerProperties;
  }

  public Object getSelectedItemData()
  {
    if (this.mDataAdapter.getSelectedItem() != null)
      return this.mDataAdapter.getSelectedItem().getData();
    return null;
  }

  public int getSelectedItemPosition()
  {
    return this.mDataAdapter.getSelectedItemIndex();
  }

  public int getSelectedPagePosition()
  {
    return this.mDataAdapter.getSelectedPageIndex();
  }

  public void makeItemRowVisible(int paramInt1, int paramInt2)
  {
    int i = (1 + (paramInt1 / this.mContentProperties.getColumnCount() - paramInt2)) * this.mContentProperties.getItemHeight();
    if (i < 0)
      i = 0;
    this.mViewPainter.setContentTopOffset(i);
    invalidate();
  }

  public void makeItemVisible(int paramInt1, int paramInt2)
  {
    int i = (1 + (paramInt1 / this.mContentProperties.getRowCount() - paramInt2)) * this.mContentProperties.getItemWidth();
    if (i < 0)
      i = 0;
    this.mViewPainter.setContentLeftOffset(i);
    invalidate();
  }

  protected void onDetachedFromWindow()
  {
    if (this.mViewPainter != null)
      this.mViewPainter.destroy();
    super.onDetachedFromWindow();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    boolean bool;
    if (!this.isEmpty)
    {
      bool = true;
      setFocusable(bool);
      getDrawingRect(this.viewRect);
      this.contentRect.set(this.viewRect);
      if (this.backgroundImage != null)
      {
        paramCanvas.save();
        paramCanvas.drawBitmap(this.backgroundImage, new Rect(0, 0, this.backgroundImage.getWidth(), this.backgroundImage.getHeight()), this.viewRect, this.backgroundPaint);
        paramCanvas.restore();
      }
      if ((this.mPagerProperties != null) || (this.mContentProperties != null))
        break label108;
    }
    label108: 
    do
    {
      do
      {
        return;
        bool = false;
        break;
        if ((this.mDataAdapter == null) || (this.mViewPainter == null) || (this.mInputHandler == null))
          initHelper();
        if (!this.isLoading)
          break label284;
      }
      while (this.loadingImage == null);
      this.rotate = (60.0F + this.rotate);
      paramCanvas.save();
      int m = this.viewRect.centerX() - this.loadingImage.getWidth() / 2;
      int n = this.viewRect.centerY() - this.loadingImage.getHeight() / 2;
      this.rotateMatrix.reset();
      this.rotateMatrix.setTranslate(m, n);
      this.rotateMatrix.postRotate(this.rotate, this.viewRect.centerX(), this.viewRect.centerY());
      paramCanvas.drawBitmap(this.loadingImage, this.rotateMatrix, this.backgroundPaint);
      this.rotateMatrix.reset();
      paramCanvas.restore();
      postInvalidateDelayed(200L);
      return;
      if (!this.isEmpty)
        break label482;
    }
    while (this.emptyImage == null);
    label284: this.rotate = 0.0F;
    paramCanvas.save();
    paramCanvas.drawBitmap(this.emptyImage, this.viewRect.centerX() - this.emptyImage.getWidth() / 2, this.viewRect.centerY() - this.emptyImage.getHeight(), this.backgroundPaint);
    if (!TextUtils.isEmpty(this.tips))
    {
      float[] arrayOfFloat = new float[this.tips.length()];
      this.textPaint.getTextWidths(this.tips, arrayOfFloat);
      int i = this.tips.length();
      int j = 0;
      for (int k = 0; k < i; k++)
        j += (int)Math.ceil(arrayOfFloat[k]);
      paramCanvas.drawText(this.tips, this.viewRect.centerX() - j / 2, this.viewRect.centerY() + 2 * this.emptyImage.getHeight() / 5, this.textPaint);
    }
    paramCanvas.restore();
    return;
    label482: this.rotate = 0.0F;
    this.mViewPainter.drawView(paramCanvas, this.viewRect, this.mPagerProperties, this.mContentProperties);
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    int i;
    if (paramBoolean)
      if (this.mDataAdapter.getFocusedItem() != null)
      {
        i = this.mDataAdapter.getFocusedItem().getPageIndex();
        if (!this.mDataAdapter.focusOnPager())
          break label76;
        if (!this.mViewPainter.setFocusPageAtPosition(i))
          this.mViewPainter.setFocusItemAtPosition(i);
      }
    while (true)
    {
      invalidate();
      return;
      i = 0;
      break;
      label76: if (!this.mViewPainter.setFocusItemAtPosition(i))
      {
        this.mViewPainter.setFocusPageAtPosition(i);
        continue;
        this.mViewPainter.clearFocus();
      }
    }
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((hasFocus()) && (this.mInputHandler.handleKeyDown(paramInt, paramKeyEvent)))
    {
      invalidate(this.mViewPainter.getFocusedRect());
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((hasFocus()) && (this.mInputHandler.handleKeyUp(paramInt, paramKeyEvent)))
    {
      invalidate(this.mViewPainter.getFocusedRect());
      return true;
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }

  public boolean requestFocus(int paramInt, Rect paramRect)
  {
    return super.requestFocus(paramInt, paramRect);
  }

  public void restorePreviousSavedState()
  {
    this.mViewPainter.setContentLeftOffset(this.savedContentLeftOffset);
    this.mViewPainter.setContentTopOffset(this.savedContentTopOffset);
    this.mViewPainter.setPagerTopOffset(this.savedPageTopOffset);
    this.mViewPainter.setPagerLeftOffset(this.savedPageLeftOffset);
    if (this.savedPageSelectedPosition >= 0)
      setSelectedPage(this.savedPageSelectedPosition);
    if (this.savedItemFocusedPosition >= 0)
    {
      setPreDefineFocusItem(this.savedItemFocusedPosition);
      this.savedItemFocusedPosition = -1;
    }
    if (this.savedItemSelectedPosition >= 0)
    {
      setSelectedItem(this.savedItemSelectedPosition);
      this.savedItemSelectedPosition = -1;
    }
    this.savedPageSelectedPosition = -1;
  }

  public void saveCurrentState()
  {
    this.savedContentLeftOffset = this.mViewPainter.getContentLeftOffset();
    this.savedContentTopOffset = this.mViewPainter.getContentTopOffset();
    this.savedPageLeftOffset = this.mViewPainter.getPagerLeftOffset();
    this.savedPageTopOffset = this.mViewPainter.getPagerTopOffset();
    this.savedItemFocusedPosition = getFocusedItemPosition();
    this.savedItemSelectedPosition = getSelectedItemPosition();
    this.savedPageFocusedPosition = getFocusedPagePosition();
    this.savedPageSelectedPosition = getSelectedPagePosition();
  }

  public void setBackgroundImage(Bitmap paramBitmap)
  {
    this.backgroundImage = paramBitmap;
  }

  public void setContentProperties(ContentProperties paramContentProperties)
  {
    this.mContentProperties = paramContentProperties;
    initHelper();
  }

  public void setEmptyImage(Bitmap paramBitmap)
  {
    this.emptyImage = paramBitmap;
  }

  public void setEmptyText(String paramString)
  {
    this.tips = paramString;
  }

  public void setIsEmpty(boolean paramBoolean)
  {
    this.isEmpty = paramBoolean;
    invalidate();
  }

  public void setLoadingImage(Bitmap paramBitmap)
  {
    this.loadingImage = paramBitmap;
  }

  public void setMovingToEdgeListener(OnMovingToEdgeListener paramOnMovingToEdgeListener)
  {
    this.movingToEdgeListener = paramOnMovingToEdgeListener;
  }

  public void setMultipleSelection(List<Integer> paramList)
  {
    this.mDataAdapter.setSelectedItems(paramList);
  }

  public void setOnItemSelectedItemListener(OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.onItemSelectedListener = paramOnItemSelectedListener;
  }

  public void setPagerProperties(PagerProperties paramPagerProperties)
  {
    this.mPagerProperties = paramPagerProperties;
  }

  public void setPreDefineFocusItem(int paramInt)
  {
    this.mDataAdapter.setFocusedItem(paramInt);
  }

  public void setSelectedItem(int paramInt)
  {
    this.mDataAdapter.setSelectedItem(paramInt);
    if (hasFocus())
      this.mViewPainter.setFocusItemAtPosition(paramInt);
  }

  public void setSelectedItemInAll(int paramInt)
  {
    ItemData localItemData = this.mDataAdapter.getItemAt(paramInt);
    int i = this.mDataAdapter.findItemInPage(localItemData);
    int j = this.mDataAdapter.findItemPositionInPage(i, localItemData);
    this.mDataAdapter.setSelectedPage(i);
    this.mDataAdapter.setSelectedItem(j);
    this.mViewPainter.setFocusItemAtPosition(j);
  }

  public void setSelectedPage(int paramInt)
  {
    this.mDataAdapter.setSelectedPage(paramInt);
  }

  public void showLoading(boolean paramBoolean)
  {
    this.isLoading = paramBoolean;
    invalidate();
  }

  public static abstract interface OnItemSelectedListener
  {
    public abstract void onItemSelected(int paramInt);

    public abstract void onPageSelected(int paramInt);
  }

  public static abstract interface OnMovingToEdgeListener
  {
    public abstract void moveToEdge(int paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.NewCustomGridView
 * JD-Core Version:    0.6.2
 */