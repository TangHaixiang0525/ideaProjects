package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Debug
{
  static final String className = ClientComms.class.getName();
  static String lineSep = System.getProperty("line.separator", "\n");
  static String separator = "==============";
  String clientID;
  ClientComms comms;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);

  public Debug(String paramString, ClientComms paramClientComms)
  {
    this.clientID = paramString;
    this.comms = paramClientComms;
    this.log.setResourceName(paramString);
  }

  public static String dumpProperties(Properties paramProperties, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Enumeration localEnumeration = paramProperties.propertyNames();
    localStringBuffer.append(lineSep + separator + " " + paramString + " " + separator + lineSep);
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      localStringBuffer.append(left(str, 28, ' ') + ":  " + paramProperties.get(str) + lineSep);
    }
    localStringBuffer.append(separator + separator + separator + lineSep);
    return localStringBuffer.toString();
  }

  public static String left(String paramString, int paramInt, char paramChar)
  {
    if (paramString.length() >= paramInt)
      return paramString;
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append(paramString);
    int i = paramInt - paramString.length();
    while (true)
    {
      i--;
      if (i < 0)
        break;
      localStringBuffer.append(paramChar);
    }
    return localStringBuffer.toString();
  }

  public void dumpBaseDebug()
  {
    dumpVersion();
    dumpSystemProperties();
    dumpMemoryTrace();
  }

  public void dumpClientComms()
  {
    if (this.comms != null)
    {
      Properties localProperties = this.comms.getDebug();
      this.log.fine(className, "dumpClientComms", dumpProperties(localProperties, this.clientID + " : ClientComms").toString());
    }
  }

  public void dumpClientDebug()
  {
    dumpClientComms();
    dumpConOptions();
    dumpClientState();
    dumpBaseDebug();
  }

  public void dumpClientState()
  {
    if ((this.comms != null) && (this.comms.getClientState() != null))
    {
      Properties localProperties = this.comms.getClientState().getDebug();
      this.log.fine(className, "dumpClientState", dumpProperties(localProperties, this.clientID + " : ClientState").toString());
    }
  }

  public void dumpConOptions()
  {
    if (this.comms != null)
    {
      Properties localProperties = this.comms.getConOptions().getDebug();
      this.log.fine(className, "dumpConOptions", dumpProperties(localProperties, this.clientID + " : Connect Options").toString());
    }
  }

  protected void dumpMemoryTrace()
  {
    this.log.dumpTrace();
  }

  public void dumpSystemProperties()
  {
    Properties localProperties = System.getProperties();
    this.log.fine(className, "dumpSystemProperties", dumpProperties(localProperties, "SystemProperties").toString());
  }

  protected void dumpVersion()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(lineSep + separator + " Version Info " + separator + lineSep);
    localStringBuffer.append(left("Version", 20, ' ') + ":  " + ClientComms.VERSION + lineSep);
    localStringBuffer.append(left("Build Level", 20, ' ') + ":  " + ClientComms.BUILD_LEVEL + lineSep);
    localStringBuffer.append(separator + separator + separator + lineSep);
    this.log.fine(className, "dumpVersion", localStringBuffer.toString());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.util.Debug
 * JD-Core Version:    0.6.2
 */