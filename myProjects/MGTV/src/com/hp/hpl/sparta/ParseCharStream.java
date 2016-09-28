package com.hp.hpl.sparta;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

class ParseCharStream
  implements ParseSource
{
  private static final char[] BEGIN_CDATA;
  private static final char[] BEGIN_ETAG;
  private static final char[] CHARREF_BEGIN;
  private static final char[] COMMENT_BEGIN;
  private static final char[] COMMENT_END;
  private static final boolean DEBUG = true;
  private static final char[] DOCTYPE_BEGIN;
  private static final char[] ENCODING;
  private static final char[] END_CDATA;
  private static final char[] END_EMPTYTAG;
  private static final char[] ENTITY_BEGIN;
  public static final int HISTORY_LENGTH = 100;
  private static final boolean H_DEBUG = false;
  private static final boolean[] IS_NAME_CHAR = new boolean[''];
  private static final char[] MARKUPDECL_BEGIN;
  private static final int MAX_COMMON_CHAR = 128;
  private static final char[] NAME_PUNCT_CHARS = { 46, 45, 95, 58 };
  private static final char[] NDATA;
  private static final char[] PI_BEGIN;
  private static final char[] PUBLIC;
  private static final char[] QU_END;
  private static final char[] SYSTEM;
  private static final int TMP_BUF_SIZE = 255;
  private static final char[] VERSION;
  private static final char[] VERSIONNUM_PUNC_CHARS;
  private static final char[] XML_BEGIN;
  private final int CBUF_SIZE = 1024;
  private final char[] cbuf_;
  private int ch_ = -2;
  private int curPos_ = 0;
  private String docTypeName_ = null;
  private final String encoding_;
  private int endPos_ = 0;
  private final Hashtable entities_ = new Hashtable();
  private boolean eos_ = false;
  private final ParseHandler handler_;
  private final CharCircBuffer history_ = null;
  private boolean isExternalDtd_ = false;
  private int lineNumber_ = -1;
  private final ParseLog log_;
  private final Hashtable pes_ = new Hashtable();
  private final Reader reader_;
  private String systemId_;
  private final char[] tmpBuf_ = new char['ÿ'];

  static
  {
    for (char c = '\000'; ; c = (char)(c + '\001'))
    {
      if (c >= '')
      {
        COMMENT_BEGIN = "<!--".toCharArray();
        COMMENT_END = "-->".toCharArray();
        PI_BEGIN = "<?".toCharArray();
        QU_END = "?>".toCharArray();
        DOCTYPE_BEGIN = "<!DOCTYPE".toCharArray();
        XML_BEGIN = "<?xml".toCharArray();
        ENCODING = "encoding".toCharArray();
        VERSION = "version".toCharArray();
        VERSIONNUM_PUNC_CHARS = new char[] { 95, 46, 58, 45 };
        MARKUPDECL_BEGIN = "<!".toCharArray();
        CHARREF_BEGIN = "&#".toCharArray();
        ENTITY_BEGIN = "<!ENTITY".toCharArray();
        NDATA = "NDATA".toCharArray();
        SYSTEM = "SYSTEM".toCharArray();
        PUBLIC = "PUBLIC".toCharArray();
        BEGIN_CDATA = "<![CDATA[".toCharArray();
        END_CDATA = "]]>".toCharArray();
        END_EMPTYTAG = "/>".toCharArray();
        BEGIN_ETAG = "</".toCharArray();
        return;
      }
      IS_NAME_CHAR[c] = isNameChar(c);
    }
  }

  public ParseCharStream(String paramString1, Reader paramReader, ParseLog paramParseLog, String paramString2, ParseHandler paramParseHandler)
    throws ParseException, EncodingMismatchException, IOException
  {
    this(paramString1, paramReader, null, paramParseLog, paramString2, paramParseHandler);
  }

  public ParseCharStream(String paramString1, Reader paramReader, char[] paramArrayOfChar, ParseLog paramParseLog, String paramString2, ParseHandler paramParseHandler)
    throws ParseException, EncodingMismatchException, IOException
  {
    if (paramParseLog == null)
      paramParseLog = ParseSource.DEFAULT_LOG;
    this.log_ = paramParseLog;
    String str;
    if (paramString2 == null)
    {
      str = null;
      this.encoding_ = str;
      this.entities_.put("lt", "<");
      this.entities_.put("gt", ">");
      this.entities_.put("amp", "&");
      this.entities_.put("apos", "'");
      this.entities_.put("quot", "\"");
      if (paramArrayOfChar == null)
        break label374;
      this.cbuf_ = paramArrayOfChar;
      this.curPos_ = 0;
      this.endPos_ = this.cbuf_.length;
      this.eos_ = true;
      this.reader_ = null;
      label211: this.systemId_ = paramString1;
      this.handler_ = paramParseHandler;
      this.handler_.setParseSource(this);
      readProlog();
      this.handler_.startDocument();
      Element localElement = readElement();
      if ((this.docTypeName_ != null) && (!this.docTypeName_.equals(localElement.getTagName())))
        this.log_.warning("DOCTYPE name \"" + this.docTypeName_ + "\" not same as tag name, \"" + localElement.getTagName() + "\" of root element", this.systemId_, getLineNumber());
    }
    while (true)
    {
      if (!isMisc())
      {
        if (this.reader_ != null)
          this.reader_.close();
        this.handler_.endDocument();
        return;
        str = paramString2.toLowerCase();
        break;
        label374: this.reader_ = paramReader;
        this.cbuf_ = new char[1024];
        fillBuf();
        break label211;
      }
      readMisc();
    }
  }

  public ParseCharStream(String paramString1, char[] paramArrayOfChar, ParseLog paramParseLog, String paramString2, ParseHandler paramParseHandler)
    throws ParseException, EncodingMismatchException, IOException
  {
    this(paramString1, null, paramArrayOfChar, paramParseLog, paramString2, paramParseHandler);
  }

  private int fillBuf()
    throws IOException
  {
    if (this.eos_)
      return -1;
    if (this.endPos_ == this.cbuf_.length)
    {
      this.endPos_ = 0;
      this.curPos_ = 0;
    }
    int i = this.reader_.read(this.cbuf_, this.endPos_, this.cbuf_.length - this.endPos_);
    if (i <= 0)
    {
      this.eos_ = true;
      return -1;
    }
    this.endPos_ = (i + this.endPos_);
    return i;
  }

  private int fillBuf(int paramInt)
    throws IOException
  {
    if (this.eos_)
    {
      j = -1;
      return j;
    }
    int i = this.cbuf_.length - this.curPos_;
    int j = 0;
    if (i < paramInt);
    int k;
    for (int m = 0; ; m++)
    {
      if (m + this.curPos_ >= this.endPos_)
      {
        int n = this.endPos_ - this.curPos_;
        this.endPos_ = n;
        this.curPos_ = 0;
        j = n;
        k = fillBuf();
        if (k != -1)
          break label114;
        if (j != 0)
          break;
        return -1;
      }
      this.cbuf_[m] = this.cbuf_[(m + this.curPos_)];
    }
    label114: return j + k;
  }

  private boolean isCdSect()
    throws ParseException, IOException
  {
    return isSymbol(BEGIN_CDATA);
  }

  private final boolean isChar(char paramChar)
    throws ParseException, IOException
  {
    if ((this.curPos_ >= this.endPos_) && (fillBuf() == -1))
      throw new ParseException(this, "unexpected end of expression.");
    return this.cbuf_[this.curPos_] == paramChar;
  }

  private final boolean isChar(char paramChar1, char paramChar2)
    throws ParseException, IOException
  {
    if ((this.curPos_ >= this.endPos_) && (fillBuf() == -1));
    char c;
    do
    {
      return false;
      c = this.cbuf_[this.curPos_];
    }
    while ((c != paramChar1) && (c != paramChar2));
    return true;
  }

  private final boolean isChar(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
    throws ParseException, IOException
  {
    if ((this.curPos_ >= this.endPos_) && (fillBuf() == -1));
    char c;
    do
    {
      return false;
      c = this.cbuf_[this.curPos_];
    }
    while ((c != paramChar1) && (c != paramChar2) && (c != paramChar3) && (c != paramChar4));
    return true;
  }

  private final boolean isComment()
    throws ParseException, IOException
  {
    return isSymbol(COMMENT_BEGIN);
  }

  private boolean isDeclSep()
    throws ParseException, IOException
  {
    return (isPeReference()) || (isS());
  }

  private boolean isDocTypeDecl()
    throws ParseException, IOException
  {
    return isSymbol(DOCTYPE_BEGIN);
  }

  private boolean isETag()
    throws ParseException, IOException
  {
    return isSymbol(BEGIN_ETAG);
  }

  private boolean isEncodingDecl()
    throws ParseException, IOException
  {
    return isSymbol(ENCODING);
  }

  private boolean isEntityDecl()
    throws ParseException, IOException
  {
    return isSymbol(ENTITY_BEGIN);
  }

  private final boolean isEntityValue()
    throws ParseException, IOException
  {
    return isChar('\'', '"');
  }

  private static boolean isExtender(char paramChar)
  {
    switch (paramChar)
    {
    default:
      return false;
    case '·':
    case 'ː':
    case 'ˑ':
    case '·':
    case 'ـ':
    case 'ๆ':
    case 'ໆ':
    case '々':
    case '〱':
    case '〲':
    case '〳':
    case '〴':
    case '〵':
    case 'ゝ':
    case 'ゞ':
    case 'ー':
    case 'ヽ':
    case 'ヾ':
    }
    return true;
  }

  private boolean isExternalId()
    throws ParseException, IOException
  {
    return (isSymbol(SYSTEM)) || (isSymbol(PUBLIC));
  }

  private static final boolean isIn(char paramChar, char[] paramArrayOfChar)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfChar.length)
        return false;
      if (paramChar == paramArrayOfChar[i])
        return true;
    }
  }

  private static boolean isLetter(char paramChar)
  {
    return "abcdefghijklmnopqrstuvwxyz".indexOf(Character.toLowerCase(paramChar)) != -1;
  }

  private boolean isMisc()
    throws ParseException, IOException
  {
    return (isComment()) || (isPi()) || (isS());
  }

  private boolean isNameChar()
    throws ParseException, IOException
  {
    char c = peekChar();
    if (c < '')
      return IS_NAME_CHAR[c];
    return isNameChar(c);
  }

  private static boolean isNameChar(char paramChar)
  {
    return (Character.isDigit(paramChar)) || (isLetter(paramChar)) || (isIn(paramChar, NAME_PUNCT_CHARS)) || (isExtender(paramChar));
  }

  private boolean isPeReference()
    throws ParseException, IOException
  {
    return isChar('%');
  }

  private final boolean isPi()
    throws ParseException, IOException
  {
    return isSymbol(PI_BEGIN);
  }

  private final boolean isReference()
    throws ParseException, IOException
  {
    return isChar('&');
  }

  private final boolean isS()
    throws ParseException, IOException
  {
    return isChar(' ', '\t', '\r', '\n');
  }

  private final boolean isSymbol(char[] paramArrayOfChar)
    throws ParseException, IOException
  {
    int i = paramArrayOfChar.length;
    if ((this.endPos_ - this.curPos_ < i) && (fillBuf(i) <= 0))
      this.ch_ = -1;
    do
    {
      return false;
      this.ch_ = this.cbuf_[(-1 + this.endPos_)];
    }
    while (this.endPos_ - this.curPos_ < i);
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return true;
      if (this.cbuf_[(j + this.curPos_)] != paramArrayOfChar[j])
        break;
    }
  }

  private boolean isVersionNumChar()
    throws ParseException, IOException
  {
    char c = peekChar();
    return (Character.isDigit(c)) || (('a' <= c) && (c <= 'z')) || (('Z' <= c) && (c <= 'Z')) || (isIn(c, VERSIONNUM_PUNC_CHARS));
  }

  private boolean isXmlDecl()
    throws ParseException, IOException
  {
    return isSymbol(XML_BEGIN);
  }

  private final char peekChar()
    throws ParseException, IOException
  {
    if ((this.curPos_ >= this.endPos_) && (fillBuf() == -1))
      throw new ParseException(this, "unexpected end of expression.");
    return this.cbuf_[this.curPos_];
  }

  private String readAttValue()
    throws ParseException, IOException
  {
    char c = readChar('\'', '"');
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      if (isChar(c))
      {
        readChar(c);
        return localStringBuffer.toString();
      }
      if (isReference())
        localStringBuffer.append(readReference());
      else
        localStringBuffer.append(readChar());
    }
  }

  private void readAttribute(Element paramElement)
    throws ParseException, IOException
  {
    String str1 = readName();
    readEq();
    String str2 = readAttValue();
    if (paramElement.getAttribute(str1) != null)
      this.log_.warning("Element " + this + " contains attribute " + str1 + "more than once", this.systemId_, getLineNumber());
    paramElement.setAttribute(str1, str2);
  }

  private void readCdSect()
    throws ParseException, IOException
  {
    Object localObject1 = null;
    readSymbol(BEGIN_CDATA);
    int i = 0;
    label99: Object localObject2;
    if (isSymbol(END_CDATA))
    {
      readSymbol(END_CDATA);
      if (localObject1 != null)
      {
        ((StringBuffer)localObject1).append(this.tmpBuf_, 0, i);
        char[] arrayOfChar2 = ((StringBuffer)localObject1).toString().toCharArray();
        this.handler_.characters(arrayOfChar2, 0, arrayOfChar2.length);
      }
    }
    else
    {
      if (i < 255)
        break label165;
      if (localObject1 == null)
      {
        localObject1 = new StringBuffer(i);
        ((StringBuffer)localObject1).append(this.tmpBuf_, 0, i);
        localObject2 = localObject1;
      }
    }
    label165: int j;
    for (int k = 0; ; k = j)
    {
      char[] arrayOfChar1 = this.tmpBuf_;
      int m = k + 1;
      arrayOfChar1[k] = readChar();
      localObject1 = localObject2;
      i = m;
      break;
      ((StringBuffer)localObject1).append(this.tmpBuf_, 0, i);
      break label99;
      this.handler_.characters(this.tmpBuf_, 0, i);
      return;
      j = i;
      localObject2 = localObject1;
    }
  }

  private final char readChar()
    throws ParseException, IOException
  {
    if ((this.curPos_ >= this.endPos_) && (fillBuf() == -1))
      throw new ParseException(this, "unexpected end of expression.");
    if (this.cbuf_[this.curPos_] == '\n')
      this.lineNumber_ = (1 + this.lineNumber_);
    char[] arrayOfChar = this.cbuf_;
    int i = this.curPos_;
    this.curPos_ = (i + 1);
    return arrayOfChar[i];
  }

  private final char readChar(char paramChar1, char paramChar2)
    throws ParseException, IOException
  {
    char c = readChar();
    if ((c != paramChar1) && (c != paramChar2))
      throw new ParseException(this, c, new char[] { paramChar1, paramChar2 });
    return c;
  }

  private final char readChar(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
    throws ParseException, IOException
  {
    char c = readChar();
    if ((c != paramChar1) && (c != paramChar2) && (c != paramChar3) && (c != paramChar4))
      throw new ParseException(this, c, new char[] { paramChar1, paramChar2, paramChar3, paramChar4 });
    return c;
  }

  private final void readChar(char paramChar)
    throws ParseException, IOException
  {
    char c = readChar();
    if (c != paramChar)
      throw new ParseException(this, c, paramChar);
  }

  private char readCharRef()
    throws ParseException, IOException
  {
    readSymbol(CHARREF_BEGIN);
    int i = 10;
    if (isChar('x'))
    {
      readChar();
      i = 16;
    }
    int j = 0;
    while (true)
    {
      String str1;
      if (isChar(';'))
      {
        readChar(';');
        str1 = new String(this.tmpBuf_, 0, j);
      }
      try
      {
        int m = Integer.parseInt(str1, i);
        return (char)m;
        char[] arrayOfChar = this.tmpBuf_;
        k = j + 1;
        arrayOfChar[j] = readChar();
        if (k >= 255)
        {
          this.log_.warning("Tmp buffer overflow on readCharRef", this.systemId_, getLineNumber());
          return ' ';
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int k;
        ParseLog localParseLog = this.log_;
        StringBuffer localStringBuffer = new StringBuffer().append("\"").append(str1).append("\" is not a valid ");
        if (i == 16);
        for (String str2 = "hexadecimal"; ; str2 = "decimal")
        {
          localParseLog.warning(str2 + " number", this.systemId_, getLineNumber());
          return ' ';
        }
        j = k;
      }
    }
  }

  private final void readComment()
    throws ParseException, IOException
  {
    readSymbol(COMMENT_BEGIN);
    while (true)
    {
      if (isSymbol(COMMENT_END))
      {
        readSymbol(COMMENT_END);
        return;
      }
      readChar();
    }
  }

  private void readContent()
    throws ParseException, IOException
  {
    readPossibleCharData();
    int i = 1;
    if (i == 0)
      return;
    if (isETag())
      i = 0;
    while (true)
    {
      readPossibleCharData();
      break;
      if (isReference())
      {
        char[] arrayOfChar = readReference();
        this.handler_.characters(arrayOfChar, 0, arrayOfChar.length);
      }
      else if (isCdSect())
      {
        readCdSect();
      }
      else if (isPi())
      {
        readPi();
      }
      else if (isComment())
      {
        readComment();
      }
      else if (isChar('<'))
      {
        readElement();
      }
      else
      {
        i = 0;
      }
    }
  }

  private void readDeclSep()
    throws ParseException, IOException
  {
    if (isPeReference())
    {
      readPeReference();
      return;
    }
    readS();
  }

  private void readDocTypeDecl()
    throws ParseException, IOException
  {
    readSymbol(DOCTYPE_BEGIN);
    readS();
    this.docTypeName_ = readName();
    if (isS())
    {
      readS();
      if ((!isChar('>')) && (!isChar('[')))
      {
        this.isExternalDtd_ = true;
        readExternalId();
        if (isS())
          readS();
      }
    }
    if (isChar('['))
      readChar();
    while (true)
    {
      if (isChar(']'))
      {
        readChar(']');
        if (isS())
          readS();
        readChar('>');
        return;
      }
      if (isDeclSep())
        readDeclSep();
      else
        readMarkupDecl();
    }
  }

  private void readETag(Element paramElement)
    throws ParseException, IOException
  {
    readSymbol(BEGIN_ETAG);
    String str = readName();
    if (!str.equals(paramElement.getTagName()))
      this.log_.warning("end tag (" + str + ") does not match begin tag (" + paramElement.getTagName() + ")", this.systemId_, getLineNumber());
    if (isS())
      readS();
    readChar('>');
  }

  private final Element readElement()
    throws ParseException, IOException
  {
    Element localElement = new Element();
    boolean bool = readEmptyElementTagOrSTag(localElement);
    this.handler_.startElement(localElement);
    if (bool)
    {
      readContent();
      readETag(localElement);
    }
    this.handler_.endElement(localElement);
    return localElement;
  }

  private boolean readEmptyElementTagOrSTag(Element paramElement)
    throws ParseException, IOException
  {
    readChar('<');
    paramElement.setTagName(readName());
    boolean bool;
    while (true)
    {
      if (!isS())
      {
        if (isS())
          readS();
        bool = isChar('>');
        if (!bool)
          break;
        readChar('>');
        return bool;
      }
      readS();
      if (!isChar('/', '>'))
        readAttribute(paramElement);
    }
    readSymbol(END_EMPTYTAG);
    return bool;
  }

  private String readEncodingDecl()
    throws ParseException, IOException
  {
    readSymbol(ENCODING);
    readEq();
    char c = readChar('\'', '"');
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      if (isChar(c))
      {
        readChar(c);
        return localStringBuffer.toString();
      }
      localStringBuffer.append(readChar());
    }
  }

  private void readEntityDecl()
    throws ParseException, IOException
  {
    readSymbol(ENTITY_BEGIN);
    readS();
    if (isChar('%'))
    {
      readChar('%');
      readS();
      String str3 = readName();
      readS();
      if (isEntityValue());
      for (String str4 = readEntityValue(); ; str4 = readExternalId())
      {
        this.pes_.put(str3, str4);
        if (isS())
          readS();
        readChar('>');
        return;
      }
    }
    String str1 = readName();
    readS();
    String str2;
    if (isEntityValue())
      str2 = readEntityValue();
    while (true)
    {
      this.entities_.put(str1, str2);
      break;
      if (!isExternalId())
        break label178;
      str2 = readExternalId();
      if (isS())
        readS();
      if (isSymbol(NDATA))
      {
        readSymbol(NDATA);
        readS();
        readName();
      }
    }
    label178: throw new ParseException(this, "expecting double-quote, \"PUBLIC\" or \"SYSTEM\" while reading entity declaration");
  }

  private String readEntityRef()
    throws ParseException, IOException
  {
    readChar('&');
    String str1 = readName();
    String str2 = (String)this.entities_.get(str1);
    if (str2 == null)
    {
      str2 = "";
      if (!this.isExternalDtd_)
        break label88;
      this.log_.warning("&" + str1 + "; not found -- possibly defined in external DTD)", this.systemId_, getLineNumber());
    }
    while (true)
    {
      readChar(';');
      return str2;
      label88: this.log_.warning("No declaration of &" + str1 + ";", this.systemId_, getLineNumber());
    }
  }

  private final String readEntityValue()
    throws ParseException, IOException
  {
    char c = readChar('\'', '"');
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      if (isChar(c))
      {
        readChar(c);
        return localStringBuffer.toString();
      }
      if (isPeReference())
        localStringBuffer.append(readPeReference());
      else if (isReference())
        localStringBuffer.append(readReference());
      else
        localStringBuffer.append(readChar());
    }
  }

  private final void readEq()
    throws ParseException, IOException
  {
    if (isS())
      readS();
    readChar('=');
    if (isS())
      readS();
  }

  private String readExternalId()
    throws ParseException, IOException
  {
    if (isSymbol(SYSTEM))
      readSymbol(SYSTEM);
    while (true)
    {
      readS();
      readSystemLiteral();
      return "(WARNING: external ID not read)";
      if (!isSymbol(PUBLIC))
        break;
      readSymbol(PUBLIC);
      readS();
      readPubidLiteral();
    }
    throw new ParseException(this, "expecting \"SYSTEM\" or \"PUBLIC\" while reading external ID");
  }

  private void readMarkupDecl()
    throws ParseException, IOException
  {
    if (isPi())
    {
      readPi();
      return;
    }
    if (isComment())
    {
      readComment();
      return;
    }
    if (isEntityDecl())
    {
      readEntityDecl();
      return;
    }
    if (isSymbol(MARKUPDECL_BEGIN))
      while (true)
      {
        if (isChar('>'))
        {
          readChar('>');
          return;
        }
        if (isChar('\'', '"'))
        {
          char c = readChar();
          while (true)
          {
            if (isChar(c))
            {
              readChar(c);
              break;
            }
            readChar();
          }
        }
        readChar();
      }
    throw new ParseException(this, "expecting processing instruction, comment, or \"<!\"");
  }

  private void readMisc()
    throws ParseException, IOException
  {
    if (isComment())
    {
      readComment();
      return;
    }
    if (isPi())
    {
      readPi();
      return;
    }
    if (isS())
    {
      readS();
      return;
    }
    throw new ParseException(this, "expecting comment or processing instruction or space");
  }

  private final String readName()
    throws ParseException, IOException
  {
    Object localObject1 = null;
    char[] arrayOfChar1 = this.tmpBuf_;
    int i = 1;
    arrayOfChar1[0] = readNameStartChar();
    label75: Object localObject2;
    if (!isNameChar())
    {
      if (localObject1 == null)
        return Sparta.intern(new String(this.tmpBuf_, 0, i));
    }
    else
    {
      if (i < 255)
        break label141;
      if (localObject1 == null)
      {
        localObject1 = new StringBuffer(i);
        ((StringBuffer)localObject1).append(this.tmpBuf_, 0, i);
        localObject2 = localObject1;
      }
    }
    label141: int j;
    for (int k = 0; ; k = j)
    {
      char[] arrayOfChar2 = this.tmpBuf_;
      int m = k + 1;
      arrayOfChar2[k] = readChar();
      localObject1 = localObject2;
      i = m;
      break;
      ((StringBuffer)localObject1).append(this.tmpBuf_, 0, i);
      break label75;
      ((StringBuffer)localObject1).append(this.tmpBuf_, 0, i);
      return ((StringBuffer)localObject1).toString();
      j = i;
      localObject2 = localObject1;
    }
  }

  private char readNameStartChar()
    throws ParseException, IOException
  {
    char c = readChar();
    if ((!isLetter(c)) && (c != '_') && (c != ':'))
      throw new ParseException(this, c, "letter, underscore, colon");
    return c;
  }

  private String readPeReference()
    throws ParseException, IOException
  {
    readChar('%');
    String str1 = readName();
    String str2 = (String)this.pes_.get(str1);
    if (str2 == null)
    {
      str2 = "";
      this.log_.warning("No declaration of %" + str1 + ";", this.systemId_, getLineNumber());
    }
    readChar(';');
    return str2;
  }

  private final void readPi()
    throws ParseException, IOException
  {
    readSymbol(PI_BEGIN);
    while (true)
    {
      if (isSymbol(QU_END))
      {
        readSymbol(QU_END);
        return;
      }
      readChar();
    }
  }

  private void readPossibleCharData()
    throws ParseException, IOException
  {
    int i = 0;
    while (true)
    {
      if ((isChar('<')) || (isChar('&')) || (isSymbol(END_CDATA)))
      {
        if (i > 0)
          this.handler_.characters(this.tmpBuf_, 0, i);
        return;
      }
      this.tmpBuf_[i] = readChar();
      if ((this.tmpBuf_[i] == '\r') && (peekChar() == '\n'))
        this.tmpBuf_[i] = readChar();
      i++;
      if (i == 255)
      {
        this.handler_.characters(this.tmpBuf_, 0, 255);
        i = 0;
      }
    }
  }

  private void readProlog()
    throws ParseException, EncodingMismatchException, IOException
  {
    if (isXmlDecl())
      readXmlDecl();
    if (!isMisc())
      if (isDocTypeDecl())
        readDocTypeDecl();
    while (true)
    {
      if (!isMisc())
      {
        return;
        readMisc();
        break;
      }
      readMisc();
    }
  }

  private final void readPubidLiteral()
    throws ParseException, IOException
  {
    readSystemLiteral();
  }

  private final char[] readReference()
    throws ParseException, IOException
  {
    if (isSymbol(CHARREF_BEGIN))
    {
      char[] arrayOfChar = new char[1];
      arrayOfChar[0] = readCharRef();
      return arrayOfChar;
    }
    return readEntityRef().toCharArray();
  }

  private final void readS()
    throws ParseException, IOException
  {
    readChar(' ', '\t', '\r', '\n');
    while (true)
    {
      if (!isChar(' ', '\t', '\r', '\n'))
        return;
      readChar();
    }
  }

  private final void readSymbol(char[] paramArrayOfChar)
    throws ParseException, IOException
  {
    int i = paramArrayOfChar.length;
    if ((this.endPos_ - this.curPos_ < i) && (fillBuf(i) <= 0))
    {
      this.ch_ = -1;
      throw new ParseException(this, "end of XML file", paramArrayOfChar);
    }
    this.ch_ = this.cbuf_[(-1 + this.endPos_)];
    if (this.endPos_ - this.curPos_ < i)
      throw new ParseException(this, "end of XML file", paramArrayOfChar);
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        this.curPos_ = (i + this.curPos_);
        return;
      }
      if (this.cbuf_[(j + this.curPos_)] != paramArrayOfChar[j])
        throw new ParseException(this, new String(this.cbuf_, this.curPos_, i), paramArrayOfChar);
    }
  }

  private final void readSystemLiteral()
    throws ParseException, IOException
  {
    char c = readChar();
    while (true)
    {
      if (peekChar() == c)
      {
        readChar(c);
        return;
      }
      readChar();
    }
  }

  private void readVersionInfo()
    throws ParseException, IOException
  {
    readS();
    readSymbol(VERSION);
    readEq();
    char c = readChar('\'', '"');
    readVersionNum();
    readChar(c);
  }

  private void readVersionNum()
    throws ParseException, IOException
  {
    readChar();
    while (true)
    {
      if (!isVersionNumChar())
        return;
      readChar();
    }
  }

  private void readXmlDecl()
    throws ParseException, EncodingMismatchException, IOException
  {
    readSymbol(XML_BEGIN);
    readVersionInfo();
    if (isS())
      readS();
    if (isEncodingDecl())
    {
      String str = readEncodingDecl();
      if ((this.encoding_ != null) && (!str.toLowerCase().equals(this.encoding_)))
        throw new EncodingMismatchException(this.systemId_, str, this.encoding_);
    }
    while (!isSymbol(QU_END))
      readChar();
    readSymbol(QU_END);
  }

  final String getHistory()
  {
    return "";
  }

  int getLastCharRead()
  {
    return this.ch_;
  }

  public int getLineNumber()
  {
    return this.lineNumber_;
  }

  ParseLog getLog()
  {
    return this.log_;
  }

  public String getSystemId()
  {
    return this.systemId_;
  }

  public String toString()
  {
    return this.systemId_;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.ParseCharStream
 * JD-Core Version:    0.6.2
 */