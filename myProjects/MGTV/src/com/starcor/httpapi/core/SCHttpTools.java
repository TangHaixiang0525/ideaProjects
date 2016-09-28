package com.starcor.httpapi.core;

import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.encrypt.EncryptLogic;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class SCHttpTools
{
  private static final String TAG = "SCHttpApiEngine";

  private static HttpUriRequest createHttpRequest(SCHttpApiTask paramSCHttpApiTask, String paramString)
  {
    switch (paramSCHttpApiTask.getRequestMethod())
    {
    default:
      return null;
    case 0:
      return new HttpGet(paramString);
    case 3:
      return new HttpDelete(paramString);
    case 1:
      return new HttpPost(paramString);
    case 2:
    }
    return new HttpPut(paramString);
  }

  public static SCResponse doRequest(SCHttpApiTask paramSCHttpApiTask, String paramString, int paramInt1, int paramInt2)
  {
    SCResponse localSCResponse = new SCResponse();
    localSCResponse.setTaskName(paramSCHttpApiTask.getTaskName());
    HttpUriRequest localHttpUriRequest = createHttpRequest(paramSCHttpApiTask, paramString);
    if (localHttpUriRequest == null)
    {
      localSCResponse.setRunState(2);
      localSCResponse.setRunReason("httpUriRequest == null");
      return localSCResponse;
    }
    Logger.i("Req url:" + paramString);
    HttpEntity localHttpEntity;
    List localList2;
    if (paramSCHttpApiTask.getRequestMethod() == 1)
    {
      localHttpEntity = paramSCHttpApiTask.getPostBody();
      localList2 = paramSCHttpApiTask.getPostForm();
      if (localHttpEntity == null)
        break label203;
    }
    while (true)
    {
      try
      {
        Logger.i("Post Body:" + EntityUtils.toString(localHttpEntity, "utf-8"));
        ((HttpPost)localHttpUriRequest).setEntity(localHttpEntity);
        List localList1 = paramSCHttpApiTask.getReqHeaders();
        if ((localList1 == null) || (localList1.size() <= 0))
          break;
        int j = 0;
        if (j >= localList1.size())
          break;
        localHttpUriRequest.addHeader((Header)localList1.get(j));
        j++;
        continue;
      }
      catch (IOException localIOException3)
      {
        localIOException3.printStackTrace();
        continue;
      }
      label203: if (localList2 != null)
        try
        {
          UrlEncodedFormEntity localUrlEncodedFormEntity = new UrlEncodedFormEntity(localList2, paramSCHttpApiTask.getPostEncoding());
          ((HttpPost)localHttpUriRequest).setEntity(localUrlEncodedFormEntity);
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
        }
    }
    localHttpUriRequest.addHeader(ServerApiTools.buildHttpHeader("Accept-Encoding", "gzip"));
    HttpParams localHttpParams = localHttpUriRequest.getParams();
    HttpConnectionParams.setConnectionTimeout(localHttpParams, paramInt1);
    HttpConnectionParams.setSoTimeout(localHttpParams, paramInt2);
    DefaultHttpClient localDefaultHttpClient = getHttpClient(paramSCHttpApiTask, paramInt1, paramInt2);
    paramSCHttpApiTask.markConnectStartTime();
    HttpResponse localHttpResponse;
    try
    {
      localHttpResponse = localDefaultHttpClient.execute(localHttpUriRequest);
      paramSCHttpApiTask.markConnectOkTime();
      if (localHttpResponse == null)
      {
        localSCResponse.setRunState(3);
        localSCResponse.setRunReason("httpResponse == null");
        return localSCResponse;
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      localSCResponse.setRunState(4);
      localSCResponse.setRunReason(localClientProtocolException.toString());
      return localSCResponse;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      localSCResponse.setRunState(3);
      localSCResponse.setRunReason(localException1.toString());
      return localSCResponse;
    }
    StatusLine localStatusLine = localHttpResponse.getStatusLine();
    if (localStatusLine == null)
    {
      localSCResponse.setRunState(4);
      localSCResponse.setRunReason("statusLine == null");
      return localSCResponse;
    }
    paramSCHttpApiTask.markHeadOkTime();
    localSCResponse.setHttpCode(localStatusLine.getStatusCode());
    localSCResponse.setHttpReason(localStatusLine.getReasonPhrase());
    localSCResponse.setHeaders(localHttpResponse.getAllHeaders());
    try
    {
      Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
      Object localObject;
      if ((localHeader != null) && (localHeader.getValue().toLowerCase().indexOf("gzip") >= 0))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(localHttpResponse.getEntity().getContent());
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte1 = new byte[1024];
        try
        {
          while (true)
          {
            int i = localGZIPInputStream.read(arrayOfByte1);
            if (i <= 0)
              break;
            localByteArrayOutputStream.write(arrayOfByte1, 0, i);
          }
        }
        catch (Exception localException2)
        {
          Logger.w("SCHttpApiEngine", "decode gzip stream error!!");
          localException2.printStackTrace();
          localObject = localByteArrayOutputStream.toByteArray();
        }
      }
      while (true)
      {
        boolean bool = AppFuncCfg.FUNCTION_USE_URL_ENCRYPT;
        if (bool);
        try
        {
          byte[] arrayOfByte2 = EncryptLogic.decode((byte[])localObject, localHttpUriRequest.getURI().toString(), paramSCHttpApiTask.getTaskName());
          localObject = arrayOfByte2;
          Logger.i("SCHttpApiEngine", "getEntity size:" + localObject.length);
          localSCResponse.setContents((byte[])localObject);
          paramSCHttpApiTask.markBodyOkTime();
          localSCResponse.setRunState(1);
          localSCResponse.setRunReason("OK");
          return localSCResponse;
          localObject = EntityUtils.toByteArray(localHttpResponse.getEntity());
        }
        catch (Exception localException3)
        {
          while (true)
          {
            StringBuilder localStringBuilder = new StringBuilder().append("http status: ").append(localSCResponse.getHttpCode()).append(", xx = ");
            String str = new String((byte[])localObject);
            Logger.e("SCHttpApiEngine", str);
            localException3.printStackTrace();
          }
        }
      }
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
      localSCResponse.setRunState(4);
      localSCResponse.setRunReason(localIOException1.toString());
    }
    return localSCResponse;
  }

  private static DefaultHttpClient getHttpClient(SCHttpApiTask paramSCHttpApiTask, int paramInt1, int paramInt2)
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, paramInt1);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, paramInt2);
    HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 16384);
    HttpClientParams.setRedirecting(localBasicHttpParams, paramSCHttpApiTask.isAutoRedirect());
    return new DefaultHttpClient(localBasicHttpParams);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.httpapi.core.SCHttpTools
 * JD-Core Version:    0.6.2
 */