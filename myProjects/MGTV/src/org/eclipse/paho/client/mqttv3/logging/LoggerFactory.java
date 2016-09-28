package org.eclipse.paho.client.mqttv3.logging;

import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoggerFactory
{
  public static final String MQTT_CLIENT_MSG_CAT = "org.eclipse.paho.client.mqttv3.internal.nls.logcat";
  private static final String className = LoggerFactory.class.getName();
  private static String jsr47LoggerClassName = "org.eclipse.paho.client.mqttv3.logging.JSR47Logger";
  private static String overrideloggerClassName = null;

  public static Logger getLogger(String paramString1, String paramString2)
  {
    String str = overrideloggerClassName;
    if (str == null)
      str = jsr47LoggerClassName;
    Logger localLogger = getLogger(str, ResourceBundle.getBundle(paramString1), paramString2, null);
    if (localLogger == null)
      throw new MissingResourceException("Error locating the logging class", className, paramString2);
    return localLogger;
  }

  // ERROR //
  private static Logger getLogger(String paramString1, ResourceBundle paramResourceBundle, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 64	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   4: astore 6
    //   6: aconst_null
    //   7: astore 7
    //   9: aload 6
    //   11: ifnull +23 -> 34
    //   14: aload 6
    //   16: invokevirtual 68	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   19: checkcast 70	org/eclipse/paho/client/mqttv3/logging/Logger
    //   22: astore 7
    //   24: aload 7
    //   26: aload_1
    //   27: aload_2
    //   28: aload_3
    //   29: invokeinterface 74 4 0
    //   34: aload 7
    //   36: areturn
    //   37: astore 5
    //   39: aconst_null
    //   40: areturn
    //   41: astore 4
    //   43: aconst_null
    //   44: areturn
    //   45: astore 11
    //   47: aconst_null
    //   48: areturn
    //   49: astore 10
    //   51: aconst_null
    //   52: areturn
    //   53: astore 9
    //   55: aconst_null
    //   56: areturn
    //   57: astore 8
    //   59: aconst_null
    //   60: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	37	java/lang/NoClassDefFoundError
    //   0	6	41	java/lang/ClassNotFoundException
    //   14	24	45	java/lang/IllegalAccessException
    //   14	24	49	java/lang/InstantiationException
    //   14	24	53	java/lang/ExceptionInInitializerError
    //   14	24	57	java/lang/SecurityException
  }

  public static String getLoggingProperty(String paramString)
  {
    try
    {
      Class localClass = Class.forName("java.util.logging.LogManager");
      Object localObject = localClass.getMethod("getLogManager", new Class[0]).invoke(null, null);
      String str = (String)localClass.getMethod("getProperty", new Class[] { String.class }).invoke(localObject, new Object[] { paramString });
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static void setLogger(String paramString)
  {
    overrideloggerClassName = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.logging.LoggerFactory
 * JD-Core Version:    0.6.2
 */