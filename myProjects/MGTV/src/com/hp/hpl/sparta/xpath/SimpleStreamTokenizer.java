package com.hp.hpl.sparta.xpath;

import java.io.IOException;
import java.io.Reader;

public class SimpleStreamTokenizer
{
  private static final int QUOTE = -6;
  public static final int TT_EOF = -1;
  public static final int TT_NUMBER = -2;
  public static final int TT_WORD = -3;
  private static final int WHITESPACE = -5;
  private final StringBuffer buf_ = new StringBuffer();
  private final int[] charType_ = new int[256];
  private char inQuote_ = '\000';
  private int nextType_;
  public int nval = -2147483648;
  private boolean pushedBack_ = false;
  private final Reader reader_;
  public String sval = "";
  public int ttype = -2147483648;

  public SimpleStreamTokenizer(Reader paramReader)
    throws IOException
  {
    this.reader_ = paramReader;
    if (i >= this.charType_.length)
    {
      nextToken();
      return;
    }
    if (((65 <= i) && (i <= 90)) || ((97 <= i) && (i <= 122)) || (i == 45))
      this.charType_[i] = -3;
    while (true)
    {
      i = (char)(i + 1);
      break;
      if ((48 <= i) && (i <= 57))
        this.charType_[i] = -2;
      else if ((i >= 0) && (i <= 32))
        this.charType_[i] = -5;
      else
        this.charType_[i] = i;
    }
  }

  public int nextToken()
    throws IOException
  {
    if (this.pushedBack_)
    {
      this.pushedBack_ = false;
      return this.ttype;
    }
    this.ttype = this.nextType_;
    int i = 0;
    int j;
    int k;
    label59: int m;
    label75: label86: 
    do
    {
      j = this.reader_.read();
      if (j != -1)
        break;
      if (this.inQuote_ != 0)
        throw new IOException("Unterminated quote");
      k = -1;
      if ((this.inQuote_ != 0) || (k != -5))
        break label304;
      m = 1;
      if ((i == 0) && (m == 0))
        break label310;
      i = 1;
    }
    while (m != 0);
    label116: int n;
    label169: label220: int i1;
    if ((k == 39) || (k == 34))
    {
      if (this.inQuote_ == 0)
        this.inQuote_ = ((char)k);
    }
    else
    {
      if (this.inQuote_ != 0)
        k = this.inQuote_;
      if ((i == 0) && ((this.ttype < -1) || (this.ttype == 39) || (this.ttype == 34)) && (this.ttype == k))
        break label331;
      n = 1;
      if (n != 0);
      switch (this.ttype)
      {
      default:
        if (k != -5)
        {
          if (k == -6)
          {
            i1 = j;
            label235: this.nextType_ = i1;
          }
        }
        else
          switch (k)
          {
          default:
          case -3:
          case -2:
          case 34:
          case 39:
          }
        break;
      case -3:
      case 34:
      case 39:
      case -2:
      }
    }
    while (n != 0)
    {
      return this.ttype;
      k = this.charType_[j];
      break label59;
      label304: m = 0;
      break label75;
      label310: i = 0;
      break label86;
      if (this.inQuote_ != k)
        break label116;
      this.inQuote_ = '\000';
      break label116;
      label331: n = 0;
      break label169;
      this.sval = this.buf_.toString();
      this.buf_.setLength(0);
      break label220;
      this.sval = this.buf_.toString().substring(1, -1 + this.buf_.length());
      this.buf_.setLength(0);
      break label220;
      this.nval = Integer.parseInt(this.buf_.toString());
      this.buf_.setLength(0);
      break label220;
      i1 = k;
      break label235;
      this.buf_.append((char)j);
    }
  }

  public void ordinaryChar(char paramChar)
  {
    this.charType_[paramChar] = paramChar;
  }

  public void pushBack()
  {
    this.pushedBack_ = true;
  }

  public String toString()
  {
    switch (this.ttype)
    {
    default:
      return "'" + (char)this.ttype + "'";
    case -2:
      return Integer.toString(this.nval);
    case -3:
    case 34:
      return "\"" + this.sval + "\"";
    case 39:
      return "'" + this.sval + "'";
    case -1:
    }
    return "(EOF)";
  }

  public void wordChars(char paramChar1, char paramChar2)
  {
    while (true)
    {
      if (paramChar1 > paramChar2)
        return;
      this.charType_[paramChar1] = -3;
      paramChar1 = (char)(paramChar1 + '\001');
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.SimpleStreamTokenizer
 * JD-Core Version:    0.6.2
 */