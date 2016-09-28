package com.huawei.playerinterface.decoderConfig;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import com.huawei.dmpbase.DmpLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DecoderMatch
{
  private static final String TAG = "HAPlayer_decoderMatch";
  private static final String configFile = "HAPlayerDecoderInfo.xml";
  private Context context;
  private DeviceInfo device;
  private String updateUrl = null;

  public DecoderMatch(Context paramContext)
  {
    this.context = paramContext;
    this.device = new DeviceInfo(getDeviceID(), getDeviceSDKVersion());
    int[] arrayOfInt = ParseCfgXML(readStream(getValidCfgPath()), this.device.deviceID, this.device.sdkVersion);
    if (arrayOfInt != null)
      this.device.decoders = arrayOfInt;
  }

  public DecoderMatch(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.updateUrl = paramString;
  }

  private int[] GetDeviceDecoders(Element paramElement, String paramString, int paramInt)
  {
    String str = paramElement.getAttribute("name");
    if ((!str.equals(paramString)) && (!str.equals("default")));
    while (true)
    {
      return null;
      NodeList localNodeList = paramElement.getElementsByTagName("item");
      for (int i = 0; i < localNodeList.getLength(); i++)
      {
        Element localElement = (Element)localNodeList.item(i);
        if ((localElement.getNodeName().equals("item")) && (matchSDK(localElement.getAttribute("sdklevel"), paramInt)))
          return setDecoders(localElement.getAttribute("codecs"));
      }
    }
  }

  private int[] ParseCfgXML(InputStream paramInputStream, String paramString, int paramInt)
  {
    int[] arrayOfInt;
    if (paramInputStream == null)
      arrayOfInt = null;
    while (true)
    {
      return arrayOfInt;
      DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
      try
      {
        DocumentBuilder localDocumentBuilder2 = localDocumentBuilderFactory.newDocumentBuilder();
        localDocumentBuilder1 = localDocumentBuilder2;
        localDocument = null;
      }
      catch (ParserConfigurationException localParserConfigurationException)
      {
        try
        {
          DocumentBuilder localDocumentBuilder1;
          localDocument = localDocumentBuilder1.parse(paramInputStream);
          paramInputStream.close();
          if (localDocument == null)
          {
            DmpLog.e("HAPlayer_decoderMatch", "ParseCfgXML err: XML parse fail");
            return null;
            localParserConfigurationException = localParserConfigurationException;
            localParserConfigurationException.printStackTrace();
            localDocumentBuilder1 = null;
          }
        }
        catch (SAXException localSAXException)
        {
          while (true)
            localSAXException.printStackTrace();
        }
        catch (IOException localIOException)
        {
          Document localDocument;
          while (true)
            localIOException.printStackTrace();
          NodeList localNodeList = localDocument.getDocumentElement().getElementsByTagName("device");
          for (int i = 0; ; i++)
          {
            if (i >= localNodeList.getLength())
              break label150;
            arrayOfInt = GetDeviceDecoders((Element)localNodeList.item(i), paramString, paramInt);
            if (arrayOfInt != null)
              break;
          }
        }
      }
    }
    label150: return null;
  }

  private String getContextCfgFileDir()
  {
    return this.context.getCacheDir().getParent() + File.separator + this.context.getCacheDir().getName() + File.separator;
  }

  private String getContextCfgFilePath()
  {
    return getContextCfgFileDir() + "HAPlayerDecoderInfo.xml";
  }

  private static String getDeviceID()
  {
    return Build.MODEL;
  }

  private static int getDeviceSDKVersion()
  {
    return Build.VERSION.SDK_INT;
  }

  private String getSDCardCfgFilePath()
  {
    return Environment.getExternalStorageDirectory().getPath() + "/haplayer/" + "HAPlayerDecoderInfo.xml";
  }

  private String getValidCfgPath()
  {
    String str1 = getSDCardCfgFilePath();
    if (new File(str1).exists())
      return str1;
    String str2 = getContextCfgFilePath();
    if (new File(str2).exists())
      return str2;
    return null;
  }

  private boolean matchSDK(String paramString, int paramInt)
  {
    boolean bool = true;
    int i = paramString.indexOf("-");
    if (i != paramString.lastIndexOf("-"))
    {
      DmpLog.w("HAPlayer_decoderMatch", "matchSDK sdk err:" + paramString);
      bool = false;
    }
    String str1;
    String str2;
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return bool;
              if (i >= 0)
                break;
            }
            while (Integer.parseInt(paramString) == paramInt);
            return false;
          }
          while (paramString.length() < 2);
          str1 = paramString.substring(0, i);
          str2 = paramString.substring(i + 1, paramString.length());
          if (!str1.equals(""))
            break;
        }
        while (paramInt <= Integer.parseInt(str2));
        return false;
        if (!str2.equals(""))
          break;
      }
      while (paramInt >= Integer.parseInt(str1));
      return false;
    }
    while ((paramInt >= Integer.parseInt(str1)) && (paramInt <= Integer.parseInt(str2)));
    return false;
  }

  private InputStream readStream(String paramString)
  {
    if (paramString != null)
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(paramString);
        return localFileInputStream;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
        return null;
      }
    DmpLog.w("HAPlayer_decoderMatch", "open assets config file");
    try
    {
      InputStream localInputStream = this.context.getAssets().open("HAPlayerDecoderInfo.xml");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  private int[] setDecoders(String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    int[] arrayOfInt = new int[arrayOfString.length];
    for (int i = 0; i < arrayOfString.length; i++)
      arrayOfInt[i] = Integer.parseInt(arrayOfString[i]);
    return arrayOfInt;
  }

  public void UpdateConfigFile()
  {
    if (this.updateUrl == null)
    {
      DmpLog.e("HAPlayer_decoderMatch", "UpdateConfigFile err: updateUrl is null, please use DecoderMatch(Context context, String updateUrl) to initialize");
      return;
    }
    new Thread()
    {
      public void run()
      {
        try
        {
          int i = new NetDownload().download(DecoderMatch.this.updateUrl, DecoderMatch.this.getContextCfgFileDir(), "HAPlayerDecoderInfo.xml");
          System.out.println(i);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
    .start();
  }

  public int getDecoder()
  {
    return this.device.decoders[0];
  }

  public int[] getDecoderList()
  {
    return this.device.decoders;
  }

  void test()
  {
    test_matchSDK();
    test_setDecoders();
    if (test_ParseCfgXML())
    {
      DmpLog.i("HAPlayer_decoderMatch", "test ok");
      return;
    }
    DmpLog.i("HAPlayer_decoderMatch", "test error");
  }

  boolean test_ParseCfgXML()
  {
    int[] arrayOfInt1 = ParseCfgXML(readStream(getValidCfgPath()), "HTC", 9);
    if (arrayOfInt1 == null)
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_ParseCfgXML error: maybe no config xml file");
      return false;
    }
    if ((arrayOfInt1.length != 3) || (arrayOfInt1[0] != 1) || (arrayOfInt1[1] != 2) || (arrayOfInt1[2] != 3))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_ParseCfgXML error: HTC 9");
      return false;
    }
    int[] arrayOfInt2 = ParseCfgXML(readStream(getValidCfgPath()), "GT-I9100G", 9);
    if ((arrayOfInt2.length != 1) || (arrayOfInt2[0] != 1))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_ParseCfgXML error: GT-I9100G, 9");
      return false;
    }
    int[] arrayOfInt3 = ParseCfgXML(readStream(getValidCfgPath()), "GT-P7510", 8);
    if ((arrayOfInt3.length != 2) || (arrayOfInt3[0] != 1) || (arrayOfInt3[1] != 2))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_ParseCfgXML error: GT-P7510, 8");
      return false;
    }
    int[] arrayOfInt4 = ParseCfgXML(readStream(getValidCfgPath()), "GT-P7510", 9);
    if ((arrayOfInt4.length != 2) || (arrayOfInt4[0] != 1) || (arrayOfInt4[1] != 2))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_ParseCfgXML error: GT-P7510, 9");
      return false;
    }
    int[] arrayOfInt5 = ParseCfgXML(readStream(getValidCfgPath()), "GT", 15);
    if ((arrayOfInt5.length != 2) || (arrayOfInt5[0] != 1) || (arrayOfInt5[1] != 3))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_ParseCfgXML error: GT-P7510, 15");
      return false;
    }
    return true;
  }

  boolean test_matchSDK()
  {
    if (!matchSDK("0-", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 0-");
      return false;
    }
    if (!matchSDK("5-", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 5-");
      return false;
    }
    if (matchSDK("8", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 8");
      return false;
    }
    if (!matchSDK("9", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 9");
      return false;
    }
    if (!matchSDK("8-", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 8-");
      return false;
    }
    if (!matchSDK("9-", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 9-");
      return false;
    }
    if (!matchSDK("9-10", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 9-10");
      return false;
    }
    if (matchSDK("10-", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 10-");
      return false;
    }
    if (matchSDK("10-15", 9))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_matchSDK error: 10-15");
      return false;
    }
    return true;
  }

  boolean test_setDecoders()
  {
    int i = 1;
    int[] arrayOfInt1 = setDecoders("1");
    if ((arrayOfInt1.length != i) || (arrayOfInt1[0] != i))
    {
      DmpLog.e("HAPlayer_decoderMatch", "test_setDecoders error: 1");
      i = 0;
    }
    int[] arrayOfInt3;
    do
    {
      return i;
      int[] arrayOfInt2 = setDecoders("1,2");
      if ((arrayOfInt2.length != 2) || (arrayOfInt2[0] != i) || (arrayOfInt2[i] != 2))
      {
        DmpLog.e("HAPlayer_decoderMatch", "test_setDecoders error: 1,2");
        return false;
      }
      arrayOfInt3 = setDecoders("1,2,3");
    }
    while ((arrayOfInt3.length == 3) && (arrayOfInt3[0] == i) && (arrayOfInt3[i] == 2) && (arrayOfInt3[2] == 3));
    DmpLog.e("HAPlayer_decoderMatch", "test_setDecoders error: 1,2,3");
    return false;
  }

  class DeviceInfo
  {
    public int[] decoders;
    public String deviceID;
    public int sdkVersion;

    DeviceInfo(String paramInt, int arg3)
    {
      this.deviceID = paramInt;
      int i;
      this.sdkVersion = i;
      this.decoders = new int[4];
      this.decoders[0] = 1;
      this.decoders[1] = 3;
      this.decoders[2] = 2;
      this.decoders[3] = 4;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.decoderConfig.DecoderMatch
 * JD-Core Version:    0.6.2
 */