package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GifDrawableBuilder
{
  private ScheduledThreadPoolExecutor mExecutor;
  private InputSource mInputSource;
  private boolean mIsRenderingTriggeredOnDraw = true;
  private GifDrawable mOldDrawable;

  public GifDrawable build()
    throws IOException
  {
    if (this.mInputSource == null)
      throw new NullPointerException("Source is not set");
    return this.mInputSource.build(this.mOldDrawable, this.mExecutor, this.mIsRenderingTriggeredOnDraw);
  }

  public GifDrawableBuilder from(ContentResolver paramContentResolver, Uri paramUri)
  {
    this.mInputSource = new InputSource.UriSource(paramContentResolver, paramUri);
    return this;
  }

  public GifDrawableBuilder from(AssetFileDescriptor paramAssetFileDescriptor)
  {
    this.mInputSource = new InputSource.AssetFileDescriptorSource(paramAssetFileDescriptor);
    return this;
  }

  public GifDrawableBuilder from(AssetManager paramAssetManager, String paramString)
  {
    this.mInputSource = new InputSource.AssetSource(paramAssetManager, paramString);
    return this;
  }

  public GifDrawableBuilder from(Resources paramResources, int paramInt)
  {
    this.mInputSource = new InputSource.ResourcesSource(paramResources, paramInt);
    return this;
  }

  public GifDrawableBuilder from(File paramFile)
  {
    this.mInputSource = new InputSource.FileSource(paramFile);
    return this;
  }

  public GifDrawableBuilder from(FileDescriptor paramFileDescriptor)
  {
    this.mInputSource = new InputSource.FileDescriptorSource(paramFileDescriptor);
    return this;
  }

  public GifDrawableBuilder from(InputStream paramInputStream)
  {
    this.mInputSource = new InputSource.InputStreamSource(paramInputStream);
    return this;
  }

  public GifDrawableBuilder from(String paramString)
  {
    this.mInputSource = new InputSource.FileSource(paramString);
    return this;
  }

  public GifDrawableBuilder from(ByteBuffer paramByteBuffer)
  {
    this.mInputSource = new InputSource.DirectByteBufferSource(paramByteBuffer);
    return this;
  }

  public GifDrawableBuilder from(byte[] paramArrayOfByte)
  {
    this.mInputSource = new InputSource.ByteArraySource(paramArrayOfByte);
    return this;
  }

  public GifDrawableBuilder setRenderingTriggeredOnDraw(boolean paramBoolean)
  {
    this.mIsRenderingTriggeredOnDraw = paramBoolean;
    return this;
  }

  public GifDrawableBuilder taskExecutor(ScheduledThreadPoolExecutor paramScheduledThreadPoolExecutor)
  {
    this.mExecutor = paramScheduledThreadPoolExecutor;
    return this;
  }

  public GifDrawableBuilder threadPoolSize(int paramInt)
  {
    this.mExecutor = new ScheduledThreadPoolExecutor(paramInt);
    return this;
  }

  public GifDrawableBuilder with(GifDrawable paramGifDrawable)
  {
    this.mOldDrawable = paramGifDrawable;
    return this;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifDrawableBuilder
 * JD-Core Version:    0.6.2
 */