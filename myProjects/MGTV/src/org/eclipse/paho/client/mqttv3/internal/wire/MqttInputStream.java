package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class MqttInputStream extends InputStream
{
  private static final String className = MqttInputStream.class.getName();
  private DataInputStream in;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);

  public MqttInputStream(InputStream paramInputStream)
  {
    this.in = new DataInputStream(paramInputStream);
  }

  public int available()
    throws IOException
  {
    return this.in.available();
  }

  public void close()
    throws IOException
  {
    this.in.close();
  }

  public int read()
    throws IOException
  {
    return this.in.read();
  }

  public MqttWireMessage readMqttWireMessage()
    throws IOException, MqttException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = this.in.readByte();
    int j = (byte)(0xF & i >>> 4);
    if ((j < 1) || (j > 14))
      throw ExceptionHelper.createMqttException(32108);
    long l = MqttWireMessage.readMBI(this.in).getValue();
    localByteArrayOutputStream.write(i);
    localByteArrayOutputStream.write(MqttWireMessage.encodeMBI(l));
    byte[] arrayOfByte1 = new byte[(int)(l + localByteArrayOutputStream.size())];
    this.in.readFully(arrayOfByte1, localByteArrayOutputStream.size(), arrayOfByte1.length - localByteArrayOutputStream.size());
    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
    MqttWireMessage localMqttWireMessage = MqttWireMessage.createWireMessage(arrayOfByte1);
    this.log.fine(className, "readMqttWireMessage", "501", new Object[] { localMqttWireMessage });
    return localMqttWireMessage;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream
 * JD-Core Version:    0.6.2
 */