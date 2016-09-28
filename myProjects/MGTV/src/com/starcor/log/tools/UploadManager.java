package com.starcor.log.tools;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class UploadManager
{
  private static final String CHARSET = "utf-8";
  private static final String FAILURE = "failed";
  private static final String SUCCESS = "success";
  private static final String TAG = UploadManager.class.getSimpleName();
  private static final int TIME_OUT = 300000;
  private String currentResultContent = null;

  private List<String> getFiles(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    File localFile1 = new File(paramString);
    if ((localFile1 == null) || (!localFile1.exists()));
    while (true)
    {
      return localArrayList;
      if (localFile1.isFile())
      {
        localArrayList.add(localFile1.getAbsolutePath());
        return localArrayList;
      }
      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
      {
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++)
        {
          File localFile2 = arrayOfFile[j];
          if (localFile2.isFile())
          {
            Logger.i(TAG, "检测到" + localFile2.getName());
            localArrayList.add(localFile2.getAbsolutePath());
            Logger.i(TAG, "日志压缩包" + localFile2.getName() + "大小为" + localFile2.length());
          }
        }
      }
    }
  }

  public void upload(String paramString1, String paramString2, Map<String, String> paramMap, LogCache.UploadCallBack paramUploadCallBack)
  {
    Logger.i(TAG, "开始上传日志");
    Iterator localIterator = getFiles(paramString1).iterator();
    while (true)
      if (localIterator.hasNext())
      {
        File localFile = new File((String)localIterator.next());
        try
        {
          String str = uploadFile(localFile, paramString2, paramMap);
          if (localFile.exists())
          {
            Logger.i(TAG, "删除日志压缩包" + localFile.getName() + "大小为" + localFile.length());
            localFile.delete();
          }
          if ("success".equals(str))
          {
            Logger.i(TAG, "日志上传成功");
            if (paramUploadCallBack != null)
              paramUploadCallBack.onSuccess(this.currentResultContent);
            this.currentResultContent = null;
          }
          else
          {
            Logger.i(TAG, "日志上传失败");
            if (paramUploadCallBack != null)
              paramUploadCallBack.onError();
          }
        }
        finally
        {
          if (localFile.exists())
          {
            Logger.i(TAG, "删除日志压缩包" + localFile.getName() + "大小为" + localFile.length());
            localFile.delete();
          }
          if ("success".equals(null))
          {
            Logger.i(TAG, "日志上传成功");
            if (paramUploadCallBack != null)
              paramUploadCallBack.onSuccess(this.currentResultContent);
            this.currentResultContent = null;
          }
        }
      }
  }

  public String uploadFile(File paramFile, String paramString, Map<String, String> paramMap)
  {
    String str1 = UUID.randomUUID().toString();
    if (TextUtils.isEmpty(paramString))
    {
      Logger.i(TAG, "请求url为空");
      return "failed";
    }
    try
    {
      Logger.i(TAG, "传入的上传url" + paramString);
      URL localURL = new URL(paramString);
      localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      localHttpURLConnection.setReadTimeout(300000);
      localHttpURLConnection.setConnectTimeout(300000);
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.setDoOutput(true);
      localHttpURLConnection.setUseCaches(false);
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.setRequestProperty("Charset", "utf-8");
      localHttpURLConnection.setRequestProperty("connection", "keep-alive");
      localHttpURLConnection.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + str1);
      localDataOutputStream = new DataOutputStream(localHttpURLConnection.getOutputStream());
      if ((paramMap != null) && (!paramMap.isEmpty()))
      {
        localStringBuilder1 = new StringBuilder();
        Iterator localIterator = paramMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          localStringBuilder1.append("--");
          localStringBuilder1.append(str1);
          localStringBuilder1.append("\r\n");
          localStringBuilder1.append("Content-Disposition: form-data; name=\"" + (String)localEntry.getKey() + "\"");
          localStringBuilder1.append("\r\n");
          localStringBuilder1.append("\r\n");
          localStringBuilder1.append((String)localEntry.getValue());
          localStringBuilder1.append("\r\n");
        }
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      StringBuilder localStringBuilder1;
      localMalformedURLException.printStackTrace();
      return "failed";
      localDataOutputStream.write(localStringBuilder1.toString().getBytes());
      localDataOutputStream.write(("--" + str1 + "\r\n" + "Content-Disposition: form-data; name=\"img\"; filename=\"" + paramFile.getName() + "\"" + "\r\n" + "Content-Type: application/octet-stream; charset=" + "utf-8" + "\r\n" + "\r\n").getBytes());
      localFileInputStream = new FileInputStream(paramFile);
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localDataOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (IOException localIOException)
    {
      HttpURLConnection localHttpURLConnection;
      int j;
      do
      {
        DataOutputStream localDataOutputStream;
        FileInputStream localFileInputStream;
        while (true)
          localIOException.printStackTrace();
        localFileInputStream.close();
        localDataOutputStream.write("\r\n".getBytes());
        localDataOutputStream.write(("--" + str1 + "--" + "\r\n").getBytes());
        localDataOutputStream.flush();
        j = localHttpURLConnection.getResponseCode();
        Logger.i(TAG, "response code:" + j);
      }
      while (j != 200);
      StringBuilder localStringBuilder2 = new StringBuilder();
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      if (localInputStream != null)
      {
        InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream);
        BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
        while (true)
        {
          String str2 = localBufferedReader.readLine();
          if (str2 == null)
            break;
          localStringBuilder2.append(str2);
          localStringBuilder2.append("\n");
        }
        this.currentResultContent = localStringBuilder2.toString();
        localBufferedReader.close();
        localInputStreamReader.close();
      }
    }
    return "success";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.log.tools.UploadManager
 * JD-Core Version:    0.6.2
 */