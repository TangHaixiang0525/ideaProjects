package com.miaozhen.mzmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MZSdkProfile
{
  private static final int DEFAULT_LOCATION_EXPIRES_IN = 300;
  private static final int DEFAULT_LOCATION_SERVICE_TIMEOUT = 15;
  private static final int DEFAULT_LOG_EXPIRES_IN = 604800;
  private static final int DEFAULT_MAX_LOG_ITEMS = 100;
  private static final int DEFAULT_MAX_LOG_RETRY_TIME = 20;
  public static final String DEFAULT_MZ_PROFILE_URI = "http://s.mzfile.com/sdk/mz_sdk_config.xml";
  private static final int DEFAULT_PROFILE_EXPIRES_IN = 86400;
  private static final int DEFAULT_PROFILE_VERSION = 1;
  private static final String DEFAULT_SIGN_VERSION = "1.1";
  private static final String MMA_LATEST_LOCATION = "latestLocation";
  public static final String MZSDK_PROFILE_PREFS_NAME = "mzSdkProfilePrefs";
  private static final String MZ_CONFIGFILE = "mzConfigFile";
  private static final String MZ_LATEST_LOCATION = "mzLatestLocation";
  private static final String MZ_LOCATION_EXPIRES_IN = "mzLocationExpiresIn";
  private static final String MZ_LOCATION_SERVICE_TIMEOUT = "mzLocationServiceTimeout";
  private static final String MZ_LOCATION_UPDATE_TIMESTAMP = "mzLocationUpdateTimestamp";
  private static final String MZ_LOG_EXPIRES_IN = "mzLogExpiresIn";
  private static final String MZ_MAX_LOG_ITEMS = "mzMaxLogItems";
  private static final String MZ_MAX_LOG_RETRY_TIME = "mzMaxLogRetryTime";
  private static final String MZ_PROFILE_EXPIRES_IN = "mzProfileExpiresIn";
  private static final String MZ_PROFILE_UPDATE_TIMESTAMP = "mzProfileUpdateTimestamp";
  public static final String MZ_PROFILE_URI = "mzProfileURI";
  private static final String MZ_PROFILE_VERSION = "mzProfileVersion";
  private static final String MZ_SIGN_VERSION = "mzSignVersion";
  static long configFile_nextUpdateTime = 0L;
  private static Thread configFile_updateThread;
  static final int configFile_update_baseIntervalTime = 900;
  static int configFile_update_retryTimes = 0;

  static String getLatestLocation_MMA(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("latestLocation", "0x0");
  }

  public static String getLatestLocation_MZ(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("mzLatestLocation", "[UNKNOWN]");
  }

  public static int getLocationServiceTimeout(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzLocationServiceTimeout", 15);
  }

  public static int getLogExpiresIn(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzLogExpiresIn", 604800);
  }

  public static int getMaxLogItems(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzMaxLogItems", 100);
  }

  public static int getMaxLogRetryTimes(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzMaxLogRetryTime", 20);
  }

  public static int getProfileVersion(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getInt("mzProfileVersion", 1);
  }

  public static String getSignVersion(Context paramContext)
  {
    return paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("mzSignVersion", "1.1");
  }

  public static boolean isLocationInvalid(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0);
    long l1 = MZUtility.currentUnixTimestamp();
    long l2 = localSharedPreferences.getLong("mzLocationUpdateTimestamp", l1);
    if (l2 == l1);
    int i;
    do
    {
      return true;
      i = localSharedPreferences.getInt("mzLocationExpiresIn", 300);
    }
    while ((l1 - l2 >= i) || (localSharedPreferences.getString("mzLatestLocation", "[UNKNOWN]").equals("[UNKNOWN]")));
    return false;
  }

  public static boolean isProfileInvalid(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0);
    if (localSharedPreferences.getString("mzConfigFile", null) == null);
    long l1;
    long l2;
    int i;
    do
    {
      return true;
      l1 = MZUtility.currentUnixTimestamp();
      l2 = localSharedPreferences.getLong("mzProfileUpdateTimestamp", l1);
      i = localSharedPreferences.getInt("mzProfileExpiresIn", 86400);
    }
    while (l1 - l2 >= i);
    return false;
  }

  static String loadConfigFromLocal(Context paramContext)
  {
    String str = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).getString("mzConfigFile", null);
    if (str == null)
      str = j.a;
    return str;
  }

  static void loadConfigFromNet(Context paramContext)
  {
    if ((configFile_updateThread != null) && (configFile_updateThread.isAlive()))
      return;
    configFile_updateThread = new r(paramContext);
    configFile_updateThread.start();
  }

  @Deprecated
  public static void setCustomProfile(Context paramContext, String paramString)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).edit();
    localEditor.putString("mzProfileURI", paramString);
    localEditor.commit();
  }

  // ERROR //
  static void updateLocalProfileByConfig(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 32
    //   3: iconst_0
    //   4: invokevirtual 99	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   7: astore 7
    //   9: new 184	java/io/ByteArrayInputStream
    //   12: dup
    //   13: aload 7
    //   15: ldc 35
    //   17: aconst_null
    //   18: invokeinterface 107 3 0
    //   23: ldc 186
    //   25: invokevirtual 190	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   28: invokespecial 193	java/io/ByteArrayInputStream:<init>	([B)V
    //   31: astore_2
    //   32: invokestatic 199	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   35: astore 8
    //   37: aload 8
    //   39: aload_2
    //   40: ldc 186
    //   42: invokeinterface 205 3 0
    //   47: aload 8
    //   49: invokeinterface 209 1 0
    //   54: istore 9
    //   56: iconst_0
    //   57: istore 10
    //   59: iconst_0
    //   60: istore 11
    //   62: aconst_null
    //   63: astore 12
    //   65: goto +552 -> 617
    //   68: aload 7
    //   70: invokeinterface 168 1 0
    //   75: astore 13
    //   77: aload 12
    //   79: ldc 211
    //   81: invokeinterface 216 2 0
    //   86: ifeq +28 -> 114
    //   89: aload 13
    //   91: ldc 68
    //   93: aload 12
    //   95: ldc 211
    //   97: invokeinterface 220 2 0
    //   102: checkcast 135	java/lang/String
    //   105: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   108: invokeinterface 230 3 0
    //   113: pop
    //   114: aload 12
    //   116: ldc 232
    //   118: invokeinterface 216 2 0
    //   123: ifeq +25 -> 148
    //   126: aload 13
    //   128: ldc 71
    //   130: aload 12
    //   132: ldc 232
    //   134: invokeinterface 220 2 0
    //   139: checkcast 135	java/lang/String
    //   142: invokeinterface 174 3 0
    //   147: pop
    //   148: aload 12
    //   150: ldc 234
    //   152: invokeinterface 216 2 0
    //   157: ifeq +28 -> 185
    //   160: aload 13
    //   162: ldc 59
    //   164: aload 12
    //   166: ldc 234
    //   168: invokeinterface 220 2 0
    //   173: checkcast 135	java/lang/String
    //   176: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   179: invokeinterface 230 3 0
    //   184: pop
    //   185: aload 12
    //   187: ldc 236
    //   189: invokeinterface 216 2 0
    //   194: ifeq +28 -> 222
    //   197: aload 13
    //   199: ldc 50
    //   201: aload 12
    //   203: ldc 236
    //   205: invokeinterface 220 2 0
    //   210: checkcast 135	java/lang/String
    //   213: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   216: invokeinterface 230 3 0
    //   221: pop
    //   222: aload 12
    //   224: ldc 238
    //   226: invokeinterface 216 2 0
    //   231: ifeq +28 -> 259
    //   234: aload 13
    //   236: ldc 53
    //   238: aload 12
    //   240: ldc 238
    //   242: invokeinterface 220 2 0
    //   247: checkcast 135	java/lang/String
    //   250: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   253: invokeinterface 230 3 0
    //   258: pop
    //   259: aload 12
    //   261: ldc 240
    //   263: invokeinterface 216 2 0
    //   268: ifeq +28 -> 296
    //   271: aload 13
    //   273: ldc 56
    //   275: aload 12
    //   277: ldc 240
    //   279: invokeinterface 220 2 0
    //   284: checkcast 135	java/lang/String
    //   287: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   290: invokeinterface 230 3 0
    //   295: pop
    //   296: aload 12
    //   298: ldc 242
    //   300: invokeinterface 216 2 0
    //   305: ifeq +28 -> 333
    //   308: aload 13
    //   310: ldc 41
    //   312: aload 12
    //   314: ldc 242
    //   316: invokeinterface 220 2 0
    //   321: checkcast 135	java/lang/String
    //   324: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   327: invokeinterface 230 3 0
    //   332: pop
    //   333: aload 12
    //   335: ldc 244
    //   337: invokeinterface 216 2 0
    //   342: ifeq +28 -> 370
    //   345: aload 13
    //   347: ldc 44
    //   349: aload 12
    //   351: ldc 244
    //   353: invokeinterface 220 2 0
    //   358: checkcast 135	java/lang/String
    //   361: invokestatic 226	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   364: invokeinterface 230 3 0
    //   369: pop
    //   370: aload 13
    //   372: ldc 62
    //   374: invokestatic 129	com/miaozhen/mzmonitor/MZUtility:currentUnixTimestamp	()J
    //   377: invokeinterface 248 4 0
    //   382: pop
    //   383: aload 13
    //   385: invokeinterface 177 1 0
    //   390: pop
    //   391: aload_2
    //   392: ifnull +7 -> 399
    //   395: aload_2
    //   396: invokevirtual 253	java/io/InputStream:close	()V
    //   399: return
    //   400: iload 10
    //   402: ifne -334 -> 68
    //   405: iload 9
    //   407: tableswitch	default:+29 -> 436, 0:+41->448, 1:+29->436, 2:+95->502, 3:+143->550
    //   437: iconst_5
    //   438: invokeinterface 256 1 0
    //   443: istore 9
    //   445: goto +172 -> 617
    //   448: new 258	java/util/HashMap
    //   451: dup
    //   452: invokespecial 259	java/util/HashMap:<init>	()V
    //   455: astore 12
    //   457: goto -21 -> 436
    //   460: astore_1
    //   461: ldc_w 261
    //   464: new 263	java/lang/StringBuilder
    //   467: dup
    //   468: ldc_w 265
    //   471: invokespecial 268	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   474: aload_1
    //   475: invokevirtual 272	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   478: invokevirtual 276	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   481: invokestatic 282	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   484: pop
    //   485: aload_2
    //   486: ifnull -87 -> 399
    //   489: aload_2
    //   490: invokevirtual 253	java/io/InputStream:close	()V
    //   493: return
    //   494: astore 6
    //   496: aload 6
    //   498: invokevirtual 285	java/io/IOException:printStackTrace	()V
    //   501: return
    //   502: aload 8
    //   504: invokeinterface 288 1 0
    //   509: astore 26
    //   511: iload 11
    //   513: ifeq +20 -> 533
    //   516: aload 12
    //   518: aload 26
    //   520: aload 8
    //   522: invokeinterface 291 1 0
    //   527: invokeinterface 295 3 0
    //   532: pop
    //   533: aload 26
    //   535: ldc_w 297
    //   538: invokevirtual 139	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   541: ifeq -105 -> 436
    //   544: iconst_1
    //   545: istore 11
    //   547: goto -111 -> 436
    //   550: aload 8
    //   552: invokeinterface 288 1 0
    //   557: ldc_w 297
    //   560: invokevirtual 139	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   563: istore 25
    //   565: iload 25
    //   567: ifeq -131 -> 436
    //   570: iconst_1
    //   571: istore 10
    //   573: goto -137 -> 436
    //   576: astore_3
    //   577: aconst_null
    //   578: astore_2
    //   579: aload_2
    //   580: ifnull +7 -> 587
    //   583: aload_2
    //   584: invokevirtual 253	java/io/InputStream:close	()V
    //   587: aload_3
    //   588: athrow
    //   589: astore 4
    //   591: aload 4
    //   593: invokevirtual 285	java/io/IOException:printStackTrace	()V
    //   596: goto -9 -> 587
    //   599: astore 16
    //   601: aload 16
    //   603: invokevirtual 285	java/io/IOException:printStackTrace	()V
    //   606: return
    //   607: astore_3
    //   608: goto -29 -> 579
    //   611: astore_1
    //   612: aconst_null
    //   613: astore_2
    //   614: goto -153 -> 461
    //   617: iload 9
    //   619: iconst_1
    //   620: if_icmpne -220 -> 400
    //   623: goto -555 -> 68
    //
    // Exception table:
    //   from	to	target	type
    //   32	56	460	java/lang/Exception
    //   68	114	460	java/lang/Exception
    //   114	148	460	java/lang/Exception
    //   148	185	460	java/lang/Exception
    //   185	222	460	java/lang/Exception
    //   222	259	460	java/lang/Exception
    //   259	296	460	java/lang/Exception
    //   296	333	460	java/lang/Exception
    //   333	370	460	java/lang/Exception
    //   370	391	460	java/lang/Exception
    //   436	445	460	java/lang/Exception
    //   448	457	460	java/lang/Exception
    //   502	511	460	java/lang/Exception
    //   516	533	460	java/lang/Exception
    //   533	544	460	java/lang/Exception
    //   550	565	460	java/lang/Exception
    //   489	493	494	java/io/IOException
    //   0	32	576	finally
    //   583	587	589	java/io/IOException
    //   395	399	599	java/io/IOException
    //   32	56	607	finally
    //   68	114	607	finally
    //   114	148	607	finally
    //   148	185	607	finally
    //   185	222	607	finally
    //   222	259	607	finally
    //   259	296	607	finally
    //   296	333	607	finally
    //   333	370	607	finally
    //   370	391	607	finally
    //   436	445	607	finally
    //   448	457	607	finally
    //   461	485	607	finally
    //   502	511	607	finally
    //   516	533	607	finally
    //   533	544	607	finally
    //   550	565	607	finally
    //   0	32	611	java/lang/Exception
  }

  static void updateLocation(Context paramContext, String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("mzSdkProfilePrefs", 0).edit();
      localEditor.putString("mzLatestLocation", paramString1);
      localEditor.putString("latestLocation", paramString2);
      localEditor.putLong("mzLocationUpdateTimestamp", MZUtility.currentUnixTimestamp());
      localEditor.commit();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.MZSdkProfile
 * JD-Core Version:    0.6.2
 */