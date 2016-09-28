package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttDisconnect extends MqttWireMessage
{
  public static String KEY = "Disc";

  public MqttDisconnect()
  {
    super((byte)14);
  }

  public MqttDisconnect(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)14);
  }

  public String getKey()
  {
    return new String(KEY);
  }

  protected byte getMessageInfo()
  {
    return 0;
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return new byte[0];
  }

  public boolean isMessageIdRequired()
  {
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect
 * JD-Core Version:    0.6.2
 */