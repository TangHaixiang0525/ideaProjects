package com.starcor.apitask;

import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public abstract interface IApiTaskListener<T>
{
  public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

  public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, T paramT);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.apitask.IApiTaskListener
 * JD-Core Version:    0.6.2
 */