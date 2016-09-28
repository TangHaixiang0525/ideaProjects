package com.starcor.hunan.opendownload.drm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

public abstract class NanoHTTPD
{
  public static final String MIME_HTML = "text/html";
  public static final String MIME_PLAINTEXT = "text/plain";
  private static final String QUERY_STRING_PARAMETER = "NanoHttpd.QUERY_STRING";
  public static final int SOCKET_READ_TIMEOUT = 5000;
  private AsyncRunner asyncRunner;
  private final String hostname;
  private final int myPort;
  private ServerSocket myServerSocket;
  private Thread myThread;
  private Set<Socket> openConnections = new HashSet();
  private TempFileManagerFactory tempFileManagerFactory;

  public NanoHTTPD(int paramInt)
  {
    this(null, paramInt);
  }

  public NanoHTTPD(String paramString, int paramInt)
  {
    this.hostname = paramString;
    this.myPort = paramInt;
    setTempFileManagerFactory(new DefaultTempFileManagerFactory(null));
    setAsyncRunner(new DefaultAsyncRunner());
  }

  private static void safeClose(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  private static void safeClose(ServerSocket paramServerSocket)
  {
    if (paramServerSocket != null);
    try
    {
      paramServerSocket.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  private static void safeClose(Socket paramSocket)
  {
    if (paramSocket != null);
    try
    {
      paramSocket.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public void closeAllConnections()
  {
    try
    {
      Iterator localIterator = this.openConnections.iterator();
      while (localIterator.hasNext())
        safeClose((Socket)localIterator.next());
    }
    finally
    {
    }
  }

  protected Map<String, List<String>> decodeParameters(String paramString)
  {
    HashMap localHashMap = new HashMap();
    if (paramString != null)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "&");
      label155: label159: 
      while (localStringTokenizer.hasMoreTokens())
      {
        String str1 = localStringTokenizer.nextToken();
        int i = str1.indexOf('=');
        String str2;
        if (i >= 0)
        {
          str2 = decodePercent(str1.substring(0, i)).trim();
          label67: if (!localHashMap.containsKey(str2))
            localHashMap.put(str2, new ArrayList());
          if (i < 0)
            break label155;
        }
        for (String str3 = decodePercent(str1.substring(i + 1)); ; str3 = null)
        {
          if (str3 == null)
            break label159;
          ((List)localHashMap.get(str2)).add(str3);
          break;
          str2 = decodePercent(str1).trim();
          break label67;
        }
      }
    }
    return localHashMap;
  }

  protected Map<String, List<String>> decodeParameters(Map<String, String> paramMap)
  {
    return decodeParameters((String)paramMap.get("NanoHttpd.QUERY_STRING"));
  }

  protected String decodePercent(String paramString)
  {
    try
    {
      String str = URLDecoder.decode(paramString, "UTF8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return null;
  }

  public final int getListeningPort()
  {
    if (this.myServerSocket == null)
      return -1;
    return this.myServerSocket.getLocalPort();
  }

  public final boolean isAlive()
  {
    return (wasStarted()) && (!this.myServerSocket.isClosed()) && (this.myThread.isAlive());
  }

  public void registerConnection(Socket paramSocket)
  {
    try
    {
      this.openConnections.add(paramSocket);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Response serve(IHTTPSession paramIHTTPSession)
  {
    HashMap localHashMap = new HashMap();
    Method localMethod = paramIHTTPSession.getMethod();
    if ((Method.PUT.equals(localMethod)) || (Method.POST.equals(localMethod)));
    try
    {
      paramIHTTPSession.parseBody(localHashMap);
      Map localMap = paramIHTTPSession.getParms();
      localMap.put("NanoHttpd.QUERY_STRING", paramIHTTPSession.getQueryParameterString());
      return serve(paramIHTTPSession.getUri(), localMethod, paramIHTTPSession.getHeaders(), localMap, localHashMap);
    }
    catch (IOException localIOException)
    {
      return new Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "SERVER INTERNAL ERROR: IOException: " + localIOException.getMessage());
    }
    catch (ResponseException localResponseException)
    {
      return new Response(localResponseException.getStatus(), "text/plain", localResponseException.getMessage());
    }
  }

  @Deprecated
  public Response serve(String paramString, Method paramMethod, Map<String, String> paramMap1, Map<String, String> paramMap2, Map<String, String> paramMap3)
  {
    return new Response(NanoHTTPD.Response.Status.NOT_FOUND, "text/plain", "Not Found");
  }

  public void setAsyncRunner(AsyncRunner paramAsyncRunner)
  {
    this.asyncRunner = paramAsyncRunner;
  }

  public void setTempFileManagerFactory(TempFileManagerFactory paramTempFileManagerFactory)
  {
    this.tempFileManagerFactory = paramTempFileManagerFactory;
  }

  public void start()
    throws IOException
  {
    this.myServerSocket = new ServerSocket();
    ServerSocket localServerSocket = this.myServerSocket;
    if (this.hostname != null);
    for (InetSocketAddress localInetSocketAddress = new InetSocketAddress(this.hostname, this.myPort); ; localInetSocketAddress = new InetSocketAddress(this.myPort))
    {
      localServerSocket.bind(localInetSocketAddress);
      this.myThread = new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            label53: 
            do
            {
              final Socket localSocket = NanoHTTPD.this.myServerSocket.accept();
              NanoHTTPD.this.registerConnection(localSocket);
              localSocket.setSoTimeout(5000);
              final InputStream localInputStream = localSocket.getInputStream();
              NanoHTTPD.this.asyncRunner.exec(new Runnable()
              {
                public void run()
                {
                  OutputStream localOutputStream = null;
                  try
                  {
                    localOutputStream = localSocket.getOutputStream();
                    NanoHTTPD.TempFileManager localTempFileManager = NanoHTTPD.this.tempFileManagerFactory.create();
                    NanoHTTPD.HTTPSession localHTTPSession = new NanoHTTPD.HTTPSession(NanoHTTPD.this, localTempFileManager, localInputStream, localOutputStream, localSocket.getInetAddress());
                    while (!localSocket.isClosed())
                      localHTTPSession.execute();
                  }
                  catch (Exception localException)
                  {
                    if ((!(localException instanceof SocketException)) || (!"NanoHttpd Shutdown".equals(localException.getMessage())))
                      localException.printStackTrace();
                    return;
                    return;
                  }
                  finally
                  {
                    NanoHTTPD.safeClose(localOutputStream);
                    NanoHTTPD.safeClose(localInputStream);
                    NanoHTTPD.safeClose(localSocket);
                    NanoHTTPD.this.unRegisterConnection(localSocket);
                  }
                }
              });
            }
            while (!NanoHTTPD.this.myServerSocket.isClosed());
            return;
          }
          catch (IOException localIOException)
          {
            break label53;
          }
        }
      });
      this.myThread.setDaemon(true);
      this.myThread.setName("NanoHttpd Main Listener");
      this.myThread.start();
      return;
    }
  }

  public void stop()
  {
    try
    {
      safeClose(this.myServerSocket);
      closeAllConnections();
      if (this.myThread != null)
        this.myThread.join();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void unRegisterConnection(Socket paramSocket)
  {
    try
    {
      this.openConnections.remove(paramSocket);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final boolean wasStarted()
  {
    return (this.myServerSocket != null) && (this.myThread != null);
  }

  public static abstract interface AsyncRunner
  {
    public abstract void exec(Runnable paramRunnable);
  }

  public static class Cookie
  {
    private String e;
    private String n;
    private String v;

    public Cookie(String paramString1, String paramString2)
    {
      this(paramString1, paramString2, 30);
    }

    public Cookie(String paramString1, String paramString2, int paramInt)
    {
      this.n = paramString1;
      this.v = paramString2;
      this.e = getHTTPTime(paramInt);
    }

    public Cookie(String paramString1, String paramString2, String paramString3)
    {
      this.n = paramString1;
      this.v = paramString2;
      this.e = paramString3;
    }

    public static String getHTTPTime(int paramInt)
    {
      Calendar localCalendar = Calendar.getInstance();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
      localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      localCalendar.add(5, paramInt);
      return localSimpleDateFormat.format(localCalendar.getTime());
    }

    public String getHTTPHeader()
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this.n;
      arrayOfObject[1] = this.v;
      arrayOfObject[2] = this.e;
      return String.format("%s=%s; expires=%s", arrayOfObject);
    }
  }

  public class CookieHandler
    implements Iterable<String>
  {
    private HashMap<String, String> cookies = new HashMap();
    private ArrayList<NanoHTTPD.Cookie> queue = new ArrayList();

    public CookieHandler()
    {
      Object localObject;
      String str = (String)localObject.get("cookie");
      if (str != null)
      {
        String[] arrayOfString1 = str.split(";");
        int i = arrayOfString1.length;
        for (int j = 0; j < i; j++)
        {
          String[] arrayOfString2 = arrayOfString1[j].trim().split("=");
          if (arrayOfString2.length == 2)
            this.cookies.put(arrayOfString2[0], arrayOfString2[1]);
        }
      }
    }

    public void delete(String paramString)
    {
      set(paramString, "-delete-", -30);
    }

    public Iterator<String> iterator()
    {
      return this.cookies.keySet().iterator();
    }

    public String read(String paramString)
    {
      return (String)this.cookies.get(paramString);
    }

    public void set(NanoHTTPD.Cookie paramCookie)
    {
      this.queue.add(paramCookie);
    }

    public void set(String paramString1, String paramString2, int paramInt)
    {
      this.queue.add(new NanoHTTPD.Cookie(paramString1, paramString2, NanoHTTPD.Cookie.getHTTPTime(paramInt)));
    }

    public void unloadQueue(NanoHTTPD.Response paramResponse)
    {
      Iterator localIterator = this.queue.iterator();
      while (localIterator.hasNext())
        paramResponse.addHeader("Set-Cookie", ((NanoHTTPD.Cookie)localIterator.next()).getHTTPHeader());
    }
  }

  public static class DefaultAsyncRunner
    implements NanoHTTPD.AsyncRunner
  {
    private long requestCount;

    public void exec(Runnable paramRunnable)
    {
      this.requestCount = (1L + this.requestCount);
      Thread localThread = new Thread(paramRunnable);
      localThread.setDaemon(true);
      localThread.setName("NanoHttpd Request Processor (#" + this.requestCount + ")");
      localThread.start();
    }
  }

  public static class DefaultTempFile
    implements NanoHTTPD.TempFile
  {
    private File file;
    private OutputStream fstream;

    public DefaultTempFile(String paramString)
      throws IOException
    {
      this.file = File.createTempFile("NanoHTTPD-", "", new File(paramString));
      this.fstream = new FileOutputStream(this.file);
    }

    public void delete()
      throws Exception
    {
      NanoHTTPD.safeClose(this.fstream);
      this.file.delete();
    }

    public String getName()
    {
      return this.file.getAbsolutePath();
    }

    public OutputStream open()
      throws Exception
    {
      return this.fstream;
    }
  }

  public static class DefaultTempFileManager
    implements NanoHTTPD.TempFileManager
  {
    private final List<NanoHTTPD.TempFile> tempFiles = new ArrayList();
    private final String tmpdir = System.getProperty("java.io.tmpdir");

    public void clear()
    {
      Iterator localIterator = this.tempFiles.iterator();
      while (localIterator.hasNext())
      {
        NanoHTTPD.TempFile localTempFile = (NanoHTTPD.TempFile)localIterator.next();
        try
        {
          localTempFile.delete();
        }
        catch (Exception localException)
        {
        }
      }
      this.tempFiles.clear();
    }

    public NanoHTTPD.TempFile createTempFile()
      throws Exception
    {
      NanoHTTPD.DefaultTempFile localDefaultTempFile = new NanoHTTPD.DefaultTempFile(this.tmpdir);
      this.tempFiles.add(localDefaultTempFile);
      return localDefaultTempFile;
    }
  }

  private class DefaultTempFileManagerFactory
    implements NanoHTTPD.TempFileManagerFactory
  {
    private DefaultTempFileManagerFactory()
    {
    }

    public NanoHTTPD.TempFileManager create()
    {
      return new NanoHTTPD.DefaultTempFileManager();
    }
  }

  protected class HTTPSession
    implements NanoHTTPD.IHTTPSession
  {
    public static final int BUFSIZE = 8192;
    private NanoHTTPD.CookieHandler cookies;
    private Map<String, String> headers;
    private PushbackInputStream inputStream;
    private NanoHTTPD.Method method;
    private final OutputStream outputStream;
    private Map<String, String> parms;
    private String queryParameterString;
    private int rlen;
    private int splitbyte;
    private final NanoHTTPD.TempFileManager tempFileManager;
    private String uri;

    public HTTPSession(NanoHTTPD.TempFileManager paramInputStream, InputStream paramOutputStream, OutputStream arg4)
    {
      this.tempFileManager = paramInputStream;
      this.inputStream = new PushbackInputStream(paramOutputStream, 8192);
      Object localObject;
      this.outputStream = localObject;
    }

    public HTTPSession(NanoHTTPD.TempFileManager paramInputStream, InputStream paramOutputStream, OutputStream paramInetAddress, InetAddress arg5)
    {
      this.tempFileManager = paramInputStream;
      this.inputStream = new PushbackInputStream(paramOutputStream, 8192);
      this.outputStream = paramInetAddress;
      Object localObject;
      if ((localObject.isLoopbackAddress()) || (localObject.isAnyLocalAddress()));
      for (String str = "127.0.0.1"; ; str = localObject.getHostAddress())
      {
        this.headers = new HashMap();
        this.headers.put("remote-addr", str);
        this.headers.put("http-client-ip", str);
        return;
      }
    }

    private void decodeHeader(BufferedReader paramBufferedReader, Map<String, String> paramMap1, Map<String, String> paramMap2, Map<String, String> paramMap3)
      throws NanoHTTPD.ResponseException
    {
      StringTokenizer localStringTokenizer;
      try
      {
        String str1 = paramBufferedReader.readLine();
        if (str1 == null)
          return;
        localStringTokenizer = new StringTokenizer(str1);
        if (!localStringTokenizer.hasMoreTokens())
          throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
      }
      catch (IOException localIOException)
      {
        throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + localIOException.getMessage(), localIOException);
      }
      paramMap1.put("method", localStringTokenizer.nextToken());
      if (!localStringTokenizer.hasMoreTokens())
        throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
      String str2 = localStringTokenizer.nextToken();
      int i = str2.indexOf('?');
      if (i >= 0)
        decodeParms(str2.substring(i + 1), paramMap2);
      for (String str3 = NanoHTTPD.this.decodePercent(str2.substring(0, i)); localStringTokenizer.hasMoreTokens(); str3 = NanoHTTPD.this.decodePercent(str2))
        for (String str4 = paramBufferedReader.readLine(); (str4 != null) && (str4.trim().length() > 0); str4 = paramBufferedReader.readLine())
        {
          int j = str4.indexOf(':');
          if (j >= 0)
            paramMap3.put(str4.substring(0, j).trim().toLowerCase(Locale.US), str4.substring(j + 1).trim());
        }
      paramMap1.put("uri", str3);
    }

    private void decodeMultipartData(String paramString, ByteBuffer paramByteBuffer, BufferedReader paramBufferedReader, Map<String, String> paramMap1, Map<String, String> paramMap2)
      throws NanoHTTPD.ResponseException
    {
      while (true)
      {
        int[] arrayOfInt;
        int i;
        try
        {
          arrayOfInt = getBoundaryPositions(paramByteBuffer, paramString.getBytes());
          i = 1;
          str1 = paramBufferedReader.readLine();
          if (str1 == null)
            break;
          if (!str1.contains(paramString))
            throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
        }
        catch (IOException localIOException)
        {
          NanoHTTPD.ResponseException localResponseException = new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + localIOException.getMessage(), localIOException);
          throw localResponseException;
        }
        i++;
        HashMap localHashMap1 = new HashMap();
        for (String str1 = paramBufferedReader.readLine(); (str1 != null) && (str1.trim().length() > 0); str1 = paramBufferedReader.readLine())
        {
          int n = str1.indexOf(':');
          if (n != -1)
            localHashMap1.put(str1.substring(0, n).trim().toLowerCase(Locale.US), str1.substring(n + 1).trim());
        }
        if (str1 != null)
        {
          String str2 = (String)localHashMap1.get("content-disposition");
          if (str2 == null)
            throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
          StringTokenizer localStringTokenizer = new StringTokenizer(str2, ";");
          HashMap localHashMap2 = new HashMap();
          while (localStringTokenizer.hasMoreTokens())
          {
            String str7 = localStringTokenizer.nextToken().trim();
            int m = str7.indexOf('=');
            if (m != -1)
              localHashMap2.put(str7.substring(0, m).trim().toLowerCase(Locale.US), str7.substring(m + 1).trim());
          }
          String str3 = (String)localHashMap2.get("name");
          String str4 = str3.substring(1, -1 + str3.length());
          String str5 = "";
          if (localHashMap1.get("content-type") == null)
            while ((str1 != null) && (!str1.contains(paramString)))
            {
              str1 = paramBufferedReader.readLine();
              if (str1 != null)
              {
                int j = str1.indexOf(paramString);
                if (j == -1)
                  str5 = str5 + str1;
                else
                  str5 = str5 + str1.substring(0, j - 2);
              }
            }
          if (i > arrayOfInt.length)
            throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.INTERNAL_ERROR, "Error processing request");
          int k = stripMultipartHeaders(paramByteBuffer, arrayOfInt[(i - 2)]);
          paramMap2.put(str4, saveTmpFile(paramByteBuffer, k, -4 + (arrayOfInt[(i - 1)] - k)));
          String str6 = (String)localHashMap2.get("filename");
          str5 = str6.substring(1, -1 + str6.length());
          do
            str1 = paramBufferedReader.readLine();
          while ((str1 != null) && (!str1.contains(paramString)));
          paramMap1.put(str4, str5);
        }
      }
    }

    private void decodeParms(String paramString, Map<String, String> paramMap)
    {
      if (paramString == null)
        this.queryParameterString = "";
      while (true)
      {
        return;
        this.queryParameterString = paramString;
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "&");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken();
          int i = str.indexOf('=');
          if (i >= 0)
            paramMap.put(NanoHTTPD.this.decodePercent(str.substring(0, i)).trim(), NanoHTTPD.this.decodePercent(str.substring(i + 1)));
          else
            paramMap.put(NanoHTTPD.this.decodePercent(str).trim(), "");
        }
      }
    }

    private int findHeaderEnd(byte[] paramArrayOfByte, int paramInt)
    {
      for (int i = 0; i + 3 < paramInt; i++)
        if ((paramArrayOfByte[i] == 13) && (paramArrayOfByte[(i + 1)] == 10) && (paramArrayOfByte[(i + 2)] == 13) && (paramArrayOfByte[(i + 3)] == 10))
          return i + 4;
      return 0;
    }

    private int[] getBoundaryPositions(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte)
    {
      int i = 0;
      int j = -1;
      ArrayList localArrayList = new ArrayList();
      int k = 0;
      if (k < paramByteBuffer.limit())
      {
        if (paramByteBuffer.get(k) == paramArrayOfByte[i])
        {
          if (i == 0)
            j = k;
          i++;
          if (i == paramArrayOfByte.length)
          {
            localArrayList.add(Integer.valueOf(j));
            i = 0;
            j = -1;
          }
        }
        while (true)
        {
          k++;
          break;
          k -= i;
          j = -1;
          i = 0;
        }
      }
      int[] arrayOfInt = new int[localArrayList.size()];
      for (int m = 0; m < arrayOfInt.length; m++)
        arrayOfInt[m] = ((Integer)localArrayList.get(m)).intValue();
      return arrayOfInt;
    }

    private RandomAccessFile getTmpBucket()
    {
      try
      {
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(this.tempFileManager.createTempFile().getName(), "rw");
        return localRandomAccessFile;
      }
      catch (Exception localException)
      {
        throw new Error(localException);
      }
    }

    // ERROR //
    private String saveTmpFile(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
    {
      // Byte code:
      //   0: ldc 210
      //   2: astore 4
      //   4: iload_3
      //   5: ifle +88 -> 93
      //   8: aconst_null
      //   9: astore 5
      //   11: aload_0
      //   12: getfield 39	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:tempFileManager	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$TempFileManager;
      //   15: invokeinterface 277 1 0
      //   20: astore 8
      //   22: aload_1
      //   23: invokevirtual 294	java/nio/ByteBuffer:duplicate	()Ljava/nio/ByteBuffer;
      //   26: astore 9
      //   28: new 296	java/io/FileOutputStream
      //   31: dup
      //   32: aload 8
      //   34: invokeinterface 282 1 0
      //   39: invokespecial 297	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
      //   42: astore 10
      //   44: aload 10
      //   46: invokevirtual 301	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
      //   49: astore 11
      //   51: aload 9
      //   53: iload_2
      //   54: invokevirtual 305	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
      //   57: iload_2
      //   58: iload_3
      //   59: iadd
      //   60: invokevirtual 309	java/nio/Buffer:limit	(I)Ljava/nio/Buffer;
      //   63: pop
      //   64: aload 11
      //   66: aload 9
      //   68: invokevirtual 312	java/nio/ByteBuffer:slice	()Ljava/nio/ByteBuffer;
      //   71: invokevirtual 318	java/nio/channels/FileChannel:write	(Ljava/nio/ByteBuffer;)I
      //   74: pop
      //   75: aload 8
      //   77: invokeinterface 282 1 0
      //   82: astore 14
      //   84: aload 14
      //   86: astore 4
      //   88: aload 10
      //   90: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   93: aload 4
      //   95: areturn
      //   96: astore 7
      //   98: new 287	java/lang/Error
      //   101: dup
      //   102: aload 7
      //   104: invokespecial 290	java/lang/Error:<init>	(Ljava/lang/Throwable;)V
      //   107: athrow
      //   108: astore 6
      //   110: aload 5
      //   112: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   115: aload 6
      //   117: athrow
      //   118: astore 6
      //   120: aload 10
      //   122: astore 5
      //   124: goto -14 -> 110
      //   127: astore 7
      //   129: aload 10
      //   131: astore 5
      //   133: goto -35 -> 98
      //
      // Exception table:
      //   from	to	target	type
      //   11	44	96	java/lang/Exception
      //   11	44	108	finally
      //   98	108	108	finally
      //   44	84	118	finally
      //   44	84	127	java/lang/Exception
    }

    private int stripMultipartHeaders(ByteBuffer paramByteBuffer, int paramInt)
    {
      for (int i = paramInt; ; i++)
        if (i < paramByteBuffer.limit())
        {
          if (paramByteBuffer.get(i) == 13)
          {
            i++;
            if (paramByteBuffer.get(i) == 10)
            {
              i++;
              if (paramByteBuffer.get(i) == 13)
              {
                i++;
                if (paramByteBuffer.get(i) != 10);
              }
            }
          }
        }
        else
          return i + 1;
    }

    // ERROR //
    public void execute()
      throws IOException
    {
      // Byte code:
      //   0: sipush 8192
      //   3: newarray byte
      //   5: astore 6
      //   7: aload_0
      //   8: iconst_0
      //   9: putfield 329	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:splitbyte	I
      //   12: aload_0
      //   13: iconst_0
      //   14: putfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   17: aload_0
      //   18: getfield 46	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   21: aload 6
      //   23: iconst_0
      //   24: sipush 8192
      //   27: invokevirtual 335	java/io/PushbackInputStream:read	([BII)I
      //   30: istore 8
      //   32: iload 8
      //   34: istore 9
      //   36: iload 9
      //   38: iconst_m1
      //   39: if_icmpne +100 -> 139
      //   42: aload_0
      //   43: getfield 46	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   46: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   49: aload_0
      //   50: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   53: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   56: new 325	java/net/SocketException
      //   59: dup
      //   60: ldc_w 337
      //   63: invokespecial 338	java/net/SocketException:<init>	(Ljava/lang/String;)V
      //   66: athrow
      //   67: astore 5
      //   69: aload 5
      //   71: athrow
      //   72: astore_2
      //   73: aload_0
      //   74: getfield 39	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:tempFileManager	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$TempFileManager;
      //   77: invokeinterface 341 1 0
      //   82: aload_2
      //   83: athrow
      //   84: astore 7
      //   86: aload_0
      //   87: getfield 46	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   90: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   93: aload_0
      //   94: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   97: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   100: new 325	java/net/SocketException
      //   103: dup
      //   104: ldc_w 337
      //   107: invokespecial 338	java/net/SocketException:<init>	(Ljava/lang/String;)V
      //   110: athrow
      //   111: astore 4
      //   113: aload 4
      //   115: athrow
      //   116: aload_0
      //   117: getfield 46	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   120: aload 6
      //   122: aload_0
      //   123: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   126: sipush 8192
      //   129: aload_0
      //   130: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   133: isub
      //   134: invokevirtual 335	java/io/PushbackInputStream:read	([BII)I
      //   137: istore 9
      //   139: iload 9
      //   141: ifle +35 -> 176
      //   144: aload_0
      //   145: iload 9
      //   147: aload_0
      //   148: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   151: iadd
      //   152: putfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   155: aload_0
      //   156: aload_0
      //   157: aload 6
      //   159: aload_0
      //   160: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   163: invokespecial 343	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:findHeaderEnd	([BI)I
      //   166: putfield 329	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:splitbyte	I
      //   169: aload_0
      //   170: getfield 329	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:splitbyte	I
      //   173: ifle -57 -> 116
      //   176: aload_0
      //   177: getfield 329	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:splitbyte	I
      //   180: aload_0
      //   181: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   184: if_icmpge +25 -> 209
      //   187: aload_0
      //   188: getfield 46	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:inputStream	Ljava/io/PushbackInputStream;
      //   191: aload 6
      //   193: aload_0
      //   194: getfield 329	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:splitbyte	I
      //   197: aload_0
      //   198: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   201: aload_0
      //   202: getfield 329	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:splitbyte	I
      //   205: isub
      //   206: invokevirtual 347	java/io/PushbackInputStream:unread	([BII)V
      //   209: aload_0
      //   210: new 62	java/util/HashMap
      //   213: dup
      //   214: invokespecial 63	java/util/HashMap:<init>	()V
      //   217: putfield 349	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:parms	Ljava/util/Map;
      //   220: aload_0
      //   221: getfield 65	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   224: ifnonnull +14 -> 238
      //   227: aload_0
      //   228: new 62	java/util/HashMap
      //   231: dup
      //   232: invokespecial 63	java/util/HashMap:<init>	()V
      //   235: putfield 65	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   238: new 87	java/io/BufferedReader
      //   241: dup
      //   242: new 351	java/io/InputStreamReader
      //   245: dup
      //   246: new 353	java/io/ByteArrayInputStream
      //   249: dup
      //   250: aload 6
      //   252: iconst_0
      //   253: aload_0
      //   254: getfield 331	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:rlen	I
      //   257: invokespecial 355	java/io/ByteArrayInputStream:<init>	([BII)V
      //   260: invokespecial 358	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
      //   263: invokespecial 361	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   266: astore 10
      //   268: new 62	java/util/HashMap
      //   271: dup
      //   272: invokespecial 63	java/util/HashMap:<init>	()V
      //   275: astore 11
      //   277: aload_0
      //   278: aload 10
      //   280: aload 11
      //   282: aload_0
      //   283: getfield 349	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:parms	Ljava/util/Map;
      //   286: aload_0
      //   287: getfield 65	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   290: invokespecial 363	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:decodeHeader	(Ljava/io/BufferedReader;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V
      //   293: aload_0
      //   294: aload 11
      //   296: ldc 131
      //   298: invokeinterface 199 2 0
      //   303: checkcast 138	java/lang/String
      //   306: invokestatic 369	com/starcor/hunan/opendownload/drm/NanoHTTPD$Method:lookup	(Ljava/lang/String;)Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Method;
      //   309: putfield 371	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:method	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Method;
      //   312: aload_0
      //   313: getfield 371	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:method	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Method;
      //   316: ifnonnull +77 -> 393
      //   319: new 83	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   322: dup
      //   323: getstatic 104	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status:BAD_REQUEST	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status;
      //   326: ldc_w 373
      //   329: invokespecial 109	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException:<init>	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status;Ljava/lang/String;)V
      //   332: athrow
      //   333: astore_3
      //   334: new 375	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response
      //   337: dup
      //   338: getstatic 112	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status:INTERNAL_ERROR	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status;
      //   341: ldc_w 377
      //   344: new 114	java/lang/StringBuilder
      //   347: dup
      //   348: invokespecial 115	java/lang/StringBuilder:<init>	()V
      //   351: ldc 117
      //   353: invokevirtual 121	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   356: aload_3
      //   357: invokevirtual 124	java/io/IOException:getMessage	()Ljava/lang/String;
      //   360: invokevirtual 121	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   363: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   366: invokespecial 380	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response:<init>	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$IStatus;Ljava/lang/String;Ljava/lang/String;)V
      //   369: aload_0
      //   370: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   373: invokevirtual 384	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response:send	(Ljava/io/OutputStream;)V
      //   376: aload_0
      //   377: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   380: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   383: aload_0
      //   384: getfield 39	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:tempFileManager	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$TempFileManager;
      //   387: invokeinterface 341 1 0
      //   392: return
      //   393: aload_0
      //   394: aload 11
      //   396: ldc 177
      //   398: invokeinterface 199 2 0
      //   403: checkcast 138	java/lang/String
      //   406: putfield 386	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:uri	Ljava/lang/String;
      //   409: aload_0
      //   410: new 388	com/starcor/hunan/opendownload/drm/NanoHTTPD$CookieHandler
      //   413: dup
      //   414: aload_0
      //   415: getfield 34	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:this$0	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD;
      //   418: aload_0
      //   419: getfield 65	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:headers	Ljava/util/Map;
      //   422: invokespecial 391	com/starcor/hunan/opendownload/drm/NanoHTTPD$CookieHandler:<init>	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD;Ljava/util/Map;)V
      //   425: putfield 393	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:cookies	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$CookieHandler;
      //   428: aload_0
      //   429: getfield 34	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:this$0	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD;
      //   432: aload_0
      //   433: invokevirtual 397	com/starcor/hunan/opendownload/drm/NanoHTTPD:serve	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$IHTTPSession;)Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response;
      //   436: astore 12
      //   438: aload 12
      //   440: ifnonnull +60 -> 500
      //   443: new 83	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   446: dup
      //   447: getstatic 112	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status:INTERNAL_ERROR	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status;
      //   450: ldc_w 399
      //   453: invokespecial 109	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException:<init>	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status;Ljava/lang/String;)V
      //   456: athrow
      //   457: astore_1
      //   458: new 375	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response
      //   461: dup
      //   462: aload_1
      //   463: invokevirtual 403	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException:getStatus	()Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$Status;
      //   466: ldc_w 377
      //   469: aload_1
      //   470: invokevirtual 404	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException:getMessage	()Ljava/lang/String;
      //   473: invokespecial 380	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response:<init>	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response$IStatus;Ljava/lang/String;Ljava/lang/String;)V
      //   476: aload_0
      //   477: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   480: invokevirtual 384	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response:send	(Ljava/io/OutputStream;)V
      //   483: aload_0
      //   484: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   487: invokestatic 322	com/starcor/hunan/opendownload/drm/NanoHTTPD:access$300	(Ljava/io/Closeable;)V
      //   490: aload_0
      //   491: getfield 39	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:tempFileManager	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$TempFileManager;
      //   494: invokeinterface 341 1 0
      //   499: return
      //   500: aload_0
      //   501: getfield 393	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:cookies	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$CookieHandler;
      //   504: aload 12
      //   506: invokevirtual 408	com/starcor/hunan/opendownload/drm/NanoHTTPD$CookieHandler:unloadQueue	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Response;)V
      //   509: aload 12
      //   511: aload_0
      //   512: getfield 371	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:method	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Method;
      //   515: invokevirtual 412	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response:setRequestMethod	(Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$Method;)V
      //   518: aload 12
      //   520: aload_0
      //   521: getfield 48	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:outputStream	Ljava/io/OutputStream;
      //   524: invokevirtual 384	com/starcor/hunan/opendownload/drm/NanoHTTPD$Response:send	(Ljava/io/OutputStream;)V
      //   527: aload_0
      //   528: getfield 39	com/starcor/hunan/opendownload/drm/NanoHTTPD$HTTPSession:tempFileManager	Lcom/starcor/hunan/opendownload/drm/NanoHTTPD$TempFileManager;
      //   531: invokeinterface 341 1 0
      //   536: return
      //
      // Exception table:
      //   from	to	target	type
      //   0	17	67	java/net/SocketException
      //   17	32	67	java/net/SocketException
      //   42	67	67	java/net/SocketException
      //   86	111	67	java/net/SocketException
      //   116	139	67	java/net/SocketException
      //   144	176	67	java/net/SocketException
      //   176	209	67	java/net/SocketException
      //   209	238	67	java/net/SocketException
      //   238	333	67	java/net/SocketException
      //   393	438	67	java/net/SocketException
      //   443	457	67	java/net/SocketException
      //   500	527	67	java/net/SocketException
      //   0	17	72	finally
      //   17	32	72	finally
      //   42	67	72	finally
      //   69	72	72	finally
      //   86	111	72	finally
      //   113	116	72	finally
      //   116	139	72	finally
      //   144	176	72	finally
      //   176	209	72	finally
      //   209	238	72	finally
      //   238	333	72	finally
      //   334	383	72	finally
      //   393	438	72	finally
      //   443	457	72	finally
      //   458	490	72	finally
      //   500	527	72	finally
      //   17	32	84	java/lang/Exception
      //   0	17	111	java/net/SocketTimeoutException
      //   17	32	111	java/net/SocketTimeoutException
      //   42	67	111	java/net/SocketTimeoutException
      //   86	111	111	java/net/SocketTimeoutException
      //   116	139	111	java/net/SocketTimeoutException
      //   144	176	111	java/net/SocketTimeoutException
      //   176	209	111	java/net/SocketTimeoutException
      //   209	238	111	java/net/SocketTimeoutException
      //   238	333	111	java/net/SocketTimeoutException
      //   393	438	111	java/net/SocketTimeoutException
      //   443	457	111	java/net/SocketTimeoutException
      //   500	527	111	java/net/SocketTimeoutException
      //   0	17	333	java/io/IOException
      //   17	32	333	java/io/IOException
      //   42	67	333	java/io/IOException
      //   86	111	333	java/io/IOException
      //   116	139	333	java/io/IOException
      //   144	176	333	java/io/IOException
      //   176	209	333	java/io/IOException
      //   209	238	333	java/io/IOException
      //   238	333	333	java/io/IOException
      //   393	438	333	java/io/IOException
      //   443	457	333	java/io/IOException
      //   500	527	333	java/io/IOException
      //   0	17	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   17	32	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   42	67	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   86	111	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   116	139	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   144	176	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   176	209	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   209	238	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   238	333	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   393	438	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   443	457	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
      //   500	527	457	com/starcor/hunan/opendownload/drm/NanoHTTPD$ResponseException
    }

    public NanoHTTPD.CookieHandler getCookies()
    {
      return this.cookies;
    }

    public final Map<String, String> getHeaders()
    {
      return this.headers;
    }

    public final InputStream getInputStream()
    {
      return this.inputStream;
    }

    public final NanoHTTPD.Method getMethod()
    {
      return this.method;
    }

    public final Map<String, String> getParms()
    {
      return this.parms;
    }

    public String getQueryParameterString()
    {
      return this.queryParameterString;
    }

    public final String getUri()
    {
      return this.uri;
    }

    public void parseBody(Map<String, String> paramMap)
      throws IOException, NanoHTTPD.ResponseException
    {
      RandomAccessFile localRandomAccessFile = null;
      long l;
      Object localObject3;
      try
      {
        localRandomAccessFile = getTmpBucket();
        if (this.headers.containsKey("content-length"))
        {
          l = Integer.parseInt((String)this.headers.get("content-length"));
          byte[] arrayOfByte = new byte[512];
          while ((this.rlen >= 0) && (l > 0L))
          {
            this.rlen = this.inputStream.read(arrayOfByte, 0, (int)Math.min(l, 512L));
            l -= this.rlen;
            if (this.rlen > 0)
            {
              int i = this.rlen;
              localRandomAccessFile.write(arrayOfByte, 0, i);
            }
          }
        }
      }
      finally
      {
        localObject3 = null;
      }
      while (true)
        while (true)
        {
          NanoHTTPD.safeClose(localRandomAccessFile);
          NanoHTTPD.safeClose((Closeable)localObject3);
          throw localObject1;
          MappedByteBuffer localMappedByteBuffer;
          if (this.splitbyte < this.rlen)
          {
            l = this.rlen - this.splitbyte;
            break;
            localMappedByteBuffer = localRandomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, localRandomAccessFile.length());
            localRandomAccessFile.seek(0L);
            localObject3 = new BufferedReader(new InputStreamReader(new FileInputStream(localRandomAccessFile.getFD())));
          }
          try
          {
            String str1;
            if (NanoHTTPD.Method.POST.equals(this.method))
            {
              str1 = "";
              String str2 = (String)this.headers.get("content-type");
              StringTokenizer localStringTokenizer = null;
              if (str2 != null)
              {
                localStringTokenizer = new StringTokenizer(str2, ",; ");
                if (localStringTokenizer.hasMoreTokens())
                  str1 = localStringTokenizer.nextToken();
              }
              if ("multipart/form-data".equalsIgnoreCase(str1))
              {
                if (!localStringTokenizer.hasMoreTokens())
                  throw new NanoHTTPD.ResponseException(NanoHTTPD.Response.Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                String str5 = str2.substring(str2.indexOf("boundary=") + "boundary=".length(), str2.length());
                if ((str5.startsWith("\"")) && (str5.endsWith("\"")))
                  str5 = str5.substring(1, -1 + str5.length());
                decodeMultipartData(str5, localMappedByteBuffer, (BufferedReader)localObject3, this.parms, paramMap);
              }
            }
            while (true)
            {
              NanoHTTPD.safeClose(localRandomAccessFile);
              NanoHTTPD.safeClose((Closeable)localObject3);
              return;
              String str3 = "";
              StringBuilder localStringBuilder = new StringBuilder();
              char[] arrayOfChar = new char[512];
              for (int j = ((BufferedReader)localObject3).read(arrayOfChar); (j >= 0) && (!str3.endsWith("\r\n")); j = ((BufferedReader)localObject3).read(arrayOfChar))
              {
                str3 = String.valueOf(arrayOfChar, 0, j);
                localStringBuilder.append(str3);
              }
              String str4 = localStringBuilder.toString().trim();
              if ("application/x-www-form-urlencoded".equalsIgnoreCase(str1))
              {
                decodeParms(str4, this.parms);
              }
              else if (str4.length() != 0)
              {
                paramMap.put("postData", str4);
                continue;
                if (NanoHTTPD.Method.PUT.equals(this.method))
                  paramMap.put("content", saveTmpFile(localMappedByteBuffer, 0, localMappedByteBuffer.limit()));
              }
            }
            l = 0L;
            break;
          }
          finally
          {
          }
        }
    }
  }

  public static abstract interface IHTTPSession
  {
    public abstract void execute()
      throws IOException;

    public abstract NanoHTTPD.CookieHandler getCookies();

    public abstract Map<String, String> getHeaders();

    public abstract InputStream getInputStream();

    public abstract NanoHTTPD.Method getMethod();

    public abstract Map<String, String> getParms();

    public abstract String getQueryParameterString();

    public abstract String getUri();

    public abstract void parseBody(Map<String, String> paramMap)
      throws IOException, NanoHTTPD.ResponseException;
  }

  public static enum Method
  {
    static
    {
      POST = new Method("POST", 2);
      DELETE = new Method("DELETE", 3);
      HEAD = new Method("HEAD", 4);
      OPTIONS = new Method("OPTIONS", 5);
      Method[] arrayOfMethod = new Method[6];
      arrayOfMethod[0] = GET;
      arrayOfMethod[1] = PUT;
      arrayOfMethod[2] = POST;
      arrayOfMethod[3] = DELETE;
      arrayOfMethod[4] = HEAD;
      arrayOfMethod[5] = OPTIONS;
    }

    static Method lookup(String paramString)
    {
      for (Method localMethod : values())
        if (localMethod.toString().equalsIgnoreCase(paramString))
          return localMethod;
      return null;
    }
  }

  public static class Response
  {
    private boolean chunkedTransfer;
    private InputStream data;
    private Map<String, String> header = new HashMap();
    private String mimeType;
    private NanoHTTPD.Method requestMethod;
    private IStatus status;

    public Response(IStatus paramIStatus, String paramString, InputStream paramInputStream)
    {
      this.status = paramIStatus;
      this.mimeType = paramString;
      this.data = paramInputStream;
    }

    public Response(IStatus paramIStatus, String paramString1, String paramString2)
    {
      this.status = paramIStatus;
      this.mimeType = paramString1;
      if (paramString2 != null);
      try
      {
        for (ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramString2.getBytes("UTF-8")); ; localByteArrayInputStream = null)
        {
          this.data = localByteArrayInputStream;
          return;
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
    }

    public Response(String paramString)
    {
      this(Status.OK, "text/html", paramString);
    }

    private boolean headerAlreadySent(Map<String, String> paramMap, String paramString)
    {
      boolean bool = false;
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
        bool |= ((String)localIterator.next()).equalsIgnoreCase(paramString);
      return bool;
    }

    private void sendAsChunked(OutputStream paramOutputStream, PrintWriter paramPrintWriter)
      throws IOException
    {
      paramPrintWriter.print("Transfer-Encoding: chunked\r\n");
      paramPrintWriter.print("\r\n");
      paramPrintWriter.flush();
      byte[] arrayOfByte1 = "\r\n".getBytes();
      byte[] arrayOfByte2 = new byte[16384];
      while (true)
      {
        int i = this.data.read(arrayOfByte2);
        if (i <= 0)
          break;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        paramOutputStream.write(String.format("%x\r\n", arrayOfObject).getBytes());
        paramOutputStream.write(arrayOfByte2, 0, i);
        paramOutputStream.write(arrayOfByte1);
      }
      paramOutputStream.write(String.format("0\r\n\r\n", new Object[0]).getBytes());
    }

    private void sendAsFixedLength(OutputStream paramOutputStream, int paramInt)
      throws IOException
    {
      byte[] arrayOfByte;
      if ((this.requestMethod != NanoHTTPD.Method.HEAD) && (this.data != null))
        arrayOfByte = new byte[16384];
      while (true)
      {
        InputStream localInputStream;
        if (paramInt > 0)
        {
          localInputStream = this.data;
          if (paramInt <= 16384)
            break label62;
        }
        int j;
        label62: for (int i = 16384; ; i = paramInt)
        {
          j = localInputStream.read(arrayOfByte, 0, i);
          if (j > 0)
            break;
          return;
        }
        paramOutputStream.write(arrayOfByte, 0, j);
        paramInt -= j;
      }
    }

    public void addHeader(String paramString1, String paramString2)
    {
      this.header.put(paramString1, paramString2);
    }

    public InputStream getData()
    {
      return this.data;
    }

    public String getHeader(String paramString)
    {
      return (String)this.header.get(paramString);
    }

    public String getMimeType()
    {
      return this.mimeType;
    }

    public NanoHTTPD.Method getRequestMethod()
    {
      return this.requestMethod;
    }

    public IStatus getStatus()
    {
      return this.status;
    }

    protected void send(OutputStream paramOutputStream)
    {
      String str1 = this.mimeType;
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      try
      {
        if (this.status == null)
          throw new Error("sendResponse(): Status can't be null.");
        PrintWriter localPrintWriter = new PrintWriter(paramOutputStream);
        localPrintWriter.print("HTTP/1.1 " + this.status.getDescription() + " \r\n");
        if (str1 != null)
          localPrintWriter.print("Content-Type: " + str1 + "\r\n");
        if ((this.header == null) || (this.header.get("Date") == null))
          localPrintWriter.print("Date: " + localSimpleDateFormat.format(new Date()) + "\r\n");
        if (this.header != null)
        {
          Iterator localIterator = this.header.keySet().iterator();
          while (localIterator.hasNext())
          {
            String str2 = (String)localIterator.next();
            String str3 = (String)this.header.get(str2);
            localPrintWriter.print(str2 + ": " + str3 + "\r\n");
          }
        }
        sendConnectionHeaderIfNotAlreadyPresent(localPrintWriter, this.header);
        if ((this.requestMethod != NanoHTTPD.Method.HEAD) && (this.chunkedTransfer))
        {
          sendAsChunked(paramOutputStream, localPrintWriter);
          paramOutputStream.flush();
          NanoHTTPD.safeClose(this.data);
          return;
        }
        if (this.data != null);
        for (int i = this.data.available(); ; i = 0)
        {
          sendContentLengthHeaderIfNotAlreadyPresent(localPrintWriter, this.header, i);
          localPrintWriter.print("\r\n");
          localPrintWriter.flush();
          sendAsFixedLength(paramOutputStream, i);
          break;
        }
      }
      catch (IOException localIOException)
      {
      }
    }

    protected void sendConnectionHeaderIfNotAlreadyPresent(PrintWriter paramPrintWriter, Map<String, String> paramMap)
    {
      if (!headerAlreadySent(paramMap, "connection"))
        paramPrintWriter.print("Connection: keep-alive\r\n");
    }

    protected void sendContentLengthHeaderIfNotAlreadyPresent(PrintWriter paramPrintWriter, Map<String, String> paramMap, int paramInt)
    {
      if (!headerAlreadySent(paramMap, "content-length"))
        paramPrintWriter.print("Content-Length: " + paramInt + "\r\n");
    }

    public void setChunkedTransfer(boolean paramBoolean)
    {
      this.chunkedTransfer = paramBoolean;
    }

    public void setData(InputStream paramInputStream)
    {
      this.data = paramInputStream;
    }

    public void setMimeType(String paramString)
    {
      this.mimeType = paramString;
    }

    public void setRequestMethod(NanoHTTPD.Method paramMethod)
    {
      this.requestMethod = paramMethod;
    }

    public void setStatus(Status paramStatus)
    {
      this.status = paramStatus;
    }

    public static abstract interface IStatus
    {
      public abstract String getDescription();

      public abstract int getRequestStatus();
    }

    public static enum Status
      implements NanoHTTPD.Response.IStatus
    {
      private final String description;
      private final int requestStatus;

      static
      {
        OK = new Status("OK", 1, 200, "OK");
        CREATED = new Status("CREATED", 2, 201, "Created");
        ACCEPTED = new Status("ACCEPTED", 3, 202, "Accepted");
        NO_CONTENT = new Status("NO_CONTENT", 4, 204, "No Content");
        PARTIAL_CONTENT = new Status("PARTIAL_CONTENT", 5, 206, "Partial Content");
        REDIRECT = new Status("REDIRECT", 6, 301, "Moved Permanently");
        NOT_MODIFIED = new Status("NOT_MODIFIED", 7, 304, "Not Modified");
        BAD_REQUEST = new Status("BAD_REQUEST", 8, 400, "Bad Request");
        UNAUTHORIZED = new Status("UNAUTHORIZED", 9, 401, "Unauthorized");
        FORBIDDEN = new Status("FORBIDDEN", 10, 403, "Forbidden");
        NOT_FOUND = new Status("NOT_FOUND", 11, 404, "Not Found");
        METHOD_NOT_ALLOWED = new Status("METHOD_NOT_ALLOWED", 12, 405, "Method Not Allowed");
        RANGE_NOT_SATISFIABLE = new Status("RANGE_NOT_SATISFIABLE", 13, 416, "Requested Range Not Satisfiable");
        INTERNAL_ERROR = new Status("INTERNAL_ERROR", 14, 500, "Internal Server Error");
        Status[] arrayOfStatus = new Status[15];
        arrayOfStatus[0] = SWITCH_PROTOCOL;
        arrayOfStatus[1] = OK;
        arrayOfStatus[2] = CREATED;
        arrayOfStatus[3] = ACCEPTED;
        arrayOfStatus[4] = NO_CONTENT;
        arrayOfStatus[5] = PARTIAL_CONTENT;
        arrayOfStatus[6] = REDIRECT;
        arrayOfStatus[7] = NOT_MODIFIED;
        arrayOfStatus[8] = BAD_REQUEST;
        arrayOfStatus[9] = UNAUTHORIZED;
        arrayOfStatus[10] = FORBIDDEN;
        arrayOfStatus[11] = NOT_FOUND;
        arrayOfStatus[12] = METHOD_NOT_ALLOWED;
        arrayOfStatus[13] = RANGE_NOT_SATISFIABLE;
        arrayOfStatus[14] = INTERNAL_ERROR;
      }

      private Status(int paramInt, String paramString)
      {
        this.requestStatus = paramInt;
        this.description = paramString;
      }

      public String getDescription()
      {
        return "" + this.requestStatus + " " + this.description;
      }

      public int getRequestStatus()
      {
        return this.requestStatus;
      }
    }
  }

  public static final class ResponseException extends Exception
  {
    private final NanoHTTPD.Response.Status status;

    public ResponseException(NanoHTTPD.Response.Status paramStatus, String paramString)
    {
      super();
      this.status = paramStatus;
    }

    public ResponseException(NanoHTTPD.Response.Status paramStatus, String paramString, Exception paramException)
    {
      super(paramException);
      this.status = paramStatus;
    }

    public NanoHTTPD.Response.Status getStatus()
    {
      return this.status;
    }
  }

  public static abstract interface TempFile
  {
    public abstract void delete()
      throws Exception;

    public abstract String getName();

    public abstract OutputStream open()
      throws Exception;
  }

  public static abstract interface TempFileManager
  {
    public abstract void clear();

    public abstract NanoHTTPD.TempFile createTempFile()
      throws Exception;
  }

  public static abstract interface TempFileManagerFactory
  {
    public abstract NanoHTTPD.TempFileManager create();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.drm.NanoHTTPD
 * JD-Core Version:    0.6.2
 */