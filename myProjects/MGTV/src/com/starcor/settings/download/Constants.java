package com.starcor.settings.download;

public class Constants
{
  public static final String ACTION_HIDE = "android.intent.action.DOWNLOAD_HIDE";
  public static final String ACTION_LIST = "android.intent.action.DOWNLOAD_LIST";
  public static final String ACTION_OPEN = "android.intent.action.DOWNLOAD_OPEN";
  public static final String ACTION_RETRY = "android.intent.action.DOWNLOAD_WAKEUP";
  public static final int BUFFER_SIZE = 4096;
  public static final String DEFAULT_DL_BINARY_EXTENSION = ".bin";
  public static final String DEFAULT_DL_FILENAME = "downloadfile";
  public static final String DEFAULT_DL_HTML_EXTENSION = ".html";
  public static final String DEFAULT_DL_SUBDIR = "/download";
  public static final String DEFAULT_DL_TEXT_EXTENSION = ".txt";
  public static final String DEFAULT_MUSIC_SUBDIR = "/Music";
  public static final String DEFAULT_PHOTO_SUBDIR = "/Photos";
  public static final String DEFAULT_USER_AGENT = "AndroidDownloadManager";
  public static final String DEFAULT_VIDEO_SUBDIR = "/Video";
  public static final String FILENAME_SEQUENCE_SEPARATOR = "-";
  public static final boolean IGNORE_ETAG = true;
  public static final boolean LOGV = true;
  public static final boolean LOGVV = true;
  static final boolean LOGX = true;
  public static final int MAX_DOWNLOADS = 1000;
  public static final int MAX_REDIRECTS = 5;
  public static final int MAX_RETRIES = 5;
  public static final int MAX_RETRY_AFTER = 86400;
  public static final int MIN_PROGRESS_STEP = 4096;
  public static final long MIN_PROGRESS_TIME = 1500L;
  public static final int MIN_RETRY_AFTER = 30;
  public static final int RETRY_FIRST_DELAY = 30;
  public static final String TAG = "DownloadManager";
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.Constants
 * JD-Core Version:    0.6.2
 */