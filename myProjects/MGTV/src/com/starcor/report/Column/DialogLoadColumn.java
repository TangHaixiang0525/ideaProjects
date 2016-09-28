package com.starcor.report.Column;

import com.starcor.core.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogLoadColumn extends Column
{
  public static final int DIALOG_EXIT = 1;
  public static final int DIALOG_LOAD = 0;
  public static final int DIALOG_MESSAGE_FLAG = 1;

  public static class Builder
  {
    private int ext1;
    private String ext10;
    private int ext2;
    private String ext3;
    private String ext4;
    private String ext5;
    private String ext6;
    private String ext7;
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

    public Builder addExt2(int paramInt)
    {
      this.ext2 = paramInt;
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

    public Builder addExt6(String paramString)
    {
      this.ext6 = paramString;
      return this;
    }

    public Builder addExt7(String paramString)
    {
      this.ext7 = paramString;
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

    public DialogLoadColumn build()
    {
      DialogLoadColumn localDialogLoadColumn = new DialogLoadColumn(null);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("bid", "3.1.11");
        localJSONObject.put("act", "ondialog");
        localJSONObject.put("ext1", PublicColumn.getNullIfEmpty(String.valueOf(this.ext1)));
        localJSONObject.put("ext2", PublicColumn.getNullIfEmpty(String.valueOf(this.ext2)));
        localJSONObject.put("ext3", PublicColumn.getNullIfEmpty(this.ext3));
        localJSONObject.put("ext4", PublicColumn.getNullIfEmpty(this.ext4));
        localJSONObject.put("ext5", PublicColumn.getNullIfEmpty(this.ext5));
        localJSONObject.put("ext6", PublicColumn.getNullIfEmpty(this.ext6));
        localJSONObject.put("ext7", PublicColumn.getNullIfEmpty(this.ext7));
        localJSONObject.put("ext8", PublicColumn.getNullIfEmpty(this.ext8));
        localJSONObject.put("ext9", PublicColumn.getNullIfEmpty(this.ext9));
        localJSONObject.put("ext10", PublicColumn.getNullIfEmpty(this.ext10));
        localDialogLoadColumn.buildJsonData(localJSONObject);
        return localDialogLoadColumn;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          Logger.w("ReportService", "DialogLoadColumn json exception!", localJSONException);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.DialogLoadColumn
 * JD-Core Version:    0.6.2
 */