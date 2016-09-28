package com.starcor.jump.bussines.simple;

import com.starcor.common.IntentDataManager;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.jump.bussines.CommonBussines;
import com.starcor.jump.bussines.data.CommonData;

public class ShowSearchBussines extends CommonBussines
{
  private static final String KEY_ACTION = "actions";
  private static final String KEY_CATEGORY_ID = "category_id";
  private static final String KEY_MEDIA_ASSET_NAME = "media_asset_name";
  private static final String KEY_SEARCH_RANGE = "search_range";
  private static final String TAG = ShowSearchBussines.class.getSimpleName();

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.common_to_search");
    putExtra("cmdstring", "actorinfo");
    setClassForActivity(ActivityAdapter.getInstance().getSearchActivity());
    putExtra("search_key", this._data.manager.getStringValue("key"));
    putExtra("media_asset_name", this._data.manager.getStringValue("media_asset_name"));
    putExtra("search_range", this._data.manager.getStringValue("search_range"));
    putExtra("category_id", this._data.categoryId);
    putExtra("actions", this._data.manager.getStringValue("actions"));
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.ShowSearchBussines
 * JD-Core Version:    0.6.2
 */