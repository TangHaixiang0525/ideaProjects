package com.starcor.xul.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class XulSimpleArray<T>
{
  T[] _buf;
  int _maximumSize;
  int _num;

  public XulSimpleArray()
  {
    this._buf = null;
    this._maximumSize = 0;
    this._num = 0;
  }

  public XulSimpleArray(int paramInt)
  {
    this._buf = allocArrayBuf(paramInt);
    this._maximumSize = paramInt;
    this._num = 0;
  }

  private void _enlargeBuf(int paramInt)
  {
    this._maximumSize = (paramInt + this._maximumSize);
    Object[] arrayOfObject = allocArrayBuf(this._maximumSize);
    if (this._buf != null)
      System.arraycopy(this._buf, 0, arrayOfObject, 0, this._num);
    this._buf = arrayOfObject;
  }

  private static int getDelta(int paramInt)
  {
    return 0xFFFFFFE0 & paramInt + 31;
  }

  public void add(int paramInt, T paramT)
  {
    if ((paramInt >= this._num) || (paramInt < 0))
    {
      push(paramT);
      return;
    }
    if (this._num < this._maximumSize)
    {
      for (int i = this._num; i > paramInt; i--)
        this._buf[i] = this._buf[(i - 1)];
      this._buf[paramInt] = paramT;
      this._num = (1 + this._num);
      return;
    }
    this._maximumSize += getDelta(1);
    Object[] arrayOfObject = allocArrayBuf(this._maximumSize);
    if (this._buf != null)
    {
      System.arraycopy(this._buf, 0, arrayOfObject, 0, paramInt);
      System.arraycopy(this._buf, paramInt, arrayOfObject, paramInt + 1, this._num - paramInt);
    }
    this._buf = arrayOfObject;
    this._buf[paramInt] = paramT;
    this._num = (1 + this._num);
  }

  public void add(T paramT)
  {
    if (this._num < this._maximumSize)
    {
      this._buf[this._num] = paramT;
      this._num = (1 + this._num);
      return;
    }
    _enlargeBuf(getDelta(1));
    this._buf[this._num] = paramT;
    this._num = (1 + this._num);
  }

  public void addAll(XulSimpleArray<T> paramXulSimpleArray)
  {
    if (this._num + paramXulSimpleArray._num >= this._maximumSize)
      _enlargeBuf(getDelta(this._num + paramXulSimpleArray._num - this._maximumSize));
    System.arraycopy(paramXulSimpleArray._buf, 0, this._buf, this._num, paramXulSimpleArray._num);
    this._num += paramXulSimpleArray._num;
  }

  protected abstract T[] allocArrayBuf(int paramInt);

  public void clear()
  {
    if (this._num == 0)
      return;
    Arrays.fill(this._buf, 0, this._num, null);
    this._num = 0;
  }

  public boolean contains(T paramT)
  {
    for (int i = 0; i < this._num; i++)
      if (this._buf[i].equals(paramT))
        return true;
    return false;
  }

  public T get(int paramInt)
  {
    return this._buf[paramInt];
  }

  public T[] getArray()
  {
    return this._buf;
  }

  public int indexOf(T paramT)
  {
    for (int i = 0; i < this._num; i++)
      if (this._buf[i].equals(paramT))
        return i;
    return -1;
  }

  public boolean isEmpty()
  {
    return this._num <= 0;
  }

  public int lastIndexOf(T paramT)
  {
    for (int i = -1 + this._num; i >= 0; i--)
      if (this._buf[i].equals(paramT))
        return i;
    return -1;
  }

  public T pop()
  {
    if (this._num <= 0)
      return null;
    this._num = (-1 + this._num);
    Object localObject = this._buf[this._num];
    this._buf[this._num] = null;
    return localObject;
  }

  public void push(T paramT)
  {
    if (this._num < this._maximumSize)
    {
      this._buf[this._num] = paramT;
      this._num = (1 + this._num);
      return;
    }
    _enlargeBuf(getDelta(1));
    this._buf[this._num] = paramT;
    this._num = (1 + this._num);
  }

  public void put(int paramInt, T paramT)
  {
    this._buf[paramInt] = paramT;
  }

  public T remove(int paramInt)
  {
    int i = this._num;
    if (paramInt >= i)
      return null;
    Object[] arrayOfObject = this._buf;
    Object localObject = arrayOfObject[paramInt];
    int j = paramInt + 1;
    int k = i - 1;
    System.arraycopy(arrayOfObject, j, arrayOfObject, paramInt, k - paramInt);
    arrayOfObject[k] = null;
    this._num = k;
    return localObject;
  }

  public void remove(int paramInt1, int paramInt2)
  {
    int i = this._num;
    if (paramInt1 >= i)
      return;
    if (paramInt2 >= i)
      paramInt2 = i;
    Object[] arrayOfObject = this._buf;
    int j = paramInt2 - paramInt1;
    System.arraycopy(arrayOfObject, paramInt2, arrayOfObject, paramInt1, i - paramInt2);
    Arrays.fill(arrayOfObject, i - j, i, null);
    this._num = (i - j);
  }

  public boolean remove(T paramT)
  {
    int i = this._num;
    Object[] arrayOfObject = this._buf;
    if (paramT != null)
      for (int n = 0; n < i; n++)
        if (paramT.equals(arrayOfObject[n]))
        {
          int i1 = n + 1;
          int i2 = i - 1;
          System.arraycopy(arrayOfObject, i1, arrayOfObject, n, i2 - n);
          arrayOfObject[i2] = null;
          this._num = i2;
          return true;
        }
    for (int j = 0; j < i; j++)
      if (arrayOfObject[j] == null)
      {
        int k = j + 1;
        int m = i - 1;
        System.arraycopy(arrayOfObject, k, arrayOfObject, j, m - j);
        arrayOfObject[m] = null;
        this._num = m;
        return true;
      }
    return false;
  }

  public void removeAll(Collection<? extends T> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
      remove(localIterator.next());
  }

  public void resize(int paramInt)
  {
    if (paramInt > this._maximumSize)
      _enlargeBuf(getDelta(paramInt - this._maximumSize));
    Arrays.fill(this._buf, this._num, paramInt, null);
    this._num = paramInt;
  }

  public int size()
  {
    return this._num;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulSimpleArray
 * JD-Core Version:    0.6.2
 */