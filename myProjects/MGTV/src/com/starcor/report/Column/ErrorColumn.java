package com.starcor.report.Column;

import com.starcor.core.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class ErrorColumn extends Column
{
  public static class Builder
  {
    private String apiName;
    private String errorCode;
    private String errorMess;
    private String httpCode;
    private String lcid;
    private String liveid;
    private String ln;
    private String oplid;
    private String ovid;
    private int playType = -1;
    private String plid;
    private String reqDomain;
    private String serverAddr;
    private String serverCode;
    private String soplid;
    private String sourceid;
    private String sovid;
    private String streamid;
    private int tpt = -1;
    private String vid;

    public Builder(String paramString1, String paramString2)
    {
      this.errorCode = paramString1;
      this.errorMess = paramString2;
    }

    private boolean hasItem(int paramInt)
    {
      return paramInt != -1;
    }

    private boolean hasItem(String paramString)
    {
      return paramString != null;
    }

    public Builder addApiName(String paramString)
    {
      this.apiName = paramString;
      return this;
    }

    public Builder addHttpCode(String paramString)
    {
      this.httpCode = paramString;
      return this;
    }

    public Builder addLcid(String paramString)
    {
      this.lcid = paramString;
      return this;
    }

    public Builder addLiveid(String paramString)
    {
      this.liveid = paramString;
      return this;
    }

    public Builder addLn(String paramString)
    {
      this.ln = paramString;
      return this;
    }

    public Builder addOplid(int paramInt)
    {
      this.oplid = Integer.toString(paramInt);
      return this;
    }

    public Builder addOplid(String paramString)
    {
      this.oplid = paramString;
      return this;
    }

    public Builder addOvid(int paramInt)
    {
      this.ovid = Integer.toString(paramInt);
      return this;
    }

    public Builder addOvid(String paramString)
    {
      this.ovid = paramString;
      return this;
    }

    public Builder addPlayType(int paramInt)
    {
      this.playType = paramInt;
      return this;
    }

    public Builder addPlid(String paramString)
    {
      this.plid = paramString;
      return this;
    }

    public Builder addReqestDomain(String paramString)
    {
      this.reqDomain = paramString;
      return this;
    }

    public Builder addServerAddr(String paramString)
    {
      this.serverAddr = paramString;
      return this;
    }

    public Builder addServerCode(String paramString)
    {
      this.serverCode = paramString;
      return this;
    }

    public Builder addSoplid(String paramString)
    {
      this.soplid = paramString;
      return this;
    }

    public Builder addSourceid(String paramString)
    {
      this.sourceid = paramString;
      return this;
    }

    public Builder addSovid(int paramInt)
    {
      this.sovid = Integer.toString(paramInt);
      return this;
    }

    public Builder addSovid(String paramString)
    {
      this.sovid = paramString;
      return this;
    }

    public Builder addStreamid(String paramString)
    {
      this.streamid = paramString;
      return this;
    }

    public Builder addTpt(int paramInt)
    {
      this.tpt = paramInt;
      return this;
    }

    public Builder addVid(String paramString)
    {
      this.vid = paramString;
      return this;
    }

    public ErrorColumn build()
    {
      ErrorColumn localErrorColumn = new ErrorColumn(null);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("act", "error");
        localJSONObject.put("bid", "3.1.3");
        localJSONObject.put("errorCode", PublicColumn.getEmptyIfEmpty(this.errorCode));
        localJSONObject.put("errorMess", PublicColumn.getEmptyIfEmpty(this.errorMess));
        if (hasItem(this.serverCode))
          localJSONObject.put("serverCode", this.serverCode);
        if (hasItem(this.playType))
        {
          localJSONObject.put("pt", this.playType);
          if (hasItem(this.tpt))
            localJSONObject.put("tpt", this.tpt);
          localJSONObject.put("vid", PublicColumn.getEmptyIfEmpty(this.vid));
          localJSONObject.put("ovid", PublicColumn.getEmptyIfEmpty(this.ovid));
          localJSONObject.put("sovid", PublicColumn.getEmptyIfEmpty(this.sovid));
          localJSONObject.put("plid", PublicColumn.getEmptyIfEmpty(this.plid));
          localJSONObject.put("oplid", PublicColumn.getEmptyIfEmpty(this.oplid));
          localJSONObject.put("soplid", PublicColumn.getEmptyIfEmpty(this.soplid));
          localJSONObject.put("lcid", PublicColumn.getEmptyIfEmpty(this.lcid));
          localJSONObject.put("sourceid", PublicColumn.getEmptyIfEmpty(this.sourceid));
          localJSONObject.put("streamid", PublicColumn.getEmptyIfEmpty(this.streamid));
          localJSONObject.put("ln", PublicColumn.getEmptyIfEmpty(this.ln));
          localJSONObject.put("liveid", PublicColumn.getEmptyIfEmpty(this.liveid));
        }
        while (true)
        {
          localJSONObject.put("ext1", PublicColumn.getEmptyIfEmpty(this.apiName));
          localJSONObject.put("ext2", PublicColumn.getEmptyIfEmpty(this.httpCode));
          localJSONObject.put("ext3", PublicColumn.getEmptyIfEmpty(this.reqDomain));
          localJSONObject.put("ext4", PublicColumn.getEmptyIfEmpty(this.serverAddr));
          localErrorColumn.buildJsonData(localJSONObject);
          return localErrorColumn;
          if (hasItem(this.tpt))
            localJSONObject.put("tpt", this.tpt);
          if (hasItem(this.vid))
            localJSONObject.put("vid", this.vid);
          if (hasItem(this.ovid))
            localJSONObject.put("ovid", this.ovid);
          if (hasItem(this.sovid))
            localJSONObject.put("sovid", this.sovid);
          if (hasItem(this.plid))
            localJSONObject.put("plid", this.plid);
          if (hasItem(this.oplid))
            localJSONObject.put("oplid", this.oplid);
          if (hasItem(this.soplid))
            localJSONObject.put("soplid", this.soplid);
          if (hasItem(this.lcid))
            localJSONObject.put("lcid", this.lcid);
          if (hasItem(this.sourceid))
            localJSONObject.put("sourceid", this.sourceid);
          if (hasItem(this.streamid))
            localJSONObject.put("streamid", this.streamid);
          if (hasItem(this.ln))
            localJSONObject.put("ln", this.ln);
          if (hasItem(this.liveid))
            localJSONObject.put("liveid", this.liveid);
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
          Logger.w("ReportService", "json exception!", localJSONException);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.ErrorColumn
 * JD-Core Version:    0.6.2
 */