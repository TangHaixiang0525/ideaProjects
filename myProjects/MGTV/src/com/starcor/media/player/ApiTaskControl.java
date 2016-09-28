package com.starcor.media.player;

import com.starcor.core.utils.Logger;
import java.util.ArrayList;

public class ApiTaskControl
{
  private static final String TAG = "ApiTaskControl";
  private ArrayList<ApiTaskLabel> taskLabelList = new ArrayList();

  public void addTaskLabel(int paramInt, String paramString1, String paramString2)
  {
    Logger.i("ApiTaskControl", "ApiTaskControl() taskId:" + paramInt + ", taskName:" + paramString1 + ", Label:" + paramString2);
    ApiTaskLabel localApiTaskLabel = new ApiTaskLabel(paramInt, paramString1, paramString2);
    this.taskLabelList.add(localApiTaskLabel);
  }

  public boolean checkSameTask(String paramString1, String paramString2)
  {
    if (this.taskLabelList == null)
      return false;
    for (int i = 0; i < this.taskLabelList.size(); i++)
    {
      String str1 = ((ApiTaskLabel)this.taskLabelList.get(i)).taskName;
      String str2 = ((ApiTaskLabel)this.taskLabelList.get(i)).Label;
      Logger.i("ApiTaskControl", "checkSameTask() taskName:" + paramString1 + ", apiLabel:" + str2 + ", apiName:" + str1);
      if ((str2.equals(paramString2)) && (str1.equals(paramString1)))
        return true;
    }
    Logger.i("ApiTaskControl", "checkSameTask() invalid taskName:" + paramString1);
    return false;
  }

  public boolean checkTaskValid(int paramInt, String paramString)
  {
    if ((this.taskLabelList == null) || (paramString == null))
      return false;
    for (int i = 0; i < this.taskLabelList.size(); i++)
    {
      String str1 = ((ApiTaskLabel)this.taskLabelList.get(i)).Label;
      String str2 = ((ApiTaskLabel)this.taskLabelList.get(i)).taskName;
      int j = ((ApiTaskLabel)this.taskLabelList.get(i)).taskId;
      Logger.i("ApiTaskControl", "checkTaskValid() taskId:" + paramInt + ", apiLabel:" + str1 + ", apiTaskId:" + j + ", apiName:" + str2);
      if ((str1.equals(paramString)) && (paramInt == j))
      {
        this.taskLabelList.remove(i);
        return true;
      }
    }
    Logger.i("ApiTaskControl", "checkTaskValid() invalid taskId:" + paramInt);
    return false;
  }

  public void clear()
  {
    Logger.i("ApiTaskControl", "ApiTaskControl() clear");
    if (this.taskLabelList != null)
      this.taskLabelList.clear();
  }

  private class ApiTaskLabel
  {
    public String Label = "";
    public int taskId = -1;
    public String taskName = "";

    public ApiTaskLabel(int paramString1, String paramString2, String arg4)
    {
      String str;
      Logger.i("ApiTaskControl", "ApiTaskLabel taskId:" + paramString1 + ", taskName:" + paramString2 + ", Label:" + str);
      this.taskId = paramString1;
      this.taskName = paramString2;
      this.Label = str;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.ApiTaskControl
 * JD-Core Version:    0.6.2
 */