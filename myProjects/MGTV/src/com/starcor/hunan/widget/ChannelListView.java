package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.FilmListItem;
import com.starcor.hunan.App;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChannelListView extends View
  implements View.OnTouchListener
{
  private static final int CATEGORY_HEIGHT = 70;
  private static final int ITEM_HEIGHT_NORMAL = 60;
  private static final int ITEM_HEIGHT_WITH_CATEGORY = 64;
  private static final long MAX_SCROLL_HIDE_DURATION = 300L;
  private static final long MAX_SCROLL_VISIBLE_DURATION = 3000L;
  public static final String USEFUL_CHANNEL = "常用频道";
  private int categoryBackground;
  private boolean categoryHidden = false;
  private String currentCategoryId;
  private String currentDisplayCategory;
  int currentTop = 0;
  private boolean displayCategory = true;
  ArrayList<ListItem> displayItems = new ArrayList();
  int edgeHitCount;
  private String firstCategory;
  int focusedItem = 0;
  int focusedTextColor = -1;
  private Paint focusedTextPaint;
  int idxBkgHeight = 40;
  private Paint idxBkgPaint;
  private Paint idxPaint;
  int idxRightBorder = 80;
  private Comparator itemComparator;
  int itemHeight = 64;
  ArrayList<ListItem> items = new ArrayList();
  private String lastCategory;
  int lastHandledKeyEvent;
  long lastScrollVisibleTime = -1L;
  private int leftArrowRes;
  private Paint linePaint;
  private Paint lineShadowPaint;
  private boolean loopFocus = false;
  private ArrayList<String> mCategories;
  private int mCategoryHeight = 60;
  int namePaddingLeft = 90;
  int normalTextColor = -5789785;
  private Paint normalTextPaint;
  OnItemClickedListener onItemClickedListener;
  OnItemFocusedListener onItemFocusedListener;
  OnItemSelectedListener onItemSelectedListener;
  int paddingLeft = 10;
  int paddingRight = 10;
  private int rightArrowRes;
  int savedFocusedItem = 0;
  private int savedSelectedItem = -1;
  Handler scrollHideHandler;
  private Paint scrollPaint;
  int selectedItem = -1;
  private HashMap<Integer, Bitmap> typedIdxBkg = new HashMap();

  public ChannelListView(Context paramContext)
  {
    super(paramContext);
    init(null);
  }

  public ChannelListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }

  public ChannelListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }

  private int categoryItemCount(String paramString)
  {
    return getItemsForCategory(paramString).size();
  }

  private boolean changeCategory(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2;
    if (i < 0)
      i = -1 + this.mCategories.size();
    String str;
    ArrayList localArrayList;
    while (true)
    {
      str = (String)this.mCategories.get(i);
      localArrayList = getItemsForCategory(str);
      if (localArrayList.size() > 0)
        break;
      return changeCategory(i, paramInt2);
      if (i >= this.mCategories.size())
        i = 0;
    }
    if (this.selectedItem != -1)
      this.savedSelectedItem = convertDisplayIndex2Idx(this.selectedItem);
    this.selectedItem = -1;
    ListItem localListItem;
    if (this.savedSelectedItem != -1)
      localListItem = (ListItem)this.items.get(this.savedSelectedItem);
    for (int j = 0; ; j++)
      if (j < localArrayList.size())
      {
        if (this.itemComparator.compare(localListItem.data, ((ListItem)localArrayList.get(j)).data) == 0)
          this.selectedItem = j;
      }
      else
      {
        this.currentDisplayCategory = str;
        this.displayItems = localArrayList;
        if (this.selectedItem > 0)
          setSelectedNotFocus(this.selectedItem);
        if (this.focusedItem >= this.displayItems.size())
          this.focusedItem = (-1 + this.displayItems.size());
        onItemFocused(this.focusedItem);
        scrollIntoFocus();
        invalidate();
        return true;
      }
  }

  private int convertDisplayIndex2Idx(int paramInt)
  {
    if (isCategoryDisplaying())
    {
      if ((this.displayItems == null) || (this.displayItems.size() == 0))
        this.displayItems = getItemsForCategory(this.currentDisplayCategory);
      if ((paramInt >= 0) && (paramInt < this.displayItems.size()))
        paramInt = this.items.indexOf(this.displayItems.get(paramInt));
    }
    else
    {
      return paramInt;
    }
    return -1;
  }

  private int convertDisplayIndex2Idx(int paramInt, String paramString)
  {
    if (isCategoryDisplaying())
    {
      if ((this.displayItems == null) || (this.displayItems.size() == 0))
        this.displayItems = getItemsForCategory(this.currentDisplayCategory);
      if ((paramInt >= 0) && (paramInt < this.displayItems.size()))
      {
        ListItem localListItem1 = (ListItem)this.displayItems.get(paramInt);
        for (int i = 0; i < this.items.size(); i++)
        {
          ListItem localListItem2 = (ListItem)this.items.get(i);
          if ((localListItem2.equals(localListItem1)) && (localListItem2.categories.contains(paramString)))
            return i;
        }
      }
    }
    else
    {
      return paramInt;
    }
    return -1;
  }

  private int convertIdx2DisplayIndex(int paramInt)
  {
    if (isCategoryDisplaying())
    {
      if ((this.displayItems == null) || (this.displayItems.size() == 0))
        this.displayItems = getItemsForCategory(this.currentDisplayCategory);
      if ((paramInt >= 0) && (paramInt < this.items.size()))
      {
        ((ListItem)this.items.get(paramInt));
        paramInt = this.displayItems.indexOf(this.items.get(paramInt));
      }
    }
    else
    {
      return paramInt;
    }
    return -1;
  }

  private int convertIndex2DisplayForFullScreen(int paramInt)
  {
    if (isCategoryDisplaying())
    {
      if ((this.displayItems == null) || (this.displayItems.size() == 0))
        this.displayItems = getItemsForCategory(this.currentDisplayCategory);
      if ((paramInt >= 0) && (paramInt < this.items.size()))
      {
        ListItem localListItem = (ListItem)this.items.get(paramInt);
        for (int i = 0; i < this.displayItems.size(); i++)
          if (this.itemComparator.compare(((ListItem)this.displayItems.get(i)).data, localListItem.data) == 0)
            return i;
      }
    }
    else
    {
      return paramInt;
    }
    return -1;
  }

  private void drawCategory(Canvas paramCanvas, String paramString, Rect paramRect)
  {
    paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    Paint localPaint1 = new Paint();
    Paint localPaint2 = new Paint();
    this.lineShadowPaint.setColor(-264487876);
    paramCanvas.drawRect(paramRect, this.lineShadowPaint);
    localPaint2.setColor(-1);
    paramCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(), this.categoryBackground), paramRect.left, paramRect.top, localPaint2);
    Bitmap localBitmap1 = BitmapFactory.decodeResource(getResources(), this.leftArrowRes);
    int i = 5 + (this.currentTop + (getCategoryHeight() - localBitmap1.getHeight()) / 2);
    paramCanvas.drawBitmap(localBitmap1, 60, i, localPaint2);
    Bitmap localBitmap2 = BitmapFactory.decodeResource(getResources(), this.rightArrowRes);
    int j = -60 + getWidth() - localBitmap2.getWidth();
    int k = 5 + (this.currentTop + (getCategoryHeight() - localBitmap2.getHeight()) / 2);
    paramCanvas.drawBitmap(localBitmap2, j, k, localPaint2);
    localPaint1.setColor(-1);
    localPaint1.setTextSize(App.Operation(27.0F));
    Rect localRect = new Rect();
    localPaint1.getTextBounds(paramString, 0, paramString.length(), localRect);
    paramCanvas.drawText(paramString, paramRect.centerX() - localRect.centerX(), 5 + (paramRect.centerY() - localRect.centerY()), localPaint1);
    paramCanvas.restore();
  }

  private void drawItem(Canvas paramCanvas, int paramInt, ListItem paramListItem, Rect paramRect)
  {
    paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    int i = this.normalTextColor;
    Paint localPaint = this.normalTextPaint;
    String str = paramListItem.idx;
    if (paramInt == this.focusedItem)
    {
      paramCanvas.drawColor(-266696193);
      this.lineShadowPaint.setColor(-266099203);
      paramCanvas.drawRect(paramRect, this.lineShadowPaint);
      i = this.focusedTextColor;
      localPaint = this.focusedTextPaint;
      if (!TextUtils.isEmpty(paramListItem.focusedIdx))
        str = paramListItem.focusedIdx;
    }
    while (true)
    {
      this.idxPaint.setColor(i);
      Rect localRect1 = new Rect();
      this.idxPaint.getTextBounds(str, 0, str.length(), localRect1);
      Rect localRect2 = new Rect(0, 0, this.idxRightBorder - this.paddingLeft, this.idxBkgHeight);
      localRect2.offsetTo(paramRect.left + this.paddingLeft, paramRect.top + (this.itemHeight - this.idxBkgHeight) / 2);
      Bitmap localBitmap = (Bitmap)this.typedIdxBkg.get(Integer.valueOf(0));
      if (paramInt == this.focusedItem)
        localBitmap = (Bitmap)this.typedIdxBkg.get(Integer.valueOf(paramListItem.type));
      if (localBitmap != null)
        paramCanvas.drawBitmap(localBitmap, new Rect(0, 0, localBitmap.getWidth(), localBitmap.getHeight()), localRect2, this.idxBkgPaint);
      paramCanvas.drawText(str, paramRect.left + this.paddingLeft + (this.idxRightBorder - this.paddingLeft - localRect1.width()) / 2, paramRect.bottom - (paramRect.height() - localRect1.height()) / 2, this.idxPaint);
      localPaint.setColor(i);
      Rect localRect3 = new Rect();
      localPaint.getTextBounds(paramListItem.name, 0, paramListItem.name.length(), localRect3);
      int j = paramRect.left + this.paddingLeft + this.namePaddingLeft;
      int k = paramRect.right - this.paddingRight;
      drawTextEllipsis(paramCanvas, paramListItem.name, j, -1 + (paramRect.bottom - (paramRect.height() - localRect3.height()) / 2), k - j, localPaint);
      paramCanvas.restore();
      return;
      if (paramInt == this.selectedItem)
      {
        paramCanvas.drawColor(-265339301);
        this.lineShadowPaint.setColor(-264813214);
        paramCanvas.drawRect(paramRect, this.lineShadowPaint);
      }
      else if (paramListItem.isHighlight)
      {
        paramCanvas.drawColor(-264948427);
        this.lineShadowPaint.setColor(-264487876);
        paramCanvas.drawRect(paramRect, this.lineShadowPaint);
      }
      else
      {
        paramCanvas.drawColor(-265869529);
        this.lineShadowPaint.setColor(-265408976);
        paramCanvas.drawRect(paramRect, this.lineShadowPaint);
      }
    }
  }

  private void drawTextEllipsis(Canvas paramCanvas, String paramString, float paramFloat1, float paramFloat2, float paramFloat3, Paint paramPaint)
  {
    float[] arrayOfFloat = new float[paramString.length()];
    paramPaint.getTextWidths(paramString, arrayOfFloat);
    Rect localRect = new Rect();
    paramPaint.getTextBounds("...", 0, 3, localRect);
    int i = -1;
    float f1 = 0.0F;
    float f2 = 0.0F;
    for (int j = 0; ; j++)
    {
      int k = arrayOfFloat.length;
      if (j >= k)
        break;
      f2 += arrayOfFloat[j];
      if ((i < 0) && (paramFloat3 - f2 < localRect.width()))
      {
        f1 = f2 - arrayOfFloat[j];
        i = j;
      }
    }
    if (paramFloat3 >= f2)
    {
      paramCanvas.drawText(paramString, paramFloat1, paramFloat2, paramPaint);
      return;
    }
    paramCanvas.drawText(paramString, 0, i, paramFloat1, paramFloat2, paramPaint);
    paramCanvas.drawText("...", 0, 3, paramFloat1 + f1, paramFloat2, paramPaint);
  }

  private int getCategoryHeight()
  {
    return App.Operation(this.mCategoryHeight);
  }

  private ArrayList<ListItem> getItemsForCategory(String paramString)
  {
    if (isCategoryDisplayEnabled())
    {
      localArrayList = new ArrayList();
      Iterator localIterator = this.items.iterator();
      while (localIterator.hasNext())
      {
        ListItem localListItem = (ListItem)localIterator.next();
        if (localListItem.categories.contains(paramString))
          localArrayList.add(localListItem);
      }
    }
    ArrayList localArrayList = this.items;
    return localArrayList;
  }

  private void initFirstAndLastCategory()
  {
    if (categoryItemCount("常用频道") != 0)
      this.firstCategory = "常用频道";
    Iterator localIterator = this.mCategories.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (categoryItemCount(str) > 0)
      {
        if (TextUtils.isEmpty(this.firstCategory))
          this.firstCategory = str;
        this.lastCategory = str;
      }
    }
  }

  private boolean isCategoryDisplaying()
  {
    return (this.mCategories != null) && (this.mCategories.size() > 1) && (this.displayCategory);
  }

  private void onItemClicked(int paramInt)
  {
    if (this.onItemClickedListener != null)
    {
      int i = convertDisplayIndex2Idx(paramInt);
      this.onItemClickedListener.onItemClicked(i, ((ListItem)this.items.get(i)).data);
    }
  }

  private void onItemFocused(int paramInt)
  {
    this.focusedItem = paramInt;
    this.savedFocusedItem = paramInt;
    invalidate();
    int i;
    if (this.onItemFocusedListener != null)
    {
      i = convertDisplayIndex2Idx(paramInt);
      if (i != -1);
    }
    else
    {
      return;
    }
    this.onItemFocusedListener.onItemFocused(i, ((ListItem)this.items.get(i)).data);
  }

  private void onItemSelected(int paramInt)
  {
    this.selectedItem = paramInt;
    invalidate();
    if (this.onItemSelectedListener != null)
      this.onItemSelectedListener.onItemSelected(paramInt, ((ListItem)this.items.get(paramInt)).data);
  }

  private void onItemSelectedByDisplayIndex(int paramInt)
  {
    this.selectedItem = paramInt;
    invalidate();
    if (this.onItemSelectedListener != null)
    {
      int i = this.items.indexOf(this.displayItems.get(paramInt));
      this.onItemSelectedListener.onItemSelected(i, ((ListItem)this.items.get(i)).data);
    }
  }

  private void setSelectedItemInternal(int paramInt, boolean paramBoolean)
  {
    if (paramInt < 0);
    do
    {
      do
      {
        return;
        if (paramInt != this.focusedItem)
        {
          this.focusedItem = paramInt;
          if (!paramBoolean)
          {
            onItemFocused(paramInt);
            this.savedFocusedItem = 0;
          }
          scrollIntoFocus();
        }
      }
      while (paramInt == this.selectedItem);
      this.selectedItem = paramInt;
    }
    while (paramBoolean);
    onItemSelectedByDisplayIndex(paramInt);
  }

  private void setSelectedNotFocus(int paramInt)
  {
    if (paramInt < 0);
    while (paramInt == this.selectedItem)
      return;
    this.selectedItem = paramInt;
    onItemSelectedByDisplayIndex(paramInt);
  }

  private static long timestamp()
  {
    return System.nanoTime() / 1000000L;
  }

  public void addCategories(String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      addCategory(paramArrayOfString[j]);
  }

  public void addCategory(String paramString)
  {
    if (this.mCategories == null)
    {
      this.mCategories = new ArrayList();
      this.currentDisplayCategory = paramString;
    }
    if (!this.mCategories.contains(paramString))
      this.mCategories.add(paramString);
  }

  public int addItem(int paramInt, String paramString1, String paramString2, Object paramObject)
  {
    ListItem localListItem = new ListItem(paramInt, paramString1, null, paramString2, paramObject);
    int i = this.items.size();
    addItemToList(localListItem);
    invalidate();
    return i;
  }

  public int addItem(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    ListItem localListItem = new ListItem(paramInt, paramString1, null, paramString2, null);
    localListItem.categories.add(paramString3);
    int i = this.items.size();
    addItemToList(localListItem);
    invalidate();
    return i;
  }

  public int addItem(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    ListItem localListItem = new ListItem(paramInt, paramString1, paramString2, paramString3, paramObject);
    int i = this.items.size();
    addItemToList(localListItem);
    invalidate();
    return i;
  }

  public int addItem(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, Object paramObject)
  {
    ListItem localListItem = new ListItem(paramInt, paramString1, paramString2, paramString3, paramObject);
    String[] arrayOfString = paramString4.split("\\|");
    Collections.addAll(localListItem.categories, arrayOfString);
    addCategories(arrayOfString);
    int i = this.items.size();
    addItemToList(localListItem);
    invalidate();
    return i;
  }

  public int addItem(int paramInt, String paramString1, String paramString2, String paramString3, List<String> paramList, Object paramObject)
  {
    ListItem localListItem = new ListItem(paramInt, paramString1, paramString2, paramString3, paramObject);
    localListItem.categories.addAll(paramList);
    int i = this.items.size();
    addItemToList(localListItem);
    invalidate();
    return i;
  }

  public void addItemIdxBkg(int paramInt, Bitmap paramBitmap)
  {
    this.typedIdxBkg.put(Integer.valueOf(paramInt), paramBitmap);
  }

  public void addItemToList(ListItem paramListItem)
  {
    this.items.add(paramListItem);
  }

  public void changeToAllCategory()
  {
    if ((this.categoryHidden) && (this.currentDisplayCategory != this.mCategories.get(1)))
    {
      this.currentDisplayCategory = ((String)this.mCategories.get(1));
      this.displayItems = getItemsForCategory(this.currentDisplayCategory);
    }
  }

  public void enableLoopFocus(boolean paramBoolean)
  {
    this.loopFocus = paramBoolean;
  }

  public int findItem(Comparable<Object> paramComparable)
  {
    for (int i = 0; i < this.displayItems.size(); i++)
      if (paramComparable.compareTo(((ListItem)this.displayItems.get(i)).data) == 0)
        return convertDisplayIndex2Idx(i);
    for (int j = 0; j < this.items.size(); j++)
      if (paramComparable.compareTo(((ListItem)this.items.get(j)).data) == 0)
        return j;
    return -1;
  }

  public String getCurrentCategoryId()
  {
    return this.currentCategoryId;
  }

  public int getDisplayItemCount()
  {
    if ((this.displayItems == null) || (this.displayItems.size() == 0))
      this.displayItems = getItemsForCategory(this.currentDisplayCategory);
    return this.displayItems.size();
  }

  public int getFocusedItem()
  {
    return convertDisplayIndex2Idx(this.focusedItem);
  }

  int getItemByXY(int paramInt1, int paramInt2)
  {
    return (paramInt2 + this.currentTop) / this.itemHeight;
  }

  public int getItemCount()
  {
    return this.items.size();
  }

  public Object getItemData(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.items.size()))
      return null;
    return ((ListItem)this.items.get(paramInt)).data;
  }

  public String getItemIdx(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.items.size()))
      return null;
    return ((ListItem)this.items.get(paramInt)).idx;
  }

  public int getSelectedItem()
  {
    return convertDisplayIndex2Idx(this.selectedItem);
  }

  public void hideCategory(boolean paramBoolean)
  {
    if (isCategoryDisplayEnabled())
    {
      this.categoryHidden = paramBoolean;
      if (!paramBoolean)
        break label112;
      if ((DeviceInfo.isHMD()) || (DeviceInfo.isFactoryTCL()))
        this.itemHeight = App.Operation(50.0F);
      this.mCategoryHeight = 0;
      int i = convertDisplayIndex2Idx(this.selectedItem);
      if (i >= 0)
      {
        this.currentDisplayCategory = ((String)this.mCategories.get(1));
        this.displayItems = getItemsForCategory(this.currentDisplayCategory);
        setSelectedItemInternal(convertIndex2DisplayForFullScreen(i));
      }
      this.currentTop = 0;
      scrollIntoFocus(App.Operation(546.0F));
    }
    while (true)
    {
      invalidate();
      return;
      label112: if ((DeviceInfo.isHMD()) || (DeviceInfo.isFactoryTCL()))
        this.itemHeight = App.Operation(52.0F);
      this.mCategoryHeight = 70;
      if (this.selectedItem >= 0)
      {
        this.currentDisplayCategory = ((String)this.mCategories.get(1));
        this.displayItems = getItemsForCategory(this.currentDisplayCategory);
        setSelectedItemInternal(this.selectedItem);
      }
      this.currentTop = 0;
      scrollIntoFocus(App.Operation(640.0F));
    }
  }

  void init(AttributeSet paramAttributeSet)
  {
    setFocusable(true);
    setFocusableInTouchMode(true);
    this.idxPaint = new Paint();
    this.idxPaint.setShadowLayer(2.0F, 0.5F, 0.5F, -16777216);
    this.idxPaint.setTextSize(20.0F);
    this.idxPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.idxPaint.setStrokeWidth(0.0F);
    this.idxPaint.setAntiAlias(true);
    this.idxBkgPaint = new Paint();
    this.focusedTextPaint = new Paint();
    this.focusedTextPaint.setShadowLayer(3.5F, 0.5F, 0.5F, -16777216);
    this.focusedTextPaint.setTextSize(24.0F);
    this.focusedTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.focusedTextPaint.setStrokeWidth(0.6F);
    this.focusedTextPaint.setAntiAlias(true);
    this.normalTextPaint = new Paint();
    this.normalTextPaint.setShadowLayer(3.5F, 0.5F, 0.5F, -16777216);
    this.normalTextPaint.setTextSize(24.0F);
    this.normalTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.normalTextPaint.setStrokeWidth(0.0F);
    this.normalTextPaint.setAntiAlias(true);
    this.linePaint = new Paint();
    this.linePaint.setStyle(Paint.Style.STROKE);
    this.linePaint.setStrokeWidth(1.0F);
    this.lineShadowPaint = new Paint();
    this.lineShadowPaint.setStyle(Paint.Style.STROKE);
    this.lineShadowPaint.setStrokeWidth(1.2F);
    this.lineShadowPaint.setAntiAlias(true);
    this.lineShadowPaint.setColor(-1);
    this.scrollPaint = new Paint();
    this.scrollPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.scrollPaint.setShadowLayer(1.8F, 0.0F, 0.0F, -16777216);
    this.scrollPaint.setStrokeWidth(1.0F);
    setOnTouchListener(this);
  }

  public boolean isCategoryDisplayEnabled()
  {
    return this.displayCategory;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = this.currentTop / this.itemHeight;
    int j = this.currentTop + getHeight();
    boolean bool = isCategoryDisplaying();
    Rect localRect1 = null;
    Rect localRect2;
    int k;
    label87: ListItem localListItem;
    long l;
    float f;
    int i1;
    int i2;
    if (bool)
    {
      if (this.categoryHidden)
        this.mCategoryHeight = 0;
    }
    else
    {
      if (localRect1 != null)
      {
        paramCanvas.save();
        paramCanvas.clipRect(localRect1);
      }
      localRect2 = new Rect(0, 1 - this.itemHeight, getWidth(), 0);
      k = i;
      if (k < this.displayItems.size())
      {
        localListItem = (ListItem)this.displayItems.get(k);
        localRect2.offsetTo(0, k * this.itemHeight + getCategoryHeight());
        if (localRect2.top <= j)
          break label515;
      }
      if (localRect2.bottom < j)
      {
        localRect2.top = localRect2.bottom;
        localRect2.bottom = j;
        paramCanvas.save();
        paramCanvas.clipRect(localRect2);
        paramCanvas.drawColor(-265869529);
        this.lineShadowPaint.setColor(-265408976);
        paramCanvas.drawRect(localRect2, this.lineShadowPaint);
        paramCanvas.restore();
      }
      if (localRect1 != null)
        paramCanvas.restore();
      if ((this.scrollHideHandler != null) && (this.displayItems.size() > 0))
      {
        l = timestamp() - this.lastScrollVisibleTime;
        f = 1.0F;
        if (l <= 3300L)
        {
          int m = this.displayItems.size() * this.itemHeight;
          int n = getHeight() - getCategoryHeight();
          i1 = 15 + n * (n - 20) / m;
          i2 = 2 + (this.currentTop + this.currentTop * (n - 20) / m);
          if (l < 3000L)
            break label583;
          f = (float)(300L - (l - 3000L)) / 300.0F;
        }
      }
    }
    while (true)
    {
      RectF localRectF = new RectF(0.0F, 0.0F, 5.0F, i1);
      localRectF.offset(getWidth() - localRectF.width() - 1.0F, i2 + getCategoryHeight());
      this.scrollPaint.setAlpha((int)(255.0F * f));
      paramCanvas.drawRect(localRectF, this.scrollPaint);
      paramCanvas.save();
      paramCanvas.clipRect(localRectF);
      paramCanvas.drawColor(4210752 + 16777216 * (int)(240.0F * f));
      paramCanvas.restore();
      return;
      this.mCategoryHeight = 70;
      Rect localRect4 = new Rect(0, this.currentTop, getWidth(), this.currentTop + getCategoryHeight());
      drawCategory(paramCanvas, this.currentDisplayCategory, localRect4);
      localRect1 = new Rect(0, localRect4.bottom, getWidth(), j);
      break;
      label515: drawItem(paramCanvas, k, localListItem, localRect2);
      Rect localRect3 = new Rect(0, 0, localRect2.width(), 2);
      localRect3.offsetTo(0, localRect2.bottom);
      paramCanvas.save();
      paramCanvas.clipRect(localRect3);
      paramCanvas.drawColor(-268435456);
      paramCanvas.restore();
      k++;
      break label87;
      label583: if (l < 300L)
        f = (float)l / 300.0F;
    }
  }

  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getPointerCount() != 1);
    int i;
    int j;
    do
    {
      return false;
      i = (int)paramMotionEvent.getY();
      j = (int)paramMotionEvent.getX();
      switch (paramMotionEvent.getAction())
      {
      case 0:
      case 1:
      case 5:
      case 6:
      case 7:
      case 9:
      case 2:
      case 3:
      case 4:
      case 8:
      default:
        return false;
      case 10:
      }
    }
    while ((j <= 0) || (j >= getWidth()) || (i < 0) || (i <= getHeight()));
    return false;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.displayItems.isEmpty())
      return false;
    this.lastHandledKeyEvent = -1;
    switch (paramInt)
    {
    default:
    case 19:
    case 20:
    case 21:
    case 22:
      do
      {
        do
          while (true)
          {
            return super.onKeyDown(paramInt, paramKeyEvent);
            showScrollBar();
            if (this.focusedItem > 0)
            {
              this.lastHandledKeyEvent = paramInt;
              this.focusedItem = (-1 + this.focusedItem);
              onItemFocused(this.focusedItem);
              this.savedFocusedItem = 0;
              scrollIntoFocus();
              invalidate();
              this.edgeHitCount = 0;
              return true;
            }
            if (this.loopFocus)
            {
              this.lastHandledKeyEvent = paramInt;
              this.edgeHitCount = (1 + this.edgeHitCount);
              continue;
              showScrollBar();
              if (this.focusedItem < -1 + this.displayItems.size())
              {
                this.lastHandledKeyEvent = paramInt;
                this.focusedItem = (1 + this.focusedItem);
                onItemFocused(this.focusedItem);
                this.savedFocusedItem = 0;
                scrollIntoFocus();
                invalidate();
                this.edgeHitCount = 0;
                return true;
              }
              if (this.loopFocus)
              {
                this.lastHandledKeyEvent = paramInt;
                this.edgeHitCount = (1 + this.edgeHitCount);
              }
            }
          }
        while ((!isCategoryDisplaying()) || (this.categoryHidden));
        return changeCategory(this.mCategories.indexOf(this.currentDisplayCategory), -1);
      }
      while ((!isCategoryDisplaying()) || (this.categoryHidden));
      return changeCategory(this.mCategories.indexOf(this.currentDisplayCategory), 1);
    case 23:
    case 66:
    }
    this.lastHandledKeyEvent = paramInt;
    return true;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.items.isEmpty())
      return false;
    if (this.lastHandledKeyEvent == paramInt)
    {
      switch (paramInt)
      {
      default:
      case 23:
      case 66:
      case 20:
      case 19:
      }
      while (true)
      {
        this.lastHandledKeyEvent = -1;
        return true;
        this.lastHandledKeyEvent = paramInt;
        onItemSelectedByDisplayIndex(this.focusedItem);
        onItemClicked(this.focusedItem);
        continue;
        if ((this.loopFocus) && (this.edgeHitCount != 0) && (this.edgeHitCount <= 3) && (this.displayItems.size() > 1))
        {
          this.lastHandledKeyEvent = paramInt;
          this.focusedItem = 0;
          onItemFocused(this.focusedItem);
          this.savedFocusedItem = 0;
          scrollIntoFocus();
          invalidate();
          return true;
        }
        this.edgeHitCount = 0;
        continue;
        if ((this.loopFocus) && (this.edgeHitCount != 0) && (this.edgeHitCount <= 3) && (this.displayItems.size() > 1))
        {
          this.lastHandledKeyEvent = paramInt;
          this.focusedItem = (-1 + this.displayItems.size());
          onItemFocused(this.focusedItem);
          this.savedFocusedItem = 0;
          scrollIntoFocus();
          invalidate();
          return true;
        }
        this.edgeHitCount = 0;
      }
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.displayItems = getItemsForCategory(this.currentDisplayCategory);
    if ((this.displayItems.size() > 0) && (((ListItem)this.displayItems.get(0)).data != null) && ((((ListItem)this.displayItems.get(0)).data instanceof FilmListItem)))
      this.currentCategoryId = ((FilmListItem)((ListItem)this.displayItems.get(0)).data).category_id;
    scrollIntoFocus();
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getPointerCount() != 1);
    int k;
    do
    {
      return false;
      int i = (int)paramMotionEvent.getY();
      int j = (int)paramMotionEvent.getX();
      switch (paramMotionEvent.getAction())
      {
      case 1:
      case 5:
      case 6:
      case 2:
      case 3:
      case 4:
      default:
        return false;
      case 0:
      }
      k = getItemByXY(j, i);
    }
    while ((k < 0) || (k >= this.displayItems.size()));
    setSelectedItemInternal(k);
    onItemClicked(this.focusedItem);
    paramView.requestFocus();
    return false;
  }

  public void removeAllItems()
  {
    this.items.clear();
    this.focusedItem = 0;
    this.selectedItem = -1;
    scrollIntoFocus();
    invalidate();
  }

  public boolean removeItem(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.items.size()))
      return false;
    if ((paramInt < this.focusedItem) && (this.focusedItem > 0))
      this.focusedItem = (-1 + this.focusedItem);
    if (paramInt == this.selectedItem)
      this.selectedItem = -1;
    this.items.remove(paramInt);
    scrollIntoFocus();
    invalidate();
    return true;
  }

  public void scrollIntoFocus()
  {
    if ((1 + this.focusedItem) * this.itemHeight + getCategoryHeight() > this.currentTop + getHeight())
      this.currentTop = ((1 + this.focusedItem) * this.itemHeight + getCategoryHeight() - getHeight());
    if (this.focusedItem * this.itemHeight < this.currentTop)
      this.currentTop = (this.focusedItem * this.itemHeight);
    int i = this.displayItems.size() * this.itemHeight + getCategoryHeight();
    if (i <= getHeight())
      this.currentTop = 0;
    while (true)
    {
      if (this.currentTop != getScrollY())
        scrollTo(0, this.currentTop);
      return;
      if (i < this.currentTop + getHeight())
        this.currentTop = (i - getHeight());
    }
  }

  public void scrollIntoFocus(int paramInt)
  {
    if ((1 + this.focusedItem) * this.itemHeight + getCategoryHeight() > paramInt + this.currentTop)
      this.currentTop = ((1 + this.focusedItem) * this.itemHeight + getCategoryHeight() - paramInt);
    if (this.focusedItem * this.itemHeight < this.currentTop)
      this.currentTop = (this.focusedItem * this.itemHeight);
    int i = this.displayItems.size() * this.itemHeight + getCategoryHeight();
    if (i <= getHeight())
      this.currentTop = 0;
    while (true)
    {
      if (this.currentTop != getScrollY())
        scrollTo(0, this.currentTop);
      return;
      if (i < this.currentTop + getHeight())
        this.currentTop = (i - getHeight());
    }
  }

  public void setCategoryBackground(int paramInt)
  {
    this.categoryBackground = paramInt;
  }

  public void setCategoryDisplayEnabled(boolean paramBoolean)
  {
    this.displayCategory = paramBoolean;
    this.displayItems = getItemsForCategory(this.currentDisplayCategory);
    if (!paramBoolean)
      this.mCategoryHeight = 0;
    scrollIntoFocus();
    invalidate();
  }

  public void setCategoryLeftArrow(int paramInt)
  {
    this.leftArrowRes = paramInt;
  }

  public void setCategoryRightArrow(int paramInt)
  {
    this.rightArrowRes = paramInt;
  }

  public void setComparator(Comparator<Object> paramComparator)
  {
    this.itemComparator = paramComparator;
  }

  public void setFocusedItem(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.items.size()));
    while (paramInt == this.focusedItem)
      return;
    this.focusedItem = paramInt;
    scrollIntoFocus();
    onItemFocused(paramInt);
  }

  public void setItemGrid(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.itemHeight = paramInt6;
    this.paddingLeft = paramInt1;
    this.paddingRight = paramInt2;
    this.idxRightBorder = (paramInt1 + paramInt3);
    this.namePaddingLeft = (paramInt5 + this.idxRightBorder);
    this.idxBkgHeight = paramInt4;
    invalidate();
  }

  public void setItemHighlight(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= this.items.size()))
      return;
    ((ListItem)this.items.get(paramInt)).isHighlight = paramBoolean;
  }

  public void setOnItemClickedListener(OnItemClickedListener paramOnItemClickedListener)
  {
    this.onItemClickedListener = paramOnItemClickedListener;
  }

  public void setOnItemFocusedListener(OnItemFocusedListener paramOnItemFocusedListener)
  {
    this.onItemFocusedListener = paramOnItemFocusedListener;
  }

  public void setOnItemSelectedListener(OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.onItemSelectedListener = paramOnItemSelectedListener;
  }

  public void setSelectedItem(int paramInt)
  {
    setSelectedItem(paramInt, false);
  }

  public void setSelectedItem(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= this.items.size()));
    int i;
    while (true)
    {
      return;
      i = convertIdx2DisplayIndex(paramInt);
      if (i >= 0)
        break;
      ListItem localListItem = (ListItem)this.items.get(paramInt);
      if (localListItem != null)
        for (int j = 0; j < this.mCategories.size(); j++)
          if (localListItem.categories.contains(this.mCategories.get(j)))
          {
            changeCategory(j - 1, 1);
            setSelectedItem(paramInt, false);
            return;
          }
    }
    setSelectedItemInternal(i, paramBoolean);
  }

  public void setSelectedItem(Comparable<Object> paramComparable)
  {
    setSelectedItem(findItem(paramComparable));
  }

  public void setSelectedItemInternal(int paramInt)
  {
    setSelectedItemInternal(paramInt, false);
  }

  public void setTextSize(int paramInt1, int paramInt2)
  {
    this.idxPaint.setTextSize(paramInt1);
    this.focusedTextPaint.setTextSize(paramInt2);
    this.normalTextPaint.setTextSize(paramInt2);
  }

  public void showScrollBar()
  {
    if (this.scrollHideHandler == null)
    {
      this.lastScrollVisibleTime = timestamp();
      this.scrollHideHandler = new Handler();
      new Runnable()
      {
        public void run()
        {
          long l = ChannelListView.access$000();
          if (ChannelListView.this.scrollHideHandler != null)
          {
            if ((l - ChannelListView.this.lastScrollVisibleTime <= 300L) || (l - ChannelListView.this.lastScrollVisibleTime >= 3000L))
              break label103;
            ChannelListView.this.scrollHideHandler.postDelayed(this, 3000L - (l - ChannelListView.this.lastScrollVisibleTime));
          }
          while (true)
          {
            if (l - ChannelListView.this.lastScrollVisibleTime > 3300L)
              ChannelListView.this.scrollHideHandler = null;
            ChannelListView.this.invalidate();
            return;
            label103: ChannelListView.this.scrollHideHandler.postDelayed(this, 30L);
          }
        }
      }
      .run();
    }
    while (true)
    {
      invalidate();
      return;
      this.lastScrollVisibleTime = (timestamp() - 300L);
    }
  }

  static class ListItem
  {
    Set<String> categories;
    Object data;
    String focusedIdx;
    String idx;
    boolean isHighlight = false;
    String name;
    int type;

    ListItem(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject)
    {
      this.type = paramInt;
      this.idx = paramString1;
      this.focusedIdx = paramString2;
      this.name = paramString3;
      this.data = paramObject;
      this.categories = new HashSet();
    }

    public boolean equals(Object paramObject)
    {
      if ((paramObject == null) || (this.data == null) || (!(paramObject instanceof ListItem)) || (!(this.data instanceof FilmListItem)) || (((ListItem)paramObject).data == null) || (!(((ListItem)paramObject).data instanceof FilmListItem)))
        return false;
      String str = ((FilmListItem)this.data).video_id;
      if (TextUtils.isEmpty(str))
        return false;
      return str.equals(((FilmListItem)((ListItem)paramObject).data).video_id);
    }
  }

  public static abstract interface OnItemClickedListener
  {
    public abstract void onItemClicked(int paramInt, Object paramObject);
  }

  public static abstract interface OnItemFocusedListener
  {
    public abstract void onItemFocused(int paramInt, Object paramObject);
  }

  public static abstract interface OnItemSelectedListener
  {
    public abstract void onItemSelected(int paramInt, Object paramObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ChannelListView
 * JD-Core Version:    0.6.2
 */