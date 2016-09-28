package com.starcor.core.domain;

public class Task
{
  public static final int CANNCEL = 2;
  public static final int FINISH = 3;
  public static final int RUNNING = 1;
  public static final int WAITING;
  protected int level;
  protected String name = "";
  protected int taskId;
  protected int taskState;

  public int getLevel()
  {
    return this.level;
  }

  public String getName()
  {
    return this.name;
  }

  public int getTaskId()
  {
    return this.taskId;
  }

  public int getTaskState()
  {
    return this.taskState;
  }

  public void setLevel(int paramInt)
  {
    this.level = paramInt;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setState(int paramInt)
  {
    this.taskState = paramInt;
  }

  public void setTaskId(int paramInt)
  {
    this.taskId = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.Task
 * JD-Core Version:    0.6.2
 */