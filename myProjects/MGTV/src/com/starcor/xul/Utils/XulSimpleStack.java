package com.starcor.xul.Utils;

public class XulSimpleStack<T>
{
  int _maximumSize;
  int _num;
  Object[] _stack;

  public XulSimpleStack(int paramInt)
  {
    this._stack = new Object[paramInt];
    this._maximumSize = paramInt;
    this._num = 0;
  }

  public T pop()
  {
    if (this._num <= 0)
      return null;
    this._num = (-1 + this._num);
    Object localObject = this._stack[this._num];
    this._stack[this._num] = null;
    return localObject;
  }

  public void push(T paramT)
  {
    if (this._num >= this._maximumSize)
    {
      this._maximumSize = (128 + this._maximumSize);
      Object[] arrayOfObject = new Object[this._maximumSize];
      System.arraycopy(this._stack, 0, arrayOfObject, 0, this._num);
      this._stack = arrayOfObject;
    }
    this._stack[this._num] = paramT;
    this._num = (1 + this._num);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulSimpleStack
 * JD-Core Version:    0.6.2
 */