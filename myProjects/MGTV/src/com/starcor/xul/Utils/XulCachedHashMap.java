package com.starcor.xul.Utils;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class XulCachedHashMap<K, V> extends AbstractMap<K, V>
  implements Cloneable, Serializable
{
  static final float DEFAULT_LOAD_FACTOR = 0.75F;
  private static final Map.Entry[] EMPTY_TABLE = new HashMapEntry[2];
  private static final int MAXIMUM_CAPACITY = 1073741824;
  private static final int MINIMUM_CAPACITY = 4;
  static final ArrayList<HashMapEntry> _cachedEntries = new ArrayList(4096);
  private static final ObjectStreamField[] serialPersistentFields;
  private static final long serialVersionUID = 362498820763181265L;
  transient HashMapEntry<K, V> entryForNullKey;
  private transient Set<Map.Entry<K, V>> entrySet;
  private transient Set<K> keySet;
  transient int modCount;
  transient int size;
  transient HashMapEntry<K, V>[] table;
  private transient int threshold;
  private transient Collection<V> values;

  static
  {
    ObjectStreamField[] arrayOfObjectStreamField = new ObjectStreamField[1];
    arrayOfObjectStreamField[0] = new ObjectStreamField("loadFactor", Float.TYPE);
    serialPersistentFields = arrayOfObjectStreamField;
  }

  public XulCachedHashMap()
  {
    this.table = ((HashMapEntry[])EMPTY_TABLE);
    this.threshold = -1;
  }

  public XulCachedHashMap(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("Capacity: " + paramInt);
    if (paramInt == 0)
    {
      this.table = ((HashMapEntry[])EMPTY_TABLE);
      this.threshold = -1;
      return;
    }
    int i;
    if (paramInt < 4)
      i = 4;
    while (true)
    {
      makeTable(i);
      return;
      if (paramInt > 1073741824)
        i = 1073741824;
      else
        i = roundUpToPowerOfTwo(paramInt);
    }
  }

  public XulCachedHashMap(int paramInt, float paramFloat)
  {
    this(paramInt);
    if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat)))
      throw new IllegalArgumentException("Load factor: " + paramFloat);
  }

  public XulCachedHashMap(Map<? extends K, ? extends V> paramMap)
  {
    this(capacityForInitSize(paramMap.size()));
    constructorPutAll(paramMap);
  }

  static int capacityForInitSize(int paramInt)
  {
    int i = paramInt + (paramInt >> 1);
    if ((0xC0000000 & i) == 0)
      return i;
    return 1073741824;
  }

  static <K, V> HashMapEntry<K, V> constructorNewEntry(K paramK, V paramV, int paramInt, HashMapEntry<K, V> paramHashMapEntry)
  {
    synchronized (_cachedEntries)
    {
      if (_cachedEntries.isEmpty())
      {
        HashMapEntry localHashMapEntry1 = new HashMapEntry(paramK, paramV, paramInt, paramHashMapEntry);
        return localHashMapEntry1;
      }
      int i = -1 + _cachedEntries.size();
      HashMapEntry localHashMapEntry2 = (HashMapEntry)_cachedEntries.remove(i);
      if (localHashMapEntry2.next != null)
        _cachedEntries.add(localHashMapEntry2.next);
      HashMapEntry localHashMapEntry3 = localHashMapEntry2.update(paramK, paramV, paramInt, paramHashMapEntry);
      return localHashMapEntry3;
    }
  }

  private void constructorPut(K paramK, V paramV)
  {
    if (paramK == null)
    {
      HashMapEntry localHashMapEntry3 = this.entryForNullKey;
      if (localHashMapEntry3 == null)
      {
        this.entryForNullKey = constructorNewEntry(null, paramV, 0, null);
        this.size = (1 + this.size);
        return;
      }
      localHashMapEntry3.value = paramV;
      return;
    }
    int i = secondaryHash(paramK);
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    int j = i & -1 + arrayOfHashMapEntry.length;
    HashMapEntry localHashMapEntry1 = arrayOfHashMapEntry[j];
    for (HashMapEntry localHashMapEntry2 = localHashMapEntry1; localHashMapEntry2 != null; localHashMapEntry2 = localHashMapEntry2.next)
      if ((localHashMapEntry2.hash == i) && (paramK.equals(localHashMapEntry2.key)))
      {
        localHashMapEntry2.value = paramV;
        return;
      }
    arrayOfHashMapEntry[j] = constructorNewEntry(paramK, paramV, i, localHashMapEntry1);
    this.size = (1 + this.size);
  }

  private boolean containsMapping(Object paramObject1, Object paramObject2)
  {
    boolean bool1;
    if (paramObject1 == null)
    {
      HashMapEntry localHashMapEntry2 = this.entryForNullKey;
      bool1 = false;
      if (localHashMapEntry2 != null)
      {
        boolean bool2 = testEquals(paramObject2, localHashMapEntry2.value);
        bool1 = false;
        if (bool2)
          bool1 = true;
      }
      return bool1;
    }
    int i = secondaryHash(paramObject1);
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    for (HashMapEntry localHashMapEntry1 = arrayOfHashMapEntry[(i & -1 + arrayOfHashMapEntry.length)]; ; localHashMapEntry1 = localHashMapEntry1.next)
    {
      bool1 = false;
      if (localHashMapEntry1 == null)
        break;
      if ((localHashMapEntry1.hash == i) && (paramObject1.equals(localHashMapEntry1.key)))
        return testEquals(paramObject2, localHashMapEntry1.value);
    }
  }

  private HashMapEntry<K, V>[] doubleCapacity()
  {
    HashMapEntry[] arrayOfHashMapEntry1 = this.table;
    int i = arrayOfHashMapEntry1.length;
    HashMapEntry[] arrayOfHashMapEntry2;
    if (i == 1073741824)
      arrayOfHashMapEntry2 = arrayOfHashMapEntry1;
    do
    {
      return arrayOfHashMapEntry2;
      arrayOfHashMapEntry2 = makeTable(i * 2);
    }
    while (this.size == 0);
    int j = 0;
    label36: Object localObject1;
    if (j < i)
    {
      localObject1 = arrayOfHashMapEntry1[j];
      if (localObject1 != null)
        break label59;
    }
    while (true)
    {
      j++;
      break label36;
      break;
      label59: int k = i & ((HashMapEntry)localObject1).hash;
      Object localObject2 = null;
      arrayOfHashMapEntry2[(j | k)] = localObject1;
      HashMapEntry localHashMapEntry = ((HashMapEntry)localObject1).next;
      if (localHashMapEntry != null)
      {
        int m = i & localHashMapEntry.hash;
        if (m != k)
        {
          if (localObject2 != null)
            break label144;
          arrayOfHashMapEntry2[(j | m)] = localHashMapEntry;
        }
        while (true)
        {
          localObject2 = localObject1;
          k = m;
          localObject1 = localHashMapEntry;
          localHashMapEntry = localHashMapEntry.next;
          break;
          label144: localObject2.next = localHashMapEntry;
        }
      }
      if (localObject2 != null)
        localObject2.next = null;
    }
  }

  private void ensureCapacity(int paramInt)
  {
    int i = roundUpToPowerOfTwo(capacityForInitSize(paramInt));
    HashMapEntry[] arrayOfHashMapEntry1 = this.table;
    int j = arrayOfHashMapEntry1.length;
    if (i <= j);
    while (true)
    {
      return;
      if (i == j * 2)
      {
        doubleCapacity();
        return;
      }
      HashMapEntry[] arrayOfHashMapEntry2 = makeTable(i);
      if (this.size != 0)
      {
        int k = i - 1;
        for (int m = 0; m < j; m++)
        {
          HashMapEntry localHashMapEntry1;
          for (Object localObject = arrayOfHashMapEntry1[m]; localObject != null; localObject = localHashMapEntry1)
          {
            localHashMapEntry1 = ((HashMapEntry)localObject).next;
            int n = k & ((HashMapEntry)localObject).hash;
            HashMapEntry localHashMapEntry2 = arrayOfHashMapEntry2[n];
            arrayOfHashMapEntry2[n] = localObject;
            ((HashMapEntry)localObject).next = localHashMapEntry2;
          }
        }
      }
    }
  }

  private HashMapEntry<K, V>[] makeTable(int paramInt)
  {
    HashMapEntry[] arrayOfHashMapEntry = (HashMapEntry[])new HashMapEntry[paramInt];
    this.table = arrayOfHashMapEntry;
    this.threshold = ((paramInt >> 1) + (paramInt >> 2));
    return arrayOfHashMapEntry;
  }

  static <K, V> void postRemove(HashMapEntry<K, V> paramHashMapEntry)
  {
    paramHashMapEntry.next = null;
    synchronized (_cachedEntries)
    {
      recycleEntry(paramHashMapEntry);
      return;
    }
  }

  private V putValueForNullKey(V paramV)
  {
    HashMapEntry localHashMapEntry = this.entryForNullKey;
    if (localHashMapEntry == null)
    {
      addNewEntryForNullKey(paramV);
      this.size = (1 + this.size);
      this.modCount = (1 + this.modCount);
      return null;
    }
    preModify(localHashMapEntry);
    Object localObject = localHashMapEntry.value;
    localHashMapEntry.value = paramV;
    return localObject;
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    int i = paramObjectInputStream.readInt();
    if (i < 0)
      throw new InvalidObjectException("Capacity: " + i);
    int j;
    if (i < 4)
      j = 4;
    int k;
    while (true)
    {
      makeTable(j);
      k = paramObjectInputStream.readInt();
      if (k >= 0)
        break;
      throw new InvalidObjectException("Size: " + k);
      if (i > 1073741824)
        j = 1073741824;
      else
        j = roundUpToPowerOfTwo(i);
    }
    init();
    for (int m = 0; m < k; m++)
      constructorPut(paramObjectInputStream.readObject(), paramObjectInputStream.readObject());
  }

  private static <K, V> void recycleEntry(HashMapEntry<K, V> paramHashMapEntry)
  {
    paramHashMapEntry.key = null;
    paramHashMapEntry.value = null;
    _cachedEntries.add(paramHashMapEntry);
  }

  static <K, V> void recycleTable(HashMapEntry<K, V>[] paramArrayOfHashMapEntry)
  {
    ArrayList localArrayList = _cachedEntries;
    for (int i = 0; ; i++)
      try
      {
        int j = paramArrayOfHashMapEntry.length;
        if (i < j)
        {
          HashMapEntry<K, V> localHashMapEntry = paramArrayOfHashMapEntry[i];
          if (localHashMapEntry != null)
            recycleEntry(localHashMapEntry);
        }
        else
        {
          return;
        }
      }
      finally
      {
      }
  }

  private boolean removeMapping(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null)
    {
      HashMapEntry localHashMapEntry3 = this.entryForNullKey;
      if ((localHashMapEntry3 == null) || (!testEquals(paramObject2, localHashMapEntry3.value)))
        return false;
      this.entryForNullKey = null;
      this.modCount = (1 + this.modCount);
      this.size = (-1 + this.size);
      postRemove(localHashMapEntry3);
      return true;
    }
    int i = secondaryHash(paramObject1);
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    int j = i & -1 + arrayOfHashMapEntry.length;
    HashMapEntry localHashMapEntry1 = arrayOfHashMapEntry[j];
    HashMapEntry localHashMapEntry2 = null;
    while (localHashMapEntry1 != null)
    {
      if ((localHashMapEntry1.hash == i) && (paramObject1.equals(localHashMapEntry1.key)))
      {
        if (!testEquals(paramObject2, localHashMapEntry1.value))
          return false;
        if (localHashMapEntry2 == null)
          arrayOfHashMapEntry[j] = localHashMapEntry1.next;
        while (true)
        {
          this.modCount = (1 + this.modCount);
          this.size = (-1 + this.size);
          postRemove(localHashMapEntry1);
          return true;
          localHashMapEntry2.next = localHashMapEntry1.next;
        }
      }
      localHashMapEntry2 = localHashMapEntry1;
      localHashMapEntry1 = localHashMapEntry1.next;
    }
    return false;
  }

  private V removeNullKey()
  {
    HashMapEntry localHashMapEntry = this.entryForNullKey;
    if (localHashMapEntry == null)
      return null;
    this.entryForNullKey = null;
    this.modCount = (1 + this.modCount);
    this.size = (-1 + this.size);
    Object localObject = localHashMapEntry.value;
    postRemove(localHashMapEntry);
    return localObject;
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

  static int secondaryHash(Object paramObject)
  {
    int i = paramObject.hashCode();
    int j = i ^ (i >>> 20 ^ i >>> 12);
    return j ^ (j >>> 7 ^ j >>> 4);
  }

  private static <K, V> boolean testEquals(K paramK, V paramV)
  {
    return (paramK == paramV) || ((paramK != null) && (paramK.equals(paramV)));
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.putFields().put("loadFactor", 0.75F);
    paramObjectOutputStream.writeFields();
    paramObjectOutputStream.writeInt(this.table.length);
    paramObjectOutputStream.writeInt(this.size);
    Iterator localIterator = entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      paramObjectOutputStream.writeObject(localEntry.getKey());
      paramObjectOutputStream.writeObject(localEntry.getValue());
    }
  }

  void addNewEntry(K paramK, V paramV, int paramInt1, int paramInt2)
  {
    this.table[paramInt2] = constructorNewEntry(paramK, paramV, paramInt1, this.table[paramInt2]);
  }

  void addNewEntryForNullKey(V paramV)
  {
    this.entryForNullKey = constructorNewEntry(null, paramV, 0, null);
  }

  public void clear()
  {
    if (this.size != 0)
    {
      recycleTable(this.table);
      Arrays.fill(this.table, null);
      HashMapEntry localHashMapEntry = this.entryForNullKey;
      this.entryForNullKey = null;
      if (localHashMapEntry != null)
        postRemove(localHashMapEntry);
      this.modCount = (1 + this.modCount);
      this.size = 0;
    }
  }

  public Object clone()
  {
    try
    {
      XulCachedHashMap localXulCachedHashMap = (XulCachedHashMap)super.clone();
      localXulCachedHashMap.makeTable(this.table.length);
      localXulCachedHashMap.entryForNullKey = null;
      localXulCachedHashMap.size = 0;
      localXulCachedHashMap.keySet = null;
      localXulCachedHashMap.entrySet = null;
      localXulCachedHashMap.values = null;
      localXulCachedHashMap.init();
      localXulCachedHashMap.constructorPutAll(this);
      return localXulCachedHashMap;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError(localCloneNotSupportedException);
    }
  }

  final void constructorPutAll(Map<? extends K, ? extends V> paramMap)
  {
    if (this.table == EMPTY_TABLE)
      doubleCapacity();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      constructorPut(localEntry.getKey(), localEntry.getValue());
    }
  }

  public boolean containsKey(Object paramObject)
  {
    if (paramObject == null)
      return this.entryForNullKey != null;
    int i = paramObject.hashCode();
    int j = i ^ (i >>> 20 ^ i >>> 12);
    int k = j ^ (j >>> 7 ^ j >>> 4);
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    for (HashMapEntry localHashMapEntry = arrayOfHashMapEntry[(k & -1 + arrayOfHashMapEntry.length)]; ; localHashMapEntry = localHashMapEntry.next)
    {
      if (localHashMapEntry == null)
        break label110;
      Object localObject = localHashMapEntry.key;
      if ((localObject == paramObject) || ((localHashMapEntry.hash == k) && (paramObject.equals(localObject))))
        break;
    }
    label110: return false;
  }

  public boolean containsValue(Object paramObject)
  {
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    int i = arrayOfHashMapEntry.length;
    int k;
    HashMapEntry localHashMapEntry2;
    if (paramObject == null)
    {
      k = 0;
      if (k < i)
      {
        localHashMapEntry2 = arrayOfHashMapEntry[k];
        label27: if (localHashMapEntry2 != null)
          if (localHashMapEntry2.value != null);
      }
    }
    label119: label125: 
    do
    {
      do
      {
        return true;
        localHashMapEntry2 = localHashMapEntry2.next;
        break label27;
        k++;
        break;
      }
      while ((this.entryForNullKey != null) && (this.entryForNullKey.value == null));
      return false;
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label125;
        for (HashMapEntry localHashMapEntry1 = arrayOfHashMapEntry[j]; ; localHashMapEntry1 = localHashMapEntry1.next)
        {
          if (localHashMapEntry1 == null)
            break label119;
          if (paramObject.equals(localHashMapEntry1.value))
            break;
        }
      }
    }
    while ((this.entryForNullKey != null) && (paramObject.equals(this.entryForNullKey.value)));
    return false;
  }

  public Set<Map.Entry<K, V>> entrySet()
  {
    Set localSet = this.entrySet;
    if (localSet != null)
      return localSet;
    EntrySet localEntrySet = new EntrySet(null);
    this.entrySet = localEntrySet;
    return localEntrySet;
  }

  public V get(Object paramObject)
  {
    HashMapEntry localHashMapEntry2;
    if (paramObject == null)
    {
      localHashMapEntry2 = this.entryForNullKey;
      if (localHashMapEntry2 != null);
    }
    while (true)
    {
      return null;
      return localHashMapEntry2.value;
      int i = paramObject.hashCode();
      int j = i ^ (i >>> 20 ^ i >>> 12);
      int k = j ^ (j >>> 7 ^ j >>> 4);
      HashMapEntry[] arrayOfHashMapEntry = this.table;
      for (HashMapEntry localHashMapEntry1 = arrayOfHashMapEntry[(k & -1 + arrayOfHashMapEntry.length)]; localHashMapEntry1 != null; localHashMapEntry1 = localHashMapEntry1.next)
      {
        Object localObject = localHashMapEntry1.key;
        if ((localObject == paramObject) || ((localHashMapEntry1.hash == k) && (paramObject.equals(localObject))))
          return localHashMapEntry1.value;
      }
    }
  }

  void init()
  {
  }

  public boolean isEmpty()
  {
    return this.size == 0;
  }

  public Set<K> keySet()
  {
    Set localSet = this.keySet;
    if (localSet != null)
      return localSet;
    KeySet localKeySet = new KeySet(null);
    this.keySet = localKeySet;
    return localKeySet;
  }

  Iterator<Map.Entry<K, V>> newEntryIterator()
  {
    return new EntryIterator(null);
  }

  Iterator<K> newKeyIterator()
  {
    return new KeyIterator(null);
  }

  Iterator<V> newValueIterator()
  {
    return new ValueIterator(null);
  }

  void preModify(HashMapEntry<K, V> paramHashMapEntry)
  {
  }

  public V put(K paramK, V paramV)
  {
    if (paramK == null)
      return putValueForNullKey(paramV);
    int i = secondaryHash(paramK);
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    int j = i & -1 + arrayOfHashMapEntry.length;
    for (HashMapEntry localHashMapEntry = arrayOfHashMapEntry[j]; localHashMapEntry != null; localHashMapEntry = localHashMapEntry.next)
      if ((localHashMapEntry.hash == i) && (paramK.equals(localHashMapEntry.key)))
      {
        preModify(localHashMapEntry);
        Object localObject = localHashMapEntry.value;
        localHashMapEntry.value = paramV;
        return localObject;
      }
    this.modCount = (1 + this.modCount);
    int k = this.size;
    this.size = (k + 1);
    if (k > this.threshold)
      j = i & -1 + doubleCapacity().length;
    addNewEntry(paramK, paramV, i, j);
    return null;
  }

  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    ensureCapacity(paramMap.size());
    super.putAll(paramMap);
  }

  public V remove(Object paramObject)
  {
    if (paramObject == null)
      return removeNullKey();
    int i = secondaryHash(paramObject);
    HashMapEntry[] arrayOfHashMapEntry = this.table;
    int j = i & -1 + arrayOfHashMapEntry.length;
    HashMapEntry localHashMapEntry1 = arrayOfHashMapEntry[j];
    HashMapEntry localHashMapEntry2 = null;
    while (localHashMapEntry1 != null)
    {
      if ((localHashMapEntry1.hash == i) && (paramObject.equals(localHashMapEntry1.key)))
      {
        if (localHashMapEntry2 == null)
          arrayOfHashMapEntry[j] = localHashMapEntry1.next;
        while (true)
        {
          this.modCount = (1 + this.modCount);
          this.size = (-1 + this.size);
          Object localObject = localHashMapEntry1.value;
          postRemove(localHashMapEntry1);
          return localObject;
          localHashMapEntry2.next = localHashMapEntry1.next;
        }
      }
      localHashMapEntry2 = localHashMapEntry1;
      localHashMapEntry1 = localHashMapEntry1.next;
    }
    return null;
  }

  public int size()
  {
    return this.size;
  }

  public Collection<V> values()
  {
    Collection localCollection = this.values;
    if (localCollection != null)
      return localCollection;
    Values localValues = new Values(null);
    this.values = localValues;
    return localValues;
  }

  private final class EntryIterator extends XulCachedHashMap<K, V>.HashIterator
    implements Iterator<Map.Entry<K, V>>
  {
    private EntryIterator()
    {
      super();
    }

    public Map.Entry<K, V> next()
    {
      return nextEntry();
    }
  }

  private final class EntrySet extends AbstractSet<Map.Entry<K, V>>
  {
    private EntrySet()
    {
    }

    public void clear()
    {
      XulCachedHashMap.this.clear();
    }

    public boolean contains(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry))
        return false;
      Map.Entry localEntry = (Map.Entry)paramObject;
      return XulCachedHashMap.this.containsMapping(localEntry.getKey(), localEntry.getValue());
    }

    public boolean isEmpty()
    {
      return XulCachedHashMap.this.size == 0;
    }

    public Iterator<Map.Entry<K, V>> iterator()
    {
      return XulCachedHashMap.this.newEntryIterator();
    }

    public boolean remove(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry))
        return false;
      Map.Entry localEntry = (Map.Entry)paramObject;
      return XulCachedHashMap.this.removeMapping(localEntry.getKey(), localEntry.getValue());
    }

    public int size()
    {
      return XulCachedHashMap.this.size;
    }
  }

  private abstract class HashIterator
  {
    int expectedModCount = XulCachedHashMap.this.modCount;
    XulCachedHashMap.HashMapEntry<K, V> lastEntryReturned;
    XulCachedHashMap.HashMapEntry<K, V> nextEntry = XulCachedHashMap.this.entryForNullKey;
    int nextIndex;

    HashIterator()
    {
      if (this.nextEntry == null)
      {
        XulCachedHashMap.HashMapEntry[] arrayOfHashMapEntry = XulCachedHashMap.this.table;
        int i;
        for (XulCachedHashMap.HashMapEntry localHashMapEntry = null; (localHashMapEntry == null) && (this.nextIndex < arrayOfHashMapEntry.length); localHashMapEntry = arrayOfHashMapEntry[i])
        {
          i = this.nextIndex;
          this.nextIndex = (i + 1);
        }
        this.nextEntry = localHashMapEntry;
      }
    }

    public boolean hasNext()
    {
      return this.nextEntry != null;
    }

    XulCachedHashMap.HashMapEntry<K, V> nextEntry()
    {
      if (XulCachedHashMap.this.modCount != this.expectedModCount)
        throw new ConcurrentModificationException();
      if (this.nextEntry == null)
        throw new NoSuchElementException();
      XulCachedHashMap.HashMapEntry localHashMapEntry1 = this.nextEntry;
      XulCachedHashMap.HashMapEntry[] arrayOfHashMapEntry = XulCachedHashMap.this.table;
      int i;
      for (XulCachedHashMap.HashMapEntry localHashMapEntry2 = localHashMapEntry1.next; (localHashMapEntry2 == null) && (this.nextIndex < arrayOfHashMapEntry.length); localHashMapEntry2 = arrayOfHashMapEntry[i])
      {
        i = this.nextIndex;
        this.nextIndex = (i + 1);
      }
      this.nextEntry = localHashMapEntry2;
      this.lastEntryReturned = localHashMapEntry1;
      return localHashMapEntry1;
    }

    public void remove()
    {
      if (this.lastEntryReturned == null)
        throw new IllegalStateException();
      if (XulCachedHashMap.this.modCount != this.expectedModCount)
        throw new ConcurrentModificationException();
      XulCachedHashMap.this.remove(this.lastEntryReturned.key);
      this.lastEntryReturned = null;
      this.expectedModCount = XulCachedHashMap.this.modCount;
    }
  }

  static class HashMapEntry<K, V>
    implements Map.Entry<K, V>
  {
    int hash;
    K key;
    HashMapEntry<K, V> next;
    V value;

    HashMapEntry(K paramK, V paramV, int paramInt, HashMapEntry<K, V> paramHashMapEntry)
    {
      update(paramK, paramV, paramInt, paramHashMapEntry);
    }

    public final boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry));
      Map.Entry localEntry;
      do
      {
        return false;
        localEntry = (Map.Entry)paramObject;
      }
      while ((!XulCachedHashMap.testEquals(localEntry.getKey(), this.key)) || (!XulCachedHashMap.testEquals(localEntry.getValue(), this.value)));
      return true;
    }

    public final K getKey()
    {
      return this.key;
    }

    public final V getValue()
    {
      return this.value;
    }

    public final int hashCode()
    {
      int i;
      int j;
      if (this.key == null)
      {
        i = 0;
        Object localObject = this.value;
        j = 0;
        if (localObject != null)
          break label35;
      }
      while (true)
      {
        return i ^ j;
        i = this.key.hashCode();
        break;
        label35: j = this.value.hashCode();
      }
    }

    public final V setValue(V paramV)
    {
      Object localObject = this.value;
      this.value = paramV;
      return localObject;
    }

    public final String toString()
    {
      return this.key + "=" + this.value;
    }

    HashMapEntry<K, V> update(K paramK, V paramV, int paramInt, HashMapEntry<K, V> paramHashMapEntry)
    {
      this.key = paramK;
      this.value = paramV;
      this.hash = paramInt;
      this.next = paramHashMapEntry;
      return this;
    }
  }

  private final class KeyIterator extends XulCachedHashMap<K, V>.HashIterator
    implements Iterator<K>
  {
    private KeyIterator()
    {
      super();
    }

    public K next()
    {
      return nextEntry().key;
    }
  }

  private final class KeySet extends AbstractSet<K>
  {
    private KeySet()
    {
    }

    public void clear()
    {
      XulCachedHashMap.this.clear();
    }

    public boolean contains(Object paramObject)
    {
      return XulCachedHashMap.this.containsKey(paramObject);
    }

    public boolean isEmpty()
    {
      return XulCachedHashMap.this.size == 0;
    }

    public Iterator<K> iterator()
    {
      return XulCachedHashMap.this.newKeyIterator();
    }

    public boolean remove(Object paramObject)
    {
      int i = XulCachedHashMap.this.size;
      XulCachedHashMap.this.remove(paramObject);
      return XulCachedHashMap.this.size != i;
    }

    public int size()
    {
      return XulCachedHashMap.this.size;
    }
  }

  private final class ValueIterator extends XulCachedHashMap<K, V>.HashIterator
    implements Iterator<V>
  {
    private ValueIterator()
    {
      super();
    }

    public V next()
    {
      return nextEntry().value;
    }
  }

  private final class Values extends AbstractCollection<V>
  {
    private Values()
    {
    }

    public void clear()
    {
      XulCachedHashMap.this.clear();
    }

    public boolean contains(Object paramObject)
    {
      return XulCachedHashMap.this.containsValue(paramObject);
    }

    public boolean isEmpty()
    {
      return XulCachedHashMap.this.size == 0;
    }

    public Iterator<V> iterator()
    {
      return XulCachedHashMap.this.newValueIterator();
    }

    public int size()
    {
      return XulCachedHashMap.this.size;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulCachedHashMap
 * JD-Core Version:    0.6.2
 */