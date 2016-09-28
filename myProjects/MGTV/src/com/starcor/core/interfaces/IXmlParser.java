package com.starcor.core.interfaces;

import java.io.InputStream;

public abstract interface IXmlParser<Result>
{
  public abstract Result parser(InputStream paramInputStream);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.interfaces.IXmlParser
 * JD-Core Version:    0.6.2
 */