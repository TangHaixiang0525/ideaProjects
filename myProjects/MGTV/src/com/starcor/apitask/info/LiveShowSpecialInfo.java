package com.starcor.apitask.info;

import java.util.ArrayList;
import java.util.List;

public class LiveShowSpecialInfo
{
  public List<SpecialItem> specialItems = new ArrayList();

  public static class SpecialItem
  {
    public String beginTime;
    public String endTime;
    public String linkUrl;
    public String name;
    public String special_id;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.apitask.info.LiveShowSpecialInfo
 * JD-Core Version:    0.6.2
 */