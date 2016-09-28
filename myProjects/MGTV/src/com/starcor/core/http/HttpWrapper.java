package com.starcor.core.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.text.TextUtils;
import com.ei.libs.bitmap.EIBitmapUtil;
import com.starcor.core.domain.ApiTask;
import com.starcor.core.domain.BaseParams;
import com.starcor.core.epgapi.Api;
import com.starcor.core.exception.HttpResponseException;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.service.BitmapService;
import com.starcor.core.service.RoundedProcess;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

public class HttpWrapper
{
  private static final String CHEKCK_IP_URL = "http://www.ip138.com";
  private static final String TAG = "Api";
  private static HttpWrapper wrapper;
  private Context mContext;
  private BroadcastReceiver receiver;

  public HttpWrapper(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static DefaultHttpClient getHttpClient()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 3000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 8000);
    HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 16384);
    HttpClientParams.setRedirecting(localBasicHttpParams, true);
    return new DefaultHttpClient(localBasicHttpParams);
  }

  public static HttpWrapper getInstance(Context paramContext)
  {
    try
    {
      if (wrapper == null)
        wrapper = new HttpWrapper(paramContext);
      HttpWrapper localHttpWrapper = wrapper;
      return localHttpWrapper;
    }
    finally
    {
    }
  }

  private boolean isTaskHeaderNeedLogging(ApiTask paramApiTask)
  {
    return paramApiTask.getApi().getApiName().equals("N3_A_A_4");
  }

  private byte[] read(InputStream paramInputStream)
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1)
        break;
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    paramInputStream.close();
    return localByteArrayOutputStream.toByteArray();
  }

  public String encodeURL(String paramString)
  {
    int i = paramString.indexOf("?");
    if (i == -1)
      return paramString;
    Object localObject = paramString.substring(i + 1).trim();
    try
    {
      String str = URLEncoder.encode((String)localObject, "utf-8");
      localObject = str;
      return paramString.substring(0, i + 1) + (String)localObject;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  protected void finalize()
    throws Throwable
  {
    try
    {
      this.mContext.getApplicationContext().unregisterReceiver(this.receiver);
      super.finalize();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public String getCityName()
  {
    String str = "北京";
    Matcher localMatcher = Pattern.compile("省(.*)市").matcher(getHtml("http://www.ip138.com"));
    if (localMatcher.find())
      str = localMatcher.group(1);
    return str;
  }

  public String getHtml(String paramString)
  {
    HttpGet localHttpGet = new HttpGet();
    localHttpGet.setURI(URI.create(paramString));
    supportGzip(localHttpGet);
    try
    {
      String str = getResposeHtml(HttpClientManager.getHttpClient().execute(localHttpGet));
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public int getHttpStatus(String paramString)
    throws IOException, UnsupportedEncodingException
  {
    HttpGet localHttpGet = new HttpGet(paramString);
    int i = new DefaultHttpClient().execute(localHttpGet).getStatusLine().getStatusCode();
    if (i == 200)
      i = 200;
    return i;
  }

  public Bitmap getImage(String paramString)
  {
    File localFile = new File(GlobalEnv.getInstance().getPicCachePath(), GeneralUtils.getImageNameFromUrl(paramString));
    Logger.d("Api", localFile.getAbsolutePath());
    if (localFile.exists())
      return BitmapService.getInstance(this.mContext).getBitmap(localFile);
    InputStream localInputStream;
    FileOutputStream localFileOutputStream;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setConnectTimeout(10000);
      if (localHttpURLConnection.getResponseCode() != 200)
        break label166;
      localInputStream = localHttpURLConnection.getInputStream();
      localFileOutputStream = new FileOutputStream(localFile);
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localFileOutputStream.close();
    localInputStream.close();
    Logger.d("Api", "文件转存完成！");
    label166: Bitmap localBitmap = BitmapService.getInstance(this.mContext).getBitmap(localFile);
    return localBitmap;
  }

  public Bitmap getImage(String paramString, int paramInt)
  {
    File localFile = new File(GlobalEnv.getInstance().getPicCachePath(), GeneralUtils.getImageNameFromUrl(paramString));
    GeneralUtils.markTime("从网络取图");
    Bitmap localBitmap1 = null;
    InputStream localInputStream;
    FileOutputStream localFileOutputStream;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setConnectTimeout(10000);
      int i = localHttpURLConnection.getResponseCode();
      localBitmap1 = null;
      if (i == 200)
      {
        localInputStream = localHttpURLConnection.getInputStream();
        if (localInputStream != null)
          break label204;
        return null;
        localFileOutputStream = new FileOutputStream(localFile);
        byte[] arrayOfByte = new byte[1024];
        while (true)
        {
          int j = localInputStream.read(arrayOfByte);
          if (j == -1)
            break;
          localFileOutputStream.write(arrayOfByte, 0, j);
        }
      }
    }
    catch (Exception localException)
    {
      if (localFile.exists())
        localFile.delete();
      localException.printStackTrace();
    }
    return localBitmap1;
    localBitmap1 = BitmapFactory.decodeStream(localInputStream);
    if (localInputStream != null)
      localInputStream.close();
    return RoundedProcess.Process(localBitmap1, localFile);
    localFileOutputStream.close();
    localInputStream.close();
    Bitmap localBitmap2 = EIBitmapUtil.decodeFile(localFile.getAbsolutePath());
    return localBitmap2;
    label204: switch (paramInt)
    {
    default:
    case 1:
    }
  }

  public InputStream getImageInputStreamFromURL(String paramString)
  {
    HttpGet localHttpGet = new HttpGet();
    localHttpGet.setURI(URI.create(paramString.replace("?&", "?")));
    supportGzip(localHttpGet);
    HttpResponse localHttpResponse;
    try
    {
      localHttpResponse = HttpClientManager.getHttpClient().execute(localHttpGet);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      Logger.i("image", "api connect ok url:" + paramString);
      if (i != 200)
        return null;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      return null;
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
      return null;
    }
    HttpEntity localHttpEntity = localHttpResponse.getEntity();
    try
    {
      if (isSupportGzip(localHttpResponse))
        return new GZIPInputStream(localHttpEntity.getContent());
      InputStream localInputStream = localHttpEntity.getContent();
      return localInputStream;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      return null;
    }
    catch (IOException localIOException2)
    {
      localIOException2.printStackTrace();
    }
    return null;
  }

  public InputStream getInputStreamFromURL(Message paramMessage, String paramString, ApiTask paramApiTask)
  {
    HttpGet localHttpGet = new HttpGet();
    Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", getInputStreamFromUrl url:" + paramString);
    localHttpGet.setURI(URI.create(paramString.replace("?&", "?")));
    supportGzip(localHttpGet);
    DefaultHttpClient localDefaultHttpClient = getHttpClient();
    HttpResponse localHttpResponse;
    try
    {
      localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", api connect ok url:" + paramString);
      if (isTaskHeaderNeedLogging(paramApiTask))
      {
        String str = Arrays.toString(localHttpResponse.getAllHeaders());
        Logger.i("Api", "response header: " + str);
      }
      paramMessage.arg1 = i;
      if (i != 200)
        return null;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", getInputStreamFromURL clientProtocolException ");
      localClientProtocolException.printStackTrace();
      paramMessage.arg1 = 602;
      return null;
    }
    catch (Exception localException)
    {
      Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", getInputStreamFromURL IOException ");
      localException.printStackTrace();
      paramMessage.arg1 = 600;
      return null;
    }
    HttpEntity localHttpEntity = localHttpResponse.getEntity();
    try
    {
      if (isSupportGzip(localHttpResponse))
        return new GZIPInputStream(localHttpEntity.getContent());
      InputStream localInputStream = localHttpEntity.getContent();
      return localInputStream;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      paramMessage.arg1 = 603;
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      paramMessage.arg1 = 601;
    }
    return null;
  }

  public InputStream getInputStreamFromURL(String paramString)
    throws Exception
  {
    HttpGet localHttpGet = new HttpGet();
    localHttpGet.setURI(URI.create(paramString));
    supportGzip(localHttpGet);
    HttpResponse localHttpResponse = HttpClientManager.getHttpClient().execute(localHttpGet);
    if (localHttpResponse.getStatusLine().getStatusCode() == 200)
    {
      HttpEntity localHttpEntity = localHttpResponse.getEntity();
      if (isSupportGzip(localHttpResponse))
        return new GZIPInputStream(localHttpEntity.getContent());
      return localHttpEntity.getContent();
    }
    return null;
  }

  protected String getResposeHtml(HttpResponse paramHttpResponse)
    throws IOException, UnsupportedEncodingException
  {
    int i = paramHttpResponse.getStatusLine().getStatusCode();
    if (i == 200)
    {
      HttpEntity localHttpEntity = paramHttpResponse.getEntity();
      String str2;
      if (isSupportGzip(paramHttpResponse))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(localHttpEntity.getContent());
        String str1 = EntityUtils.getContentCharSet(localHttpEntity);
        if (TextUtils.isEmpty(str1))
          str1 = "UTF-8";
        InputStreamReader localInputStreamReader = new InputStreamReader(localGZIPInputStream, str1);
        CharArrayBuffer localCharArrayBuffer = new CharArrayBuffer(1024);
        try
        {
          char[] arrayOfChar = new char[1024];
          while (true)
          {
            int j = localInputStreamReader.read(arrayOfChar);
            if (j == -1)
              break;
            localCharArrayBuffer.append(arrayOfChar, 0, j);
          }
        }
        finally
        {
          localInputStreamReader.close();
        }
        localInputStreamReader.close();
        str2 = localCharArrayBuffer.toString();
        Logger.i("Api", str2);
      }
      while (true)
      {
        localHttpEntity.consumeContent();
        if (TextUtils.isEmpty(str2))
          str2 = null;
        return str2;
        str2 = EntityUtils.toString(localHttpEntity);
      }
    }
    throw new HttpResponseException(i);
  }

  public boolean isSupportGzip(HttpResponse paramHttpResponse)
  {
    Header localHeader = paramHttpResponse.getFirstHeader("Content-Encoding");
    return (localHeader != null) && (localHeader.getValue().equalsIgnoreCase("gzip"));
  }

  public int postHeartbeatPack(String paramString, BaseParams paramBaseParams)
  {
    HttpPost localHttpPost = new HttpPost();
    localHttpPost.setURI(URI.create(paramString));
    supportGzip(localHttpPost);
    try
    {
      Logger.d("Api", "发送一个心跳包：" + paramBaseParams.toString());
      localHttpPost.setEntity(paramBaseParams.getEntity());
      int i = new DefaultHttpClient().execute(localHttpPost).getStatusLine().getStatusCode();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public void supportGzip(HttpRequest paramHttpRequest)
  {
    paramHttpRequest.addHeader("Accept-Encoding", "gzip");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.http.HttpWrapper
 * JD-Core Version:    0.6.2
 */