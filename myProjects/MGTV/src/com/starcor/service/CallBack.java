package com.starcor.service;

import com.starcor.core.domain.Version;

public abstract interface CallBack
{
  public abstract void doErrorLogic(ErrorCode paramErrorCode);

  public abstract void doTheNextThings();

  public abstract void doUpgrade(Version paramVersion);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.CallBack
 * JD-Core Version:    0.6.2
 */