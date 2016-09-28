package com.starcor.xul.Utils;

import java.util.ArrayList;

public class XulSparseArray<T>
  implements Cloneable
{
  private static final int _ARRAY_LIMITATION = 1024;
  private static final int _CONTAINER_SIZE = 32;
  private static ArrayList<Object[]> _cachedItem = new ArrayList();
  private Object[] _obj;

  private static Object[] _allocContainer()
  {
    if (!_cachedItem.isEmpty())
      return (Object[])_cachedItem.remove(-1 + _cachedItem.size());
    return new Object[32];
  }

  private static void _recycleAll(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null)
      return;
    int i = 0;
    if (i < 32)
    {
      Object localObject = paramArrayOfObject[i];
      if (localObject == null);
      while (true)
      {
        i++;
        break;
        paramArrayOfObject[i] = null;
        Object[] arrayOfObject = (Object[])localObject;
        for (int j = 0; j < 32; j++)
          arrayOfObject[j] = null;
        _recycleContainer(arrayOfObject);
      }
    }
    _recycleContainer(paramArrayOfObject);
  }

  private static void _recycleContainer(Object[] paramArrayOfObject)
  {
    _cachedItem.add(paramArrayOfObject);
  }

  public void clear()
  {
    _recycleAll(this._obj);
    this._obj = null;
  }

  public Object clone()
  {
    XulSparseArray localXulSparseArray = new XulSparseArray();
    if (this._obj == null)
      return localXulSparseArray;
    localXulSparseArray._obj = _allocContainer();
    localXulSparseArray.copy(this);
    return localXulSparseArray;
  }

  public void copy(XulSparseArray<T> paramXulSparseArray)
  {
    Object[] arrayOfObject1 = paramXulSparseArray._obj;
    if (arrayOfObject1 == null)
      return;
    if (this._obj == null)
      this._obj = _allocContainer();
    int i = 0;
    label26: Object localObject1;
    if (i < 32)
    {
      localObject1 = arrayOfObject1[i];
      if (localObject1 != null)
        break label48;
    }
    while (true)
    {
      i++;
      break label26;
      break;
      label48: Object[] arrayOfObject2 = (Object[])localObject1;
      Object localObject2 = this._obj[i];
      if (localObject2 == null)
      {
        Object[] arrayOfObject3 = this._obj;
        localObject2 = _allocContainer();
        arrayOfObject3[i] = localObject2;
      }
      System.arraycopy(arrayOfObject2, 0, (Object[])localObject2, 0, 32);
    }
  }

  public T get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 1024));
    int i;
    Object[] arrayOfObject;
    do
    {
      Object localObject;
      do
      {
        do
          return null;
        while (this._obj == null);
        i = paramInt % 32;
        int j = paramInt / 32;
        localObject = this._obj[j];
      }
      while (localObject == null);
      arrayOfObject = (Object[])localObject;
    }
    while (arrayOfObject[i] == null);
    return arrayOfObject[i];
  }

  public int nextIdx(int paramInt)
  {
    int i = paramInt + 1;
    if (i >= 1024)
      return -1;
    if (this._obj == null)
      return -1;
    int j = i % 32;
    int k = i / 32;
    Object localObject1 = this._obj[k];
    while (localObject1 == null)
    {
      k++;
      if (k >= 32)
        return -1;
      localObject1 = this._obj[k];
      j = 0;
    }
    Object[] arrayOfObject = (Object[])localObject1;
    for (Object localObject2 = arrayOfObject[j]; localObject2 == null; localObject2 = arrayOfObject[j])
    {
      j++;
      if (j >= 32)
      {
        Object localObject3;
        do
        {
          k++;
          if (k >= 32)
            return -1;
          localObject3 = this._obj[k];
          j = 0;
        }
        while (localObject3 == null);
        arrayOfObject = (Object[])localObject3;
      }
    }
    return j + k * 32;
  }

  public void put(int paramInt, T paramT)
  {
    if ((paramInt < 0) || (paramInt >= 1024))
      return;
    if (paramT == null)
    {
      remove(paramInt);
      return;
    }
    int i = paramInt % 32;
    int j = paramInt / 32;
    if (this._obj == null)
      this._obj = _allocContainer();
    Object localObject = this._obj[j];
    if (localObject == null)
    {
      Object[] arrayOfObject = this._obj;
      localObject = _allocContainer();
      arrayOfObject[j] = localObject;
    }
    ((Object[])localObject)[i] = paramT;
  }

  public void remove(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 1024));
    int i;
    Object localObject;
    do
    {
      do
        return;
      while (this._obj == null);
      i = paramInt % 32;
      int j = paramInt / 32;
      localObject = this._obj[j];
    }
    while (localObject == null);
    ((Object[])localObject)[i] = null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulSparseArray
 * JD-Core Version:    0.6.2
 */