package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsTokenStore
{
  static final String className = CommsTokenStore.class.getName();
  private MqttException closedResponse = null;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private String logContext;
  private Hashtable tokens;

  public CommsTokenStore(String paramString)
  {
    this.log.setResourceName(paramString);
    this.tokens = new Hashtable();
    this.logContext = paramString;
    this.log.fine(className, "<Init>", "308");
  }

  public void clear()
  {
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new Integer(this.tokens.size());
    localLogger.fine(str, "clear", "305", arrayOfObject);
    synchronized (this.tokens)
    {
      this.tokens.clear();
      return;
    }
  }

  public int count()
  {
    synchronized (this.tokens)
    {
      int i = this.tokens.size();
      return i;
    }
  }

  public MqttDeliveryToken[] getOutstandingDelTokens()
  {
    Vector localVector;
    synchronized (this.tokens)
    {
      this.log.fine(className, "getOutstandingDelTokens", "311");
      localVector = new Vector();
      Enumeration localEnumeration = this.tokens.elements();
      while (localEnumeration.hasMoreElements())
      {
        MqttToken localMqttToken = (MqttToken)localEnumeration.nextElement();
        if ((localMqttToken != null) && ((localMqttToken instanceof MqttDeliveryToken)) && (!localMqttToken.internalTok.isNotified()))
          localVector.addElement(localMqttToken);
      }
    }
    MqttDeliveryToken[] arrayOfMqttDeliveryToken = (MqttDeliveryToken[])localVector.toArray(new MqttDeliveryToken[localVector.size()]);
    return arrayOfMqttDeliveryToken;
  }

  public Vector getOutstandingTokens()
  {
    Vector localVector;
    synchronized (this.tokens)
    {
      this.log.fine(className, "getOutstandingTokens", "312");
      localVector = new Vector();
      Enumeration localEnumeration = this.tokens.elements();
      while (localEnumeration.hasMoreElements())
      {
        MqttToken localMqttToken = (MqttToken)localEnumeration.nextElement();
        if (localMqttToken != null)
          localVector.addElement(localMqttToken);
      }
    }
    return localVector;
  }

  public MqttToken getToken(String paramString)
  {
    return (MqttToken)this.tokens.get(paramString);
  }

  public MqttToken getToken(MqttWireMessage paramMqttWireMessage)
  {
    String str = paramMqttWireMessage.getKey();
    return (MqttToken)this.tokens.get(str);
  }

  public void open()
  {
    synchronized (this.tokens)
    {
      this.log.fine(className, "open", "310");
      this.closedResponse = null;
      return;
    }
  }

  protected void quiesce(MqttException paramMqttException)
  {
    synchronized (this.tokens)
    {
      this.log.fine(className, "quiesce", "309", new Object[] { paramMqttException });
      this.closedResponse = paramMqttException;
      return;
    }
  }

  public MqttToken removeToken(String paramString)
  {
    this.log.fine(className, "removeToken", "306", new Object[] { paramString });
    if (paramString != null)
      synchronized (this.tokens)
      {
        MqttToken localMqttToken1 = (MqttToken)this.tokens.get(paramString);
        if (localMqttToken1 != null)
          try
          {
            MqttToken localMqttToken2 = (MqttToken)this.tokens.remove(paramString);
            return localMqttToken2;
          }
          finally
          {
          }
      }
    return null;
  }

  public MqttToken removeToken(MqttWireMessage paramMqttWireMessage)
  {
    if (paramMqttWireMessage != null)
      return removeToken(paramMqttWireMessage.getKey());
    return null;
  }

  protected MqttDeliveryToken restoreToken(MqttPublish paramMqttPublish)
  {
    synchronized (this.tokens)
    {
      String str = new Integer(paramMqttPublish.getMessageId()).toString();
      if (this.tokens.containsKey(str))
      {
        localMqttDeliveryToken = (MqttDeliveryToken)this.tokens.get(str);
        this.log.fine(className, "restoreToken", "302", new Object[] { str, paramMqttPublish, localMqttDeliveryToken });
        return localMqttDeliveryToken;
      }
      MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(this.logContext);
      localMqttDeliveryToken.internalTok.setKey(str);
      this.tokens.put(str, localMqttDeliveryToken);
      this.log.fine(className, "restoreToken", "303", new Object[] { str, paramMqttPublish, localMqttDeliveryToken });
    }
  }

  protected void saveToken(MqttToken paramMqttToken, String paramString)
  {
    synchronized (this.tokens)
    {
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = paramMqttToken.toString();
      localLogger.fine(str, "saveToken", "307", arrayOfObject);
      paramMqttToken.internalTok.setKey(paramString);
      this.tokens.put(paramString, paramMqttToken);
      return;
    }
  }

  protected void saveToken(MqttToken paramMqttToken, MqttWireMessage paramMqttWireMessage)
    throws MqttException
  {
    synchronized (this.tokens)
    {
      if (this.closedResponse == null)
      {
        String str = paramMqttWireMessage.getKey();
        this.log.fine(className, "saveToken", "300", new Object[] { str, paramMqttWireMessage });
        saveToken(paramMqttToken, str);
        return;
      }
      throw this.closedResponse;
    }
  }

  public String toString()
  {
    String str1 = System.getProperty("line.separator", "\n");
    StringBuffer localStringBuffer = new StringBuffer();
    synchronized (this.tokens)
    {
      Enumeration localEnumeration = this.tokens.elements();
      if (localEnumeration.hasMoreElements())
      {
        MqttToken localMqttToken = (MqttToken)localEnumeration.nextElement();
        localStringBuffer.append("{" + localMqttToken.internalTok + "}" + str1);
      }
    }
    String str2 = localStringBuffer.toString();
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.CommsTokenStore
 * JD-Core Version:    0.6.2
 */