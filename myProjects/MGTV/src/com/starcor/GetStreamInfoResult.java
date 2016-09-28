package com.starcor;

public class GetStreamInfoResult
{
  public int httpcode;
  public Result result;
  public StreamInfo stream;

  public String toString()
  {
    return "GetStreamInfoResult [httpcode=" + this.httpcode + ", result=" + this.result + ", stream=" + this.stream + "]";
  }

  public static class Result
  {
    public int api_run_ms;
    public String reason;
    public int ret;

    public String toString()
    {
      return "Result [ret=" + this.ret + ", reason=" + this.reason + ", api_run_ms=" + this.api_run_ms + "]";
    }
  }

  public static class StreamInfo
  {
    public int code;
    public long data_size;
    public long file_size;
    public long flow_r_kbps;
    public int index;
    public int is_proxy_tunnel;
    public long play_pos;
    public String reason;
    public long run_ms;
    public int tasks;

    public String toString()
    {
      return "StreamInfo [index=" + this.index + ", run_ms=" + this.run_ms + ", flow_r_kbps=" + this.flow_r_kbps + ", tasks=" + this.tasks + ", file_size=" + this.file_size + ", play_pos=" + this.play_pos + ", data_size=" + this.data_size + ", code=" + this.code + ", reason=" + this.reason + "]";
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.GetStreamInfoResult
 * JD-Core Version:    0.6.2
 */