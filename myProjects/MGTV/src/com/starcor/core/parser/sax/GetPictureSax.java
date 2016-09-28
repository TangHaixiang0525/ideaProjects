package com.starcor.core.parser.sax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.starcor.core.domain.Skin;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetPictureHandler;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetPictureSax<Result>
  implements IXmlParser<Result>
{
  GetPictureHandler handler;

  public Bitmap getBitmap(String paramString)
  {
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setReadTimeout(5000);
      int i = localHttpURLConnection.getResponseCode();
      Object localObject = null;
      if (i == 200)
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(localHttpURLConnection.getInputStream());
        localObject = localBitmap;
      }
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public InputStream getInputStream(String paramString)
  {
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setConnectTimeout(5000);
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      return localInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public List<Skin> getSkins()
  {
    return this.handler.getSkins();
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

  public void saxSkins(InputStream paramInputStream)
  {
    try
    {
      SAXParser localSAXParser = SAXParserFactory.newInstance().newSAXParser();
      this.handler = new GetPictureHandler();
      localSAXParser.parse(paramInputStream, this.handler);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetPictureSax
 * JD-Core Version:    0.6.2
 */