package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class MqttOutputStream extends OutputStream
{
  private static final String className = MqttOutputStream.class.getName();
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private BufferedOutputStream out;

  public MqttOutputStream(OutputStream paramOutputStream)
  {
    this.out = new BufferedOutputStream(paramOutputStream);
  }

  public void close()
    throws IOException
  {
    this.out.close();
  }

  public void flush()
    throws IOException
  {
    this.out.flush();
  }

  public void write(int paramInt)
    throws IOException
  {
    this.out.write(paramInt);
  }

  public void write(MqttWireMessage paramMqttWireMessage)
    throws IOException, MqttException
  {
    byte[] arrayOfByte1 = paramMqttWireMessage.getHeader();
    byte[] arrayOfByte2 = paramMqttWireMessage.getPayload();
    this.out.write(arrayOfByte1, 0, arrayOfByte1.length);
    this.out.write(arrayOfByte2, 0, arrayOfByte2.length);
    this.log.fine(className, "write", "500", new Object[] { paramMqttWireMessage });
  }

  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    this.out.write(paramArrayOfByte);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream
 * JD-Core Version:    0.6.2
 */