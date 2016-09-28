package com.starcor.report.pay;

import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.report.Column.Column;
import com.starcor.report.Column.PublicColumn;
import org.json.JSONException;
import org.json.JSONObject;

public class RechargeColumn extends Column
{
  public static class Builder
  {
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;
    private String ext5;
    private String o;
    private String source;
    TvPayInfo tv;
    private String vipid;
    VodPayInfo vod;

    public Builder()
    {
      UserInfo localUserInfo = GlobalLogic.getInstance().getUserInfo();
      if (localUserInfo != null)
        this.vipid = localUserInfo.vip_id;
    }

    public Builder addOrderId(String paramString)
    {
      this.o = paramString;
      return this;
    }

    public Builder addSource(String paramString)
    {
      this.source = paramString;
      return this;
    }

    public Builder addTVInfo(TvPayInfo paramTvPayInfo)
    {
      this.tv = paramTvPayInfo;
      return this;
    }

    public Builder addVodInfo(VodPayInfo paramVodPayInfo)
    {
      this.vod = paramVodPayInfo;
      return this;
    }

    public RechargeColumn build()
    {
      RechargeColumn localRechargeColumn = new RechargeColumn();
      JSONObject localJSONObject = PayReportHelper.buildVideoInfo(this.vod, this.tv);
      try
      {
        localJSONObject.put("act", "recharge");
        localJSONObject.put("bid", "3.1.1");
        localJSONObject.put("o", PublicColumn.getNullIfEmpty(this.o));
        localJSONObject.put("source", PublicColumn.getNullIfEmpty(this.source));
        localJSONObject.put("vipid", PublicColumn.getNullIfEmpty(this.vipid));
        localJSONObject.put("ext1", PublicColumn.getNullIfEmpty(this.ext1));
        localJSONObject.put("ext2", PublicColumn.getNullIfEmpty(this.ext2));
        localJSONObject.put("ext3", PublicColumn.getNullIfEmpty(this.ext3));
        localJSONObject.put("ext4", PublicColumn.getNullIfEmpty(this.ext4));
        localJSONObject.put("ext5", PublicColumn.getNullIfEmpty(this.ext5));
        localRechargeColumn.buildJsonData(localJSONObject);
        localRechargeColumn.buildJsonData(PayReportHelper.buildVideoInfo(this.vod, this.tv));
        return localRechargeColumn;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          Logger.w("ReportService", "RechargeColumn json exception!", localJSONException);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.pay.RechargeColumn
 * JD-Core Version:    0.6.2
 */