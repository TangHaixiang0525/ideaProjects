package com.starcor.core.domain;

import java.util.Arrays;

public class BootAdData
{
  public Data data;
  public int err_code;
  public String success;

  public String toString()
  {
    return "BootAdData{data=" + this.data.toString() + ", success='" + this.success + '\'' + ", err_code=" + this.err_code + '}';
  }

  public class Data
  {
    public int duration;
    public String[] impression;
    public PosterData postdata;
    public int type;
    public String url;

    public Data()
    {
    }

    public String toString()
    {
      return "Data{duration=" + this.duration + ", type=" + this.type + ", postdata=" + this.postdata + ", impression=" + Arrays.toString(this.impression) + ", url='" + this.url + '\'' + '}';
    }

    public class PosterData
    {
      public String ip;
      public String mac;
      public String os;
      public String ts;
      public String ver;

      public PosterData()
      {
      }

      public String toString()
      {
        return "PosterData{os='" + this.os + '\'' + ", mac='" + this.mac + '\'' + ", ver='" + this.ver + '\'' + ", ts='" + this.ts + '\'' + ", ip='" + this.ip + '\'' + '}';
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.BootAdData
 * JD-Core Version:    0.6.2
 */