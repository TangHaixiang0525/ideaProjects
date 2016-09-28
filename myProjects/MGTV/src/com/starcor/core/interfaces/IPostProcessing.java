package com.starcor.core.interfaces;

import android.graphics.Bitmap;
import java.io.File;

public abstract interface IPostProcessing
{
  public abstract Bitmap Process(Bitmap paramBitmap, File paramFile);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.interfaces.IPostProcessing
 * JD-Core Version:    0.6.2
 */