package com.starcor.xulapp.http;

import android.text.TextUtils;
import com.starcor.xul.Utils.XulMemoryOutputStream;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulWorker;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XulHttpServer
{
  private Thread _listeningWorker;
  private String _localAddr;
  private int _localPort;
  private ThreadPoolExecutor _reactorPool;
  private volatile Selector _selector;
  private volatile ServerSocketChannel _socketChannel;
  private ThreadGroup _workerGroup;

  public XulHttpServer(int paramInt)
  {
    _initServer(null, paramInt);
  }

  public XulHttpServer(String paramString, int paramInt)
  {
    _initServer(paramString, paramInt);
  }

  // ERROR //
  private void _doWork()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 36	com/starcor/xulapp/http/XulHttpServer:_selector	Ljava/nio/channels/Selector;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnonnull +95 -> 103
    //   11: aload_0
    //   12: invokestatic 42	java/nio/channels/Selector:open	()Ljava/nio/channels/Selector;
    //   15: putfield 36	com/starcor/xulapp/http/XulHttpServer:_selector	Ljava/nio/channels/Selector;
    //   18: aload_0
    //   19: getfield 36	com/starcor/xulapp/http/XulHttpServer:_selector	Ljava/nio/channels/Selector;
    //   22: invokevirtual 46	java/nio/channels/Selector:provider	()Ljava/nio/channels/spi/SelectorProvider;
    //   25: astore 19
    //   27: aload_0
    //   28: getfield 48	com/starcor/xulapp/http/XulHttpServer:_localAddr	Ljava/lang/String;
    //   31: invokestatic 54	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   34: ifeq +183 -> 217
    //   37: new 56	java/net/InetSocketAddress
    //   40: dup
    //   41: aload_0
    //   42: getfield 58	com/starcor/xulapp/http/XulHttpServer:_localPort	I
    //   45: invokespecial 60	java/net/InetSocketAddress:<init>	(I)V
    //   48: astore 20
    //   50: aload_0
    //   51: aload 19
    //   53: invokevirtual 66	java/nio/channels/spi/SelectorProvider:openServerSocketChannel	()Ljava/nio/channels/ServerSocketChannel;
    //   56: putfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   59: aload_0
    //   60: getfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   63: iconst_0
    //   64: invokevirtual 74	java/nio/channels/ServerSocketChannel:configureBlocking	(Z)Ljava/nio/channels/SelectableChannel;
    //   67: pop
    //   68: aload_0
    //   69: getfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   72: invokevirtual 78	java/nio/channels/ServerSocketChannel:socket	()Ljava/net/ServerSocket;
    //   75: aload 20
    //   77: invokevirtual 84	java/net/ServerSocket:bind	(Ljava/net/SocketAddress;)V
    //   80: aload_0
    //   81: getfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   84: aload_0
    //   85: getfield 36	com/starcor/xulapp/http/XulHttpServer:_selector	Ljava/nio/channels/Selector;
    //   88: aload_0
    //   89: getfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   92: invokevirtual 88	java/nio/channels/ServerSocketChannel:validOps	()I
    //   95: aload_0
    //   96: getfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   99: invokevirtual 92	java/nio/channels/ServerSocketChannel:register	(Ljava/nio/channels/Selector;ILjava/lang/Object;)Ljava/nio/channels/SelectionKey;
    //   102: pop
    //   103: aload_0
    //   104: monitorexit
    //   105: aload_0
    //   106: getfield 36	com/starcor/xulapp/http/XulHttpServer:_selector	Ljava/nio/channels/Selector;
    //   109: astore_3
    //   110: aload_3
    //   111: ifnull +286 -> 397
    //   114: aload_3
    //   115: invokevirtual 96	java/nio/channels/Selector:isOpen	()Z
    //   118: ifeq +279 -> 397
    //   121: aload_3
    //   122: invokevirtual 99	java/nio/channels/Selector:select	()I
    //   125: pop
    //   126: aload_3
    //   127: monitorenter
    //   128: aload_3
    //   129: invokevirtual 103	java/nio/channels/Selector:selectedKeys	()Ljava/util/Set;
    //   132: astore 7
    //   134: aload_3
    //   135: monitorexit
    //   136: aload 7
    //   138: invokeinterface 109 1 0
    //   143: astore 8
    //   145: aload 7
    //   147: monitorenter
    //   148: aload 8
    //   150: invokeinterface 114 1 0
    //   155: ifeq +104 -> 259
    //   158: aload 8
    //   160: invokeinterface 118 1 0
    //   165: checkcast 120	java/nio/channels/SelectionKey
    //   168: astore 10
    //   170: aload 8
    //   172: invokeinterface 123 1 0
    //   177: aload 7
    //   179: monitorexit
    //   180: aload 10
    //   182: ifnull -37 -> 145
    //   185: aload 10
    //   187: invokevirtual 126	java/nio/channels/SelectionKey:isValid	()Z
    //   190: ifne +83 -> 273
    //   193: aload 10
    //   195: invokevirtual 129	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
    //   198: checkcast 131	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler
    //   201: invokevirtual 134	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler:terminate	()V
    //   204: goto -59 -> 145
    //   207: astore 4
    //   209: aload 4
    //   211: invokevirtual 137	java/io/IOException:printStackTrace	()V
    //   214: goto -109 -> 105
    //   217: new 56	java/net/InetSocketAddress
    //   220: dup
    //   221: aload_0
    //   222: getfield 48	com/starcor/xulapp/http/XulHttpServer:_localAddr	Ljava/lang/String;
    //   225: aload_0
    //   226: getfield 58	com/starcor/xulapp/http/XulHttpServer:_localPort	I
    //   229: invokespecial 139	java/net/InetSocketAddress:<init>	(Ljava/lang/String;I)V
    //   232: astore 20
    //   234: goto -184 -> 50
    //   237: astore 18
    //   239: aload 18
    //   241: invokevirtual 140	java/lang/Exception:printStackTrace	()V
    //   244: goto -141 -> 103
    //   247: astore_1
    //   248: aload_0
    //   249: monitorexit
    //   250: aload_1
    //   251: athrow
    //   252: astore 6
    //   254: aload_3
    //   255: monitorexit
    //   256: aload 6
    //   258: athrow
    //   259: aload 7
    //   261: monitorexit
    //   262: goto -157 -> 105
    //   265: astore 9
    //   267: aload 7
    //   269: monitorexit
    //   270: aload 9
    //   272: athrow
    //   273: aload 10
    //   275: invokevirtual 143	java/nio/channels/SelectionKey:isAcceptable	()Z
    //   278: ifeq +52 -> 330
    //   281: aload_0
    //   282: getfield 68	com/starcor/xulapp/http/XulHttpServer:_socketChannel	Ljava/nio/channels/ServerSocketChannel;
    //   285: invokevirtual 147	java/nio/channels/ServerSocketChannel:accept	()Ljava/nio/channels/SocketChannel;
    //   288: astore 15
    //   290: aload 15
    //   292: ifnull -147 -> 145
    //   295: aload 15
    //   297: iconst_0
    //   298: invokevirtual 150	java/nio/channels/SocketChannel:configureBlocking	(Z)Ljava/nio/channels/SelectableChannel;
    //   301: pop
    //   302: aload 15
    //   304: aload_3
    //   305: iconst_1
    //   306: aload_0
    //   307: aload_0
    //   308: aload 15
    //   310: invokevirtual 154	com/starcor/xulapp/http/XulHttpServer:createHandler	(Lcom/starcor/xulapp/http/XulHttpServer;Ljava/nio/channels/SocketChannel;)Lcom/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler;
    //   313: invokevirtual 155	java/nio/channels/SocketChannel:register	(Ljava/nio/channels/Selector;ILjava/lang/Object;)Ljava/nio/channels/SelectionKey;
    //   316: pop
    //   317: goto -172 -> 145
    //   320: astore 11
    //   322: aload 11
    //   324: invokevirtual 140	java/lang/Exception:printStackTrace	()V
    //   327: goto -182 -> 145
    //   330: aload 10
    //   332: invokevirtual 129	java/nio/channels/SelectionKey:attachment	()Ljava/lang/Object;
    //   335: checkcast 131	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler
    //   338: astore 12
    //   340: aload 10
    //   342: invokevirtual 158	java/nio/channels/SelectionKey:isWritable	()Z
    //   345: ifeq +21 -> 366
    //   348: aload 12
    //   350: invokevirtual 161	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler:notifyWritable	()V
    //   353: goto -208 -> 145
    //   356: astore 14
    //   358: aload 12
    //   360: invokevirtual 134	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler:terminate	()V
    //   363: goto -218 -> 145
    //   366: aload 10
    //   368: invokevirtual 164	java/nio/channels/SelectionKey:isReadable	()Z
    //   371: ifeq -226 -> 145
    //   374: aload 12
    //   376: invokevirtual 167	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler:notifyReadable	()V
    //   379: goto -234 -> 145
    //   382: astore 13
    //   384: aload 13
    //   386: invokevirtual 140	java/lang/Exception:printStackTrace	()V
    //   389: aload 12
    //   391: invokevirtual 134	com/starcor/xulapp/http/XulHttpServer$XulHttpServerHandler:terminate	()V
    //   394: goto -249 -> 145
    //   397: return
    //
    // Exception table:
    //   from	to	target	type
    //   121	128	207	java/io/IOException
    //   136	145	207	java/io/IOException
    //   145	148	207	java/io/IOException
    //   185	204	207	java/io/IOException
    //   256	259	207	java/io/IOException
    //   270	273	207	java/io/IOException
    //   273	290	207	java/io/IOException
    //   295	317	207	java/io/IOException
    //   322	327	207	java/io/IOException
    //   330	340	207	java/io/IOException
    //   340	353	207	java/io/IOException
    //   358	363	207	java/io/IOException
    //   366	379	207	java/io/IOException
    //   384	394	207	java/io/IOException
    //   11	50	237	java/lang/Exception
    //   50	103	237	java/lang/Exception
    //   217	234	237	java/lang/Exception
    //   2	7	247	finally
    //   11	50	247	finally
    //   50	103	247	finally
    //   103	105	247	finally
    //   217	234	247	finally
    //   239	244	247	finally
    //   248	250	247	finally
    //   128	136	252	finally
    //   254	256	252	finally
    //   148	180	265	finally
    //   259	262	265	finally
    //   267	270	265	finally
    //   273	290	320	java/lang/Exception
    //   295	317	320	java/lang/Exception
    //   340	353	356	java/nio/channels/CancelledKeyException
    //   366	379	356	java/nio/channels/CancelledKeyException
    //   340	353	382	java/lang/Exception
    //   366	379	382	java/lang/Exception
  }

  private void _initServer(String paramString, int paramInt)
  {
    this._localAddr = paramString;
    this._localPort = paramInt;
    this._workerGroup = new ThreadGroup("Xul HTTP Server");
    ThreadFactory local1 = new ThreadFactory()
    {
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        return new Thread(XulHttpServer.this._workerGroup, paramAnonymousRunnable, "Reactor");
      }
    };
    this._reactorPool = new ThreadPoolExecutor(1, 16, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(16), local1);
    this._listeningWorker = new Thread(this._workerGroup, "Acceptor")
    {
      public void run()
      {
        XulHttpServer.this._doWork();
      }
    };
    this._listeningWorker.start();
  }

  private void dispatchRequest(final XulHttpServerHandler paramXulHttpServerHandler, final XulHttpServerRequest paramXulHttpServerRequest)
  {
    this._reactorPool.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          paramXulHttpServerHandler.handleHttpRequest(paramXulHttpServerRequest);
          return;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          paramXulHttpServerHandler.terminate();
        }
      }
    });
  }

  protected XulHttpServerHandler createHandler(XulHttpServer paramXulHttpServer, SocketChannel paramSocketChannel)
  {
    return new XulHttpServerHandler(paramXulHttpServer, paramSocketChannel);
  }

  public static class XulHttpServerHandler
  {
    private ByteBuffer _requestBuffer = ByteBuffer.allocate(2048);
    private HttpRequestBuilder _requestBuilder;
    private XulHttpServer.XulHttpServerResponse _response;
    private ByteBuffer _responseBuffer;
    private boolean _sendChunkedData = false;
    private final XulHttpServer _server;
    private final SocketChannel _socketChannel;

    public XulHttpServerHandler(XulHttpServer paramXulHttpServer, SocketChannel paramSocketChannel)
    {
      this._server = paramXulHttpServer;
      this._socketChannel = paramSocketChannel;
    }

    private void _internalHandleHttpRequest(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
      throws IOException
    {
      this._socketChannel.register(this._server._selector, 0, this);
      this._server.dispatchRequest(this, paramXulHttpServerRequest);
    }

    private void clear()
    {
      HttpRequestBuilder localHttpRequestBuilder = this._requestBuilder;
      this._requestBuilder = null;
      if (localHttpRequestBuilder != null)
        localHttpRequestBuilder.destroy();
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = this._response;
      this._response = null;
      if (localXulHttpServerResponse != null)
        localXulHttpServerResponse.destroy();
    }

    protected XulHttpServer.XulHttpServerResponse getResponse(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
    {
      XulHttpServer.XulHttpServerResponse localXulHttpServerResponse = new XulHttpServer.XulHttpServerResponse(this);
      localXulHttpServerResponse.protocolVer = paramXulHttpServerRequest.protocolVer;
      localXulHttpServerResponse.setStatus(200).addHeader("Host", paramXulHttpServerRequest.getHostString());
      return localXulHttpServerResponse;
    }

    SocketChannel getSocketChannel()
    {
      return this._socketChannel;
    }

    protected void handleHttpRequest(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest)
      throws IOException
    {
      getResponse(paramXulHttpServerRequest).setStatus(404).setMessage("Page Not Found").send();
    }

    void notifyReadable()
      throws IOException
    {
      this._requestBuffer.rewind();
      int i = this._socketChannel.read(this._requestBuffer);
      if (i < 0)
        if (this._requestBuilder == null)
          this._socketChannel.close();
      XulHttpServer.XulHttpServerRequest localXulHttpServerRequest1;
      do
      {
        do
          return;
        while (this._requestBuilder.isFinished());
        this._requestBuffer.rewind();
        XulHttpServer.XulHttpServerRequest localXulHttpServerRequest2 = this._requestBuilder.buildRequest(this._requestBuffer, i);
        if (localXulHttpServerRequest2 == null)
        {
          this._socketChannel.close();
          return;
        }
        _internalHandleHttpRequest(localXulHttpServerRequest2);
        return;
        if (this._requestBuilder == null)
          this._requestBuilder = new HttpRequestBuilder();
        this._requestBuffer.rewind();
        localXulHttpServerRequest1 = this._requestBuilder.buildRequest(this._requestBuffer, i);
      }
      while (localXulHttpServerRequest1 == null);
      _internalHandleHttpRequest(localXulHttpServerRequest1);
    }

    void notifyWritable()
      throws IOException
    {
      if (this._responseBuffer == null);
      final SocketChannel localSocketChannel;
      do
      {
        return;
        localSocketChannel = this._socketChannel;
        localSocketChannel.write(this._responseBuffer);
      }
      while (this._responseBuffer.hasRemaining());
      if (this._response.hasUserBodyStream())
      {
        final Selector localSelector = this._server._selector;
        localSocketChannel.register(localSelector, 0, this);
        localSelector.wakeup();
        this._server._reactorPool.execute(new Runnable()
        {
          public void run()
          {
            while (true)
            {
              int m;
              try
              {
                if (!XulHttpServer.XulHttpServerHandler.this._sendChunkedData)
                  break label308;
                i = 32;
                boolean bool = XulHttpServer.XulHttpServerHandler.this._sendChunkedData;
                int j = 0;
                if (bool)
                  j = 2;
                if (!XulHttpServer.XulHttpServerHandler.this._sendChunkedData)
                  break label313;
                k = 8192;
                if ((XulHttpServer.XulHttpServerHandler.this._response == null) || (!XulHttpServer.XulHttpServerHandler.this._response.prepareUserBodyData(i, j, k)))
                {
                  XulHttpServer.XulHttpServerHandler.this.terminate();
                  return;
                }
                m = XulHttpServer.XulHttpServerHandler.this._response.getDataSize();
                if (m > 0)
                  break label184;
                if (XulHttpServer.XulHttpServerHandler.this._sendChunkedData)
                {
                  XulHttpServer.XulHttpServerHandler.this._response.writeStream(null);
                  XulHttpServer.XulHttpServerHandler.access$502(XulHttpServer.XulHttpServerHandler.this, ByteBuffer.wrap("0\r\n\r\n".getBytes()));
                  localSocketChannel.register(localSelector, 4, jdField_this);
                  localSelector.wakeup();
                  return;
                }
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
                XulHttpServer.XulHttpServerHandler.this.terminate();
                return;
              }
              XulHttpServer.XulHttpServerHandler.this.terminate();
              return;
              label184: byte[] arrayOfByte1 = XulHttpServer.XulHttpServerHandler.this._response.getData();
              if (XulHttpServer.XulHttpServerHandler.this._sendChunkedData)
              {
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = Integer.valueOf(m);
                byte[] arrayOfByte2 = String.format("%X\r\n", arrayOfObject).getBytes();
                i -= arrayOfByte2.length;
                System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i, arrayOfByte2.length);
                int n = m + arrayOfByte2.length;
                int i1 = n + 1;
                arrayOfByte1[(i + n)] = 13;
                m = i1 + 1;
                arrayOfByte1[(i + i1)] = 10;
              }
              XulHttpServer.XulHttpServerHandler.access$502(XulHttpServer.XulHttpServerHandler.this, ByteBuffer.wrap(arrayOfByte1, i, m));
              continue;
              label308: int i = 0;
              continue;
              label313: int k = -1;
            }
          }
        });
        return;
      }
      localSocketChannel.close();
    }

    void reply(XulHttpServer.XulHttpServerResponse paramXulHttpServerResponse)
    {
      this._response = paramXulHttpServerResponse;
      paramXulHttpServerResponse.addHeaderIfNotExists("Content-Type", "text/html").addHeaderIfNotExists("Connection", "close");
      this._sendChunkedData = "chunked".equals((String)this._response.headers.get("Transfer-Encoding"));
      paramXulHttpServerResponse.prepareResponseData();
      this._responseBuffer = ByteBuffer.wrap(paramXulHttpServerResponse.getData(), 0, paramXulHttpServerResponse.getDataSize());
      try
      {
        Selector localSelector = this._server._selector;
        this._socketChannel.register(localSelector, 4, this);
        localSelector.wakeup();
        return;
      }
      catch (ClosedChannelException localClosedChannelException)
      {
        localClosedChannelException.printStackTrace();
        clear();
      }
    }

    void terminate()
    {
      SocketChannel localSocketChannel = this._socketChannel;
      if (localSocketChannel != null);
      try
      {
        localSocketChannel.close();
        clear();
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    class HttpRequestBuilder
    {
      private boolean _finished = false;
      private int _parseState = 0;
      private XulMemoryOutputStream _readBuffer = XulWorker.obtainDownloadBuffer(4096);
      private int _readPos = 0;
      private XulHttpServer.XulHttpServerRequest _request;
      private int _requestBodySize = 0;
      private int _scanPos = 0;

      public HttpRequestBuilder()
      {
      }

      private String readLine()
      {
        int i = this._readBuffer.getDataSize();
        byte[] arrayOfByte = this._readBuffer.getDataBuffer();
        int j = this._scanPos;
        if (j < i)
        {
          int k = arrayOfByte[j];
          if ((k != 13) && (k != 10))
            this._scanPos = j;
          while ((j + 1 >= i) || (k != 13) || (arrayOfByte[(j + 1)] != 10))
          {
            j++;
            break;
          }
          int m = this._readPos;
          int n = j;
          int i1 = j + 2;
          this._scanPos = i1;
          this._readPos = i1;
          return new String(arrayOfByte, m, n - m);
        }
        return null;
      }

      public XulHttpServer.XulHttpServerRequest buildRequest(ByteBuffer paramByteBuffer, int paramInt)
      {
        int i = this._readBuffer.getDataSize();
        if (paramInt <= 0)
        {
          int i4 = this._parseState;
          paramInt = 0;
          if (i4 == 2)
            this._requestBodySize = (i - this._readPos);
        }
        int j = i + paramInt;
        this._readBuffer.expand(j);
        byte[] arrayOfByte = this._readBuffer.getDataBuffer();
        paramByteBuffer.get(arrayOfByte, i, paramInt);
        this._readBuffer.setDataSize(i + paramInt);
        while (true)
        {
          String str1;
          if (this._parseState != 2)
          {
            str1 = readLine();
            if (str1 != null)
            {
              if (this._parseState == 0)
              {
                String[] arrayOfString = str1.split(" ");
                String str7 = arrayOfString[0];
                String str8 = arrayOfString[1];
                String str9;
                if (arrayOfString.length > 2)
                {
                  str9 = arrayOfString[2];
                  this._request = new XulHttpServer.XulHttpServerRequest();
                  this._request.method = str7.toLowerCase();
                  this._request.protocolVer = str9.toLowerCase();
                }
                Matcher localMatcher;
                try
                {
                  this._request.schema = str9.split("/")[0].toLowerCase();
                  n = str8.indexOf("?");
                  int i1 = str8.indexOf("#");
                  if (i1 > 0)
                  {
                    XulHttpServer.XulHttpServerRequest localXulHttpServerRequest = this._request;
                    int i3 = i1 + 1;
                    localXulHttpServerRequest.fragment = str8.substring(i3);
                    str8 = str8.substring(0, i1);
                  }
                  if (n < 0)
                  {
                    this._request.path = str8;
                    this._parseState = 1;
                    continue;
                    str9 = "HTTP/1.1";
                  }
                }
                catch (Exception localException)
                {
                  int n;
                  while (true)
                    this._request.schema = "http";
                  this._request.path = str8.substring(0, n);
                  int i2 = n + 1;
                  String str10 = str8.substring(i2);
                  localMatcher = Pattern.compile("([^&=]+)(?:=([^&]*))?").matcher(str10);
                }
                while (localMatcher.find())
                {
                  String str11 = localMatcher.group(1);
                  Object localObject = localMatcher.group(2);
                  try
                  {
                    str11 = URLDecoder.decode(str11, "utf-8");
                    if (!TextUtils.isEmpty((CharSequence)localObject))
                    {
                      String str12 = URLDecoder.decode((String)localObject, "utf-8");
                      localObject = str12;
                    }
                    this._request.addQueryString(str11, (String)localObject);
                  }
                  catch (UnsupportedEncodingException localUnsupportedEncodingException)
                  {
                    while (true)
                      localUnsupportedEncodingException.printStackTrace();
                  }
                }
              }
              if (this._parseState != 1)
                continue;
              if (!str1.isEmpty())
                break label646;
              this._parseState = 2;
              String str4 = this._request.getHeader("connection");
              if ((str4 != null) && (str4.toLowerCase().contains("close")))
                this._requestBodySize = 2147483647;
              String str5 = this._request.getHeader("expect");
              if ((str5 != null) && (str5.contains("100-continue")))
                if (this._requestBodySize > 1048576)
                  try
                  {
                    XulHttpServer.XulHttpServerHandler.this._socketChannel.close();
                    return null;
                  }
                  catch (IOException localIOException2)
                  {
                    while (true)
                      localIOException2.printStackTrace();
                  }
            }
          }
          else
          {
            try
            {
              String str6 = this._request.protocolVer.toUpperCase() + " 100 Continue\r\n\r\n";
              XulHttpServer.XulHttpServerHandler.this._socketChannel.write(ByteBuffer.wrap(str6.getBytes("utf-8")));
              if ((this._parseState != 2) || (this._requestBodySize > j - this._readPos))
                break;
              if (this._requestBodySize > 0)
                this._request.body = Arrays.copyOfRange(arrayOfByte, this._readPos, this._readPos + this._requestBodySize);
              return this._request;
            }
            catch (IOException localIOException1)
            {
              while (true)
                localIOException1.printStackTrace();
            }
            label646: int k = str1.indexOf(':');
            String str2 = str1.substring(0, k).trim();
            String str3 = str1.substring(k + 1).trim();
            this._request.addHeader(str2.toLowerCase(), str3);
            if (str2.equalsIgnoreCase("content-length"))
            {
              this._requestBodySize = XulUtils.tryParseInt(str3);
            }
            else if (str2.equalsIgnoreCase("host"))
            {
              int m = str3.indexOf(":");
              if (m > 0)
              {
                this._request.port = XulUtils.tryParseInt(str3.substring(m + 1));
                this._request.host = str3.substring(0, m);
              }
              else
              {
                this._request.host = str3;
              }
            }
          }
        }
        return null;
      }

      public void destroy()
      {
        if (this._readBuffer != null)
          this._readBuffer.onClose();
      }

      public boolean isFinished()
      {
        return this._finished;
      }
    }
  }

  public static class XulHttpServerRequest extends XulHttpRequest
  {
    public byte[] body;
    public HashMap<String, String> headers = new HashMap();
    public String protocolVer;

    public void addHeader(String paramString1, String paramString2)
    {
      this.headers.put(paramString1, paramString2);
    }

    public String getHeader(String paramString)
    {
      return (String)this.headers.get(paramString);
    }
  }

  public static class XulHttpServerResponse extends XulHttpResponse
  {
    private InputStream _bodyStream;
    private Runnable _cleanup;
    private XulHttpServer.XulHttpServerHandler _handler;
    private XulMemoryOutputStream _outputStream;
    public LinkedHashMap<String, String> headers = new LinkedHashMap();
    public String protocolVer;

    public XulHttpServerResponse(XulHttpServer.XulHttpServerHandler paramXulHttpServerHandler)
    {
      this._handler = paramXulHttpServerHandler;
      this._outputStream = XulWorker.obtainDownloadBuffer(2048);
      this._outputStream.reset(2048);
    }

    static String httpMessageFromCode(int paramInt)
    {
      switch (paramInt)
      {
      default:
        return null;
      case 200:
        return "OK";
      case 401:
        return "Unauthorized";
      case 403:
        return "Forbidden";
      case 404:
        return "Not Found";
      case 301:
        return "Moved Permanently";
      case 302:
        return "Redirect";
      case 304:
        return "Not Modified";
      case 500:
        return "Internal Server Error";
      case 501:
        return "Not implemented";
      case 502:
        return "Proxy Error";
      case 100:
      }
      return "Continue";
    }

    public XulHttpServerResponse addHeader(String paramString1, String paramString2)
    {
      this.headers.put(paramString1, paramString2);
      return this;
    }

    public XulHttpServerResponse addHeaderIfNotExists(String paramString1, String paramString2)
    {
      if (!this.headers.containsKey(paramString1))
        this.headers.put(paramString1, paramString2);
      return this;
    }

    public XulHttpResponse cleanBody()
    {
      this._outputStream.setDataSize(0);
      return this;
    }

    public void destroy()
    {
      if (this._outputStream != null)
        this._outputStream.onClose();
      try
      {
        InputStream localInputStream = this._bodyStream;
        this._bodyStream = null;
        if (localInputStream != null)
          localInputStream.close();
        try
        {
          label35: Runnable localRunnable = this._cleanup;
          this._cleanup = null;
          if (localRunnable != null)
            localRunnable.run();
          return;
        }
        catch (Exception localException2)
        {
        }
      }
      catch (Exception localException1)
      {
        break label35;
      }
    }

    public OutputStream getBodyStream()
    {
      return this._outputStream;
    }

    public OutputStream getBodyStream(int paramInt)
    {
      this._outputStream.expand(paramInt);
      return this._outputStream;
    }

    byte[] getData()
    {
      return this._outputStream.getDataBuffer();
    }

    int getDataSize()
    {
      return this._outputStream.getDataSize();
    }

    boolean hasUserBodyStream()
    {
      return this._bodyStream != null;
    }

    void prepareResponseData()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.protocolVer.toUpperCase());
      localStringBuilder.append(" ");
      localStringBuilder.append(this.code);
      if (TextUtils.isEmpty(this.message))
        this.message = httpMessageFromCode(this.code);
      if (!TextUtils.isEmpty(this.message))
      {
        localStringBuilder.append(" ");
        localStringBuilder.append(this.message);
      }
      localStringBuilder.append("\r\n");
      boolean bool = "chunked".equals((String)this.headers.get("Transfer-Encoding"));
      int i = this._outputStream.getDataSize();
      if ((!hasUserBodyStream()) && (!bool))
        addHeader("Content-Length", String.valueOf(i));
      Iterator localIterator = this.headers.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localStringBuilder.append((String)localEntry.getKey());
        localStringBuilder.append(":");
        localStringBuilder.append((String)localEntry.getValue());
        localStringBuilder.append("\r\n");
      }
      localStringBuilder.append("\r\n");
      try
      {
        byte[] arrayOfByte4 = localStringBuilder.toString().getBytes("utf-8");
        arrayOfByte1 = arrayOfByte4;
        byte[] arrayOfByte2 = this._outputStream.getDataBuffer();
        int j = arrayOfByte1.length;
        int k = i + j;
        this._outputStream.expand(k);
        byte[] arrayOfByte3 = this._outputStream.getDataBuffer();
        System.arraycopy(arrayOfByte2, 0, arrayOfByte3, j, i);
        System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
        this._outputStream.setDataSize(k);
        return;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        while (true)
        {
          localUnsupportedEncodingException.printStackTrace();
          byte[] arrayOfByte1 = null;
        }
      }
    }

    boolean prepareUserBodyData()
    {
      return prepareUserBodyData(0, 0, -1);
    }

    boolean prepareUserBodyData(int paramInt1, int paramInt2)
    {
      return prepareUserBodyData(paramInt1, paramInt2, -1);
    }

    boolean prepareUserBodyData(int paramInt1, int paramInt2, int paramInt3)
    {
      if (paramInt3 < 0)
        this._outputStream.expand(128 + (paramInt1 + paramInt2));
      while (true)
      {
        byte[] arrayOfByte = this._outputStream.getDataBuffer();
        if ((paramInt3 < 0) || (paramInt3 > arrayOfByte.length))
          paramInt3 = arrayOfByte.length;
        try
        {
          int i = this._bodyStream.read(arrayOfByte, paramInt1, paramInt3 - paramInt1 - paramInt2);
          this._outputStream.setDataSize(i);
          return true;
          this._outputStream.expand(paramInt3 + (paramInt1 + paramInt2));
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      return false;
    }

    public void send()
    {
      this._handler.reply(this);
    }

    public XulHttpServerResponse setCleanUp(Runnable paramRunnable)
    {
      this._cleanup = paramRunnable;
      return this;
    }

    public XulHttpServerResponse setMessage(String paramString)
    {
      this.message = paramString;
      return this;
    }

    public XulHttpServerResponse setStatus(int paramInt)
    {
      this.code = paramInt;
      return this;
    }

    public XulHttpServerResponse writeBody(InputStream paramInputStream)
    {
      while (true)
      {
        int i = this._outputStream.getDataSize();
        this._outputStream.expand(i + 1024);
        byte[] arrayOfByte = this._outputStream.getDataBuffer();
        int j = arrayOfByte.length - i;
        try
        {
          int k = paramInputStream.read(arrayOfByte, i, j);
          if (k > 0)
            this._outputStream.setDataSize(i + k);
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
      return this;
    }

    public XulHttpServerResponse writeBody(String paramString)
    {
      try
      {
        this._outputStream.write(paramString.getBytes("utf-8"));
        return this;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      return this;
    }

    public XulHttpServerResponse writeBody(byte[] paramArrayOfByte)
    {
      try
      {
        this._outputStream.write(paramArrayOfByte);
        return this;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      return this;
    }

    public XulHttpServerResponse writeBody(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      try
      {
        this._outputStream.write(paramArrayOfByte, paramInt1, paramInt2);
        return this;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      return this;
    }

    public XulHttpServerResponse writeStream(InputStream paramInputStream)
    {
      this._bodyStream = paramInputStream;
      return this;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.http.XulHttpServer
 * JD-Core Version:    0.6.2
 */