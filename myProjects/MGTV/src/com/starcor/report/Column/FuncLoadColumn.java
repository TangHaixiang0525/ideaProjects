package com.starcor.report.Column;

import com.starcor.core.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class FuncLoadColumn extends Column
{
  public static final int FUNC_LOAD_ALIPAY_QR = 5;
  public static final int FUNC_LOAD_FILTER_RESULT = 7;
  public static final int FUNC_LOAD_INTERACTIVE_MSG = 3;
  public static final int FUNC_LOAD_INTERACTIVE_PAGE = 4;
  public static final int FUNC_LOAD_PAY_QR = 1;
  public static final int FUNC_LOAD_PAY_RESULT = 2;
  public static final int FUNC_LOAD_STAR_LIBRARY = 6;
  public static final int FUNC_SEARCH_CONTENT;

  public static class Builder
  {
    private int ext1;
    private String ext10;
    private String ext2;
    private String ext3;
    private String ext4;
    private String ext5;
    private long ext6;
    private int ext7 = 0;
    private int ext8;
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

    public Builder addExt5(String paramString)
    {
      this.ext5 = paramString;
      return this;
    }

    public Builder addExt6(long paramLong)
    {
      this.ext6 = paramLong;
      return this;
    }

    public Builder addExt7(boolean paramBoolean)
    {
      if (paramBoolean);
      for (int i = 1; ; i = 0)
      {
        this.ext7 = i;
        return this;
      }
    }

    public Builder addExt8(int paramInt)
    {
      this.ext8 = paramInt;
      return this;
    }

    public Builder addExt9(String paramString)
    {
      this.ext9 = paramString;
      return this;
    }

    public FuncLoadColumn build()
    {
      FuncLoadColumn localFuncLoadColumn = new FuncLoadColumn(null);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("bid", "3.1.11");
        localJSONObject.put("act", "dataload");
        localJSONObject.put("ext1", String.valueOf(this.ext1));
        localJSONObject.put("ext2", PublicColumn.getEmptyIfEmpty(this.ext2));
        localJSONObject.put("ext3", PublicColumn.getNullIfEmpty(this.ext3));
        localJSONObject.put("ext4", PublicColumn.getNullIfEmpty(this.ext4));
        localJSONObject.put("ext5", PublicColumn.getNullIfEmpty(this.ext5));
        localJSONObject.put("ext6", String.valueOf(this.ext6));
        localJSONObject.put("ext7", String.valueOf(this.ext7));
        if (this.ext8 < 0);
        for (int i = 0; ; i = this.ext8)
        {
          localJSONObject.put("ext8", String.valueOf(i));
          localJSONObject.put("ext9", PublicColumn.getNullIfEmpty(this.ext9));
          localJSONObject.put("ext10", PublicColumn.getNullIfEmpty(this.ext10));
          localFuncLoadColumn.buildJsonData(localJSONObject);
          return localFuncLoadColumn;
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
          Logger.w("ReportService", "FuncLoadColumn json exception!", localJSONException);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.FuncLoadColumn
 * JD-Core Version:    0.6.2
 */