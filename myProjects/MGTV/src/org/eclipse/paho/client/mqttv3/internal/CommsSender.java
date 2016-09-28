package org.eclipse.paho.client.mqttv3.internal;

import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsSender
  implements Runnable
{
  private static final String className = CommsSender.class.getName();
  private ClientComms clientComms = null;
  private ClientState clientState = null;
  private Object lifecycle = new Object();
  private Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private MqttOutputStream out;
  private boolean running = false;
  private Thread sendThread = null;
  private CommsTokenStore tokenStore = null;

  public CommsSender(ClientComms paramClientComms, ClientState paramClientState, CommsTokenStore paramCommsTokenStore, OutputStream paramOutputStream)
  {
    this.out = new MqttOutputStream(paramOutputStream);
    this.clientComms = paramClientComms;
    this.clientState = paramClientState;
    this.tokenStore = paramCommsTokenStore;
    this.log.setResourceName(paramClientComms.getClient().getClientId());
  }

  private void handleRunException(MqttWireMessage paramMqttWireMessage, Exception paramException)
  {
    this.log.fine(className, "handleRunException", "804", null, paramException);
    if (!(paramException instanceof MqttException));
    for (MqttException localMqttException = new MqttException(32109, paramException); ; localMqttException = (MqttException)paramException)
    {
      this.running = false;
      this.clientComms.shutdownConnection(null, localMqttException);
      return;
    }
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 40	org/eclipse/paho/client/mqttv3/internal/CommsSender:running	Z
    //   6: ifeq +203 -> 209
    //   9: aload_0
    //   10: getfield 67	org/eclipse/paho/client/mqttv3/internal/CommsSender:out	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream;
    //   13: ifnull +196 -> 209
    //   16: aload_0
    //   17: getfield 44	org/eclipse/paho/client/mqttv3/internal/CommsSender:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   20: invokevirtual 113	org/eclipse/paho/client/mqttv3/internal/ClientState:get	()Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;
    //   23: astore_1
    //   24: aload_1
    //   25: ifnull +160 -> 185
    //   28: aload_0
    //   29: getfield 60	org/eclipse/paho/client/mqttv3/internal/CommsSender:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   32: astore 4
    //   34: getstatic 34	org/eclipse/paho/client/mqttv3/internal/CommsSender:className	Ljava/lang/String;
    //   37: astore 5
    //   39: iconst_2
    //   40: anewarray 4	java/lang/Object
    //   43: astore 6
    //   45: aload 6
    //   47: iconst_0
    //   48: aload_1
    //   49: invokevirtual 118	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage:getKey	()Ljava/lang/String;
    //   52: aastore
    //   53: aload 6
    //   55: iconst_1
    //   56: aload_1
    //   57: aastore
    //   58: aload 4
    //   60: aload 5
    //   62: ldc 119
    //   64: ldc 121
    //   66: aload 6
    //   68: invokeinterface 124 5 0
    //   73: aload_1
    //   74: instanceof 126
    //   77: ifeq +31 -> 108
    //   80: aload_0
    //   81: getfield 67	org/eclipse/paho/client/mqttv3/internal/CommsSender:out	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream;
    //   84: aload_1
    //   85: invokevirtual 130	org/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream:write	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;)V
    //   88: aload_0
    //   89: getfield 67	org/eclipse/paho/client/mqttv3/internal/CommsSender:out	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream;
    //   92: invokevirtual 133	org/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream:flush	()V
    //   95: goto -93 -> 2
    //   98: astore_3
    //   99: aload_0
    //   100: aload_1
    //   101: aload_3
    //   102: invokespecial 135	org/eclipse/paho/client/mqttv3/internal/CommsSender:handleRunException	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Ljava/lang/Exception;)V
    //   105: goto -103 -> 2
    //   108: aload_0
    //   109: getfield 48	org/eclipse/paho/client/mqttv3/internal/CommsSender:tokenStore	Lorg/eclipse/paho/client/mqttv3/internal/CommsTokenStore;
    //   112: aload_1
    //   113: invokevirtual 141	org/eclipse/paho/client/mqttv3/internal/CommsTokenStore:getToken	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;)Lorg/eclipse/paho/client/mqttv3/MqttToken;
    //   116: astore 7
    //   118: aload 7
    //   120: ifnull -118 -> 2
    //   123: aload 7
    //   125: monitorenter
    //   126: aload_0
    //   127: getfield 67	org/eclipse/paho/client/mqttv3/internal/CommsSender:out	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream;
    //   130: aload_1
    //   131: invokevirtual 130	org/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream:write	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;)V
    //   134: aload_0
    //   135: getfield 67	org/eclipse/paho/client/mqttv3/internal/CommsSender:out	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream;
    //   138: invokevirtual 133	org/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream:flush	()V
    //   141: aload_0
    //   142: getfield 44	org/eclipse/paho/client/mqttv3/internal/CommsSender:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   145: aload_1
    //   146: invokevirtual 144	org/eclipse/paho/client/mqttv3/internal/ClientState:notifySent	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;)V
    //   149: aload 7
    //   151: monitorexit
    //   152: goto -150 -> 2
    //   155: astore 8
    //   157: aload 7
    //   159: monitorexit
    //   160: aload 8
    //   162: athrow
    //   163: astore_2
    //   164: aload_0
    //   165: aload_1
    //   166: aload_2
    //   167: invokespecial 135	org/eclipse/paho/client/mqttv3/internal/CommsSender:handleRunException	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Ljava/lang/Exception;)V
    //   170: goto -168 -> 2
    //   173: astore 9
    //   175: aload_1
    //   176: instanceof 146
    //   179: ifne -38 -> 141
    //   182: aload 9
    //   184: athrow
    //   185: aload_0
    //   186: getfield 60	org/eclipse/paho/client/mqttv3/internal/CommsSender:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   189: getstatic 34	org/eclipse/paho/client/mqttv3/internal/CommsSender:className	Ljava/lang/String;
    //   192: ldc 119
    //   194: ldc 148
    //   196: invokeinterface 151 4 0
    //   201: aload_0
    //   202: iconst_0
    //   203: putfield 40	org/eclipse/paho/client/mqttv3/internal/CommsSender:running	Z
    //   206: goto -204 -> 2
    //   209: aload_0
    //   210: getfield 60	org/eclipse/paho/client/mqttv3/internal/CommsSender:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   213: getstatic 34	org/eclipse/paho/client/mqttv3/internal/CommsSender:className	Ljava/lang/String;
    //   216: ldc 119
    //   218: ldc 153
    //   220: invokeinterface 151 4 0
    //   225: return
    //
    // Exception table:
    //   from	to	target	type
    //   16	24	98	org/eclipse/paho/client/mqttv3/MqttException
    //   28	95	98	org/eclipse/paho/client/mqttv3/MqttException
    //   108	118	98	org/eclipse/paho/client/mqttv3/MqttException
    //   123	126	98	org/eclipse/paho/client/mqttv3/MqttException
    //   160	163	98	org/eclipse/paho/client/mqttv3/MqttException
    //   185	206	98	org/eclipse/paho/client/mqttv3/MqttException
    //   126	134	155	finally
    //   134	141	155	finally
    //   141	152	155	finally
    //   157	160	155	finally
    //   175	185	155	finally
    //   16	24	163	java/lang/Exception
    //   28	95	163	java/lang/Exception
    //   108	118	163	java/lang/Exception
    //   123	126	163	java/lang/Exception
    //   160	163	163	java/lang/Exception
    //   185	206	163	java/lang/Exception
    //   134	141	173	java/io/IOException
  }

  public void start(String paramString)
  {
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.running = true;
        this.sendThread = new Thread(this, paramString);
        this.sendThread.start();
      }
      return;
    }
  }

  public void stop()
  {
    synchronized (this.lifecycle)
    {
      this.log.fine(className, "stop", "800");
      if (this.running)
      {
        this.running = false;
        boolean bool = Thread.currentThread().equals(this.sendThread);
        if (bool);
      }
    }
    try
    {
      this.clientState.notifyQueueLock();
      this.sendThread.join();
      label64: this.sendThread = null;
      this.log.fine(className, "stop", "801");
      return;
      localObject2 = finally;
      throw localObject2;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label64;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.CommsSender
 * JD-Core Version:    0.6.2
 */