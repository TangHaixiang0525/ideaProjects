package com.starcor.xul.PropMap;

import com.starcor.xul.Prop.XulProp;
import com.starcor.xul.Prop.XulPropNameCache;
import com.starcor.xul.Utils.XulSparseArray;

class XulPriorityPropMap<T extends XulProp> extends XulSparseArray<MapEntry<T>>
  implements IXulPropChain<T>
{
  public void add(int paramInt, T paramT)
  {
    add(paramInt, paramT, 0);
  }

  public void add(int paramInt1, T paramT, int paramInt2)
  {
    MapEntry localMapEntry1 = (MapEntry)super.get(paramInt1);
    if (localMapEntry1 == null)
    {
      super.put(paramInt1, MapEntry.create(paramT, paramInt2));
      return;
    }
    int i = paramT.getPriority();
    MapEntry localMapEntry2 = localMapEntry1;
    MapEntry localMapEntry3 = null;
    while (true)
    {
      if (localMapEntry2 == null)
        break label116;
      XulProp localXulProp = localMapEntry2.prop;
      if (localXulProp == paramT)
        break;
      if (i > localXulProp.getPriority())
      {
        MapEntry localMapEntry4 = MapEntry.create(paramT, paramInt2);
        if (localMapEntry3 == null)
        {
          localMapEntry4.next = localMapEntry2;
          super.put(paramInt1, localMapEntry4);
          return;
        }
        localMapEntry3.addNext(localMapEntry4);
        return;
      }
      localMapEntry3 = localMapEntry2;
      localMapEntry2 = localMapEntry2.next;
    }
    label116: localMapEntry3.next = MapEntry.create(paramT, paramInt2);
  }

  public void add(String paramString, T paramT)
  {
    add(paramString, paramT, 0);
  }

  public void add(String paramString, T paramT, int paramInt)
  {
    add(XulPropNameCache.name2Id(paramString), paramT, paramInt);
  }

  public Object clone()
  {
    XulPriorityPropMap localXulPriorityPropMap = new XulPriorityPropMap();
    localXulPriorityPropMap.copy(this);
    return localXulPriorityPropMap;
  }

  public <TIter extends XulProp> void each(IXulPropIterator<TIter> paramIXulPropIterator)
  {
    int i = super.nextIdx(-1);
    if (i >= 0)
    {
      MapEntry localMapEntry1 = (MapEntry)super.get(i);
      if (localMapEntry1 == null);
      while (true)
      {
        i = super.nextIdx(i);
        break;
        for (MapEntry localMapEntry2 = localMapEntry1; localMapEntry2 != null; localMapEntry2 = localMapEntry2.next)
          paramIXulPropIterator.onProp(localMapEntry2.prop, localMapEntry2.state);
      }
    }
  }

  public T getVal(int paramInt1, int paramInt2)
  {
    MapEntry localMapEntry1 = (MapEntry)super.get(paramInt1);
    if (localMapEntry1 == null);
    while (true)
    {
      return null;
      for (MapEntry localMapEntry2 = localMapEntry1; localMapEntry2 != null; localMapEntry2 = localMapEntry2.next)
        if ((localMapEntry2.state == 0) || (localMapEntry2.state == paramInt2))
          return localMapEntry2.prop;
    }
  }

  public void remove(T paramT)
  {
    int i = paramT.getNameId();
    MapEntry localMapEntry1 = (MapEntry)super.get(i);
    if (localMapEntry1 == null);
    while (true)
    {
      return;
      MapEntry localMapEntry2 = localMapEntry1;
      MapEntry localMapEntry3 = null;
      while (localMapEntry2 != null)
      {
        if (paramT == localMapEntry2.prop)
        {
          if (localMapEntry2 == localMapEntry1)
          {
            super.put(i, localMapEntry2.next);
            return;
          }
          localMapEntry3.removeNext();
          return;
        }
        localMapEntry3 = localMapEntry2;
        localMapEntry2 = localMapEntry2.next;
      }
    }
  }

  public void remove(T paramT, int paramInt)
  {
    int i = paramT.getNameId();
    MapEntry localMapEntry1 = (MapEntry)super.get(i);
    if (localMapEntry1 == null);
    while (true)
    {
      return;
      MapEntry localMapEntry2 = localMapEntry1;
      MapEntry localMapEntry3 = null;
      while (localMapEntry2 != null)
      {
        if ((paramT == localMapEntry2.prop) && (paramInt == localMapEntry2.state))
        {
          if (localMapEntry2 == localMapEntry1)
          {
            super.put(i, localMapEntry2.next);
            return;
          }
          localMapEntry3.removeNext();
          return;
        }
        localMapEntry3 = localMapEntry2;
        localMapEntry2 = localMapEntry2.next;
      }
    }
  }

  public static final class MapEntry<T extends XulProp>
  {
    static MapEntry _entries = null;
    MapEntry<T> next;
    T prop;
    int state;

    public MapEntry(T paramT, int paramInt)
    {
      this.state = paramInt;
      this.prop = paramT;
    }

    public static <T extends XulProp> MapEntry<T> create(T paramT, int paramInt)
    {
      if (_entries != null)
      {
        MapEntry localMapEntry = _entries;
        _entries = localMapEntry.next;
        localMapEntry.next = null;
        localMapEntry.prop = paramT;
        localMapEntry.state = paramInt;
        return localMapEntry;
      }
      return new MapEntry(paramT, paramInt);
    }

    private static void recycleEntry(MapEntry paramMapEntry)
    {
      paramMapEntry.next = _entries;
      paramMapEntry.prop = null;
      _entries = paramMapEntry;
    }

    public final void addNext(MapEntry<T> paramMapEntry)
    {
      paramMapEntry.next = this.next;
      this.next = paramMapEntry;
    }

    public final MapEntry<T> cloneEntry(boolean paramBoolean)
    {
      MapEntry localMapEntry = create(this.prop, this.state);
      if ((paramBoolean) && (this.next != null))
        localMapEntry.next = this.next.cloneEntry(paramBoolean);
      return localMapEntry;
    }

    public final void removeNext()
    {
      if (this.next != null)
      {
        MapEntry localMapEntry = this.next;
        this.next = this.next.next;
        recycleEntry(localMapEntry);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.PropMap.XulPriorityPropMap
 * JD-Core Version:    0.6.2
 */