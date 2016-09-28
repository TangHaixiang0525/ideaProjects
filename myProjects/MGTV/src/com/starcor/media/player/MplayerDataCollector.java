package com.starcor.media.player;

import android.content.Context;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.http.HttpClientManager;
import com.starcor.core.report.enums.BufferEnum;
import com.starcor.core.utils.Logger;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpParams;

public class MplayerDataCollector
{
  public static final String PLAYEND_TYPE_CLOSED = "Closed";
  public static final String PLAYEND_TYPE_ENDED = "Ended";
  public static final String PLAYEND_TYPE_STOPPED = "Stopped";
  public static final String PLAYEND_TYPE_SWITCHED = "Switched";
  private static final String TAG = "MplayerDataCollector";
  public static boolean isHWAnalyticServiceOpen = false;
  ArrayList<MplayerDataReporter> _reporters = new ArrayList();

  public static MplayerDataCollector create()
  {
    return new MplayerDataCollector();
  }

  public static String getRedirectByHttpClient(String paramString)
  {
    String str;
    try
    {
      HttpGet localHttpGet = new HttpGet();
      localHttpGet.setURI(URI.create(paramString));
      HttpClient localHttpClient = HttpClientManager.getHttpClient();
      localHttpClient.getParams().setParameter("http.protocol.handle-redirects", Boolean.valueOf(false));
      Header[] arrayOfHeader = localHttpClient.execute(localHttpGet).getAllHeaders();
      int i = arrayOfHeader.length;
      for (int j = 0; ; j++)
      {
        str = null;
        if (j >= i)
          break;
        Header localHeader = arrayOfHeader[j];
        if ("Location".equals(localHeader.getName()))
        {
          Logger.i("MplayerDataCollector", "head:" + localHeader.getName() + "=" + localHeader.getValue());
          str = localHeader.getValue();
          break;
        }
        Logger.i("MplayerDataCollector", " All head:" + localHeader.getName() + "=" + localHeader.getValue());
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      Logger.i("MplayerDataCollector", " 网络连接错误！！");
      isHWAnalyticServiceOpen = false;
      return null;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        isHWAnalyticServiceOpen = false;
      }
    }
    return str;
  }

  public static String getRedirectByHttpURLConnection(String paramString)
  {
    Logger.i("MplayerDataCollector", "geturl" + paramString);
    HttpURLConnection localHttpURLConnection;
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      HttpURLConnection.setFollowRedirects(false);
      int i = localHttpURLConnection.getResponseCode();
      Logger.i("MplayerDataCollector", "geturl resposecode:" + i);
      Iterator localIterator1 = localHttpURLConnection.getHeaderFields().entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        Iterator localIterator2 = ((List)localEntry.getValue()).iterator();
        while (localIterator2.hasNext())
        {
          String str = (String)localIterator2.next();
          Logger.i("MplayerDataCollector", "head:key=" + (String)localEntry.getKey() + "value:" + str);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      return null;
      localHttpURLConnection.disconnect();
    }
  }

  public static long timestamp()
  {
    return System.nanoTime() / 1000000L;
  }

  public void addReporter(MplayerDataReporter paramMplayerDataReporter)
  {
    this._reporters.add(paramMplayerDataReporter);
  }

  public void onAddVideotaskToPrePare(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    Logger.i("MplayerDataCollector", "report video is preparing");
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onAddPlayTask2Play(paramContext, paramString1, paramString2, paramString3, paramString4, paramInt);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onError(String paramString)
  {
    Logger.i("MplayerDataCollector", "report video is preparing");
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onError(paramString);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerBufferBegin(BufferEnum paramBufferEnum, long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerBufferBegin() bufferType:" + paramBufferEnum + ", currentPlayPos:" + paramLong);
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerBufferBegin(paramBufferEnum, paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerBufferEnd(long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerBufferEnd() currentPlayPos:" + paramLong);
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerBufferEnd(paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerCreate(String paramString)
  {
    Logger.i("MplayerDataCollector", "onPlayerCreate()");
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerCreate(paramString);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerError(int paramInt)
  {
    Logger.i("MplayerDataCollector", "onPlayerError() errorCode:" + paramInt);
  }

  public void onPlayerFirstStart(String paramString1, int paramInt, long paramLong1, long paramLong2, long paramLong3, String paramString2, MediaPlayerCore paramMediaPlayerCore, String paramString3, String paramString4, long paramLong4, PlayerIntentParams paramPlayerIntentParams)
  {
    Logger.i("MplayerDataCollector", "onPlayerFirstStart() playMode:" + paramInt + ", currentPlayPos:" + paramLong1 + ", startTime:" + paramLong2 + ", offsetTime:" + paramLong3 + ", channelId:" + paramString2);
    Logger.i("MplayerDataCollector", "onPlayerFirstStart() url=================:" + paramString1);
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerFirstStart(paramString1, paramInt, paramLong1, paramLong2, paramLong3, paramString2, paramLong4, paramPlayerIntentParams);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerPause(long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerPause() currentPlayPos:" + paramLong);
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerPause(paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerSeekBegin(long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerSeekBegin() currentPlayPos:" + paramLong);
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerSeekBegin(paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerSeekEnd(long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerSeekEnd()");
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerSeekEnd(paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerStart(long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerStart() currentPlayPos:" + paramLong);
    Executors.newSingleThreadExecutor();
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerStart(paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onPlayerStop(long paramLong)
  {
    Logger.i("MplayerDataCollector", "onPlayerStop() currentPlayPos:" + paramLong);
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onPlayerStop(paramLong);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void release()
  {
    Logger.i("MplayerDataCollector", "release");
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.release();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void removeReporter(MplayerDataReporter paramMplayerDataReporter)
  {
    this._reporters.remove(paramMplayerDataReporter);
  }

  public void startCollector()
  {
    Logger.i("MplayerDataCollector", "startCollector()");
    Iterator localIterator = this._reporters.iterator();
    while (localIterator.hasNext())
    {
      MplayerDataReporter localMplayerDataReporter = (MplayerDataReporter)localIterator.next();
      try
      {
        localMplayerDataReporter.onStartCollector(this);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerDataCollector
 * JD-Core Version:    0.6.2
 */