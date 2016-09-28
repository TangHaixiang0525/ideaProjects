package org.eclipse.paho.client.mqttv3.internal;

public abstract class MessageCatalog
{
  private static MessageCatalog INSTANCE = null;

  public static final String getMessage(int paramInt)
  {
    if ((INSTANCE != null) || (ExceptionHelper.isClassAvailable("java.util.ResourceBundle")));
    while (true)
    {
      try
      {
        INSTANCE = (MessageCatalog)Class.forName("org.eclipse.paho.client.mqttv3.internal.ResourceBundleCatalog").newInstance();
        return INSTANCE.getLocalizedMessage(paramInt);
      }
      catch (Exception localException2)
      {
        return "";
      }
      if (ExceptionHelper.isClassAvailable("org.eclipse.paho.client.mqttv3.internal.MIDPCatalog"))
        try
        {
          INSTANCE = (MessageCatalog)Class.forName("org.eclipse.paho.client.mqttv3.internal.MIDPCatalog").newInstance();
        }
        catch (Exception localException1)
        {
        }
    }
    return "";
  }

  protected abstract String getLocalizedMessage(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.MessageCatalog
 * JD-Core Version:    0.6.2
 */