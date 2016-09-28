package com.starcor.core.domain;

import java.io.Serializable;

public class FAQItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String answer = "";
  public String question = "";

  public String toString()
  {
    return "FAQItem [question=" + this.question + ", answer=" + this.answer + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.FAQItem
 * JD-Core Version:    0.6.2
 */