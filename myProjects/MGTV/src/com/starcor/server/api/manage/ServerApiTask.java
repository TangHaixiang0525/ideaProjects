package com.starcor.server.api.manage;

import com.starcor.httpapi.core.SCHttpApiTask;
import com.starcor.httpapi.core.SCResponse;

public abstract class ServerApiTask extends SCHttpApiTask
{
  public String getUrl()
  {
    return null;
  }

  public void perform(SCResponse paramSCResponse)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.server.api.manage.ServerApiTask
 * JD-Core Version:    0.6.2
 */