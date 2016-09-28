package com.intertrust.wasabi.drm;

import com.intertrust.wasabi.ErrorCodeException;

public final class Service
{
  private Engine engine;
  private String name;
  private String uid;

  Service(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.uid = paramString2;
  }

  Engine getEngine()
  {
    return this.engine;
  }

  public String getName()
  {
    return this.name;
  }

  public String getUid()
  {
    return this.uid;
  }

  public User[] getUsers()
    throws ErrorCodeException
  {
    User[][] arrayOfUser; = new User[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.Engine.getServiceUsers(this.engine.getJniPeer(), this.uid, arrayOfUser;));
      [Lcom.intertrust.wasabi.drm.User localUser; = arrayOfUser;[0];
      int i = localUser;.length;
      for (int j = 0; j < i; j++)
        localUser;[j].setService(this);
    }
    finally
    {
    }
    return arrayOfUser;[0];
  }

  void setEngine(Engine paramEngine)
  {
    this.engine = paramEngine;
  }

  public String toString()
  {
    String str = "{name: " + this.name + ", uid: " + this.uid;
    return str + "}";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.Service
 * JD-Core Version:    0.6.2
 */