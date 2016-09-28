package com.starcor.core.epgapi.params;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;

public class GetCommonVideoIdParams extends Api
{
  private StringParams mNns_asset_id = null;
  private StringParams mNns_clip_id = null;
  private StringParams mNns_file_id = null;
  private StringParams mNns_live_id = null;

  public GetCommonVideoIdParams(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.prefix = AppInfo.URL_n200;
    this.nns_func.setValue("get_ids_by_mgtv");
    this.mNns_asset_id = new StringParams("nns_asset_id");
    this.mNns_asset_id.setValue(paramString1);
    this.mNns_clip_id = new StringParams("nns_clip_id");
    this.mNns_clip_id.setValue(paramString2);
    this.mNns_file_id = new StringParams("nns_file_id");
    this.mNns_file_id.setValue(paramString3);
    this.mNns_live_id = new StringParams("nns_live_id");
    this.mNns_live_id.setValue(paramString4);
  }

  public String getApiName()
  {
    return "N200_A_22";
  }

  public String toString()
  {
    String str = this.prefix;
    if ((str != null) && (!str.contains("?")))
      str = str + "?";
    StringBuilder localStringBuilder1 = new StringBuilder().append(str).append(this.nns_func);
    Object localObject1;
    Object localObject2;
    label105: Object localObject3;
    label135: StringBuilder localStringBuilder4;
    if ((this.mNns_asset_id == null) || (this.mNns_asset_id.getValue() == null))
    {
      localObject1 = "";
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localObject1);
      if ((this.mNns_clip_id != null) && (this.mNns_clip_id.getValue() != null))
        break label191;
      localObject2 = "";
      StringBuilder localStringBuilder3 = localStringBuilder2.append(localObject2);
      if ((this.mNns_file_id != null) && (this.mNns_file_id.getValue() != null))
        break label200;
      localObject3 = "";
      localStringBuilder4 = localStringBuilder3.append(localObject3);
      if ((this.mNns_live_id != null) && (this.mNns_live_id.getValue() != null))
        break label209;
    }
    label191: label200: label209: for (Object localObject4 = ""; ; localObject4 = this.mNns_live_id)
    {
      return localObject4 + this.suffix;
      localObject1 = this.mNns_asset_id;
      break;
      localObject2 = this.mNns_clip_id;
      break label105;
      localObject3 = this.mNns_file_id;
      break label135;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetCommonVideoIdParams
 * JD-Core Version:    0.6.2
 */