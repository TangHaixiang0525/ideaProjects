package nochump.util.extend;

import nochump.util.zip.Crc32;

public class ZipCrypto
{
  private static long[] _Keys = { 305419896L, 591751049L, 878082192L };

  public static byte[] DecryptMessage(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    if (i >= paramInt)
      return arrayOfByte;
    short s = (byte)(MagicByte() ^ paramArrayOfByte[i]);
    if (s < 0)
    {
      UpdateKeys((short)(256 + (short)s));
      arrayOfByte[i] = ((byte)(short)(256 + (short)s));
    }
    while (true)
    {
      i++;
      break;
      UpdateKeys(s);
      arrayOfByte[i] = s;
    }
  }

  public static byte[] EncryptMessage(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    for (int i = 0; ; i++)
    {
      if (i >= paramInt)
        return arrayOfByte;
      short s = paramArrayOfByte[i];
      arrayOfByte[i] = ((byte)(paramArrayOfByte[i] ^ MagicByte()));
      UpdateKeys(s);
    }
  }

  public static void InitCipher(String paramString)
  {
    _Keys[0] = 305419896L;
    _Keys[1] = 591751049L;
    _Keys[2] = 878082192L;
    for (int i = 0; ; i++)
    {
      if (i >= paramString.length())
        return;
      UpdateKeys((byte)paramString.charAt(i));
    }
  }

  private static short MagicByte()
  {
    int i = (int)(0x2 | 0xFFFF & _Keys[2]);
    return (short)(i * (i ^ 0x1) >> 8);
  }

  private static void UpdateKeys(short paramShort)
  {
    _Keys[0] = Crc32.update(_Keys[0], paramShort);
    int i = (byte)(int)_Keys[0];
    if ((byte)(int)_Keys[0] < 0)
      i = (short)(i + 256);
    _Keys[1] += i;
    _Keys[1] = (134775813L * _Keys[1]);
    _Keys[1] = (1L + _Keys[1]);
    _Keys[2] = Crc32.update(_Keys[2], (byte)(int)(_Keys[1] >> 24));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.extend.ZipCrypto
 * JD-Core Version:    0.6.2
 */