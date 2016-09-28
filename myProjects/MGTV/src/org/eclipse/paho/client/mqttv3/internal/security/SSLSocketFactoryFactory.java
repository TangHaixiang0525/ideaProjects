package org.eclipse.paho.client.mqttv3.internal.security;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.logging.Logger;

public class SSLSocketFactoryFactory
{
  public static final String CIPHERSUITES = "com.ibm.ssl.enabledCipherSuites";
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory";
  public static final String CLIENTAUTH = "com.ibm.ssl.clientAuthentication";
  public static final String DEFAULT_PROTOCOL = "TLS";
  public static final String JSSEPROVIDER = "com.ibm.ssl.contextProvider";
  public static final String KEYSTORE = "com.ibm.ssl.keyStore";
  public static final String KEYSTOREMGR = "com.ibm.ssl.keyManager";
  public static final String KEYSTOREPROVIDER = "com.ibm.ssl.keyStoreProvider";
  public static final String KEYSTOREPWD = "com.ibm.ssl.keyStorePassword";
  public static final String KEYSTORETYPE = "com.ibm.ssl.keyStoreType";
  public static final String SSLPROTOCOL = "com.ibm.ssl.protocol";
  public static final String SYSKEYMGRALGO = "ssl.KeyManagerFactory.algorithm";
  public static final String SYSKEYSTORE = "javax.net.ssl.keyStore";
  public static final String SYSKEYSTOREPWD = "javax.net.ssl.keyStorePassword";
  public static final String SYSKEYSTORETYPE = "javax.net.ssl.keyStoreType";
  public static final String SYSTRUSTMGRALGO = "ssl.TrustManagerFactory.algorithm";
  public static final String SYSTRUSTSTORE = "javax.net.ssl.trustStore";
  public static final String SYSTRUSTSTOREPWD = "javax.net.ssl.trustStorePassword";
  public static final String SYSTRUSTSTORETYPE = "javax.net.ssl.trustStoreType";
  public static final String TRUSTSTORE = "com.ibm.ssl.trustStore";
  public static final String TRUSTSTOREMGR = "com.ibm.ssl.trustManager";
  public static final String TRUSTSTOREPROVIDER = "com.ibm.ssl.trustStoreProvider";
  public static final String TRUSTSTOREPWD = "com.ibm.ssl.trustStorePassword";
  public static final String TRUSTSTORETYPE = "com.ibm.ssl.trustStoreType";
  private static final byte[] key = { -99, -89, -39, -128, 5, -72, -119, -100 };
  private static final String[] propertyKeys = { "com.ibm.ssl.protocol", "com.ibm.ssl.contextProvider", "com.ibm.ssl.keyStore", "com.ibm.ssl.keyStorePassword", "com.ibm.ssl.keyStoreType", "com.ibm.ssl.keyStoreProvider", "com.ibm.ssl.keyManager", "com.ibm.ssl.trustStore", "com.ibm.ssl.trustStorePassword", "com.ibm.ssl.trustStoreType", "com.ibm.ssl.trustStoreProvider", "com.ibm.ssl.trustManager", "com.ibm.ssl.enabledCipherSuites", "com.ibm.ssl.clientAuthentication" };
  private static final String xorTag = "{xor}";
  private Hashtable configs = new Hashtable();
  private Properties defaultProperties;
  private Logger logger = null;

  public SSLSocketFactoryFactory()
  {
  }

  public SSLSocketFactoryFactory(Logger paramLogger)
  {
    this();
    this.logger = paramLogger;
  }

  private void checkPropertyKeys(Properties paramProperties)
    throws IllegalArgumentException
  {
    Iterator localIterator = paramProperties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!keyValid(str))
        throw new IllegalArgumentException(str + " is not a valid IBM SSL property key.");
    }
  }

  private void convertPassword(Properties paramProperties)
  {
    String str1 = paramProperties.getProperty("com.ibm.ssl.keyStorePassword");
    if ((str1 != null) && (!str1.startsWith("{xor}")))
      paramProperties.put("com.ibm.ssl.keyStorePassword", obfuscate(str1.toCharArray()));
    String str2 = paramProperties.getProperty("com.ibm.ssl.trustStorePassword");
    if ((str2 != null) && (!str2.startsWith("{xor}")))
      paramProperties.put("com.ibm.ssl.trustStorePassword", obfuscate(str2.toCharArray()));
  }

  public static char[] deObfuscate(String paramString)
  {
    if (paramString == null)
      return null;
    byte[] arrayOfByte;
    try
    {
      arrayOfByte = SimpleBase64Encoder.decode(paramString.substring("{xor}".length()));
      for (int i = 0; i < arrayOfByte.length; i++)
        arrayOfByte[i] = ((byte)(0xFF & (arrayOfByte[i] ^ key[(i % key.length)])));
    }
    catch (Exception localException)
    {
      return null;
    }
    return toChar(arrayOfByte);
  }

  private String getProperty(String paramString1, String paramString2, String paramString3)
  {
    String str = getPropertyFromConfig(paramString1, paramString2);
    if (str != null)
      return str;
    if (paramString3 != null)
      str = System.getProperty(paramString3);
    return str;
  }

  private String getPropertyFromConfig(String paramString1, String paramString2)
  {
    Properties localProperties1 = null;
    if (paramString1 != null)
      localProperties1 = (Properties)this.configs.get(paramString1);
    String str = null;
    if (localProperties1 != null)
    {
      str = localProperties1.getProperty(paramString2);
      if (str != null)
        return str;
    }
    Properties localProperties2 = this.defaultProperties;
    if (localProperties2 != null)
    {
      str = localProperties2.getProperty(paramString2);
      if (str != null)
        return str;
    }
    return str;
  }

  // ERROR //
  private SSLContext getSSLContext(String paramString)
    throws MqttSecurityException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 243	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getSSLProtocol	(Ljava/lang/String;)Ljava/lang/String;
    //   5: astore_2
    //   6: aload_2
    //   7: ifnonnull +6 -> 13
    //   10: ldc 17
    //   12: astore_2
    //   13: aload_0
    //   14: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   17: ifnull +48 -> 65
    //   20: aload_0
    //   21: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   24: astore 89
    //   26: iconst_2
    //   27: anewarray 4	java/lang/Object
    //   30: astore 90
    //   32: aload_1
    //   33: ifnull +1037 -> 1070
    //   36: aload_1
    //   37: astore 91
    //   39: aload 90
    //   41: iconst_0
    //   42: aload 91
    //   44: aastore
    //   45: aload 90
    //   47: iconst_1
    //   48: aload_2
    //   49: aastore
    //   50: aload 89
    //   52: ldc 11
    //   54: ldc 244
    //   56: ldc 246
    //   58: aload 90
    //   60: invokeinterface 252 5 0
    //   65: aload_0
    //   66: aload_1
    //   67: invokevirtual 255	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getJSSEProvider	(Ljava/lang/String;)Ljava/lang/String;
    //   70: astore_3
    //   71: aload_3
    //   72: ifnonnull +1006 -> 1078
    //   75: aload_2
    //   76: invokestatic 260	javax/net/ssl/SSLContext:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/SSLContext;
    //   79: astore 10
    //   81: aload_0
    //   82: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   85: ifnull +56 -> 141
    //   88: aload_0
    //   89: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   92: astore 86
    //   94: iconst_2
    //   95: anewarray 4	java/lang/Object
    //   98: astore 87
    //   100: aload_1
    //   101: ifnull +1230 -> 1331
    //   104: aload_1
    //   105: astore 88
    //   107: aload 87
    //   109: iconst_0
    //   110: aload 88
    //   112: aastore
    //   113: aload 87
    //   115: iconst_1
    //   116: aload 10
    //   118: invokevirtual 264	javax/net/ssl/SSLContext:getProvider	()Ljava/security/Provider;
    //   121: invokevirtual 269	java/security/Provider:getName	()Ljava/lang/String;
    //   124: aastore
    //   125: aload 86
    //   127: ldc 11
    //   129: ldc 244
    //   131: ldc_w 271
    //   134: aload 87
    //   136: invokeinterface 252 5 0
    //   141: aload_0
    //   142: aload_1
    //   143: ldc 23
    //   145: aconst_null
    //   146: invokespecial 273	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getProperty	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   149: astore 11
    //   151: aconst_null
    //   152: astore 12
    //   154: iconst_0
    //   155: ifne +460 -> 615
    //   158: aload 11
    //   160: ifnonnull +14 -> 174
    //   163: aload_0
    //   164: aload_1
    //   165: ldc 23
    //   167: ldc 44
    //   169: invokespecial 273	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getProperty	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   172: astore 11
    //   174: aload_0
    //   175: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   178: ifnull +59 -> 237
    //   181: aload_0
    //   182: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   185: astore 82
    //   187: iconst_2
    //   188: anewarray 4	java/lang/Object
    //   191: astore 83
    //   193: aload_1
    //   194: ifnull +1145 -> 1339
    //   197: aload_1
    //   198: astore 84
    //   200: aload 83
    //   202: iconst_0
    //   203: aload 84
    //   205: aastore
    //   206: aload 11
    //   208: ifnull +1139 -> 1347
    //   211: aload 11
    //   213: astore 85
    //   215: aload 83
    //   217: iconst_1
    //   218: aload 85
    //   220: aastore
    //   221: aload 82
    //   223: ldc 11
    //   225: ldc 244
    //   227: ldc_w 275
    //   230: aload 83
    //   232: invokeinterface 252 5 0
    //   237: aload_0
    //   238: aload_1
    //   239: invokevirtual 278	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getKeyStorePassword	(Ljava/lang/String;)[C
    //   242: astore 49
    //   244: aload_0
    //   245: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   248: ifnull +62 -> 310
    //   251: aload_0
    //   252: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   255: astore 78
    //   257: iconst_2
    //   258: anewarray 4	java/lang/Object
    //   261: astore 79
    //   263: aload_1
    //   264: ifnull +1091 -> 1355
    //   267: aload_1
    //   268: astore 80
    //   270: aload 79
    //   272: iconst_0
    //   273: aload 80
    //   275: aastore
    //   276: aload 49
    //   278: ifnull +1085 -> 1363
    //   281: aload 49
    //   283: invokestatic 180	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:obfuscate	([C)Ljava/lang/String;
    //   286: astore 81
    //   288: aload 79
    //   290: iconst_1
    //   291: aload 81
    //   293: aastore
    //   294: aload 78
    //   296: ldc 11
    //   298: ldc 244
    //   300: ldc_w 280
    //   303: aload 79
    //   305: invokeinterface 252 5 0
    //   310: aload_0
    //   311: aload_1
    //   312: invokevirtual 283	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getKeyStoreType	(Ljava/lang/String;)Ljava/lang/String;
    //   315: astore 50
    //   317: aload 50
    //   319: ifnonnull +8 -> 327
    //   322: invokestatic 288	java/security/KeyStore:getDefaultType	()Ljava/lang/String;
    //   325: astore 50
    //   327: aload_0
    //   328: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   331: ifnull +59 -> 390
    //   334: aload_0
    //   335: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   338: astore 74
    //   340: iconst_2
    //   341: anewarray 4	java/lang/Object
    //   344: astore 75
    //   346: aload_1
    //   347: ifnull +1024 -> 1371
    //   350: aload_1
    //   351: astore 76
    //   353: aload 75
    //   355: iconst_0
    //   356: aload 76
    //   358: aastore
    //   359: aload 50
    //   361: ifnull +727 -> 1088
    //   364: aload 50
    //   366: astore 77
    //   368: aload 75
    //   370: iconst_1
    //   371: aload 77
    //   373: aastore
    //   374: aload 74
    //   376: ldc 11
    //   378: ldc 244
    //   380: ldc_w 290
    //   383: aload 75
    //   385: invokeinterface 252 5 0
    //   390: invokestatic 295	javax/net/ssl/KeyManagerFactory:getDefaultAlgorithm	()Ljava/lang/String;
    //   393: astore 51
    //   395: aload_0
    //   396: aload_1
    //   397: invokevirtual 298	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getKeyStoreProvider	(Ljava/lang/String;)Ljava/lang/String;
    //   400: astore 52
    //   402: aload_0
    //   403: aload_1
    //   404: invokevirtual 301	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getKeyManager	(Ljava/lang/String;)Ljava/lang/String;
    //   407: astore 53
    //   409: aload 53
    //   411: ifnull +7 -> 418
    //   414: aload 53
    //   416: astore 51
    //   418: aconst_null
    //   419: astore 12
    //   421: aload 11
    //   423: ifnull +192 -> 615
    //   426: aconst_null
    //   427: astore 12
    //   429: aload 50
    //   431: ifnull +184 -> 615
    //   434: aconst_null
    //   435: astore 12
    //   437: aload 51
    //   439: ifnull +176 -> 615
    //   442: aload 50
    //   444: invokestatic 304	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   447: astore 64
    //   449: new 306	java/io/FileInputStream
    //   452: dup
    //   453: aload 11
    //   455: invokespecial 307	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   458: astore 65
    //   460: aload 64
    //   462: aload 65
    //   464: aload 49
    //   466: invokevirtual 311	java/security/KeyStore:load	(Ljava/io/InputStream;[C)V
    //   469: aload 52
    //   471: ifnull +625 -> 1096
    //   474: aload 51
    //   476: aload 52
    //   478: invokestatic 314	javax/net/ssl/KeyManagerFactory:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljavax/net/ssl/KeyManagerFactory;
    //   481: astore 66
    //   483: aload_0
    //   484: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   487: ifnull +108 -> 595
    //   490: aload_0
    //   491: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   494: astore 68
    //   496: iconst_2
    //   497: anewarray 4	java/lang/Object
    //   500: astore 69
    //   502: aload_1
    //   503: ifnull +876 -> 1379
    //   506: aload_1
    //   507: astore 70
    //   509: aload 69
    //   511: iconst_0
    //   512: aload 70
    //   514: aastore
    //   515: aload 51
    //   517: ifnull +870 -> 1387
    //   520: aload 69
    //   522: iconst_1
    //   523: aload 51
    //   525: aastore
    //   526: aload 68
    //   528: ldc 11
    //   530: ldc 244
    //   532: ldc_w 316
    //   535: aload 69
    //   537: invokeinterface 252 5 0
    //   542: aload_0
    //   543: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   546: astore 71
    //   548: iconst_2
    //   549: anewarray 4	java/lang/Object
    //   552: astore 72
    //   554: aload_1
    //   555: ifnull +551 -> 1106
    //   558: aload_1
    //   559: astore 73
    //   561: aload 72
    //   563: iconst_0
    //   564: aload 73
    //   566: aastore
    //   567: aload 72
    //   569: iconst_1
    //   570: aload 66
    //   572: invokevirtual 317	javax/net/ssl/KeyManagerFactory:getProvider	()Ljava/security/Provider;
    //   575: invokevirtual 269	java/security/Provider:getName	()Ljava/lang/String;
    //   578: aastore
    //   579: aload 71
    //   581: ldc 11
    //   583: ldc 244
    //   585: ldc_w 319
    //   588: aload 72
    //   590: invokeinterface 252 5 0
    //   595: aload 66
    //   597: aload 64
    //   599: aload 49
    //   601: invokevirtual 323	javax/net/ssl/KeyManagerFactory:init	(Ljava/security/KeyStore;[C)V
    //   604: aload 66
    //   606: invokevirtual 327	javax/net/ssl/KeyManagerFactory:getKeyManagers	()[Ljavax/net/ssl/KeyManager;
    //   609: astore 67
    //   611: aload 67
    //   613: astore 12
    //   615: aload_0
    //   616: aload_1
    //   617: invokevirtual 330	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getTrustStore	(Ljava/lang/String;)Ljava/lang/String;
    //   620: astore 13
    //   622: aload_0
    //   623: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   626: ifnull +59 -> 685
    //   629: aload_0
    //   630: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   633: astore 45
    //   635: iconst_2
    //   636: anewarray 4	java/lang/Object
    //   639: astore 46
    //   641: aload_1
    //   642: ifnull +753 -> 1395
    //   645: aload_1
    //   646: astore 47
    //   648: aload 46
    //   650: iconst_0
    //   651: aload 47
    //   653: aastore
    //   654: aload 13
    //   656: ifnull +747 -> 1403
    //   659: aload 13
    //   661: astore 48
    //   663: aload 46
    //   665: iconst_1
    //   666: aload 48
    //   668: aastore
    //   669: aload 45
    //   671: ldc 11
    //   673: ldc 244
    //   675: ldc_w 332
    //   678: aload 46
    //   680: invokeinterface 252 5 0
    //   685: aload_0
    //   686: aload_1
    //   687: invokevirtual 335	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getTrustStorePassword	(Ljava/lang/String;)[C
    //   690: astore 14
    //   692: aload_0
    //   693: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   696: ifnull +62 -> 758
    //   699: aload_0
    //   700: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   703: astore 41
    //   705: iconst_2
    //   706: anewarray 4	java/lang/Object
    //   709: astore 42
    //   711: aload_1
    //   712: ifnull +699 -> 1411
    //   715: aload_1
    //   716: astore 43
    //   718: aload 42
    //   720: iconst_0
    //   721: aload 43
    //   723: aastore
    //   724: aload 14
    //   726: ifnull +693 -> 1419
    //   729: aload 14
    //   731: invokestatic 180	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:obfuscate	([C)Ljava/lang/String;
    //   734: astore 44
    //   736: aload 42
    //   738: iconst_1
    //   739: aload 44
    //   741: aastore
    //   742: aload 41
    //   744: ldc 11
    //   746: ldc 244
    //   748: ldc_w 337
    //   751: aload 42
    //   753: invokeinterface 252 5 0
    //   758: aload_0
    //   759: aload_1
    //   760: invokevirtual 340	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getTrustStoreType	(Ljava/lang/String;)Ljava/lang/String;
    //   763: astore 15
    //   765: aload 15
    //   767: ifnonnull +8 -> 775
    //   770: invokestatic 288	java/security/KeyStore:getDefaultType	()Ljava/lang/String;
    //   773: astore 15
    //   775: aload_0
    //   776: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   779: ifnull +59 -> 838
    //   782: aload_0
    //   783: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   786: astore 37
    //   788: iconst_2
    //   789: anewarray 4	java/lang/Object
    //   792: astore 38
    //   794: aload_1
    //   795: ifnull +632 -> 1427
    //   798: aload_1
    //   799: astore 39
    //   801: aload 38
    //   803: iconst_0
    //   804: aload 39
    //   806: aastore
    //   807: aload 15
    //   809: ifnull +433 -> 1242
    //   812: aload 15
    //   814: astore 40
    //   816: aload 38
    //   818: iconst_1
    //   819: aload 40
    //   821: aastore
    //   822: aload 37
    //   824: ldc 11
    //   826: ldc 244
    //   828: ldc_w 342
    //   831: aload 38
    //   833: invokeinterface 252 5 0
    //   838: invokestatic 345	javax/net/ssl/TrustManagerFactory:getDefaultAlgorithm	()Ljava/lang/String;
    //   841: astore 16
    //   843: aload_0
    //   844: aload_1
    //   845: invokevirtual 348	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getTrustStoreProvider	(Ljava/lang/String;)Ljava/lang/String;
    //   848: astore 17
    //   850: aload_0
    //   851: aload_1
    //   852: invokevirtual 351	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:getTrustManager	(Ljava/lang/String;)Ljava/lang/String;
    //   855: astore 18
    //   857: aload 18
    //   859: ifnull +7 -> 866
    //   862: aload 18
    //   864: astore 16
    //   866: aconst_null
    //   867: astore 19
    //   869: aload 13
    //   871: ifnull +186 -> 1057
    //   874: aconst_null
    //   875: astore 19
    //   877: aload 15
    //   879: ifnull +178 -> 1057
    //   882: aconst_null
    //   883: astore 19
    //   885: aload 16
    //   887: ifnull +170 -> 1057
    //   890: aload 15
    //   892: invokestatic 304	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   895: astore 28
    //   897: new 306	java/io/FileInputStream
    //   900: dup
    //   901: aload 13
    //   903: invokespecial 307	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   906: astore 29
    //   908: aload 28
    //   910: aload 29
    //   912: aload 14
    //   914: invokevirtual 311	java/security/KeyStore:load	(Ljava/io/InputStream;[C)V
    //   917: aload 17
    //   919: ifnull +331 -> 1250
    //   922: aload 16
    //   924: aload 17
    //   926: invokestatic 354	javax/net/ssl/TrustManagerFactory:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljavax/net/ssl/TrustManagerFactory;
    //   929: astore 30
    //   931: aload_0
    //   932: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   935: ifnull +104 -> 1039
    //   938: aload_0
    //   939: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   942: astore 32
    //   944: iconst_2
    //   945: anewarray 4	java/lang/Object
    //   948: astore 33
    //   950: aload_1
    //   951: ifnull +484 -> 1435
    //   954: aload_1
    //   955: astore 34
    //   957: aload 33
    //   959: iconst_0
    //   960: aload 34
    //   962: aastore
    //   963: aload 16
    //   965: ifnull +478 -> 1443
    //   968: aload 33
    //   970: iconst_1
    //   971: aload 16
    //   973: aastore
    //   974: aload 32
    //   976: ldc 11
    //   978: ldc 244
    //   980: ldc_w 356
    //   983: aload 33
    //   985: invokeinterface 252 5 0
    //   990: aload_0
    //   991: getfield 111	org/eclipse/paho/client/mqttv3/internal/security/SSLSocketFactoryFactory:logger	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   994: astore 35
    //   996: iconst_2
    //   997: anewarray 4	java/lang/Object
    //   1000: astore 36
    //   1002: aload_1
    //   1003: ifnull +257 -> 1260
    //   1006: aload 36
    //   1008: iconst_0
    //   1009: aload_1
    //   1010: aastore
    //   1011: aload 36
    //   1013: iconst_1
    //   1014: aload 30
    //   1016: invokevirtual 357	javax/net/ssl/TrustManagerFactory:getProvider	()Ljava/security/Provider;
    //   1019: invokevirtual 269	java/security/Provider:getName	()Ljava/lang/String;
    //   1022: aastore
    //   1023: aload 35
    //   1025: ldc 11
    //   1027: ldc 244
    //   1029: ldc_w 359
    //   1032: aload 36
    //   1034: invokeinterface 252 5 0
    //   1039: aload 30
    //   1041: aload 28
    //   1043: invokevirtual 362	javax/net/ssl/TrustManagerFactory:init	(Ljava/security/KeyStore;)V
    //   1046: aload 30
    //   1048: invokevirtual 366	javax/net/ssl/TrustManagerFactory:getTrustManagers	()[Ljavax/net/ssl/TrustManager;
    //   1051: astore 31
    //   1053: aload 31
    //   1055: astore 19
    //   1057: aload 10
    //   1059: aload 12
    //   1061: aload 19
    //   1063: aconst_null
    //   1064: invokevirtual 369	javax/net/ssl/SSLContext:init	([Ljavax/net/ssl/KeyManager;[Ljavax/net/ssl/TrustManager;Ljava/security/SecureRandom;)V
    //   1067: aload 10
    //   1069: areturn
    //   1070: ldc_w 371
    //   1073: astore 91
    //   1075: goto -1036 -> 39
    //   1078: aload_2
    //   1079: aload_3
    //   1080: invokestatic 374	javax/net/ssl/SSLContext:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljavax/net/ssl/SSLContext;
    //   1083: astore 10
    //   1085: goto -1004 -> 81
    //   1088: ldc_w 376
    //   1091: astore 77
    //   1093: goto -725 -> 368
    //   1096: aload 51
    //   1098: invokestatic 379	javax/net/ssl/KeyManagerFactory:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/KeyManagerFactory;
    //   1101: astore 66
    //   1103: goto -620 -> 483
    //   1106: ldc_w 371
    //   1109: astore 73
    //   1111: goto -550 -> 561
    //   1114: astore 62
    //   1116: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1119: dup
    //   1120: aload 62
    //   1122: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1125: astore 63
    //   1127: aload 63
    //   1129: athrow
    //   1130: astore 8
    //   1132: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1135: dup
    //   1136: aload 8
    //   1138: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1141: astore 9
    //   1143: aload 9
    //   1145: athrow
    //   1146: astore 60
    //   1148: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1151: dup
    //   1152: aload 60
    //   1154: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1157: astore 61
    //   1159: aload 61
    //   1161: athrow
    //   1162: astore 6
    //   1164: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1167: dup
    //   1168: aload 6
    //   1170: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1173: astore 7
    //   1175: aload 7
    //   1177: athrow
    //   1178: astore 58
    //   1180: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1183: dup
    //   1184: aload 58
    //   1186: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1189: astore 59
    //   1191: aload 59
    //   1193: athrow
    //   1194: astore 4
    //   1196: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1199: dup
    //   1200: aload 4
    //   1202: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1205: astore 5
    //   1207: aload 5
    //   1209: athrow
    //   1210: astore 56
    //   1212: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1215: dup
    //   1216: aload 56
    //   1218: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1221: astore 57
    //   1223: aload 57
    //   1225: athrow
    //   1226: astore 54
    //   1228: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1231: dup
    //   1232: aload 54
    //   1234: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1237: astore 55
    //   1239: aload 55
    //   1241: athrow
    //   1242: ldc_w 376
    //   1245: astore 40
    //   1247: goto -431 -> 816
    //   1250: aload 16
    //   1252: invokestatic 385	javax/net/ssl/TrustManagerFactory:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/TrustManagerFactory;
    //   1255: astore 30
    //   1257: goto -326 -> 931
    //   1260: ldc_w 371
    //   1263: astore_1
    //   1264: goto -258 -> 1006
    //   1267: astore 26
    //   1269: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1272: dup
    //   1273: aload 26
    //   1275: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1278: astore 27
    //   1280: aload 27
    //   1282: athrow
    //   1283: astore 24
    //   1285: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1288: dup
    //   1289: aload 24
    //   1291: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1294: astore 25
    //   1296: aload 25
    //   1298: athrow
    //   1299: astore 22
    //   1301: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1304: dup
    //   1305: aload 22
    //   1307: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1310: astore 23
    //   1312: aload 23
    //   1314: athrow
    //   1315: astore 20
    //   1317: new 224	org/eclipse/paho/client/mqttv3/MqttSecurityException
    //   1320: dup
    //   1321: aload 20
    //   1323: invokespecial 382	org/eclipse/paho/client/mqttv3/MqttSecurityException:<init>	(Ljava/lang/Throwable;)V
    //   1326: astore 21
    //   1328: aload 21
    //   1330: athrow
    //   1331: ldc_w 371
    //   1334: astore 88
    //   1336: goto -1229 -> 107
    //   1339: ldc_w 371
    //   1342: astore 84
    //   1344: goto -1144 -> 200
    //   1347: ldc_w 376
    //   1350: astore 85
    //   1352: goto -1137 -> 215
    //   1355: ldc_w 371
    //   1358: astore 80
    //   1360: goto -1090 -> 270
    //   1363: ldc_w 376
    //   1366: astore 81
    //   1368: goto -1080 -> 288
    //   1371: ldc_w 371
    //   1374: astore 76
    //   1376: goto -1023 -> 353
    //   1379: ldc_w 371
    //   1382: astore 70
    //   1384: goto -875 -> 509
    //   1387: ldc_w 376
    //   1390: astore 51
    //   1392: goto -872 -> 520
    //   1395: ldc_w 371
    //   1398: astore 47
    //   1400: goto -752 -> 648
    //   1403: ldc_w 376
    //   1406: astore 48
    //   1408: goto -745 -> 663
    //   1411: ldc_w 371
    //   1414: astore 43
    //   1416: goto -698 -> 718
    //   1419: ldc_w 376
    //   1422: astore 44
    //   1424: goto -688 -> 736
    //   1427: ldc_w 371
    //   1430: astore 39
    //   1432: goto -631 -> 801
    //   1435: ldc_w 371
    //   1438: astore 34
    //   1440: goto -483 -> 957
    //   1443: ldc_w 376
    //   1446: astore 16
    //   1448: goto -480 -> 968
    //
    // Exception table:
    //   from	to	target	type
    //   442	469	1114	java/security/KeyStoreException
    //   474	483	1114	java/security/KeyStoreException
    //   483	502	1114	java/security/KeyStoreException
    //   509	515	1114	java/security/KeyStoreException
    //   520	554	1114	java/security/KeyStoreException
    //   561	595	1114	java/security/KeyStoreException
    //   595	611	1114	java/security/KeyStoreException
    //   1096	1103	1114	java/security/KeyStoreException
    //   75	81	1130	java/security/NoSuchAlgorithmException
    //   81	100	1130	java/security/NoSuchAlgorithmException
    //   107	141	1130	java/security/NoSuchAlgorithmException
    //   141	151	1130	java/security/NoSuchAlgorithmException
    //   163	174	1130	java/security/NoSuchAlgorithmException
    //   174	193	1130	java/security/NoSuchAlgorithmException
    //   200	206	1130	java/security/NoSuchAlgorithmException
    //   215	237	1130	java/security/NoSuchAlgorithmException
    //   237	263	1130	java/security/NoSuchAlgorithmException
    //   270	276	1130	java/security/NoSuchAlgorithmException
    //   281	288	1130	java/security/NoSuchAlgorithmException
    //   288	310	1130	java/security/NoSuchAlgorithmException
    //   310	317	1130	java/security/NoSuchAlgorithmException
    //   322	327	1130	java/security/NoSuchAlgorithmException
    //   327	346	1130	java/security/NoSuchAlgorithmException
    //   353	359	1130	java/security/NoSuchAlgorithmException
    //   368	390	1130	java/security/NoSuchAlgorithmException
    //   390	409	1130	java/security/NoSuchAlgorithmException
    //   442	469	1130	java/security/NoSuchAlgorithmException
    //   474	483	1130	java/security/NoSuchAlgorithmException
    //   483	502	1130	java/security/NoSuchAlgorithmException
    //   509	515	1130	java/security/NoSuchAlgorithmException
    //   520	554	1130	java/security/NoSuchAlgorithmException
    //   561	595	1130	java/security/NoSuchAlgorithmException
    //   595	611	1130	java/security/NoSuchAlgorithmException
    //   615	641	1130	java/security/NoSuchAlgorithmException
    //   648	654	1130	java/security/NoSuchAlgorithmException
    //   663	685	1130	java/security/NoSuchAlgorithmException
    //   685	711	1130	java/security/NoSuchAlgorithmException
    //   718	724	1130	java/security/NoSuchAlgorithmException
    //   729	736	1130	java/security/NoSuchAlgorithmException
    //   736	758	1130	java/security/NoSuchAlgorithmException
    //   758	765	1130	java/security/NoSuchAlgorithmException
    //   770	775	1130	java/security/NoSuchAlgorithmException
    //   775	794	1130	java/security/NoSuchAlgorithmException
    //   801	807	1130	java/security/NoSuchAlgorithmException
    //   816	838	1130	java/security/NoSuchAlgorithmException
    //   838	857	1130	java/security/NoSuchAlgorithmException
    //   890	917	1130	java/security/NoSuchAlgorithmException
    //   922	931	1130	java/security/NoSuchAlgorithmException
    //   931	950	1130	java/security/NoSuchAlgorithmException
    //   957	963	1130	java/security/NoSuchAlgorithmException
    //   968	1002	1130	java/security/NoSuchAlgorithmException
    //   1006	1039	1130	java/security/NoSuchAlgorithmException
    //   1039	1053	1130	java/security/NoSuchAlgorithmException
    //   1057	1067	1130	java/security/NoSuchAlgorithmException
    //   1078	1085	1130	java/security/NoSuchAlgorithmException
    //   1096	1103	1130	java/security/NoSuchAlgorithmException
    //   1116	1130	1130	java/security/NoSuchAlgorithmException
    //   1148	1162	1130	java/security/NoSuchAlgorithmException
    //   1180	1194	1130	java/security/NoSuchAlgorithmException
    //   1212	1226	1130	java/security/NoSuchAlgorithmException
    //   1228	1242	1130	java/security/NoSuchAlgorithmException
    //   1250	1257	1130	java/security/NoSuchAlgorithmException
    //   1269	1283	1130	java/security/NoSuchAlgorithmException
    //   1285	1299	1130	java/security/NoSuchAlgorithmException
    //   1301	1315	1130	java/security/NoSuchAlgorithmException
    //   1317	1331	1130	java/security/NoSuchAlgorithmException
    //   442	469	1146	java/security/cert/CertificateException
    //   474	483	1146	java/security/cert/CertificateException
    //   483	502	1146	java/security/cert/CertificateException
    //   509	515	1146	java/security/cert/CertificateException
    //   520	554	1146	java/security/cert/CertificateException
    //   561	595	1146	java/security/cert/CertificateException
    //   595	611	1146	java/security/cert/CertificateException
    //   1096	1103	1146	java/security/cert/CertificateException
    //   75	81	1162	java/security/NoSuchProviderException
    //   81	100	1162	java/security/NoSuchProviderException
    //   107	141	1162	java/security/NoSuchProviderException
    //   141	151	1162	java/security/NoSuchProviderException
    //   163	174	1162	java/security/NoSuchProviderException
    //   174	193	1162	java/security/NoSuchProviderException
    //   200	206	1162	java/security/NoSuchProviderException
    //   215	237	1162	java/security/NoSuchProviderException
    //   237	263	1162	java/security/NoSuchProviderException
    //   270	276	1162	java/security/NoSuchProviderException
    //   281	288	1162	java/security/NoSuchProviderException
    //   288	310	1162	java/security/NoSuchProviderException
    //   310	317	1162	java/security/NoSuchProviderException
    //   322	327	1162	java/security/NoSuchProviderException
    //   327	346	1162	java/security/NoSuchProviderException
    //   353	359	1162	java/security/NoSuchProviderException
    //   368	390	1162	java/security/NoSuchProviderException
    //   390	409	1162	java/security/NoSuchProviderException
    //   442	469	1162	java/security/NoSuchProviderException
    //   474	483	1162	java/security/NoSuchProviderException
    //   483	502	1162	java/security/NoSuchProviderException
    //   509	515	1162	java/security/NoSuchProviderException
    //   520	554	1162	java/security/NoSuchProviderException
    //   561	595	1162	java/security/NoSuchProviderException
    //   595	611	1162	java/security/NoSuchProviderException
    //   615	641	1162	java/security/NoSuchProviderException
    //   648	654	1162	java/security/NoSuchProviderException
    //   663	685	1162	java/security/NoSuchProviderException
    //   685	711	1162	java/security/NoSuchProviderException
    //   718	724	1162	java/security/NoSuchProviderException
    //   729	736	1162	java/security/NoSuchProviderException
    //   736	758	1162	java/security/NoSuchProviderException
    //   758	765	1162	java/security/NoSuchProviderException
    //   770	775	1162	java/security/NoSuchProviderException
    //   775	794	1162	java/security/NoSuchProviderException
    //   801	807	1162	java/security/NoSuchProviderException
    //   816	838	1162	java/security/NoSuchProviderException
    //   838	857	1162	java/security/NoSuchProviderException
    //   890	917	1162	java/security/NoSuchProviderException
    //   922	931	1162	java/security/NoSuchProviderException
    //   931	950	1162	java/security/NoSuchProviderException
    //   957	963	1162	java/security/NoSuchProviderException
    //   968	1002	1162	java/security/NoSuchProviderException
    //   1006	1039	1162	java/security/NoSuchProviderException
    //   1039	1053	1162	java/security/NoSuchProviderException
    //   1057	1067	1162	java/security/NoSuchProviderException
    //   1078	1085	1162	java/security/NoSuchProviderException
    //   1096	1103	1162	java/security/NoSuchProviderException
    //   1116	1130	1162	java/security/NoSuchProviderException
    //   1148	1162	1162	java/security/NoSuchProviderException
    //   1180	1194	1162	java/security/NoSuchProviderException
    //   1212	1226	1162	java/security/NoSuchProviderException
    //   1228	1242	1162	java/security/NoSuchProviderException
    //   1250	1257	1162	java/security/NoSuchProviderException
    //   1269	1283	1162	java/security/NoSuchProviderException
    //   1285	1299	1162	java/security/NoSuchProviderException
    //   1301	1315	1162	java/security/NoSuchProviderException
    //   1317	1331	1162	java/security/NoSuchProviderException
    //   442	469	1178	java/io/FileNotFoundException
    //   474	483	1178	java/io/FileNotFoundException
    //   483	502	1178	java/io/FileNotFoundException
    //   509	515	1178	java/io/FileNotFoundException
    //   520	554	1178	java/io/FileNotFoundException
    //   561	595	1178	java/io/FileNotFoundException
    //   595	611	1178	java/io/FileNotFoundException
    //   1096	1103	1178	java/io/FileNotFoundException
    //   75	81	1194	java/security/KeyManagementException
    //   81	100	1194	java/security/KeyManagementException
    //   107	141	1194	java/security/KeyManagementException
    //   141	151	1194	java/security/KeyManagementException
    //   163	174	1194	java/security/KeyManagementException
    //   174	193	1194	java/security/KeyManagementException
    //   200	206	1194	java/security/KeyManagementException
    //   215	237	1194	java/security/KeyManagementException
    //   237	263	1194	java/security/KeyManagementException
    //   270	276	1194	java/security/KeyManagementException
    //   281	288	1194	java/security/KeyManagementException
    //   288	310	1194	java/security/KeyManagementException
    //   310	317	1194	java/security/KeyManagementException
    //   322	327	1194	java/security/KeyManagementException
    //   327	346	1194	java/security/KeyManagementException
    //   353	359	1194	java/security/KeyManagementException
    //   368	390	1194	java/security/KeyManagementException
    //   390	409	1194	java/security/KeyManagementException
    //   442	469	1194	java/security/KeyManagementException
    //   474	483	1194	java/security/KeyManagementException
    //   483	502	1194	java/security/KeyManagementException
    //   509	515	1194	java/security/KeyManagementException
    //   520	554	1194	java/security/KeyManagementException
    //   561	595	1194	java/security/KeyManagementException
    //   595	611	1194	java/security/KeyManagementException
    //   615	641	1194	java/security/KeyManagementException
    //   648	654	1194	java/security/KeyManagementException
    //   663	685	1194	java/security/KeyManagementException
    //   685	711	1194	java/security/KeyManagementException
    //   718	724	1194	java/security/KeyManagementException
    //   729	736	1194	java/security/KeyManagementException
    //   736	758	1194	java/security/KeyManagementException
    //   758	765	1194	java/security/KeyManagementException
    //   770	775	1194	java/security/KeyManagementException
    //   775	794	1194	java/security/KeyManagementException
    //   801	807	1194	java/security/KeyManagementException
    //   816	838	1194	java/security/KeyManagementException
    //   838	857	1194	java/security/KeyManagementException
    //   890	917	1194	java/security/KeyManagementException
    //   922	931	1194	java/security/KeyManagementException
    //   931	950	1194	java/security/KeyManagementException
    //   957	963	1194	java/security/KeyManagementException
    //   968	1002	1194	java/security/KeyManagementException
    //   1006	1039	1194	java/security/KeyManagementException
    //   1039	1053	1194	java/security/KeyManagementException
    //   1057	1067	1194	java/security/KeyManagementException
    //   1078	1085	1194	java/security/KeyManagementException
    //   1096	1103	1194	java/security/KeyManagementException
    //   1116	1130	1194	java/security/KeyManagementException
    //   1148	1162	1194	java/security/KeyManagementException
    //   1180	1194	1194	java/security/KeyManagementException
    //   1212	1226	1194	java/security/KeyManagementException
    //   1228	1242	1194	java/security/KeyManagementException
    //   1250	1257	1194	java/security/KeyManagementException
    //   1269	1283	1194	java/security/KeyManagementException
    //   1285	1299	1194	java/security/KeyManagementException
    //   1301	1315	1194	java/security/KeyManagementException
    //   1317	1331	1194	java/security/KeyManagementException
    //   442	469	1210	java/io/IOException
    //   474	483	1210	java/io/IOException
    //   483	502	1210	java/io/IOException
    //   509	515	1210	java/io/IOException
    //   520	554	1210	java/io/IOException
    //   561	595	1210	java/io/IOException
    //   595	611	1210	java/io/IOException
    //   1096	1103	1210	java/io/IOException
    //   442	469	1226	java/security/UnrecoverableKeyException
    //   474	483	1226	java/security/UnrecoverableKeyException
    //   483	502	1226	java/security/UnrecoverableKeyException
    //   509	515	1226	java/security/UnrecoverableKeyException
    //   520	554	1226	java/security/UnrecoverableKeyException
    //   561	595	1226	java/security/UnrecoverableKeyException
    //   595	611	1226	java/security/UnrecoverableKeyException
    //   1096	1103	1226	java/security/UnrecoverableKeyException
    //   890	917	1267	java/security/KeyStoreException
    //   922	931	1267	java/security/KeyStoreException
    //   931	950	1267	java/security/KeyStoreException
    //   957	963	1267	java/security/KeyStoreException
    //   968	1002	1267	java/security/KeyStoreException
    //   1006	1039	1267	java/security/KeyStoreException
    //   1039	1053	1267	java/security/KeyStoreException
    //   1250	1257	1267	java/security/KeyStoreException
    //   890	917	1283	java/security/cert/CertificateException
    //   922	931	1283	java/security/cert/CertificateException
    //   931	950	1283	java/security/cert/CertificateException
    //   957	963	1283	java/security/cert/CertificateException
    //   968	1002	1283	java/security/cert/CertificateException
    //   1006	1039	1283	java/security/cert/CertificateException
    //   1039	1053	1283	java/security/cert/CertificateException
    //   1250	1257	1283	java/security/cert/CertificateException
    //   890	917	1299	java/io/FileNotFoundException
    //   922	931	1299	java/io/FileNotFoundException
    //   931	950	1299	java/io/FileNotFoundException
    //   957	963	1299	java/io/FileNotFoundException
    //   968	1002	1299	java/io/FileNotFoundException
    //   1006	1039	1299	java/io/FileNotFoundException
    //   1039	1053	1299	java/io/FileNotFoundException
    //   1250	1257	1299	java/io/FileNotFoundException
    //   890	917	1315	java/io/IOException
    //   922	931	1315	java/io/IOException
    //   931	950	1315	java/io/IOException
    //   957	963	1315	java/io/IOException
    //   968	1002	1315	java/io/IOException
    //   1006	1039	1315	java/io/IOException
    //   1039	1053	1315	java/io/IOException
    //   1250	1257	1315	java/io/IOException
  }

  public static boolean isSupportedOnJVM()
    throws LinkageError, ExceptionInInitializerError
  {
    try
    {
      Class.forName("javax.net.ssl.SSLServerSocketFactory");
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    return false;
  }

  private boolean keyValid(String paramString)
  {
    for (int i = 0; ; i++)
      if ((i >= propertyKeys.length) || (propertyKeys[i].equals(paramString)))
      {
        if (i >= propertyKeys.length)
          break;
        return true;
      }
    return false;
  }

  public static String obfuscate(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null)
      return null;
    byte[] arrayOfByte = toByte(paramArrayOfChar);
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfByte[i] = ((byte)(0xFF & (arrayOfByte[i] ^ key[(i % key.length)])));
    return "{xor}" + new String(SimpleBase64Encoder.encode(arrayOfByte));
  }

  public static String packCipherSuites(String[] paramArrayOfString)
  {
    String str = null;
    if (paramArrayOfString != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        localStringBuffer.append(paramArrayOfString[i]);
        if (i < -1 + paramArrayOfString.length)
          localStringBuffer.append(',');
      }
      str = localStringBuffer.toString();
    }
    return str;
  }

  public static byte[] toByte(char[] paramArrayOfChar)
  {
    byte[] arrayOfByte;
    if (paramArrayOfChar == null)
      arrayOfByte = null;
    while (true)
    {
      return arrayOfByte;
      arrayOfByte = new byte[2 * paramArrayOfChar.length];
      int i = 0;
      int m;
      for (int j = 0; j < paramArrayOfChar.length; j = m)
      {
        int k = i + 1;
        arrayOfByte[i] = ((byte)(0xFF & paramArrayOfChar[j]));
        i = k + 1;
        m = j + 1;
        arrayOfByte[k] = ((byte)(0xFF & paramArrayOfChar[j] >> '\b'));
      }
    }
  }

  public static char[] toChar(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar;
    if (paramArrayOfByte == null)
      arrayOfChar = null;
    while (true)
    {
      return arrayOfChar;
      arrayOfChar = new char[paramArrayOfByte.length / 2];
      int i = 0;
      int k;
      for (int j = 0; i < paramArrayOfByte.length; j = k)
      {
        k = j + 1;
        int m = i + 1;
        int n = 0xFF & paramArrayOfByte[i];
        i = m + 1;
        arrayOfChar[j] = ((char)(n + ((0xFF & paramArrayOfByte[m]) << 8)));
      }
    }
  }

  public static String[] unpackCipherSuites(String paramString)
  {
    if (paramString == null)
      return null;
    Vector localVector = new Vector();
    int i = paramString.indexOf(',');
    int j = 0;
    while (i > -1)
    {
      localVector.add(paramString.substring(j, i));
      j = i + 1;
      i = paramString.indexOf(',', j);
    }
    localVector.add(paramString.substring(j));
    String[] arrayOfString = new String[localVector.size()];
    localVector.toArray(arrayOfString);
    return arrayOfString;
  }

  public SSLSocketFactory createSocketFactory(String paramString)
    throws MqttSecurityException
  {
    SSLContext localSSLContext = getSSLContext(paramString);
    Logger localLogger;
    Object[] arrayOfObject;
    String str1;
    if (this.logger != null)
    {
      localLogger = this.logger;
      arrayOfObject = new Object[2];
      if (paramString == null)
        break label82;
      str1 = paramString;
      arrayOfObject[0] = str1;
      if (getEnabledCipherSuites(paramString) == null)
        break label90;
    }
    label82: label90: for (String str2 = getProperty(paramString, "com.ibm.ssl.enabledCipherSuites", null); ; str2 = "null (using platform-enabled cipher suites)")
    {
      arrayOfObject[1] = str2;
      localLogger.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "createSocketFactory", "12020", arrayOfObject);
      return localSSLContext.getSocketFactory();
      str1 = "null (broker defaults)";
      break;
    }
  }

  public boolean getClientAuthentication(String paramString)
  {
    String str = getProperty(paramString, "com.ibm.ssl.clientAuthentication", null);
    boolean bool = false;
    if (str != null)
      bool = Boolean.valueOf(str).booleanValue();
    return bool;
  }

  public Properties getConfiguration(String paramString)
  {
    if (paramString == null);
    for (Object localObject = this.defaultProperties; ; localObject = this.configs.get(paramString))
      return (Properties)localObject;
  }

  public String[] getEnabledCipherSuites(String paramString)
  {
    return unpackCipherSuites(getProperty(paramString, "com.ibm.ssl.enabledCipherSuites", null));
  }

  public String getJSSEProvider(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.contextProvider", null);
  }

  public String getKeyManager(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.keyManager", "ssl.KeyManagerFactory.algorithm");
  }

  public String getKeyStore(String paramString)
  {
    String str = getPropertyFromConfig(paramString, "com.ibm.ssl.keyStore");
    if (str != null)
      return str;
    if ("javax.net.ssl.keyStore" != null)
      str = System.getProperty("javax.net.ssl.keyStore");
    return str;
  }

  public char[] getKeyStorePassword(String paramString)
  {
    String str = getProperty(paramString, "com.ibm.ssl.keyStorePassword", "javax.net.ssl.keyStorePassword");
    char[] arrayOfChar = null;
    if (str != null)
    {
      if (str.startsWith("{xor}"))
        arrayOfChar = deObfuscate(str);
    }
    else
      return arrayOfChar;
    return str.toCharArray();
  }

  public String getKeyStoreProvider(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.keyStoreProvider", null);
  }

  public String getKeyStoreType(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.keyStoreType", "javax.net.ssl.keyStoreType");
  }

  public String getSSLProtocol(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.protocol", null);
  }

  public String getTrustManager(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.trustManager", "ssl.TrustManagerFactory.algorithm");
  }

  public String getTrustStore(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.trustStore", "javax.net.ssl.trustStore");
  }

  public char[] getTrustStorePassword(String paramString)
  {
    String str = getProperty(paramString, "com.ibm.ssl.trustStorePassword", "javax.net.ssl.trustStorePassword");
    char[] arrayOfChar = null;
    if (str != null)
    {
      if (str.startsWith("{xor}"))
        arrayOfChar = deObfuscate(str);
    }
    else
      return arrayOfChar;
    return str.toCharArray();
  }

  public String getTrustStoreProvider(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.trustStoreProvider", null);
  }

  public String getTrustStoreType(String paramString)
  {
    return getProperty(paramString, "com.ibm.ssl.trustStoreType", null);
  }

  public void initialize(Properties paramProperties, String paramString)
    throws IllegalArgumentException
  {
    checkPropertyKeys(paramProperties);
    Properties localProperties = new Properties();
    localProperties.putAll(paramProperties);
    convertPassword(localProperties);
    if (paramString != null)
    {
      this.configs.put(paramString, localProperties);
      return;
    }
    this.defaultProperties = localProperties;
  }

  public void merge(Properties paramProperties, String paramString)
    throws IllegalArgumentException
  {
    checkPropertyKeys(paramProperties);
    Properties localProperties = this.defaultProperties;
    if (paramString != null)
      localProperties = (Properties)this.configs.get(paramString);
    if (localProperties == null)
      localProperties = new Properties();
    convertPassword(paramProperties);
    localProperties.putAll(paramProperties);
    if (paramString != null)
    {
      this.configs.put(paramString, localProperties);
      return;
    }
    this.defaultProperties = localProperties;
  }

  public boolean remove(String paramString)
  {
    boolean bool;
    if (paramString != null)
      if (this.configs.remove(paramString) != null)
        bool = true;
    Properties localProperties;
    do
    {
      return bool;
      return false;
      localProperties = this.defaultProperties;
      bool = false;
    }
    while (localProperties == null);
    this.defaultProperties = null;
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory
 * JD-Core Version:    0.6.2
 */