package com.starcor.hunan.msgsys.data.admintopic;

import com.starcor.core.utils.Logger;

public class AdminTopicMessageData
{
  private String mMpns_admin_action = "update-action-levels";
  private AdminTopicSubData mMpns_admin_message = new AdminTopicSubData();

  public String getMpns_admin_action()
  {
    return this.mMpns_admin_action;
  }

  public AdminTopicSubData getMpns_admin_message()
  {
    return this.mMpns_admin_message;
  }

  public void setMpns_admin_action(String paramString)
  {
    this.mMpns_admin_action = paramString;
  }

  public void showInfo()
  {
    Logger.i("AdminTopicMessageData", "AdminInfo:");
    Logger.i("AdminTopicMessageData", "mMpns_admin_action=" + this.mMpns_admin_action);
    Logger.i("AdminTopicMessageData", "AdminTopicSubData:");
    if (this.mMpns_admin_action != null)
    {
      if (!this.mMpns_admin_action.equals("update-action-levels"))
        break label181;
      Logger.i("AdminTopicMessageData", "message=" + this.mMpns_admin_message.getMessage());
      Logger.i("AdminTopicMessageData", "page=" + this.mMpns_admin_message.getPage());
      Logger.i("AdminTopicMessageData", "video=" + this.mMpns_admin_message.getVideo());
      Logger.i("AdminTopicMessageData", "detail=" + this.mMpns_admin_message.getDetail());
    }
    label181: 
    do
      while (true)
      {
        return;
        if (!this.mMpns_admin_action.equals("clear-messages"))
          break;
        Logger.i("AdminTopicMessageData", "message_ids:");
        String[] arrayOfString = this.mMpns_admin_message.getMessage_ids();
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++)
          Logger.i("AdminTopicMessageData", arrayOfString[j]);
      }
    while (!this.mMpns_admin_action.equals("disconnect"));
    Logger.i("AdminTopicMessageData", "disconnect:");
    Logger.i("AdminTopicMessageData", "when=" + this.mMpns_admin_message.getWhen());
    Logger.i("AdminTopicMessageData", "next_connect_time=" + this.mMpns_admin_message.getNext_connect_time());
  }

  public String toString()
  {
    String str = "mpns_admin_action=" + this.mMpns_admin_action;
    if (this.mMpns_admin_action != null)
    {
      if (!this.mMpns_admin_action.equals("update-action-levels"))
        break label119;
      str = str + "\nmessage=" + this.mMpns_admin_message.getMessage() + "\npage=" + this.mMpns_admin_message.getPage() + "\nvideo=" + this.mMpns_admin_message.getVideo() + "\ndetail=" + this.mMpns_admin_message.getDetail();
    }
    label119: 
    do
      while (true)
      {
        return str;
        if (!this.mMpns_admin_action.equals("clear-messages"))
          break;
        str = str + "\nmessage_ids=\n";
        String[] arrayOfString = this.mMpns_admin_message.getMessage_ids();
        if ((arrayOfString != null) && (arrayOfString.length > 0))
        {
          str = str + arrayOfString[0] + "\n";
          for (int i = 1; i < arrayOfString.length; i++)
            str = str + arrayOfString[i] + "\n";
        }
      }
    while (!this.mMpns_admin_action.equals("disconnect"));
    Logger.i("AdminTopicMessageData", "disconnect:");
    return str + "\nwhen=" + this.mMpns_admin_message.getWhen() + "\nnext_connect_time=" + this.mMpns_admin_message.getNext_connect_time();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.admintopic.AdminTopicMessageData
 * JD-Core Version:    0.6.2
 */