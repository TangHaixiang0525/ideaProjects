package pl.droidsonroids.gif;

import android.support.annotation.NonNull;
import java.util.Locale;

public enum GifError
{

  @NonNull
  public final String description;
  int errorCode;

  static
  {
    NOT_GIF_FILE = new GifError("NOT_GIF_FILE", 3, 103, "Data is not in GIF format");
    NO_SCRN_DSCR = new GifError("NO_SCRN_DSCR", 4, 104, "No screen descriptor detected");
    NO_IMAG_DSCR = new GifError("NO_IMAG_DSCR", 5, 105, "No image descriptor detected");
    NO_COLOR_MAP = new GifError("NO_COLOR_MAP", 6, 106, "Neither global nor local color map found");
    WRONG_RECORD = new GifError("WRONG_RECORD", 7, 107, "Wrong record type detected");
    DATA_TOO_BIG = new GifError("DATA_TOO_BIG", 8, 108, "Number of pixels bigger than width * height");
    NOT_ENOUGH_MEM = new GifError("NOT_ENOUGH_MEM", 9, 109, "Failed to allocate required memory");
    CLOSE_FAILED = new GifError("CLOSE_FAILED", 10, 110, "Failed to close given input");
    NOT_READABLE = new GifError("NOT_READABLE", 11, 111, "Given file was not opened for read");
    IMAGE_DEFECT = new GifError("IMAGE_DEFECT", 12, 112, "Image is defective, decoding aborted");
    EOF_TOO_SOON = new GifError("EOF_TOO_SOON", 13, 113, "Image EOF detected before image complete");
    NO_FRAMES = new GifError("NO_FRAMES", 14, 1000, "No frames found, at least one frame required");
    INVALID_SCR_DIMS = new GifError("INVALID_SCR_DIMS", 15, 1001, "Invalid screen size, dimensions must be positive");
    INVALID_IMG_DIMS = new GifError("INVALID_IMG_DIMS", 16, 1002, "Invalid image size, dimensions must be positive");
    IMG_NOT_CONFINED = new GifError("IMG_NOT_CONFINED", 17, 1003, "Image size exceeds screen size");
    REWIND_FAILED = new GifError("REWIND_FAILED", 18, 1004, "Input source rewind has failed, animation is stopped");
    INVALID_BYTE_BUFFER = new GifError("INVALID_BYTE_BUFFER", 19, 1005, "Invalid and/or indirect byte buffer specified");
    UNKNOWN = new GifError("UNKNOWN", 20, -1, "Unknown error");
    GifError[] arrayOfGifError = new GifError[21];
    arrayOfGifError[0] = NO_ERROR;
    arrayOfGifError[1] = OPEN_FAILED;
    arrayOfGifError[2] = READ_FAILED;
    arrayOfGifError[3] = NOT_GIF_FILE;
    arrayOfGifError[4] = NO_SCRN_DSCR;
    arrayOfGifError[5] = NO_IMAG_DSCR;
    arrayOfGifError[6] = NO_COLOR_MAP;
    arrayOfGifError[7] = WRONG_RECORD;
    arrayOfGifError[8] = DATA_TOO_BIG;
    arrayOfGifError[9] = NOT_ENOUGH_MEM;
    arrayOfGifError[10] = CLOSE_FAILED;
    arrayOfGifError[11] = NOT_READABLE;
    arrayOfGifError[12] = IMAGE_DEFECT;
    arrayOfGifError[13] = EOF_TOO_SOON;
    arrayOfGifError[14] = NO_FRAMES;
    arrayOfGifError[15] = INVALID_SCR_DIMS;
    arrayOfGifError[16] = INVALID_IMG_DIMS;
    arrayOfGifError[17] = IMG_NOT_CONFINED;
    arrayOfGifError[18] = REWIND_FAILED;
    arrayOfGifError[19] = INVALID_BYTE_BUFFER;
    arrayOfGifError[20] = UNKNOWN;
  }

  private GifError(int paramInt, @NonNull String paramString)
  {
    this.errorCode = paramInt;
    this.description = paramString;
  }

  static GifError fromCode(int paramInt)
  {
    for (GifError localGifError2 : values())
      if (localGifError2.errorCode == paramInt)
        return localGifError2;
    GifError localGifError1 = UNKNOWN;
    localGifError1.errorCode = paramInt;
    return localGifError1;
  }

  public int getErrorCode()
  {
    return this.errorCode;
  }

  String getFormattedDescription()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.errorCode);
    arrayOfObject[1] = this.description;
    return String.format(localLocale, "GifError %d: %s", arrayOfObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.GifError
 * JD-Core Version:    0.6.2
 */