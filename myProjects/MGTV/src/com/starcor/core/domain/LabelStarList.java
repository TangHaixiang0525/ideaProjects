package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class LabelStarList
  implements Serializable
{
  public ArrayList<LabelStarItemStructure> l = new ArrayList();
  public String reason;
  public String state;

  public class LabelStarItemStructure
    implements Serializable
  {
    public String id = "";
    public ArrayList<LabelStarItemStructure> l = new ArrayList();
    public String name = "";
    public String type = "";

    public LabelStarItemStructure()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.LabelStarList
 * JD-Core Version:    0.6.2
 */