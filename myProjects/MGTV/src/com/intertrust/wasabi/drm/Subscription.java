package com.intertrust.wasabi.drm;

import com.intertrust.wasabi.Attribute;

public final class Subscription
{
  private Attribute details;
  private DateTime expirationDate;
  private boolean isValid;
  private String name;
  private DateTime renewalDate;
  private String renewalUriTemplate;
  private String uid;
  private User user;

  Subscription(String paramString1, String paramString2, boolean paramBoolean, DateTime paramDateTime1, DateTime paramDateTime2, String paramString3, Attribute paramAttribute)
  {
    this.name = paramString1;
    this.uid = paramString2;
    this.isValid = paramBoolean;
    this.expirationDate = paramDateTime1;
    this.renewalDate = paramDateTime2;
    this.renewalUriTemplate = paramString3;
    this.details = paramAttribute;
  }

  public Attribute getDetails()
  {
    return this.details;
  }

  public DateTime getExpirationDate()
  {
    return this.expirationDate;
  }

  public String getName()
  {
    return this.name;
  }

  public DateTime getRenewalDate()
  {
    return this.renewalDate;
  }

  public String getRenewalUriTemplate()
  {
    return this.renewalUriTemplate;
  }

  public String getUid()
  {
    return this.uid;
  }

  public User getUser()
  {
    return this.user;
  }

  public boolean isValid()
  {
    return this.isValid;
  }

  void setUser(User paramUser)
  {
    this.user = paramUser;
  }

  public String toString()
  {
    String str = "{name: " + this.name + ", uid: " + this.uid + ", isValid " + this.isValid;
    if (this.expirationDate != null)
      str = str + ", expirationDate: " + this.expirationDate.toString();
    if (this.renewalDate != null)
      str = str + ", renewalDate: " + this.renewalDate.toString();
    if (this.renewalUriTemplate != null)
      str = str + ", renewalUriTemplate: " + this.renewalUriTemplate;
    if (this.details != null)
      str = str + ", details: " + this.details.toString();
    return str + "}";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.Subscription
 * JD-Core Version:    0.6.2
 */