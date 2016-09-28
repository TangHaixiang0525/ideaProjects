package com.starcor.settings.download;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemClock;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.starcor.settings.utils.Debug;
import java.io.File;
import java.util.Random;

public class Helpers
{
  private static final int NO_SPACE = 55;
  public static final int TYPE_AUDIO = 1;
  public static final int TYPE_PHOTO = 2;
  public static final int TYPE_VIDEO;
  public static Random sRandom = new Random(SystemClock.uptimeMillis());

  private static String chooseExtensionFromFilename(String paramString1, String paramString2, int paramInt)
  {
    String str1 = null;
    if (paramString1 != null)
    {
      int i = paramString2.lastIndexOf('.');
      String str2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString2.substring(i + 1));
      if (str2 != null)
      {
        boolean bool = str2.equalsIgnoreCase(paramString1);
        str1 = null;
        if (bool);
      }
      else
      {
        str1 = chooseExtensionFromMimeType(paramString1, false);
        if (str1 == null)
          break label88;
        Debug.v("DownloadManager", "substituting extension from type");
      }
    }
    while (true)
    {
      if (str1 == null)
      {
        Debug.v("DownloadManager", "keeping extension");
        str1 = paramString2.substring(paramInt);
      }
      return str1;
      label88: Debug.v("DownloadManager", "couldn't find extension for " + paramString1);
    }
  }

  private static String chooseExtensionFromMimeType(String paramString, boolean paramBoolean)
  {
    String str = null;
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("audio/mpeg"))
        return ".mp3";
      str = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString);
      if (str != null)
      {
        Debug.v("DownloadManager", "adding extension from type");
        str = "." + str;
      }
    }
    else if (str == null)
    {
      if ((paramString == null) || (!paramString.toLowerCase().startsWith("text/")))
        break label134;
      if (!paramString.equalsIgnoreCase("text/html"))
        break label119;
      Debug.v("DownloadManager", "adding default html extension");
    }
    label134: 
    while (true)
    {
      return paramString;
      Debug.v("DownloadManager", "couldn't find extension for " + paramString);
      break;
      label119: if (paramBoolean)
      {
        Debug.v("DownloadManager", "adding default text extension");
        continue;
        if (paramBoolean)
          Debug.v("DownloadManager", "adding default binary extension");
      }
    }
  }

  private static String chooseFilename(String paramString1, String paramString2)
  {
    String str1 = null;
    int j;
    if (0 == 0)
    {
      str1 = null;
      if (paramString2 != null)
      {
        boolean bool = paramString2.endsWith("/");
        str1 = null;
        if (!bool)
        {
          Debug.v("DownloadManager", "getting filename from hint");
          j = 1 + paramString2.lastIndexOf('/');
          if (j <= 0)
            break label144;
        }
      }
    }
    label144: for (str1 = paramString2.substring(j); ; str1 = paramString2)
    {
      if (str1 == null)
      {
        String str2 = Uri.decode(paramString1);
        if ((str2 != null) && (!str2.endsWith("/")) && (str2.indexOf('?') < 0))
        {
          int i = 1 + str2.lastIndexOf('/');
          if (i > 0)
          {
            Debug.v("DownloadManager", "getting filename from uri");
            str1 = str2.substring(i);
          }
        }
      }
      if (str1 == null)
      {
        Debug.v("DownloadManager", "using default filename");
        str1 = "downloadfile";
      }
      return replaceInvalidVfatCharacters(str1);
    }
  }

  private static String chooseFullPath(Context paramContext, int paramInt, String paramString1, String paramString2, String paramString3, long paramLong)
    throws Helpers.GenerateSaveFileError
  {
    File localFile = getExternalDestination(paramContext, paramLong, paramInt);
    String str1 = chooseFilename(paramString1, paramString2);
    int i = str1.indexOf('.');
    String str2;
    if (i < 0)
      str2 = chooseExtensionFromMimeType(paramString3, true);
    while (true)
    {
      String str3 = localFile.getPath() + File.separator + str1;
      Debug.v("DownloadManager", "target file: " + str3 + str2);
      return chooseUniqueFilename(str3, str2);
      str2 = chooseExtensionFromFilename(paramString3, str1, i);
      str1 = str1.substring(0, i);
    }
  }

  private static String chooseUniqueFilename(String paramString1, String paramString2)
    throws Helpers.GenerateSaveFileError
  {
    String str1 = paramString1 + paramString2;
    if (!new File(str1).exists())
      return str1;
    String str2 = paramString1 + "-";
    int i = 1;
    int j = 1;
    if (j >= 1000000000)
      throw new GenerateSaveFileError(492, "failed to generate an unused filename on internal download storage");
    for (int k = 0; ; k++)
    {
      if (k >= 9)
      {
        j *= 10;
        break;
      }
      String str3 = str2 + i + paramString2;
      if (!new File(str3).exists())
        return str3;
      Debug.v("DownloadManager", "file with sequence number " + i + " exists");
      i += 1 + sRandom.nextInt(j);
    }
  }

  static void deleteFile(ContentResolver paramContentResolver, long paramLong, String paramString1, String paramString2)
  {
    try
    {
      new File(paramString1).delete();
      Uri localUri = Downloads.Item.CONTENT_URI;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      paramContentResolver.delete(localUri, "_id = ? ", arrayOfString);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Debug.w("DownloadManager", "file: '" + paramString1 + "' couldn't be deleted", localException);
    }
  }

  public static String generateSaveFile(Context paramContext, int paramInt, String paramString1, String paramString2, String paramString3, long paramLong, boolean paramBoolean)
    throws Helpers.GenerateSaveFileError
  {
    return chooseFullPath(paramContext, paramInt, paramString1, paramString2, paramString3, paramLong);
  }

  public static long getAvailableBytes(File paramFile)
  {
    StatFs localStatFs = new StatFs(paramFile.getPath());
    return (localStatFs.getAvailableBlocks() - 4L) * localStatFs.getBlockSize();
  }

  private static File getExternalDestination(Context paramContext, long paramLong, int paramInt)
    throws Helpers.GenerateSaveFileError
  {
    String str1 = "";
    File localFile1 = new File(str1);
    if ((!localFile1.exists()) || (!localFile1.isDirectory()))
      localFile1.mkdirs();
    if (getAvailableBytes(localFile1) < paramLong)
    {
      Debug.d("DownloadManager", "download aborted - not enough free space");
      new MyHandler(Looper.getMainLooper(), paramContext).sendEmptyMessage(55);
      throw new GenerateSaveFileError(498, "insufficient space on external media");
    }
    int i;
    int j;
    if ((new File(str1).exists()) && (new File(str1).isFile()))
    {
      i = 1;
      j = 1;
      if (j < 1000000000);
    }
    else
    {
      label130: switch (paramInt)
      {
      default:
      case 1:
      case 0:
      case 2:
      }
    }
    File localFile2;
    while (true)
    {
      localFile2 = new File(str1);
      if ((localFile2.isDirectory()) || (localFile2.mkdirs()))
        break label414;
      throw new GenerateSaveFileError(492, "unable to create external downloads directory " + localFile2.getPath());
      for (int k = 0; ; k++)
      {
        if (k >= 9)
        {
          j *= 10;
          break;
        }
        String str2 = str1 + i;
        if ((!new File(str2).exists()) || (new File(str2).isDirectory()))
          break label130;
        Debug.v("DownloadManager", "file with sequence number " + i + " exists");
        i += 1 + sRandom.nextInt(j);
      }
      str1 = str1 + "/Music";
      continue;
      str1 = str1 + "/Video";
      continue;
      str1 = str1 + "/Photos";
    }
    label414: return localFile2;
  }

  public static File getFilesystemRoot(String paramString)
  {
    File localFile1 = Environment.getDownloadCacheDirectory();
    if (paramString.startsWith(localFile1.getPath()))
      return localFile1;
    File localFile2 = Environment.getExternalStorageDirectory();
    if (paramString.startsWith(localFile2.getPath()))
      return localFile2;
    throw new IllegalArgumentException("Cannot determine filesystem root for " + paramString);
  }

  public static boolean isExternalMediaMounted()
  {
    if (!Environment.getExternalStorageState().equals("mounted"))
    {
      Debug.d("DownloadManager", "no external storage");
      return false;
    }
    return true;
  }

  public static boolean isFilenameValid(String paramString)
  {
    String str = paramString.replaceFirst("/+", "/");
    return (str.startsWith(Environment.getDownloadCacheDirectory().toString())) || (str.startsWith(Environment.getExternalStorageDirectory().toString()));
  }

  public static boolean isNetworkAvailable(SystemFacade paramSystemFacade)
  {
    return paramSystemFacade.getActiveNetworkType() != null;
  }

  private static String replaceInvalidVfatCharacters(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    if (j >= paramString.length())
      return localStringBuffer.toString();
    char c = paramString.charAt(j);
    if (((c >= 0) && (c <= '\037')) || (c == '"') || (c == '*') || (c == '/') || (c == ':') || (c == '<') || (c == '>') || (c == '?') || (c == '\\') || (c == '|') || (c == ''))
      if (i == 0)
        localStringBuffer.append('_');
    for (i = 1; ; i = 0)
    {
      j++;
      break;
      localStringBuffer.append(c);
    }
  }

  public static class GenerateSaveFileError extends Exception
  {
    private static final long serialVersionUID = 1692018499174863419L;
    public String mMessage;
    public int mStatus;

    public GenerateSaveFileError(int paramInt, String paramString)
    {
      this.mStatus = paramInt;
      this.mMessage = paramString;
    }
  }

  private static class MyHandler extends Handler
  {
    Context mContext;

    public MyHandler(Looper paramLooper, Context paramContext)
    {
      super();
      this.mContext = paramContext;
    }

    public void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 55)
        Toast.makeText(this.mContext, "download aborted , not enough free space", 1).show();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.Helpers
 * JD-Core Version:    0.6.2
 */