package com.starcor.report.Column;

import org.json.JSONException;
import org.json.JSONObject;

public class PageClickColumn extends Column
{
  public static final int BUTTON_DETAILPAGE_EPISODE = 2;
  public static final int BUTTON_DETAILPAGE_PLAY = 1;
  public static final int BUTTON_HOME = 9;
  public static final int BUTTON_MAINPAGE_TOP_LEVEL_CATEGORY = 7;
  public static final int BUTTON_MESSAGE_ACTION = 11;
  public static final int BUTTON_MESSAGE_LOOK = 10;
  public static final int BUTTON_PLAYPAGE_EXIT = 4;
  public static final int BUTTON_PLAYPAGE_INTERACTIVE_MSG = 6;
  public static final int BUTTON_PLAYPAGE_NEXT = 5;
  public static final int BUTTON_PLAYPAGE_SEL = 3;
  public static final int BUTTON_ZFBSQ = 8;

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

    public PageClickColumn build()
    {
      PageClickColumn localPageClickColumn = new PageClickColumn(null);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("bid", "3.1.11");
        localJSONObject.put("act", "onclick");
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
        localPageClickColumn.buildJsonData(localJSONObject);
        return localPageClickColumn;
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
 * Qualified Name:     com.starcor.report.Column.PageClickColumn
 * JD-Core Version:    0.6.2
 */