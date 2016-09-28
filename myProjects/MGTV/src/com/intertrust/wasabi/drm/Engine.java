package com.intertrust.wasabi.drm;

import com.intertrust.wasabi.Attribute;
import com.intertrust.wasabi.ErrorCodeException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Engine
{
  public static final String BUILD_DATE_PROPERTY = "BuildDate";
  public static final String CACHE_POLICY_PROPERTY = "CachePolicy";
  public static final String HTTPS_PROXY_HOST_NAME_PROPERTY = "HttpsProxyHostname";
  public static final String HTTPS_PROXY_PORT_PROPERTY = "HttpsProxyPort";
  public static final String HTTP_CONNECTION_TIMEOUT_PROPERTY = "HttpConnectionTimeout";
  public static final String HTTP_IO_TIMEOUT_PROPERTY = "HttpIOTimeout";
  public static final String HTTP_PROXY_ENABLED_PROPERTY = "HttpProxyEnabled";
  public static final String HTTP_PROXY_HOST_NAME_PROPERTY = "HttpProxyHostname";
  public static final String HTTP_PROXY_PORT_PROPERTY = "HttpProxyPort";
  public static final String HTTP_PROXY_SYSTEM_DEFAULT_PROPERTY = "HttpProxySystemDefault";
  public static final String IS_PERSONALIZED_PROPERTY = "IsPersonalized";
  public static final String NEMO_PROPERTY = "Nemo";
  public static final String PERSONALITY_PROPERTY = "Personality";
  public static final Integer POLICY_DO_NOT_RELOAD_LINKS = new Integer(0);
  public static final Integer POLICY_RELOAD_LINKS = new Integer(1);
  public static final String PREFERRED_LANGUAGES_PROPERTY = "PreferredLanguages";
  public static final int VACUUM_FLAG_DONT_SAVE_RECENT_LINK = 8;
  public static final int VACUUM_FLAG_LINKS = 2;
  public static final int VACUUM_FLAG_NODES = 1;
  public static final int VACUUM_FLAG_SEASHELL = 4;
  public static final int VACUUM_FLAG_WIPE = 16;
  public static final String VERSION_PROPERTY = "Version";
  private long jniPeer;
  private List<TransactionListener> transactionListeners = new LinkedList();

  public Engine()
    throws ErrorCodeException
  {
    long[] arrayOfLong = new long[1];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.create(this, arrayOfLong));
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.setProperty(arrayOfLong[0], "CachePolicy", POLICY_RELOAD_LINKS));
      this.jniPeer = arrayOfLong[0];
      return;
    }
    finally
    {
    }
  }

  public void addTransactionListener(TransactionListener paramTransactionListener)
    throws NullPointerException
  {
    if (paramTransactionListener == null)
      throw new NullPointerException("listener cannot be null");
    try
    {
      this.transactionListeners.add(paramTransactionListener);
      return;
    }
    finally
    {
    }
  }

  public void destroy()
  {
    try
    {
      if (this.jniPeer != 0L)
      {
        com.intertrust.wasabi.drm.jni.Engine.destroy(this.jniPeer);
        this.jniPeer = 0L;
      }
      return;
    }
    finally
    {
    }
  }

  long getJniPeer()
  {
    return this.jniPeer;
  }

  public String[] getLinkIds()
    throws ErrorCodeException
  {
    String[][] arrayOfString; = new String[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getLinkIds(this.jniPeer, arrayOfString;));
      return arrayOfString;[0];
    }
    finally
    {
    }
  }

  public String[] getNodeIds()
    throws ErrorCodeException
  {
    String[][] arrayOfString; = new String[1][];
    ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getNodeIds(this.jniPeer, arrayOfString;));
    return arrayOfString;[0];
  }

  public Attribute getObjectDetails(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    if (paramString == null)
      throw new NullPointerException("objectId parameter cannot be null");
    Attribute[] arrayOfAttribute = new Attribute[1];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getObjectDetails(this.jniPeer, paramString, arrayOfAttribute));
      return arrayOfAttribute[0];
    }
    finally
    {
    }
  }

  public Object getProperty(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    if (paramString == null)
      throw new NullPointerException("name argument cannot be null");
    Object[] arrayOfObject = new Object[1];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getProperty(this.jniPeer, paramString, arrayOfObject));
      return arrayOfObject[0];
    }
    finally
    {
    }
  }

  public String[] getPropertyNames()
    throws ErrorCodeException
  {
    String[][] arrayOfString; = new String[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getPropertyNames(this.jniPeer, arrayOfString;));
      return arrayOfString;[0];
    }
    finally
    {
    }
  }

  public Service[] getServices()
    throws ErrorCodeException
  {
    Service[][] arrayOfService; = new Service[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getServices(this.jniPeer, arrayOfService;));
      [Lcom.intertrust.wasabi.drm.Service localService; = arrayOfService;[0];
      int i = localService;.length;
      for (int j = 0; j < i; j++)
        localService;[j].setEngine(this);
    }
    finally
    {
    }
    return arrayOfService;[0];
  }

  public DateTime getTrustedTime()
    throws ErrorCodeException
  {
    DateTime localDateTime = new DateTime();
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getTrustedTime(this.jniPeer, localDateTime));
      return localDateTime;
    }
    finally
    {
    }
  }

  public boolean isPersonalized()
  {
    try
    {
      boolean bool = com.intertrust.wasabi.drm.jni.Engine.isPersonalized(this.jniPeer);
      return bool;
    }
    finally
    {
    }
  }

  void onLicenseDataReceived(byte[] paramArrayOfByte)
  {
    Iterator localIterator = new LinkedList(this.transactionListeners).iterator();
    while (localIterator.hasNext())
      ((TransactionListener)localIterator.next()).onLicenseDataReceived(paramArrayOfByte);
  }

  void onTransactionBegin(int paramInt)
  {
    Iterator localIterator = new LinkedList(this.transactionListeners).iterator();
    while (localIterator.hasNext())
      ((TransactionListener)localIterator.next()).onTransactionBegin(TransactionType.values()[paramInt]);
  }

  void onTransactionEnd(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    Iterator localIterator = new LinkedList(this.transactionListeners).iterator();
    while (localIterator.hasNext())
      ((TransactionListener)localIterator.next()).onTransactionEnd(TransactionType.values()[paramInt1], paramInt2, paramString1, paramString2);
  }

  void onTransactionProgress(int paramInt1, int paramInt2, int paramInt3)
  {
    Iterator localIterator = new LinkedList(this.transactionListeners).iterator();
    while (localIterator.hasNext())
      ((TransactionListener)localIterator.next()).onTransactionProgress(TransactionType.values()[paramInt1], paramInt2, paramInt3);
  }

  public void personalize(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.personalize(this.jniPeer, paramString));
      return;
    }
    finally
    {
    }
  }

  public void processServiceToken(String paramString)
    throws ErrorCodeException
  {
    if (paramString == null)
      throw new NullPointerException("actionToken parameter cannot be null");
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.processServiceToken(this.jniPeer, paramString));
      return;
    }
    finally
    {
    }
  }

  public void removeTransactionListener(TransactionListener paramTransactionListener)
    throws NullPointerException
  {
    if (paramTransactionListener == null)
      throw new NullPointerException("listener cannot be null");
    try
    {
      this.transactionListeners.remove(paramTransactionListener);
      return;
    }
    finally
    {
    }
  }

  public void setProperty(String paramString, Object paramObject)
    throws ErrorCodeException, NullPointerException
  {
    if ((paramString == null) || (paramObject == null))
      throw new NullPointerException("parameters cannot be null");
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.setProperty(this.jniPeer, paramString, paramObject));
      return;
    }
    finally
    {
    }
  }

  public String transformUriTemplate(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    if (paramString == null)
      throw new NullPointerException("template parameter cannot be null");
    String[] arrayOfString = new String[1];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.transformUriTemplate(this.jniPeer, paramString, arrayOfString));
      return arrayOfString[0];
    }
    finally
    {
    }
  }

  public void updateSecurityData(String paramString, int paramInt)
    throws ErrorCodeException, NullPointerException
  {
    if (paramString == null)
      throw new NullPointerException("serviceId parameter cannot be null");
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.updateSecurityData(this.jniPeer, paramString, paramInt));
      return;
    }
    finally
    {
    }
  }

  public void vacuumData(int paramInt)
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.vacuumData(this.jniPeer, paramInt));
      return;
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.Engine
 * JD-Core Version:    0.6.2
 */