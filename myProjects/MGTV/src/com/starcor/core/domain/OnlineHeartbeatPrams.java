package com.starcor.core.domain;

import android.content.Context;
import com.starcor.core.epgapi.StringParams;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntity;

public class OnlineHeartbeatPrams extends HeartbeatPrams
{
  private StringParams onLineFlag = new StringParams("m");

  public OnlineHeartbeatPrams(Context paramContext)
  {
    super(paramContext);
    this.onLineFlag.setValue("online");
  }

  public HttpEntity getEntity()
  {
    MultipartEntity localMultipartEntity = (MultipartEntity)super.getEntity();
    localMultipartEntity.addPart(this.onLineFlag.getName(), this.onLineFlag.getBody());
    return localMultipartEntity;
  }

  public StringParams getOnLineFlag()
  {
    return this.onLineFlag;
  }

  public String toString()
  {
    return super.toString() + "OnlineHeartbeatPrams [onLineFlag=" + this.onLineFlag + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.OnlineHeartbeatPrams
 * JD-Core Version:    0.6.2
 */