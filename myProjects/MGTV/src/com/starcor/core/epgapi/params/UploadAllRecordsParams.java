package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.IntegerParams;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadAllRecordsParams extends Api
{
  public static final int TYPE_COLLECT = 1;
  public static final int TYPE_PLAYLIST = 2;
  public static final int TYPE_TRACEOPLAY = 3;
  public int Record_type = 0;
  private StringParams add_time;
  private StringParams category_id;
  private StringParams media_asset_id;
  private StringParams nns_sync_data;
  private StringParams nns_user_id;
  private StringParams nns_version;
  private StringParams nns_webtoken;
  public IntegerParams played_time_len;
  private StringParams tstv_begin_time;
  private StringParams tstv_time_len;
  private List<CollectListItem> upLoadAllRecordsDataList = null;
  private StringParams video_id;
  private IntegerParams video_index;
  private StringParams video_name;
  private IntegerParams video_type;

  public UploadAllRecordsParams(int paramInt, List<CollectListItem> paramList)
  {
    this.prefix = AppInfo.URL_n40_a;
    if (paramInt == 1)
    {
      this.Record_type = 1;
      this.nns_func.setValue("sync_collect_by_user");
    }
    while (true)
    {
      this.nns_user_id = new StringParams("nns_user_id");
      this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
      this.nns_version = new StringParams("nns_version");
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
      this.nns_webtoken = new StringParams("nns_webtoken");
      this.nns_webtoken.setValue(GlobalLogic.getInstance().getCheckWebToken());
      this.upLoadAllRecordsDataList = paramList;
      this.cacheValidTime = 0L;
      return;
      if (paramInt == 2)
      {
        this.Record_type = 2;
        this.nns_func.setValue("sync_playlist_by_user");
      }
      else if (paramInt == 3)
      {
        this.Record_type = 3;
        this.nns_func.setValue("sync_catch_by_user");
      }
    }
  }

  public String getAllRecordsList()
  {
    if ((this.upLoadAllRecordsDataList != null) && (this.upLoadAllRecordsDataList.size() != 0))
    {
      JSONArray localJSONArray = new JSONArray();
      int i = 0;
      while (true)
        if (i < this.upLoadAllRecordsDataList.size())
          try
          {
            JSONObject localJSONObject = new JSONObject();
            localJSONObject.put("video_id", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).video_id);
            localJSONObject.put("video_type", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).video_type);
            localJSONObject.put("media_asset_id", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).media_assets_id);
            localJSONObject.put("category_id", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).category_id);
            localJSONObject.put("video_name", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).video_name);
            localJSONObject.put("video_index", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).video_index);
            localJSONObject.put("played_time_len", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).played_time);
            localJSONObject.put("add_time", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).add_time);
            localJSONObject.put("tstv_time_len", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).ts_time_len);
            localJSONObject.put("tstv_begin_time", ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).ts_day + ((CollectListItem)this.upLoadAllRecordsDataList.get(i)).ts_begin);
            localJSONArray.put(localJSONObject);
            i++;
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
      return localJSONArray.toString();
    }
    return null;
  }

  public String getApiName()
  {
    String str = "";
    if (this.Record_type == 1)
      str = "N40_A_10";
    do
    {
      return str;
      if (this.Record_type == 2)
        return "N40_A_11";
    }
    while (this.Record_type != 3);
    return "N40_A_12";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_version + this.nns_webtoken + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UploadAllRecordsParams
 * JD-Core Version:    0.6.2
 */