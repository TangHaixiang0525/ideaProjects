package com.starcor.xul.Utils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class XulArrayList<E> extends AbstractList<E>
  implements Cloneable, Serializable, RandomAccess
{
  private static final Object[] EMPTY_ARRAY = new Object[0];
  private static final int MIN_CAPACITY_INCREMENT = 12;
  private static final long serialVersionUID = 8683452581122892189L;
  transient Object[] array;
  int size;

  public XulArrayList()
  {
    this.array = EMPTY_ARRAY;
  }

  public XulArrayList(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("capacity < 0: " + paramInt);
    if (paramInt == 0);
    for (Object[] arrayOfObject = EMPTY_ARRAY; ; arrayOfObject = new Object[paramInt])
    {
      this.array = arrayOfObject;
      return;
    }
  }

  public XulArrayList(Collection<? extends E> paramCollection)
  {
    if (paramCollection == null)
      throw new NullPointerException("collection == null");
    Object localObject = paramCollection.toArray();
    if (localObject.getClass() != [Ljava.lang.Object.class)
    {
      Object[] arrayOfObject = new Object[localObject.length];
      System.arraycopy(localObject, 0, arrayOfObject, 0, localObject.length);
      localObject = arrayOfObject;
    }
    this.array = ((Object[])localObject);
    this.size = localObject.length;
  }

  private static int newCapacity(int paramInt)
  {
    if (paramInt < 6);
    for (int i = 12; ; i = paramInt >> 1)
      return paramInt + i;
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    int i = paramObjectInputStream.readInt();
    if (i < this.size)
      throw new InvalidObjectException("Capacity: " + i + " < size: " + this.size);
    if (i == 0);
    for (Object[] arrayOfObject = EMPTY_ARRAY; ; arrayOfObject = new Object[i])
    {
      this.array = arrayOfObject;
      for (int j = 0; j < this.size; j++)
        this.array[j] = paramObjectInputStream.readObject();
    }
  }

  static IndexOutOfBoundsException throwIndexOutOfBoundsException(int paramInt1, int paramInt2)
  {
    throw new IndexOutOfBoundsException("Invalid index " + paramInt1 + ", size is " + paramInt2);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeInt(this.array.length);
    for (int i = 0; i < this.size; i++)
      paramObjectOutputStream.writeObject(this.array[i]);
  }

  public void add(int paramInt, E paramE)
  {
    Object localObject = this.array;
    int i = this.size;
    if ((paramInt > i) || (paramInt < 0))
      throwIndexOutOfBoundsException(paramInt, i);
    if (i < localObject.length)
      System.arraycopy(localObject, paramInt, localObject, paramInt + 1, i - paramInt);
    while (true)
    {
      localObject[paramInt] = paramE;
      this.size = (i + 1);
      this.modCount = (1 + this.modCount);
      return;
      Object[] arrayOfObject = new Object[newCapacity(i)];
      System.arraycopy(localObject, 0, arrayOfObject, 0, paramInt);
      System.arraycopy(localObject, paramInt, arrayOfObject, paramInt + 1, i - paramInt);
      localObject = arrayOfObject;
      this.array = arrayOfObject;
    }
  }

  public boolean add(E paramE)
  {
    Object localObject = this.array;
    int i = this.size;
    if (i == localObject.length)
      if (i >= 6)
        break label76;
    label76: for (int j = 12; ; j = i >> 1)
    {
      Object[] arrayOfObject = new Object[j + i];
      System.arraycopy(localObject, 0, arrayOfObject, 0, i);
      localObject = arrayOfObject;
      this.array = arrayOfObject;
      localObject[i] = paramE;
      this.size = (i + 1);
      this.modCount = (1 + this.modCount);
      return true;
    }
  }

  public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    int i = this.size;
    if ((paramInt > i) || (paramInt < 0))
      throwIndexOutOfBoundsException(paramInt, i);
    Object[] arrayOfObject1 = paramCollection.toArray();
    int j = arrayOfObject1.length;
    if (j == 0)
      return false;
    Object localObject = this.array;
    int k = i + j;
    if (k <= localObject.length)
      System.arraycopy(localObject, paramInt, localObject, paramInt + j, i - paramInt);
    while (true)
    {
      System.arraycopy(arrayOfObject1, 0, localObject, paramInt, j);
      this.size = k;
      this.modCount = (1 + this.modCount);
      return true;
      Object[] arrayOfObject2 = new Object[newCapacity(k - 1)];
      System.arraycopy(localObject, 0, arrayOfObject2, 0, paramInt);
      System.arraycopy(localObject, paramInt, arrayOfObject2, paramInt + j, i - paramInt);
      localObject = arrayOfObject2;
      this.array = arrayOfObject2;
    }
  }

  public boolean addAll(Collection<? extends E> paramCollection)
  {
    Object[] arrayOfObject1 = paramCollection.toArray();
    int i = arrayOfObject1.length;
    if (i == 0)
      return false;
    Object localObject = this.array;
    int j = this.size;
    int k = j + i;
    if (k > localObject.length)
    {
      Object[] arrayOfObject2 = new Object[newCapacity(k - 1)];
      System.arraycopy(localObject, 0, arrayOfObject2, 0, j);
      localObject = arrayOfObject2;
      this.array = arrayOfObject2;
    }
    System.arraycopy(arrayOfObject1, 0, localObject, j, i);
    this.size = k;
    this.modCount = (1 + this.modCount);
    return true;
  }

  public void clear()
  {
    if (this.size != 0)
    {
      Arrays.fill(this.array, 0, this.size, null);
      this.size = 0;
      this.modCount = (1 + this.modCount);
    }
  }

  public Object clone()
  {
    try
    {
      XulArrayList localXulArrayList = (XulArrayList)super.clone();
      localXulArrayList.array = ((Object[])this.array.clone());
      return localXulArrayList;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
    }
    throw new AssertionError();
  }

  public boolean contains(Object paramObject)
  {
    Object[] arrayOfObject = this.array;
    int i = this.size;
    if (paramObject != null)
      for (int k = 0; k < i; k++)
        if (paramObject.equals(arrayOfObject[k]))
          return true;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label64;
      if (arrayOfObject[j] == null)
        break;
    }
    label64: return false;
  }

  public void ensureCapacity(int paramInt)
  {
    Object[] arrayOfObject1 = this.array;
    if (arrayOfObject1.length < paramInt)
    {
      Object[] arrayOfObject2 = new Object[paramInt];
      System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, this.size);
      this.array = arrayOfObject2;
      this.modCount = (1 + this.modCount);
    }
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this);
    while (true)
    {
      return true;
      if (!(paramObject instanceof List))
        return false;
      List localList = (List)paramObject;
      int i = this.size;
      if (localList.size() != i)
        return false;
      Object[] arrayOfObject = this.array;
      if ((localList instanceof RandomAccess))
      {
        for (int k = 0; k < i; k++)
        {
          Object localObject3 = arrayOfObject[k];
          Object localObject4 = localList.get(k);
          if (localObject3 == null)
          {
            if (localObject4 == null);
          }
          else
            while (!localObject3.equals(localObject4))
              return false;
        }
      }
      else
      {
        Iterator localIterator = localList.iterator();
        for (int j = 0; j < i; j++)
        {
          Object localObject1 = arrayOfObject[j];
          Object localObject2 = localIterator.next();
          if (localObject1 == null)
          {
            if (localObject2 == null);
          }
          else
            while (!localObject1.equals(localObject2))
              return false;
        }
      }
    }
  }

  public E get(int paramInt)
  {
    if (paramInt >= this.size)
      throwIndexOutOfBoundsException(paramInt, this.size);
    return this.array[paramInt];
  }

  public Object[] getArray()
  {
    return this.array;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = this.array;
    int i = 1;
    int j = 0;
    int k = this.size;
    if (j < k)
    {
      Object localObject = arrayOfObject[j];
      int m = i * 31;
      if (localObject == null);
      for (int n = 0; ; n = localObject.hashCode())
      {
        i = m + n;
        j++;
        break;
      }
    }
    return i;
  }

  public int indexOf(Object paramObject)
  {
    Object[] arrayOfObject = this.array;
    int i = this.size;
    if (paramObject != null)
      for (int k = 0; k < i; k++)
        if (paramObject.equals(arrayOfObject[k]))
          return k;
    for (int j = 0; j < i; j++)
      if (arrayOfObject[j] == null)
        return j;
    return -1;
  }

  public boolean isEmpty()
  {
    return this.size == 0;
  }

  public Iterator<E> iterator()
  {
    return new ArrayListIterator(null);
  }

  public int lastIndexOf(Object paramObject)
  {
    Object[] arrayOfObject = this.array;
    if (paramObject != null)
      for (int j = -1 + this.size; j >= 0; j--)
        if (paramObject.equals(arrayOfObject[j]))
          return j;
    for (int i = -1 + this.size; i >= 0; i--)
      if (arrayOfObject[i] == null)
        return i;
    return -1;
  }

  public E remove(int paramInt)
  {
    Object[] arrayOfObject = this.array;
    int i = this.size;
    if (paramInt >= i)
      throwIndexOutOfBoundsException(paramInt, i);
    Object localObject = arrayOfObject[paramInt];
    int j = paramInt + 1;
    int k = i - 1;
    System.arraycopy(arrayOfObject, j, arrayOfObject, paramInt, k - paramInt);
    arrayOfObject[k] = null;
    this.size = k;
    this.modCount = (1 + this.modCount);
    return localObject;
  }

  public boolean remove(Object paramObject)
  {
    Object[] arrayOfObject = this.array;
    int i = this.size;
    if (paramObject != null)
      for (int n = 0; n < i; n++)
        if (paramObject.equals(arrayOfObject[n]))
        {
          int i1 = n + 1;
          int i2 = i - 1;
          System.arraycopy(arrayOfObject, i1, arrayOfObject, n, i2 - n);
          arrayOfObject[i2] = null;
          this.size = i2;
          this.modCount = (1 + this.modCount);
          return true;
        }
    for (int j = 0; j < i; j++)
      if (arrayOfObject[j] == null)
      {
        int k = j + 1;
        int m = i - 1;
        System.arraycopy(arrayOfObject, k, arrayOfObject, j, m - j);
        arrayOfObject[m] = null;
        this.size = m;
        this.modCount = (1 + this.modCount);
        return true;
      }
    return false;
  }

  protected void removeRange(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2)
      return;
    Object[] arrayOfObject = this.array;
    int i = this.size;
    if (paramInt1 >= i)
      throw new IndexOutOfBoundsException("fromIndex " + paramInt1 + " >= size " + this.size);
    if (paramInt2 > i)
      throw new IndexOutOfBoundsException("toIndex " + paramInt2 + " > size " + this.size);
    if (paramInt1 > paramInt2)
      throw new IndexOutOfBoundsException("fromIndex " + paramInt1 + " > toIndex " + paramInt2);
    System.arraycopy(arrayOfObject, paramInt2, arrayOfObject, paramInt1, i - paramInt2);
    int j = paramInt2 - paramInt1;
    Arrays.fill(arrayOfObject, i - j, i, null);
    this.size = (i - j);
    this.modCount = (1 + this.modCount);
  }

  public E set(int paramInt, E paramE)
  {
    Object[] arrayOfObject = this.array;
    if (paramInt >= this.size)
      throwIndexOutOfBoundsException(paramInt, this.size);
    Object localObject = arrayOfObject[paramInt];
    arrayOfObject[paramInt] = paramE;
    return localObject;
  }

  public int size()
  {
    return this.size;
  }

  public Object[] toArray()
  {
    int i = this.size;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(this.array, 0, arrayOfObject, 0, i);
    return arrayOfObject;
  }

  public <T> T[] toArray(T[] paramArrayOfT)
  {
    int i = this.size;
    if (paramArrayOfT.length < i)
      paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
    System.arraycopy(this.array, 0, paramArrayOfT, 0, i);
    if (paramArrayOfT.length > i)
      paramArrayOfT[i] = null;
    return paramArrayOfT;
  }

  public void trimToSize()
  {
    int i = this.size;
    if (i == this.array.length)
      return;
    if (i == 0);
    Object[] arrayOfObject;
    for (this.array = EMPTY_ARRAY; ; this.array = arrayOfObject)
    {
      this.modCount = (1 + this.modCount);
      return;
      arrayOfObject = new Object[i];
      System.arraycopy(this.array, 0, arrayOfObject, 0, i);
    }
  }

  private class ArrayListIterator
    implements Iterator<E>
  {
    private int expectedModCount = XulArrayList.this.modCount;
    private int remaining = XulArrayList.this.size;
    private int removalIndex = -1;

    private ArrayListIterator()
    {
    }

    public boolean hasNext()
    {
      return this.remaining != 0;
    }

    public E next()
    {
      XulArrayList localXulArrayList = XulArrayList.this;
      int i = this.remaining;
      if (localXulArrayList.modCount != this.expectedModCount)
        throw new ConcurrentModificationException();
      if (i == 0)
        throw new NoSuchElementException();
      this.remaining = (i - 1);
      Object[] arrayOfObject = localXulArrayList.array;
      int j = localXulArrayList.size - i;
      this.removalIndex = j;
      return arrayOfObject[j];
    }

    public void remove()
    {
      Object[] arrayOfObject = XulArrayList.this.array;
      int i = this.removalIndex;
      if (XulArrayList.this.modCount != this.expectedModCount)
        throw new ConcurrentModificationException();
      if (i < 0)
        throw new IllegalStateException();
      System.arraycopy(arrayOfObject, i + 1, arrayOfObject, i, this.remaining);
      XulArrayList localXulArrayList = XulArrayList.this;
      int j = -1 + localXulArrayList.size;
      localXulArrayList.size = j;
      arrayOfObject[j] = null;
      this.removalIndex = -1;
      this.expectedModCount = XulArrayList.access$404(XulArrayList.this);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulArrayList
 * JD-Core Version:    0.6.2
 */