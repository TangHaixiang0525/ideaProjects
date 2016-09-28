package com.starcor.core.domain;

import java.util.ArrayList;
import java.util.List;

public class Agreements
{
  public List<AgreementInfo> agreements = new ArrayList();
  public String reason = "";
  public String state = "";

  public String toString()
  {
    return "Agreements [state=" + this.state + ", reason=" + this.reason + ", agreements=" + this.agreements + "]";
  }

  public static final class AgreementInfo
  {
    public String action = "";
    public String info = "";
    public String name = "";
    public String type = "";

    public String toString()
    {
      return "AgreementInfo [type=" + this.type + ", info=" + this.info + ", name=" + this.name + ", action=" + this.action + "]";
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.Agreements
 * JD-Core Version:    0.6.2
 */