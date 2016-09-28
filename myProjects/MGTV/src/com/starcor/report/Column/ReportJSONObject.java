package com.starcor.report.Column;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportJSONObject extends JSONObject
{
  public JSONObject put(String paramString, Object paramObject)
    throws JSONException
  {
    if (paramObject == null)
      paramObject = "";
    return super.put(paramString, paramObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.ReportJSONObject
 * JD-Core Version:    0.6.2
 */