package com.starcor.xul.Utils;

import java.util.Arrays;

public class XulIntArray
{
  int[] _buf;
  int _maximumSize;
  int _num;

  public XulIntArray(int paramInt)
  {
    this._buf = new int[paramInt];
    this._maximumSize = paramInt;
    this._num = 0;
  }

  private void _enlargeBuf(int paramInt)
  {
    this._maximumSize = (paramInt + this._maximumSize);
    int[] arrayOfInt = new int[this._maximumSize];
    System.arraycopy(this._buf, 0, arrayOfInt, 0, this._num);
    this._buf = arrayOfInt;
  }

  public void add(int paramInt)
  {
    if (this._num >= this._maximumSize)
      _enlargeBuf(128);
    this._buf[this._num] = paramInt;
    this._num = (1 + this._num);
  }

  public void addAll(XulIntArray paramXulIntArray)
  {
    if (this._num + paramXulIntArray._num >= this._maximumSize)
      _enlargeBuf(0xFFFFFFE0 & 31 + (this._num + paramXulIntArray._num - this._maximumSize));
    System.arraycopy(paramXulIntArray._buf, 0, this._buf, this._num, paramXulIntArray._num);
    this._num += paramXulIntArray._num;
  }

  public void clear()
  {
    if (this._num == 0)
      return;
    Arrays.fill(this._buf, 0, this._num, -1);
    this._num = 0;
  }

  public int get(int paramInt)
  {
    if (paramInt >= this._num)
      return -1;
    return this._buf[paramInt];
  }

  public int pop()
  {
    if (this._num <= 0)
      return -1;
    this._num = (-1 + this._num);
    int i = this._buf[this._num];
    this._buf[this._num] = -1;
    return i;
  }

  public void push(int paramInt)
  {
    add(paramInt);
  }

  public int size()
  {
    return this._num;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulIntArray
 * JD-Core Version:    0.6.2
 */