package com.starcor.jump.bussines.data;

public class PageControlData extends CommonData
{
  public String id = "";
  public String value = "";

  protected void processWithData()
  {
    this.id = processStringValue("id", this.id);
    this.value = processStringValue("value", this.value);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.data.PageControlData
 * JD-Core Version:    0.6.2
 */