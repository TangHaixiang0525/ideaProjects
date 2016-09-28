package com.intertrust.wasabi;

public final class Runtime
{
  static
  {
    System.loadLibrary("WasabiJni");
  }

  public static void checkLicense(String paramString)
    throws ErrorCodeException
  {
    if (paramString == null)
      throw new NullPointerException("content_id cannot be null");
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.checkLicense(paramString));
  }

  public static Object getProperty(Property paramProperty)
    throws ErrorCodeException
  {
    if (paramProperty == null)
      throw new NullPointerException("property_id cannot be null");
    Object[] arrayOfObject = new Object[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.getProperty(paramProperty, arrayOfObject));
    return arrayOfObject[0];
  }

  public static void initialize()
    throws ErrorCodeException
  {
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.initializeEx(null, null));
  }

  public static void initialize(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    initializeEx(paramString, null);
  }

  public static void initializeEx(String paramString1, String paramString2)
    throws ErrorCodeException, NullPointerException
  {
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.initializeEx(paramString1, paramString2));
  }

  public static boolean isPersonalized()
  {
    return com.intertrust.wasabi.jni.Runtime.isPersonalized();
  }

  public static void personalize()
    throws ErrorCodeException
  {
    personalize(null);
  }

  public static void personalize(String paramString)
    throws ErrorCodeException
  {
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.personalize(paramString));
  }

  public static void processServiceToken(String paramString)
    throws ErrorCodeException
  {
    if (paramString == null)
      throw new NullPointerException("token parameter cannot be null");
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.processServiceToken(paramString));
  }

  public static void setProperty(Property paramProperty, Object paramObject)
    throws ErrorCodeException
  {
    if (paramProperty == null)
      throw new NullPointerException("property_id cannot be null");
    if (paramObject == null)
      throw new NullPointerException("value cannot be null");
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.setProperty(paramProperty, paramObject));
  }

  public static void shutdown()
    throws ErrorCodeException
  {
    ErrorCodeException.checkResult(com.intertrust.wasabi.jni.Runtime.shutdown());
  }

  public static enum Property
  {
    static
    {
      MS3_ACCEPT_HOSTNAME_MISMATCH = new Property("MS3_ACCEPT_HOSTNAME_MISMATCH", 1);
      STORAGE_DIRECTORY = new Property("STORAGE_DIRECTORY", 2);
      PROXY_DASH_CONTENT_LENGTH = new Property("PROXY_DASH_CONTENT_LENGTH", 3);
      ROOTED_OK = new Property("ROOTED_OK", 4);
      KEY_SPACE = new Property("KEY_SPACE", 5);
      NEMO_DEVICE_ID = new Property("NEMO_DEVICE_ID", 6);
      PERSONALITY_NODE_ID = new Property("PERSONALITY_NODE_ID", 7);
      Property[] arrayOfProperty = new Property[8];
      arrayOfProperty[0] = MS3_ACCEPT_SELF_SIGNED_CERTS;
      arrayOfProperty[1] = MS3_ACCEPT_HOSTNAME_MISMATCH;
      arrayOfProperty[2] = STORAGE_DIRECTORY;
      arrayOfProperty[3] = PROXY_DASH_CONTENT_LENGTH;
      arrayOfProperty[4] = ROOTED_OK;
      arrayOfProperty[5] = KEY_SPACE;
      arrayOfProperty[6] = NEMO_DEVICE_ID;
      arrayOfProperty[7] = PERSONALITY_NODE_ID;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.Runtime
 * JD-Core Version:    0.6.2
 */