package com.sohu.applist.c.a;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class b
{
  public static String a(String paramString)
  {
    MessageDigest localMessageDigest = null;
    StringBuffer localStringBuffer;
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(paramString.getBytes("UTF-8"));
      arrayOfByte = localMessageDigest.digest();
      localStringBuffer = new StringBuffer();
      for (i = 0; ; i++)
      {
        if (i >= arrayOfByte.length)
          break label137;
        if (Integer.toHexString(0xFF & arrayOfByte[i]).length() != 1)
          break;
        localStringBuffer.append("0").append(Integer.toHexString(0xFF & arrayOfByte[i]));
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
      {
        System.out.println("NoSuchAlgorithmException caught!");
        System.exit(-1);
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
      {
        byte[] arrayOfByte;
        int i;
        localUnsupportedEncodingException.printStackTrace();
        continue;
        localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
      }
    }
    label137: return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.applist.c.a.b
 * JD-Core Version:    0.6.2
 */