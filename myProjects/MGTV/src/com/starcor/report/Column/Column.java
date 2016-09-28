package com.starcor.report.Column;

import com.starcor.core.utils.Logger;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class Column extends PublicColumn
{
  public static final int LOAD_FAIL = 0;
  public static final int LOAD_SUCCESS = 1;
  private static final String TAG = "Column";

  private JSONObject mergeJson(JSONObject paramJSONObject)
  {
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      try
      {
        put(str, paramJSONObject.optString(str));
      }
      catch (Exception localException)
      {
        Logger.w("Column", "merge Json failed", localException);
      }
    }
    return this;
  }

  public JSONObject buildJsonData(JSONObject paramJSONObject)
  {
    super.buildJsonData();
    mergeJson(paramJSONObject);
    if (!has("pagename"));
    try
    {
      put("pagename", "");
      return this;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return this;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.Column
 * JD-Core Version:    0.6.2
 */