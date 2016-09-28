package com.starcor.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LineReader
{
  public static final String CHARSET_NAME = SYSTEM_CHARSET;
  public static String SYSTEM_CHARSET = "GBK";
  boolean _dropFirstLF = false;
  InputStream _is;
  ArrayList<String> _lines = new ArrayList();
  ByteArrayOutputStream _w = new ByteArrayOutputStream();

  public LineReader(InputStream paramInputStream)
  {
    this._is = paramInputStream;
  }

  private void _prepareLines()
  {
    int i;
    while (true)
    {
      try
      {
        if (this._is.available() <= 0)
          return;
        i = this._is.read();
        if (i == -1)
          break;
        break label163;
        this._w.write(i);
        this._dropFirstLF = false;
        continue;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return;
      }
      if (this._is.available() > 0)
      {
        i = this._is.read();
        if (i == -1)
          this._dropFirstLF = true;
      }
      while (true)
      {
        this._lines.add(this._w.toString(CHARSET_NAME));
        this._w.reset();
        break;
        if (i != 10)
        {
          this._dropFirstLF = false;
          break label163;
          this._dropFirstLF = true;
        }
      }
      if (!this._dropFirstLF)
      {
        this._lines.add(this._w.toString(CHARSET_NAME));
        this._w.reset();
      }
    }
    return;
    label163: switch (i)
    {
    case 11:
    case 12:
    default:
    case 13:
    case 10:
    }
  }

  public void flush()
  {
    if (this._w.size() > 0);
    try
    {
      this._lines.add(this._w.toString(CHARSET_NAME));
      this._w.reset();
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  public String readLine()
  {
    if (this._lines.isEmpty())
      return null;
    return (String)this._lines.remove(0);
  }

  public boolean ready()
  {
    _prepareLines();
    return !this._lines.isEmpty();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.LineReader
 * JD-Core Version:    0.6.2
 */