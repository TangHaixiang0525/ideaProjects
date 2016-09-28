package com.starcor.report.Column;

import org.json.JSONException;
import org.json.JSONObject;

public class PageFocusColumn extends Column
{
  public static class Builder
  {
    private int ext1;
    private String ext10;
    private String ext2;
    private String ext3;
    private String ext4;
    private String ext5;

    public Builder addExt1(int paramInt)
    {
      this.ext1 = paramInt;
      return this;
    }

    public Builder addExt10(String paramString)
    {
      this.ext10 = paramString;
      return this;
    }

    public Builder addExt2(String paramString)
    {
      this.ext2 = paramString;
      return this;
    }

    public Builder addExt3(String paramString)
    {
      this.ext3 = paramString;
      return this;
    }

    public Builder addExt4(String paramString)
    {
      this.ext4 = paramString;
      return this;
    }

    public Builder addExt5(String paramString)
    {
      this.ext5 = paramString;
      return this;
    }

    public PageFocusColumn build()
    {
      PageFocusColumn localPageFocusColumn = new PageFocusColumn(null);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("bid", "3.1.11");
        localJSONObject.put("act", "onfocus");
        localJSONObject.put("ext1", String.valueOf(this.ext1));
        localJSONObject.put("ext2", PublicColumn.getEmptyIfEmpty(this.ext2));
        localJSONObject.put("ext3", PublicColumn.getNullIfEmpty(this.ext3));
        localJSONObject.put("ext4", PublicColumn.getNullIfEmpty(this.ext4));
        localJSONObject.put("ext5", PublicColumn.getNullIfEmpty(this.ext5));
        localJSONObject.put("ext6", "null");
        localJSONObject.put("ext7", "null");
        localJSONObject.put("ext8", "null");
        localJSONObject.put("ext9", "null");
        localJSONObject.put("ext10", PublicColumn.getNullIfEmpty(this.ext10));
        localPageFocusColumn.buildJsonData(localJSONObject);
        return localPageFocusColumn;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.PageFocusColumn
 * JD-Core Version:    0.6.2
 */