package com.intertrust.wasabi.drm;

import com.intertrust.wasabi.Attribute;
import com.intertrust.wasabi.ErrorCodeException;

public final class User
{
  private Attribute details;
  private boolean isDeviceRegistered;
  private String name;
  private Service service;
  private String uid;

  User(String paramString1, String paramString2, boolean paramBoolean, Attribute paramAttribute)
  {
    this.name = paramString1;
    this.uid = paramString2;
    this.isDeviceRegistered = paramBoolean;
    this.details = paramAttribute;
  }

  public Attribute getDetails()
  {
    return this.details;
  }

  public String getName()
  {
    return this.name;
  }

  public Service getService()
  {
    return this.service;
  }

  public Subscription[] getSubscriptions()
    throws ErrorCodeException
  {
    Subscription[][] arrayOfSubscription; = new Subscription[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getServiceSubscriptions(this.service.getEngine().getJniPeer(), this.service.getUid(), this.uid, arrayOfSubscription;));
      [Lcom.intertrust.wasabi.drm.Subscription localSubscription; = arrayOfSubscription;[0];
      int i = localSubscription;.length;
      for (int j = 0; j < i; j++)
        localSubscription;[j].setUser(this);
    }
    finally
    {
    }
    return arrayOfSubscription;[0];
  }

  public String getUid()
  {
    return this.uid;
  }

  public boolean isDeviceRegistered()
  {
    return this.isDeviceRegistered;
  }

  void setService(Service paramService)
  {
    this.service = paramService;
  }

  public String toString()
  {
    String str = "{name: " + this.name + ", uid: " + this.uid + ", isDeviceRegistered " + this.isDeviceRegistered;
    if (this.details != null)
      str = str + ", details: " + this.details.toString();
    return str + "}";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.User
 * JD-Core Version:    0.6.2
 */