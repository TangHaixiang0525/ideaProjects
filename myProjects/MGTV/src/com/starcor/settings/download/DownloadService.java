package com.starcor.settings.download;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;
import com.starcor.settings.utils.Debug;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DownloadService extends Service
  implements MediaScannerConnection.MediaScannerConnectionClient
{
  private static final int DEVICE_LOST = 5;
  private static final int MAX_DOWNLOAD_THREAD_COUNT = 1;
  private Map<Long, DownloadInfo> mDownloads = new HashMap();
  private boolean mMediaScannerConnected;
  private MediaScannerConnection mMediaScannerConnection;
  private DownloadManagerContentObserver mObserver;
  private boolean mPendingUpdate;
  private SystemFacade mSystemFacade;
  UpdateThread mUpdateThread;
  private Handler toastHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 5)
        Toast.makeText(DownloadService.this, "DMS Lost", 0).show();
    }
  };

  private void bindMediaScanner()
  {
    if (!this.mMediaScannerConnected)
      this.mMediaScannerConnection.connect();
  }

  private void deleteDownload(long paramLong)
  {
    DownloadInfo localDownloadInfo = (DownloadInfo)this.mDownloads.get(Long.valueOf(paramLong));
    if (localDownloadInfo.shouldScanFile())
      scanFile(localDownloadInfo);
    if (localDownloadInfo.mStatus == 192)
      localDownloadInfo.mStatus = 490;
    if ((localDownloadInfo.mDeleted) && (localDownloadInfo.mFileName != null))
      new File(localDownloadInfo.mFileName).delete();
    this.mSystemFacade.cancelNotification(localDownloadInfo.mId);
    this.mDownloads.remove(Long.valueOf(localDownloadInfo.mId));
  }

  private DownloadInfo insertDownload(DownloadInfo.Reader paramReader, long paramLong, boolean paramBoolean)
  {
    DownloadInfo localDownloadInfo = paramReader.newDownloadInfo(this, this.mSystemFacade);
    this.mDownloads.put(Long.valueOf(localDownloadInfo.mId), localDownloadInfo);
    localDownloadInfo.logVerboseInfo();
    if (paramBoolean)
      localDownloadInfo.startIfReady(paramLong);
    return localDownloadInfo;
  }

  private void pauseAllRunningAndPending()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(193));
    String str = "status<'200' AND status!='193' AND " + "deleted != '1'";
    getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues, str, null);
  }

  private boolean scanFile(DownloadInfo paramDownloadInfo)
  {
    Debug.v("DownloadManager", "Scanning file " + paramDownloadInfo.mFileName + " connected:" + this.mMediaScannerConnected);
    if (!this.mMediaScannerConnected)
      return false;
    Debug.v("DownloadManager", "Scanning file " + paramDownloadInfo.mFileName);
    try
    {
      this.mMediaScannerConnection.scanFile(paramDownloadInfo.mFileName, paramDownloadInfo.mMimeType);
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        Debug.e("lucien", "download scan file failed");
    }
  }

  private void unbindMediaScanner()
  {
    if (this.mMediaScannerConnected)
    {
      this.mMediaScannerConnected = false;
      this.mMediaScannerConnection.disconnect();
    }
  }

  private void updateDownload(DownloadInfo.Reader paramReader, DownloadInfo paramDownloadInfo, long paramLong, boolean paramBoolean)
  {
    int i = paramDownloadInfo.mStatus;
    paramReader.updateFromDatabase(paramDownloadInfo);
    if ((!Downloads.Item.isStatusCompleted(i)) && (Downloads.Item.isStatusCompleted(paramDownloadInfo.mStatus)));
    for (int j = 1; ; j = 0)
    {
      if (j != 0)
        this.mSystemFacade.cancelNotification(paramDownloadInfo.mId);
      if (paramBoolean)
        paramDownloadInfo.startIfReady(paramLong);
      return;
    }
  }

  private void updateFromProvider()
  {
    try
    {
      this.mPendingUpdate = true;
      if (this.mUpdateThread == null)
      {
        this.mUpdateThread = new UpdateThread();
        this.mUpdateThread.start();
      }
      return;
    }
    finally
    {
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    throw new UnsupportedOperationException("Cannot bind to Download Manager Service");
  }

  public void onCreate()
  {
    super.onCreate();
    Debug.v("DownloadManager", "Service onCreate");
    if (this.mSystemFacade == null)
      this.mSystemFacade = new RealSystemFacade(this);
    this.mObserver = new DownloadManagerContentObserver();
    getContentResolver().registerContentObserver(Downloads.Item.CONTENT_URI, true, this.mObserver);
    this.mMediaScannerConnection = new MediaScannerConnection(getApplicationContext(), this);
    bindMediaScanner();
    updateFromProvider();
  }

  public void onDestroy()
  {
    unbindMediaScanner();
    getContentResolver().unregisterContentObserver(this.mObserver);
    Debug.v("DownloadManager", "Service onDestroy");
    super.onDestroy();
  }

  public void onMediaScannerConnected()
  {
    this.mMediaScannerConnected = true;
  }

  public void onScanCompleted(String paramString, Uri paramUri)
  {
    Cursor localCursor = getContentResolver().query(Downloads.Item.CONTENT_URI, new String[] { "deleted", "_id", "mimetype", "status" }, "_data=?", new String[] { paramString }, null);
    if (localCursor != null)
      if (localCursor.moveToFirst())
      {
        if ((localCursor.getInt(0) == 1) || (!Downloads.Item.isStatusSuccess(localCursor.getInt(3))))
          break label156;
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("scanned", Integer.valueOf(1));
        if (paramUri != null)
          localContentValues.put("mediaprovider_uri", paramUri.toString());
        getContentResolver().update(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, localCursor.getLong(1)), localContentValues, null, null);
      }
    while (true)
    {
      localCursor.close();
      return;
      label156: Helpers.deleteFile(getContentResolver(), localCursor.getLong(1), paramString, localCursor.getString(2));
    }
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    int i = super.onStartCommand(paramIntent, paramInt1, paramInt2);
    Debug.v("DownloadManager", "Service onStart");
    updateFromProvider();
    return i;
  }

  private class DownloadManagerContentObserver extends ContentObserver
  {
    public DownloadManagerContentObserver()
    {
      super();
    }

    public void onChange(boolean paramBoolean)
    {
      Debug.v("DownloadManager", "onChange---Service ContentObserver received notification");
      DownloadService.this.updateFromProvider();
    }
  }

  private class UpdateThread extends Thread
  {
    public UpdateThread()
    {
      super();
    }

    private void scheduleAlarm(long paramLong)
    {
      AlarmManager localAlarmManager = (AlarmManager)DownloadService.this.getSystemService("alarm");
      if (localAlarmManager == null)
      {
        Debug.e("DownloadManager", "couldn't get alarm manager");
        return;
      }
      Debug.v("DownloadManager", "scheduling retry in " + paramLong + "ms");
      DownloadService.this.pauseAllRunningAndPending();
      Intent localIntent = new Intent("android.intent.action.DOWNLOAD_WAKEUP");
      localIntent.setClass(DownloadService.this.getApplicationContext(), DownloadReceiver.class);
      localAlarmManager.set(0, paramLong + DownloadService.this.mSystemFacade.currentTimeMillis(), PendingIntent.getBroadcast(DownloadService.this, 0, localIntent, 1073741824));
    }

    public void run()
    {
      Process.setThreadPriority(10);
      int i = 0;
      long l1 = 9223372036854775807L;
      long l2;
      HashSet localHashSet;
      Cursor localCursor;
      do
      {
        synchronized (DownloadService.this)
        {
          if (DownloadService.this.mUpdateThread != this)
            throw new IllegalStateException("multiple UpdateThreads in DownloadService");
        }
        if (!DownloadService.this.mPendingUpdate)
        {
          DownloadService.this.mUpdateThread = null;
          if (i == 0)
            DownloadService.this.stopSelf();
          if (l1 != 9223372036854775807L)
            scheduleAlarm(l1);
          return;
        }
        DownloadService.this.mPendingUpdate = false;
        l2 = DownloadService.this.mSystemFacade.currentTimeMillis();
        l1 = 9223372036854775807L;
        localHashSet = new HashSet(DownloadService.this.mDownloads.keySet());
        localCursor = DownloadService.this.getContentResolver().query(Downloads.Item.CONTENT_URI, null, null, null, null);
        i = 0;
      }
      while (localCursor == null);
      label667: 
      while (true)
      {
        DownloadInfo.Reader localReader;
        int k;
        Iterator localIterator2;
        DownloadInfo localDownloadInfo2;
        Object localObject3;
        long l4;
        try
        {
          localReader = new DownloadInfo.Reader(localCursor);
          int j = localCursor.getColumnIndexOrThrow("_id");
          k = 0;
          Iterator localIterator1 = localHashSet.iterator();
          if (!localIterator1.hasNext())
          {
            localCursor.moveToFirst();
            boolean bool1 = localCursor.isAfterLast();
            if (!bool1)
              continue;
            localCursor.close();
            localIterator2 = localHashSet.iterator();
            if (localIterator2.hasNext())
              break label604;
            Iterator localIterator3 = DownloadService.this.mDownloads.values().iterator();
            if (!localIterator3.hasNext())
              break;
            localDownloadInfo2 = (DownloadInfo)localIterator3.next();
            if (!TextUtils.isEmpty(localDownloadInfo2.mMediaProviderUri))
              break label667;
            if (!localDownloadInfo2.shouldScanFile())
              break label631;
            if (DownloadService.this.scanFile(localDownloadInfo2))
              continue;
            i = 1;
            continue;
          }
          if (!((DownloadInfo)DownloadService.this.mDownloads.get(localIterator1.next())).mHasActiveThread)
            continue;
          k++;
          continue;
          long l3 = localCursor.getLong(j);
          localHashSet.remove(Long.valueOf(l3));
          localObject3 = (DownloadInfo)DownloadService.this.mDownloads.get(Long.valueOf(l3));
          if (localObject3 == null)
            break label543;
          DownloadService localDownloadService2 = DownloadService.this;
          if (k < 1)
          {
            bool2 = true;
            localDownloadService2.updateDownload(localReader, (DownloadInfo)localObject3, l2, bool2);
            if (((DownloadInfo)localObject3).mHasActiveThread)
              k++;
            if ((((DownloadInfo)localObject3).shouldScanFile()) && (!DownloadService.this.scanFile((DownloadInfo)localObject3)))
              i = 1;
            if (((DownloadInfo)localObject3).hasCompletionNotification())
              i = 1;
            l4 = ((DownloadInfo)localObject3).nextAction(l2);
            if (l4 != 0L)
              break label584;
            i = 1;
            localCursor.moveToNext();
            continue;
          }
        }
        finally
        {
          localCursor.close();
        }
        boolean bool2 = false;
        continue;
        label543: DownloadService localDownloadService3 = DownloadService.this;
        if (k < 1);
        for (boolean bool3 = true; ; bool3 = false)
        {
          DownloadInfo localDownloadInfo1 = localDownloadService3.insertDownload(localReader, l2, bool3);
          localObject3 = localDownloadInfo1;
          break;
        }
        label584: if ((l4 > 0L) && (l4 < l1))
        {
          l1 = l4;
          continue;
          label604: Long localLong = (Long)localIterator2.next();
          DownloadService.this.deleteDownload(localLong.longValue());
          continue;
          label631: if (localDownloadInfo2.mDeleted)
          {
            Helpers.deleteFile(DownloadService.this.getContentResolver(), localDownloadInfo2.mId, localDownloadInfo2.mFileName, localDownloadInfo2.mMimeType);
            continue;
            if (localDownloadInfo2.mDeleted)
            {
              DownloadService.this.getContentResolver().delete(Uri.parse(localDownloadInfo2.mMediaProviderUri), null, null);
              Helpers.deleteFile(DownloadService.this.getContentResolver(), localDownloadInfo2.mId, localDownloadInfo2.mFileName, localDownloadInfo2.mMimeType);
            }
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.DownloadService
 * JD-Core Version:    0.6.2
 */