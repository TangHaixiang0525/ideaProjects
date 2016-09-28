package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class InputSource
{
  private boolean mIsOpaque;

  final GifDrawable build(GifDrawable paramGifDrawable, ScheduledThreadPoolExecutor paramScheduledThreadPoolExecutor, boolean paramBoolean)
    throws IOException
  {
    return new GifDrawable(open(), paramGifDrawable, paramScheduledThreadPoolExecutor, paramBoolean);
  }

  final boolean isOpaque()
  {
    return this.mIsOpaque;
  }

  abstract GifInfoHandle open()
    throws IOException;

  final InputSource setOpaque(boolean paramBoolean)
  {
    this.mIsOpaque = paramBoolean;
    return this;
  }

  public static class AssetFileDescriptorSource extends InputSource
  {
    private final AssetFileDescriptor mAssetFileDescriptor;

    public AssetFileDescriptorSource(@NonNull AssetFileDescriptor paramAssetFileDescriptor)
    {
      this.mAssetFileDescriptor = paramAssetFileDescriptor;
    }

    GifInfoHandle open()
      throws IOException
    {
      return GifInfoHandle.openAssetFileDescriptor(this.mAssetFileDescriptor, false);
    }
  }

  public static final class AssetSource extends InputSource
  {
    private final AssetManager mAssetManager;
    private final String mAssetName;

    public AssetSource(@NonNull AssetManager paramAssetManager, @NonNull String paramString)
    {
      this.mAssetManager = paramAssetManager;
      this.mAssetName = paramString;
    }

    GifInfoHandle open()
      throws IOException
    {
      return GifInfoHandle.openAssetFileDescriptor(this.mAssetManager.openFd(this.mAssetName), false);
    }
  }

  public static final class ByteArraySource extends InputSource
  {
    private final byte[] bytes;

    public ByteArraySource(@NonNull byte[] paramArrayOfByte)
    {
      this.bytes = paramArrayOfByte;
    }

    GifInfoHandle open()
      throws GifIOException
    {
      return GifInfoHandle.openByteArray(this.bytes, false);
    }
  }

  public static final class DirectByteBufferSource extends InputSource
  {
    private final ByteBuffer byteBuffer;

    public DirectByteBufferSource(@NonNull ByteBuffer paramByteBuffer)
    {
      this.byteBuffer = paramByteBuffer;
    }

    GifInfoHandle open()
      throws GifIOException
    {
      return GifInfoHandle.openDirectByteBuffer(this.byteBuffer, false);
    }
  }

  public static final class FileDescriptorSource extends InputSource
  {
    private final FileDescriptor mFd;

    public FileDescriptorSource(@NonNull FileDescriptor paramFileDescriptor)
    {
      this.mFd = paramFileDescriptor;
    }

    GifInfoHandle open()
      throws IOException
    {
      return GifInfoHandle.openFd(this.mFd, 0L, false);
    }
  }

  public static final class FileSource extends InputSource
  {
    private final String mPath;

    public FileSource(@NonNull File paramFile)
    {
      this.mPath = paramFile.getPath();
    }

    public FileSource(@NonNull String paramString)
    {
      this.mPath = paramString;
    }

    GifInfoHandle open()
      throws GifIOException
    {
      return GifInfoHandle.openFile(this.mPath, false);
    }
  }

  public static final class InputStreamSource extends InputSource
  {
    private final InputStream inputStream;

    public InputStreamSource(@NonNull InputStream paramInputStream)
    {
      this.inputStream = paramInputStream;
    }

    GifInfoHandle open()
      throws IOException
    {
      return GifInfoHandle.openMarkableInputStream(this.inputStream, false);
    }
  }

  public static class ResourcesSource extends InputSource
  {
    private final int mResourceId;
    private final Resources mResources;

    public ResourcesSource(@NonNull Resources paramResources, int paramInt)
    {
      this.mResources = paramResources;
      this.mResourceId = paramInt;
    }

    GifInfoHandle open()
      throws IOException
    {
      return GifInfoHandle.openAssetFileDescriptor(this.mResources.openRawResourceFd(this.mResourceId), false);
    }
  }

  public static final class UriSource extends InputSource
  {
    private final ContentResolver mContentResolver;
    private final Uri mUri;

    public UriSource(@Nullable ContentResolver paramContentResolver, @NonNull Uri paramUri)
    {
      this.mContentResolver = paramContentResolver;
      this.mUri = paramUri;
    }

    GifInfoHandle open()
      throws IOException
    {
      return GifInfoHandle.openUri(this.mContentResolver, this.mUri, false);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.InputSource
 * JD-Core Version:    0.6.2
 */