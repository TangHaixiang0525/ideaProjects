package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class FAQStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public List<FAQItem> mItems;
  public String name = "";
  public int type;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.FAQStruct
 * JD-Core Version:    0.6.2
 */