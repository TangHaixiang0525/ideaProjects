package com.starcor.xul.Utils;

import android.graphics.Rect;
import com.starcor.xul.XulUtils;

public class XulLayoutHelper
{
  public static final int MODE_GRID_HORIZONTAL = 4;
  public static final int MODE_GRID_INVERSE_HORIZONTAL = 16777220;
  public static final int MODE_GRID_VERTICAL = 8;
  public static final int MODE_LINEAR_HORIZONTAL = 1;
  public static final int MODE_LINEAR_INVERSE_HORIZONTAL = 16777217;
  public static final int MODE_LINEAR_INVERSE_VERTICAL = 16777218;
  public static final int MODE_LINEAR_VERTICAL = 2;
  static LayoutElementArray _tmpElementArray;
  private static MatchParentUpdateException matchParentUpdateException;

  static
  {
    if (!XulLayoutHelper.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      _tmpElementArray = new LayoutElementArray(4096);
      matchParentUpdateException = new MatchParentUpdateException();
      return;
    }
  }

  private static void _calChildrenSizeP2_HorizontalGrid(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    if (i3 < i)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(i3);
      if (localILayoutElement == null);
      while (true)
      {
        i3++;
        break;
        Rect localRect = localILayoutElement.getMargin();
        int i4 = localRect.left;
        int i5 = localRect.top;
        int i6 = localRect.right;
        int i7 = localRect.bottom;
        int i8 = _judgeMargin(n, i4);
        int i9 = _judgeMargin(i1, i5);
        int i10 = j + i8;
        boolean bool = localILayoutElement.changed();
        int i11;
        if (bool)
        {
          if ((localILayoutElement instanceof ILayoutContainer))
          {
            _calContainerSizeP2((ILayoutContainer)localILayoutElement, paramInt3 + i10, i9 + (paramInt4 + k), paramInt1 - i10 - i6, paramInt2 - k - i9 - i7);
            _calContainerSizeP3((ILayoutContainer)localILayoutElement);
          }
        }
        else
        {
          i11 = localILayoutElement.getWidth();
          if (i10 + i11 <= paramInt1)
            break label346;
          i10 = i4;
          k += m;
          m = 0;
          i1 = i2;
          i2 = i7;
          i9 = _judgeMargin(i1, i5);
        }
        while (true)
        {
          n = i6;
          _rebase(localILayoutElement, paramInt3 + i10, i9 + (paramInt4 + k));
          if ((bool) || (!localILayoutElement.checkUpdateMatchParent(paramInt1 - i10 - i6, paramInt2 - k - i9 - i7)))
            break label358;
          localILayoutElement.prepare();
          throw matchParentUpdateException;
          _calElementSizeP2(localILayoutElement, paramInt3 + i10, i9 + (paramInt4 + k), paramInt1 - i10 - i6, paramInt2 - k - i9 - i7);
          localILayoutElement.doFinal();
          break;
          label346: i2 = _judgeMargin(i2, i7);
        }
        label358: j = i10 + i11;
        int i12 = i9 + localILayoutElement.getHeight();
        if (m < i12)
          m = i12;
      }
    }
  }

  private static void _calChildrenSizeP2_HorizontalLinear(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    ItemsInfo localItemsInfo = null;
    int m = 0;
    while (m < i)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(m);
      if (localILayoutElement == null)
      {
        m++;
      }
      else
      {
        Rect localRect = localILayoutElement.getMargin();
        int n = _judgeMargin(k, localRect.left);
        int i1 = localRect.top;
        int i2 = localRect.right;
        int i3 = localRect.bottom;
        int i4 = j + n;
        int i5 = localILayoutElement.getWidth();
        int i6 = i2;
        int i7 = 1;
        label168: int i8;
        if (i5 == 2147483644)
        {
          if (localItemsInfo == null)
            localItemsInfo = _rangedCalChildrenSize_HLinear(paramILayoutContainer, m + 1, i, i2);
          i6 = localItemsInfo.size;
          i7 += localItemsInfo.countMatchParent;
          localItemsInfo.countMatchParent = (-1 + localItemsInfo.countMatchParent);
          i8 = (paramInt1 - i6 - i4) / i7;
          if (!localILayoutElement.changed())
            break label326;
          if (!(localILayoutElement instanceof ILayoutContainer))
            break label292;
          _calContainerSizeP2((ILayoutContainer)localILayoutElement, paramInt3 + i4, paramInt4 + i1, i8, paramInt2 - i1 - i3);
          _calContainerSizeP3((ILayoutContainer)localILayoutElement);
        }
        while (true)
        {
          j = i4 + localILayoutElement.getWidth();
          k = localRect.right;
          break;
          if (localItemsInfo == null)
            break label168;
          i6 = localItemsInfo.size;
          if (i5 >= 2147483547)
            break label168;
          localItemsInfo.size -= n + i5;
          break label168;
          label292: _calElementSizeP2(localILayoutElement, paramInt3 + i4, paramInt4 + i1, i8, paramInt2 - i1 - i3);
          localILayoutElement.doFinal();
          continue;
          label326: _rebaseAndCheckUpdateMatchParent(localILayoutElement, paramInt3 + i4, paramInt4 + i1, i8, paramInt2 - i1 - i3);
        }
      }
    }
  }

  private static void _calChildrenSizeP2_InverseHorizontalGrid(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    if (i3 < i)
    {
      ILayoutElement localILayoutElement2 = paramILayoutContainer.getVisibleChild(i3);
      if (localILayoutElement2 == null);
      while (true)
      {
        i3++;
        break;
        Rect localRect2 = localILayoutElement2.getMargin();
        int i9 = localRect2.left;
        int i10 = localRect2.top;
        int i11 = localRect2.right;
        int i12 = localRect2.bottom;
        int i13 = _judgeMargin(n, i9);
        int i14 = _judgeMargin(i1, i10);
        int i15 = j + i13;
        boolean bool = localILayoutElement2.changed();
        int i16;
        if (bool)
        {
          if ((localILayoutElement2 instanceof ILayoutContainer))
          {
            _calContainerSizeP2((ILayoutContainer)localILayoutElement2, paramInt3 + i15, i14 + (paramInt4 + k), paramInt1 - i15, paramInt2 - k - i14);
            _calContainerSizeP3((ILayoutContainer)localILayoutElement2);
          }
        }
        else
        {
          i16 = localILayoutElement2.getWidth();
          if (i15 + i16 <= paramInt1)
            break label328;
          i15 = i9;
          k += m;
          m = 0;
          i1 = i2;
          i2 = i12;
          i14 = _judgeMargin(i1, i10);
        }
        while (true)
        {
          n = i11;
          _rebase(localILayoutElement2, paramInt3 + i15, i14 + (paramInt4 + k));
          if ((bool) || (!localILayoutElement2.checkUpdateMatchParent(paramInt1 - i15, paramInt2 - k - i14)))
            break label340;
          localILayoutElement2.prepare();
          throw matchParentUpdateException;
          _calElementSizeP2(localILayoutElement2, paramInt3 + i15, i14 + (paramInt4 + k), paramInt1 - i15, paramInt2 - k - i14);
          localILayoutElement2.doFinal();
          break;
          label328: i2 = _judgeMargin(i2, i12);
        }
        label340: j = i15 + i16;
        int i17 = i14 + localILayoutElement2.getHeight();
        if (m < i17)
          m = i17;
      }
    }
    _calContainerSizeP3(paramILayoutContainer);
    Rect localRect1 = paramILayoutContainer.getPadding();
    int i4;
    int i5;
    int i6;
    label407: ILayoutElement localILayoutElement1;
    if (localRect1 == null)
    {
      i4 = 0;
      i5 = paramILayoutContainer.getWidth() - i4;
      i6 = 0;
      if (i6 >= i)
        return;
      localILayoutElement1 = paramILayoutContainer.getVisibleChild(i6);
      if (localILayoutElement1 != null)
        break label445;
    }
    while (true)
    {
      i6++;
      break label407;
      i4 = localRect1.right;
      break;
      label445: int i7 = localILayoutElement1.getBaseX();
      int i8 = localILayoutElement1.getWidth();
      _offsetBase(localILayoutElement1, i5 - 2 * (i7 - paramInt3) - i8, 0);
    }
  }

  private static void _calChildrenSizeP2_InverseHorizontalLinear(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    int m = 0;
    while (m < i)
    {
      ILayoutElement localILayoutElement2 = paramILayoutContainer.getVisibleChild(m);
      if (localILayoutElement2 == null)
      {
        m++;
      }
      else
      {
        Rect localRect3 = localILayoutElement2.getMargin();
        int i3 = _judgeMargin(k, localRect3.right);
        int i4 = localRect3.top;
        int i5 = localRect3.bottom;
        int i6 = localRect3.right;
        int i7 = j + i3;
        if (localILayoutElement2.changed())
          if ((localILayoutElement2 instanceof ILayoutContainer))
          {
            _calContainerSizeP2((ILayoutContainer)localILayoutElement2, paramInt3 + i7, paramInt4 + i4, paramInt1 - i7 - i6, paramInt2 - i4 - i5);
            _calContainerSizeP3((ILayoutContainer)localILayoutElement2);
          }
        while (true)
        {
          j = i7 + localILayoutElement2.getWidth();
          k = localRect3.left;
          break;
          _calElementSizeP2(localILayoutElement2, paramInt3 + i7, paramInt4 + i4, paramInt1 - i7 - i6, paramInt2 - i4 - i5);
          localILayoutElement2.doFinal();
          continue;
          _rebaseAndCheckUpdateMatchParent(localILayoutElement2, paramInt3 + i7, paramInt4 + i4, paramInt1 - i7 - i6, paramInt2 - i4 - i5);
        }
      }
    }
    _calContainerSizeP3(paramILayoutContainer);
    Rect localRect1 = paramILayoutContainer.getPadding();
    int n = paramILayoutContainer.getViewRight() + paramILayoutContainer.getOffsetX() - localRect1.right;
    int i1 = 0;
    int i2 = 0;
    if (i2 < i)
    {
      ILayoutElement localILayoutElement1 = paramILayoutContainer.getVisibleChild(i2);
      if (localILayoutElement1 == null);
      while (true)
      {
        i2++;
        break;
        Rect localRect2 = localILayoutElement1.getMargin();
        n = n - _judgeMargin(i1, localRect2.right) - localILayoutElement1.getRight();
        _offsetBase(localILayoutElement1, n - localILayoutElement1.getBaseX(), 0);
        i1 = localRect2.left;
      }
    }
  }

  private static void _calChildrenSizeP2_InverseVerticalLinear(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    int m = 0;
    while (m < i)
    {
      ILayoutElement localILayoutElement2 = paramILayoutContainer.getVisibleChild(m);
      if (localILayoutElement2 == null)
      {
        m++;
      }
      else
      {
        Rect localRect2 = localILayoutElement2.getMargin();
        int i2 = _judgeMargin(k, localRect2.bottom);
        int i3 = localRect2.left;
        int i4 = localRect2.right;
        int i5 = localRect2.bottom;
        int i6 = j + i2;
        if (localILayoutElement2.changed())
          if ((localILayoutElement2 instanceof ILayoutContainer))
          {
            _calContainerSizeP2((ILayoutContainer)localILayoutElement2, paramInt3 + i3, paramInt4 + i6, paramInt1 - i3 - i4, paramInt2 - i6 - i5);
            _calContainerSizeP3((ILayoutContainer)localILayoutElement2);
          }
        while (true)
        {
          j = i6 + localILayoutElement2.getHeight();
          k = localRect2.top;
          break;
          _calElementSizeP2(localILayoutElement2, paramInt3 + i3, paramInt4 + i6, paramInt1 - i3 - i4, paramInt2 - i6 - i5);
          localILayoutElement2.doFinal();
          continue;
          _rebaseAndCheckUpdateMatchParent(localILayoutElement2, paramInt3 + i3, paramInt4 + i6, paramInt1 - i3 - i4, paramInt2 - i6 - i5);
        }
      }
    }
    _calContainerSizeP3(paramILayoutContainer);
    Rect localRect1 = paramILayoutContainer.getPadding();
    int n = paramILayoutContainer.getViewBottom() + paramILayoutContainer.getOffsetY() - localRect1.bottom;
    int i1 = 0;
    if (i1 < i)
    {
      ILayoutElement localILayoutElement1 = paramILayoutContainer.getVisibleChild(i1);
      if (localILayoutElement1 == null);
      while (true)
      {
        i1++;
        break;
        n = n - _judgeMargin(0, localILayoutElement1.getMargin().bottom) - localILayoutElement1.getBottom();
        _offsetBase(localILayoutElement1, 0, n - localILayoutElement1.getBaseY());
      }
    }
  }

  private static void _calChildrenSizeP2_Normal(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    if (j < i)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(j);
      if (localILayoutElement == null);
      while (true)
      {
        j++;
        break;
        if (localILayoutElement.changed())
        {
          if ((localILayoutElement instanceof ILayoutContainer))
          {
            _calContainerSizeP2((ILayoutContainer)localILayoutElement, paramInt3, paramInt4, paramInt1, paramInt2);
            _calContainerSizeP3((ILayoutContainer)localILayoutElement);
          }
          else
          {
            _calElementSizeP2(localILayoutElement, paramInt3, paramInt4, paramInt1, paramInt2);
            localILayoutElement.doFinal();
          }
        }
        else
          _rebaseAndCheckUpdateMatchParent(localILayoutElement, paramInt3, paramInt4, paramInt1, paramInt2);
      }
    }
  }

  private static void _calChildrenSizeP2_VerticalGrid(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    if (i3 < i)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(i3);
      if (localILayoutElement == null);
      while (true)
      {
        i3++;
        break;
        Rect localRect = localILayoutElement.getMargin();
        int i4 = localRect.left;
        int i5 = localRect.top;
        int i6 = localRect.right;
        int i7 = localRect.bottom;
        int i8 = _judgeMargin(n, i4);
        int i9 = k + _judgeMargin(i2, i5);
        boolean bool = localILayoutElement.changed();
        int i10;
        if (bool)
        {
          if ((localILayoutElement instanceof ILayoutContainer))
          {
            _calContainerSizeP2((ILayoutContainer)localILayoutElement, i8 + (paramInt3 + j), paramInt4 + i9, paramInt1 - j - i8 - i6, paramInt2 - i9 - i7);
            _calContainerSizeP3((ILayoutContainer)localILayoutElement);
          }
        }
        else
        {
          i10 = localILayoutElement.getHeight();
          if (i9 + i10 <= paramInt2)
            break label342;
          i9 = i5;
          j += m;
          m = 0;
          n = i1;
          i1 = i6;
          i8 = _judgeMargin(n, i4);
        }
        while (true)
        {
          i2 = i7;
          _rebase(localILayoutElement, i8 + (paramInt3 + j), paramInt4 + i9);
          if ((bool) || (!localILayoutElement.checkUpdateMatchParent(paramInt1 - j - i8 - i6, paramInt2 - i9 - i7)))
            break label354;
          localILayoutElement.prepare();
          throw matchParentUpdateException;
          _calElementSizeP2(localILayoutElement, i8 + (paramInt3 + j), paramInt4 + i9, paramInt1 - j - i8 - i6, paramInt2 - i9 - i7);
          localILayoutElement.doFinal();
          break;
          label342: i1 = _judgeMargin(i1, i6);
        }
        label354: k = i9 + i10;
        int i11 = i8 + localILayoutElement.getWidth();
        if (m < i11)
          m = i11;
      }
    }
  }

  private static void _calChildrenSizeP2_VerticalLinear(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    int k = 0;
    ItemsInfo localItemsInfo = null;
    int m = 0;
    while (m < i)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(m);
      if (localILayoutElement == null)
      {
        m++;
      }
      else
      {
        Rect localRect = localILayoutElement.getMargin();
        int n = _judgeMargin(k, localRect.top);
        int i1 = localRect.left;
        int i2 = localRect.right;
        int i3 = localRect.bottom;
        int i4 = j + n;
        int i5 = localILayoutElement.getHeight();
        int i6 = i3;
        int i7 = 1;
        label168: int i8;
        if (i5 == 2147483644)
        {
          if (localItemsInfo == null)
            localItemsInfo = _rangedCalChildrenSize_VLinear(paramILayoutContainer, m + 1, i, i3);
          i6 = localItemsInfo.size;
          i7 += localItemsInfo.countMatchParent;
          localItemsInfo.countMatchParent = (-1 + localItemsInfo.countMatchParent);
          i8 = (paramInt2 - i6 - i4) / i7;
          if (!localILayoutElement.changed())
            break label326;
          if (!(localILayoutElement instanceof ILayoutContainer))
            break label292;
          _calContainerSizeP2((ILayoutContainer)localILayoutElement, paramInt3 + i1, paramInt4 + i4, paramInt1 - i1 - i2, i8);
          _calContainerSizeP3((ILayoutContainer)localILayoutElement);
        }
        while (true)
        {
          j = i4 + localILayoutElement.getHeight();
          k = localRect.bottom;
          break;
          if (localItemsInfo == null)
            break label168;
          i6 = localItemsInfo.size;
          if (i5 >= 2147483547)
            break label168;
          localItemsInfo.size -= n + i5;
          break label168;
          label292: _calElementSizeP2(localILayoutElement, paramInt3 + i1, paramInt4 + i4, paramInt1 - i1 - i2, i8);
          localILayoutElement.doFinal();
          continue;
          label326: _rebaseAndCheckUpdateMatchParent(localILayoutElement, paramInt3 + i1, paramInt4 + i4, paramInt1 - i1 - i2, i8);
        }
      }
    }
  }

  private static void _calContainerSizeP1(ILayoutContainer paramILayoutContainer)
  {
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    if (j < i)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(j);
      if (localILayoutElement == null);
      while (true)
      {
        j++;
        break;
        if (localILayoutElement.changed())
          if ((localILayoutElement instanceof ILayoutContainer))
            _calContainerSizeP1((ILayoutContainer)localILayoutElement);
          else
            _calElementSizeP1(localILayoutElement);
      }
    }
  }

  private static void _calContainerSizeP2(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    _rebase(paramILayoutContainer, paramInt1, paramInt2);
    Rect localRect = paramILayoutContainer.getPadding();
    int i = localRect.left + localRect.right;
    int j = localRect.top + localRect.bottom;
    int k = paramILayoutContainer.getOffsetX();
    int m = paramILayoutContainer.getOffsetY();
    int n = k + (paramInt1 + paramILayoutContainer.getLeft() + localRect.left);
    int i1 = m + (paramInt2 + paramILayoutContainer.getTop() + localRect.top);
    int i2 = paramILayoutContainer.getWidth();
    int i4;
    label139: int i7;
    switch (i2)
    {
    default:
      i4 = i2 - i;
      int i5 = paramILayoutContainer.getHeight();
      switch (i5)
      {
      default:
        i7 = i5 - j;
      case 2147483645:
      case 2147483646:
      case 2147483644:
      }
      break;
    case 2147483645:
    case 2147483646:
    case 2147483644:
    }
    int i8;
    int i9;
    while (true)
    {
      i8 = i4 - k;
      i9 = i7 - m;
      switch (paramILayoutContainer.getLayoutMode())
      {
      default:
        _calChildrenSizeP2_Normal(paramILayoutContainer, i8, i9, n, i1);
        return;
        i4 = paramInt3 - (i + paramILayoutContainer.getLeft());
        break label139;
        int i3 = paramInt3 - paramILayoutContainer.getLeft();
        paramILayoutContainer.setWidth(i3);
        i4 = i3 - i;
        break label139;
        i7 = paramInt4 - (j + paramILayoutContainer.getTop());
        continue;
        int i6 = paramInt4 - paramILayoutContainer.getTop();
        paramILayoutContainer.setHeight(i6);
        i7 = i6 - j;
      case 1:
      case 2:
      case 16777217:
      case 16777218:
      case 4:
      case 8:
      case 16777220:
      }
    }
    _calChildrenSizeP2_HorizontalLinear(paramILayoutContainer, i8, i9, n, i1);
    return;
    _calChildrenSizeP2_VerticalLinear(paramILayoutContainer, i8, i9, n, i1);
    return;
    _calChildrenSizeP2_InverseHorizontalLinear(paramILayoutContainer, i8, i9, n, i1);
    return;
    _calChildrenSizeP2_InverseVerticalLinear(paramILayoutContainer, i8, i9, n, i1);
    return;
    _calChildrenSizeP2_HorizontalGrid(paramILayoutContainer, i8, i9, n, i1);
    return;
    _calChildrenSizeP2_VerticalGrid(paramILayoutContainer, i8, i9, n, i1);
    return;
    _calChildrenSizeP2_InverseHorizontalGrid(paramILayoutContainer, i8, i9, n, i1);
  }

  private static void _calContainerSizeP3(ILayoutContainer paramILayoutContainer)
  {
    Rect localRect1 = paramILayoutContainer.getPadding();
    int i = paramILayoutContainer.getChildNum();
    int j = 2147483547;
    int k = 2147483547;
    int m = paramILayoutContainer.getWidth();
    int n = 0;
    int i2;
    label92: int i3;
    label120: int i4;
    label123: ILayoutElement localILayoutElement;
    switch (m)
    {
    default:
      int i1 = paramILayoutContainer.getHeight();
      i2 = 0;
      switch (i1)
      {
      default:
        if (paramILayoutContainer.updateContentSize())
        {
          j = -2147483648;
          k = -2147483648;
        }
        if (paramILayoutContainer.getLayoutMode() == 0)
        {
          i3 = 1;
          i4 = 0;
          if (i4 >= i)
            break label346;
          localILayoutElement = paramILayoutContainer.getVisibleChild(i4);
          if (localILayoutElement != null)
            break label175;
        }
        break;
      case 2147483645:
      case 2147483646:
      }
      break;
    case 2147483645:
    case 2147483646:
    }
    while (true)
    {
      i4++;
      break label123;
      j = -2147483648;
      n = 1;
      break;
      k = -2147483648;
      i2 = 1;
      break label92;
      i3 = 0;
      break label120;
      label175: if (localILayoutElement.changed())
      {
        if (!(localILayoutElement instanceof ILayoutContainer))
          break label260;
        _calContainerSizeP3((ILayoutContainer)localILayoutElement);
      }
      while (true)
      {
        if (i3 == 0)
          break label271;
        if (j < 2147483547)
        {
          int i16 = localILayoutElement.getViewRight();
          if (i16 > j)
            j = i16;
        }
        if (k >= 2147483547)
          break;
        int i15 = localILayoutElement.getViewBottom();
        if (i15 <= k)
          break;
        k = i15;
        break;
        label260: localILayoutElement.doFinal();
      }
      label271: Rect localRect2 = localILayoutElement.getMargin();
      if (j < 2147483547)
      {
        int i14 = localILayoutElement.getViewRight() + localRect2.right;
        if (i14 > j)
          j = i14;
      }
      if (k < 2147483547)
      {
        int i13 = localILayoutElement.getViewBottom() + localRect2.bottom;
        if (i13 > k)
          k = i13;
      }
    }
    label346: if (n != 0)
    {
      if (j > -2147483648)
        paramILayoutContainer.setWidth(j - paramILayoutContainer.getLeft() - paramILayoutContainer.getBaseX() + localRect1.right);
    }
    else if (i2 != 0)
    {
      if (k <= -2147483648)
        break label720;
      paramILayoutContainer.setHeight(k - paramILayoutContainer.getTop() - paramILayoutContainer.getBaseY() + localRect1.bottom);
    }
    while (true)
    {
      if ((j > -2147483648) && (k > -2147483648) && (paramILayoutContainer.updateContentSize()))
      {
        int i5 = j - paramILayoutContainer.getLeft() - paramILayoutContainer.getBaseX() - localRect1.left - paramILayoutContainer.getOffsetX();
        int i6 = k - paramILayoutContainer.getTop() - paramILayoutContainer.getBaseY() - localRect1.top - paramILayoutContainer.getOffsetY();
        paramILayoutContainer.setContentSize(i5, i6);
        float f1 = paramILayoutContainer.getAlignmentX();
        float f2 = paramILayoutContainer.getAlignmentY();
        boolean bool1 = Float.isNaN(f1);
        int i7 = 0;
        if (!bool1)
        {
          int i12 = paramILayoutContainer.getWidth() - localRect1.left - localRect1.right;
          i7 = 0;
          if (i12 > i5)
            i7 = XulUtils.roundToInt(f1 * (i12 - i5));
        }
        boolean bool2 = Float.isNaN(f2);
        int i8 = 0;
        if (!bool2)
        {
          int i11 = paramILayoutContainer.getHeight() - localRect1.top - localRect1.bottom;
          i8 = 0;
          if (i11 > i6)
            i8 = XulUtils.roundToInt(f2 * (i11 - i6));
        }
        int i9 = paramILayoutContainer.getAlignmentOffsetY();
        int i10 = paramILayoutContainer.getAlignmentOffsetX();
        if ((i8 != i9) || (i7 != i10))
        {
          offsetChild(paramILayoutContainer, i7 - i10, i8 - i9);
          paramILayoutContainer.setAlignmentOffset(i7, i8);
        }
      }
      paramILayoutContainer.doFinal();
      return;
      paramILayoutContainer.setWidth(0);
      break;
      label720: paramILayoutContainer.setHeight(0);
    }
  }

  private static void _calElementSizeP1(ILayoutElement paramILayoutElement)
  {
    Rect localRect = paramILayoutElement.getPadding();
    switch (paramILayoutElement.getWidth())
    {
    default:
    case 2147483645:
    case 2147483646:
    }
    while (true)
      switch (paramILayoutElement.getHeight())
      {
      default:
        return;
        paramILayoutElement.setWidth(paramILayoutElement.getContentWidth() + localRect.left + localRect.right);
      case 2147483645:
      case 2147483646:
      }
    paramILayoutElement.setHeight(paramILayoutElement.getContentHeight() + localRect.top + localRect.bottom);
  }

  private static void _calElementSizeP2(ILayoutElement paramILayoutElement, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramILayoutElement.setBase(paramInt1, paramInt2);
    switch (paramILayoutElement.getWidth())
    {
    default:
    case 2147483644:
    }
    while (true)
      switch (paramILayoutElement.getHeight())
      {
      default:
        return;
        paramILayoutElement.setWidth(paramInt3 - paramILayoutElement.getLeft());
      case 2147483644:
      }
    paramILayoutElement.setHeight(paramInt4 - paramILayoutElement.getTop());
  }

  private static int _judgeMargin(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2)
      return paramInt2;
    return paramInt1;
  }

  private static void _offsetBase(ILayoutElement paramILayoutElement, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0));
    do
    {
      return;
      paramILayoutElement.offsetBase(paramInt1, paramInt2);
    }
    while (!(paramILayoutElement instanceof ILayoutContainer));
    _offsetChild((ILayoutContainer)paramILayoutElement, paramInt1, paramInt2);
  }

  private static void _offsetChild(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2)
  {
    assert (_tmpElementArray.isEmpty());
    paramILayoutContainer.getAllVisibleChildren(_tmpElementArray);
    int i = 0;
    for (int j = _tmpElementArray.size(); i < j; j = _tmpElementArray.size())
    {
      ILayoutElement[] arrayOfILayoutElement = (ILayoutElement[])_tmpElementArray.getArray();
      if (i < j)
      {
        ILayoutElement localILayoutElement = arrayOfILayoutElement[i];
        if (localILayoutElement == null);
        while (true)
        {
          i++;
          break;
          localILayoutElement.offsetBase(paramInt1, paramInt2);
          if ((localILayoutElement instanceof ILayoutContainer))
            ((ILayoutContainer)localILayoutElement).getAllVisibleChildren(_tmpElementArray);
        }
      }
    }
    _tmpElementArray.clear();
  }

  private static void _prepareContainer(ILayoutContainer paramILayoutContainer)
  {
    paramILayoutContainer.prepare();
    if (!paramILayoutContainer.isVisible())
    {
      paramILayoutContainer.doFinal();
      return;
    }
    int i = paramILayoutContainer.getChildNum();
    int j = 0;
    label33: ILayoutElement localILayoutElement;
    if (j < i)
    {
      localILayoutElement = paramILayoutContainer.getChild(j);
      if (localILayoutElement != null)
        break label58;
    }
    while (true)
    {
      j++;
      break label33;
      break;
      label58: if (localILayoutElement.changed())
        if ((localILayoutElement instanceof ILayoutContainer))
          _prepareContainer((ILayoutContainer)localILayoutElement);
        else
          _prepareElement(localILayoutElement);
    }
  }

  private static void _prepareElement(ILayoutElement paramILayoutElement)
  {
    paramILayoutElement.prepare();
    if (!paramILayoutElement.isVisible())
      paramILayoutElement.doFinal();
  }

  private static ItemsInfo _rangedCalChildrenSize_HLinear(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = paramInt1;
    while (m < paramInt2)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(m);
      if (localILayoutElement == null)
      {
        m++;
      }
      else
      {
        j++;
        Rect localRect = localILayoutElement.getMargin();
        i += _judgeMargin(paramInt3, localRect.left);
        int n = localILayoutElement.getWidth();
        if (n == 2147483644)
          k++;
        while (true)
        {
          paramInt3 = localRect.right;
          break;
          if (n < 2147483547)
            i += n;
        }
      }
    }
    return new ItemsInfo(j, k, i + paramInt3);
  }

  private static ItemsInfo _rangedCalChildrenSize_VLinear(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = paramInt1;
    while (m < paramInt2)
    {
      ILayoutElement localILayoutElement = paramILayoutContainer.getVisibleChild(m);
      if (localILayoutElement == null)
      {
        m++;
      }
      else
      {
        j++;
        Rect localRect = localILayoutElement.getMargin();
        i += _judgeMargin(paramInt3, localRect.top);
        int n = localILayoutElement.getHeight();
        if (n == 2147483644)
          k++;
        while (true)
        {
          paramInt3 = localRect.bottom;
          break;
          if (n < 2147483547)
            i += n;
        }
      }
    }
    return new ItemsInfo(j, k, i + paramInt3);
  }

  private static boolean _rebase(ILayoutElement paramILayoutElement, int paramInt1, int paramInt2)
  {
    int i = paramILayoutElement.getBaseX();
    int j = paramILayoutElement.getBaseY();
    if ((paramInt1 == i) && (paramInt2 == j))
      return false;
    paramILayoutElement.setBase(paramInt1, paramInt2);
    int k = paramInt1 - i;
    int m = paramInt2 - j;
    if ((paramILayoutElement instanceof ILayoutContainer))
      _offsetChild((ILayoutContainer)paramILayoutElement, k, m);
    return true;
  }

  private static void _rebaseAndCheckUpdateMatchParent(ILayoutElement paramILayoutElement, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws XulLayoutHelper.MatchParentUpdateException
  {
    _rebase(paramILayoutElement, paramInt1, paramInt2);
    if (paramILayoutElement.checkUpdateMatchParent(paramInt3, paramInt4))
    {
      paramILayoutElement.prepare();
      throw matchParentUpdateException;
    }
  }

  public static void offsetBase(ILayoutElement paramILayoutElement, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0))
      return;
    _offsetBase(paramILayoutElement, paramInt1, paramInt2);
  }

  public static void offsetChild(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0))
      return;
    _offsetChild(paramILayoutContainer, paramInt1, paramInt2);
  }

  public static void updateLayout(ILayoutContainer paramILayoutContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!paramILayoutContainer.changed());
    while (true)
    {
      return;
      _prepareContainer(paramILayoutContainer);
      if (paramILayoutContainer.isVisible())
        while (paramILayoutContainer.changed())
          try
          {
            _calContainerSizeP1(paramILayoutContainer);
            _calContainerSizeP2(paramILayoutContainer, paramInt1, paramInt2, paramInt3, paramInt4);
            _calContainerSizeP3(paramILayoutContainer);
          }
          catch (MatchParentUpdateException localMatchParentUpdateException)
          {
          }
    }
  }

  public static abstract interface ILayoutContainer extends XulLayoutHelper.ILayoutElement
  {
    public abstract int getAlignmentOffsetX();

    public abstract int getAlignmentOffsetY();

    public abstract float getAlignmentX();

    public abstract float getAlignmentY();

    public abstract int getAllVisibleChildren(XulSimpleArray<XulLayoutHelper.ILayoutElement> paramXulSimpleArray);

    public abstract XulLayoutHelper.ILayoutElement getChild(int paramInt);

    public abstract int getChildNum();

    public abstract int getContentOffsetX();

    public abstract int getContentOffsetY();

    public abstract int getLayoutMode();

    public abstract int getOffsetX();

    public abstract int getOffsetY();

    public abstract XulLayoutHelper.ILayoutElement getVisibleChild(int paramInt);

    public abstract int setAlignmentOffset(int paramInt1, int paramInt2);

    public abstract int setContentSize(int paramInt1, int paramInt2);

    public abstract boolean updateContentSize();
  }

  public static abstract interface ILayoutElement
  {
    public abstract boolean changed();

    public abstract boolean checkUpdateMatchParent(int paramInt1, int paramInt2);

    public abstract int doFinal();

    public abstract int getBaseX();

    public abstract int getBaseY();

    public abstract int getBottom();

    public abstract int getContentHeight();

    public abstract int getContentWidth();

    public abstract int getHeight();

    public abstract int getLeft();

    public abstract Rect getMargin();

    public abstract Rect getPadding();

    public abstract int getRight();

    public abstract int getTop();

    public abstract int getViewBottom();

    public abstract int getViewRight();

    public abstract int getWidth();

    public abstract boolean isVisible();

    public abstract boolean offsetBase(int paramInt1, int paramInt2);

    public abstract int prepare();

    public abstract boolean setBase(int paramInt1, int paramInt2);

    public abstract boolean setHeight(int paramInt);

    public abstract boolean setWidth(int paramInt);
  }

  private static class ItemsInfo
  {
    int count;
    int countMatchParent;
    int size;

    public ItemsInfo(int paramInt1, int paramInt2, int paramInt3)
    {
      this.count = paramInt1;
      this.countMatchParent = paramInt2;
      this.size = paramInt3;
    }
  }

  private static class LayoutElementArray extends XulSimpleArray<XulLayoutHelper.ILayoutElement>
  {
    public LayoutElementArray(int paramInt)
    {
      super();
    }

    protected XulLayoutHelper.ILayoutElement[] allocArrayBuf(int paramInt)
    {
      return new XulLayoutHelper.ILayoutElement[paramInt];
    }
  }

  private static class MatchParentUpdateException extends Exception
  {
    public MatchParentUpdateException()
    {
      super();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulLayoutHelper
 * JD-Core Version:    0.6.2
 */