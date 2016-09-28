package com.hp.hpl.sparta;

import java.io.IOException;

class ParseByteStream
  implements ParseSource
{
  private ParseCharStream parseSource_;

  // ERROR //
  public ParseByteStream(String paramString1, java.io.InputStream paramInputStream, ParseLog paramParseLog, String paramString2, ParseHandler paramParseHandler)
    throws ParseException, IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 21	java/lang/Object:<init>	()V
    //   4: aload_3
    //   5: ifnonnull +369 -> 374
    //   8: getstatic 25	com/hp/hpl/sparta/ParseSource:DEFAULT_LOG	Lcom/hp/hpl/sparta/ParseLog;
    //   11: astore 6
    //   13: aload_2
    //   14: invokevirtual 31	java/io/InputStream:markSupported	()Z
    //   17: ifne +13 -> 30
    //   20: new 33	java/lang/Error
    //   23: dup
    //   24: ldc 35
    //   26: invokespecial 38	java/lang/Error:<init>	(Ljava/lang/String;)V
    //   29: athrow
    //   30: aload_2
    //   31: getstatic 42	com/hp/hpl/sparta/ParseSource:MAXLOOKAHEAD	I
    //   34: invokevirtual 46	java/io/InputStream:mark	(I)V
    //   37: iconst_4
    //   38: newarray byte
    //   40: astore 7
    //   42: aload_2
    //   43: aload 7
    //   45: invokevirtual 50	java/io/InputStream:read	([B)I
    //   48: istore 8
    //   50: aload 4
    //   52: ifnonnull +315 -> 367
    //   55: aload_1
    //   56: aload 7
    //   58: iload 8
    //   60: aload 6
    //   62: invokestatic 54	com/hp/hpl/sparta/ParseByteStream:guessEncoding	(Ljava/lang/String;[BILcom/hp/hpl/sparta/ParseLog;)Ljava/lang/String;
    //   65: astore 9
    //   67: aload_2
    //   68: invokevirtual 57	java/io/InputStream:reset	()V
    //   71: new 59	java/io/InputStreamReader
    //   74: dup
    //   75: aload_2
    //   76: aload 9
    //   78: invokestatic 63	com/hp/hpl/sparta/ParseByteStream:fixEncoding	(Ljava/lang/String;)Ljava/lang/String;
    //   81: invokespecial 66	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   84: astore 14
    //   86: aload_0
    //   87: new 68	com/hp/hpl/sparta/ParseCharStream
    //   90: dup
    //   91: aload_1
    //   92: aload 14
    //   94: aload 6
    //   96: aload 9
    //   98: aload 5
    //   100: invokespecial 71	com/hp/hpl/sparta/ParseCharStream:<init>	(Ljava/lang/String;Ljava/io/Reader;Lcom/hp/hpl/sparta/ParseLog;Ljava/lang/String;Lcom/hp/hpl/sparta/ParseHandler;)V
    //   103: putfield 73	com/hp/hpl/sparta/ParseByteStream:parseSource_	Lcom/hp/hpl/sparta/ParseCharStream;
    //   106: return
    //   107: astore 15
    //   109: aload 6
    //   111: new 75	java/lang/StringBuffer
    //   114: dup
    //   115: invokespecial 76	java/lang/StringBuffer:<init>	()V
    //   118: ldc 78
    //   120: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   123: aload 9
    //   125: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   128: ldc 84
    //   130: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   133: ldc 86
    //   135: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   138: invokevirtual 90	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   141: aload_1
    //   142: iconst_1
    //   143: invokeinterface 96 4 0
    //   148: aload_2
    //   149: invokevirtual 57	java/io/InputStream:reset	()V
    //   152: new 59	java/io/InputStreamReader
    //   155: dup
    //   156: aload_2
    //   157: ldc 86
    //   159: invokestatic 63	com/hp/hpl/sparta/ParseByteStream:fixEncoding	(Ljava/lang/String;)Ljava/lang/String;
    //   162: invokespecial 66	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   165: astore 16
    //   167: aload_0
    //   168: new 68	com/hp/hpl/sparta/ParseCharStream
    //   171: dup
    //   172: aload_1
    //   173: aload 16
    //   175: aload 6
    //   177: aconst_null
    //   178: aload 5
    //   180: invokespecial 71	com/hp/hpl/sparta/ParseCharStream:<init>	(Ljava/lang/String;Ljava/io/Reader;Lcom/hp/hpl/sparta/ParseLog;Ljava/lang/String;Lcom/hp/hpl/sparta/ParseHandler;)V
    //   183: putfield 73	com/hp/hpl/sparta/ParseByteStream:parseSource_	Lcom/hp/hpl/sparta/ParseCharStream;
    //   186: return
    //   187: astore 10
    //   189: aload 10
    //   191: invokevirtual 99	com/hp/hpl/sparta/EncodingMismatchException:getDeclaredEncoding	()Ljava/lang/String;
    //   194: astore 11
    //   196: aload 6
    //   198: new 75	java/lang/StringBuffer
    //   201: dup
    //   202: invokespecial 76	java/lang/StringBuffer:<init>	()V
    //   205: ldc 101
    //   207: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   210: aload 11
    //   212: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   215: ldc 103
    //   217: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   220: aload 9
    //   222: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   225: ldc 105
    //   227: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   230: invokevirtual 90	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   233: aload_1
    //   234: iconst_1
    //   235: invokeinterface 96 4 0
    //   240: aload_2
    //   241: invokevirtual 57	java/io/InputStream:reset	()V
    //   244: new 59	java/io/InputStreamReader
    //   247: dup
    //   248: aload_2
    //   249: aload 11
    //   251: invokestatic 63	com/hp/hpl/sparta/ParseByteStream:fixEncoding	(Ljava/lang/String;)Ljava/lang/String;
    //   254: invokespecial 66	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   257: astore 12
    //   259: aload_0
    //   260: new 68	com/hp/hpl/sparta/ParseCharStream
    //   263: dup
    //   264: aload_1
    //   265: aload 12
    //   267: aload 6
    //   269: aconst_null
    //   270: aload 5
    //   272: invokespecial 71	com/hp/hpl/sparta/ParseCharStream:<init>	(Ljava/lang/String;Ljava/io/Reader;Lcom/hp/hpl/sparta/ParseLog;Ljava/lang/String;Lcom/hp/hpl/sparta/ParseHandler;)V
    //   275: putfield 73	com/hp/hpl/sparta/ParseByteStream:parseSource_	Lcom/hp/hpl/sparta/ParseCharStream;
    //   278: return
    //   279: astore 17
    //   281: new 75	java/lang/StringBuffer
    //   284: dup
    //   285: invokespecial 76	java/lang/StringBuffer:<init>	()V
    //   288: ldc 107
    //   290: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   293: ldc 86
    //   295: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   298: ldc 109
    //   300: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   303: invokevirtual 90	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   306: astore 18
    //   308: new 12	com/hp/hpl/sparta/ParseException
    //   311: dup
    //   312: aload 6
    //   314: aload_1
    //   315: iconst_1
    //   316: iconst_0
    //   317: ldc 86
    //   319: aload 18
    //   321: invokespecial 112	com/hp/hpl/sparta/ParseException:<init>	(Lcom/hp/hpl/sparta/ParseLog;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V
    //   324: athrow
    //   325: astore 13
    //   327: new 12	com/hp/hpl/sparta/ParseException
    //   330: dup
    //   331: aload 6
    //   333: aload_1
    //   334: iconst_1
    //   335: iconst_0
    //   336: aload 11
    //   338: new 75	java/lang/StringBuffer
    //   341: dup
    //   342: invokespecial 76	java/lang/StringBuffer:<init>	()V
    //   345: ldc 107
    //   347: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   350: aload 11
    //   352: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   355: ldc 109
    //   357: invokevirtual 82	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   360: invokevirtual 90	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   363: invokespecial 112	com/hp/hpl/sparta/ParseException:<init>	(Lcom/hp/hpl/sparta/ParseLog;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V
    //   366: athrow
    //   367: aload 4
    //   369: astore 9
    //   371: goto -304 -> 67
    //   374: aload_3
    //   375: astore 6
    //   377: goto -364 -> 13
    //
    // Exception table:
    //   from	to	target	type
    //   86	106	107	java/io/IOException
    //   67	86	187	com/hp/hpl/sparta/EncodingMismatchException
    //   86	106	187	com/hp/hpl/sparta/EncodingMismatchException
    //   109	152	187	com/hp/hpl/sparta/EncodingMismatchException
    //   152	167	187	com/hp/hpl/sparta/EncodingMismatchException
    //   167	186	187	com/hp/hpl/sparta/EncodingMismatchException
    //   281	325	187	com/hp/hpl/sparta/EncodingMismatchException
    //   152	167	279	java/io/UnsupportedEncodingException
    //   244	259	325	java/io/UnsupportedEncodingException
  }

  private static boolean equals(byte[] paramArrayOfByte, int paramInt)
  {
    return (paramArrayOfByte[0] == (byte)(paramInt >>> 24)) && (paramArrayOfByte[1] == (byte)(0xFF & paramInt >>> 16)) && (paramArrayOfByte[2] == (byte)(0xFF & paramInt >>> 8)) && (paramArrayOfByte[3] == (byte)(paramInt & 0xFF));
  }

  private static boolean equals(byte[] paramArrayOfByte, short paramShort)
  {
    return (paramArrayOfByte[0] == (byte)(paramShort >>> 8)) && (paramArrayOfByte[1] == (byte)(paramShort & 0xFF));
  }

  private static String fixEncoding(String paramString)
  {
    if (paramString.toLowerCase().equals("utf8"))
      paramString = "UTF-8";
    return paramString;
  }

  private static String guessEncoding(String paramString, byte[] paramArrayOfByte, int paramInt, ParseLog paramParseLog)
    throws IOException
  {
    String str2;
    String str1;
    if (paramInt != 4)
      if (paramInt <= 0)
      {
        str2 = "no characters in input";
        paramParseLog.error(str2, paramString, 1);
        str1 = "UTF-8";
      }
    while (true)
    {
      if (!str1.equals("UTF-8"))
        paramParseLog.note("From start " + hex(paramArrayOfByte[0]) + " " + hex(paramArrayOfByte[1]) + " " + hex(paramArrayOfByte[2]) + " " + hex(paramArrayOfByte[3]) + " deduced encoding = " + str1, paramString, 1);
      return str1;
      str2 = "less than 4 characters in input: \"" + new String(paramArrayOfByte, 0, paramInt) + "\"";
      break;
      if ((equals(paramArrayOfByte, 65279)) || (equals(paramArrayOfByte, -131072)) || (equals(paramArrayOfByte, 65534)) || (equals(paramArrayOfByte, -16842752)) || (equals(paramArrayOfByte, 60)) || (equals(paramArrayOfByte, 1006632960)) || (equals(paramArrayOfByte, 15360)) || (equals(paramArrayOfByte, 3932160)))
        str1 = "UCS-4";
      else if (equals(paramArrayOfByte, 3932223))
        str1 = "UTF-16BE";
      else if (equals(paramArrayOfByte, 1006649088))
        str1 = "UTF-16LE";
      else if (equals(paramArrayOfByte, 1010792557))
        str1 = "UTF-8";
      else if (equals(paramArrayOfByte, 1282385812))
        str1 = "EBCDIC";
      else if ((equals(paramArrayOfByte, (short)-2)) || (equals(paramArrayOfByte, (short)-257)))
        str1 = "UTF-16";
      else
        str1 = "UTF-8";
    }
  }

  private static String hex(byte paramByte)
  {
    String str = Integer.toHexString(paramByte);
    switch (str.length())
    {
    default:
      str = str.substring(-2 + str.length());
    case 2:
      return str;
    case 1:
    }
    return "0" + str;
  }

  public int getLineNumber()
  {
    return this.parseSource_.getLineNumber();
  }

  public String getSystemId()
  {
    return this.parseSource_.getSystemId();
  }

  public String toString()
  {
    return this.parseSource_.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.ParseByteStream
 * JD-Core Version:    0.6.2
 */