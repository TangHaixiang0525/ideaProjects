package org.eclipse.paho.client.mqttv3.logging;

import java.util.logging.Formatter;

public class SimpleLogFormatter extends Formatter
{
  final String ls = System.getProperty("line.separator");

  public static String left(String paramString, int paramInt, char paramChar)
  {
    if (paramString.length() >= paramInt)
      return paramString;
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append(paramString);
    int i = paramInt - paramString.length();
    while (true)
    {
      i--;
      if (i < 0)
        break;
      localStringBuffer.append(paramChar);
    }
    return localStringBuffer.toString();
  }

  // ERROR //
  public String format(java.util.logging.LogRecord paramLogRecord)
  {
    // Byte code:
    //   0: new 30	java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial 49	java/lang/StringBuffer:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: new 51	java/lang/StringBuilder
    //   12: dup
    //   13: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   16: aload_1
    //   17: invokevirtual 58	java/util/logging/LogRecord:getLevel	()Ljava/util/logging/Level;
    //   20: invokevirtual 63	java/util/logging/Level:getName	()Ljava/lang/String;
    //   23: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: ldc 68
    //   28: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   37: pop
    //   38: new 51	java/lang/StringBuilder
    //   41: dup
    //   42: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   45: astore 4
    //   47: iconst_1
    //   48: anewarray 71	java/lang/Object
    //   51: astore 5
    //   53: new 73	java/util/Date
    //   56: dup
    //   57: aload_1
    //   58: invokevirtual 77	java/util/logging/LogRecord:getMillis	()J
    //   61: invokespecial 80	java/util/Date:<init>	(J)V
    //   64: astore 6
    //   66: aload 5
    //   68: iconst_0
    //   69: aload 6
    //   71: aastore
    //   72: aload_2
    //   73: aload 4
    //   75: ldc 82
    //   77: aload 5
    //   79: invokestatic 87	java/text/MessageFormat:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   82: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: ldc 68
    //   87: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   93: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   96: pop
    //   97: aload_1
    //   98: invokevirtual 90	java/util/logging/LogRecord:getSourceClassName	()Ljava/lang/String;
    //   101: astore 8
    //   103: ldc 92
    //   105: astore 9
    //   107: aload 8
    //   109: ifnull +31 -> 140
    //   112: aload 8
    //   114: invokevirtual 28	java/lang/String:length	()I
    //   117: istore 23
    //   119: iload 23
    //   121: bipush 20
    //   123: if_icmple +200 -> 323
    //   126: aload_1
    //   127: invokevirtual 90	java/util/logging/LogRecord:getSourceClassName	()Ljava/lang/String;
    //   130: iload 23
    //   132: bipush 19
    //   134: isub
    //   135: invokevirtual 96	java/lang/String:substring	(I)Ljava/lang/String;
    //   138: astore 9
    //   140: aload_2
    //   141: new 51	java/lang/StringBuilder
    //   144: dup
    //   145: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   148: aload 9
    //   150: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: ldc 68
    //   155: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   161: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   164: ldc 98
    //   166: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   169: pop
    //   170: aload_2
    //   171: new 51	java/lang/StringBuilder
    //   174: dup
    //   175: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   178: aload_1
    //   179: invokevirtual 101	java/util/logging/LogRecord:getSourceMethodName	()Ljava/lang/String;
    //   182: bipush 23
    //   184: bipush 32
    //   186: invokestatic 103	org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter:left	(Ljava/lang/String;IC)Ljava/lang/String;
    //   189: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: ldc 68
    //   194: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   200: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   203: pop
    //   204: aload_2
    //   205: new 51	java/lang/StringBuilder
    //   208: dup
    //   209: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   212: aload_1
    //   213: invokevirtual 106	java/util/logging/LogRecord:getThreadID	()I
    //   216: invokevirtual 109	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   219: ldc 68
    //   221: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   224: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   227: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   230: pop
    //   231: aload_2
    //   232: aload_0
    //   233: aload_1
    //   234: invokevirtual 112	org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter:formatMessage	(Ljava/util/logging/LogRecord;)Ljava/lang/String;
    //   237: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   240: aload_0
    //   241: getfield 20	org/eclipse/paho/client/mqttv3/logging/SimpleLogFormatter:ls	Ljava/lang/String;
    //   244: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   247: pop
    //   248: aload_1
    //   249: invokevirtual 116	java/util/logging/LogRecord:getThrown	()Ljava/lang/Throwable;
    //   252: ifnull +66 -> 318
    //   255: aload_2
    //   256: ldc 118
    //   258: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   261: pop
    //   262: aload_1
    //   263: invokevirtual 116	java/util/logging/LogRecord:getThrown	()Ljava/lang/Throwable;
    //   266: astore 15
    //   268: aconst_null
    //   269: astore 16
    //   271: new 120	java/io/StringWriter
    //   274: dup
    //   275: invokespecial 121	java/io/StringWriter:<init>	()V
    //   278: astore 17
    //   280: new 123	java/io/PrintWriter
    //   283: dup
    //   284: aload 17
    //   286: invokespecial 126	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   289: astore 18
    //   291: aload 15
    //   293: aload 18
    //   295: invokevirtual 132	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   298: aload_2
    //   299: aload 17
    //   301: invokevirtual 133	java/io/StringWriter:toString	()Ljava/lang/String;
    //   304: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   307: pop
    //   308: aload 18
    //   310: ifnull +8 -> 318
    //   313: aload 18
    //   315: invokevirtual 136	java/io/PrintWriter:close	()V
    //   318: aload_2
    //   319: invokevirtual 44	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   322: areturn
    //   323: iconst_1
    //   324: newarray char
    //   326: dup
    //   327: iconst_0
    //   328: bipush 32
    //   330: castore
    //   331: astore 24
    //   333: new 30	java/lang/StringBuffer
    //   336: dup
    //   337: invokespecial 49	java/lang/StringBuffer:<init>	()V
    //   340: aload 8
    //   342: invokevirtual 37	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   345: aload 24
    //   347: iconst_0
    //   348: iconst_1
    //   349: invokevirtual 139	java/lang/StringBuffer:append	([CII)Ljava/lang/StringBuffer;
    //   352: invokevirtual 44	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   355: astore 9
    //   357: goto -217 -> 140
    //   360: astore 19
    //   362: aload 16
    //   364: ifnull +8 -> 372
    //   367: aload 16
    //   369: invokevirtual 136	java/io/PrintWriter:close	()V
    //   372: aload 19
    //   374: athrow
    //   375: astore 22
    //   377: goto -59 -> 318
    //   380: astore 20
    //   382: goto -10 -> 372
    //   385: astore 19
    //   387: aload 18
    //   389: astore 16
    //   391: goto -29 -> 362
    //
    // Exception table:
    //   from	to	target	type
    //   271	291	360	finally
    //   313	318	375	java/lang/Exception
    //   367	372	380	java/lang/Exception
    //   291	308	385	finally
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.logging.SimpleLogFormatter
 * JD-Core Version:    0.6.2
 */