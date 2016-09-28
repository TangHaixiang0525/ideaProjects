package com.starcor.core.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils
{
  public static JSONArray getJsonArray(String paramString)
    throws JSONException
  {
    return new JSONArray(paramString);
  }

  public static JSONObject getJsonObject(String paramString)
    throws Exception
  {
    return new JSONObject(paramString);
  }

  public static String getJsonString(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    return paramJSONObject.getString(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.JsonUtils
 * JD-Core Version:    0.6.2
 */