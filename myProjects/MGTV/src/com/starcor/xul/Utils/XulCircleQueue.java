package com.starcor.xul.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class XulCircleQueue<E>
  implements Queue<E>
{
  int _begin = 0;
  E[] _data;
  int _end = 0;

  static
  {
    if (!XulCircleQueue.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public XulCircleQueue()
  {
    this(32);
  }

  public XulCircleQueue(int paramInt)
  {
    this._data = ((Object[])new Object[paramInt]);
  }

  private void _eliminateCollection(Collection<?> paramCollection, boolean paramBoolean)
  {
    int i = this._begin;
    int j = this._end;
    int k = i;
    Object[] arrayOfObject = this._data;
    int m = arrayOfObject.length;
    int n = i;
    while (n != j)
      if (paramCollection.contains(arrayOfObject[n]) != paramBoolean)
      {
        n++;
        if (n == m)
          n = 0;
      }
      else
      {
        if (k != n)
          arrayOfObject[k] = arrayOfObject[n];
        n++;
        if (n == m)
          n = 0;
        k++;
        if (k == m)
          k = 0;
      }
    int i1 = k;
    while (i1 != j)
    {
      arrayOfObject[i1] = null;
      i1++;
      if (i1 == m)
        i1 = 0;
    }
    this._end = k;
  }

  private void _internalAdd(E paramE)
  {
    this._data[this._end] = paramE;
    this._end = (1 + this._end);
    if (this._end >= this._data.length)
      this._end = 0;
  }

  private static int roundUpToPowerOfTwo(int paramInt)
  {
    int i = 1;
    while (paramInt > 1)
    {
      int j = 2;
      if (paramInt >= 4096)
        j = 4096;
      while (true)
      {
        i *= j;
        if (paramInt % j <= 0)
          break label51;
        paramInt = 1 + paramInt / j;
        break;
        if (paramInt >= 16)
          j = 16;
      }
      label51: paramInt /= j;
    }
    return i;
  }

  private static <K, V> boolean testEquals(K paramK, V paramV)
  {
    return (paramK == paramV) || ((paramK != null) && (paramK.equals(paramV)));
  }

  void _enlargeBuffer(int paramInt)
  {
    int j;
    if (this._end >= this._begin)
    {
      j = this._end - this._begin;
      if (-1 + this._data.length - j <= paramInt);
    }
    int i;
    do
    {
      return;
      Object[] arrayOfObject2 = (Object[])new Object[this._data.length + roundUpToPowerOfTwo(paramInt + 15)];
      System.arraycopy(this._data, this._begin, arrayOfObject2, 0, j);
      this._begin = 0;
      this._end = j;
      this._data = arrayOfObject2;
      return;
      i = this._data.length - this._begin + this._end;
    }
    while (-1 + this._data.length - i > paramInt);
    Object[] arrayOfObject1 = (Object[])new Object[this._data.length + roundUpToPowerOfTwo(paramInt + 15)];
    System.arraycopy(this._data, this._begin, arrayOfObject1, 0, this._data.length - this._begin);
    System.arraycopy(this._data, 0, arrayOfObject1, this._data.length - this._begin, this._end);
    this._begin = 0;
    this._end = i;
    this._data = arrayOfObject1;
  }

  public boolean add(E paramE)
  {
    _enlargeBuffer(1);
    _internalAdd(paramE);
    return true;
  }

  public boolean addAll(Collection<? extends E> paramCollection)
  {
    _enlargeBuffer(paramCollection.size());
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
      _internalAdd(localIterator.next());
    return true;
  }

  public void clear()
  {
    Arrays.fill(this._data, null);
    this._begin = 0;
    this._end = 0;
  }

  public boolean contains(Object paramObject)
  {
    int i = this._begin;
    while (i != this._end)
    {
      if (testEquals(this._data[i], paramObject))
        return true;
      i++;
      if (i == this._data.length)
        i = 0;
    }
    return false;
  }

  public boolean containsAll(Collection<?> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
      if (!contains(localIterator.next()))
        return false;
    return true;
  }

  public E element()
  {
    return peek();
  }

  public boolean isEmpty()
  {
    return this._end == this._begin;
  }

  public Iterator<E> iterator()
  {
    return new Iterator()
    {
      int _pos = XulCircleQueue.this._begin;

      public boolean hasNext()
      {
        return this._pos != XulCircleQueue.this._end;
      }

      public E next()
      {
        Object[] arrayOfObject = XulCircleQueue.this._data;
        int i = this._pos;
        this._pos = (i + 1);
        return arrayOfObject[i];
      }

      public void remove()
      {
        XulCircleQueue.this.remove(XulCircleQueue.this._data[(-1 + this._pos)]);
      }
    };
  }

  public boolean offer(E paramE)
  {
    return false;
  }

  public E peek()
  {
    if (this._end == this._begin)
      return null;
    return this._data[this._begin];
  }

  public E poll()
  {
    Object localObject;
    if (this._end == this._begin)
      localObject = null;
    do
    {
      return localObject;
      localObject = this._data[this._begin];
      this._data[this._begin] = null;
      this._begin = (1 + this._begin);
      if (this._begin >= this._data.length)
        this._begin = 0;
    }
    while (this._end != this._begin);
    this._end = 0;
    this._begin = 0;
    return localObject;
  }

  public E remove()
  {
    return poll();
  }

  public boolean remove(Object paramObject)
  {
    int i = this._begin;
    int j = this._end;
    int k = i;
    Object[] arrayOfObject = this._data;
    int m = arrayOfObject.length;
    int n = i;
    while (n != j)
      if (testEquals(paramObject, arrayOfObject[n]))
      {
        n++;
        if (n == m)
          n = 0;
      }
      else
      {
        if (k != n)
          arrayOfObject[k] = arrayOfObject[n];
        n++;
        if (n == m)
          n = 0;
        k++;
        if (k == m)
          k = 0;
      }
    int i1 = k;
    while (i1 != j)
    {
      arrayOfObject[i1] = null;
      i1++;
      if (i1 == m)
        i1 = 0;
    }
    this._end = k;
    return false;
  }

  public boolean removeAll(Collection<?> paramCollection)
  {
    _eliminateCollection(paramCollection, false);
    return true;
  }

  public boolean retainAll(Collection<?> paramCollection)
  {
    _eliminateCollection(paramCollection, true);
    return true;
  }

  public int size()
  {
    if (this._end >= this._begin)
      return this._end - this._begin;
    return this._data.length - this._begin + this._end;
  }

  public Object[] toArray()
  {
    Object[] arrayOfObject = new Object[size()];
    int i = this._data.length;
    int j = this._begin;
    for (int k = 0; j < this._end; k++)
    {
      arrayOfObject[k] = this._data[j];
      j++;
      if (j == i)
        j = 0;
    }
    return arrayOfObject;
  }

  public <T> T[] toArray(T[] paramArrayOfT)
  {
    assert (paramArrayOfT.length >= size());
    int i = this._data.length;
    int j = this._begin;
    for (int k = 0; j < this._end; k++)
    {
      paramArrayOfT[k] = this._data[j];
      j++;
      if (j == i)
        j = 0;
    }
    return paramArrayOfT;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulCircleQueue
 * JD-Core Version:    0.6.2
 */