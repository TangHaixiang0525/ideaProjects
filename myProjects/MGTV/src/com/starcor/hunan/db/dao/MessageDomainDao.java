package com.starcor.hunan.db.dao;

import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.domain.MessageDomain;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageDomainDao
{
  private static final String MESSAGE_DATA_FILE_NAME = "messagedomain.dat";
  private static final String TAG = MessageDomainDao.class.getSimpleName();
  private boolean IsInitData = false;
  private int autoincrease = 1;
  File file = null;
  private List<MessageDomain> messagedomains = null;
  private String path;

  private void writetoFile()
  {
    File localFile = new File(this.path);
    IOTools.writeObject(localFile, this.messagedomains);
    Logger.i(TAG, "写消息数据文件到磁盘 file.exits:" + localFile.exists());
  }

  public void addMessageDomain(MessageDomain paramMessageDomain)
  {
    if (!this.IsInitData)
    {
      Logger.i(TAG, "addMessageDomain Data not init");
      return;
    }
    if ((paramMessageDomain == null) || (this.messagedomains.contains(paramMessageDomain)))
    {
      Logger.i(TAG, "addMessageDomain Data is null");
      return;
    }
    if (this.messagedomains.size() >= 30)
      for (int j = 0; j < -30 + this.messagedomains.size(); j++)
      {
        long l = this.autoincrease;
        int k = -1;
        for (int m = 0; m < this.messagedomains.size(); m++)
          if (((MessageDomain)this.messagedomains.get(m)).getMessageId() < l)
          {
            l = ((MessageDomain)this.messagedomains.get(m)).getMessageId();
            k = m;
          }
        if (k != -1)
          this.messagedomains.remove(k);
      }
    int i = 1 + this.autoincrease;
    this.autoincrease = i;
    paramMessageDomain.setMessageId(i);
    Logger.i(TAG, "addMessageDomain size=" + this.messagedomains.size());
    Logger.i(TAG, "addMessageDomain dao" + this.messagedomains.toString());
    this.messagedomains.add(paramMessageDomain);
    writetoFile();
  }

  public void clearnMessageDomain()
  {
    this.messagedomains.clear();
    writetoFile();
  }

  public List<MessageDomain> findAllMessageDomain()
  {
    File localFile = new File(this.path);
    if (localFile == null)
      return null;
    return (List)IOTools.readObject(localFile);
  }

  public MessageDomain findMessageDomain(int paramInt)
  {
    if (!this.IsInitData)
    {
      Logger.i(TAG, "removeMessageDomain Data not init");
      return null;
    }
    for (int i = 0; i < this.messagedomains.size(); i++)
      if (((MessageDomain)this.messagedomains.get(i)).getMessageId() == paramInt)
      {
        Logger.i(TAG, "findReservation film_type:" + ((MessageDomain)this.messagedomains.get(i)).getMessageId());
        return (MessageDomain)this.messagedomains.get(i);
      }
    return null;
  }

  public int getMaxMessageId()
  {
    List localList = (List)IOTools.readObject(new File(this.path));
    int i;
    if (localList == null)
      i = 0;
    while (true)
    {
      return i;
      i = 0;
      for (int j = 0; j < localList.size(); j++)
        if (((MessageDomain)localList.get(j)).getMessageId() > i)
          i = ((MessageDomain)localList.get(j)).getMessageId();
    }
  }

  public void initData(String paramString)
  {
    if (paramString == null)
      return;
    this.path = (paramString + "messagedomain.dat");
    Logger.i(TAG, "file path:" + this.path);
    File localFile = new File(this.path);
    this.messagedomains = null;
    if (localFile.exists());
    while (true)
    {
      try
      {
        this.messagedomains = ((List)IOTools.readObject(localFile));
        Logger.i(TAG, "read object file ok! ");
        if (this.messagedomains == null)
          this.messagedomains = new ArrayList();
        int i = -1 + this.messagedomains.size();
        if (i < 0)
          break;
        if (((MessageDomain)this.messagedomains.get(i)).getMessageId() > this.autoincrease)
          this.autoincrease = ((MessageDomain)this.messagedomains.get(i)).getMessageId();
        i--;
        continue;
      }
      catch (Exception localException)
      {
        Logger.i(TAG, "init fails data file error");
        continue;
      }
      Logger.i(TAG, "messagedomains data file is not exists");
    }
    Logger.i(TAG, "init finish,data count:" + this.messagedomains.size());
    this.IsInitData = true;
  }

  public void removeMessageDomain(int paramInt)
  {
    if (!this.IsInitData)
      Logger.i(TAG, "removeMessageDomain Data not init");
    while (true)
    {
      return;
      for (int i = -1 + this.messagedomains.size(); i >= 0; i--)
        if (((MessageDomain)this.messagedomains.get(i)).getMessageId() == paramInt)
        {
          this.messagedomains.remove(i);
          writetoFile();
          return;
        }
    }
  }

  public void writemessagetoFile(List<MessageDomain> paramList)
  {
    File localFile = new File(this.path);
    IOTools.writeObject(localFile, paramList);
    Logger.i(TAG, "写消息数据文件到磁盘 file.exits:" + localFile.exists());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.dao.MessageDomainDao
 * JD-Core Version:    0.6.2
 */