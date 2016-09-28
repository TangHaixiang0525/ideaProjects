package com.starcor.xulapp.debug;

import android.content.Intent;
import android.os.Debug;
import android.text.TextUtils;
import com.starcor.xul.XulUtils;
import com.starcor.xulapp.http.XulHttpRequest;
import com.starcor.xulapp.http.XulHttpServer;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerHandler;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerRequest;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerResponse;
import com.starcor.xulapp.utils.XulSystemUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

public class XulDebugServer extends XulHttpServer
{
  public static XulHttpServer.XulHttpServerResponse PENDING_RESPONSE = new XulHttpServer.XulHttpServerResponse(null);
  private static XulHttpServer _debugServer;
  private static XulDebugMonitor _monitor;
  private static ArrayList<IXulDebugCommandHandler> _userHandlers;

  private XulDebugServer()
  {
    super(8810);
  }

  public static XulDebugMonitor getMonitor()
  {
    return _monitor;
  }

  public static boolean registerCommandHandler(IXulDebugCommandHandler paramIXulDebugCommandHandler)
  {
    try
    {
      XulHttpServer localXulHttpServer = _debugServer;
      boolean bool1 = false;
      if (localXulHttpServer == null);
      while (true)
      {
        return bool1;
        if (_userHandlers == null)
          _userHandlers = new ArrayList();
        boolean bool2 = _userHandlers.contains(paramIXulDebugCommandHandler);
        bool1 = false;
        if (!bool2)
        {
          _userHandlers.add(paramIXulDebugCommandHandler);
          bool1 = true;
        }
      }
    }
    finally
    {
    }
  }

  public static void startUp()
  {
    if (_debugServer != null)
      return;
    _debugServer = new XulDebugServer();
    _monitor = new XulDebugMonitor();
  }

  protected XulHttpServer.XulHttpServerHandler createHandler(XulHttpServer paramXulHttpServer, SocketChannel paramSocketChannel)
  {
    return new XulDebugApiHandler(paramXulHttpServer, paramSocketChannel);
  }

  private static class XulDebugApiHandler extends XulHttpServer.XulHttpServerHandler
  {
    private volatile SimpleDateFormat _dateTimeFormat;

    public XulDebugApiHandler(XulHttpServer paramXulHttpServer, SocketChannel paramSocketChannel)
    {
      super(paramSocketChannel);
    }

    private XulHttpServer.XulHttpServerResponse addItemClass(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return null;
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.addItemClass(XulUtils.tryParseInt(paramArrayOfString[0]), (String[])Arrays.copyOfRange(paramArrayOfString, 1, paramArrayOfString.length), paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse closePage(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.closePage(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml").writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse dropPageState(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.popPageState(paramInt, true, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse dumpItemContent(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.getItemContent(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse dumpPageContent(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
      throws IOException
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.getPageLayout(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse execUserObjectCommand(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt, String paramString)
    {
      return XulDebugServer._monitor.execUserObjectCommand(paramInt, paramString, paramXulHttpServerRequest, this);
    }

    private XulHttpServer.XulHttpServerResponse fireEvent(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return null;
      int i = XulUtils.tryParseInt(paramArrayOfString[0]);
      String str = paramArrayOfString[1];
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      int j = paramArrayOfString.length;
      String[] arrayOfString = null;
      if (j > 2)
        arrayOfString = (String[])Arrays.copyOfRange(paramArrayOfString, 2, paramArrayOfString.length);
      if (XulDebugServer._monitor.fireEvent(i, str, arrayOfString, paramXulHttpServerRequest, localXulHttpServerResponse))
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml").writeBody("<result status=\"OK\"/>");
      while (true)
      {
        return localXulHttpServerResponse;
        localXulHttpServerResponse.setStatus(404).cleanBody();
      }
    }

    // ERROR //
    private XulHttpServer.XulHttpServerResponse getAssets(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String paramString)
    {
      // Byte code:
      //   0: aload_2
      //   1: ldc 102
      //   3: invokestatic 108	java/net/URLDecoder:decode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   6: astore 4
      //   8: aconst_null
      //   9: astore 5
      //   11: aload 4
      //   13: iconst_0
      //   14: anewarray 110	java/lang/String
      //   17: invokestatic 116	com/starcor/xul/XulWorker:loadData	(Ljava/lang/String;[Ljava/lang/String;)Ljava/io/InputStream;
      //   20: astore 11
      //   22: aload 11
      //   24: astore 5
      //   26: aload 5
      //   28: ifnonnull +35 -> 63
      //   31: aconst_null
      //   32: astore 9
      //   34: aload 5
      //   36: ifnull +8 -> 44
      //   39: aload 5
      //   41: invokevirtual 122	java/io/InputStream:close	()V
      //   44: aload 9
      //   46: areturn
      //   47: astore_3
      //   48: aload_3
      //   49: invokevirtual 125	java/io/UnsupportedEncodingException:printStackTrace	()V
      //   52: aconst_null
      //   53: areturn
      //   54: astore 12
      //   56: aload 12
      //   58: invokevirtual 126	java/io/IOException:printStackTrace	()V
      //   61: aconst_null
      //   62: areturn
      //   63: aload_0
      //   64: aload_1
      //   65: invokevirtual 16	com/starcor/xulapp/debug/XulDebugServer$XulDebugApiHandler:getResponse	(Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerRequest;)Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse;
      //   68: ldc 43
      //   70: ldc 128
      //   72: invokevirtual 51	com/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse:addHeader	(Ljava/lang/String;Ljava/lang/String;)Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse;
      //   75: aload 5
      //   77: invokevirtual 131	com/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse:writeBody	(Ljava/io/InputStream;)Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerResponse;
      //   80: astore 13
      //   82: aload 13
      //   84: astore 9
      //   86: aload 5
      //   88: ifnull -44 -> 44
      //   91: aload 5
      //   93: invokevirtual 122	java/io/InputStream:close	()V
      //   96: aload 9
      //   98: areturn
      //   99: astore 14
      //   101: aload 14
      //   103: invokevirtual 126	java/io/IOException:printStackTrace	()V
      //   106: aload 9
      //   108: areturn
      //   109: astore 8
      //   111: aload 8
      //   113: invokevirtual 132	java/lang/Exception:printStackTrace	()V
      //   116: aconst_null
      //   117: astore 9
      //   119: aload 5
      //   121: ifnull -77 -> 44
      //   124: aload 5
      //   126: invokevirtual 122	java/io/InputStream:close	()V
      //   129: aconst_null
      //   130: areturn
      //   131: astore 10
      //   133: aload 10
      //   135: invokevirtual 126	java/io/IOException:printStackTrace	()V
      //   138: aconst_null
      //   139: areturn
      //   140: astore 6
      //   142: aload 5
      //   144: ifnull +8 -> 152
      //   147: aload 5
      //   149: invokevirtual 122	java/io/InputStream:close	()V
      //   152: aload 6
      //   154: athrow
      //   155: astore 7
      //   157: aload 7
      //   159: invokevirtual 126	java/io/IOException:printStackTrace	()V
      //   162: goto -10 -> 152
      //
      // Exception table:
      //   from	to	target	type
      //   0	8	47	java/io/UnsupportedEncodingException
      //   39	44	54	java/io/IOException
      //   91	96	99	java/io/IOException
      //   11	22	109	java/lang/Exception
      //   63	82	109	java/lang/Exception
      //   124	129	131	java/io/IOException
      //   11	22	140	finally
      //   63	82	140	finally
      //   111	116	140	finally
      //   147	152	155	java/io/IOException
    }

    private XulHttpServer.XulHttpServerResponse getPageSnapShot(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
      throws IOException
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.getPageSnapshot(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "image/jpeg");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse getUserObject(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.getUserObject(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse highlightItem(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.highlightItem(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml").writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse listPages(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.dumpPageList(paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse listUserObjects(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.listUserObjects(paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404).cleanBody();
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse popPageState(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.popPageState(paramInt, false, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse pushPageState(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.pushPageState(paramArrayOfString, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse removeItemClass(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return null;
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.removeItemClass(XulUtils.tryParseInt(paramArrayOfString[0]), (String[])Arrays.copyOfRange(paramArrayOfString, 1, paramArrayOfString.length), paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse requestItemFocus(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, int paramInt)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.requestItemFocus(paramInt, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
        localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse responseCommandOutput(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String paramString)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest).addHeader("Content-Type", "text/plain");
      try
      {
        final Process localProcess = Runtime.getRuntime().exec(paramString);
        localXulHttpServerResponse.writeStream(localProcess.getInputStream()).addHeader("Transfer-Encoding", "chunked").setCleanUp(new Runnable()
        {
          public void run()
          {
            localProcess.destroy();
          }
        });
        return localXulHttpServerResponse;
      }
      catch (Exception localException)
      {
        localException.printStackTrace(new PrintStream(localXulHttpServerResponse.getBodyStream()));
      }
      return localXulHttpServerResponse;
    }

    private void responseFile(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, final File paramFile, boolean paramBoolean)
      throws IOException
    {
      if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.canRead()))
      {
        super.handleHttpRequest(paramXulHttpServerRequest);
        return;
      }
      FileInputStream localFileInputStream = new FileInputStream(paramFile);
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      localXulHttpServerResponse.addHeader("Content-Type", "application/oct-stream").writeStream(localFileInputStream);
      if (paramBoolean)
        localXulHttpServerResponse.setCleanUp(new Runnable()
        {
          public void run()
          {
            paramFile.delete();
          }
        });
      localXulHttpServerResponse.send();
    }

    private XulHttpServer.XulHttpServerResponse sendKeyEvent(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      if (XulDebugServer._monitor.sendKeyEvents(paramArrayOfString, paramXulHttpServerRequest, localXulHttpServerResponse))
      {
        localXulHttpServerResponse.addHeader("Content-Type", "text/xml").writeBody("<result status=\"OK\"/>");
        return localXulHttpServerResponse;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse sendMotionEvent(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      return null;
    }

    private XulHttpServer.XulHttpServerResponse setItemAttr(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      if ((paramArrayOfString.length != 2) && (paramArrayOfString.length != 3))
        return null;
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      while (true)
      {
        try
        {
          String str1 = URLDecoder.decode(paramArrayOfString[1], "utf-8");
          if (paramArrayOfString.length > 2)
          {
            str2 = URLDecoder.decode(paramArrayOfString[2], "utf-8");
            if (!XulDebugServer._monitor.setItemAttr(XulUtils.tryParseInt(paramArrayOfString[0]), str1, str2, paramXulHttpServerRequest, localXulHttpServerResponse))
              break;
            localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
            localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
            return localXulHttpServerResponse;
          }
        }
        catch (Exception localException)
        {
          localXulHttpServerResponse.setStatus(501);
          localException.printStackTrace(new PrintStream(localXulHttpServerResponse.getBodyStream()));
          return localXulHttpServerResponse;
        }
        String str2 = null;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse setItemStyle(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      if ((paramArrayOfString.length != 2) && (paramArrayOfString.length != 3))
        return null;
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
      while (true)
      {
        try
        {
          String str1 = URLDecoder.decode(paramArrayOfString[1], "utf-8");
          if (paramArrayOfString.length > 2)
          {
            str2 = URLDecoder.decode(paramArrayOfString[2], "utf-8");
            if (!XulDebugServer._monitor.setItemStyle(XulUtils.tryParseInt(paramArrayOfString[0]), str1, str2, paramXulHttpServerRequest, localXulHttpServerResponse))
              break;
            localXulHttpServerResponse.addHeader("Content-Type", "text/xml");
            localXulHttpServerResponse.writeBody("<result status=\"OK\"/>");
            return localXulHttpServerResponse;
          }
        }
        catch (Exception localException)
        {
          localXulHttpServerResponse.setStatus(501);
          localException.printStackTrace(new PrintStream(localXulHttpServerResponse.getBodyStream()));
          return localXulHttpServerResponse;
        }
        String str2 = null;
      }
      localXulHttpServerResponse.setStatus(404);
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse startActivity(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String paramString1, String paramString2, String paramString3)
    {
      Intent localIntent;
      while (true)
      {
        String str;
        String[] arrayOfString;
        try
        {
          localIntent = new Intent(paramString2);
          str = XulDebugAdapter.getPackageName();
          if (!TextUtils.isEmpty(paramString1))
          {
            arrayOfString = paramString1.split(",");
            if (arrayOfString.length == 1)
            {
              if (paramString1.startsWith("."))
                paramString1 = str + paramString1;
              localIntent.setClass(XulDebugAdapter.getAppContext(), getClass().getClassLoader().loadClass(paramString1));
            }
          }
          else
          {
            if (!TextUtils.isEmpty(str))
              localIntent.setPackage(str);
            if (!TextUtils.isEmpty(paramString3))
              localIntent.addCategory(paramString3);
            if (paramXulHttpServerRequest.queries == null)
              break;
            Iterator localIterator = paramXulHttpServerRequest.queries.entrySet().iterator();
            if (!localIterator.hasNext())
              break;
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            localIntent.putExtra((String)localEntry.getKey(), (String)localEntry.getValue());
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return null;
        }
        if (arrayOfString.length == 2)
        {
          str = arrayOfString[0];
          paramString1 = arrayOfString[1];
        }
      }
      localIntent.addFlags(268435456);
      XulDebugAdapter.startActivity(localIntent);
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = getResponse(paramXulHttpServerRequest).addHeader("Content-Type", "text/xml").writeBody("<result status=\"OK\"/>");
      return localXulHttpServerResponse;
    }

    private XulHttpServer.XulHttpServerResponse startActivity(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, String[] paramArrayOfString)
    {
      if (paramArrayOfString == null)
        return null;
      String str1;
      String str2;
      if (paramArrayOfString.length > 0)
      {
        str1 = paramArrayOfString[0].trim();
        if (paramArrayOfString.length <= 1)
          break label62;
        str2 = paramArrayOfString[1].trim();
        label32: if (paramArrayOfString.length <= 2)
          break label70;
      }
      label62: label70: for (String str3 = paramArrayOfString[2].trim(); ; str3 = null)
      {
        return startActivity(paramXulHttpServerRequest, str1, str2, str3);
        str1 = null;
        break;
        str2 = "android.intent.action.MAIN";
        break label32;
      }
    }

    private XulHttpServer.XulHttpServerResponse unsupportedCommand(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
    {
      ArrayList localArrayList = XulDebugServer._userHandlers;
      if (localArrayList != null)
      {
        int i = 0;
        int j = localArrayList.size();
        while (i < j)
        {
          IXulDebugCommandHandler localIXulDebugCommandHandler = (IXulDebugCommandHandler)localArrayList.get(i);
          try
          {
            XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = localIXulDebugCommandHandler.execCommand(paramXulHttpServerRequest.path, this, paramXulHttpServerRequest);
            if (localXulHttpServerResponse != null)
              return localXulHttpServerResponse;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            i++;
          }
        }
      }
      return getResponse(paramXulHttpServerRequest).setStatus(501).setMessage("Debug API Not implemented");
    }

    public XulHttpServer.XulHttpServerResponse getResponse(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = super.getResponse(paramXulHttpServerRequest);
      try
      {
        if (this._dateTimeFormat == null)
        {
          this._dateTimeFormat = new SimpleDateFormat("ccc, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
          this._dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        localXulHttpServerResponse.addHeader("Date", this._dateTimeFormat.format(new Date())).addHeader("Server", "Xul Debug Server/1.0");
        return localXulHttpServerResponse;
      }
      finally
      {
      }
    }

    protected void handleHttpRequest(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
      throws IOException
    {
      String str1 = paramXulHttpServerRequest.path;
      if ((str1 != null) && (str1.startsWith("/api/")))
      {
        XulHttpServer.XulHttpServerResponse localXulHttpServerResponse;
        if ("/api/list-pages".equals(str1))
          localXulHttpServerResponse = listPages(paramXulHttpServerRequest);
        while (localXulHttpServerResponse == XulDebugServer.PENDING_RESPONSE)
        {
          return;
          if (str1.startsWith("/api/take-snapshot/"))
          {
            localXulHttpServerResponse = getPageSnapShot(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(19)));
          }
          else if (str1.startsWith("/api/get-layout/"))
          {
            localXulHttpServerResponse = dumpPageContent(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(16)));
          }
          else if (str1.startsWith("/api/get-item/"))
          {
            localXulHttpServerResponse = dumpItemContent(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(14)));
          }
          else if (str1.startsWith("/api/add-class/"))
          {
            localXulHttpServerResponse = addItemClass(paramXulHttpServerRequest, str1.substring(15).split("/"));
          }
          else if (str1.startsWith("/api/remove-class/"))
          {
            localXulHttpServerResponse = removeItemClass(paramXulHttpServerRequest, str1.substring(18).split("/"));
          }
          else if (str1.startsWith("/api/set-style/"))
          {
            localXulHttpServerResponse = setItemStyle(paramXulHttpServerRequest, str1.substring(15).split("/"));
          }
          else
          {
            boolean bool = str1.startsWith("/api/set-data/");
            localXulHttpServerResponse = null;
            if (!bool)
              if (str1.startsWith("/api/set-attr/"))
              {
                localXulHttpServerResponse = setItemAttr(paramXulHttpServerRequest, str1.substring(14).split("/"));
              }
              else if (str1.startsWith("/api/request-focus/"))
              {
                localXulHttpServerResponse = requestItemFocus(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(19)));
              }
              else if (str1.startsWith("/api/push-states/"))
              {
                localXulHttpServerResponse = pushPageState(paramXulHttpServerRequest, str1.substring(17).split("/"));
              }
              else if (str1.startsWith("/api/pop-states/"))
              {
                localXulHttpServerResponse = popPageState(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(16)));
              }
              else if (str1.startsWith("/api/drop-states/"))
              {
                localXulHttpServerResponse = dropPageState(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(17)));
              }
              else if (str1.startsWith("/api/get-assets/"))
              {
                localXulHttpServerResponse = getAssets(paramXulHttpServerRequest, str1.substring(16));
              }
              else if (str1.startsWith("/api/send-key/"))
              {
                localXulHttpServerResponse = sendKeyEvent(paramXulHttpServerRequest, str1.substring(14).split("/"));
              }
              else if (str1.startsWith("/api/send-motion/"))
              {
                localXulHttpServerResponse = sendMotionEvent(paramXulHttpServerRequest, str1.substring(17).split("/"));
              }
              else if (str1.equals("/api/list-user-objects"))
              {
                localXulHttpServerResponse = listUserObjects(paramXulHttpServerRequest);
              }
              else if (str1.startsWith("/api/get-user-object/"))
              {
                String str2 = str1.substring(21);
                int i = str2.indexOf('/');
                String str3 = "";
                if (i > 0)
                {
                  str3 = str2.substring(i + 1);
                  str2 = str2.substring(0, i);
                }
                if (TextUtils.isEmpty(str3))
                  localXulHttpServerResponse = getUserObject(paramXulHttpServerRequest, XulUtils.tryParseInt(str2));
                else
                  localXulHttpServerResponse = execUserObjectCommand(paramXulHttpServerRequest, XulUtils.tryParseInt(str2), str3);
              }
              else if (str1.startsWith("/api/close-page/"))
              {
                localXulHttpServerResponse = closePage(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(16)));
              }
              else if (str1.equals("/api/start-activity"))
              {
                localXulHttpServerResponse = startActivity(paramXulHttpServerRequest, null, "android.intent.action.MAIN", null);
              }
              else if (str1.startsWith("/api/start-activity/"))
              {
                localXulHttpServerResponse = startActivity(paramXulHttpServerRequest, str1.substring(20).split("/"));
              }
              else if (str1.startsWith("/api/highlight-item/"))
              {
                localXulHttpServerResponse = highlightItem(paramXulHttpServerRequest, XulUtils.tryParseInt(str1.substring(20)));
              }
              else if (str1.startsWith("/api/fire-event/"))
              {
                localXulHttpServerResponse = fireEvent(paramXulHttpServerRequest, str1.substring(16).split("/"));
              }
              else if (str1.equals("/api/get-trace"))
              {
                localXulHttpServerResponse = responseCommandOutput(paramXulHttpServerRequest, "cat /data/anr/traces.txt");
              }
              else if (str1.equals("/api/get-logcat"))
              {
                localXulHttpServerResponse = responseCommandOutput(paramXulHttpServerRequest, "logcat -v time");
              }
              else if (str1.equals("/api/dump-heap"))
              {
                localXulHttpServerResponse = getResponse(paramXulHttpServerRequest);
                XulHttpRequest localXulHttpRequest = paramXulHttpServerRequest.makeCloneNoQueryString();
                localXulHttpRequest.path = (localXulHttpRequest.path + "/" + XulDebugAdapter.getPackageName() + ".hprof");
                localXulHttpServerResponse.setStatus(302).addHeader("Location", localXulHttpRequest.toString());
              }
              else
              {
                if (str1.startsWith("/api/dump-heap/"))
                {
                  File localFile = File.createTempFile("dump-", ".hprof", XulSystemUtil.getDiskCacheDir(XulDebugAdapter.getAppContext(), "debug"));
                  Debug.dumpHprofData(localFile.getAbsolutePath());
                  responseFile(paramXulHttpServerRequest, localFile, true);
                  return;
                }
                localXulHttpServerResponse = unsupportedCommand(paramXulHttpServerRequest);
              }
          }
        }
        if (localXulHttpServerResponse != null)
        {
          localXulHttpServerResponse.send();
          return;
        }
      }
      super.handleHttpRequest(paramXulHttpServerRequest);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.debug.XulDebugServer
 * JD-Core Version:    0.6.2
 */