package com.starcor.xul.Render;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Direction;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_Float;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_Integer;
import com.starcor.xul.Utils.XulSimpleArray;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulLayout.FocusDirection;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulTaskCollector;
import com.starcor.xul.XulTemplate;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

public class XulMassiveRender extends XulAreaRender
{
  private static final String TAG;
  private int _arrangement = 4;
  private float _cachePages = 0.5F;
  private float _clipRangeHeight = 0.0F;
  private float _clipRangeLeft = 0.0F;
  private float _clipRangeTop = 0.0F;
  private ClipRangeUpdater _clipRangeUpdater;
  private float _clipRangeWidth = 0.0F;
  private int _contentHeight = 0;
  private int _contentWidth = 0;
  ArrayList<_ItemData> _data;
  private int _firstItemIdxOffset = 0;
  private boolean _fixedItem = false;
  private boolean _initBinding = true;
  WeakHashMap<XulView, ArrayList<XulView>> _instanceCache = new WeakHashMap();
  private int _itemIdxOffset = 0;
  private float _itemOffsetX = 0.0F;
  private float _itemOffsetY = 0.0F;
  XulArea.XulAreaIterator _itemRecycler = new XulArea.XulAreaIterator()
  {
    public boolean onXulArea(int paramAnonymousInt, XulArea paramAnonymousXulArea)
    {
      XulMassiveRender.this.recycleItem(paramAnonymousXulArea);
      return super.onXulArea(paramAnonymousInt, paramAnonymousXulArea);
    }

    public boolean onXulItem(int paramAnonymousInt, XulItem paramAnonymousXulItem)
    {
      XulMassiveRender.this.recycleItem(paramAnonymousXulItem);
      return super.onXulItem(paramAnonymousInt, paramAnonymousXulItem);
    }
  };
  List<Object> _itemRecycler_list = new AbstractList()
  {
    static
    {
      if (!XulMassiveRender.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    public boolean add(Object paramAnonymousObject)
    {
      if ((paramAnonymousObject instanceof XulTemplate))
        return false;
      assert ((paramAnonymousObject instanceof XulView));
      XulMassiveRender.this.recycleItem((XulView)paramAnonymousObject);
      return true;
    }

    public Object get(int paramAnonymousInt)
    {
      return null;
    }

    public int size()
    {
      return 0;
    }
  };
  XulTemplate _itemTemplate = null;
  private int _lastItemIdxOffset = 0;
  private int _minItemNum = 16;
  _RebindViewList _rebindViews = new _RebindViewList(64);
  private ScrollAnimation _scrollAnimation = null;
  private float _scrollTargetX = 0.0F;
  private float _scrollTargetY = 0.0F;
  private float _scrollX = 0.0F;
  private float _scrollY = 0.0F;
  private float _skipItemOffsetX = 0.0F;
  private float _skipItemOffsetY = 0.0F;
  private boolean _updateItemSize = false;

  static
  {
    if (!XulMassiveRender.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      TAG = XulMassiveRender.class.getSimpleName();
      return;
    }
  }

  public XulMassiveRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
    this._area.eachChild(new XulArea.XulAreaIterator()
    {
      public boolean onXulTemplate(int paramAnonymousInt, XulTemplate paramAnonymousXulTemplate)
      {
        if ("@item-template".equals(paramAnonymousXulTemplate.getId()))
        {
          XulMassiveRender.this._itemTemplate = paramAnonymousXulTemplate;
          return false;
        }
        return true;
      }
    });
    this._area.removeAllChildren();
  }

  static void assert_true(boolean paramBoolean)
  {
    if (!paramBoolean)
      new Exception().printStackTrace();
  }

  private boolean createItemInstance(XulPage paramXulPage, int paramInt1, int paramInt2)
  {
    return createItemInstance(paramXulPage, paramInt1, paramInt2, false);
  }

  private boolean createItemInstance(XulPage paramXulPage, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    boolean bool = false;
    int i = this._area.getChildNum();
    int j = paramInt1;
    int k = Math.min(this._data.size(), paramInt2);
    if (j < k)
    {
      if ((j >= this._itemIdxOffset) && (j < i + this._itemIdxOffset));
      while (true)
      {
        j++;
        break;
        _ItemData local_ItemData = (_ItemData)this._data.get(j);
        ArrayList localArrayList1 = local_ItemData.dataArray;
        XulDataNode localXulDataNode = local_ItemData.data;
        if (local_ItemData.refView == null);
        for (Object localObject = null; ; localObject = (XulView)local_ItemData.refView.get())
        {
          if (localObject != null)
            break label158;
          localObject = this._itemTemplate.getItemTemplate(localXulDataNode, j, k);
          if (!(localObject instanceof XulTemplate))
            break label158;
          Log.w(TAG, "DO NOT use template as the item-template of massive render!");
          break;
        }
        label158: int m = j - paramInt1;
        XulView localXulView1 = (XulView)localObject;
        local_ItemData.refView = localXulView1.getWeakReference();
        ArrayList localArrayList2 = (ArrayList)this._instanceCache.get(localXulView1);
        bool = true;
        if ((localArrayList2 == null) || (localArrayList2.isEmpty()))
        {
          if ((localXulView1 instanceof XulItem))
          {
            XulItem localXulItem = ((XulItem)localXulView1).makeClone(this._area, m);
            localXulItem.prepareRender(getRenderContext(), true);
            localXulItem.setBindingCtx("[" + j + "]", localArrayList1);
            paramXulPage.addSelectorTarget(localXulItem);
            paramXulPage.applySelectors(localXulItem);
            this._rebindViews.add(localXulItem);
          }
          else if ((localXulView1 instanceof XulArea))
          {
            XulArea localXulArea = ((XulArea)localXulView1).makeClone(this._area, m);
            localXulArea.prepareRender(getRenderContext(), true);
            localXulArea.setBindingCtx("[" + j + "]", localArrayList1);
            paramXulPage.addSelectorTargets(localXulArea);
            paramXulPage.applySelectors(localXulArea);
            this._rebindViews.add(localXulArea);
          }
        }
        else
        {
          XulView localXulView2 = (XulView)localArrayList2.remove(-1 + localArrayList2.size());
          localXulView2.cleanImageItems();
          this._area.addChild(m, localXulView2);
          localXulView2.cleanBindingCtx();
          localXulView2.setBindingCtx("[" + j + "]", localArrayList1);
          this._rebindViews.add(localXulView2);
        }
      }
    }
    if (bool)
    {
      paramXulPage.rebindViews((XulView[])this._rebindViews.getArray(), this._rebindViews.size(), getRenderContext().getDefaultActionCallback());
      XulAction localXulAction = this._view.getAction(XulPropNameCache.TagId.ACTION_MASSIVE_UPDATED);
      if ((localXulAction != null) && (!paramBoolean))
        XulPage.invokeActionNoPopup(this._view, localXulAction);
    }
    this._rebindViews.clear();
    return bool;
  }

  private void fixItemSize()
  {
    if (!this._fixedItem);
    while ((this._data == null) || (this._data.isEmpty()) || (this._area.getChildNum() == 0) || ((!this._updateItemSize) && (((_ItemData)this._data.get(this._firstItemIdxOffset)).isInitialized())))
      return;
    this._updateItemSize = false;
    this._contentWidth = 0;
    this._contentHeight = 0;
    XulViewRender localXulViewRender = this._area.getFirstChild().getRender();
    RectF localRectF = localXulViewRender.getFocusRect();
    Rect localRect1 = localXulViewRender.getLayoutElement().getMargin();
    Rect localRect2 = this._padding;
    int i = localRect1.left + localRect2.left;
    int j = localRect1.top + localRect2.top;
    int k = localRect1.right;
    int m = localRect1.bottom;
    float f1 = XulUtils.calRectWidth(localRectF);
    float f2 = XulUtils.calRectHeight(localRectF);
    float f3 = f1 + Math.max(localRect1.left, localRect1.right);
    float f4 = f2 + Math.max(localRect1.top, localRect1.bottom);
    int n = getWidth() - localRect2.right;
    int i1 = getHeight() - localRect2.bottom;
    int i2 = i;
    int i3 = j;
    int i4 = 0;
    int i5 = this._data.size();
    if (i4 < i5)
    {
      _ItemData local_ItemData = (_ItemData)this._data.get(i4);
      if (this._arrangement == 8)
      {
        if (f2 + i3 > i1)
        {
          i3 = j;
          i2 = (int)(f3 + i2);
        }
        label291: local_ItemData.marginLeft = localRect1.left;
        local_ItemData.marginTop = localRect1.top;
        local_ItemData.marginRight = localRect1.right;
        local_ItemData.marginBottom = localRect1.bottom;
        local_ItemData.relativeTop = i3;
        local_ItemData.relativeLeft = i2;
        local_ItemData.width = f1;
        local_ItemData.height = f2;
        if ((this._arrangement != 2) && (this._arrangement != 8))
          break label467;
        i3 = (int)(f4 + i3);
        int i6 = XulUtils.roundToInt(f1 + i2);
        if (this._contentWidth < i6)
          this._contentWidth = i6;
        if (this._contentHeight < i3)
          this._contentHeight = i3;
      }
      while (true)
      {
        i4++;
        break;
        if ((this._arrangement != 4) || (f1 + i2 <= n))
          break label291;
        i2 = i;
        i3 = (int)(f4 + i3);
        break label291;
        label467: if ((this._arrangement == 1) || (this._arrangement == 4))
        {
          i2 = (int)(f3 + i2);
          if (this._contentWidth < i2)
            this._contentWidth = i2;
          int i7 = XulUtils.roundToInt(f2 + i3);
          if (this._contentHeight < i7)
            this._contentHeight = i7;
        }
      }
    }
    this._contentWidth = (k + this._contentWidth);
    this._contentHeight = (m + this._contentHeight);
  }

  private ScrollAnimation getScrollAnimation()
  {
    if (this._scrollAnimation == null)
      this._scrollAnimation = new ScrollAnimation(null);
    return this._scrollAnimation;
  }

  private void prepareAttr()
  {
    if (!_isViewChanged())
      return;
    String str = this._area.getAttrString(XulPropNameCache.TagId.ARRANGEMENT);
    XulAttr localXulAttr1 = this._area.getAttr(XulPropNameCache.TagId.DIRECTION);
    XulAttr localXulAttr2 = this._area.getAttr(XulPropNameCache.TagId.MINIMUM_ITEM);
    XulAttr localXulAttr3 = this._area.getAttr(XulPropNameCache.TagId.CACHE_PAGES);
    boolean bool = true;
    if (localXulAttr1 != null)
      bool = ((XulPropParser.xulParsedAttr_Direction)localXulAttr1.getParsedValue()).vertical;
    int i = 4;
    if ("grid".equals(str))
      if (bool)
      {
        i = 8;
        this._fixedItem = false;
      }
    while (true)
      label99: if (this._arrangement != i)
      {
        this._arrangement = i;
        if ((this._data != null) && (!this._data.isEmpty()))
        {
          this._updateItemSize = true;
          int k = 0;
          int m = this._data.size();
          while (true)
            if (k < m)
            {
              ((_ItemData)this._data.get(k)).reset();
              k++;
              continue;
              i = 4;
              break;
              if ("grid-fixed".equals(str))
              {
                if (bool);
                for (i = 8; ; i = 4)
                {
                  this._fixedItem = true;
                  break;
                }
              }
              if ("linear".equals(str))
              {
                if (bool);
                for (i = 2; ; i = 1)
                {
                  this._fixedItem = false;
                  break;
                }
              }
              if (!"linear-fixed".equals(str))
                break label99;
              if (bool);
              for (i = 2; ; i = 1)
              {
                this._fixedItem = true;
                break;
              }
            }
        }
      }
    int j;
    if (localXulAttr2 == null)
    {
      j = 16;
      this._minItemNum = j;
      if (localXulAttr3 != null)
        break label329;
    }
    label329: for (float f = 0.5F; ; f = Math.max(((XulPropParser.xulParsedProp_Float)localXulAttr3.getParsedValue()).val, 0.5F))
    {
      this._cachePages = f;
      return;
      j = Math.max(((XulPropParser.xulParsedProp_Integer)localXulAttr2.getParsedValue()).val, 16);
      break;
    }
  }

  private void prepareBinding()
  {
    if (this._itemTemplate == null);
    label7: XulDataNode localXulDataNode2;
    do
    {
      XulDataNode localXulDataNode1;
      do
      {
        ArrayList localArrayList;
        do
        {
          break label7;
          do
            return;
          while (!this._initBinding);
          this._initBinding = false;
          this._area.eachChild(this._itemRecycler);
          this._area.removeAllChildren();
          localArrayList = this._area.getBindingData();
        }
        while ((localArrayList == null) || (localArrayList.isEmpty()));
        localXulDataNode1 = (XulDataNode)localArrayList.get(0);
      }
      while (localXulDataNode1 == null);
      localXulDataNode2 = localXulDataNode1.getFirstChild();
    }
    while (localXulDataNode2 == null);
    this._data = new ArrayList(localXulDataNode2.size());
    while (localXulDataNode2 != null)
    {
      _ItemData local_ItemData = new _ItemData(null);
      local_ItemData.setData(localXulDataNode2);
      this._data.add(local_ItemData);
      localXulDataNode2 = localXulDataNode2.getNext();
    }
    createItemInstance(this._area.getOwnerPage(), 0, this._minItemNum);
    reset();
  }

  private void recycleItem(XulView paramXulView)
  {
    WeakReference localWeakReference = paramXulView.getRefView();
    assert (localWeakReference != null);
    XulView localXulView = (XulView)localWeakReference.get();
    if (localXulView == null)
      Log.d(TAG, "recycle failed! invalid ref view.");
    do
    {
      return;
      ArrayList localArrayList = (ArrayList)this._instanceCache.get(localXulView);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList();
        this._instanceCache.put(localXulView, localArrayList);
      }
      localArrayList.add(paramXulView);
    }
    while (!paramXulView.hasFocus());
    paramXulView.getRootLayout().killFocus();
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "massive", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulMassiveRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulMassiveRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
  }

  private void syncClipRange()
  {
    RectF localRectF1 = getFocusRect();
    if (localRectF1 == null);
    label77: float f5;
    float f6;
    float f7;
    float f8;
    do
    {
      return;
      XulArea localXulArea = this._area.getParent();
      float f1 = 0.0F;
      float f2 = 0.0F;
      float f3 = 3.4028235E+38F;
      float f4 = 3.4028235E+38F;
      if (localXulArea != null)
      {
        XulViewRender localXulViewRender = localXulArea.getRender();
        if ((localXulViewRender instanceof XulSliderAreaRender))
          if (((XulSliderAreaRender)localXulViewRender)._clipChildren)
            break label77;
        while (true)
        {
          localXulArea = localXulArea.getParent();
          break;
          if ((localXulArea instanceof XulLayout))
          {
            RectF localRectF2 = localXulViewRender.getFocusRect();
            f1 = Math.max(localRectF2.left, f1);
            f2 = Math.max(localRectF2.top, f2);
            f3 = Math.min(localRectF2.right, f3);
            f4 = Math.min(localRectF2.bottom, f4);
          }
        }
      }
      f5 = f1 - localRectF1.left;
      f6 = f2 - localRectF1.top;
      f7 = f3 - f1;
      f8 = f4 - f2;
    }
    while ((f5 == this._clipRangeLeft) && (f6 == this._clipRangeTop) && (f7 == this._clipRangeWidth) && (f8 == this._clipRangeHeight));
    if (this._clipRangeUpdater == null)
      this._clipRangeUpdater = new ClipRangeUpdater(null);
    this._clipRangeUpdater.doUpdate(f5, f6, f7, f8);
  }

  private void syncItemInstance()
  {
    if (this._data == null);
    int i;
    int j;
    do
    {
      do
      {
        return;
        if (_isLayoutChanged())
          getRenderContext().doLayout();
        i = this._area.getChildNum();
        if (i != 0)
          break;
      }
      while (this._data.isEmpty());
      if ((this._arrangement != 8) && (this._arrangement != 1));
      for (int i16 = 1; ((i16 != 0) && (this._clipRangeTop <= 0.0F)) || ((i16 == 0) && (this._clipRangeLeft <= 0.0F)); i16 = 0)
      {
        createItemInstance(this._area.getOwnerPage(), 0, this._minItemNum);
        reset();
        return;
      }
      if (!this._fixedItem)
        break;
      this._updateItemSize = true;
      createItemInstance(this._area.getOwnerPage(), 0, 1, true);
      fixItemSize();
      this._area.eachChild(this._itemRecycler);
      this._area.removeAllChildren();
      j = this._data.size();
      if (j != 0)
        break label251;
    }
    while (this._area.getChildNum() <= 0);
    this._initBinding = false;
    this._area.eachChild(this._itemRecycler);
    this._area.removeAllChildren();
    reset();
    return;
    createItemInstance(this._area.getOwnerPage(), 0, this._minItemNum);
    reset();
    Log.w(TAG, "non-fixed massive container must layout from very begining.");
    return;
    label251: RectF localRectF1 = this._area.getFocusRc();
    float f1 = localRectF1.left - this._screenX;
    float f2 = localRectF1.right - this._screenX;
    float f3 = localRectF1.top - this._screenY;
    float f4 = localRectF1.bottom - this._screenY;
    int k = 1;
    switch (this._arrangement)
    {
    default:
      if (k != 0)
      {
        if (f3 < this._clipRangeTop)
          f3 = (int)this._clipRangeTop;
        if (f4 > this._clipRangeTop + this._clipRangeHeight)
          f4 = (int)(this._clipRangeTop + this._clipRangeHeight);
      }
      break;
    case 2:
    case 4:
    case 16777218:
    case 16777220:
    case 1:
    case 8:
    case 16777217:
    }
    float f5;
    float f6;
    float f7;
    float f8;
    float f11;
    float f12;
    float f13;
    float f14;
    int m;
    int n;
    while (true)
    {
      f5 = f1;
      f6 = f3;
      f7 = f2;
      f8 = f4;
      float f9 = Math.min(this._clipRangeWidth, XulUtils.calRectWidth(localRectF1)) * this._cachePages;
      float f10 = Math.min(this._clipRangeHeight, XulUtils.calRectHeight(localRectF1)) * this._cachePages;
      f11 = f1 - f9;
      f12 = f2 + f9;
      f13 = f3 - f10;
      f14 = f4 + f10;
      m = -1 + (i + this._itemIdxOffset);
      n = this._itemIdxOffset;
      for (int i1 = 0; i1 < i; i1++)
      {
        _ItemData local_ItemData7 = (_ItemData)this._data.get(i1 + this._itemIdxOffset);
        if (!local_ItemData7.isInitialized())
        {
          RectF localRectF2 = this._area.getChild(i1).getFocusRc();
          local_ItemData7.relativeLeft = (localRectF2.left - this._itemOffsetX + this._skipItemOffsetX - this._screenX);
          local_ItemData7.relativeTop = (localRectF2.top - this._itemOffsetY + this._skipItemOffsetY - this._screenY);
          local_ItemData7.width = XulUtils.calRectWidth(localRectF2);
          local_ItemData7.height = XulUtils.calRectHeight(localRectF2);
        }
        float f33 = local_ItemData7.relativeLeft + this._scrollX - this._skipItemOffsetX;
        float f34 = local_ItemData7.relativeLeft + local_ItemData7.width + this._scrollX - this._skipItemOffsetX;
        float f35 = local_ItemData7.relativeTop + this._scrollY - this._skipItemOffsetY;
        float f36 = local_ItemData7.relativeTop + local_ItemData7.height + this._scrollY - this._skipItemOffsetY;
        if ((f33 <= f7) && (f35 < f8) && (f34 >= f5) && (f36 > f6))
        {
          if (m > i1 + this._itemIdxOffset)
            m = i1 + this._itemIdxOffset;
          int i15 = i1 + this._itemIdxOffset;
          if (n < i15)
            n = i1 + this._itemIdxOffset;
        }
      }
      k = 1;
      break;
      k = 0;
      break;
      if (f1 < this._clipRangeLeft)
        f1 = (int)this._clipRangeLeft;
      if (f2 > this._clipRangeLeft + this._clipRangeWidth)
        f2 = (int)(this._clipRangeLeft + this._clipRangeWidth);
    }
    int i2 = Math.min(j - 1, m);
    if ((n < i2) || (i2 < 0))
    {
      if (i2 < 0)
        i2 = 0;
      n = i2;
      for (int i3 = i2; ; i3++)
        if (i3 < j)
        {
          _ItemData local_ItemData6 = (_ItemData)this._data.get(i3);
          assert_true(local_ItemData6.isInitialized());
          float f29 = local_ItemData6.relativeLeft + this._scrollX - this._skipItemOffsetX;
          float f30 = local_ItemData6.relativeLeft + local_ItemData6.width + this._scrollX - this._skipItemOffsetX;
          float f31 = local_ItemData6.relativeTop + this._scrollY - this._skipItemOffsetY;
          float f32 = local_ItemData6.relativeTop + local_ItemData6.height + this._scrollY - this._skipItemOffsetY;
          if ((f29 <= f7) && (f31 < f8) && (f30 >= f5) && (f32 > f6))
          {
            i2 = i3;
            n = i3;
          }
        }
        else
        {
          for (int i4 = i2 + 1; i4 < j; i4++)
          {
            _ItemData local_ItemData5 = (_ItemData)this._data.get(i4);
            assert_true(local_ItemData5.isInitialized());
            float f25 = local_ItemData5.relativeLeft + this._scrollX - this._skipItemOffsetX;
            float f26 = local_ItemData5.relativeLeft + local_ItemData5.width + this._scrollX - this._skipItemOffsetX;
            float f27 = local_ItemData5.relativeTop + this._scrollY - this._skipItemOffsetY;
            float f28 = local_ItemData5.relativeTop + local_ItemData5.height + this._scrollY - this._skipItemOffsetY;
            if ((f25 > f7) || (f27 >= f8) || (f26 < f5) || (f28 <= f6))
              break;
            n = i4;
          }
        }
    }
    int i5 = i2;
    int i6 = n;
    for (int i7 = i5; i7 >= 0; i7--)
    {
      _ItemData local_ItemData4 = (_ItemData)this._data.get(i7);
      assert_true(local_ItemData4.isInitialized());
      float f21 = local_ItemData4.relativeLeft + this._scrollX - this._skipItemOffsetX;
      float f22 = local_ItemData4.relativeLeft + local_ItemData4.width + this._scrollX - this._skipItemOffsetX;
      float f23 = local_ItemData4.relativeTop + this._scrollY - this._skipItemOffsetY;
      float f24 = local_ItemData4.relativeTop + local_ItemData4.height + this._scrollY - this._skipItemOffsetY;
      if ((f21 <= f7) && (f23 < f8) && (f22 >= f5) && (f24 > f6) && (i2 > i7))
        i2 = i7;
      if ((f22 <= f11) || (f24 <= f13))
        break;
      if (i5 > i7)
        i5 = i7;
    }
    for (int i8 = i6; ; i8++)
    {
      _ItemData local_ItemData3;
      if (i8 < j)
      {
        local_ItemData3 = (_ItemData)this._data.get(i8);
        if (local_ItemData3.isInitialized())
          break label1817;
      }
      label1817: float f17;
      float f19;
      do
      {
        int i9 = Math.min(j - 1, i2);
        this._firstItemIdxOffset = i9;
        this._lastItemIdxOffset = n;
        int i10 = i9 + this._minItemNum;
        if (i6 < i10)
          i6 = i9 + this._minItemNum;
        if (i6 > j)
          i6 = j;
        if ((this._itemIdxOffset == i5) && (i6 - i5 == i))
          break;
        int i11 = this._itemIdxOffset;
        if (i5 != i11)
        {
          _ItemData local_ItemData1 = (_ItemData)this._data.get(i5);
          assert_true(local_ItemData1.isInitialized());
          _ItemData local_ItemData2 = (_ItemData)this._data.get(this._itemIdxOffset);
          float f15 = local_ItemData1.relativeLeft - local_ItemData2.relativeLeft;
          float f16 = local_ItemData1.relativeTop - local_ItemData2.relativeTop;
          this._scrollTargetX = (f15 + this._scrollTargetX);
          this._scrollX = (f15 + this._scrollX);
          this._skipItemOffsetX = (f15 + this._skipItemOffsetX);
          this._scrollTargetY = (f16 + this._scrollTargetY);
          this._scrollY = (f16 + this._scrollY);
          this._skipItemOffsetY = (f16 + this._skipItemOffsetY);
          if (!this._fixedItem)
          {
            this._contentWidth = ((int)(this._contentWidth - f15));
            this._contentHeight = ((int)(this._contentHeight - f16));
          }
        }
        int i12 = i6 - this._itemIdxOffset;
        int i13 = 0;
        if (i12 < i)
        {
          i13 = 1;
          this._area.removeChildren(i6 - this._itemIdxOffset, i, this._itemRecycler_list);
        }
        int i14 = this._itemIdxOffset;
        if (i5 > i14)
        {
          i13 = 1;
          this._area.removeChildren(0, i5 - this._itemIdxOffset, this._itemRecycler_list);
          this._itemIdxOffset = i5;
        }
        if (createItemInstance(this._area.getOwnerPage(), i5, i6))
        {
          i13 = 1;
          this._itemIdxOffset = i5;
        }
        if (i13 == 0)
          break;
        reset();
        return;
        f17 = local_ItemData3.relativeLeft + this._scrollX - this._skipItemOffsetX;
        float f18 = local_ItemData3.relativeLeft + local_ItemData3.width + this._scrollX - this._skipItemOffsetX;
        f19 = local_ItemData3.relativeTop + this._scrollY - this._skipItemOffsetY;
        float f20 = local_ItemData3.relativeTop + local_ItemData3.height + this._scrollY - this._skipItemOffsetY;
        if ((f17 <= f7) && (f19 < f8) && (f18 >= f5) && (f20 > f6) && (n < i8))
          n = i8;
      }
      while ((f17 > f12) || (f19 > f14));
      if (i6 < i8)
        i6 = i8;
    }
  }

  public void addDataItem(int paramInt, XulDataNode paramXulDataNode)
  {
    if (this._data == null)
      this._data = new ArrayList();
    this._updateItemSize = true;
    int i = this._data.size();
    if ((paramInt < 0) || (paramInt > i))
      paramInt = i;
    _ItemData local_ItemData1 = new _ItemData(null);
    local_ItemData1.setData(paramXulDataNode);
    this._data.add(paramInt, local_ItemData1);
    if (paramInt >= i)
      syncItemInstance();
    XulPage localXulPage;
    int i1;
    do
    {
      int k;
      int n;
      do
      {
        return;
        for (int j = paramInt; j < i; j++)
        {
          _ItemData local_ItemData2 = (_ItemData)this._data.get(j);
          _ItemData local_ItemData3 = (_ItemData)this._data.get(j + 1);
          local_ItemData2.width = local_ItemData3.width;
          local_ItemData2.height = local_ItemData3.height;
          local_ItemData2.relativeTop = local_ItemData3.relativeTop;
          local_ItemData2.relativeLeft = local_ItemData3.relativeLeft;
          local_ItemData2.marginTop = local_ItemData3.marginTop;
          local_ItemData2.marginLeft = local_ItemData3.marginLeft;
          local_ItemData2.marginRight = local_ItemData3.marginRight;
          local_ItemData2.marginBottom = local_ItemData3.marginBottom;
          local_ItemData3.reset();
        }
        localXulPage = this._area.getOwnerPage();
        k = this._area.getChildNum();
        int m = this._itemIdxOffset;
        if (paramInt < m)
        {
          this._itemIdxOffset = (1 + this._itemIdxOffset);
          createItemInstance(localXulPage, -1 + this._itemIdxOffset, -1 + (k + this._itemIdxOffset));
          this._itemIdxOffset = (-1 + this._itemIdxOffset);
          reset();
          return;
        }
        n = this._itemIdxOffset;
      }
      while (paramInt < n);
      i1 = k + this._itemIdxOffset;
    }
    while (paramInt >= i1);
    XulTemplate localXulTemplate = this._itemTemplate;
    int i2 = this._data.size();
    Object localObject = localXulTemplate.getItemTemplate(paramXulDataNode, paramInt, i2);
    if ((localObject instanceof XulTemplate))
    {
      Log.w(TAG, "DO NOT use template as the item-template of massive render!");
      return;
    }
    int i3 = paramInt - this._itemIdxOffset;
    XulView localXulView1 = (XulView)localObject;
    local_ItemData1.refView = localXulView1.getWeakReference();
    this._rebindViews.clear();
    ArrayList localArrayList = (ArrayList)this._instanceCache.get(localXulView1);
    if ((localArrayList == null) || (localArrayList.isEmpty()))
      if ((localXulView1 instanceof XulItem))
      {
        XulItem localXulItem = ((XulItem)localXulView1).makeClone(this._area, i3);
        localXulItem.prepareRender(getRenderContext(), true);
        localXulItem.setBindingCtx("[" + paramInt + "]", local_ItemData1.dataArray);
        localXulPage.addSelectorTarget(localXulItem);
        localXulPage.applySelectors(localXulItem);
        this._rebindViews.add(localXulItem);
      }
    while (true)
    {
      localXulPage.rebindViews((XulView[])this._rebindViews.getArray(), this._rebindViews.size(), getRenderContext().getDefaultActionCallback());
      this._rebindViews.clear();
      XulAction localXulAction = this._view.getAction(XulPropNameCache.TagId.ACTION_MASSIVE_UPDATED);
      if (localXulAction != null)
        XulPage.invokeActionNoPopup(this._view, localXulAction);
      reset();
      return;
      if ((localXulView1 instanceof XulArea))
      {
        XulArea localXulArea = ((XulArea)localXulView1).makeClone(this._area, i3);
        localXulArea.prepareRender(getRenderContext(), true);
        localXulArea.setBindingCtx("[" + paramInt + "]", local_ItemData1.dataArray);
        localXulPage.addSelectorTargets(localXulArea);
        localXulPage.applySelectors(localXulArea);
        this._rebindViews.add(localXulArea);
        continue;
        XulView localXulView2 = (XulView)localArrayList.remove(-1 + localArrayList.size());
        localXulView2.cleanImageItems();
        this._area.addChild(i3, localXulView2);
        localXulView2.cleanBindingCtx();
        localXulView2.setBindingCtx("[" + paramInt + "]", local_ItemData1.dataArray);
        this._rebindViews.add(localXulView2);
      }
    }
  }

  public void addDataItem(XulDataNode paramXulDataNode)
  {
    if (this._data == null)
      this._data = new ArrayList();
    this._updateItemSize = true;
    _ItemData local_ItemData = new _ItemData(null);
    local_ItemData.setData(paramXulDataNode);
    this._data.add(local_ItemData);
  }

  public boolean cleanDataItems()
  {
    this._scrollX = 0.0F;
    this._scrollY = 0.0F;
    this._scrollTargetX = 0.0F;
    this._scrollTargetY = 0.0F;
    this._itemIdxOffset = 0;
    this._firstItemIdxOffset = 0;
    this._lastItemIdxOffset = 0;
    this._skipItemOffsetX = 0.0F;
    this._skipItemOffsetY = 0.0F;
    if ((this._data != null) && (!this._data.isEmpty()))
      this._data.clear();
    this._area.eachChild(this._itemRecycler);
    this._area.removeAllChildren();
    return true;
  }

  public void cleanImageItems()
  {
    Iterator localIterator1 = this._instanceCache.values().iterator();
    while (localIterator1.hasNext())
    {
      ArrayList localArrayList = (ArrayList)localIterator1.next();
      if ((localArrayList != null) && (!localArrayList.isEmpty()))
      {
        Iterator localIterator2 = localArrayList.iterator();
        while (localIterator2.hasNext())
          ((XulView)localIterator2.next()).cleanImageItems();
      }
    }
    super.cleanImageItems();
  }

  public boolean collectPendingItems(XulTaskCollector paramXulTaskCollector)
  {
    if ((_isViewChanged()) || (this._rect == null))
      return true;
    while (true)
    {
      int i;
      int j;
      int k;
      int i1;
      try
      {
        i = this._area.getChildNum();
        j = this._firstItemIdxOffset - this._itemIdxOffset;
        k = this._lastItemIdxOffset - this._itemIdxOffset;
        if (j >= 0)
          break label158;
        j = 0;
        break label136;
        if (i1 >= k)
          break label182;
        paramXulTaskCollector.addPendingItem(this._area.getChild(i1));
        i1++;
        continue;
        if (n < i)
          paramXulTaskCollector.addPendingItem(this._area.getChild(n));
        if (m >= 0)
          paramXulTaskCollector.addPendingItem(this._area.getChild(m));
        m--;
        n++;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return true;
      }
      label136: if (k < 0)
        k = 0;
      while (true)
      {
        if (j > k)
          break label182;
        i1 = j;
        break;
        label158: if (j <= i)
          break label136;
        j = i;
        break label136;
        if (k > i)
          k = i;
      }
      label182: int m = j - 1;
      int n = k;
      if (n < m)
        n = m + 1;
      if (m < 0)
        if (n >= i)
          break;
    }
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutContainer();
  }

  public boolean doSuspendRecycle(int paramInt)
  {
    Iterator localIterator1 = this._instanceCache.values().iterator();
    while (localIterator1.hasNext())
    {
      ArrayList localArrayList = (ArrayList)localIterator1.next();
      if ((localArrayList != null) && (!localArrayList.isEmpty()))
      {
        Iterator localIterator2 = localArrayList.iterator();
        while (localIterator2.hasNext())
          ((XulView)localIterator2.next()).cleanImageItems();
      }
    }
    return super.doSuspendRecycle(paramInt);
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    super.draw(paramXulDC, paramRect, paramInt1, paramInt2);
    syncClipRange();
  }

  public void eachDataItem(DataItemIterator paramDataItemIterator)
  {
    ArrayList localArrayList = this._data;
    if (localArrayList == null);
    while (true)
    {
      return;
      int i = 0;
      int j = localArrayList.size();
      while (i < j)
      {
        paramDataItemIterator.onDataItem(i, ((_ItemData)localArrayList.get(i)).data);
        i++;
      }
    }
  }

  public boolean fixedItem()
  {
    return this._fixedItem;
  }

  public XulDataNode getDataItem(int paramInt)
  {
    if (this._data == null);
    while ((paramInt < 0) || (paramInt >= this._data.size()))
      return null;
    return ((_ItemData)this._data.get(paramInt)).data;
  }

  public int getDataItemNum()
  {
    if (this._data == null)
      return 0;
    return this._data.size();
  }

  public int getItemIdx(XulView paramXulView)
  {
    int i = this._area.findChildPos(paramXulView);
    if (i < 0)
      return -1;
    return i + this._itemIdxOffset;
  }

  public RectF getItemRect(int paramInt)
  {
    return getItemRect(paramInt, new RectF());
  }

  public RectF getItemRect(int paramInt, RectF paramRectF)
  {
    if ((paramInt < 0) || (paramInt >= this._data.size()))
      return null;
    _ItemData local_ItemData = (_ItemData)this._data.get(paramInt);
    if (!local_ItemData.isInitialized())
      return null;
    paramRectF.set(local_ItemData.relativeLeft, local_ItemData.relativeTop, local_ItemData.relativeLeft + local_ItemData.width, local_ItemData.relativeTop + local_ItemData.height);
    XulUtils.offsetRect(paramRectF, this._screenX, this._screenY);
    return paramRectF;
  }

  public XulView getItemView(int paramInt)
  {
    if ((paramInt >= this._data.size()) || (paramInt < this._itemIdxOffset));
    int i;
    do
    {
      return null;
      i = paramInt - this._itemIdxOffset;
    }
    while (i >= this._area.getChildNum());
    return this._area.getChild(i);
  }

  public boolean handleScrollEvent(float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public XulView postFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    int i = this._area.getChildNum();
    XulView localXulView;
    if (i == 0)
      localXulView = null;
    RectF localRectF1;
    do
    {
      RectF localRectF2;
      do
      {
        return localXulView;
        if (this._arrangement != 4)
          break;
        if (paramFocusDirection != XulLayout.FocusDirection.MOVE_DOWN)
          return null;
        if (i + this._firstItemIdxOffset < this._data.size())
          return XulItem.KEEP_FOCUS;
        localXulView = this._area.getChild(i - 1);
        localRectF2 = localXulView.getFocusRc();
        if (localRectF2.centerY() < paramRectF.bottom)
          return null;
      }
      while (localXulView.focusable());
      if (!(localXulView instanceof XulArea))
        break;
      XulArea localXulArea2 = (XulArea)localXulView;
      paramRectF.offsetTo(localRectF2.left, paramRectF.top);
      return localXulArea2.findSubFocus(paramFocusDirection, paramRectF, paramXulView);
      if (this._arrangement != 8)
        break;
      if (paramFocusDirection != XulLayout.FocusDirection.MOVE_RIGHT)
        return null;
      if (i + this._firstItemIdxOffset < this._data.size())
        return XulItem.KEEP_FOCUS;
      localXulView = this._area.getChild(i - 1);
      localRectF1 = localXulView.getFocusRc();
      if (localRectF1.centerX() < paramRectF.right)
        return null;
    }
    while (localXulView.focusable());
    if ((localXulView instanceof XulArea))
    {
      XulArea localXulArea1 = (XulArea)localXulView;
      paramRectF.offsetTo(paramRectF.left, localRectF1.top);
      return localXulArea1.findSubFocus(paramFocusDirection, paramRectF, paramXulView);
    }
    return null;
  }

  public boolean removeDataItem(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this._data.size()))
      return false;
    this._updateItemSize = true;
    this._data.remove(paramInt);
    int i = paramInt;
    int j = this._data.size();
    while (i < j)
    {
      ((_ItemData)this._data.get(i)).reset();
      i++;
    }
    int k = this._area.getChildNum();
    if ((paramInt >= this._itemIdxOffset) && (paramInt < k + this._itemIdxOffset))
    {
      XulLayout localXulLayout = this._area.getOwnerPage().getLayout();
      boolean bool = this._area.getChild(paramInt - this._itemIdxOffset).hasFocus();
      if (bool)
        localXulLayout.requestFocus(null);
      this._area.removeChildren(paramInt - this._itemIdxOffset, k, this._itemRecycler_list);
      createItemInstance(this._area.getOwnerPage(), this._itemIdxOffset, k + this._itemIdxOffset);
      if (bool)
      {
        int m = this._area.getChildNum();
        for (int n = paramInt - this._itemIdxOffset; n >= m; n--);
        if (n >= 0)
        {
          XulView localXulView = this._area.getChild(n);
          getRenderContext().doLayout();
          localXulLayout.requestFocus(localXulView);
        }
      }
    }
    while (true)
    {
      return true;
      if (paramInt < this._itemIdxOffset)
        this._area.removeChildren(0, 1, this._itemRecycler_list);
    }
  }

  public boolean removeItem(XulView paramXulView)
  {
    return removeDataItem(getItemIdx(paramXulView));
  }

  public void resetBinding()
  {
    this._initBinding = true;
    this._scrollX = 0.0F;
    this._scrollY = 0.0F;
    this._scrollTargetX = 0.0F;
    this._scrollTargetY = 0.0F;
    this._itemIdxOffset = 0;
    this._firstItemIdxOffset = 0;
    this._lastItemIdxOffset = 0;
    this._skipItemOffsetX = 0.0F;
    this._skipItemOffsetY = 0.0F;
    reset();
  }

  public void syncContentView()
  {
    syncItemInstance();
  }

  public boolean updateDataItems(int paramInt, Iterable<XulDataNode> paramIterable)
  {
    this._rebindViews.clear();
    int i = this._area.getChildNum();
    Iterator localIterator = paramIterable.iterator();
    int j = 0;
    int k = this._data.size() - paramInt;
    while ((j < k) && (localIterator.hasNext()))
    {
      ((_ItemData)this._data.get(paramInt + j)).setData((XulDataNode)localIterator.next());
      int m = paramInt + j - this._itemIdxOffset;
      if ((m >= 0) && (m < i))
      {
        XulView localXulView = this._area.getChild(m);
        this._rebindViews.add(localXulView);
      }
      j++;
    }
    if (!this._rebindViews.isEmpty())
    {
      this._area.getOwnerPage().rebindViews((XulView[])this._rebindViews.getArray(), this._rebindViews.size(), getRenderContext().getDefaultActionCallback());
      this._rebindViews.clear();
      reset();
      XulAction localXulAction = this._view.getAction(XulPropNameCache.TagId.ACTION_MASSIVE_UPDATED);
      if (localXulAction != null)
        XulPage.invokeActionNoPopup(this._view, localXulAction);
    }
    return true;
  }

  public boolean updateDataItems(int paramInt, XulDataNode[] paramArrayOfXulDataNode)
  {
    this._rebindViews.clear();
    int i = this._area.getChildNum();
    int j = 0;
    int k = Math.min(this._data.size() - paramInt, paramArrayOfXulDataNode.length);
    while (j < k)
    {
      ((_ItemData)this._data.get(paramInt + j)).setData(paramArrayOfXulDataNode[j]);
      int m = paramInt + j - this._itemIdxOffset;
      if ((m >= 0) && (m < i))
      {
        XulView localXulView = this._area.getChild(m);
        this._rebindViews.add(localXulView);
      }
      j++;
    }
    if (!this._rebindViews.isEmpty())
    {
      this._area.getOwnerPage().rebindViews((XulView[])this._rebindViews.getArray(), this._rebindViews.size(), getRenderContext().getDefaultActionCallback());
      this._rebindViews.clear();
      reset();
      XulAction localXulAction = this._view.getAction(XulPropNameCache.TagId.ACTION_MASSIVE_UPDATED);
      if (localXulAction != null)
        XulPage.invokeActionNoPopup(this._view, localXulAction);
    }
    return true;
  }

  private class ClipRangeUpdater
    implements Runnable
  {
    private float _newClipRangeHeight;
    private float _newClipRangeLeft;
    private float _newClipRangeTop;
    private float _newClipRangeWidth;
    private boolean _running = false;

    private ClipRangeUpdater()
    {
    }

    public void doUpdate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this._newClipRangeLeft = paramFloat1;
      this._newClipRangeTop = paramFloat2;
      this._newClipRangeWidth = paramFloat3;
      this._newClipRangeHeight = paramFloat4;
      XulRenderContext localXulRenderContext = XulMassiveRender.this.getRenderContext();
      if ((localXulRenderContext == null) || (this._running))
        return;
      this._running = true;
      localXulRenderContext.uiRun(this);
    }

    public void run()
    {
      XulMassiveRender.access$202(XulMassiveRender.this, this._newClipRangeLeft);
      XulMassiveRender.access$302(XulMassiveRender.this, this._newClipRangeTop);
      XulMassiveRender.access$402(XulMassiveRender.this, this._newClipRangeWidth);
      XulMassiveRender.access$502(XulMassiveRender.this, this._newClipRangeHeight);
      XulMassiveRender.this.syncItemInstance();
      this._running = false;
    }
  }

  public static abstract interface DataItemIterator
  {
    public abstract void onDataItem(int paramInt, XulDataNode paramXulDataNode);
  }

  protected class LayoutContainer extends XulAreaRender.LayoutContainer
  {
    protected LayoutContainer()
    {
      super();
    }

    public int getLayoutMode()
    {
      return XulMassiveRender.this._arrangement;
    }

    public int getOffsetX()
    {
      XulMassiveRender.access$2102(XulMassiveRender.this, (int)XulMassiveRender.this._scrollX);
      return (int)XulMassiveRender.this._itemOffsetX;
    }

    public int getOffsetY()
    {
      XulMassiveRender.access$2202(XulMassiveRender.this, (int)XulMassiveRender.this._scrollY);
      return (int)XulMassiveRender.this._itemOffsetY;
    }

    public boolean offsetBase(int paramInt1, int paramInt2)
    {
      boolean bool = super.offsetBase(paramInt1, paramInt2);
      XulMassiveRender.this.syncClipRange();
      return bool;
    }

    public int prepare()
    {
      if (!XulMassiveRender.this._isLayoutChangedByChild())
        XulMassiveRender.this.prepareAttr();
      int i = super.prepare();
      XulMassiveRender.this.prepareBinding();
      return i;
    }

    public boolean setBase(int paramInt1, int paramInt2)
    {
      boolean bool = super.setBase(paramInt1, paramInt2);
      XulMassiveRender.this.syncClipRange();
      return bool;
    }

    public int setContentSize(int paramInt1, int paramInt2)
    {
      XulMassiveRender.access$1702(XulMassiveRender.this, paramInt1);
      XulMassiveRender.access$1802(XulMassiveRender.this, paramInt2);
      return 0;
    }

    public boolean setHeight(int paramInt)
    {
      int i = getHeight();
      if ((XulMassiveRender.this._fixedItem) && ((i == 2147483645) || (i == 2147483646)))
        return super.setHeight(XulMassiveRender.this._contentHeight);
      return super.setHeight(paramInt);
    }

    public boolean setWidth(int paramInt)
    {
      int i = getWidth();
      if ((XulMassiveRender.this._fixedItem) && ((i == 2147483645) || (i == 2147483646)))
        return super.setWidth(XulMassiveRender.this._contentWidth);
      return super.setWidth(paramInt);
    }

    public boolean updateContentSize()
    {
      if (XulMassiveRender.this._fixedItem)
      {
        XulMassiveRender.this.fixItemSize();
        return false;
      }
      return true;
    }
  }

  private class ScrollAnimation
    implements IXulAnimation
  {
    long _lastTimestamp = 0L;

    private ScrollAnimation()
    {
    }

    private float calDelta(float paramFloat)
    {
      float f1 = paramFloat / 3.0F;
      if (Math.abs(f1) <= 4.0F)
      {
        f1 = paramFloat;
        float f2 = Math.abs(f1);
        if (f2 > 4.0F)
          f1 = f1 * 4.0F / f2;
      }
      return f1;
    }

    public void reset()
    {
      this._lastTimestamp = 0L;
    }

    public boolean updateAnimation(long paramLong)
    {
      if (this._lastTimestamp == 0L)
        this._lastTimestamp = paramLong;
      float f1 = calDelta(XulMassiveRender.this._scrollTargetX - XulMassiveRender.this._scrollX);
      float f2 = calDelta(XulMassiveRender.this._scrollTargetY - XulMassiveRender.this._scrollY);
      if ((Math.abs(f1) <= 0.001D) && (Math.abs(f2) <= 0.001D))
      {
        reset();
        return false;
      }
      XulMassiveRender.access$916(XulMassiveRender.this, f1);
      XulMassiveRender.access$1116(XulMassiveRender.this, f2);
      XulMassiveRender.this.syncItemInstance();
      return true;
    }
  }

  private static class _ItemData
  {
    XulDataNode data;
    ArrayList<XulDataNode> dataArray = new ArrayList(1);
    float height = 0.0F;
    int marginBottom = 0;
    int marginLeft = 0;
    int marginRight = 0;
    int marginTop = 0;
    WeakReference<XulView> refView;
    float relativeLeft = (0.0F / 0.0F);
    float relativeTop = (0.0F / 0.0F);
    float width = 0.0F;

    public boolean isInitialized()
    {
      return (!Float.isNaN(this.relativeLeft)) && (!Float.isNaN(this.relativeTop));
    }

    public void reset()
    {
      this.relativeLeft = (0.0F / 0.0F);
      this.relativeTop = (0.0F / 0.0F);
    }

    public void setData(XulDataNode paramXulDataNode)
    {
      this.data = paramXulDataNode;
      if (this.dataArray.isEmpty())
      {
        this.dataArray.add(paramXulDataNode);
        return;
      }
      this.dataArray.set(0, paramXulDataNode);
    }
  }

  private class _RebindViewList extends XulSimpleArray<XulView>
  {
    public _RebindViewList(int arg2)
    {
      super();
    }

    protected XulView[] allocArrayBuf(int paramInt)
    {
      return new XulView[paramInt];
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulMassiveRender
 * JD-Core Version:    0.6.2
 */