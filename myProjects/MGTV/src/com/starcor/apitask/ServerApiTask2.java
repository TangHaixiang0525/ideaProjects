package com.starcor.apitask;

import com.starcor.core.epgapi.StringParams;
import com.starcor.httpapi.core.SCHttpApiTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class ServerApiTask2 extends SCHttpApiTask
{
  private static final String TAG = ServerApiTask2.class.getSimpleName();
  private IApiTaskListener apiTaskListener;
  protected StringParams nns_func = new StringParams("nns_func");
  protected String prefix = "";
  protected String suffix = "&nns_user_agent=nn_player%2Fstd%2F1.0.0";

  private String convertStreamToString(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuilder.append(str).append("\n");
      }
    }
    catch (IOException localIOException2)
    {
      localIOException2 = localIOException2;
      localIOException2.printStackTrace();
      try
      {
        paramInputStream.close();
        while (true)
        {
          return localStringBuilder.toString();
          try
          {
            paramInputStream.close();
          }
          catch (IOException localIOException4)
          {
            localIOException4.printStackTrace();
          }
        }
      }
      catch (IOException localIOException3)
      {
        while (true)
          localIOException3.printStackTrace();
      }
    }
    finally
    {
    }
    try
    {
      paramInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
  }

  public String buildUrlPrefix()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str;
  }

  public abstract ParserHandler getDefaultHandler();

  public abstract String getUrl();

  // ERROR //
  public void perform(com.starcor.httpapi.core.SCResponse paramSCResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 96	com/starcor/apitask/ServerApiTask2:apiTaskListener	Lcom/starcor/apitask/IApiTaskListener;
    //   4: ifnonnull +4 -> 8
    //   7: return
    //   8: aload_1
    //   9: invokestatic 102	com/starcor/server/api/manage/ServerApiTools:isSCResponseError	(Lcom/starcor/httpapi/core/SCResponse;)Z
    //   12: ifeq +29 -> 41
    //   15: aload_0
    //   16: getfield 96	com/starcor/apitask/ServerApiTask2:apiTaskListener	Lcom/starcor/apitask/IApiTaskListener;
    //   19: new 104	com/starcor/server/api/manage/ServerApiTaskInfo
    //   22: dup
    //   23: aload_0
    //   24: invokevirtual 108	com/starcor/apitask/ServerApiTask2:getTaskId	()I
    //   27: aload_1
    //   28: invokespecial 111	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILcom/starcor/httpapi/core/SCResponse;)V
    //   31: aload_1
    //   32: invokestatic 115	com/starcor/server/api/manage/ServerApiTools:buildCommonError	(Lcom/starcor/httpapi/core/SCResponse;)Lcom/starcor/server/api/manage/ServerApiCommonError;
    //   35: invokeinterface 121 3 0
    //   40: return
    //   41: new 123	org/xml/sax/InputSource
    //   44: dup
    //   45: new 125	java/io/ByteArrayInputStream
    //   48: dup
    //   49: aload_1
    //   50: invokevirtual 131	com/starcor/httpapi/core/SCResponse:getContents	()[B
    //   53: invokespecial 134	java/io/ByteArrayInputStream:<init>	([B)V
    //   56: invokespecial 135	org/xml/sax/InputSource:<init>	(Ljava/io/InputStream;)V
    //   59: astore_2
    //   60: invokestatic 141	javax/xml/parsers/SAXParserFactory:newInstance	()Ljavax/xml/parsers/SAXParserFactory;
    //   63: astore 4
    //   65: aload_0
    //   66: invokevirtual 143	com/starcor/apitask/ServerApiTask2:getDefaultHandler	()Lcom/starcor/apitask/ParserHandler;
    //   69: astore 5
    //   71: aload 4
    //   73: invokevirtual 147	javax/xml/parsers/SAXParserFactory:newSAXParser	()Ljavax/xml/parsers/SAXParser;
    //   76: invokevirtual 153	javax/xml/parsers/SAXParser:getXMLReader	()Lorg/xml/sax/XMLReader;
    //   79: astore 7
    //   81: aload 7
    //   83: aload 5
    //   85: invokeinterface 159 2 0
    //   90: aload 7
    //   92: aload 5
    //   94: invokeinterface 163 2 0
    //   99: aload 7
    //   101: aload_2
    //   102: invokeinterface 167 2 0
    //   107: aload 5
    //   109: invokevirtual 173	com/starcor/apitask/ParserHandler:getResult	()Ljava/lang/Object;
    //   112: astore 8
    //   114: getstatic 22	com/starcor/apitask/ServerApiTask2:TAG	Ljava/lang/String;
    //   117: new 57	java/lang/StringBuilder
    //   120: dup
    //   121: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   124: aload_0
    //   125: invokevirtual 176	com/starcor/apitask/ServerApiTask2:getTaskName	()Ljava/lang/String;
    //   128: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: ldc 178
    //   133: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: aload 8
    //   138: invokevirtual 181	java/lang/Object:toString	()Ljava/lang/String;
    //   141: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   147: invokestatic 187	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   150: aload_0
    //   151: getfield 96	com/starcor/apitask/ServerApiTask2:apiTaskListener	Lcom/starcor/apitask/IApiTaskListener;
    //   154: new 104	com/starcor/server/api/manage/ServerApiTaskInfo
    //   157: dup
    //   158: aload_0
    //   159: invokevirtual 108	com/starcor/apitask/ServerApiTask2:getTaskId	()I
    //   162: aload_1
    //   163: invokespecial 111	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILcom/starcor/httpapi/core/SCResponse;)V
    //   166: aload 8
    //   168: invokeinterface 191 3 0
    //   173: return
    //   174: astore_3
    //   175: aload_3
    //   176: invokevirtual 192	java/lang/Exception:printStackTrace	()V
    //   179: aload_0
    //   180: getfield 96	com/starcor/apitask/ServerApiTask2:apiTaskListener	Lcom/starcor/apitask/IApiTaskListener;
    //   183: new 104	com/starcor/server/api/manage/ServerApiTaskInfo
    //   186: dup
    //   187: aload_0
    //   188: invokevirtual 108	com/starcor/apitask/ServerApiTask2:getTaskId	()I
    //   191: aload_1
    //   192: invokespecial 111	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILcom/starcor/httpapi/core/SCResponse;)V
    //   195: aload_1
    //   196: aload_3
    //   197: invokevirtual 195	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   200: invokestatic 199	com/starcor/server/api/manage/ServerApiTools:buildParseError	(Lcom/starcor/httpapi/core/SCResponse;Ljava/lang/String;)Lcom/starcor/server/api/manage/ServerApiCommonError;
    //   203: invokeinterface 121 3 0
    //   208: return
    //   209: astore 6
    //   211: getstatic 22	com/starcor/apitask/ServerApiTask2:TAG	Ljava/lang/String;
    //   214: aload 6
    //   216: invokevirtual 195	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   219: invokestatic 202	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   222: return
    //
    // Exception table:
    //   from	to	target	type
    //   41	71	174	java/lang/Exception
    //   107	173	174	java/lang/Exception
    //   211	222	174	java/lang/Exception
    //   71	107	209	java/lang/Exception
  }

  public void setListener(IApiTaskListener paramIApiTaskListener)
  {
    this.apiTaskListener = paramIApiTaskListener;
  }

  public void setResultJson(boolean paramBoolean)
  {
    if (paramBoolean)
      this.suffix += "&nns_output_type=json";
  }

  public String toString()
  {
    return getClass().getSimpleName() + ", taskName=" + getTaskName();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.apitask.ServerApiTask2
 * JD-Core Version:    0.6.2
 */