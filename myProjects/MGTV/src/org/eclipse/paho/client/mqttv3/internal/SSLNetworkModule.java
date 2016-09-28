package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class SSLNetworkModule extends TCPNetworkModule
{
  static final String className = SSLNetworkModule.class.getName();
  private String[] enabledCiphers;
  private int handshakeTimeoutSecs;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);

  public SSLNetworkModule(SSLSocketFactory paramSSLSocketFactory, String paramString1, int paramInt, String paramString2)
  {
    super(paramSSLSocketFactory, paramString1, paramInt, paramString2);
    this.log.setResourceName(paramString2);
  }

  public String[] getEnabledCiphers()
  {
    return this.enabledCiphers;
  }

  public void setEnabledCiphers(String[] paramArrayOfString)
  {
    this.enabledCiphers = paramArrayOfString;
    if ((this.socket != null) && (paramArrayOfString != null))
    {
      if (this.log.isLoggable(5))
      {
        String str = "";
        for (int i = 0; i < paramArrayOfString.length; i++)
        {
          if (i > 0)
            str = str + ",";
          str = str + paramArrayOfString[i];
        }
        this.log.fine(className, "setEnabledCiphers", "260", new Object[] { str });
      }
      ((SSLSocket)this.socket).setEnabledCipherSuites(paramArrayOfString);
    }
  }

  public void setSSLhandshakeTimeout(int paramInt)
  {
    this.handshakeTimeoutSecs = paramInt;
  }

  public void start()
    throws IOException, MqttException
  {
    super.start();
    setEnabledCiphers(this.enabledCiphers);
    int i = this.socket.getSoTimeout();
    if (i == 0)
      this.socket.setSoTimeout(1000 * this.handshakeTimeoutSecs);
    ((SSLSocket)this.socket).startHandshake();
    this.socket.setSoTimeout(i);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule
 * JD-Core Version:    0.6.2
 */