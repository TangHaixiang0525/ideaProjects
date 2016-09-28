package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class InjectingID
  implements Serializable
{
  public ArrayList<Item> items = new ArrayList();
  public String reason = "";
  public String state = "";

  public String toString()
  {
    return "InjectingID{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", items=" + this.items + '}';
  }

  public class Item
  {
    public String id;
    public String importId;
    public String importSource;
    public String name;
    public String originalId;
    public String type;

    public Item()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.InjectingID
 * JD-Core Version:    0.6.2
 */