package com.starcor.apitask.impl;

import com.starcor.apitask.ParserHandler;
import com.starcor.apitask.ServerApiTask2;
import com.starcor.apitask.info.CenterEdgeSchedulingInfo;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.Logger;
import com.starcor.mgtv.boss.webUrlFormatter;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ApiTaskCenterEdgeScheduling extends ServerApiTask2
{
  public ApiTaskCenterEdgeScheduling()
  {
    this.prefix = "http://192.168.90.87/nn_cms/nn_cms_view/mgtv/n41_a.php";
    this.nns_func.setValue("center_edge_scheduling");
  }

  public ParserHandler getDefaultHandler()
  {
    return new ParserHandler()
    {
      CenterEdgeSchedulingInfo centerEdgeSchedulingInfo = new CenterEdgeSchedulingInfo();
      String curTagName;
      boolean is_proxy_cache = false;
      StringBuilder sb = new StringBuilder();

      public void characters(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
        throws SAXException
      {
        super.characters(paramAnonymousArrayOfChar, paramAnonymousInt1, paramAnonymousInt2);
        this.sb.append(paramAnonymousArrayOfChar, paramAnonymousInt1, paramAnonymousInt2);
        String str = this.sb.toString();
        if ("nns_proxy_node_ip".equals(this.curTagName))
          this.centerEdgeSchedulingInfo.mainUrl = str;
        if (this.is_proxy_cache)
        {
          if (this.centerEdgeSchedulingInfo.proxyCache == null)
            this.centerEdgeSchedulingInfo.proxyCache = new HashMap();
          Logger.i("xx", "CenterEdgeSchedulingInfo  " + this.curTagName + " = " + str);
          this.centerEdgeSchedulingInfo.proxyCache.put(this.curTagName, str);
        }
      }

      public void endDocument()
        throws SAXException
      {
        super.endDocument();
        setResult(this.centerEdgeSchedulingInfo);
      }

      public void endElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
        throws SAXException
      {
        super.endElement(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
        if ((paramAnonymousString2 != null) && (paramAnonymousString2.equalsIgnoreCase("nns_proxy_cache")))
          this.is_proxy_cache = false;
        this.curTagName = null;
      }

      public void startElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Attributes paramAnonymousAttributes)
        throws SAXException
      {
        super.startElement(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousAttributes);
        this.sb.setLength(0);
        this.curTagName = paramAnonymousString2;
        if ((this.curTagName != null) && (this.curTagName.equalsIgnoreCase("nns_proxy_cache")))
          this.is_proxy_cache = true;
      }
    };
  }

  public String getTaskName()
  {
    return "N41_A_3";
  }

  public String getUrl()
  {
    String str = buildUrlPrefix() + this.nns_func + this.suffix;
    return webUrlFormatter.i().formatUrl(str, getTaskName());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.apitask.impl.ApiTaskCenterEdgeScheduling
 * JD-Core Version:    0.6.2
 */