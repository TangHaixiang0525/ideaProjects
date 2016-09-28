package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Locale;

public class GifAnimationMetaData
  implements Serializable, Parcelable
{
  public static final Parcelable.Creator<GifAnimationMetaData> CREATOR = new Parcelable.Creator()
  {
    public GifAnimationMetaData createFromParcel(Parcel paramAnonymousParcel)
    {
      return new GifAnimationMetaData(paramAnonymousParcel, null);
    }

    public GifAnimationMetaData[] newArray(int paramAnonymousInt)
    {
      return new GifAnimationMetaData[paramAnonymousInt];
    }
  };
  private static final long serialVersionUID = 5692363926580237325L;
  private final int mDuration;
  private final int mHeight;
  private final int mImageCount;
  private final int mLoopCount;
  private final int mWidth;

  public GifAnimationMetaData(@Nullable ContentResolver paramContentResolver, @NonNull Uri paramUri)
    throws IOException
  {
    this(GifInfoHandle.openUri(paramContentResolver, paramUri, true));
  }

  public GifAnimationMetaData(@NonNull AssetFileDescriptor paramAssetFileDescriptor)
    throws IOException
  {
    this(GifInfoHandle.openAssetFileDescriptor(paramAssetFileDescriptor, true));
  }

  public GifAnimationMetaData(@NonNull AssetManager paramAssetManager, @NonNull String paramString)
    throws IOException
  {
    this(paramAssetManager.openFd(paramString));
  }

  public GifAnimationMetaData(@NonNull Resources paramResources, int paramInt)
    throws Resources.NotFoundException, IOException
  {
    this(paramResources.openRawResourceFd(paramInt));
  }

  private GifAnimationMetaData(Parcel paramParcel)
  {
    this.mLoopCount = paramParcel.readInt();
    this.mDuration = paramParcel.readInt();
    this.mHeight = paramParcel.readInt();
    this.mWidth = paramParcel.readInt();
    this.mImageCount = paramParcel.readInt();
  }

  public GifAnimationMetaData(@NonNull File paramFile)
    throws IOException
  {
    this(GifInfoHandle.openFile(paramFile.getPath(), true));
  }

  public GifAnimationMetaData(@NonNull FileDescriptor paramFileDescriptor)
    throws IOException
  {
    this(GifInfoHandle.openFd(paramFileDescriptor, 0L, true));
  }

  public GifAnimationMetaData(@NonNull InputStream paramInputStream)
    throws IOException
  {
    this(GifInfoHandle.openMarkableInputStream(paramInputStream, true));
  }

  public GifAnimationMetaData(@NonNull String paramString)
    throws IOException
  {
    this(GifInfoHandle.openFile(paramString, true));
  }

  public GifAnimationMetaData(@NonNull ByteBuffer paramByteBuffer)
    throws IOException
  {
    this(GifInfoHandle.openDirectByteBuffer(paramByteBuffer, true));
  }

  private GifAnimationMetaData(GifInfoHandle paramGifInfoHandle)
  {
    this.mLoopCount = paramGifInfoHandle.getLoopCount();
    this.mDuration = paramGifInfoHandle.getDuration();
    paramGifInfoHandle.recycle();
    this.mWidth = paramGifInfoHandle.width;
    this.mHeight = paramGifInfoHandle.height;
    this.mImageCount = paramGifInfoHandle.frameCount;
  }

  public GifAnimationMetaData(@NonNull byte[] paramArrayOfByte)
    throws IOException
  {
    this(GifInfoHandle.openByteArray(paramArrayOfByte, true));
  }

  public int describeContents()
  {
    return 0;
  }

  public int getDuration()
  {
    return this.mDuration;
  }

  public int getHeight()
  {
    return this.mHeight;
  }

  public int getLoopCount()
  {
    return this.mLoopCount;
  }

  public int getNumberOfFrames()
  {
    return this.mImageCount;
  }

  public int getWidth()
  {
    return this.mWidth;
  }

  public boolean isAnimated()
  {
    return (this.mImageCount > 1) && (this.mDuration > 0);
  }

  public String toString()
  {
    if (this.mLoopCount == 0);
    for (String str1 = "Infinity"; ; str1 = Integer.toString(this.mLoopCount))
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = Integer.valueOf(this.mWidth);
      arrayOfObject[1] = Integer.valueOf(this.mHeight);
      arrayOfObject[2] = Integer.valueOf(this.mImageCount);
      arrayOfObject[3] = str1;
      arrayOfObject[4] = Integer.valueOf(this.mDuration);
      String str2 = String.format(localLocale, "GIF: size: %dx%d, frames: %d, loops: %s, duration: %d", arrayOfObject);
      if (isAnimated())
        str2 = "Animated " + str2;
      return str2;
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mLoopCount);
    paramParcel.writeInt(this.mDuration);
    paramParcel.writeInt(this.mHeight);
    paramParcel.writeInt(this.mWidth);
    paramParcel.writeInt(this.mImageCount);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifAnimationMetaData
 * JD-Core Version:    0.6.2
 */