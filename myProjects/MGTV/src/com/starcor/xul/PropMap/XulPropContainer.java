package com.starcor.xul.PropMap;

import com.starcor.xul.Prop.XulProp;
import com.starcor.xul.Prop.XulPropNameCache;
import com.starcor.xul.Utils.XulSparseArray;

public class XulPropContainer<T extends XulProp> extends XulSparseArray<T>
{
  boolean _enabled = true;
  XulPriorityPropMap<T> _priorityProp = new XulPriorityPropMap();
  int _state;

  public static <T extends XulProp> XulPropContainer<T> makeClone(XulPropContainer<T> paramXulPropContainer)
  {
    Object localObject;
    if (paramXulPropContainer == null)
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = new XulPropContainer();
      ((XulPropContainer)localObject)._state = paramXulPropContainer._state;
      XulPriorityPropMap localXulPriorityPropMap = ((XulPropContainer)localObject)._priorityProp;
      localXulPriorityPropMap.copy(paramXulPropContainer._priorityProp);
      for (int i = paramXulPropContainer.nextIdx(-1); i >= 0; i = paramXulPropContainer.nextIdx(i))
        ((XulPropContainer)localObject).put(i, paramXulPropContainer.get(i).makeClone());
      for (int j = localXulPriorityPropMap.nextIdx(-1); j >= 0; j = localXulPriorityPropMap.nextIdx(j))
        localXulPriorityPropMap.put(j, ((XulPriorityPropMap.MapEntry)localXulPriorityPropMap.get(j)).cloneEntry(true));
    }
  }

  public void add(T paramT)
  {
    int i = paramT.getNameId();
    this._priorityProp.add(i, paramT);
  }

  public void add(T paramT, int paramInt)
  {
    int i = paramT.getNameId();
    this._priorityProp.add(i, paramT, paramInt);
  }

  public void destroy()
  {
    super.clear();
    this._priorityProp.clear();
  }

  public <T extends XulProp> void each(IXulPropIterator<T> paramIXulPropIterator)
  {
    eachInlineProp(paramIXulPropIterator);
    this._priorityProp.each(paramIXulPropIterator);
  }

  public <T extends XulProp> void eachInlineProp(IXulPropIterator<T> paramIXulPropIterator)
  {
    for (int i = super.nextIdx(-1); i >= 0; i = super.nextIdx(i))
      paramIXulPropIterator.onProp((XulProp)super.get(i), -1);
  }

  public T get(int paramInt)
  {
    return getWithState(paramInt, this._state);
  }

  public T get(String paramString)
  {
    return get(XulPropNameCache.name2Id(paramString));
  }

  public T getOwnProp(int paramInt)
  {
    return (XulProp)super.get(paramInt);
  }

  public T getOwnProp(String paramString)
  {
    return getOwnProp(XulPropNameCache.name2Id(paramString));
  }

  public T getWithState(int paramInt1, int paramInt2)
  {
    XulProp localXulProp = (XulProp)super.get(paramInt1);
    if (localXulProp != null)
      return localXulProp;
    XulPriorityPropMap localXulPriorityPropMap = this._priorityProp;
    if (this._enabled);
    while (true)
    {
      return localXulPriorityPropMap.getVal(paramInt1, paramInt2);
      paramInt2 = 2;
    }
  }

  public void put(T paramT)
  {
    super.put(paramT.getNameId(), paramT);
  }

  public void remove(T paramT)
  {
    removeOwnProp(paramT);
    this._priorityProp.remove(paramT);
  }

  public void remove(T paramT, int paramInt)
  {
    removeOwnProp(paramT);
    this._priorityProp.remove(paramT, paramInt);
  }

  public void removeOwnProp(int paramInt)
  {
    super.remove(paramInt);
  }

  public void removeOwnProp(T paramT)
  {
    for (int i = super.nextIdx(-1); ; i = super.nextIdx(i))
      if (i >= 0)
      {
        if (paramT == super.get(i))
          super.remove(i);
      }
      else
        return;
  }

  public void removeOwnProp(String paramString)
  {
    removeOwnProp(XulPropNameCache.name2Id(paramString));
  }

  public void switchEnabled(boolean paramBoolean)
  {
    this._enabled = paramBoolean;
  }

  public void switchState(int paramInt)
  {
    this._state = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.PropMap.XulPropContainer
 * JD-Core Version:    0.6.2
 */