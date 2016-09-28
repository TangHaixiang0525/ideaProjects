package com.starcor.settings.download;

import android.net.Uri;
import android.provider.BaseColumns;

public class Downloads
{
  public static final String AUTHORITY = "com.starcor.settings.download";

  public static class Item
    implements BaseColumns
  {
    public static final String ACCOUNT = "account";
    public static final Uri CONTENT_URI = Uri.parse("content://com.starcor.settings.download/downloads");
    public static final String CURRENT_BYTES = "current_bytes";
    public static final String DELETED = "deleted";
    public static final String DESCRIPTION = "description";
    public static final String DOWNLOADED_TIME = "downloaded_time";
    public static final String ETAG = "etag";
    public static final String FAILED_CONNECTIONS = "numfailed";
    public static final String FILE_NAME_HINT = "hint";
    public static final String LAST_MODIFICATION = "lastmod";
    public static final String MEDIAPROVIDER_URI = "mediaprovider_uri";
    public static final String MEDIA_SCANNED = "scanned";
    public static final String MIME_TYPE = "mimetype";
    public static final int MIN_ARTIFICIAL_ERROR_STATUS = 488;
    public static final String RETRY_AFTER_X_REDIRECT_COUNT = "method";
    public static final String STATUS = "status";
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_CANCELED = 490;
    public static final int STATUS_CANNOT_RESUME = 489;
    public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 499;
    public static final int STATUS_FILE_ALREADY_EXISTS_ERROR = 488;
    public static final int STATUS_FILE_ERROR = 492;
    public static final int STATUS_HTTP_DATA_ERROR = 495;
    public static final int STATUS_HTTP_EXCEPTION = 496;
    public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 498;
    public static final int STATUS_LENGTH_REQUIRED = 411;
    public static final int STATUS_NOT_ACCEPTABLE = 406;
    public static final int STATUS_PAUSED_BY_APP = 193;
    public static final int STATUS_PENDING = 190;
    public static final int STATUS_PRECONDITION_FAILED = 412;
    public static final int STATUS_QUEUED_FOR_WIFI = 196;
    public static final int STATUS_RUNNING = 192;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_TOO_MANY_REDIRECTS = 497;
    public static final int STATUS_UNHANDLED_HTTP_CODE = 494;
    public static final int STATUS_UNHANDLED_REDIRECT = 493;
    public static final int STATUS_UNKNOWN_ERROR = 491;
    public static final int STATUS_WAITING_FOR_NETWORK = 195;
    public static final int STATUS_WAITING_TO_RETRY = 194;
    public static final String TITLE = "title";
    public static final String TOTAL_BYTES = "total_bytes";
    public static final String URI = "uri";
    public static final String _DATA = "_data";

    public static boolean isStatusClientError(int paramInt)
    {
      return (paramInt >= 400) && (paramInt < 500);
    }

    public static boolean isStatusCompleted(int paramInt)
    {
      return ((paramInt >= 200) && (paramInt < 300)) || ((paramInt >= 400) && (paramInt < 600));
    }

    public static boolean isStatusError(int paramInt)
    {
      return (paramInt >= 400) && (paramInt < 600);
    }

    public static boolean isStatusInformational(int paramInt)
    {
      return (paramInt >= 100) && (paramInt < 200);
    }

    public static boolean isStatusPause(int paramInt)
    {
      return (paramInt >= 193) && (paramInt < 200);
    }

    public static boolean isStatusPending(int paramInt)
    {
      return paramInt == 190;
    }

    public static boolean isStatusRunning(int paramInt)
    {
      return paramInt == 192;
    }

    public static boolean isStatusServerError(int paramInt)
    {
      return (paramInt >= 500) && (paramInt < 600);
    }

    public static boolean isStatusSuccess(int paramInt)
    {
      return (paramInt >= 200) && (paramInt < 300);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.Downloads
 * JD-Core Version:    0.6.2
 */