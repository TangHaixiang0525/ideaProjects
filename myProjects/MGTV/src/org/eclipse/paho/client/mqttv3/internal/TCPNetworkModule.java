package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class TCPNetworkModule
  implements NetworkModule
{
  static final String className = TCPNetworkModule.class.getName();
  private int conTimeout;
  private SocketFactory factory;
  private String host;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private int port;
  protected Socket socket;

  public TCPNetworkModule(SocketFactory paramSocketFactory, String paramString1, int paramInt, String paramString2)
  {
    this.log.setResourceName(paramString2);
    this.factory = paramSocketFactory;
    this.host = paramString1;
    this.port = paramInt;
  }

  public InputStream getInputStream()
    throws IOException
  {
    return this.socket.getInputStream();
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    return this.socket.getOutputStream();
  }

  public void setConnectTimeout(int paramInt)
  {
    this.conTimeout = paramInt;
  }

  public void start()
    throws IOException, MqttException
  {
    try
    {
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this.host;
      arrayOfObject[1] = new Integer(this.port);
      arrayOfObject[2] = new Long(1000 * this.conTimeout);
      localLogger.fine(str, "start", "252", arrayOfObject);
      InetSocketAddress localInetSocketAddress = new InetSocketAddress(this.host, this.port);
      this.socket = this.factory.createSocket();
      this.socket.connect(localInetSocketAddress, 1000 * this.conTimeout);
      return;
    }
    catch (ConnectException localConnectException)
    {
      this.log.fine(className, "start", "250", null, localConnectException);
      throw new MqttException(32103, localConnectException);
    }
  }

  public void stop()
    throws IOException
  {
    if (this.socket != null)
      this.socket.close();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule
 * JD-Core Version:    0.6.2
 */