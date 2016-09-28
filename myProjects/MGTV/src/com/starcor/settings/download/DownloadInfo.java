package com.starcor.settings.download;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.net.Uri;
import android.util.Pair;
import com.starcor.settings.utils.Debug;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DownloadInfo
{
  public static final int NETWORK_NO_CONNECTION = 2;
  public static final int NETWORK_OK = 1;
  public String mAccount;
  private Context mContext;
  public long mCurrentBytes;
  public boolean mDeleted;
  public String mDescription;
  public String mDms;
  public long mDownloadedTime;
  public String mETag;
  public String mFileName;
  public int mFuzz;
  public volatile boolean mHasActiveThread;
  public String mHint;
  public long mId;
  public long mLastMod;
  public String mMediaProviderUri;
  public boolean mMediaScanned;
  public String mMimeType;
  public int mNumFailed;
  public String mObjId;
  private List<Pair<String, String>> mRequestHeaders = new ArrayList();
  public int mRetryAfter;
  public int mStatus;
  private SystemFacade mSystemFacade;
  public String mTitle;
  public long mTotalBytes;
  public int mType;
  public String mUri;

  private DownloadInfo(Context paramContext, SystemFacade paramSystemFacade)
  {
    this.mContext = paramContext;
    this.mSystemFacade = paramSystemFacade;
    this.mFuzz = Helpers.sRandom.nextInt(1001);
  }

  private boolean isReadyToStart(long paramLong)
  {
    if (this.mHasActiveThread);
    do
    {
      do
      {
        return false;
        switch (this.mStatus)
        {
        default:
          return false;
        case 0:
        case 190:
        case 192:
          return true;
        case 195:
        case 196:
        case 194:
        }
      }
      while (checkCanUseNetwork() != 1);
      return true;
    }
    while (restartTime(paramLong) > paramLong);
    return true;
  }

  public int checkCanUseNetwork()
  {
    try
    {
      if (this.mSystemFacade.getActiveNetworkType().intValue() != 1)
      {
        int i = this.mSystemFacade.getActiveNetworkType().intValue();
        if (i != 0);
      }
      else
      {
        return 1;
      }
    }
    catch (Exception localException)
    {
      Debug.e("lx", "network exception");
    }
    return 2;
  }

  public Uri getAllDownloadsUri()
  {
    return ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, this.mId);
  }

  public Collection<Pair<String, String>> getHeaders()
  {
    return Collections.unmodifiableList(this.mRequestHeaders);
  }

  public String getLogMessageForNetworkError(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return "unknown error with network connectivity";
    case 2:
    }
    return "no network connection available";
  }

  public Uri getMyDownloadsUri()
  {
    return ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, this.mId);
  }

  public boolean hasCompletionNotification()
  {
    return Downloads.Item.isStatusCompleted(this.mStatus);
  }

  public boolean isOnCache()
  {
    return false;
  }

  public void logVerboseInfo()
  {
  }

  long nextAction(long paramLong)
  {
    long l1 = 0L;
    if (Downloads.Item.isStatusCompleted(this.mStatus))
      l1 = -1L;
    long l2;
    do
    {
      do
        return l1;
      while (this.mStatus != 194);
      l2 = restartTime(paramLong);
    }
    while (l2 <= paramLong);
    return l2 - paramLong;
  }

  public long restartTime(long paramLong)
  {
    if (this.mNumFailed == 0)
      return paramLong;
    if (this.mRetryAfter > 0)
      return this.mLastMod + this.mRetryAfter;
    return this.mLastMod + 30 * (1000 + this.mFuzz) * (1 << -1 + this.mNumFailed);
  }

  public void sendIntentIfRequested()
  {
  }

  boolean shouldScanFile()
  {
    return (!this.mMediaScanned) && (Downloads.Item.isStatusSuccess(this.mStatus));
  }

  void startIfReady(long paramLong)
  {
    if (!isReadyToStart(paramLong))
      return;
    Debug.v("DownloadManager", "Service spawning thread to handle download " + this.mId);
    if (this.mHasActiveThread)
      throw new IllegalStateException("Multiple threads on same download");
    if (this.mStatus != 192)
    {
      this.mStatus = 192;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("status", Integer.valueOf(this.mStatus));
      this.mContext.getContentResolver().update(getAllDownloadsUri(), localContentValues, null, null);
    }
    DownloadThread localDownloadThread = new DownloadThread(this.mContext, this.mSystemFacade, this);
    this.mHasActiveThread = true;
    localDownloadThread.start();
  }

  public static class Reader
  {
    private Cursor mCursor;
    private CharArrayBuffer mNewChars;
    private CharArrayBuffer mOldChars;

    public Reader(Cursor paramCursor)
    {
      this.mCursor = paramCursor;
    }

    private Integer getInt(String paramString)
    {
      return Integer.valueOf(this.mCursor.getInt(this.mCursor.getColumnIndexOrThrow(paramString)));
    }

    private Long getLong(String paramString)
    {
      return Long.valueOf(this.mCursor.getLong(this.mCursor.getColumnIndexOrThrow(paramString)));
    }

    private String getString(String paramString1, String paramString2)
    {
      int i = this.mCursor.getColumnIndexOrThrow(paramString2);
      if (paramString1 == null)
        paramString1 = this.mCursor.getString(i);
      while (true)
      {
        return paramString1;
        if (this.mNewChars == null)
          this.mNewChars = new CharArrayBuffer(128);
        this.mCursor.copyStringToBuffer(i, this.mNewChars);
        int j = this.mNewChars.sizeCopied;
        if (j != paramString1.length())
          return new String(this.mNewChars.data, 0, j);
        if ((this.mOldChars == null) || (this.mOldChars.sizeCopied < j))
          this.mOldChars = new CharArrayBuffer(j);
        char[] arrayOfChar1 = this.mOldChars.data;
        char[] arrayOfChar2 = this.mNewChars.data;
        paramString1.getChars(0, j, arrayOfChar1, 0);
        for (int k = j - 1; k >= 0; k--)
          if (arrayOfChar1[k] != arrayOfChar2[k])
            return new String(arrayOfChar2, 0, j);
      }
    }

    public DownloadInfo newDownloadInfo(Context paramContext, SystemFacade paramSystemFacade)
    {
      DownloadInfo localDownloadInfo = new DownloadInfo(paramContext, paramSystemFacade, null);
      updateFromDatabase(localDownloadInfo);
      return localDownloadInfo;
    }

    public void updateFromDatabase(DownloadInfo paramDownloadInfo)
    {
      int i = 1;
      paramDownloadInfo.mId = getLong("_id").longValue();
      paramDownloadInfo.mUri = getString(paramDownloadInfo.mAccount, "uri");
      paramDownloadInfo.mAccount = getString(paramDownloadInfo.mAccount, "account");
      paramDownloadInfo.mHint = getString(paramDownloadInfo.mHint, "hint");
      paramDownloadInfo.mFileName = getString(paramDownloadInfo.mFileName, "_data");
      paramDownloadInfo.mMimeType = getString(paramDownloadInfo.mMimeType, "mimetype");
      paramDownloadInfo.mStatus = getInt("status").intValue();
      paramDownloadInfo.mNumFailed = getInt("numfailed").intValue();
      paramDownloadInfo.mRetryAfter = (0xFFFFFFF & getInt("method").intValue());
      paramDownloadInfo.mLastMod = getLong("lastmod").longValue();
      paramDownloadInfo.mTotalBytes = getLong("total_bytes").longValue();
      paramDownloadInfo.mCurrentBytes = getLong("current_bytes").longValue();
      paramDownloadInfo.mETag = getString(paramDownloadInfo.mETag, "etag");
      int j;
      if (getInt("scanned").intValue() == i)
      {
        j = i;
        paramDownloadInfo.mMediaScanned = j;
        if (getInt("deleted").intValue() != i)
          break label266;
      }
      while (true)
      {
        paramDownloadInfo.mDeleted = i;
        paramDownloadInfo.mMediaProviderUri = getString(paramDownloadInfo.mMediaProviderUri, "mediaprovider_uri");
        paramDownloadInfo.mTitle = getString(paramDownloadInfo.mTitle, "title");
        paramDownloadInfo.mDescription = getString(paramDownloadInfo.mDescription, "description");
        return;
        j = 0;
        break;
        label266: i = 0;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.DownloadInfo
 * JD-Core Version:    0.6.2
 */