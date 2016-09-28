package com.xiaomi.account.openauth;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.xiaomi.account.openauth.utils.Base64Coder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class AuthorizeHelper
{
  private static final String HMAC_SHA1 = "HmacSHA1";
  public static final String OAUTH2_HOST;
  public static final boolean USE_PREVIEW = false;
  private static final String UTF8 = "UTF-8";
  private static Random random;

  static
  {
    if (USE_PREVIEW);
    for (String str = "http://account.preview.n.xiaomi.net"; ; str = "https://account.xiaomi.com")
    {
      OAUTH2_HOST = str;
      random = new Random();
      return;
    }
  }

  protected static HashMap<String, String> buildMacRequestHead(String paramString1, String paramString2, String paramString3)
    throws UnsupportedEncodingException
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = URLEncoder.encode(paramString1, "UTF-8");
    arrayOfObject[1] = URLEncoder.encode(paramString2, "UTF-8");
    arrayOfObject[2] = URLEncoder.encode(paramString3, "UTF-8");
    String str = String.format("MAC access_token=\"%s\", nonce=\"%s\",mac=\"%s\"", arrayOfObject);
    HashMap localHashMap = new HashMap();
    localHashMap.put("Authorization", str);
    return localHashMap;
  }

  protected static String encodeSign(byte[] paramArrayOfByte)
  {
    return new String(Base64Coder.encode(paramArrayOfByte));
  }

  protected static byte[] encryptHMACSha1(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte2, "HmacSHA1");
    Mac localMac = Mac.getInstance("HmacSHA1");
    localMac.init(localSecretKeySpec);
    localMac.update(paramArrayOfByte1);
    return localMac.doFinal();
  }

  protected static String generateNonce()
  {
    long l = random.nextLong();
    int i = (int)(System.currentTimeMillis() / 60000L);
    return l + ":" + i;
  }

  protected static String generateUrl(String paramString, List<NameValuePair> paramList)
  {
    String str = paramString;
    if ((paramList != null) && (paramList.size() > 0))
    {
      Uri.Builder localBuilder = Uri.parse(str).buildUpon();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        NameValuePair localNameValuePair = (NameValuePair)localIterator.next();
        localBuilder.appendQueryParameter(localNameValuePair.getName(), localNameValuePair.getValue());
      }
      str = localBuilder.build().toString();
    }
    return str;
  }

  protected static String getMacAccessTokenSignatureString(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
    throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException
  {
    if ("HmacSHA1".equalsIgnoreCase(paramString7))
    {
      StringBuilder localStringBuilder = new StringBuilder("");
      localStringBuilder.append(paramString1 + "\n");
      localStringBuilder.append(paramString2.toUpperCase() + "\n");
      localStringBuilder.append(paramString3 + "\n");
      localStringBuilder.append(paramString4 + "\n");
      if (!TextUtils.isEmpty(paramString5))
      {
        StringBuffer localStringBuffer = new StringBuffer();
        ArrayList localArrayList = new ArrayList();
        URLEncodedUtils.parse(localArrayList, new Scanner(paramString5), "UTF-8");
        Collections.sort(localArrayList, new Comparator()
        {
          public int compare(NameValuePair paramAnonymousNameValuePair1, NameValuePair paramAnonymousNameValuePair2)
          {
            return paramAnonymousNameValuePair1.getName().compareTo(paramAnonymousNameValuePair2.getName());
          }
        });
        localStringBuffer.append(URLEncodedUtils.format(localArrayList, "UTF-8"));
        localStringBuilder.append(localStringBuffer.toString() + "\n");
      }
      return encodeSign(encryptHMACSha1(localStringBuilder.toString().getBytes("UTF-8"), paramString6.getBytes("UTF-8")));
    }
    throw new NoSuchAlgorithmException("error mac algorithm : " + paramString7);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.AuthorizeHelper
 * JD-Core Version:    0.6.2
 */