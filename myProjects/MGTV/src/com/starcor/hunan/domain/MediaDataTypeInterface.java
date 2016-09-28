package com.starcor.hunan.domain;

import com.starcor.hunan.service.CollectAndPlayListLogic;

public abstract interface MediaDataTypeInterface
{
  public abstract void dealMediaDataAndUploadMediaData();

  public abstract void getUnLoginList();

  public abstract void setCollectAndPlayListLogicObject(CollectAndPlayListLogic paramCollectAndPlayListLogic);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.MediaDataTypeInterface
 * JD-Core Version:    0.6.2
 */