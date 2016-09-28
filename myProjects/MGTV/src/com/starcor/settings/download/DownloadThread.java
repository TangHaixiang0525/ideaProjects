package com.starcor.settings.download;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import com.starcor.settings.utils.Debug;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;

public class DownloadThread extends Thread
{
  public static final int DOWNLOAD_COMPLETE = 96;
  public static final String GOTO_DOWNLOAD_COMPLETE = "com.smedio.download.complete";
  private Context mContext;
  private DownloadInfo mInfo;
  private SystemFacade mSystemFacade;

  public DownloadThread(Context paramContext, SystemFacade paramSystemFacade, DownloadInfo paramDownloadInfo)
  {
    this.mContext = paramContext;
    this.mSystemFacade = paramSystemFacade;
    this.mInfo = paramDownloadInfo;
  }

  private void addRequestHeaders(InnerState paramInnerState, HttpGet paramHttpGet)
  {
    Iterator localIterator = this.mInfo.getHeaders().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if (paramInnerState.mContinuingDownload)
          paramHttpGet.addHeader("Range", "bytes=" + paramInnerState.mBytesSoFar + "-");
        return;
      }
      Pair localPair = (Pair)localIterator.next();
      paramHttpGet.addHeader((String)localPair.first, (String)localPair.second);
    }
  }

  private boolean cannotResume(InnerState paramInnerState)
  {
    if (paramInnerState.mBytesSoFar > 0L);
    return false;
  }

  private void checkConnectivity()
    throws DownloadThread.StopRequest
  {
    if (!checkNetwork(true))
      throw new StopRequest(193, "the network unavailable");
    int i = this.mInfo.checkCanUseNetwork();
    if (i != 1)
      throw new StopRequest(195, this.mInfo.getLogMessageForNetworkError(i));
  }

  private boolean checkNetwork(boolean paramBoolean)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
      return (!paramBoolean) || (localNetworkInfo.getType() == 1);
    return false;
  }

  private void checkPausedOrCanceled(State paramState)
    throws DownloadThread.StopRequest
  {
    if (this.mInfo.mStatus == 490)
      throw new StopRequest(this.mInfo.mStatus, "download canceled");
    if (this.mInfo.mStatus == 193)
      throw new StopRequest(this.mInfo.mStatus, "download paused");
  }

  private void cleanupDestination(State paramState, int paramInt)
  {
    closeDestination(paramState);
    if ((paramState.mFilename != null) && (Downloads.Item.isStatusError(paramInt)))
    {
      new File(paramState.mFilename).delete();
      paramState.mFilename = null;
    }
  }

  private void closeDestination(State paramState)
  {
    try
    {
      if (paramState.mStream != null)
      {
        paramState.mStream.close();
        paramState.mStream = null;
      }
      return;
    }
    catch (IOException localIOException)
    {
      Debug.v("DownloadManager", "exception when closing the file after download : " + localIOException);
    }
  }

  private void executeDownload(State paramState, AndroidHttpClient paramAndroidHttpClient, HttpGet paramHttpGet)
    throws DownloadThread.StopRequest, DownloadThread.RetryDownload
  {
    InnerState localInnerState = new InnerState(null);
    byte[] arrayOfByte = new byte[4096];
    setupDestinationFile(paramState, localInnerState);
    addRequestHeaders(localInnerState, paramHttpGet);
    checkConnectivity();
    HttpResponse localHttpResponse = sendRequest(paramState, paramAndroidHttpClient, paramHttpGet);
    handleExceptionalStatus(paramState, localInnerState, localHttpResponse);
    Debug.v("DownloadManager", "received response for " + this.mInfo.mUri);
    processResponseHeaders(paramState, localInnerState, localHttpResponse);
    transferData(paramState, localInnerState, arrayOfByte, openResponseEntity(paramState, localHttpResponse));
  }

  private void finalizeDestinationFile(State paramState)
    throws DownloadThread.StopRequest
  {
    syncDestination(paramState);
  }

  private int getFinalStatusForHttpError(State paramState)
  {
    if (this.mSystemFacade.getActiveNetworkType() == null)
      return 195;
    if (this.mInfo.mNumFailed < 5)
    {
      paramState.mCountRetry = true;
      return 194;
    }
    Debug.w("DownloadManager", "reached max retries for " + this.mInfo.mId);
    return 495;
  }

  private String getMiniType(String paramString)
  {
    String str = paramString.substring(paramString.lastIndexOf("."), paramString.length());
    if (str.substring(-1 + str.length()).equals("\""))
      str = str.substring(0, -1 + str.length());
    Debug.i("lucien", "the extension name is : " + str);
    return str;
  }

  private void handleEndOfStream(State paramState, InnerState paramInnerState)
    throws DownloadThread.StopRequest
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("current_bytes", Long.valueOf(paramInnerState.mBytesSoFar));
    if (paramInnerState.mHeaderContentLength == null)
      localContentValues.put("total_bytes", Long.valueOf(paramInnerState.mBytesSoFar));
    this.mContext.getContentResolver().update(this.mInfo.getAllDownloadsUri(), localContentValues, null, null);
    int i;
    if ((paramInnerState.mHeaderContentLength != null) && (paramInnerState.mBytesSoFar != Integer.parseInt(paramInnerState.mHeaderContentLength)))
      i = 1;
    while (i != 0)
      if (cannotResume(paramInnerState))
      {
        throw new StopRequest(489, "mismatched content length");
        i = 0;
      }
      else
      {
        throw new StopRequest(getFinalStatusForHttpError(paramState), "closed socket before end of file");
      }
  }

  private void handleExceptionalStatus(State paramState, InnerState paramInnerState, HttpResponse paramHttpResponse)
    throws DownloadThread.StopRequest, DownloadThread.RetryDownload
  {
    int i = paramHttpResponse.getStatusLine().getStatusCode();
    if ((i == 503) && (this.mInfo.mNumFailed < 5))
      handleServiceUnavailable(paramState, paramHttpResponse);
    if ((i == 301) || (i == 302) || (i == 303) || (i == 307))
      handleRedirect(paramState, paramHttpResponse, i);
    if (paramInnerState.mContinuingDownload);
    for (int j = 206; ; j = 200)
    {
      if (i != j)
        handleOtherStatus(paramState, paramInnerState, i);
      return;
    }
  }

  private void handleOtherStatus(State paramState, InnerState paramInnerState, int paramInt)
    throws DownloadThread.StopRequest
  {
    int i;
    if (Downloads.Item.isStatusError(paramInt))
      i = paramInt;
    while (true)
    {
      throw new StopRequest(i, "http error " + paramInt);
      if ((paramInt >= 300) && (paramInt < 400))
        i = 493;
      else if ((paramInnerState.mContinuingDownload) && (paramInt == 200))
        i = 489;
      else
        i = 494;
    }
  }

  private void handleRedirect(State paramState, HttpResponse paramHttpResponse, int paramInt)
    throws DownloadThread.StopRequest, DownloadThread.RetryDownload
  {
    Debug.v("DownloadManager", "got HTTP redirect " + paramInt);
    if (paramState.mRedirectCount >= 5)
      throw new StopRequest(497, "too many redirects");
    Header localHeader = paramHttpResponse.getFirstHeader("Location");
    if (localHeader == null)
      return;
    Debug.v("DownloadManager", "Location :" + localHeader.getValue());
    try
    {
      String str = new URI(this.mInfo.mUri).resolve(new URI(localHeader.getValue())).toString();
      paramState.mRedirectCount = (1 + paramState.mRedirectCount);
      paramState.mRequestUri = str;
      if ((paramInt == 301) || (paramInt == 303))
        paramState.mNewUri = str;
      throw new RetryDownload(null);
    }
    catch (URISyntaxException localURISyntaxException)
    {
      Debug.d("DownloadManager", "Couldn't resolve redirect URI " + localHeader.getValue() + " for " + this.mInfo.mUri);
    }
    throw new StopRequest(495, "Couldn't resolve redirect URI");
  }

  private void handleServiceUnavailable(State paramState, HttpResponse paramHttpResponse)
    throws DownloadThread.StopRequest
  {
    Debug.v("DownloadManager", "got HTTP response code 503");
    paramState.mCountRetry = true;
    Header localHeader = paramHttpResponse.getFirstHeader("Retry-After");
    if (localHeader != null);
    try
    {
      Debug.v("DownloadManager", "Retry-After :" + localHeader.getValue());
      paramState.mRetryAfter = Integer.parseInt(localHeader.getValue());
      if (paramState.mRetryAfter < 0)
      {
        paramState.mRetryAfter = 0;
        label85: throw new StopRequest(194, "got 503 Service Unavailable, will retry later");
      }
      if (paramState.mRetryAfter < 30)
        paramState.mRetryAfter = 30;
      while (true)
      {
        paramState.mRetryAfter += Helpers.sRandom.nextInt(31);
        paramState.mRetryAfter = (1000 * paramState.mRetryAfter);
        break;
        if (paramState.mRetryAfter > 86400)
          paramState.mRetryAfter = 86400;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      break label85;
    }
  }

  private void logNetworkState()
  {
    StringBuilder localStringBuilder = new StringBuilder("Net ");
    if (Helpers.isNetworkAvailable(this.mSystemFacade));
    for (String str = "Up"; ; str = "Down")
    {
      Debug.i("DownloadManager", str);
      return;
    }
  }

  private void notifyDownloadCompleted(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, String paramString1, String paramString2, String paramString3)
  {
    notifyThroughDatabase(paramInt1, paramBoolean1, paramInt2, paramBoolean2, paramString1, paramString2, paramString3);
    if (Downloads.Item.isStatusCompleted(paramInt1))
      this.mInfo.sendIntentIfRequested();
  }

  private void notifyThroughDatabase(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(paramInt1));
    localContentValues.put("_data", paramString1);
    if (paramString2 != null)
      localContentValues.put("uri", paramString2);
    localContentValues.put("mimetype", paramString3);
    localContentValues.put("lastmod", Long.valueOf(this.mSystemFacade.currentTimeMillis()));
    localContentValues.put("method", Integer.valueOf(paramInt2));
    localContentValues.put("downloaded_time", Long.valueOf(this.mInfo.mDownloadedTime));
    if (!paramBoolean1)
      localContentValues.put("numfailed", Integer.valueOf(0));
    while (true)
    {
      this.mContext.getContentResolver().update(this.mInfo.getAllDownloadsUri(), localContentValues, null, null);
      return;
      if (paramBoolean2)
        localContentValues.put("numfailed", Integer.valueOf(1));
      else
        localContentValues.put("numfailed", Integer.valueOf(1 + this.mInfo.mNumFailed));
    }
  }

  private InputStream openResponseEntity(State paramState, HttpResponse paramHttpResponse)
    throws DownloadThread.StopRequest
  {
    try
    {
      InputStream localInputStream = paramHttpResponse.getEntity().getContent();
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      logNetworkState();
      throw new StopRequest(getFinalStatusForHttpError(paramState), "while getting entity: " + localIOException.toString(), localIOException);
    }
  }

  // ERROR //
  private void processResponseHeaders(State paramState, InnerState paramInnerState, HttpResponse paramHttpResponse)
    throws DownloadThread.StopRequest
  {
    // Byte code:
    //   0: aload_2
    //   1: getfield 59	com/starcor/settings/download/DownloadThread$InnerState:mContinuingDownload	Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: aload_1
    //   10: aload_2
    //   11: aload_3
    //   12: invokespecial 530	com/starcor/settings/download/DownloadThread:readResponseHeaders	(Lcom/starcor/settings/download/DownloadThread$State;Lcom/starcor/settings/download/DownloadThread$InnerState;Lorg/apache/http/HttpResponse;)V
    //   15: aload_0
    //   16: getfield 24	com/starcor/settings/download/DownloadThread:mContext	Landroid/content/Context;
    //   19: astore 5
    //   21: aload_0
    //   22: getfield 28	com/starcor/settings/download/DownloadThread:mInfo	Lcom/starcor/settings/download/DownloadInfo;
    //   25: getfield 533	com/starcor/settings/download/DownloadInfo:mType	I
    //   28: istore 6
    //   30: aload_0
    //   31: getfield 28	com/starcor/settings/download/DownloadThread:mInfo	Lcom/starcor/settings/download/DownloadInfo;
    //   34: getfield 236	com/starcor/settings/download/DownloadInfo:mUri	Ljava/lang/String;
    //   37: astore 7
    //   39: aload_0
    //   40: getfield 28	com/starcor/settings/download/DownloadThread:mInfo	Lcom/starcor/settings/download/DownloadInfo;
    //   43: getfield 536	com/starcor/settings/download/DownloadInfo:mHint	Ljava/lang/String;
    //   46: astore 8
    //   48: aload_1
    //   49: getfield 539	com/starcor/settings/download/DownloadThread$State:mMimeType	Ljava/lang/String;
    //   52: astore 9
    //   54: aload_2
    //   55: getfield 321	com/starcor/settings/download/DownloadThread$InnerState:mHeaderContentLength	Ljava/lang/String;
    //   58: ifnull +96 -> 154
    //   61: aload_2
    //   62: getfield 321	com/starcor/settings/download/DownloadThread$InnerState:mHeaderContentLength	Ljava/lang/String;
    //   65: invokestatic 543	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   68: lstore 10
    //   70: aload_1
    //   71: aload 5
    //   73: iload 6
    //   75: aload 7
    //   77: aload 8
    //   79: aload 9
    //   81: lload 10
    //   83: iconst_0
    //   84: invokestatic 547	com/starcor/settings/download/Helpers:generateSaveFile	(Landroid/content/Context;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;JZ)Ljava/lang/String;
    //   87: putfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   90: aload_1
    //   91: new 192	java/io/FileOutputStream
    //   94: dup
    //   95: aload_1
    //   96: getfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   99: invokespecial 548	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   102: putfield 190	com/starcor/settings/download/DownloadThread$State:mStream	Ljava/io/FileOutputStream;
    //   105: ldc 197
    //   107: new 63	java/lang/StringBuilder
    //   110: dup
    //   111: ldc_w 550
    //   114: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   117: aload_0
    //   118: getfield 28	com/starcor/settings/download/DownloadThread:mInfo	Lcom/starcor/settings/download/DownloadInfo;
    //   121: getfield 236	com/starcor/settings/download/DownloadInfo:mUri	Ljava/lang/String;
    //   124: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: ldc_w 552
    //   130: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: aload_1
    //   134: getfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   137: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   143: invokestatic 208	com/starcor/settings/utils/Debug:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   146: pop
    //   147: aload_0
    //   148: aload_1
    //   149: aload_2
    //   150: invokespecial 555	com/starcor/settings/download/DownloadThread:updateDatabaseFromHeaders	(Lcom/starcor/settings/download/DownloadThread$State;Lcom/starcor/settings/download/DownloadThread$InnerState;)V
    //   153: return
    //   154: lconst_0
    //   155: lstore 10
    //   157: goto -87 -> 70
    //   160: astore 4
    //   162: new 114	com/starcor/settings/download/DownloadThread$StopRequest
    //   165: dup
    //   166: aload_0
    //   167: aload 4
    //   169: getfield 556	com/starcor/settings/download/Helpers$GenerateSaveFileError:mStatus	I
    //   172: aload 4
    //   174: getfield 559	com/starcor/settings/download/Helpers$GenerateSaveFileError:mMessage	Ljava/lang/String;
    //   177: invokespecial 123	com/starcor/settings/download/DownloadThread$StopRequest:<init>	(Lcom/starcor/settings/download/DownloadThread;ILjava/lang/String;)V
    //   180: athrow
    //   181: astore 12
    //   183: new 114	com/starcor/settings/download/DownloadThread$StopRequest
    //   186: dup
    //   187: aload_0
    //   188: sipush 492
    //   191: new 63	java/lang/StringBuilder
    //   194: dup
    //   195: ldc_w 561
    //   198: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   201: aload 12
    //   203: invokevirtual 562	java/io/FileNotFoundException:toString	()Ljava/lang/String;
    //   206: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   212: aload 12
    //   214: invokespecial 523	com/starcor/settings/download/DownloadThread$StopRequest:<init>	(Lcom/starcor/settings/download/DownloadThread;ILjava/lang/String;Ljava/lang/Throwable;)V
    //   217: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   15	70	160	com/starcor/settings/download/Helpers$GenerateSaveFileError
    //   70	90	160	com/starcor/settings/download/Helpers$GenerateSaveFileError
    //   90	105	181	java/io/FileNotFoundException
  }

  private int readFromResponse(State paramState, InnerState paramInnerState, byte[] paramArrayOfByte, InputStream paramInputStream)
    throws DownloadThread.StopRequest
  {
    try
    {
      int i = paramInputStream.read(paramArrayOfByte);
      return i;
    }
    catch (IOException localIOException)
    {
      logNetworkState();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("current_bytes", Long.valueOf(paramInnerState.mBytesSoFar));
      this.mContext.getContentResolver().update(this.mInfo.getAllDownloadsUri(), localContentValues, null, null);
      if (cannotResume(paramInnerState))
        throw new StopRequest(489, "while reading response: " + localIOException.toString() + ", can't resume interrupted download with no ETag", localIOException);
      throw new StopRequest(getFinalStatusForHttpError(paramState), "while reading response: " + localIOException.toString(), localIOException);
    }
  }

  private void readResponseHeaders(State paramState, InnerState paramInnerState, HttpResponse paramHttpResponse)
    throws DownloadThread.StopRequest
  {
    if (paramState.mMimeType == null)
    {
      Header localHeader4 = paramHttpResponse.getFirstHeader("Content-Disposition");
      if (localHeader4 != null)
        paramState.mMimeType = getMiniType(localHeader4.getValue());
    }
    Header localHeader1 = paramHttpResponse.getFirstHeader("ETag");
    if (localHeader1 != null)
      paramInnerState.mHeaderETag = localHeader1.getValue();
    Header localHeader2 = paramHttpResponse.getFirstHeader("Transfer-Encoding");
    String str = null;
    if (localHeader2 != null)
      str = localHeader2.getValue();
    if (str == null)
    {
      Header localHeader3 = paramHttpResponse.getFirstHeader("Content-Length");
      if (localHeader3 != null)
      {
        paramInnerState.mHeaderContentLength = localHeader3.getValue();
        this.mInfo.mTotalBytes = Long.parseLong(paramInnerState.mHeaderContentLength);
      }
      Debug.v("DownloadManager", "Content-Length: " + paramInnerState.mHeaderContentLength);
      Debug.v("DownloadManager", "Content-Type: " + paramState.mMimeType);
      Debug.v("DownloadManager", "ETag: " + paramInnerState.mHeaderETag);
      Debug.v("DownloadManager", "Transfer-Encoding: " + str);
      if ((paramInnerState.mHeaderContentLength != null) || ((str != null) && (str.equalsIgnoreCase("chunked"))))
        break label299;
    }
    label299: for (int i = 1; ; i = 0)
    {
      if (i == 0)
        return;
      throw new StopRequest(495, "can't know size of download, giving up");
      Debug.v("DownloadManager", "ignoring content-length because of xfer-encoding");
      break;
    }
  }

  private void reportProgress(State paramState, InnerState paramInnerState)
  {
    long l = this.mSystemFacade.currentTimeMillis();
    if ((paramInnerState.mBytesSoFar - paramInnerState.mBytesNotified > 4096L) && (l - paramInnerState.mTimeLastNotification > 1500L))
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("current_bytes", Long.valueOf(paramInnerState.mBytesSoFar));
      this.mContext.getContentResolver().update(this.mInfo.getAllDownloadsUri(), localContentValues, null, null);
      paramInnerState.mBytesNotified = paramInnerState.mBytesSoFar;
      paramInnerState.mTimeLastNotification = l;
    }
  }

  private static String sanitizeMimeType(String paramString)
  {
    try
    {
      Object localObject = paramString.trim().toLowerCase(Locale.ENGLISH);
      int i = ((String)localObject).indexOf(';');
      if (i != -1)
      {
        String str = ((String)localObject).substring(0, i);
        localObject = str;
      }
      return localObject;
    }
    catch (NullPointerException localNullPointerException)
    {
    }
    return null;
  }

  private HttpResponse sendRequest(State paramState, AndroidHttpClient paramAndroidHttpClient, HttpGet paramHttpGet)
    throws DownloadThread.StopRequest
  {
    try
    {
      HttpResponse localHttpResponse = paramAndroidHttpClient.execute(paramHttpGet);
      return localHttpResponse;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new StopRequest(495, "while trying to execute request: " + localIllegalArgumentException.toString(), localIllegalArgumentException);
    }
    catch (IOException localIOException)
    {
      logNetworkState();
      throw new StopRequest(getFinalStatusForHttpError(paramState), "while trying to execute request: " + localIOException.toString(), localIOException);
    }
  }

  private void setupDestinationFile(State paramState, InnerState paramInnerState)
    throws DownloadThread.StopRequest
  {
    long l;
    if (!TextUtils.isEmpty(paramState.mFilename))
    {
      if (!Helpers.isFilenameValid(paramState.mFilename))
        throw new StopRequest(492, "found invalid internal destination filename");
      File localFile = new File(paramState.mFilename);
      if (localFile.exists())
      {
        l = localFile.length();
        if (l != 0L)
          break label90;
        localFile.delete();
        paramState.mFilename = null;
      }
    }
    while (true)
    {
      if (paramState.mStream != null)
        closeDestination(paramState);
      return;
      try
      {
        label90: paramState.mStream = new FileOutputStream(paramState.mFilename, true);
        paramInnerState.mBytesSoFar = ((int)l);
        if (this.mInfo.mTotalBytes != -1L)
          paramInnerState.mHeaderContentLength = Long.toString(this.mInfo.mTotalBytes);
        paramInnerState.mHeaderETag = this.mInfo.mETag;
        paramInnerState.mContinuingDownload = true;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        throw new StopRequest(492, "while opening destination for resuming: " + localFileNotFoundException.toString(), localFileNotFoundException);
      }
    }
  }

  // ERROR //
  private void syncDestination(State paramState)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 192	java/io/FileOutputStream
    //   5: dup
    //   6: aload_1
    //   7: getfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   10: iconst_1
    //   11: invokespecial 667	java/io/FileOutputStream:<init>	(Ljava/lang/String;Z)V
    //   14: astore_3
    //   15: aload_3
    //   16: invokevirtual 682	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   19: invokevirtual 687	java/io/FileDescriptor:sync	()V
    //   22: aload_3
    //   23: ifnull +358 -> 381
    //   26: aload_3
    //   27: invokevirtual 195	java/io/FileOutputStream:close	()V
    //   30: return
    //   31: astore 4
    //   33: ldc 197
    //   35: new 63	java/lang/StringBuilder
    //   38: dup
    //   39: ldc_w 689
    //   42: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   45: aload_1
    //   46: getfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   49: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: ldc_w 691
    //   55: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: aload 4
    //   60: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   63: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   66: invokestatic 273	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   69: pop
    //   70: aload_2
    //   71: ifnull -41 -> 30
    //   74: aload_2
    //   75: invokevirtual 195	java/io/FileOutputStream:close	()V
    //   78: return
    //   79: astore 13
    //   81: ldc 197
    //   83: ldc_w 693
    //   86: aload 13
    //   88: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   91: pop
    //   92: return
    //   93: astore 11
    //   95: ldc 197
    //   97: ldc_w 698
    //   100: aload 11
    //   102: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   105: pop
    //   106: return
    //   107: astore 15
    //   109: ldc 197
    //   111: new 63	java/lang/StringBuilder
    //   114: dup
    //   115: ldc_w 689
    //   118: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   121: aload_1
    //   122: getfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   125: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: ldc_w 700
    //   131: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: aload 15
    //   136: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   139: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   142: invokestatic 273	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   145: pop
    //   146: aload_2
    //   147: ifnull -117 -> 30
    //   150: aload_2
    //   151: invokevirtual 195	java/io/FileOutputStream:close	()V
    //   154: return
    //   155: astore 19
    //   157: ldc 197
    //   159: ldc_w 693
    //   162: aload 19
    //   164: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   167: pop
    //   168: return
    //   169: astore 17
    //   171: ldc 197
    //   173: ldc_w 698
    //   176: aload 17
    //   178: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   181: pop
    //   182: return
    //   183: astore 21
    //   185: ldc 197
    //   187: new 63	java/lang/StringBuilder
    //   190: dup
    //   191: ldc_w 702
    //   194: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   197: aload_1
    //   198: getfield 172	com/starcor/settings/download/DownloadThread$State:mFilename	Ljava/lang/String;
    //   201: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: ldc_w 704
    //   207: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: aload 21
    //   212: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   215: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   218: invokestatic 273	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   221: pop
    //   222: aload_2
    //   223: ifnull -193 -> 30
    //   226: aload_2
    //   227: invokevirtual 195	java/io/FileOutputStream:close	()V
    //   230: return
    //   231: astore 25
    //   233: ldc 197
    //   235: ldc_w 693
    //   238: aload 25
    //   240: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   243: pop
    //   244: return
    //   245: astore 23
    //   247: ldc 197
    //   249: ldc_w 698
    //   252: aload 23
    //   254: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   257: pop
    //   258: return
    //   259: astore 27
    //   261: ldc 197
    //   263: ldc_w 706
    //   266: aload 27
    //   268: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   271: pop
    //   272: aload_2
    //   273: ifnull -243 -> 30
    //   276: aload_2
    //   277: invokevirtual 195	java/io/FileOutputStream:close	()V
    //   280: return
    //   281: astore 31
    //   283: ldc 197
    //   285: ldc_w 693
    //   288: aload 31
    //   290: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   293: pop
    //   294: return
    //   295: astore 29
    //   297: ldc 197
    //   299: ldc_w 698
    //   302: aload 29
    //   304: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   307: pop
    //   308: return
    //   309: astore 5
    //   311: aload_2
    //   312: ifnull +7 -> 319
    //   315: aload_2
    //   316: invokevirtual 195	java/io/FileOutputStream:close	()V
    //   319: aload 5
    //   321: athrow
    //   322: astore 8
    //   324: ldc 197
    //   326: ldc_w 693
    //   329: aload 8
    //   331: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   334: pop
    //   335: goto -16 -> 319
    //   338: astore 6
    //   340: ldc 197
    //   342: ldc_w 698
    //   345: aload 6
    //   347: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   350: pop
    //   351: goto -32 -> 319
    //   354: astore 35
    //   356: ldc 197
    //   358: ldc_w 693
    //   361: aload 35
    //   363: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   366: pop
    //   367: return
    //   368: astore 33
    //   370: ldc 197
    //   372: ldc_w 698
    //   375: aload 33
    //   377: invokestatic 696	com/starcor/settings/utils/Debug:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   380: pop
    //   381: return
    //   382: astore 5
    //   384: aload_3
    //   385: astore_2
    //   386: goto -75 -> 311
    //   389: astore 27
    //   391: aload_3
    //   392: astore_2
    //   393: goto -132 -> 261
    //   396: astore 21
    //   398: aload_3
    //   399: astore_2
    //   400: goto -215 -> 185
    //   403: astore 15
    //   405: aload_3
    //   406: astore_2
    //   407: goto -298 -> 109
    //   410: astore 4
    //   412: aload_3
    //   413: astore_2
    //   414: goto -381 -> 33
    //
    // Exception table:
    //   from	to	target	type
    //   2	15	31	java/io/FileNotFoundException
    //   74	78	79	java/io/IOException
    //   74	78	93	java/lang/RuntimeException
    //   2	15	107	java/io/SyncFailedException
    //   150	154	155	java/io/IOException
    //   150	154	169	java/lang/RuntimeException
    //   2	15	183	java/io/IOException
    //   226	230	231	java/io/IOException
    //   226	230	245	java/lang/RuntimeException
    //   2	15	259	java/lang/RuntimeException
    //   276	280	281	java/io/IOException
    //   276	280	295	java/lang/RuntimeException
    //   2	15	309	finally
    //   33	70	309	finally
    //   109	146	309	finally
    //   185	222	309	finally
    //   261	272	309	finally
    //   315	319	322	java/io/IOException
    //   315	319	338	java/lang/RuntimeException
    //   26	30	354	java/io/IOException
    //   26	30	368	java/lang/RuntimeException
    //   15	22	382	finally
    //   15	22	389	java/lang/RuntimeException
    //   15	22	396	java/io/IOException
    //   15	22	403	java/io/SyncFailedException
    //   15	22	410	java/io/FileNotFoundException
  }

  private void transferData(State paramState, InnerState paramInnerState, byte[] paramArrayOfByte, InputStream paramInputStream)
    throws DownloadThread.StopRequest
  {
    while (true)
    {
      int i = readFromResponse(paramState, paramInnerState, paramArrayOfByte, paramInputStream);
      if (i == -1)
      {
        handleEndOfStream(paramState, paramInnerState);
        return;
      }
      paramState.mGotData = true;
      writeDataToDestination(paramState, paramArrayOfByte, i);
      paramInnerState.mBytesSoFar += i;
      reportProgress(paramState, paramInnerState);
      checkPausedOrCanceled(paramState);
    }
  }

  private void updateDatabaseFromHeaders(State paramState, InnerState paramInnerState)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("_data", paramState.mFilename);
    if (paramInnerState.mHeaderETag != null)
      localContentValues.put("etag", paramInnerState.mHeaderETag);
    if (paramState.mMimeType != null)
      localContentValues.put("mimetype", paramState.mMimeType);
    localContentValues.put("total_bytes", Long.valueOf(this.mInfo.mTotalBytes));
    this.mContext.getContentResolver().update(this.mInfo.getAllDownloadsUri(), localContentValues, null, null);
  }

  private String userAgent()
  {
    return "AndroidDownloadManager";
  }

  private void writeDataToDestination(State paramState, byte[] paramArrayOfByte, int paramInt)
    throws DownloadThread.StopRequest
  {
    try
    {
      if (paramState.mStream == null)
        paramState.mStream = new FileOutputStream(paramState.mFilename, true);
      paramState.mStream.write(paramArrayOfByte, 0, paramInt);
      closeDestination(paramState);
      return;
    }
    catch (IOException localIOException)
    {
      if (!Helpers.isExternalMediaMounted())
        throw new StopRequest(499, "external media not mounted while writing destination file");
      if (Helpers.getAvailableBytes(Helpers.getFilesystemRoot(paramState.mFilename)) < paramInt)
        throw new StopRequest(498, "insufficient space while writing destination file", localIOException);
      throw new StopRequest(492, "while writing destination file: " + localIOException.toString(), localIOException);
    }
  }

  public void run()
  {
    Process.setThreadPriority(10);
    State localState = new State(this.mInfo, this.mContext);
    AndroidHttpClient localAndroidHttpClient = null;
    PowerManager.WakeLock localWakeLock = null;
    try
    {
      localWakeLock = ((PowerManager)this.mContext.getSystemService("power")).newWakeLock(1, "DownloadManager");
      localWakeLock.acquire();
      Debug.v("DownloadManager", "initiating download for " + this.mInfo.mUri);
      localAndroidHttpClient = AndroidHttpClient.newInstance(userAgent(), this.mContext);
      j = 0;
      if (j != 0)
      {
        Debug.v("DownloadManager", "download completed for " + this.mInfo.mUri);
        this.mInfo.mDownloadedTime = System.currentTimeMillis();
        finalizeDestinationFile(localState);
        if (localWakeLock != null)
          localWakeLock.release();
        if (localAndroidHttpClient != null)
          localAndroidHttpClient.close();
        cleanupDestination(localState, 200);
        notifyDownloadCompleted(200, localState.mCountRetry, localState.mRetryAfter, localState.mGotData, localState.mFilename, localState.mNewUri, localState.mMimeType);
        this.mInfo.mHasActiveThread = false;
        return;
      }
      Debug.i("DownloadManager", "Initiating request for download " + this.mInfo.mId);
      ConnRouteParams.setDefaultProxy(localAndroidHttpClient.getParams(), null);
      localHttpGet = new HttpGet(localState.mRequestUri);
    }
    catch (StopRequest localStopRequest)
    {
    }
    catch (Throwable localThrowable)
    {
      int j;
      HttpGet localHttpGet;
      int i;
      Debug.w("DownloadManager", "Exception for id " + this.mInfo.mId + ": " + localThrowable);
      return;
    }
    finally
    {
      if (localWakeLock != null)
        localWakeLock.release();
      if (localAndroidHttpClient != null)
        localAndroidHttpClient.close();
      cleanupDestination(localState, 491);
      notifyDownloadCompleted(491, localState.mCountRetry, localState.mRetryAfter, localState.mGotData, localState.mFilename, localState.mNewUri, localState.mMimeType);
      this.mInfo.mHasActiveThread = false;
    }
  }

  private static class InnerState
  {
    public long mBytesNotified = 0L;
    public long mBytesSoFar = 0L;
    public boolean mContinuingDownload = false;
    public String mHeaderContentLength;
    public String mHeaderETag;
    public long mTimeLastNotification = 0L;
  }

  private class RetryDownload extends Throwable
  {
    private static final long serialVersionUID = -8768419357022778172L;

    private RetryDownload()
    {
    }
  }

  private static class State
  {
    public boolean mCountRetry = false;
    public String mFilename;
    public boolean mGotData = false;
    public String mMimeType;
    public String mNewUri;
    public int mRedirectCount = 0;
    public String mRequestUri;
    public int mRetryAfter = 0;
    public FileOutputStream mStream;

    public State(DownloadInfo paramDownloadInfo, Context paramContext)
    {
      this.mMimeType = DownloadThread.sanitizeMimeType(paramDownloadInfo.mMimeType);
      this.mRequestUri = paramDownloadInfo.mUri;
      this.mFilename = paramDownloadInfo.mFileName;
    }
  }

  private class StopRequest extends Throwable
  {
    private static final long serialVersionUID = 7660618395871087888L;
    public int mFinalStatus;

    public StopRequest(int paramString, String arg3)
    {
      super();
      this.mFinalStatus = paramString;
    }

    public StopRequest(int paramString, String paramThrowable, Throwable arg4)
    {
      super(localThrowable);
      this.mFinalStatus = paramString;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.DownloadThread
 * JD-Core Version:    0.6.2
 */