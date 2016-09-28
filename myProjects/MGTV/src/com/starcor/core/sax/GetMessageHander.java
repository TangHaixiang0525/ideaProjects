package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.ServerMessage;
import com.starcor.core.domain.ServerMessageList;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.SystemTimeManager;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetMessageHander extends DefaultHandler
{
  private ServerMessage msg = null;
  private ServerMessageList result = null;
  private StringBuilder sb = new StringBuilder();
  private String value = "";

  private String getDate()
  {
    return new SimpleDateFormat("yyyy/MM/dd").format(new Date(SystemTimeManager.getCurrentServerTime()));
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    if (paramInt2 > 0)
    {
      String str = this.sb.toString();
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
        this.value = str;
    }
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString3.equals("state"))
      this.result.state = this.value;
    if (paramString3.equals("reason"))
      this.result.reason = this.value;
    if (paramString3.equals("type"))
      this.msg.type = this.value;
    if (paramString3.equals("id"))
      this.msg.id = this.value;
    if (paramString3.equals("message_type"))
      this.msg.message_type = this.value;
    if (paramString3.equals("user_cluster_id"))
      this.msg.user_cluster_id = this.value;
    if (paramString3.equals("name"))
      this.msg.title = this.value;
    if (paramString3.equals("content"))
      this.msg.content = this.value;
    if (paramString3.equals("i"))
    {
      this.msg.date = getDate();
      Logger.i("MSG date" + this.msg.date);
      this.result.msgList.add(this.msg);
    }
    super.endElement(paramString1, paramString2, paramString3);
  }

  public ServerMessageList getMsgInfo()
  {
    return this.result;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.result == null)
    {
      this.result = new ServerMessageList();
      this.result.msgList = new ArrayList();
    }
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    if (paramString3.equals("i"))
      this.msg = new ServerMessage();
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetMessageHander
 * JD-Core Version:    0.6.2
 */