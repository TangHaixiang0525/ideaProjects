package com.starcor.core.logic.domain;

import com.starcor.core.logic.UserCPLLogic;
import java.io.Serializable;

public class LocalPlayRecordKey
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String videoId;
  public int videoIndex;

  private UserCPLLogic getOuterType()
  {
    return UserCPLLogic.getInstance();
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    LocalPlayRecordKey localLocalPlayRecordKey;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (getClass() != paramObject.getClass())
        return false;
      localLocalPlayRecordKey = (LocalPlayRecordKey)paramObject;
      if (!getOuterType().equals(localLocalPlayRecordKey.getOuterType()))
        return false;
      if (this.videoId == null)
      {
        if (localLocalPlayRecordKey.videoId != null)
          return false;
      }
      else if (!this.videoId.equals(localLocalPlayRecordKey.videoId))
        return false;
    }
    while (this.videoIndex == localLocalPlayRecordKey.videoIndex);
    return false;
  }

  public int hashCode()
  {
    int i = 31 * (31 + getOuterType().hashCode());
    if (this.videoId == null);
    for (int j = 0; ; j = this.videoId.hashCode())
      return 31 * (i + j) + this.videoIndex;
  }

  public String toString()
  {
    return "LocalPlayRecordKey [videoId=" + this.videoId + ", videoIndex=" + this.videoIndex + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.logic.domain.LocalPlayRecordKey
 * JD-Core Version:    0.6.2
 */