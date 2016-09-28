package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class ExceptionHelper
{
  public static MqttException createMqttException(int paramInt)
  {
    if ((paramInt == 4) || (paramInt == 5))
      return new MqttSecurityException(paramInt);
    return new MqttException(paramInt);
  }

  public static MqttException createMqttException(Throwable paramThrowable)
  {
    if (paramThrowable.getClass().getName().equals("java.security.GeneralSecurityException"))
      return new MqttSecurityException(paramThrowable);
    return new MqttException(paramThrowable);
  }

  public static boolean isClassAvailable(String paramString)
  {
    try
    {
      Class.forName(paramString);
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.ExceptionHelper
 * JD-Core Version:    0.6.2
 */