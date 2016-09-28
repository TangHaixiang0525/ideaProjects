package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ActorStarInfoList
  implements Serializable
{
  public ArrayList<ActorStarInfo> lists = new ArrayList();
  public String reason = "";
  public String state = "";

  public class ActorStarInfo
    implements Serializable
  {
    public String alias_name = "";
    public String area = "";
    public String birthday = "";
    public String blood_type = "";
    public String college = "";
    public String constellation = "";
    public String en_name = "";
    public String height = "";
    public String id = "";
    public String img_h = "";
    public String img_s = "";
    public String img_v = "";
    public String info = "";
    public String label_id = "";
    public String name = "";
    public String nation = "";
    public String old_name = "";
    public String profession = "";
    public String sex = "";
    public String story = "";
    public String type = "";
    public String weight = "";
    public String works = "";

    public ActorStarInfo()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ActorStarInfoList
 * JD-Core Version:    0.6.2
 */