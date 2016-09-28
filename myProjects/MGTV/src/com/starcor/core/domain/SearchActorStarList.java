package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchActorStarList
  implements Serializable
{
  public int cur_page;
  public ArrayList<ActorStar> lists = new ArrayList();
  public String reason = "";
  public String state = "";
  public int total_page;
  public int total_rows;

  public String toString()
  {
    return "SearchActorStarList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", cur_page=" + this.cur_page + ", total_page=" + this.total_page + ", total_rows=" + this.total_rows + ", lists=" + this.lists + '}';
  }

  public class ActorStar
    implements Serializable
  {
    public String alias_name;
    public String en_name;
    public String id;
    public String img_h;
    public String img_s;
    public String img_v;
    public String info;
    public String label_id;
    public String label_id_v2;
    public String name;
    public String old_name;
    public String sex;
    public String type;

    public ActorStar()
    {
    }

    public String toString()
    {
      return "ActorStar{type='" + this.type + '\'' + ", id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", alias_name='" + this.alias_name + '\'' + ", img_h='" + this.img_h + '\'' + ", img_s='" + this.img_s + '\'' + ", img_v='" + this.img_v + '\'' + ", info='" + this.info + '\'' + ", label_id='" + this.label_id + '\'' + ", sex='" + this.sex + '\'' + ", old_name='" + this.old_name + '\'' + ", en_name='" + this.en_name + '\'' + ", label_id_v2='" + this.label_id_v2 + '\'' + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SearchActorStarList
 * JD-Core Version:    0.6.2
 */