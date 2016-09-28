package com.intertrust.wasabi.media;

public class MediaInfo
{
  private int bitrate;
  private int duration;
  private EncryptionMethod encMethod;
  private Format format;
  private Type type;

  MediaInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.type = Type.values()[paramInt1];
    this.format = Format.values()[paramInt2];
    this.encMethod = EncryptionMethod.values()[paramInt3];
    this.duration = paramInt4;
    this.bitrate = paramInt5;
  }

  protected MediaInfo(Type paramType, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.type = paramType;
    this.format = Format.values()[paramInt1];
    this.encMethod = EncryptionMethod.values()[paramInt2];
    this.duration = paramInt3;
    this.bitrate = paramInt4;
  }

  public int getBitrate()
  {
    return this.bitrate;
  }

  public int getDuration()
  {
    return this.duration;
  }

  public EncryptionMethod getEncryptionMethod()
  {
    return this.encMethod;
  }

  public Format getFormat()
  {
    return this.format;
  }

  public Type getType()
  {
    return this.type;
  }

  public static enum EncryptionMethod
  {
    static
    {
      NULL = new EncryptionMethod("NULL", 1);
      AES_128_CBC = new EncryptionMethod("AES_128_CBC", 2);
      AES_128_CTR = new EncryptionMethod("AES_128_CTR", 3);
      EncryptionMethod[] arrayOfEncryptionMethod = new EncryptionMethod[4];
      arrayOfEncryptionMethod[0] = UNKNOWN;
      arrayOfEncryptionMethod[1] = NULL;
      arrayOfEncryptionMethod[2] = AES_128_CBC;
      arrayOfEncryptionMethod[3] = AES_128_CTR;
    }
  }

  public static enum Format
  {
    static
    {
      AAC = new Format("AAC", 1);
      AVC = new Format("AVC", 2);
      MP3 = new Format("MP3", 3);
      MPEG4_VID = new Format("MPEG4_VID", 4);
      Format[] arrayOfFormat = new Format[5];
      arrayOfFormat[0] = UNKNOWN;
      arrayOfFormat[1] = AAC;
      arrayOfFormat[2] = AVC;
      arrayOfFormat[3] = MP3;
      arrayOfFormat[4] = MPEG4_VID;
    }
  }

  public static enum Type
  {
    static
    {
      AUDIO = new Type("AUDIO", 1);
      VIDEO = new Type("VIDEO", 2);
      Type[] arrayOfType = new Type[3];
      arrayOfType[0] = UNKNOWN;
      arrayOfType[1] = AUDIO;
      arrayOfType[2] = VIDEO;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.MediaInfo
 * JD-Core Version:    0.6.2
 */