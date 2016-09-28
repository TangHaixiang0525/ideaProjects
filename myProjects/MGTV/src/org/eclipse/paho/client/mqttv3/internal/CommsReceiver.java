package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsReceiver
  implements Runnable
{
  private static final String className = CommsReceiver.class.getName();
  private ClientComms clientComms = null;
  private ClientState clientState = null;
  private MqttInputStream in;
  private Object lifecycle = new Object();
  private Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private Thread recThread = null;
  private boolean running = false;
  private CommsTokenStore tokenStore = null;

  public CommsReceiver(ClientComms paramClientComms, ClientState paramClientState, CommsTokenStore paramCommsTokenStore, InputStream paramInputStream)
  {
    this.in = new MqttInputStream(paramInputStream);
    this.clientComms = paramClientComms;
    this.clientState = paramClientState;
    this.tokenStore = paramCommsTokenStore;
    this.log.setResourceName(paramClientComms.getClient().getClientId());
  }

  public boolean isRunning()
  {
    return this.running;
  }

  public void run()
  {
    MqttToken localMqttToken = null;
    while ((this.running) && (this.in != null))
    {
      MqttWireMessage localMqttWireMessage;
      try
      {
        this.log.fine(className, "run", "852");
        localMqttWireMessage = this.in.readMqttWireMessage();
        if (!(localMqttWireMessage instanceof MqttAck))
          break label189;
        localMqttToken = this.tokenStore.getToken(localMqttWireMessage);
        if (localMqttToken != null)
          try
          {
            this.clientState.notifyReceivedAck((MqttAck)localMqttWireMessage);
            continue;
          }
          finally
          {
          }
      }
      catch (MqttException localMqttException)
      {
        this.log.fine(className, "run", "856", null, localMqttException);
        this.running = false;
        this.clientComms.shutdownConnection(localMqttToken, localMqttException);
        continue;
        throw new MqttException(6);
      }
      catch (IOException localIOException)
      {
        this.log.fine(className, "run", "853");
        this.running = false;
      }
      if (!this.clientComms.isDisconnecting())
      {
        this.clientComms.shutdownConnection(localMqttToken, new MqttException(32109, localIOException));
        continue;
        label189: this.clientState.notifyReceivedMsg(localMqttWireMessage);
      }
    }
    this.log.fine(className, "run", "854");
  }

  public void start(String paramString)
  {
    this.log.fine(className, "start", "855");
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.running = true;
        this.recThread = new Thread(this, paramString);
        this.recThread.start();
      }
      return;
    }
  }

  public void stop()
  {
    synchronized (this.lifecycle)
    {
      this.log.fine(className, "stop", "850");
      if (this.running)
      {
        this.running = false;
        boolean bool = Thread.currentThread().equals(this.recThread);
        if (bool);
      }
    }
    try
    {
      this.recThread.join();
      label57: this.recThread = null;
      this.log.fine(className, "stop", "851");
      return;
      localObject2 = finally;
      throw localObject2;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label57;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.CommsReceiver
 * JD-Core Version:    0.6.2
 */