package com.starcor.data.processor;

import com.starcor.data.model.DataModel;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface IProcessor
{
  public abstract void generateData(OutputStream paramOutputStream, DataModel paramDataModel);

  public abstract void parseData(InputStream paramInputStream, DataModel paramDataModel);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.processor.IProcessor
 * JD-Core Version:    0.6.2
 */