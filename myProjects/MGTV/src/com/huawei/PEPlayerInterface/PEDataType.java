package com.huawei.PEPlayerInterface;

public enum PEDataType
{
  static
  {
    PE_DATA_TYPE_AUDIO_FRAME = new PEDataType("PE_DATA_TYPE_AUDIO_FRAME", 1);
    PEDataType[] arrayOfPEDataType = new PEDataType[2];
    arrayOfPEDataType[0] = PE_DATA_TYPE_VIDEO_FRAME;
    arrayOfPEDataType[1] = PE_DATA_TYPE_AUDIO_FRAME;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.PEPlayerInterface.PEDataType
 * JD-Core Version:    0.6.2
 */