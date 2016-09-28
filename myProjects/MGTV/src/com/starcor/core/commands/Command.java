package com.starcor.core.commands;

import android.content.Intent;
import com.starcor.core.utils.GeneralUtils;

public abstract class Command
{
  private String entrance_num;
  private String entrance_param = "";
  private String id = "";
  private String isToSplash = "0";
  private String[] name;

  public Command(String[] paramArrayOfString)
  {
    this.name = paramArrayOfString;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
      throw new IllegalArgumentException();
    this.id = GeneralUtils.MD5(paramArrayOfString[0]);
  }

  public static int getIntValue(Intent paramIntent, String paramString)
  {
    int i = -1;
    try
    {
      String str = paramIntent.getStringExtra(paramString);
      if (str != null)
      {
        int j = Integer.parseInt(str);
        i = j;
      }
      if (i == -1)
        i = paramIntent.getIntExtra(paramString, 0);
      return i;
    }
    catch (Exception localException)
    {
      while (true)
        i = -1;
    }
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Command;
    boolean bool2 = false;
    if (bool1)
    {
      Command localCommand = (Command)paramObject;
      boolean bool3 = this.id.equals(localCommand.getId());
      bool2 = false;
      if (bool3)
        bool2 = true;
    }
    return bool2;
  }

  public abstract void execute(Object paramObject);

  public String getEntrance_num()
  {
    return this.entrance_num;
  }

  public String getEntrance_param()
  {
    return this.entrance_param;
  }

  public String getId()
  {
    return this.id;
  }

  public String getIsToSplash()
  {
    return this.isToSplash;
  }

  public String[] getName()
  {
    return this.name;
  }

  public void setEntrance_num(String paramString)
  {
    this.entrance_num = paramString;
  }

  public void setEntrance_param(String paramString)
  {
    this.entrance_param = paramString;
  }

  public void setIsToSplash(String paramString)
  {
    this.isToSplash = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.commands.Command
 * JD-Core Version:    0.6.2
 */