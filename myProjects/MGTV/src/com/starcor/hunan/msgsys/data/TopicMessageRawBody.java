package com.starcor.hunan.msgsys.data;

import java.util.ArrayList;

public class TopicMessageRawBody
{
  protected String mAction = "";
  protected String mAction_args = "";
  protected int mAlter = 1;
  protected String mBody = "";
  protected int mDialogway = 1;
  protected String mDialogway_args = "";
  protected String mExt = "";
  protected String mLaunch_image = "";
  protected ArrayList<MessageButtonBody> mMessageButtonBodies = null;
  protected String mMessage_type = "";
  protected String mTitle = "";

  public String getAction()
  {
    return this.mAction;
  }

  public String getAction_args()
  {
    return this.mAction_args;
  }

  public int getAlter()
  {
    return this.mAlter;
  }

  public String getBody()
  {
    return this.mBody;
  }

  public int getDialogway()
  {
    return this.mDialogway;
  }

  public String getDialogway_args()
  {
    return this.mDialogway_args;
  }

  public String getExt()
  {
    return this.mExt;
  }

  public String getLaunch_image()
  {
    return this.mLaunch_image;
  }

  public ArrayList<MessageButtonBody> getMessageButtonBodies()
  {
    return this.mMessageButtonBodies;
  }

  public String getMessage_type()
  {
    return this.mMessage_type;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public void setAction(String paramString)
  {
    this.mAction = paramString;
  }

  public void setAction_args(String paramString)
  {
    this.mAction_args = paramString;
  }

  public void setAlter(int paramInt)
  {
    this.mAlter = paramInt;
  }

  public void setBody(String paramString)
  {
    this.mBody = paramString;
  }

  public void setDialogway(int paramInt)
  {
    this.mDialogway = paramInt;
  }

  public void setDialogway_args(String paramString)
  {
    this.mDialogway_args = paramString;
  }

  public void setExt(String paramString)
  {
    this.mExt = paramString;
  }

  public void setLaunch_image(String paramString)
  {
    this.mLaunch_image = paramString;
  }

  public void setMessageButtonBodies(ArrayList<MessageButtonBody> paramArrayList)
  {
    this.mMessageButtonBodies = paramArrayList;
  }

  public void setMessage_type(String paramString)
  {
    this.mMessage_type = paramString;
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.TopicMessageRawBody
 * JD-Core Version:    0.6.2
 */