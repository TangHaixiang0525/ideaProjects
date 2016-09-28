package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttToken;

public class ConnectActionListener
  implements IMqttActionListener
{
  private MqttAsyncClient client;
  private ClientComms comms;
  private MqttConnectOptions options;
  private MqttClientPersistence persistence;
  private IMqttActionListener userCallback;
  private Object userContext;
  private MqttToken userToken;

  public ConnectActionListener(MqttAsyncClient paramMqttAsyncClient, MqttClientPersistence paramMqttClientPersistence, ClientComms paramClientComms, MqttConnectOptions paramMqttConnectOptions, MqttToken paramMqttToken, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    this.persistence = paramMqttClientPersistence;
    this.client = paramMqttAsyncClient;
    this.comms = paramClientComms;
    this.options = paramMqttConnectOptions;
    this.userToken = paramMqttToken;
    this.userContext = paramObject;
    this.userCallback = paramIMqttActionListener;
  }

  public void connect()
    throws MqttPersistenceException
  {
    MqttToken localMqttToken = new MqttToken(this.client.getClientId());
    localMqttToken.setActionCallback(this);
    localMqttToken.setUserContext(this);
    this.persistence.open(this.client.getClientId(), this.client.getServerURI());
    if (this.options.isCleanSession())
      this.persistence.clear();
    try
    {
      this.comms.connect(this.options, localMqttToken);
      return;
    }
    catch (MqttException localMqttException)
    {
      onFailure(localMqttToken, localMqttException);
    }
  }

  public void onFailure(IMqttToken paramIMqttToken, Throwable paramThrowable)
  {
    int i = this.comms.getNetworkModules().length;
    int j = 1 + this.comms.getNetworkModuleIndex();
    if (j < i)
    {
      this.comms.setNetworkModuleIndex(j);
      try
      {
        connect();
        return;
      }
      catch (MqttPersistenceException localMqttPersistenceException)
      {
        onFailure(paramIMqttToken, localMqttPersistenceException);
        return;
      }
    }
    if ((paramThrowable instanceof MqttException));
    for (MqttException localMqttException = (MqttException)paramThrowable; ; localMqttException = new MqttException(paramThrowable))
    {
      this.userToken.internalTok.markComplete(null, localMqttException);
      this.userToken.internalTok.notifyComplete();
      if (this.userCallback == null)
        break;
      this.userToken.setUserContext(this.userContext);
      this.userCallback.onFailure(this.userToken, paramThrowable);
      return;
    }
  }

  public void onSuccess(IMqttToken paramIMqttToken)
  {
    this.userToken.internalTok.markComplete(null, null);
    this.userToken.internalTok.notifyComplete();
    if (this.userCallback != null)
    {
      this.userToken.setUserContext(this.userContext);
      this.userCallback.onSuccess(this.userToken);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.ConnectActionListener
 * JD-Core Version:    0.6.2
 */