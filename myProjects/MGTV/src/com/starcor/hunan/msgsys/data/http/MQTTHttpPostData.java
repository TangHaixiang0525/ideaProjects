package com.starcor.hunan.msgsys.data.http;

import java.io.PrintStream;

public class MQTTHttpPostData
{
  private String mClient_id;
  private MQTTCredentials mCredentials = new MQTTCredentials();
  private MQTTLevels mLevels = new MQTTLevels();

  public String getClient_id()
  {
    return this.mClient_id;
  }

  public MQTTCredentials getCredentials()
  {
    return this.mCredentials;
  }

  public MQTTLevels getLevels()
  {
    return this.mLevels;
  }

  public void setClient_id(String paramString)
  {
    this.mClient_id = paramString;
  }

  public void showInfo()
  {
    System.out.println("mClient_id=" + this.mClient_id);
    System.out.println("In Credentials :");
    System.out.println("username=" + this.mCredentials.getUserName());
    System.out.println("password=" + this.mCredentials.getPassword());
    System.out.println("In Levels:");
    System.out.println("message=" + this.mLevels.getMessage());
    System.out.println("video=" + this.mLevels.getVideo());
    System.out.println("page=" + this.mLevels.getPage());
    System.out.println("detail=" + this.mLevels.getDetail());
  }

  public String toString()
  {
    return "client_id=" + this.mClient_id + "\nusername=" + this.mCredentials.getUserName() + "\npassword=" + this.mCredentials.getPassword() + "\nmessage=" + this.mLevels.getMessage() + "\nvideo=" + this.mLevels.getVideo() + "\npage=" + this.mLevels.getPage() + "\ndetail=" + this.mLevels.getDetail();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.http.MQTTHttpPostData
 * JD-Core Version:    0.6.2
 */