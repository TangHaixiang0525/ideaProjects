package com.starcor.cache.naming;

import com.starcor.utils.Logger;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5FileNameGenerator
  implements FileNameGenerator
{
  private static final String HASH_ALGORITHM = "MD5";
  private static final int RADIX = 36;

  private byte[] getMD5(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      arrayOfByte1 = arrayOfByte2;
      return arrayOfByte1;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      do
        byte[] arrayOfByte1 = null;
      while (localNoSuchAlgorithmException == null);
      Logger.e("", localNoSuchAlgorithmException.getMessage());
    }
    return null;
  }

  public String generate(String paramString)
  {
    return new BigInteger(getMD5(paramString.getBytes())).abs().toString(36);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.naming.Md5FileNameGenerator
 * JD-Core Version:    0.6.2
 */