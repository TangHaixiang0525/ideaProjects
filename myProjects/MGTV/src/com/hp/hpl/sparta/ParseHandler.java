package com.hp.hpl.sparta;

public abstract interface ParseHandler
{
  public abstract void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws ParseException;

  public abstract void endDocument()
    throws ParseException;

  public abstract void endElement(Element paramElement)
    throws ParseException;

  public abstract ParseSource getParseSource();

  public abstract void setParseSource(ParseSource paramParseSource);

  public abstract void startDocument()
    throws ParseException;

  public abstract void startElement(Element paramElement)
    throws ParseException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.ParseHandler
 * JD-Core Version:    0.6.2
 */