package u.aly;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class cc
{
  public static int a;

  public static String a(byte[] paramArrayOfByte, String paramString)
    throws UnsupportedEncodingException, DataFormatException
  {
    byte[] arrayOfByte = b(paramArrayOfByte);
    if (arrayOfByte != null)
      return new String(arrayOfByte, paramString);
    return null;
  }

  public static byte[] a(String paramString1, String paramString2)
    throws IOException
  {
    if (cd.d(paramString1))
      return null;
    return a(paramString1.getBytes(paramString2));
  }

  public static byte[] a(byte[] paramArrayOfByte)
    throws IOException
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
      return null;
    Deflater localDeflater = new Deflater();
    localDeflater.setInput(paramArrayOfByte);
    localDeflater.finish();
    byte[] arrayOfByte = new byte[8192];
    a = 0;
    try
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      try
      {
        while (true)
        {
          if (localDeflater.finished())
          {
            localDeflater.end();
            if (localByteArrayOutputStream != null)
              localByteArrayOutputStream.close();
            return localByteArrayOutputStream.toByteArray();
          }
          int i = localDeflater.deflate(arrayOfByte);
          a = i + a;
          localByteArrayOutputStream.write(arrayOfByte, 0, i);
        }
      }
      finally
      {
      }
      if (localByteArrayOutputStream != null)
        localByteArrayOutputStream.close();
      throw localObject1;
    }
    finally
    {
      Object localObject2 = localObject3;
      ByteArrayOutputStream localByteArrayOutputStream = null;
    }
  }

  public static byte[] b(byte[] paramArrayOfByte)
    throws UnsupportedEncodingException, DataFormatException
  {
    int i = 0;
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return null;
    Inflater localInflater = new Inflater();
    localInflater.setInput(paramArrayOfByte, 0, paramArrayOfByte.length);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      if (localInflater.needsInput())
      {
        localInflater.end();
        return localByteArrayOutputStream.toByteArray();
      }
      int j = localInflater.inflate(arrayOfByte);
      localByteArrayOutputStream.write(arrayOfByte, i, j);
      i += j;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.cc
 * JD-Core Version:    0.6.2
 */