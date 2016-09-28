package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Surface;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

final class GifInfoHandle
{
  static final GifInfoHandle NULL_INFO = new GifInfoHandle(0L, 0, 0, 0);
  final int frameCount;
  private volatile long gifInfoPtr;
  final int height;
  final int width;

  static
  {
    System.loadLibrary("pl_All_imgoTV_droidsonroids_gif");
  }

  private GifInfoHandle(long paramLong, int paramInt1, int paramInt2, int paramInt3)
  {
    this.gifInfoPtr = paramLong;
    this.width = paramInt1;
    this.height = paramInt2;
    this.frameCount = paramInt3;
  }

  private static native void bindSurface(long paramLong, Surface paramSurface, long[] paramArrayOfLong, boolean paramBoolean);

  private static native void free(long paramLong);

  private static native long getAllocationByteCount(long paramLong);

  private static native String getComment(long paramLong);

  private static native int getCurrentFrameIndex(long paramLong);

  private static native int getCurrentLoop(long paramLong);

  private static native int getCurrentPosition(long paramLong);

  private static native int getDuration(long paramLong);

  private static native int getFrameDuration(long paramLong, int paramInt);

  private static native int getLoopCount(long paramLong);

  private static native int getNativeErrorCode(long paramLong);

  private static native long[] getSavedState(long paramLong);

  private static native long getSourceLength(long paramLong);

  private static native boolean isAnimationCompleted(long paramLong);

  static GifInfoHandle openAssetFileDescriptor(AssetFileDescriptor paramAssetFileDescriptor, boolean paramBoolean)
    throws IOException
  {
    try
    {
      GifInfoHandle localGifInfoHandle = openFd(paramAssetFileDescriptor.getFileDescriptor(), paramAssetFileDescriptor.getStartOffset(), paramBoolean);
      return localGifInfoHandle;
    }
    finally
    {
      paramAssetFileDescriptor.close();
    }
  }

  static native GifInfoHandle openByteArray(byte[] paramArrayOfByte, boolean paramBoolean)
    throws GifIOException;

  static native GifInfoHandle openDirectByteBuffer(ByteBuffer paramByteBuffer, boolean paramBoolean)
    throws GifIOException;

  static native GifInfoHandle openFd(FileDescriptor paramFileDescriptor, long paramLong, boolean paramBoolean)
    throws GifIOException;

  static native GifInfoHandle openFile(String paramString, boolean paramBoolean)
    throws GifIOException;

  static GifInfoHandle openMarkableInputStream(InputStream paramInputStream, boolean paramBoolean)
    throws GifIOException
  {
    if (!paramInputStream.markSupported())
      throw new IllegalArgumentException("InputStream does not support marking");
    return openStream(paramInputStream, paramBoolean);
  }

  static native GifInfoHandle openStream(InputStream paramInputStream, boolean paramBoolean)
    throws GifIOException;

  static GifInfoHandle openUri(ContentResolver paramContentResolver, Uri paramUri, boolean paramBoolean)
    throws IOException
  {
    if ("file".equals(paramUri.getScheme()))
      return openFile(paramUri.getPath(), paramBoolean);
    return openAssetFileDescriptor(paramContentResolver.openAssetFileDescriptor(paramUri, "r"), paramBoolean);
  }

  private static native void postUnbindSurface(long paramLong);

  private static native long renderFrame(long paramLong, Bitmap paramBitmap);

  private static native boolean reset(long paramLong);

  private static native long restoreRemainder(long paramLong);

  private static native int restoreSavedState(long paramLong, long[] paramArrayOfLong, Bitmap paramBitmap);

  private static native void saveRemainder(long paramLong);

  private static native void seekToFrame(long paramLong, int paramInt, Bitmap paramBitmap);

  private static native void seekToTime(long paramLong, int paramInt, Bitmap paramBitmap);

  private static native void setLoopCount(long paramLong, int paramInt);

  private static native void setSpeedFactor(long paramLong, float paramFloat);

  void bindSurface(Surface paramSurface, long[] paramArrayOfLong, boolean paramBoolean)
  {
    bindSurface(this.gifInfoPtr, paramSurface, paramArrayOfLong, paramBoolean);
  }

  protected void finalize()
    throws Throwable
  {
    try
    {
      recycle();
      return;
    }
    finally
    {
      super.finalize();
    }
  }

  long getAllocationByteCount()
  {
    try
    {
      long l = getAllocationByteCount(this.gifInfoPtr);
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  String getComment()
  {
    try
    {
      String str = getComment(this.gifInfoPtr);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int getCurrentFrameIndex()
  {
    try
    {
      int i = getCurrentFrameIndex(this.gifInfoPtr);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int getCurrentLoop()
  {
    try
    {
      int i = getCurrentLoop(this.gifInfoPtr);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int getCurrentPosition()
  {
    try
    {
      int i = getCurrentPosition(this.gifInfoPtr);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int getDuration()
  {
    try
    {
      int i = getDuration(this.gifInfoPtr);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  int getFrameDuration(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: iflt +11 -> 14
    //   6: iload_1
    //   7: aload_0
    //   8: getfield 38	pl/droidsonroids/gif/GifInfoHandle:frameCount	I
    //   11: if_icmplt +18 -> 29
    //   14: new 178	java/lang/IndexOutOfBoundsException
    //   17: dup
    //   18: ldc 180
    //   20: invokespecial 181	java/lang/IndexOutOfBoundsException:<init>	(Ljava/lang/String;)V
    //   23: athrow
    //   24: astore_2
    //   25: aload_0
    //   26: monitorexit
    //   27: aload_2
    //   28: athrow
    //   29: aload_0
    //   30: getfield 32	pl/droidsonroids/gif/GifInfoHandle:gifInfoPtr	J
    //   33: iload_1
    //   34: invokestatic 183	pl/droidsonroids/gif/GifInfoHandle:getFrameDuration	(JI)I
    //   37: istore_3
    //   38: aload_0
    //   39: monitorexit
    //   40: iload_3
    //   41: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   6	14	24	finally
    //   14	24	24	finally
    //   29	38	24	finally
  }

  int getLoopCount()
  {
    try
    {
      int i = getLoopCount(this.gifInfoPtr);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int getNativeErrorCode()
  {
    try
    {
      int i = getNativeErrorCode(this.gifInfoPtr);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  long[] getSavedState()
  {
    try
    {
      long[] arrayOfLong = getSavedState(this.gifInfoPtr);
      return arrayOfLong;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  long getSourceLength()
  {
    try
    {
      long l = getSourceLength(this.gifInfoPtr);
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  boolean isAnimationCompleted()
  {
    try
    {
      boolean bool = isAnimationCompleted(this.gifInfoPtr);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  boolean isRecycled()
  {
    try
    {
      long l = this.gifInfoPtr;
      if (l == 0L)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  void postUnbindSurface()
  {
    try
    {
      postUnbindSurface(this.gifInfoPtr);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void recycle()
  {
    try
    {
      free(this.gifInfoPtr);
      this.gifInfoPtr = 0L;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  long renderFrame(Bitmap paramBitmap)
  {
    try
    {
      long l = renderFrame(this.gifInfoPtr, paramBitmap);
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  boolean reset()
  {
    try
    {
      boolean bool = reset(this.gifInfoPtr);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  long restoreRemainder()
  {
    try
    {
      long l = restoreRemainder(this.gifInfoPtr);
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int restoreSavedState(long[] paramArrayOfLong, Bitmap paramBitmap)
  {
    try
    {
      int i = restoreSavedState(this.gifInfoPtr, paramArrayOfLong, paramBitmap);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void saveRemainder()
  {
    try
    {
      saveRemainder(this.gifInfoPtr);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void seekToFrame(int paramInt, Bitmap paramBitmap)
  {
    try
    {
      seekToFrame(this.gifInfoPtr, paramInt, paramBitmap);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void seekToTime(int paramInt, Bitmap paramBitmap)
  {
    try
    {
      seekToTime(this.gifInfoPtr, paramInt, paramBitmap);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void setLoopCount(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 65535))
      throw new IllegalArgumentException("Loop count of range <0, 65535>");
    try
    {
      setLoopCount(this.gifInfoPtr, paramInt);
      return;
    }
    finally
    {
    }
  }

  void setSpeedFactor(float paramFloat)
  {
    if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat)))
      throw new IllegalArgumentException("Speed factor is not positive");
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    try
    {
      setSpeedFactor(this.gifInfoPtr, paramFloat);
      return;
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifInfoHandle
 * JD-Core Version:    0.6.2
 */