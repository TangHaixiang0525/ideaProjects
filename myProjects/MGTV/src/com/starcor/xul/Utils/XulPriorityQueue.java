package com.starcor.xul.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class XulPriorityQueue<E> extends AbstractQueue<E>
  implements Serializable
{
  private static final int DEFAULT_CAPACITY = 11;
  private static final int DEFAULT_CAPACITY_RATIO = 2;
  private static final double DEFAULT_INIT_CAPACITY_RATIO = 1.1D;
  private static final long serialVersionUID = -7720805057305804111L;
  private Comparator<? super E> comparator;
  private transient E[] elements;
  private int size;

  public XulPriorityQueue()
  {
    this(11);
  }

  public XulPriorityQueue(int paramInt)
  {
    this(paramInt, null);
  }

  public XulPriorityQueue(int paramInt, Comparator<? super E> paramComparator)
  {
    if (paramInt < 1)
      throw new IllegalArgumentException("initialCapacity < 1: " + paramInt);
    this.elements = newElementArray(paramInt);
    this.comparator = paramComparator;
  }

  public XulPriorityQueue(XulPriorityQueue<? extends E> paramXulPriorityQueue)
  {
    getFromPriorityQueue(paramXulPriorityQueue);
  }

  public XulPriorityQueue(Collection<? extends E> paramCollection)
  {
    if ((paramCollection instanceof XulPriorityQueue))
    {
      getFromPriorityQueue((XulPriorityQueue)paramCollection);
      return;
    }
    if ((paramCollection instanceof SortedSet))
    {
      getFromSortedSet((SortedSet)paramCollection);
      return;
    }
    initSize(paramCollection);
    addAll(paramCollection);
  }

  public XulPriorityQueue(SortedSet<? extends E> paramSortedSet)
  {
    getFromSortedSet(paramSortedSet);
  }

  private int compare(E paramE1, E paramE2)
  {
    if (this.comparator != null)
      return this.comparator.compare(paramE1, paramE2);
    return ((Comparable)paramE1).compareTo(paramE2);
  }

  private void getFromPriorityQueue(XulPriorityQueue<? extends E> paramXulPriorityQueue)
  {
    initSize(paramXulPriorityQueue);
    this.comparator = paramXulPriorityQueue.comparator();
    System.arraycopy(paramXulPriorityQueue.elements, 0, this.elements, 0, paramXulPriorityQueue.size());
    this.size = paramXulPriorityQueue.size();
  }

  private void getFromSortedSet(SortedSet<? extends E> paramSortedSet)
  {
    initSize(paramSortedSet);
    this.comparator = paramSortedSet.comparator();
    Iterator localIterator = paramSortedSet.iterator();
    while (localIterator.hasNext())
    {
      Object[] arrayOfObject = this.elements;
      int i = this.size;
      this.size = (i + 1);
      arrayOfObject[i] = localIterator.next();
    }
  }

  private void growToSize(int paramInt)
  {
    if (paramInt > this.elements.length)
    {
      Object[] arrayOfObject = newElementArray(paramInt * 2);
      System.arraycopy(this.elements, 0, arrayOfObject, 0, this.elements.length);
      this.elements = arrayOfObject;
    }
  }

  private void initSize(Collection<? extends E> paramCollection)
  {
    if (paramCollection == null)
      throw new NullPointerException("c == null");
    if (paramCollection.isEmpty())
    {
      this.elements = newElementArray(1);
      return;
    }
    this.elements = newElementArray((int)Math.ceil(1.1D * paramCollection.size()));
  }

  private E[] newElementArray(int paramInt)
  {
    return (Object[])new Object[paramInt];
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    this.elements = newElementArray(paramObjectInputStream.readInt());
    for (int i = 0; i < this.size; i++)
      this.elements[i] = paramObjectInputStream.readObject();
  }

  private void removeAt(int paramInt)
  {
    this.size = (-1 + this.size);
    Object localObject = this.elements[this.size];
    this.elements[paramInt] = localObject;
    siftDown(paramInt);
    this.elements[this.size] = null;
    if (localObject == this.elements[paramInt])
      siftUp(paramInt);
  }

  private void siftDown(int paramInt)
  {
    Object localObject = this.elements[paramInt];
    while (true)
    {
      int i = 1 + paramInt * 2;
      if (i < this.size)
      {
        if ((i + 1 < this.size) && (compare(this.elements[(i + 1)], this.elements[i]) < 0))
          i++;
        if (compare(localObject, this.elements[i]) > 0);
      }
      else
      {
        this.elements[paramInt] = localObject;
        return;
      }
      this.elements[paramInt] = this.elements[i];
      paramInt = i;
    }
  }

  private void siftUp(int paramInt)
  {
    Object localObject1 = this.elements[paramInt];
    while (true)
    {
      int i;
      Object localObject2;
      if (paramInt > 0)
      {
        i = (paramInt - 1) / 2;
        localObject2 = this.elements[i];
        if (compare(localObject2, localObject1) > 0);
      }
      else
      {
        this.elements[paramInt] = localObject1;
        return;
      }
      this.elements[paramInt] = localObject2;
      paramInt = i;
    }
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeInt(this.elements.length);
    for (int i = 0; i < this.size; i++)
      paramObjectOutputStream.writeObject(this.elements[i]);
  }

  public boolean add(E paramE)
  {
    return offer(paramE);
  }

  public void clear()
  {
    Arrays.fill(this.elements, null);
    this.size = 0;
  }

  public Comparator<? super E> comparator()
  {
    return this.comparator;
  }

  public Iterator<E> iterator()
  {
    return new PriorityIterator(null);
  }

  public boolean offer(E paramE)
  {
    if (paramE == null)
      throw new NullPointerException("o == null");
    growToSize(1 + this.size);
    this.elements[this.size] = paramE;
    int i = this.size;
    this.size = (i + 1);
    siftUp(i);
    return true;
  }

  public E peek()
  {
    if (isEmpty())
      return null;
    return this.elements[0];
  }

  public E poll()
  {
    if (isEmpty())
      return null;
    Object localObject = this.elements[0];
    removeAt(0);
    return localObject;
  }

  public boolean remove(Object paramObject)
  {
    if (paramObject == null);
    while (true)
    {
      return false;
      for (int i = 0; i < this.size; i++)
        if (paramObject.equals(this.elements[i]))
        {
          removeAt(i);
          return true;
        }
    }
  }

  public boolean resetIterator(Iterator<E> paramIterator)
  {
    PriorityIterator localPriorityIterator = (PriorityIterator)paramIterator;
    if (!localPriorityIterator.testOwner(this))
      return false;
    PriorityIterator.access$102(localPriorityIterator, -1);
    return false;
  }

  public int size()
  {
    return this.size;
  }

  private class PriorityIterator
    implements Iterator<E>
  {
    private boolean allowRemove = false;
    private int currentIndex = -1;

    private PriorityIterator()
    {
    }

    public boolean hasNext()
    {
      return this.currentIndex < -1 + XulPriorityQueue.this.size;
    }

    public E next()
    {
      if (!hasNext())
        throw new NoSuchElementException();
      this.allowRemove = true;
      Object[] arrayOfObject = XulPriorityQueue.this.elements;
      int i = 1 + this.currentIndex;
      this.currentIndex = i;
      return arrayOfObject[i];
    }

    public void remove()
    {
      if (!this.allowRemove)
        throw new IllegalStateException();
      this.allowRemove = false;
      XulPriorityQueue localXulPriorityQueue = XulPriorityQueue.this;
      int i = this.currentIndex;
      this.currentIndex = (i - 1);
      localXulPriorityQueue.removeAt(i);
    }

    public boolean testOwner(XulPriorityQueue<E> paramXulPriorityQueue)
    {
      return XulPriorityQueue.this.equals(paramXulPriorityQueue);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulPriorityQueue
 * JD-Core Version:    0.6.2
 */