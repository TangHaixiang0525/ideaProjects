package com.starcor.core.logic.domain;

import java.io.Serializable;

public class LocalPlayStateKey
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String videoId;
  public int videoIndex;

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof LocalPlayStateKey))
    {
      LocalPlayStateKey localLocalPlayStateKey = (LocalPlayStateKey)paramObject;
      if ((localLocalPlayStateKey.videoId != null) && (localLocalPlayStateKey.videoId.equals(this.videoId)) && (localLocalPlayStateKey.videoIndex == this.videoIndex))
        return true;
    }
    return false;
  }

  public int hashCode()
  {
    if (this.videoId == null);
    for (int i = 0; ; i = this.videoId.hashCode())
      return 31 * (i + 31) + this.videoIndex;
  }

  public String toString()
  {
    return "LocalPlayStateKey [videoId=" + this.videoId + ", videoIndex=" + this.videoIndex + ", hashCode=" + hashCode() + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.logic.domain.LocalPlayStateKey
 * JD-Core Version:    0.6.2
 */