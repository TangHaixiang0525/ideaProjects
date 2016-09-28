package com.starcor.hunan.widget.gridview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataAdapter
{
  private ArrayList<ItemData> allItems = new ArrayList();
  private ItemData focusedItem;
  private boolean isMultiSelect = false;
  private int pageCount;
  private ArrayList<ArrayList<ItemData>> pageData;
  private int pageSize = 0;
  private ArrayList<ItemData> pagerItems = new ArrayList();
  private ItemData selectedItem;
  private ArrayList<ItemData> selectedItems;
  private ItemData selectedPage;

  public DataAdapter(int paramInt)
  {
    this.pageSize = paramInt;
  }

  private int getCurrentPage()
  {
    if (this.selectedPage != null)
      return this.selectedPage.getPageIndex();
    return 0;
  }

  private void initPerPageData()
  {
    if (this.pageSize == 0)
    {
      this.pageCount = 1;
      this.pageSize = this.allItems.size();
      this.pageData = new ArrayList(this.pageCount);
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.pageCount)
        return;
      ArrayList localArrayList = new ArrayList();
      int j = 0;
      while (true)
        if (j < this.pageSize)
        {
          int k = j + i * this.pageSize;
          if ((k >= 0) && (k < this.allItems.size()))
          {
            ItemData localItemData = (ItemData)this.allItems.get(k);
            localItemData.setPageIndex(j);
            localArrayList.add(localItemData);
          }
          j++;
          continue;
          this.pageCount = ((int)Math.ceil(this.allItems.size() / this.pageSize));
          break;
        }
      this.pageData.add(localArrayList);
    }
  }

  private void selectFocusedItem(boolean paramBoolean)
  {
    if ((!focusOnPager()) && (this.selectedItem != null))
    {
      this.selectedItem.setSelected(false);
      this.selectedItem = null;
    }
    if (paramBoolean)
    {
      if (focusOnPager())
      {
        this.selectedPage.setSelected(false);
        this.selectedPage = this.focusedItem;
        this.selectedPage.setSelected(true);
      }
    }
    else
      return;
    this.selectedItem = this.focusedItem;
    this.selectedItem.setSelected(true);
  }

  public void addItem(ItemData paramItemData)
  {
    if (this.allItems == null)
      this.allItems = new ArrayList();
    paramItemData.setPageIndex(this.allItems.size());
    this.allItems.add(paramItemData);
  }

  public void addItem(ItemData paramItemData, int paramInt)
  {
    if (this.pageData == null)
      this.pageData = new ArrayList(this.pagerItems.size());
    if (this.allItems == null)
      this.allItems = new ArrayList();
    this.allItems.add(paramItemData);
    while (paramInt >= this.pageData.size())
    {
      ArrayList localArrayList1 = new ArrayList();
      this.pageData.add(localArrayList1);
    }
    ArrayList localArrayList2 = (ArrayList)this.pageData.get(paramInt);
    paramItemData.setPageIndex(localArrayList2.size());
    localArrayList2.add(paramItemData);
  }

  public void addPage(ItemData paramItemData)
  {
    if (this.pagerItems == null)
    {
      this.pagerItems = new ArrayList();
      this.selectedPage = paramItemData;
      this.selectedPage.setSelected(true);
    }
    paramItemData.setPageIndex(this.pagerItems.size());
    this.pageCount = (1 + this.pageCount);
    this.pagerItems.add(paramItemData);
  }

  public void clearData()
  {
    this.allItems.clear();
    this.pageData = null;
    this.pagerItems = null;
    this.focusedItem = null;
    this.selectedItem = null;
    this.selectedPage = null;
  }

  public void clearFocus()
  {
    if (this.focusedItem != null)
      this.focusedItem.setFocused(false);
  }

  public int findItemInPage(ItemData paramItemData)
  {
    int i = -1;
    Iterator localIterator;
    if (this.pageData != null)
    {
      i = 0;
      localIterator = this.pageData.iterator();
    }
    while (true)
    {
      if ((!localIterator.hasNext()) || (((ArrayList)localIterator.next()).contains(paramItemData)))
        return i;
      i++;
    }
  }

  public int findItemPositionInPage(int paramInt, ItemData paramItemData)
  {
    int i = -1;
    if ((this.pageData != null) && (this.pageData.size() >= paramInt + 1))
      i = ((ArrayList)this.pageData.get(paramInt)).indexOf(paramItemData);
    return i;
  }

  public boolean focusOnPager()
  {
    return (this.pagerItems != null) && (this.pagerItems.contains(this.focusedItem));
  }

  public ItemData getFocusedItem()
  {
    return this.focusedItem;
  }

  public ItemData getItemAt(int paramInt)
  {
    if (this.allItems != null)
      return (ItemData)this.allItems.get(paramInt);
    return null;
  }

  public ItemData getItemAt(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0));
    do
    {
      return null;
      if (this.pageData == null)
        initPerPageData();
    }
    while ((this.pageData.size() <= paramInt1) || (getItemCount(paramInt1) <= paramInt2));
    return (ItemData)((ArrayList)this.pageData.get(paramInt1)).get(paramInt2);
  }

  public int getItemCount(int paramInt)
  {
    if (this.pageData == null)
      initPerPageData();
    if ((paramInt >= this.pageData.size()) || (paramInt < 0))
      return 0;
    return ((ArrayList)this.pageData.get(paramInt)).size();
  }

  public int getItemPositionInAllItems(int paramInt)
  {
    if (this.allItems != null)
      return this.allItems.indexOf(getItemAt(getSelectedPageIndex(), paramInt));
    return -1;
  }

  public ItemData getPageAt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.pagerItems.size()))
      return null;
    return (ItemData)this.pagerItems.get(paramInt);
  }

  public int getPageCount()
  {
    return this.pagerItems.size();
  }

  public ItemData getSelectedItem()
  {
    return this.selectedItem;
  }

  public int getSelectedItemIndex()
  {
    if (this.selectedItem == null)
      return -1;
    return this.selectedItem.getPageIndex();
  }

  public int getSelectedPageIndex()
  {
    if (this.selectedPage == null)
      return 0;
    return this.selectedPage.getPageIndex();
  }

  public int getSelectedPageItemCount()
  {
    if (this.selectedPage == null)
      return this.allItems.size();
    return getItemCount(this.selectedPage.getPageIndex());
  }

  public void itemClicked()
  {
    if (this.focusedItem != null)
      if (this.focusedItem.isSelected())
        break label25;
    label25: for (boolean bool = true; ; bool = false)
    {
      selectFocusedItem(bool);
      return;
    }
  }

  public void itemClicked(int paramInt)
  {
    setFocusedItem(paramInt);
    selectFocusedItem(true);
  }

  public void releaseFocus()
  {
    this.focusedItem = null;
  }

  public void setFocusedInAll(int paramInt)
  {
    if ((paramInt > 0) && (paramInt < this.allItems.size()))
    {
      if (this.focusedItem != null)
        this.focusedItem.setFocused(false);
      this.focusedItem = ((ItemData)this.allItems.get(paramInt));
      this.focusedItem.setFocused(true);
    }
  }

  public boolean setFocusedItem(int paramInt)
  {
    if (this.selectedPage != null);
    for (int i = this.selectedPage.getPageIndex(); ; i = 0)
    {
      ItemData localItemData = getItemAt(i, paramInt);
      boolean bool = false;
      if (localItemData != null)
      {
        if (this.focusedItem != null)
          this.focusedItem.setFocused(false);
        this.focusedItem = localItemData;
        this.focusedItem.setFocused(true);
        bool = true;
      }
      return bool;
    }
  }

  public void setFocusedPage(int paramInt)
  {
    if (this.focusedItem != null)
    {
      this.focusedItem.setFocused(false);
      this.focusedItem = null;
    }
    if (this.pagerItems == null);
    do
    {
      return;
      this.focusedItem = ((ItemData)this.pagerItems.get(paramInt));
    }
    while (this.focusedItem == null);
    this.focusedItem.setFocused(true);
  }

  public void setFocusedPager()
  {
    this.focusedItem = ((ItemData)this.pagerItems.get(-1 + this.pagerItems.size()));
  }

  public void setMultiSelect(boolean paramBoolean)
  {
    this.isMultiSelect = paramBoolean;
  }

  public void setSelectedItem(int paramInt)
  {
    if (this.selectedItem != null)
    {
      this.selectedItem.setSelected(false);
      this.selectedItem = null;
    }
    this.selectedItem = getItemAt(getCurrentPage(), paramInt);
    if (this.selectedItem != null)
      this.selectedItem.setSelected(true);
  }

  public void setSelectedItems(List<Integer> paramList)
  {
    if (this.selectedItems == null)
      this.selectedItems = new ArrayList();
    while (true)
    {
      Iterator localIterator2 = paramList.iterator();
      while (localIterator2.hasNext())
      {
        ItemData localItemData = getItemAt(((Integer)localIterator2.next()).intValue());
        if (localItemData != null)
        {
          this.selectedItems.add(localItemData);
          localItemData.setSelected(true);
        }
      }
      Iterator localIterator1 = this.selectedItems.iterator();
      while (localIterator1.hasNext())
        ((ItemData)localIterator1.next()).setSelected(false);
      this.selectedItems.clear();
    }
  }

  public void setSelectedPage(int paramInt)
  {
    if (this.selectedPage != null)
    {
      this.selectedPage.setSelected(false);
      this.selectedPage = null;
    }
    this.selectedPage = ((ItemData)this.pagerItems.get(paramInt));
    if (this.selectedPage != null)
      this.selectedPage.setSelected(true);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.DataAdapter
 * JD-Core Version:    0.6.2
 */