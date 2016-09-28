package com.sohu.logger.common;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.util.MD5Utils;
import com.sohu.logger.util.NetUtils;
import com.sohu.logger.util.PrefUtil;
import com.sohu.logger.util.SystemUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.util.UUID;

public class DeviceConstants
{
  private static final String APPID = "1074";
  public static final String CONFIG = "config";
  public static final int GID_GEN_TYPE_DEVICE = 1;
  public static final int GID_GEN_TYPE_LOCAL_SAVED = 2;
  public static final int GID_GEN_TYPE_RANDOM = 0;
  private static final String MODELTYPE = "ffff";
  private static final String OSTYPE = "02";
  public static final String PREF_KEY_NEW_GID = "new_tv_gid";
  public static final String PREF_KEY_NEW_UID = "uid_v3";
  public static final String PREF_KEY_UID_BEFORE_V3 = "uid_before_v3";
  public static final String PREF_KEY_WIFI_MAC = "wifi_mac";
  private static final String REGEX_ONE_MORE_ZERO = "0+";
  private static final String TAG = "DeviceConstants";
  public static final int UID_GEN_TYPE_DEVICE = 1;
  public static final int UID_GEN_TYPE_LOCAL_SAVED = 2;
  public static final int UID_GEN_TYPE_RANDOM = 0;
  private static final String UNKNOWN_HW_SERIALNO = "unknown";
  private static DeviceConstants mInstance;
  private boolean isSupportH265 = false;
  public String mAppVersion;
  private Context mContext;
  public String mDeviceName;
  private String mGID;
  private int mGenType = 0;
  private int mGidGenType;
  public String mMac;
  private String mManufacturer;
  public String mNetType;
  public String mPID;
  private String mScreen;
  public String mSystemVersion;
  private String mUID;
  private String mUIDBeforeV3;
  public int mVersionCode = -1;

  private String checkDeviceParam(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    return paramString.trim().replaceAll(" ", "");
  }

  private String checkImeiParam(String paramString)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramString.length() < 15))
      return "";
    return paramString.trim().replaceAll(" ", "");
  }

  private String checkMacParam(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    return paramString.trim().replaceAll(":", "");
  }

  private String checkMacParamIsValid(String paramString)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramString.startsWith("00:00:00")))
      return "";
    return paramString.trim().replaceAll(":", "");
  }

  private void generateUIDBeforeV3(Context paramContext)
  {
    String str1 = PrefUtil.getString(paramContext, "uid_before_v3", "");
    if (!TextUtils.isEmpty(str1))
    {
      OutputLog.i("DeviceConstants", "local saved uid:" + str1);
      this.mUIDBeforeV3 = str1;
      return;
    }
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    String str3;
    String str2;
    if (localTelephonyManager != null)
    {
      str3 = localTelephonyManager.getDeviceId();
      str2 = localTelephonyManager.getSubscriberId();
    }
    while (true)
    {
      String str4 = getCPUSerialNumber();
      String str5 = getHWSerialNumber(paramContext);
      String str6 = NetUtils.getLocalMacAddress(paramContext);
      String str7 = checkDeviceParam(str3) + checkDeviceParam(str2) + checkDeviceParam(str4) + checkDeviceParam(str5) + checkDeviceParam(str6);
      if (!TextUtils.isEmpty(str7));
      for (String str8 = MD5Utils.crypt(str7); ; str8 = MD5Utils.crypt(UUID.randomUUID().toString()))
      {
        this.mUIDBeforeV3 = str8;
        PrefUtil.putString(paramContext, "uid_before_v3", this.mUIDBeforeV3);
        OutputLog.i("DeviceConstants", "imei:" + str3 + ",\nimsi:" + str2 + ",\ncpusn:" + str4 + ",\nhwsn:" + str5 + ",\nresult:" + str7 + ",\nuid:" + str8);
        return;
      }
      str2 = null;
      str3 = null;
    }
  }

  // ERROR //
  private String getCPUSerialNumber()
  {
    // Byte code:
    //   0: ldc 88
    //   2: astore_1
    //   3: new 205	java/io/FileReader
    //   6: dup
    //   7: ldc 207
    //   9: invokespecial 210	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   12: astore_2
    //   13: new 212	java/io/BufferedReader
    //   16: dup
    //   17: aload_2
    //   18: invokespecial 215	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   21: astore_3
    //   22: aload_3
    //   23: invokevirtual 218	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   26: astore 9
    //   28: aload 9
    //   30: ifnull +24 -> 54
    //   33: aload 9
    //   35: ldc 220
    //   37: invokevirtual 224	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   40: iconst_m1
    //   41: if_icmple +78 -> 119
    //   44: aload 9
    //   46: ldc 226
    //   48: invokevirtual 230	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   51: iconst_1
    //   52: aaload
    //   53: astore_1
    //   54: iconst_0
    //   55: ifeq +7 -> 62
    //   58: aconst_null
    //   59: invokevirtual 235	java/io/InputStream:close	()V
    //   62: iconst_0
    //   63: ifeq +7 -> 70
    //   66: aconst_null
    //   67: invokevirtual 240	java/lang/Process:destroy	()V
    //   70: aload_2
    //   71: ifnull +7 -> 78
    //   74: aload_2
    //   75: invokevirtual 241	java/io/FileReader:close	()V
    //   78: aload_3
    //   79: ifnull +7 -> 86
    //   82: aload_3
    //   83: invokevirtual 242	java/io/BufferedReader:close	()V
    //   86: aload_1
    //   87: ldc 244
    //   89: ldc 88
    //   91: invokevirtual 248	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   94: ldc 250
    //   96: ldc 88
    //   98: invokevirtual 248	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   101: astore 8
    //   103: aload 8
    //   105: ldc 39
    //   107: invokevirtual 253	java/lang/String:matches	(Ljava/lang/String;)Z
    //   110: ifeq +6 -> 116
    //   113: aconst_null
    //   114: astore 8
    //   116: aload 8
    //   118: areturn
    //   119: aload_3
    //   120: invokevirtual 218	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   123: astore 10
    //   125: aload 10
    //   127: astore 9
    //   129: goto -101 -> 28
    //   132: astore 11
    //   134: aload 11
    //   136: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   139: goto -53 -> 86
    //   142: astore 4
    //   144: aconst_null
    //   145: astore_3
    //   146: aconst_null
    //   147: astore_2
    //   148: aload 4
    //   150: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   153: iconst_0
    //   154: ifeq +7 -> 161
    //   157: aconst_null
    //   158: invokevirtual 235	java/io/InputStream:close	()V
    //   161: iconst_0
    //   162: ifeq +7 -> 169
    //   165: aconst_null
    //   166: invokevirtual 240	java/lang/Process:destroy	()V
    //   169: aload_2
    //   170: ifnull +7 -> 177
    //   173: aload_2
    //   174: invokevirtual 241	java/io/FileReader:close	()V
    //   177: aload_3
    //   178: ifnull -92 -> 86
    //   181: aload_3
    //   182: invokevirtual 242	java/io/BufferedReader:close	()V
    //   185: goto -99 -> 86
    //   188: astore 7
    //   190: aload 7
    //   192: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   195: goto -109 -> 86
    //   198: astore 5
    //   200: aconst_null
    //   201: astore_3
    //   202: aconst_null
    //   203: astore_2
    //   204: iconst_0
    //   205: ifeq +7 -> 212
    //   208: aconst_null
    //   209: invokevirtual 235	java/io/InputStream:close	()V
    //   212: iconst_0
    //   213: ifeq +7 -> 220
    //   216: aconst_null
    //   217: invokevirtual 240	java/lang/Process:destroy	()V
    //   220: aload_2
    //   221: ifnull +7 -> 228
    //   224: aload_2
    //   225: invokevirtual 241	java/io/FileReader:close	()V
    //   228: aload_3
    //   229: ifnull +7 -> 236
    //   232: aload_3
    //   233: invokevirtual 242	java/io/BufferedReader:close	()V
    //   236: aload 5
    //   238: athrow
    //   239: astore 6
    //   241: aload 6
    //   243: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   246: goto -10 -> 236
    //   249: astore 5
    //   251: aconst_null
    //   252: astore_3
    //   253: goto -49 -> 204
    //   256: astore 5
    //   258: goto -54 -> 204
    //   261: astore 4
    //   263: aconst_null
    //   264: astore_3
    //   265: goto -117 -> 148
    //   268: astore 4
    //   270: goto -122 -> 148
    //
    // Exception table:
    //   from	to	target	type
    //   58	62	132	java/io/IOException
    //   66	70	132	java/io/IOException
    //   74	78	132	java/io/IOException
    //   82	86	132	java/io/IOException
    //   3	13	142	java/io/IOException
    //   157	161	188	java/io/IOException
    //   165	169	188	java/io/IOException
    //   173	177	188	java/io/IOException
    //   181	185	188	java/io/IOException
    //   3	13	198	finally
    //   208	212	239	java/io/IOException
    //   216	220	239	java/io/IOException
    //   224	228	239	java/io/IOException
    //   232	236	239	java/io/IOException
    //   13	22	249	finally
    //   22	28	256	finally
    //   33	54	256	finally
    //   119	125	256	finally
    //   148	153	256	finally
    //   13	22	261	java/io/IOException
    //   22	28	268	java/io/IOException
    //   33	54	268	java/io/IOException
    //   119	125	268	java/io/IOException
  }

  private Context getContext()
  {
    return this.mContext;
  }

  public static String getDeviceId(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null)
    {
      OutputLog.e("[NetTools|getDeviceId]Error, cannot get TelephonyManager!");
      return null;
    }
    return localTelephonyManager.getDeviceId();
  }

  public static String getGid(Context paramContext)
  {
    return PrefUtil.getString(paramContext, "config", "new_tv_gid", "");
  }

  private String getHWSerialNumber(Context paramContext)
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      localObject = (String)localClass.getMethod("get", new Class[] { String.class, String.class }).invoke(localClass, new Object[] { "ro.serialno", "unknown" });
    }
    catch (Exception localException1)
    {
      try
      {
        Object localObject;
        String str1;
        if (!"unknown".equals(localObject))
        {
          String[] arrayOfString = Build.MODEL.replaceAll(" ", "").split("-");
          str1 = arrayOfString[(-1 + arrayOfString.length)];
          int i = str1.length();
          int j = ((String)localObject).length();
          if (i + j <= 20)
            break label163;
          String str2 = str1.substring(0, 20 - j) + (String)localObject;
          localObject = str2;
        }
        while (true)
        {
          label150: if ("unknown".equals(localObject))
            localObject = null;
          return localObject;
          label163: String str3 = str1 + (String)localObject;
          localObject = str3;
          continue;
          localException1 = localException1;
          localObject = null;
        }
      }
      catch (Exception localException2)
      {
        break label150;
      }
    }
  }

  public static DeviceConstants getInstance()
  {
    try
    {
      if (mInstance == null)
        mInstance = new DeviceConstants();
      DeviceConstants localDeviceConstants = mInstance;
      return localDeviceConstants;
    }
    finally
    {
    }
  }

  public static String getUid(Context paramContext)
  {
    return PrefUtil.getString(paramContext, "config", "uid_v3", "");
  }

  public static String getWifiMac(Context paramContext)
  {
    return PrefUtil.getString(paramContext, "config", "wifi_mac", "");
  }

  private void init(Context paramContext)
  {
    setContext(paramContext);
    Context localContext = getContext();
    generateUID(localContext);
    generateUIDBeforeV3(localContext);
    generateGID(localContext);
    this.mPID = SystemUtils.getDeviceId(localContext);
    this.mMac = NetUtils.getLocalMacAddress(localContext);
    OutputLog.d("DeviceConstants", "mMac:" + this.mMac);
    this.mDeviceName = SystemUtils.getBuildModel();
    this.mManufacturer = SystemUtils.getBuildManufacturer();
    this.mAppVersion = AppConstants.getInstance().getSver();
    this.mSystemVersion = SystemUtils.getBuildVersionRelease();
    this.mScreen = (SystemUtils.getWidthXHeight(localContext) + "_" + SystemUtils.getDensityDpi(localContext));
  }

  public static void initInstance(Context paramContext)
  {
    getInstance().init(paramContext);
  }

  private void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static void setGid(Context paramContext, String paramString)
  {
    PrefUtil.putString(paramContext, "config", "new_tv_gid", paramString);
  }

  public static void setUid(Context paramContext, String paramString)
  {
    PrefUtil.putString(paramContext, "config", "uid_v3", paramString);
  }

  public static void setWifiMac(Context paramContext, String paramString)
  {
    PrefUtil.putString(paramContext, "config", "wifi_mac", paramString);
  }

  public void generateGID(Context paramContext)
  {
    String str1 = getGid(paramContext);
    if (!TextUtils.isEmpty(str1))
    {
      OutputLog.i("DeviceConstants", "local saved gid:" + str1);
      this.mGID = str1;
      setGidGenType(2);
    }
    char[] arrayOfChar = { 48, 48, 48, 48 };
    String str2 = SystemUtils.getDeviceId(paramContext);
    String str3 = SystemUtils.getSubscriberId(paramContext);
    String str4 = getWifiMacAddress(paramContext);
    if (!TextUtils.isEmpty(checkDeviceParam(str2)))
      arrayOfChar[0] = '1';
    if (!TextUtils.isEmpty(checkDeviceParam(str3)))
      arrayOfChar[1] = '1';
    if (!TextUtils.isEmpty(checkMacParam(str4)))
      arrayOfChar[2] = '1';
    String str5 = checkDeviceParam(str2) + checkDeviceParam(str3) + checkMacParam(str4);
    String str6;
    if (!TextUtils.isEmpty(str5))
    {
      arrayOfChar[3] = '0';
      str6 = "02ffff1074" + String.valueOf(arrayOfChar) + MD5Utils.getMD5Lower(str5);
      setGidGenType(1);
    }
    while (true)
    {
      this.mGID = str6;
      setGid(paramContext, this.mGID);
      OutputLog.i("DeviceConstants", "imei:" + str2 + ",\nimsi:" + str3 + ",\nmac:" + str4 + ",\nmask:" + String.valueOf(arrayOfChar) + ",\nresult:" + str5 + ",\ngid:" + str6);
      return;
      arrayOfChar[3] = '1';
      str6 = "02ffff1074" + String.valueOf(arrayOfChar) + MD5Utils.getMD5Lower(UUID.randomUUID().toString());
      setGidGenType(0);
    }
  }

  public void generateUID(Context paramContext)
  {
    String str1 = getUid(paramContext);
    if (!TextUtils.isEmpty(str1))
    {
      this.mUID = str1;
      setGenType(2);
      return;
    }
    String str2 = SystemUtils.getDeviceId(paramContext);
    String str3 = SystemUtils.getSubscriberId(paramContext);
    String str4 = checkImeiParam(str2);
    String str5 = checkDeviceParam(str3);
    String str6 = checkDeviceParam(getHWSerialNumber(paramContext));
    String str7 = checkMacParamIsValid(getWifiMacAddress(paramContext));
    String str8 = checkMacParamIsValid(getWiredMac());
    String str9;
    String str10;
    if ((!TextUtils.isEmpty(str7)) || (!TextUtils.isEmpty(str8)))
    {
      str9 = str4 + str5 + str6 + str7 + str8;
      if (TextUtils.isEmpty(str9))
        break label356;
      str10 = MD5Utils.crypt(str9);
      setGenType(1);
    }
    while (true)
    {
      this.mUID = str10;
      setUid(paramContext, str10);
      OutputLog.i("DeviceConstants", "imei:" + str4 + ",\nimsi:" + str5 + ",\nhwsn:" + str6 + ",\nmMac:" + str7 + ",\nmWiredMac:" + str8 + ",\nresult:" + str9 + ",\nuid:" + str10);
      return;
      if (((TextUtils.isEmpty(str4)) || (TextUtils.isEmpty(str5))) && ((TextUtils.isEmpty(str4)) || (TextUtils.isEmpty(str6))))
      {
        boolean bool1 = TextUtils.isEmpty(str5);
        str9 = null;
        if (bool1)
          break;
        boolean bool2 = TextUtils.isEmpty(str6);
        str9 = null;
        if (bool2)
          break;
      }
      str9 = str4 + str5 + str6 + str7 + str8;
      break;
      label356: str10 = MD5Utils.crypt(UUID.randomUUID().toString());
      setGenType(0);
    }
  }

  public String getCheckUid()
  {
    return getGID() + "_" + MD5Utils.crypt(UUID.randomUUID().toString());
  }

  public String getGID()
  {
    return this.mGID;
  }

  public int getGenType()
  {
    return this.mGenType;
  }

  public int getGidGenType()
  {
    return this.mGidGenType;
  }

  public String getManufacturer()
  {
    return this.mManufacturer;
  }

  public String getScreen()
  {
    return this.mScreen;
  }

  public String getUID()
  {
    return this.mUID;
  }

  public String getUIDBeforeV3()
  {
    return this.mUIDBeforeV3;
  }

  public String getWifiMacAddress(Context paramContext)
  {
    int i = 0;
    Object localObject1 = getWifiMac(paramContext);
    if (!TextUtils.isEmpty((CharSequence)localObject1));
    while (true)
    {
      return localObject1;
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      if (localWifiManager == null)
        return "";
      try
      {
        String str1 = localWifiManager.getConnectionInfo().getMacAddress();
        String str2 = str1;
        if (str2 == null);
        try
        {
          if (!localWifiManager.isWifiEnabled())
            localWifiManager.setWifiEnabled(true);
          while (true)
          {
            while (true)
            {
              if (i < 5)
              {
                str2 = localWifiManager.getConnectionInfo().getMacAddress();
                if (str2 == null)
                  break label123;
              }
              localWifiManager.setWifiEnabled(false);
              localObject1 = str2;
              try
              {
                if (TextUtils.isEmpty((CharSequence)localObject1))
                  break;
                setWifiMac(paramContext, (String)localObject1);
                return localObject1;
              }
              catch (Exception localException2)
              {
              }
            }
            localException2.printStackTrace();
            return localObject1;
            try
            {
              label123: Thread.sleep(500L);
              i++;
            }
            catch (InterruptedException localInterruptedException)
            {
              while (true)
                localInterruptedException.printStackTrace();
            }
          }
        }
        catch (Exception localException3)
        {
          while (true)
          {
            localObject1 = str2;
            localObject2 = localException3;
          }
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          Object localObject2 = localException1;
          localObject1 = null;
        }
      }
    }
  }

  public String getWiredMac()
  {
    String str1 = "";
    try
    {
      LineNumberReader localLineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/eth0/address ").getInputStream()));
      do
      {
        localObject = null;
        if (str1 == null)
          break;
        str1 = localLineNumberReader.readLine();
      }
      while (str1 == null);
      String str2 = str1.trim();
      Object localObject = str2;
      return localObject;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public boolean isSupportH265()
  {
    return this.isSupportH265;
  }

  public void setGenType(int paramInt)
  {
    this.mGenType = paramInt;
  }

  public void setGidGenType(int paramInt)
  {
    this.mGidGenType = paramInt;
  }

  public void setSupportH265(boolean paramBoolean)
  {
    this.isSupportH265 = paramBoolean;
  }

  public void setUID(String paramString)
  {
    this.mUID = paramString;
  }

  public void setUIDBeforeV3(String paramString)
  {
    this.mUIDBeforeV3 = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.common.DeviceConstants
 * JD-Core Version:    0.6.2
 */