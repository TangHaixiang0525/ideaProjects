package com.starcor.core.domain;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntity;

public abstract class BaseParams
{
  public HttpEntity getEntity()
  {
    return new MultipartEntity();
  }

  public abstract void updateData();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.BaseParams
 * JD-Core Version:    0.6.2
 */