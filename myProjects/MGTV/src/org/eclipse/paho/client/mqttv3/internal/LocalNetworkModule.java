package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import org.eclipse.paho.client.mqttv3.MqttException;

public class LocalNetworkModule
  implements NetworkModule
{
  private Class LocalListener;
  private String brokerName;
  private Object localAdapter;

  public LocalNetworkModule(String paramString)
  {
    this.brokerName = paramString;
  }

  public InputStream getInputStream()
    throws IOException
  {
    try
    {
      InputStream localInputStream = (InputStream)this.LocalListener.getMethod("getClientInputStream", new Class[0]).invoke(this.localAdapter, new Object[0]);
      return localInputStream;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    try
    {
      OutputStream localOutputStream = (OutputStream)this.LocalListener.getMethod("getClientOutputStream", new Class[0]).invoke(this.localAdapter, new Object[0]);
      return localOutputStream;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public void start()
    throws IOException, MqttException
  {
    if (!ExceptionHelper.isClassAvailable("com.ibm.mqttdirect.modules.local.bindings.LocalListener"))
      throw ExceptionHelper.createMqttException(32103);
    try
    {
      this.LocalListener = Class.forName("com.ibm.mqttdirect.modules.local.bindings.LocalListener");
      Method localMethod = this.LocalListener.getMethod("connect", new Class[] { String.class });
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.brokerName;
      this.localAdapter = localMethod.invoke(null, arrayOfObject);
      label65: if (this.localAdapter == null)
        throw ExceptionHelper.createMqttException(32103);
      return;
    }
    catch (Exception localException)
    {
      break label65;
    }
  }

  public void stop()
    throws IOException
  {
    if (this.localAdapter != null);
    try
    {
      this.LocalListener.getMethod("close", new Class[0]).invoke(this.localAdapter, new Object[0]);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.LocalNetworkModule
 * JD-Core Version:    0.6.2
 */