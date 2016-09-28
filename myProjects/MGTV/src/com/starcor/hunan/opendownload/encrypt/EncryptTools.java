package com.starcor.hunan.opendownload.encrypt;

import android.util.Base64;
import com.starcor.core.utils.Logger;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptTools
{
  private static final String TAG = "EncryptLogic";

  public static int byte2int(byte[] paramArrayOfByte)
  {
    return 0xFF & paramArrayOfByte[0] | 0xFF00 & paramArrayOfByte[1] << 8;
  }

  public static String decryptByAes(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws Exception
  {
    try
    {
      Logger.i("EncryptLogic", "decryptByAes start!");
      Cipher localCipher = Cipher.getInstance("AES/ECB/NoPadding");
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(hexString2Bytes(paramString1), "AES");
      new IvParameterSpec(paramString2.getBytes());
      localCipher.init(2, localSecretKeySpec);
      String str = new String(localCipher.doFinal(paramArrayOfByte));
      Logger.i("EncryptLogic", "decryptByAes end!");
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.e("EncryptLogic", "decryptByAes error!, exception=" + localException.getMessage());
    }
    return null;
  }

  public static String decryptByRsa(byte[] paramArrayOfByte, PublicKey paramPublicKey)
  {
    try
    {
      Logger.i("EncryptLogic", "decryptByRsa start!");
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(2, paramPublicKey);
      String str = new String(localCipher.doFinal(paramArrayOfByte));
      Logger.i("EncryptLogic", "decryptByRsa end!");
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("EncryptLogic", "decryptByRsa error!, exception=" + localException.getMessage() + "\npublicKey=" + paramPublicKey);
    }
    return null;
  }

  public static byte[] encryptByAes(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    Logger.i("EncryptLogic", "encryptByAes begin!");
    try
    {
      Cipher localCipher = Cipher.getInstance("AES/ECB/NoPadding");
      int i = localCipher.getBlockSize();
      byte[] arrayOfByte1 = padString(paramString1).getBytes();
      int j = arrayOfByte1.length;
      if (j % i != 0)
        j += i - j % i;
      byte[] arrayOfByte2 = new byte[j];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(hexString2Bytes(paramString2), "AES");
      new IvParameterSpec(paramString3.getBytes());
      localCipher.init(1, localSecretKeySpec);
      byte[] arrayOfByte3 = localCipher.doFinal(arrayOfByte2);
      Logger.i("EncryptLogic", "encryptByAes end!");
      return arrayOfByte3;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.i("EncryptLogic", "encryptByAes error!, exception=" + localException.getMessage());
    }
    return new byte[0];
  }

  public static byte[] encryptByRsa(String paramString, PrivateKey paramPrivateKey)
  {
    try
    {
      Logger.i("EncryptLogic", "encryptByRsa start!");
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(1, paramPrivateKey);
      byte[] arrayOfByte = localCipher.doFinal(paramString.getBytes());
      Logger.i("EncryptLogic", "encryptByRsa end!");
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.e("EncryptLogic", "encryptByRsa error!, exception=" + localException.getMessage());
    }
    return new byte[0];
  }

  public static PrivateKey getPrivateKey(String paramString)
  {
    try
    {
      PKCS8EncodedKeySpec localPKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(paramString, 0));
      PrivateKey localPrivateKey = KeyFactory.getInstance("RSA", "BC").generatePrivate(localPKCS8EncodedKeySpec);
      return localPrivateKey;
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      localInvalidKeySpecException.printStackTrace();
      return null;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
        localNoSuchAlgorithmException.printStackTrace();
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      while (true)
        localNoSuchProviderException.printStackTrace();
    }
  }

  public static PublicKey getPublicKey(String paramString)
  {
    try
    {
      X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(paramString, 0));
      PublicKey localPublicKey = KeyFactory.getInstance("RSA").generatePublic(localX509EncodedKeySpec);
      return localPublicKey;
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      localInvalidKeySpecException.printStackTrace();
      return null;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
        localNoSuchAlgorithmException.printStackTrace();
    }
  }

  public static String getRandomString(int paramInt)
  {
    Random localRandom = new Random();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramInt; i++)
      localStringBuffer.append("abcdef0123456789".charAt(localRandom.nextInt("abcdef0123456789".length())));
    return localStringBuffer.toString();
  }

  public static byte[] hexString2Bytes(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    int i = 0;
    for (int j = 0; j < arrayOfByte.length; j++)
    {
      int k = i + 1;
      char c1 = paramString.charAt(i);
      i = k + 1;
      char c2 = paramString.charAt(k);
      arrayOfByte[j] = ((byte)(parse(c1) << 4 | parse(c2)));
    }
    return arrayOfByte;
  }

  public static void init()
  {
  }

  public static byte[] int2byteArray16(int paramInt)
  {
    byte[] arrayOfByte = new byte[2];
    arrayOfByte[0] = ((byte)(paramInt >>> 8));
    arrayOfByte[1] = ((byte)paramInt);
    return arrayOfByte;
  }

  public static String md5(String paramString)
  {
    StringBuilder localStringBuilder;
    try
    {
      byte[] arrayOfByte = MessageDigest.getInstance("MD5").digest(paramString.getBytes("UTF-8"));
      localStringBuilder = new StringBuilder(2 * arrayOfByte.length);
      int i = arrayOfByte.length;
      for (int j = 0; j < i; j++)
      {
        int k = arrayOfByte[j];
        if ((k & 0xFF) < 16)
          localStringBuilder.append("0");
        localStringBuilder.append(Integer.toHexString(k & 0xFF));
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException("Huh, MD5 should be supported?", localNoSuchAlgorithmException);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("Huh, UTF-8 should be supported?", localUnsupportedEncodingException);
    }
    return localStringBuilder.toString();
  }

  private static String padString(String paramString)
  {
    int i = 16 - paramString.length() % 16;
    for (int j = 0; j < i; j++)
      paramString = paramString + ' ';
    return paramString;
  }

  private static int parse(char paramChar)
  {
    if (paramChar >= 'a')
      return 0xF & 10 + (paramChar - 'a');
    if (paramChar >= 'A')
      return 0xF & 10 + (paramChar - 'A');
    return 0xF & paramChar - '0';
  }

  public static byte[] sha1WithRsa(String paramString, PrivateKey paramPrivateKey)
  {
    try
    {
      Signature localSignature = Signature.getInstance("SHA1withRSA");
      localSignature.initSign(paramPrivateKey);
      localSignature.update(paramString.getBytes());
      byte[] arrayOfByte = localSignature.sign();
      return arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
      return null;
    }
    catch (SignatureException localSignatureException)
    {
      while (true)
        localSignatureException.printStackTrace();
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      while (true)
        localInvalidKeyException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.encrypt.EncryptTools
 * JD-Core Version:    0.6.2
 */