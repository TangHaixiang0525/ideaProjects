package com.starcor.sccms.api;

import com.starcor.config.MgtvVersion;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.Skin;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetPictureHandler;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class SccmsApiGetSkinTask extends ServerApiTask
{
  ISccmsApiGetSkinTaskListener lsr = null;

  public SccmsApiGetSkinTask()
  {
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N36_A_2";
  }

  public String getUrl()
  {
    GetSkinPackageParams localGetSkinPackageParams = new GetSkinPackageParams();
    localGetSkinPackageParams.setResultFormat(0);
    String str = webUrlFormatter.i().formatUrl(localGetSkinPackageParams.toString(), localGetSkinPackageParams.getApiName());
    Logger.i(str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSkinInfoSaxParserInfo localGetSkinInfoSaxParserInfo = new GetSkinInfoSaxParserInfo();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    List localList;
    try
    {
      localList = (List)localGetSkinInfoSaxParserInfo.parser(paramSCResponse.getContents());
      if ((localList == null) || (localList.size() <= 0))
      {
        Logger.e("SccmsApiGetRelevantFilmsTask", "返回错误的结果数");
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, ""));
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
      return;
    }
    Logger.i("SccmsApiGetRelevantFilmsTask", " result:" + localList.toString());
    this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localList);
  }

  public void setListener(ISccmsApiGetSkinTaskListener paramISccmsApiGetSkinTaskListener)
  {
    this.lsr = paramISccmsApiGetSkinTaskListener;
  }

  public class GetSkinInfoSaxParserInfo<Result>
    implements IXmlParser<Result>
  {
    private static final String TAG = "GetSkinInfoSaxParserInfo";
    private Skin skin;

    public GetSkinInfoSaxParserInfo()
    {
    }

    public Result parser(InputStream paramInputStream)
    {
      if (paramInputStream == null)
        return null;
      InputSource localInputSource = new InputSource(paramInputStream);
      SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
      GetPictureHandler localGetPictureHandler = new GetPictureHandler();
      try
      {
        XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
        localXMLReader.setContentHandler(localGetPictureHandler);
        localXMLReader.setErrorHandler(localGetPictureHandler);
        localXMLReader.parse(localInputSource);
        return localGetPictureHandler.getSkins();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }

    public Result parser(byte[] paramArrayOfByte)
    {
      return parser(new ByteArrayInputStream(paramArrayOfByte));
    }
  }

  public class GetSkinPackageParams extends Api
  {
    private StringParams version;

    public GetSkinPackageParams()
    {
      this.prefix = AppInfo.URL_n36_a;
      this.nns_func.setValue("get_skin");
      this.version = new StringParams("nns_version");
      this.version.setValue(MgtvVersion.getVersion());
    }

    public String getApiName()
    {
      return "N36-A-2";
    }

    public String toString()
    {
      String str = this.prefix;
      if (!str.contains("?"))
        str = str + "?";
      return str + this.nns_func + this.version + this.suffix;
    }
  }

  public static abstract interface ISccmsApiGetSkinTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, List<Skin> paramList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetSkinTask
 * JD-Core Version:    0.6.2
 */