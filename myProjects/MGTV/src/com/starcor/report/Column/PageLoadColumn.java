package com.starcor.report.Column;

import com.starcor.core.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class PageLoadColumn extends Column
{
  public static final int PAGE_EXIT = 1;
  public static final int PAGE_LOAD;

  public static class Builder
  {
    private int ext1;
    private String ext10;
    private String ext2;
    private String ext3;
    private String ext4;
    private int ext5 = 0;
    private long ext6;
    private long ext7;
    private String ext8;
    private String ext9;

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

    public Builder addExt5(boolean paramBoolean)
    {
      if (paramBoolean);
      for (int i = 1; ; i = 0)
      {
        this.ext5 = i;
        return this;
      }
    }

    public Builder addExt6(long paramLong)
    {
      this.ext6 = paramLong;
      return this;
    }

    public Builder addExt7(long paramLong)
    {
      this.ext7 = paramLong;
      return this;
    }

    public Builder addExt8(String paramString)
    {
      this.ext8 = paramString;
      return this;
    }

    public Builder addExt9(String paramString)
    {
      this.ext9 = paramString;
      return this;
    }

    public PageLoadColumn build()
    {
      PageLoadColumn localPageLoadColumn = new PageLoadColumn(null);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("bid", "3.1.11");
        localJSONObject.put("act", "pageload");
        localJSONObject.put("ext1", String.valueOf(this.ext1));
        localJSONObject.put("ext2", PublicColumn.getEmptyIfEmpty(this.ext2));
        localJSONObject.put("ext3", PublicColumn.getNullIfEmpty(this.ext3));
        localJSONObject.put("ext4", PublicColumn.getNullIfEmpty(this.ext4));
        localJSONObject.put("ext5", String.valueOf(this.ext5));
        localJSONObject.put("ext6", String.valueOf(this.ext6));
        localJSONObject.put("ext7", String.valueOf(this.ext7));
        localJSONObject.put("ext8", PublicColumn.getNullIfEmpty(this.ext8));
        localJSONObject.put("ext9", PublicColumn.getNullIfEmpty(this.ext9));
        localJSONObject.put("ext10", PublicColumn.getNullIfEmpty(this.ext10));
        localPageLoadColumn.buildJsonData(localJSONObject);
        return localPageLoadColumn;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          Logger.w("ReportService", "PageLoadColumn json exception!", localJSONException);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.PageLoadColumn
 * JD-Core Version:    0.6.2
 */