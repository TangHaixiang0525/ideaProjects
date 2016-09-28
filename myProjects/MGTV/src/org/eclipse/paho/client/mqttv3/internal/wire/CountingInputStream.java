package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends InputStream
{
  private int counter;
  private InputStream in;

  public CountingInputStream(InputStream paramInputStream)
  {
    this.in = paramInputStream;
    this.counter = 0;
  }

  public int getCounter()
  {
    return this.counter;
  }

  public int read()
    throws IOException
  {
    int i = this.in.read();
    if (i != -1)
      this.counter = (1 + this.counter);
    return i;
  }

  public void resetCounter()
  {
    this.counter = 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.CountingInputStream
 * JD-Core Version:    0.6.2
 */