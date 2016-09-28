package com.starcor.core.domain;

import java.io.Serializable;

public class WebChatLogin
  implements Serializable
{
  public int err = -1;
  public String rcode = "";
  public String reason = "";
  public String scene_id = "";
  public String state = "";
  public String status = "";
  public String url = "";

  public String toString()
  {
    return "WebChatLogin{err=" + this.err + ", status='" + this.status + '\'' + ", scene_id='" + this.scene_id + '\'' + ", url='" + this.url + '\'' + ", rcode='" + this.rcode + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.WebChatLogin
 * JD-Core Version:    0.6.2
 */