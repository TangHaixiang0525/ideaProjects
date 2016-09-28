package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class UserAuthV2
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<UserKey> list;
  public AuthState state;

  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      localCloneNotSupportedException.printStackTrace();
    }
    return null;
  }

  public void setList(ArrayList<UserKey> paramArrayList)
  {
    this.list = paramArrayList;
  }

  public void setState(AuthState paramAuthState)
  {
    this.state = paramAuthState;
  }

  public String toString()
  {
    String str = "";
    if (this.list.size() > 0)
      for (int i = 0; i < this.list.size(); i++)
        str = str + ((UserKey)this.list.get(i)).toString();
    return "UserAuthV2 [state=" + this.state + ", list=" + str + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserAuthV2
 * JD-Core Version:    0.6.2
 */