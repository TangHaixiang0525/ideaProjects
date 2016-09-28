package com.starcor.hunan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import com.starcor.core.domain.ImageAd;
import com.starcor.core.image.BitmapTools;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.IOTools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class AdvertActivity extends DialogActivity
{
  protected int count = 0;
  private ImageAd imageAd;
  private ImageView img_advert;
  private int index = 0;
  private HashMap<String, Bitmap> mAdvertCache;
  private HashMap<Integer, String> mLocalPathCache;
  private String path = App.getInstance().getDir("advert", 1).toString() + File.separator;
  protected int time = 0;

  private void DownloadAdvert()
  {
    if ((this.imageAd == null) || (this.imageAd.getImageUrls() == null) || (this.imageAd.getImageUrls().size() <= 0));
    while (true)
    {
      return;
      if (!this.path.endsWith("/"))
        this.path += "/";
      this.path += "starcor/inneradvert/";
      File localFile = new File(this.path);
      if (!localFile.exists())
        localFile.mkdirs();
      localFile.listFiles();
      IOTools.deletePathFilesifMore(this.path, 0L);
      for (int i = 0; i < this.imageAd.getImageUrls().size(); i++)
      {
        String str = (String)this.imageAd.getImageUrls().get(i);
        imageDownloader(i, str, this.path + GeneralUtils.getImageNameFromUrl(str));
      }
    }
  }

  private void imageDownloader(final int paramInt, final String paramString1, final String paramString2)
  {
    new Thread()
    {
      public void run()
      {
        try
        {
          URLConnection localURLConnection = new URL(paramString1).openConnection();
          localURLConnection.getContentLength();
          localInputStream = localURLConnection.getInputStream();
          byte[] arrayOfByte = new byte[2048];
          File localFile = new File(paramString2);
          if (localFile.exists())
            localFile.delete();
          localFileOutputStream = new FileOutputStream(localFile);
          while (true)
          {
            int i = localInputStream.read(arrayOfByte);
            if (i == -1)
              break;
            localFileOutputStream.write(arrayOfByte, 0, i);
          }
        }
        catch (MalformedURLException localMalformedURLException)
        {
          InputStream localInputStream;
          FileOutputStream localFileOutputStream;
          localMalformedURLException.printStackTrace();
          return;
          localFileOutputStream.close();
          localInputStream.close();
          AdvertActivity.this.mLocalPathCache.put(Integer.valueOf(paramInt), paramString2);
          return;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
    }
    .start();
  }

  private void initview()
  {
    this.img_advert = ((ImageView)findViewById(2131165257));
    showDefaultPic();
  }

  private void showPic(String paramString)
  {
    dismissLoaddingDialog();
    if (this.mAdvertCache.containsKey(paramString))
      this.img_advert.setImageBitmap((Bitmap)this.mAdvertCache.get(paramString));
    while (true)
    {
      return;
      try
      {
        if (!TextUtils.isEmpty(paramString))
        {
          FileInputStream localFileInputStream = new FileInputStream(new File(paramString));
          Bitmap localBitmap = BitmapTools.decodeStream(localFileInputStream, Bitmap.Config.ARGB_8888, App.Operation(1280.0F), App.Operation(720.0F));
          localFileInputStream.close();
          if (localBitmap != null)
          {
            this.img_advert.setImageBitmap(localBitmap);
            this.mAdvertCache.put(paramString, localBitmap);
            return;
          }
        }
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
        return;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mLocalPathCache = new HashMap();
    this.mAdvertCache = new HashMap();
    this.imageAd = ((ImageAd)getIntent().getSerializableExtra("ImageAd"));
    setContentView(2130903052);
    initview();
    showLoaddingDialog();
    DownloadAdvert();
    final Handler localHandler = new Handler();
    localHandler.post(new Runnable()
    {
      public void run()
      {
        AdvertActivity.this.count = AdvertActivity.this.mLocalPathCache.size();
        if (AdvertActivity.this.count == 0)
        {
          AdvertActivity.this.showDefaultPic();
          if (AdvertActivity.this.time > 10)
            AdvertActivity.this.showDownloadPicFail();
        }
        do
        {
          return;
          AdvertActivity localAdvertActivity = AdvertActivity.this;
          localAdvertActivity.time = (1 + localAdvertActivity.time);
          localHandler.postDelayed(this, 1000L);
          return;
          AdvertActivity.this.showPic((String)AdvertActivity.this.mLocalPathCache.get(Integer.valueOf(AdvertActivity.this.index)));
          AdvertActivity.access$108(AdvertActivity.this);
          AdvertActivity.access$102(AdvertActivity.this, AdvertActivity.this.index % AdvertActivity.this.count);
        }
        while ("static".equalsIgnoreCase(AdvertActivity.this.imageAd.getAction()));
        localHandler.postDelayed(this, 1000 * AdvertActivity.this.imageAd.getInterval());
      }
    });
  }

  protected void onDestroy()
  {
    this.mLocalPathCache.clear();
    this.mAdvertCache.clear();
    super.onDestroy();
  }

  protected void showDefaultPic()
  {
    dismissLoaddingDialog();
    this.img_advert.setBackgroundColor(Color.parseColor("#000000"));
  }

  protected void showDownloadPicFail()
  {
    showErrorDialogWithReport(1, "1002005", "download ad pic failed.", "");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.AdvertActivity
 * JD-Core Version:    0.6.2
 */