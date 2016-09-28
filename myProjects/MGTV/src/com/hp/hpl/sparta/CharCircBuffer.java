package com.hp.hpl.sparta;

class CharCircBuffer
{
  private final int[] buf_;
  private boolean enabled_ = true;
  private int next_ = 0;
  private int total_ = 0;

  CharCircBuffer(int paramInt)
  {
    this.buf_ = new int[paramInt];
  }

  private void addRaw(int paramInt)
  {
    if (this.enabled_)
    {
      this.buf_[this.next_] = paramInt;
      this.next_ = ((1 + this.next_) % this.buf_.length);
      this.total_ = (1 + this.total_);
    }
  }

  void addChar(char paramChar)
  {
    addRaw(paramChar);
  }

  void addInt(int paramInt)
  {
    addRaw(65536 + paramInt);
  }

  void addString(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      addChar(arrayOfChar[j]);
    }
  }

  void disable()
  {
    this.enabled_ = false;
  }

  void enable()
  {
    this.enabled_ = true;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(11 * this.buf_.length / 10);
    if (this.total_ < this.buf_.length);
    for (int i = this.buf_.length - this.total_; i >= this.buf_.length; i = 0)
      return localStringBuffer.toString();
    int j = (i + this.next_) % this.buf_.length;
    int k = this.buf_[j];
    if (k < 65536)
      localStringBuffer.append((char)k);
    while (true)
    {
      i++;
      break;
      localStringBuffer.append(Integer.toString(k - 65536));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.CharCircBuffer
 * JD-Core Version:    0.6.2
 */