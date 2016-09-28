package pl.droidsonroids.gif;

import android.support.annotation.NonNull;
import java.io.IOException;

public class GifIOException extends IOException
{
  private static final long serialVersionUID = 13038402904505L;

  @NonNull
  public final GifError reason;

  GifIOException(int paramInt)
  {
    this(GifError.fromCode(paramInt));
  }

  private GifIOException(@NonNull GifError paramGifError)
  {
    super(paramGifError.getFormattedDescription());
    this.reason = paramGifError;
  }

  static GifIOException fromCode(int paramInt)
  {
    if (paramInt == GifError.NO_ERROR.errorCode)
      return null;
    return new GifIOException(paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifIOException
 * JD-Core Version:    0.6.2
 */