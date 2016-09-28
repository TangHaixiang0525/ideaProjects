package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttReceivedMessage extends MqttMessage
{
  private int messageId;

  public int getMessageId()
  {
    return this.messageId;
  }

  public void setDuplicate(boolean paramBoolean)
  {
    super.setDuplicate(paramBoolean);
  }

  public void setMessageId(int paramInt)
  {
    this.messageId = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage
 * JD-Core Version:    0.6.2
 */