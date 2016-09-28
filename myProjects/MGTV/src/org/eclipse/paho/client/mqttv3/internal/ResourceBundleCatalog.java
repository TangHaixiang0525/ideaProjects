package org.eclipse.paho.client.mqttv3.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleCatalog extends MessageCatalog
{
  private ResourceBundle bundle = ResourceBundle.getBundle("org.eclipse.paho.client.mqttv3.internal.nls.messages");

  public ResourceBundleCatalog()
    throws MissingResourceException
  {
  }

  protected String getLocalizedMessage(int paramInt)
  {
    try
    {
      String str = this.bundle.getString(Integer.toString(paramInt));
      return str;
    }
    catch (MissingResourceException localMissingResourceException)
    {
    }
    return "MqttException";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.ResourceBundleCatalog
 * JD-Core Version:    0.6.2
 */