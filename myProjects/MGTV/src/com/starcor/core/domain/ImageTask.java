package com.starcor.core.domain;

import android.graphics.Bitmap.Config;
import android.os.Handler;

public class ImageTask extends Task
{
  public static final int RESAMPLE_PIXEL_PROCESS = 4;
  public static final int ROUNDED_PROCESS = 1;
  public static final int SCALE_PROCESS = 2;
  private Handler handler;
  private int messageNumber;
  private Bitmap.Config outPixelFormat;
  private int process;
  private int scaledHeight;
  private int scaledWidth;
  private String url;

  public Handler getHandler()
  {
    return this.handler;
  }

  public int getMessageNumber()
  {
    return this.messageNumber;
  }

  public Bitmap.Config getOutPixelFormat()
  {
    return this.outPixelFormat;
  }

  public int getProcess()
  {
    return this.process;
  }

  public int getScaledHeight()
  {
    return this.scaledHeight;
  }

  public int getScaledWidth()
  {
    return this.scaledWidth;
  }

  public String getUrl()
  {
    return this.url;
  }

  public void setHandler(Handler paramHandler)
  {
    this.handler = paramHandler;
  }

  public void setMessageNumber(int paramInt)
  {
    this.messageNumber = paramInt;
  }

  public void setOutPixelFormat(Bitmap.Config paramConfig)
  {
    this.outPixelFormat = paramConfig;
  }

  public void setProcess(int paramInt)
  {
    this.process = paramInt;
  }

  public void setScaledHeight(int paramInt)
  {
    this.scaledHeight = paramInt;
  }

  public void setScaledWidth(int paramInt)
  {
    this.scaledWidth = paramInt;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ImageTask
 * JD-Core Version:    0.6.2
 */