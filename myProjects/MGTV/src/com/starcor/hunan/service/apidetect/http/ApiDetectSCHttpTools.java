package com.starcor.hunan.service.apidetect.http;

import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.hunan.opendownload.encrypt.EncryptLogic;
import com.starcor.server.api.manage.ServerApiTask;
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

public class ApiDetectSCHttpTools
{
  private static final String TAG = "DetectSCHttpApiEngine";

  private static HttpUriRequest createHttpRequest(ServerApiTask paramServerApiTask)
  {
    switch (paramServerApiTask.getRequestMethod())
    {
    default:
      return null;
    case 0:
      return new HttpGet(paramServerApiTask.getUrl());
    case 3:
      return new HttpDelete(paramServerApiTask.getUrl());
    case 1:
      return new HttpPost(paramServerApiTask.getUrl());
    case 2:
    }
    return new HttpPut(paramServerApiTask.getUrl());
  }

  public static SCResponse doRequest(ServerApiTask paramServerApiTask, int paramInt1, int paramInt2)
  {
    SCResponse localSCResponse = new SCResponse();
    localSCResponse.setTaskName(paramServerApiTask.getTaskName());
    HttpUriRequest localHttpUriRequest = createHttpRequest(paramServerApiTask);
    if (localHttpUriRequest == null)
    {
      localSCResponse.setRunState(2);
      localSCResponse.setRunReason("httpUriRequest == null");
      return localSCResponse;
    }
    Logger.e("Req url:" + paramServerApiTask.getUrl());
    HttpEntity localHttpEntity;
    List localList2;
    if (paramServerApiTask.getRequestMethod() == 1)
    {
      localHttpEntity = paramServerApiTask.getPostBody();
      localList2 = paramServerApiTask.getPostForm();
      if (localHttpEntity == null)
        break label198;
    }
    while (true)
    {
      try
      {
        Logger.i("Post Body:" + EntityUtils.toString(localHttpEntity));
        ((HttpPost)localHttpUriRequest).setEntity(localHttpEntity);
        List localList1 = paramServerApiTask.getReqHeaders();
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
      label198: if (localList2 != null)
        try
        {
          UrlEncodedFormEntity localUrlEncodedFormEntity = new UrlEncodedFormEntity(localList2, paramServerApiTask.getPostEncoding());
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
    DefaultHttpClient localDefaultHttpClient = getHttpClient(paramServerApiTask, paramInt1, paramInt2);
    paramServerApiTask.markConnectStartTime();
    HttpResponse localHttpResponse;
    try
    {
      localHttpResponse = localDefaultHttpClient.execute(localHttpUriRequest);
      paramServerApiTask.markConnectOkTime();
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
    paramServerApiTask.markHeadOkTime();
    localSCResponse.setHttpCode(localStatusLine.getStatusCode());
    localSCResponse.setHttpReason(localStatusLine.getReasonPhrase());
    Logger.i("DetectSCHttpApiEngine", "httpResponse headers=>" + localHttpResponse.getAllHeaders());
    Logger.i("DetectSCHttpApiEngine", "httpUriRequest headers=>" + localHttpUriRequest.getAllHeaders());
    localSCResponse.setHeaders(localHttpResponse.getAllHeaders());
    localSCResponse.setRequestHeaders(localHttpUriRequest.getAllHeaders());
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
          Logger.w("DetectSCHttpApiEngine", "decode gzip stream error!!");
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
          byte[] arrayOfByte2 = EncryptLogic.decode((byte[])localObject, localHttpUriRequest.getURI().toString(), paramServerApiTask.getTaskName());
          localObject = arrayOfByte2;
          Logger.i("DetectSCHttpApiEngine", "getEntity size:" + localObject.length);
          localSCResponse.setContents((byte[])localObject);
          paramServerApiTask.markBodyOkTime();
          localSCResponse.setRunState(1);
          localSCResponse.setRunReason("OK");
          return localSCResponse;
          localObject = EntityUtils.toByteArray(localHttpResponse.getEntity());
        }
        catch (Exception localException3)
        {
          while (true)
            localException3.printStackTrace();
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

  private static DefaultHttpClient getHttpClient(ServerApiTask paramServerApiTask, int paramInt1, int paramInt2)
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, paramInt1);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, paramInt2);
    HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 16384);
    HttpClientParams.setRedirecting(localBasicHttpParams, paramServerApiTask.isAutoRedirect());
    return new DefaultHttpClient(localBasicHttpParams);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.http.ApiDetectSCHttpTools
 * JD-Core Version:    0.6.2
 */