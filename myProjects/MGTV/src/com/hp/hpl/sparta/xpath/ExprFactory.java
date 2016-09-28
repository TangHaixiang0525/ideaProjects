package com.hp.hpl.sparta.xpath;

import java.io.IOException;

public class ExprFactory
{
  static BooleanExpr createExpr(XPath paramXPath, SimpleStreamTokenizer paramSimpleStreamTokenizer)
    throws XPathException, IOException
  {
    switch (paramSimpleStreamTokenizer.ttype)
    {
    default:
      throw new XPathException(paramXPath, "at beginning of expression", paramSimpleStreamTokenizer, "@, number, or text()");
    case -2:
      int k = paramSimpleStreamTokenizer.nval;
      paramSimpleStreamTokenizer.nextToken();
      return new PositionEqualsExpr(k);
    case 64:
      if (paramSimpleStreamTokenizer.nextToken() != -3)
        throw new XPathException(paramXPath, "after @", paramSimpleStreamTokenizer, "name");
      String str3 = paramSimpleStreamTokenizer.sval;
      switch (paramSimpleStreamTokenizer.nextToken())
      {
      default:
        return new AttrExistsExpr(str3);
      case 61:
        paramSimpleStreamTokenizer.nextToken();
        if ((paramSimpleStreamTokenizer.ttype != 34) && (paramSimpleStreamTokenizer.ttype != 39))
          throw new XPathException(paramXPath, "right hand side of equals", paramSimpleStreamTokenizer, "quoted string");
        String str5 = paramSimpleStreamTokenizer.sval;
        paramSimpleStreamTokenizer.nextToken();
        return new AttrEqualsExpr(str3, str5);
      case 60:
        paramSimpleStreamTokenizer.nextToken();
        if ((paramSimpleStreamTokenizer.ttype == 34) || (paramSimpleStreamTokenizer.ttype == 39));
        for (int j = Integer.parseInt(paramSimpleStreamTokenizer.sval); ; j = paramSimpleStreamTokenizer.nval)
        {
          paramSimpleStreamTokenizer.nextToken();
          return new AttrLessExpr(str3, j);
          if (paramSimpleStreamTokenizer.ttype != -2)
            break;
        }
        throw new XPathException(paramXPath, "right hand side of less-than", paramSimpleStreamTokenizer, "quoted string or number");
      case 62:
        paramSimpleStreamTokenizer.nextToken();
        if ((paramSimpleStreamTokenizer.ttype == 34) || (paramSimpleStreamTokenizer.ttype == 39));
        for (int i = Integer.parseInt(paramSimpleStreamTokenizer.sval); ; i = paramSimpleStreamTokenizer.nval)
        {
          paramSimpleStreamTokenizer.nextToken();
          return new AttrGreaterExpr(str3, i);
          if (paramSimpleStreamTokenizer.ttype != -2)
            break;
        }
        throw new XPathException(paramXPath, "right hand side of greater-than", paramSimpleStreamTokenizer, "quoted string or number");
      case 33:
      }
      paramSimpleStreamTokenizer.nextToken();
      if (paramSimpleStreamTokenizer.ttype != 61)
        throw new XPathException(paramXPath, "after !", paramSimpleStreamTokenizer, "=");
      paramSimpleStreamTokenizer.nextToken();
      if ((paramSimpleStreamTokenizer.ttype != 34) && (paramSimpleStreamTokenizer.ttype != 39))
        throw new XPathException(paramXPath, "right hand side of !=", paramSimpleStreamTokenizer, "quoted string");
      String str4 = paramSimpleStreamTokenizer.sval;
      paramSimpleStreamTokenizer.nextToken();
      return new AttrNotEqualsExpr(str3, str4);
    case -3:
    }
    if (!paramSimpleStreamTokenizer.sval.equals("text"))
      throw new XPathException(paramXPath, "at beginning of expression", paramSimpleStreamTokenizer, "text()");
    if (paramSimpleStreamTokenizer.nextToken() != 40)
      throw new XPathException(paramXPath, "after text", paramSimpleStreamTokenizer, "(");
    if (paramSimpleStreamTokenizer.nextToken() != 41)
      throw new XPathException(paramXPath, "after text(", paramSimpleStreamTokenizer, ")");
    switch (paramSimpleStreamTokenizer.nextToken())
    {
    default:
      return TextExistsExpr.INSTANCE;
    case 61:
      paramSimpleStreamTokenizer.nextToken();
      if ((paramSimpleStreamTokenizer.ttype != 34) && (paramSimpleStreamTokenizer.ttype != 39))
        throw new XPathException(paramXPath, "right hand side of equals", paramSimpleStreamTokenizer, "quoted string");
      String str2 = paramSimpleStreamTokenizer.sval;
      paramSimpleStreamTokenizer.nextToken();
      return new TextEqualsExpr(str2);
    case 33:
    }
    paramSimpleStreamTokenizer.nextToken();
    if (paramSimpleStreamTokenizer.ttype != 61)
      throw new XPathException(paramXPath, "after !", paramSimpleStreamTokenizer, "=");
    paramSimpleStreamTokenizer.nextToken();
    if ((paramSimpleStreamTokenizer.ttype != 34) && (paramSimpleStreamTokenizer.ttype != 39))
      throw new XPathException(paramXPath, "right hand side of !=", paramSimpleStreamTokenizer, "quoted string");
    String str1 = paramSimpleStreamTokenizer.sval;
    paramSimpleStreamTokenizer.nextToken();
    return new TextNotEqualsExpr(str1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.ExprFactory
 * JD-Core Version:    0.6.2
 */