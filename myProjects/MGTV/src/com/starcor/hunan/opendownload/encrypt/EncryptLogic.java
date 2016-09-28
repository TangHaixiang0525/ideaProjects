package com.starcor.hunan.opendownload.encrypt;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.starcor.core.domain.GetSecretKeysInfo;
import com.starcor.core.domain.GetSecretKeysInfo.ApiEncrypt;
import com.starcor.core.domain.GetSecretKeysInfo.SecretKeys;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.encrypt.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;

public class EncryptLogic
{
  private static final String TAG = EncryptLogic.class.getSimpleName();
  static Set<EncryptApiData> encryptApiDatas = new HashSet();
  static Set<Integer> invaildCodeList = new HashSet();

  static
  {
    addEncryptApi("n41_a_1", "1,2", "1,2");
  }

  private static void addEncryptApi(String paramString1, String paramString2, String paramString3)
  {
    EncryptApiData localEncryptApiData = new EncryptApiData();
    localEncryptApiData.apiName = paramString1;
    localEncryptApiData.encryptTypes = paramString2;
    localEncryptApiData.encryptType = randomType(paramString2);
    localEncryptApiData.decryptTypes = paramString3;
    localEncryptApiData.decryptType = randomType(paramString3);
    encryptApiDatas.add(localEncryptApiData);
    Logger.d(TAG, "addEncryptApi--->" + localEncryptApiData.toString());
  }

  public static void addEncryptData(GetSecretKeysInfo paramGetSecretKeysInfo)
  {
    synchronized (encryptApiDatas)
    {
      encryptApiDatas.clear();
      Iterator localIterator1 = paramGetSecretKeysInfo.apiEncrypts.iterator();
      if (localIterator1.hasNext())
      {
        GetSecretKeysInfo.ApiEncrypt localApiEncrypt = (GetSecretKeysInfo.ApiEncrypt)localIterator1.next();
        addEncryptApi(localApiEncrypt.sign, localApiEncrypt.requestEncryptMode, localApiEncrypt.responseEncryptMode);
      }
    }
    Iterator localIterator2 = paramGetSecretKeysInfo.secretKeys.iterator();
    while (localIterator2.hasNext())
    {
      GetSecretKeysInfo.SecretKeys localSecretKeys = (GetSecretKeysInfo.SecretKeys)localIterator2.next();
      Integer localInteger = Integer.valueOf(localSecretKeys.sign);
      String str2 = filterKey(localSecretKeys.requestEncryptKey);
      String str3 = filterKey(localSecretKeys.responseEncryptKey);
      RsaKeyPairs.keyMap.put(localInteger, Pair.create(str2, str3));
    }
    List localList = Arrays.asList(paramGetSecretKeysInfo.validKeyGroup.split(","));
    ArrayList localArrayList = new ArrayList(Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
    Iterator localIterator3 = paramGetSecretKeysInfo.secretKeys.iterator();
    while (localIterator3.hasNext())
      localArrayList.add(((GetSecretKeysInfo.SecretKeys)localIterator3.next()).sign);
    Iterator localIterator4 = localArrayList.iterator();
    while (localIterator4.hasNext())
    {
      String str1 = (String)localIterator4.next();
      if (!localList.contains(str1))
        invaildCodeList.add(Integer.valueOf(Integer.parseInt(str1)));
    }
    Logger.d(TAG, "validKeyGroup--->" + paramGetSecretKeysInfo.validKeyGroup + ", allCodes=" + localArrayList.toString() + ", invaildCodeList=" + invaildCodeList.toString());
  }

  private static String buildAesArgs()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("mode", 0);
      localJSONObject.put("bits", 128);
      localJSONObject.put("init", EncryptTools.getRandomString(32));
      localJSONObject.put("pass", EncryptTools.getRandomString(32));
      return localJSONObject.toString();
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  private static String buildCodec(EncryptApiData paramEncryptApiData, String paramString)
  {
    return paramEncryptApiData.encryptType + "0" + paramString + paramEncryptApiData.decryptType + "0" + randomKeyCodec();
  }

  private static String buildPackArgs(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    int i = paramArrayOfByte1[0];
    paramArrayOfByte1[0] = paramArrayOfByte1[1];
    paramArrayOfByte1[1] = i;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1 + paramArrayOfByte1.length + paramArrayOfByte2.length + paramArrayOfByte3.length);
    localByteArrayOutputStream.write(0);
    localByteArrayOutputStream.write(paramArrayOfByte1, 0, paramArrayOfByte1.length);
    localByteArrayOutputStream.write(paramArrayOfByte2, 0, paramArrayOfByte2.length);
    localByteArrayOutputStream.write(paramArrayOfByte3, 0, paramArrayOfByte3.length);
    return Base64.encodeToString(localByteArrayOutputStream.toByteArray(), 8);
  }

  private static String buildUrl(String paramString1, String paramString2, String paramString3, String paramString4, ArrayList<String> paramArrayList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getPrefix(paramString1));
    Iterator localIterator1 = paramArrayList.iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      if (str2.contains("nns_func"))
        localStringBuilder.append("&").append(str2);
    }
    localStringBuilder.append("&nns_hash=").append(paramString2);
    localStringBuilder.append("&nns_codec=").append(paramString3);
    Iterator localIterator2 = paramArrayList.iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      if (str1.contains("nns_output_type"))
        localStringBuilder.append("&").append(str1);
    }
    localStringBuilder.append("&nns_pack=").append(paramString4);
    return localStringBuilder.toString().replace("?&", "?").replace("\n", "").trim();
  }

  private static boolean checkKeyVaild()
  {
    return RsaKeyPairs.keyMap.size() != invaildCodeList.size();
  }

  public static byte[] decode(byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    if (!checkKeyVaild())
      Logger.e(TAG, "decode url, no vaild key!!!, url=" + paramString1);
    long l1;
    String str;
    EncryptApiData localEncryptApiData;
    do
    {
      return paramArrayOfByte;
      if (TextUtils.isEmpty(paramString2))
      {
        Logger.e(TAG, "decode url, the TaskName is null!!!, url=" + paramString1);
        return paramArrayOfByte;
      }
      l1 = System.currentTimeMillis();
      str = paramString2.replace("-", "_").toLowerCase();
      localEncryptApiData = getEncryptApiData(str);
    }
    while ((localEncryptApiData == null) || ("0".equals(localEncryptApiData.decryptType)));
    byte[] arrayOfByte;
    if ("1".equals(localEncryptApiData.decryptType))
      arrayOfByte = decodeBySha1WithRsa(paramArrayOfByte, str).getBytes();
    while (true)
    {
      Logger.i(TAG, "decode url = " + paramString1);
      long l2 = System.currentTimeMillis() - l1;
      Logger.i(TAG, "decodeApiName:" + str + ", 解密方式：" + localEncryptApiData.decryptType + ", 解密耗时：" + l2 + "毫秒");
      return arrayOfByte;
      if ("2".equals(localEncryptApiData.decryptType))
      {
        arrayOfByte = decodeByAesAndRsa(paramArrayOfByte, paramString1, str).getBytes();
      }
      else
      {
        Logger.e(TAG, "error decode type ---> " + localEncryptApiData.decryptType);
        arrayOfByte = paramArrayOfByte;
      }
    }
  }

  public static String decodeByAesAndRsa(byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    Logger.d(TAG, "decodeByAesAndRsa start");
    byte[] arrayOfByte1 = Base64.decode(paramArrayOfByte, 0);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte1);
    localByteArrayInputStream.read();
    byte[] arrayOfByte2 = new byte[2];
    localByteArrayInputStream.read(arrayOfByte2, 0, arrayOfByte2.length);
    int i = EncryptTools.byte2int(arrayOfByte2);
    byte[] arrayOfByte3 = new byte[i];
    localByteArrayInputStream.read(arrayOfByte3, 0, i);
    PublicKey localPublicKey = generatePublicKey(paramString1);
    String str1 = EncryptTools.decryptByRsa(arrayOfByte3, localPublicKey);
    if (TextUtils.isEmpty(str1))
    {
      Logger.e(TAG, "--------------------------->ERROR<---------------------------");
      Logger.e(TAG, "url=" + paramString1);
      Logger.e(TAG, "decodeApiName=" + paramString2);
      Logger.e(TAG, "publicKey=" + localPublicKey);
      Logger.e(TAG, "dataStr=" + new String(paramArrayOfByte));
    }
    Logger.d(TAG, "aesLength=" + i + ", aesArgs=" + str1);
    int j = -1 + (arrayOfByte1.length - arrayOfByte3.length - arrayOfByte2.length);
    byte[] arrayOfByte4 = new byte[j];
    localByteArrayInputStream.read(arrayOfByte4, 0, j);
    Object localObject = "";
    try
    {
      String str2 = EncryptTools.decryptByAes(arrayOfByte4, getArgsFromJson(str1, "pass"), getArgsFromJson(str1, "init")).replace("", "");
      localObject = str2;
      Logger.d(TAG, "decodeByAesAndRsa end, apiName=" + paramString2 + ", oriDataStr=\n" + (String)localObject);
      return localObject;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static String decodeBySha1WithRsa(byte[] paramArrayOfByte, String paramString)
  {
    Logger.d(TAG, "decodeBySha1WithRsa start");
    byte[] arrayOfByte1 = Base64.decode(paramArrayOfByte, 0);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte1);
    localByteArrayInputStream.read();
    byte[] arrayOfByte2 = new byte[2];
    localByteArrayInputStream.read(arrayOfByte2, 0, arrayOfByte2.length);
    int i = EncryptTools.byte2int(arrayOfByte2);
    Logger.d(TAG, "decodeBySha1WithRsa, sha1Length=" + i);
    byte[] arrayOfByte3 = new byte[i];
    localByteArrayInputStream.read(arrayOfByte3, 0, i);
    new String(arrayOfByte3);
    int j = -1 + (-2 + (arrayOfByte1.length - i));
    byte[] arrayOfByte4 = new byte[j];
    localByteArrayInputStream.read(arrayOfByte4, 0, j);
    String str = new String(arrayOfByte4);
    Logger.d(TAG, "decodeBySha1WithRsa end, apiName=" + paramString + ", oriDataStr=\n" + str);
    return str;
  }

  public static String encode(String paramString1, String paramString2)
  {
    if (!checkKeyVaild())
      Logger.e(TAG, "encode url, no vaild key!!!, url=" + paramString1);
    long l1;
    String str1;
    EncryptApiData localEncryptApiData;
    do
    {
      return paramString1;
      if (TextUtils.isEmpty(paramString2))
      {
        Logger.e(TAG, "encode url, the TaskName is null!!!, url=" + paramString1);
        return paramString1;
      }
      l1 = System.currentTimeMillis();
      str1 = paramString2.replace("-", "_").toLowerCase();
      localEncryptApiData = getEncryptApiData(str1);
    }
    while (localEncryptApiData == null);
    String str2;
    if ("0".equals(localEncryptApiData.encryptType))
    {
      String str3 = buildCodec(localEncryptApiData, randomKeyCodec());
      str2 = paramString1 + "&nns_codec=" + str3;
    }
    while (true)
    {
      long l2 = System.currentTimeMillis() - l1;
      Logger.i(TAG, "encodeApiName:" + str1 + ", 加密方式：" + localEncryptApiData.encryptType + ", 加密耗时：" + l2 + "毫秒" + "\n---------------加密前的url start-------------\n" + paramString1 + "\n---------------加密前的url end-------------\n" + "\n---------------加密后的url start-------------\n" + str2 + "\n---------------加密后的url end-------------\n");
      return str2;
      if ("1".equals(localEncryptApiData.encryptType))
      {
        str2 = encodeBySha1WithRsa(paramString1, localEncryptApiData);
      }
      else if ("2".equals(localEncryptApiData.encryptType))
      {
        str2 = encodeByAesAndRsa(paramString1, localEncryptApiData);
      }
      else
      {
        str2 = paramString1;
        Logger.e(TAG, "error encode type ---> " + localEncryptApiData.encryptType);
      }
    }
  }

  public static String encodeByAesAndRsa(String paramString, EncryptApiData paramEncryptApiData)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = getHashJsonString(paramString);
    String str2 = getPackJsonString(paramString, localArrayList);
    Logger.d(TAG, "RSA+AES, hashJson=" + str1);
    Logger.d(TAG, "RSA+AES, packJson=" + str2);
    String str3 = buildAesArgs();
    Logger.d(TAG, "RSA+AES, aesArgs=" + str3);
    String str4 = randomKeyCodec();
    String str5 = getPrivateKeyString(str4);
    PrivateKey localPrivateKey = EncryptTools.getPrivateKey(str5);
    if (localPrivateKey == null)
    {
      Logger.e(TAG, "RSA+SHA1, getPrivateKey is null, keyCodec=" + str4 + ", privateKeyString=\n" + str5);
      return paramString;
    }
    byte[] arrayOfByte1 = new byte[0];
    try
    {
      arrayOfByte1 = EncryptTools.encryptByRsa(str3, localPrivateKey);
      String str11 = Base64.encodeToString(arrayOfByte1, 0).replace("\n", "");
      Logger.d(TAG, "RSA+AES, aesArgsRsaData=" + str11);
      arrayOfByte2 = new byte[0];
    }
    catch (Exception localException1)
    {
      try
      {
        byte[] arrayOfByte2 = EncryptTools.encryptByAes(str2, getArgsFromJson(str3, "pass"), getArgsFromJson(str3, "init"));
        String str10 = Base64.encodeToString(arrayOfByte2, 0).replace("\n", "");
        Logger.d(TAG, "RSA+AES, packAesData=" + str10);
        String str6 = buildPackArgs(EncryptTools.int2byteArray16(arrayOfByte1.length), arrayOfByte1, arrayOfByte2);
        String str7 = EncryptTools.md5(str1);
        String str8 = buildCodec(paramEncryptApiData, str4);
        String str9 = buildUrl(paramString, str7, str8, str6, localArrayList);
        Logger.d(TAG, "RSA+AES encrypt, codec=" + str8 + " url=" + str9);
        return str9;
        localException1 = localException1;
        localException1.printStackTrace();
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }
  }

  public static String encodeBySha1WithRsa(String paramString, EncryptApiData paramEncryptApiData)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = getHashJsonString(paramString);
    String str2 = getPackJsonString(paramString, localArrayList);
    Logger.d(TAG, "RSA+SHA1, hashJson=" + str1);
    Logger.d(TAG, "RSA+SHA1, packJson=" + str2);
    String str3 = randomKeyCodec();
    String str4 = getPrivateKeyString(str3);
    PrivateKey localPrivateKey = EncryptTools.getPrivateKey(str4);
    if (localPrivateKey == null)
    {
      Logger.e(TAG, "RSA+SHA1, getPrivateKey is null, keyCodec=" + str3 + ", privateKeyString=\n" + str4);
      return paramString;
    }
    byte[] arrayOfByte = EncryptTools.sha1WithRsa(str2, localPrivateKey);
    String str5 = buildPackArgs(EncryptTools.int2byteArray16(arrayOfByte.length), arrayOfByte, str2.getBytes());
    String str6 = EncryptTools.md5(str1);
    String str7 = buildCodec(paramEncryptApiData, str3);
    String str8 = buildUrl(paramString, str6, str7, str5, localArrayList);
    Logger.d(TAG, "RSA+SHA1 encrypt, codec=" + str7 + " url=" + str8);
    return str8;
  }

  private static String filterKey(String paramString)
  {
    String str1 = new String(Base64.decode(paramString, 0));
    String str2 = str1.substring(0, -1 + str1.length());
    return str2.substring(1 + str2.indexOf("\n"), str2.lastIndexOf("\n"));
  }

  private static PublicKey generatePublicKey(String paramString)
  {
    Iterator localIterator = URLEncodedUtils.parse(URI.create(paramString), "utf-8").iterator();
    NameValuePair localNameValuePair;
    do
    {
      boolean bool = localIterator.hasNext();
      str1 = null;
      if (!bool)
        break;
      localNameValuePair = (NameValuePair)localIterator.next();
    }
    while ((!localNameValuePair.getName().equals("Codec")) && (!localNameValuePair.getName().equals("nns_codec")));
    String str4 = localNameValuePair.getValue();
    String str1 = str4.substring(-2 + str4.length(), str4.length());
    if (TextUtils.isEmpty(str1))
    {
      int i = 9 + paramString.indexOf("--Codec__");
      String str3 = paramString.substring(i, i + 8);
      str1 = str3.substring(-2 + str3.length(), str3.length());
    }
    Integer localInteger = Integer.valueOf(str1);
    String str2 = (String)((Pair)RsaKeyPairs.keyMap.get(localInteger)).second;
    PublicKey localPublicKey = EncryptTools.getPublicKey(str2);
    if (localPublicKey == null)
      Logger.e(TAG, "generatePublicKey, publicKey is null, url=" + paramString + ", codec=" + localInteger + ", publicKeyString=\n" + str2);
    return localPublicKey;
  }

  private static String getArgsFromJson(String paramString1, String paramString2)
  {
    try
    {
      String str = new JSONObject(paramString1).getString(paramString2);
      return str;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return "";
  }

  private static EncryptApiData getEncryptApiData(String paramString)
  {
    synchronized (encryptApiDatas)
    {
      Iterator localIterator = encryptApiDatas.iterator();
      EncryptApiData localEncryptApiData;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject2 = null;
        if (!bool)
          break;
        localEncryptApiData = (EncryptApiData)localIterator.next();
      }
      while (!localEncryptApiData.apiName.equalsIgnoreCase(paramString));
      Object localObject2 = localEncryptApiData;
      return localObject2;
    }
  }

  private static String getHashJsonString(String paramString)
  {
    TreeMap localTreeMap = new TreeMap();
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator1 = URLEncodedUtils.parse(URI.create(paramString), "utf-8").iterator();
    while (localIterator1.hasNext())
    {
      NameValuePair localNameValuePair = (NameValuePair)localIterator1.next();
      if (!EncryptConfig.noEncryptionArgs.contains(localNameValuePair.getName()))
      {
        String str3 = localNameValuePair.getName();
        if (TextUtils.isEmpty(localNameValuePair.getValue()));
        for (String str4 = ""; ; str4 = localNameValuePair.getValue())
        {
          localTreeMap.put(str3, str4);
          break;
        }
      }
    }
    Iterator localIterator2 = localTreeMap.keySet().iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      String str2 = (String)localTreeMap.get(str1);
      try
      {
        localJSONObject.put(str1, str2);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return localJSONObject.toString();
  }

  private static String getPackJsonString(String paramString, ArrayList<String> paramArrayList)
  {
    TreeMap localTreeMap = new TreeMap();
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator1 = URLEncodedUtils.parse(URI.create(paramString), "utf-8").iterator();
    while (localIterator1.hasNext())
    {
      NameValuePair localNameValuePair = (NameValuePair)localIterator1.next();
      if (EncryptConfig.retainArgsName.contains(localNameValuePair.getName()))
      {
        paramArrayList.add(localNameValuePair.toString());
      }
      else
      {
        String str3 = localNameValuePair.getName();
        if (TextUtils.isEmpty(localNameValuePair.getValue()));
        for (String str4 = ""; ; str4 = localNameValuePair.getValue())
        {
          localTreeMap.put(str3, str4);
          break;
        }
      }
    }
    Iterator localIterator2 = localTreeMap.keySet().iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      String str2 = (String)localTreeMap.get(str1);
      try
      {
        localJSONObject.put(str1, str2);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return localJSONObject.toString();
  }

  private static String getPrefix(String paramString)
  {
    URI localURI = URI.create(paramString);
    String str1 = localURI.getHost();
    String str2 = localURI.getPath();
    String str3 = localURI.getScheme();
    return str3 + "://" + str1 + str2 + "?";
  }

  private static String getPrivateKeyString(String paramString)
  {
    Integer localInteger = Integer.valueOf(paramString);
    Pair localPair = (Pair)RsaKeyPairs.keyMap.get(localInteger);
    ((String)localPair.first);
    return (String)localPair.first;
  }

  private static String randomKeyCodec()
  {
    ArrayList localArrayList = new ArrayList(RsaKeyPairs.keyMap.keySet());
    Iterator localIterator = invaildCodeList.iterator();
    while (localIterator.hasNext())
      localArrayList.remove((Integer)localIterator.next());
    int i = ((Integer)localArrayList.get(new Random().nextInt(localArrayList.size()))).intValue();
    if (i < 10)
      return "0" + i;
    return String.valueOf(i);
  }

  private static String randomType(String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    return arrayOfString[new Random().nextInt(arrayOfString.length)];
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.encrypt.EncryptLogic
 * JD-Core Version:    0.6.2
 */