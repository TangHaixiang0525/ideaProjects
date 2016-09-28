package com.starcor;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StreamInfoResultParser extends DefaultHandler
{
  GetStreamInfoResult result;

  private GetStreamInfoResult getResult()
  {
    return this.result;
  }

  public GetStreamInfoResult parser(InputStream paramInputStream)
  {
    try
    {
      SAXParserFactory.newInstance().newSAXParser().parse(paramInputStream, this);
      paramInputStream.close();
      GetStreamInfoResult localGetStreamInfoResult = getResult();
      return localGetStreamInfoResult;
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      localParserConfigurationException.printStackTrace();
      return null;
    }
    catch (SAXException localSAXException)
    {
      while (true)
        localSAXException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public void startDocument()
    throws SAXException
  {
    this.result = new GetStreamInfoResult();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equals("result"))
      this.result.result = new GetStreamInfoResult.Result();
    try
    {
      this.result.result.ret = Integer.valueOf(paramAttributes.getValue("ret")).intValue();
      label48: this.result.result.reason = paramAttributes.getValue("reason");
      try
      {
        this.result.result.api_run_ms = Integer.valueOf(paramAttributes.getValue("api_run_ms")).intValue();
        do
          return;
        while (!paramString2.equals("stream"));
        this.result.stream = new GetStreamInfoResult.StreamInfo();
        try
        {
          this.result.stream.index = Integer.valueOf(paramAttributes.getValue("index")).intValue();
          try
          {
            label141: this.result.stream.is_proxy_tunnel = Integer.valueOf(paramAttributes.getValue("is_proxy_tunnel")).intValue();
            try
            {
              label166: this.result.stream.run_ms = Long.valueOf(paramAttributes.getValue("run_ms")).longValue();
              try
              {
                label191: this.result.stream.flow_r_kbps = Long.valueOf(paramAttributes.getValue("flow_r_kbps")).longValue();
                try
                {
                  label216: this.result.stream.tasks = Integer.valueOf(paramAttributes.getValue("tasks")).intValue();
                  try
                  {
                    label241: this.result.stream.file_size = Long.valueOf(paramAttributes.getValue("file_size")).longValue();
                    try
                    {
                      label266: this.result.stream.play_pos = Long.valueOf(paramAttributes.getValue("play_pos")).longValue();
                      try
                      {
                        label291: this.result.stream.data_size = Long.valueOf(paramAttributes.getValue("data_size")).longValue();
                        try
                        {
                          label316: this.result.stream.code = Integer.valueOf(paramAttributes.getValue("code")).intValue();
                          label341: this.result.stream.reason = paramAttributes.getValue("reason");
                          return;
                        }
                        catch (Exception localException9)
                        {
                          break label341;
                        }
                      }
                      catch (Exception localException8)
                      {
                        break label316;
                      }
                    }
                    catch (Exception localException7)
                    {
                      break label291;
                    }
                  }
                  catch (Exception localException6)
                  {
                    break label266;
                  }
                }
                catch (Exception localException5)
                {
                  break label241;
                }
              }
              catch (Exception localException4)
              {
                break label216;
              }
            }
            catch (Exception localException3)
            {
              break label191;
            }
          }
          catch (Exception localException2)
          {
            break label166;
          }
        }
        catch (Exception localException1)
        {
          break label141;
        }
      }
      catch (Exception localException11)
      {
      }
    }
    catch (Exception localException10)
    {
      break label48;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.StreamInfoResultParser
 * JD-Core Version:    0.6.2
 */